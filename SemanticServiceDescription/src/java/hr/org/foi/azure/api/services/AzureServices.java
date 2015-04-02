/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.azure.api.services;

import com.hp.hpl.jena.rdf.model.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import com.sun.rowset.CachedRowSetImpl;
import hr.org.foi.jena.AzureDataOntologyCreator;
import hr.org.foi.jena.DataOntologyReader;
import hr.org.foi.jena.DataTypeConverter;
import hr.org.foi.jena.TableUtil;
import hr.org.foi.jena.TypeConstants;
import java.util.ArrayList;
import java.util.List;
import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.blob.client.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.microsoft.windowsazure.services.core.storage.*;
import com.microsoft.windowsazure.services.table.client.*;
import com.microsoft.windowsazure.services.table.client.TableQuery.*;
import java.io.FileFilter;
import javax.sql.rowset.CachedRowSet;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Darko Androcec
 */
@WebService(serviceName = "AzureServices")
public class AzureServices {

    // The types for the following variables are
    // defined in the java.sql library.
    Connection connection = null;  // For making the connection
    Statement statement = null;    // For the SQL statement
    ResultSet resultSet = null;    // For the result set, if applicable

    // BEGIN - USE BLOB STORAGE FROM JAVA
    /*
     * Create blob container
     * All blobs reside in a container. Use the CloudBlobClient object to get a reference to the container you want to use. You can create the container if it doesn't exist with the createIfNotExist method, which will otherwise return the existing container. 
     */
    @WebMethod(operationName = "createBlobContainer")
    public String createBlobContainer(@WebParam(name = "containerName") String containerName) {
        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // Get a reference to a container
// The container name must be lower case
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);
                // Create the container if it does not exist
                container.createIfNotExist();
                result = "Blob container is successfully created!";
            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        }

        return result;

    }

    /*
     * Upload blob
     * To upload a file to a blob, get a container reference and use it to get a blob reference. 
     * Once you have a blob reference, you can upload any stream by calling upload on the blob reference. 
     * This operation will create the blob if it doesn't exist, or overwrite it if it does.
     */
    @WebMethod(operationName = "uploadBlob")
    public String uploadBlob(@WebParam(name = "containerName") String containerName, @WebParam(name = "fileName") String fileName, @WebParam(name = "path") String path) {
        String result = "";

        // Retrieve storage account from connection-string
        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

// Retrieve reference to a previously created container
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);
                CloudBlockBlob blob = container.getBlockBlobReference(fileName);
                File source = new File(path);

                try {
                    blob.upload(new FileInputStream(source), source.length());
                    result = "Blob is successfully uploaded!";
                } catch (IOException ex) {
                    Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                    result = "Failure: " + ex.toString();
                }

            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure: " + ex.toString();
            }

// Create or overwrite the "myimage.jpg" blob with contents from a local file

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure: " + ex.toString();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure: " + ex.toString();
        }

// Create the blob client



        return result;

    }

    /*
     * List Blobs
     * 
     */
    @WebMethod(operationName = "listBlobs")
    public String listBlobs(@WebParam(name = "containerName") String containerName) {
        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // Get a reference to a container
// The container name must be lower case
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);
                // Create the container if it does not exist
                // Loop over blobs within the container and output the URI to each of them
                for (ListBlobItem blobItem : container.listBlobs()) {
                    System.out.println(blobItem.getUri());
                    result = result + blobItem.getUri() + "\n";
                }

            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        }

        return result;

    }

    /*
     * Download Blobs
     * call download to transfer the blob contents to a stream object such as a 
     * FileOutputStream that you can use to persist the blob to a local file.
     */
    @WebMethod(operationName = "downloadBlobs")
    public String downloadBlobs(@WebParam(name = "containerName") String containerName) {
        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // Get a reference to a container
// The container name must be lower case
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);
                // Create the container if it does not exist
                // Loop over blobs within the container and output the URI to each of them
                for (ListBlobItem blobItem : container.listBlobs()) {
                    if (blobItem instanceof CloudBlob) {
                        // Download the item and save it to a file with the same name
                        CloudBlob blob = (CloudBlob) blobItem;

                        try {
                            blob.download(new FileOutputStream(blob.getName()));
                        } catch (IOException ex) {
                            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                            result = "Failure";
                        }

                    }
                }

            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure";
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        }

        return result;

    }

    /*
     * Delete blob
     * To delete a blob, get a blob reference, and call delete.
     */
    @WebMethod(operationName = "deleteBlob")
    public String deleteBlob(@WebParam(name = "containerName") String containerName,
            @WebParam(name = "blobName") String blobName) {
        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // Get a reference to a container
// The container name must be lower case
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);

                // Retrieve reference to a blob named "myimage.jpg"
                CloudBlockBlob blob = container.getBlockBlobReference(blobName);

