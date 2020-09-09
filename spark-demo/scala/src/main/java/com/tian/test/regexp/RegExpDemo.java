package com.tian.test.regexp;

import java.util.Arrays;

/**
 * @author tian
 * 2019/9/10 15:09
 */
/*
正则表达式Regular Expression
处理文本的强大工具
java:
    提供了两个类:
    Pattern: 用来把一段字符串编译成正则
    Matcher: 匹配器，用来去匹配字符串是否满足要求

    java的字符串提供了4个支持正则的方法
    matchs
    replaceAll
    replaceFirst
    split

正则表达式的语法
[abc] 或者a或者b或者c
[a-z] 所有的小写字母都匹配
[a-zA-Z0-9] 所有字母和数字都匹配
[^ab] 除了a和b都匹配，非a非b
\n 换行 \r 回车 \t 制表符
\s 表示空白字符
\\s 多使用一个\转义
\S 非空白 等价于 [^\s]
\d 匹配数字digital 等价于[0-9]
\D 非数字 等价于[^0-9]
\w 匹配单词字符 [a-zA-Z0-9_] 数字字母下划线
\W 匹配非单词字符 [^a-zA-Z0-9_] [^\w]
. 匹配任意字符 除\r 和 \n
\. 只匹配 .

数量词
a? 表示0个或1个a，至多一个
a* 表示0个或多个a，至少零个
a+ 表示1个或多个a，至少一个
a{3} 表示正好3个a
a{3,} 表示至少3个a
a{m,n} 至少m个，至多n个
^...$ 控制字符串长度，^表开始 $表结尾，java中可以没有
 */
public class RegExpDemo {
    public static void main(String[] args) {
        String phone = "18816668549";
        String email = "tian@tian.cn";
        boolean phoneMatch = phone.matches("^1[3456789]\\d{9}$"); //判断电话号码是否合法
        boolean emailMatch = email.matches("^\\w{3,15}@[\\w-]+\\.(com|cn|org|edu|com\\.cn)$");
        System.out.println(phoneMatch);
        System.out.println(emailMatch);

        String str1 = "good123good111t";
        System.out.println(str1.replaceAll("\\D", "+"));
        System.out.println(str1.replaceAll("\\D+", "+"));
        System.out.println(str1.replaceFirst("\\D+", ">>"));

        String str2 = "AAccBBbbddaaABC"; //消除叠词
        System.out.println(str2.replaceAll("(.)\\1+", "$1")); //取第一组

        String str3 = "146465464a4464a65464a564654a";//只保留数字
        System.out.println(Arrays.toString(str3.split("\\D")));

        String str4 = "a1a2aaaa"; //会优化掉尾部切出的空元素
        System.out.println(Arrays.toString(str4.split("\\D")));
    }
}
