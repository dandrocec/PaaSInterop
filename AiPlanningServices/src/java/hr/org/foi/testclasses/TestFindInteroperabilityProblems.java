/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.testclasses;

import JSHOP2.InternalDomain;
import JSHOP2.JSHOP2;
import JSHOP2.PlanStepInfo;
import com.google.common.io.Files;
import hr.org.foi.generated.aifiles.cloud;
import hr.org.foi.generated.aifiles.problem;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Darko Androcec
 */
public class TestFindInteroperabilityProblems {

    static String problematicAtom;

    public static void main(String[] args) {

        problem.getPlans();
        ArrayList<PlanStepInfo> planSteps = JSHOP2.getPlanStepList();

        //   System.out.println(planSteps);

        for (Iterator<PlanStepInfo> step = planSteps.iterator(); step.hasNext();) {

            PlanStepInfo stepInfo = step.next();

            // za sada pretpostavka da se problem nalazi u onoj metodi / operatoru gdje se prvi put pojavljuje backtracking - ovo naravno vrijedi ako nije pronađen plan
            if (stepInfo.action.compareTo("BACKTRACKING") == 0) {
                problematicAtom = stepInfo.taskAtom.toString();
                break;
            }


            //    stepInfo.print();
            //   System.out.println(stepInfo);
        }
        System.out.println("Problematic atom " + problematicAtom);
        String operatorOrMethod = problematicAtom.substring(problematicAtom.indexOf("(")+1, problematicAtom.indexOf(" "));
        System.out.println("Operator or method " + operatorOrMethod);
        
        List<String> arguments = new ArrayList();
        while (problematicAtom.contains(" ")){
            int firstIndex = problematicAtom.indexOf(" ");
            int lastIndex = problematicAtom.lastIndexOf(" ");
            
            if (firstIndex == lastIndex){
                String argument = problematicAtom.substring(problematicAtom.indexOf(" ")+1, problematicAtom.indexOf(")"));
                problematicAtom = problematicAtom.substring(problematicAtom.indexOf(" ")+1);
                System.out.println("DEBUG argument = " + argument);
                arguments.add(argument);
            }
            else{
                 String argument = problematicAtom.substring(problematicAtom.indexOf(" ")+1);
                 problematicAtom = problematicAtom.substring(problematicAtom.indexOf(" ")+1);
                 argument = argument.substring(0, problematicAtom.indexOf(" "));
                 System.out.println("DEBUG argument = " + argument);
                 arguments.add(argument);
            }
        }


        File source = new File("D:\\DOKTORAT\\JShop2CloudSourceFiles\\cloud");

        System.out.println(source.getAbsolutePath());
        try {
            List<String> fileLines = Files.readLines(source, Charset.forName("UTF-8"));
            boolean lookNext = false;
            for (Iterator<String> line = fileLines.iterator(); line.hasNext();) {

                String currentLine = line.next();
                String lowerCaseCurrentLine = currentLine.toLowerCase();
                
                if (lookNext){
                   String preconditions = currentLine;
                   
                   System.out.println("Preconditions =" + preconditions);
                   
                   if (preconditions.contains("hasApiOperation")){
                       System.out.println("MissingApiOperationProblem => Operation " + operatorOrMethod + " is missing in " + arguments.get(0));
                   }
                   
                   lookNext = false; 
                }
                // pronađena je tražena metoda, u sljedećem će se retku nalaziti preconditions
                if (lowerCaseCurrentLine.contains("(:operator (" + operatorOrMethod) 
                         || lowerCaseCurrentLine.contains("(:method (" + operatorOrMethod)){
               //     System.out.println("Tu sam");
                    lookNext = true;
                }
             //   System.out.println("Current line " + currentLine);
            }


        } catch (IOException e) {
            System.out.println(e);
        }





    }
}
