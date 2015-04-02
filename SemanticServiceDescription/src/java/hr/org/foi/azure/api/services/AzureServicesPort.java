/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.azure.api.services;

import hr.org.foi.azure.api.services_client.AzureServices;
import hr.org.foi.azure.api.services_client.TableServiceEntity;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

/**
 * REST Web Service
 *
 * @author Darko Androcec
 */
@Path("azureservicesport")
public class AzureServicesPort {
    private AzureServices port;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AzureServicesPort
     */
    public AzureServicesPort() {
        port = getPort();
    }

    /**
     * Invokes the SOAP method insertTodoItem
     * @param itemId resource URI parameter
     * @param item resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("inserttodoitem/")
    public String getInsertTodoItem(@QueryParam("itemId") String itemId, @QueryParam("item") String item) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.insertTodoItem(itemId, item);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
    
     /**
     * Invokes the SOAP method executeQuery
     * @param query resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("executequery/")
    public String getExecuteQuery(@QueryParam("query") String query) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.executeQuery(query);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }


    /**
     * Invokes the SOAP method selectQuery
     * @param query resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("selectquery/")
    public String getSelectQuery(@QueryParam("query") String query) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.selectQuery(query);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createAzureDataModelOntology
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createazuredatamodelontology/")
    public String getCreateAzureDataModelOntology() {
        try {
            // Call Web Service Operation
            if (port != null) {
                boolean result = port.createAzureDataModelOntology();
                return new java.lang.Boolean(result).toString();
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method findKeys
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("findkeys/")
    public String getFindKeys() {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.findKeys();
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createTableFromDataOntology
     * @param pathToOntology resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createtablefromdataontology/")
    public String getCreateTableFromDataOntology(@QueryParam("pathToOntology") String pathToOntology) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createTableFromDataOntology(pathToOntology);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createBlobContainer
     * @param containerName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createblobcontainer/")
    public String getCreateBlobContainer(@QueryParam("containerName") String containerName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createBlobContainer(containerName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method uploadBlob
     * @param containerName resource URI parameter
     * @param fileName resource URI parameter
     * @param path resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("uploadblob/")
    public String getUploadBlob(@QueryParam("containerName") String containerName, @QueryParam("fileName") String fileName, @QueryParam("path") String path) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.uploadBlob(containerName, fileName, path);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method listBlobs
     * @param containerName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("listblobs/")
    public String getListBlobs(@QueryParam("containerName") String containerName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.listBlobs(containerName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method downloadBlobs
     * @param containerName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("downloadblobs/")
    public String getDownloadBlobs(@QueryParam("containerName") String containerName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.downloadBlobs(containerName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method deleteBlob
     * @param containerName resource URI parameter
     * @param blobName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("deleteblob/")
    public String getDeleteBlob(@QueryParam("containerName") String containerName, @QueryParam("blobName") String blobName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.deleteBlob(containerName, blobName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method deleteBlobContainer
     * @param containerName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("deleteblobcontainer/")
    public String getDeleteBlobContainer(@QueryParam("containerName") String containerName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.deleteBlobContainer(containerName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method createTable
     * @param tableName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("createtable/")
    public String getCreateTable(@QueryParam("tableName") String tableName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.createTable(tableName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method deleteTable
     * @param tableName resource URI parameter
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/plain")
    @Consumes("text/plain")
    @Path("deletetable/")
    public String getDeleteTable(@QueryParam("tableName") String tableName) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.deleteTable(tableName);
                return result;
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }

    /**
     * Invokes the SOAP method insertEntityToTable
     * @param tableName resource URI parameter
     * @param entity resource URI parameter
     * @return an instance of java.lang.String
     */
    @POST
    @Produces("text/plain")
    @Consumes("application/xml")
    @Path("insertentitytotable/")
    public String postInsertEntityToTable(String tableName, JAXBElement<TableServiceEntity> entity) {
        try {
            // Call Web Service Operation
            if (port != null) {
                java.lang.String result = port.insertEntityToTable(tableName, entity.getValue());
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
    private AzureServices getPort() {
        try {
            // Call Web Service Operation
            hr.org.foi.azure.api.services_client.AzureServices_Service service = new hr.org.foi.azure.api.services_client.AzureServices_Service();
            hr.org.foi.azure.api.services_client.AzureServices p = service.getAzureServicesPort();
            return p;
        } catch (Exception ex) {
            // TODO handle custom exceptions here
        }
        return null;
    }
}
