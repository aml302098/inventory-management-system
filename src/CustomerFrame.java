import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

public class CustomerFrame extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerFrame frame = new CustomerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 764, 450);

		JTable customerTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(customerTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		customerTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewCustomerInputPanel customerInputPanel = new NewCustomerInputPanel();
				Object[] optionsCustomerInputPanel = {
						"Select", "Cancel"
					};
					
				boolean isInputCorrect;		// is user choose correct input form?
				int optionYesNo;			// option for JOptionPane.YES_NO_OPTION		
				
				/*
				 * Open a new JOptionPane for choosing a customer. If choosing is not correct form, pop up error messages and open JOptionPane again.
				 * Closes only when choose "Select" option with no error (choose correctly) or choosing "Cancel" option.
				 * If choosing correctly, extract relevant data and push to currentInvoiceTable.  
				 */
				do {
					optionYesNo = JOptionPane.showOptionDialog(null, customerInputPanel, "Add a customer", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, optionsCustomerInputPanel, optionsCustomerInputPanel[0]);
					if (optionYesNo == JOptionPane.NO_OPTION ) {
						isInputCorrect = true;
					}else {
						isInputCorrect = customerInputPanel.isInputCorrect();
					} 
				}while (!isInputCorrect);
				
				if (optionYesNo == JOptionPane.YES_OPTION)
				{				    
				    Object[] customerInfo = new Object[13];
				    customerInfo[0] = (String)customerInputPanel.getTextFieldCustomerCode().getText();
				    customerInfo[1] = (String)customerInputPanel.getTextFieldCustomerName().getText();
				    customerInfo[2] = (String)customerInputPanel.getTextFieldAddress().getText();
				    customerInfo[3] = (String)customerInputPanel.getTextFieldCity().getText();
				    customerInfo[4] = (String)customerInputPanel.getTextFieldState().getText();
				    customerInfo[5] = (String)customerInputPanel.getTextFieldPostcode().getText();
				    customerInfo[6] = (String)customerInputPanel.getTextFieldDeliveryAddress().getText();
				    customerInfo[7] = (String)customerInputPanel.getTextFieldDeliveryCity().getText();
				    customerInfo[8] = (String)customerInputPanel.getTextFieldDeliveryState().getText();
				    customerInfo[9] = (String)customerInputPanel.getTextFieldDeliveryPostcode().getText();
				    customerInfo[10] = (String)customerInputPanel.getTextFieldPhone().getText();
				    customerInfo[11] = (String)customerInputPanel.getTextFieldFax().getText();
				    customerInfo[12] = (String)customerInputPanel.getTextFieldABN().getText();
				    
				    String insertQuery = "INSERT INTO `CUSTOMER` (`CUSTOMER_CODE`, `CUSTOMER_NAME`, `ADDRESS`, `CITY`, `STATE`, `POSTCODE`, `DELIVERY_ADDRESS`, `DELIVERY_CITY`, `DELIVERY_STATE`, `DELIVERY_POSTCODE`, `PHONE`, `FAX`, `ABN`)" 
							+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				    
				    Table.insertNewInstanceToDatabase(insertQuery, customerInfo);
				    Table.insertNewInstanceToTable(customerTable, customerInfo);
				}
		  	}			
		});
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int actionDelete = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
				if (actionDelete == JOptionPane.YES_OPTION) {
					// Delete
					// System.out.println("Delete");
					int selectedRow = customerTable.getSelectedRow();
					deleteCustomer(customerTable, selectedRow);
				}
			}
		});
		JButton btnFilter = new JButton("Filter");
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNew)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnFilter)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNew)
						.addComponent(btnDelete)
						.addComponent(btnFilter))
					.addGap(9))
		);
		
		
		String listQuery = "Select * FROM CUSTOMER;";
        String countQuery = "Select COUNT(*) FROM CUSTOMER;";
		
		Object[][] data = Table.populateTableData(listQuery, countQuery);
        String[] columnNames = {"CUSTOMER CODE", "CUSTOMER NAME", "ADDRESS", "CITY", "STATE", "POSTCODE", 
        						"DELIVERY ADDRESS", "DELIVERT CITY", "DELIVERY STATE", "DELIVERY POSTCODE", "PHONE", "FAX", "ABN"};
        customerTable.setModel(new DefaultTableModel(data, columnNames) {
        	Class[] columnTypes = new Class[] {
        		String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        	boolean[] columnEditables = new boolean[] {
        		false, false, false, false, false, false, false, false, false, false, false, false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
		
		scrollPane.setViewportView(customerTable);
		getContentPane().setLayout(groupLayout);
	}
	
	
	private void deleteCustomer(JTable table, int selectedRow) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		String delete = "DELETE FROM CUSTOMER WHERE CUSTOMER_CODE = ?";
		String customerCode = (String) model.getValueAt(selectedRow, 0);
		
		try {
			   Connection con = MainFrame.getConnection();
			   PreparedStatement deleteStatement = con.prepareStatement(delete);
			   deleteStatement.setString(1, customerCode);			 
			   deleteStatement.executeUpdate();	
	 	} catch (SQLException e) {e.printStackTrace();}
		
		model.removeRow(selectedRow);
	}
}
