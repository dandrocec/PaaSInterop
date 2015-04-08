
package hr.org.foi.appengine.api.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hr.org.foi.appengine.api.services package. 
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

    private final static QName _GetEntitiesOfKind_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getEntitiesOfKind");
    private final static QName _RetrieveResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "retrieveResponse");
    private final static QName _GetResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getResponse");
    private final static QName _GetDataTypesInCurrentDataResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getDataTypesInCurrentDataResponse");
    private final static QName _QueryResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "queryResponse");
    private final static QName _PutResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "putResponse");
    private final static QName _GetDataTypesInCurrentData_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getDataTypesInCurrentData");
    private final static QName _Query_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "query");
    private final static QName _TestReturnOfListResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "testReturnOfListResponse");
    private final static QName _CreateDataModelOntologyResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "createDataModelOntologyResponse");
    private final static QName _Get_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "get");
    private final static QName _Put_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "put");
    private final static QName _TestReturnOfList_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "testReturnOfList");
    private final static QName _GetEntitiesOfKindResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getEntitiesOfKindResponse");
    private final static QName _CreateDataModelOntology_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "createDataModelOntology");
    private final static QName _CreateEntitiesFromDataOntologyResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "createEntitiesFromDataOntologyResponse");
    private final static QName _GetCapabilityStatusResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getCapabilityStatusResponse");
    private final static QName _Delete_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "delete");
    private final static QName _DeleteKeyNameResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "deleteKeyNameResponse");
    private final static QName _Retrieve_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "retrieve");
    private final static QName _CreateEntitiesFromDataOntology_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "createEntitiesFromDataOntology");
    private final static QName _DeleteKeyName_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "deleteKeyName");
    private final static QName _GetCapabilityStatus_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "getCapabilityStatus");
    private final static QName _DeleteResponse_QNAME = new QName("http://services.api.appengine.foi.org.hr/", "deleteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hr.org.foi.appengine.api.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEntitiesOfKindResponse }
     * 
     */
    public GetEntitiesOfKindResponse createGetEntitiesOfKindResponse() {
        return new GetEntitiesOfKindResponse();
    }

    /**
     * Create an instance of {@link TestReturnOfList }
     * 
     */
    public TestReturnOfList createTestReturnOfList() {
        return new TestReturnOfList();
    }

    /**
     * Create an instance of {@link Put }
     * 
     */
    public Put createPut() {
        return new Put();
    }

    /**
     * Create an instance of {@link CreateDataModelOntologyResponse }
     * 
     */
    public CreateDataModelOntologyResponse createCreateDataModelOntologyResponse() {
        return new CreateDataModelOntologyResponse();
    }

    /**
     * Create an instance of {@link Get }
     * 
     */
    public Get createGet() {
        return new Get();
    }

    /**
     * Create an instance of {@link TestReturnOfListResponse }
     * 
     */
    public TestReturnOfListResponse createTestReturnOfListResponse() {
        return new TestReturnOfListResponse();
    }

    /**
     * Create an instance of {@link Query }
     * 
     */
    public Query createQuery() {
        return new Query();
    }

    /**
     * Create an instance of {@link PutResponse }
     * 
     */
    public PutResponse createPutResponse() {
        return new PutResponse();
    }

    /**
     * Create an instance of {@link GetDataTypesInCurrentData }
     * 
     */
    public GetDataTypesInCurrentData createGetDataTypesInCurrentData() {
        return new GetDataTypesInCurrentData();
    }

    /**
     * Create an instance of {@link QueryResponse }
     * 
     */
    public QueryResponse createQueryResponse() {
        return new QueryResponse();
    }

    /**
     * Create an instance of {@link GetDataTypesInCurrentDataResponse }
     * 
     */
    public GetDataTypesInCurrentDataResponse createGetDataTypesInCurrentDataResponse() {
        return new GetDataTypesInCurrentDataResponse();
    }

    /**
     * Create an instance of {@link RetrieveResponse }
     * 
     */
    public RetrieveResponse createRetrieveResponse() {
        return new RetrieveResponse();
    }

    /**
     * Create an instance of {@link GetResponse }
     * 
     */
    public GetResponse createGetResponse() {
        return new GetResponse();
    }

    /**
     * Create an instance of {@link GetEntitiesOfKind }
     * 
     */
    public GetEntitiesOfKind createGetEntitiesOfKind() {
        return new GetEntitiesOfKind();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link GetCapabilityStatus }
     * 
     */
    public GetCapabilityStatus createGetCapabilityStatus() {
        return new GetCapabilityStatus();
    }

    /**
     * Create an instance of {@link DeleteKeyName }
     * 
     */
    public DeleteKeyName createDeleteKeyName() {
        return new DeleteKeyName();
    }

    /**
     * Create an instance of {@link CreateEntitiesFromDataOntology }
     * 
     */
    public CreateEntitiesFromDataOntology createCreateEntitiesFromDataOntology() {
        return new CreateEntitiesFromDataOntology();
    }

    /**
     * Create an instance of {@link Retrieve }
     * 
     */
    public Retrieve createRetrieve() {
        return new Retrieve();
    }

    /**
     * Create an instance of {@link DeleteKeyNameResponse }
     * 
     */
    public DeleteKeyNameResponse createDeleteKeyNameResponse() {
        return new DeleteKeyNameResponse();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link GetCapabilityStatusResponse }
     * 
     */
    public GetCapabilityStatusResponse createGetCapabilityStatusResponse() {
        return new GetCapabilityStatusResponse();
    }

    /**
     * Create an instance of {@link CreateEntitiesFromDataOntologyResponse }
     * 
     */
    public CreateEntitiesFromDataOntologyResponse createCreateEntitiesFromDataOntologyResponse() {
        return new CreateEntitiesFromDataOntologyResponse();
    }

    /**
     * Create an instance of {@link CreateDataModelOntology }
     * 
     */
    public CreateDataModelOntology createCreateDataModelOntology() {
        return new CreateDataModelOntology();
    }

    /**
     * Create an instance of {@link GoogleKey }
     * 
     */
    public GoogleKey createGoogleKey() {
        return new GoogleKey();
    }

    /**
     * Create an instance of {@link Capability }
     * 
     */
    public Capability createCapability() {
        return new Capability();
    }

    /**
     * Create an instance of {@link GoogleProperty }
     * 
     */
    public GoogleProperty createGoogleProperty() {
        return new GoogleProperty();
    }

    /**
     * Create an instance of {@link GoogleEntity }
     * 
     */
    public GoogleEntity createGoogleEntity() {
        return new GoogleEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntitiesOfKind }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getEntitiesOfKind")
    public JAXBElement<GetEntitiesOfKind> createGetEntitiesOfKind(GetEntitiesOfKind value) {
        return new JAXBElement<GetEntitiesOfKind>(_GetEntitiesOfKind_QNAME, GetEntitiesOfKind.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "retrieveResponse")
    public JAXBElement<RetrieveResponse> createRetrieveResponse(RetrieveResponse value) {
        return new JAXBElement<RetrieveResponse>(_RetrieveResponse_QNAME, RetrieveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getResponse")
    public JAXBElement<GetResponse> createGetResponse(GetResponse value) {
        return new JAXBElement<GetResponse>(_GetResponse_QNAME, GetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTypesInCurrentDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getDataTypesInCurrentDataResponse")
    public JAXBElement<GetDataTypesInCurrentDataResponse> createGetDataTypesInCurrentDataResponse(GetDataTypesInCurrentDataResponse value) {
        return new JAXBElement<GetDataTypesInCurrentDataResponse>(_GetDataTypesInCurrentDataResponse_QNAME, GetDataTypesInCurrentDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "queryResponse")
    public JAXBElement<QueryResponse> createQueryResponse(QueryResponse value) {
        return new JAXBElement<QueryResponse>(_QueryResponse_QNAME, QueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "putResponse")
    public JAXBElement<PutResponse> createPutResponse(PutResponse value) {
        return new JAXBElement<PutResponse>(_PutResponse_QNAME, PutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTypesInCurrentData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getDataTypesInCurrentData")
    public JAXBElement<GetDataTypesInCurrentData> createGetDataTypesInCurrentData(GetDataTypesInCurrentData value) {
        return new JAXBElement<GetDataTypesInCurrentData>(_GetDataTypesInCurrentData_QNAME, GetDataTypesInCurrentData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Query }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "query")
    public JAXBElement<Query> createQuery(Query value) {
        return new JAXBElement<Query>(_Query_QNAME, Query.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestReturnOfListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "testReturnOfListResponse")
    public JAXBElement<TestReturnOfListResponse> createTestReturnOfListResponse(TestReturnOfListResponse value) {
        return new JAXBElement<TestReturnOfListResponse>(_TestReturnOfListResponse_QNAME, TestReturnOfListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDataModelOntologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "createDataModelOntologyResponse")
    public JAXBElement<CreateDataModelOntologyResponse> createCreateDataModelOntologyResponse(CreateDataModelOntologyResponse value) {
        return new JAXBElement<CreateDataModelOntologyResponse>(_CreateDataModelOntologyResponse_QNAME, CreateDataModelOntologyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Get }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "get")
    public JAXBElement<Get> createGet(Get value) {
        return new JAXBElement<Get>(_Get_QNAME, Get.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Put }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "put")
    public JAXBElement<Put> createPut(Put value) {
        return new JAXBElement<Put>(_Put_QNAME, Put.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TestReturnOfList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "testReturnOfList")
    public JAXBElement<TestReturnOfList> createTestReturnOfList(TestReturnOfList value) {
        return new JAXBElement<TestReturnOfList>(_TestReturnOfList_QNAME, TestReturnOfList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntitiesOfKindResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getEntitiesOfKindResponse")
    public JAXBElement<GetEntitiesOfKindResponse> createGetEntitiesOfKindResponse(GetEntitiesOfKindResponse value) {
        return new JAXBElement<GetEntitiesOfKindResponse>(_GetEntitiesOfKindResponse_QNAME, GetEntitiesOfKindResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDataModelOntology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "createDataModelOntology")
    public JAXBElement<CreateDataModelOntology> createCreateDataModelOntology(CreateDataModelOntology value) {
        return new JAXBElement<CreateDataModelOntology>(_CreateDataModelOntology_QNAME, CreateDataModelOntology.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateEntitiesFromDataOntologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "createEntitiesFromDataOntologyResponse")
    public JAXBElement<CreateEntitiesFromDataOntologyResponse> createCreateEntitiesFromDataOntologyResponse(CreateEntitiesFromDataOntologyResponse value) {
        return new JAXBElement<CreateEntitiesFromDataOntologyResponse>(_CreateEntitiesFromDataOntologyResponse_QNAME, CreateEntitiesFromDataOntologyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapabilityStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getCapabilityStatusResponse")
    public JAXBElement<GetCapabilityStatusResponse> createGetCapabilityStatusResponse(GetCapabilityStatusResponse value) {
        return new JAXBElement<GetCapabilityStatusResponse>(_GetCapabilityStatusResponse_QNAME, GetCapabilityStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteKeyNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "deleteKeyNameResponse")
    public JAXBElement<DeleteKeyNameResponse> createDeleteKeyNameResponse(DeleteKeyNameResponse value) {
        return new JAXBElement<DeleteKeyNameResponse>(_DeleteKeyNameResponse_QNAME, DeleteKeyNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Retrieve }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "retrieve")
    public JAXBElement<Retrieve> createRetrieve(Retrieve value) {
        return new JAXBElement<Retrieve>(_Retrieve_QNAME, Retrieve.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateEntitiesFromDataOntology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "createEntitiesFromDataOntology")
    public JAXBElement<CreateEntitiesFromDataOntology> createCreateEntitiesFromDataOntology(CreateEntitiesFromDataOntology value) {
        return new JAXBElement<CreateEntitiesFromDataOntology>(_CreateEntitiesFromDataOntology_QNAME, CreateEntitiesFromDataOntology.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteKeyName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "deleteKeyName")
    public JAXBElement<DeleteKeyName> createDeleteKeyName(DeleteKeyName value) {
        return new JAXBElement<DeleteKeyName>(_DeleteKeyName_QNAME, DeleteKeyName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapabilityStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "getCapabilityStatus")
    public JAXBElement<GetCapabilityStatus> createGetCapabilityStatus(GetCapabilityStatus value) {
        return new JAXBElement<GetCapabilityStatus>(_GetCapabilityStatus_QNAME, GetCapabilityStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.appengine.foi.org.hr/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

}
