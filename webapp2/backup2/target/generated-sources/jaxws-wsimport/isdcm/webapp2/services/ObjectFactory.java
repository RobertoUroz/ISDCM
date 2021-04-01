
package isdcm.webapp2.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the isdcm.webapp2.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BusquedaVideoResponse_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaVideoResponse");
    private final static QName _BusquedaPorAutorResponse_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorAutorResponse");
    private final static QName _BusquedaPorTitulo_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorTitulo");
    private final static QName _BusquedaPorAutor_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorAutor");
    private final static QName _BusquedaPorFechaDeCreacionResponse_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorFechaDeCreacionResponse");
    private final static QName _BusquedaPorTituloResponse_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorTituloResponse");
    private final static QName _BusquedaPorFechaDeCreacion_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaPorFechaDeCreacion");
    private final static QName _SearchMyVideosResponse_QNAME = new QName("http://services.webapp2.isdcm/", "searchMyVideosResponse");
    private final static QName _SearchMyVideos_QNAME = new QName("http://services.webapp2.isdcm/", "searchMyVideos");
    private final static QName _BusquedaVideo_QNAME = new QName("http://services.webapp2.isdcm/", "busquedaVideo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: isdcm.webapp2.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BusquedaVideoResponse }
     * 
     */
    public BusquedaVideoResponse createBusquedaVideoResponse() {
        return new BusquedaVideoResponse();
    }

    /**
     * Create an instance of {@link BusquedaPorTitulo }
     * 
     */
    public BusquedaPorTitulo createBusquedaPorTitulo() {
        return new BusquedaPorTitulo();
    }

    /**
     * Create an instance of {@link BusquedaPorAutorResponse }
     * 
     */
    public BusquedaPorAutorResponse createBusquedaPorAutorResponse() {
        return new BusquedaPorAutorResponse();
    }

    /**
     * Create an instance of {@link BusquedaPorFechaDeCreacion }
     * 
     */
    public BusquedaPorFechaDeCreacion createBusquedaPorFechaDeCreacion() {
        return new BusquedaPorFechaDeCreacion();
    }

    /**
     * Create an instance of {@link SearchMyVideosResponse }
     * 
     */
    public SearchMyVideosResponse createSearchMyVideosResponse() {
        return new SearchMyVideosResponse();
    }

    /**
     * Create an instance of {@link BusquedaPorAutor }
     * 
     */
    public BusquedaPorAutor createBusquedaPorAutor() {
        return new BusquedaPorAutor();
    }

    /**
     * Create an instance of {@link BusquedaPorFechaDeCreacionResponse }
     * 
     */
    public BusquedaPorFechaDeCreacionResponse createBusquedaPorFechaDeCreacionResponse() {
        return new BusquedaPorFechaDeCreacionResponse();
    }

    /**
     * Create an instance of {@link BusquedaPorTituloResponse }
     * 
     */
    public BusquedaPorTituloResponse createBusquedaPorTituloResponse() {
        return new BusquedaPorTituloResponse();
    }

    /**
     * Create an instance of {@link BusquedaVideo }
     * 
     */
    public BusquedaVideo createBusquedaVideo() {
        return new BusquedaVideo();
    }

    /**
     * Create an instance of {@link SearchMyVideos }
     * 
     */
    public SearchMyVideos createSearchMyVideos() {
        return new SearchMyVideos();
    }

    /**
     * Create an instance of {@link Video }
     * 
     */
    public Video createVideo() {
        return new Video();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaVideoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaVideoResponse")
    public JAXBElement<BusquedaVideoResponse> createBusquedaVideoResponse(BusquedaVideoResponse value) {
        return new JAXBElement<BusquedaVideoResponse>(_BusquedaVideoResponse_QNAME, BusquedaVideoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorAutorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorAutorResponse")
    public JAXBElement<BusquedaPorAutorResponse> createBusquedaPorAutorResponse(BusquedaPorAutorResponse value) {
        return new JAXBElement<BusquedaPorAutorResponse>(_BusquedaPorAutorResponse_QNAME, BusquedaPorAutorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorTitulo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorTitulo")
    public JAXBElement<BusquedaPorTitulo> createBusquedaPorTitulo(BusquedaPorTitulo value) {
        return new JAXBElement<BusquedaPorTitulo>(_BusquedaPorTitulo_QNAME, BusquedaPorTitulo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorAutor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorAutor")
    public JAXBElement<BusquedaPorAutor> createBusquedaPorAutor(BusquedaPorAutor value) {
        return new JAXBElement<BusquedaPorAutor>(_BusquedaPorAutor_QNAME, BusquedaPorAutor.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorFechaDeCreacionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorFechaDeCreacionResponse")
    public JAXBElement<BusquedaPorFechaDeCreacionResponse> createBusquedaPorFechaDeCreacionResponse(BusquedaPorFechaDeCreacionResponse value) {
        return new JAXBElement<BusquedaPorFechaDeCreacionResponse>(_BusquedaPorFechaDeCreacionResponse_QNAME, BusquedaPorFechaDeCreacionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorTituloResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorTituloResponse")
    public JAXBElement<BusquedaPorTituloResponse> createBusquedaPorTituloResponse(BusquedaPorTituloResponse value) {
        return new JAXBElement<BusquedaPorTituloResponse>(_BusquedaPorTituloResponse_QNAME, BusquedaPorTituloResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaPorFechaDeCreacion }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaPorFechaDeCreacion")
    public JAXBElement<BusquedaPorFechaDeCreacion> createBusquedaPorFechaDeCreacion(BusquedaPorFechaDeCreacion value) {
        return new JAXBElement<BusquedaPorFechaDeCreacion>(_BusquedaPorFechaDeCreacion_QNAME, BusquedaPorFechaDeCreacion.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchMyVideosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "searchMyVideosResponse")
    public JAXBElement<SearchMyVideosResponse> createSearchMyVideosResponse(SearchMyVideosResponse value) {
        return new JAXBElement<SearchMyVideosResponse>(_SearchMyVideosResponse_QNAME, SearchMyVideosResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchMyVideos }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "searchMyVideos")
    public JAXBElement<SearchMyVideos> createSearchMyVideos(SearchMyVideos value) {
        return new JAXBElement<SearchMyVideos>(_SearchMyVideos_QNAME, SearchMyVideos.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusquedaVideo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.webapp2.isdcm/", name = "busquedaVideo")
    public JAXBElement<BusquedaVideo> createBusquedaVideo(BusquedaVideo value) {
        return new JAXBElement<BusquedaVideo>(_BusquedaVideo_QNAME, BusquedaVideo.class, null, value);
    }

}
