package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

@SuppressWarnings("rawtypes")
public class TablesWindow {
	
	private SetTableButtonEventListener setTableButtonEventListener;
	
	// PANE FOR THE WINDOW ***
	private BorderPane tablesWindow;
	
	// Search Bar items
	private HBox tablesBar;
	private Label selectTableLbl;
	private ComboBox<String> tablesComBox;
	private Button setTableBtn;
	
	// BorderPane for tables
	private BorderPane tablePane;	
	
	// Tables
	private TableView mainTable;
	
	// Boxes for Tables
	private HBox tableBox;

	private String comBoxSelection;

	private HBox newRowBar;

	private ComboBox selectTableEdit;

	private HBox accountsBox;

	private HBox coursesBox;

	private HBox departmentsBox;

	private HBox enrollmentBox;

	private HBox majorsBox;

	private HBox rowFieldsBox = new HBox(5);

	private Button addBtn;
	private AddButtonEventListener addButtonEventListener;

	private TextField idField;

	private TextField accountTypeField;

	private TextField usernameField;

	private TextField passwordField;

	private TextField firstNameField;

	private TextField lastNameField;

	private TextField addressField;

	private TextField phoneField;

	private TextField deptIdField;

	private TextField courseNumberField;

	private TextField titleField;

	private TextField creditsField;

	private TextField deptNameField;

	private TextField gradeField;
	
