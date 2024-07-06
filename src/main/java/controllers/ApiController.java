package controllers;

import models.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.QuoteRepository;
import services.QuoteService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    QuoteRepository repository;

    @Autowired
    QuoteService service;

    @GetMapping("/all")
    public ResponseEntity<List<Quote>> getAll(@RequestParam(required = false, defaultValue = "1") String page){
        int newPage = 1;
        try{
            newPage = Integer.parseInt(page);
        } catch (Exception e){
            e.printStackTrace();
        }
        Page<Quote> res = repository.findAll(PageRequest.of(newPage - 1, 5));
        return new ResponseEntity<>(res.stream().collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<List<Quote>> getPage(@RequestParam(required = false, defaultValue = "1") String page) throws IOException {
        int newPage = 1;
        try{
            newPage = Integer.parseInt(page);
        } catch (Exception e){
            e.printStackTrace();
        }
        List<Quote> res = service.getPage(newPage);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quote> getById(@PathVariable("id") int id) {
        Quote res = service.getById(id);
        if (res == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/random")
    public ResponseEntity<Quote> getRandom() {
        Quote res = service.getRandom();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/randomList")
    public ResponseEntity<List<Quote>> getRandomList() {
        List<Quote> res = repository.findAll(Sort.by("random()"));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
