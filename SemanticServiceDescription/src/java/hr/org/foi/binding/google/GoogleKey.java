/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.binding.google;

/**
 *
 * @author Darko Androcec
 */

public class GoogleKey implements java.io.Serializable{
    protected long googleKeyId;
    protected String googleKeyName;
    protected String googleKeyKind;
    protected String googleKeyNamespace;

    public String getGoogleKeyKind() {
        return googleKeyKind;
    }

    public void setGoogleKeyKind(String googleKeyKind) {
        this.googleKeyKind = googleKeyKind;
    }

    
    public long getGoogleKeyId() {
        return googleKeyId;
    }

    public void setGoogleKeyId(long googleKeyId) {
        this.googleKeyId = googleKeyId;
    }

    public String getGoogleKeyName() {
        return googleKeyName;
    }

    public void setGoogleKeyName(String googleKeyName) {
        this.googleKeyName = googleKeyName;
    }

    public String getGoogleKeyNamespace() {
        return googleKeyNamespace;
    }

    public void setGoogleKeyNamespace(String googleKeyNamespace) {
        this.googleKeyNamespace = googleKeyNamespace;
    }

     
   
}
