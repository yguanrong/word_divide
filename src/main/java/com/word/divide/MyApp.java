package com.word.divide;


import com.word.divide.words.Words;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ：ygr
 * @date ：Created in 2020-6-30
 */

@Slf4j
public class MyApp {


    public static void main(String[] args){
        String str = "你好，我喜欢打球、唱歌、编程还有你";
        Words words = new Words();

        log.info("words = " + words.getWords(str));
    }
}

