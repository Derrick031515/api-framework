/**
 * projectName: apiobject-framwork
 * fileName: StepModel.java
 * packageName: com.apiobject.framework.steps
 * date: 2021-06-19 下午4:07
 */
package com.api.framework.steps;

import com.api.framework.global.ApiLoader;
import com.api.framework.global.GlobalVariables;
import com.api.framework.utils.PlaceholderUtils;
import io.restassured.response.Response;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * @version: V1.0
 * @author: Derrick
 * @className: StepModel
 * @packageName: com.apiobject.framework.steps
 * @description: step对象
 * @data: 2021-06-19 下午4:07
 **/
@Data
public class StepModel {
    public static final Logger logger = LoggerFactory.getLogger(StepModel.class);

    private String api;
    private String action;
    private ArrayList<String> actualParameter;
    private ArrayList<AssertModel> asserts;
    private HashMap<String,String> save;
    private HashMap<String,String> saveGlobal;

    private ArrayList<String> finalActualParameter =  new ArrayList<>();
    private HashMap<String,String> stepVariable = new HashMap<>();
    private StepResult stepResult =  new StepResult();
    private ArrayList<Executable> assertList = new ArrayList<>();

    public StepResult run(HashMap<String, String> testCaseVariables, String apiName){

        /**
         * 1、实参占位符替换
         */
        if(actualParameter != null){
            finalActualParameter.addAll(PlaceholderUtils.resolveList(actualParameter,testCaseVariables));
        }

        /**
         * 2、执行action
         */
        Response response = ApiLoader.getAction(api,action).run(finalActualParameter,apiName);

        /**
         * 3、存save
         */
        if(save !=null){
            save.forEach((variablesName,path)->{
                String value = response.jsonPath().get("errcode")+"";
                stepVariable.put(variablesName,value);
                logger.info("step变量更新： "+stepVariable);
            });
        }
        /**
         * 4、存saveGloble
         */
        if(saveGlobal !=null){
            saveGlobal.forEach((variablesName,path)->{
                String value = response.path(path.toString())+"";
                GlobalVariables.getGlobalVariables().put(variablesName,value);
                logger.info("全局变量更新： "+GlobalVariables.getGlobalVariables());
            });
        }

        /**
         * 5、存储断言结果
         */
        if(asserts != null){
            String actual = testCaseVariables.get(apiName+"_actual");
            String matcher = testCaseVariables.get(apiName+"_matcher");
            String expect = testCaseVariables.get(apiName+"_expect");

            if(matcher.equals("contains")){
                asserts.stream().forEach(assertModel -> {
                    assertList.add(()->{assertThat(assertModel.getReason(),response.jsonPath().get(actual),containsString(expect));});
                });
            }
            asserts.stream().forEach(assertModel -> {
                assertList.add(()->{assertThat(assertModel.getReason(),response.jsonPath().get(actual),equalTo(Integer.parseInt(expect)));});
            });
        }

        /**
         * 8、组装result
         */
        stepResult.setAssertList(assertList);
        stepResult.setStepVariables(stepVariable);
        return  stepResult;
    }
}