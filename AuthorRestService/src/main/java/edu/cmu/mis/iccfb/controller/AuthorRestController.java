package edu.cmu.mis.iccfb.controller;
import edu.cmu.mis.iccfb.service.AuthorService;
import edu.cmu.mis.iccfb.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
@EnableCircuitBreaker
@RestController
public class AuthorRestController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private DiscoveryClient discoveryClient;

    @HystrixCommand(fallbackMethod = "findAuthorFallback")
    @RequestMapping(value = "/api/author/{authorId}", method = RequestMethod.GET)
    public Author findByAuthorId(@RequestParam(required = true)Long id) {

        return authorService.findById(id);
    }
    @HystrixCommand(fallbackMethod = "findAuthorNameFallback")
    @RequestMapping(value = "/api/author/by", method = RequestMethod.GET)
    public Author findByName(@RequestParam(required = true)String name) {

        return authorService.findByName(name);
    }

    @HystrixCommand(fallbackMethod = "saveAuthorFallback")
    @RequestMapping(value = "/api/author", method = RequestMethod.POST)
    public Object saveAuthor(@RequestBody String name) {
        Author author = new Author(name);
            authorService.save(author);
        return author;

    }

    public Author findAuthorFallback(){
        Author defaultAuthor1 = new Author("Confucius");
        return defaultAuthor1;

    }

    public Author findAuthorNameFallback(){
        Author defaultAuthor2 = new Author("Confucius");
        return defaultAuthor2;

    }
    public Author saveAuthorFallback(){
        Author defaultAuthor3 = new Author("Confucius");
        return defaultAuthor3;

    }


}
