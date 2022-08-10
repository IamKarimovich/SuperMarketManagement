package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Help.DBConnection;
import Help.Helper;
import Objects.Client;
import Objects.User;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.plugins.tiff.ExifGPSTagSet;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class UserProfileGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txF_Name;
	private JTextField txF_LoginId;
	private JTextField txF_Password;
	private JTextField txF_PhoneNumb;
	private JTextField txF_Location;
	private static Client client;
	private DBConnection cnn = new DBConnection();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserProfileGUI frame = new UserProfileGUI(client);
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
	public UserProfileGUI(Client client) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 606);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_ProfileLogo = new JLabel("");
		lbl_ProfileLogo.setIcon(new ImageIcon(UserProfileGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lbl_ProfileLogo.setBounds(180, 21, 183, 159);
		contentPane.add(lbl_ProfileLogo);
		
		txF_Name = new JTextField();
		txF_Name.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Name.setText(client.getName());
		txF_Name.setBounds(296, 190, 259, 48);
		contentPane.add(txF_Name);
		txF_Name.setColumns(10);
		
		txF_LoginId = new JTextField();
		txF_LoginId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_LoginId.setHorizontalAlignment(SwingConstants.CENTER);
		txF_LoginId.setText(client.getLoginId());
		txF_LoginId.setColumns(10);
		txF_LoginId.setBounds(296, 248, 259, 48);
		contentPane.add(txF_LoginId);
		
		txF_Password = new JTextField();
		txF_Password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Password.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Password.setText(client.getPassword());
		txF_Password.setColumns(10);
		txF_Password.setBounds(296, 306, 259, 48);
		contentPane.add(txF_Password);
		
		/////////////////////////////////
		String strBonus = String.format("Bonus : %.2f",client.getBonus());
		JLabel lbl_Bonus = new JLabel(strBonus);
		lbl_Bonus.setFont(new Font("Serif", Font.BOLD, 20));
		lbl_Bonus.setBounds(447, 21, 142, 40);
		contentPane.add(lbl_Bonus);
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ClientGUI cG = new ClientGUI(client);
				cG.setVisible(true);
				dispose();
				
			}
		});
		btn_back.setForeground(Color.BLACK);
		btn_back.setFont(new Font("Serif", Font.BOLD, 20));
		btn_back.setBorder(null);
		btn_back.setBackground(new Color(255, 165, 0));
		btn_back.setBounds(10, 21, 120, 40);
		contentPane.add(btn_back);
		
		JButton btn_Name = new JButton("Name : ");
		btn_Name.setForeground(Color.BLACK);
		btn_Name.setFont(new Font("Serif", Font.BOLD, 30));
		btn_Name.setBorder(null);
		btn_Name.setBackground(new Color(255, 165, 0));
		btn_Name.setBounds(46, 190, 227, 48);
		contentPane.add(btn_Name);
		
		JButton btn_Loginid = new JButton("Login ID : ");
		btn_Loginid.setForeground(Color.BLACK);
		btn_Loginid.setFont(new Font("Serif", Font.BOLD, 30));
		btn_Loginid.setBorder(null);
		btn_Loginid.setBackground(new Color(255, 165, 0));
		btn_Loginid.setBounds(46, 248, 227, 48);
		contentPane.add(btn_Loginid);
		
		JButton btn_pass = new JButton("Password : ");
		btn_pass.setForeground(Color.BLACK);
		btn_pass.setFont(new Font("Serif", Font.BOLD, 30));
		btn_pass.setBorder(null);
		btn_pass.setBackground(new Color(255, 165, 0));
		btn_pass.setBounds(46, 306, 227, 48);
		contentPane.add(btn_pass);
		
		JButton btn_PhoneNumb = new JButton("Phone Number : ");
		btn_PhoneNumb.setForeground(Color.BLACK);
		btn_PhoneNumb.setFont(new Font("Serif", Font.BOLD, 30));
		btn_PhoneNumb.setBorder(null);
		btn_PhoneNumb.setBackground(new Color(255, 165, 0));
		btn_PhoneNumb.setBounds(46, 364, 227, 48);
		contentPane.add(btn_PhoneNumb);
		
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("(###)-###-####");
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		txF_PhoneNumb = new JFormattedTextField(mask);
		txF_PhoneNumb.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_PhoneNumb.setHorizontalAlignment(SwingConstants.CENTER);
		txF_PhoneNumb.setText(client.getPhoneNumber());
		txF_PhoneNumb.setColumns(10);
		txF_PhoneNumb.setBounds(296, 364, 259, 48);
		contentPane.add(txF_PhoneNumb);
		
		JButton btn_Location = new JButton("Location : ");
		btn_Location.setForeground(Color.BLACK);
		btn_Location.setFont(new Font("Serif", Font.BOLD, 30));
		btn_Location.setBorder(null);
		btn_Location.setBackground(new Color(255, 165, 0));
		btn_Location.setBounds(46, 422, 227, 48);
		contentPane.add(btn_Location);
		
		txF_Location = new JTextField();
		txF_Location.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Location.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Location.setText(client.getLocation());
		txF_Location.setColumns(10);
		txF_Location.setBounds(296, 422, 259, 48);
		contentPane.add(txF_Location);
		
		JButton btn_changeInfo = new JButton("Change Information");
		btn_changeInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con;
				int fnd= 0;
				if(Helper.askQuestion("sureEdit")==1)
				{
					System.out.println(!txF_Name.getText().equals(client.getName()) || !txF_Password.getText().equals(client.getPassword()) 
							|| !txF_Location.getText().equals(client.getLocation()) || !txF_LoginId.getText().equals(client.getLoginId()) 
							|| !txF_PhoneNumb.getText().equals(client.getPhoneNumber()));
					
					if(!txF_Name.getText().equals(client.getName()) || !txF_Password.getText().equals(client.getPassword()) 
							|| !txF_Location.getText().equals(client.getLocation()) || !txF_LoginId.getText().equals(client.getLoginId()) 
							|| !txF_PhoneNumb.getText().equals(client.getPhoneNumber()))
					{
						if(!txF_LoginId.getText().equals(client.getLoginId()))
						{	
							try {
								con = cnn.connDB();
								Statement st = con.createStatement();
								ResultSet rs = st.executeQuery("SELECT * FROM clientdata WHERE loginId="+txF_LoginId.getText());
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
						if(!txF_Password.getText().equals(client.getPassword()))
						{
							Last_pas =  JOptionPane.showInputDialog("Enter Last password");
							if(!Last_pas.equals(client.getPassword()))
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
									PreparedStatement pSt = con.prepareStatement("UPDATE clientdata SET Name=?, Location=? WHERE userid=?");
									
									pSt.setString(1, txF_Name.getText() );
									pSt.setString(2, txF_Location.getText());	
									pSt.setInt(3, client.getID());
									pSt.executeUpdate();
									
									pSt = con.prepareStatement("UPDATE userdata SET Name=?, loginId=?, password=?, phone_number=?, Location=? WHERE ID=?");
									
									pSt.setString(1, txF_Name.getText() );
									pSt.setString(2, txF_LoginId.getText());
									pSt.setString(3, txF_Password.getText());
									pSt.setString(4, txF_PhoneNumb.getText());
									pSt.setString(5, txF_Location.getText());	
									pSt.setInt(6, client.getID());
									pSt.executeUpdate();
									
									client.setName(txF_Name.getText());
									client.setLoginId(txF_LoginId.getText());
									client.setPassword(txF_Password.getText());
									client.setPhoneNumber(txF_PhoneNumb.getText());
									client.setLocation(txF_Location.getText());
									
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
		btn_changeInfo.setBackground(new Color(192, 192, 192));
		btn_changeInfo.setBounds(133, 496, 329, 48);
		contentPane.add(btn_changeInfo);
	}
}
