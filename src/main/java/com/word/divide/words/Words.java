package com.word.divide.words;


import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用字典树来构建词典
 * @author ：ygr
 * @date ：Created in 2020-8-12
 */
@Slf4j
public class Words {


    private String fileName = "words.txt";

    private Node root;

    /**
     * 树的节点
     */
    class Node{
        /**
         * 判断是否是叶子结点
         */
        private boolean isWord;

        /**
         * 单词出现的次数
         */
        private int wordNum;

        /**
         * 该节点的字
         */
        private char value;

        /**
         * 词的子树。表示词的下一个字。如 “学”，学的下一个字可以是 ["习","霸","生",···]
         */
        private Map<Character,Node> child;

        Node(){
            child = new HashMap<Character,Node>();
            isWord = false;
            wordNum = 0;
        }
    }

    public Words(){
        root = new Node();
        initWords();
    }

    /**
     * 查找词典中是否存在词：word
     * @param word
     * @return
     */
    public boolean hashWord(String word){

        Node pNode = this.root;

        for (int i = 0; i< word.length(); i++){
            char c = word.charAt(i);

            if (pNode.child.get(c) == null || ( i+1 == word.length() && !pNode.child.get(c).isWord)){
                return false;
            }
            pNode = pNode.child.get(c);
        }
        return true;
    }

    /**
     * 插入词到词典中，如果词典中没有该词则添加。如果已存在，wordnum++
     * @param word
     */
    public void insertWord(String word){

        if (word == null || word.isEmpty()) {
            return;
        }
        Node pNode = this.root;
        for (int i = 0; i< word.length(); i++){
            char c = word.charAt(i);
            if (pNode.child.get(c) == null ){
                Node node = new Node();
                node.value = c;
                pNode.child.put(c,node);
            }
            pNode = pNode.child.get(c);
        }
        pNode.isWord = true;
        pNode.wordNum++;

    }

    /**
     * 插入词到词典中，如果词典中没有该词则添加。如果已存在，wordnum++
     *
     */
    private void initWords(){
        BufferedReader reader = null;
        InputStream inputStream = null;
        try{
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line=reader.readLine())!=null)
            {
                insertWord(line);
            }
        }catch (Exception e){
            log.error(e.toString());
        }finally {
            try{
                if (reader!=null){
                    reader.close();
                }
                if (inputStream!=null)
                inputStream.close();
            }catch (Exception e){
                log.error(e.toString());
            }


        }

    }

    /**
     * 查询一句话中出现的词
     * @param sentence
     * @return
     */
    public List<String> getWords(String sentence){
        List<String> res = new ArrayList<String>();
        if (sentence == null || sentence.isEmpty()) {
            return res;
        }
        Node pNode;
        for (int i = 0; i< sentence.length(); i++){

            pNode = getRoot();
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = i; j< sentence.length(); j++){
                char c = sentence.charAt(j);

                if(pNode.child.get(c) == null ){

                    break;
                }else {
                    stringBuilder.append(c);
                }

                if (pNode.child.get(c).isWord){
                    res.add(stringBuilder.toString());
                }
                pNode = pNode.child.get(c);
            }
        }
        return res;
    }

    public Node getRoot(){
        return this.root;
    }


}