	@SuppressWarnings("unchecked")
	public TablesWindow() {
		
		// Window (BorderPane)
		tablesWindow = new BorderPane();
		
		// Search Bar (Top of BorderPane)
		tablesBar = new HBox(20);
		selectTableLbl = new Label("Select Table To View: ");
		
		tablesBar.setAlignment(Pos.CENTER);
		tablesBar.setPadding(new Insets(15, 5, 15, 5));
		tablesBar.setStyle("-fx-border-color: black");
		tablesBar.setStyle("-fx-background-color: linear-gradient(#D8D8D8, #B8B8B8, #D8D8D8)");
		
		// What-If major selection ComboBox
		tablesComBox = new ComboBox<>();
		tablesComBox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		tablesComBox.setOnHidden(e -> {
			if(tablesComBox.getValue() != null){
				setComBoxSelection(tablesComBox.getValue().toString());
				setTableBtn.setVisible(true);;
			}
		});
		
		setTableBtn = new Button("Generate Table");
		setTableBtn.setVisible(false);
		setTableBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		setTableBtn.setOnAction(e ->{
			mainTable.getColumns().removeAll(mainTable.getColumns());
			mainTable.getItems().removeAll(mainTable.getItems());
			
			SetTableButtonEventObject ev =
					new SetTableButtonEventObject(this, comBoxSelection, mainTable);
			if(setTableButtonEventListener != null) {
				setTableButtonEventListener.setTableButtonClicked(ev);
				mainTable.setVisible(true);
			}
		});

		tablesBar.getChildren().addAll(selectTableLbl, tablesComBox, setTableBtn);
		
		// Tables
		mainTable = new TableView<>();
		mainTable.setVisible(false);
		mainTable.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		
		tableBox = new HBox(20);
		tableBox.setAlignment(Pos.CENTER);
		tableBox.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		tableBox.setPadding(new Insets(5, 5, 5, 5));

		tableBox.getChildren().addAll(mainTable);
		
		newRowBar = new HBox(5);
		newRowBar.setAlignment(Pos.CENTER);
		
		selectTableEdit = new ComboBox<>();
		
		ObservableList<String> tablesList = FXCollections.observableArrayList();
		tablesList.addAll("accounts", "courses", "departments", "enrollment", "majors");
		selectTableEdit.setItems(tablesList);
		selectTableEdit.setValue("accounts");
		
		accountsBox = new HBox(5);
		coursesBox = new HBox(5);
		departmentsBox = new HBox(5);
		enrollmentBox = new HBox(5);
		majorsBox = new HBox(5);
		
		selectTableEdit.setOnHidden(e -> {
			rowFieldsBox.getChildren().clear();
			accountsBox.getChildren().clear();
			coursesBox.getChildren().clear();
			departmentsBox.getChildren().clear();
			enrollmentBox.getChildren().clear();
			majorsBox.getChildren().clear();
			
			if(selectTableEdit.getValue().toString() == "accounts"){
				idField = new TextField();
				accountTypeField = new TextField();
				usernameField = new TextField();
				passwordField = new TextField();
				firstNameField = new TextField();
				lastNameField = new TextField();
				addressField = new TextField();
				phoneField = new TextField();
				deptIdField = new TextField();
				
				accountsBox.getChildren().addAll(idField, accountTypeField, usernameField,
						passwordField, firstNameField, lastNameField, addressField, phoneField, deptIdField);
				rowFieldsBox.getChildren().add(accountsBox);
			}
			else if(selectTableEdit.getValue().toString() == "courses"){
				deptIdField = new TextField();
				courseNumberField = new TextField();
				titleField = new TextField();
				creditsField = new TextField();
				
				coursesBox.getChildren().addAll(deptIdField, courseNumberField, titleField, creditsField);
				rowFieldsBox.getChildren().add(coursesBox);
			}
			else if(selectTableEdit.getValue().toString() == "departments"){
				deptIdField = new TextField();
				deptNameField = new TextField();
				
				departmentsBox.getChildren().addAll(deptIdField, deptNameField);
				rowFieldsBox.getChildren().add(departmentsBox);
			}
			else if(selectTableEdit.getValue().toString() == "enrollment"){
				idField = new TextField();
				deptIdField = new TextField();
				courseNumberField = new TextField();
				gradeField = new TextField();
				
				enrollmentBox.getChildren().addAll(idField, deptIdField, courseNumberField, gradeField);
				rowFieldsBox.getChildren().add(enrollmentBox);
			}
			else if(selectTableEdit.getValue().toString() == "majors"){
				deptIdField = new TextField();
				courseNumberField = new TextField();
				
				majorsBox.getChildren().addAll(deptIdField, courseNumberField);
				rowFieldsBox.getChildren().add(majorsBox);
			}
			else{
				idField = new TextField();
				accountTypeField = new TextField();
				usernameField = new TextField();
				passwordField = new TextField();
				firstNameField = new TextField();
				lastNameField = new TextField();
				addressField = new TextField();
				phoneField = new TextField();
				deptIdField = new TextField();
				
				accountsBox.getChildren().addAll(idField, accountTypeField, usernameField,
						passwordField, firstNameField, lastNameField, addressField, phoneField, deptIdField);
				rowFieldsBox.getChildren().add(accountsBox);
			}
			
		});
		
		addBtn = new Button("Add Row");
		addBtn.setOnAction(e ->{
			/*AddButtonEventObject ev = new AddButtonEventObject(this);
			if(selectTableEdit.getValue().toString() == "accounts"){
				String id = idField.getText();
				String accountType = accountTypeField.getText();
				String username = usernameField.getText();
				String password = passwordField.getText();
				String firstName = firstNameField.getText();
				String lastName = lastNameField.getText();
				String address = addressField.getText();
				String phone = phoneField.getText();
				String deptId = deptIdField.getText();
				
				ev = new AddButtonEventObject(this, id, accountType, username, password,
						firstName, lastName, address, phone, deptId, 
						null, null, null, null, null);
			}
			else if(selectTableEdit.getValue().toString() == "courses"){
				String deptId = deptIdField.getText();
				String courseNumber = courseNumberField.getText();
				String title = titleField.getText();
				String credits = creditsField.getText();
				
				ev = new AddButtonEventObject(this, null, null, null, null,
						null, null, null, null, deptId,
						courseNumber, title, credits, null, null);
			}
			else if(selectTableEdit.getValue().toString() == "departments"){
				String deptId = deptIdField.getText();
				String deptName = deptNameField.getText();
				
				ev = new AddButtonEventObject(this, null, null, null, null,
						null, null, null, null, deptId,
						null, null, null, deptName, null);
			}
			else if(selectTableEdit.getValue().toString() == "enrollment"){
				String id = idField.getText();
				String deptId = deptIdField.getText();
				String courseNumber = courseNumberField.getText();
				String grade = gradeField.getText();
				
				ev = new AddButtonEventObject(this, id, null, null, null,
						null, null, null, null, deptId,
						courseNumber, null, null, null, grade);
			}
			else if(selectTableEdit.getValue().toString() == "majors"){
				String deptId = deptIdField.getText();
				String courseNumber = courseNumberField.getText();
				
				ev = new AddButtonEventObject(this, null, null, null, null,
						null, null, null, null, deptId,
						courseNumber, null, null, null, null);
			}
			else {
				ev = null;
			}
			
			if(addButtonEventListener != null && ev != null){
				addButtonEventListener.addButtonClicked(ev);
			}*/
			
		});
		
		newRowBar.getChildren().addAll(selectTableEdit, rowFieldsBox, addBtn);
		
		tablePane = new BorderPane();
		tablePane.setCenter(tableBox);
		
		tablesWindow.setTop(tablesBar);
		tablesWindow.setCenter(tablePane);
		tablesWindow.setBottom(newRowBar);
	}
	
	// Retrieve window for display
	public BorderPane getTablesWindow() {
		return tablesWindow;
	}
	
	public void setMainTable(TableView mainTable){
		this.mainTable = mainTable;
	}
	
	// ** search query to populate tablesBox
	public void setTablesComBox(ObservableList<String> tablesList) {
		this.tablesComBox.setItems(tablesList);
	}

	public String getComBoxSelection() {
		return comBoxSelection;
	}

	public void setComBoxSelection(String comBoxSelection) {
		this.comBoxSelection = comBoxSelection;
	}

	// Listeners
	public SetTableButtonEventListener getSetTableButtonEventListener() {
		return setTableButtonEventListener;
	}
	public void setSetTableButtonEventListener(SetTableButtonEventListener setTableButtonEventListener) {
		this.setTableButtonEventListener = setTableButtonEventListener;
	}
	
	public AddButtonEventListener addButtonEventListener() {
		return addButtonEventListener;
	}
	public void setAddButtonEventListener(AddButtonEventListener addButtonEventListener) {
		this.addButtonEventListener = addButtonEventListener;
	}
	

	public ComboBox getSelectTableEdit() {
		return selectTableEdit;
	}

	public void setSelectTableEdit(ComboBox selectTableEdit) {
		this.selectTableEdit = selectTableEdit;
	}
}