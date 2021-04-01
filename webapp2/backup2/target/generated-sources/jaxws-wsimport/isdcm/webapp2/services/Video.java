
package isdcm.webapp2.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para video complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="video">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="autor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descripcion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="duracionH" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="duracionMin" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="duracionS" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="duracionString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="formato" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="reproducciones" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="titulo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "video", propOrder = {
    "autor",
    "date",
    "descripcion",
    "duracionH",
    "duracionMin",
    "duracionS",
    "duracionString",
    "formato",
    "id",
    "reproducciones",
    "titulo",
    "url",
    "username"
})
public class Video {

    protected String autor;
    protected String date;
    protected String descripcion;
    protected int duracionH;
    protected int duracionMin;
    protected int duracionS;
    protected String duracionString;
    protected String formato;
    protected int id;
    protected int reproducciones;
    protected String titulo;
    protected String url;
    protected String username;

    /**
     * Obtiene el valor de la propiedad autor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Define el valor de la propiedad autor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutor(String value) {
        this.autor = value;
    }

    /**
     * Obtiene el valor de la propiedad date.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad duracionH.
     * 
     */
    public int getDuracionH() {
        return duracionH;
    }

    /**
     * Define el valor de la propiedad duracionH.
     * 
     */
    public void setDuracionH(int value) {
        this.duracionH = value;
    }

    /**
     * Obtiene el valor de la propiedad duracionMin.
     * 
     */
    public int getDuracionMin() {
        return duracionMin;
    }

    /**
     * Define el valor de la propiedad duracionMin.
     * 
     */
    public void setDuracionMin(int value) {
        this.duracionMin = value;
    }

    /**
     * Obtiene el valor de la propiedad duracionS.
     * 
     */
    public int getDuracionS() {
        return duracionS;
    }

    /**
     * Define el valor de la propiedad duracionS.
     * 
     */
    public void setDuracionS(int value) {
        this.duracionS = value;
    }

    /**
     * Obtiene el valor de la propiedad duracionString.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuracionString() {
        return duracionString;
    }

    /**
     * Define el valor de la propiedad duracionString.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuracionString(String value) {
        this.duracionString = value;
    }

    /**
     * Obtiene el valor de la propiedad formato.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormato() {
        return formato;
    }

    /**
     * Define el valor de la propiedad formato.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormato(String value) {
        this.formato = value;
    }

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad reproducciones.
     * 
     */
    public int getReproducciones() {
        return reproducciones;
    }

    /**
     * Define el valor de la propiedad reproducciones.
     * 
     */
    public void setReproducciones(int value) {
        this.reproducciones = value;
    }

    /**
     * Obtiene el valor de la propiedad titulo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define el valor de la propiedad titulo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitulo(String value) {
        this.titulo = value;
    }

    /**
     * Obtiene el valor de la propiedad url.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Define el valor de la propiedad url.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Obtiene el valor de la propiedad username.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define el valor de la propiedad username.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

}
