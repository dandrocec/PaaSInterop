
package hr.org.foi.salesforce.api.services;



/**
 *
 * @author Darko Androcec
 */
public class Constants {

    public static final String SALESFORCE_USERNAME = "SALESFORCE_USERNAME";
    // novi password je Konjak99, ostalo je security token
    public static final String SALESFORCE_PASSWORD = "SALESFORCE_PASSWORD";
    public static final String SALESFORCE_AUTH_ENPOINT_FOR_PARTNER  =  "https://login.salesforce.com/services/Soap/u/29.0";
   
    // for initial data - customer, warning, and credit
    public static final  String SALESFORCE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\Salesforce";
    
    // for vosao cms
    //public static final String SALESFORCE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\VosaoSalesforce";
    
    private static String sessionId;
    private static String userId;
    public static final String SALESFORCE_REFERENCE_DATATYPE = "reference";

    public static String getSessionId() {
        return sessionId;
    }

    public static void setSessionId(String sessionId) {
        Constants.sessionId = sessionId;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        Constants.userId = userId;
    }
    
    
}
