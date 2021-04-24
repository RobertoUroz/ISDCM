/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.controlador;

import isdcm.webapp.modelo.PreferenciasUsuario;
import isdcm.webapp.soap.Video;
import isdcm.webapp.soap.BusquedaWS_Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import org.json.JSONException;								  
import org.json.JSONObject;

/**
 *
 * @author ruroz
 */
@WebServlet(name = "servletListadoVid", urlPatterns = {"/servletListadoVid"})
public class servletListadoVid extends HttpServlet {
    
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
        try {
            request.getRequestDispatcher("jsp/listadoVid.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();
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
        //processRequest(request, response);
        System.out.println("GET QUERYPARAMS: " + request.getQueryString());
        System.out.println("GET Header : " + request.getHeaderNames().nextElement() + " + " + response.getHeader("Content-Length"));
        if (request.getQueryString() != null){
            String[] msg_arr = request.getQueryString().split("&");
            for (String element : msg_arr){
                request.setAttribute(element.split("=")[0], decode(element.split("=")[1]));
            }
        }
        mostrarlistado(request);
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
        mostrarlistado(request);
        processRequest(request, response);
    }
            
    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    private void mostrarlistado(HttpServletRequest request) throws JSONException {
        List<Video> listVideos = new ArrayList<>();
        Video v = new Video();
        boolean ownvideo = false;
        JSONObject jsonVideos;
        if (Objects.isNull(request.getParameter("button"))) {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("user");
            if (PreferenciasUsuario.get(username).listavideos() == PreferenciasUsuario.LISTAVIDEOS_BD) {
                ownvideo = true;
                List<isdcm.webapp.modelo.Video> listVideos2 = new ArrayList<>();
                isdcm.webapp.modelo.Video vv = new isdcm.webapp.modelo.Video();
                jsonVideos = vv.getAllVideos();
                //jsonVideos = port.busquedaVideo(arg0, arg1, arg2, arg3, arg4);
                for (int i = 0; i < jsonVideos.getJSONArray("items").length(); i++){
                    JSONObject item = jsonVideos.getJSONArray("items").getJSONObject(i);
                    listVideos2.add(new isdcm.webapp.modelo.Video(item));
                }
                request.setAttribute("listVideos", listVideos2);
            } else {
                try { // Call Web Service Operation
                    isdcm.webapp.soap.BusquedaWS port = service.getBusquedaWSPort();
                    listVideos = port.busquedaVideo((String)null,(String)null,(String)null,(String)null,(String)null);
                    // TODO handle custom exceptions here
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }
            }
        }
        else if (request.getParameter("button").equals("myVideos")){
            //search for My Videos
            try { // Call Web Service Operation
                    isdcm.webapp.soap.BusquedaWS port = service.getBusquedaWSPort();
                    listVideos = port.searchMyVideos((String)request.getSession().getAttribute("user"));
                } catch (Exception ex) {
                    // TODO handle custom exceptions here
                }
        }
        else {
            try { // Call Web Service Operation
                isdcm.webapp.soap.BusquedaWS port = service.getBusquedaWSPort();
                listVideos = port.busquedaVideo((String)null,(String)null,(String)null,(String)null,(String)null);
                // TODO handle custom exceptions here
            } catch (Exception ex) {
                // TODO handle custom exceptions here
            }
        }
        if (!ownvideo) {
            request.setAttribute("listVideos", listVideos);
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