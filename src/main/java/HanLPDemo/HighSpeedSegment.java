package HanLPDemo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

/**
 * 演示极速分词，基于DoubleArrayTrie实现的词典正向最长分词，适用于“高吞吐量”“精度一般”的场合
 * @author hankcs
 */
public class HighSpeedSegment
{
    public static void main(String[] args)
    {
        String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
        HanLP.Config.ShowTermNature = false;
        System.out.println(SpeedTokenizer.segment(text));
        long start = System.currentTimeMillis();
        int pressure = 1000000;
        for (int i = 0; i < pressure; ++i)
        {
            SpeedTokenizer.segment(text);
        }
        double costTime = (System.currentTimeMillis() - start) / (double)1000;
        System.out.printf("SpeedTokenizer分词速度：%.2f字每秒\n", text.length() * pressure / costTime);
    }
}
