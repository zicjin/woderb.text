import java.io.IOException;
import java.math.BigInteger;

public class SimHashDemo {

    public static void main(String[] args) throws IOException {
        // TestAndOperation();
        // Test0();
        // Test1();
        Test2();
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
        SimHash hash1 = new SimHash(s, 128);
        System.out.println(hash1.intSimHash + "  " + hash1.intSimHash.bitCount());
        // 计算 海明距离 在 3 以内的各块签名的 hash 值
        hash1.subByDistance(hash1, 3);

        s = "留点悬念不好吗？小米 Note 3 正式确认！";
        SimHash hash2 = new SimHash(s, 128);
        System.out.println(hash2.intSimHash + "  " + hash2.intSimHash.bitCount());
        hash1.subByDistance(hash2, 3);

        s = "小米 MIX2 可能会用上这些黑科技";
        SimHash hash3 = new SimHash(s, 128);
        System.out.println(hash3.intSimHash + "  " + hash3.intSimHash.bitCount());
        hash1.subByDistance(hash3, 3);

        s = "小米通报一起舞弊案件：涉及 6 人 1 人被逮捕";
        SimHash hash4 = new SimHash(s, 128);
        System.out.println(hash4.intSimHash + "  " + hash4.intSimHash.bitCount());
        hash1.subByDistance(hash4, 3);

        s = "小米对 MIX2 又进行了预热 主要突出 「无边框」";
        SimHash hash5 = new SimHash(s, 128);
        System.out.println(hash5.intSimHash + "  " + hash5.intSimHash.bitCount());
        hash1.subByDistance(hash5, 3);

        System.out.println("============================");

        System.out.println(hash1.hammingDistance(hash2));
        System.out.println("1-2:" + SimHash.getDistance(hash1.strSimHash, hash2.strSimHash));

        System.out.println(hash1.hammingDistance(hash3));
        System.out.println("1-3:" + SimHash.getDistance(hash1.strSimHash, hash3.strSimHash));

        System.out.println(hash1.hammingDistance(hash4));
        System.out.println("1-4:" + SimHash.getDistance(hash1.strSimHash, hash4.strSimHash));

        System.out.println(hash1.hammingDistance(hash5));
        System.out.println("1-5:" + SimHash.getDistance(hash1.strSimHash, hash5.strSimHash));

        // 实测结论 Readhub 聚合的节点在海明距离7
    }

    public static void Test2() throws IOException {
        SimHash hash1 = new SimHash(contents[0], 128);
        for (String text: contents) {
            SimHash hash2 = new SimHash(text, 128);
            System.out.println(text + ": " + hash2.hammingDistance(hash1));
        }
    }

    private static String[] contents = new String[] {
            "苹果 CEO 库克每天 3:45 起床 会收到 700 到 800 份电子邮件",
            "尽管 CEO 放狠话 摩根大通仍提供比特币交易服务",
            "腾讯联合广汽集团开发联网汽车 后者股价上涨 5%",
            "广汽与腾讯达成战略合作协议，围绕汽车全产业链展开业务与资本合作",
            "广汽和腾讯达成合作，深耕车联网、智能汽车和大数据 | 钛快讯",

            "摩根大通否认在欧洲悄悄低吸比特币：是客户自己买的！",

            "苹果 CEO 也不轻松！3:45 起床，会收到 700 到 800 份邮件",
            "北京交通委：共享单车每辆车都应打钢印 一般 3 年报废",
            "北京确认共享单车 3 年 「报废」 上路车辆完好率不低于 95%",
            "北京共享单车新规：设电子地图展示停放区禁停区等",
            "工商总局近日将约谈苹果 遭 80 余家 App 开发者举报垄断",
            "美机顶盒公司 Roku 将 IPO 融资额上调一倍至 2.5 亿美元",
            "美机顶盒厂商 Roku 上调 IPO 融资额至 2.19 亿美元 估值高于 10 亿美元",
            "美国机顶盒厂商 Roku 计划本月进行 IPO，目标融资额 2.04 亿美元",
            "谷歌或通过拍卖同意向竞争对手出售广告位",
            "为避免再度被欧盟处罚，谷歌将拍卖搜索广告位",
            "为了让欧盟放心 谷歌同意让对手竞拍广告位",
            "iOS 11 北京时间明日凌晨更新：哪些设备能升级",
            "iOS 11 系统今晚凌晨一点推送更新：升级机型一览汇总",
            "美国参议院投票 禁止联邦政府使用卡巴斯基产品",
            "美国参议院支持政府禁用卡巴斯基 称可能威胁安全",
            "京东入局房产电商，京东 VS 阿里的下一个战场：租房市场？",
    };

}