// Delete the blob
                blob.delete();
                result = "Blob is successfully deleted!";

            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure";
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        }

        return result;

    }

    /*
     * Delete blob container
     * To delete a blob container, get a blob container reference, and call delete.
     */
    @WebMethod(operationName = "deleteBlobContainer")
    public String deleteBlobContainer(@WebParam(name = "containerName") String containerName) {
        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the blob client
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
            // Get a reference to a container
// The container name must be lower case
            CloudBlobContainer container;
            try {
                container = blobClient.getContainerReference(containerName);

                container.delete();
                result = "Blob container is successfully deleted!";

            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure";
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure";
        }

        return result;

    }

    // END - USE BLOB STORAGE FROM JAVA
    // BEGIN - USE THE TABLE STORAGE SERVICE FROM JAVA
    /*
     * The Windows Azure Table storage service stores large amounts of structured data. 
     * The service is 
     * a NoSQL datastore which accepts authenticated calls from inside and outside the Windows Azure cloud. 
     */
    /*
     * Create a table
     * A CloudTableClient object lets you get reference objects for tables and 
     * entities. The following code creates a CloudTableClient object and uses 
     * it to create a new table.
     */
    @WebMethod(operationName = "createTable")
    public String createTable(@WebParam(name = "tableName") String tableName) {
        String result = "";

        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);
            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

