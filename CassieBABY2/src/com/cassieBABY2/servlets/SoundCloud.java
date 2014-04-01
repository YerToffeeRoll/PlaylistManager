package com.cassieBABY2.servlets;

public class SoundCloud {

	// Create a wrapper instance
	ApiWrapper wrapper = new ApiWrapper("client_id", "client_secret",
	                                    null, null, Env.LIVE);
	// Obtain a token
	wrapper.login("username", "password");

	// Execute a request
	HttpResponse resp = wrapper.get(Request.to("/me"));

	// Get a resource
	HttpResponse response = api.get("me");
	if(response.getStatusLine().getStatusCode() == 200)
	{
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document dom = db.parse(response.getEntity().getContent());
	
}
