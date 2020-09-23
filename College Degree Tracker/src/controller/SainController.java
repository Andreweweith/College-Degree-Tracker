package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Account;
import model.Course;
import view.AddButtonEventListener;
import view.AddButtonEventObject;
import view.AlertBox;
import view.ClientFacade;
import view.GenerateButtonEventObject;
import view.GenerateButtonListener;
import view.LoginButtonEventObject;
import view.LoginButtonListener;
import view.LogoutButtonEventObject;
import view.LogoutButtonListener;
import view.SearchButtonEventObject;
import view.SearchButtonListener;
import view.SetTableButtonEventListener;
import view.SetTableButtonEventObject;
import view.WhatIfComBoxEventListener;
import view.WhatIfComBoxEventObject;

public class SainController {

	private ClientFacade view;
	private Account loginModel;
	private Account studentSearchModel;
	
	private DatabaseCommands dbCommands = new DatabaseCommands();
	
	public SainController(ClientFacade view, Account model){
		super();
		this.view = view;
		this.loginModel = model;
		
		dbCommands.initializeDB();

		// LoginWindow LOGIN 
		this.view.getLoginWindow().setLoginButtonListener(new LoginButtonListener() {
			@Override
			public void loginButtonClicked(LoginButtonEventObject ev) {
				try {
					Account loginModel = dbCommands.accountSearch(ev.getUsername(), ev.getPassword());
					setClientWindow(loginModel);

					if (loginModel.getAccountType().startsWith("A")) {
						// Administrator
						// - Logout
						getView().getAdminWindow().setLogoutButtonListener(new LogoutButtonListener() {
							@Override
							public void logoutButtonClicked(LogoutButtonEventObject ev) {
								getView().constructLogin();
							}
						});
						// - Account Info
						getView().getAdminWindow().setAccountPanelInfo(loginModel.getfName(), loginModel.getlName(),
								loginModel.getId());
						// - SearchWindow
						getView().getSearchWindow().setWhatIfComBox(dbCommands.allMajorsSearch());
						getView().getSearchWindow().setSearchButtonListener(new SearchButtonListener() {
							@Override
							public void searchButtonClicked(SearchButtonEventObject ev) {
								try{
									Account studentModel = dbCommands.studentSearch(getStudentModel(), Integer.parseInt(ev.getIdSearch()));
									setStudentModel(studentModel);
									getView().getSearchWindow().setSearchStudentInfo(
											studentModel.getfName(), studentModel.getlName(),
											studentModel.getId(), studentModel.getDeptId());
									getView().getSearchWindow().setStudentIdNumber(studentModel.getId());
									getView().getSearchWindow().setStudentMajor(studentModel.getDeptId());
								}
								catch(NumberFormatException nfe){
									AlertBox.display("Invalid Search Input");
								}
								
							}
						});
						getView().getSearchWindow().setGenerateButtonListener(new GenerateButtonListener() {
							@Override
							public void generateButtonClicked(GenerateButtonEventObject ev) {
								try {
									int id = Integer.parseInt(ev.getStudentIdNumber());
									String major = ev.getStudentMajor();

									ObservableList<ObservableList<String>> requiredCourses = 
											dbCommands.reqForMajorSearch(major);
									ObservableList<ObservableList<String>> requiredTaken = 
											dbCommands.reqCoursesCompletedSearch(id, major);
									ObservableList<ObservableList<String>> requiredProgress = 
											dbCommands.reqCoursesInProgressSearch(id, major);
									ObservableList<ObservableList<String>> otherTaken = 
											dbCommands.otherCoursesSearch(id, major);
									
									ObservableList<Course> requiredCoursesData = FXCollections.observableArrayList();
									ObservableList<Course> requiredTakenData = FXCollections.observableArrayList();
									ObservableList<Course> requiredProgressData = FXCollections.observableArrayList();
									ObservableList<Course> otherTakenData = FXCollections.observableArrayList();
									
									for(int i = 0; i < requiredCourses.size(); i++){
										requiredCoursesData.add(new Course(requiredCourses.get(i).get(0),
												requiredCourses.get(i).get(1),
												requiredCourses.get(i).get(2),
												requiredCourses.get(i).get(3)));
									}
									for(int i = 0; i < requiredTaken.size(); i++){
										requiredTakenData.add(new Course(requiredTaken.get(i).get(0),
												requiredTaken.get(i).get(1),
												requiredTaken.get(i).get(2),
												requiredTaken.get(i).get(3),
												requiredTaken.get(i).get(4)));
									}
									for(int i = 0; i < requiredProgress.size(); i++){
										requiredProgressData.add(new Course(requiredProgress.get(i).get(0),
												requiredProgress.get(i).get(1),
												requiredProgress.get(i).get(2),
												requiredProgress.get(i).get(3),
												requiredProgress.get(i).get(4)));
									}
									for(int i = 0; i < otherTaken.size(); i++){
										otherTakenData.add(new Course(otherTaken.get(i).get(0),
												otherTaken.get(i).get(1),
												otherTaken.get(i).get(2),
												otherTaken.get(i).get(3),
												otherTaken.get(i).get(4)));
									}
									
									getView().getSearchWindow().setReport(requiredCoursesData,
											requiredTakenData, requiredProgressData, otherTakenData);
								} catch (NumberFormatException e) {
									AlertBox.display("Student has not been searched yet");
								}
							}
						});
						getView().getSearchWindow().setWhatIfComBoxListener(new WhatIfComBoxEventListener() {
							@Override
							public void whatIfComBoxClicked(WhatIfComBoxEventObject ev) {
								getView().getSearchWindow().setStudentWhatIfMajor(ev.getStudentWhatIfMajor());
							}
						});
						// - TablesWindow
						getView().getTablesWindow().setTablesComBox(dbCommands.allTablesSearch());
						getView().getTablesWindow().setSetTableButtonEventListener(new SetTableButtonEventListener() {
							@SuppressWarnings("rawtypes")
							@Override
							public void setTableButtonClicked(SetTableButtonEventObject ev) {
								try {
									TableView tableView = dbCommands.setTableView(
											ev.getTableName(), ev.getTableView());
									
									getView().getTablesWindow().setMainTable(tableView);
								} catch (Exception e) {
									e.printStackTrace();
									AlertBox.display("Problem getting tableview");
								}
							}
						});
						getView().getTablesWindow().setAddButtonEventListener(new AddButtonEventListener(){
							@Override
							public void addButtonClicked(AddButtonEventObject ev) {
								// Accounts
								/*if(ev.getAccountType() != null && ev.getUsername() != null
										&& ev.getPassword() != null){
									ObservableList<String> newAccount = FXCollections.observableArrayList();
									newAccount.addAll(ev.getId(), ev.getAccountType(),
											ev.getUsername(), ev.getPassword(),
											ev.getFirstName(), ev.getLastName(),
											ev.getAddress(), ev.getPhone(),
											ev.getDeptId(), null);
									dbCommands.addRow("accounts", newAccount);
								}*/
							}
						});
					}
					else if (loginModel.getAccountType().startsWith("P")) {
						// Professor 
						// - Logout
						getView().getProfessorWindow().setLogoutButtonListener(new LogoutButtonListener() {
							@Override
							public void logoutButtonClicked(LogoutButtonEventObject ev) {
								getView().constructLogin();
							}
						});
						// - Account Info
						getView().getProfessorWindow().setAccountPanelInfo(loginModel.getfName(), loginModel.getlName(),
								loginModel.getId());
						// - ProfessorSearchWindow
						getView().getProfessorSearchWindow().setWhatIfComBox(dbCommands.allMajorsSearch());
						getView().getProfessorSearchWindow().setSearchButtonListener(new SearchButtonListener() {
							@Override
							public void searchButtonClicked(SearchButtonEventObject ev) {
								try{
									Account studentModel = dbCommands.studentSearch(getStudentModel(), Integer.parseInt(ev.getIdSearch()));
									setStudentModel(studentModel);
									getView().getProfessorSearchWindow().setSearchStudentInfo(
											studentModel.getfName(), studentModel.getlName(),
											studentModel.getId(), studentModel.getDeptId());
									getView().getProfessorSearchWindow().setStudentIdNumber(studentModel.getId());
									getView().getProfessorSearchWindow().setStudentMajor(studentModel.getDeptId());
								}
								catch(NumberFormatException nfe){
									AlertBox.display("Invalid Search Input");
								}
								
							}
						});
						getView().getProfessorSearchWindow().setGenerateButtonListener(new GenerateButtonListener() {
							@Override
							public void generateButtonClicked(GenerateButtonEventObject ev) {
								try {
									int id = Integer.parseInt(ev.getStudentIdNumber());
									String major = ev.getStudentMajor();

									ObservableList<ObservableList<String>> requiredCourses = 
											dbCommands.reqForMajorSearch(major);
									ObservableList<ObservableList<String>> requiredTaken = 
											dbCommands.reqCoursesCompletedSearch(id, major);
									ObservableList<ObservableList<String>> requiredProgress = 
											dbCommands.reqCoursesInProgressSearch(id, major);
									ObservableList<ObservableList<String>> otherTaken = 
											dbCommands.otherCoursesSearch(id, major);
									
									ObservableList<Course> requiredCoursesData = FXCollections.observableArrayList();
									ObservableList<Course> requiredTakenData = FXCollections.observableArrayList();
									ObservableList<Course> requiredProgressData = FXCollections.observableArrayList();
									ObservableList<Course> otherTakenData = FXCollections.observableArrayList();
									
									for(int i = 0; i < requiredCourses.size(); i++){
										requiredCoursesData.add(new Course(requiredCourses.get(i).get(0),
												requiredCourses.get(i).get(1),
												requiredCourses.get(i).get(2),
												requiredCourses.get(i).get(3)));
									}
									for(int i = 0; i < requiredTaken.size(); i++){
										requiredTakenData.add(new Course(requiredTaken.get(i).get(0),
												requiredTaken.get(i).get(1),
												requiredTaken.get(i).get(2),
												requiredTaken.get(i).get(3),
												requiredTaken.get(i).get(4)));
									}
									for(int i = 0; i < requiredProgress.size(); i++){
										requiredProgressData.add(new Course(requiredProgress.get(i).get(0),
												requiredProgress.get(i).get(1),
												requiredProgress.get(i).get(2),
												requiredProgress.get(i).get(3),
												requiredProgress.get(i).get(4)));
									}
									for(int i = 0; i < otherTaken.size(); i++){
										otherTakenData.add(new Course(otherTaken.get(i).get(0),
												otherTaken.get(i).get(1),
												otherTaken.get(i).get(2),
												otherTaken.get(i).get(3),
												otherTaken.get(i).get(4)));
									}
									
									getView().getProfessorSearchWindow().setReport(requiredCoursesData,
											requiredTakenData, requiredProgressData, otherTakenData);
								} catch (NumberFormatException e) {
									AlertBox.display("Student has not been searched yet");
								}
							}
						});
						getView().getProfessorSearchWindow().setWhatIfComBoxListener(new WhatIfComBoxEventListener() {
							@Override
							public void whatIfComBoxClicked(WhatIfComBoxEventObject ev) {
								getView().getProfessorSearchWindow().setStudentWhatIfMajor(ev.getStudentWhatIfMajor());
							}
						});
					}
					else if (loginModel.getAccountType().startsWith("S")) {
						// Student
						// - Logout
						getView().getStudentWindow().setLogoutButtonListener(new LogoutButtonListener() {
							@Override
							public void logoutButtonClicked(LogoutButtonEventObject ev) {
								getView().constructLogin();
							}
						});
						// - Account Info
						getView().getStudentWindow().setAccountPanelInfo(loginModel.getfName(), loginModel.getlName(),
								loginModel.getId());
						// - StudentReportWindow
						getView().getStudentReportWindow().setWhatIfComBox(dbCommands.allMajorsSearch());
						getView().getStudentReportWindow().setReportStudentInfo(loginModel.getfName(), loginModel.getlName(),
								loginModel.getId(), loginModel.getDeptId());
						getView().getStudentReportWindow().setGenerateButtonListener(new GenerateButtonListener() {
							@Override
							public void generateButtonClicked(GenerateButtonEventObject ev) {
								try {
									int id = Integer.parseInt(ev.getStudentIdNumber());
									String major = ev.getStudentMajor();

									ObservableList<ObservableList<String>> requiredCourses = 
											dbCommands.reqForMajorSearch(major);
									ObservableList<ObservableList<String>> requiredTaken = 
											dbCommands.reqCoursesCompletedSearch(id, major);
									ObservableList<ObservableList<String>> requiredProgress = 
											dbCommands.reqCoursesInProgressSearch(id, major);
									ObservableList<ObservableList<String>> otherTaken = 
											dbCommands.otherCoursesSearch(id, major);
									
									ObservableList<Course> requiredCoursesData = FXCollections.observableArrayList();
									ObservableList<Course> requiredTakenData = FXCollections.observableArrayList();
									ObservableList<Course> requiredProgressData = FXCollections.observableArrayList();
									ObservableList<Course> otherTakenData = FXCollections.observableArrayList();
									
									for(int i = 0; i < requiredCourses.size(); i++){
										requiredCoursesData.add(new Course(requiredCourses.get(i).get(0),
												requiredCourses.get(i).get(1),
												requiredCourses.get(i).get(2),
												requiredCourses.get(i).get(3)));
									}
									for(int i = 0; i < requiredTaken.size(); i++){
										requiredTakenData.add(new Course(requiredTaken.get(i).get(0),
												requiredTaken.get(i).get(1),
												requiredTaken.get(i).get(2),
												requiredTaken.get(i).get(3),
												requiredTaken.get(i).get(4)));
									}
									for(int i = 0; i < requiredProgress.size(); i++){
										requiredProgressData.add(new Course(requiredProgress.get(i).get(0),
												requiredProgress.get(i).get(1),
												requiredProgress.get(i).get(2),
												requiredProgress.get(i).get(3),
												requiredProgress.get(i).get(4)));
									}
									for(int i = 0; i < otherTaken.size(); i++){
										otherTakenData.add(new Course(otherTaken.get(i).get(0),
												otherTaken.get(i).get(1),
												otherTaken.get(i).get(2),
												otherTaken.get(i).get(3),
												otherTaken.get(i).get(4)));
									}
									
									getView().getStudentReportWindow().setReport(requiredCoursesData,
											requiredTakenData, requiredProgressData, otherTakenData);
								} catch (NumberFormatException e) {
									AlertBox.display("Student has not been searched yet");
								}
							}
						});
						getView().getStudentReportWindow().setWhatIfComBoxListener(new WhatIfComBoxEventListener() {
							@Override
							public void whatIfComBoxClicked(WhatIfComBoxEventObject ev) {
								getView().getStudentReportWindow().setStudentWhatIfMajor(ev.getStudentWhatIfMajor());
							}
						});
					}
				} 
				catch (Exception e) {
					AlertBox.display("Invalid Login Info");
				}
			}
		});
	}
	
	// Set Different Clients
	public void setLoginWindow(Stage primaryStage) {
		view.constructLogin();
	}
	public void setClientWindow(Account loginModel) {
		view.constructClientWindow(loginModel.getAccountType());
	}

	public ClientFacade getView() {
		return view;
	}
	public void setView(ClientFacade view) {
		this.view = view;
	}

	public Account getLoginModel() {
		return loginModel;
	}
	public void setLoginModel(Account model) {
		this.loginModel = model;
	}

	public Account getStudentModel() {
		return studentSearchModel;
	}
	public void setStudentModel(Account studentSearchModel) {
		this.studentSearchModel = studentSearchModel;
	}
	
	
	
}
