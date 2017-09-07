package HanLPDemo.Segment;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;

import java.util.List;

/**
 * 索引分词
 * @author hankcs
 */
public class IndexSegment
{
    public static void main(String[] args)
    {
        List<Term> termList = IndexTokenizer.segment("主副食品");
        for (Term term : termList)
        {
            System.out.println(term + " [" + term.offset + ":" + (term.offset + term.word.length()) + "]");
        }
    }
}
