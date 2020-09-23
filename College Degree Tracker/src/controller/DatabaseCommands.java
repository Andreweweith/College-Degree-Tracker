package controller;

import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Account;

public class DatabaseCommands {

	private PreparedStatement findAccount;
	private PreparedStatement findStudent;
	private PreparedStatement findTables;
	private PreparedStatement findReqCoursesInProgress;
	private PreparedStatement findReqCoursesCompleted;
	private PreparedStatement findOtherCourses;
	private PreparedStatement findCourseInfo;
	private PreparedStatement findRequiredCourses;
	private PreparedStatement findMajorsList;
	
	private DatabaseMetaData dbMetaData;
	private Connection connection;
	
	public void initializeDB() {
		try {
		     // Load the JDBC driver
		     Class.forName("com.mysql.jdbc.Driver");
		     System.out.println("Driver loaded");
		     
		     // Establish a connection
		     connection = DriverManager.getConnection
		       ("jdbc:mysql://localhost:3306/sain", "root", "password");
		     System.out.println("Database connected");
		     
		     // ** find a particular person/account using ID - or - USERNAME/PASSWORD
		     String findAccountQuery = "select * from accounts where username = ? and password = ?";
		     String findStudentQuery = "select * from accounts where accountType = 'Student' and id = ?";
		     // ** find registered courses (for student by ID) that count towards the major (in progress and complete)
		     String findReqCoursesInProgressQuery = "select courseNumber, grade from Enrollment where id = ? and deptId = ? and grade = 'IP'";
		     String findReqCoursesCompletedQuery = "select courseNumber, grade from Enrollment where id = ? and deptId = ? and grade <> 'IP'";
		     // ** find registered courses (for student by ID) that don't count towards the major (grade irrelevant)
		     String findOtherCoursesQuery = "select courseNumber, grade from Enrollment where id = ? and deptId != ?";
		     // ** find info for courses (using COURSE NUMBER -- not course ID)
		     String findCourseInfoQuery = "select * from Courses where courseNumber = ?";
		     // ** find courses that are required for a major (by DEPTID)
		     String findRequiredCoursesQuery = "select courseNumber from Majors where deptId = ?";
		     // ** find list of all majors in system
		     String findMajorsListQuery = "select distinct deptId from Majors";
		     
		     // Create a statement
		     findAccount = connection.prepareStatement(findAccountQuery);
		     findStudent = connection.prepareStatement(findStudentQuery);
		     findReqCoursesInProgress = connection.prepareStatement(findReqCoursesInProgressQuery);
		     findReqCoursesCompleted = connection.prepareStatement(findReqCoursesCompletedQuery);
		     findOtherCourses = connection.prepareStatement(findOtherCoursesQuery);
		     findCourseInfo = connection.prepareStatement(findCourseInfoQuery);
		     findRequiredCourses = connection.prepareStatement(findRequiredCoursesQuery);
		     findMajorsList = connection.prepareStatement(findMajorsListQuery);
		     
		     dbMetaData = connection.getMetaData();
		     
		}
		catch (Exception ex) {
		  System.out.println("Problem initializing database");
		  ex.printStackTrace();
		}
	}
	
	public Account accountSearch(String username, String password){	    			    	
		Account model = new Account();
		try {
			findAccount.setString(1, username);
			findAccount.setString(2, password);
			ResultSet rs1 = findAccount.executeQuery();
					
			if(rs1.next()){	
				model = new Account(
						rs1.getString(1), rs1.getString(2),
						rs1.getString(5), rs1.getString(6),
						rs1.getString(7), rs1.getString(8),
						rs1.getString(9));
				
			}
		} 
		catch (SQLException e) {
			System.out.println("Problem finding account with Account Search");
			e.printStackTrace();
		}
		
		return model;
	}
	
	public Account studentSearch(Account model, int id) {
		try {
			findStudent.setInt(1, id);
			ResultSet rs1 = findStudent.executeQuery();
			
			if(rs1.next()) {
				model = new Account(
						rs1.getString(1), rs1.getString(2),
						rs1.getString(5), rs1.getString(6),
						rs1.getString(7), rs1.getString(8),
						rs1.getString(9));
			}
		}
		catch (SQLException e) {
			System.out.println("Problem finding student with Student Search");
			e.printStackTrace();
		}
		
		return model;
	}
	
