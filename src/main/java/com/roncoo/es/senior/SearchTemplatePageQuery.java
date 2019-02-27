package com.roncoo.es.senior;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.script.ScriptService;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Admin
 * @date 2018/10/6
 */
public class SearchTemplatePageQuery {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));

        Map<String, Object> scriptParams = new HashMap<String, Object>();
        scriptParams.put("from",0);
        scriptParams.put("size",0);
        scriptParams.put("brand","宝马");

        SearchResponse searchResponse = new SearchTemplateRequestBuilder(client)
                .setScript("page_query_by_brand")
                .setScriptType(ScriptType.STORED)
                .setScriptParams(scriptParams)
                .setRequest(new SearchRequest("car_sale").types("sales"))
                .get()
                .getResponse();
        for (SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsString());
        }
        client.close();

    }
}
