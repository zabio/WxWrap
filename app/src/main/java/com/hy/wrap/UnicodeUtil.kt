package com.hy.wrap

import java.util.regex.Pattern

/**
 * Created by henry  2017/11/7.
 */

object UnicodeUtil {
    /**
     * 含有unicode 的字符串转一般字符串
     *
     * @param unicodeStr 混有 Unicode 的字符串
     * @return
     */
    fun unicodeStr2String(unicodeStr: String): String {
        val length = unicodeStr.length
        var count = 0
        //正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        val regex = "\\\\u[a-f0-9A-F]{1,4}"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(unicodeStr)
        val sb = StringBuffer()

        while (matcher.find()) {
            val oldChar = matcher.group()//原本的Unicode字符
            val newChar = unicode2String(oldChar)//转换为普通字符
            val index = unicodeStr.indexOf(oldChar)

            sb.append(unicodeStr.substring(count, index))//添加前面不是unicode的字符
            sb.append(newChar)//添加转换后的字符
            count = index + oldChar.length//统计下标移动的位置
        }
        sb.append(unicodeStr.substring(count, length))//添加末尾不是Unicode的字符
        return sb.toString()
    }

    /**
     * 字符串转换unicode
     *
     * @param string
     * @return
     */
    fun string2Unicode(string: String): String {
        val unicode = StringBuffer()
        for (i in 0 until string.length) {
            // 取出每一个字符
            val c = string[i]
            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c.toInt()))
        }

        return unicode.toString()
    }

    /**
     * unicode 转字符串
     *
     * @param unicode 全为 Unicode 的字符串
     * @return
     */
    fun unicode2String(unicode: String): String {
        val string = StringBuffer()
        val hex = unicode.split("\\\\u".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (i in 1 until hex.size) {
            // 转换出每一个代码点
            val data = Integer.parseInt(hex[i], 16)
            // 追加成string
            string.append(data.toChar())
        }

        return string.toString()
    }
}
