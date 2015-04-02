/**
 * Copyright (c) 2009, Ontotext AD
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307 USA
 *
 */
package org.soa4all.dashboard.gwt.module.wsmolite.server;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.*;
import org.soa4all.dashboard.gwt.module.wsmolite.client.WsmoLiteDataService;
import org.soa4all.dashboard.gwt.module.wsmolite.client.editor.onto.model.OntologyNode;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import uk.ac.open.kmi.iserve.client.rest.IServeHttpClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class WsmoLiteDataServiceImpl extends RemoteServiceServlet implements WsmoLiteDataService {

    static Logger log = Logger.getLogger(WsmoLiteDataServiceImpl.class.getName());

    public List<String> listRepositories(String url) throws Exception {

        if (url.endsWith("/")) {
            url += "repositories/";
        } else {
            url += "/repositories/";
        }
        Document doc = getContentAsXML(url);
        NodeList repEls = doc.getElementsByTagName("repository");
        List<String> result = new LinkedList<String>();
        for (int i = 0; i < repEls.getLength(); i++) {
            result.add(((CDATASection) repEls.item(i).getFirstChild()).getTextContent());
        }
        return result;
    }

    public Map<String, String> listRepositoryFiles(String url, String repoID) throws Exception {
        StringBuffer fullURL = new StringBuffer(url);
        if (false == url.endsWith("/")) {
            fullURL.append('/');
        }
        try {
            fullURL.append("repositories/").append(URLEncoder.encode(repoID, "UTF-8")).append("/files/");
        } catch (Exception e) {
            // ignore
        }
        Document doc = getContentAsXML(fullURL.toString());
        NodeList repEls = doc.getElementsByTagName("file");
        Map<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < repEls.getLength(); i++) {
            result.put(((CDATASection) repEls.item(i).getFirstChild()).getTextContent(),
                    ((Element) repEls.item(i)).getAttribute("urlEncoded"));
        }
        return result;
    }

    public String getSAWSDLService(String restURL) throws Exception {
        Document parsed = getContentAsXML(restURL);
        Element root = parsed.getDocumentElement();
        ServiceDataProcessor.markElementsWithIDs(root);
        ServiceDataProcessor.buildLabels(root);

        ServiceDataProcessor.encodeWSDL11AnnOnOperations(root);

        NodeList schemas = root.getElementsByTagNameNS(
                XMLConstants.W3C_XML_SCHEMA_NS_URI, "schema");

        for (int i = 0; i < schemas.getLength(); i++) {
            ServiceDataProcessor.simplifyTree((Element) schemas.item(i));
        }
        return getXMLString(parsed);
    }

    public void storeSAWSDLService(String fileName, String xmlData) throws Exception {
        
        System.out.println("DEBUG filename " + fileName);
        Document annDoc = parseXML(xmlData);
        Document original = getContentAsXML(annDoc.getDocumentElement().getAttribute("src"));

        ServiceDataProcessor.mergeAnnotation(annDoc, original);
        ServiceDataProcessor.decodeWSDL11AnnOnOperations(original.getDocumentElement());

       

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
  
        DocumentBuilder builder;  
        
  
        
        try  
        {  
            builder = factory.newDocumentBuilder();  
 
            TransformerFactory tranFactory = TransformerFactory.newInstance();  
            Transformer aTransformer = tranFactory.newTransformer();  
            Source src = new DOMSource( original );  
            Result dest = new StreamResult( new File( fileName) );  
            aTransformer.transform( src, dest );  
        } catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  

        /*
        
         PutMethod putMtd = new PutMethod(restURL);
         HttpClient httpclient = new HttpClient();

         try {
         putMtd.setRequestEntity(new StringRequestEntity(getXMLString(original), "text/xml", "UTF-8"));
         int result = httpclient.executeMethod(putMtd);
         if (result != 200) {
         if (putMtd.getResponseHeader("Error") != null) {
         throw new Exception("Error : " + putMtd.getResponseHeader("Error").getValue());
         }
         throw new Exception("Storage service error code - " + result);
         }
         }
         catch(Exception exc) {
         putMtd.releaseConnection();
         throw new Exception(exc.getClass().getSimpleName() + " : " + exc.getMessage());
         }
         */
    }

    public OntologyNode buildModelFromOntology(String restURL) throws Exception {
        String rawData = fetchContent(restURL);
        if (restURL.toLowerCase().endsWith(".wsml")) {
            return GuiModelBuilder.buildFromWSML(rawData);
        }
        //    else if (restURL.toLowerCase().endsWith(".rdf") 
        //           || restURL.toLowerCase().endsWith(".xml") 
        //           || restURL.toLowerCase().endsWith(".owl")) {
        try {
            return GuiModelBuilder.buildFromRDF(rawData);
        } catch (Throwable err) {
            System.out.println("ERROR " + err.getMessage());
            err.printStackTrace();
            throw new Exception(err.getMessage());
        }
//        }
        //      throw new Exception("Unsupported data format!");
    }

    public String getRepositoryFileAsString(String url, String repoID, String fileID) throws Exception {
        StringBuffer fullURL = new StringBuffer(url);
        if (false == url.endsWith("/")) {
            fullURL.append('/');
        }
        try {
            fullURL.append("repositories/")
                    .append(URLEncoder.encode(repoID, "UTF-8"))
                    .append("/files/")
                    .append(URLEncoder.encode(fileID, "UTF-8"));
        } catch (Exception e) {
            // ignore
        }
        return fetchContent(fullURL.toString());
    }

    private String fetchContent(String url) throws Exception {
        GetMethod getMtd = new GetMethod(url);
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(getMtd);
            if (result != 200) {
                if (getMtd.getResponseHeader("Error") != null) {
                    throw new Exception("Error : " + getMtd.getResponseHeader("Error").getValue());
                }
                throw new Exception("Storage service error code - " + result);
            }
            BufferedInputStream response = new BufferedInputStream(getMtd.getResponseBodyAsStream());
            ByteArrayOutputStream resultBuffer = new ByteArrayOutputStream();

            byte[] buffer = new byte[1000];
            int i;
            do {
                i = response.read(buffer);
                if (i > 0) {
                    resultBuffer.write(buffer, 0, i);
                }
            } while (i > 0);
            getMtd.releaseConnection();

            return resultBuffer.toString("UTF-8");
        } catch (Exception exc) {
            getMtd.releaseConnection();
            throw new Exception(exc.getClass().getSimpleName() + " : " + exc.getMessage());
        }
    }

    private Document getContentAsXML(String url) throws Exception {
        String rawData = fetchContent(url);
        return parseXML(rawData);
    }

    private Document parseXML(String rawData) throws Exception {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(rawData)));
        } catch (Exception exc) {
            throw new Exception(exc.getClass().getSimpleName() + " : " + exc.getMessage());
        }
        return doc;
    }

    private String getXMLString(Document doc) throws Exception {

        StringWriter buffer = new StringWriter();
        Transformer tr = TransformerFactory.newInstance().newTransformer();
        tr.setOutputProperty(OutputKeys.METHOD, "xml");
        tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        tr.transform(new DOMSource(doc), new StreamResult(buffer));
        return buffer.getBuffer().toString();

    }

    @Override
    public String proxifyRequest(String method, String url,
            Map<String, String> headers) throws Exception {

        HttpMethod httpMtd;
        if (method.equalsIgnoreCase("post")) {
            httpMtd = new PostMethod(url);
        }
        if (method.equalsIgnoreCase("put")) {
            httpMtd = new PutMethod(url);
        }
        if (method.equalsIgnoreCase("delete")) {
            httpMtd = new DeleteMethod(url);
        } else {
            httpMtd = new GetMethod(url);
        }

        if (headers != null) {
            for (String key : headers.keySet()) {
                httpMtd.addRequestHeader(key, headers.get(key));
            }
        }

        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(httpMtd);
            if (result != 200) {
                if (httpMtd.getResponseHeader("Error") != null) {
                    throw new Exception(httpMtd.getResponseHeader("Error").getValue());
                }
                throw new Exception("Service error code - " + result);
            }
            BufferedInputStream response = new BufferedInputStream(httpMtd.getResponseBodyAsStream());
            ByteArrayOutputStream resultBuffer = new ByteArrayOutputStream();

            byte[] buffer = new byte[1000];
            int i;
            do {
                i = response.read(buffer);
                if (i > 0) {
                    resultBuffer.write(buffer, 0, i);
                }
            } while (i > 0);
            httpMtd.releaseConnection();

            return resultBuffer.toString("UTF-8");
        } catch (Exception exc) {
            httpMtd.releaseConnection();
            throw new Exception(exc.getClass().getSimpleName() + " : " + exc.getMessage());
        }
    }

    @Override
    public boolean deleteRepositoryFile(String fullRequestUrl) throws Exception {

        DeleteMethod deleteMtd = new DeleteMethod(fullRequestUrl);
        HttpClient httpclient = new HttpClient();

        try {
            int result = httpclient.executeMethod(deleteMtd);
            if (result != 200) {
                deleteMtd.releaseConnection();
                throw new Exception("Error " + result + ": " + deleteMtd.getResponseHeader("Error"));
            }
        } finally {
            deleteMtd.releaseConnection();
        }
        return true;
    }

    @Override
    public String exportSAWSDLToIServe(String iServURL, String user,
            String pass, String data) throws Exception {
        try {
            Document annDoc = parseXML(data);
            Document original = getContentAsXML(annDoc.getDocumentElement().getAttribute("src"));
            ServiceDataProcessor.mergeAnnotation(annDoc, original);
            ServiceDataProcessor.decodeWSDL11AnnOnOperations(original.getDocumentElement());

            IServeHttpClient iServeClient =
                    new IServeHttpClient(iServURL, user, pass);
            String wsdlfileData = getXMLString(original);
            String response =
                    iServeClient.addService(wsdlfileData,
                    annDoc.getDocumentElement().getAttribute("src"));
            return response;
        } catch (Throwable err) {
            err.printStackTrace();
            throw new Exception(err.getMessage());
        }
        /*        ServiceRepositoryClient client = new ServiceRepositoryClient(iServURL, user, pass);
         Response resp = client.addService(getXMLString(original), "WSDL");
         if (resp.getStatus().isSuccess()) {
         return resp.getEntity().getText();
         }
         else {
         System.out.println(resp.getAttributes());
         System.out.println(resp.getStatus());
         System.out.println(resp);
         if (resp.getEntity() != null) {
         System.out.println(resp.getEntity().getText());
         }
         else {
         System.out.println("Entity is null");
         }
         throw new Exception(resp.getStatus().toString());
         }
         }
         catch(Throwable err) {
         err.printStackTrace();
         throw new Exception(err.getMessage());
         } */
    }
}