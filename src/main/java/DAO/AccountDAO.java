package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
    Connection connection = ConnectionUtil.getConnection();

    public Account insertAccount(Account account){
        try{
            String sql = "select * from Account where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.getRow() == 0){
                String sql1 = "insert into Account (username,Password) values(?,?)";
                preparedStatement = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,account.getUsername());
                preparedStatement.setString(2,account.getPassword());
        
                preparedStatement.executeUpdate();
                ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = (int) pkeyResultSet.getLong(1);
                    return new Account(generated_account_id, account.getUsername(),account.getPassword());
                }
            } else{
                return null;
            }
                    
        } catch(SQLException e){
                System.out.println(e.getMessage());
        }
        
        return null;
    }

    public Account loginAccount(Account account){
        try{
            String sql = "select * from Account where username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet pkeyResultSet = preparedStatement.executeQuery();
            if(pkeyResultSet.next()){
                int account_id = (int) pkeyResultSet.getInt(1);
                return new Account(account_id, account.getUsername(),account.getPassword());
            }
            else{
                return null;
            }
               
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
