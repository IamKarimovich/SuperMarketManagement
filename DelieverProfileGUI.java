package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Help.DBConnection;
import Help.Helper;
import Objects.Deliever;

public class DelieverProfileGUI extends JFrame {

	DBConnection cnn = new DBConnection();
	private JPanel contentPane;
	private JTextField txF_Name,txF_LoginId,txF_Password,txF_PhoneNumb,txF_Location;
	private  int ac_client=0;
	private double salary=0;
	public static Deliever deliever;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DelieverProfileGUI frame = new DelieverProfileGUI(deliever);
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
	public DelieverProfileGUI(Deliever deliever) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 606);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_ProfileLogo = new JLabel("");
		lbl_ProfileLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_ProfileLogo.setIcon(new ImageIcon(UserProfileGUI.class.getResource("/View/output-onlinepngtools (8).png")));
		lbl_ProfileLogo.setBounds(181, 33, 149, 132);
		contentPane.add(lbl_ProfileLogo);
		
		txF_Name = new JTextField();
		txF_Name.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Name.setText(deliever.getName());
		txF_Name.setBounds(296, 190, 259, 48);
		contentPane.add(txF_Name);
		txF_Name.setColumns(10);
		
		txF_LoginId = new JTextField();
		txF_LoginId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_LoginId.setHorizontalAlignment(SwingConstants.CENTER);
		txF_LoginId.setText(deliever.getLoginId());
		txF_LoginId.setColumns(10);
		txF_LoginId.setBounds(296, 248, 259, 48);
		contentPane.add(txF_LoginId);
		
		txF_Password = new JTextField();
		txF_Password.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txF_Password.setHorizontalAlignment(SwingConstants.CENTER);
		txF_Password.setText(deliever.getPassword());
		txF_Password.setColumns(10);
		txF_Password.setBounds(296, 306, 259, 48);
		contentPane.add(txF_Password);
		
		/////////////////////////////////
		
		
		
		Connection con = cnn.connDB();
		
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM workerdata");
			
			while(rs.next())
			{
				if(rs.getInt("userId")==deliever.getID())
				{
					salary = rs.getDouble("Salary");
					ac_client = rs.getInt("acceptedClient");
					break;
				}
			}
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		JLabel lbl_Bonus = new JLabel("Salary : "+salary);
		lbl_Bonus.setFont(new Font("Serif", Font.BOLD, 20));
		lbl_Bonus.setBounds(340, 21, 215, 40);
		contentPane.add(lbl_Bonus);
		
		
		
		JButton btn_back = new JButton("Back");
		btn_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DelieverGUI dg = new DelieverGUI(deliever);
				dg.setVisible(true);
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
		txF_PhoneNumb.setText(deliever.getPhoneNumber());
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
		txF_Location.setText(deliever.getLocation());
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
					System.out.println(!txF_Name.getText().equals(deliever.getName()) || !txF_Password.getText().equals(deliever.getPassword()) 
							|| !txF_Location.getText().equals(deliever.getLocation()) || !txF_LoginId.getText().equals(deliever.getLoginId()) 
							|| !txF_PhoneNumb.getText().equals(deliever.getPhoneNumber()));
					
					if(!txF_Name.getText().equals(deliever.getName()) || !txF_Password.getText().equals(deliever.getPassword()) 
							|| !txF_Location.getText().equals(deliever.getLocation()) || !txF_LoginId.getText().equals(deliever.getLoginId()) 
							|| !txF_PhoneNumb.getText().equals(deliever.getPhoneNumber()))
					{
						if(!txF_LoginId.getText().equals(deliever.getLoginId()))
						{	
							try {
								con = cnn.connDB();
								Statement st = con.createStatement();
								ResultSet rs = st.executeQuery("SELECT * FROM workerdata WHERE userid = "+Integer.parseInt(txF_LoginId.getText()));
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
						String Last_pas = "";
						boolean pass = true;
						if(!txF_Password.getText().equals(deliever.getPassword()))
						{
							Last_pas =  JOptionPane.showInputDialog("Enter Last password");
							if(!Last_pas.equals(deliever.getPassword()))
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
									PreparedStatement pSt = con.prepareStatement("UPDATE workerdata SET Name=?,phoneNumber=?, location=? WHERE userid=?");
									
									pSt.setString(1, txF_Name.getText() );
									pSt.setString(2, txF_PhoneNumb.getText());
									pSt.setString(3, txF_Location.getText());	
									pSt.setInt(4, deliever.getID());
									pSt.executeUpdate();
									
									pSt = con.prepareStatement("UPDATE userdata SET Name=?, loginId=?, password=?, phone_number=?, Location=? WHERE ID=?");
									
									pSt.setString(1, txF_Name.getText() );
									pSt.setString(2, txF_LoginId.getText());
									pSt.setString(3, txF_Password.getText());
									pSt.setString(4, txF_PhoneNumb.getText());
									pSt.setString(5, txF_Location.getText());	
									pSt.setInt(6, deliever.getID());
									pSt.executeUpdate();
									
									
									deliever.setName(txF_Name.getText());
									deliever.setLoginId(txF_LoginId.getText());
									deliever.setPassword(txF_Password.getText());
									deliever.setPhoneNumber(txF_PhoneNumb.getText());
									deliever.setLocation(txF_Location.getText());
									
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
		
		JLabel lbl_Bonus_1 = new JLabel("Accepted Client : "+ac_client);
		lbl_Bonus_1.setFont(new Font("Serif", Font.BOLD, 20));
		lbl_Bonus_1.setBounds(340, 71, 215, 40);
		contentPane.add(lbl_Bonus_1);
		
		double totSal = (ac_client + salary);
		JLabel lbl_Bonus_1_1 = new JLabel("Total Salary : "+totSal);
		lbl_Bonus_1_1.setFont(new Font("Serif", Font.BOLD, 20));
		lbl_Bonus_1_1.setBounds(340, 121, 215, 40);
		contentPane.add(lbl_Bonus_1_1);
	}

}
