package com.reesecake.simple_hsk.vocab;

import com.reesecake.simple_hsk.AbstractWebIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VocabResourceIntegrationTest extends AbstractWebIntegrationTest {

    @Autowired
    VocabRepository vocabRepository;

    /**
     * Checks that database being tested on is empty; should be a new, in-memory H2 db
     */
    @Test
    @Order(1)
    void shouldReturnEmptyVocabAggregate() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isEmpty());
    }

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
                .andExpect(jsonPath("$._embedded.vocabs.[0].wordSimplified", is("塑料袋")))
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
                .andExpect(jsonPath("$.wordSimplified", is("笔记本")))
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
                .get("/api/vocabs/search/findVocabsByWordSimplifiedContaining?word=电").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].wordSimplified", is("电脑")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("diàn nǎo")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("computer")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")))
                // second word:
                .andExpect(jsonPath("$._embedded.vocabs.[1].wordSimplified", is("电视")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].pinyin", is("diàn shì")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].meaning", is("TV")))
                .andExpect(jsonPath("$._embedded.vocabs.[1].level", is("HSK1")));
    }

    @Test
    @Transactional
    void shouldReturnEmptyListOfWordsContaining() throws Exception {
        vocabRepository.deleteAll();

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/vocabs/search/findVocabsByWordSimplifiedContaining?word=电").contentType(MediaTypes.HAL_JSON))
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
                .get("/api/vocabs/search/findVocabsByWordSimplifiedContaining?word=面").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].wordSimplified", is("后面")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("hòu miàn")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("rear")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")))
                // second word:
                .andExpect(jsonPath("$._embedded.vocabs.[1].wordSimplified", is("面条")))
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
                .get("/api/vocabs/search/findVocabsByWordSimplifiedContaining?word=后面").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded.vocabs").isNotEmpty())
                // first word:
                .andExpect(jsonPath("$._embedded.vocabs.[0].wordSimplified", is("后面")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].pinyin", is("hòu miàn")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].meaning", is("rear")))
                .andExpect(jsonPath("$._embedded.vocabs.[0].level", is("HSK1")));
    }

    /**
     * Checks that the /api/vocabs/search _links are not removed/renamed
     * Note: uses hasKey() because _links is a map of api names to links
     */
    @Test
    void shouldReturnVocabsSearchLinks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$._links", hasKey("findVocabsByWordSimplifiedContaining")))
                .andExpect(jsonPath("$._links", hasKey("findVocabByLevelIsLessThanEqual")))
                .andExpect(jsonPath("$._links", hasKey("findVocabsByLevel")));
    }

    @Test
    void shouldReturnVocabsSearchLinksFindVocabsByWordSimplifiedContainingHasLink() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$._links", hasKey("findVocabsByWordSimplifiedContaining")))
                .andExpect(jsonPath("$._links.findVocabsByWordSimplifiedContaining.href",
                        is("http://localhost/api/vocabs/search/findVocabsByWordSimplifiedContaining{?word}"))
                );
    }

    @Test
    void shouldReturnVocabsSearchLinksFindVocabByLevelIsLessThanEqualHasLink() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$._links", hasKey("findVocabByLevelIsLessThanEqual")))
                .andExpect(jsonPath("$._links.findVocabByLevelIsLessThanEqual.href",
                        is("http://localhost/api/vocabs/search/findVocabByLevelIsLessThanEqual{?level,page,size,sort}"))
                );
    }

    @Test
    void shouldReturnVocabsSearchLinksFindVocabsByLevelHasLink() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search").contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$._links", hasKey("findVocabsByLevel")))
                .andExpect(jsonPath("$._links.findVocabsByLevel.href",
                        is("http://localhost/api/vocabs/search/findVocabsByLevel{?level}"))
                );
    }

    /**
     * Tests endpoint with required param and checks that pagination is present and matching
     * @throws Exception
     */
    @Test
    void shouldReturnVocabsSearchLinksFindVocabByLevelIsLessThanEqual_HSK1() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("爱", "ài", "love", "HSK1"));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search/findVocabByLevelIsLessThanEqual?level=HSK1")
                        .contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded").isNotEmpty())
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$.page").isNotEmpty())

                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/vocabs/search/findVocabByLevelIsLessThanEqual?level=HSK1&page=0&size=20")))

                .andExpect(jsonPath("$.page.size", is(20)))
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }

    @Test
    void shouldReturnVocabsSearchLinksFindVocabByLevelIsLessThanEqual_HSK1Size5000() throws Exception {
        vocabRepository.deleteAll();
        vocabRepository.save(new Vocab("爱", "ài", "love", "HSK1"));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/vocabs/search/findVocabByLevelIsLessThanEqual?size=5000&level=HSK1")
                        .contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$._embedded").isNotEmpty())
                .andExpect(jsonPath("$._links").isNotEmpty())
                .andExpect(jsonPath("$.page").isNotEmpty())

                .andExpect(jsonPath("$._links.self.href",
                        is("http://localhost/api/vocabs/search/findVocabByLevelIsLessThanEqual?level=HSK1&page=0&size=5000")))

                .andExpect(jsonPath("$.page.size", is(5000)))
                .andExpect(jsonPath("$.page.totalElements", is(1)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }
}
