import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.TextRankKeyword;

public class SimHash {

    private String tokens;
    private BigInteger intSimHash;
    private String strSimHash;
    private int hashbits = 64;

    public SimHash(String tokens) throws IOException {
        this.tokens = tokens;
        this.intSimHash = this.simHash();
    }

    public SimHash(String tokens, int hashbits) throws IOException {
        this.tokens = tokens;
        this.hashbits = hashbits;
        this.intSimHash = this.simHash();
    }

    HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public BigInteger simHash() throws IOException {
        // 定义特征向量/数组
        int[] v = new int[this.hashbits];

        TextRankKeyword textRankKeyword = new TextRankKeyword();
        Map<String, Float> keywordList = textRankKeyword.getTermAndRank(this.tokens, 10);
        keywordList.forEach((key, value) -> {
            System.out.println(key + " " + value);
        });

        Segment segment = new com.hankcs.hanlp.seg.CRF.CRFSegment().enableCustomDictionary(true);
        List<Term> termList = segment.seg(this.tokens);

        termList.forEach((term) -> {
            Float weight = new Float(0.5);
            if (keywordList.containsKey(term.word)) {
                weight = keywordList.get(term.word);
            }
            System.out.println(term.word + " " + weight);
            // 将每一个分词hash为一组固定长度的数列.比如 64bit 的一个整数.
            BigInteger t = this.hash(term.word);
            for (int i = 0; i < this.hashbits; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                // 建立一个长度为64的整数数组(假设要生成64位的数字指纹,也可以是其它数字)
                if (t.and(bitmask).signum() != 0) {
                    // 计算整个文档的所有特征的向量和
                    // 实际使用中需要 +- 权重，比如词频，而不是简单的 +1/-1，
                    v[i] += weight;
                } else {
                    v[i] -= weight;
                }
            }
        });

        BigInteger fingerprint = new BigInteger("0");
        StringBuffer simHashBuffer = new StringBuffer();
        for (int i = 0; i < this.hashbits; i++) {
            // 最后对数组进行判断,大于0的记为1,小于等于0的记为0,得到一个 64bit 的数字指纹/签名.
            if (v[i] >= 0) {
                fingerprint = fingerprint.add(new BigInteger("1").shiftLeft(i));
                simHashBuffer.append("1");
            } else {
                simHashBuffer.append("0");
            }
        }
        this.strSimHash = simHashBuffer.toString();
        System.out.println(this.strSimHash + " length " + this.strSimHash.length());
        return fingerprint;
    }

    private BigInteger hash(String source) {
        if (source == null || source.length() == 0) {
            return new BigInteger("0");
        } else {
            char[] sourceArray = source.toCharArray();
            BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
            BigInteger m = new BigInteger("1000003");
            BigInteger mask = new BigInteger("2").pow(this.hashbits).subtract(new BigInteger("1"));
            for (char item : sourceArray) {
                BigInteger temp = BigInteger.valueOf((long) item);
                x = x.multiply(m).xor(temp).and(mask);
            }
            x = x.xor(new BigInteger(String.valueOf(source.length())));
            if (x.equals(new BigInteger("-1"))) {
                x = new BigInteger("-2");
            }
            return x;
        }
    }

    public int hammingDistance(SimHash other) {

        BigInteger x = this.intSimHash.xor(other.intSimHash);
        int tot = 0;

        // 统计x中二进制位数为1的个数
        // 我们想想，一个二进制数减去1，那么，从最后那个1（包括那个1）后面的数字全都反了，
        // 对吧，然后，n&(n-1)就相当于把后面的数字清0，
        // 我们看n能做多少次这样的操作就OK了。

        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    public static int getDistance(String str1, String str2) {
        int distance;
        if (str1.length() != str2.length()) {
            distance = -1;
        } else {
            distance = 0;
            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) != str2.charAt(i)) {
                    distance++;
                }
            }
        }
        return distance;
    }

    public List subByDistance(SimHash simHash, int distance) {
        // 分成几组来检查
        int numEach = this.hashbits / (distance + 1);
        List characters = new ArrayList();

        StringBuffer buffer = new StringBuffer();

        int k = 0;
        for (int i = 0; i < this.intSimHash.bitLength(); i++) {
            // 当且仅当设置了指定的位时，返回 true
            boolean sr = simHash.intSimHash.testBit(i);

            if (sr) {
                buffer.append("1");
            } else {
                buffer.append("0");
            }

            if ((i + 1) % numEach == 0) {
                // 将二进制转为BigInteger
                BigInteger eachValue = new BigInteger(buffer.toString(), 2);
                System.out.println("----" + eachValue);
                buffer.delete(0, buffer.length());
                characters.add(eachValue);
            }
        }

        return characters;
    }

    public static void main(String[] args) throws IOException {
//        BigInteger ss = new BigInteger("1").shiftLeft(20);
//        System.out.println(ss);
//        SimHash hash1 = new SimHash("传统的 hash 算法只负责将原始内容尽量均匀随机地映射为一个签名值", 64);
//        BigInteger t = hash1.hash("传统");
//        System.out.println("intSimHash:" + hash1.intSimHash);
//        System.out.println(t.toString());
//        System.out.println(t.and(ss).signum());

        String s = "小米再对 MIX2 进行预热 主要突出 「无边框」 主题";
        SimHash hash1 = new SimHash(s, 64);
        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitCount());
        // 计算 海明距离 在 3 以内的各块签名的 hash 值
        hash1.subByDistance(hash1, 3);

        s = "留点悬念不好吗？小米 Note 3 正式确认！";
        SimHash hash2 = new SimHash(s, 64);
        System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2, 3);

        s = "小米 MIX2 可能会用上这些黑科技";
        SimHash hash3 = new SimHash(s, 64);
        System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3, 3);

        s = "小米通报一起舞弊案件：涉及 6 人 1 人被逮捕";
        SimHash hash4 = new SimHash(s, 64);
        System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
        hash1.subByDistance(hash4, 3);

        s = "小米对 MIX2 又进行了预热 主要突出 「无边框」";
        SimHash hash5 = new SimHash(s, 64);
        System.out.println(hash5.intSimHash + "  " + hash5.intSimHash.bitCount());
        hash1.subByDistance(hash5, 3);

        System.out.println("============================");

        System.out.println(hash1.hammingDistance(hash2));
        System.out.println(SimHash.getDistance(hash1.strSimHash, hash2.strSimHash));

        System.out.println(hash1.hammingDistance(hash3));
        System.out.println(SimHash.getDistance(hash1.strSimHash, hash3.strSimHash));

        System.out.println(hash1.hammingDistance(hash4));
        System.out.println(SimHash.getDistance(hash1.strSimHash, hash4.strSimHash));

        System.out.println(hash1.hammingDistance(hash5));
        System.out.println(SimHash.getDistance(hash1.strSimHash, hash5.strSimHash));

        // 实测Readhub 聚合的节点在海明距离7

        // 根据鸽巢原理（也成抽屉原理，见组合数学），如果两个签名的海明距离在 3 以内，它们必有一块签名subByDistance()完全相同。
    }
}