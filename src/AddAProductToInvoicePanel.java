import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

public class AddAProductToInvoicePanel extends JPanel {
	private JTextField textFieldUnitPrice;

	private Object[] selectedProductInfo = new Object[8]; //Information: {ProductId, ProductName, QuantityPerUnit, Unit, QuantityOnHand, 
														  //PurchasePrice, UnitPrice, ExpiredDate}
	private Object[] selectedProductExtraInfo = new Object[6]; //Information: {(Integer)Quantity, (Integer)Unit Price, (Boolean) Keep Stock, 
															   //(Boolean) Big Packing, (Boolean) UL, (Boolean) GST}
	private JTable productTable;
	private JCheckBox chckbxBigPacking;
	private JCheckBox chckbxKeepStock;
	private JCheckBox chckbxUl;
	private JCheckBox chckbxGst;
	
	//Formats to format and parse numbers
	private NumberFormat quantityFormat;
	private JTextField textFieldQuantity;
	/**
	 * Create the panel.
	 */
	public AddAProductToInvoicePanel() {
		setBounds(100, 100, 764, 450);

		productTable = new JTable();
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
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(btnNew)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnFilter)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNew)
						.addComponent(btnDelete)
						.addComponent(btnFilter))
					.addGap(9))
		);
		
		JLabel lblNewLabel = new JLabel("Quantity");
		
		JLabel lblUnitPrice = new JLabel("Unit Price");
		
		textFieldUnitPrice = new JTextField();
		textFieldUnitPrice.setColumns(10);
		
		chckbxKeepStock = new JCheckBox("Keep Stock");
		
		chckbxBigPacking = new JCheckBox("Big Packing");
		
		chckbxUl = new JCheckBox("UL");
		
		chckbxGst = new JCheckBox("GST");
		
		textFieldQuantity = new JTextField();
		textFieldQuantity.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldQuantity, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblUnitPrice, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldUnitPrice, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))
					.addGap(41)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxBigPacking, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxGst, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(chckbxKeepStock, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxUl, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(388, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(chckbxKeepStock)
						.addComponent(chckbxUl)
						.addComponent(textFieldQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUnitPrice)
						.addComponent(textFieldUnitPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxBigPacking)
						.addComponent(chckbxGst))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		
		String listQuery = "Select * FROM PRODUCT;";
        String countQuery = "Select COUNT(*) FROM PRODUCT;";
		
		Object[][] data = Table.populateTableData(listQuery, countQuery);
        String[] columnNames = {"PRODUCT ID", "PRODUCT NAME", "QUANTITY PER UNIT", "UNIT", "PURCHASE PRICE", "UNIT PRICE", "QUANTITY ON HAND", "EXPIRED DATE"};
        productTable.setModel(new DefaultTableModel(
        	data,
        	columnNames
        ) {
        	Class[] columnTypes = new Class[] {
        		String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        });
        for(int i = 0; i < columnNames.length; i++) {
        	productTable.getColumnModel().getColumn(i).setMinWidth(100);
        }
		
		scrollPane.setViewportView(productTable);
		this.setLayout(groupLayout);
	}
	
	private void insertNewProduct(JTable productTable, Object[] productInfo) {
		String insertQuery = "INSERT INTO `PRODUCT` (`PRODUCT_ID`, `PRODUCT_NAME`, `QUANTITY_PER_UNIT`, `UNIT`, `PURCHASED_PRICE`, `UNIT_PRICE`, `QUANTITY_ON_HAND`, `EXPIRY_DATE`)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		
		   try {
			   Connection con = MainFrame.getConnection();
			   PreparedStatement insertStatement = con.prepareStatement(insertQuery);
			   
			   for (int i=0;i<8;i++) {
				   String s = (String) productInfo[i];
				   if(s.isEmpty()) {
					   s = null;
				   }
				   insertStatement.setString(i+1, s);
			   }	   
			   insertStatement.executeUpdate();	
	 	} catch (SQLException e) {e.printStackTrace();}
	    DefaultTableModel model = (DefaultTableModel) productTable.getModel();			
        model.addRow(productInfo);
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
	
	/*
	 * return to selectedProductInfo after getting information in productTable
	 * Assume isInputCorrect() return true
	 * Information: {ProductId, ProductName, QuantityPerUnit, Unit, QuantityOnHand, PurchasePrice, UnitPrice, ExpiredDate}
	 */
	
	public Object[] getSelectedProductInfo() {
		int selectedRowIndex = productTable.getSelectedRow();
		for (int i=0; i<selectedProductInfo.length; i++) {
			selectedProductInfo[i] = productTable.getValueAt(selectedRowIndex, i);
		}
		return selectedProductInfo;
	}
	
	/* 
	 * return selectedProductExtraInfo after getting relevant information below productTable
	 * Assume isInputCorrect() return true
	 * Information: {(String) Quantity, (String) Unit Price, (Bool) Keep Stock, (Bool) Big Packing, (Bool) UL, (Bool) GST}
	 */
	public Object[] getSelectedProductExtraInfo() {
		selectedProductExtraInfo[0] = Integer.parseInt(textFieldQuantity.getText());
		selectedProductExtraInfo[1] = Double.parseDouble(textFieldUnitPrice.getText());
		selectedProductExtraInfo[2] = chckbxKeepStock.isSelected();
		selectedProductExtraInfo[3] = chckbxBigPacking.isSelected();
		selectedProductExtraInfo[4] = chckbxUl.isSelected();
		selectedProductExtraInfo[5] = chckbxGst.isSelected();
		return selectedProductExtraInfo;
	}
	
	/*
	 * Is user choose the correct input form?
	 * If not, open a new JOptionPane for error messages
	 * Correct input form:
	 * 	  A row from productTable is selected, and
	 *    Quantity is Integer and Unit Price is float.
	 */
	public boolean isInputCorrect() {
		boolean inputCorrectStatus = true;			// value to return for this function
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		/*
		 *  check if a row is selected.
		 */
		try {
			int selectedRowIndex = productTable.getSelectedRow();
			productTable.getValueAt(selectedRowIndex, 0);;
		}catch(Exception exception) {
			String noProductChosenErrorMessage = "No product chosen";
			errorMessages.add(noProductChosenErrorMessage);
		}
		
		/*
		 * check if quantity is Integer.
		 */
		try {
			Integer.parseInt(textFieldQuantity.getText());
		}catch (NumberFormatException exception) {
			String quantityNotIntegerErrorMessage = "Quantity is not an integer";
			errorMessages.add(quantityNotIntegerErrorMessage);
		}
		
		/*
		 * check if unit price is Double.
		 */
		try {
			Double.parseDouble(textFieldUnitPrice.getText());
		}catch (NumberFormatException exception) {
			String unitPriceNotDoubleErrorMessage = "Unit Price must be a decimal";
			errorMessages.add(unitPriceNotDoubleErrorMessage);
		}
		
		/*
		 * If there are error messages, show error messages and input is not in correct form
		 */
		if (errorMessages.size()>0) {
			JOptionPane.showMessageDialog(null, errorMessages);
			inputCorrectStatus = false;
		}
		return inputCorrectStatus;
	}
}
