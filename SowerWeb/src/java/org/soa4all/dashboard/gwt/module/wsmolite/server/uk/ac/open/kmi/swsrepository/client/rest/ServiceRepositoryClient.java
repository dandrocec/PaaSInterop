package org.soa4all.dashboard.gwt.module.wsmolite.server.uk.ac.open.kmi.swsrepository.client.rest;

import org.restlet.Client;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

public class ServiceRepositoryClient {

	private String servicesUrl;

	private String documentsUrl;

	private String queryUrl;

	private String userName;

	private String password;

	private Client client;

	// server URL example: "http://localhost:8081"
	public ServiceRepositoryClient(String serverUrl, String userName, String password) {
		servicesUrl = serverUrl + "/data/services/";
		documentsUrl = serverUrl + "/data/documents/";
		queryUrl = serverUrl + "/data/execute-query";
		this.userName = userName;
		this.password = password;
		client = new Client(Protocol.HTTP);
	}

	public Response listServices() {
		return client.get(servicesUrl);
	}

	// format: "HTML", "WSDL", or "RDFXML"
	// returns the uri of the new service
	public Response addService(String description, String format) {
		// Prepare the request
		Request request = new Request(Method.POST, servicesUrl);

		Form form = new Form();
		form.add("description", description);
		form.add("format", format);
		form.add("user", userName);
		Representation rep = form.getWebRepresentation();
		request.setEntity(rep);

		// Add the client authentication to the call
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme, userName, password);
		request.setChallengeResponse(authentication);

		// Ask to the HTTP client connector to handle the call
		return client.handle(request);
	}

	public Response getService(String serviceUriString) {
		return client.get(serviceUriString);
	}

	public Response listDocuments() {
		return client.get(documentsUrl);
	}

	public Response addDocument(String name, String content) {
		// Prepare the request
		Request request = new Request(Method.POST, documentsUrl);

		Form form = new Form();
		form.add("name", name);
		form.add("content", content);
		form.add("user", userName);
		Representation rep = form.getWebRepresentation();
		request.setEntity(rep);

		// Add the client authentication to the call
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme, userName, password);
		request.setChallengeResponse(authentication);

		// Ask to the HTTP client connector to handle the call
		return client.handle(request);

	}

	public Response getDocument(String documentUriString) {
		return client.get(documentUriString);
	}

	public Response executeQuery(String queryString) {
//		System.out.println(queryUrl + "?query=" + java.net.URLEncoder.encode(queryString));
		return client.get(queryUrl + "?query=" + java.net.URLEncoder.encode(queryString));
	}

}
