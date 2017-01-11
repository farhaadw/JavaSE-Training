/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import system.WOTS;

/**
 * @author Administrator
 *
 */
public class Swing extends JFrame implements ActionListener {

	// WOTS
	WOTS wots;
	
	// JPanel
	JPanel panel1, panel2, panel3;
	JLabel label;
	JTextArea textArea;
	JButton showCustOrders;
	JButton showPurchaseOrders;

	//
	JSplitPane splitPane;
	JTabbedPane tabbedPane;

	// customer order columns and data
	String[] custOrderColumns = { "Order Number", "Number Of Different Products In Order" };
	Object[][] custOrderData;
	
	/**
	 * Constructor
	 */
	public Swing() {
		initUI();
	}

	public void initUI() {
		// Instantiate system as employee with id of 1
		wots = new WOTS(1);
		// Get any checked out customer orders
		custOrderData = getCustomerOrders();
		
		// set default operations
		setTitle("Warehouse Tracking System");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// set layout
		setLayout(new BorderLayout());

		// create panel 1
		panel1 = new JPanel();
		panel1.setBackground(Color.lightGray);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		add(panel1, BorderLayout.WEST);

		// create panel 2
		panel2 = new JPanel();
		panel2.setBackground(Color.lightGray);
		add(panel2, BorderLayout.EAST);

		// create buttons
		showCustOrders = new JButton("check out new order");
		showPurchaseOrders = new JButton("do something else");

		// add action listeners
		showCustOrders.addActionListener(this);
		showPurchaseOrders.addActionListener(this);

		// add text area
		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);

		// add heading to panel 1
		label = new JLabel("My Customer orders");
		label.setFont(new Font("Arial", Font.TRUETYPE_FONT, 25));
		panel1.add(label, BorderLayout.LINE_START);

		// create table
		JTable table = new JTable(custOrderData, custOrderColumns);
		table.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(table);
		panel1.add(scrollPane, BorderLayout.CENTER);

		// add buttons to panel 1
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		panel3.add(showCustOrders, BorderLayout.WEST);
		panel3.add(showPurchaseOrders, BorderLayout.WEST);
		panel1.add(panel3);

		// tabbed pane.
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Arial", 0, 16));
		tabbedPane.add("Customer orders", panel1);
		tabbedPane.add("Purchase orders", panel2);
		add(tabbedPane);

	}
	
	// WOTS methods
	
	public Object[][] getCustomerOrders() {
		if (wots.getCheckedOutOrders() != null) {
			custOrderData = new Object[wots.getCheckedOutOrders().size()][2];
			for (int i = 0; i < wots.getCheckedOutOrders().size(); i++) {
				custOrderData[i][0] = wots.getCheckedOutOrders().get(i).getOrderID();
				custOrderData[i][1] = wots.getCheckedOutOrders().get(i).getProducts().size();
			}
			return custOrderData;
		}
		else return new Object[0][0];
	}

	/**
	 * Method to create layout.
	 */
	public void createLayout() {
	}

	public void createTable() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == showCustOrders) {
			System.out.println("show customers");
		} else if (e.getSource() == showPurchaseOrders) {
			System.out.println("show purchase orders");
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Swing ex = new Swing();
				ex.setVisible(true);
				
			}
		});
	}
}
