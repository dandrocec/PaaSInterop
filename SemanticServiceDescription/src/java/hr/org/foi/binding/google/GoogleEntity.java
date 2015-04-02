/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.binding.google;

import java.util.List;


/**
 *
 * @author Darko Androcec
 */

public class GoogleEntity implements java.io.Serializable {
    protected String googleEntityKind;
    protected GoogleKey googleEntityKey;
    protected List<GoogleProperty> googleEntityProperties;

    public String getGoogleEntityKind() {
        return googleEntityKind;
    }

    public void setGoogleEntityKind(String googleEntityKind) {
        this.googleEntityKind = googleEntityKind;
    }

    public GoogleKey getGoogleEntityKey() {
        return googleEntityKey;
    }

    public void setGoogleEntityKey(GoogleKey googleEntityKey) {
        this.googleEntityKey = googleEntityKey;
    }

    public List<GoogleProperty> getGoogleEntityProperties() {
        return googleEntityProperties;
    }

    public void setGoogleEntityProperties(List<GoogleProperty> googleEntityProperties) {
        this.googleEntityProperties = googleEntityProperties;
    }

   

    
    
}
