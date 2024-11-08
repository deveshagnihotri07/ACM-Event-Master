import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.Scanner;

import javax.imageio.spi.RegisterableService;
import javax.security.auth.login.LoginContext;
import javax.swing.text.View;

import com.sun.javafx.scene.EnteredExitedHandler;

class TalentNext {
	public static Connection connectDB() {
		Connection con = null;
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/acmevent?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Sorry Database Connectivity Issue...");
		}
		return con;
	}
	public static void displayHome()throws Exception {
		System.out.println("=============================================================================================================================");
		System.out.println("\t\t\t\t\t\t ACM Event Organiser");
		System.out.println("=============================================================================================================================");
		System.out.println("\n\n 1) Login");
		System.out.println("\n\n 2) Regiter");
		System.out.println("\n\n    Select any one...");
		System.out.println("=============================================================================================================================");
		int input = inScanner.nextInt();
		if(input == 1)
		{
			login();
			
		}
		else if(input == 2) {
			register();
			System.out.println("\n Press enter key to go to home page...");
			System.in.read();
			displayHome();
		}
		else {
			System.out.println("Wrong input Try again...\n Press enter key to go to home page...");
			System.in.read();
			displayHome();
		}
	}
	public static void login() throws Exception {
		System.out.println("Enter your User ID:");
		int uid = inScanner.nextInt();
		System.out.println("Enter your password:");
		String pass = inScanner.next();
		String querry = "select pass from user where uid = "+uid;
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		
		String rpass = "";
		while(rs.next())
			rpass = rs.getString("pass");
		if(pass.equals(rpass))
			profileHome(uid);
		else {
			System.out.println("Your are not Registered... \n\n 1) Register \n\n 2) Home page");
			int t = inScanner.nextInt();
			if(t == 2)
				displayHome();
			else if(t == 1)
				register();
			else {
				System.out.println("Wrong input Try again...\n Press enter key to go to home page...");
				System.in.read();
				displayHome();
			}
		}
	}
	public static boolean register() throws Exception
	{
		String querry = "insert into user values (?,?,?,?,?,?)";
		PreparedStatement pStatement = con.prepareStatement(querry);
		System.out.println("Enter your User ID:");
		pStatement.setInt(1, inScanner.nextInt());
		System.out.println("Enter your Name:");
		pStatement.setString(2, inScanner.next());
		System.out.println("Enter your password:");
		pStatement.setString(3, inScanner.next());
		System.out.println("Enter your Mobile Number:");
		pStatement.setInt(4, inScanner.nextInt());
		System.out.println("Enter your email:");
		pStatement.setString(5, inScanner.next());
		System.out.println("Enter your position of responsibility in ACM:");
		pStatement.setString(6, inScanner.next());
		
		int t = pStatement.executeUpdate();
		if(t > 0)
		{
			System.out.print("Successfully Registered :)");
			
			return true;
		}
		else {
			System.out.print("Sorry you are not Registered :(");
			return false;
		}
	}
	public static void viewProfile(int uid)throws Exception {
		String querry = "select * from user where uid = "+uid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		while(rs.next()) {
			System.out.println("=============================================================================================================================");
			System.out.println("\t\t\t\t\t\t ACM Event Organiser");
			System.out.println("=============================================================================================================================");
			System.out.println("                                                                                                Welcome "+rs.getString("name")+"   ("+uid+")");
			System.out.println("\n\nYour Profile:");
			System.out.println("=============================================================================================================================");
			System.out.println("Name: "+rs.getString("name")+"\n");
			System.out.println("User ID: "+rs.getString("uid")+"\n");
			System.out.println("Mobile Number: "+rs.getString("mno")+"\n");
			System.out.println("Email ID: "+rs.getString("email")+"\n");
			System.out.println("Membership type: "+rs.getString("memership")+"\n");
			System.out.println("=============================================================================================================================");
			System.out.println("\n Press enter key to go back...");
			System.in.read();
		}
	}
	public static void eventList(int uid)throws Exception {
		String q = "select name from events";
		Statement st = con.createStatement();
		ResultSet rs1 = st.executeQuery(q);
		
		String querry = "select name from user where uid = "+uid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		String name = "";
		while(rs.next())
			name = rs.getString("name");
			System.out.println("=============================================================================================================================");
			System.out.println("\t\t\t\t\t\t ACM Event Organiser");
			System.out.println("=============================================================================================================================");
			System.out.println("                                                                                                Welcome "+name+"   ("+uid+")");
			System.out.println("\n\nEvents List: ");
			System.out.println("=============================================================================================================================");
			int i =1;
			while(rs1.next()) {
				System.out.println(i+") "+rs1.getString("name")+"\n");
				i++;
			}
			System.out.println("=============================================================================================================================");
			System.out.println("\n Press enter key to go back...");
			System.in.read();
	}
	public static void eventDetails(int uid, String na)throws Exception {
		String q = "select * from events where name ='"+na+"'";
		Statement st = con.createStatement();
		ResultSet rs1 = st.executeQuery(q);
		
		String querry = "select name from user where uid = "+uid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		String n = "";
		int f = 0;
		while(rs.next())
			n = rs.getString("name");
			System.out.println("=============================================================================================================================");
			System.out.println("\t\t\t\t\t\t ACM Event Organiser");
			System.out.println("=============================================================================================================================");
			System.out.println("                                                                                                Welcome "+n+"   ("+uid+")");
			while(rs1.next()) {
				f = 1;
				System.out.println("Name: "+rs1.getString("name")+"\n");
				System.out.println("Event type: "+rs1.getString("type")+"\n");
				System.out.println("Date: "+rs1.getDate("date")+"\n");
				System.out.println("Description: \n"+rs1.getString("dis")+"\n");
			}
			if(f== 0)
				System.out.println("No Such Event Found :(");
			System.out.println("=============================================================================================================================");
			System.out.println("\n Press enter key to go back...");
			System.in.read();
	}
	public static void addEvent(int uid)throws Exception {
		String q = "insert into events values (?,?,?,?)";
		PreparedStatement pStatement = con.prepareStatement(q); 
		
		String querry = "select name from user where uid = "+uid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		String n = "";
		while(rs.next())
			n = rs.getString("name");
			System.out.println("=============================================================================================================================");
			System.out.println("\t\t\t\t\t\t ACM Event Organiser");
			System.out.println("=============================================================================================================================");
			System.out.println("                                                                                                Welcome "+n+"   ("+uid+")");
			System.out.println("\nEnter Event Details:");
			System.out.println("=============================================================================================================================");
			System.out.println("Enter Event name: ");
			pStatement.setString(1, inScanner.next());
			System.out.println("Enter Event type: ");
			pStatement.setString(2, inScanner.next());
			System.out.println("Enter Event date(in format YYYY-MM-DD): ");
			pStatement.setString(3, inScanner.next());
			System.out.println("Enter Event Description: ");
			inScanner.nextLine();
			pStatement.setString(4, inScanner.nextLine());
			
			int t = pStatement.executeUpdate();
			if(t > 0)
			{
				System.out.print("Event added Successfully :)\n");
			}
			else {
				System.out.print("Sorry event not added :(");
			}
			System.out.println("=============================================================================================================================");
			System.out.println("\n Press enter key to get back to Dashboard...");
			System.in.read();
	}
	public static void profileHome(int uid)throws Exception {
		String querry = "select name from user where uid = "+uid;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(querry);
		String name = "";
		while(rs.next())
			name = rs.getString("name");
		int ch = 0;
		while(ch != 6) {
			System.out.println("=============================================================================================================================");
			System.out.println("\t\t\t\t\t\t ACM Event Organiser");
			System.out.println("=============================================================================================================================");
			System.out.println("                                                                                                Welcome "+name+"   ("+uid+")");
			System.out.println("\n\n 1) View Profile");
			System.out.println("\n\n 2) Event List");
			System.out.println("\n\n 3) Add Event");
			System.out.println("\n\n 4) Event Details");
			System.out.println("\n\n 5) Log out");
			System.out.println("\n\n 6) Exit");
			System.out.println("\n\n    Select any one...");
			System.out.println("=============================================================================================================================");
			ch = inScanner.nextInt();
			switch (ch) {
			case 1:
				viewProfile(uid);
				break;
			case 2:
				eventList(uid);
				break;
			case 3:
				addEvent(uid);
				break;
			case 4:
				System.out.println("Enter Event name");
				inScanner.nextLine();
				String n = inScanner.nextLine();
				eventDetails(uid, n);
				break;
			case 5:
				displayHome();
				ch = 6;
				break;
			case 6:
				System.out.println("Exiting Thank you :)  ");
				break;

			default:
				System.out.println("Wrong input Try again...");
				break;
			}
		}
	}
	public static Scanner inScanner = new Scanner(System.in); 
	public static Connection con;
	public static void main(String[] args) throws Exception {
		 con = connectDB();
		 displayHome();
		
	}
}
