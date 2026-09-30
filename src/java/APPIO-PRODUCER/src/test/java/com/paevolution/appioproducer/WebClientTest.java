package com.paevolution.appioproducer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.paevolution.appioproducer.ws.client.AppIOGatewayClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebClientTest {

    private static final String BASE_URL = "http://devel3:8080/appiogateway";
    private AppIOGatewayClient client;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {

	//client = new AppIOGatewayClient();
	//this.webTestClient = WebTestClient.bindToApplicationContext();
    }

    @Test
    public void testPOSTMessageRequest() throws Throwable {

	//CreatedMessageResponse response = client.postMessage(dummyRequest());
	//assertThat(response).isNotNull();
    }
    /*private MessaggiRequest dummyRequest() {
    
    return MessaggiRequest.builder().idcomune("E256").codicecomune("E256").software("SS").timeToLive(4700).fiscalCode("AAAAAA00A00A000A")
    	.subject("Test from producer")
    	.markdown(
    		"# This is a markdown header  to show how easily markdown can be converted to **HTML**  Remember: this has to be a long text. The full version of the message, in plain text or Markdown format. The content of this field will be delivered to channels that don't have any limit in terms of content size (e.g. email, etc...). asfdvohijhihi joihiophchjv[opjuopj ojoj[pojopcxjopjopj ohjiohiohiohiohiughasdiucgiu giugiugiugiugasdiucg iugiugiugxc iuguigiugiugiugi ugugiugiugcx iugiugiugiugiu giugiugiugdchuopiu iohjoiyhsdiofhio hiugugiugdcho ihiohiohiohdsc9ioh iogiug9c ihiohac ihio0hich i9hi9yh9iy9iASDY89 YOIHOIHOISGHDCI9OG U9G")
    	.dueDate("2021-03-05T15:49:57.5757Z").messageId(SecurityTools.generateType4UUID().toString()).build();
    }*/
}
