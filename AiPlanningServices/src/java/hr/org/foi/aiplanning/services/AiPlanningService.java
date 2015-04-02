/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;

import JSHOP2.InternalDomain;
import JSHOP2.JSHOP2;
import JSHOP2.JSHOP2GUI;
import JSHOP2.PlanStepInfo;
import JSHOP2.State;
import com.google.common.io.Files;
import hr.org.foi.generated.aifiles.cloud;
import hr.org.foi.generated.aifiles.problem;
import hr.org.foi.sawsdl.parser.EasySawsdlParser;
import hr.org.foi.testclasses.TestFindInteroperabilityProblems;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.Oneway;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.cxf.helpers.FileUtils;

/**
 *
 * @author Darko Androcec
 */
@WebService(serviceName = "AiPlanningService")
public class AiPlanningService {

    static String problematicAtom;

    @WebMethod(operationName = "executeServicesFromPlan")
    public String executeServicesFromPlan(@WebParam(name = "plan") String plan) {

        String result = WebServicesFromPlanExecutor.execute(plan);
        return result;

    }

    @WebMethod(operationName = "getInteroperabilityProblems")
    public String getInteroperabilityProblems(@WebParam(name = "aiGoal") String aiGoal) {
        String result = "";

        System.out.println("DEBUG AI GOAL = " + aiGoal);
        aiGoal = aiGoal.substring(aiGoal.indexOf(" ") + 1);
        String from = aiGoal;
        from = from.substring(0, from.indexOf(" "));
        from = from.toLowerCase();
        System.out.println("DEBUG from = " + from);

        String to = aiGoal.substring(aiGoal.indexOf(" ") + 1);

        if (to.indexOf(" ") != -1) {
            to = to.substring(0, to.indexOf(" "));
            to = to.toLowerCase();
            System.out.println("DEBUG to = " + to);
        }
        if (to.indexOf(")") != -1) {
            to = to.substring(0, to.indexOf(")"));
            to = to.toLowerCase();
            System.out.println("DEBUG to = " + to);
        }

        problem.getPlans();
        ArrayList<PlanStepInfo> planSteps = JSHOP2.getPlanStepList();

        //   System.out.println(planSteps);

        ArrayList<String> stepStatesOnTheEnd = null;

        for (Iterator<PlanStepInfo> step = planSteps.iterator(); step.hasNext();) {

            PlanStepInfo stepInfo = step.next();

            // za sada pretpostavka da se problem nalazi u onoj metodi / operatoru gdje se prvi put pojavljuje backtracking - ovo naravno vrijedi ako nije pronađen plan
            if (stepInfo.action.compareTo("BACKTRACKING") == 0) {
                problematicAtom = stepInfo.taskAtom.toString();
                break;
            }

            if (stepInfo.state != null) {
                stepStatesOnTheEnd = stepInfo.state;
            }


            //    stepInfo.print();
            //   System.out.println(stepInfo);
        }
        // trenutno stanje na kraju izvođenja plana - to se gleda prilikom da li je neki precondition zadovoljen
        //  System.out.println("stepStates: " + stepStatesAfterPlanExecution);
        if (stepStatesOnTheEnd != null) {


            System.out.println("BEGIN DEBUG step states:");
            for (String oneState : stepStatesOnTheEnd) {
                System.out.println(oneState);
            }
        }
        System.out.println("END DEBUG step states:");


        System.out.println("Problematic atom " + problematicAtom);
        String operatorOrMethod = problematicAtom.substring(problematicAtom.indexOf("(") + 1, problematicAtom.indexOf(" "));
        System.out.println("Operator or method " + operatorOrMethod);

        List<String> arguments = new ArrayList();
        while (problematicAtom.contains(" ")) {
            int firstIndex = problematicAtom.indexOf(" ");
            int lastIndex = problematicAtom.lastIndexOf(" ");

            if (firstIndex == lastIndex) {
                String argument = problematicAtom.substring(problematicAtom.indexOf(" ") + 1, problematicAtom.indexOf(")"));
                problematicAtom = problematicAtom.substring(problematicAtom.indexOf(" ") + 1);
                System.out.println("DEBUG argument = " + argument);
                arguments.add(argument);
            } else {
                String argument = problematicAtom.substring(problematicAtom.indexOf(" ") + 1);
                problematicAtom = problematicAtom.substring(problematicAtom.indexOf(" ") + 1);
                argument = argument.substring(0, problematicAtom.indexOf(" "));
                System.out.println("DEBUG argument = " + argument);
                arguments.add(argument);
            }
        }

        if (operatorOrMethod.contains("checkdatatypemappings")) {
            result = "DataRepresentationProblem: Source PaaS offering storage includes data types that can "
                    + "not be mapped to destination PaaS's storage -> Missing or impossible data type mapping!";
        }


        File source = new File("D:\\DOKTORAT\\JShop2CloudSourceFiles\\cloud");

        System.out.println(source.getAbsolutePath());
        try {
            List<String> fileLines = Files.readLines(source, Charset.forName("UTF-8"));
            boolean lookNext = false;
            for (Iterator<String> line = fileLines.iterator(); line.hasNext();) {

                String currentLine = line.next();
                String lowerCaseCurrentLine = currentLine.toLowerCase();

                //  System.out.println("DEBUG lowerCaseCurrentLine " + lowerCaseCurrentLine);

                if (lookNext) {
                    String preconditions = currentLine;

                    System.out.println("Preconditions =" + preconditions);

                    // ovdje je stavljeno samo ako se u preconditions nađe hasApiOperation da je tada problem u tome 
                    // to ne mora biti - provjeriti da li je preconditions u trenutnom stanju!

                    // ovo radi kad je precondition samo jedan - radit će za (hasApiOperation x y ) kad je samo to precondition

                    // izbaciti početnu zagradu it preconditions
                    preconditions = preconditions.substring(preconditions.indexOf("(") + 1);


                    while (preconditions.contains("(")) {

                        String onePrecondition = preconditions.substring(preconditions.indexOf("(") + 1, preconditions.indexOf(")"));
                        if (preconditions.indexOf(")") + 1 != preconditions.length() - 1) {
                            preconditions = preconditions.substring(preconditions.indexOf(")") + 1);
                        } else {
                            preconditions = preconditions.substring(preconditions.indexOf(")"));
                        }



                        if (onePrecondition.contains("?from")) {
                            onePrecondition = onePrecondition.replace("?from", from);
                        }

                        if (onePrecondition.contains("?to")) {
                            onePrecondition = onePrecondition.replace("?to", to);
                        }

                        // maknuti varijable u preconditions
                        if (onePrecondition.contains("?type1")) {
                            onePrecondition = onePrecondition.replace("?type1", "");
                        }

                        if (onePrecondition.contains("?type2")) {
                            onePrecondition = onePrecondition.replace("?type2", "");
                        }

                        if (onePrecondition.contains("?type3")) {
                            onePrecondition = onePrecondition.replace("?type3", "");
                        }

                        if (onePrecondition.contains("?lifting")) {
                            onePrecondition = onePrecondition.replace("?lifting", "");
                        }

                        if (onePrecondition.contains("?lowering1")) {
                            onePrecondition = onePrecondition.replace("?lowering1", "");
                        }

                        if (onePrecondition.contains("?lowering1")) {
                            onePrecondition = onePrecondition.replace("?lowering2", "");
                        }

                        System.out.println("DEBUG onePrecondition = " + onePrecondition);
                        System.out.println("DEBUG preconditions = " + preconditions);

                        // provjeriti da li trenutni state sadrži preconditionsWithoutParenthesis
                        boolean isPreconditionInAStep = false;
                        if (stepStatesOnTheEnd != null) {

                            for (String oneState : stepStatesOnTheEnd) {
                                // ako state sadrži precondition nije u tome problem
                                System.out.println("DEBUG oneState " + oneState);
                                if (oneState.toLowerCase().contains(onePrecondition.toLowerCase())) {
                                    System.out.println("Precondition is in the step " + oneState);
                                    isPreconditionInAStep = true;
                                }
                            }
                        }



                        if (!isPreconditionInAStep && onePrecondition.contains("hasApiOperation")) {
                            result = result + "MissingApiOperationProblem => Operation " + operatorOrMethod + " is missing in " + arguments.get(0)
                                    + "! Check service annotations (SAWSDL file) or whether this operation is supported by PaaS vendor!\n";
                            System.out.println("DEBUG result " + result);
                        }

                        if (!isPreconditionInAStep && onePrecondition.contains("TypeHasLoweringSchema")) {
                            result = result + "Missing lowering schema => " + onePrecondition
                                    + "! Check service annotations (SAWSDL file) and add adequate lowering schema!\n";
                            System.out.println("DEBUG result " + result);
                        }

                        if (!isPreconditionInAStep && onePrecondition.contains("TypeHasLiftingSchema")) {
                            result = result + "Missing lifting schema => " + onePrecondition
                                    + "! Check service annotations (SAWSDL file) and add adequate lifting schema!\n";
                            System.out.println("DEBUG result " + result);
                        }

                        if (!isPreconditionInAStep && onePrecondition.contains("operationHasInput")) {
                            result = result + "Missing annotation on operation input => " + onePrecondition
                                    + "! Check service annotations (SAWSDL file)!\n";
                            System.out.println("DEBUG result " + result);
                        }

                        if (!isPreconditionInAStep && onePrecondition.contains("operationHasOutput")) {
                            result = result + "Missing annotation on operation output => " + onePrecondition
                                    + "! Check service annotations (SAWSDL file)!\n";
                            System.out.println("DEBUG result " + result);
                        }


                        lookNext = false;
                    }

                    //  System.out.println("Current line " + currentLine);
                }

                if (lowerCaseCurrentLine.contains("(:operator (" + operatorOrMethod)
                        || lowerCaseCurrentLine.contains("(:method (" + operatorOrMethod)
                        || lowerCaseCurrentLine.contains("(:operator (!" + operatorOrMethod)) {
                    System.out.println("Tu sam");
                    lookNext = true;
                }
                // pronađena je tražena metoda, u sljedećem će se retku nalaziti preconditions

            }


        } catch (IOException e) {
            System.out.println(e);
        }



        return result;
    }

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "createProblemFile")
    public String createProblemFile(@WebParam(name = "aiGoal") String aiGoal) {
        return EasySawsdlParser.createProblemFile(aiGoal);
    }

    @WebMethod(operationName = "prepareAiPlan")
    public String prepareAiPlan() {
        String result = " ";
        String[] parameters1 = {"D:\\DOKTORAT\\JShop2CloudSourceFiles\\cloud"};

        try {
            InternalDomain.main(parameters1);
        } catch (Exception ex) {
            Logger.getLogger(TestFindInteroperabilityProblems.class.getName()).log(Level.SEVERE, null, ex);
            result = "error";
        }

        String[] parameters2 = {"-ra", "D:\\DOKTORAT\\JShop2CloudSourceFiles\\problem"};

        try {
            InternalDomain.main(parameters2);
        } catch (Exception ex) {
            Logger.getLogger(TestFindInteroperabilityProblems.class.getName()).log(Level.SEVERE, null, ex);
            result = "error";
        }

        // Automatic redeployment of AiPlanningService project
        try {
            Runtime.getRuntime().exec("cmd /c start D:\\DOKTORAT\\NetbeansProjects\\AiPlanningServices\\myRedeploy.bat");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AiPlanningService.class.getName()).log(Level.SEVERE, null, ex);
                result = "error";
            }

        } catch (IOException ex) {
            //Validate the case the file can't be accesed (not enought permissions)
            result = "error";

        }

        result = "ok";

        return result;
    }

    @WebMethod(operationName = "findAiPlan")
    public String findAiPlan() {

        String result = "";



        if (problem.getPlans() == null || problem.getPlans().isEmpty()) {
            result = "A plan was not found!";
        } else {
            result = problem.getPlans().toString();
        }



        State state = JSHOP2.getState();
        ArrayList<String> states = state.getState();
        System.out.println("DEBUG states:");
        for (String oneState : states) {
            System.out.println(oneState);
        }


        return result;
    }

    @WebMethod(operationName = "doesPlanExist")
    public boolean doesPlanExist() {

        boolean result;

        if (problem.getPlans() == null || problem.getPlans().isEmpty()) {
            result = false;
        } else {
            result = true;
        }


        return result;

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "removeUnrelatedCsvFiles")
    @Oneway
    public void removeUnrelatedCsvFiles(@WebParam(name = "directory") String directory, @WebParam(name = "chosenDataContainer") String chosenDataContainer) {
        System.out.println("DEBUG TODO Implement remove unrelated CSV files");
        System.out.println("DEBUG directory " + directory);
        System.out.println("DEBUG chosen containter " + chosenDataContainer);

        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {

            if (listOfFiles[i].isFile()) {

                String fileName = listOfFiles[i].getName();

                System.out.println("DEBUG fileName " + fileName);
                System.out.println("DEBUG chosenDataContainer " + chosenDataContainer);

                if (fileName.toLowerCase().contains(chosenDataContainer.toLowerCase()) && !fileName.contains("FILTERED_D_C")) {
                    File f = new File(directory + "\\" + fileName);
                    System.out.println("DEBUG file name " + listOfFiles[i].getName());
                    // dodavanje prefiksa koji označava da je samo određeni data container za migraciju


                    File newFile = new File(directory + "\\" +  "FILTERED_D_C" + fileName);


                    try {
                        Files.move(f, newFile);
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        }
    }
}
