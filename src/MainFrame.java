import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JLayeredPane;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Label;
import com.toedter.calendar.JDateChooser;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainFrame {

	private JFrame mainFrame;
	private JLayeredPane layeredPane = new JLayeredPane(); // layeredPane consists of panels for Sales, Product and Purchases
	private JTextField textField_5;
	private JTextField textField_10;
	private JTable table;
	private JTextField quantityTextField;
	private JTextField totalGSTExclTextField;
	private JTextField GSTTextField;
	private JTextField totalTextField;
	private JTextField textFieldInvoiceNumber;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private static Connection con;	// connect to database
	private JTextField textFieldCustomerID;
	private JTextField textFieldCustomerName;
	private JTable invoiceDetailTable;
	private static ArrayList<Object[]> currentInvoice = new ArrayList<Object[]>();
	private int currentInvoiceTotalQuantity;
	private float currentInvoiceTotalGSTExcl;
	private float currentInvoiceTotalGST;
	private float currentInvoiceTotalGSTIncl;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Switch to a panel in layeredPane.
	 */
	
	public void switchPanel(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	/**
	 *  open a new frame (new window)
	 */
	public void openNewFrame(JFrame newFrame) {
		newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		newFrame.setVisible(true);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		/** Connect to database **/
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();  
			con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/1UrTMdldVR","1UrTMdldVR","OAlHi4zHXX");	
		}catch(Exception e){ System.out.println(e);}
		
		/** Initialize some values for Total section **/
		currentInvoiceTotalQuantity = 0;
		currentInvoiceTotalGSTExcl = 0.0f;
		currentInvoiceTotalGST = 0.0f;
		currentInvoiceTotalGSTIncl = 0.0f;
		
		/** Set up main frame **/
		mainFrame = new JFrame();
		mainFrame.setBounds(100, 100, 1113, 779);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel salesPanel = new JPanel();
		layeredPane.add(salesPanel, "name_92328742804838");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel invoicePanel = new JPanel();
		tabbedPane.addTab("Invoice", null, invoicePanel, null);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnFindACustomer = new JButton("Choose a Customer");
		btnFindACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseACustomerPanel chooseACustomerPanel = new ChooseACustomerPanel();
				
				/*
				 * Open a new JOptionPane for choosing a customer. If choosing is not correct form, pop up error messages and open JOptionPane again.
				 * Closes only when choose "Select" option with no error (choose correctly) or choosing "Cancel" option.
				 * If choosing correctly, extract relevant data and push to currentInvoiceTable.  
				 */
				
				boolean isInputCorrect = false;				// Is choosing a customer correctly?
				int optionYesNo;
				do {
					Object[] optionsChooseACustomerPanel = {
							"Select", "Cancel"
						};
					optionYesNo = JOptionPane.showOptionDialog(null, chooseACustomerPanel, "Choose a customer", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, optionsChooseACustomerPanel, optionsChooseACustomerPanel[0]);
					if (optionYesNo == JOptionPane.NO_OPTION ) {
						isInputCorrect = true;
					}else {
						isInputCorrect = chooseACustomerPanel.isInputCorrect();
					}
				}while(!isInputCorrect);
				
				if (optionYesNo == JOptionPane.YES_OPTION) {
					Object[] newCustomerInfo = chooseACustomerPanel.getSelectedCustomerInfo();
					textFieldCustomerID.setText((String) newCustomerInfo[0]);
					textFieldCustomerName.setText((String) newCustomerInfo[1]);
				}
				
				
			}
		});
		
		JButton btnAddAProduct = new JButton("Add a product to invoice");
		btnAddAProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddAProductToInvoicePanel addAProductToInvoicePanel = new AddAProductToInvoicePanel();
				Object[] optionsAddAProductToInvoicePanel = {
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
					optionYesNo = JOptionPane.showOptionDialog(null, addAProductToInvoicePanel, "Choose a customer", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, optionsAddAProductToInvoicePanel, optionsAddAProductToInvoicePanel[0]);
					if (optionYesNo == JOptionPane.NO_OPTION ) {
						isInputCorrect = true;
					}else {
						isInputCorrect = addAProductToInvoicePanel.isInputCorrect();
					} 
				}while (!isInputCorrect);
				
				if (optionYesNo == JOptionPane.YES_OPTION) {
					Object[] newProductInfo = addAProductToInvoicePanel.getSelectedProductInfo();
					Object[] newProductExtraInfo = addAProductToInvoicePanel.getSelectedProductExtraInfo();
					
					HashMap<String, Object> invoiceDetail = new HashMap<String, Object>();
					invoiceDetail.put("Product ID", (String)newProductInfo[0]);
					invoiceDetail.put("Product Name", (String)newProductInfo[1]);
					invoiceDetail.put("Unit", (String)newProductInfo[3]);
					invoiceDetail.put("Unit Price", (float) newProductInfo[5]);
					invoiceDetail.put("Quantity", (int) newProductExtraInfo[0]);
					invoiceDetail.put("Quantity Per Unit", (int) newProductInfo[2]);
					invoiceDetail.put("GST", (boolean)newProductExtraInfo[5]);
					float invoiceDetailSubTotal = (float) invoiceDetail.get("Unit Price") * (int) invoiceDetail.get("Quantity") * (int) invoiceDetail.get("Quantity Per Unit");
					if ((boolean)invoiceDetail.get("GST")) { 
						// Include GST
						invoiceDetailSubTotal *= 1.1f;
					}
					invoiceDetail.put("Sub Total", invoiceDetailSubTotal);
					invoiceDetail.put("Keep Stock", (boolean)newProductExtraInfo[2]);
					
					Object[] invoiceDetailNewRow = new Object[9];
					invoiceDetailNewRow[0] = invoiceDetail.get("Product ID"); //ProductID
					invoiceDetailNewRow[1] = invoiceDetail.get("Product Name"); //ProductName
					invoiceDetailNewRow[2] = invoiceDetail.get("Unit"); //Unit
					invoiceDetailNewRow[3] = invoiceDetail.get("Unit Price"); //Unit Price
					invoiceDetailNewRow[4] = invoiceDetail.get("Quantity"); //Quantity
					invoiceDetailNewRow[5] = invoiceDetail.get("Quantity Per Unit"); //Quantity per Unit
					invoiceDetailNewRow[6] = invoiceDetail.get("GST"); //GST
					invoiceDetailNewRow[7] = invoiceDetail.get("Sub Total"); //Sub Total
					invoiceDetailNewRow[8] = invoiceDetail.get("Keep Stock"); //Keep stock
					
					DefaultTableModel model = (DefaultTableModel) invoiceDetailTable.getModel();			
			        model.addRow(invoiceDetailNewRow);     
			        currentInvoice.add(invoiceDetailNewRow);
			        
			        /* 
			         * Update Total section
			         */
			        currentInvoiceTotalQuantity += (int) invoiceDetail.get("Quantity");
			        currentInvoiceTotalGSTExcl += (float) invoiceDetail.get("Unit Price") * (int) invoiceDetail.get("Quantity") * (int) invoiceDetail.get("Quantity Per Unit");
			        if ((boolean)invoiceDetail.get("GST")) { // GST
			        	currentInvoiceTotalGST += (float) currentInvoiceTotalGSTExcl * 0.1f;
			        }
			        currentInvoiceTotalGSTIncl += (float) invoiceDetail.get("Sub Total");
			        quantityTextField.setText(Integer.toString(currentInvoiceTotalQuantity));
			        totalGSTExclTextField.setText(Double.toString(currentInvoiceTotalGSTExcl));
			        GSTTextField.setText(Double.toString(currentInvoiceTotalGST));
			        totalTextField.setText(Double.toString(currentInvoiceTotalGSTIncl));
				}
			}
		});
		
		JPanel panel = new JPanel();
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newInvoice(invoiceDetailTable);
			}
		});
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String orderID = "abcxyz";
				String customerCode = "a";
				String orderDate = "2019-09-07";
				int quantity = Integer.parseInt(quantityTextField.getText());
				float GST = Float.parseFloat(GSTTextField.getText());
				float total = Float.parseFloat(GSTTextField.getText());
				String insertInvoiceQuery = "INSERT INTO `ORDERS` (`ORDER_ID`, `CUSTOMER_CODE`, `ORDER_DATE`, `QUANTITY`, `GST`, `TOTAL`)"  
									+ " VALUES (?, ?, ?, ?, ?, ?)";
				Object[] invoiceInfo = {orderID, customerCode, orderDate, quantity, GST, total};
				Table.insertNewInstanceToDatabase(insertInvoiceQuery, invoiceInfo);
				
				for(int i = 0; i < currentInvoice.size(); i++) {
					String insertInvoiceDetailQuery = "INSERT INTO `ORDER_DETAIL` (`ORDER_ID`, `PRODUCT_ID`, `UNIT_PRICE`, `QUANTITY`, `GST`, `SUB_TOTAL`, `KEEP_STOCK`) " 
													+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
					Object[] invoiceDetailInfo = currentInvoice.get(i);
					Table.insertNewInstanceToDatabase(insertInvoiceDetailQuery, invoiceDetailInfo);
				}
			}
		});
		
		JButton btnPrint = new JButton("Print");
		
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel lblTotal = new JLabel("Total");
		
		JLabel lblQuantity = new JLabel("Quantity");
		
		quantityTextField = new JTextField();
		quantityTextField.setColumns(10);
		
		JLabel lblTotalgstExcl = new JLabel("Total (GST Excl.)");
		
		totalGSTExclTextField = new JTextField();
		totalGSTExclTextField.setColumns(10);
		
		JLabel lblGst = new JLabel("GST");
		
		GSTTextField = new JTextField();
		GSTTextField.setColumns(10);
		
		JLabel lblTotal_1 = new JLabel("Total");
		
		totalTextField = new JTextField();
		totalTextField.setColumns(10);
		GroupLayout gl_invoicePanel = new GroupLayout(invoicePanel);
		gl_invoicePanel.setHorizontalGroup(
			gl_invoicePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addGap(10)
					.addComponent(btnFindACustomer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAddAProduct)
					.addGap(803))
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNew)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrint)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLoad)
					.addContainerGap(830, Short.MAX_VALUE))
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTotal, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(225)
					.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addGap(43)
					.addComponent(lblTotalgstExcl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(totalGSTExclTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblGst)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(GSTTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblTotal_1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(242, Short.MAX_VALUE))
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_invoicePanel.setVerticalGroup(
			gl_invoicePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_invoicePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_invoicePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFindACustomer)
						.addComponent(btnAddAProduct))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addGroup(gl_invoicePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTotal)
						.addComponent(lblQuantity)
						.addComponent(quantityTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotalgstExcl)
						.addComponent(totalGSTExclTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblGst)
						.addComponent(GSTTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTotal_1)
						.addComponent(totalTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_invoicePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNew)
						.addComponent(btnSave)
						.addComponent(btnPrint)
						.addComponent(btnLoad))
					.addContainerGap())
		);
		
		JLabel lblCustomerInfo = new JLabel("Customer Info");
		
		JLabel lblCustomerId = new JLabel("Customer ID");
		
		textFieldCustomerID = new JTextField();
		textFieldCustomerID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name");
		
		textFieldCustomerName = new JTextField();
		textFieldCustomerName.setColumns(10);
		
		JLabel lblInvoiceNumber_1 = new JLabel("Invoice number");
		
		textFieldInvoiceNumber = new JTextField();
		textFieldInvoiceNumber.setColumns(10);
		
		JLabel lblInvoiceDate = new JLabel("Invoice Date");
		
		JDateChooser dateChooser = new JDateChooser();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCustomerId)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldCustomerID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(114)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldCustomerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblCustomerInfo))
					.addGap(58)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblInvoiceNumber_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldInvoiceNumber, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblInvoiceDate, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dateChooser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(373, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCustomerInfo)
							.addComponent(lblInvoiceDate))
						.addComponent(dateChooser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCustomerId)
						.addComponent(textFieldCustomerID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(textFieldCustomerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInvoiceNumber_1)
						.addComponent(textFieldInvoiceNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		scrollPane.setViewportView(table);
		
		invoiceDetailTable = new JTable();
		invoiceDetailTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Product ID", "Product Name", "Unit", "Unit Price", "Quantity", "QuantityPerUnit", "GST", "Subtotal", "Keep Stock"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Float.class, Integer.class, Integer.class, Boolean.class, Float.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane.setViewportView(invoiceDetailTable);
		invoicePanel.setLayout(gl_invoicePanel);
		
		JPanel stockListPanel = new JPanel();
		tabbedPane.addTab("Stock List", null, stockListPanel, null);
		
		JPanel panel_1 = new JPanel();
		
		JScrollPane scrollPane_3 = new JScrollPane();
		
		JButton btnPrintStockList = new JButton("Print Stockout List");
		GroupLayout gl_stockListPanel = new GroupLayout(stockListPanel);
		gl_stockListPanel.setHorizontalGroup(
			gl_stockListPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_stockListPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_stockListPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
						.addComponent(btnPrintStockList))
					.addContainerGap())
		);
		gl_stockListPanel.setVerticalGroup(
			gl_stockListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_stockListPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPrintStockList)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JLabel lblTableOfStock = new JLabel("Table of stock list goes here");
		scrollPane_3.setViewportView(lblTableOfStock);
		
		JLabel lblNewLabel_1 = new JLabel("Customer");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		
		JLabel lblInvoiceNumber = new JLabel("Invoice Number");
		lblInvoiceNumber.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(textField_5, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addGap(43)
					.addComponent(lblInvoiceNumber, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_10, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
					.addGap(318))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblInvoiceNumber, GroupLayout.DEFAULT_SIZE, 17, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(1)
								.addComponent(textField_10)))
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(3)
								.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(1)
								.addComponent(textField_5))))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		stockListPanel.setLayout(gl_stockListPanel);
		
		JPanel pickupListPanel = new JPanel();
		tabbedPane.addTab("Pickup List", null, pickupListPanel, null);
		
		JPanel statementsPanel = new JPanel();
		tabbedPane.addTab("Statements", null, statementsPanel, null);
		
		JPanel statementReportPanel = new JPanel();
		tabbedPane.addTab("Statement Report", null, statementReportPanel, null);
		GroupLayout gl_salesPanel = new GroupLayout(salesPanel);
		gl_salesPanel.setHorizontalGroup(
			gl_salesPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, Alignment.TRAILING)
		);
		gl_salesPanel.setVerticalGroup(
			gl_salesPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane)
		);
		salesPanel.setLayout(gl_salesPanel);
		
		JPanel purchasesPanel = new JPanel();
		layeredPane.add(purchasesPanel, "name_798211335409");
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnNewButton = new JButton("New Order");
		
		JButton btnDeleteOrder = new JButton("Delete Order");
		
		JButton btnPrintOrder = new JButton("Print Order");
		GroupLayout gl_purchasesPanel = new GroupLayout(purchasesPanel);
		gl_purchasesPanel.setHorizontalGroup(
			gl_purchasesPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1090, Short.MAX_VALUE)
				.addGroup(gl_purchasesPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDeleteOrder)
					.addGap(18)
					.addComponent(btnPrintOrder, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(762, Short.MAX_VALUE))
		);
		gl_purchasesPanel.setVerticalGroup(
			gl_purchasesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_purchasesPanel.createSequentialGroup()
					.addComponent(tabbedPane_1, GroupLayout.PREFERRED_SIZE, 633, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addGroup(gl_purchasesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnPrintOrder)
						.addComponent(btnDeleteOrder))
					.addContainerGap())
		);
		
		JPanel orderLinesPanel = new JPanel();
		tabbedPane_1.addTab("Order Lines", null, orderLinesPanel, null);
		
		JButton btnFindASupplier = new JButton("Find a Supplier");
		
		JButton btnAddAProduct_1 = new JButton("Add a product to Order Lines");
		
		JPanel panel_4 = new JPanel();
		
		JLabel lblOrderLines = new JLabel("Order Lines");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_orderLinesPanel = new GroupLayout(orderLinesPanel);
		gl_orderLinesPanel.setHorizontalGroup(
			gl_orderLinesPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_orderLinesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_orderLinesPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
						.addGroup(gl_orderLinesPanel.createSequentialGroup()
							.addComponent(btnFindASupplier, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAddAProduct_1))
						.addComponent(lblOrderLines))
					.addContainerGap())
		);
		gl_orderLinesPanel.setVerticalGroup(
			gl_orderLinesPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_orderLinesPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_orderLinesPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFindASupplier)
						.addComponent(btnAddAProduct_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblOrderLines)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTableOfOrder = new JLabel("Table of Order Lines goes here");
		scrollPane_1.setViewportView(lblTableOfOrder);
		
		JLabel lblSupplierInfo = new JLabel("Supplier Info");
		
		JLabel lblSupplierName = new JLabel("Supplier Name");
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		
		JLabel lblOrderDate = new JLabel("Order Place Date");
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		
		JLabel lblType = new JLabel("Type");
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		
		JLabel lblFcl = new JLabel("FCL");
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		
		JLabel lblMiscellaneous = new JLabel("Miscellaneous");
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(lblMiscellaneous, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
							.addGap(18))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblSupplierInfo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblSupplierName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)))
					.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblOrderDate, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(lblType, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFcl, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(textField_12, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(451, Short.MAX_VALUE))
				.addComponent(textField_13, GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addComponent(lblSupplierInfo, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSupplierName)
						.addComponent(lblOrderDate)
						.addComponent(textField_8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFcl)
						.addComponent(textField_11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblType)
						.addComponent(textField_9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(5)
					.addComponent(lblMiscellaneous)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField_13, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		orderLinesPanel.setLayout(gl_orderLinesPanel);
		
		JPanel orderListPanel = new JPanel();
		tabbedPane_1.addTab("Order List", null, orderListPanel, null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_orderListPanel = new GroupLayout(orderListPanel);
		gl_orderListPanel.setHorizontalGroup(
			gl_orderListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_orderListPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 1054, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_orderListPanel.setVerticalGroup(
			gl_orderListPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_orderListPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTableOfOrder_1 = new JLabel("Table of Order list goes here");
		scrollPane_2.setViewportView(lblTableOfOrder_1);
		orderListPanel.setLayout(gl_orderListPanel);
		purchasesPanel.setLayout(gl_purchasesPanel);
		
		JButton btnSales = new JButton("Sales");
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(salesPanel);
			}
		});
		btnSales.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnSales);
		
		JButton btnProduct = new JButton("Product");
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductFrame ProductFrame = new ProductFrame();
				openNewFrame(ProductFrame);
			}
		});
		
		JButton btnPurchases = new JButton("Purchases");
		btnPurchases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanel(purchasesPanel);
			}
		});
		btnPurchases.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnPurchases);
		btnProduct.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnProduct);
		
		JButton btncustomer = new JButton("Customer");
		btncustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open new customer window & show contents
				CustomerFrame customerFrame = new CustomerFrame();
				openNewFrame(customerFrame);
			}
		});
		btncustomer.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btncustomer);
		
		JButton btnSupplier = new JButton("Supplier");
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SupplierFrame SupplierFrame = new SupplierFrame();
				openNewFrame(SupplierFrame);
			}
		});
		btnSupplier.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnSupplier);
		
		JButton btnInvoices = new JButton("Invoices");
		btnInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InvoiceFrame invoiceFrame = new InvoiceFrame();
				openNewFrame(invoiceFrame);
			}
		});
		btnInvoices.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnInvoices);
		
		JButton btnReports = new JButton("Reports");
		btnReports.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnReports);
		
		JButton btnPrintPreview = new JButton("Print Preview");
		btnPrintPreview.setFont(new Font("Dialog", Font.PLAIN, 12));
		toolBar.add(btnPrintPreview);
		GroupLayout groupLayout = new GroupLayout(mainFrame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(layeredPane, Alignment.TRAILING)
						.addComponent(toolBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		mainFrame.getContentPane().setLayout(groupLayout);
		
		JMenuBar menuBar = new JMenuBar();
		mainFrame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuBar.add(mnFile);
		
		JMenuItem mntmNewInvoice = new JMenuItem("New Invoice");
		mnFile.add(mntmNewInvoice);
		
		JMenuItem mntmLoadInvoice = new JMenuItem("Load Invoice");
		mnFile.add(mntmLoadInvoice);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Sales");
		mnFile.add(mntmNewMenuItem);
		
		JMenuItem mntmProduct = new JMenuItem("Product");
		mnFile.add(mntmProduct);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmPurchases = new JMenuItem("Purchases");
		mnFile.add(mntmPurchases);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmPrintPreview = new JMenuItem("Print Preview");
		mnFile.add(mntmPrintPreview);
		
		JSeparator separator_3 = new JSeparator();
		mnFile.add(separator_3);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Exit");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu = new JMenu("View");
		mnNewMenu.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("customer");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmSupplier = new JMenuItem("Supplier");
		mnNewMenu.add(mntmSupplier);
		
		JSeparator separator_4 = new JSeparator();
		mnNewMenu.add(separator_4);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Invoices");
		mnNewMenu.add(mntmNewMenuItem_3);
		
		JSeparator separator_5 = new JSeparator();
		mnNewMenu.add(separator_5);
		
		JMenuItem mntmReports = new JMenuItem("Reports");
		mnNewMenu.add(mntmReports);
		
		JSeparator separator_6 = new JSeparator();
		mnNewMenu.add(separator_6);
		
		JMenuItem mntmCompanyProfile = new JMenuItem("Company Profile");
		mnNewMenu.add(mntmCompanyProfile);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setFont(new Font("Dialog", Font.PLAIN, 14));
		menuBar.add(mnHelp);
		
		JMenuItem mntmContent = new JMenuItem("Content");
		mnHelp.add(mntmContent);
		
		JSeparator separator_7 = new JSeparator();
		mnHelp.add(separator_7);
		
		JMenuItem mntmCompanyProfile_1 = new JMenuItem("About ...");
		mnHelp.add(mntmCompanyProfile_1);
	}
	
	// get connection to database
	public static Connection getConnection() {
		return con;
	}
	
	public static void loadInvoice(JTable table, int selectedRow) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		//An invoice is identified by their invoiceID
		String invoiceID = (String) model.getValueAt(selectedRow, 0);
		String listQuery = "SELECT ORDER_DETAIL.PRODUCT_ID, PRODUCT.PRODUCT_NAME, PRODUCT.UNIT, ORDER_DETAIL.UNIT_PRICE, "
				+ "ORDER_DETAIL.QUANTITY, PRODUCT.QUANTITY_PER_UNIT, ORDER_DETAIL.GST, ORDER_DETAIL.SUBTOTAL, ORDER_DETAIL.KEEP_STOCK"
				+ " FROM ORDER_DETAIL JOIN PRODUCT ON ORDER_DETAIL.PRODUCT_ID = PRODUCT.PRODUCT_ID"
				+ " WHERE ORDER_DETAIL.ORDER_ID = " + invoiceID + ";";
		String countQuery = "SELECT COUNT(*) FROM ORDER_DETAIL WHERE ORDER_DETAIL.ORDER_ID = " + invoiceID + ";";
		Object[][] data = Table.populateTableData(listQuery, countQuery);
		String[] columnNames = {"Product ID", "Product Name", "Unit", "Unit Price", "Quantity", "QuantityPerUnit", "GST", "Subtotal", "Keep Stock"};
		
		DefaultTableModel tableData = new DefaultTableModel(data, columnNames);
		table.setModel(tableData);
	}
	
	public void newInvoice(JTable table) {
		/* Clear table section */
		currentInvoice.clear();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		textFieldCustomerID.setText("");
		textFieldCustomerName.setText("");
		textFieldInvoiceNumber.setText("");
		/* Clear customer info section */
		
	}
}
