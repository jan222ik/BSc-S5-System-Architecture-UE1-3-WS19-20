package pipesfilters

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import pipesfilters.framework.pmp.interfaces.Writeable

class KotlinMain {
    private val logger: Logger = LogManager.getLogger(KotlinMain::class.java)
    fun main(args: Array<String>) {
        logger.debug("Main Start")
        SourceImpl(Writeable { logger.info(it) }).run()
        Trie().also { trie ->
            trie.addWord("Hugo")
            trie.addWord("Hans")
            trie.addWord("Heinz")
            trie.isWord("Hugo").also { println(it)}
            trie.isWord("Hans").also { println(it)}
            trie.isWord("Heinz").also { println(it)}
            trie.isWord("No").also { println(it)}
        }

    }
}
