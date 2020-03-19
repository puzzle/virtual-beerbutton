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
	
	@Value("${application.authtoken:@null}")
	private String token;
	
	@Value("${application.homeassistanturl}")
	private String homeassistanturl;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/beerbutton")
	public void hitBeerButton() {
		log.info("Beer Button was clicked");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " + token);
	    
	    HttpEntity<String> request = 
	    	      new HttpEntity<String>("{\"entity_id\": \"script.notify_remote_fyrabebier\"}", headers);
	    
	    String result = restTemplate.postForObject(homeassistanturl, request, String.class);
	    log.info("Result from Home Assistant: " + result);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/coffeebutton")
	public void hitCoffeeButton() {
		log.info("Coffee Button was clicked");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " + token);
	    
	    HttpEntity<String> request = 
	    	      new HttpEntity<String>("{\"entity_id\": \"script.notify_remote_kafipouse\"}", headers);
	    
	    String result = restTemplate.postForObject(homeassistanturl, request, String.class);
	    log.info("Result from Home Assistant: " + result);
	}
}
