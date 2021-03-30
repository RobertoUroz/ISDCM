/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp2.controlador;

import isdcm.webapp2.services.BusquedaWS_Service;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceRef;
import org.json.JSONObject;


/**
 *
 * @author ruroz
 */
public class servletBusqueda extends HttpServlet {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/BusquedaWS/BusquedaWS.wsdl")
    private BusquedaWS_Service service;

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
            /*
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet servletBusqueda</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet servletBusqueda at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
            try {
                request.getRequestDispatcher("jsp/busqueda.jsp").forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace();
            }
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
        String videos = null;
        switch (request.getParameter("button")){
            case "busquedaVideo":
                //list videos
                System.out.println("HOLA");
                videos = "{}";
                //TODO: Crear WSDL para conectarse con el server
                //call to web service busquedaVideo (se necesita WSDL con conexión al server IMPORTANTE)
                
                try { // Call Web Service Operation
                    isdcm.webapp2.services.BusquedaWS port = service.getBusquedaWSPort();
                    java.lang.String arg0 = request.getParameter("titulo");
                    java.lang.String arg1 = request.getParameter("autor");
                    java.lang.String arg2 = request.getParameter("fechay");
                    java.lang.String arg3 = request.getParameter("fecham");
                    java.lang.String arg4 = request.getParameter("fechad");
                    // TODO process result here
                    videos = port.busquedaVideo(arg0, arg1, arg2, arg3, arg4);
                    //request.setAttribute("listVideos",videos);
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }

break;

            default:
        }
        System.out.println(videos);
        JSONObject videos_parsed = new JSONObject(videos);
        System.out.println(videos_parsed.toString());
        request.setAttribute("listVideos",videos_parsed);
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
