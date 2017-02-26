package edu.cmu.mis.iccfb.controller;

import java.util.ArrayList;
import java.util.List;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.service.AuthorService;
import edu.cmu.mis.iccfb.service.QuoteService;
import edu.cmu.mis.iccfb.model.Quote;
import edu.cmu.mis.iccfb.ApiGatewayApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
@EnableCircuitBreaker
@RestController
public class ApiGatewayController {

    private AuthorService authorService;
    private QuoteService quoteService;
    @Value("${service.quote.uri.random}")
    private String authorServerUriRandom;

    @Value("${service.quote.uri.quote}")
    private String quoteServerUriListQuote;

    @Value("${service.api.uri.save}")
    private String quoteServerUriSaveQuote;
    @Value("${service.api.uri.id}")
    private String quoteServerUriListById;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("/ApiGatewayService/{ApiGatewayApplication}")
    public List<ApiGatewayApplication> ApiGatewayApplication(
            @PathVariable String ApiGatewayApplication) {
        return this.discoveryClient.getInstances();
    }




    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/api/quote/random", method = RequestMethod.GET)
    public Quote random() {
        return quoteService.randomQuote();
    }

    @HystrixCommand(fallbackMethod = "listFallback")
    @RequestMapping(value = "/api/quote/by")
    public List<Quote> findByName(@RequestParam(required = true)String name) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = quoteServerUriListById+name;
        Author author =  restTemplate.getForObject(uri,Author.class);
        ResponseEntity<Quote> st = restTemplate.postForEntity(uri, author, Quote.class);
        return quoteService.findByAuthor_Name(name);
    }







    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    public void saveQuote(@RequestBody Quote quote) {
        System.out.println(quote);
        RestTemplate restTemplate = new RestTemplate();
        String uri = quoteServerUriSaveQuote;
        ResponseEntity st = restTemplate.postForEntity(uri, quote, Quote.class);
        Author a = authorService.findAuthorById(quote.getAuthorId());

        System.out.println("Saving author");
        authorService.save(authorService.findAuthorById(quote.getAuthorId()));
        System.out.println("Saving quote");
        quoteService.save(quote);
        System.out.println(st.getBody());
    }
    
    
    
    public Quote fallback() {
        Quote q = new Quote();
        Author a = new Author("Confucius");
        q.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q.setSource("wikiquote");
        q.setAuthorId(a.getId());
        return q; 
    }

    public List<Quote> listFallback() {
        List<Quote> list = new ArrayList<>();
        Quote q1 = new Quote();
        Author a = new Author("Confucius");
        q1.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q1.setSource("wikiquote");
        q1.setAuthorId(1l);
        list.add(q1);

        Quote q2 = new Quote();
        q2.setText("Life is really simple, but we insist on making it complicated.");
        q2.setSource("wikiquote");
        q2.setAuthorId(1l);
        list.add(q2);

        Quote q3 = new Quote();
        q3.setText("It does not matter how slowly you go as long as you do not stop.");
        q3.setSource("wikiquote");
        q3.setAuthorId(1l);
        list.add(q3);


        return list;
    }

}
