package com.reesecake.simple_hsk.vocab;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Vocab {

    private @Id @GeneratedValue Long id;
    private String word;
    private String pinyin;  // the romanization of words for pronunciation
    private String meaning;

    private String level;  // the HSK level the word was introduced in

    public Vocab() {}

    public Vocab(String word, String pinyin, String meaning, String level) {
        this.word = word;
        this.pinyin = pinyin;
        this.meaning = meaning;
        this.level = level;
    }
}
