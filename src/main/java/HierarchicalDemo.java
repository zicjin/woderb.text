import java.io.IOException;
import java.util.ArrayList;

public class HierarchicalDemo {
    public static void main(String[] args) throws IOException {
        ArrayList<Hierarchical.Node> nodes = new ArrayList<Hierarchical.Node>();

        for (String text : contents)
        {
            Hierarchical.Node node = new Hierarchical.Node();
            node.id = java.util.UUID.randomUUID().toString();
            node.text =  text;
            node.simHash = new SimHash(text, 64);
            nodes.add(node);
        }

        Hierarchical hi = new Hierarchical(nodes);
        ArrayList<ArrayList<Hierarchical.Node>> result = hi.processHierarchical(100);
        for (ArrayList<Hierarchical.Node> assemble: result) {
            System.out.println("assemble:");
            for (Hierarchical.Node node: assemble) {
                System.out.println(" [" + node.text + "]");
            }
        }
    }

    private static String[] contents = new String[] {
            "腾讯联合广汽集团开发联网汽车 后者股价上涨 5%",
            "广汽与腾讯达成战略合作协议，围绕汽车全产业链展开业务与资本合作",
            "广汽和腾讯达成合作，深耕车联网、智能汽车和大数据 | 钛快讯",
            "尽管 CEO 放狠话 摩根大通仍提供比特币交易服务",
            "摩根大通否认在欧洲悄悄低吸比特币：是客户自己买的！",
            "苹果 CEO 库克每天 3:45 起床 会收到 700 到 800 份电子邮件",
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
            "iPhone X 影响了 iPhone 8 的预售 手表需求高于预期",
            "苹果新品预购情况：iPhone X 抢单 iPhone 8，新 Apple Watch 需求高",
            "KGI：iPhone X 可能影响 iPhone 8 销量 手表需求高于预期",
            "iOS 11 暂不支持 Apple Pay Cash 等未来升级",
            "iOS 11 暂不支持 Apple Pay Cash 功能，等未来会升级",
            "苹果的个人转账和 Apple Pay Cash 零钱包功能不会随 iOS 11 正式版一同到来",
            "工信部再发 4 张 CDN 牌照 获牌企业增至 28 家",
            "趣店拟赴美 IPO 最高募资 7.5 亿美元",
            "趣店终于提交 IPO 拟筹资 7.5 亿美元",
            "趣店向美 SEC 提交 IPO 招股书 拟筹资 7.5 亿美元",
            "趣店向美国 SEC 递交 IPO 招股书 拟最高募资 7.5 亿美元",
            "8 年来，英特尔一直与谷歌合作，为 Waymo 提供芯片",
            "英特尔与 Waymo 合作开发无人驾驶汽车技术",
            "英特尔 CEO 科再奇：英特尔已经与谷歌就无人驾驶项目合作了八年",
            "英特尔向人工智能企业投资已超 10 亿美元",
            "永安行共享单车项目重启 8 亿融资： 蚂蚁金服子公司参与",
            "「共享单车第一股」 永安行：子公司拟融资 8 亿",
            "蚁金服投资 ofo 后，其子公司又以 4 亿元入股了永安行低碳",
            "刚 IPO 的永安行，为何又要融资 8.1 亿元？",
            "海贼王纪念版魅蓝 Note6：动漫迷的大礼",
            "魅蓝或将推出海贼王 20 周年纪念版 Note 6",
            "36 氪首发｜纸巾自动售卖机「来包纸」获千万级天使轮融资，梅花天使领投",
            "坐享庞大专利库 诺基亚授权收入持续增加",
            "诺基亚与 LG 专利费仲裁案作出判决 Q3 营收将获提振",
            "悲催！Apple Watch 3 的 LTE 功能出国旅行时不能用",
    };
}
