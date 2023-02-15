
public class TTransaction {
	private String transid;
	private String userid;
	private java.sql.Date transdate;
	private String journalid;
	private int transno;
	private java.math.BigDecimal amount;
	private String remark;
	private java.sql.Timestamp transtime;
	private java.sql.Date editdate;
	private java.sql.Time edittime;
	private String action;
	
	public TTransaction() {
		super();
	}

	public static java.sql.Connection getConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/mydb";
		String user = "root";
		String password = "root";
		Class.forName(driver);		
		return java.sql.DriverManager.getConnection(url,user,password);
	}

	public static void main(String[] args) {
		//java TTransaction -act create -tid t001 -uid tester -jid 1001 -amt 100 -no 1
		//java TTransaction -act retrieve -tid t001
		//java TTransaction -act update -tid t001 -jid 101 -amt 120
		//java TTransaction -act delete -tid t001
		try {
			if(args.length>0) {
				TTransaction trx = new TTransaction();
				trx.readArguments(args);
				String act = trx.getAction();
				if(act!=null) {
					try(java.sql.Connection connection = TTransaction.getConnection()) {
						if("create".equalsIgnoreCase(act)) {
							int rows = trx.create(connection);
							System.out.println(act+": effected "+rows+" rows");
						} else if("retrieve".equalsIgnoreCase(act)) {
							int rows = trx.retrieve(connection);
							if(rows>0) {
								System.out.println(act+": "+trx);
							} else {
								System.out.println(act+": not found");
							}
						} else if("update".equalsIgnoreCase(act)) {
							int rows = trx.update(connection);
							System.out.println(act+": effected "+rows+" rows");
						} else if("delete".equalsIgnoreCase(act)) {
							int rows = trx.delete(connection);
							System.out.println(act+": effected "+rows+" rows");							
						} else {
							System.out.println("Unknown action "+act);
						}
					}
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public int create(java.sql.Connection connection) throws Exception {		
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ttransaction(transid,userid,transdate,journalid,transno,amount,remark,transtime,editdate,edittime) ");
		sql.append("values(?,?,?,?,?,?,?,?,?,?) ");
		try(java.sql.PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setString(1,getTransid());
			ps.setString(2,getUserid());
			ps.setDate(3,getTransdate());
			ps.setString(4,getJournalid());
			ps.setInt(5,getTransno());
			ps.setBigDecimal(6,getAmount());
			ps.setString(7,getRemark());
			ps.setTimestamp(8,getTranstime());
			ps.setDate(9,getEditdate());
			ps.setTime(10,getEdittime());
			return ps.executeUpdate();
		}
	}
	
	public int delete(java.sql.Connection connection) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ttransaction where transid = ? ");
		try(java.sql.PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setString(1,getTransid());
			return ps.executeUpdate();
		}
	}
	
	public String getAction() {
		return action;
	}
	
	public java.math.BigDecimal getAmount() {
		return amount;
	}
	
	public java.sql.Date getEditdate() {
		if(editdate==null) return new java.sql.Date(System.currentTimeMillis());
		return editdate;
	}
	
	public java.sql.Time getEdittime() {
		if(edittime==null) return new java.sql.Time(System.currentTimeMillis());
		return edittime;
	}

	public String getJournalid() {
		return journalid;
	}
	
	public String getRemark() {
		return remark;
	}

	public java.sql.Date getTransdate() {
		if(transdate==null) transdate = new java.sql.Date(System.currentTimeMillis());
		return transdate;
	}

	public String getTransid() {
		if(transid==null) return java.util.UUID.randomUUID().toString();
		return transid;
	}

	public int getTransno() {
		return transno;
	}

	public java.sql.Timestamp getTranstime() {
		if(transtime==null) return new java.sql.Timestamp(System.currentTimeMillis());
		return transtime;
	}

	public String getUserid() {
		return userid;
	}

	public void readArguments(String[] args) {
		if(args!=null) {
			for(int i=0,isz=args.length;i<isz;i++) {
				String para = args[i];
				if(para.equalsIgnoreCase("-tid")) {
					if(args.length>(i+1)) {
						setTransid(args[i+1]);
					}
				} else if(para.equalsIgnoreCase("-uid")) {
					if(args.length>(i+1)) {
						setUserid(args[i+1]);
					}
				} else if(para.equalsIgnoreCase("-date")) { //in format yyyy-MM-dd
					if(args.length>(i+1)) {
						setTransdate(java.sql.Date.valueOf(args[i+1]));
					}
				} else if(para.equalsIgnoreCase("-jid")) { //journal id
					if(args.length>(i+1)) {
						setJournalid(args[i+1]);
					}
				} else if(para.equalsIgnoreCase("-no")) { //transaction no.
					if(args.length>(i+1)) {
						setTransno(Integer.parseInt(args[i+1]));
					}
				} else if(para.equalsIgnoreCase("-amt")) { 
					if(args.length>(i+1)) {
						setAmount(new java.math.BigDecimal(args[i+1]));
					}
				} else if(para.equalsIgnoreCase("-rem")) { 
					if(args.length>(i+1)) {
						setRemark(args[i+1]);
					}
				} else if(para.equalsIgnoreCase("-act")) { 
					if(args.length>(i+1)) {
						setAction(args[i+1]);
					}
				}
			}			
		}
	}

	public int retrieve(java.sql.Connection connection) throws Exception {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ttransaction where transid = ? ");
		try(java.sql.PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setString(1,getTransid());
			try(java.sql.ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					result++;
					setUserid(rs.getString("userid"));
					setTransdate(rs.getDate("transdate"));
					setJournalid(rs.getString("journalid"));
					setTransno(rs.getInt("transno"));
					setAmount(rs.getBigDecimal("amount"));
					setRemark(rs.getString("remark"));
					setTranstime(rs.getTimestamp("transtime"));
					setEditdate(rs.getDate("editdate"));
					setEdittime(rs.getTime("edittime"));
				}
			}
		}
		return result;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}

	public void setEditdate(java.sql.Date editdate) {
		this.editdate = editdate;
	}

	public void setEdittime(java.sql.Time edittime) {
		this.edittime = edittime;
	}

	public void setJournalid(String journalid) {
		this.journalid = journalid;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTransdate(java.sql.Date transdate) {
		this.transdate = transdate;
	}

	public void setTransid(String transid) {
		this.transid = transid;
	}

	public void setTransno(int transno) {
		this.transno = transno;
	}

	public void setTranstime(java.sql.Timestamp transtime) {
		this.transtime = transtime;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String toString() {
		return super.toString()+"{transid="+transid+",userid="+userid+",transdate="+transdate+",journalid="+journalid+",transno="+transno+",amount="+amount+",remark="+remark+",transtime="+transtime+",editdate="+editdate+",edittime="+edittime+"}";
	}

	public int update(java.sql.Connection connection) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("update ttransaction set journalid = ?, amount = ?, remark = ? , ");
		sql.append("editdate = ?, edittime = ? ");
		sql.append("where transid = ? ");
		try(java.sql.PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.setString(1,getJournalid());
			ps.setBigDecimal(2,getAmount());
			ps.setString(3,getRemark());
			ps.setDate(4,getEditdate());
			ps.setTime(5,getEdittime());
			ps.setString(6,getTransid());
			return ps.executeUpdate();
		}
	}
}
