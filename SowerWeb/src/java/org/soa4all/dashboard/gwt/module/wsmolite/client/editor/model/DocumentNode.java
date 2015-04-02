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

package org.soa4all.dashboard.gwt.module.wsmolite.client.editor.model;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.TreeModel;


@SuppressWarnings({ "serial", "unchecked" })
public class DocumentNode extends BaseTreeModel<TreeModel> {

    private String sourceURL, 
                   fileName,
                   sawsdlPrefix;
    
    public DocumentNode() {
    }
    
    public String getSourceUrl() {
        return this.sourceURL; 
    }
    public String getFileName() {
        return this.fileName; 
    }
    public void setSourceUrl(String url) {
        this.sourceURL = url; 
    }
    public void setFileName(String fn) {
        this.fileName = fn; 
    }
    
    public String getSawsdlPrefix() {
        return this.sawsdlPrefix;
    }
    public void setSawsdlPrefix(String ns) {
        this.sawsdlPrefix = ns;
    }

    public String toString() {
        return "root";
    }
}
