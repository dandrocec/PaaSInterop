/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.test.xslt.transform;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.AbstractLoggingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.LoggingMessage;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.Phase;

/**
 * A simple logging handler which outputs the bytes of the message to the
 * Logger.
 */
public class LogInterceptor extends AbstractLoggingInterceptor {

    private static final Logger LOG = LogUtils.getLogger(LogInterceptor.class);

    public LogInterceptor() {
        super(Phase.RECEIVE);
    }

    public void handleMessage(Message message) throws Fault {

        if (writer != null || getLogger().isLoggable(Level.INFO)) {
            logging(message);
        }
    }

    protected void logging(Message message) throws Fault {

        if (message.containsKey(LoggingMessage.ID_KEY)) {
            return;
        }

        String id = (String) message.getExchange().get(LoggingMessage.ID_KEY);

        if (id == null) {

            id = LoggingMessage.nextId();

            message.getExchange().put(LoggingMessage.ID_KEY, id);

        }

        message.put(LoggingMessage.ID_KEY, id);
        final LoggingMessage buffer = new LoggingMessage("Inbound Message\n----------------------------", id);

        Integer responseCode = (Integer) message.get(Message.RESPONSE_CODE);

        if (responseCode != null) {

            buffer.getResponseCode().append(responseCode);

        }

        String encoding = (String) message.get(Message.ENCODING);

        if (encoding != null) {

            buffer.getEncoding().append(encoding);

        }

        String httpMethod = (String) message.get(Message.HTTP_REQUEST_METHOD);

        if (httpMethod != null) {

            buffer.getHttpMethod().append(httpMethod);

        }

        String ct = (String) message.get(Message.CONTENT_TYPE);

        if (ct != null) {

            buffer.getContentType().append(ct);

        }

        Object headers = message.get(Message.PROTOCOL_HEADERS);

        if (headers != null) {

            buffer.getHeader().append(headers);

        }

        String uri = (String) message.get(Message.REQUEST_URL);

        if (uri != null) {

            buffer.getAddress().append(uri);

            String query = (String) message.get(Message.QUERY_STRING);

            if (query != null) {

                buffer.getAddress().append("?").append(query);

            }

        }

        InputStream is = message.getContent(InputStream.class);

        if (is != null) {

            CachedOutputStream bos = new CachedOutputStream();

            try {

                IOUtils.copy(is, bos);

                bos.flush();

                is.close();

                message.setContent(InputStream.class, bos.getInputStream());

                writePayload(buffer.getPayload(), bos, encoding, ct);

                bos.close();

            } catch (Exception e) {
                throw new Fault(e);
            }

        }

        System.out.println("============= SOAP Message ==============");
        
        String payloadPartString = buffer.toString().substring(buffer.toString().indexOf("Payload:") + 8, buffer.toString().lastIndexOf("Envelope") + 9);

        if (payloadPartString.indexOf("<return") != -1) {


           
            if (payloadPartString != null && payloadPartString.indexOf("<return") != -1 && payloadPartString.indexOf("</return") != -1) {
                payloadPartString = payloadPartString.substring(payloadPartString.indexOf("<return"));
            }
            payloadPartString = payloadPartString.substring(0, payloadPartString.indexOf("</return>") + 9);

            System.out.println(payloadPartString);

            // SAVE payload part into file - later I will apply XSLT transformation on it if these were defined in SAWSDL
            File destination = new File("D:\\DOKTORAT\\generatedResultSOAPMessage\\serviceResultSoap.xml");

            System.out.println(destination.getAbsolutePath());
            try {
                Files.write(payloadPartString, destination, Charset.forName("UTF-8"));
            } catch (IOException e) {
                System.out.println(e);
            }
        }


    }

    @Override
    protected Logger getLogger() {

        return LOG;

    }
}