
package hr.org.foi.aiplanning.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hr.org.foi.aiplanning.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CreateProblemFileResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "createProblemFileResponse");
    private final static QName _CreateProblemFile_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "createProblemFile");
    private final static QName _DoesPlanExist_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "doesPlanExist");
    private final static QName _ExecuteServicesFromPlan_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "executeServicesFromPlan");
    private final static QName _FindAiPlanResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "findAiPlanResponse");
    private final static QName _GetInteroperabilityProblems_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "getInteroperabilityProblems");
    private final static QName _RemoveUnrelatedCsvFiles_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "removeUnrelatedCsvFiles");
    private final static QName _FindAiPlan_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "findAiPlan");
    private final static QName _ExecuteServicesFromPlanResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "executeServicesFromPlanResponse");
    private final static QName _GetInteroperabilityProblemsResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "getInteroperabilityProblemsResponse");
    private final static QName _PrepareAiPlan_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "prepareAiPlan");
    private final static QName _DoesPlanExistResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "doesPlanExistResponse");
    private final static QName _PrepareAiPlanResponse_QNAME = new QName("http://services.aiplanning.foi.org.hr/", "prepareAiPlanResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hr.org.foi.aiplanning.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExecuteServicesFromPlanResponse }
     * 
     */
    public ExecuteServicesFromPlanResponse createExecuteServicesFromPlanResponse() {
        return new ExecuteServicesFromPlanResponse();
    }

    /**
     * Create an instance of {@link GetInteroperabilityProblemsResponse }
     * 
     */
    public GetInteroperabilityProblemsResponse createGetInteroperabilityProblemsResponse() {
        return new GetInteroperabilityProblemsResponse();
    }

    /**
     * Create an instance of {@link PrepareAiPlan }
     * 
     */
    public PrepareAiPlan createPrepareAiPlan() {
        return new PrepareAiPlan();
    }

    /**
     * Create an instance of {@link PrepareAiPlanResponse }
     * 
     */
    public PrepareAiPlanResponse createPrepareAiPlanResponse() {
        return new PrepareAiPlanResponse();
    }

    /**
     * Create an instance of {@link DoesPlanExistResponse }
     * 
     */
    public DoesPlanExistResponse createDoesPlanExistResponse() {
        return new DoesPlanExistResponse();
    }

    /**
     * Create an instance of {@link CreateProblemFileResponse }
     * 
     */
    public CreateProblemFileResponse createCreateProblemFileResponse() {
        return new CreateProblemFileResponse();
    }

    /**
     * Create an instance of {@link DoesPlanExist }
     * 
     */
    public DoesPlanExist createDoesPlanExist() {
        return new DoesPlanExist();
    }

    /**
     * Create an instance of {@link CreateProblemFile }
     * 
     */
    public CreateProblemFile createCreateProblemFile() {
        return new CreateProblemFile();
    }

    /**
     * Create an instance of {@link GetInteroperabilityProblems }
     * 
     */
    public GetInteroperabilityProblems createGetInteroperabilityProblems() {
        return new GetInteroperabilityProblems();
    }

    /**
     * Create an instance of {@link ExecuteServicesFromPlan }
     * 
     */
    public ExecuteServicesFromPlan createExecuteServicesFromPlan() {
        return new ExecuteServicesFromPlan();
    }

    /**
     * Create an instance of {@link FindAiPlanResponse }
     * 
     */
    public FindAiPlanResponse createFindAiPlanResponse() {
        return new FindAiPlanResponse();
    }

    /**
     * Create an instance of {@link FindAiPlan }
     * 
     */
    public FindAiPlan createFindAiPlan() {
        return new FindAiPlan();
    }

    /**
     * Create an instance of {@link RemoveUnrelatedCsvFiles }
     * 
     */
    public RemoveUnrelatedCsvFiles createRemoveUnrelatedCsvFiles() {
        return new RemoveUnrelatedCsvFiles();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProblemFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "createProblemFileResponse")
    public JAXBElement<CreateProblemFileResponse> createCreateProblemFileResponse(CreateProblemFileResponse value) {
        return new JAXBElement<CreateProblemFileResponse>(_CreateProblemFileResponse_QNAME, CreateProblemFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProblemFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "createProblemFile")
    public JAXBElement<CreateProblemFile> createCreateProblemFile(CreateProblemFile value) {
        return new JAXBElement<CreateProblemFile>(_CreateProblemFile_QNAME, CreateProblemFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesPlanExist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "doesPlanExist")
    public JAXBElement<DoesPlanExist> createDoesPlanExist(DoesPlanExist value) {
        return new JAXBElement<DoesPlanExist>(_DoesPlanExist_QNAME, DoesPlanExist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteServicesFromPlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "executeServicesFromPlan")
    public JAXBElement<ExecuteServicesFromPlan> createExecuteServicesFromPlan(ExecuteServicesFromPlan value) {
        return new JAXBElement<ExecuteServicesFromPlan>(_ExecuteServicesFromPlan_QNAME, ExecuteServicesFromPlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAiPlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "findAiPlanResponse")
    public JAXBElement<FindAiPlanResponse> createFindAiPlanResponse(FindAiPlanResponse value) {
        return new JAXBElement<FindAiPlanResponse>(_FindAiPlanResponse_QNAME, FindAiPlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInteroperabilityProblems }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "getInteroperabilityProblems")
    public JAXBElement<GetInteroperabilityProblems> createGetInteroperabilityProblems(GetInteroperabilityProblems value) {
        return new JAXBElement<GetInteroperabilityProblems>(_GetInteroperabilityProblems_QNAME, GetInteroperabilityProblems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUnrelatedCsvFiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "removeUnrelatedCsvFiles")
    public JAXBElement<RemoveUnrelatedCsvFiles> createRemoveUnrelatedCsvFiles(RemoveUnrelatedCsvFiles value) {
        return new JAXBElement<RemoveUnrelatedCsvFiles>(_RemoveUnrelatedCsvFiles_QNAME, RemoveUnrelatedCsvFiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAiPlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "findAiPlan")
    public JAXBElement<FindAiPlan> createFindAiPlan(FindAiPlan value) {
        return new JAXBElement<FindAiPlan>(_FindAiPlan_QNAME, FindAiPlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteServicesFromPlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "executeServicesFromPlanResponse")
    public JAXBElement<ExecuteServicesFromPlanResponse> createExecuteServicesFromPlanResponse(ExecuteServicesFromPlanResponse value) {
        return new JAXBElement<ExecuteServicesFromPlanResponse>(_ExecuteServicesFromPlanResponse_QNAME, ExecuteServicesFromPlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInteroperabilityProblemsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "getInteroperabilityProblemsResponse")
    public JAXBElement<GetInteroperabilityProblemsResponse> createGetInteroperabilityProblemsResponse(GetInteroperabilityProblemsResponse value) {
        return new JAXBElement<GetInteroperabilityProblemsResponse>(_GetInteroperabilityProblemsResponse_QNAME, GetInteroperabilityProblemsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrepareAiPlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "prepareAiPlan")
    public JAXBElement<PrepareAiPlan> createPrepareAiPlan(PrepareAiPlan value) {
        return new JAXBElement<PrepareAiPlan>(_PrepareAiPlan_QNAME, PrepareAiPlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoesPlanExistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "doesPlanExistResponse")
    public JAXBElement<DoesPlanExistResponse> createDoesPlanExistResponse(DoesPlanExistResponse value) {
        return new JAXBElement<DoesPlanExistResponse>(_DoesPlanExistResponse_QNAME, DoesPlanExistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrepareAiPlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.aiplanning.foi.org.hr/", name = "prepareAiPlanResponse")
    public JAXBElement<PrepareAiPlanResponse> createPrepareAiPlanResponse(PrepareAiPlanResponse value) {
        return new JAXBElement<PrepareAiPlanResponse>(_PrepareAiPlanResponse_QNAME, PrepareAiPlanResponse.class, null, value);
    }

}
