import java.sql.*;
public class BasicCallable {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
			String sql = "{call GetJournal(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1,"1");
			ResultSet rs = cs.executeQuery();
			while(rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				java.math.BigDecimal amt = rs.getBigDecimal(3);
				System.out.println("id="+id+",name="+name+",amt="+amt);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
