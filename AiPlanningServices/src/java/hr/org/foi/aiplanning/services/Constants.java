/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;

/**
 *
 * @author Darko Androcec
 */
public class Constants {
    // migrate all data from one PaaS storage to another
    public static final String ACTION_MIGRATE_DATA = "migrateData";
    // migrate chosen data container (e.g., entity, table, etc.)
    public static final String ACTION_MIGRATE_DATA_CONTAINER = "migrateDC";
    // add existing user to another PaaS 
    public static final String ACTION_ADDUSER_TO_ANOTHERPAAS = "addUserToAnotherPaaS";


    public static final String GOOGLE_APP_ENGINE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\Vosao";
    public static final String SALESFORCE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\VosaoSalesforce";
    public static final String AZURE_CSV_DIRECTORY = "D:\\DOKTORAT\\exportPodataka\\VosaoAzure";
    

}
