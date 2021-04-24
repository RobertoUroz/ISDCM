/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.controlador;

import isdcm.webapp.modelo.Video;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "servletRegistroVid", urlPatterns = {"/servletRegistroVid"})
public class servletRegistroVid extends HttpServlet {

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
        switch (request.getMethod()){
            case "GET":
                break;
            case "POST":
                String url = "";
                if ((boolean)request.getAttribute("correct")){
                    url = "servletListadoVid";
                    request.setAttribute("vid_insertado", "true");
                } else {
                    //Add error message
                    url = "jsp/registroVid.jsp";
                    request.setAttribute("error_registro_vid", true);
                }
                request.getRequestDispatcher(url).forward(request, response);
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
        if (request.getParameter("registroVideo") != null) {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("user");
            if (username == null) {
                request.getRequestDispatcher("").forward(request, response);
                return;
            }
            System.out.println("He venido del registro de video");
            String titulo = request.getParameter("titulo");
            String autor = request.getParameter("autor");
            int duracionH = Integer.parseInt(request.getParameter("duracionh"));
            int duracionMin = Integer.parseInt(request.getParameter("duracionmin"));
            int duracionS = Integer.parseInt(request.getParameter("duracions"));
            String duracion = duracionH + ":" + duracionMin + ":" + duracionS;
            String descripcion = request.getParameter("descripcion");
            String formato = request.getParameter("formato");
            String url = request.getParameter("url");
            Video v = new Video(titulo, autor, duracionH, duracionMin, duracionS, descripcion, formato, url, username);
            request.setAttribute("correct", v.registerVideo());
            processRequest(request, response);
        } else {
            System.out.println("El button no ha llegado");
        }
        //processRequest(request, response);
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