// Create the table if it doesn't exist.
            CloudTable table;
            try {
                table = tableClient.getTableReference(tableName);
                table.createIfNotExist();
                result = "Table " + tableName + " is successfully created!";
            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure " + ex.toString();
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure " + ex.toString();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure " + ex.toString();
        }



        return result;
    }

    @WebMethod(operationName = "deleteTable")
    public String deleteTable(@WebParam(name = "tableName") String tableName) {
        String result = "";

        CloudStorageAccount storageAccount;
        try {
            storageAccount = CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);
            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();

// Create the table if it doesn't exist.
            CloudTable table;
            try {
                table = tableClient.getTableReference(tableName);
                table.deleteIfExists();
                result = "Table " + tableName + " is successfully deleted!";
            } catch (StorageException ex) {
                Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
                result = "Failure " + ex.toString();
            }

        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure " + ex.toString();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure " + ex.toString();
        }



        return result;
    }
    /*
     * Insert entity to table
     * Entities map to Java objects using a custom class implementing TableEntity. 
     * For convenience, the TableServiceEntity class implements TableEntity and 
     * uses reflection to map properties to getter and setter methods named 
     * for the properties. To add an entity to a table, first create a 
     * class that defines the properties of your entity. 
     * The following code defines an entity class that uses the customer's 
     * first name as the row key, and last name as the partition key. 
     */

    @WebMethod(operationName = "insertEntityToTable")
    public String insertEntityToTable(@WebParam(name = "tableName") String tableName, @WebParam(name = "entity") TableServiceEntity entity) {

        String result = "";

        try {


            // Retrieve storage account from connection-string
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(Constants.STORAGE_CONNECTION_STRING);

            // Create the table client.
            CloudTableClient tableClient = storageAccount.createCloudTableClient();


            // Create an operation to add the new customer to the people table.
            TableOperation insertEntity = TableOperation.insert(entity);

            // Submit the operation to the table service.
            tableClient.execute(tableName, insertEntity);
            result = "Entity is inserted to table!";

        } catch (StorageException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure: " + ex.toString();
        } catch (URISyntaxException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure: " + ex.toString();
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AzureServices.class.getName()).log(Level.SEVERE, null, ex);
            result = "Failure: " + ex.toString();
        }

        return result;
    }

    // END - USE THE TABLE STORAGE SERVICE FROM JAVA
    /**
     * Web service operation
     */
    @WebMethod(operationName = "insertTodoItem")
    public String insertTodoItem(@WebParam(name = "itemId") String itemId, @WebParam(name = "item") String item) {
        //TODO write your implementation code here:

        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(Constants.CONNECTION_STRING);

            // Define the SQL string.
            String sqlString =
                    "SET IDENTITY_INSERT ToDoItems ON "
                    + "INSERT INTO ToDoItems "
                    + "(ToDoItemId, Name, IsComplete) "
                    + "VALUES(" + itemId + ", '" + item + "', 'True')";


            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

            // Execute the statement.
            statement.executeUpdate(sqlString);

            // Provide a message when processing is complete.
            System.out.println("Processing complete.");

        } // Exception handling
        catch (ClassNotFoundException cnfe) {

            System.out.println("ClassNotFoundException "
                    + cnfe.getMessage());
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close resources.
                if (null != connection) {
                    connection.close();
                }
                if (null != statement) {
                    statement.close();
                }
                if (null != resultSet) {
                    resultSet.close();
                }
            } catch (SQLException sqlException) {
                // No additional action if close() statements fail.
            }
        }
        return "Processing complete.";
    }

    // operation to execute query
    @WebMethod(operationName = "executeQuery")
    public String executeQuery(@WebParam(name = "query") String query) {
        //   System.out.println("Query = " + query);
        String result = "";
        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(Constants.CONNECTION_STRING);

            // Define the SQL string.
            String sqlString = query;

            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

            // Execute the statement.
            resultSet = statement.executeQuery(sqlString);

            String tablename = sqlString.substring(sqlString.lastIndexOf(" ") + 1);
            //    System.out.println("Table name = " + tablename);
            
           System.out.println("Jesam li možda tu?");

            AzureDataOntologyCreator.getListOfTableName().add(tablename);
            //   System.out.println("Result set = " + resultSet);
            if (resultSet != null) {


                //       System.out.println("DEbug tu sam");


                // Loop through the results
                while (resultSet.next()) {
                    // Print out the row data

                    //            System.out.println("DEbug tu sam 1");
                    ResultSetMetaData metadata = resultSet.getMetaData();

                    int columnNumber = metadata.getColumnCount();

                    //           System.out.println("DEbug columnNumber = " + columnNumber);

                    for (int i = 1; i <= columnNumber; i++) {
                        System.out.println("COLUMN NAME: "
                                + metadata.getColumnName(i));
                        System.out.println("COLUMN TYPE: "
                                + metadata.getColumnTypeName(i));
                        System.out.println("VALUE: "
                                + resultSet.getString(i));


                        result = result + "\n" + "COLUMN NAME: "
                                + metadata.getColumnName(i) + "\t" + "COLUMN TYPE: "
                                + metadata.getColumnTypeName(i) + "\t" + "VALUE: "
                                + resultSet.getString(i);

                    }

                }

            }


            // Provide a message when processing is complete.
            //   System.out.println("Processing complete.");

            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close resources.
                if (null != connection) {
                    connection.close();
                }
                if (null != statement) {
                    statement.close();
                }


                if (null != resultSet) {
                    resultSet.close();
                }


            } catch (SQLException sqlException) {
                // No additional action if close() statements fail.
                sqlException.printStackTrace();
            }
        }
        return result;

    }

    // for purpose of parsing
    @WebMethod(operationName = "selectQuery")
    public String selectQuery(@WebParam(name = "query") String query) {
        // System.out.println("Query = " + query);
        String result = "";
        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(Constants.CONNECTION_STRING);

            // Define the SQL string.
            String sqlString = query;

            // Use the connection to create the SQL statement.
            statement = connection.createStatement();

              System.out.println("DEBUG 102 SQL string = " + sqlString);

            // Execute the statement.
            resultSet = statement.executeQuery(sqlString);

            String tablename = sqlString.substring(sqlString.lastIndexOf(" ") + 1);
            System.out.println("DEBUG 102 Table name = " + tablename);
            
            System.out.println("DEBUG 102 resultSet to String = " + resultSet.toString());

            AzureDataOntologyCreator.getListOfTableName().add(tablename);
            // System.out.println("Result set = " + resultSet);
            if (resultSet != null) {


                System.out.println("DEBUG 102 Result set isn't null!");

                // CachedRowSet hold a ResultSet independently of the originating JDBC connection
                CachedRowSet crs = new CachedRowSetImpl();

                crs.populate(resultSet);

                AzureDataOntologyCreator.getListOfEntities().add(crs);

                
 
                // Loop through the results
                while (crs.next()) {
                    // Print out the row data

                    System.out.println("DEbug tu sam 1");
                    ResultSetMetaData metadata = crs.getMetaData();

                    int columnNumber = metadata.getColumnCount();

                    System.out.println("DEbug columnNumber = " + columnNumber);

                    for (int i = 1; i <= columnNumber; i++) {
                        System.out.println("DEBUG 101 COLUMN NAME: "
                                + metadata.getColumnName(i));
                        System.out.println("DEBUG 101 COLUMN TYPE: "
                                + metadata.getColumnTypeName(i));
                        System.out.println("DEBUG 101 "
                                + "VALUE: "
                                + crs.getString(i));


                        result = result + "\n" + "COLUMN NAME: "
                                + metadata.getColumnName(i) + "\t" + "COLUMN TYPE: "
                                + metadata.getColumnTypeName(i) + "\t" + "VALUE: "
                                + crs.getString(i);

                    }

                }
                
              


            }


            // Provide a message when processing is complete.
            System.out.println("Processing complete.");

            return result;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            
            try {
                // Close resources.
                
                if (null != resultSet) {
                  //  resultSet.close();
                }

                if (null != statement) {
                   // statement.close();
                }

                if (null != connection) {
                    connection.close();
                }
                
                
                
            } catch (SQLException sqlException) {
                // No additional action if close() statements fail.
                sqlException.printStackTrace();
            }
            
            
        }
        return result;

    }

    @WebMethod(operationName = "findKeys")
    public List<String> findKeys() {
        List<String> results = new ArrayList();
        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(Constants.CONNECTION_STRING);

            // Define the SQL string.
            /*
             String sqlFindKeys = "SELECT DISTINCT Constraint_Name AS [Constraint],"
             + " Table_Schema AS [Schema],"
             + "Table_Name AS [TableName]"
             + "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE";
             */


            // query 1
            /*
             String sqlFindKeys = "SELECT OBJECT_NAME(OBJECT_ID) AS NameofConstraint,"
             + "             SCHEMA_NAME(schema_id) AS SchemaName,"
             + "             OBJECT_NAME(parent_object_id) AS TableName,"
             + "             type_desc AS ConstraintType"
             + "             FROM sys.objects"
             + "             WHERE type_desc IN ('FOREIGN_KEY_CONSTRAINT','PRIMARY_KEY_CONSTRAINT')";
             */

            /*
             * Second SQL option
             *  SELECT OBJECT_NAME(OBJECT_ID) AS NameofConstraint,
             SCHEMA_NAME(schema_id) AS SchemaName,
             OBJECT_NAME(parent_object_id) AS TableName,
             type_desc AS ConstraintType
             FROM sys.objects
             WHERE type_desc IN ('FOREIGN_KEY_CONSTRAINT','PRIMARY_KEY_CONSTRAINT')

             */

            /*
             String sqlFindKeys = "SELECT t.table_schema AS PrimarySchemaName , "
             + "t.TABLE_NAME AS PrimaryKeyTable, "
             + "tc.CONSTRAINT_NAME AS PrimaryKey, "
             + "COALESCE(tc2.constraint_schema,'N/A') AS ForeignSchemaName, "
             + "COALESCE(rc1.CONSTRAINT_NAME,'N/A') AS ForeignKey ,"
             + "COALESCE(tc2.TABLE_NAME,'N/A') AS ForeignKeyTable "
             + "FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc "
             + "INNER JOIN INFORMATION_SCHEMA.TABLES t on tc.TABLE_NAME = t.TABLE_NAME "
             + "LEFT JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc1 ON tc.CONSTRAINT_NAME =rc1.UNIQUE_CONSTRAINT_NAME "
             + "LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc2 ON tc2.CONSTRAINT_NAME =rc1.CONSTRAINT_NAME "
             + "WHERE TC.CONSTRAINT_TYPE ='PRIMARY KEY' "
             + "ORDER BY tc.TABLE_NAME,tc.CONSTRAINT_NAME,rc1.CONSTRAINT_NAME";
             */
            String sqlFindKeys = "SELECT "
                    + "K_Table = FK.TABLE_NAME, "
                    + "FK_Column = CU.COLUMN_NAME, "
                    + "PK_Table = PK.TABLE_NAME, "
                    + "PK_Column = PT.COLUMN_NAME, "
                    + "Constraint_Name = C.CONSTRAINT_NAME "
                    + "FROM "
                    + "INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS C "
                    + "INNER JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS FK "
                    + "ON C.CONSTRAINT_NAME = FK.CONSTRAINT_NAME "
                    + "INNER JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS PK "
                    + "   ON C.UNIQUE_CONSTRAINT_NAME = PK.CONSTRAINT_NAME "
                    + "INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE CU "
                    + "   ON C.CONSTRAINT_NAME = CU.CONSTRAINT_NAME "
                    + "INNER JOIN ( "
                    + "           SELECT "
                    + "               i1.TABLE_NAME, "
                    + "               i2.COLUMN_NAME "
                    + "           FROM "
                    + "               INFORMATION_SCHEMA.TABLE_CONSTRAINTS i1 "
                    + "           INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE i2 "
                    + "               ON i1.CONSTRAINT_NAME = i2.CONSTRAINT_NAME "
                    + "          WHERE "
                    + "               i1.CONSTRAINT_TYPE = 'PRIMARY KEY' "
                    + "          ) PT "
                    + "   ON PT.TABLE_NAME = PK.TABLE_NAME ";



            // Use the connection to create the SQL statement.
            statement = connection.createStatement();



            // Execute the statement.
            resultSet = statement.executeQuery(sqlFindKeys);

            System.out.println("DEBUG: Result set " + resultSet.toString());

            if (resultSet != null) {

                //   CachedRowSetImpl crs = new CachedRowSetImpl();
                //   crs.populate(resultSet);


                System.out.println("DEBUG KEYS:");

                // Loop through the results
                while (resultSet.next()) {
                    // Print out the row data

                    System.out.println("DEBUG KEYS: inside resultset loop");

                    /*  // result for query 1
                     result = "NameofConstraint="
                     + resultSet.getString("NameofConstraint")
                     + " SchemaName= "
                     + resultSet.getString("SchemaName") + " TableName="
                     + resultSet.getString("TableName") + " ConstraintType="
                     + resultSet.getString("ConstraintType");
                     */

                    String result = "K_Table=" + resultSet.getString("K_Table") + "|"
                            + "FK_Column=" + resultSet.getString("FK_Column") + "|"
                            + "PK_Table=" + resultSet.getString("PK_Table") + "|"
                            + "PK_Column=" + resultSet.getString("PK_Column") + "|"
                            + "Constraint_Name=" + resultSet.getString("Constraint_Name") + "|";


                    System.out.println(
                            result);
                    results.add(result);
                    //  return result;


                }

            }

            String findPrimaryKeys = "	SELECT  i.name AS IndexName, "
                    + "OBJECT_NAME(ic.OBJECT_ID) AS TableName, "
                    + "COL_NAME(ic.OBJECT_ID,ic.column_id) AS ColumnName "
                    + "FROM    sys.indexes AS i INNER JOIN "
                    + "      sys.index_columns AS ic ON  i.OBJECT_ID = ic.OBJECT_ID "
                    + "                            AND i.index_id = ic.index_id "
                    + "WHERE   i.is_primary_key = 1 ";

            resultSet = statement.executeQuery(findPrimaryKeys);

            System.out.println("DEBUG: Result set primary keys " + resultSet.toString());

            if (resultSet != null) {

                //   CachedRowSetImpl crs = new CachedRowSetImpl();
                //   crs.populate(resultSet);


                System.out.println("DEBUG PRIMARY KEYS:");

                // Loop through the results
                while (resultSet.next()) {
                    // Print out the row data

                    System.out.println("DEBUG KEYS: inside primary keyresultset loop");

                    /*  // result for query 1
                     result = "NameofConstraint="
                     + resultSet.getString("NameofConstraint")
                     + " SchemaName= "
                     + resultSet.getString("SchemaName") + " TableName="
                     + resultSet.getString("TableName") + " ConstraintType="
                     + resultSet.getString("ConstraintType");
                     */

                    String result = "PrimaryKeyIndexName=" + resultSet.getString("IndexName") + "|"
                            + "PrimaryKeyTableName=" + resultSet.getString("TableName") + "|"
                            + "PrimaryKeyColumnName=" + resultSet.getString("ColumnName") + "|";


                    System.out.println(
                            result);
                    results.add(result);
                    //  return result;


                }

            }




            // Provide a message when processing is complete.
            System.out.println("Processing complete.");

            return results;
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close resources.
                if (null != connection) {
                    connection.close();
                }
                if (null != statement) {
                    statement.close();
                }


                if (null != resultSet) {
                    resultSet.close();
                }


            } catch (SQLException sqlException) {
                // No additional action if close() statements fail.
            }
        }
        return results;

    }

    @WebMethod(operationName = "createAzureDataModelOntology")
    public boolean createAzureDataModelOntology() {

        List<String> foundKeysResult = findKeys();
        AzureDataOntologyCreator.create(foundKeysResult);
        return true;
    }

    // get data types from current Azure SQL storage
    @WebMethod(operationName = "getDataTypesInCurrentData")
    public List<String> getDataTypesInCurrentData() {

        // get all tables from Azure's csv files

        File[] files = new File(Constants.AZURE_CSV_DIRECTORY).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith("FILTERED_D_C");
            }
        });
        
         // nije nađeni filtrirani data container, operaciju treba provesti za sve
        if (files == null || files.length == 0) {
            files = new File(Constants.AZURE_CSV_DIRECTORY).listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".csv");
                }
            });
        }

        System.out.println("DEBUG CSV files size " + files.length);

        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {

                String filename = files[i].getName();
                System.out.println("DEBUG filename = " + filename);
                
                if (filename.contains("FILTERED_D_C")) {
                    filename = filename.substring(12);
                }

                String azureTableName = filename.substring(0, filename.indexOf("."));
                System.out.println("DEBUG azureTableName " + azureTableName);

                String sqlQuery = "SELECT * FROM " + azureTableName;

                System.out.println("DEBUG: sql query before invoking service = " + sqlQuery);
                String queryResult = selectQuery(sqlQuery);
                System.out.println("DEBUG QUERY RESULT ==>> " + queryResult);
            }
        }

        return AzureDataOntologyCreator.getDataTypesInCurrentData();
    }

    @WebMethod(operationName = "createTableFromDataOntology")
    public String createTableFromDataOntology(@WebParam(name = "pathToOntology") String pathToOntology) {
        String result = "";


        try {
            // Ensure the SQL Server driver class is available.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Establish the connection.
            connection = DriverManager.getConnection(Constants.CONNECTION_STRING);

            // Define the SQL string.

            /*
             String sqlString =
             "CREATE TABLE PersonMigrate ("
             + "[PersonID] [int] IDENTITY(1,1) NOT NULL,"
             + "[LastName] [nvarchar](50) NOT NULL,"
             + "[FirstName] [nvarchar](50) NOT NULL)";
             */

            DataOntologyReader.readOntology(pathToOntology);

            List<TableUtil> tables = DataOntologyReader.getTables();

            System.out.println("DEBUG Tables size " + tables.size());

            String generateSql = "";
            for (TableUtil tableUtil : tables) {

                generateSql = "CREATE TABLE " + tableUtil.getName() + "Migrated (";

                List<String> properties = tableUtil.getProperties();
                List<String> propertyTypes = tableUtil.getPropertyTypes();
                List<ArrayList<String>> rows = tableUtil.getRows();
                List<String> identifiersValues = tableUtil.getIdentifiersValues();
                List<String> linksToOtherObjects = tableUtil.getListOfLinksBetweenDataObjects();

                System.out.println("DEBUG tableUtil " + tableUtil.toString());
                System.out.println("DEBUG properties size " + properties.size());
                System.out.println("DEBUG propertyTypes size " + propertyTypes.size());
                System.out.println("DEBUG rows size " + rows.size());
                System.out.println("DEBUG idenfiersValues " + identifiersValues.size());
                System.out.println("DEBUG linksToOtherObjects " + linksToOtherObjects.size());

                String identifier = tableUtil.getIdentifier();


                String columnList = "(";

                // add identity (primary key) on first column
                // replace later Identifier_Type with correct Azure data type
                // identifier is always string type in the ontology

                generateSql = generateSql + " " + identifier + " varchar(255)  NOT NULL, ";
                columnList = columnList + identifier + ", ";


                int i = 0;
                for (String property : properties) {

                    // if the property is primary key, then only obtain its correct data type and replace Identifier_Type with it in SQL query string

                    // VALUES is reserved word in SQL, so I need to rename it in order to prevent the error
                    if (property.trim().compareTo("values") == 0) {
                        property = "values_1";
                    }

                    // INDEX is reserved word in SQL, so I need to rename it in order to prevent the error
                    if (property.trim().compareTo("index") == 0) {
                        property = "index_1";
                    }

                    generateSql = generateSql + property;

                    columnList = columnList + property + ", ";

                    if (i < propertyTypes.size()) {
                        String type = propertyTypes.get(i);
                        // System.out.println("DEBUG type = " + type);

                        // TODO convert types from XSD type to Azure type
                        String convertedType = DataTypeConverter.convertFromOwlDataType(TypeConstants.AZURE, type);

                        convertedType = convertedType.toLowerCase();
                        // System.out.println("DEBUG converted type = " + convertedType);


                        if (type == null || convertedType == null || type.trim().compareTo("") == 0
                                || convertedType.trim().compareTo("") == 0) {
                            convertedType = "varchar";
                        }



                        generateSql = generateSql + " [" + convertedType + "] ";

                        // if type is varchar then append (255)
                        if (convertedType.contains("varchar")) {
                            generateSql = generateSql + "(1000) ";
                        }

                        if (convertedType.contains("varbinary")) {
                            generateSql = generateSql + "(1000) ";
                        }



                        generateSql = generateSql + " , ";
                    } else {
                        generateSql = generateSql + " [" + "varchar" + "] (1000) ";
                        generateSql = generateSql + " , ";
                    }

                    i++;
                }

                columnList = columnList.substring(0, columnList.lastIndexOf(","));
                columnList = columnList + ")";


                /*
                 * Unlike SQL Server, every table in SQL Azure needs to have a clustered index. 
                 * A clustered index is usually created on the primary key column of the table. 
                 * Clustered indexes sort and store the data rows in the table based on their key values (columns in the index).
                 * There can only be one clustered index per table, because the data rows themselves can only be sorted in one order.
                 */
                generateSql = generateSql + " CONSTRAINT PK_" + tableUtil.getName() + "Migrated" + " PRIMARY KEY CLUSTERED (" + identifier + ") ";


                // set foreign key constraints
                int temp = 0;
                for (String linkString : linksToOtherObjects) {
                    System.out.println("DEBUG linkString " + linkString);

                    String tableName = linkString.substring(0, linkString.indexOf("("));
                    System.out.println("DEBUG link string table name = " + tableName);

                    if (tableUtil.getName().compareTo(tableName) == 0) {
                        String foreignKeyTableString = linkString.substring(linkString.indexOf("#") + 1, linkString.lastIndexOf("_"));
                        System.out.println("DEBUG foreignKeyTableString = " + foreignKeyTableString);

                        String foreignKeyColumnString = "";

                        List<String> tableProperties = tableUtil.getProperties();
                        // we get foreign key table property as a property wich contains the name of the table to which foreign key connects
                        for (String tableProperty : tableProperties) {
                            if (tableProperty.toLowerCase().contains(foreignKeyTableString.toLowerCase())) {
                                foreignKeyColumnString = tableProperty;
                            }

                        }

                        System.out.println("DEBUG foreignKeyColumnString = " + foreignKeyColumnString);


                        generateSql = generateSql + " , " + " CONSTRAINT FK_" + tableUtil.getName() + "Migrated" + "_" + temp
                                + " FOREIGN KEY (" + foreignKeyColumnString + ") "
                                + " REFERENCES " + foreignKeyTableString + "Migrated" + " (identifier)";

                    }

                    temp++;
                }


                generateSql = generateSql + ")";





                System.out.println("DEBUG generateSql = " + generateSql);
                // System.out.println("DEBUG columnList => " + columnList);



                // EXECUTION OF QUERIES TO CREATE TABLES

                // Use the connection to create the SQL statement.
                statement = connection.createStatement();

                // Execute the statement.
                statement.executeUpdate(generateSql);


                /*
                 String sqlString = 
                 "SET IDENTITY_INSERT Person ON " + 
                 "INSERT INTO Person " + 
                 "(PersonID, LastName, FirstName) " + 
                 "VALUES(1, 'Abercrombie', 'Kim')," + 
                 "(2, 'Goeschl', 'Gerhard')," + 
                 "(3, 'Grachev', 'Nikolay')," + 
                 "(4, 'Yee', 'Tai')," + 
                 "(5, 'Wilson', 'Jim')";*/

                String createQuery =
                        "INSERT INTO " + tableUtil.getName() + "Migrated " + columnList;
                createQuery = createQuery + " VALUES ";
                // Insert queries to put data in the tables
                int jj = 0;
                for (ArrayList<String> rowData : rows) {

                    createQuery = createQuery + "(";
                    // insert identifier value
                    createQuery = createQuery + "'" + tableUtil.getIdentifiersValues().get(jj) + "', ";
                    int k = 0;

                    for (String column : rowData) {
                        // System.out.println("DEBUG ROW DATA: " + column);

                        // TODO - if column data is not String, then '' are not needed

                        if (column == null || column.trim().compareTo("") == 0) {

                            createQuery = createQuery + " null, ";

                        } else {

                            String queryItem = StringEscapeUtils.escapeHtml4(column);
                            queryItem = StringEscapeUtils.escapeJava(column);

                            // da se izbjegne jedna greška
                            if (queryItem.contains("'#nav")) {
                                queryItem = queryItem.replace("'#nav", "nav");
                                System.out.println("DEBUG querItem nav= " + queryItem);
                            }

                            String type = propertyTypes.get(k);
                            System.out.println("DEBUG property type 222 = " + type);

                            // TODO convert types from XSD type to Azure type
                            String convertedType = DataTypeConverter.convertFromOwlDataType(TypeConstants.AZURE, type);

                            convertedType = convertedType.toLowerCase();
                            // System.out.println("DEBUG converted type = " + convertedType);


                            if (type == null || convertedType == null || type.trim().compareTo("") == 0
                                    || convertedType.trim().compareTo("") == 0) {
                                convertedType = "varchar";
                            }

                            // errors with varbinary: Implicit conversion from data type varchar to varbinary is not allowed. Use the CONVERT function to run this query.
                            if (convertedType.contains("varbinary")) {
                                // without quotes
                                createQuery = createQuery + "CONVERT(varbinary(1000), '" + queryItem + "' ,0)"
                                        + ", ";
                            } else {
                                createQuery = createQuery + "'" + queryItem
                                        + "', ";
                            }





                        }
                        k++;
                    }

                    createQuery = createQuery.substring(0, createQuery.lastIndexOf(","));
                    createQuery = createQuery + "),";
                    jj++;

                }

                createQuery = createQuery.substring(0, createQuery.lastIndexOf(","));



                System.out.println("DEBUG INSERT QUERY => " + createQuery);

                // EXECUTION OF QUERIES TO INSERT DATA INTO TABLES

                // Use the connection to create the SQL statement.
                statement = connection.createStatement();


                // Execute the statement.
                statement.executeUpdate(createQuery);

                // Provide a message when processing is complete.
                System.out.println("Processing complete.");



            }



            // first debug that I get proper sql string






        } // Exception handling
        catch (ClassNotFoundException cnfe) {

            System.out.println("ClassNotFoundException "
                    + cnfe.getMessage());
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Close resources.
                if (null != connection) {
                    connection.close();
                }
                if (null != statement) {
                    statement.close();
                }
                if (null != resultSet) {
                    resultSet.close();
                }
            } catch (SQLException sqlException) {
                // No additional action if close() statements fail.
            }
        }
        return result;

    }
    /*  // CODE FOR VARIOUS DATABASE OPERATION BY USING JDBC DRIVER TO ACCESS SQL AZURE 
     // To create a table
    
     // The following code shows you how to create a table named Person.
    
    
    
     // Connection string for your SQL Database server.
     // Change the values assigned to your_server, 
     // your_user@your_server,
     // and your_password.
     String connectionString = 
     "jdbc:sqlserver://your_server.database.windows.net:1433" + ";" +  
     "database=gettingstarted" + ";" + 
     "user=your_user@your_server" + ";" +  
     "password=your_password";
    
     // The types for the following variables are
     // defined in the java.sql library.
     Connection connection = null;  // For making the connection
     Statement statement = null;    // For the SQL statement
     ResultSet resultSet = null;    // For the result set, if applicable
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "CREATE TABLE Person (" + 
     "[PersonID] [int] IDENTITY(1,1) NOT NULL," +
     "[LastName] [nvarchar](50) NOT NULL," + 
     "[FirstName] [nvarchar](50) NOT NULL)";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling
     catch (ClassNotFoundException cnfe)  
     {
    
     System.out.println("ClassNotFoundException " +
     cnfe.getMessage());
     }
     catch (Exception e)
     {
     System.out.println("Exception " + e.getMessage());
     e.printStackTrace();
     }
     finally
     {
     try
     {
     // Close resources.
     if (null != connection) connection.close();
     if (null != statement) statement.close();
     if (null != resultSet) resultSet.close();
     }
     catch (SQLException sqlException)
     {
     // No additional action if close() statements fail.
     }
     }
    
     }
    
     }
    
     // To create an index on a table
    
     // The following code shows you how to create an index named index1 on the Person table, using the PersonID column.
    
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "CREATE CLUSTERED INDEX index1 " + "ON Person (PersonID)";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     // To insert rows
    
     // The following code shows you how to add rows to the Person table.
    
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "SET IDENTITY_INSERT Person ON " + 
     "INSERT INTO Person " + 
     "(PersonID, LastName, FirstName) " + 
     "VALUES(1, 'Abercrombie', 'Kim')," + 
     "(2, 'Goeschl', 'Gerhard')," + 
     "(3, 'Grachev', 'Nikolay')," + 
     "(4, 'Yee', 'Tai')," + 
     "(5, 'Wilson', 'Jim')";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     // To retrieve rows
    
     // The following code shows you how to retrieve rows from the Person table.
    
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = "SELECT TOP 10 * FROM Person";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     resultSet = statement.executeQuery(sqlString);
    
     // Loop through the results
     while (resultSet.next())
     {
     // Print out the row data
     System.out.println(
     "Person with ID " + 
     resultSet.getString("PersonID") + 
     " has name " +
     resultSet.getString("FirstName") + " " +
     resultSet.getString("LastName"));
     }
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     // The code above selected the top 10 rows from the Person table. If you want to return all rows, modify the SQL statement to the following:
    
     String sqlString = "SELECT * FROM Person";
    
     //To retrieve rows using a WHERE clause
    
     // To retrieve rows using a clause, use the code as shown above, except change the SQL statement to include a clause. The following SQL statement includes a clause for rows whose FirstName value equals Jim.
    
     // Define the SQL string.
     String sqlString = "SELECT * FROM Person WHERE FirstName='Jim'";
    
     WHERE clauses can also be used when retrieving counts, updating rows, or deleting rows.
     To retrieve a count of rows
    
     // The following code shows you how to retrieve a count of rows from the Person table.
    
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = "SELECT COUNT (PersonID) FROM Person";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     resultSet = statement.executeQuery(sqlString);
    
     // Print out the returned number of rows.
     while (resultSet.next())
     {
     System.out.println("There were " + 
     resultSet.getInt(1) +
     " rows returned.");
     }
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     // To update rows
    
     // The following code shows you how to update rows. In this example, the LastName value is changed to Kim for any rows where the FirstName value is Jim.
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "UPDATE Person " + "SET LastName = 'Kim' " + "WHERE FirstName='Jim'";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }// Exception handling and resource closing not shown...
    
     // To delete rows
    
     // The following code shows you how to delete rows. In this example, any rows where the FirstName value is Jim are deleted.
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "DELETE from Person " + 
     "WHERE FirstName='Jim'";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     //To check whether a table exists
    
     //The following code shows you how to determine whether a table exists.
    
     // Connection string for your SQL Database server.
     // Change the values assigned to your_server, 
     // your_user@your_server,
     // and your_password.
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "IF EXISTS (SELECT 1 " +
     "FROM sysobjects " + 
     "WHERE xtype='u' AND name='Person') " +
     "SELECT 'Person table exists.'" +
     "ELSE  " +
     "SELECT 'Person table does not exist.'";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     resultSet = statement.executeQuery(sqlString);
    
     // Display the result.
     while (resultSet.next())
     {
     System.out.println(resultSet.getString(1));
     }
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     // To drop an index
    
     // The following code shows you how to drop an index named index1 on the Person table.
    
     // Connection string for your SQL Database server.
     // Change the values assigned to your_server, 
     // your_user@your_server,
     // and your_password.
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = 
     "DROP INDEX index1 " + 
     "ON Person";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     //To drop a table
    
     //The following code shows you how to drop a table named Person.
    
     // Connection string for your SQL Database server.
     // Change the values assigned to your_server, 
     // your_user@your_server,
     // and your_password.
    
    
     try
     {
     // Ensure the SQL Server driver class is available.
     Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
     // Establish the connection.
     connection = DriverManager.getConnection(connectionString);
    
     // Define the SQL string.
     String sqlString = "DROP TABLE Person";
    
     // Use the connection to create the SQL statement.
     statement = connection.createStatement();
    
     // Execute the statement.
     statement.executeUpdate(sqlString);
    
     // Provide a message when processing is complete.
     System.out.println("Processing complete.");
    
     }
     // Exception handling and resource closing not shown...
    
     */
}
