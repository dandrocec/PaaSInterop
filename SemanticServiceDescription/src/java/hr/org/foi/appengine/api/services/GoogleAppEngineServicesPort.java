/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.appengine.api.services;

import hr.org.foi.appengine.api.services_client.Capability;
import hr.org.foi.appengine.api.services_client.Entity;
import hr.org.foi.appengine.api.services_client.GetEntitiesOfKindResponse;
import hr.org.foi.appengine.api.services_client.GoogleAppEngineServices;
import hr.org.foi.appengine.api.services_client.Key;
import hr.org.foi.appengine.api.services_client.TestReturnOfListResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * REST Web Service
 *
 * @author Darko Androcec
 */
@Path("googleappengineservicesport")
public class GoogleAppEngineServicesPort {
    private GoogleAppEngineServices port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GoogleAppEngineServicesPort
     */
    public GoogleAppEngineServicesPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method get
     * @param keyName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("get/")
    public String get(@QueryParam("keyName") String keyName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.get(keyName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method put
     * @param entity resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("put/")
    public String postPut(JAXBElement<Entity> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.put(entity.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method delete
     * @param entity resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("delete/")
    public String postDelete(JAXBElement<Key> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.delete(entity.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method query
     * @param gqLquery resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("query/")
    public String getQuery(@QueryParam("gqLquery") String gqLquery) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.query(gqLquery);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method retrieve
     * @param entity resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("retrieve/")
    public String postRetrieve(JAXBElement<Key> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.retrieve(entity.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getEntitiesOfKind
     * @param entityKind resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<hr.org.foi.appengine.api.services_client.GetEntitiesOfKindResponse>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("getentitiesofkind/")
    public JAXBElement<GetEntitiesOfKindResponse> getEntitiesOfKind(@QueryParam("entityKind") String entityKind) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<java.lang.String> result = port.getEntitiesOfKind(entityKind);

                class GetEntitiesOfKindResponse_1 extends hr.org.foi.appengine.api.services_client.GetEntitiesOfKindResponse {

                    GetEntitiesOfKindResponse_1(java.util.List<java.lang.String> _return) {
                        this._return = _return;
                    }
                }
                hr.org.foi.appengine.api.services_client.GetEntitiesOfKindResponse response = new GetEntitiesOfKindResponse_1(result);
                return new hr.org.foi.appengine.api.services_client.ObjectFactory().createGetEntitiesOfKindResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method testReturnOfList
     * @param entityKind resource URI parameter
     * @return an instance of javax.xml.bind.JAXBElement<hr.org.foi.appengine.api.services_client.TestReturnOfListResponse>
     */
    @GET
    @Produces("application/xml")
    @Consumes("text/plain")
    @Path("testreturnoflist/")
    public JAXBElement<TestReturnOfListResponse> getTestReturnOfList(@QueryParam("entityKind") String entityKind) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.util.List<java.lang.String> result = port.testReturnOfList(entityKind);

                class TestReturnOfListResponse_1 extends hr.org.foi.appengine.api.services_client.TestReturnOfListResponse {

                    TestReturnOfListResponse_1(java.util.List<java.lang.String> _return) {
                        this._return = _return;
                    }
                }
                hr.org.foi.appengine.api.services_client.TestReturnOfListResponse response = new TestReturnOfListResponse_1(result);
                return new hr.org.foi.appengine.api.services_client.ObjectFactory().createTestReturnOfListResponse(response);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createDataModelOntology
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createdatamodelontology/")
    public String getCreateDataModelOntology() {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.createDataModelOntology();
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createEntitiesFromDataOntology
     * @param pathToOntology resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createentitiesfromdataontology/")
    public String getCreateEntitiesFromDataOntology(@QueryParam("pathToOntology") String pathToOntology) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createEntitiesFromDataOntology(pathToOntology);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getCapabilityStatus
     * @param pathToOntology resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getcapabilitystatus/")
    public String postGetCapabilityStatus(JAXBElement<Capability> pathToOntology) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getCapabilityStatus(pathToOntology.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method deleteKeyName
     * @param keyName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("deletekeyname/")
    public String getDeleteKeyName(@QueryParam("keyName") String keyName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.deleteKeyName(keyName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     *
     */
    private GoogleAppEngineServices getPort() {
        try {
            // Call Web Service Operation
            hr.org.foi.appengine.api.services_client.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services_client.GoogleAppEngineServices_Service();
            hr.org.foi.appengine.api.services_client.GoogleAppEngineServices p = service.getGoogleAppEngineServicesPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
