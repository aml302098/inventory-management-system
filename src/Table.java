import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {
	public static void main(String[] args) {
		String insertQuery = "INSERT INTO `ORDERS` (`ORDER_ID`, `CUSTOMER_CODE`, `ORDER_DATE`) VALUES (?, ?, ?)";
		Object[] instanceInfo = {"13", "c", "2019-09-07"};
		insertNewInstanceToDatabase(insertQuery, instanceInfo);
	}
	
	public static Object[][] populateTableData(String listQuery, String countQuery){ 
		Connection con = MainFrame.getConnection();
		       
        Statement listStmt, countStmt;      
        ResultSet listResult, countResult;
 		int column = 0, row = 0;
 		if (con != null) {
	        try {
	 			listStmt = con.createStatement();
	 			countStmt = con.createStatement();
	 			
	 			listResult = listStmt.executeQuery(listQuery);
	 			countResult = countStmt.executeQuery(countQuery);
	 			
	 			ResultSetMetaData rsmd = listResult.getMetaData(); 		    
	 		    column = rsmd.getColumnCount();
	
	 			while(countResult.next()) {
	 				row = countResult.getInt(1);
	 			}
	 			
	 			Object[][] data = new Object[row][column];
	 			int rowIndex = 0;
	 			
	 			while (listResult.next()) {
				    for (int i = 0; i < column; i++) {
				    	data[rowIndex][i] = listResult.getObject(i+1);
				    }
				    rowIndex++;	
	 			}
	 			
	 			return data;
				    
	 			} catch (SQLException e) {e.printStackTrace();}	
 		}
        return null;
        }

	public static void insertNewInstanceToDatabase(String insertQuery, Object[] instanceInfo) {
		   try {
			   Connection con = MainFrame.getConnection();
			   PreparedStatement insertStatement = con.prepareStatement(insertQuery);
			   
			   for (int i=0; i < instanceInfo.length; i++) {
				   String s = ((Object)instanceInfo[i]).toString();   
				   if(s.isEmpty()) {
					   s = null;
				   }
				   insertStatement.setString(i+1, s);
			   }
			   insertStatement.executeUpdate();	
	 	} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void insertNewInstanceToTable(JTable table, Object[] instanceInfo) {
		 DefaultTableModel model = (DefaultTableModel) table.getModel();			
	     model.addRow(instanceInfo);
	}
}
