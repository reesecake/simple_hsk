package com.reesecake.simple_hsk.vocab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class VocabUnitTest {

    @Autowired
    VocabRepository vocabRepository;
    @Autowired
    VocabService service;

    @Test
    @Transactional
    void shouldReturnStuff() {
        Vocab vocab = new Vocab("电视", "diàn shì", "TV", "HSK1");
        vocabRepository.save(vocab);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab);

        List<Vocab> name = service.getAllVocabs("HSK1", 0, 5000, "id");

        Assertions.assertEquals(expectedList, name);
    }
}
