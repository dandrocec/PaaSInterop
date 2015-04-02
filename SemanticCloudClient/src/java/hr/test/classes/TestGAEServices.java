/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.test.classes;

import com.google.appengine.api.datastore.Entity;
import hr.org.foi.appengine.api.services.GoogleEntity;
import hr.org.foi.appengine.api.services.GoogleKey;
import hr.org.foi.appengine.api.services.GoogleProperty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;

/**
 *
 * @author Darko Androcec
 */
public class TestGAEServices {

    public static void main(String[] args) {
        
        
      /*
        
        GoogleProperty property = new GoogleProperty();
        property.setGoogleProperyName("firstName");
        property.setGoogleProperyValue("Darko");
        
     
        
        GoogleProperty property1 = new GoogleProperty();
        property1.setGoogleProperyName("lastName");
        property1.setGoogleProperyValue("Androcec");
        
  
        
    
        GoogleEntity employee = new GoogleEntity();
        employee.setGoogleEntityKind("Employee");
        employee.getGoogleEntityProperties().add(property);
        employee.getGoogleEntityProperties().add(property1);
    
        

        
      
       
        String result = put(employee);
        System.out.println("DEBUG result = " + result);
        
 
        */
        
        /*
        // test of retrieve call
        GoogleKey googleKey = new GoogleKey();
        googleKey.setGoogleKeyKind("Employee");
        googleKey.setGoogleKeyId(89001);
        
        String retrieveResult = retrieve(googleKey);
        System.out.println("DEBUG Retrieve result: " + retrieveResult);
        */
        
         // test of delete call
        GoogleKey googleKey = new GoogleKey();
        googleKey.setGoogleKeyKind("Employee");
        googleKey.setGoogleKeyId(89001);
        
        String retrieveResult = delete(googleKey);
        System.out.println("DEBUG Delete result: " + retrieveResult);
        
        

    }

    private static String put(hr.org.foi.appengine.api.services.GoogleEntity entity) {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.put(entity);
    }

    private static String retrieve(hr.org.foi.appengine.api.services.GoogleKey entity) {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.retrieve(entity);
    }

    private static String delete(hr.org.foi.appengine.api.services.GoogleKey entity) {
        hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service service = new hr.org.foi.appengine.api.services.GoogleAppEngineServices_Service();
        hr.org.foi.appengine.api.services.GoogleAppEngineServices port = service.getGoogleAppEngineServicesPort();
        return port.delete(entity);
    }

    



}
