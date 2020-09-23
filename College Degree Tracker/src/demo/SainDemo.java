package demo;


import javafx.application.Application;
import javafx.stage.Stage;
import controller.SainController;
import model.Account;
import view.ClientFacade;

public class SainDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		ClientFacade client = new ClientFacade(primaryStage);
		Account acc = new Account();
		SainController controller = new SainController(client, acc);
		controller.setLoginWindow(primaryStage);
	}

}
