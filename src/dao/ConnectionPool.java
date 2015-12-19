package dao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Vector;

/*
 * 该文件主要实现了一个数据库连接池
 */
public class ConnectionPool {

	private static String DRIVER="com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/signin";
	private static String USERNAME = "root";
	private static String PASSWORD = "21424019";
	private String _testTable = ""; // 测试连接是否可用的测试表名，默认没有测试表
	private int initialConnections = 20; // 连接池的初始大小
	private int incrementalConnections = 5;// 连接池自动增加的大小
    private int maxConnections = 50; // 连接池最大的大小
    private Vector connectionVector = null; // 存放连接池中数据库连接的向量 , 初始时为 null
    
	/*
	 * 从连接池中取出连接
	 */
	public ConnectionPool(String jdbcDrive,String dbUrl,String dbUsername,String dbPassword)
	{
		this.DRIVER=jdbcDrive;
		this.URL=dbUrl;
		this.USERNAME=dbUsername;
		this.PASSWORD=dbPassword;
	}
	
	public ConnectionPool()
	{
		
	}
	
	//返回连接池初始大小
	public int getInitialConnections()
	{
		return this.initialConnections;
	}
	
	
	public void setInitialConnections(int initialConnections)
	{
		this.initialConnections=initialConnections;
	}
	
	//获取连接池自动增大的大小
	public int getIncrementalConnections()
	{
		return this.incrementalConnections;
	}
	
	public void setIncrementalConnections(int incrementalConnections)
	{
		this.incrementalConnections = incrementalConnections;
	}
	
	//返回连接池中的最大可用连接
	public int getMaxConnections()
	{
		return this.maxConnections;
	}
	
	public void setMaxConnections(int maxConnections)
	{
		this.maxConnections = maxConnections;
	}
	
	//创建数据库连接池
	public synchronized void createPool()throws Exception{
		if(connectionVector!=null)
		{
			return;//如果已经创建
		}
		
		//实例化JDBC Driver中指定的驱动类实例
		Driver driver =(Driver)(Class.forName(DRIVER).newInstance());
		
		DriverManager.registerDriver(driver);
		connectionVector = new Vector();
		
		createConnections(this.initialConnections);
		System.out.println("数据库创建成功");
	}
	
	private void createConnections(int numConnections)throws SQLException{
		for(int x = 0;x<numConnections;x++)
		{
			//是否连接池中的数据库连接的数量已经达到最大值 ？最大值由类成员 maxConnections
			// 指出，如果 maxConnections 为 0 或负数，表示连接数量没有限制。
			// 如果连接数己经达到最大，即退出。
			 if (this.maxConnections >0 && this.connectionVector.size() >= this.maxConnections) {
					              break;
			}
			 
			 // add a new PooledConnection object to connections vector
			 // 增加一个连接到连接池中（向量 connections 中)
			 try{
				 connectionVector.addElement(new PooledConnection(newConnection()));
			 }catch(SQLException e){
				 System.out.println("创建数据库连接失败"+e.getMessage());
			 }
			 System.out.println("数据库连接已经能够建立");
		}
	}
	
	private Connection newConnection() throws SQLException{
		Connection conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
		if(connectionVector.size()==0)
		{
			DatabaseMetaData metaData = (DatabaseMetaData) conn.getMetaData();
			int driverMaxConnections = metaData.getMaxConnections();
			if(driverMaxConnections>0&& this.maxConnections>driverMaxConnections){
				this.maxConnections = driverMaxConnections;
			}
		}
		return conn;
	}
	
