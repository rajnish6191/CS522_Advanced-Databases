package es;

import java.util.Date;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;

@SuppressWarnings("deprecation")
public class Connection {

	@SuppressWarnings("resource")
	public static Client getConnection() {
		Settings settings = ImmutableSettings.settingsBuilder()
							.put("cluster.name", "elasticsearch")
							.put("number_of_shards", 3)
						    .put("number_of_replicas", 1)
							.put("transport.netty.connections_per_node.low", 2)
							.put("transport.netty.connections_per_node.med", 6)
							.put("transport.netty.connections_per_node.high", 1)
							.put("client.transport.sniff", true)
							.build();

		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));

		
		return client;
	}

	@SuppressWarnings({ "resource", "unused" })
	public static Client createIndexAndgetConnection(String indexName) {

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").put("number_of_shards", 3)
				.put("number_of_replicas", 1).put("transport.netty.connections_per_node.low", 2)
				.put("transport.netty.connections_per_node.med", 6).put("transport.netty.connections_per_node.high", 1)
				.put("client.transport.sniff", true).build();

		CreateIndexRequest indexRequest = new CreateIndexRequest(indexName, settings);

		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));
				CreateIndexResponse resp = client.admin().indices().create(indexRequest).actionGet();

		return client;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		long start = System.currentTimeMillis();

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch")
				.build();

		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));

		XContentBuilder builder;
		IndexResponse response = null;

		builder = XContentFactory.jsonBuilder().startObject().field("user", "rajnish").field("postDate", new Date())
				.field("message", "trying out Elasticsearch").endObject();

		String json = builder.string();

		response = client.prepareIndex("roh", "tweet"/* , i + "" */).setSource(json).execute().actionGet();
		String _index = response.getIndex();
		String _type = response.getType();
		String _id = response.getId();
		long _version = response.getVersion();

		System.out.println(_index + ";" + _type + ";" + _id + ";" + _version);


		System.out.println("Time taken " + (System.currentTimeMillis() - start));

		// on shutdown
		client.close();
	}

	// bulk request for add and delete......
	@SuppressWarnings({ "resource", "unused" })
	public void bulk() throws Exception {

		// Node node = nodeBuilder().clusterName("Avengers").node();
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch")
				/* .put("client.transport.sniff", true) */.build();

		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));

		BulkRequestBuilder bulkRequest = client.prepareBulk();

		// either use client#prepare, or use Requests# to directly build
		// index/delete requests
		bulkRequest
				.add(client.prepareIndex("twitter", "tweet", "1")
						.setSource(XContentFactory.jsonBuilder().startObject().field("user", "kimchy")
								.field("postDate", new Date()).field("message", "trying out Elasticsearch")
								.endObject()));

		bulkRequest.add(client.prepareIndex("twitter", "tweet").setSource(XContentFactory.jsonBuilder().startObject()
				.field("user", "kimchy").field("postDate", new Date()).field("message", "another post").endObject()));

		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}

		// Search query:
		SearchResponse response = client.prepareSearch("index1", "index2").setTypes("type1", "type2")
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.termQuery("multi", "test"))
				// Query
				.setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18)) // Filter
				.setFrom(0).setSize(60).setExplain(true).execute().actionGet();

		// Delete query:
		DeleteByQueryResponse responsedel = client.prepareDeleteByQuery("test")
				.setQuery(QueryBuilders.termQuery("_type", "type1")).execute().actionGet();

	}
}