package com.scsu.ia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DBManager2 {
	
    public JsonObject getInfo(String userId,String userPass) {
        User user = new User();
        Connection conn = null;            
        Statement stmt = null;     
        String sql = null;
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        JsonArray ja = new JsonArray();
 
        try {      
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            conn = ((DataSource) ctx.lookup("jdbc/mysql")).getConnection(); 
            stmt = conn.createStatement();
            sql = "select * from users where user_id = '"+ userId +"' and user_pass = '"+userPass+"';"; 
            
            ResultSet rs = stmt.executeQuery(sql);
 
            while(rs.next()){ 
            	user.setUserId(rs.getString("user_id").trim());
            	user.setUserPass(rs.getString("user_pass").trim());
            	user.setType(rs.getString("user_type").trim());
                user.setAccountBalance(rs.getString("account_balance") == null ? new Double(0) : Double.parseDouble(rs.getString("account_balance").trim()));
                JsonElement je = gson.toJsonTree(user);
                ja.add(je);
            }
            if(user.getUserId() == null){
                jo.addProperty("success", false);
            }
            else {
                jo.addProperty("success", true);
            }
            jo.add("users", ja);
//            System.out.println(jo.toString());
            rs.close();                                                               
            stmt.close();                                                             
            stmt = null;                                                              
 
            conn.close();                                                             
            conn = null; 
        }catch(Exception e){System.out.println(e);
        }finally {
            if (stmt != null) {                                            
                try {                                                         
                    stmt.close();                                                
                } catch (SQLException sqlex) {                                
                    // ignore -- as we can't do anything about it here           
                }                                                             
                stmt = null;                                            
            }                                                        
 
            if (conn != null) {                                      
                try {                                                   
                    conn.close();                                          
                } catch (SQLException sqlex) {                          
                    // ignore -- as we can't do anything about it here     
                }                                                       
 
                conn = null;                                            
            }                                                        
        }              
        return jo;
    }
    
    public JsonObject getInfoProtection(String userId,String userPass) {
        User user = new User();
        Connection conn = null;            
        PreparedStatement stmt = null;     
        String sql = null;
        Gson gson = new Gson();
        JsonObject jo = new JsonObject();
        JsonArray ja = new JsonArray();
 
        try {      
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            conn = ((DataSource) ctx.lookup("jdbc/mysql")).getConnection(); 
 
            sql = "select * from users where user_id = ? and user_pass = ?;"; 
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId.trim());
            stmt.setString(2, userPass.trim());
            ResultSet rs = stmt.executeQuery();
 
            while(rs.next()){ 
            	user.setUserId(rs.getString("user_id").trim());
            	user.setUserPass(rs.getString("user_pass").trim());
            	user.setType(rs.getString("user_type").trim());
                user.setAccountBalance(rs.getString("account_balance") == null ? new Double(0) : Double.parseDouble(rs.getString("account_balance").trim()));
//                System.out.println(user);
                JsonElement je = gson.toJsonTree(user);
                ja.add(je);
            }
            if(user.getUserId() == null){
                jo.addProperty("success", false);
            }
            else {
                jo.addProperty("success", true);
            }
            
            jo.add("users", ja);
//            System.out.println(jo.toString());
            rs.close();                                                               
            stmt.close();                                                             
            stmt = null;                                                              
 
            conn.close();                                                             
            conn = null; 
        }catch(Exception e){System.out.println(e);
        }finally {
            if (stmt != null) {                                            
                try {                                                         
                    stmt.close();                                                
                } catch (SQLException sqlex) {                                
                    // ignore -- as we can't do anything about it here           
                }                                                             
                stmt = null;                                            
            }                                                        
 
            if (conn != null) {                                      
                try {                                                   
                    conn.close();                                          
                } catch (SQLException sqlex) {                          
                    // ignore -- as we can't do anything about it here     
                }                                                       
 
                conn = null;                                            
            }                                                        
        }              
        return jo;
    }

}
