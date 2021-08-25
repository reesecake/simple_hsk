package com.reesecake.simple_hsk.vocab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void clearRepo() {
        vocabRepository.deleteAll();
    }

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
    void shouldReturnVocabEqualsTrue() {
        Vocab vocab = new Vocab("电视", "diàn shì", "TV", "HSK1");
        vocabRepository.save(vocab);

        boolean actual = vocab.equals(vocab);

        Assertions.assertTrue(actual);
    }

    @Test
    void shouldReturnVocabEqualsFalse() {
        Vocab vocab1 = new Vocab("电视", "diàn shì", "TV", "HSK1");
        Vocab vocab2 = new Vocab("吧", "ba", "onomatopoeia", "HSK2");
        vocabRepository.save(vocab1);
        vocabRepository.save(vocab2);

        boolean actual = vocab1.equals(vocab2);

        Assertions.assertFalse(actual);
    }

    @Test
    void shouldReturnVocabEquals_Null() {
        Vocab vocab = new Vocab("八", "bā", "eight", "HSK1");

        boolean actual = vocab.equals(null);

        Assertions.assertFalse(actual);
    }

    @Test
    void shouldReturnVocabHashCode() {
        Vocab vocab = new Vocab("爸爸", "bàba", "Dad", "HSK1");

        Assertions.assertEquals(977216114, vocab.hashCode());
    }

    @Test
    void shouldSetVocabId() {
        Vocab vocab = new Vocab("多", "duō", "many", "HSK1");

        Assertions.assertNull(vocab.getId());

        vocab.setId(69L);

        Assertions.assertEquals(69L, vocab.getId());
    }

    @Test
    void shouldSetVocabWordSimplified() {
        // fun fact: 背景 means 'background' but is similarly pronounced bèijǐng
        Vocab vocab = new Vocab("背景", "Běijīng", "Beijing", "HSK1");  // do not copy

        vocab.setWordSimplified("北京");

        Assertions.assertEquals("北京", vocab.getWordSimplified());
    }

    @Test
    void shouldSetVocabWordTraditional() {
        Vocab vocab = new Vocab("打电话", "dǎ diànhuà", "make a phone call", "HSK1");

        vocab.setWordTraditional("打電話");

        Assertions.assertEquals("打電話", vocab.getWordTraditional());
    }

    @Test
    void shouldSetVocabPinyin() {
        // fun fact: 北京 is pronounced Běijīng but means Beijing (city) ass opposed to bèijǐng (background)
        Vocab vocab = new Vocab("北京", "bèijǐng", "Beijing", "HSK1");  // do not copy

        vocab.setPinyin("Běijīng");

        Assertions.assertEquals("Běijīng", vocab.getPinyin());
    }

    @Test
    void shouldSetVocabPinyinNumbered() {
        Vocab vocab = new Vocab("大", "dà", "big; large", "HSK1");

        vocab.setPinyinNumbered("da4");

        Assertions.assertEquals("da4", vocab.getPinyinNumbered());
    }

    @Test
    void shouldSetVocabMeaning() {
        Vocab vocab = new Vocab("二", "èr", "tHrEe", "HSK1");  // do not copy

        vocab.setMeaning("two");

        Assertions.assertEquals("two", vocab.getMeaning());
    }

    @Test
    void shouldSetVocabLevel() {
        Vocab vocab = new Vocab("杯子", "bēizi", "cup; glass", "HSK3");  // do not copy

        vocab.setLevel("HSK1");  // set correct level

        Assertions.assertEquals("HSK1", vocab.getLevel());
    }
}
