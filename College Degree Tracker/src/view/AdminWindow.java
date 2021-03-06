package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AdminWindow {
	
	private LogoutButtonListener logoutButtonListener;
	
	private Scene scene;
	
	// BORDERPANE FOR WINDOW
	private BorderPane borderPane;
	
	// TOP OF BORDERPANE
	private HBox accountPanel;
	private Label nameLbl = new Label("Current User: ");
	private Label firstNameLbl = new Label();
	private Label lastNameLbl = new Label();
	private Label idLbl = new Label("User ID: ");
	private Label idNumLbl = new Label();
	private Button logoutBtn;

	// CENTER OF BORDERPANE
	//*** TabPane
	private TabPane tabPane;
	private Tab searchTab;
	private Tab tablesTab;	
	
	public AdminWindow() {	
		
		// Miscellaneous
		Font fontBold = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 14);
		nameLbl.setFont(fontBold);
		idLbl.setFont(fontBold);
		Text sainText =	new Text("Sain System Client");
		sainText.setStyle("-fx-font-size: 32px; " +
				"-fx-font-family: Arial Black;  -fx-fill: #818181; " + 
				"-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 6, 0.0, 0, 2)");

		// BorderPane
		borderPane = new BorderPane();
		borderPane.setStyle("-fx-border-width: 3px; -fx-border-color: grey");
		
		// Account Panel (TOP OF BORDERPANE)
		accountPanel = new HBox(280);
		accountPanel.setPadding(new Insets(15, 20, 15, 20));
		accountPanel.setStyle("-fx-background-color: linear-gradient(blue,dodgerblue, deepskyblue, deepskyblue, dodgerblue,blue)");
		
		HBox info = new HBox(10);
		info.getChildren().addAll(nameLbl, firstNameLbl, lastNameLbl, idLbl, idNumLbl);
		info.setAlignment(Pos.CENTER);
		logoutBtn = new Button("Logout");
		logoutBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		logoutBtn.setOnAction(e -> {
			LogoutButtonEventObject ev = 
					new LogoutButtonEventObject(this);
			if(logoutButtonListener != null) {
				logoutButtonListener.logoutButtonClicked(ev);
			}
		});
		HBox logout = new HBox();
		logout.getChildren().add(logoutBtn);
		logout.setPadding(new Insets(5, 0, 0, 250));
		
		accountPanel.getChildren().addAll(sainText, info, logout);
		accountPanel.setAlignment(Pos.CENTER);
		
		// TabPane (CENTER OF BORDERPANE)
		tabPane = new TabPane();
		tabPane.setStyle("-fx-border-color: black");
		tabPane.setStyle("-fx-border-width: 2px");
		tabPane.setStyle("-fx-background-color: #585858");
		
		searchTab = new Tab();
		searchTab.setText("Search");
		searchTab.setClosable(false);
		searchTab.setStyle("-fx-background-color: #7CAFC2");
		
		tablesTab = new Tab();
		tablesTab.setText("Tables");
		tablesTab.setClosable(false);
		tablesTab.setStyle("-fx-background-color: #86C1B9");
		
		tabPane.getTabs().addAll(searchTab, tablesTab);
		
		// Putting things together
		borderPane.setTop(accountPanel);
		borderPane.setCenter(tabPane);
		
		scene = new Scene(borderPane, 1800, 900);
	}
	
	public Scene getAdminScene() {
		return scene;
	}
	
	public void setLogoutButtonListener(LogoutButtonListener logoutButtonListener) {
		this.logoutButtonListener = logoutButtonListener;
	}
	
	public void setSearchTabContent(BorderPane searchWindow) {
		this.searchTab.setContent(searchWindow);
	}
	
	public void setTablesTabContent(BorderPane tablesWindow) {
		this.tablesTab.setContent(tablesWindow);
	}
	
	public void setAccountPanelInfo(String firstName, String lastName, String idNumber){
		this.firstNameLbl.setText(firstName);
		this.lastNameLbl.setText(lastName);
		this.idNumLbl.setText(idNumber);
	}
	
}
