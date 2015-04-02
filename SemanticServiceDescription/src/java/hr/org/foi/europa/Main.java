/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.europa;

import psengine.PSUtil;
import psengine.PSEngine;
import psengine.util.LibraryLoader;
import org.ops.ui.main.swing.PSDesktop;
import bsh.Interpreter;
import java.util.Vector;
import psengine.PSObject;
import psengine.PSObjectList;
import psengine.PSSolver;
import psengine.PSStringList;
import psengine.PSToken;
import psengine.PSTokenList;
import psengine.PSVariable;
import psengine.PSVariableList;

class Main {

    protected static PSEngine psEngine_;

    public static void main(String args[]) {
        String debugMode = "g";
        PSUtil.loadLibraries(debugMode);

       LibraryLoader.loadLibrary("System_" + debugMode);
        psEngine_ = PSEngine.makeInstance();

        String currentPath = psEngine_.getConfig().getProperty("nddl.includePath");
        System.out.println("DEBUG current path " + currentPath);
        psEngine_.getConfig().setProperty("nddl.includePath", "D:\\java\\europaSource\\trunk\\examples\\Light");

        System.out.println("Include path: " + psEngine_.getConfig().getProperty("nddl.includePath"));

        psEngine_.start();
        loadCustomCode(debugMode);

        try {
            // Load the model and the problem :
            String nddlModel = "Light-initial-state.nddl";

            String errors = psEngine_.executeScript("nddl", nddlModel, true/*isFile*/);

            System.out.println("Errors " + errors);

            String plannerConfig = "PlannerConfig.xml";
            int startHorizon = 0, endHorizon = 100;

            PSSolver solver = psEngine_.createSolver(plannerConfig);
            solver.configure(startHorizon, endHorizon);
            
            System.out.println("Solver get step count " + solver.getStepCount());
            System.out.println("Solver getFlaws size " + solver.getFlaws().size());

            int maxSteps = 1000;
        for (int i = 0; !solver.isExhausted() && !solver.isTimedOut() && i<maxSteps; i = solver.getStepCount()) {
            solver.step();
            System.out.println ("Solver last executed decision " + solver.getLastExecutedDecision());
            PSStringList flaws = solver.getFlaws();
            
            for (int j=0; j< flaws.size(); j++){
                System.out.println("Flaws num. " + j + " --> " + flaws.get(j));
            }
            
            if (solver.getFlaws().size() == 0)
                break; // we're done!
        }

            
           


        } catch (Exception e) {
            e.printStackTrace();
        }

        showPlan();

        psEngine_.shutdown();

    }

  
    // call showPlan() after you've run the solver to see the resulting plan
    static void showPlan() {
        PSObjectList l = psEngine_.getObjectsByType("Object");
        for (int i = 0; i < l.size(); i++) {
            PSObject o = l.get(i);
           
            if (o.toLongString().startsWith("Light")) // ie LightBulb or LightSwitch
            {
                System.out.println(l.get(i).toString());
            }
        }
    }

    protected static void loadCustomCode(String debugMode) {
        //Load module with any custom code if it exists:
        String libName = "Light_" + debugMode;
        String fullLibName = LibraryLoader.getResolvedName(libName);
        if (fullLibName == null) {
            // Run 'make' to compile the library if you need it:
            System.out.println("INFO: Custom library " + libName + " wasn't found and won't be loaded.");
        } else {
            // WARNING: Shared library loaded twice (see ticket #164)
            System.load(fullLibName);
            psEngine_.loadModule(fullLibName);
        }
    }

    static class ShutdownHook extends Thread {

        public ShutdownHook() {
            super("ShutdownHook");
        }

        public void run() {
            psEngine_.shutdown();
        }
    }
}
