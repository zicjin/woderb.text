import java.io.IOException;
import java.math.BigInteger;

public class SimHashDemo {

    public static void main(String[] args) throws IOException {
        // TestAndOperation();
         Test0();
        // Test1();
    }

    // 理解实施逻辑，为什么用 new BigInteger("1").shiftLeft(i) &运算 word hash
    public static void TestAndOperation() {
        BigInteger test1 = new BigInteger("64");
        System.out.println(test1.toString(2));
        BigInteger test2 = new BigInteger("2343423");
        System.out.println(test2.toString(2));
        BigInteger result = test1.and(test2);
        System.out.println(result);
        System.out.println(result.signum());
    }

    public static void Test0() throws IOException {
        SimHash hash1 = new SimHash("传统的 hash 算法只负责将原始内容尽量均匀随机地映射为一个签名值", 64);
        System.out.println("intSimHash:" + hash1.intSimHash);

        BigInteger t = SimHash.hash("传统", 64);
        System.out.println(t.toString());

        BigInteger ss = new BigInteger("1").shiftLeft(20);
        System.out.println(ss);
        System.out.println(t.and(ss).signum());
    }

    public static void Test1() throws IOException {
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

        // 实测结论 Readhub 聚合的节点在海明距离7
    }
}
