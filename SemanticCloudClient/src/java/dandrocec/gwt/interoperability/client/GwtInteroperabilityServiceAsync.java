/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dandrocec.gwt.interoperability.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author Darko Androcec
 */
public interface GwtInteroperabilityServiceAsync {
    public void executeAiplanning(String aiGoal, AsyncCallback<String> asyncCallback);
    public void prepareAiplanning(String aiGoal, AsyncCallback<String> asyncCallback);
    public void doesPlanExist(AsyncCallback<String> asyncCallback);
    public void getInteroperabilityProblems(String aiGoal, AsyncCallback<String> asyncCallback);

    public void executeFoundPlan(String plan, AsyncCallback<String> asyncCallback);

    public void removeUnrelatedCsvFiles(String directory, String chosenDataContainer, AsyncCallback<Void> asyncCallback);
}
