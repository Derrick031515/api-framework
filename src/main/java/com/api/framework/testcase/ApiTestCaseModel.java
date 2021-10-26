/**
 * projectName: apiobject-framwork
 * fileName: ApiTestCaseModel.java
 * packageName: com.apiobject.framework.testcase
 * date: 2021-06-19 下午5:17
 */
package com.api.framework.testcase;

import com.api.framework.steps.StepModel;
import com.api.framework.steps.StepResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * @version: V1.0
 * @author: kuohai
 * @className: ApiTestCaseModel
 * @packageName: com.apiobject.framework.testcase
 * @description: 用例对象
 * @data: 2021-06-19 下午5:17
 **/
@Data
public class ApiTestCaseModel {
    public static final Logger logger = LoggerFactory.getLogger(ApiTestCaseModel.class);

    private String name;
    private String description;
    private ArrayList<StepModel> steps;
    private ArrayList<Executable> assertList =  new ArrayList<>();
    private HashMap<String,String> testCaseVariables =  new HashMap<>();

    public static ApiTestCaseModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path), ApiTestCaseModel.class);
    }
    public void run(HashMap<String, String> caseData){
        /**
         * 1、加载用例层保留关键字变量
         */
//        this.testCaseVariables.put("getTimeStamp", FakerUtils.getTimeStamp());
        this.testCaseVariables = caseData;
        logger.info("用例变量更新： "+testCaseVariables);


        /**
         * 2、遍历执行step
         */
        steps.forEach(step->{
            //获取当前执行的api
            String apiName = step.getApi();
            StepResult stepResult =  step.run(testCaseVariables,apiName);
            /**
             * 3、处理save变量
             */
            if(stepResult.getStepVariables().size()>0){
                testCaseVariables.putAll(stepResult.getStepVariables());
                logger.info("testcase变量更新： " +testCaseVariables);
            }
            /**
             * 4、追加每次的断言
             */
            if(stepResult.getAssertList().size()>0){
                assertList.addAll(stepResult.getAssertList());
            }
        });
        /**
         * 5、统一断言
         */
        logger.info("开始统一断言");
        logger.info("断言数量为："+assertList.size());
        assertAll("",assertList.stream());
        logger.info("断言结束");
    }
}