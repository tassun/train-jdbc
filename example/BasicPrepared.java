import java.sql.*;
public class BasicPrepared {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
			String sql = "insert into tjournal(journalid,journalname,amount) values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1,"3001");
			ps.setString(2,"Fee");
			ps.setBigDecimal(3,new java.math.BigDecimal(100));
			int rows = ps.executeUpdate();
			System.out.println("effected "+rows+" rows");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
