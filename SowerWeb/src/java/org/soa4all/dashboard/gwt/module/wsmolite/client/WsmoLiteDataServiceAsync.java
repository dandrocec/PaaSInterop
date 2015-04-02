/**
 Copyright (c) 2009, Ontotext AD
 
 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 */

package org.soa4all.dashboard.gwt.module.wsmolite.client;


import java.util.List;
import java.util.Map;

import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.OntologyNode;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface WsmoLiteDataServiceAsync {
    
    public void listRepositories(String url, AsyncCallback<List<String>> callBack);
    public void listRepositoryFiles(String url, String repoID, AsyncCallback<Map<String, String>> callBack);
    
    public void deleteRepositoryFile(String fullRequestUrl, AsyncCallback<Boolean> callBack) throws Exception;
    
    public void getRepositoryFileAsString(String url, String repoID, String fileID, AsyncCallback<String> callBack) throws Exception;
    public void getSAWSDLService(String restURL, AsyncCallback<String> callBack);
    
    public void exportSAWSDLToIServe(String iServURL, String user, String pass, String data, AsyncCallback<String> callBack) throws Exception;

    public void buildModelFromOntology(String restURL, AsyncCallback<OntologyNode> callBack) throws Exception;
    
    public void proxifyRequest(String method, String url, Map<String, String> headers, AsyncCallback<String> callBack);

    public void storeSAWSDLService(String restURL, String data, AsyncCallback<String> asyncCallback);

}