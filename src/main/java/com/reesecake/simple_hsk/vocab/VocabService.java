package com.reesecake.simple_hsk.vocab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VocabService
{
    @Autowired
    VocabRepository repository;

    // https://howtodoinjava.com/spring-boot2/pagination-sorting-example/

    public List<Vocab> getAllVocabs(String hskLevel, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Vocab> pagedResult = repository.findVocabByLevelIsLessThanEqual(hskLevel, paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<Vocab>();
        }
    }
}
