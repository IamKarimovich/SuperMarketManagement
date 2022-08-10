package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Help.DBConnection;
import Help.Helper;
import Objects.Client;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class FrameOrderList extends JFrame {
	public static DBConnection cnn = new DBConnection();
	private JPanel contentPane;
	private static String arr[][] = new String[Helper.countRowFromSql(cnn, "productdata")][5];
	private static String strdate,strtime;
	private JTable table;
	private String[] shopHistoryHeader = {"No","Product name","Price","Quantity","Total Price"};
	private static Client client;
	private double 	totalPrice = 0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameOrderList frame = new FrameOrderList(arr,strdate,strtime,client);
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
	public FrameOrderList(String arr[][],String strdate,String strtime,Client client) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 546, 558);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 512, 390);
		contentPane.add(scrollPane);
		
		fillUpShopHistoryArr(arr, strdate, strtime, client.getID());
		
		DefaultTableModel tableModel = new DefaultTableModel(arr,shopHistoryHeader)
		{
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		table = new JTable(tableModel);
		table.setRowSelectionAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		
		System.out.println(strdate+" "+strtime);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = cnn.connDB();
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM shopping_data");
					while(rs.next())
					{
						if(rs.getString("shopDate").equals(strdate) && rs.getString("shopTime").equals(strtime))
						{
							if(Helper.askQuestion("sureDelete")==1)
							{
								PreparedStatement pst = con.prepareStatement("DELETE FROM shopping_data WHERE ID="+rs.getInt("ID"));
								pst.executeUpdate();
								Helper.showMsg("successDelete");
							}
							
						}
					}
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnDelete.setFont(new Font("Segoe UI Historic", Font.BOLD | Font.ITALIC, 20));
		btnDelete.setBorder(null);
		btnDelete.setBackground(new Color(255, 165, 0));
		btnDelete.setBounds(415, 20, 95, 45);
		contentPane.add(btnDelete);
		
		JButton btnShoppingDeatails = new JButton("Shopping Deatails");
		btnShoppingDeatails.setFont(new Font("Segoe UI Historic", Font.BOLD | Font.ITALIC, 20));
		btnShoppingDeatails.setBorder(null);
		btnShoppingDeatails.setBackground(new Color(255, 165, 0));
		btnShoppingDeatails.setBounds(155, 10, 206, 45);
		contentPane.add(btnShoppingDeatails);
		
		JButton btnTotal = new JButton("Total : "+totalPrice);
		btnTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnTotal.setText("Total : "+totalPrice);
			}
		});
		btnTotal.setFont(new Font("Segoe UI Historic", Font.BOLD | Font.ITALIC, 20));
		btnTotal.setBorder(null);
		btnTotal.setBackground(new Color(255, 165, 0));
		btnTotal.setBounds(155, 466, 206, 45);
		contentPane.add(btnTotal);
			
	}
	
	public void fillUpShopHistoryArr(String [][] arr,String strdate,String strTime,int id)
	{
		totalPrice = 0;
		Connection con = cnn.connDB();
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM shopping_data ");
			
			int row = 0,col = 0;
			while(rs.next())
			{
				
				if(rs.getInt("userid")==id && rs.getString("shopDate").equals(strdate) && rs.getString("shopTime").equals(strTime) )
				{
					ResultSet rs1 = st.executeQuery("SELECT * FROM productdata WHERE ID = "+rs.getInt("prodid"));
					while(rs1.next())
					{
						
							double price = rs1.getDouble("price");
							int quantity = rs.getInt("quantity");
							
							arr[row][col] = Integer.toString(row+1);

							arr[row][col+1] = rs1.getString("Name");
							arr[row][col+2] = Double.toString(rs1.getDouble("price"));
							arr[row][col+3] = Integer.toString(quantity);
							arr[row][col+4] =  Double.toString( price * quantity);
							totalPrice+=(price * quantity);
							row++;
							
						
					}
					
					
					
				}	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
}
