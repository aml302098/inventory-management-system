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
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;

public class ProductFrame extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductFrame frame = new ProductFrame();
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
	public ProductFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 764, 450);

		JTable productTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(productTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		productTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JButton btnNew = new JButton("New");
		btnNew.setBackground(UIManager.getColor("Button.background"));
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewProductInputPanel productInputPanel = new NewProductInputPanel();
				Object[] optionsProductInputPanel = {
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
					optionYesNo = JOptionPane.showOptionDialog(null, productInputPanel, "New product", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, optionsProductInputPanel, optionsProductInputPanel[0]);
					if (optionYesNo == JOptionPane.NO_OPTION ) {
						isInputCorrect = true;
					}else {
						isInputCorrect = productInputPanel.isInputCorrect();
					} 
				}while (!isInputCorrect);
				
				if (optionYesNo == JOptionPane.OK_OPTION)
				{
					// Parse the inputs into correct format and insert them into an array
					Object[] productInfo = new Object[8];
				    productInfo[0] = (String)productInputPanel.getTextFieldProductID().getText();
				    productInfo[1] = (String)productInputPanel.getTextFieldProductName().getText();
				    productInfo[2] = Integer.parseInt(productInputPanel.getTextFieldQuantityPerUnit().getText());
				    productInfo[3] = (String)productInputPanel.getTextFieldUnit().getText();			   
				    productInfo[4] = Float.parseFloat(productInputPanel.getTextFieldPurchasePrice().getText());
				    productInfo[5] = Float.parseFloat(productInputPanel.getTextFieldUnitPrice().getText());
				    productInfo[6] = Integer.parseInt(productInputPanel.getTextFieldQuantityOnHand().getText());
				    productInfo[7] = (String)productInputPanel.getTextFieldExpiredDate().getText();
				    
				    String insertQuery = "INSERT INTO `PRODUCT` (`PRODUCT_ID`, `PRODUCT_NAME`, `QUANTITY_PER_UNIT`, `UNIT`, `PURCHASED_PRICE`, `UNIT_PRICE`, `QUANTITY_ON_HAND`, `EXPIRY_DATE`)" 
				    					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
					
					Table.insertNewInstanceToDatabase(insertQuery, productInfo);
				    Table.insertNewInstanceToTable(productTable, productInfo);	    
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
					int selectedRow = productTable.getSelectedRow();
					deleteProduct(productTable, selectedRow);
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
		
		
		String listQuery = "Select * FROM PRODUCT;";
        String countQuery = "Select COUNT(*) FROM PRODUCT;";
		
		Object[][] data = Table.populateTableData(listQuery, countQuery);
        String[] columnNames = {"PRODUCT ID", "PRODUCT NAME", "QUANTITY PER UNIT", "UNIT", "PURCHASE PRICE", "UNIT PRICE", "QUANTITY ON HAND", "EXPIRED DATE"};
        productTable.setModel(new DefaultTableModel(data, columnNames));
        for(int i = 0; i < columnNames.length; i++) {
        	productTable.getColumnModel().getColumn(i).setMinWidth(100);
        }
		
		scrollPane.setViewportView(productTable);
		getContentPane().setLayout(groupLayout);
	}
		
	private void deleteProduct(JTable table, int selectedRow) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		String delete = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ?";
		String ProductID = (String) model.getValueAt(selectedRow, 0);
		
		try {
			   Connection con = MainFrame.getConnection();
			   PreparedStatement deleteStatement = con.prepareStatement(delete);
			   deleteStatement.setString(1, ProductID);			 
			   deleteStatement.executeUpdate();	
	 	} catch (SQLException e) {e.printStackTrace();}
		
		model.removeRow(selectedRow);
	}
}
