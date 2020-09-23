package view;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Course;

public class ProfessorSearchWindow {
	
	// Button Listeners
	private GenerateButtonListener generateButtonListener;
	private SearchButtonListener searchButtonListener;
	// ** Combo Box listener
	private WhatIfComBoxEventListener whatIfComBoxListener;
	
	// Event Object Handling for Radio Buttons
	private GenerateButtonEventObject ev;
	
	// Fields for Student Search
	private String studentFirstName;
	private String studentLastName;
	private String studentIdNumber;
	private String studentMajor;
	
	private String studentWhatIfMajor;
	
	// Searches for the report
	private ObservableList<Course> requiredCourses;
	private ObservableList<Course> requiredTaken;
	private ObservableList<Course> requiredProgress;		
	private ObservableList<Course> otherTaken;
	
	// PANE FOR THE WINDOW ***
	private BorderPane professorSearchWindow;
	
	// Search Bar items
	private HBox searchBar;
	private Label studentSearchLbl;
	private TextField idField;
	private Button searchBtn;
	
	// Panes for Info bar
	private HBox studentInfoBar;

	private VBox firstNamePane;
	private Label firstNameLbl;
	private TextField firstNameField;
	
	private VBox lastNamePane;
	private Label lastNameLbl;
	private TextField lastNameField;
	
	private VBox idNumberPane;
	private Label idNumberLbl;
	private TextField idNumberField;
	
	private VBox majorPane;
	private Label majorLbl;
	private TextField majorField;
	
	private VBox radioPane;
	private ToggleGroup group;
	private RadioButton rbSain;
	private RadioButton rbWhatIf;
	
	private ComboBox<String> whatIfComBox;
	private Button generateBtn;
	
	// Box to attach Search bar and Info bar
	private VBox attachBox;
	
	// BorderPane for report
	private BorderPane reportPane;	
	
	// Tables
	private TableView<Course> requiredMajorTable;
	private TableView<Course> requiredTakenTable;
	private TableView<Course> requiredProgressTable;
	private TableView<Course> otherTable;
	
	// TableColumns
	private TableColumn<Course, String> majorRequiredNumberColumn;
	private TableColumn<Course, String> majorRequiredNameColumn;
	private TableColumn<Course, String> majorRequiredCreditColumn;
	private TableColumn<Course, String> majorRequiredColumn;
	
	private TableColumn<Course, String> takenCoursesNumberColumn;
	private TableColumn<Course, String> takenCoursesNameColumn;
	private TableColumn<Course, String> takenCoursesCreditColumn;
	private TableColumn<Course, String> takenCoursesGradeColumn;
	private TableColumn<Course, String> takenCoursesColumn;
	
	private TableColumn<Course, String> progressCoursesNumberColumn;
	private TableColumn<Course, String> progressCoursesNameColumn;
	private TableColumn<Course, String> progressCoursesCreditColumn;
	private TableColumn<Course, String> progressCoursesGradeColumn;
	private TableColumn<Course, String> progressCoursesColumn;
		
	private TableColumn<Course, String> otherCoursesNumberColumn;
	private TableColumn<Course, String> otherCoursesNameColumn;
	private TableColumn<Course, String> otherCoursesCreditColumn;
	private TableColumn<Course, String> otherCoursesGradeColumn;
	private TableColumn<Course, String> otherCoursesColumn;
	
