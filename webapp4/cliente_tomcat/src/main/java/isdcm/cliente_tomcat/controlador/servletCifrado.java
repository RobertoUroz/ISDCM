/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.cliente_tomcat.controlador;

import isdcm.cliente_tomcat.modelo.Cifrado;
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
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Resis
 */
@WebServlet(name = "servletCifrado", urlPatterns = {"/servletCifrado"})
@MultipartConfig
public class servletCifrado extends HttpServlet {
    
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
        System.out.println(request.getParameter("button"));
        Document doc;
        switch (request.getParameter("button")){
            case "encryptXML":
                doc = encryptXML(request, response);
                serveFile(request, response, doc, "_encrypted");
                break;
            case "decryptXML":
                doc = decryptXML(request, response);
                serveFile(request, response, doc, "_decrypted");
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
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Document encryptXML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        //write(fileContent);
        // ... (do your job here)
        
        //cifrar
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(fileContent);
        } catch (SAXException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = doc.getDocumentElement();
        //Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, document_encrypted.toString());
        Cifrado cifrado = new Cifrado();
        Boolean only_content = false;
        if (request.getParameter("only_content") != null)
            only_content = true;
        //System.out.println(root.getChildNodes().item(0).getNodeName());
        if (request.getParameter("names").toString().length() == 0){
            System.out.println("HELLO");
            return cifrado.encrypt(doc, doc.getDocumentElement(), only_content);
        }
        return cifradoElements(request.getParameter("names").split("\\,"), doc, root, cifrado, only_content);
        //return recursiveCifrado(request.getParameter("names").split("\\,"), doc, root, cifrado, only_content);
    }
    
    private Document decryptXML(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        InputStream fileContent = filePart.getInputStream();
        
        //cifrar
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder dBuilder = null;
        Document doc = null;
        try {
            dBuilder = factory.newDocumentBuilder();
            doc = dBuilder.parse(fileContent);
        } catch (SAXException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        Element root = doc.getDocumentElement();
        System.out.println("Length: " + root.getChildNodes().getLength());
        //Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, document_encrypted.toString());
        Cifrado cifrado = new Cifrado();
        return descifradoElements(doc, cifrado);
    }
    
    private void serveFile(HttpServletRequest request, HttpServletResponse response, Document document_encrypted, String sufix) throws IOException, ServletException {
        String description = request.getParameter("description"); // Retrieves <input type="text" name="description">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
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
        String[] fileNameSplitted = fileName.split("\\.");
        System.out.println(fileNameSplitted[0]);
        if (fileNameSplitted.length > 1){
            String sAfterDot = "";
            for (int i = 1; i < fileNameSplitted.length; i++)
                sAfterDot += "." + fileNameSplitted[i];
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameSplitted[0] + sufix + sAfterDot + "\"");
        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameSplitted[0] + sufix + "\"");
        }

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open streams.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Source xmlSource = new DOMSource(document_encrypted);
            Result outputTarget = new StreamResult(outputStream);
            TransformerFactory.newInstance().newTransformer().transform(xmlSource, outputTarget);
            InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
            input = new BufferedInputStream(is, DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            response.setHeader("Content-Length", String.valueOf(length));
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }
    }
    
    private Document cifradoElements(String[] names, Document doc, Element el, Cifrado cifrado, Boolean only_content) {
        System.out.println(names);
        for (String name : names){
            System.out.println(name);
            NodeList els = el.getElementsByTagName(name);
            for (int i = 0; i < els.getLength(); i++){
                try {
                    doc = cifrado.encrypt(doc, (Element)els.item(i), only_content);
                } catch (Exception ex) {
                    Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return doc;
    }

    /*private Document recursiveCifrado(String[] names, Document doc, Element el, Cifrado cifrado, Boolean only_content) {
        if (!el.hasChildNodes()){
            for (String name : names){
                if (el.getAttribute("name").toLowerCase().toString().equals(name.toLowerCase().toString())){
                    System.out.println("About to encrypt recursiveCifrado : " + el.getAttribute("name"));
                    return cifrado.encrypt(doc, el, only_content);
                }
            }
        }
        for (int i = 0; i < el.getChildNodes().getLength(); i++){
            try {
                Element e = (Element)el.getChildNodes().item(i);
                doc = recursiveCifrado(names, doc, e, cifrado, only_content);
            } catch (ClassCastException ex) {
            }
        }
        return doc;
    }*/

    private Document descifradoElements(Document doc, Cifrado cifrado) {
        NodeList els = doc.getElementsByTagName("xenc:EncryptedData");
        for (int i = 0; i < els.getLength(); i++){
            try {
                doc = cifrado.decrypt(doc, (Element)els.item(i));
            } catch (Exception ex) {
                Logger.getLogger(servletCifrado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return doc;
    }
    
}
