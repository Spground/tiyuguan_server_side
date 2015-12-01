package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * 该类主要完成的功能是查询用户和场馆的预约记录，包括过去和未来的预约记录
 */
public class RecordSql {
	private String tbl_record_vcount = "count(r.venues_id)";
	private String tbl_record_user_id = "user_id";
	private String tbl_record_v_name="v_name";
	private String tbl_record_order_start_time="order_start_time";
	private String tbl_record_order_end_time = "order_end_time";
	
	
	private Connection connection;
	
	public RecordSql()
	{
		connection = DB.createConn();
	}
	
	public JSONArray getUserRecord(String userid,Timestamp time)
	{
		JSONArray jsonarray = new JSONArray();
		//String getsql="";
		return jsonarray;
	}
	
	//获取指定时间段 场馆的预约记录
	public JSONArray getCourtRecord(String courtname,Timestamp starttime,Timestamp endtime)
	{
		JSONArray jsonarray = new JSONArray();
		String getsql = "SELECT r.user_id,r.location,r.order_start_time,r.order_end_time  FROM gym_reserve.tbl_venues v join gym_reserve.tbl_record r on v.id=r.venues_id where v.v_name=? and r.order_start_time<=? and r.order_end_time>=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1,courtname);
			getpreparedstatement.setString(2,starttime.toString());
			getpreparedstatement.setString(2,endtime.toString());
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null )
			{
				String str="";
				Timestamp time=null;
				while(resultset.next())
				{
					//创建json对象
					JSONObject jsonobject = new JSONObject();
					
					//馆名
					str = resultset.getString(tbl_record_v_name);
					jsonobject.put(tbl_record_v_name, str);
					
					//用户ID
					str = resultset.getString(tbl_record_user_id);
					jsonobject.put(tbl_record_user_id, str);
					
					//预约起始时间
					time = resultset.getTimestamp(tbl_record_order_start_time);
					jsonobject.put(tbl_record_order_start_time, time);
					
					//预约结束时间
					time = resultset.getTimestamp(tbl_record_order_end_time);
					jsonobject.put(tbl_record_order_end_time, time);
					
					jsonarray.put(jsonobject);
				}
			}
			resultset.close();
			getpreparedstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.destory();
		}
		return jsonarray;
	}
	
	public JSONArray getUserRecord(String userid,Timestamp starttime,Timestamp endtime)
	{
		JSONArray jsonarray = new JSONArray();
		String getsql = "SELECT v.v_name,r.location,r.order_start_time,r.order_end_time FROM gym_reserve.tbl_venues v "
				+ "join gym_reserve.tbl_record r on v.id=r.venues_id "
				+ "where r.user_id=? and r.order_start_time<=? and r.order_end_time>=?";
		
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1,userid);
			getpreparedstatement.setString(2,starttime.toString());
			getpreparedstatement.setString(2,endtime.toString());
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null )
			{
				String str="";
				Timestamp time=null;
				while(resultset.next())
				{
					//创建json对象
					JSONObject jsonobject = new JSONObject();
					
					//馆名
					str = resultset.getString(tbl_record_v_name);
					jsonobject.put(tbl_record_v_name, str);
					
					//预约起始时间
					time = resultset.getTimestamp(tbl_record_order_start_time);
					jsonobject.put(tbl_record_order_start_time, time);
					
					//预约结束时间
					time = resultset.getTimestamp(tbl_record_order_end_time);
					jsonobject.put(tbl_record_order_end_time, time);
					
					jsonarray.put(jsonobject);
				}
			}
			resultset.close();
			getpreparedstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.destory();
		}
		return jsonarray;
	}
	
	//获取指定时间段场馆预约数量
	public int getCountofCourtRecord(String courtname,Timestamp starttime,Timestamp endtime)
	{
		int num=0;
		String getsql = "SELECT count(r.venues_id) FROM gym_reserve.tbl_venues v join gym_reserve.tbl_record r on v.id=r.venues_id where v.v_name=? and r.order_start_time<=? and r.order_end_time>=?";
		PreparedStatement getpreparedstatement;
		try {
			getpreparedstatement = connection.prepareStatement(getsql);
			getpreparedstatement.setString(1,courtname);
			getpreparedstatement.setString(2,starttime.toString());
			getpreparedstatement.setString(2,endtime.toString());
			ResultSet resultset=getpreparedstatement.executeQuery();
			if(resultset!=null && resultset.next())
			{
				num = resultset.getInt(tbl_record_vcount);
			}
			resultset.close();
			getpreparedstatement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			this.destory();
		}
		return num;
	}
	
	public void destory()
	{
		DB.close(connection);
	}
}
