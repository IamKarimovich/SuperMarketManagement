package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Help.DBConnection;
import Help.Helper;
import Objects.Seller;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SellerGUI extends JFrame {
	private DBConnection cnn = new DBConnection();
	private JPanel contentPane;
	private JTable table_ShoppingList;
	private JTextField txF_ProdId;
	private JTextField txF_PRodQuantity;
	private JTextField txF_ProdName;
	private JTextField txF_Price;
	private String ShopHeader[] = {"No","Name","Quantity","Price","Total Price"};
	private String[][] ShopData = new String[Helper.countRowFromSql(cnn, "shop_list")][6];
	private double total_price = 0;
	private DefaultTableModel tableModel;
	private int prodid = 0;
	private static Seller seller;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerGUI frame = new SellerGUI(seller);
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
	public SellerGUI(Seller seller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 626);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(206, 10, 775, 250);
		panel.setBackground(new Color(192, 192, 192));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(206, 270, 775, 309);
		contentPane.add(scrollPane);
		
		JButton btn_Save = new JButton("Save");
		btn_Save.setVisible(false);
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(prodid != Integer.parseInt(txF_ProdId.getText()) || txF_ProdId.getText().length()==0 || txF_PRodQuantity.getText().length() == 0)
				{
					Helper.showMsg("Incorrect ID or Quantity");
				}else {
					Connection con = cnn.connDB();
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM shop_list");
						int fnd = 0;
						while(rs.next()) {
							if(prodid == rs.getInt("prodid"))
							{
								fnd++;
								break;
							}
						}
						PreparedStatement pSt = con.prepareStatement("UPDATE shop_list SET Quantity = ? , total_price = ? WHERE prodid = ?");
						pSt.setInt(1, Integer.parseInt(txF_PRodQuantity.getText()));
						pSt.setDouble(2, (rs.getDouble("price")*Integer.parseInt(txF_PRodQuantity.getText())));
						pSt.setInt(3, prodid);
						pSt.executeUpdate();
						btn_Save.setVisible(false);
						txF_Price.setText("");
						txF_ProdId.setText("");
						txF_ProdName.setText("");
						txF_PRodQuantity.setText("");
						
						ShopData = new String[Helper.countRowFromSql(cnn, "shop_list")][6];
						fillUpArray(ShopData);
						tableModel = new DefaultTableModel(ShopData,ShopHeader)
						{
						    @Override
						    public boolean isCellEditable(int row, int column) {
						        return false;
						    }
						};
						table_ShoppingList = new JTable(tableModel);
						table_ShoppingList.setBackground(new Color(192, 192, 192));
						scrollPane.setViewportView(table_ShoppingList);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_Save.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Save.setBounds(66, 163, 139, 37);
		panel.add(btn_Save);
		
		JLabel lbl_ProductID = new JLabel("Product ID : ");
		lbl_ProductID.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ProductID.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_ProductID.setBackground(new Color(255, 165, 0));
		lbl_ProductID.setBounds(10, 10, 175, 37);
		panel.add(lbl_ProductID);
		
		txF_ProdId = new JTextField();
		txF_ProdId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_ProdId.setColumns(10);
		txF_ProdId.setBounds(184, 12, 196, 37);
		panel.add(txF_ProdId);
		
		txF_PRodQuantity = new JTextField();
		txF_PRodQuantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_PRodQuantity.setColumns(10);
		txF_PRodQuantity.setBounds(545, 10, 196, 37);
		panel.add(txF_PRodQuantity);
		
		JLabel lbl_Quantitiy = new JLabel("Quantity : ");
		lbl_Quantitiy.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Quantitiy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Quantitiy.setBackground(new Color(255, 165, 0));
		lbl_Quantitiy.setBounds(386, 10, 154, 37);
		panel.add(lbl_Quantitiy);
		
		txF_ProdName = new JTextField();
		txF_ProdName.setEditable(false);
		txF_ProdName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_ProdName.setColumns(10);
		txF_ProdName.setBounds(184, 70, 196, 37);
		panel.add(txF_ProdName);
		
		JLabel lbl_productName = new JLabel("Product Name : ");
		lbl_productName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_productName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_productName.setBackground(new Color(255, 165, 0));
		lbl_productName.setBounds(14, 70, 175, 37);
		panel.add(lbl_productName);
		
		JLabel lbl_Price = new JLabel("Price : ");
		lbl_Price.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Price.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Price.setBackground(new Color(255, 165, 0));
		lbl_Price.setBounds(386, 70, 154, 37);
		panel.add(lbl_Price);
		
		
		fillUpArray(ShopData);
		tableModel = new DefaultTableModel(ShopData,ShopHeader)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table_ShoppingList = new JTable(tableModel);
		table_ShoppingList.setBackground(new Color(192, 192, 192));
		scrollPane.setViewportView(table_ShoppingList);
		
		txF_Price = new JTextField();
		txF_Price.setEditable(false);
		txF_Price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Price.setColumns(10);
		txF_Price.setBounds(545, 70, 196, 37);
		panel.add(txF_Price);
		
		JButton btn_Edit = new JButton("Edit");
		btn_Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table_ShoppingList.getSelectedRow()>=0)
				{
					btn_Save.setVisible(true);
					Connection con = cnn.connDB();
					Statement st;
					ResultSet rs;
					String strname = (String) table_ShoppingList.getValueAt(table_ShoppingList.getSelectedRow(), 1);
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM shop_list");
						while(rs.next())
						{
							if(rs.getString("name").equals(strname)) {
								prodid = rs.getInt("prodid");
								txF_ProdId.setText(Integer.toString(rs.getInt("prodid")));
								txF_PRodQuantity.setText(Integer.toString(rs.getInt("Quantity")));
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
		btn_Edit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Edit.setBounds(545, 140, 139, 37);
		panel.add(btn_Edit);
		
		JButton btn_AddShopList = new JButton("Add");
		btn_AddShopList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				Connection con = cnn.connDB();
				Statement st;
				ResultSet rs,rs1;
				int fnd = 0,fnd1=0;
				if(txF_PRodQuantity.getText().length()==0 )
				{
					txF_PRodQuantity.setText("1");
				}
				if(txF_ProdId.getText().length()==0)
				{
					Helper.showMsg("Enter Product ID!");
				}else {
					PreparedStatement pSt;
					try {
						int productid = Integer.parseInt(txF_ProdId.getText());
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM productdata");
						while(rs.next())
						{
							if(rs.getInt("ID") == productid && rs.getInt("quantity")>0)
							{
								fnd++;
								break;
							}
							
						}
						if(fnd==0)
						{
							Helper.showMsg("NotProdFound");
						}else {
						
							rs1 = st.executeQuery("SELECT * FROM shop_list");
							while(rs1.next())
							{
								if(rs1.getInt("prodid") == productid )
								{
									fnd1++;
									break;
								}
							}
							if(fnd1==0)
							{
								pSt = con.prepareStatement("INSERT INTO shop_list (name,prodid,Quantity,price,total_price) VALUES (?,?,?,?,?)");
								
															
								pSt.setString(1, rs.getString("Name"));
								pSt.setInt(2, productid);
								pSt.setDouble(3, Double.parseDouble(txF_PRodQuantity.getText()));
								pSt.setDouble(4, rs.getDouble("price"));
								total_price = rs.getDouble("price") * Double.parseDouble(txF_PRodQuantity.getText());
								pSt.setDouble(5, total_price);
								pSt.executeUpdate();
							}else {
								
								pSt = con.prepareStatement("UPDATE shop_list SET prodid = ?,Quantity = ? ,total_price = ? WHERE prodid = ?");
								pSt.setInt(1, productid);
								pSt.setInt(2, rs1.getInt("Quantity")+ Integer.parseInt(txF_PRodQuantity.getText()));
								total_price = rs1.getDouble("total_price")+(rs.getDouble("price") * Integer.parseInt(txF_PRodQuantity.getText()));
								pSt.setDouble(3, total_price);
								pSt.setInt(4, productid);
								pSt.executeUpdate();
							}
							int rowCount = tableModel.getRowCount();
							//Remove rows one by one from the end of the table
							for (int i = rowCount - 1; i >= 0; i--) {
							    tableModel.removeRow(i);
							}
							ShopData = new String[Helper.countRowFromSql(cnn, "shop_list")][6];
							fillUpArray(ShopData);
							tableModel = new DefaultTableModel(ShopData,ShopHeader)
							{
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false;
							    }
							};
							table_ShoppingList = new JTable(tableModel);
							table_ShoppingList.setBackground(new Color(192, 192, 192));
							scrollPane.setViewportView(table_ShoppingList);
							
							txF_ProdName.setText("");
							txF_Price.setText("");
							txF_ProdId.setText("");
							txF_PRodQuantity.setText("");
							
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		btn_AddShopList.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_AddShopList.setBounds(396, 140, 139, 37);
		panel.add(btn_AddShopList);
		
		JButton btn_Clear = new JButton("Clear");
		btn_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txF_Price.setText("");
				txF_ProdId.setText("");
				txF_ProdName.setText("");
				txF_PRodQuantity.setText("");
				
			}
		});
		btn_Clear.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Clear.setBounds(545, 192, 139, 37);
		panel.add(btn_Clear);
		
		JButton btn_Delete = new JButton("Delete");
		btn_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table_ShoppingList.getSelectedRow()>=0)
				{
					Connection con = cnn.connDB();
					try {
						
							PreparedStatement pSt = con.prepareStatement("DELETE FROM shop_list WHERE name = ?");
							pSt.setString(1,(String) table_ShoppingList.getValueAt(table_ShoppingList.getSelectedRow(), 1));
							pSt.executeUpdate();
							txF_Price.setText("");
							txF_ProdId.setText("");
							txF_ProdName.setText("");
							txF_PRodQuantity.setText("");
							
							ShopData = new String[Helper.countRowFromSql(cnn, "shop_list")][6];
							fillUpArray(ShopData);
							tableModel = new DefaultTableModel(ShopData,ShopHeader)
							{
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false;
							    }
							};
							table_ShoppingList = new JTable(tableModel);
							table_ShoppingList.setBackground(new Color(192, 192, 192));
							scrollPane.setViewportView(table_ShoppingList);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else {
					Helper.showMsg("MissOp");
				}
			}
		});
		btn_Delete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Delete.setBounds(396, 192, 139, 37);
		panel.add(btn_Delete);
		
		JButton btn_Show = new JButton("Show");
		btn_Show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = cnn.connDB();
				Statement st;
				ResultSet rs;
				int id=0,fnd = 0;
				
				String strPrice;
				if(txF_ProdId.getText().length()==0)
				{
					Helper.showMsg("Enter product ID!");
				}else {
					id = Integer.parseInt((String) txF_ProdId.getText());
					if(txF_PRodQuantity.getText().length() == 0)
					{
						txF_PRodQuantity.setText("1");
					}
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM productdata");
						while(rs.next())
						{
							if(rs.getInt("ID")==id)
							{
								fnd++;
								break;
							}
						}
						if(fnd==1)
						{
							strPrice = Double.toString(rs.getDouble("price"));
							txF_Price.setText(strPrice);
							txF_ProdName.setText(rs.getString("Name"));
							total_price = rs.getDouble("price") * Double.parseDouble(txF_PRodQuantity.getText());
							
							
									
						}else {
							Helper.showMsg("Enter correct ID");
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					
					
					
				}
				
			}
		});
		btn_Show.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Show.setBounds(247, 140, 139, 37);
		panel.add(btn_Show);
		
		JButton btn_Print = new JButton("Print");
		btn_Print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				if(Helper.countRowFromSql(cnn, "shop_list")!=1)
				{
				Connection con = cnn.connDB();
				Statement st;
				ResultSet rs,rs1,rs2;
				double total = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
				Date now = new Date();
				String strdate = sdf.format(now);
				String userid = "" ;
				String name = null;
				
				try {
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM workerdata WHERE userid ="+seller.getID());
					int selAc = 2;
					while(rs.next())
					{
							selAc = rs.getInt("acceptedClient");
							break;
					}
					PreparedStatement pst = con.prepareStatement("UPDATE workerdata SET acceptedClient=? WHERE userid = "+seller.getID());
					pst.setInt(1, selAc+1);
					pst.executeUpdate();
					seller.setWorkCount(seller.getWorkCount()+1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				File fileCheck = new File("C:\\Users\\User\\Desktop\\Text.txt");
				try {
					FileWriter writer = new FileWriter(fileCheck);
					
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM shop_list");
					writer.write("*******************************************************************\n");
					writer.write("************************Check**************************************\n");
					writer.write("*******************************************************************\n");
					writer.write("--No---------Name---------Quantity--------Price-----Total Price---\n");
					int row = 1;
					while(rs.next())
					{
						rs1 = st.executeQuery("SELECT * FROM productdata");
						while(rs1.next())
						{
							if(rs1.getInt("ID")==rs.getInt("prodid"))
							{
								PreparedStatement pSt = con.prepareStatement("UPDATE productdata SET quantity=? WHERE ID = "+rs.getInt("prodid"));
								int last_quant = rs1.getInt("quantity");
								pSt.setInt(1, last_quant - rs.getInt("Quantity"));
								pSt.executeUpdate();
								
								break;
							}
							
						}
						
						
						String check = String.format("  %-4d    %-20s %-8d    %-10.2f   %-10.2f\n", row,rs.getString("name"),rs.getInt("Quantity"),rs.getDouble("price"),rs.getDouble("total_price"));
						writer.write(check);
						total += rs.getDouble("price");
						row++;
					}
					String str2 = String.format("               Total Price : %.2f", total);
					writer.write("******************************************************************\n");
					writer.write(str2+"\n");
					writer.write("******************************************************************\n");
					
					
					int answer = JOptionPane.showConfirmDialog(null, "Is Customer Our User?", null, JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION)
					{
						int fnd = 0;
						userid = JOptionPane.showInputDialog("Enter user ID");
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM clientdata");
						while(rs.next())
						{
							if(rs.getInt("userid") == Integer.parseInt(userid))
							{
								fnd++;
								break;
							}
						}
						if(fnd != 0)
						{
							String str4 = String.format("USER : %s\t\tBonus : %.2f",rs.getString("Name"),rs.getDouble("Bonus"));
							writer.write(str4+"\n");
							PreparedStatement pSt = con.prepareStatement("UPDATE clientdata SET Bonus = ? WHERE userid =?");
							double newBonus = (total*0.02)+rs.getDouble("Bonus");
							pSt.setDouble(1,newBonus );
							pSt.setInt(2, Integer.parseInt(userid));
							pSt.executeUpdate();
							
							
							rs = st.executeQuery("SELECT * FROM clientdata");
							while(rs.next())
							{
								if(rs.getInt("userid") == Integer.parseInt(userid))
								{
									name=rs.getString("Name");
									break;
								}
							}
							String str3 = String.format("Total Bonus : %.2f", rs.getDouble("Bonus"));
							writer.write(str3+"\n");
							writer.write("**********Thanks For EveryThing*****************************\n");
							writer.write("************************************************************\n");
						}else {
							Helper.showMsg("Incorrect ID!");
							writer.write("**********Thanks For EveryThing*****************************\n");
							writer.write("************************************************************\n");
						}
					}else {
						writer.write("**********Thanks For EveryThing***********************************\n");
						writer.write("******************************************************************\n");
					}
					writer.write("Date : "+strdate);
					
					int intuserid = userid =="" ? 0 : Integer.parseInt(userid);
					
					ResultSet rsprod = st.executeQuery("SELECT * FROM shop_list");
					
					while(rsprod.next())
					{
						PreparedStatement pst1 = con.prepareStatement("INSERT INTO shopping_data (userid,clientName,shopDate,shopTime,shopPrice,shopBonus,prodid,quantity) "
								+ "VALUES (?,?,?,?,?,?,?,?)");
						pst1.setInt(1, intuserid);
						pst1.setString(2, name);
						pst1.setString(3, strdate.substring(0, 10));
						pst1.setString(4, strdate.substring(10));
						pst1.setDouble(5, total);
						pst1.setDouble(6, (total*0.02));
						pst1.setInt(7, rsprod.getInt("prodid"));
						pst1.setInt(8, rsprod.getInt("Quantity"));
						pst1.executeUpdate();
					}
					
					PreparedStatement pst = con.prepareStatement("DELETE FROM shop_list");
					pst.executeUpdate();			
					
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				ShopData = new String[Helper.countRowFromSql(cnn, "shop_list")][6];
				fillUpArray(ShopData);
				tableModel = new DefaultTableModel(ShopData,ShopHeader)
				{
				    @Override
				    public boolean isCellEditable(int row, int column) {
				        return false;
				    }
				};
				table_ShoppingList = new JTable(tableModel);
				table_ShoppingList.setBackground(new Color(192, 192, 192));
				scrollPane.setViewportView(table_ShoppingList);
				
				txF_ProdName.setText("");
				txF_Price.setText("");
				txF_ProdId.setText("");
				txF_PRodQuantity.setText("");
				
				
				
				
				}else {
					Helper.showMsg("Shopping list is empty!");
				}
				
				
				
				
			}
		});
		btn_Print.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Print.setBounds(247, 192, 139, 37);
		panel.add(btn_Print);
		
		JLabel lbl_ProfLogo = new JLabel("");
		lbl_ProfLogo.setBounds(23, 35, 153, 115);
		lbl_ProfLogo.setIcon(new ImageIcon(SellerGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lbl_ProfLogo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lbl_ProfLogo);
		
		JButton btn_AdminProf = new JButton(seller.getName());
		btn_AdminProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerProfileGUI sg = new SellerProfileGUI(seller);
				sg.setVisible(true);
				dispose();
			}
		});
		btn_AdminProf.setBounds(10, 160, 186, 50);
		btn_AdminProf.setForeground(Color.BLACK);
		btn_AdminProf.setFont(new Font("Myanmar Text", Font.BOLD, 20));
		btn_AdminProf.setBorder(null);
		btn_AdminProf.setBackground(new Color(255, 165, 0));
		contentPane.add(btn_AdminProf);
		
		JButton btn_ManageShopping = new JButton("Manage Shopping");
		btn_ManageShopping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SellerGUI sg = new SellerGUI(seller);
				sg.setVisible(true);
				dispose();
				
			}
		});
		btn_ManageShopping.setBounds(10, 244, 186, 50);
		btn_ManageShopping.setForeground(Color.BLACK);
		btn_ManageShopping.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageShopping.setBorder(null);
		btn_ManageShopping.setBackground(new Color(255, 165, 0));
		contentPane.add(btn_ManageShopping);
		
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
		btn_SellerLogOut.setBounds(10, 519, 186, 50);
		btn_SellerLogOut.setForeground(Color.BLACK);
		btn_SellerLogOut.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		btn_SellerLogOut.setBorder(null);
		btn_SellerLogOut.setBackground(new Color(255, 165, 0));
		contentPane.add(btn_SellerLogOut);
		
	}

	public void fillUpArray(String[][] arr)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM shop_list");
			int row = 0;
			while(rs.next())
			{
				//"No","Name","Quantity","Price","Total Price"
				for(int i = 0; i< arr[0].length;i++)
				{
					int k = 0;
					arr[row][k] = Integer.toString(row+1);
					arr[row][k+1] = rs.getString("name");
					arr[row][k+2] = rs.getString("Quantity");
					arr[row][k+3] = Double.toString(rs.getDouble("price"));
					arr[row][k+4] = Double.toString(rs.getDouble("total_price"));
					
				}
				row++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
