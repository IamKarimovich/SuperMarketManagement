package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.mariadb.jdbc.internal.util.constant.StateChange;

import Help.DBConnection;
import Help.Helper;
import Objects.Client;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private static Client client;
	private JTable table_ShopList,table_OnlineShopList;
	private DBConnection cnn = new DBConnection();
	private String[] ShoplistHeader = {"ID","Shopping Date","Shopping hours","Total shopping price","Total shopping bonus"};
	private String[][] shopListData = new String[Helper.countRowFromSql(cnn,"shopping_data")][5];
	private JTable table_products;
	private DefaultTableModel tableModelProd,tableOnlineShopList;
	private String[] headerProducts = {"ID","Product Name","Price"};
	private String[][] dataProducts = new String[Helper.countRowFromSql(cnn,"productdata")][3];
	
	private String[] headerOrder = {"ID","Product Name","Price","Quantity"};
	private String[][] dataOrder = new String[Helper.countRowFromSql(cnn,"productdata")][4];
	private int rowOnline = 0,colOnline = 0;
	String[] shopHistoryHeader = {"No","Product name","Price","Quantity","Total Price"};
	String[][] shopHistoryData = new String[Helper.countRowFromSql(cnn, "productdata")][5];
	private double totalBill = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI(client);
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
	public ClientGUI(Client client) {
		//System.out.println(client.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 817, 538);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 783, 481);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 165, 0));
		tabbedPane.addTab("Show Shopping List", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPaneShopList = new JScrollPane();
		scrollPaneShopList.setFont(new Font("Tahoma", Font.BOLD, 10));
		scrollPaneShopList.setBounds(10, 146, 758, 298);
		panel.add(scrollPaneShopList);
		
		
		fillUpShopListData(shopListData,client.getID());
		
		DefaultTableModel tableModel = new DefaultTableModel(shopListData,ShoplistHeader)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table_ShopList = new JTable(tableModel);
		table_ShopList.setFont(new Font("Tahoma", Font.BOLD, 10));
		table_ShopList.setBackground(new Color(192, 192, 192));
		scrollPaneShopList.setViewportView(table_ShopList);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserProfileGUI ug = new UserProfileGUI(client);
				ug.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(255, 165, 0));
		btnNewButton.setIcon(new ImageIcon(ClientGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		btnNewButton.setBounds(10, 10, 151, 125);
		panel.add(btnNewButton);
		
		JButton btnShowList = new JButton("Show List");
		btnShowList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_ShopList.getSelectedRow()>=0)
				{
					String strdate = (String) table_ShopList.getValueAt(table_ShopList.getSelectedRow(), 1);
					String strtime = (String) table_ShopList.getValueAt(table_ShopList.getSelectedRow(), 2);
					
					
					FrameOrderList fmo = new FrameOrderList(shopHistoryData, strdate, strtime, client);
					fmo.setVisible(true);
					
				}
				
			}
		});
		btnShowList.setBorder(null);
		btnShowList.setFont(new Font("Segoe UI Historic", Font.BOLD, 20));
		btnShowList.setBackground(new Color(255, 165, 0));
		btnShowList.setBounds(545, 55, 151, 45);
		panel.add(btnShowList);
		
		JButton btn_showShopHistory = new JButton("");
		btn_showShopHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btn_showShopHistory.setIcon(new ImageIcon(ClientGUI.class.getResource("/View/output-onlinepngtools (10).png")));
		btn_showShopHistory.setBorder(null);
		btn_showShopHistory.setBackground(new Color(255, 165, 0));
		btn_showShopHistory.setBounds(288, 10, 151, 125);
		panel.add(btn_showShopHistory);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 165, 0));
		tabbedPane.addTab("Online Order", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 197, 758, 212);
		panel_1.add(scrollPane);
		
		
		tableModelProd = new DefaultTableModel(dataProducts,headerProducts)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		
		table_products = new JTable(tableModelProd);
		table_products.setBackground(new Color(192, 192, 192));
		scrollPane.setViewportView(table_products);
		
		
		
		JComboBox select_category = new JComboBox();
		select_category.setFont(new Font("Tahoma", Font.BOLD, 16));
		select_category.setModel(new DefaultComboBoxModel(new String[] {"Bakery", "Beverage", "Nonfood & Pharmacy", "Meat", "Seafood"}));
		select_category.setBounds(559, 38, 161, 28);
		panel_1.add(select_category);
		
		fillUpPRoductArr(dataOrder, select_category.getSelectedItem().toString());
		
		JButton btn_ShowCategory = new JButton("Show");
		btn_ShowCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillUpPRoductArr(dataProducts, select_category.getSelectedItem().toString());
				deleteRows(tableModel);
				
				int rowCount = tableModel.getRowCount();
				//Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    tableModel.removeRow(i);
				}
				dataProducts = new String[Helper.countRowFromSql(cnn, "productdata")][3];
				fillUpPRoductArr(dataProducts, select_category.getSelectedItem().toString());
				tableModelProd = new DefaultTableModel(dataProducts,headerProducts)
				{
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				table_products = new JTable(tableModelProd);
				table_products.setBackground(new Color(192, 192, 192));
				scrollPane.setViewportView(table_products);
				
			}
		});
		btn_ShowCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_ShowCategory.setBounds(559, 76, 159, 28);
		panel_1.add(btn_ShowCategory);
		
		JLabel lbl_TotalBill_1 = new JLabel("Total : \r\n");
		lbl_TotalBill_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl_TotalBill_1.setBounds(265, 410, 248, 34);
		panel_1.add(lbl_TotalBill_1);
		
		JButton btn_AddOrder = new JButton("Add");
		btn_AddOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				lbl_TotalBill_1.setText("Total : "+totalBill);
				Connection con = cnn.connDB();
				Statement st;
				if(table_products.getSelectedRow() >=0)
				{
					try {
							st = con.createStatement();
							ResultSet rs = st.executeQuery("SELECT * FROM productdata");
							
							while(rs.next())
							{
								
								System.out.println(table_products.getSelectedRow());
								String prodid = (String) table_products.getValueAt((Integer) table_products.getSelectedRow(), 0);
								
								if(rs.getInt("ID")== Integer.parseInt(prodid))		
								{
									String a = JOptionPane.showInputDialog("Enter quantity");
									int quantity = a!=null ? Integer.parseInt(a) : 1;
									
									if(quantity>rs.getInt("quantity"))
									{
										String str = String.format("Product in warehouse : %d", rs.getInt("quantity"));
										Helper.showMsg("There is not enough product in the warehouse\n"+str);
										
									}else {
										
										dataOrder[rowOnline][colOnline] = Integer.toString(rs.getInt("ID"));
										dataOrder[rowOnline][colOnline+1] = rs.getString("Name");
										dataOrder[rowOnline][colOnline+2] = Double.toString(rs.getDouble("price"));
										dataOrder[rowOnline][colOnline+3] = Integer.toString(quantity);
										totalBill+=rs.getDouble("price")*quantity;
										rowOnline++;
									}
									
									break;
								}
							}
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					
					
					
				}
				else {
					Helper.showMsg("MissOp");
				}
				
				
				
			}
		});
		btn_AddOrder.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_AddOrder.setBounds(559, 114, 159, 28);
		panel_1.add(btn_AddOrder);
		
		
		
		JButton btn_userProf = new JButton("");
		btn_userProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserProfileGUI ug = new UserProfileGUI(client);
				ug.setVisible(true);
				dispose();
			}
		});
		btn_userProf.setIcon(new ImageIcon(ClientGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		btn_userProf.setBorder(null);
		btn_userProf.setBackground(new Color(255, 165, 0));
		btn_userProf.setBounds(83, 33, 151, 125);
		panel_1.add(btn_userProf);
		
		JButton btn_SaveOrder = new JButton("Get Order");
		btn_SaveOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_SaveOrder.setVisible(false);
				Connection con = cnn.connDB();
				Statement st;
				ResultSet rs;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
				Date now = new Date();
				String strdate = sdf.format(now);
				String location = "";
				String PhoneNumb = "";
				if(client.getLocation()!=null || !client.getLocation().equals(""))
				{
					int ques = JOptionPane.showConfirmDialog(null, "Do you want to use your profile location? ", "Choose Location", JOptionPane.YES_NO_OPTION);
					if(ques == JOptionPane.YES_OPTION)
					{
						location = client.getLocation();
					}else {
						location = JOptionPane.showInputDialog("Enter Location");
					}
				}else {
					location = JOptionPane.showInputDialog("Enter Location");
				}
				
				if(client.getPhoneNumber().length() != 0)
				{
					int ques = JOptionPane.showConfirmDialog(null, "Do you want to use Current phone Number?");
					if(ques == JOptionPane.YES_OPTION)
					{
						PhoneNumb = client.getPhoneNumber();
					}else {
						PhoneNumb = JOptionPane.showInputDialog("Enter phone Number");
					}
				}else {
					PhoneNumb = JOptionPane.showInputDialog("Enter phone Number");
				}
				
				
				int row = 0;
				try {
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM productdata");
					for(int i = 0;i<rowOnline;i++)
					{
						while(rs.next())
						{
							if(rs.getInt("ID") == Integer.parseInt(dataOrder[row][0]))
							{
								PreparedStatement pst = con.prepareStatement("UPDATE productdata SET quantity = ? WHERE ID = "+rs.getInt("ID"));
								pst.setInt(1, rs.getInt("quantity")-Integer.parseInt(dataOrder[row][3]));
								pst.executeUpdate();
								
								PreparedStatement pst1 = con.prepareStatement("INSERT INTO shopping_data (userid,clientName,shopDate,"
										+ "shopTime, shopPrice,shopBonus ,prodid,quantity) VALUES (?,?,?,?,?,?,?,?)");
								pst1.setInt(1, client.getID());
								pst1.setString(2, client.getName());
								pst1.setString(3, strdate.substring(0,10));
								pst1.setString(4, strdate.substring(10));
								pst1.setDouble(5, totalBill);
								pst1.setDouble(6, totalBill*0.02);
								pst1.setInt(7, rs.getInt("ID"));
								pst1.setInt(8, Integer.parseInt(dataOrder[row][3]));
								pst1.executeUpdate();
								
								
								PreparedStatement pst2 = con.prepareStatement("INSERT INTO deliever_order_data (userid,clientName,shop_date,"
										+ "shop_time, shopPrice,prodid,quantity,Location,phoneNumb) VALUES (?,?,?,?,?,?,?,?,?)");
								pst2.setInt(1, client.getID());
								pst2.setString(2, client.getName());
								pst2.setString(3, strdate.substring(0,10));
								pst2.setString(4, strdate.substring(10));
								pst2.setDouble(5, totalBill);
								pst2.setDouble(6, rs.getInt("ID"));
								pst2.setInt(7,Integer.parseInt(dataOrder[row][3]));
								pst2.setString(8,location );
								pst2.setString(9, PhoneNumb);
								pst2.executeUpdate();
								
								row++;
								break;
							}
						}
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btn_SaveOrder.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_SaveOrder.setBounds(559, 152, 159, 28);
		panel_1.add(btn_SaveOrder);
		
		btn_SaveOrder.setVisible(false);
		
		JButton btn_shopList = new JButton("");
		btn_shopList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				btn_SaveOrder.setVisible(true);
				
				table_products.setVisible(false);
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 197, 758, 212);
				panel_1.add(scrollPane);
				
				
				tableOnlineShopList = new DefaultTableModel(dataOrder,headerOrder)
				{
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				
				
				table_OnlineShopList = new JTable(tableOnlineShopList);
				table_OnlineShopList.setBackground(new Color(192, 192, 192));
				scrollPane.setViewportView(table_OnlineShopList);
		
				table_OnlineShopList.setVisible(true);
			}
		});
		btn_shopList.setIcon(new ImageIcon(ClientGUI.class.getResource("/View/output-onlinepngtools (9).png")));
		btn_shopList.setBorder(null);
		btn_shopList.setBackground(new Color(255, 165, 0));
		btn_shopList.setBounds(305, 22, 151, 125);
		panel_1.add(btn_shopList);
		
		JButton btn_ShowProductsTable = new JButton("Products");
		btn_ShowProductsTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_SaveOrder.setVisible(false);
				table_OnlineShopList.setVisible(false);
				//scrollPane.setVisible(false);
				//table_products.setVisible(true);
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 197, 758, 212);
				panel_1.add(scrollPane);
				
				
				tableModelProd = new DefaultTableModel(dataProducts,headerProducts)
				{
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				
				
				table_products = new JTable(tableModelProd);
				table_products.setBackground(new Color(192, 192, 192));
				scrollPane.setViewportView(table_products);
			}
		});
		btn_ShowProductsTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_ShowProductsTable.setBorder(null);
		btn_ShowProductsTable.setBackground(new Color(255, 165, 0));
		btn_ShowProductsTable.setBounds(305, 153, 151, 34);
		panel_1.add(btn_ShowProductsTable);
		
		
		
		
	}
	
	public void fillUpShopListData(String [][] arr,int id)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM shopping_data WHERE userid="+id);
			System.out.println(id);
			int row = 0,col = 0;
			
			while(rs.next())
			{
				int fnd = 0;
					//"ID","Shopping Date","Shopping hours","Total shopping price","Total shopping bonus"
				for(int i = 0;i<=row;i++)
				{
					if(rs.getString("shopDate").equals(arr[i][1]) && rs.getString("shopTime").equals(arr[i][2]))
					{
						fnd++;
					}
				}
				if(fnd==0)
				{
					arr[row][col] = Integer.toString(rs.getInt("ID"));
					arr[row][col+1] = rs.getString("shopDate");
					arr[row][col+2] = rs.getString("shopTime"); 
					arr[row][col+3] = rs.getString("shopPrice");
					arr[row][col+4] = rs.getString("shopBonus");
					
					row++;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void fillUpPRoductArr(String [][] arr,String strName)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM productdata ");
			
			int row = 0,col = 0;
			while(rs.next())
			{
				if(rs.getString("Category").equals(strName))
				{
					//"ID",name price
					arr[row][col] = Integer.toString(rs.getInt("ID"));
					arr[row][col+1] = rs.getString("Name");
					arr[row][col+2] = Double.toString(rs.getDouble("price")); 
					row++;
				}	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void fillUpShopHistoryArr(String [][] arr,String strdate,String strTime,int id)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM shopping_data ");
			
			int row = 0,col = 0;
			while(rs.next())
			{
				ResultSet rs1 = st.executeQuery("SELECT * FROM productdata WHERE ID = "+rs.getInt("prodid"));
				if(rs.getInt("userid")==id && rs.getString("shopDate").equals(strdate) && rs.getString("shopTime").equals(strTime) )
				{
					while(rs1.next())
					{
						
							double price = rs1.getDouble("price");
							int quantity = rs.getInt("quantity");
							
							arr[row][col] = Integer.toString(row+1);

							arr[row][col+1] = rs1.getString("Name");
							arr[row][col+2] = Double.toString(rs1.getDouble("price"));
							arr[row][col+3] = Integer.toString(quantity);
							arr[row][col+4] =  Double.toString( price * quantity);
							totalBill+=price * quantity;
							row++;
							
						
					}
					continue;
					
					
				}	
			}
			System.out.println(arr[0][0]);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void deleteRows(DefaultTableModel tableModel) {
		int rowCount = tableModel.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	
	public void updateTable(DefaultTableModel tableModel)
	{
		deleteRows(tableModel);
		
	}
}
