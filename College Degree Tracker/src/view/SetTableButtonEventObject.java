package view;

import java.util.EventObject;

import javafx.scene.control.TableView;

@SuppressWarnings("rawtypes")
public class SetTableButtonEventObject extends EventObject{

	private String tableName;
	private TableView tableView;
	
	public SetTableButtonEventObject(Object source) {
		super(source);
	}
	
	public SetTableButtonEventObject(Object source, String tableName, TableView tableView){
		super(source);
		this.setTableName(tableName);
		this.setTableView(tableView);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public TableView getTableView() {
		return tableView;
	}

	public void setTableView(TableView tableView) {
		this.tableView = tableView;
	}

}
