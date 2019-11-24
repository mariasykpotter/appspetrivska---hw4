package ua.edu.ucu.autocomplete;
import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;


public class PrefixMatchesITTest {
    private PrefixMatches pm1;
    private PrefixMatches pm2;
    private PrefixMatches pm3;
    private PrefixMatches pm4;
    @Before
    public void init() {
        pm1 = new PrefixMatches(new RWayTrie());
        pm1.load("abc", "abce", "abcd", "abcde", "abcdef");
        pm2 = new PrefixMatches(new RWayTrie());
        pm2.load("li", "lit", "lio", "lion","little","lipppps");
        pm3 = new PrefixMatches(new RWayTrie());
        pm3.load("ra", "rad", "radf", "radfe", "radfet");
        pm4 = new PrefixMatches(new RWayTrie());
        pm4.load("ra", "rac", "raco", "racoo","racoon");
    }
    @Test
    public void testWordsWithPrefix_String1() {
        String pref = "ab";
        Iterable<String> result = pm1.wordsWithPrefix(pref);
        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_String_and_K1() {
        String pref = "abc";
        int k = 3;
        Iterable<String> result = pm1.wordsWithPrefix(pref, k);
        String[] expResult = {"abc", "abce", "abcd", "abcde"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString_and_K2() {
        String pref = "li";
        int k = 3;
        Iterable<String> result = pm2.wordsWithPrefix(pref, k);
        String[] expResult = {"li", "lit", "lio", "lion"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString2(){
        String pref = "li";
        Iterable<String> result = pm2.wordsWithPrefix(pref);
        String[] expResult = {"li", "lit", "lio", "lion","little","lipppps"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString_and_K3() {
        String pref = "ra";
        int k = 3;
        Iterable<String> result = pm3.wordsWithPrefix(pref, k);
        String[] expResult = {"ra", "rad", "radf"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString3(){
        String pref = "ra";
        Iterable<String> result = pm3.wordsWithPrefix(pref);
        String[] expResult = {"ra","rad", "radf", "radfe", "radfet"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString_and_K4() {
        String pref = "rac";
        int k = 3;
        Iterable<String> result = pm4.wordsWithPrefix(pref, k);
        String[] expResult = {"rac", "raco", "racoo"};
        assertThat(result, containsInAnyOrder(expResult));
    }
    @Test
    public void testWordsWithPrefix_LongString4(){
        String pref = "ra";
        Iterable<String> result = pm4.wordsWithPrefix(pref);
        String[] expResult = {"ra","rac","raco", "racoo","racoon"};
        assertThat(result, containsInAnyOrder(expResult));
    }


}