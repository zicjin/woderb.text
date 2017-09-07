package HanLPDemo;

import com.hankcs.hanlp.dictionary.CoreSynonymDictionary;

/**
 * 语义距离
 */
public class WordDistance {
    public static void main(String[] args) {
        String[] wordArray = new String[]
        {
            "香蕉",
            "苹果",
            "白菜",
            "水果",
            "蔬菜",
            "自行车",
            "公交车",
            "飞机",
            "买",
            "卖",
            "购入",
            "新年",
            "春节",
            "丢失",
            "补办",
            "办理",
            "送给",
            "寻找",
            "孩子",
            "教室",
            "教师",
            "会计",
        };
        System.out.printf("%-5s\t%-5s\t%-10s\t%-5s\n", "词A", "词B", "语义距离", "语义相似度");
        for (String a : wordArray) {
            for (String b : wordArray) {
                System.out.printf("%-5s\t%-5s\t%-15d\t%-5.10f\n",
                        a, b,
                        CoreSynonymDictionary.distance(a, b),
                        CoreSynonymDictionary.similarity(a, b)
                );
            }
        }
    }
}