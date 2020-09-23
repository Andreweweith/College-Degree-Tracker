package view;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ClientFacade {
	
	protected LoginWindow loginWindow;
	protected AdminWindow adminWindow;
	protected SearchWindow searchWindow;
	protected TablesWindow tablesWindow;
	protected ProfessorWindow professorWindow;
	protected ProfessorSearchWindow professorSearchWindow;
	protected StudentWindow studentWindow;
	protected StudentReportWindow studentReportWindow;

	protected Stage primaryStage;
	
	public ClientFacade(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setResizable(false);
		this.loginWindow = new LoginWindow();
	}
	
	public ClientFacade() {}
	
	public void constructLogin() {
		primaryStage.setTitle("Login");
		primaryStage.setScene(loginWindow.getLoginScene());
		primaryStage.show();
		
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2); 
		this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 4);
	}
	
	public void constructClientWindow(String accountType) {
		if (accountType != null) {
			System.out.println("Constructing: " + accountType + " Client");
		}
		if(accountType.startsWith("A")) {
			this.adminWindow = new AdminWindow();
			this.searchWindow = new SearchWindow();
			this.tablesWindow = new TablesWindow();
			adminWindow.setSearchTabContent(searchWindow.getSearchWindow());
			adminWindow.setTablesTabContent(tablesWindow.getTablesWindow());
			
			primaryStage.setTitle("Admin Client");
			primaryStage.setScene(adminWindow.getAdminScene());
			primaryStage.show();
			
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2); 
			this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 4);
		}
		else if(accountType.startsWith("P")){
			this.professorWindow = new ProfessorWindow();
			this.professorSearchWindow = new ProfessorSearchWindow();
			professorWindow.setSearchTabContent(professorSearchWindow.getProfessorSearchWindow());
			
		  	primaryStage.setTitle("Professor Client");
			primaryStage.setScene(professorWindow.getProfessorScene());
			primaryStage.show();
			
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2); 
			this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 4);
		}
		else if(accountType.startsWith("S")) {
			this.studentWindow = new StudentWindow();
			this.studentReportWindow = new StudentReportWindow();
			studentWindow.setReportTabContent(studentReportWindow.getStudentReportWindow());
			
			primaryStage.setTitle("Student Client");
			primaryStage.setScene(studentWindow.getStudentScene());
			primaryStage.show();
			
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			this.primaryStage.setX((primScreenBounds.getWidth() - this.primaryStage.getWidth()) / 2); 
			this.primaryStage.setY((primScreenBounds.getHeight() - this.primaryStage.getHeight()) / 4);
		}
		else{
			System.out.println("Error constructing client facade");
		}
	}
	
	public LoginWindow getLoginWindow() {
		return loginWindow;
	}
	
	public AdminWindow getAdminWindow() {
		return adminWindow;
	}
	
	public ProfessorWindow getProfessorWindow() {
		return professorWindow;
	}
	
	public StudentWindow getStudentWindow() {
		return studentWindow;
	}
	
	public SearchWindow getSearchWindow() {
		return searchWindow;
	}
	
	public ProfessorSearchWindow getProfessorSearchWindow() {
		return professorSearchWindow;
	}
	
	public StudentReportWindow getStudentReportWindow() {
		return studentReportWindow;
	}
	
	public TablesWindow getTablesWindow() {
		return tablesWindow;
	}

}
