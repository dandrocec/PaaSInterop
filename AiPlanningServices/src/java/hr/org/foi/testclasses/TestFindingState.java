/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.testclasses;

import JSHOP2.JSHOP2;
import JSHOP2.PlanStepInfo;
import JSHOP2.State;
import hr.org.foi.aiplanning.services.WebServicesFromPlanExecutor;
import hr.org.foi.generated.aifiles.problem;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Darko Androcec
 */
public class TestFindingState {
    
    public static void main(String[] args) {
        WebServicesFromPlanExecutor.getStepStatesAfterPlanExecution();
    }

    /*
    public static void main(String[] args) {
        String result = "";



        if (problem.getPlans() == null || problem.getPlans().isEmpty()) {
            result = "A plan was not found!";
        } else {
            result = problem.getPlans().toString();
        }

        System.out.println("Plan result: " + result);


        ArrayList<PlanStepInfo> planSteps = JSHOP2.getPlanStepList();

        //   System.out.println(planSteps);
        
          ArrayList<String> stepStates = null;


        for (Iterator<PlanStepInfo> step = planSteps.iterator(); step.hasNext();) {

            PlanStepInfo stepInfo = step.next();

          
            // za sada pretpostavka da se problem nalazi u onoj metodi / operatoru gdje se prvi put pojavljuje backtracking - ovo naravno vrijedi ako nije pronađen plan
            System.out.println("Step info: " + stepInfo);
            System.out.println("Step info plan found: " + stepInfo.planFound);
            System.out.println("Step info plan found: " + stepInfo.action);


            if (stepInfo.state != null)
                stepStates = stepInfo.state;

            System.out.println("stepStates: " + stepStates);
            if (stepStates != null) {
                System.out.println("DEBUG step states:");
                for (String oneState : stepStates) {
                    System.out.println(oneState);
                }
            }


        }

       
    }
    */

}
