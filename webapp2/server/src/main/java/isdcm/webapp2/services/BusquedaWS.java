/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp2.services;

import isdcm.webapp2.modelo.Video;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author ruroz
 */
@WebService(serviceName = "BusquedaWS")
@Stateless()
public class BusquedaWS {

    /**
     * Web service operation
     * @param titulo
     * @return 
     */
    @WebMethod(operationName = "busquedaPorTitulo")
    public String busquedaPorTitulo(@WebParam(name = "titulo") String titulo) {
        Video v = new Video();
        return v.searchByName(titulo).toString();
    }

    /**
     * Web service operation
     * @param autor
     * @return 
     */
    @WebMethod(operationName = "busquedaPorAutor")
    public String busquedaPorAutor(@WebParam(name = "autor") String autor) {
        Video v = new Video();
        return v.searchByAutor(autor).toString();
    }

    /**
     * Web service operation
     * @param dia
     * @param mes
     * @param year
     * @return 
     */
    @WebMethod(operationName = "busquedaPorFechaDeCreacion")
    public String busquedaPorFechaDeCreacion(@WebParam(name = "day") Integer dia, @WebParam(name = "month") Integer mes, @WebParam(name = "year") int year) {
        Video v = new Video();
        return v.searchByFechaCreacion(dia, mes, year).toString();
    }
    
    /**
     * Web service operation
     * @param titulo
     * @param autor
     * @param year
     * @param mes
     * @param dia
     * @return 
     */
    @WebMethod(operationName = "busquedaVideo")
    public String busquedaVideo(@WebParam(name = "titulo") String titulo,
            @WebParam(name = "autor") String autor,
            @WebParam(name = "year") String year,
            @WebParam(name = "month") String mes,
            @WebParam(name = "day") String dia) {
        Video v = new Video();
        System.out.print("Búsqueda vídeo "+ titulo+","+ autor+","+ year+","+mes+","+dia);
        return v.searchVideo(titulo, autor, year, mes, dia).toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "searchMyVideos")
    public String searchMyVideos(@WebParam(name = "username") String username) {
        Video v = new Video();
        return v.searchMyVideos(username).toString();
    }
}
