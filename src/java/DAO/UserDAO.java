/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DBcontext.DBcontext;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Linh
 */
public class UserDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User checkLogin(String email, String password) {
        try {
            String sql = "SELECT email, password FROM Users where email =? and password =?";
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11));

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public User checkStatusAndRole(String email) {
        User user = null;
        try {
            String sql = "SELECT role, status FROM Users where email =? ";
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(
                        rs.getString(1),
                        rs.getString(2));

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return user;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> ar = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Users ";
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("fullName"));
                u.setGender(rs.getInt("gender"));
                u.setDob(rs.getDate("dob"));
                u.setEmail(rs.getString("email"));
                u.setAddress(rs.getString("address"));
                u.setAvatar(rs.getString("avatar"));
                u.setPhoneNumber(rs.getString("phoneNumber"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                ar.add(u);
            }
        } catch (Exception e) {
        }
        return ar;
    }

    public boolean checkEmail(String email) {
        ArrayList<User> array = getUsers();
        int check = 0;
        for (User u : array) {
            if (u.getEmail().equals(email)) {
                check = 1;
                break;
            }
        }
        return check == 0;
    }

    public void createUser(User user) {
        ArrayList<User> array = getUsers();
        int check = 0;
        for (User u : array) {
            if (u.getEmail().equals(user.getEmail())) {
                check = 1;
                break;
            }
        }
        if (check == 0) {
            try {
                String sql = "INSERT INTO Users (fullName, gender, dob, email, address, avatar, phoneNumber, password, role, status)\n"
                        + " VALUES ("
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?,"
                        + "?);";
                conn = new DBcontext().getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, user.getFullName());
                ps.setInt(2, user.getGender());
                ps.setDate(3, user.getDob());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getAddress());
                ps.setString(6, user.getAvatar());
                ps.setString(7, user.getPhoneNumber());
                ps.setString(8, user.getPassword());
                ps.setString(9, "Customer");
                ps.setString(10, "Active");
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("This email has already existed!");
        }
    }

    public void updateUserPassword(String email, String newpass) {
        String query = "Update Users set password = ? where email = ? ";
        try {
            conn = new DBcontext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, newpass);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
