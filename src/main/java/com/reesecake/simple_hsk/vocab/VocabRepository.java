package com.reesecake.simple_hsk.vocab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VocabRepository extends PagingAndSortingRepository<Vocab, Long> {

    List<Vocab> findVocabsByLevel(String level);
    List<Vocab> findVocabsByWordContaining(String word);
//    List<Vocab> findVocabByLevelIsLessThanEqual(String level);
    Page<Vocab> findVocabByLevelIsLessThanEqual(String level, Pageable pageable);
}
