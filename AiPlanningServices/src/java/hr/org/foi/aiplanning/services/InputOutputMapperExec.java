/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.aiplanning.services;


import hr.org.foi.appengine.api.services.GoogleEntity;
import hr.org.foi.appengine.api.services.GoogleProperty;
import hr.org.foi.salesforce.api.services.SingleEmailMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Darko Androcec
 */
public class InputOutputMapperExec {

    private final static String TRANSFORMER_FOLDER = "D:\\DOKTORAT\\generatedResultSOAPMessage\\";
    private final static String SOAP_RESULT_FILE = "serviceResultSoap.xml";

    public static Object processSchemaMapping(String schemaMapping) {

        Object result = null;

        // LIFTING SCHEMA MAPPING
        if (schemaMapping.contains("lifting")) {
            try {
                TransformerFactory tFactory = TransformerFactory.newInstance();

                Transformer transformer;

                String baseClass = schemaMapping.substring(0, schemaMapping.indexOf("_"));

                transformer = tFactory.newTransformer(new StreamSource(TRANSFORMER_FOLDER + schemaMapping));
                transformer.transform(new StreamSource(TRANSFORMER_FOLDER + SOAP_RESULT_FILE), new StreamResult(new FileOutputStream(TRANSFORMER_FOLDER + baseClass + "TransformedOutput.xml")));

                System.out.println("************* " + TRANSFORMER_FOLDER + baseClass + "TransformedOutput.xml *************");


            } catch (TransformerException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // LOWERING SCHEMA MAPPING
        else {
            try {
                TransformerFactory tFactory = TransformerFactory.newInstance();

                Transformer transformer;

                String fromBaseClass = schemaMapping.substring(0, schemaMapping.indexOf("_"));
                String toBaseClass = schemaMapping.substring(schemaMapping.indexOf("to_") + 3, schemaMapping.indexOf(".xslt"));

                transformer = tFactory.newTransformer(new StreamSource(TRANSFORMER_FOLDER + schemaMapping));
                transformer.transform(new StreamSource(TRANSFORMER_FOLDER + fromBaseClass + "TransformedOutput.xml"), new StreamResult(new FileOutputStream(TRANSFORMER_FOLDER + toBaseClass + "TransformedInput.xml")));

                System.out.println("************* " + TRANSFORMER_FOLDER + toBaseClass + "TransformedInput.xml *************");

                /*
                 * TODO:
                 *       
                 * pročitati TransformedInput.xml datoteku i kreirati objekt na osnovu toga
                 * java dynamically read XML file
                 * drugi način - raditi sa SOAP porukama?
                 */

                InputStream in = new FileInputStream(TRANSFORMER_FOLDER + toBaseClass + "TransformedInput.xml");
                Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);

                // parse XML
                Element root = doc.getDocumentElement();
                System.out.println("DEBUG root = " + root.getTagName());



                // if root contains entity, Google Entity is created and returned as result
                if (root.getTagName() != null && root.getTagName().contains("Entity")) {
                    GoogleEntity entity = new GoogleEntity();
                    entity.setGoogleEntityKind(root.getTagName());
                  
                    Node objects = doc.getDocumentElement();
                    for (Node object = objects.getFirstChild(); object != null; object = object.getNextSibling()) {
                        if (object instanceof Element) {
                            Element e = (Element) object;
                           // System.out.println("DEBUG tagName = " + e.getTagName());
                           // System.out.println("DEBUG textContent = " + e.getTextContent());
                            
                            GoogleProperty property = new GoogleProperty();
                            property.setGoogleProperyName(e.getTagName());
                            property.setGoogleProperyValue(e.getTextContent());
                            
                             entity.getGoogleEntityProperties().add(property);
                            

                        }
                    }
                    
                
                 
                   // System.out.println("DEBUG entity.getGoogleEntityProperties() size = " + entity.getGoogleEntityProperties().size());
                   
                    return entity;
                    
                }
                else if (root.getTagName() != null && root.getTagName().contains("EmailMessage")) {
                   SingleEmailMessage emailMessage = new SingleEmailMessage();
                   
                    Node objects = doc.getDocumentElement();
                    for (Node object = objects.getFirstChild(); object != null; object = object.getNextSibling()) {
                        if (object instanceof Element) {
                            Element e = (Element) object;
                            
                            String tagName = e.getTagName();
                            String textContent = e.getTextContent();
                            
                            System.out.println("DEBUG tagName = " + tagName);
                            System.out.println("DEBUG textContent = " + textContent);
                            
                            if (tagName.contains("toAddresses")){
                                emailMessage.getToAddresses().add(textContent);
                            }
                            else if (tagName.contains("subject")){
                                emailMessage.setSubject(textContent);
                            }
                            else if (tagName.contains("plainTextBody")){
                                emailMessage.setPlainTextBody(textContent);
                            }
    
                          
                        }
                    }
                    
                    return emailMessage;
                    
                }


            } catch (SAXException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TransformerException ex) {
                Logger.getLogger(InputOutputMapperExec.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return result;

    }
}
