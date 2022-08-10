package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Help.DBConnection;
import Help.Helper;
import Objects.Deliever;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class DelieverGUI extends JFrame {

	DBConnection cnn = new DBConnection();
	private static Deliever deliever;
	private JPanel contentPane;
	private JTable table_WaitingOrders;
	private DefaultTableModel tableModel;
	private String[] OrderHeader = {"ID","userid","Client Name","Order date","Order Price","Location"};
	private String[][] orderData = new String[Helper.countRowFromSql(cnn, "deliever_order_data")][6];
	double totalPrice = 0;
	
	private String[] currentHeader = {"Product Name","Price","Quantity","Total"};
	private String[][] currentData = new String[Helper.countRowFromSql(cnn, "deliever_order_data")][4];
	private JTable table_currentOrders;
	private JTextField txF_CustomerName;
	private JTextField txF_PhoneNumb;
	private JTextField txF_Location;
	private JTextField txF_DelieverFee;
	private JTextField txF_TotalBill;
	private String Location,PhoneNumber,CustomerName;
	private double delieverFee = 0,TotalBill = 0;
	DefaultTableModel tablemodelcurrent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DelieverGUI frame = new DelieverGUI(deliever);
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
	public DelieverGUI(Deliever deliever) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 859, 510);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fillUpShopHistoryArr(orderData);
		
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(255, 165, 0));
		tabbedPane.setBounds(164, 10, 671, 453);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Waiting Orders", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 99, 646, 317);
		panel.add(scrollPane);
		
		tableModel = new DefaultTableModel(orderData,OrderHeader);
		
		table_WaitingOrders = new JTable(tableModel);
		scrollPane.setViewportView(table_WaitingOrders);
		table_WaitingOrders.setBackground(new Color(255, 165, 0));
		
		JButton btnNewButton = new JButton("Get Order");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = cnn.connDB();
				int fnd = 0;
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM deliever_order_data WHERE delieverId = "+deliever.getID());
					if(rs.next()==true)
					{
						Helper.showMsg("You have Continuing Order!");
						fnd++;
					}
					
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				
				if(fnd == 0)
				{
					
				
				if(table_WaitingOrders.getSelectedRow()>=0)
				{
					int id = Integer.parseInt( (String) table_WaitingOrders.getValueAt(table_WaitingOrders.getSelectedRow(), 0));
					
					try {
						PreparedStatement pst = con.prepareStatement("UPDATE deliever_order_data SET delieverId = ? WHERE ID="+id);
						pst.setInt(1, deliever.getID());
						pst.executeUpdate();
						
						orderData = new String[Helper.countRowFromSql(cnn, "deliever_order_data")][6];
						
						fillUpShopHistoryArr(orderData);
						
						JScrollPane scrollPane = new JScrollPane();
						scrollPane.setBounds(10, 99, 646, 317);
						panel.add(scrollPane);
						
						tableModel = new DefaultTableModel(orderData,OrderHeader);
						
						table_WaitingOrders = new JTable(tableModel);
						scrollPane.setViewportView(table_WaitingOrders);
						table_WaitingOrders.setBackground(new Color(255, 165, 0));
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				else 
				{
					Helper.showMsg("MissOp");
				}
				
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(215, 38, 198, 40);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Current order", null, panel_1, null);
		panel_1.setLayout(null);
		
		
		
		txF_CustomerName = new JTextField();
		txF_CustomerName.setEditable(false);
		txF_CustomerName.setFont(new Font("Tahoma", Font.BOLD, 16));
		txF_CustomerName.setBounds(447, 10, 209, 34);
		panel_1.add(txF_CustomerName);
		txF_CustomerName.setColumns(10);
		
		txF_PhoneNumb = new JTextField();
		txF_PhoneNumb.setEditable(false);
		txF_PhoneNumb.setFont(new Font("Tahoma", Font.BOLD, 16));
		txF_PhoneNumb.setColumns(10);
		txF_PhoneNumb.setBounds(447, 57, 209, 34);
		panel_1.add(txF_PhoneNumb);
		
		txF_Location = new JTextField();
		txF_Location.setEditable(false);
		txF_Location.setFont(new Font("Tahoma", Font.BOLD, 16));
		txF_Location.setColumns(10);
		txF_Location.setBounds(447, 102, 209, 34);
		panel_1.add(txF_Location);
		
		txF_DelieverFee = new JTextField();
		txF_DelieverFee.setFont(new Font("Tahoma", Font.BOLD, 16));
		txF_DelieverFee.setEditable(false);
		txF_DelieverFee.setColumns(10);
		txF_DelieverFee.setBounds(447, 146, 209, 34);
		panel_1.add(txF_DelieverFee);
		
		txF_TotalBill = new JTextField();
		txF_TotalBill.setFont(new Font("Tahoma", Font.BOLD, 16));
		txF_TotalBill.setEditable(false);
		txF_TotalBill.setColumns(10);
		txF_TotalBill.setBounds(10, 146, 209, 34);
		panel_1.add(txF_TotalBill);
		
		JLabel lblNewLabel = new JLabel("Customer Name : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(278, 10, 142, 34);
		panel_1.add(lblNewLabel);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number : ");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPhoneNumber.setBounds(278, 54, 142, 34);
		panel_1.add(lblPhoneNumber);
		
		JLabel lblLocation = new JLabel("Location : ");
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLocation.setBounds(278, 102, 142, 34);
		panel_1.add(lblLocation);
		
		JLabel lblDeliever = new JLabel("Deliever fee : ");
		lblDeliever.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDeliever.setBounds(278, 146, 142, 34);
		panel_1.add(lblDeliever);
		
		JLabel lblTotalBill = new JLabel("Total Bill : ");
		lblTotalBill.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotalBill.setBounds(10, 112, 142, 34);
		panel_1.add(lblTotalBill);
		
		JButton btn_FinishOrder = new JButton("Finish Order");
		btn_FinishOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = cnn.connDB();
				try {
					PreparedStatement pst = con.prepareStatement("DELETE FROM deliever_order_data WHERE delieverId = "+deliever.getID());
					pst.executeUpdate();
					Helper.showMsg("Succesfully Finished");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
					int rowCount = tablemodelcurrent == null ? 1 : tablemodelcurrent.getRowCount();
					//Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
						tableModel.removeRow(i);
					}
				
			}
		});
		btn_FinishOrder.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_FinishOrder.setBounds(10, 19, 190, 28);
		panel_1.add(btn_FinishOrder);
		
		JButton btn_ShowOrderInfo = new JButton("Show Order Details");
		btn_ShowOrderInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = cnn.connDB();
				//"Product Name","Price","Quantity","Total"}
				currentData = new String[Helper.countRowFromSql(cnn, "deliever_order_data")][4];
				int row = 0;
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM deliever_order_data WHERE delieverId = "+deliever.getID());
					
					int customerid = 0;
					while(rs.next())
					{
						ResultSet rs1 = st.executeQuery("SELECT * FROM productdata WHERE ID = "+rs.getInt("prodid"));
						txF_Location.setText(rs.getString("Location"));
						txF_PhoneNumb.setText(rs.getString("phoneNumb"));
					
						customerid = rs.getInt("userid");
						
						while(rs1.next())
						{
							
							String strprice = Double.toString(rs1.getDouble("price"));
							
							currentData[row][0] = rs1.getString("Name");
							currentData[row][1] = strprice;
							
						}
						String strquantity = Integer.toString(rs.getInt("quantity"));
						
						currentData[row][2] = strquantity;
						double price = Double.parseDouble(currentData[row][1]);
						
						int  quantity = Integer.parseInt(currentData[row][2]);
						double total = price * quantity;
						
						
						String strtotal = Double.toString(total);
						System.out.println("asdf");
						currentData[row][3] = strtotal ;
						System.out.println("asdf");
						TotalBill += total;
						
						
						
						row++;
					}
					String str1 = String.format("%.2f", TotalBill);
					String str2 = String.format("%.2f", (TotalBill*0.2));
					txF_TotalBill.setText(str1);
					txF_DelieverFee.setText(str2);
					
					
					ResultSet rs3 = st.executeQuery("SELECT * FROM userdata WHERE ID = "+customerid);
					while(rs3.next())
					{
						if(rs3.getInt("ID") == customerid)
						{
							txF_CustomerName.setText(rs3.getString("Name"));
							
						}
					}
					TotalBill = 0;
					
					JScrollPane scrollPane_1 = new JScrollPane();
					scrollPane_1.setBounds(10, 201, 646, 215);
					panel_1.add(scrollPane_1);
					
					
					
					tablemodelcurrent = new DefaultTableModel(currentData,currentHeader)
					{
						@Override
						public boolean isCellEditable(int row,int col)
						{
							return false;
						}
					};
				
					table_currentOrders = new JTable(tablemodelcurrent);
					table_currentOrders.setBackground(new Color(255, 165, 0));
					scrollPane_1.setViewportView(table_currentOrders);
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btn_ShowOrderInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_ShowOrderInfo.setBounds(10, 57, 190, 28);
		panel_1.add(btn_ShowOrderInfo);
		
		
		
		
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DelieverProfileGUI dg = new DelieverProfileGUI(deliever);
				dg.setVisible(true);
				dispose();
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(DelieverGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		btnNewButton_1.setBackground(new Color(255, 165, 0));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setAutoscrolls(true);
		btnNewButton_1.setBounds(10, 27, 140, 123);
		contentPane.add(btnNewButton_1);
		
		JButton btn_SellerLogOut = new JButton("Log-out");
		btn_SellerLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureLogOut")==1)
				{
					LoginGUI lg = new LoginGUI();
					lg.setVisible(true);
					dispose();
					
				}
			}
		});
		btn_SellerLogOut.setForeground(Color.BLACK);
		btn_SellerLogOut.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		btn_SellerLogOut.setBorder(null);
		btn_SellerLogOut.setBackground(new Color(255, 165, 0));
		btn_SellerLogOut.setBounds(10, 413, 140, 50);
		contentPane.add(btn_SellerLogOut);
	}
	
	
	//"ID","userid","Client Name","Order date","Order Price","Location"
	

	public void fillUpShopHistoryArr(String [][] arr)
	{
		totalPrice = 0;
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM deliever_order_data WHERE delieverId = 0");
			
			int row = 0,col = 0;
			while(rs.next())
			{
				
				arr[row][col] = Integer.toString(rs.getInt("ID"));
				arr[row][col+1] = Integer.toString(rs.getInt("userid"));
				arr[row][col+2] = rs.getString("clientName");
				arr[row][col+3] = rs.getString("shop_date") + " " + rs.getString("shop_time");
				arr[row][col+4] = Double.toString(rs.getDouble("shopPrice"));
				arr[row][col+5] = rs.getString("Location");
				row++;
					
					
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
