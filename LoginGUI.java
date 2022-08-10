package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import Help.DBConnection;
import Help.Helper;
import Objects.Admin;
import Objects.Client;
import Objects.Deliever;
import Objects.Seller;
import Objects.User;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.Point;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_UserID;
	private JPasswordField txF_userPassField;
	private DBConnection con = new DBConnection();
	private User user;
	private Admin admin;
	private Client client;
	private Seller seller;
	private Deliever deliever;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(new Color(255, 165, 0));
		panel.setBounds(10, 10, 347, 459);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setLabelFor(panel);
		lblNewLabel.setIcon(new ImageIcon(LoginGUI.class.getResource("/View/MarketLogo.png")));
		lblNewLabel.setBounds(76, 30, 175, 159);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fast-Ket");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(24, 199, 290, 55);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Not Jet,but Fast \r\n");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("ZapfHumnst Ult BT", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_2.setBounds(24, 264, 290, 82);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Let's get appetites");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setFont(new Font("ZapfHumnst Ult BT", Font.ITALIC, 20));
		lblNewLabel_2_1.setBounds(24, 336, 290, 55);
		panel.add(lblNewLabel_2_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		panel_1.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel_1.setBackground(new Color(192, 192, 192));
		panel_1.setBounds(357, 10, 479, 459);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txF_UserID = new JTextField();
		txF_UserID.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_UserID.setBounds(213, 234, 243, 36);
		panel_1.add(txF_UserID);
		txF_UserID.setColumns(10);
		
		txF_userPassField = new JPasswordField();
		txF_userPassField.setEchoChar('*');
		txF_userPassField.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_userPassField.setBounds(213, 288, 243, 36);
		panel_1.add(txF_userPassField);
		
		JComboBox select_Role = new JComboBox();
		select_Role.setFont(new Font("NSimSun", Font.BOLD, 16));
		select_Role.setModel(new DefaultComboBoxModel(new String[] {"Seller", "Admin", "Deliever", "Client"}));
		select_Role.setBounds(213, 181, 243, 36);
		panel_1.add(select_Role);
		
		JLabel lbl_userRole = new JLabel("User Role :");
		lbl_userRole.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_userRole.setBounds(10, 181, 193, 36);
		panel_1.add(lbl_userRole);
		
		JLabel lbl_userID = new JLabel("User ID :");
		lbl_userID.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_userID.setBounds(10, 234, 193, 36);
		panel_1.add(lbl_userID);
		
		JLabel lbl_UserPass = new JLabel("User Password :");
		lbl_UserPass.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserPass.setBounds(10, 288, 193, 36);
		panel_1.add(lbl_UserPass);
	
		JButton btn_SignIn = new JButton("Sign-In\r\n");
		btn_SignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				new UserSignInGUI();
				
			}});
		btn_SignIn.setBackground(new Color(255, 165, 0));
		btn_SignIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_SignIn.setBounds(24, 371, 214, 55);
		panel_1.add(btn_SignIn);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setIcon(new ImageIcon(LoginGUI.class.getResource("/View/output-onlinepngtools (7).png")));
		lblNewLabel_3.setBounds(167, 10, 148, 103);
		panel_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_1_1 = new JLabel("Log-In");
		lblNewLabel_1_1.setBounds(177, 106, 128, 55);
		panel_1.add(lblNewLabel_1_1);
		lblNewLabel_1_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		
		JButton btn_LogIn = new JButton("Log-In");
		btn_LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection cn = con.connDB();
				Statement st;
				ResultSet rs,rs1;
				int found = 0;
				
				try {
					st = cn.createStatement();
					String value;
					if(select_Role.getSelectedItem().equals("Admin"))
					{
						value = "admindata";
					}else {
						value = "userdata";
					}
					
					if(txF_UserID.getText().length() == 0 || txF_userPassField.getText().length() == 0)
					{
						Helper.showMsg("fillUp");
					}else {
						rs = st.executeQuery("SELECT * FROM "+value);
						while(rs.next())
						{
							if(rs.getString("loginId").equals(txF_UserID.getText()) && rs.getString("password").equals(txF_userPassField.getText())
									&& rs.getString("type").equals((String) select_Role.getSelectedItem()))
							{
								
								found++;
								break;
							}
						}
						if(found == 0)
						{
							Helper.showMsg("NotFoundUSer");
						}else {
							
							switch ((String) select_Role.getSelectedItem()) {
							case "Seller":
								rs1 = st.executeQuery("SELECT * FROM workerdata ");
								int workCount = 0;
								
								while(rs1.next())
								{
									if(rs.getInt("ID")==rs1.getInt("userId"))
									{
										workCount = rs1.getInt("acceptedClient");
										break;
									}
								}
								System.out.println(rs.getInt("ID"));
								seller = new Seller(rs.getInt("ID"),rs.getString("Name"),rs.getString("type"),rs.getString("loginId"),rs.getString("password")
										,rs.getString("phone_number"),rs.getString("Location"),workCount);
								
								SellerGUI sG = new SellerGUI(seller);
								sG.setVisible(true);
								dispose();
								break;
							case "Deliever":
								rs1 = st.executeQuery("SELECT * FROM workerdata ");
								int workCount1 = 0;
								
								while(rs1.next())
								{
									if(rs.getInt("ID")==rs1.getInt("userId"))
									{
										workCount = rs1.getInt("acceptedClient");
										break;
									}
								}
								deliever = new Deliever(rs.getInt("ID"),rs.getString("Name"),rs.getString("type"),rs.getString("loginId"),rs.getString("password")
										,rs.getString("phone_number"),rs.getString("Location"),workCount1);
								DelieverGUI dG = new DelieverGUI(deliever);
								dG.setVisible(true);
								dispose();
								break;
							case "Admin":
								admin = new Admin(rs.getInt("ID"),rs.getString("Name"),rs.getString("loginId"),rs.getString("password"));
								AdminGUI aG = new AdminGUI(admin);
								aG.setVisible(true);
								dispose();
								break;
							case "Client":	
								rs1 = st.executeQuery("SELECT * FROM clientdata ");
								float bonus = 2;
								
								while(rs1.next())
								{
									if(rs.getInt("ID")==rs1.getInt("userid"))
									{
										bonus = rs1.getFloat("Bonus");
										break;
									}
								}
								System.out.println(rs.getInt("ID"));
								client = new Client(rs.getInt("ID"),rs.getString("Name"),rs.getString("type"),rs.getString("loginId"),rs.getString("password")
										,rs.getString("phone_number"),rs.getString("Location"),bonus);
								
								ClientGUI cG = new ClientGUI(client);
								cG.setVisible(true);
								dispose();
								break;
							default:
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
		btn_LogIn.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btn_LogIn.setFocusCycleRoot(true);
		btn_LogIn.setBackground(new Color(255, 165, 0));
		btn_LogIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		btn_LogIn.setBounds(248, 371, 214, 55);
		panel_1.add(btn_LogIn);
		
	}
}
	
	
	
	
	
	
