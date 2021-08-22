package com.reesecake.simple_hsk;

import com.reesecake.simple_hsk.vocab.Vocab;
import com.reesecake.simple_hsk.vocab.VocabRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    private final VocabRepository vocabRepository;

    @Autowired
    public DatabaseLoader(VocabRepository vocabRepository) {
        this.vocabRepository = vocabRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.vocabRepository.save(new Vocab("电脑", "diàn nǎo", "computer", "HSK1"));
        this.vocabRepository.save(new Vocab("电视", "diàn shì", "TV", "HSK1"));
        this.vocabRepository.save(new Vocab("宾馆", "bīn guǎn", "hotel", "HSK2"));
        this.vocabRepository.save(new Vocab("打篮球", "dǎ lán qiú", "play basketball", "HSK2"));
        this.vocabRepository.save(new Vocab("爱情", "ài qíng", "romance / love (romantic)", "HSK4"));
        this.vocabRepository.save(new Vocab("安排", "ān pái", "to arrange / to plan / to set up", "HSK4"));
        this.vocabRepository.save(new Vocab("安全", "ān quán", "safe / secure / safety / security", "HSK4"));
        this.vocabRepository.save(new Vocab("按时", "àn shí", "on time / before deadline / on schedule", "HSK4"));
        this.vocabRepository.save(new Vocab("按照", "àn zhào", "according to / in accordance with / in the light of / on the basis of", "HSK4"));
        this.vocabRepository.save(new Vocab("百分之", "bǎi fēn zhī", "percent", "HSK4"));
        this.vocabRepository.save(new Vocab("棒", "bàng", "a stick / club or cudgel / smart / capable / strong / wonderful / classifier for legs of a relay race", "HSK4"));
        this.vocabRepository.save(new Vocab("塑料袋", "sù liào dài", "plastic bag", "HSK4"));
        this.vocabRepository.findAll().forEach(vocab -> log.info("Preloaded " + vocab));
    }
}
