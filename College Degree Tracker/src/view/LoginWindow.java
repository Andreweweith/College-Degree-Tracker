package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginWindow {
	
	private LoginButtonListener loginButtonListener;
	
	private TextField userTextField = new TextField();
	private PasswordField pwBox = new PasswordField();
	private Scene scene;
	
	private String username;
	private String password;

	private VBox adminTestButtonBox;
	private Label adminLbl;
	private ToggleGroup accountTestButtons;

	private Button btn;

	private BorderPane loginWindow;
	private GridPane grid;

	private RadioButton adminRB;

	private RadioButton professorRB;

	private RadioButton studentRB;

	private Button clearBtn;
	
    public LoginWindow() {
    	
    	loginWindow = new BorderPane();
    	
        grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(40);
        grid.setVgap(20);
        grid.setPadding(new Insets(50, 10, 10, 0));
 
        Text scenetitle = new Text("SAIN");
        scenetitle.setStyle("-fx-font-size: 50px; " +
        		"-fx-font-family: Arial Black; " +
        		"-fx-fill: #818181; " +
        		"-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 6, 0.0, 0, 2)");
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        userName.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)"); 
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        
        userTextField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: grey");

        Label pw = new Label("Password:");
        pw.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)"); 
        grid.add(pw, 0, 2);  
        grid.add(pwBox, 1, 2);
        
        pwBox.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: grey");

        btn = new Button("Login");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e ->{
        	username = userTextField.getText(); 
    		password = pwBox.getText();
        	LoginButtonEventObject ev = 
        			new LoginButtonEventObject(this, username, password);
        	if(loginButtonListener != null){
        		loginButtonListener.loginButtonClicked(ev);
        	}
        	
			userTextField.clear();
			pwBox.clear();
			
        });

        Label accountsInfoLbl = new Label("Select a default login account for testing");
        accountsInfoLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)"); 
        HBox infoLblBox = new HBox();
        infoLblBox.setAlignment(Pos.CENTER);
        infoLblBox.getChildren().add(accountsInfoLbl);
        
        accountTestButtons = new ToggleGroup();
        
        adminRB = new RadioButton("Admin");
        adminRB.setToggleGroup(accountTestButtons);
        adminRB.setOnAction(e -> {
        	this.userTextField.setText("admin");
        	this.pwBox.setText("admin");
        });
        adminRB.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)");
        
        professorRB = new RadioButton("Professor");
        professorRB.setToggleGroup(accountTestButtons);
        professorRB.setOnAction(e -> {
        	this.userTextField.setText("professor");
        	this.pwBox.setText("password");
        });
        professorRB.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)");
        
        studentRB = new RadioButton("Student");
        studentRB.setToggleGroup(accountTestButtons);
        studentRB.setOnAction(e -> {
        	this.userTextField.setText("student");
        	this.pwBox.setText("password");
        });
        studentRB.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333; " +
        		"-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.5), 0, 0, 0, 1)");
        
        HBox topRadioButtonBox = new HBox(20);
        topRadioButtonBox.setAlignment(Pos.CENTER);
        topRadioButtonBox.getChildren().addAll(adminRB, professorRB, studentRB);
        HBox clearBtnBox = new HBox();
        clearBtnBox.setAlignment(Pos.CENTER);
        clearBtn = new Button("Clear");
        clearBtn.setOnAction(e -> {
        	this.userTextField.clear();
			this.pwBox.clear();
        	if (accountTestButtons.getSelectedToggle() != null) {
				accountTestButtons.getSelectedToggle().setSelected(false);
			}
        });
        clearBtnBox.getChildren().add(clearBtn);
        VBox topBoxes = new VBox(20);
        topBoxes.setAlignment(Pos.CENTER);
        topBoxes.setPadding(new Insets(25, 0, 0, 0));
        topBoxes.getChildren().addAll(infoLblBox, topRadioButtonBox, clearBtnBox);
        
        loginWindow.setTop(topBoxes);
        loginWindow.setCenter(grid);
        loginWindow.setStyle("-fx-background-image: url(loginBackground.jpg)");
        
        scene = new Scene(loginWindow, 1000, 600);
    }
    
    public Scene getLoginScene() {
    	return scene;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setLoginButtonListener(LoginButtonListener loginButtonListener){
    	this.loginButtonListener = loginButtonListener;
    }

	public VBox getAdminTestButtonBox() {
		return adminTestButtonBox;
	}

	public void setAdminTestButtonBox(VBox adminTestButtonBox) {
		this.adminTestButtonBox = adminTestButtonBox;
	}

	public Label getAdminLbl() {
		return adminLbl;
	}

	public void setAdminLbl(Label adminLbl) {
		this.adminLbl = adminLbl;
	}

	public ToggleGroup getAccountTestButtons() {
		return accountTestButtons;
	}

	public void setAccountTestButtons(ToggleGroup accountTestButtons) {
		this.accountTestButtons = accountTestButtons;
	}
    
}
