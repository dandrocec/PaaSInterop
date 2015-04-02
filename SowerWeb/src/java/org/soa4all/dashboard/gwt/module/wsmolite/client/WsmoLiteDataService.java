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

import com.google.gwt.user.client.rpc.RemoteService;


public interface WsmoLiteDataService extends RemoteService {
    
    public List<String> listRepositories(String url) throws Exception;
    /**
     * 
     * @param url
     * @param repoID
     * @return file-to-REST_calls
     * @throws Exception
     */
    public Map<String, String> listRepositoryFiles(String url, String repoID) throws Exception;
    
    public String getRepositoryFileAsString(String url, String repoID, String fileID) throws Exception;
    public boolean deleteRepositoryFile(String fullRequestUrl) throws Exception;
    
    public String getSAWSDLService(String restURL) throws Exception;
    
    public void storeSAWSDLService(String restURL, String data) throws Exception;
    
    public String exportSAWSDLToIServe(String iServURL, String user, String pass, String data) throws Exception;
    
    public OntologyNode buildModelFromOntology(String restURL) throws Exception;
    
    public String proxifyRequest(String method, String url, Map<String, String> headers) throws Exception;
}