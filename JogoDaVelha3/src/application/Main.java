package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private InfoCenter infoCenter;
	private TileBoard tileBoard;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,UIConstants.WIDTH,UIConstants.HEIGHT);
			initLayout(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initLayout(BorderPane root) {
		initInfoCenter(root);
		initTileBoard(root);
	}
	
	private void initTileBoard(BorderPane root) {
		// TODO Auto-generated method stub
		tileBoard = new TileBoard(infoCenter);
		root.getChildren().add(tileBoard.getStackPane());
		
	}

	private void initInfoCenter(BorderPane root) {
		// TODO Auto-generated method stub
		infoCenter = new InfoCenter();	
		infoCenter.setStartButtonOnAction(chooseWhoStart());
		infoCenter.setCpuStartButtonOnAction(startNewGame(true));
		infoCenter.setUserStartButtonOnAction(startNewGame(false));
		root.getChildren().add(infoCenter.getStackPane());
	}
	
	private EventHandler<ActionEvent> chooseWhoStart(){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				infoCenter.hideStartButton();
				infoCenter.hideDificuldadeComboBox();
				infoCenter.hideDificuldadeLabel();
				infoCenter.showCpuStartButton();
				infoCenter.showUserStartButton();
			}
		};
	}
	
	private EventHandler<ActionEvent> startNewGame(boolean player) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				infoCenter.hideCpuStartButton();
				infoCenter.hideUserStartButton();
				if(player) {
					infoCenter.updateMessage("Turno do X");
					tileBoard.startNewGame(true, (9 - (infoCenter.getDificulade().getValue() - 1)) );
				}else {
					infoCenter.updateMessage("Turno do O");
					tileBoard.startNewGame(false, (9 - (infoCenter.getDificulade().getValue() - 1)));
				}
			}
		};
	}

	public static void main(String[] args) {
		launch(args);
	}
}
