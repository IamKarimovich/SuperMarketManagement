package Help;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class Helper {

	public static void fillProgress(JProgressBar b)
	{
		int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                b.setValue(i + 10);
 
                // delay the thread
                Thread.sleep(1000);
                i += 30;
            }
        }
        catch (Exception e) {
        }
    }
	
	public static void showMsg(String str)
	{
		String msg;
		
		switch (str) {
		case "fillUp":
			msg = "Please fill up empty places!";
			break;
		case "Success":
			msg = "Succesful Process!";
			break;
		case "NotFoundUSer":
			msg = "User couldn't found!";
			break;
		case "NotProdFound":
			msg = "Product couldn't found!";
			break;
		case "succesAdd":
			msg = "Successfully addition!";
			break;
		case "successDelete":
			msg = "Successfully delete!";
			break;
		case "MissOp":
			msg = "Missing Operation!";
			break;
		case "successUpdate":
			msg = "Succesfully update operation!";
			break;
		case "ExistLogId":
			msg = "This Login ID is already exist!";
			break;
		default:
			msg = str;
			break;
		}
		
		JOptionPane.showMessageDialog(null, msg,"Information message", JOptionPane.INFORMATION_MESSAGE);
		
	}
	public static int askQuestion(String str)
	{
		String msg;
		
		switch (str) {
		case "sureBack":
			msg = "Are you Sure to back?";
			break;
		case "sureDelete":
			msg = "Are you sure to delete selected item?";
			break;
		case "sureLogOut":
			msg = "Are you sure to log out?";
			break;
		case "sureEdit":
			msg = "Are you sure to edit?";
			break;
		default:
			msg = str;
			break;
		}
		
		int answer = JOptionPane.showConfirmDialog(null, msg, "Information message", JOptionPane.YES_NO_OPTION);
		if(answer == JOptionPane.YES_OPTION)
		{
			return 1;
		}
		else {
			return 0;
		}
	}
	public static int countRowFromSql(DBConnection con,String tableName)
	{
		Statement st;
		int size = 0;
		try {
			Connection cn = con.connDB();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM "+tableName);
			
		        while(rs.next()){
		            size++;
		        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return size+1;
	    
	   
	}
	
	
	
	
	
}
