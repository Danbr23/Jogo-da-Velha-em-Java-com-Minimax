package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class InfoCenter {
	
	private StackPane pane;
	private Label message, nivelDeDificuldade;
	private Button startGameButton, userStartButton, cpuStartButton;
	private ComboBox<Integer> dificuldade;
	private int dificuldadeSelecionada;
	
	public InfoCenter() {
		pane = new StackPane();
		pane.setMinSize(UIConstants.WIDTH, UIConstants.INFO_CENTER_HEIGHT);
		pane.setTranslateX(UIConstants.WIDTH/2);
		pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT/2);
		
		message = new Label("Tic-Tac-Toe");
		message.setMinSize(UIConstants.WIDTH, UIConstants.INFO_CENTER_HEIGHT);
		message.setFont(Font.font(24));
		message.setAlignment(Pos.CENTER);
		message.setTranslateY(-20);
		pane.getChildren().add(message);
		
		startGameButton = new Button("Novo Jogo");
		startGameButton.setMinSize(100, 30);
		startGameButton.setTranslateY(20);
		pane.getChildren().add(startGameButton);
		
		nivelDeDificuldade = new Label("Dificuldade:");
		nivelDeDificuldade.setTranslateY(20);
		nivelDeDificuldade.setTranslateX(-120);
		nivelDeDificuldade.setMinSize(135, 30);
		pane.getChildren().add(nivelDeDificuldade);
		
		dificuldade = new ComboBox<>();
		dificuldade.getItems().addAll(1,2,3,4,5,6,7,8,9);
		dificuldade.setValue(1);
		dificuldade.setTranslateY(20);
		dificuldade.setTranslateX(-90);
		dificuldade.setOnAction(e -> {
			this.dificuldadeSelecionada = dificuldade.getValue();
			System.out.println(dificuldadeSelecionada);
		});
		pane.getChildren().add(dificuldade);
		
		
		userStartButton = new Button("Jogador começa");
		userStartButton.setMinSize(135, 30);
		userStartButton.setTranslateY(20);
		userStartButton.setTranslateX(80);
		pane.getChildren().add(userStartButton);
		hideUserStartButton();
		
		cpuStartButton = new Button("CPU começa");
		cpuStartButton.setMinSize(135, 30);
		cpuStartButton.setTranslateY(20);
		cpuStartButton.setTranslateX(-80);
		pane.getChildren().add(cpuStartButton);
		hideCpuStartButton();
	}
	
	public StackPane getStackPane() {
		return pane;
	}
	
	public void updateMessage(String message) {
		this.message.setText(message);
	}
	
	public void showDificuldadeComboBox() {
		dificuldade.setVisible(true);
	}
	
	public void hideDificuldadeComboBox() {
		dificuldade.setVisible(false);
	}
	
	public void showDificuldadeLabel() {
		nivelDeDificuldade.setVisible(true);
	}
	
	public void hideDificuldadeLabel() {
		nivelDeDificuldade.setVisible(false);
	}
	
	public void showStartButton() {
		startGameButton.setVisible(true);
	}
	
	public void hideStartButton() {
		startGameButton.setVisible(false);
	}
	
	public void showUserStartButton() {
		userStartButton.setVisible(true);
	}
	
	public void hideUserStartButton() {
		userStartButton.setVisible(false);
	}
	
	public void showCpuStartButton() {
		cpuStartButton.setVisible(true);
	}
	
	public void hideCpuStartButton() {
		cpuStartButton.setVisible(false);
	}
	
	public ComboBox<Integer> getDificulade() {
		return this.dificuldade;
	}
	
	public void setStartButtonOnAction(EventHandler<ActionEvent> onAction) {
		startGameButton.setOnAction(onAction);
	}
	
	public void setUserStartButtonOnAction(EventHandler<ActionEvent> onAction) {
		userStartButton.setOnAction(onAction);
	}
	
	public void setCpuStartButtonOnAction(EventHandler<ActionEvent> onAction) {
		cpuStartButton.setOnAction(onAction);
	}

}
