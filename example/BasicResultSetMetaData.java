import java.sql.*;
public class BasicResultSetMetaData {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select * from tjournal");
			java.sql.ResultSetMetaData met = rs.getMetaData();
			int colcount = met.getColumnCount();
			while(rs.next()) {
				for(int i=1;i<=colcount;i++) {
					String name = met.getColumnName(i);
					System.out.println(name+"="+rs.getString(name));
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
