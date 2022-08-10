package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Help.DBConnection;
import Help.Helper;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class UserSignInGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_UserSurName;
	private JTextField txF_UserName;
	private JTextField txF_LoginID;
	private JTextField txF_UserPass;
	private DBConnection con = new DBConnection();
	private JTextField txF_userPhoneNumb;
	private JTextField txF_UserLocation;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserSignInGUI frame = new UserSignInGUI();
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
	public UserSignInGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 661);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon(UserSignInGUI.class.getResource("/View/SignUpLogo.png")));
		lblNewLabel.setBounds(230, 10, 113, 88);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Sign-Up");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblNewLabel_1_1.setBackground(Color.BLACK);
		lblNewLabel_1_1.setBounds(144, 108, 290, 55);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lbl_UserName = new JLabel("Name :");
		lbl_UserName.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserName.setBounds(57, 190, 193, 42);
		contentPane.add(lbl_UserName);
		
		JLabel lbl_UserSurName = new JLabel("Surname :");
		lbl_UserSurName.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserSurName.setBounds(57, 246, 193, 42);
		contentPane.add(lbl_UserSurName);
		
		JLabel lbl_UserID = new JLabel("Login ID : ");
		lbl_UserID.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserID.setBounds(57, 298, 193, 42);
		contentPane.add(lbl_UserID);
		
		JLabel lbl_UserPass = new JLabel("User Password :");
		lbl_UserPass.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserPass.setBounds(57, 356, 193, 42);
		contentPane.add(lbl_UserPass);
		
		txF_UserSurName = new JTextField();
		txF_UserSurName.setBackground(new Color(192, 192, 192));
		txF_UserSurName.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_UserSurName.setBounds(320, 247, 243, 42);
		contentPane.add(txF_UserSurName);
		txF_UserSurName.setColumns(10);
		
		txF_UserName = new JTextField();
		txF_UserName.setBackground(new Color(192, 192, 192));
		txF_UserName.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_UserName.setColumns(10);
		txF_UserName.setBounds(320, 191, 243, 42);
		contentPane.add(txF_UserName);
		
		txF_LoginID = new JTextField();
		txF_LoginID.setBackground(new Color(192, 192, 192));
		txF_LoginID.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_LoginID.setColumns(10);
		txF_LoginID.setBounds(320, 299, 243, 42);
		contentPane.add(txF_LoginID);
		
		txF_UserPass = new JTextField();
		txF_UserPass.setBackground(new Color(192, 192, 192));
		txF_UserPass.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_UserPass.setColumns(10);
		txF_UserPass.setBounds(320, 356, 243, 42);
		contentPane.add(txF_UserPass);
		
		JButton btn_SignIn = new JButton("Back");
		btn_SignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.askQuestion("sureBack") == 1)
				{
					LoginGUI lG = new LoginGUI();
					lG.setVisible(true);
					dispose();
				}
			}
		});
		btn_SignIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_SignIn.setBackground(new Color(255, 255, 255));
		btn_SignIn.setBounds(90, 538, 214, 55);
		contentPane.add(btn_SignIn);
		
		JButton btn_LogIn = new JButton("Sign-Up");
		btn_LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int found = 0;
				if(txF_UserName.getText().length()==0 || txF_UserSurName.getText().length() == 0 || txF_LoginID.getText().length() ==0
						|| txF_UserPass.getText().length() == 0)
				{
					Helper.showMsg("fillUp");
				}else
				{
					try {
						Connection cn = con.connDB();
						Statement st = cn.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM userdata");
						while(rs.next())
						{
							if(rs.getString("loginId").equals(txF_LoginID))
							{
								found++;
								break;
							}
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(found != 0)
					{
						Helper.showMsg("Incorrect User ID! ");
					}else {
						try {
							Connection cn = con.connDB();
							PreparedStatement Pst1 = cn.prepareStatement("INSERT INTO userdata (Name,loginId,password,phone_number,Location) VALUES (?,?,?,?,?)");
							Pst1.setString(1, txF_UserName.getText()+" "+txF_UserSurName.getText());
							Pst1.setString(2, txF_LoginID.getText());
							Pst1.setString(3, txF_UserPass.getText());
							Pst1.setString(4, txF_userPhoneNumb.getText());
							Pst1.setString(5, txF_UserLocation.getText());
							Pst1.executeUpdate();
							
							Statement st = cn.createStatement();
							ResultSet rs = st.executeQuery("SELECT * FROM userdata");
							int userid = 0;
							String name = null,location = null;
							while(rs.next())
							{
								if(rs.getString("Name").equals(txF_UserName.getText()+" "+txF_UserSurName.getText()) 
										&& rs.getString("loginId").equals(txF_LoginID.getText()) && rs.getString("password").equals(txF_UserPass.getText()))
								{
									userid = rs.getInt("ID");
									name = rs.getString("Name");
									location = rs.getString("Location");
								}
							}
							
							PreparedStatement Pst = cn.prepareStatement("INSERT INTO clientdata (userid,Name,Location) VALUES (?,?,?)");
							Pst.setInt(1, userid);
							Pst.setString(2, name);
							Pst.setString(3, location);
							
							Pst.executeUpdate();
							
							Helper.showMsg("Success");
							LoginGUI lG = new LoginGUI();
							lG.setVisible(true);
							dispose();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_LogIn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btn_LogIn.setFocusCycleRoot(true);
		btn_LogIn.setBackground(new Color(255, 255, 255));
		btn_LogIn.setBounds(314, 538, 214, 55);
		contentPane.add(btn_LogIn);
		
		JLabel lbl_userNumber = new JLabel("Phone Number : ");
		lbl_userNumber.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_userNumber.setBounds(57, 408, 193, 42);
		contentPane.add(lbl_userNumber);
		
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("(###)-###-####");
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		txF_userPhoneNumb = new JFormattedTextField(mask);
		txF_userPhoneNumb.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_userPhoneNumb.setColumns(10);
		txF_userPhoneNumb.setBackground(Color.LIGHT_GRAY);
		txF_userPhoneNumb.setBounds(320, 408, 243, 42);
		contentPane.add(txF_userPhoneNumb);
		
		JLabel lbl_UserLocation = new JLabel("Location : ");
		lbl_UserLocation.setFont(new Font("Arial Black", Font.BOLD, 20));
		lbl_UserLocation.setBounds(57, 458, 193, 42);
		contentPane.add(lbl_UserLocation);
		
		txF_UserLocation = new JTextField();
		txF_UserLocation.setFont(new Font("NSimSun", Font.BOLD, 16));
		txF_UserLocation.setColumns(10);
		txF_UserLocation.setBackground(Color.LIGHT_GRAY);
		txF_UserLocation.setBounds(320, 460, 243, 42);
		contentPane.add(txF_UserLocation);
	}
}
