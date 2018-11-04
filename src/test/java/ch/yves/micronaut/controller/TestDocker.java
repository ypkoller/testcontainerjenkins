package ch.yves.micronaut.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.org.apache.http.HttpEntity;
import org.testcontainers.shaded.org.apache.http.client.methods.CloseableHttpResponse;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpGet;
import org.testcontainers.shaded.org.apache.http.impl.client.CloseableHttpClient;
import org.testcontainers.shaded.org.apache.http.impl.client.HttpClientBuilder;
import org.testcontainers.shaded.org.apache.http.util.EntityUtils;

public class TestDocker {
	
	private final int exposedPort = 8080;

    @Rule
    public GenericContainer dslContainer = new GenericContainer("gandalf1973/micronaut:latest");
    	

    @Test
    public void simpleDslTest() throws IOException {
        //String address = String.format("http://%s:%s/hello", dslContainer.getContainerIpAddress(), dslContainer.getMappedPort(exposedPort));

        String address = "http://192.168.1.102:8080/hello";
        
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(address);

        try (CloseableHttpResponse response = httpClient.execute(get)) {
            assertEquals("Answer",
                            200,
                            response.getStatusLine().getStatusCode());
            
            assertNotNull("Has body", response.getEntity());
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            assertEquals("Check String","Hello Worlddfsgdsg",content);
        }
    }
}