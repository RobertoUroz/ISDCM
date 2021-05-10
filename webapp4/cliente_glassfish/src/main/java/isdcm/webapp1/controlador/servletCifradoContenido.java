/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.controlador;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import isdcm.webapp1.modelo.CifradoContenido;
import isdcm.webapp1.modelo.Video;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.misc.IOUtils;

/**
 *
 * @author ruroz
 */
@WebServlet(name = "servletCifradoContenido", urlPatterns = {"/servletCifradoContenido"})
@MultipartConfig()
public class servletCifradoContenido extends HttpServlet {

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
                    url = "jsp/cifradoContenido.jsp";
                    request.setAttribute("acierto_opcifrado", true);
                } else {
                    //Add error message
                    url = "jsp/cifradoContenido.jsp";
                    request.setAttribute("error_opcifrado", true);
                }
                request.getRequestDispatcher(url).forward(request, response);
                break;
            default:
        }
    }

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
        if (request.getParameter("accioncifrado").equals("cifrar")) {
            System.out.println("He venido del cifrado de contenido");
            Part filepart = request.getPart("fichero");
            if (filepart == null) {
                request.setAttribute("correct",false);
                processRequest(request,response);
                return;
            }
            InputStream is = filepart.getInputStream();
            byte[] file = new byte[(int)filepart.getSize()];
            is.read(file, 0, file.length);
            is.close();
            /*byte[] codif = Base64.encode(file);*/
            CifradoContenido cc = new CifradoContenido();
            String usuario = (String) request.getParameter("username");
            System.out.println("usuario: "+usuario);
            byte[] skc = cc.getuserkey(usuario);
            SecretKey sk = cc.decodekey(skc);
            byte[] ef = cc.encryptfile(file, sk);
            request.setAttribute("correct", true);
            request.setAttribute("dlfile", ef);
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
    }

}
