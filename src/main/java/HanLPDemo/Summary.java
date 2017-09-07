/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/12/7 19:25</create-date>
 *
 * <copyright file="DemoChineseNameRecoginiton.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014+ 上海林原信息科技有限公司. All Right Reserved+ http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package HanLPDemo;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.summary.TextRankSentence;

import java.util.List;

/**
 * 自动摘要
 * @author hankcs
 */
public class Summary
{
    public static void main(String[] args)
    {
        String document = "水利部水资源司司长陈明忠9月29日在国务院新闻办举行的新闻发布会上透露，" +
                "根据刚刚完成了水资源管理制度的考核，有部分省接近了红线的指标，" +
                "有部分省超过红线的指标。对一些超过红线的地方，陈明忠表示，对一些取用水项目进行区域的限批，" +
                "严格地进行水资源论证和取水许可的批准。";
        String document2 = "新浪科技讯 北京时间9月7日下午消息，IBM公司本周四表示，将在未来十年斥资2.4亿美元，资助麻省理工学院（MIT）建设一座新型人工智能（AI）研究实验室。\n" +
                "　　作为本次合作成果，MIT-IBM人工智能实验室将集中关注少数关键性AI领域，包括开发新的“深度学习”算法。深度学习是人工智能的一个分支，目的是将人类的学习能力带给计算机，使其能够更自主地操作。\n" +
                "　　该实验室将位于麻省剑桥，由IBM研究部副总裁达里奥·吉尔（Dario Gil）和MIT工程学院院长阿纳瑟·钱德卡桑（Anantha Chandrakasan）联合主持，将吸纳来自IBM和MIT大学大约100名研究人员。\n" +
                "　　钱德卡桑通过电子邮件表示，其中一些研究人员可能正在MIT的相关团队工作，包括计算机科学和人工智能实验室（CSAIL）、媒体实验室、大脑与认知科学实验室，以及数据系统和社会研究所，他们需要跨界运作。当被问及研究人员是否必须在其工作中使用IBM技术时，他说后者可以按需选择最合适的技术。\n" +
                "　　虽然以前曾有企业资助MIT研究，例如丰田赞助了CSAIL内部的自动汽车项目，但是，由一家公司承包该院的整个实验室实属首次。\n" +
                "　　IBM一直在对外宣传沃森机器人，后者曾在2011年击败两位人类冠军。作为IBM公司的重要优先事项，它一直在支持一些机器人技术的发展。沃森与癌症药物研究所进行了广泛合作，例如与康奈尔大学对食品安全展开研究。沃森与伦斯勒理工学院和密歇根大学的合作项目也在进行中。\n" +
                "　　不过，IBM在将人工智能应用于现实世界的竞争中面临许多挑战。谷歌、微软、亚马逊和其他公司都在投入资源，使其软件更加智能化，更能适应需求变化。（斯眉）";
        List<String> sentenceList = HanLP.extractSummary(document2, 5);
        System.out.println(sentenceList);
    }
}
