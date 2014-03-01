package com.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ziplock.IO;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import static com.example.RestClient.execute;
import static com.example.RestClient.normalize;
import static org.junit.Assert.assertTrue;

/**
 * Created by ron on 3/1/14.
 */
@RunWith(Arquillian.class)
public class TestRequired {
    @ArquillianResource
    private URL webappUrl;

    @Deployment
    public static WebArchive archive() {
        final WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.example");
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory("src/main/webapp").as(GenericArchive.class),
                "/", Filters.includeAll());
        System.out.println(war.toString(true));
        return war;
    }

    @Test
    public void test() {
        System.out.println(webappUrl);
        assertTrue(true);
    }

    @Test
    public void testCatalogIsReturned() throws IOException, URISyntaxException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest/catalogs"));
        final String content = execute(get);
        System.out.println("content: \'"+content+"\'");
        Assert.assertTrue(content.contains("root"));
    }

    @Test
    public void testCatalogWithOneChildIsReturned() throws URISyntaxException, IOException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest/catalogs/filledChild"));
        final String content = execute(get);
        System.out.println("content: \'"+content+"\'");
        Assert.assertTrue(content.contains("children"));
    }
}