	public synchronized Connection getConnection()throws SQLException{
		int i=0;
		//确保连接池已经被创建
		if(connectionVector == null)
		{
			return null;
		}
		Connection conn = getFreeConnection();
		
		while(conn == null&&i<5){   ///最长等待5*50milliseconds,最多循环5次
			try {
				wait(50);//等待50 milliseconds
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = getFreeConnection();
			i++;
		}
		return conn;
	}
	
	private Connection getFreeConnection()throws SQLException{
		
		//从连接池中获得一个可用的数据库连接
		Connection conn = findFreeConnection();
		if(conn ==null )
		{
			return null;
		}
		return conn;
	}
	
	private Connection findFreeConnection()throws SQLException{
		Connection conn =null;
		PooledConnection pConn=null;
		Enumeration enumerate = connectionVector.elements();
		while(enumerate.hasMoreElements())
		{
			pConn = (PooledConnection)enumerate.nextElement();
			 if (!pConn.isBusy()) {
				
				    // 如果此对象不忙，则获得它的数据库连接并把它设为忙
				
				    conn = pConn.getConnection();
				 
				    pConn.setBusy(true);
				
				   // 测试此连接是否可用
				 
				   if (!testConnection(conn)) {
				
				   // 如果此连接不可再用了，则创建一个新的连接，
				 
				  // 并替换此不可用的连接对象，如果创建失败，返回 null
				 
				  try {
				 
				         conn = newConnection();
				
				       } catch (SQLException e) {
				
				           System.out.println(" 创建数据库连接失败！ " + e.getMessage());
				                       return null;
				               }
				  		pConn.setConnection(conn);
				}
				 break; // 己经找到一个可用的连接，退出
				}
		}
		return conn;
	}
	
	/*
	 * 此函数返回一个数据库连接到连接池中，并把此链接置为空闲
	 * 所有使用连接池获得的数据库连接均应该在不使用它时返回它
	 */
	public synchronized void returnConnection(Connection conn)
	{
		if(connectionVector == null)
		{
			System.out.println("连接池不存在");
			return;
		}
		PooledConnection pConn = null;
		Enumeration enumerate = connectionVector.elements();
		while(enumerate.hasMoreElements()){
			pConn = (PooledConnection)enumerate.nextElement();
			
			//先找到连接池中的要返回的连接对象
			if(conn == pConn.getConnection())
			{
				pConn.setBusy(false);//置为空闲状态
				break;
			}
		}
		
	}
	
	/**
	     * 
	     * 测试一个连接是否可用，如果不可用，关掉它并返回 false
	     * 
	     * 否则可用返回 true
	     * 
	     * 
	     * 
	     * @param conn
	     *            需要测试的数据库连接
	     * 
	     * @return 返回 true 表示此连接可用， false 表示不可用
	     * 
	     */
    private boolean testConnection(Connection conn) {

       try {

	            // 判断测试表是否存在

           if (_testTable.equals("")) {
	
               // 如果测试表为空，试着使用此连接的 setAutoCommit() 方法

	               // 来判断连接否可用（此方法只在部分数据库可用，如果不可用 ,

	               // 抛出异常）。注意：使用测试表的方法更可靠
               conn.setAutoCommit(true);

            } else {// 有测试表的时候使用测试表测试

              // check if this connection is valid

             Statement stmt = (Statement) conn.createStatement();

               stmt.execute("select count(*) from " + _testTable);

           }

       } catch (SQLException e) {

           // 上面抛出异常，此连接己不可用，关闭它，并返回 false;

           closeConnection(conn);

           return false;

      }

       // 连接可用，返回 true

      return true;

   }
    
    /**
         * 
         * 关闭连接池中所有的连接，并清空连接池。
         * 
         */
    
       public synchronized void closeConnectionPool() throws SQLException {
   
            // 确保连接池存在，如果不存在，返回
   
           if (connectionVector == null) {
    
               System.out.println(" 连接池不存在，无法关闭 !");
    
                return;
   
          }
  
           PooledConnection pConn = null;
 
          Enumeration enumerate = connectionVector.elements();
  
          while (enumerate.hasMoreElements()) {
  
           pConn = (PooledConnection) enumerate.nextElement();
  
              // 如果忙，等 5 秒

            if (pConn.isBusy()) {

                 try {
					wait(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 等 5 秒
 
             }

            // 5 秒后直接关闭它

             closeConnection(pConn.getConnection());

            // 从连接池向量中删除它

          connectionVector.removeElement(pConn);

     }

       // 置连接池为空
       connectionVector = null;

   }

    
    /*
     * 关闭一个数据库连接
     */
	private void closeConnection(Connection conn)
	{
		try{
			conn.close();
		}catch(SQLException e){
			System.out.println("关闭数据库连接出错:"+e.getMessage());
		}
	}
    
    
	 /**
	     * 
	    * 
	    * 
	    * 内部使用的用于保存连接池中连接对象的类
	     * 
	     * 此类中有两个成员，一个是数据库的连接，另一个是指示此连接是否
	     * 
	     * 正在使用的标志。
	    * 
	    */

    class PooledConnection {

	       Connection connection = null;// 数据库连接
	
	      boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用
	
	       // 构造函数，根据一个 Connection 构告一个 PooledConnection 对象
	
	       public PooledConnection(Connection connection) {
	
           this.connection = connection;

	       }

	       // 返回此对象中的连接
	
	       public Connection getConnection() {
	
            return connection;
	
       }
	
       // 设置此对象的，连接

	        public void setConnection(Connection connection) {
	
           this.connection = connection;

	        }
	
	        // 获得对象连接是否忙
        public boolean isBusy() {
	
            return busy;
	
       }
	
	       // 设置对象的连接正在忙
	       public void setBusy(boolean busy) {
           this.busy = busy;
      }
	}
	
}
