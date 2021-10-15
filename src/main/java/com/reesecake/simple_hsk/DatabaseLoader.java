package com.reesecake.simple_hsk;

import com.reesecake.simple_hsk.security.alternative.AppUser;
import com.reesecake.simple_hsk.security.alternative.AppUserRepository;
import com.reesecake.simple_hsk.vocab.VocabRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    private final VocabRepository vocabRepository;
    private final AppUserRepository appUserRepository;

    @Autowired
    public DatabaseLoader(VocabRepository vocabRepository, AppUserRepository appUserRepository) {
        this.vocabRepository = vocabRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
//        this.vocabRepository.save(new Vocab("电脑", "diàn nǎo", "computer", "HSK1"));
//        this.vocabRepository.save(new Vocab("电视", "diàn shì", "TV", "HSK1"));
//        this.vocabRepository.save(new Vocab("宾馆", "bīn guǎn", "hotel", "HSK2"));
//        this.vocabRepository.save(new Vocab("打篮球", "dǎ lán qiú", "play basketball", "HSK2"));
//        this.vocabRepository.save(new Vocab("爱情", "ài qíng", "romance / love (romantic)", "HSK4"));
//        this.vocabRepository.save(new Vocab("安排", "ān pái", "to arrange / to plan / to set up", "HSK4"));
//        this.vocabRepository.save(new Vocab("安全", "ān quán", "safe / secure / safety / security", "HSK4"));
//        this.vocabRepository.save(new Vocab("按时", "àn shí", "on time / before deadline / on schedule", "HSK4"));
//        this.vocabRepository.save(new Vocab("按照", "àn zhào", "according to / in accordance with / in the light of / on the basis of", "HSK4"));
//        this.vocabRepository.save(new Vocab("百分之", "bǎi fēn zhī", "percent", "HSK4"));
//        this.vocabRepository.save(new Vocab("棒", "bàng", "a stick / club or cudgel / smart / capable / strong / wonderful / classifier for legs of a relay race", "HSK4"));
//        this.vocabRepository.save(new Vocab("塑料袋", "sù liào dài", "plastic bag", "HSK4"));
//        this.vocabRepository.findAll().forEach(vocab -> log.info("Preloaded " + vocab));

//        AppUser reese = this.appUserRepository.save(new AppUser("reese", "rlam2017@gmail.com", "lam", "ROLE_ADMIN"));
//        AppUser nathan = this.appUserRepository.save(new AppUser("nathan", "nathan@simplehsk.com", "mohapatra", "ROLE_ADMIN"));
//        AppUser bigGao = this.appUserRepository.save(new AppUser("gao", "sample1@simplehsk.com", "password", "ROLE_TEACHER"));
//        AppUser littleWang = this.appUserRepository.save(new AppUser("wang", "sample2@simplehsk.com", "password", "ROLE_STUDENT"));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("reese", "helloworld",
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT")));

//        this.appUserRepository.findAll().forEach(appUser -> log.info("Preloaded " + appUser.getUsername()));

        SecurityContextHolder.clearContext();
    }
}
