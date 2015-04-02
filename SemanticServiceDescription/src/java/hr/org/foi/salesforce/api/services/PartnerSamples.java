/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.salesforce.api.services;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.sobject.*;
import com.sforce.soap.partner.*;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.ConnectionException;
import com.sforce.soap.partner.Error;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.*;

public class PartnerSamples {

    PartnerConnection partnerConnection = null;
    private static BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        PartnerSamples samples = new PartnerSamples();
        if (samples.login()) {
            // Add calls to the methods in this class.
            // For example:
            // samples.querySample();
            String result = samples.createSample();
            System.out.println("Result of create call " + result);
        }
    }

    private String getUserInput(String prompt) {
        String result = "";
        try {
            System.out.print(prompt);
            result = reader.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    private boolean login() {
        boolean success = false;
        String username = Constants.SALESFORCE_USERNAME;
        String password = Constants.SALESFORCE_PASSWORD;
        String authEndPoint = Constants.SALESFORCE_AUTH_ENPOINT_FOR_PARTNER;

        try {
            ConnectorConfig config = new ConnectorConfig();
            config.setUsername(username);
            config.setPassword(password);

            config.setAuthEndpoint(authEndPoint);
            config.setTraceFile("traceLogs.txt");
            config.setTraceMessage(true);
            config.setPrettyPrintXml(true);

            partnerConnection = new PartnerConnection(config);

            success = true;
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

        return success;
    }

    // 
    // Add your methods here.
    //
    public String createSample() {
        String result = null;
        try {
            // Create a new sObject of type Contact
            // and fill out its fields.
            SObject contact = new SObject();
            contact.setType("Contact");
            contact.setField("FirstName", "Otto");
            contact.setField("LastName", "Jespersen");
            contact.setField("Salutation", "Professor");
            contact.setField("Phone", "(999) 555-1234");
            contact.setField("Title", "Philologist");

            // Add this sObject to an array 
            SObject[] contacts = new SObject[1];
            contacts[0] = contact;
            // Make a create call and pass it the array of sObjects
            SaveResult[] results = partnerConnection.create(contacts);

            // Iterate through the results list
            // and write the ID of the new sObject
            // or the errors if the object creation failed.
            // In this case, we only have one result
            // since we created one contact.
            for (int j = 0; j < results.length; j++) {
                if (results[j].isSuccess()) {
                    result = results[j].getId();
                    System.out.println(
                            "\nA contact was created with an ID of: " + result);
                } else {
                    // There were errors during the create call,
                    // go through the errors array and write
                    // them to the console
                    for (int i = 0; i < results[j].getErrors().length; i++) {
                        Error err = results[j].getErrors()[i];
                        System.out.println("Errors were found on item " + j);
                        System.out.println("Error code: "
                                + err.getStatusCode().toString());
                        System.out.println("Error message: " + err.getMessage());
                    }
                }
            }
        } catch (ConnectionException ce) {
            ce.printStackTrace();
        }
        return result;
    }
}