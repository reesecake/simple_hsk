package com.reesecake.simple_hsk.vocab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vocabs")
public class VocabController {

    @Autowired
    VocabService service;

    // https://howtodoinjava.com/spring-boot2/pagination-sorting-example/

    /**
     * GetMapping for getting a Pageable list of Vocabs by level and levels less than.
     * URL query parameters are {?level,page,size,sort}
     * @param hskLevel String of HSK# level to query downwards from
     * @param pageNo The queried page in the Pageable
     * @param pageSize Size of a page in the Pageable
     * @param sortBy id to sort the list of Vocabs by
     * @return Pageable list of Vocab
     */
    @GetMapping("/search/findVocabByLevelIsLessThanEqual")
    public ResponseEntity<List<Vocab>> getAllVocabs(
            @RequestParam(defaultValue = "HSK1") String hskLevel,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        List<Vocab> list = service.getAllVocabs(hskLevel, pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Vocab>>(list, new HttpHeaders(), HttpStatus.OK);
    }
}
