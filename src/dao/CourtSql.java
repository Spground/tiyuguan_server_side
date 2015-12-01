package dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*
 * 获取场地信息，实现场地信息的增删改查
 */
public class CourtSql {

	public final static String tbl_gym_name = "v_name";//体育馆名字
	public final static String tbl_gym_opentime = "open_time";//开馆时间
	public final static String tbl_gym_closetime = "close_time";//闭关时间
	public final static String tbl_gym_num = "num";//数量
	public final static String tbl_gym_charge = "charge";//价格
	public final static String tbl_gym_isopen= "is_open";//是否开放
	public final static String tbl_gym_id="id";
	
	private Connection connection;
	public CourtSql()
	{
		connection = DB.createConn();
	}
	
	public int setOpenTime(Timestamp opentime,String courtname)
	{
		int num=0;
		String sql="UPDATE `gym_reserve`.`tbl_venues` SET `open_time`=? WHERE `v_name`=?";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, opentime.toString());
			prestatement.setString(2, courtname);
			num=prestatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public int setCloseTime(Timestamp closetime,String courtname)
	{
		int num=0;
		String sql="UPDATE `gym_reserve`.`tbl_venues` SET `close_time`=? WHERE `v_name`=?";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, closetime.toString());
			prestatement.setString(2, courtname);
			num=prestatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public int setCourtnum(int num,String courtname)
	{
		int row=0;
		String sql="UPDATE `gym_reserve`.`tbl_venues` SET `num`=? WHERE `v_name`=?";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, String.valueOf(num));
			prestatement.setString(2, courtname);
			num=prestatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	
	public int setCourtcharge(int charge,String courtname)
	{
		int num=0;
		String sql="UPDATE `gym_reserve`.`tbl_venues` SET `charge`=? WHERE `v_name`=?";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, String.valueOf(charge));
			prestatement.setString(2, courtname);
			num=prestatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public int setCourtissopen(int isopen,String courtname)
	{
		int num=0;
		String sql="UPDATE `gym_reserve`.`tbl_venues` SET `is_open`=? WHERE `v_name`=?";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			prestatement.setString(1, String.valueOf(isopen));
			prestatement.setString(2, courtname);
			num=prestatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return num;
	}
	
	public int deleteCourt(String courtname)
	{
		int row=0;
		
		String sql1="delete FROM gym_reserve.tbl_record where `venues_id`=?";
		String sql2="delete FROM gym_reserve.tbl_venues where `v_name`=?";
		String venues_id = getIdByCourtname(courtname);
		try {
			
			System.out.println("v_id "+venues_id);
			PreparedStatement prestatement1 = connection.prepareStatement(sql1);
			PreparedStatement prestatement2 = connection.prepareStatement(sql2);
			prestatement1.setString(1,venues_id);
			prestatement2.setString(1, courtname);
			row=prestatement1.executeUpdate();
			row=prestatement2.executeUpdate();
			System.out.println("ROW "+row);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	
	//获取各个场馆的基本信息，例如馆名，
	public JSONArray getCourtinfo(){
		JSONArray jsonarray = new JSONArray();
		String sql = "SELECT `v_name`,`open_time`,`close_time`,`num`,`charge`,`is_open`  FROM gym_reserve.tbl_venues";
		try {
			PreparedStatement prestatement = connection.prepareStatement(sql);
			ResultSet rs=prestatement.executeQuery();
			if(rs!=null)
			{
				String str="";
				Timestamp time=null;
				int hour=0;
				while(rs.next())
				{
					//创建json对象
					JSONObject jsonobject = new JSONObject();
					
					//馆名
					str = rs.getString(tbl_gym_name);
					jsonobject.put(tbl_gym_name, str);
					
					//开馆时间
					time = rs.getTimestamp(tbl_gym_opentime);
					hour = time.getHours();
					jsonobject.put(tbl_gym_opentime, hour+":00");
					
					//闭馆时间
					time = rs.getTimestamp(tbl_gym_closetime);
					hour=time.getHours();
					jsonobject.put(tbl_gym_closetime, hour+":00");
					
					//获取资源数目
					str = rs.getString(tbl_gym_num);
					jsonobject.put(tbl_gym_num, str);
					
					//获取价格
 					str = rs.getString(tbl_gym_charge);
 					jsonobject.put(tbl_gym_charge, str);
 					
 					//是否开放
 					str = rs.getString(tbl_gym_isopen);
 					if(str.equalsIgnoreCase("1"))
 					{
 						str="YES";
 					}
 					else
 					{
 						str="NO";
 					}
 					jsonobject.put(tbl_gym_isopen, str);
 					
 					jsonarray.put(jsonobject);
				}
				System.out.println(jsonarray.toString());
				prestatement.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonarray;
	}
	
	//获取场馆的资源数量
	public int getNumofCourt(String courtname)
	{
		int number=0;
		String getsql = "select `num` from gym_reserve.tbl_venues where v_name=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1, courtname);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				number = resultset.getInt(tbl_gym_num);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return number;
	}
	
	public float getChargeofCourt(String courtname)
	{
		float charge=0;
		String getsql = "select `charge` from gym_reserve.tbl_venues where v_name=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1, courtname);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				charge = resultset.getInt(tbl_gym_charge);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return charge;
	}
	
	public int getOpentime(String courtname)
	{
		int opentime=0;
		Timestamp time=null;
		String getsql = "select `open_time` from gym_reserve.tbl_venues where v_name=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1, courtname);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				time = resultset.getTimestamp(tbl_gym_opentime);
				opentime=time.getHours();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return opentime;
	}
	
	public int getClosetime(String courtname)
	{
		int closetime=0;
		Timestamp time=null;
		String getsql = "select `close_time` from gym_reserve.tbl_venues where v_name=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1, courtname);
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				time = resultset.getTimestamp(tbl_gym_closetime);
				closetime=time.getHours();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return closetime;
	}
	
	public String getIdByCourtname(String courtname)
	{
		int id=0;
		String strid="";
		
		//PreparedStatement getpreparedstatement;
		Statement statement;
		try {
			//String str = new String(courtname.getBytes("UTF-8"), "utf8_unicode_ci");
			String getsql = "select * from gym_reserve.tbl_venues where `v_name`="+"'"+courtname+"';"; 
			System.out.println("SQL"+getsql);
			statement = connection.createStatement();
		//	getpreparedstatement.setString(1, courtname);
		//	getpreparedstatement.
			System.out.println("getname "+courtname);
			ResultSet resultset=statement.executeQuery(getsql);
			if(resultset!=null && resultset.next())
			{
				id = resultset.getInt(tbl_gym_id);
				System.out.println("id "+id);
				strid=String.valueOf(id);
			}
			else
			{
				if(resultset==null)
				System.out.println("result is null");
				else
				{
					System.out.println("resultset not has next");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return strid;
	}
	public void destory()
	{
		DB.close(connection);
	}
}
