/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.org.foi.salesforce.api.services;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import com.sforce.soap.metadata.*;

/**
 *
 * @author Darko Androcec
 */
public class FileBasedDeployAndRetrieve {

    private MetadataConnection metadataConnection;
    private static final String ZIP_FILE = "components.zip";
// manifest file that controls which components get retrieved
    private static final String MANIFEST_FILE = "package.xml";
    private static final double API_VERSION = 26.0;
// one second in milliseconds
    private static final long ONE_SECOND = 1000;
// maximum number of attempts to deploy the zip file
    private static final int MAX_NUM_POLL_REQUESTS = 50;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        FileBasedDeployAndRetrieve sample = new FileBasedDeployAndRetrieve();
        sample.run();
    }

    public FileBasedDeployAndRetrieve() {
    }

    private void run() throws Exception {
        this.metadataConnection = MetadataLoginUtil.login();
// Show the options to retrieve or deploy until user exits
        String choice = getUsersChoice();
        while (choice != null && !choice.equals("99")) {
            if (choice.equals("1")) {
                retrieveZip();
            } else if (choice.equals("2")) {
                deployZip();
            } else {
                break;
            }
// show the options again
            choice = getUsersChoice();
        }
    }
    /*
     * Utility method to present options to retrieve or deploy.
     */

    private String getUsersChoice() throws IOException {
        System.out.println(" 1: Retrieve");
        System.out.println(" 2: Deploy");
        System.out.println("99: Exit");
        System.out.println();
        System.out.print("Enter 1 to retrieve, 2 to deploy, or 99 to exit: ");
// wait for the user input.
        String choice = reader.readLine();
        return choice != null ? choice.trim() : "";
    }

    private void deployZip() throws Exception {
        byte zipBytes[] = readZipFile();
        DeployOptions deployOptions = new DeployOptions();
        deployOptions.setPerformRetrieve(false);
        deployOptions.setRollbackOnError(true);
        AsyncResult asyncResult = metadataConnection.deploy(zipBytes, deployOptions);
        asyncResult = waitForCompletion(asyncResult);
        DeployResult result = metadataConnection.checkDeployStatus(asyncResult.getId());
        if (!result.isSuccess()) {
            printErrors(result);
            throw new Exception("The files were not successfully deployed");
        }
        System.out.println("The file " + ZIP_FILE + " was successfully deployed\n");
    }
    /*
     * Read in the zip file contents into a byte array.
     */

    private byte[] readZipFile() throws Exception {
        byte[] result = null;
// We assume here that you have a deploy.zip file.
// See the retrieve sample for how to retrieve a zip file.
        File zipFile = new File(ZIP_FILE);
        if (!zipFile.exists() || !zipFile.isFile()) {
            throw new Exception("Cannot find the zip file for deploy() on path:"
                    + zipFile.getAbsolutePath());
        }
        FileInputStream fileInputStream = new FileInputStream(zipFile);
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (-1 != (bytesRead = fileInputStream.read(buffer))) {
                bos.write(buffer, 0, bytesRead);
            }
            result = bos.toByteArray();
        } finally {
            fileInputStream.close();
        }
        return result;
    }
    /*
     * Print out any errors, if any, related to the deploy.
     * @param result - DeployResult
     */

    private void printErrors(DeployResult result) {
        DeployMessage messages[] = result.getMessages();
        StringBuilder stringBuilder = new StringBuilder("Failures:\n");
        for (DeployMessage message : messages) {
            if (!message.isSuccess()) {
                String loc = "(" + message.getLineNumber() + ", " + message.getColumnNumber();
                if (loc.length() == 0 && !message.getFileName().equals(message.getFullName())) {
                    loc = "(" + message.getFullName() + ")";
                }
                stringBuilder.append(message.getFileName() + loc + ":"
                        + message.getProblem()).append('\n');
            }
        }
        RunTestsResult rtr = result.getRunTestResult();
        if (rtr.getFailures() != null) {
            for (RunTestFailure failure : rtr.getFailures()) {
                String n = (failure.getNamespace() == null ? ""
                        : (failure.getNamespace() + ".")) + failure.getName();

                stringBuilder.append("Test failure, method: " + n + "."
                        + failure.getMethodName() + " -- " + failure.getMessage()
                        + " stack " + failure.getStackTrace() + "\n\n");
            }
        }
        if (rtr.getCodeCoverageWarnings() != null) {
            for (CodeCoverageWarning ccw : rtr.getCodeCoverageWarnings()) {
                stringBuilder.append("Code coverage issue");
                if (ccw.getName() != null) {
                    String n = (ccw.getNamespace() == null ? ""
                            : (ccw.getNamespace() + ".")) + ccw.getName();
                    stringBuilder.append(", class: " + n);
                }
                stringBuilder.append(" -- " + ccw.getMessage() + "\n");
            }
        }
        System.out.println(stringBuilder.toString());
    }

    private void retrieveZip() throws Exception {
        RetrieveRequest retrieveRequest = new RetrieveRequest();
        retrieveRequest.setApiVersion(API_VERSION);
        setUnpackaged(retrieveRequest);
        AsyncResult asyncResult = metadataConnection.retrieve(retrieveRequest);
        asyncResult = waitForCompletion(asyncResult);
        RetrieveResult result =
                metadataConnection.checkRetrieveStatus(asyncResult.getId());
// Print out any warning messages
        StringBuilder stringBuilder = new StringBuilder();
        if (result.getMessages() != null) {
            for (RetrieveMessage rm : result.getMessages()) {
                stringBuilder.append(rm.getFileName() + " - " + rm.getProblem() + "\n");
            }
        }
        if (stringBuilder.length() > 0) {
            System.out.println("Retrieve warnings:\n" + stringBuilder);
        }
        System.out.println("Writing results to zip file");
        File resultsFile = new File(ZIP_FILE);
        FileOutputStream os = new FileOutputStream(resultsFile);
        try {
            os.write(result.getZipFile());
        } finally {
            os.close();
        }
    }

    private AsyncResult waitForCompletion(AsyncResult asyncResult) throws Exception {
        int poll = 0;
        long waitTimeMilliSecs = ONE_SECOND;
        while (!asyncResult.isDone()) {
            Thread.sleep(waitTimeMilliSecs);
// double the wait time for the next iteration
            waitTimeMilliSecs *= 2;
            if (poll++ > MAX_NUM_POLL_REQUESTS) {
                throw new Exception(
                        "Request timed out. If this is a large set of metadata components, "
                        + "ensure that MAX_NUM_POLL_REQUESTS is sufficient.");
            }
            asyncResult = metadataConnection.checkStatus(
                    new String[]{asyncResult.getId()})[0];

            System.out.println("Status is: " + asyncResult.getState());
        }
        if (asyncResult.getState() != AsyncRequestState.Completed) {
            throw new Exception(asyncResult.getStatusCode() + " msg: "
                    + asyncResult.getMessage());
        }
        return asyncResult;
    }

    private void setUnpackaged(RetrieveRequest request) throws Exception {
// Edit the path, if necessary, if your package.xml file is located elsewhere
        File unpackedManifest = new File(MANIFEST_FILE);
        System.out.println("Manifest file: " + unpackedManifest.getAbsolutePath());
        if (!unpackedManifest.exists() || !unpackedManifest.isFile()) {
            throw new Exception("Should provide a valid retrieve manifest "
                    + "for unpackaged content. Looking for "
                    + unpackedManifest.getAbsolutePath());
        }
// Note that we use the fully quualified class name because
// of a collision with the java.lang.Package class
        com.sforce.soap.metadata.Package p = parsePackageManifest(unpackedManifest);
        request.setUnpackaged(p);
    }

    private com.sforce.soap.metadata.Package parsePackageManifest(File file)
            throws ParserConfigurationException, IOException, SAXException {
        com.sforce.soap.metadata.Package packageManifest = null;
        List<PackageTypeMembers> listPackageTypes = new ArrayList<PackageTypeMembers>();
        DocumentBuilder db =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream inputStream = new FileInputStream(file);
        Element d = db.parse(inputStream).getDocumentElement();
        for (Node c = d.getFirstChild(); c != null; c = c.getNextSibling()) {
            if (c instanceof Element) {
                Element ce = (Element) c;
                NodeList nodeList = ce.getElementsByTagName("name");
                if (nodeList.getLength() == 0) {
                    continue;
                }
                String name = nodeList.item(0).getTextContent();
                NodeList m = ce.getElementsByTagName("members");
                List<String> members = new ArrayList<String>();
                for (int i = 0; i < m.getLength(); i++) {
                    Node mm = m.item(i);
                    members.add(mm.getTextContent());
                }
                PackageTypeMembers packageTypes = new PackageTypeMembers();
                packageTypes.setName(name);
                packageTypes.setMembers(members.toArray(new String[members.size()]));
                listPackageTypes.add(packageTypes);
            }
        }
        packageManifest = new com.sforce.soap.metadata.Package();
        PackageTypeMembers[] packageTypesArray =
                new PackageTypeMembers[listPackageTypes.size()];
        packageManifest.setTypes(listPackageTypes.toArray(packageTypesArray));
        packageManifest.setVersion(API_VERSION + "");
        return packageManifest;
    }
}
