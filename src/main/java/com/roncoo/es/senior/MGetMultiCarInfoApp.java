package com.roncoo.es.senior;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Admin on 2018/10/5.
 */
public class MGetMultiCarInfoApp {
    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name","elasticsearch")
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));

        MultiGetResponse multiGetResponse = client.prepareMultiGet()
                .add("car_shop","cars","1")
                .add("car_shop","cars","2")
                .get();
        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse){
            GetResponse getResponse = multiGetItemResponse.getResponse();
            if (getResponse.isExists()){
                System.out.println(getResponse.getSourceAsString());
            }
        }
        client.close();
    }
}
