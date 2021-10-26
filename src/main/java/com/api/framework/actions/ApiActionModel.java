/**
 * projectName: apiobject-framwork
 * fileName: ApiActionModel.java
 * packageName: com.apiobject.framework.actions
 * date: 2021-06-19 下午2:07
 */
package com.api.framework.actions;

import com.api.framework.global.GlobalVariables;
import com.api.framework.utils.PlaceholderUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @version: V1.0
 * @author: Derrick
 * @className: ApiActionModel
 * @packageName: com.apiobject.framework.actions
 * @description: 接口动作对象
 * @data: 2021-06-19 下午2:07
 **/
@Data
public class ApiActionModel {
    private String method="get";
    private String url;
    private String body;
    private String contentType;
    private HashMap<String,String> query;
    private HashMap<String,String> headers;
    private String post;
    private String get;
    private Response response;
    private ArrayList<String> formalParam;
    private HashMap<String,String> actionVariables = new HashMap<>();

    public Response run(ArrayList<String> actualParameter){
        String runUrl =this.url;
        String runBody = this.body;
        HashMap<String,String> finalQuery = new HashMap<>();
        /**
         * 1、确定请求方法和URL
         */
        if(post!=null){
            runUrl=post;
            method = "post";
        }else if(get != null){
            runUrl = get;
            method ="get";
        }
        /**
         * 2、请求参数、URL中全局变量替换
         */
        if(query !=null){
            finalQuery.putAll(PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables()));
        }
        //body全局变量替换
        runBody = PlaceholderUtils.resolveString(runBody, GlobalVariables.getGlobalVariables());
        //url全局变量替换
        runUrl = PlaceholderUtils.resolveString(runUrl,GlobalVariables.getGlobalVariables());
        if(formalParam !=null &&actualParameter!=null && formalParam.size()>0 && actualParameter.size()>0){
            /**
             * 3、根据形参和实参构建内部变量MAP
             */
            for(int index =0; index<formalParam.size();index++){
                actionVariables.put(formalParam.get(index),actualParameter.get(index));
            }
            /**
             * 4、请求、URL中的内部变量进行替换
             */
            if(query !=null){
                finalQuery.putAll(PlaceholderUtils.resolveMap(query,actionVariables));
            }
            //body全局变量替换
            runBody = PlaceholderUtils.resolveString(runBody, actionVariables);
            //url全局变量替换
            runUrl = PlaceholderUtils.resolveString(runUrl,actionVariables);
        }
        /**
         * 5、拿到了上面完成了变量替换的请求数据，我们要进行请求并返回结果
         */
        RequestSpecification requestSpecification =given().log().all();
        if(contentType!=null){
            requestSpecification.contentType(contentType);
        }
        if(headers!=null){
            requestSpecification.headers(headers);
        }
        if(finalQuery!=null&&finalQuery.size()>0){
            requestSpecification.formParams(finalQuery);
        }else if(runBody !=null){
            requestSpecification.body(runBody);
        }
        response = requestSpecification.request(method,runUrl).then().log().all().extract().response();
        return response;
    }

}