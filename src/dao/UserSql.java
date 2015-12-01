package dao;

import java.sql.*;

//import net.sf.json.JSONArray;

/**
 * 用户数据库操作，获取用户的用户名、密码、预约记录
 */
public class UserSql {
	private Connection conn;
	 
	private String tbl_user_id="id";
	private String tbl_user_name="name";
	private String tbl_user_role="role";//身份
	//private String tbl_user_cookie="cookie";
	private String tbl_user_password="password";//密码
	private String tbl_user_creditworthiness="creditworthiness";//信誉值
	

	public UserSql()
	{
		System.out.println("UserSql 28");
		conn =  DB.createConn();//获取数据库连接
	}
	public String getUseridbyCookie(String cookie)
	{
		String userid = null;
		String getsql="SELECT `id` FROM gym_reserve.tbl_user where `cookie`=?";
		try {
			PreparedStatement getpreparedstatement =conn.prepareStatement(getsql);
			getpreparedstatement.setString(1,cookie);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null&&resultset.next())
			{
				userid=resultset.getString(tbl_user_id);
			}
			getpreparedstatement.close();
			resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userid;
	}
	
	public String getPassword(String id)
	{
		String password=null;
		String getsql="SELECT `password` FROM gym_reserve.tbl_user where `id`=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = conn.prepareStatement(getsql);
			getpreparedstatement.setString(1, id);
			System.out.println(getpreparedstatement.toString());
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
				{
					password = resultset.getString(tbl_user_password);
					System.out.println("UserSql 73");
				}
			getpreparedstatement.close();
			resultset.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return password;
	}
	
	public boolean setCookie(String id,String cookie)
	{
		boolean result=false;
		int row=0;
		try {
			String setsql="update  `gym_reserve`.`tbl_user` SET `cookie`=? where `id`=?";
			PreparedStatement setpreparedstatement = conn.prepareStatement(setsql);
			setpreparedstatement.setString(1,cookie);
			setpreparedstatement.setString(2,id);
			row=setpreparedstatement.executeUpdate();
			if(row>0)
			{
				result=true;
			}
			setpreparedstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean setPassword(String id,String newpassword)
	{
		boolean result=false;
		int row=0;
		try {
			String setsql="update  `gym_reserve`.`tbl_user` SET `password`=? where 'id'=?";
			PreparedStatement setpreparedstatement = conn.prepareStatement(setsql);
			setpreparedstatement.setString(1,newpassword);
			setpreparedstatement.setString(2,id);
			row=setpreparedstatement.executeUpdate();
			if(row>0)
			{
				result=true;
			}
			setpreparedstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String getRole(String id)
	{
		String role=null;
		String getsql="SELECT `role` FROM gym_reserve.tbl_user where `id`=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = conn.prepareStatement(getsql);
			getpreparedstatement.setString(1, id);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				role = resultset.getString(tbl_user_role);
			}
			getpreparedstatement.close();
			resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return role;
	}
	
	public int getCreditworthiness(String id)
	{
		int creditworthiness=0;
		PreparedStatement getpreparedstatement;
		String getsql = "SELECT `creditworthiness` FROM gym_reserve.tbl_user where `name`=?";
		try {
			getpreparedstatement = conn.prepareStatement(getsql);
			getpreparedstatement.setString(1, id);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				creditworthiness = resultset.getInt(tbl_user_creditworthiness);
			}
			getpreparedstatement.close();
			resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return creditworthiness;
	}
	
	public String getusernameByid(String id)
	{
		String username=null;
		String getsql="SELECT `name` FROM gym_reserve.tbl_user where `id`=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = conn.prepareStatement(getsql);
			getpreparedstatement.setString(1, id);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				username = resultset.getString(tbl_user_name);
			}
			getpreparedstatement.close();
			resultset.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}
	
	
	
	public void destory()
	{
		DB.close(conn);
	}
	
}
