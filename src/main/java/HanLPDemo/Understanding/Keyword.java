package HanLPDemo.Understanding;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankKeyword;

import java.util.List;

/**
 * 关键词提取
 * @author hankcs
 */
public class Keyword
{
    public static void main(String[] args)
    {
        String content = "程序员(英文Programmer)是从事程序开发、维护的专业人员。" +
                "一般将程序员分为程序设计人员和程序编码人员，" +
                "但两者的界限并不非常清楚，特别是在中国。" +
                "软件从业人员分为初级程序员、高级程序员、系统" +
                "分析员和项目经理四大类。";
        String content2 = "兰州银行在其官方微信宣布兰州银行ATM上线扫码取款业务，使用微信或支付宝扫ATM机上的二维码，即可将微信或支付宝内的余额提现。随着不带现金和银行卡出门的现象越来越普遍，兰州银行此举被认为是抓住了时下痛点，具有开创意义。";
        String content3 = "互联网群组信息服务管理规定公布：对使用者实名认证";
        String content4 = "互联网群组群主及管理者应规范群组信息发布";
        String content5 = "网信办公布网络用户公众账号信息服务管理规定";
        List<String> keywordList = HanLP.extractKeyword(content5, 5);
        System.out.println(keywordList);
    }
}
