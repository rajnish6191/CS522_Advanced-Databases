
package es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class LocalConnection {

	
	public static Client getLocalConnection() {
		Settings settings = ImmutableSettings.settingsBuilder()
							.put("cluster.name", "STG")
							.put("number_of_shards", 3)
							.put("number_of_replicas", 1)
							.put("transport.netty.connections_per_node.low", 2)
							.put("transport.netty.connections_per_node.med", 6)
							.put("transport.netty.connections_per_node.high", 1)
							.put("client.transport.sniff", true)
							.build();

		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));
		
		return client;
	}
	
	public static Client getLocalConnection67() {
		Settings settings = ImmutableSettings.settingsBuilder()
							.put("cluster.name", "STG")
							.put("number_of_shards", 3)
							.put("number_of_replicas", 1)
							.put("transport.netty.connections_per_node.low", 2)
							.put("transport.netty.connections_per_node.med", 6)
							.put("transport.netty.connections_per_node.high", 1)
							.put("client.transport.sniff", true)
							.build();

		@SuppressWarnings("resource")
		Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("192.168.0.25", 9300));
		
		return client;
	}
	
	
}
