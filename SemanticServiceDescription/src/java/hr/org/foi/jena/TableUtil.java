/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.jena;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Darko Androcec
 */
public class TableUtil {
  
    public List<String> identifiersValues = new ArrayList();
    public String identifier;
    
    public List<String> listOfLinksBetweenDataObjects = new ArrayList();
    
    public String name;
    public List<String> properties = new ArrayList();
    public List<String> propertyTypes = new ArrayList();
    public List<ArrayList<String>> rows = new ArrayList<ArrayList<String>>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }

    public List<String> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<String> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public List<ArrayList<String>> getRows() {
        return rows;
    }

    public void setRows(ArrayList<ArrayList<String>> rows) {
        this.rows = rows;
    }

    
    public String toString(){
        return super.toString() + "\t" + this.name + "\t" + this.identifier + "\t"  + this.properties.toString() + "\t" + this.propertyTypes.toString();
    }

    public List<String> getIdentifiersValues() {
        return identifiersValues;
    }

    public void setIdentifiersValues(List<String> identifiersValues) {
        this.identifiersValues = identifiersValues;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getListOfLinksBetweenDataObjects() {
        return listOfLinksBetweenDataObjects;
    }

    public void setListOfLinksBetweenDataObjects(List<String> listOfLinksBetweenDataObjects) {
        this.listOfLinksBetweenDataObjects = listOfLinksBetweenDataObjects;
    }

    
}
