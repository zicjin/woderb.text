package HanLPDemo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

/**
 * 日本人名识别
 * @author hankcs
 */
public class JapaneseNameRecognition
{
    public static void main(String[] args)
    {
        String[] testCase = new String[]{
                "北川景子参演了林诣彬导演的《速度与激情3》",
                "林志玲亮相网友:确定不是波多野结衣？",
                "龟山千广和近藤公园在龟山公园里喝酒赏花",
        };
        Segment segment = HanLP.newSegment().enableJapaneseNameRecognize(true);
        for (String sentence : testCase)
        {
            List<Term> termList = segment.seg(sentence);
            System.out.println(termList);
        }
    }
}
