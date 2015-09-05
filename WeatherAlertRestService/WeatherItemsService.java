package com.bgh.weather.items.rest;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;


@Path("/witems")
public class WeatherItemsService{
	
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<String> getWeatherItems(@QueryParam("wkey") String weatherKey,
											@QueryParam("tval") String tempVal) 
											throws UnknownHostException{
        //System.out.println("weatherKey "+ weatherKey);
		//System.out.println("tempVal "+ tempVal);												
		List<String> aar = getResults(weatherKey, tempVal);
		return aar;
	}
	
    
  public List<String> getResults(String weatherKey, String tempVal) throws java.net.UnknownHostException{
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		  DB db = mongoClient.getDB( "weather_food_db" );
		 
		  DBCollection coll = db.getCollection("weather_items");
		  
		  List<BasicDBObject> mainQueryObj = new ArrayList<>();
		  
		  BasicDBObject searchQuery1=new BasicDBObject();
		  searchQuery1.put("weather_key",weatherKey);
		  
		  mainQueryObj.add(searchQuery1);
		  
		  BasicDBObject searchQuery2=new BasicDBObject();
		  List<BasicDBObject> obj2 = new ArrayList<>();
		  obj2.add(new BasicDBObject("weather_key","T"));
		  obj2.add(new BasicDBObject("weather_value",tempVal));
		  searchQuery2.put("$and", obj2);
		  
		  mainQueryObj.add(searchQuery2);
		  
		  BasicDBObject finalQuery=new BasicDBObject();
		  finalQuery.put("$or", mainQueryObj);
		  	  
		  DBCursor cursor = coll.find(finalQuery);
		  DBObject dbObj;
		  Set<String> setItems = new HashSet<>();
		
		try {
			while(cursor.hasNext()) {
				dbObj = cursor.next();
				BasicDBList itmArrJSON = (BasicDBList) dbObj.get("itemsArray");
				for(Object item : itmArrJSON){
					setItems.add(((BasicDBObject)item).get("item").toString());
				}
			}
		} finally {
			cursor.close();
		}
		List<String> list = new ArrayList<String>(setItems);

		return list;
	}
}