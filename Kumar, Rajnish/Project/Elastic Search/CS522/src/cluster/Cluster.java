package cluster;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;



public class Cluster {

	@SuppressWarnings("unused")
	public void createClusterHC(UniCns inq, List<UniCns> uniCnsList,
			Client client, String clusterIndexName, String clusterIndexNameHC,
			Connection con) {
		if (!(uniCnsList.size() > 100)) {
			Iterator<UniCns> it = uniCnsList.iterator();
			Index index = new Index();
			InsertInOracle relDB = new InsertInOracle();
			Map<String, String> map = new HashMap<String, String>();
			boolean flag = false;
			// BulkResponse blResp = null;
			String id = null;

			SearchResponse sr = fetch.searchCandidate(client, inq,
					clusterIndexName);
			if (sr.getHits().getTotalHits() > 0) {
				flag = true;
				Iterator<SearchHit> itr = sr.getHits().iterator();
				while (itr.hasNext()) {
					SearchHit sh = itr.next();
					id = sh.getId();
				}
			}
			if (flag) {
				while (it.hasNext()) {
					UniCns temp = it.next();
					sr = fetch.searchCandidate(client, temp, clusterIndexName);
					if (sr.getHits().getTotalHits() > 0) {
						Iterator<SearchHit> itr = sr.getHits().iterator();
						while (itr.hasNext()) {
							SearchHit sh = itr.next();
							if (!id.equals(sh.getId()))
								map.put(id, sh.getId());
						}
					}
				}
				index.bulkIndexClusterHC(inq, map, client, clusterIndexNameHC);
				/* relDB.hcClusterInsert(map, con); */
			}
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public void createCluster(UniCns inq, List<UniCns> uniCnsList, Client client, String clusterIndexName, String clusterIndexNameHC, Connection con, String _id) throws Exception {
		
		Iterator<UniCns> it = uniCnsList.iterator();
		Delete delete 							= new Delete();
		Index index 							= new Index();
		InsertInOracle relDb 					= new InsertInOracle();
		Set<String> set 						= new HashSet<String>();
		Set<String> setDeleteCls 				= new HashSet<String>();
		Set<String> setClsId 					= new HashSet<String>();
		Set<String> settosort 					= new HashSet<String>();
		Map<String, Map<String, Object>> map 	= new HashMap<String, Map<String, Object>>();
		boolean flag 							= false;

		while (it.hasNext()) {
			
			UniCns temp = it.next();
			
			if (!settosort.contains(temp.getCns_key() + "|"	+ temp.getUniCnstype() + "|" + temp.getUniCnsMatch().getRule_code() + "|" + temp.getUniDetails().getCin())) {
				
				set.add(temp.getCns_key() + "|" + temp.getUniCnstype() + "|" + temp.getUniCnsMatch().getRule_code() + "|" + temp.getUniDetails().getCin());
				SearchResponse sr = fetch.searchCandidate(client, temp,	clusterIndexName);
				
				if (sr.getHits().getTotalHits() > 0) {
					
					flag = true;
					Iterator<SearchHit> itr = sr.getHits().iterator();
					
					while (itr.hasNext()) {
						
						SearchHit sh = itr.next();
						map.put(sh.getId(), sh.sourceAsMap());
						setClsId.add(sh.getId());
					}
				} 
			}
			settosort.add(temp.getCns_key() + "|" 
					+ temp.getUniCnstype() + "|"
					+ temp.getUniCnsMatch().getRule_code() + "|"
					+ temp.getUniDetails().getCin());
		}
		
		ArrayList<HashMap<String, Object>> arr = null;
		settosort = new HashSet<String>();
		
		if (flag) {
			
			Iterator<Entry<String, Map<String, Object>>> itrmap = map.entrySet().iterator();
			
			while (itrmap.hasNext()) {
				
				Map.Entry<String, Map<String, Object>> e = itrmap.next();
				arr = (ArrayList<HashMap<String, Object>>) e.getValue().get("cands");

				Iterator<HashMap<String, Object>> itrarr = arr.iterator();
				
				while (itrarr.hasNext()) {
					
					HashMap<String, Object> temp = itrarr.next();
					
					settosort.add(temp.get(KeyConstants.CNS_KEY) + "|"
								+ temp.get(KeyConstants.UNICNSTYPE) + "|"
								+ temp.get(KeyConstants.RULE_CODE) + "|"
								+ temp.get(KeyConstants.CUSTOMER_ID));
				}
			}
			if (set.containsAll(settosort) && settosort.containsAll(set)&& setClsId.size() == 1) {
				setClsId = new HashSet<String>();
			} else {
				set.addAll(settosort);
				if (!(set.size() > 50)) {
					// System.out.println("getting .........." + set);
					setDeleteCls = index.bulkIndexCluster(inq, set, client,	clusterIndexName, _id);
				} else
					setClsId = new HashSet<String>();

			}

		} else {
			setDeleteCls = index.bulkIndexCluster(inq, uniCnsList, client, clusterIndexName, _id);
		}

		delete.deleteCluster(client, setClsId, clusterIndexName, "data");
		delete.afterDeleteCluster1(client, setClsId, setDeleteCls, clusterIndexName, clusterIndexNameHC, "data", con);
	}
}
