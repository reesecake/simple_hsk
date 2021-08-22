package com.reesecake.simple_hsk.vocab;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
public class Vocab {

    private @Id @GeneratedValue Long id;
    private String wordSimplified;
    private String wordTraditional;
    private String pinyin;  // the romanization of words for pronunciation
    private String pinyinNumbered;  // tones are represented as numbers next to pinyin
    private String meaning;

    private String level;  // the HSK level the word was introduced in

    public Vocab() {}

    public Vocab(String word_simplified, String pinyin, String meaning, String level) {
        this.wordSimplified = word_simplified;
        this.pinyin = pinyin;
        this.meaning = meaning;
        this.level = level;
    }

    // TODO: add/edit a constructor to set wordTraditional and pinyinNumbered

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vocab vocab = (Vocab) o;

        return Objects.equals(id, vocab.id);
    }

    @Override
    public int hashCode() {
        return 977216114;
    }
}
