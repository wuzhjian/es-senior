package com.roncoo.es.senior;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Admin on 2018/10/6.
 */
public class BoolQuerySearchBrand {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"),9300));


        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("brand", "宝马"))
                .mustNot(QueryBuilders.termQuery("name.raw", "宝马318"))
                .should(QueryBuilders.rangeQuery("produce_date").gte("2017-01-01").lte("2017-01-31"))
                .filter(QueryBuilders.rangeQuery("price").gte(280000).lte(350000));


        SearchResponse searchResponse = client.prepareSearch("car_shop")
                .setTypes("cars")
                .setQuery(queryBuilder)
                .get();

        for(SearchHit searchHit : searchResponse.getHits().getHits()){
            System.out.println(searchHit.getSourceAsString());
        }

        client.close();
    }
}
