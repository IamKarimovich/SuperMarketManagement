package View;

import java.awt.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Help.Helper;

import javax.swing.*;

public class EnterToMarketGUI extends JFrame {

	private JPanel contentPane;
	//private static JPanel panel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnterToMarketGUI frame = new EnterToMarketGUI();
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
	public EnterToMarketGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 783, 376);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 165, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 165, 0));
		panel.setBounds(0, 0, 769, 339);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(EnterToMarketGUI.class.getResource("/View/MarketLogo.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(171, 10, 367, 128);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Fast-Ket");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Showcard Gothic", Font.PLAIN, 30));
		lblNewLabel_1.setBounds(225, 148, 290, 55);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Not Jet,but Fast \r\n");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("ZapfHumnst Ult BT", Font.BOLD | Font.ITALIC, 24));
		lblNewLabel_2.setBounds(225, 213, 290, 82);
		panel.add(lblNewLabel_2);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setForeground(new Color(192, 192, 192));
		progressBar.setBounds(10, 305, 749, 24);
		panel.add(progressBar);
		
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				fill(progressBar);
				dispose();
			}
		}); 
			
		t.start();
		
		
		
		
	}
	
	public static void fill(JProgressBar b)
    {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                b.setValue(i);
 
                // delay the thread
                Thread.sleep(50);
                i ++;
            }
        }
        catch (Exception e) {
        }
        LoginGUI lg = new LoginGUI();
		lg.setVisible(true);
		
    }

}
