package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import backEnd.Constants;
import backEnd.JogoDaVelha;

public class TileBoard {
		
	private InfoCenter infoCenter;
	private StackPane pane;
	private Tile[][] tiles = new Tile[3][3];
	private Line winningLine;
	private int[] jogadaComputador;
	
	private boolean playerTurn;
	private boolean CPU = true;
	private boolean User = false;
	public boolean isEndOfGame = true;
	public int profundidade;
	
	public JogoDaVelha algoritmo;
	
	public TileBoard(InfoCenter infoCenter) {
		this.algoritmo = new JogoDaVelha();
		this.infoCenter = infoCenter;
		pane = new StackPane();
		pane.setMinSize(UIConstants.WIDTH, UIConstants.TILE_BOARD_HEIGHT);
		pane.setTranslateX(UIConstants.WIDTH/2);
		pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT/2) + UIConstants.INFO_CENTER_HEIGHT);
	
		addAllTiles();
		
		winningLine = new Line();
		pane.getChildren().add(winningLine);
		
	}
	
	private void addAllTiles() {
		for (int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				Tile tile = new Tile(row,col);
				tile.getStackPane().setTranslateX((col * 150) - 150);
				tile.getStackPane().setTranslateY((row * 150) - 150);
				pane.getChildren().add(tile.getStackPane());
				tiles[row][col] = tile;
			}
		}
	}
	
	public void startNewGame(boolean player, int profundidade) {
		algoritmo.setProfundidade(profundidade);
		isEndOfGame = false;
		playerTurn = player;
		for (int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				tiles[row][col].setValue("");
			}
		}
		winningLine.setVisible(false);
		if(player) {
			pane.setDisable(true);
			PauseTransition espera = new PauseTransition(Duration.seconds(1));
			espera.setOnFinished(e -> {
				jogadaComputador = algoritmo.fazerJogadaDoComputador();
				marcarPosicao(jogadaComputador);
				changePlayerTurn();
				pane.setDisable(false);
			});
			espera.play();
		}
	}
	
	public void marcarPosicao(int[] jogadaComputador) {
		tiles[jogadaComputador[0]][jogadaComputador[1]].setValue("X");
	}
	
	public void changePlayerTurn() {
		if(playerTurn) {
			playerTurn = Constants.MIN;
			infoCenter.updateMessage("Turno do O");
		}else {
			playerTurn = Constants.MAX;
			infoCenter.updateMessage("Turno do X");
		}
		
	}
	
	public boolean getPlayerTurn() {
		return playerTurn;
	}
	
	public StackPane getStackPane() {
		return pane;
	}
	
	public void checkForWinner() {
		checkRowsForWinner();
		checkColsForWinner();
		checkTopLeftToBottomRightForWinner();
		checkTopRightToBottomLeftForWinner();
		checkForStalemate();
	}
	
	private void checkForStalemate() {
		// TODO Auto-generated method stub
		if(!isEndOfGame) {
			for(int row = 0; row < 3; row++) {
				for(int col = 0; col < 3; col++) {
					if(tiles[row][col].getValue().isEmpty()) {
						return;
					}
				}
			}
			isEndOfGame = true;
			algoritmo.reiniciar();
			infoCenter.updateMessage("Empate!");
			infoCenter.showStartButton();
			infoCenter.showDificuldadeComboBox();
			infoCenter.showDificuldadeLabel();
		}
	}

	private void checkTopRightToBottomLeftForWinner() {
		// TODO Auto-generated method stub
		if(!isEndOfGame) {
			if(tiles[0][2].getValue().equals(tiles[1][1].getValue()) &&
					tiles[0][2].getValue().equals(tiles[2][0].getValue()) &&
					!tiles[0][2].getValue().isEmpty()) {
				String winner = tiles[0][2].getValue();
				endGame(winner,new WinningTiles(tiles[0][2], tiles[1][1], tiles[2][0]));
				return;	
			}
		}
		
	}

	private void checkTopLeftToBottomRightForWinner() {
		// TODO Auto-generated method stub
		if(!isEndOfGame) {
			if(tiles[0][0].getValue().equals(tiles[1][1].getValue()) &&
					tiles[0][0].getValue().equals(tiles[2][2].getValue()) &&
					!tiles[0][0].getValue().isEmpty()) {
				String winner = tiles[0][0].getValue();
				endGame(winner,new WinningTiles(tiles[0][0], tiles[1][1], tiles[2][2]));
				return;	
			}
		}
		
	}

	private void checkColsForWinner() {
		// TODO Auto-generated method stub
		if(!isEndOfGame) {
			for(int col = 0; col < 3; col++) {
				if(tiles[0][col].getValue().equals( tiles[1][col].getValue()) &&
						tiles[0][col].getValue().equals(tiles[2][col].getValue()) &&
						!tiles[0][col].getValue().isEmpty()) {
					String winner = tiles[0][col].getValue();
					endGame(winner,new WinningTiles(tiles[0][col], tiles[1][col], tiles[2][col]));
					return;
				}
			}
		}
		
	}

	private void checkRowsForWinner() {
		// TODO Auto-generated method stub
		for(int row = 0; row < 3; row++) {
			if(tiles[row][0].getValue().equals( tiles[row][1].getValue()) &&
					tiles[row][0].getValue().equals(tiles[row][2].getValue()) &&
					!tiles[row][0].getValue().isEmpty()) {
				String winner = tiles[row][0].getValue();
				endGame(winner, new WinningTiles(tiles[row][0], tiles[row][1], tiles[row][2]));
				return;
			}
		}
		
	}
	
	private void endGame(String  winner, WinningTiles winningTiles) {
		isEndOfGame = true;	
		algoritmo.reiniciar();
		drawWinningLine(winningTiles);
		infoCenter.updateMessage(winner + " venceu!");
		infoCenter.showStartButton();
		infoCenter.showDificuldadeComboBox();
		infoCenter.showDificuldadeLabel();
	}

	private void drawWinningLine(WinningTiles winningTiles) {
		// TODO Auto-generated method stub
		winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
		winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
		winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
		winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());
		winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
		winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
		winningLine.setVisible(true);
	}
	
	private class WinningTiles{
		Tile start;
		Tile middle;
		Tile end;
		
		public WinningTiles(Tile start, Tile middle, Tile end) {
			this.start = start;
			this.middle = middle;
			this.end = end;
		}
	}

	private class Tile{
		
		private StackPane pane;
		private Label label;
		private int x,y;
		
		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
			pane = new StackPane();
			pane.setMinSize(100, 100);
			
			Rectangle border = new Rectangle();
			border.setHeight(150);
			border.setWidth(150);
			border.setFill(Color.TRANSPARENT);
			border.setStroke(Color.BLACK);
			pane.getChildren().add(border);
			
			label = new Label("");
			label.setAlignment(Pos.CENTER);
			label.setFont(Font.font(24));
			pane.getChildren().add(label);
			
			pane.setOnMouseClicked(event -> {
				if(!isEndOfGame) {
					//System.out.println("Esta recebendo o toque");
					//System.out.println(this.x + " " + this.y);
					/*
					if(engine.marcarPosicao(this.x, this.y,getPlayerTurn())) {
						if(getPlayerTurn()) {
							label.setText("X");
						}else {
							label.setText("O");
						}
						
						changePlayerTurn();
						checkForWinner();
					}
					*/
					if(algoritmo.marcarPosicaoUsuario(this.x, this.y)) {
						label.setText("O");
						checkForWinner();
						if(!isEndOfGame) {
							changePlayerTurn();
							
							pane.setDisable(true);
							PauseTransition espera = new PauseTransition(Duration.seconds(1));
							espera.setOnFinished(e -> {
								jogadaComputador = algoritmo.fazerJogadaDoComputador();
								marcarPosicao(jogadaComputador);
								changePlayerTurn();
								checkForWinner();
								pane.setDisable(false);
							});
							espera.play();
							
							
							
						}
						
					}
				}
				
//				System.out.println("mudar para x ou o se permitido");
			});
		}
		
		public StackPane getStackPane() {
			return pane;
		};
		public String getValue() {
			return label.getText();
		};
		
		public void setValue(String value) {
			label.setText(value);
		}
	}

}
