/*
 * Copyright 2009, 2010 Ontotext AD
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AttributeNode extends ModelNode implements Serializable {

    public AttributeNode() { super(); }
    
    public AttributeNode(String label, String URI, boolean isDatatype) {
        super(label, URI);
        setDatatypeProperty(isDatatype);
    }
    
    public String getRange() {
    	return get("range");
    }
    public void setRange(String newRangeURI) {
    	set("range", newRangeURI);
    }

    public boolean isLeaf() {
        return true;
    }
    
    public boolean isDatatypeProperty() {
    	if (get("datatypeprop") == null) {
    		return false;
    	}
    	return Boolean.valueOf(get("datatypeprop").toString());
    }
    public void setDatatypeProperty(boolean isDatatype) {
    	set("datatypeprop", String.valueOf(isDatatype));
    }
}
