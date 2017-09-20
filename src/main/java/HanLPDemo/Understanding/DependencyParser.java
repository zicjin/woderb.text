package HanLPDemo.Understanding;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

/**
 * 依存句法分析（CRF句法模型需要-Xms512m -Xmx512m -Xmn256m，MaxEnt和神经网络句法模型需要-Xms1g -Xmx1g -Xmn512m）
 * @author hankcs
 */
public class DependencyParser
{
    public static void main(String[] args)
    {
        String s1 = "尽管 CEO 放狠话 摩根大通仍提供比特币交易服务";
        String s2 = "徐先生还具体帮助他确定了把画雄鹰、松鼠和麻雀作为主攻目标。";
        String s3 = "北京交通委：共享单车每辆车都应打钢印 一般 3 年报废";
        String s4 = "8 年来，英特尔一直与谷歌合作，为 Waymo 提供芯片";
        String s5 = "腾讯联合广汽集团开发联网汽车 后者股价上涨 5%";
        CoNLLSentence sentence = HanLP.parseDependency(s5);
        System.out.println(sentence);
        // 可以方便地遍历它
        for (CoNLLWord word : sentence)
        {
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        // 也可以直接拿到数组，任意顺序或逆序遍历
        CoNLLWord[] wordArray = sentence.getWordArray();
        for (int i = wordArray.length - 1; i >= 0; i--)
        {
            CoNLLWord word = wordArray[i];
            System.out.printf("%s --(%s)--> %s\n", word.LEMMA, word.DEPREL, word.HEAD.LEMMA);
        }
        // 还可以直接遍历子树，从某棵子树的某个节点一路遍历到虚根
        CoNLLWord head = wordArray[12];
        while ((head = head.HEAD) != null)
        {
            if (head == CoNLLWord.ROOT) System.out.println(head.LEMMA);
            else System.out.printf("%s --(%s)--> ", head.LEMMA, head.DEPREL);
        }
    }
}
