/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.controlador;

import isdcm.webapp1.modelo.CifradoContenido;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Resis
 */
@WebServlet(name = "servletCifradoContenido", urlPatterns = {"/servletCifradoContenido"})
@MultipartConfig
public class servletCifradoContenido extends HttpServlet {
    
    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
    

    // Properties ---------------------------------------------------------------------------------

    private String filePath;

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
        System.out.println(request.getParameter("accioncifrado"));
        //Document doc;
        byte[] file;
        switch (request.getParameter("accioncifrado")){
            case "cifrar":
                file = encryptContent(request, response);
                serveFile(request, response, file, "_encrypted");
                break;
            case "descifrar":
                file = decryptContent(request, response);
                serveFile(request, response, file, "_decrypted");
                break;
        }
    }
    
     // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
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
    }

    private void write(InputStream fileContent) {
        try {
            BufferedInputStream input = new BufferedInputStream(fileContent, DEFAULT_BUFFER_SIZE);
            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                System.out.println(new String(buffer));
            }
        } catch (IOException ex) {
            Logger.getLogger(servletCifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private byte[] encryptContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("fichero"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        
        byte[] file = new byte[(int)filePart.getSize()];
        fileContent.read(file, 0, file.length);
        fileContent.close();
        
        CifradoContenido cifrado = new CifradoContenido();
        return cifrado.encrypt(file);
    }
    
    private byte[] decryptContent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("fichero"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        
        byte[] file = new byte[(int)filePart.getSize()];
        fileContent.read(file, 0, file.length);
        fileContent.close();
        
        CifradoContenido cifrado = new CifradoContenido();
        return cifrado.decrypt(file);
    }
    
    private void serveFile(HttpServletRequest request, HttpServletResponse response, byte[] file_encrypted, String suffix) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("fichero"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        String contentType = getServletContext().getMimeType(fileName);

        // If content type is unknown, then set the default value.
        // For all content types, see: http://www.w3schools.com/media/media_mimeref.asp
        // To add new content types, add new mime-mapping entry in web.xml.
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        String[] fileNameSplit = fileName.split("\\.");
        System.out.println(fileNameSplit[0]);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameSplit[0] + suffix + "." + fileNameSplit[1] + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(document_encrypted);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            input = new BufferedInputStream(is, DEFAULT_BUFFER_SIZE);*/
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            // Write file contents to response.
            /*byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }*/
            output.write(file_encrypted);
            response.setHeader("Content-Length", String.valueOf(file_encrypted.length));
        } catch (Exception ex) {
            Logger.getLogger(servletCifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }
    
}
