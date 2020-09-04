package org.tlhg.bobAPI.resources;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import org.tlhg.bobAPI.DbRequest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import org.json.*;

@Path("/business")
public class BusinessResource {
	
	@GET
	public Object getBusiness(@QueryParam("city") String c, @QueryParam("tag") List<String> t) throws ClassNotFoundException, SQLException {
		
		
		String[] params = new String[t.size()];
		params = t.toArray(params);
		
		DbRequest req = new DbRequest();
		
		ResultSet ret = req.restRequest(c, params);
		
		JSONArray arr = rsConverter(ret);
		JSONObject obj = new JSONObject();
		obj.put("businesses", arr);
		
		return Response.status(200).type("application/json").entity(obj.toString(4))
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
