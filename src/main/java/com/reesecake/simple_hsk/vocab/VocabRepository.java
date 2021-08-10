package com.reesecake.simple_hsk.vocab;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VocabRepository extends PagingAndSortingRepository<Vocab, Long> {

    List<Vocab> findVocabsByLevel(String level);
    List<Vocab> findVocabsByWordContaining(String word);
}
