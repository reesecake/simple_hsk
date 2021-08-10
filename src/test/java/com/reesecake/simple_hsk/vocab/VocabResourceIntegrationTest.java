package com.reesecake.simple_hsk.vocab;

import com.reesecake.simple_hsk.AbstractWebIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VocabResourceIntegrationTest extends AbstractWebIntegrationTest {

    @Autowired
    VocabRepository vocabRepository;

    @Test
    @Transactional
    void shouldReturnVocabAggregate() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("塑料袋", "sù liào dài", "plastic bag", "HSK4"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                .andExpect(jsonPath("$._embedded.vocabs.[0].word", is("塑料袋")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("sù liào dài")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("plastic bag")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK4")));
    }

    @Test
    @Transactional
    void shouldReturnVocabResource() throws Exception {
        vocabRepository.deleteAll();
        Vocab vocab = new Vocab("笔记本", "bǐ jì běn", "notebook", "HSK3");
        vocabRepository.save(vocab);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/" + vocab.getId().toString()).contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.word", is("笔记本")))
                .andExpect(jsonPath("$.pinyin", is("bǐ jì běn")))
                .andExpect(jsonPath("$.meaning", is("notebook")))
                .andExpect(jsonPath("$.level", is("HSK3")));
    }

    /**
     * Gets from /api/vocabs/search/findVocabsByLevel with no query parameters
     */
    @Test
    @Transactional
    void shouldReturnEmptyListVocabsByLevel() throws Exception {
        vocabRepository.deleteAll();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByLevel").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isEmpty());
    }

    @Test
    @Transactional
    void shouldReturnListOfWordsStartingWith() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("电脑", "diàn nǎo", "computer", "HSK1"));
        vocabRepository.save(new Vocab("电视", "diàn shì", "TV", "HSK1"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByWordContaining?word=电").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].word", is("电脑")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("diàn nǎo")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("computer")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")))
                // second word:
                .andExpect(jsonPath("$._embedded.vocabs.[1].word", is("电视")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].pinyin", is("diàn shì")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].meaning", is("TV")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].level", is("HSK1")));
    }

    @Test
    @Transactional
    void shouldReturnEmptyListOfWordsContaining() throws Exception {
        vocabRepository.deleteAll();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByWordContaining?word=电").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isEmpty());
    }

    @Test
    @Transactional
    void shouldReturnListOfWordsContaining() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("后面", "hòu miàn", "rear", "HSK1"));
        vocabRepository.save(new Vocab("面条", "miàn tiáo", "noodles", "HSK2"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByWordContaining?word=面").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].word", is("后面")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("hòu miàn")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("rear")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")))
                // second word:
                .andExpect(jsonPath("$._embedded.vocabs.[1].word", is("面条")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].pinyin", is("miàn tiáo")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].meaning", is("noodles")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].level", is("HSK2")));
    }

    @Test
    @Transactional
    void shouldReturnExactlyWordContaining() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("后面", "hòu miàn", "rear", "HSK1"));
        vocabRepository.save(new Vocab("面条", "miàn tiáo", "noodles", "HSK2"));

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByWordContaining?word=后面").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].word", is("后面")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("hòu miàn")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("rear")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")));
    }

}
