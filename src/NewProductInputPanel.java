import javax.swing.JPanel;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NewProductInputPanel extends JPanel {
	private JTextField textFieldProductID;
	private JTextField textFieldProductName;
	private JTextField textFieldQuantityPerUnit;
	private JTextField textFieldUnit;
	private JTextField textFieldQuantityOnHand;
	private JTextField textFieldPurchasePrice;
	private JTextField textFieldUnitPrice;
	private JTextField textFieldExpiredDate;

	/**
	 * Create the panel.
	 */
	public NewProductInputPanel() {		
		JLabel lblProductID = new JLabel("Product ID");
		JLabel lblProductName = new JLabel("Product Name");		
		JLabel lblQuantityPerUnit = new JLabel("Quantity Per Unit");		
		JLabel lblUnit = new JLabel("Unit");		
		JLabel lblQuantityOnHand = new JLabel("Quantity On Hand");	
		JLabel lblPurchasePrice = new JLabel("Purchase Price");
		JLabel lblUnitPrice = new JLabel("Unit Price");
		JLabel lblExpiredDate = new JLabel("Expired Date");
		
		textFieldProductID = new JTextField();
		getTextFieldProductID().setColumns(10);
		textFieldProductName = new JTextField();
		getTextFieldProductName().setColumns(10);
		textFieldQuantityPerUnit = new JTextField();
		getTextFieldQuantityPerUnit().setColumns(10);
		textFieldUnit = new JTextField();
		getTextFieldUnit().setColumns(10);
		textFieldQuantityOnHand = new JTextField();
		getTextFieldQuantityOnHand().setColumns(10);
		textFieldPurchasePrice = new JTextField();
		getTextFieldPurchasePrice().setColumns(10);
		textFieldUnitPrice = new JTextField();
		getTextFieldUnitPrice().setColumns(10);
		textFieldExpiredDate = new JTextField();
		getTextFieldExpiredDate().setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textFieldProductID, Alignment.LEADING)
								.addComponent(lblProductID, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblProductName, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblQuantityPerUnit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textFieldProductName, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldQuantityPerUnit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUnit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblQuantityOnHand, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPurchasePrice, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textFieldUnit, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldQuantityOnHand, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldPurchasePrice, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblUnitPrice, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblExpiredDate, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(textFieldUnitPrice, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldExpiredDate, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(116, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblProductID)
						.addComponent(lblProductName)
						.addComponent(lblQuantityPerUnit))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldProductID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldProductName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldQuantityPerUnit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUnit)
						.addComponent(lblQuantityOnHand)
						.addComponent(lblPurchasePrice))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldUnit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldQuantityOnHand, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldPurchasePrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUnitPrice)
						.addComponent(lblExpiredDate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldUnitPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldExpiredDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(140, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}

	public JTextField getTextFieldProductID() {
		return textFieldProductID;
	}

	public JTextField getTextFieldProductName() {
		return textFieldProductName;
	}

	public JTextField getTextFieldQuantityPerUnit() {
		return textFieldQuantityPerUnit;
	}

	public JTextField getTextFieldUnit() {
		return textFieldUnit;
	}

	public JTextField getTextFieldQuantityOnHand() {
		return textFieldQuantityOnHand;
	}

	public JTextField getTextFieldPurchasePrice() {
		return textFieldPurchasePrice;
	}

	public JTextField getTextFieldUnitPrice() {
		return textFieldUnitPrice;
	}
	
	public JTextField getTextFieldExpiredDate() {
		return textFieldExpiredDate;
	}
	

	/**
	 * Is user choose the correct input form?
	 * If not, open a new JOptionPane for error messages
	 * Correct input form:
	 * 	  Product ID is not empty
	 *    Product Name is not empty
	 *    Quantity per unit is an integer
	 *    Quantity on hand is an integer
	 *    Purchase Price is a decimal
	 *    Unit Price is a decimal
	 */
	public boolean isInputCorrect() {
		boolean inputCorrectStatus = true;			// value to return for this function
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		/*
		 *  check if product ID is empty
		 */
		if (textFieldProductID.getText().isEmpty()) {
			String productIDIsEmptyErrorMsg = "Product ID is Empty";
			errorMessages.add(productIDIsEmptyErrorMsg);
		}
		
		/*
		 *  check if product name is empty
		 */
		
		if (textFieldProductName.getText().isEmpty()) {
			String productNameIsEmptyErrorMsg = "Product Name is Empty";
			errorMessages.add(productNameIsEmptyErrorMsg);
		}
		
		/*
		 *  check if quantity per unit is not an integer
		 */
		
		try {
			Integer.parseInt(textFieldQuantityPerUnit.getText());
		}catch (NumberFormatException exception) {
			errorMessages.add("Quantity Per Unit is not an integer");
		}
		
		/*
		 *  check if quantity on hand is not an integer
		 */
		
		try {
			Integer.parseInt(textFieldQuantityOnHand.getText());
		}catch (NumberFormatException exception) {
			errorMessages.add("Quantity On Hand is not an integer");
		}
		
		/*
		 *  check if purchase price is not a decimal
		 */
		
		try {
			Double.parseDouble(textFieldPurchasePrice.getText());
		}catch (NumberFormatException exception) {
			errorMessages.add("Purchase Price must be a decimal");
		}
		
		/*
		 *  check if unit price is not a decimal
		 */
		
		try {
			Double.parseDouble(textFieldUnitPrice.getText());
		}catch (NumberFormatException exception) {
			errorMessages.add("Unit Price must be a decimal");
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
