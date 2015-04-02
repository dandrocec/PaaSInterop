/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dandrocec.gwt.interoperability.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import dandrocec.gwt.interoperability.client.GwtInteroperabilityService;
import hr.org.foi.aiplanning.services.AiPlanningService_Service;
import hr.org.foi.salesforce.api.services.SalesForceServices_Service;
import javax.xml.ws.WebServiceRef;


/**
 *
 * @author Darko Androcec
 */
public class GwtInteroperabilityServiceImpl extends RemoteServiceServlet implements GwtInteroperabilityService {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/AiPlanningServices/AiPlanningService.wsdl")
    private AiPlanningService_Service service_1;
  
    public static String hasApiOperation = "";
    
    @Override
    public void removeUnrelatedCsvFiles(String directory, String chosenDataContainer) {
        removeUnrelatedCsvFiles_1(directory, chosenDataContainer);
    }
    
    @Override
    public String executeFoundPlan(String plan) {
        System.out.println("Plan = " + plan);
        return executeServicesFromPlan(plan);
        
    }
    
    @Override
    public String getInteroperabilityProblems(String aiGoal) {
        return getInteroperabilityProblems_1(aiGoal);
    }
    
    
    
    @Override
    public String prepareAiplanning(String aiGoal) {
      
        System.out.println("DEBUG: AI goal is = " + aiGoal);
        
        String problem = createProblemFile(aiGoal);
        
        System.out.println("DEBUG: Problem is = " + problem);
        
        String result = prepareAiPlan();
        
        return result;
        
    }
   
    @Override
    public String executeAiplanning(String aiGoal) {
       
        
        String aiPlan = findAiPlan();
        
        return aiPlan ;
    }
    
    @Override
    public String doesPlanExist() {
        String result = "";
        if (doesPlanExist_1()){
            result = "The solution for interoperability action exists!";
        }
        else{
            result = "There are interoperability problems!";
        }
        return result;
    }

    private String createProblemFile(java.lang.String aiGoal) {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.createProblemFile(aiGoal);
    }

    private String findAiPlan() {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.findAiPlan();
    }

    private boolean doesPlanExist_1() {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.doesPlanExist();
    }

    private String prepareAiPlan() {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.prepareAiPlan();
    }



 

    private String getInteroperabilityProblems_1(java.lang.String aiGoal) {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.getInteroperabilityProblems(aiGoal);
    }

    private String executeServicesFromPlan(java.lang.String plan) {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        return port.executeServicesFromPlan(plan);
    }

    private void removeUnrelatedCsvFiles_1(java.lang.String directory, java.lang.String chosenDataContainer) {
        hr.org.foi.aiplanning.services.AiPlanningService port = service_1.getAiPlanningServicePort();
        port.removeUnrelatedCsvFiles(directory, chosenDataContainer);
    }

    
    
    

    
}
