package DAO;

import java.sql.*;
import java.util.*;
import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    public Message addMessage(int posted_by, String message_text, long time_posted){

        try {
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, posted_by);
            ps.setString(2, message_text);
            ps.setLong(3, time_posted);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){
                int resultId = rs.getInt(1);
                return new Message(resultId, posted_by, message_text, time_posted);
            }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return null;
    }

    public ArrayList<Message> getAllMessages(){

        ArrayList<Message> messagesReturned = new ArrayList<Message>();

        try{ 
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM message");

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int resultId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");
                messagesReturned.add(new Message(resultId, postedBy, messageText, timePosted));
            }

            return messagesReturned;

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Message getMessageById(int id){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM message WHERE message_id=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int resultId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");
                return new Message(resultId, postedBy, messageText, timePosted);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public boolean removeMessageById(int id){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("DELETE FROM message WHERE message_id = ?");

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1){
                return true;
              }

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateMessageById(int id, String message){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("UPDATE message SET message_text = ? WHERE message_id = ?");

            ps.setString(1, message);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1){
                return true;
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<Message> getAllMessagesByUser(int id){
        try{
            ArrayList<Message> messagesReturned = new ArrayList<Message>();

            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM message WHERE posted_by=?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int resultId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePosted = rs.getLong("time_posted_epoch");
                messagesReturned.add(new Message(resultId, postedBy, messageText, timePosted));
            }

            return messagesReturned;

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