	// Boxes for Tables
	private HBox allTablesBox;
	private HBox requiredTablesBox;
	
	
	public ProfessorSearchWindow() {
		
		// Window (BorderPane)
		professorSearchWindow = new BorderPane();
		
		// Search Bar (Top of BorderPane)
		searchBar = new HBox(20);
		studentSearchLbl = new Label("Search for a Student using an ID number:");
		idField = new TextField();
		idField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: #E8E8E8");
		idField.setPromptText("Enter Student ID Number...");
		
		searchBtn = new Button("Student Search");
		searchBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		searchBtn.setOnAction(e ->{
			String idSearch = idField.getText();
			SearchButtonEventObject ev =
					new SearchButtonEventObject(this, idSearch);
			if(searchButtonListener != null){
				searchButtonListener.searchButtonClicked(ev);
			}
		});
		
		searchBar.getChildren().addAll(studentSearchLbl, idField, searchBtn);
		searchBar.setAlignment(Pos.CENTER);
		searchBar.setPadding(new Insets(15, 5, 15, 5));
		searchBar.setStyle("-fx-border-color: black");
		searchBar.setStyle("-fx-background-color: linear-gradient(#D8D8D8, #B8B8B8, #D8D8D8)");
		
		// Student Information Bar (Underneath/attached to Search Bar)
		studentInfoBar = new HBox(30);

		firstNamePane = new VBox(15);
		firstNameLbl = new Label("First Name");
		firstNameLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		firstNameField = new TextField();
		firstNameField.setAlignment(Pos.CENTER);
		firstNameField.setEditable(false);
		firstNameField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		firstNamePane.getChildren().addAll(firstNameLbl, firstNameField);
		firstNamePane.setAlignment(Pos.CENTER);
		
		lastNamePane = new VBox(15);
		lastNameLbl = new Label("Last Name");
		lastNameLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		lastNameField = new TextField();
		lastNameField.setAlignment(Pos.CENTER);
		lastNameField.setEditable(false);
		lastNameField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		lastNamePane.getChildren().addAll(lastNameLbl, lastNameField);
		lastNamePane.setAlignment(Pos.CENTER);

		idNumberPane = new VBox(15);
		idNumberLbl = new Label("ID Number");
		idNumberLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		idNumberField = new TextField();
		idNumberField.setAlignment(Pos.CENTER);
		idNumberField.setEditable(false);
		idNumberField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		idNumberPane.getChildren().addAll(idNumberLbl, idNumberField);
		idNumberPane.setAlignment(Pos.CENTER);
		
		majorPane = new VBox(15);
		majorLbl = new Label("Major");
		majorLbl.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		majorField = new TextField();
		majorField.setAlignment(Pos.CENTER);
		majorField.setEditable(false);
		majorField.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		majorPane.getChildren().addAll(majorLbl, majorField);
		majorPane.setAlignment(Pos.CENTER);
		
		// RadioPane for SAIN/what-if buttons
		radioPane = new VBox(20);
		group = new ToggleGroup();
		rbSain = new RadioButton("Generate SAIN Report");
		rbSain.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		rbWhatIf = new RadioButton("Generate WHAT-IF Statement");
		rbWhatIf.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #E8E8E8");
		rbSain.setToggleGroup(group);
		rbWhatIf.setToggleGroup(group);
		radioPane.getChildren().addAll(rbSain, rbWhatIf);
		
		// What-If major selection ComboBox
		whatIfComBox = new ComboBox<>();
		whatIfComBox.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		// ComboBox disabled until what-if button is selected 
		whatIfComBox.disableProperty().set(true);
		
		// SET EVENT OBJECT DECORATOR
		rbSain.setOnAction(e -> {
			whatIfComBox.disableProperty().set(true);
			generateBtn.setDisable(false);
			GenerateButtonEventObject ev = new GenerateButtonEventObject(this, studentIdNumber, studentMajor);
			this.setEv(ev);
			
			
		});
		rbWhatIf.setOnAction(e -> {
			whatIfComBox.disableProperty().set(false);
			generateBtn.setDisable(true);
		});
		whatIfComBox.setOnAction(e -> {
			generateBtn.setDisable(false);
			WhatIfComBoxEventObject ev1 = 
					new WhatIfComBoxEventObject(this, whatIfComBox.getValue().toString());
			if(whatIfComBoxListener != null && whatIfComBox.getValue() != null){
				whatIfComBoxListener.whatIfComBoxClicked(ev1);
				studentWhatIfMajor = whatIfComBox.getValue().toString();
				GenerateButtonEventObject ev2 = new GenerateButtonEventObject(this,
								studentIdNumber, studentWhatIfMajor);
				this.setEv(ev2);
			}
		});
		
		
		
		// GENERATE BUTTON Handling
		generateBtn = new Button("GENERATE");
		generateBtn.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3)");
		generateBtn.setDisable(true);
		generateBtn.setOnAction(e -> {
			if(generateButtonListener != null){
				generateButtonListener.generateButtonClicked(ev);
				
				// POPULATE TABLE
				requiredMajorTable.setItems(requiredCourses);
				requiredMajorTable.setVisible(true);
				requiredTakenTable.setItems(requiredTaken);
				requiredTakenTable.setVisible(true);
				requiredProgressTable.setItems(requiredProgress);
				requiredProgressTable.setVisible(true);
				otherTable.setItems(otherTaken);
				otherTable.setVisible(true);
			}
		});
			
		
		studentInfoBar.getChildren().addAll(firstNamePane, lastNamePane,
				idNumberPane, majorPane, radioPane, whatIfComBox, generateBtn);
		studentInfoBar.setAlignment(Pos.CENTER);
		studentInfoBar.setPadding(new Insets(15, 10, 30, 10));
		
		// Attach Search bar and Info bar
		attachBox = new VBox();
		attachBox.getChildren().addAll(searchBar, studentInfoBar);	
			
		// Required Courses TableView ----
		requiredMajorTable = new TableView<>();
		requiredTakenTable = new TableView<>();
		requiredProgressTable = new TableView<>();
		
