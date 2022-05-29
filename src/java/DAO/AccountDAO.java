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
}
