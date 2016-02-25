
package es;

import java.util.Date;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.Sets;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

public final class LoadStatIndexer {
	
	
	public static void loadStatIndexer(Client client,final String loadStatIndex, final String fileName, final String insertDate, final int totalCount) {
		
		
		try {
			
			XContentBuilder builder = XContentFactory
									  .jsonBuilder()
									  .startObject()
									  .field("File_Name", fileName)
									  .field("Total_Count", totalCount)
									 
									  .field("Date_Inserted", insertDate)
									  .endObject();
			
			String json = builder.string();
			
			System.out.println(json);
			
			client.prepareIndex(loadStatIndex, "data").setSource(json).execute();

			
			
			builder.close();
		}catch(Exception stg) {
			stg.printStackTrace();
		}
			
	}
	
	
	public static void loadStatIndexer(Client client, final String loadStatIndex, final String process , final String fileName, final String insertDate, final int totalCount) {
		
		try {
			
			XContentBuilder builder = XContentFactory
									  .jsonBuilder()
									  .startObject()
									  .field("Process", process)
									  .field("File_Name", fileName)
									  .field("Total_Count", totalCount)
									
									  .field("Date_Inserted", insertDate)
									  .endObject();
			
			String json = builder.string();
			
			System.out.println(json);
			
			client.prepareIndex(loadStatIndex, "data").setSource(json).execute();
			 
			
			  builder.close();
		}catch(Exception stg) {
			stg.printStackTrace();
		}
			
	}
	
	public static void main(final String[] args) {
		
	Client client = LocalConnection.getLocalConnection();
	String process = "Data Load";
	String indexLoad = "testing";
    String date = new Date(System.currentTimeMillis()).toString();;

	int totCount = 0;
	
    loadStatIndexer(client, indexLoad, process, fileName, date, totCount);
	
	client.close();
	
	
	}

	
	
	
}
