package ch.puzzle.fyrabebier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/api")
public class ButtonResource {
	
	private static final Logger log = LoggerFactory.getLogger(ButtonResource.class);
	
	@Value("${application.authtokenbeer:#{null}}")
	private String tokenbeer;
	
	@Value("${application.authtokencoffee:#{null}}")
	private String tokencoffee;
	
	@Value("${application.backendurlbeer}")
	private String backendurlbeer;
	
	@Value("${application.backendurlcoffee}")
	private String backendurlcoffee;
	
	@Value("${application.payloadbeer:#{null}}")
	private String payloadbeer;
	
	@Value("${application.payloadcoffee:#{null}}")
	private String payloadcoffee;
	
	
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/beerbutton")
	public void hitBeerButton() {
		log.info("Beer Button was clicked");
		sendPayloadToBackend(backendurlbeer, tokenbeer, payloadbeer);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/coffeebutton")
	public void hitCoffeeButton() {
		log.info("Coffee Button was clicked");
		sendPayloadToBackend(backendurlcoffee, tokencoffee, payloadcoffee);
	}
	
	private void sendPayloadToBackend(String url, String token, String payload) {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    if(token != null && !token.isEmpty()) {
	    	headers.set("Authorization", "Bearer " + token);
	    }
	    
	    HttpEntity<String> request = 
	    	      new HttpEntity<String>(payload, headers);
	    
	    String result = restTemplate.postForObject(url, request, String.class);
	    log.info("Result from backend: " + result);
	}
}
