package org.gourd.hu.gateway.utils;

import org.gourd.hu.gateway.holder.SpringContextHolder;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.Map;

/**
 * 路径正则匹配工具类
 *
 * @author gourd.hu
 */
public class PathMatcherUtil {

    private static AntPathMatcher antPathMatcher;

    /**
     * 判断传入的path是否可以作为pattern使用
     *
     * @param path 访问路径
     * @return 是否可以
     */
    public static boolean isPattern(String path) {
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        return antPathMatcher.isPattern(path);
    }

    /**
     * 验证路径是否匹配
     *
     * @param pattern 正则
     * @param path      访问路径
     * @return 是否匹配
     */
    public static boolean match(String pattern, String path) {
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        return antPathMatcher.match(pattern,path);
    }

    /**
     * 批量验证路径是否匹配
     *
     * @param patterns 正则
     * @param path 访问路径
     * @return 是否匹配
     */
    public static boolean matches(Collection<String> patterns, String path) {
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        for (String pattern : patterns) {
            if(antPathMatcher.match(pattern,path)){
                return true;
            }
        }
        return false;
    }

    /**
     * 如名,是否开始部分匹配
     * @param pattern
     * @param path
     * @return
     */
    public static boolean matchStart(String pattern, String path){
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        return antPathMatcher.matchStart(pattern,path);
    }

    /**
     * 提取path中匹配到的部分,如pattern(myroot/*.html),path(myroot/myfile.html),返回myfile.html
     * @param pattern
     * @param path
     * @return
     */
    public static String extractPathWithinPattern(String pattern, String path){
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        return antPathMatcher.extractPathWithinPattern(pattern,path);
    }

    /**
     * 提取path中匹配到的部分,只是这边还需跟占位符配对为map,
     * 如pattern(/hotels/{hotel}),path(/hotels/1),解析出"hotel"->"1"
     * @param pattern
     * @param path
     * @return
     */
    public static Map<String, String> extractUriTemplateVariables(String pattern, String path){
        antPathMatcher = SpringContextHolder.getBean(AntPathMatcher.class);
        return antPathMatcher.extractUriTemplateVariables(pattern,path);
    }
}