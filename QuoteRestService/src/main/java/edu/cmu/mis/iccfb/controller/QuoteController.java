package edu.cmu.mis.iccfb.controller;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.cmu.mis.iccfb.service.QuoteService;
import edu.cmu.mis.iccfb.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import edu.cmu.mis.iccfb.model.Author;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@EnableCircuitBreaker
@SpringBootApplication
@RestController

public class QuoteController {

    @Autowired
    private QuoteService quoteService;
    @Value("${service.quote.uri.random}")
    private String quoteServerRandomUri;
    @Value("${service.author.uri.authorId}")
    private String authorServerUriAuthorId;
    @Value("${service.author.uri.saveQuote}")
    private String authorServerUriSaveQuote;
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/api/quote/random", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "fallback")
    public Quote random() {
        return quoteService.randomQuote();
    }


    @RequestMapping(value = "/api/quote", method = RequestMethod.POST)
    @HystrixCommand(fallbackMethod = "fallback")
    public void saveQuote(@RequestBody Quote quote) {
        System.out.println(quote);
        RestTemplate restTemplate = new RestTemplate();
        String uri = authorServerUriAuthorId+quote.getAuthorId();
        Author author = restTemplate.getForObject(uri, Author.class);
        ResponseEntity st = restTemplate.postForEntity(uri, author, Long.class);
        st.getBody();
            System.out.println("Saving author");
          //  authorService.save(authorService.findAuthorbyId(quote.getAuthorId()));
        System.out.println("Saving quote");
        quoteService.save(quote);
    }
    @HystrixCommand(fallbackMethod = "listFallback")
    @RequestMapping(value = "/api/quote/author", method = RequestMethod.GET)
    public List<Quote> findByAuthorId(@RequestParam(required = true)Long authorId) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = authorServerUriAuthorId + authorId;
        Author author = restTemplate.getForObject(uri, Author.class);
        return quoteService.findByAuthorId(author.getAuthorId());
    }
    @HystrixCommand(fallbackMethod = "listFallback")
    @RequestMapping(value = "/api/quote", method = RequestMethod.GET)
    public List<Quote> listQuotes() {
        List<Quote> quotes = new ArrayList<>();
        quoteService.findAll().iterator().forEachRemaining(quotes::add);
        return quotes;
    }


    public Quote fallback() {
        Quote q = new Quote();
        Author a = new Author("Confucius");
        q.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q.setSource("wikiquote");
        q.setAuthorId(a.getAuthorId());
        return q;
    }

    public List<Quote> listFallback() {
        List<Quote> list = new ArrayList<>();
        Quote q1 = new Quote();
        Author a = new Author("Confucius");
        q1.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q1.setSource("wikiquote");
        q1.setAuthorId(a.getAuthorId());
        list.add(q1);

        Quote q2 = new Quote();
        q2.setText("Life is really simple, but we insist on making it complicated.");
        q2.setSource("wikiquote");
        q2.setAuthorId(a.getAuthorId());
        list.add(q2);

        Quote q3 = new Quote();
        q3.setText("It does not matter how slowly you go as long as you do not stop.");
        q3.setSource("wikiquote");
        q3.setAuthorId(a.getAuthorId());
        list.add(q3);


        return list;
    }


}
