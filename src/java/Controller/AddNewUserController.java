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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Linh
 */
@WebServlet(name = "AddNewUserController", urlPatterns = {"/addnewuser"})
public class AddNewUserController extends HttpServlet {

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
        if (!request.getParameter("phoneNumber").matches("(09|01[2|6|8|9])+([0-9]{8})")) {
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
                u.setPassword("");

                ud.createUser(u);
                System.out.println(request.getParameter("email"));
                System.out.println(request.getParameter("password"));

                
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
