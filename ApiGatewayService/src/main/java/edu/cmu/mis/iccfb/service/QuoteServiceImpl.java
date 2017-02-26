package edu.cmu.mis.iccfb.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.cmu.mis.iccfb.model.Author;
import edu.cmu.mis.iccfb.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class QuoteServiceImpl implements QuoteServiceCustom {

    Random random = new Random();
    
    @Autowired
    private QuoteService quoteService;

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public Quote randomQuote() {
        ArrayList<Quote> quotes = new ArrayList<Quote>();

        for (Quote q: this.quoteService.findAll() ) {
            quotes.add(q);
        }
        Quote q = quotes.get(random.nextInt(quotes.size()));
        return q;
    }

    public Quote fallback() {
        Quote q = new Quote();
        Author a = new Author("Confucius");
        q.setText("The superior man is modest in his speech, but exceeds in his actions.");
        q.setSource("wikiquote");
        q.setAuthorId(a.getId());
        return q;
    }

}
