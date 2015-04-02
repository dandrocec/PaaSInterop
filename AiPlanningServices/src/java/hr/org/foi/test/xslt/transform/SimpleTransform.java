/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.test.xslt.transform;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the  "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * $Id: SimpleTransform.java 470245 2006-11-02 06:34:33Z minchau $
 */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *  Use the TraX interface to perform a transformation in the simplest manner possible
 *  (3 statements).
 */
public class SimpleTransform
{
	public static void main(String[] args)
    throws TransformerException, TransformerConfigurationException, 
           FileNotFoundException, IOException
  {  
  // Use the static TransformerFactory.newInstance() method to instantiate 
  // a TransformerFactory. The javax.xml.transform.TransformerFactory 
  // system property setting determines the actual class to instantiate --
  // org.apache.xalan.transformer.TransformerImpl.
	TransformerFactory tFactory = TransformerFactory.newInstance();
	
	// Use the TransformerFactory to instantiate a Transformer that will work with  
	// the stylesheet you specify. This method call also processes the stylesheet
  // into a compiled Templates object.
	Transformer transformer = tFactory.newTransformer(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\userInfo_liftingSchemaMapping.xslt"));

	// Use the Transformer to apply the associated Templates object to an XML document
	// (foo.xml) and write the output to a file (foo.out).
	transformer.transform(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\serviceResultSoap.xml"), new StreamResult(new FileOutputStream("D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedOntologyOutput.xml")));
	
	System.out.println("************* D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedOntologyOutput.xml *************");
        
        Transformer transformer2 = tFactory.newTransformer(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\userInfo_loweringSchemaMapping.xslt"));
        transformer2.transform(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedOntologyOutput.xml"), new StreamResult(new FileOutputStream("D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedAccoringToLoweringSchema.xml")));
	
        System.out.println("************* D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedAccoringToLoweringSchema.xml *************");
        
        Transformer transformer3 = tFactory.newTransformer(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\userInfo_lowering_To_Email.xslt"));
        transformer3.transform(new StreamSource("D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedOntologyOutput.xml"), new StreamResult(new FileOutputStream("D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedLoweringToEmail.xml")));
	
        System.out.println("************* D:\\DOKTORAT\\generatedResultSOAPMessage\\transformedLoweringToEmail.xml *************");
  }
}

