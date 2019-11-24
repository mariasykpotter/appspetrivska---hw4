package ua.edu.ucu.tries;
import ua.edu.ucu.autocomplete.Dequeue;
import java.util.Iterator;


public class RWayTrie implements Trie {
    private static final int N = 26;
    private static final int FIRST_IN_ASCII = 'a';
    private TrieNode root;
    private int treeLength;

    private static class TrieNode {
        private Integer value;
        private TrieNode[] next = new TrieNode[N];
    }

    public RWayTrie() {
    }

    @Override
    public void add(Tuple t) {
        if (!contains(t.term)) {
            root = add(root, t, 0);
        }
    }

    private TrieNode add(TrieNode node, Tuple t, int recCounter) {
        if (node == null) node = new TrieNode();
        if (recCounter == t.term.length()) {
            if (node.value == null) treeLength++;
            node.value = t.weight;
            return node;
        }

        int letterNum = numInASCII(t.term.charAt(recCounter));
        node.next[letterNum] = add(node.next[letterNum], t, recCounter + 1);
        return node;
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    @Override
    public boolean delete(String word) {
        int prevSize = treeLength;
        root = delete(root, word, 0);
        return prevSize != treeLength;
    }

    private TrieNode delete(TrieNode node, String word, int recCounter) {
        if (node == null) return null;
        if (recCounter == word.length()) {
            if (node.value != null) treeLength--;
            node.value = null;
        } else {
            int letterNum = numInASCII(word.charAt(recCounter));
            node.next[letterNum] = delete(node.next[letterNum], word, recCounter + 1);
        }

        if (node.value != null) return node;
        for (int i = 0; i < N; i++)
            if (node.next[i] != null) return node;
        return null;
    }

    private int numInASCII(char a) {
        return a - FIRST_IN_ASCII;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2)
            return null;

        return new Iterable<String>() {

            @Override
            public Iterator<String> iterator() {
                return new TrieIterator(pref);
            }
        };
    }

    @Override
    public int size() {
        return treeLength;
    }

    private Integer get(String key) {
        TrieNode node = get(root, key, 0);
        if (node == null)
            return null;
        return node.value;
    }

    private TrieNode get(TrieNode node, String key, int recCounter) {
        if (node == null) return null;
        if (recCounter == key.length()) return node;
        int letterNum = numInASCII(key.charAt(recCounter));
        return get(node.next[letterNum], key, recCounter + 1);
    }

    private char charByCode(int i) {
        return (char) (i + FIRST_IN_ASCII);
    }

    private boolean isArrayNotEmpty(TrieNode[] nodes, int begin) {
        for (int i = begin; i < nodes.length; i++) {
            if (nodes[i] != null) {
                return true;
            }
        }
        return false;
    }

    private class TrieIterator implements Iterator<String> {

        private String pref;
        private TrieNode refNode;
        private String currentWord;

        private Dequeue words = new Dequeue();
        private Dequeue path = new Dequeue();
//        private Deque<String> words = new LinkedList<>();
//        private Deque<TrieNode[]> path = new LinkedList<>();

        public TrieIterator(String pref) {
            this.pref = pref;
            refNode = get(root, pref, 0);

            if (refNode != null) {
                if (isArrayNotEmpty(refNode.next, 0)) {
                    path.enqueue(refNode.next);
                    words.enqueue(pref);
                }

                if (refNode.value != null) {
                    currentWord = pref;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !path.isEmpty() || currentWord != null;
        }

        @Override
        public String next() {
            String word = null;
            if (currentWord != null) {
                word = currentWord;
                currentWord = null;
            } else {
                word = getWord();
            }

            return word;
        }

        private String getWord() {
            while (!path.isEmpty()) {
                TrieNode[] lastnodes = (TrieNode[]) path.dequeue(2.2);
                String lastWord = (String) words.dequeue(2);

                for (int i = 0; i < N; i++) {

                    if (lastnodes[i] != null) {
                        if (isArrayNotEmpty(lastnodes[i].next, 0)) {
                            path.enqueue(lastnodes[i].next);
                            words.enqueue(lastWord + charByCode(i));
                        }

                        TrieNode tmp = lastnodes[i];
                        lastnodes[i] = null;

                        if (tmp.value != null) {
                            if ((i != N - 1) && isArrayNotEmpty(lastnodes, i)) {
                                path.addFirst(lastnodes);
                                words.addFirst(lastWord);
                            }
                            return lastWord + charByCode(i);
                        }

                    }

                }
            }
            return null;
        }

    }

}