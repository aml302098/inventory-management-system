import javax.swing.JPanel;

import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class NewCustomerInputPanel extends JPanel {
	private JTextField textFieldCustomerCode;
	private JTextField textFieldCustomerName;
	private JTextField textFieldAddress;
	private JTextField textFieldCity;
	private JTextField textFieldState;
	private JTextField textFieldPostcode;
	private JTextField textFieldDeliveryAddress;
	private JTextField textFieldDeliveryCity;
	private JTextField textFieldDeliveryState;
	private JTextField textFieldDeliveryPostcode;
	private JTextField textFieldPhone;
	private JTextField textFieldFax;
	private JLabel lblAbn;
	private JTextField textFieldABN;

	/**
	 * Create the panel.
	 */
	public NewCustomerInputPanel() {
		
		JLabel lblNewLabel = new JLabel("Customer code");
		
		textFieldCustomerCode = new JTextField();
		getTextFieldCustomerCode().setColumns(10);
		
		JLabel lblCustomerName = new JLabel("Customer name");
		
		JLabel lblAddress = new JLabel("Address");
		
		JLabel lblCity = new JLabel("City");
		
		JLabel lblState = new JLabel("State");
		
		JLabel lblPostcode = new JLabel("Postcode");
		
		textFieldCustomerName = new JTextField();
		getTextFieldCustomerName().setColumns(10);
		
		textFieldAddress = new JTextField();
		getTextFieldAddress().setColumns(10);
		
		textFieldCity = new JTextField();
		getTextFieldCity().setColumns(10);
		
		textFieldState = new JTextField();
		getTextFieldState().setColumns(10);
		
		textFieldPostcode = new JTextField();
		getTextFieldPostcode().setColumns(10);
		
		JLabel lblDeliveryAddress = new JLabel("Delivery address");
		
		JLabel lblDeliveryCity = new JLabel("Delivery city");
		
		JLabel lblDeliveryState = new JLabel("Delivery state");
		
		textFieldDeliveryAddress = new JTextField();
		getTextFieldDeliveryAddress().setColumns(10);
		
		textFieldDeliveryCity = new JTextField();
		getTextFieldDeliveryCity().setColumns(10);
		
		textFieldDeliveryState = new JTextField();
		getTextFieldDeliveryState().setColumns(10);
		
		JLabel lblDeliveryPostcode = new JLabel("Delivery postcode");
		
		JLabel lblPhone = new JLabel("Phone");
		
		JLabel lblFax = new JLabel("Fax");
		
		textFieldDeliveryPostcode = new JTextField();
		getTextFieldDeliveryPostcode().setColumns(10);
		
		textFieldPhone = new JTextField();
		getTextFieldPhone().setColumns(10);
		
		textFieldFax = new JTextField();
		getTextFieldFax().setColumns(10);
		
		lblAbn = new JLabel("ABN");
		
		textFieldABN = new JTextField();
		getTextFieldABN().setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(getTextFieldCustomerCode(), Alignment.LEADING)
								.addComponent(lblNewLabel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCustomerName, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblAddress, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(getTextFieldCustomerName(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(getTextFieldAddress(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblCity, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblState, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPostcode, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(getTextFieldCity(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldState(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldPostcode(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeliveryAddress, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDeliveryCity, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDeliveryState, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(getTextFieldDeliveryAddress(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldDeliveryCity(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldDeliveryState(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDeliveryPostcode, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPhone, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblFax, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(getTextFieldDeliveryPostcode(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldPhone(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(getTextFieldFax(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblAbn, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldABN(), GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(116, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(lblCustomerName)
						.addComponent(lblAddress))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getTextFieldCustomerCode(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldCustomerName(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldAddress(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCity)
						.addComponent(lblState)
						.addComponent(lblPostcode))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getTextFieldCity(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldState(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldPostcode(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeliveryAddress)
						.addComponent(lblDeliveryCity)
						.addComponent(lblDeliveryState))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getTextFieldDeliveryAddress(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldDeliveryCity(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldDeliveryState(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDeliveryPostcode)
						.addComponent(lblPhone)
						.addComponent(lblFax))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(getTextFieldDeliveryPostcode(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldPhone(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(getTextFieldFax(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAbn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(getTextFieldABN(), GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(58, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	/**
	 * @return the textFieldCustomerCode
	 */
	public JTextField getTextFieldCustomerCode() {
		return textFieldCustomerCode;
	}

	/**
	 * @return the textFieldCustomerName
	 */
	public JTextField getTextFieldCustomerName() {
		return textFieldCustomerName;
	}

	/**
	 * @return the textFieldAddress
	 */
	public JTextField getTextFieldAddress() {
		return textFieldAddress;
	}

	/**
	 * @return the textFieldCity
	 */
	public JTextField getTextFieldCity() {
		return textFieldCity;
	}

	/**
	 * @return the textFieldState
	 */
	public JTextField getTextFieldState() {
		return textFieldState;
	}

	/**
	 * @return the textFieldPostcode
	 */
	public JTextField getTextFieldPostcode() {
		return textFieldPostcode;
	}

	/**
	 * @return the textFieldDeliveryAddress
	 */
	public JTextField getTextFieldDeliveryAddress() {
		return textFieldDeliveryAddress;
	}

	/**
	 * @return the textFieldDeliveryCity
	 */
	public JTextField getTextFieldDeliveryCity() {
		return textFieldDeliveryCity;
	}

	/**
	 * @return the textFieldDeliveryState
	 */
	public JTextField getTextFieldDeliveryState() {
		return textFieldDeliveryState;
	}

	/**
	 * @return the textFieldDeliveryPostcode
	 */
	public JTextField getTextFieldDeliveryPostcode() {
		return textFieldDeliveryPostcode;
	}

	/**
	 * @return the textFieldPhone
	 */
	public JTextField getTextFieldPhone() {
		return textFieldPhone;
	}

	/**
	 * @return the textFieldFax
	 */
	public JTextField getTextFieldFax() {
		return textFieldFax;
	}

	/**
	 * @return the textFieldABN
	 */
	public JTextField getTextFieldABN() {
		return textFieldABN;
	}
	
	/**
	 * Is user choose the correct input form?
	 * If not, open a new JOptionPane for error messages
	 * Correct input form:
	 * 	  Customer Code is not empty
	 *    Customer Name is not empty
	 */
	public boolean isInputCorrect() {
		boolean inputCorrectStatus = true;			// value to return for this function
		
		ArrayList<String> errorMessages = new ArrayList<String>();
		/*
		 *  check if customer code is empty
		 */
		if (textFieldCustomerCode.getText().isEmpty()) {
			String customerCodeIsEmptyErrorMsg = "Customer Code is Empty";
			errorMessages.add(customerCodeIsEmptyErrorMsg);
		}
		
		/*
		 *  check if customer name is empty
		 */
		if (textFieldCustomerName.getText().isEmpty()) {
			String customerNameIsEmptyErrorMsg = "Customer Name is Empty";
			errorMessages.add(customerNameIsEmptyErrorMsg);
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