	public ObservableList<ObservableList<String>> reqCoursesInProgressSearch(int id, String deptId) {
		ObservableList<ObservableList<String>> courses = FXCollections.observableArrayList();
		
		try {
			findReqCoursesInProgress.setInt(1, id);
			findReqCoursesInProgress.setString(2, deptId);
			ResultSet rs1 = findReqCoursesInProgress.executeQuery();
			
			while(rs1.next()) {
				findCourseInfo.setString(1, rs1.getString(1));
				ResultSet rs2 = findCourseInfo.executeQuery();
				String grade = rs1.getString(2);
				while(rs2.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					// add DEPTID(2), COURSENUMBER(3), TITLE(4), CREDITS(5)
					row.addAll(rs2.getString(2), rs2.getString(3),
							rs2.getString(4), rs2.getString(5), grade);
					courses.add(row);	
				}
			}
		} 
		catch (SQLException e) {
			System.out.println("Problem finding required courses (in progress)");
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public ObservableList<ObservableList<String>> reqCoursesCompletedSearch(int id, String deptId) {
		ObservableList<ObservableList<String>> courses = FXCollections.observableArrayList();
		
		try {
			findReqCoursesCompleted.setInt(1, id);
			findReqCoursesCompleted.setString(2, deptId);
			ResultSet rs1 = findReqCoursesCompleted.executeQuery();
			
			while(rs1.next()) {
				findCourseInfo.setString(1, rs1.getString(1));
				ResultSet rs2 = findCourseInfo.executeQuery();
				String grade = rs1.getString(2);
				
				while(rs2.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					// add DEPTID(2), COURSENUMBER(3), TITLE(4), CREDITS(5)
					row.addAll(rs2.getString(2), rs2.getString(3),
							rs2.getString(4), rs2.getString(5), grade);
					courses.add(row);	
				}	
			}
		} 
		catch (SQLException e) {
			System.out.println("Problem finding required courses (completed)");
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public ObservableList<ObservableList<String>> otherCoursesSearch(int id, String deptId) {
		ObservableList<ObservableList<String>> courses = FXCollections.observableArrayList();
		
		try {
			findOtherCourses.setInt(1, id);
			findOtherCourses.setString(2, deptId);
			ResultSet rs1 = findOtherCourses.executeQuery();
			
			while(rs1.next()) {
				findCourseInfo.setString(1, rs1.getString(1));
				ResultSet rs2 = findCourseInfo.executeQuery();
				String grade = rs1.getString(2);
				
				while(rs2.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					// add DEPTID(2), COURSENUMBER(3), TITLE(4), CREDITS(5)
					row.addAll(rs2.getString(2), rs2.getString(3),
							rs2.getString(4), rs2.getString(5), grade);
					courses.add(row);				
				}
			}
		} 
		catch (SQLException e) {
			System.out.println("Problem finding other courses (not required)");
			e.printStackTrace();
		}
		
		return courses;
	}
	
	public ObservableList<ObservableList<String>> reqForMajorSearch(String deptId) {
		ObservableList<ObservableList<String>> courses = FXCollections.observableArrayList();
		
		try {
			findRequiredCourses.setString(1, deptId);
			ResultSet rs1 = findRequiredCourses.executeQuery();
			
			while(rs1.next()) {
				findCourseInfo.setString(1, rs1.getString(1));
				ResultSet rs2 = findCourseInfo.executeQuery();
				
				while(rs2.next()) {
					ObservableList<String> row = FXCollections.observableArrayList();
					// add DEPTID(2), COURSENUMBER(3), TITLE(4), CREDITS(5)
					row.addAll(rs2.getString(2), rs2.getString(3),
							rs2.getString(4), rs2.getString(5));
					courses.add(row);
				}
			}
		} 
		catch (SQLException e) {
			System.out.println("Problem finding other courses (not required)");
			e.printStackTrace();
		}
		
		return courses;	
	}
	
	public ObservableList<String> allMajorsSearch() {
		ObservableList<String> majors = FXCollections.observableArrayList();
		
		try {
			ResultSet rs1 = findMajorsList.executeQuery();
			
			while(rs1.next()) {
				majors.add(rs1.getString(1));
			}
		}
		catch (SQLException e) {
			System.out.println("Problem finding majors");
		}
		
		return majors;
	}
	
	public ObservableList<String> allTablesSearch() {
		ObservableList<String> tablesList = FXCollections.observableArrayList();
		
		try {
			ResultSet rs1 = dbMetaData.getTables(null, null, "%", null);

			while(rs1.next()) {
				tablesList.add(rs1.getString(3));
			}
		}
		catch (SQLException e) {
			System.out.println("Problem finding majors");
		}
		
		return tablesList;
	}
	
 	@SuppressWarnings("rawtypes")
	public TableView setTableView(String tableName, TableView tableView) {
	    try {
	      String findTablesQuery = ("select * from " + tableName); 
	      findTables = connection.prepareStatement(findTablesQuery);
	      ResultSet rs = findTables.executeQuery();
	      
	      populateTable(rs, tableView);
	      
	    } 
	    catch (Exception e) {
	      System.out.println("Problem setting table");
	      e.printStackTrace();
	    }
	    
	    return tableView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void populateTable(ResultSet rs, TableView tableView) {
		ObservableList data = FXCollections.observableArrayList();
	    try {
	    	for(int i = 0; i < rs.getMetaData().getColumnCount(); i++){
	    	  final int j = i;
	    	  TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
	    	  col.setCellValueFactory(
	    			  new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
	    				  public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
	    					  if (param == null || param.getValue() == null || param.getValue().get(j) == null) {
		    		                return null;
		    				  }
		    				  return new SimpleStringProperty(param.getValue().get(j).toString());
		    		      }
	    	  });
	    	
	    	  tableView.getColumns().add(col);
	      }
	    	
	    	while (rs.next()) {
    			ObservableList<String> row = FXCollections.observableArrayList(); 
    			for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
    		       row.add(rs.getString(j));
    		    }
    			data.add(row);
    		}
	    	
	    	tableView.setItems(data);
	    }
	    catch(SQLException e) {
	    	e.printStackTrace();
	    	System.out.println("Error on Building Data");
	    }
	}
	
	public void addRow(String tableName, ObservableList<String> newInfo){
		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery("select * from " + tableName);
			
			rs.last();
			rs.moveToInsertRow();
			for(int i = 0; i < rs.getMetaData().getColumnCount(); i++){
				rs.updateString(i + 1, newInfo.get(i + 1));
			}
			rs.insertRow();
			rs.moveToCurrentRow();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
