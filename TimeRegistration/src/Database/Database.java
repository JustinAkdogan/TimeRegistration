package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Functions.Registration;
import GUI.Messages;

public class Database {
	
	Connection con;
	Registration registration = new Registration();

	public Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");	
		}catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public float [] fetchAllTimeRegistrationsFromTheWeek() {
		Statement st;
		ResultSet rs;
		float dayInMillis = (float) 86400000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		String userid = System.getProperty("user.name");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1; //#TODO
		//dayOfWeek = dayOfWeek--;
		int dayOfWeekMinus1 = dayOfWeek;
		dayOfWeekMinus1--;
		float [] weekTimes = new float [5];
		Time savedStartTime = null;
		Time savedEndTime = null;
		try {
			for (int i = 0; i < dayOfWeek; i++) {
				st = con.createStatement();
				rs = st.executeQuery("SELECT * FROM zeiterfassungen WHERE date='" + sdf.format(System.currentTimeMillis()-((dayOfWeekMinus1 - i) * (dayInMillis)))+"' AND userid='"+ userid +"'");
				String lastEndTime = "";
				while(rs.next()) {
					if (rs.isFirst()) {
						savedStartTime = rs.getTime("start");
					}
					if (rs.isLast()) {
						savedEndTime = rs.getTime("end");
					}
				}
				if (savedStartTime != null && savedEndTime != null) {
					weekTimes[i] = getTotalHours(savedStartTime,savedEndTime);
				}
					
			}
			return weekTimes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String fetchLastEndTimeFromToday() {
		Statement st;
		ResultSet rs;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userid = System.getProperty("user.name");
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM zeiterfassungen WHERE date='" + sdf.format(System.currentTimeMillis())+"' AND userid='"+ userid +"'");
			String lastEndTime = "";
			while(rs.next()) {
				lastEndTime = rs.getString("end").substring(0, 5);
			}
			return lastEndTime;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String [] [] fetchAllTimeRegistrationsFromToday() {
		Statement st;
		ResultSet rs;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String userid = System.getProperty("user.name");
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM zeiterfassungen WHERE date='" + sdf.format(System.currentTimeMillis())+"' AND userid='"+ userid +"'");
			String records [][] = new String [20][5]; //#TODO 
			int counter = 1;
			records[0][0] = "Projekt"; //#TODO
			records[0][1] = "Startzeit";
			records[0][2] = "Endzeit";
			records[0][3] = "Pause";
			records[0][4] = "Beschreibung";
			while(rs.next()) {
				records[counter][0] = rs.getString("projectid");
				records[counter][1] = rs.getString("start");
				records[counter][2] = rs.getString("end");
				records[counter][3] = rs.getString("pause");
				records[counter][4] = rs.getString("description");
				counter++;
			}
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public float getTotalHours(Time savedStartTime, Time savedEndTime ) {
		int savedStartHours = savedStartTime.getHours() * (60 * 60);
		int savedStartMinutes = savedStartTime.getMinutes() * 60;
		int savedStartSeconds = savedStartHours + savedStartMinutes;
		
		int savedEndHours = savedEndTime.getHours() * (60 * 60);
		int savedEndMinutes = savedEndTime.getMinutes() * 60;
		int savedEndSeconds = savedEndHours + savedEndMinutes;
		
		float differenceInSeconds = savedEndSeconds - savedStartSeconds;	
		float differenceInHours = differenceInSeconds / 3600;
		
		return differenceInHours;
	}
	
	
	
	public void insertTimeRegistration(String [] values) {
		PreparedStatement st;
		ResultSet rs;
	     try {	
			String query = "INSERT INTO zeiterfassungen (userid,projectid,start,end,pause,description,date) VALUES (?,?,?,?,?,?,?)";
			st = con.prepareStatement(query);
			st.setString(1, values[0]);
			st.setString(2, values[1]);
			st.setString(3, values[2]);
			st.setString(4, values[3]);
			st.setString(5, values[4]);
			st.setString(6, values[5]);
			st.setString(7, values[6]);
			st.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkIfUserExists() {
		String userid = System.getProperty("user.name");
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM mitarbeiter WHERE computername='"+ userid +"'");
			if (!rs.next()) {
				createNewUser();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createNewUser() {
		PreparedStatement st;
		ResultSet rs;
		String [] memberName = new String [3];
		String userid = System.getProperty("user.name");
		memberName = registration.registrationMessage();
	     try {	
			String query = "INSERT INTO mitarbeiter (forname,lastname,computername,authorization) VALUES (?,?,?,?)";
			st = con.prepareStatement(query);
			st.setString(1, memberName[0]);
			st.setString(2, memberName[1]);
			st.setString(3, userid);
			st.setString(4, memberName[2]);
			st.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String fetchForAndLastname() {
		String userid = System.getProperty("user.name");
		Statement st;
		ResultSet rs;
		String memberName= "";
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM mitarbeiter WHERE computername='"+ userid +"'");
			if (!rs.next()) {
				createNewUser();
			}
			if (rs.first()) {
				memberName = rs.getString("forname") + " " + rs.getString("lastname");
			}
			return memberName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return memberName;
	}
	
	public int getAuthorizationLevel() {
		String userid = System.getProperty("user.name");
		Statement st;
		ResultSet rs;
		int authorizationLevel = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM mitarbeiter WHERE computername='"+ userid +"'");
			if (rs.first()) {
				authorizationLevel = rs.getInt("authorization");
			}
			return authorizationLevel;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String [][] fetchAllTimeRegistrationEntries(){
		Statement st;
		ResultSet rs;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM zeiterfassungen ORDER BY userid");
			String records [][] = new String [20][7]; //#TODO 
			int counter = 0;
			while(rs.next()) {
				records[counter][0] = rs.getString("date");
				records[counter][1] = fetchForAndLastname();
				records[counter][2] = rs.getString("projectid");
				records[counter][3] = rs.getString("start");
				records[counter][4] = rs.getString("end");
				records[counter][5] = rs.getString("pause");
				records[counter][6] = rs.getString("description");
				counter++;
			}
			return records;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

