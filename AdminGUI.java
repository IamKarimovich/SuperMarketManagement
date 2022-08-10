package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;

import Help.DBConnection;
import Help.Helper;
import Objects.Admin;
import Objects.User;

import javax.swing.border.BevelBorder;
import javax.swing.DebugGraphics;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;

public class AdminGUI extends JFrame {

	private JPanel contentPane;
	private static Admin admin;
	private JTextField txF_ProductID;
	private JTextField txF_ProductName;
	private JTextField txF_Quantity;
	private JTextField txF_Price;
	private JTable table;
	private DBConnection cnn = new DBConnection();
	private String header[] = {"ID","Product ID","Name","Quantity","Price","Category"};
	private String[][] data = new String[Helper.countRowFromSql(cnn, "productdata")][6];
	private DefaultTableModel tableModel;
	private String productid = "";
	private User user;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(admin);
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
	public AdminGUI(Admin admin) {
		
		
		
		fillUpArray(data);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1005, 626);
		contentPane = new JPanel();
		contentPane.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(206, 270, 775, 309);
		panel.setBackground(new Color(192, 192, 192));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 755, 315);
		panel.add(scrollPane);
		

		tableModel = new DefaultTableModel(data,header)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setBackground(new Color(192, 192, 192));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(206, 10, 775, 250);
		panel_1.setBackground(new Color(192, 192, 192));
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lbl_ProductID = new JLabel("Product ID : ");
		lbl_ProductID.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ProductID.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_ProductID.setBackground(new Color(255, 165, 0));
		lbl_ProductID.setBounds(10, 10, 175, 37);
		panel_1.add(lbl_ProductID);
		
		JLabel lbl_productName = new JLabel("Product Name : ");
		lbl_productName.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_productName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_productName.setBackground(new Color(255, 165, 0));
		lbl_productName.setBounds(10, 77, 175, 37);
		panel_1.add(lbl_productName);
		
		JLabel lbl_Quantitiy = new JLabel("Quantity : ");
		lbl_Quantitiy.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Quantitiy.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Quantitiy.setBackground(new Color(255, 165, 0));
		lbl_Quantitiy.setBounds(382, 10, 154, 37);
		panel_1.add(lbl_Quantitiy);
		
		JLabel lbl_Price = new JLabel("Price : ");
		lbl_Price.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Price.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_Price.setBackground(new Color(255, 165, 0));
		lbl_Price.setBounds(382, 77, 154, 37);
		panel_1.add(lbl_Price);
		
		txF_ProductID = new JTextField();
		txF_ProductID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_ProductID.setBounds(180, 10, 196, 37);
		panel_1.add(txF_ProductID);
		txF_ProductID.setColumns(10);
		
		txF_ProductName = new JTextField();
		txF_ProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_ProductName.setColumns(10);
		txF_ProductName.setBounds(180, 77, 196, 37);
		panel_1.add(txF_ProductName);
		
		txF_Quantity = new JTextField();
		txF_Quantity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Quantity.setColumns(10);
		txF_Quantity.setBounds(541, 10, 196, 37);
		panel_1.add(txF_Quantity);
		
		txF_Price = new JTextField();
		txF_Price.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Price.setColumns(10);
		txF_Price.setBounds(541, 77, 196, 37);
		panel_1.add(txF_Price);
		
		JComboBox select_category = new JComboBox();
		select_category.setModel(new DefaultComboBoxModel(new String[] {"Bakery", "Beverage", "Nonfood & Pharmacy", "Meat", "Seafood"}));
		select_category.setFont(new Font("Tahoma", Font.PLAIN, 16));
		select_category.setBounds(180, 139, 196, 37);
		panel_1.add(select_category);
		
		JLabel lbl_productCategory = new JLabel("Category : ");
		lbl_productCategory.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_productCategory.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbl_productCategory.setBackground(new Color(255, 165, 0));
		lbl_productCategory.setBounds(10, 139, 175, 37);
		panel_1.add(lbl_productCategory);
		
		JButton btn_AddProduct = new JButton("Add");
		btn_AddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txF_ProductID.getText().length() == 0 || txF_ProductName.getText().length() == 0 || txF_Price.getText().length() == 0 
						|| txF_Quantity.getText().length() == 0)
				{
					Helper.showMsg("fillUp");
				}else {
					Connection con = cnn.connDB();
					
					
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM productdata");
						int found = 0;
						while(rs.next())
						{
							if(rs.getString("Name").equals(txF_ProductName.getText())||rs.getString("productid").equals(txF_ProductID.getText()))
							{
								Helper.showMsg("This product already exist!");
								found++;
								
							}
						}
						if(found == 0)
						{
							PreparedStatement pSt = con.prepareStatement("INSERT INTO productdata (Name,productid,quantity,price,Category) "
									+ "VALUES (?,?,?,?,?)");
							pSt.setString(1, txF_ProductName.getText());
							pSt.setString(2, txF_ProductID.getText());
							int quantity = Integer.parseInt(txF_Quantity.getText());
							pSt.setInt(3, quantity);
							double price = Double.parseDouble(txF_Price.getText());
							pSt.setDouble(4,price );
							String category = (String) select_category.getSelectedItem();
							pSt.setString(5,category);
							pSt.executeUpdate();
							
							int rowCount = tableModel.getRowCount();
							//Remove rows one by one from the end of the table
							for (int i = rowCount - 1; i >= 0; i--) {
							    tableModel.removeRow(i);
							}
							data = new String[Helper.countRowFromSql(cnn, "productdata")][6];
							fillUpArray(data);
							tableModel = new DefaultTableModel(data,header)
							{
							    @Override
							    public boolean isCellEditable(int row, int column) {
							        return false;
							    }
							};	
							
							txF_Price.setText("");
							txF_ProductID.setText("");
							txF_ProductName.setText("");
							txF_Quantity.setText("");
							
							table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
							table = new JTable(tableModel);
							scrollPane.setViewportView(table);
							table.setBackground(new Color(192, 192, 192));
							Helper.showMsg("succesAdd");
						}
	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_AddProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_AddProduct.setBounds(448, 139, 139, 37);
		panel_1.add(btn_AddProduct);
		
		JButton btn_changeInfo = new JButton("Change Information");
		btn_changeInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Helper.askQuestion("sureEdit")==1)
				{
					if(txF_ProductID.getText().length() == 0 || txF_ProductName.getText().length() == 0 || txF_Price.getText().length() == 0 
							|| txF_Quantity.getText().length() == 0)
					{
						Helper.showMsg("fillUp");
					}else {
						Connection con = cnn.connDB();
						try {	
							PreparedStatement pSt1 = con.prepareStatement("DELETE FROM productdata WHERE ID="+productid);
							pSt1.executeUpdate();
					
							Statement st = con.createStatement();
							ResultSet rs = st.executeQuery("SELECT * FROM productdata");
							int found = 0;
							while(rs.next())
							{
								if(rs.getString("Name").equals(txF_ProductName.getText())||rs.getString("productid").equals(txF_ProductID.getText()))
								{
									Helper.showMsg("This product already exist!");
									found++;	
								}
							}
							if(found == 0)
							{
								PreparedStatement pSt = con.prepareStatement("INSERT INTO productdata (Name,productid,quantity,price,Category) "
										+ "VALUES (?,?,?,?,?)");
								pSt.setString(1, txF_ProductName.getText());
								pSt.setString(2, txF_ProductID.getText());
								pSt.setInt(3, Integer.parseInt(txF_Quantity.getText()));
								pSt.setFloat(4, Float.parseFloat(txF_Price.getText()));
								pSt.setString(5,(String) select_category.getSelectedItem());
								pSt.executeUpdate();
								
								int rowCount = tableModel.getRowCount();
								//Remove rows one by one from the end of the table
								for (int i = rowCount - 1; i >= 0; i--) {
								    tableModel.removeRow(i);
								}
								data = new String[Helper.countRowFromSql(cnn, "productdata")][6];
								fillUpArray(data);
								tableModel = new DefaultTableModel(data,header)
								{
								    @Override
								    public boolean isCellEditable(int row, int column) {
								        return false;
								    }
								};			
								table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
								table = new JTable(tableModel);
								scrollPane.setViewportView(table);
								table.setBackground(new Color(192, 192, 192));
								
								txF_Price.setText("");
								txF_ProductID.setText("");
								txF_ProductName.setText("");
								txF_Quantity.setText("");
								
								Helper.showMsg("successUpdate");
							}
		
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				
				
			}
		
		});
		btn_changeInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_changeInfo.setBounds(180, 186, 196, 37);
		panel_1.add(btn_changeInfo);
		btn_changeInfo.setBounds(180, 186, 196, 37);
		panel_1.add(btn_changeInfo);
		btn_changeInfo.setVisible(false);
		
		JButton btn_EditProduct = new JButton("Edit");
		btn_EditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {	
				
				productid = (String) table.getValueAt(table.getSelectedRow(), 0);
					
				txF_ProductID.setText((String)table.getValueAt(table.getSelectedRow(),1 ));
				txF_ProductName.setText((String)table.getValueAt(table.getSelectedRow(),2 ));
				txF_Quantity.setText((String)table.getValueAt(table.getSelectedRow(),3 ));
				txF_Price.setText((String)table.getValueAt(table.getSelectedRow(),4 ));
				select_category.setSelectedItem(table.getValueAt(table.getSelectedRow(),5 ));
				btn_changeInfo.setVisible(true);			
				} catch (Exception e2) {
					Helper.showMsg("MissOp");
				}
			}
		});
		btn_EditProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_EditProduct.setBounds(598, 139, 139, 37);
		panel_1.add(btn_EditProduct);
		
		JButton btn_clearTextField = new JButton("Clear");
		btn_clearTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txF_Price.setText("");
				txF_ProductID.setText("");
				txF_ProductName.setText("");
				txF_Quantity.setText("");
				
			}
		});
		btn_clearTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_clearTextField.setBounds(598, 186, 139, 37);
		panel_1.add(btn_clearTextField);
		
		
		
		JButton btn_DeleteProduct = new JButton("Delete");
		btn_DeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			try {
				txF_ProductID.setText((String)table.getValueAt(table.getSelectedRow(),1 ));
				txF_ProductName.setText((String)table.getValueAt(table.getSelectedRow(),2 ));
				txF_Quantity.setText((String)table.getValueAt(table.getSelectedRow(),3 ));
				txF_Price.setText((String)table.getValueAt(table.getSelectedRow(),4 ));
				select_category.setSelectedItem(table.getValueAt(table.getSelectedRow(),5 ));
				if(Helper.askQuestion("sureDelete")==1)
				{
					Connection con = cnn.connDB();
					try {
						PreparedStatement pSt = con.prepareStatement("DELETE FROM productdata WHERE ID="+table.getValueAt(table.getSelectedRow(), 0));
						pSt.executeUpdate();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int rowCount = tableModel.getRowCount();
					//Remove rows one by one from the end of the table
					for (int i = rowCount - 1; i >= 0; i--) {
					    tableModel.removeRow(i);
					}
					data = new String[Helper.countRowFromSql(cnn, "productdata")][6];
					fillUpArray(data);
					tableModel = new DefaultTableModel(data,header)
					{
					    @Override
					    public boolean isCellEditable(int row, int column) {
					        return false;
					    }
					};			
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table = new JTable(tableModel);
					scrollPane.setViewportView(table);
					table.setBackground(new Color(192, 192, 192));
					Helper.showMsg("successDelete");
				}
			} catch (Exception e2) {
				Helper.showMsg("MissOp");
			}
		}
				
	});
		btn_DeleteProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn_DeleteProduct.setBounds(448, 186, 139, 37);
		panel_1.add(btn_DeleteProduct);
		
		
		
		JButton btn_ManageProducts = new JButton("Manage Products");
		btn_ManageProducts.setBorder(null);
		btn_ManageProducts.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI aG = new AdminGUI(admin);
				aG.setVisible(true);
				dispose();
			}
		});
		btn_ManageProducts.setForeground(new Color(0, 0, 0));
		btn_ManageProducts.setBackground(new Color(255, 165, 0));
		btn_ManageProducts.setBounds(10, 210, 186, 50);
		contentPane.add(btn_ManageProducts);
		
		JButton btn_ManageSeller = new JButton("Manage Sellers");
		btn_ManageSeller.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageSellerGUI mG = new ManageSellerGUI(admin);
				mG.setVisible(true);
				dispose();		
			}
		});
		btn_ManageSeller.setBorder(null);
		btn_ManageSeller.setForeground(new Color(0, 0, 0));
		btn_ManageSeller.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageSeller.setBackground(new Color(255, 165, 0));
		btn_ManageSeller.setBounds(10, 270, 186, 50);
		contentPane.add(btn_ManageSeller);
		
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
		btn_AdminLogOut.setBorder(null);
		btn_AdminLogOut.setForeground(new Color(0, 0, 0));
		btn_AdminLogOut.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
		btn_AdminLogOut.setBackground(new Color(255, 165, 0));
		btn_AdminLogOut.setBounds(10, 485, 186, 50);
		contentPane.add(btn_AdminLogOut);
		
		JButton btn_ManageDeliever = new JButton("Manage Delievers");
		btn_ManageDeliever.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManageDelieverGUI mG = new ManageDelieverGUI(admin);
				mG.setVisible(true);
				dispose();
			}
		});
		btn_ManageDeliever.setBorder(null);
		btn_ManageDeliever.setForeground(new Color(0, 0, 0));
		btn_ManageDeliever.setFont(new Font("Serif", Font.BOLD, 20));
		btn_ManageDeliever.setBackground(new Color(255, 165, 0));
		btn_ManageDeliever.setBounds(10, 330, 186, 50);
		contentPane.add(btn_ManageDeliever);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(AdminGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lblNewLabel.setBounds(27, 10, 153, 115);
		contentPane.add(lblNewLabel);
		
		//(String) admin.getName()
		JButton btn_adminProf = new JButton(admin.getName());
		btn_adminProf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminProfileGUI aG = new AdminProfileGUI(admin);
				aG.setVisible(true);
				dispose();
			}
		});
		btn_adminProf.setForeground(Color.BLACK);
		btn_adminProf.setFont(new Font("Myanmar Text", Font.BOLD, 20));
		btn_adminProf.setBorder(null);
		btn_adminProf.setBackground(new Color(255, 165, 0));
		btn_adminProf.setBounds(10, 126, 186, 50);
		contentPane.add(btn_adminProf);
		
		JButton btn_endedProducts = new JButton("Ended Products");
		btn_endedProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EndedProductsGUI eg = new EndedProductsGUI();
				eg.setVisible(true);
			}
		});
		btn_endedProducts.setForeground(Color.BLACK);
		btn_endedProducts.setFont(new Font("Serif", Font.BOLD, 20));
		btn_endedProducts.setBorder(null);
		btn_endedProducts.setBackground(new Color(255, 165, 0));
		btn_endedProducts.setBounds(10, 390, 186, 50);
		contentPane.add(btn_endedProducts);
	}
	
	public void fillUpArray(String[][] arr)
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM productdata");
			int row = 0;
			while(rs.next())
			{
				//"ID","Product ID","Name","Quantity","Price","Category"
				for(int i = 0; i< arr[0].length;i++)
				{
					int k = 0;
					arr[row][k] = Integer.toString(rs.getInt("ID"));
					arr[row][k+1] = rs.getString("productid");
					arr[row][k+2] = rs.getString("Name");
					arr[row][k+3] = rs.getString("quantity");
					arr[row][k+4] = Double.toString(rs.getDouble("price"));
					arr[row][k+5] = rs.getString("Category");
				}
				row++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