		// REQUIRED FOR MAJOR
		majorRequiredNumberColumn = new TableColumn<Course, String>("Course Number");
		majorRequiredNumberColumn.setMinWidth(50);
		majorRequiredNumberColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("courseNumber"));
		
		majorRequiredNameColumn = new TableColumn<Course, String>("Course Name");
		majorRequiredNameColumn.setMinWidth(180);
		majorRequiredNameColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("title"));
		
		majorRequiredCreditColumn = new TableColumn<Course, String>("Credits");
		majorRequiredCreditColumn.setMinWidth(50);
		majorRequiredCreditColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("credits"));
		
		majorRequiredColumn = new TableColumn<Course, String>("Required For Major");
		majorRequiredColumn.setMinWidth(100);
		
		// TAKEN
		takenCoursesNumberColumn = new TableColumn<Course, String>("Course Number");
		takenCoursesNumberColumn.setMinWidth(50);
		takenCoursesNumberColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("courseNumber"));
		
		takenCoursesNameColumn = new TableColumn<Course, String>("Course Name");
		takenCoursesNameColumn.setMinWidth(180);
		takenCoursesNameColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("title"));
		
		takenCoursesCreditColumn = new TableColumn<Course, String>("Credits");
		takenCoursesCreditColumn.setMinWidth(50);
		takenCoursesCreditColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("credits"));
		
		takenCoursesGradeColumn = new TableColumn<Course, String>("Grade");
		takenCoursesGradeColumn.setMinWidth(50);
		takenCoursesGradeColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("grade"));
		
		takenCoursesColumn = new TableColumn<Course, String>("Required Courses Taken");
		takenCoursesColumn.setMinWidth(330);

		// IN PROGRESS
		progressCoursesNumberColumn = new TableColumn<Course, String>("Course Number");
		progressCoursesNumberColumn.setMinWidth(50);
		progressCoursesNumberColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("courseNumber"));
		
		progressCoursesNameColumn = new TableColumn<Course, String>("Course Name");
		progressCoursesNameColumn.setMinWidth(180);
		progressCoursesNameColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("title"));
		
		progressCoursesCreditColumn = new TableColumn<Course, String>("Credits");
		progressCoursesCreditColumn.setMinWidth(50);
		progressCoursesCreditColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("credits"));
		
		progressCoursesGradeColumn = new TableColumn<Course, String>("Grade");
		progressCoursesGradeColumn.setMinWidth(50);
		progressCoursesGradeColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("grade"));

		progressCoursesColumn = new TableColumn<Course, String>("Required Courses In-Progress");
		progressCoursesColumn.setMinWidth(330);

		
		// Add Columns
		majorRequiredColumn.getColumns().add(majorRequiredNumberColumn);
		majorRequiredColumn.getColumns().add(majorRequiredNameColumn);
		majorRequiredColumn.getColumns().add(majorRequiredCreditColumn);
		
		takenCoursesColumn.getColumns().add(takenCoursesNumberColumn);
		takenCoursesColumn.getColumns().add(takenCoursesNameColumn);
		takenCoursesColumn.getColumns().add(takenCoursesCreditColumn);
		takenCoursesColumn.getColumns().add(takenCoursesGradeColumn);
		
		progressCoursesColumn.getColumns().add(progressCoursesNumberColumn);
		progressCoursesColumn.getColumns().add(progressCoursesNameColumn);
		progressCoursesColumn.getColumns().add(progressCoursesCreditColumn);
		progressCoursesColumn.getColumns().add(progressCoursesGradeColumn);
		
		requiredMajorTable.getColumns().add(majorRequiredColumn);
		requiredMajorTable.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		requiredTakenTable.getColumns().add(takenCoursesColumn);
		requiredTakenTable.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		requiredProgressTable.getColumns().add(progressCoursesColumn);
		requiredProgressTable.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		
		// Set table visibility to FALSE
		requiredMajorTable.setVisible(false);
		requiredTakenTable.setVisible(false);
		requiredProgressTable.setVisible(false);
		
		
		// Other Courses TableView ----
		otherTable = new TableView<>();	
		
		// OTHER		
		otherCoursesNumberColumn = new TableColumn<Course, String>("Course Number");
		otherCoursesNumberColumn.setMinWidth(50);
		otherCoursesNumberColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("courseNumber"));
		
		otherCoursesNameColumn = new TableColumn<Course, String>("Course Name");
		otherCoursesNameColumn.setMinWidth(180);
		otherCoursesNameColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("title"));
		
		otherCoursesCreditColumn = new TableColumn<Course, String>("Credits");
		otherCoursesCreditColumn.setMinWidth(50);
		otherCoursesCreditColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("credits"));
		
		otherCoursesGradeColumn = new TableColumn<Course, String>("Grade");
		otherCoursesGradeColumn.setMinWidth(50);
		otherCoursesGradeColumn.setCellValueFactory(
				new PropertyValueFactory<Course, String>("grade"));
		
		otherCoursesColumn = new TableColumn<Course, String>("Other Courses Taken");
		otherCoursesColumn.setMinWidth(330);
		
		// Add Columns
		otherCoursesColumn.getColumns().add(otherCoursesNumberColumn);
		otherCoursesColumn.getColumns().add(otherCoursesNameColumn);
		otherCoursesColumn.getColumns().add(otherCoursesCreditColumn);
		otherCoursesColumn.getColumns().add(otherCoursesGradeColumn);
		otherTable.getColumns().add(otherCoursesColumn);
		otherTable.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.7), 10, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		
		// Set table visibility to FALSE
		otherTable.setVisible(false);
		
		
		allTablesBox = new HBox(20);
		allTablesBox.setAlignment(Pos.CENTER);
		allTablesBox.setStyle("-fx-effect: innershadow(three-pass-box, rgba(0,0,0,0.7), 5, 0.0, 3, 3); " +
				"-fx-background-color: darkgrey");
		allTablesBox.setPadding(new Insets(20, 20, 20, 20));
		
		requiredTablesBox = new HBox(10);
		requiredTablesBox.getChildren().addAll(requiredMajorTable,
				requiredTakenTable, requiredProgressTable);
		
		allTablesBox.getChildren().addAll(requiredTablesBox, otherTable);
		
		reportPane = new BorderPane();
		reportPane.setPadding(new Insets(10, 35, 35, 35));
		reportPane.setTop(studentInfoBar);
		reportPane.setCenter(allTablesBox);

		
		professorSearchWindow.setTop(searchBar);
		professorSearchWindow.setCenter(reportPane);
	}
	
	// Retrieve window for display
	public BorderPane getProfessorSearchWindow() {
		return professorSearchWindow;
	}
	
	// Set Listeners
	public void setGenerateButtonListener(GenerateButtonListener generateButtonListener) {
		this.generateButtonListener = generateButtonListener;
	}
	public void setSearchButtonListener(SearchButtonListener searchButtonListener) {
		this.searchButtonListener = searchButtonListener;
	}
	public void setWhatIfComBoxListener(WhatIfComBoxEventListener whatIfComBoxListener){
		this.whatIfComBoxListener = whatIfComBoxListener;
	}
	
	public void setEv(GenerateButtonEventObject ev){
		this.ev = ev;
	}
	
	// Set search queries for SAIN report
	public void setReport(ObservableList<Course> requiredCourses,
			ObservableList<Course> requiredTaken,
			ObservableList<Course> requiredProgress,
			ObservableList<Course> otherTaken) {
		
		requiredMajorTable.setVisible(true);
		this.requiredCourses = requiredCourses;
		
		this.requiredTaken = requiredTaken;
		requiredTakenTable.setVisible(true);
		this.requiredProgress = requiredProgress;
		requiredProgressTable.setVisible(true);
		this.otherTaken = otherTaken;
		otherTable.setVisible(true);
	}
	
	public void setSearchStudentInfo(String firstName, String lastName,
			String idNumber, String major){
		setStudentFirstName(firstName);
		setStudentLastName(lastName);
		setStudentIdNumber(idNumber);
		setStudentMajor(major);
	}
	
	// ** search query to populate whatIfComBox
	public void setWhatIfComBox(ObservableList<String> whatIfMajors) {
		this.whatIfComBox.setItems(whatIfMajors);
	}

	// Student Search Fields
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
		firstNameField.setText(studentFirstName);
	}

	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
		lastNameField.setText(studentLastName);
	}
	
	public String getStudentIdNumber() {
		return studentIdNumber;
	}
	public void setStudentIdNumber(String studentIdNumber) {
		this.studentIdNumber = studentIdNumber;
		idNumberField.setText(studentIdNumber);
	}

	public String getStudentMajor() {
		return studentMajor;
	}
	public void setStudentMajor(String studentMajor) {
		this.studentMajor = studentMajor;
		majorField.setText(studentMajor);
	}
	
	public String getStudentWhatIfMajor(){
		return studentWhatIfMajor;
	}
	public void setStudentWhatIfMajor(String studentWhatIfMajor){
		this.studentWhatIfMajor = studentWhatIfMajor;
	}


	public String getFirstNameField() {
		return firstNameField.getText();
	}
	public String getLastNameField() {
		return lastNameField.getText();
	}
	public String getIdNumberField() {
		return idNumberField.getText();
	}
	public String getMajorField() {
		return majorField.getText();
	}
}