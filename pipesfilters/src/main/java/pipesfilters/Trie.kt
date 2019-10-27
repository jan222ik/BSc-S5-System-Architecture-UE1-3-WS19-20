package pipesfilters

open class Trie {

    private val root = TrieNode();

    public fun addWord(word: String) {
        root.addWord(word, 0)
    }

    public fun isWord(s: String): Boolean {
        return root.containsWord(s, 0)
    }


    class TrieNode {
        private var chars: HashMap<Char, TrieNode> = HashMap()
        private var isWord: Boolean = false;

        fun addWord(word: String, index: Int) {
            if (index == word.length) {
                isWord = true
            } else {
                val node: TrieNode? = chars[word[index]]
                if (node != null) {
                    node.addWord(word, index + 1)
                } else {
                    chars[word[index]] = TrieNode().also { it.addWord(word, index + 1) }
                }
            }
        }

        fun containsWord(s: String, i: Int): Boolean {
            return if (s.length == i) {
                isWord
            } else {
                chars[s[i]]?.containsWord(s, i + 1) ?: false
            }
        }
    }
}
