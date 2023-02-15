
import java.sql.*;
public class BasicJDBC {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
			Statement stm = con.createStatement();
			ResultSet rs = stm.executeQuery("select journalname,amount from tjournal");
			while(rs.next()) {
				String name = rs.getString("journalname");
				java.math.BigDecimal amt = rs.getBigDecimal("amount");
				System.out.println(name+"="+amt);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
