
package hr.org.foi.azure.api.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hr.org.foi.azure.api.services package. 
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

    private final static QName _CreateTableFromDataOntology_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createTableFromDataOntology");
    private final static QName _GetDataTypesInCurrentData_QNAME = new QName("http://services.api.azure.foi.org.hr/", "getDataTypesInCurrentData");
    private final static QName _CreateTableResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createTableResponse");
    private final static QName _DownloadBlobsResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "downloadBlobsResponse");
    private final static QName _CreateAzureDataModelOntology_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createAzureDataModelOntology");
    private final static QName _CreateTable_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createTable");
    private final static QName _DeleteBlob_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteBlob");
    private final static QName _DeleteBlobResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteBlobResponse");
    private final static QName _GetDataTypesInCurrentDataResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "getDataTypesInCurrentDataResponse");
    private final static QName _ListBlobsResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "listBlobsResponse");
    private final static QName _SelectQueryResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "selectQueryResponse");
    private final static QName _DeleteBlobContainerResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteBlobContainerResponse");
    private final static QName _UploadBlobResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "uploadBlobResponse");
    private final static QName _InsertTodoItem_QNAME = new QName("http://services.api.azure.foi.org.hr/", "insertTodoItem");
    private final static QName _DeleteBlobContainer_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteBlobContainer");
    private final static QName _ListBlobs_QNAME = new QName("http://services.api.azure.foi.org.hr/", "listBlobs");
    private final static QName _CreateAzureDataModelOntologyResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createAzureDataModelOntologyResponse");
    private final static QName _FindKeys_QNAME = new QName("http://services.api.azure.foi.org.hr/", "findKeys");
    private final static QName _InsertEntityToTableResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "insertEntityToTableResponse");
    private final static QName _DeleteTableResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteTableResponse");
    private final static QName _DownloadBlobs_QNAME = new QName("http://services.api.azure.foi.org.hr/", "downloadBlobs");
    private final static QName _ExecuteQueryResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "executeQueryResponse");
    private final static QName _InsertTodoItemResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "insertTodoItemResponse");
    private final static QName _ExecuteQuery_QNAME = new QName("http://services.api.azure.foi.org.hr/", "executeQuery");
    private final static QName _SelectQuery_QNAME = new QName("http://services.api.azure.foi.org.hr/", "selectQuery");
    private final static QName _FindKeysResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "findKeysResponse");
    private final static QName _DeleteTable_QNAME = new QName("http://services.api.azure.foi.org.hr/", "deleteTable");
    private final static QName _CreateBlobContainerResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createBlobContainerResponse");
    private final static QName _InsertEntityToTable_QNAME = new QName("http://services.api.azure.foi.org.hr/", "insertEntityToTable");
    private final static QName _UploadBlob_QNAME = new QName("http://services.api.azure.foi.org.hr/", "uploadBlob");
    private final static QName _CreateBlobContainer_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createBlobContainer");
    private final static QName _CreateTableFromDataOntologyResponse_QNAME = new QName("http://services.api.azure.foi.org.hr/", "createTableFromDataOntologyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hr.org.foi.azure.api.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeleteBlobResponse }
     * 
     */
    public DeleteBlobResponse createDeleteBlobResponse() {
        return new DeleteBlobResponse();
    }

    /**
     * Create an instance of {@link DownloadBlobsResponse }
     * 
     */
    public DownloadBlobsResponse createDownloadBlobsResponse() {
        return new DownloadBlobsResponse();
    }

    /**
     * Create an instance of {@link DeleteBlob }
     * 
     */
    public DeleteBlob createDeleteBlob() {
        return new DeleteBlob();
    }

    /**
     * Create an instance of {@link CreateTable }
     * 
     */
    public CreateTable createCreateTable() {
        return new CreateTable();
    }

    /**
     * Create an instance of {@link CreateAzureDataModelOntology }
     * 
     */
    public CreateAzureDataModelOntology createCreateAzureDataModelOntology() {
        return new CreateAzureDataModelOntology();
    }

    /**
     * Create an instance of {@link CreateTableFromDataOntology }
     * 
     */
    public CreateTableFromDataOntology createCreateTableFromDataOntology() {
        return new CreateTableFromDataOntology();
    }

    /**
     * Create an instance of {@link CreateTableResponse }
     * 
     */
    public CreateTableResponse createCreateTableResponse() {
        return new CreateTableResponse();
    }

    /**
     * Create an instance of {@link GetDataTypesInCurrentData }
     * 
     */
    public GetDataTypesInCurrentData createGetDataTypesInCurrentData() {
        return new GetDataTypesInCurrentData();
    }

    /**
     * Create an instance of {@link ListBlobsResponse }
     * 
     */
    public ListBlobsResponse createListBlobsResponse() {
        return new ListBlobsResponse();
    }

    /**
     * Create an instance of {@link SelectQueryResponse }
     * 
     */
    public SelectQueryResponse createSelectQueryResponse() {
        return new SelectQueryResponse();
    }

    /**
     * Create an instance of {@link GetDataTypesInCurrentDataResponse }
     * 
     */
    public GetDataTypesInCurrentDataResponse createGetDataTypesInCurrentDataResponse() {
        return new GetDataTypesInCurrentDataResponse();
    }

    /**
     * Create an instance of {@link UploadBlobResponse }
     * 
     */
    public UploadBlobResponse createUploadBlobResponse() {
        return new UploadBlobResponse();
    }

    /**
     * Create an instance of {@link DeleteBlobContainerResponse }
     * 
     */
    public DeleteBlobContainerResponse createDeleteBlobContainerResponse() {
        return new DeleteBlobContainerResponse();
    }

    /**
     * Create an instance of {@link InsertTodoItem }
     * 
     */
    public InsertTodoItem createInsertTodoItem() {
        return new InsertTodoItem();
    }

    /**
     * Create an instance of {@link InsertEntityToTableResponse }
     * 
     */
    public InsertEntityToTableResponse createInsertEntityToTableResponse() {
        return new InsertEntityToTableResponse();
    }

    /**
     * Create an instance of {@link ListBlobs }
     * 
     */
    public ListBlobs createListBlobs() {
        return new ListBlobs();
    }

    /**
     * Create an instance of {@link CreateAzureDataModelOntologyResponse }
     * 
     */
    public CreateAzureDataModelOntologyResponse createCreateAzureDataModelOntologyResponse() {
        return new CreateAzureDataModelOntologyResponse();
    }

    /**
     * Create an instance of {@link FindKeys }
     * 
     */
    public FindKeys createFindKeys() {
        return new FindKeys();
    }

    /**
     * Create an instance of {@link DeleteBlobContainer }
     * 
     */
    public DeleteBlobContainer createDeleteBlobContainer() {
        return new DeleteBlobContainer();
    }

    /**
     * Create an instance of {@link DeleteTableResponse }
     * 
     */
    public DeleteTableResponse createDeleteTableResponse() {
        return new DeleteTableResponse();
    }

    /**
     * Create an instance of {@link DownloadBlobs }
     * 
     */
    public DownloadBlobs createDownloadBlobs() {
        return new DownloadBlobs();
    }

    /**
     * Create an instance of {@link DeleteTable }
     * 
     */
    public DeleteTable createDeleteTable() {
        return new DeleteTable();
    }

    /**
     * Create an instance of {@link SelectQuery }
     * 
     */
    public SelectQuery createSelectQuery() {
        return new SelectQuery();
    }

    /**
     * Create an instance of {@link FindKeysResponse }
     * 
     */
    public FindKeysResponse createFindKeysResponse() {
        return new FindKeysResponse();
    }

    /**
     * Create an instance of {@link ExecuteQuery }
     * 
     */
    public ExecuteQuery createExecuteQuery() {
        return new ExecuteQuery();
    }

    /**
     * Create an instance of {@link InsertTodoItemResponse }
     * 
     */
    public InsertTodoItemResponse createInsertTodoItemResponse() {
        return new InsertTodoItemResponse();
    }

    /**
     * Create an instance of {@link ExecuteQueryResponse }
     * 
     */
    public ExecuteQueryResponse createExecuteQueryResponse() {
        return new ExecuteQueryResponse();
    }

    /**
     * Create an instance of {@link InsertEntityToTable }
     * 
     */
    public InsertEntityToTable createInsertEntityToTable() {
        return new InsertEntityToTable();
    }

    /**
     * Create an instance of {@link CreateBlobContainerResponse }
     * 
     */
    public CreateBlobContainerResponse createCreateBlobContainerResponse() {
        return new CreateBlobContainerResponse();
    }

    /**
     * Create an instance of {@link UploadBlob }
     * 
     */
    public UploadBlob createUploadBlob() {
        return new UploadBlob();
    }

    /**
     * Create an instance of {@link CreateTableFromDataOntologyResponse }
     * 
     */
    public CreateTableFromDataOntologyResponse createCreateTableFromDataOntologyResponse() {
        return new CreateTableFromDataOntologyResponse();
    }

    /**
     * Create an instance of {@link CreateBlobContainer }
     * 
     */
    public CreateBlobContainer createCreateBlobContainer() {
        return new CreateBlobContainer();
    }

    /**
     * Create an instance of {@link TableServiceEntity }
     * 
     */
    public TableServiceEntity createTableServiceEntity() {
        return new TableServiceEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTableFromDataOntology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createTableFromDataOntology")
    public JAXBElement<CreateTableFromDataOntology> createCreateTableFromDataOntology(CreateTableFromDataOntology value) {
        return new JAXBElement<CreateTableFromDataOntology>(_CreateTableFromDataOntology_QNAME, CreateTableFromDataOntology.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTypesInCurrentData }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "getDataTypesInCurrentData")
    public JAXBElement<GetDataTypesInCurrentData> createGetDataTypesInCurrentData(GetDataTypesInCurrentData value) {
        return new JAXBElement<GetDataTypesInCurrentData>(_GetDataTypesInCurrentData_QNAME, GetDataTypesInCurrentData.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createTableResponse")
    public JAXBElement<CreateTableResponse> createCreateTableResponse(CreateTableResponse value) {
        return new JAXBElement<CreateTableResponse>(_CreateTableResponse_QNAME, CreateTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadBlobsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "downloadBlobsResponse")
    public JAXBElement<DownloadBlobsResponse> createDownloadBlobsResponse(DownloadBlobsResponse value) {
        return new JAXBElement<DownloadBlobsResponse>(_DownloadBlobsResponse_QNAME, DownloadBlobsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAzureDataModelOntology }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createAzureDataModelOntology")
    public JAXBElement<CreateAzureDataModelOntology> createCreateAzureDataModelOntology(CreateAzureDataModelOntology value) {
        return new JAXBElement<CreateAzureDataModelOntology>(_CreateAzureDataModelOntology_QNAME, CreateAzureDataModelOntology.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createTable")
    public JAXBElement<CreateTable> createCreateTable(CreateTable value) {
        return new JAXBElement<CreateTable>(_CreateTable_QNAME, CreateTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBlob }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteBlob")
    public JAXBElement<DeleteBlob> createDeleteBlob(DeleteBlob value) {
        return new JAXBElement<DeleteBlob>(_DeleteBlob_QNAME, DeleteBlob.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBlobResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteBlobResponse")
    public JAXBElement<DeleteBlobResponse> createDeleteBlobResponse(DeleteBlobResponse value) {
        return new JAXBElement<DeleteBlobResponse>(_DeleteBlobResponse_QNAME, DeleteBlobResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTypesInCurrentDataResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "getDataTypesInCurrentDataResponse")
    public JAXBElement<GetDataTypesInCurrentDataResponse> createGetDataTypesInCurrentDataResponse(GetDataTypesInCurrentDataResponse value) {
        return new JAXBElement<GetDataTypesInCurrentDataResponse>(_GetDataTypesInCurrentDataResponse_QNAME, GetDataTypesInCurrentDataResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBlobsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "listBlobsResponse")
    public JAXBElement<ListBlobsResponse> createListBlobsResponse(ListBlobsResponse value) {
        return new JAXBElement<ListBlobsResponse>(_ListBlobsResponse_QNAME, ListBlobsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "selectQueryResponse")
    public JAXBElement<SelectQueryResponse> createSelectQueryResponse(SelectQueryResponse value) {
        return new JAXBElement<SelectQueryResponse>(_SelectQueryResponse_QNAME, SelectQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBlobContainerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteBlobContainerResponse")
    public JAXBElement<DeleteBlobContainerResponse> createDeleteBlobContainerResponse(DeleteBlobContainerResponse value) {
        return new JAXBElement<DeleteBlobContainerResponse>(_DeleteBlobContainerResponse_QNAME, DeleteBlobContainerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadBlobResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "uploadBlobResponse")
    public JAXBElement<UploadBlobResponse> createUploadBlobResponse(UploadBlobResponse value) {
        return new JAXBElement<UploadBlobResponse>(_UploadBlobResponse_QNAME, UploadBlobResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertTodoItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "insertTodoItem")
    public JAXBElement<InsertTodoItem> createInsertTodoItem(InsertTodoItem value) {
        return new JAXBElement<InsertTodoItem>(_InsertTodoItem_QNAME, InsertTodoItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBlobContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteBlobContainer")
    public JAXBElement<DeleteBlobContainer> createDeleteBlobContainer(DeleteBlobContainer value) {
        return new JAXBElement<DeleteBlobContainer>(_DeleteBlobContainer_QNAME, DeleteBlobContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListBlobs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "listBlobs")
    public JAXBElement<ListBlobs> createListBlobs(ListBlobs value) {
        return new JAXBElement<ListBlobs>(_ListBlobs_QNAME, ListBlobs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAzureDataModelOntologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createAzureDataModelOntologyResponse")
    public JAXBElement<CreateAzureDataModelOntologyResponse> createCreateAzureDataModelOntologyResponse(CreateAzureDataModelOntologyResponse value) {
        return new JAXBElement<CreateAzureDataModelOntologyResponse>(_CreateAzureDataModelOntologyResponse_QNAME, CreateAzureDataModelOntologyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindKeys }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "findKeys")
    public JAXBElement<FindKeys> createFindKeys(FindKeys value) {
        return new JAXBElement<FindKeys>(_FindKeys_QNAME, FindKeys.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertEntityToTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "insertEntityToTableResponse")
    public JAXBElement<InsertEntityToTableResponse> createInsertEntityToTableResponse(InsertEntityToTableResponse value) {
        return new JAXBElement<InsertEntityToTableResponse>(_InsertEntityToTableResponse_QNAME, InsertEntityToTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteTableResponse")
    public JAXBElement<DeleteTableResponse> createDeleteTableResponse(DeleteTableResponse value) {
        return new JAXBElement<DeleteTableResponse>(_DeleteTableResponse_QNAME, DeleteTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadBlobs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "downloadBlobs")
    public JAXBElement<DownloadBlobs> createDownloadBlobs(DownloadBlobs value) {
        return new JAXBElement<DownloadBlobs>(_DownloadBlobs_QNAME, DownloadBlobs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "executeQueryResponse")
    public JAXBElement<ExecuteQueryResponse> createExecuteQueryResponse(ExecuteQueryResponse value) {
        return new JAXBElement<ExecuteQueryResponse>(_ExecuteQueryResponse_QNAME, ExecuteQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertTodoItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "insertTodoItemResponse")
    public JAXBElement<InsertTodoItemResponse> createInsertTodoItemResponse(InsertTodoItemResponse value) {
        return new JAXBElement<InsertTodoItemResponse>(_InsertTodoItemResponse_QNAME, InsertTodoItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "executeQuery")
    public JAXBElement<ExecuteQuery> createExecuteQuery(ExecuteQuery value) {
        return new JAXBElement<ExecuteQuery>(_ExecuteQuery_QNAME, ExecuteQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "selectQuery")
    public JAXBElement<SelectQuery> createSelectQuery(SelectQuery value) {
        return new JAXBElement<SelectQuery>(_SelectQuery_QNAME, SelectQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindKeysResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "findKeysResponse")
    public JAXBElement<FindKeysResponse> createFindKeysResponse(FindKeysResponse value) {
        return new JAXBElement<FindKeysResponse>(_FindKeysResponse_QNAME, FindKeysResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "deleteTable")
    public JAXBElement<DeleteTable> createDeleteTable(DeleteTable value) {
        return new JAXBElement<DeleteTable>(_DeleteTable_QNAME, DeleteTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBlobContainerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createBlobContainerResponse")
    public JAXBElement<CreateBlobContainerResponse> createCreateBlobContainerResponse(CreateBlobContainerResponse value) {
        return new JAXBElement<CreateBlobContainerResponse>(_CreateBlobContainerResponse_QNAME, CreateBlobContainerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InsertEntityToTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "insertEntityToTable")
    public JAXBElement<InsertEntityToTable> createInsertEntityToTable(InsertEntityToTable value) {
        return new JAXBElement<InsertEntityToTable>(_InsertEntityToTable_QNAME, InsertEntityToTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadBlob }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "uploadBlob")
    public JAXBElement<UploadBlob> createUploadBlob(UploadBlob value) {
        return new JAXBElement<UploadBlob>(_UploadBlob_QNAME, UploadBlob.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBlobContainer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createBlobContainer")
    public JAXBElement<CreateBlobContainer> createCreateBlobContainer(CreateBlobContainer value) {
        return new JAXBElement<CreateBlobContainer>(_CreateBlobContainer_QNAME, CreateBlobContainer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTableFromDataOntologyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.api.azure.foi.org.hr/", name = "createTableFromDataOntologyResponse")
    public JAXBElement<CreateTableFromDataOntologyResponse> createCreateTableFromDataOntologyResponse(CreateTableFromDataOntologyResponse value) {
        return new JAXBElement<CreateTableFromDataOntologyResponse>(_CreateTableFromDataOntologyResponse_QNAME, CreateTableFromDataOntologyResponse.class, null, value);
    }

}
