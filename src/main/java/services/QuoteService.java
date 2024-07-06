package services;

import models.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.QuoteRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class QuoteService {
    @Autowired
    BashParse parse;
    @Autowired
    QuoteRepository repository;


    public List<Quote> getPage(int page) throws IOException {
        List<Quote> ret = new ArrayList<>();
        Map<Integer, String> map = parse.getPage(page);
        for(Map.Entry<Integer,String> entry: map.entrySet()){
            Quote rawQuote = new Quote();
            rawQuote.setQuoteId(entry.getKey());
            rawQuote.setText(entry.getValue());
            Optional<Quote> existed = repository.findByQuoteIdEquals(rawQuote.getQuoteId());
            if(existed.isEmpty()) {
                ret.add(repository.save(rawQuote));
            } else {
                ret.add(existed.get());
            }
        }
        return ret;
    }
    public Quote getById(int id){
        Optional<Quote> existingQuote = repository.findByQuoteIdEquals(id);
        if(existingQuote.isPresent()){
            return existingQuote.get();
        }
        Map.Entry<Integer, String> quoteEntry = parse.getById(id);
        if(quoteEntry == null) return null;
        Quote newQuote = new Quote();
        newQuote.setQuoteId(quoteEntry.getKey());
        newQuote.setText(quoteEntry.getValue());
        return repository.save(newQuote);
    }
    public Quote getRandom(){
        Map.Entry<Integer, String> quoteEntry = parse.getRandom();
        if(quoteEntry == null) return null;

        Optional<Quote> existingQuote = repository.findByQuoteIdEquals(quoteEntry.getKey());
        if(existingQuote.isPresent()){
            return existingQuote.get();
        }
        Quote newQuote = new Quote();
        newQuote.setQuoteId(quoteEntry.getKey());
        newQuote.setText(quoteEntry.getValue());
        return repository.save(newQuote);
    }
}
