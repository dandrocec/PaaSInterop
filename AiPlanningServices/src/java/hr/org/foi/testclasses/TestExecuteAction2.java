/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.testclasses;

import hr.org.foi.aiplanning.services.WebServicesFromPlanExecutor;

/**
 *
 * @author Darko Androcec
 */
public class TestExecuteAction2 {
     public static void main(String[] args) {
         String action = "[(!createdatamodelontology azure) (!createdataelementsfromontology googleappengine) --------------- ]";

         WebServicesFromPlanExecutor.execute(action);
     }
}
