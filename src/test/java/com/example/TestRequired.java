package com.example;

import org.apache.http.client.methods.HttpGet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.RestClient.execute;
import static com.example.RestClient.normalize;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertTrue(content.contains("root"));
    }

    @Test
    public void testCatalogWithOneChildIsReturned() throws URISyntaxException, IOException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest/catalogs/filledChild"));
        final String content = execute(get);
        System.out.println("content: \'"+content+"\'");
        assertTrue(content.contains("children"));
    }

    @Test
    public void testCatalogWithTwoChildrenIsReturned() throws URISyntaxException, IOException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(),"rest/catalogs/twoChildren"));
        final String content = execute(get);
        System.out.println("content: \'"+content+"\'");
        assertTrue(content.contains("child 2"));
    }

    @Test
    public void testJacksonProcessor() throws URISyntaxException, IOException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest/jackson"));
        final String content = execute(get);
        System.out.println("content :\'"+content+"\'");
        assertEquals("[\"Peter\",\"pan\",\"Ihihi\"]", content);
    }

    @Test
    public void testGetChildWithChildren() throws URISyntaxException, IOException {
        final HttpGet get = new HttpGet(normalize(webappUrl.toURI(), "rest/catalogs/childwithChildren"));
        final String content = execute(get);
        System.out.println(String.format("content: \'%s\'", content));
        Pattern p = Pattern.compile("children");
        Matcher m = p.matcher(content);
        int count = 0;
        while (m.find()){
            count +=1;
        }
        System.out.println(count);
        assertEquals(4, count);
    }
}
