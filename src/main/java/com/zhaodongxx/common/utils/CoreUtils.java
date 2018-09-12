package com.zhaodongxx.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/3 15:30
 */
@Slf4j
public class CoreUtils {

    public static final char UNDERLINE = '_';

    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        log.debug("UUID {}", s);
        return s;
    }

    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    public static String getNowTime() {
        return (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(Calendar.getInstance().getTime());
    }

    /**
     * 驼峰字符串转下划线
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 排序类型适配器 方法
     *
     * @param sortOrder
     * @return "" || "ASC" || "DESC"
     */
    public static String sortOrderAdapter(String sortOrder) {
        if (sortOrder == null || "".equals(sortOrder.trim())) {
            return "";
        }
        sortOrder = sortOrder.toUpperCase();
        switch (sortOrder) {
            case "ASCEND":
                return "ASC";
            case "ASC":
                return "ASC";
            case "DESCEND":
                return "DESC";
            case "DESC":
                return "DESC";
            default:
                return "";
        }
    }

    public static String getOrderBy(String sortField, String sortOrder) {
        String field = CoreUtils.camelToUnderline(sortField);
        if (field != "") {
            String order = sortOrderAdapter(sortOrder);
            return (field + " " + order);
        }
        return "";
    }
}
