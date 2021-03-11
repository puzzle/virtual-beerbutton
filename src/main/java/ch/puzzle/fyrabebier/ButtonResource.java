package ch.puzzle.fyrabebier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;

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
	
	@Value("${application.drymodeFeatureEnabled:#{false}}")
	private boolean drymodeFeatureEnabled;
	
	@Value("${application.virtualRoomUrlBeerButton:#{null}}")
	private String virtualRoomUrlBeerButton;
	
	@Value("${application.virtualRoomUrlCoffeeButton:#{null}}")
	private String virtualRoomUrlCoffeeButton;

	@Value("${application.dashboardUrlVoteResults:#{null}}")
	private String dashboardUrlVoteResults;
	
	private final Counter beerCounter;
	private final Counter coffeeCounter;
	private final Map<String, Map<Integer, Counter>> counters;
	private final MeterRegistry meterRegistry;

	@Autowired
	public ButtonResource(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
		this.counters = new HashMap<>();
		this.beerCounter = meterRegistry.counter("puzzle.virtualbutton.button.click.total", Arrays.asList(Tag.of("button", "beer")));
		this.coffeeCounter = meterRegistry.counter("puzzle.virtualbutton.button.click.total", Arrays.asList(Tag.of("button", "coffee")));
	}

	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/beerbutton")
	public void hitBeerButton() {
		log.info("Beer Button was clicked");
		sendPayloadToBackend(backendurlbeer, tokenbeer, payloadbeer);
		beerCounter.increment();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/coffeebutton")
	public void hitCoffeeButton() {
		log.info("Coffee Button was clicked");
		sendPayloadToBackend(backendurlcoffee, tokencoffee, payloadcoffee);
		coffeeCounter.increment();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value="/vote/{group}")
	public void hitVoteButton(@PathVariable("group") String group, @RequestBody int value) {
		if (value < 0 || value >= 10){
			value = -1;
		}
		log.debug("Vote Button was clicked: group (" + group + ") value (" + value + ")");

		Counter counter;
		if(counters.containsKey(group)){
			if(counters.get(group).containsKey(Integer.valueOf(value))){
				counter = counters.get(group).get(Integer.valueOf(value));
			}else{
				counter = meterRegistry.counter("puzzle.virtualbutton.vote.total", Arrays.asList(Tag.of("group", group), Tag.of("value", String.valueOf(value))));
				counters.get(group).put(Integer.valueOf(value), counter);
			}
		}else{
			Map<Integer, Counter> counterMap;
			counterMap = new HashMap<>();
			counter = meterRegistry.counter("puzzle.virtualbutton.vote.total", Arrays.asList(Tag.of("group", group), Tag.of("value", String.valueOf(value))));
			counterMap.put(Integer.valueOf(value), counter);
			counters.put(group, counterMap);
		}

		counter.increment();
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
	
	@GetMapping(value="/configuration")
	public Configuration getConfiguration() {
		return new Configuration(drymodeFeatureEnabled, virtualRoomUrlBeerButton, virtualRoomUrlCoffeeButton, dashboardUrlVoteResults);
	}
}
