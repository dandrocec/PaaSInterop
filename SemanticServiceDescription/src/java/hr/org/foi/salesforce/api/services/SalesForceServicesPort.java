/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.salesforce.api.services;

import hr.org.foi.salesforce.api.services_client.SalesForceServices;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * REST Web Service
 *
 * @author Darko Androcec
 */
@Path("salesforceservicesport")
public class SalesForceServicesPort {
    private SalesForceServices port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SalesForceServicesPort
     */
    public SalesForceServicesPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method delete
     * @param ids resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("delete/")
    public String postDelete(JAXBElement<List<String>> ids) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.delete(ids.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method search
     * @param searchString resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("search/")
    public String getSearch(@QueryParam("searchString") String searchString) {
        try {
           
            if (port != null) {
                java.lang.String result = port.search(searchString);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method create
     * @param sObjects resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("create/")
    public String postCreate(JAXBElement<List<hr.org.foi.salesforce.api.services_client.SObject>> sObjects) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.create(sObjects.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getUserInfo
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getuserinfo/")
    public String getUserInfo() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getUserInfo();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method query
     * @param query resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("query/")
    public String getQuery(@QueryParam("query") String query) {
        try {
            // Call Web Service Operation
            
            
            // nije potrebno, radi i bez toga
          //   System.out.println("Debug query =" + query + "=");
            // TODO - srediti da zamijenjuje + sa prazninom
         /*   if (query.contains("+")){
                query = query.replace('+', ' ');
            }
            query = query.trim();
            System.out.println("Debug query =" + query + "=");
           */ 
            
            if (port != null) {
                java.lang.String result = port.query(query);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method update
     * @param sObjects resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("update/")
    public String postUpdate(JAXBElement<List<hr.org.foi.salesforce.api.services_client.SObject>> sObjects) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.update(sObjects.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method process
     * @param id resource URI parameter
     * @param approverIds resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("process/")
    public String postProcess(String id, JAXBElement<List<String>> approverIds) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.process(id, approverIds.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method merge
     * @param mergeRequests resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("merge/")
    public String postMerge(JAXBElement<List<hr.org.foi.salesforce.api.services_client.MergeRequest>> mergeRequests) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.merge(mergeRequests.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method retrieve
     * @param fieldList resource URI parameter
     * @param objectType resource URI parameter
     * @param ids resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("retrieve/")
    public String postRetrieve(String fieldList, String objectType, JAXBElement<List<String>> ids) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.retrieve(fieldList, objectType, ids.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method login
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("login/")
    public String getLogin() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.login();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method setPassword
     * @param userId resource URI parameter
     * @param password resource URI parameter
     */
    @PUT
    @Consumes("text/plain")
    @Path("setpassword/")
    public void putSetPassword(@QueryParam("userId") String userId, @QueryParam("password") String password) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.setPassword(userId, password);
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method logout
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("logout/")
    public String getLogout() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.logout();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method isUserLoggedIn
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("isuserloggedin/")
    public String getIsUserLoggedIn() {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.isUserLoggedIn();
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeGlobal
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describeglobal/")
    public String getDescribeGlobal() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeGlobal();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeSalesforceObject
     * @param arg0 resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describesalesforceobject/")
    public String getDescribeSalesforceObject(@QueryParam("arg0") String arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeSalesforceObject(arg0);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getServerTimeStamp
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("getservertimestamp/")
    public String getServerTimeStamp() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getServerTimeStamp();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method sendEmail
     * @param from resource URI parameter
     * @param to resource URI parameter
     * @param subject resource URI parameter
     * @param text resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("sendemail/")
    public String getSendEmail(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("subject") String subject, @QueryParam("text") String text) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.sendEmail(from, to, subject, text);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createSalesforceDataModelOntology
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createsalesforcedatamodelontology/")
    public String getCreateSalesforceDataModelOntology() {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.createSalesforceDataModelOntology();
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createCustomObjectsFromDataOntology
     * @param pathToOntology resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createcustomobjectsfromdataontology/")
    public String getCreateCustomObjectsFromDataOntology(@QueryParam("pathToOntology") String pathToOntology) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createCustomObjectsFromDataOntology(pathToOntology);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method insertDataIntoCreatedCustomObjects
     * @param pathToOntology resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("insertdataintocreatedcustomobjects/")
    public String getInsertDataIntoCreatedCustomObjects(@QueryParam("pathToOntology") String pathToOntology) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.insertDataIntoCreatedCustomObjects(pathToOntology);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method printAsyncResultStatus
     * @param arg0 resource URI parameter
     */
    @PUT
    @Consumes("application/xml")
    @Path("printasyncresultstatus/")
    public void putPrintAsyncResultStatus(JAXBElement<List<hr.org.foi.salesforce.api.services_client.AsyncResult>> arg0) {
        try {
            // Call Web Service Operation
            if (port != null) {
                port.printAsyncResultStatus(arg0.getValue());
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
    }

    /**
     * Invokes the SOAP method convertLeadRecords
     * @param leadsToConvert resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("convertleadrecords/")
    public String postConvertLeadRecords(JAXBElement<List<hr.org.foi.salesforce.api.services_client.LeadConvert>> leadsToConvert) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.convertLeadRecords(leadsToConvert.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method emptyRecycleBin
     * @param ids resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("emptyrecyclebin/")
    public String postEmptyRecycleBin(JAXBElement<List<String>> ids) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.emptyRecycleBin(ids.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getDeleted
     * @param objectType resource URI parameter
     * @param startDate resource URI parameter
     * @param endDate resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getdeleted/")
    public String postGetDeleted(String objectType, JAXBElement<XMLGregorianCalendar> startDate, JAXBElement<XMLGregorianCalendar> endDate) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getDeleted(objectType, startDate.getValue(), endDate.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method getUpdated
     * @param objectType resource URI parameter
     * @param startDate resource URI parameter
     * @param endDate resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("getupdated/")
    public String postGetUpdated(String objectType, JAXBElement<XMLGregorianCalendar> startDate, JAXBElement<XMLGregorianCalendar> endDate) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.getUpdated(objectType, startDate.getValue(), endDate.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method invalidateSessions
     * @param sessionIds resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("invalidatesessions/")
    public String postInvalidateSessions(JAXBElement<List<String>> sessionIds) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.invalidateSessions(sessionIds.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method queryAll
     * @param query resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("queryall/")
    public String getQueryAll(@QueryParam("query") String query) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.queryAll(query);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method undelete
     * @param ids resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("undelete/")
    public String postUndelete(JAXBElement<List<String>> ids) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.undelete(ids.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method upsert
     * @param externalIdFieldName resource URI parameter
     * @param sObjects resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("upsert/")
    public String postUpsert(String externalIdFieldName, JAXBElement<List<hr.org.foi.salesforce.api.services_client.SObject>> sObjects) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.upsert(externalIdFieldName, sObjects.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeDataCategoryGroups
     * @param sObjectTypes resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("describedatacategorygroups/")
    public String postDescribeDataCategoryGroups(JAXBElement<List<String>> sObjectTypes) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeDataCategoryGroups(sObjectTypes.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeDataCategoryGroupStructures
     * @param pairs resource URI parameter
     * @param topCategoriesOnly resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("describedatacategorygroupstructures/")
    public String postDescribeDataCategoryGroupStructures(JAXBElement<List<hr.org.foi.salesforce.api.services_client.DataCategoryGroupSobjectTypePair>> pairs, boolean topCategoriesOnly) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeDataCategoryGroupStructures(pairs.getValue(), topCategoriesOnly);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeLayout
     * @param objectType resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describelayout/")
    public String getDescribeLayout(@QueryParam("objectType") String objectType) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeLayout(objectType);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeSearchScopeOrder
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describesearchscopeorder/")
    public String getDescribeSearchScopeOrder() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeSearchScopeOrder();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeSObject
     * @param objectType resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describesobject/")
    public String getDescribeSObject(@QueryParam("objectType") String objectType) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeSObject(objectType);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeSObjects
     * @param objectTypes resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("describesobjects/")
    public String postDescribeSObjects(JAXBElement<List<String>> objectTypes) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeSObjects(objectTypes.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeSoftphoneLayout
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describesoftphonelayout/")
    public String getDescribeSoftphoneLayout() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeSoftphoneLayout();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeTabs
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describetabs/")
    public String getDescribeTabs() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeTabs();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method resetPassword
     * @param userId resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("resetpassword/")
    public String getResetPassword(@QueryParam("userId") String userId) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.resetPassword(userId);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createMetadata
     * @param metadata resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("createmetadata/")
    public String postCreateMetadata(JAXBElement<List<hr.org.foi.salesforce.api.services_client.Metadata>> metadata) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createMetadata(metadata.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method deleteMetadata
     * @param metadata resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("deletemetadata/")
    public String postDeleteMetadata(JAXBElement<List<hr.org.foi.salesforce.api.services_client.Metadata>> metadata) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.deleteMetadata(metadata.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method updateMetadatas
     * @param metadata resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("updatemetadatas/")
    public String postUpdateMetadatas(JAXBElement<List<hr.org.foi.salesforce.api.services_client.UpdateMetadata>> metadata) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.updateMetadatas(metadata.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method checkStatusMetadata
     * @param ids resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("checkstatusmetadata/")
    public String postCheckStatusMetadata(JAXBElement<List<String>> ids) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.checkStatusMetadata(ids.getValue());
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method describeMetadata
     * @param apiVersion resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("describemetadata/")
    public String getDescribeMetadata(@QueryParam("apiVersion")
            @DefaultValue("0.0") double apiVersion) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.describeMetadata(apiVersion);
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
    private SalesForceServices getPort() {
        try {
            // Call Web Service Operation
            hr.org.foi.salesforce.api.services_client.SalesForceServices_Service service = new hr.org.foi.salesforce.api.services_client.SalesForceServices_Service();
            hr.org.foi.salesforce.api.services_client.SalesForceServices p = service.getSalesForceServicesPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
