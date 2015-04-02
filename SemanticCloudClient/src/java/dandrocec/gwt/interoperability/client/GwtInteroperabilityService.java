/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dandrocec.gwt.interoperability.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author Darko Androcec
 */
@RemoteServiceRelativePath("gwtinteroperabilityservice")
public interface GwtInteroperabilityService extends RemoteService {

    public String prepareAiplanning(String aiGoal);
    public String executeAiplanning(String aiGoal);
    public String doesPlanExist();
    public String getInteroperabilityProblems(String aiGoal);
    public String executeFoundPlan(String plan);
    public void removeUnrelatedCsvFiles(String directory, String chosenDataContainer);
}
