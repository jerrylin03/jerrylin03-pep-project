package DAO;

import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    public Account addAccount(String username, String password){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("INSERT INTO account (username, password) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while(rs.next()){
                int resultId = rs.getInt(1);
                return new Account(resultId, username, password);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Account verifyLogin(String username, String password){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int resultId = rs.getInt("account_id");
                String resultUsername = rs.getString("username");
                String resultPassword = rs.getString("password");
                return new Account(resultId, resultUsername, resultPassword);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public Account getAccountById(int account_id){
        try{
            Connection connection = ConnectionUtil.getConnection();

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM account WHERE account_id = ?");
            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int resultId = rs.getInt("account_id");
                String resultUsername = rs.getString("username");
                String resultPassword = rs.getString("password");
                return new Account(resultId, resultUsername, resultPassword);
            }

        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }


    
}
