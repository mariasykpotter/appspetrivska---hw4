package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.Iterator;

public class PrefixMatches {
    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int trieCounter = 0;
        for (int i = 0; i < strings.length; i++) {
            String[] strArr = strings[i].split(" ");
            for (int j = 0; j < strArr.length; j++) {
                if (strings[i].length() >= 2) {
                    trie.add(new Tuple(strArr[j], strArr[j].length()));
                    trieCounter++;
                }
            }
        }
        return trieCounter;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2)
            return null;

        int mxWordLen = pref.length() + k - 1;

        Iterator<String> triIter = trie.wordsWithPrefix(pref).iterator();

        return new Iterable<String>() {
            String tmpWord = getNextFromTrie();

            @Override
            public Iterator<String> iterator() {

                return new Iterator<String>() {

                    @Override
                    public boolean hasNext() {

                        return tmpWord != null;
                    }

                    @Override
                    public String next() {
                        String next = tmpWord;
                        tmpWord = getNextFromTrie();
                        return next;
                    }
                };
            }

            private String getNextFromTrie() {
                String word = null;
                if (triIter.hasNext()) {
                    word = triIter.next();
                    word = word.length() <= mxWordLen ? word : null;
                }
                return word;
            }
        };

    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return wordsWithPrefix(pref, Integer.MAX_VALUE - 1);
    }

    public int size() {
        return trie.size();
    }
}