package com.exadel.dinnerorders.service;

import com.exadel.dinnerorders.entity.Menu;
import com.exadel.dinnerorders.entity.SystemResource;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.util.Collection;

public class SolrService {

    private static SolrServer getServer(){
        String url = Configuration.getProperty(SystemResource.SOLR_HOST);
        url += ":" + Configuration.getProperty(SystemResource.SOLR_PORT) + "/solr";
        return new HttpSolrServer(url);
    }

    public static Collection<Menu> loadAllMenus() throws SolrServerException {
        SolrServer server = getServer();
        SolrQuery query = new SolrQuery();
        query.setQuery(":");
        QueryResponse response = server.query(query);
        return response.getBeans(Menu.class);
    }
}
