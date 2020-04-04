

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GetConnectionMysql {
    public static Connection getConnection(){
        String driver="org.postgresql.Driver";   //获取mysql数据库的驱动类
        String url="jdbc:postgresql://172.16.85.128:5432/layuiadmin?useUnicode=true&characterEncoding=UTF-8"; //连接数据库（test是数据库名）
        String name="postgres";//连接mysql的用户名
        String pwd="postgres";//连接mysql的密码
        Connection conn= null;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e1) {
            System.out.println("驱动加载失败");
            e1.printStackTrace();
        }
        try{
            conn=(Connection) DriverManager.getConnection(url,name,pwd);//获取连接对象
            System.out.println("成功连接数据库！");
            return conn;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     
    public static void closeAll(Connection conn,PreparedStatement ps,ResultSet rs){
        try{
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(ps!=null){
                ps.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn!=null){
                conn.close();
            }
        }catch(SQLException e){
            e.printStackTrace();   
        }
    }
     
    public static void main(String[] args) throws SQLException
    {
    	
    	String str = "HW03 dd";
    	System.out.println(str.substring(2, 4));
         
        Map <String,String> map = new HashMap<String, String>();
//        map.put("1", "1");
//        map.put("1", "1");
//        map.put("2", "2");
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) { 
        	sb.append("'"+key+"'"+ ",");
        } 
        if(sb.toString().length()>0)
        	System.out.println(sb.toString().substring(0, sb.toString().length()-1));
        
    }
}