/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.controlador;

import isdcm.webapp.modelo.PreferenciasUsuario;
import isdcm.webapp.modelo.Usuario;
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
                            url = "";
                        } else {
                            request.setAttribute("error_registro", true);
                            url = "jsp/registroUsu.jsp";
                        }
                        break;
                    case "login":
                        if (correct) {
                            url = "servletListadoVid";	
                        } else {
                            request.setAttribute("error_login", true);
                            url = "";
                        }
                        break;
                    case "logout":			  
                        HttpSession session = request.getSession();
                        if (correct) {
                            session.removeAttribute("user");
                            session.invalidate();
                            url = "";
                        } else if (session.getAttribute("user") == null) {
                            request.setAttribute("error_login", true);
                            url = "";
                        } else {
                            url = "servletListadoVid";
                        }
                        break;
                        case "savepreferencias":
                        if (correct) {
                            url = "jsp/preferencias.jsp";	
                        } else {
                            request.setAttribute("error_savepreferencias", true);
                            url = "";
                        }
                        break;
                    default:
                        System.out.println("Case not handled");
                }
                try {
                    if (url.equals("servletListadoVid"))
                        response.sendRedirect(url);
                    else
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
        switch (request.getParameter("button")) {
            case "registro":
                {
                    System.out.println("He venido del registro");
                    String nombre = request.getParameter("nombre");
                    String apellidos = request.getParameter("apellidos");
                    String email = request.getParameter("email");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    Usuario u = new Usuario(nombre, apellidos, email, username, password);
                    boolean vru = u.registerUser();
                    if (vru) {
                        PreferenciasUsuario pu = new PreferenciasUsuario(username);
                        vru = pu.save();
                    }
                    request.setAttribute("correct", vru);
                    processRequest(request, response);
                    break;
                }
            case "login":
                {
                    System.out.println("He venido del login");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    Usuario u = new Usuario(username, password);
                    boolean vlu = u.loginUser();
                    if (vlu) {
                        PreferenciasUsuario pu = PreferenciasUsuario.get(username);
                        HttpSession session = request.getSession();
                        session.setAttribute("user",request.getParameter("username"));
                        session.setAttribute("preferencias",pu);
                    }
                    request.setAttribute("correct", vlu);
                    //identity REST call
                    processRequest(request, response);
                    break;
                }
            case "logout":
                System.out.println("He venido del logout button");
                request.setAttribute("correct", true);
                processRequest(request, response);
                break;
            case "savepreferencias":
                {   
                    HttpSession session = request.getSession();
                    String username = (String) session.getAttribute("user");
                    if (username == null) {
                        request.getRequestDispatcher("").forward(request, response);
                        return;
                    }
                    System.out.println("He venido de las preferencias");
                    String pu_reproductor, pu_listavideos, pu_color;
                    pu_reproductor = request.getParameter("pu_reproductor");
                    pu_listavideos = request.getParameter("pu_listavideos");
                    pu_color = request.getParameter("pu_color");
                    
                    boolean vsp = true;
                    int reproductor=2, listavideos=2, color=1;
                    try {
                        reproductor = Integer.parseInt(pu_reproductor,10);
                        listavideos = Integer.parseInt(pu_listavideos,10);
                        color = Integer.parseInt(pu_color,10);
                    } catch (Exception e) {
                        vsp = false;
                    }
                    if (vsp) {
                        PreferenciasUsuario pu = new PreferenciasUsuario(username,reproductor,listavideos,color);
                        vsp = pu.save();
                    }
                    request.setAttribute("correct", vsp);
                    processRequest(request, response);
                    break;
                }

            default:
                System.out.println("Caso no comprobado");
                break;
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
