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

public class InvoiceFrame extends JFrame {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InvoiceFrame frame = new InvoiceFrame();
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
	public InvoiceFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 764, 450);

		JTable invoiceTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(invoiceTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		invoiceTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int actionDelete = JOptionPane.showConfirmDialog(null, "Do you really want to delete?", "Delete", JOptionPane.YES_NO_OPTION);
				if (actionDelete == JOptionPane.YES_OPTION) {
					// Delete
					// System.out.println("Delete");
					int selectedRow = invoiceTable.getSelectedRow();
					deleteInvoice(invoiceTable, selectedRow);
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
						.addComponent(btnDelete)
						.addComponent(btnFilter))
					.addGap(9))
		);
		
		
		String listQuery = "Select * FROM ORDERS;";
        String countQuery = "Select COUNT(*) FROM ORDERS;";
		
		Object[][] data = Table.populateTableData(listQuery, countQuery);
        String[] columnNames = {"ORDER_ID", "CUSTOMER_CODE", "ORDER_DATE", "QUANTITY", "GST", "TOTAL"};
        invoiceTable.setModel(new DefaultTableModel(data, columnNames) {
        	Class[] columnTypes = new Class[] {
        		String.class, String.class, String.class, Integer.class, Float.class, Float.class
        	};
        	public Class getColumnClass(int columnIndex) {
        		return columnTypes[columnIndex];
        	}
        	boolean[] columnEditables = new boolean[] {
        		false, false, false, false, false, false
        	};
        	public boolean isCellEditable(int row, int column) {
        		return columnEditables[column];
        	}
        });
		
		scrollPane.setViewportView(invoiceTable);
		getContentPane().setLayout(groupLayout);
	}
	
	private void deleteInvoice(JTable table, int selectedRow) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		String delete = "DELETE FROM ORDERS WHERE ORDER_ID = ?";
		String orderID = (String) model.getValueAt(selectedRow, 0);
		
		try {
			   Connection con = MainFrame.getConnection();
			   PreparedStatement deleteStatement = con.prepareStatement(delete);
			   deleteStatement.setString(1, orderID);			 
			   deleteStatement.executeUpdate();	
	 	} catch (SQLException e) {e.printStackTrace();}
		
		model.removeRow(selectedRow);
	}
}
