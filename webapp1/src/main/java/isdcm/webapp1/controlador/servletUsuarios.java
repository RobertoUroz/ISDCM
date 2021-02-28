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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author ruroz
 */
@WebServlet(name = "servletUsuarios", urlPatterns = {"/servletUsuarios"})
public class servletUsuarios extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet servletUsuarios</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet servletUsuarios at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, boolean correct)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        switch (request.getMethod()){
            case "GET":
                if (!correct){
                    try{
                        File inputFile = new File(getServletContext().getRealPath("/jsp/login.jsp"));
                        Document document;
                        document = Jsoup.parse(inputFile, "UTF-8");
                        document.body().getElementById("errorMessage").removeClass("hidden").attr("class", "visible");
                        PrintWriter out = response.getWriter();
                        out.print(document.outerHtml());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    request.getRequestDispatcher("/servletListadoVid").forward(request, response);
                }
                break;
            case "POST":
                if (correct) {
                    try{
                        File inputFile = new File(getServletContext().getRealPath("/jsp/login.jsp"));
                        Document document;
                        document = Jsoup.parse(inputFile, "UTF-8");
                        PrintWriter out = response.getWriter();
                        out.print(document.outerHtml());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            default:
                
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
        if (request.getParameter("login") != null) {
            System.out.println("He venido del login");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Usuario u = new Usuario(username, password);
            processRequest(request, response, u.loginUser());
        } else {
            System.out.println("El button no ha llegado");
        }
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
        if (request.getParameter("registro") != null) {
            System.out.println("He venido del registro");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Usuario u = new Usuario(nombre, apellidos, email, username, password);
            processRequest(request, response, u.registerUser());
        } else {
            System.out.println("El button no ha llegado");
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
