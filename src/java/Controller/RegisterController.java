/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import SMTP.GmailAPI;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Linh
 */
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserDAO ud = new UserDAO();
        GmailAPI gmail = new GmailAPI();
        if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
            request.setAttribute("invalid", "Repassword is not equal to password!");
            //request.setAttribute("email", request.getParameter("email"));
            //request.setAttribute("username", request.getParameter("username"));
            //request.setAttribute("password", request.getParameter("password"));
            //request.setAttribute("repassword", request.getParameter("repassword"));
            request.getRequestDispatcher("register").forward(request, response);
        } else if (!request.getParameter("phoneNumber").matches("(09|01[2|6|8|9])+([0-9]{8})")) {
            request.setAttribute("invalid", "Phone number is invalid!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if (!ud.checkEmail(request.getParameter("email"))) {
            request.setAttribute("invalid", "This email has already existed!");
            //request.setAttribute("email", request.getParameter("email"));
            //request.setAttribute("username", request.getParameter("username"));
            // request.setAttribute("password", request.getParameter("password"));
            //request.setAttribute("repassword", request.getParameter("repassword"));
            request.getRequestDispatcher("register").forward(request, response);
        } else {
            try {
                User u = new User();
                u.setFullName(request.getParameter("fullName"));
                u.setGender(Integer.parseInt(request.getParameter("gender")));
                String dobString = request.getParameter("dob");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date dob = sdf.parse(dobString);
                java.sql.Date sqlDob = new java.sql.Date(dob.getTime());
                u.setDob(sqlDob);
                u.setEmail(request.getParameter("email"));
                u.setAddress(request.getParameter("address"));
                u.setAvatar(request.getParameter("avatar"));
                u.setPhoneNumber(request.getParameter("phoneNumber"));
                u.setPassword(request.getParameter("password"));

                ud.createUser(u);
                System.out.println(request.getParameter("email"));
                System.out.println(request.getParameter("password"));

                try {
                    String gmailFrom = "swp391.e2.g5@gmail.com";
                    String password = "LinhLVT2509";
                    String mailTo = request.getParameter("email");
                    String subject = "Welcome";
                    String message = "Hello " + request.getParameter("email") + ", you have successfully registered!";

                    //send mail
                    gmail.send(mailTo, subject, message, gmailFrom, password);
                } catch (MessagingException ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ParseException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
