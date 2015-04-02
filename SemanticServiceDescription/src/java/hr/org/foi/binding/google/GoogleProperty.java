/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.binding.google;


/**
 *
 * @author Darko Androcec
 */

public class GoogleProperty implements java.io.Serializable{
    protected String googleProperyName;
    protected Object googleProperyValue;

    public String getGoogleProperyName() {
        return googleProperyName;
    }

    public void setGoogleProperyName(String googleProperyName) {
        this.googleProperyName = googleProperyName;
    }

    public Object getGoogleProperyValue() {
        return googleProperyValue;
    }

    public void setGoogleProperyValue(Object googleProperyValue) {
        this.googleProperyValue = googleProperyValue;
    }
    
}
