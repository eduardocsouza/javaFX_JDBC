package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
	
	private static Scene mainScene;
	
	
	@Override
	public void start(Stage stage) {
		try {			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			Pane pane = loader.load();
			
			
			/*
			 * nesse commando crimaos um objeto do tipo scrollpane e 
			 * em seguida informamos a largura e altura. 
			 * 
			 * scrollpane.setFitToWidth(true);
				scrollpane.setFitToHeight(true);	
			 */
					
			
			mainScene = new Scene(pane);
			mainScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(mainScene);
			stage.setTitle("Loja Ana Brito");
			stage.setResizable(false);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Scene getMainScene() {
		return mainScene;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

