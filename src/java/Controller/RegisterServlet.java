/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.AccountDAO;
import Model.Account;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Linh
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

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
        AccountDAO ad = new AccountDAO();
        if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
            request.setAttribute("invalid", "Repassword is not equal to password!");
            //request.setAttribute("email", request.getParameter("email"));
            //request.setAttribute("username", request.getParameter("username"));
            //request.setAttribute("password", request.getParameter("password"));
            //request.setAttribute("repassword", request.getParameter("repassword"));
            request.getRequestDispatcher("register").forward(request, response);
        } else /*if (!request.getParameter("phone").matches("(09|01[2|6|8|9])+([0-9]{8})")) {
            request.setAttribute("invalid", "Phone number is invalid!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else */ if (!ad.checkEmail(request.getParameter("email"))) {
            request.setAttribute("invalid", "This email has already existed!");
            //request.setAttribute("email", request.getParameter("email"));
            //request.setAttribute("username", request.getParameter("username"));
           // request.setAttribute("password", request.getParameter("password"));
            //request.setAttribute("repassword", request.getParameter("repassword"));
            request.getRequestDispatcher("register").forward(request, response);
        } else {
            Account acc = new Account();
            acc.setEmail(request.getParameter("email"));
            acc.setPassword(request.getParameter("password"));
            ad.createAccount(acc);
            System.out.println(request.getParameter("email"));
            System.out.println(request.getParameter("password"));

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
