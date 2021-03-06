/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp2.busqueda;

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
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "busquedaPorTitulo")
    public org.json.JSONArray busquedaPorTitulo(@WebParam(name = "titulo") String titulo) {
        //TODO write your implementation code here:
        return null;
    }
}
