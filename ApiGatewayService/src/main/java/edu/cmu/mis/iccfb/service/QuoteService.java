package edu.cmu.mis.iccfb.service;

import edu.cmu.mis.iccfb.model.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuoteService extends CrudRepository<Quote, Long>, QuoteServiceCustom {
    List<Quote> findByAuthor_Name(String name);
    List<Quote> findByAuthorId(Long authorId);

}
