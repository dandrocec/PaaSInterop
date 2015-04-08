
package hr.org.foi.aiplanning.services;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-2b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "AiPlanningService", targetNamespace = "http://services.aiplanning.foi.org.hr/", wsdlLocation = "http://localhost:8080/AiPlanningServices/AiPlanningService?wsdl")
public class AiPlanningService_Service
    extends Service
{

    private final static URL AIPLANNINGSERVICE_WSDL_LOCATION;
    private final static WebServiceException AIPLANNINGSERVICE_EXCEPTION;
    private final static QName AIPLANNINGSERVICE_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "AiPlanningService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/AiPlanningServices/AiPlanningService?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        AIPLANNINGSERVICE_WSDL_LOCATION = url;
        AIPLANNINGSERVICE_EXCEPTION = e;
    }

    public AiPlanningService_Service() {
        super(__getWsdlLocation(), AIPLANNINGSERVICE_QNAME);
    }

    public AiPlanningService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), AIPLANNINGSERVICE_QNAME, features);
    }

    public AiPlanningService_Service(URL wsdlLocation) {
        super(wsdlLocation, AIPLANNINGSERVICE_QNAME);
    }

    public AiPlanningService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AIPLANNINGSERVICE_QNAME, features);
    }

    public AiPlanningService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AiPlanningService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AiPlanningService
     */
    @WebEndpoint(name = "AiPlanningServicePort")
    public AiPlanningService getAiPlanningServicePort() {
        return super.getPort(new QName("http://services.aiplanning.foi.org.hr/", "AiPlanningServicePort"), AiPlanningService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AiPlanningService
     */
    @WebEndpoint(name = "AiPlanningServicePort")
    public AiPlanningService getAiPlanningServicePort(WebServiceFeature... features) {
        return super.getPort(new QName("http://services.aiplanning.foi.org.hr/", "AiPlanningServicePort"), AiPlanningService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AIPLANNINGSERVICE_EXCEPTION!= null) {
            throw AIPLANNINGSERVICE_EXCEPTION;
        }
        return AIPLANNINGSERVICE_WSDL_LOCATION;
    }

}
