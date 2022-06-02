/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DBconnect;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Linh
 */
public class AccountDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    
    public Account checkLogin(String email, String pass) {
        try {
            String sql = "SELECT email, password FROM account where Email =? and Password =?";
            conn = new DBconnect().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            rs = ps.executeQuery();
               while (rs.next()) {
                return new Account(rs.getString(1),
                        rs.getString(2));

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    
    public ArrayList<Account> getAccounts() {
        ArrayList<Account> ar = new ArrayList<>();
        try {
            String sql = "SELECT *"
                    + "  FROM Account\n";
            conn = new DBconnect().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setEmail(rs.getString("email"));
                acc.setPassword(rs.getString("password"));
                ar.add(acc);
            }
        } catch (Exception e) {
        }
        return ar;
    }
    
    public boolean checkEmail(String email) {
        ArrayList<Account> array = new ArrayList<>();
        array = getAccounts();
        int check = 0;
        for (Account a : array) {
            if (a.getEmail().equals(email)) {
                check = 1;
                break;
            }
        }
        return check == 0;
    }
    
    public void createAccount(Account account) {
        ArrayList<Account> array = new ArrayList<>();
        array = getAccounts();
        int check = 0;
        for (Account a : array) {
            if (a.getEmail().equals(account.getEmail())) {
                check = 1;
                break;
            }
        }
        if (check == 0) {
            try {
                String sql = "INSERT INTO Account (email, password)\n"
                        + " VALUES ("
                        + "?,"
                        + "?);";
                conn = new DBconnect().getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, account.getEmail());
                ps.setString(2, account.getPassword());
                ps.executeUpdate();
            }  catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("This email has already existed!");
        }
    }
}
