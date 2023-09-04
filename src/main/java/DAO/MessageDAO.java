package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    Connection connection = ConnectionUtil.getConnection();
    
    public Message createMessage (Message message){
        try{
            String sql1 = "insert into Message (posted_by,message_text,time_posted_epoch) values (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(),message.getMessage_text(),message.getTime_posted_epoch());
            }
       
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "Select * from Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add(message);
             }
        } catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return messages;
     }

    public Message getMessageId(int message_id){
        try{
            String sql = "select * from Message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
     }

    public Message deleteMessageId(int message_id){
        try{
            Message mg = getMessageId(message_id);
            if(mg != null){
                String sql = "delete from Message where message_id= ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, message_id);
                int rs = preparedStatement.executeUpdate();
               if(rs == 1){
                    return mg;
                }
            }
         }catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
    }

    public Message updateMessageId(int message_id,String message_text){
        try{
            Message mg = getMessageId(message_id);
            if(mg!=null){
                String sql = "update Message set message_text = ? where message_id =?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1,message_text );
                preparedStatement.setInt(2, message_id);
                int re = preparedStatement.executeUpdate();
                if(re==1){
                    Message mgNew =getMessageId(message_id);
                    return mgNew;
                }
             }
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
       return null;
    }

    public List<Message> getAllMessagesAccountId(int account_id){
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "select * from Message where posted_by = ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message =new Message(rs.getInt("message_id"),rs.getInt("posted_by"),rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                messages.add( message);
            }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
            return messages;
    }

}
