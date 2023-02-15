import java.sql.*;
public class BasicTransaction {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb","root","root");
			try {
				con.setAutoCommit(false);
				String sql = "update tjournal set amount = ? where journalid = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setBigDecimal(1,new java.math.BigDecimal(150));
				ps.setString(2,"3001");
				int rows = ps.executeUpdate();
				System.out.println("effected "+rows+" rows");
				con.commit();
			} catch(SQLException ex) {
				ex.printStackTrace();
				con.rollback();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
