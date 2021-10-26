/**
 * projectName: apiobject-framwork
 * fileName: GlobalVariables.java
 * packageName: com.apiobject.framework.global
 * date: 2021-06-19 下午2:25
 */
package com.api.framework.global;

import java.util.HashMap;

/**
 * @version: V1.0
 * @author: kuohai
 * @className: GlobalVariables
 * @packageName: com.apiobject.framework.global
 * @description: 从来存储全局变量
 * @data: 2021-06-19 下午2:25
 **/
public class GlobalVariables {
    static private HashMap<String,String> globalVariables = new HashMap<>();


    public static HashMap<String, String> getGlobalVariables() {
        return globalVariables;
    }
    public static void setGlobalVariables(HashMap<String, String> globalVariables) {
        GlobalVariables.globalVariables = globalVariables;
    }

}