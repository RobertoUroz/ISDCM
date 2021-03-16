/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.controlador;

import isdcm.webapp1.modelo.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author ruroz
 */
@WebServlet(name = "servletUsuarios", urlPatterns = {"/servletUsuarios"})
public class servletUsuarios extends HttpServlet {

    // /**
    // * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
    // * methods.
    // *
    // * @param request servlet request
    // * @param response servlet response
    // * @throws ServletException if a servlet-specific error occurs
    // * @throws IOException if an I/O error occurs
    // */
    // protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    // throws ServletException, IOException {
    // response.setContentType("text/html;charset=UTF-8");
    // try (PrintWriter out = response.getWriter()) {
    // /* TODO output your page here. You may use following sample code. */
    // out.println("<!DOCTYPE html>");
    // out.println("<html>");
    // out.println("<head>");
    // out.println("<title>Servlet servletUsuarios</title>");            
    // out.println("</head>");
    // out.println("<body>");
    // out.println("<h1>Servlet servletUsuarios at " + request.getContextPath() + "</h1>");
    // out.println("</body>");
    // out.println("</html>");
    // }
    // }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getMethod()) {
            case "GET":
                break;
            case "POST":
                boolean correct = (boolean) request.getAttribute("correct");
                String url = "";
                switch (request.getParameter("button")) {
                    case "registro":
                        if (correct) {
                            url = "jsp/login.jsp";
                        } else {
                            request.setAttribute("error_registro", true);
                            url = "jsp/registroUsu.jsp";
                        }
                        break;
                    case "login":
                        if (correct) {
                            HttpSession session = request.getSession();
                            session.setAttribute("user",request.getParameter("username"));
                            url = "servletListadoVid";
                        } else {
                            request.setAttribute("error_login", true);
                            url = "jsp/login.jsp";
                        }
                        break;
                    case "logout":
                        HttpSession session = request.getSession();
                        if (correct) {
                            session.removeAttribute("user");
                            session.invalidate();
                            url = "jsp/login.jsp";
                        } else if (session.getAttribute("user") == null) {
                            request.setAttribute("error_login", true);
                            url = "jsp/login.jsp";
                        } else {
                            url = "servletListadoVid";
                        }
                        break;
                    default:
                        System.out.println("Case not handled");
                }
                try {
                    request.getRequestDispatcher(url).forward(request, response); //We can send attributes with forward, with sendRedirect we cannot
                } catch(IOException e) {
                    e.printStackTrace();
                }
                break;

            default:
                System.out.println("Case not handled");
        }
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
        if (request.getParameter("button").equals("registro")) {
            System.out.println("He venido del registro");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Usuario u = new Usuario(nombre, apellidos, email, username, password);
            request.setAttribute("correct", u.registerUser());
            processRequest(request, response);
        } else if (request.getParameter("button").equals("login")) {
            System.out.println("He venido del login");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Usuario u = new Usuario(username, password);
            request.setAttribute("correct", u.loginUser());
            processRequest(request, response);
        } else if (request.getParameter("button").equals("logout")) {
            System.out.println("He venido del logout button");
            request.setAttribute("correct", true);
            processRequest(request, response);
        } else {
            System.out.println("Caso no comprobado");
        }
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
