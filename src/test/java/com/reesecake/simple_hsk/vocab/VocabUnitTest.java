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
    void shouldReturnVocab() {
        Vocab vocab = new Vocab("电视", "diàn shì", "TV", "HSK1");
        vocabRepository.save(vocab);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab);

        List<Vocab> actualList = service.getAllVocabs("HSK1", 0, 5000, "id");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnEmptyVocab() {
        List<Vocab> expectedList = new ArrayList<>();

        List<Vocab> actualList = service.getAllVocabs("HSK1", 0, 5000, "id");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnVocabByLevelIsLessThanEqual() {
        Vocab vocab1 = new Vocab("电视", "diàn shì", "TV", "HSK1");
        Vocab vocab2 = new Vocab("吧", "ba", "onomatopoeia", "HSK2");
        Vocab vocab3 = new Vocab("阿姨", "āyí", "auntie", "HSK3");
        vocabRepository.save(vocab1);
        vocabRepository.save(vocab2);
        vocabRepository.save(vocab3);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab1);
        expectedList.add(vocab2);

        List<Vocab> actualList = service.getAllVocabs("HSK2", 0, 5000, "id");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnVocabSortedByPinyin() {
        Vocab vocab1 = new Vocab("都", "dōu", "all", "HSK1");
        Vocab vocab2 = new Vocab("零", "líng", "zero", "HSK2");
        vocabRepository.save(vocab2);
        vocabRepository.save(vocab1);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab1);
        expectedList.add(vocab2);

        List<Vocab> actualList = service.getAllVocabs("HSK2", 0, 5000, "pinyin");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnVocabSortedByMeaning() {
        Vocab vocab1 = new Vocab("都", "dōu", "all", "HSK1");
        Vocab vocab2 = new Vocab("零", "líng", "zero", "HSK2");
        vocabRepository.save(vocab2);
        vocabRepository.save(vocab1);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab1);
        expectedList.add(vocab2);

        List<Vocab> actualList = service.getAllVocabs("HSK2", 0, 5000, "meaning");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnVocabSortedByLevel() {
        Vocab vocab1 = new Vocab("电视", "diàn shì", "TV", "HSK1");
        Vocab vocab2 = new Vocab("吧", "ba", "onomatopoeia", "HSK2");
        vocabRepository.save(vocab2);
        vocabRepository.save(vocab1);

        List<Vocab> expectedList = new ArrayList<>();
        expectedList.add(vocab1);
        expectedList.add(vocab2);

        List<Vocab> actualList = service.getAllVocabs("HSK2", 0, 5000, "level");

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    @Transactional
    void shouldReturnVocabEqualsTrue() {
        Vocab vocab = new Vocab("电视", "diàn shì", "TV", "HSK1");
        vocabRepository.save(vocab);

        boolean actual = vocab.equals(vocab);

        Assertions.assertEquals(true, actual);
    }

    @Test
    @Transactional
    void shouldReturnVocabEqualsFalse() {
        Vocab vocab1 = new Vocab("电视", "diàn shì", "TV", "HSK1");
        Vocab vocab2 = new Vocab("吧", "ba", "onomatopoeia", "HSK2");
        vocabRepository.save(vocab1);
        vocabRepository.save(vocab2);

        boolean actual = vocab1.equals(vocab2);

        Assertions.assertEquals(false, actual);
    }

}
