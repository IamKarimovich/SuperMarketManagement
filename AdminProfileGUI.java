package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Help.DBConnection;
import Help.Helper;
import Objects.Admin;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class AdminProfileGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_AdminName;
	private JTextField txF_LogId;
	private JTextField txF_Password;
	private static Admin admin;
	private DBConnection cnn = new DBConnection();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminProfileGUI frame = new AdminProfileGUI(admin);
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
	public AdminProfileGUI(Admin admin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 479);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI aG = new AdminGUI(admin);
				aG.setVisible(true);
				dispose();
			}
		});
		btn_back.setForeground(Color.BLACK);
		btn_back.setFont(new Font("Serif", Font.BOLD, 20));
		btn_back.setBorder(null);
		btn_back.setBackground(new Color(255, 165, 0));
		btn_back.setBounds(10, 34, 120, 40);
		contentPane.add(btn_back);
		
		JLabel lbl_ProfileLogo = new JLabel("");
		lbl_ProfileLogo.setIcon(new ImageIcon(AdminProfileGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lbl_ProfileLogo.setBounds(185, 10, 183, 159);
		contentPane.add(lbl_ProfileLogo);
		
		JButton btn_Name = new JButton("Name : ");
		btn_Name.setForeground(Color.BLACK);
		btn_Name.setFont(new Font("Serif", Font.BOLD, 30));
		btn_Name.setBorder(null);
		btn_Name.setBackground(new Color(255, 165, 0));
		btn_Name.setBounds(20, 179, 227, 48);
		contentPane.add(btn_Name);
		
		
		
		txF_AdminName = new JTextField();
		txF_AdminName.setText((String) null);
		txF_AdminName.setHorizontalAlignment(SwingConstants.CENTER);
		txF_AdminName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_AdminName.setColumns(10);
		txF_AdminName.setBounds(270, 179, 259, 48);
		contentPane.add(txF_AdminName);
		
		JButton btn_Loginid = new JButton("Login ID : ");
		btn_Loginid.setForeground(Color.BLACK);
		btn_Loginid.setFont(new Font("Serif", Font.BOLD, 30));
		btn_Loginid.setBorder(null);
		btn_Loginid.setBackground(new Color(255, 165, 0));
		btn_Loginid.setBounds(20, 237, 227, 48);
		contentPane.add(btn_Loginid);
		
		txF_LogId = new JTextField();
		txF_LogId.setText((String) null);
		txF_LogId.setHorizontalAlignment(SwingConstants.CENTER);
		txF_LogId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_LogId.setColumns(10);
		txF_LogId.setBounds(270, 237, 259, 48);
		contentPane.add(txF_LogId);
		
		JButton btn_pass = new JButton("Password : ");
		btn_pass.setForeground(Color.BLACK);
		btn_pass.setFont(new Font("Serif", Font.BOLD, 30));
		btn_pass.setBorder(null);
		btn_pass.setBackground(new Color(255, 165, 0));
		btn_pass.setBounds(20, 295, 227, 48);
		contentPane.add(btn_pass);
		
		txF_Password = new JTextField();
		txF_Password.setText((String) null);
		txF_Password.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Password.setColumns(10);
		txF_Password.setBounds(270, 295, 259, 48);
		contentPane.add(txF_Password);
		
		
		txF_AdminName.setText(admin.getName());
		txF_LogId.setText(admin.getLoginId());
		txF_Password.setText(admin.getPassword());
		
		JButton btn_changeInfo = new JButton("Change Information");
		btn_changeInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Connection con;
				int fnd= 0;
				if(Helper.askQuestion("sureEdit")==1)
				{
					if(!txF_AdminName.getText().equals(admin.getName()) || !txF_LogId.getText().equals(admin.getLoginId()) || !txF_Password.getText().equals(admin.getPassword()))
					{
						if(!txF_LogId.getText().equals(admin.getLoginId()))
						{	
							try {
								con = cnn.connDB();
								Statement st = con.createStatement();
								ResultSet rs = st.executeQuery("SELECT * FROM admindata WHERE loginId="+txF_LogId.getText());
								while(rs.next())
								{
									fnd++;
									Helper.showMsg("ExistLogId");
									break;
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						String Last_pas = null;
						boolean pass = true;
						if(!txF_Password.getText().equals(admin.getPassword()))
						{
							Last_pas =  JOptionPane.showInputDialog("Enter Last password");
							if(!Last_pas.equals(admin.getPassword()))
							{
								pass = false;
							}
						}
						if(pass == true)
						{
							if(fnd==0)
							{
								try {
									con = cnn.connDB(); //Connection to database
									PreparedStatement pSt = con.prepareStatement("UPDATE admindata SET Name=?, loginId=?, password=? WHERE ID=?");
									
									pSt.setString(1, txF_AdminName.getText() );
									pSt.setString(2, txF_LogId.getText());
									pSt.setString(3, txF_Password.getText());
									
									pSt.setInt(4, admin.getID());
									
									pSt.executeUpdate();
									
									admin.setName(txF_AdminName.getText());
									admin.setLoginId(txF_LogId.getText());
									admin.setPassword(txF_Password.getText());
									
									
									Helper.showMsg("successUpdate");
								}catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						
							
						}else {
							Helper.showMsg("Enter correct Password!");
							
						}
						
						
						}
						
				}
				
				
				
				
				
				
				
				
			}
		});
		btn_changeInfo.setForeground(Color.BLACK);
		btn_changeInfo.setFont(new Font("Serif", Font.BOLD, 30));
		btn_changeInfo.setBackground(Color.LIGHT_GRAY);
		btn_changeInfo.setBounds(108, 376, 329, 48);
		contentPane.add(btn_changeInfo);
	}
}
