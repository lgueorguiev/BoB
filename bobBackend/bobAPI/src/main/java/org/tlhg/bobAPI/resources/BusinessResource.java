package org.tlhg.bobAPI.resources;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.tlhg.bobAPI.AzureDbRequest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.json.*;

@Path("/business")
public class BusinessResource {
	
	@GET
	public Object getBusiness(@QueryParam("tag") String t) throws ClassNotFoundException, SQLException {
		
		AzureDbRequest req = new AzureDbRequest();
		ResultSet ret = req.restRequest(t);
		
		JSONArray arr = rsConverter(ret);
		return Response.status(200).type("application/json").entity(arr.toString(4))
				.header("Access-Control-Allow-Origin", "*").build();
	}
	
	private static JSONArray rsConverter(ResultSet r) throws SQLException {
		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = r.getMetaData();
		while(r.next()) {
			int numCols = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			for(int i = 1; i <= numCols; i++) {
				String col_name = rsmd.getColumnName(i);
				obj.put(col_name, r.getObject(col_name));
			}
			
			json.put(obj);
		}
		
		return json;
	}
}
