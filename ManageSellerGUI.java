package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Help.DBConnection;
import Help.Helper;
import Objects.Admin;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class ManageSellerGUI extends JFrame {

	private JPanel contentPane;
	private JTable table_Seller;
	DBConnection cnn = new DBConnection();
	private String sellerHeader[] = {"ID","Name","Accepted client","Salary","Start date","Phone Number","Address"};
	private String[][] dataSeller = new String[Helper.countRowFromSql(cnn, "workerdata")][7];
	
	private SimpleDateFormat formatter;
	private JTextField txF_Name;
	private JTextField txF_LoginId;
	private JTextField txF_Location;
	private JTextField txF_PhoneNumber;
	private JTextField txF_Password;
	private JTextField txF_Salary;
	private DefaultTableModel tableModel;
	private static Admin admin;
	private int userid = 0;
	private JTextField txF_name;
	private JTextField txF_Log;
	private JTextField txF_Pas;
	private JTextField txF_Sal;
	private JTextField txF_AcClie;
	private JTextField txF_TotSal;
	private double totalSalary = 0,salary = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageSellerGUI frame = new ManageSellerGUI(admin);
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
	public ManageSellerGUI(Admin admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 626);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_adminProf = new JButton(admin.getName());
		btn_adminProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminProfileGUI aG  = new AdminProfileGUI(admin);
				aG.setVisible(true);
				dispose();
			}
		});
		btn_adminProf.setForeground(Color.BLACK);
		btn_adminProf.setFont(new Font("Myanmar Text", Font.BOLD, 20));
		btn_adminProf.setBorder(null);
		btn_adminProf.setBackground(new Color(255, 165, 0));
		btn_adminProf.setBounds(10, 146, 186, 50);
		contentPane.add(btn_adminProf);
		
		JButton btn_ManageProducts = new JButton("Manage Products");
		btn_ManageProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI ag = new AdminGUI(admin);
				ag.setVisible(true);
				dispose();
			}
		});
		btn_ManageProducts.setForeground(Color.BLACK);
		btn_ManageProducts.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageProducts.setBorder(null);
		btn_ManageProducts.setBackground(new Color(255, 165, 0));
		btn_ManageProducts.setBounds(10, 230, 186, 50);
		contentPane.add(btn_ManageProducts);
		
		JButton btn_ManageSeller = new JButton("Manage Sellers");
		btn_ManageSeller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageSellerGUI ms = new ManageSellerGUI(admin);
				ms.setVisible(true);
				dispose();
			}
		});
		btn_ManageSeller.setForeground(Color.BLACK);
		btn_ManageSeller.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageSeller.setBorder(null);
		btn_ManageSeller.setBackground(new Color(255, 165, 0));
		btn_ManageSeller.setBounds(10, 290, 186, 50);
		contentPane.add(btn_ManageSeller);
		
		JButton btn_ManageDeliever = new JButton("Manage Delievers");
		btn_ManageDeliever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageDelieverGUI md = new ManageDelieverGUI(admin);
				md.setVisible(true);
				dispose();
			}
		});
		btn_ManageDeliever.setForeground(Color.BLACK);
		btn_ManageDeliever.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageDeliever.setBorder(null);
		btn_ManageDeliever.setBackground(new Color(255, 165, 0));
		btn_ManageDeliever.setBounds(10, 350, 186, 50);
		contentPane.add(btn_ManageDeliever);
		
		JButton btn_AdminLogOut = new JButton("Log-out");
		btn_AdminLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(Helper.askQuestion("sureLogOut")==1)
				{
					LoginGUI lG = new LoginGUI();
					lG.setVisible(true);
					dispose();
				}
			}
		});
		btn_AdminLogOut.setForeground(Color.BLACK);
		btn_AdminLogOut.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		btn_AdminLogOut.setBorder(null);
		btn_AdminLogOut.setBackground(new Color(255, 165, 0));
		btn_AdminLogOut.setBounds(10, 505, 186, 50);
		contentPane.add(btn_AdminLogOut);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(192, 192, 192));
		tabbedPane.setBounds(206, 10, 775, 234);
		contentPane.add(tabbedPane);
		
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("(###)-###-####");
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(206, 254, 775, 325);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 755, 305);
		panel.add(scrollPane);
		
		fillUpArray(dataSeller);
		
		tableModel = new DefaultTableModel(dataSeller,sellerHeader)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};	
		
		table_Seller = new JTable(tableModel);
		table_Seller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_Seller);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Manage Seller", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lbl_SellerName = new JLabel("Name : ");
		lbl_SellerName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_SellerName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_SellerName.setBackground(new Color(255, 165, 0));
		lbl_SellerName.setBounds(10, 10, 175, 37);
		panel_1.add(lbl_SellerName);
		
		txF_Name = new JTextField();
		txF_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Name.setColumns(10);
		txF_Name.setBounds(180, 10, 196, 37);
		panel_1.add(txF_Name);
		
		JLabel lbl_LoginId = new JLabel("Login ID : ");
		lbl_LoginId.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_LoginId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_LoginId.setBackground(new Color(255, 165, 0));
		lbl_LoginId.setBounds(10, 57, 175, 37);
		panel_1.add(lbl_LoginId);
		
		txF_LoginId = new JTextField();
		txF_LoginId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_LoginId.setColumns(10);
		txF_LoginId.setBounds(180, 57, 196, 37);
		panel_1.add(txF_LoginId);
		
		JLabel lbl_AcceptedClient_1 = new JLabel("Address :");
		lbl_AcceptedClient_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_AcceptedClient_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_AcceptedClient_1.setBackground(new Color(255, 165, 0));
		lbl_AcceptedClient_1.setBounds(10, 104, 175, 37);
		panel_1.add(lbl_AcceptedClient_1);
		
		txF_Location = new JTextField();
		txF_Location.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Location.setColumns(10);
		txF_Location.setBounds(180, 104, 196, 37);
		panel_1.add(txF_Location);
		
		JLabel lbl_AcceptedClient_1_1 = new JLabel("Phone Number : ");
		lbl_AcceptedClient_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_AcceptedClient_1_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_AcceptedClient_1_1.setBackground(new Color(255, 165, 0));
		lbl_AcceptedClient_1_1.setBounds(371, 104, 175, 37);
		panel_1.add(lbl_AcceptedClient_1_1);
		
		
		txF_PhoneNumber = new JFormattedTextField(mask);
		txF_PhoneNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txF_PhoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_PhoneNumber.setColumns(10);
		txF_PhoneNumber.setBounds(541, 104, 196, 37);
		panel_1.add(txF_PhoneNumber);
		
		txF_Password = new JTextField();
		txF_Password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Password.setColumns(10);
		txF_Password.setBounds(541, 10, 196, 37);
		panel_1.add(txF_Password);
		
		txF_Salary = new JTextField();
		txF_Salary.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Salary.setColumns(10);
		txF_Salary.setBounds(541, 57, 196, 37);
		panel_1.add(txF_Salary);
		
		JLabel lbl_Salary = new JLabel("Salary : ");
		lbl_Salary.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Salary.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Salary.setBackground(new Color(255, 165, 0));
		lbl_Salary.setBounds(382, 57, 154, 37);
		panel_1.add(lbl_Salary);
		
		JLabel lbl_Password = new JLabel("Password : ");
		lbl_Password.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Password.setBackground(new Color(255, 165, 0));
		lbl_Password.setBounds(382, 10, 154, 37);
		panel_1.add(lbl_Password);
		
		JButton btn_Add = new JButton("Add");
		btn_Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con = cnn.connDB();
				Statement st;
				ResultSet rs;
				int fnd = 0;
				if(txF_Name.getText().length() == 0 || txF_LoginId.getText().length() == 0 || txF_Password.getText().length() == 0 || 
						txF_Salary.getText().length() == 0 || txF_PhoneNumber.getText().length() == 0 || txF_Location.getText().length() == 0) {
					
					Helper.showMsg("fillUp");
					
				}else {
					try {
						st = con.createStatement();
						rs = st.executeQuery("SELECT * FROM userdata");
						
						while(rs.next())
						{
							if(rs.getString("loginId").equals(txF_LoginId.getText()))
							{
								fnd++;
							}
						}
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(fnd == 0)
					{
						try {
							PreparedStatement pSt = con.prepareStatement("INSERT INTO userdata (Name,type,loginId,password,phone_number,Location) VALUES (?,?,?,?,?,?)");
							pSt.setString(1, txF_Name.getText());
							pSt.setString(2, "Seller");
							pSt.setString(3, txF_LoginId.getText());
							pSt.setString(4, txF_Password.getText());
							pSt.setString(5, txF_PhoneNumber.getText());
							pSt.setString(6, txF_Location.getText());
							pSt.executeUpdate();
							
							st = con.createStatement();
							rs = st.executeQuery("SELECT * FROM userdata");
							int userid = 0;
							while(rs.next())
							{
								if(rs.getString("loginId").equals(txF_LoginId.getText()))
								{
									userid = rs.getInt("ID");
									break;
								}
							}
							
							PreparedStatement pSt2 = con.prepareStatement("INSERT INTO workerdata (userid,Name,type,StartDate,Salary,phoneNumber,location) VALUES (?,?,?,?,?,?,?)");
							
							pSt2.setInt(1, userid);
							pSt2.setString(2, txF_Name.getText());
							pSt2.setString(3, "Seller");
							
							formatter = new SimpleDateFormat("dd-MM-yyyy");  
							Date date = new Date();  
							System.out.println(formatter.format(date));
							
							pSt2.setString(4, formatter.format(date));
							pSt2.setDouble(5, Double.parseDouble(txF_Salary.getText()));
							pSt2.setString(6, txF_PhoneNumber.getText());
							pSt2.setString(7, txF_Location.getText());
							pSt2.executeUpdate();
							
							Helper.showMsg("succesAdd");
							
							int rowCount = tableModel.getRowCount();
							//Remove rows one by one from the end of the table
							for (int i = rowCount - 1; i >= 0; i--) {
							    tableModel.removeRow(i);
							}
							dataSeller = new String[Helper.countRowFromSql(cnn, "workerdata")][7];
							fillUpArray(dataSeller);
							tableModel = new DefaultTableModel(dataSeller,sellerHeader)
							{
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false;
							    }
							};			
							table_Seller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							table_Seller = new JTable(tableModel);
							scrollPane.setViewportView(table_Seller);
							table_Seller.setBackground(new Color(192, 192, 192));
							
							txF_Name.setText("");
							txF_LoginId.setText("");
							txF_Location.setText("");
							txF_Password.setText("");
							txF_PhoneNumber.setText("");
							txF_Salary.setText("");
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						Helper.showMsg("ExistLogId");
					}
	
				}
				
				
				
				
				
				
				
			}
		});
		btn_Add.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Add.setBounds(487, 160, 120, 37);
		panel_1.add(btn_Add);
		
		JButton btn_Clear = new JButton("Clear");
		btn_Clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txF_Name.setText("");
				txF_LoginId.setText("");
				txF_Location.setText("");
				txF_Password.setText("");
				txF_PhoneNumber.setText("");
				txF_Salary.setText("");
			}
		});
		btn_Clear.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_Clear.setBounds(617, 160, 120, 37);
		panel_1.add(btn_Clear);
		
		JButton btn_change = new JButton("Save");
		btn_change.setVisible(false);
		btn_change.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txF_LoginId.setEditable(false);
				txF_Password.setEditable(false);
				
			if(table_Seller.getSelectedRow()>=0)
			{
				if(Helper.askQuestion("sureEdit")==1)
				{
					Connection con;
					PreparedStatement pSt;
					Statement st;
					ResultSet rs;
					
					con = cnn.connDB();
					
						if(txF_Name.getText().length() == 0 || txF_LoginId.getText().length() == 0 || txF_Password.getText().length() == 0 || 
								txF_Salary.getText().length() == 0 || txF_PhoneNumber.getText().length() == 0 || txF_Location.getText().length() == 0 || txF_PhoneNumber.getText().length()<10) {
							
							Helper.showMsg("fillUp");
							
						}else {
							try {
								
									pSt = con.prepareStatement("UPDATE userdata SET Name=?,phone_number=?,Location=? WHERE ID="+table_Seller.getValueAt(table_Seller.getSelectedRow(),0));
									pSt.setString(1, txF_Name.getText());
									pSt.setString(2, txF_PhoneNumber.getText());
									pSt.setString(3, txF_Location.getText());
									pSt.executeUpdate();
									
									pSt = con.prepareStatement("UPDATE workerdata SET Name=?,Salary=?,phoneNumber=?,location=? WHERE userid="
									+table_Seller.getValueAt(table_Seller.getSelectedRow(),0));
									
									pSt.setString(1, txF_Name.getText());
									pSt.setString(2, txF_Salary.getText());
									pSt.setString(3, txF_PhoneNumber.getText());
									pSt.setString(4, txF_Location.getText());
									
									pSt.executeUpdate();
									
									btn_change.setVisible(false);
									txF_LoginId.setEditable(true);
									txF_Password.setEditable(true);
									txF_Name.setText("");
									txF_LoginId.setText("");
									txF_Location.setText("");
									txF_Password.setText("");
									txF_PhoneNumber.setText("");
									txF_Salary.setText("");
									
									int rowCount = tableModel.getRowCount();
									//Remove rows one by one from the end of the table
									for (int i = rowCount - 1; i >= 0; i--) {
									    tableModel.removeRow(i);
									}
									dataSeller = new String[Helper.countRowFromSql(cnn, "workerdata")][7];
									fillUpArray(dataSeller);
									tableModel = new DefaultTableModel(dataSeller,sellerHeader)
									{
									    @Override
									    public boolean isCellEditable(int row, int column) {
									        return false;
									    }
									};			
									table_Seller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
									table_Seller = new JTable(tableModel);
									scrollPane.setViewportView(table_Seller);
									table_Seller.setBackground(new Color(192, 192, 192));
									
									Helper.showMsg("successUpdate");
									
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
				}
				
			}else {
				Helper.showMsg("MissOp");
			}
			}
		});
		btn_change.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_change.setBounds(39, 160, 120, 37);
		panel_1.add(btn_change);
		
		JButton btn_edit = new JButton("Edit");
		btn_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txF_LoginId.setEditable(false);
				txF_Password.setEditable(false);
				
				//"ID","Name","Accepted client","Salary","Start date","Phone Number","Address"
				Connection con;
				Statement st;
				System.out.print(table_Seller.getSelectedRow());
				//System.out.print(table_Seller.getValueAt(table_Seller.getSelectedRow(), 0));
				
				if(table_Seller.getSelectedRow()>=0)
				{
					userid = Integer.parseInt((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 0));
					System.out.println(userid);
					con = cnn.connDB();
					try {
						st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						
						while(rs.next())
						{
							if(rs.getInt("ID") == userid)
							{

								txF_Name.setText(rs.getString("Name"));
								txF_LoginId.setText(rs.getString("loginId"));
								txF_Password.setText(rs.getString("password"));
								txF_PhoneNumber.setText(rs.getString("phone_number"));
								txF_Location.setText(rs.getString("Location"));
								txF_Salary.setText((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 3));
								break;
							}
						}
						
						
						btn_change.setVisible(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
					
						e1.printStackTrace();
					}
					
				}else{
					Helper.showMsg("MissOp");
				}
				
				
				
				
			}
		});
		btn_edit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_edit.setBounds(357, 160, 120, 37);
		panel_1.add(btn_edit);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(209, 170, 120, -9);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table_Seller.getSelectedRow()>=0)
				{
					if(Helper.askQuestion("sureDelete")==1)
					{
						String confirminput = JOptionPane.showInputDialog("Enter password for confirm.");
						if(confirminput.equals(admin.getPassword()))
						{
							Connection con;
							PreparedStatement pSt;
							Statement st;
							ResultSet rs;
							
							con = cnn.connDB();
							
									try {
										
											pSt = con.prepareStatement("DELETE FROM userdata WHERE ID="+table_Seller.getValueAt(table_Seller.getSelectedRow(),0));
											pSt.executeUpdate();
											
											pSt = con.prepareStatement("DELETE FROM workerdata WHERE userid="+table_Seller.getValueAt(table_Seller.getSelectedRow(),0));
											
											pSt.executeUpdate();
											
											txF_Name.setText("");
											txF_LoginId.setText("");
											txF_Location.setText("");
											txF_Password.setText("");
											txF_PhoneNumber.setText("");
											txF_Salary.setText("");
											
											int rowCount = tableModel.getRowCount();
											//Remove rows one by one from the end of the table
											for (int i = rowCount - 1; i >= 0; i--) {
											    tableModel.removeRow(i);
											}
											dataSeller = new String[Helper.countRowFromSql(cnn, "workerdata")][7];
											fillUpArray(dataSeller);
											tableModel = new DefaultTableModel(dataSeller,sellerHeader)
											{
											    @Override
											    public boolean isCellEditable(int row, int column) {
											        return false;
											    }
											};			
											table_Seller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
											table_Seller = new JTable(tableModel);
											scrollPane.setViewportView(table_Seller);
											table_Seller.setBackground(new Color(192, 192, 192));
											
											Helper.showMsg("successDelete");
											
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
								}else {
									Helper.showMsg("Enter correct Password!");
								}
						
					}
				}else {
					Helper.showMsg("MissOp");
				}
				
		}
			
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton_1.setBounds(227, 160, 120, 37);
		panel_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(192, 192, 192));
		tabbedPane.addTab("Salary Informations", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lbl_Name = new JLabel("Name : ");
		lbl_Name.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Name.setBounds(10, 10, 152, 37);
		panel_2.add(lbl_Name);
		
		txF_name = new JTextField();
		txF_name.setHorizontalAlignment(SwingConstants.CENTER);
		txF_name.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_name.setEditable(false);
		txF_name.setBounds(180, 10, 183, 37);
		panel_2.add(txF_name);
		txF_name.setColumns(10);
		
		JLabel lbl_Log = new JLabel("Login ID : ");
		lbl_Log.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Log.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Log.setBounds(10, 57, 152, 37);
		panel_2.add(lbl_Log);
		
		txF_Log = new JTextField();
		txF_Log.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Log.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_Log.setEditable(false);
		txF_Log.setColumns(10);
		txF_Log.setBounds(180, 57, 183, 37);
		panel_2.add(txF_Log);
		
		JLabel lbl_Pas = new JLabel("Password : ");
		lbl_Pas.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Pas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Pas.setBounds(10, 104, 152, 37);
		panel_2.add(lbl_Pas);
		
		txF_Pas = new JTextField();
		txF_Pas.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Pas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_Pas.setEditable(false);
		txF_Pas.setColumns(10);
		txF_Pas.setBounds(180, 104, 183, 37);
		panel_2.add(txF_Pas);
		
		JLabel lbl_sal = new JLabel("Salary : ");
		lbl_sal.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_sal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_sal.setBounds(390, 10, 152, 37);
		panel_2.add(lbl_sal);
		
		txF_Sal = new JTextField();
		txF_Sal.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Sal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_Sal.setEditable(false);
		txF_Sal.setColumns(10);
		txF_Sal.setBounds(560, 10, 183, 37);
		panel_2.add(txF_Sal);
		
		JLabel lbl_acClie = new JLabel("Accepted client  : ");
		lbl_acClie.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_acClie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_acClie.setBounds(373, 57, 169, 37);
		panel_2.add(lbl_acClie);
		
		txF_AcClie = new JTextField();
		txF_AcClie.setHorizontalAlignment(SwingConstants.CENTER);
		txF_AcClie.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_AcClie.setEditable(false);
		txF_AcClie.setColumns(10);
		txF_AcClie.setBounds(560, 57, 183, 37);
		panel_2.add(txF_AcClie);
		
		JLabel lbl_TotalSal = new JLabel("Total Salary :");
		lbl_TotalSal.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_TotalSal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_TotalSal.setBounds(390, 104, 152, 37);
		panel_2.add(lbl_TotalSal);
		
		txF_TotSal = new JTextField();
		txF_TotSal.setHorizontalAlignment(SwingConstants.CENTER);
		txF_TotSal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txF_TotSal.setEditable(false);
		txF_TotSal.setColumns(10);
		txF_TotSal.setBounds(560, 104, 183, 37);
		panel_2.add(txF_TotSal);
		
		JButton btn_RestoreSalary = new JButton("Restore Informations");
		btn_RestoreSalary.setVisible(false);
		btn_RestoreSalary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Helper.askQuestion("Did you pay Salary to Seller?")==1)
				{
					Connection con = cnn.connDB();
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						while(rs.next())
						{
							if(txF_Log.getText().equals(rs.getString("loginId")))
							{
								userid = rs.getInt("ID");
								break;
							}
						}
						PreparedStatement pSt = con.prepareStatement("UPDATE workerdata SET acceptedClient=0");
						pSt.executeUpdate();
						txF_AcClie.setText("0");
						totalSalary = Double.parseDouble(txF_Sal.getText()) + Integer.parseInt(txF_AcClie.getText());
						txF_TotSal.setText(Double.toString(totalSalary));
						btn_RestoreSalary.setVisible(false);
						

						int rowCount = tableModel.getRowCount();
						//Remove rows one by one from the end of the table
						for (int i = rowCount - 1; i >= 0; i--) {
						    tableModel.removeRow(i);
						}
						dataSeller = new String[Helper.countRowFromSql(cnn, "workerdata")][7];
						fillUpArray(dataSeller);
						tableModel = new DefaultTableModel(dataSeller,sellerHeader)
						{
						    @Override
						    public boolean isCellEditable(int row, int column) {
						        return false;
						    }
						};			
						table_Seller.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						table_Seller = new JTable(tableModel);
						scrollPane.setViewportView(table_Seller);
						table_Seller.setBackground(new Color(192, 192, 192));
						
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		btn_RestoreSalary.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_RestoreSalary.setBackground(new Color(230, 230, 250));
		btn_RestoreSalary.setBounds(153, 154, 235, 43);
		panel_2.add(btn_RestoreSalary);
		
		JButton btn_ShowInfo = new JButton("Show Informations");
		btn_ShowInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(table_Seller.getSelectedRow()>=0)
				{
					btn_RestoreSalary.setVisible(true);
					userid = Integer.parseInt((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 0));
					System.out.println(userid);
					//{"ID","Name","Accepted client","Salary","Start date","Phone Number","Address"};
					
					txF_name.setText((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 1));
					Connection con = cnn.connDB();
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						while(rs.next())
						{
							if(rs.getInt("ID")==userid )
							{
								txF_Log.setText(rs.getString("loginId"));
								txF_Pas.setText(rs.getString("password"));
								break;
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txF_Sal.setText((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 3));
					txF_AcClie.setText((String) table_Seller.getValueAt(table_Seller.getSelectedRow(), 2));
					
					salary = Double.parseDouble(txF_Sal.getText());
					totalSalary =  salary + Integer.parseInt(txF_AcClie.getText());
					txF_TotSal.setText(Double.toString(totalSalary));
					
					
					
				}else {
					Helper.showMsg("MissOp");
				}
				
			}
		});
		btn_ShowInfo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btn_ShowInfo.setBackground(new Color(230, 230, 250));
		btn_ShowInfo.setBounds(400, 154, 235, 43);
		panel_2.add(btn_ShowInfo);
	
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ManageSellerGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(26, 16, 153, 115);
		contentPane.add(lblNewLabel);
	}
	public void fillUpArray(String[][] arr)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM workerdata");
			int row = 0;
			while(rs.next())
			{
				if(rs.getString("type").equals("Seller"))
				{
					
				
				//ID","Name","Accepted client","Salary","Start date
				for(int i = 0; i< arr[0].length;i++)
				{
					int k = 0;
					arr[row][k] = Integer.toString(rs.getInt("userid"));
					arr[row][k+1] = rs.getString("Name");
					arr[row][k+2] = Integer.toString(rs.getInt("acceptedClient"));
					arr[row][k+3] = Double.toString(rs.getDouble("Salary"));
					arr[row][k+4] = rs.getString("StartDate");
					arr[row][k+5] = rs.getString("phoneNumber");
					arr[row][k+6] = rs.getString("location");
					
				}
				row++;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
