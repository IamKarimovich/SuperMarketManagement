package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Help.DBConnection;
import Help.Helper;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class EndedProductsGUI extends JFrame {
	DBConnection cnn = new DBConnection();
	private JPanel contentPane;
	private JTable table_EndedProducts;
	private String[] strHeader = {"ID","Name","Price"};
	private String[][] strData = new String[Helper.countRowFromSql(cnn, "productdata")][3];
	DefaultTableModel tableModel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EndedProductsGUI frame = new EndedProductsGUI();
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
	public EndedProductsGUI() {
		fillUpArr(strData);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 633, 531);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ended Products");
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(158, 10, 287, 76);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 599, 350);
		contentPane.add(scrollPane);
		
		
		tableModel = new DefaultTableModel(strData,strHeader)
		{
			@Override
			public boolean isCellEditable(int row,int col)
			{
				return false;
			}
		};
		table_EndedProducts = new JTable(tableModel);
		table_EndedProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table_EndedProducts);
		
		JButton btnNewButton_1 = new JButton("Increase");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table_EndedProducts.getSelectedRow()>=0)
				{
					Connection con = cnn.connDB();
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM productdata WHERE quantity = 0");
						String strid = (String) table_EndedProducts.getValueAt(table_EndedProducts.getSelectedRow(), 0);
						int id = Integer.parseInt(strid);
						while(rs.next())
						{
							PreparedStatement pst = con.prepareStatement("UPDATE productdata SET quantity = ? WHERE ID = "+id);
						
							String str = JOptionPane.showInputDialog("Enter Quantity");
							int quantity = str==null ? 1 : Integer.parseInt(str);
							
							pst.setInt(1, quantity);
							pst.executeUpdate();
							Helper.showMsg("Successfully Update");
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}else {
					Helper.showMsg("MissOp");
				}
				
				strData = new String[Helper.countRowFromSql(cnn, "productdata")][3];
				fillUpArr(strData);
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(10, 134, 599, 350);
				contentPane.add(scrollPane);
				
				
				tableModel = new DefaultTableModel(strData,strHeader)
				{
					@Override
					public boolean isCellEditable(int row,int col)
					{
						return false;
					}
				};
				table_EndedProducts = new JTable(tableModel);
				scrollPane.setViewportView(table_EndedProducts);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_1.setBounds(220, 90, 165, 34);
		contentPane.add(btnNewButton_1);
	}
	
	public void fillUpArr(String arr[][])
	{
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM productdata WHERE quantity = 0");
			int row = 0;
			while(rs.next())
			{
				arr[row][0] = Integer.toString(rs.getInt("ID"));
				arr[row][1] = rs.getString("Name");
				arr[row][2] = Double.toString(rs.getDouble("price"));
				row++;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
