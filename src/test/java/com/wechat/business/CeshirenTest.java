package com.wechat.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.api.framework.global.ApiLoader;
import com.api.framework.steps.CaseDataModel;
import com.api.framework.testcase.ApiTestCaseModel;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CeshirenTest {
    public static final Logger log = LoggerFactory.getLogger(CeshirenTest.class);

    //统计当前模块执行用例数
    static int caseNum = 0;

    @BeforeAll
    static void loadApi(){
        ApiLoader.load("src/test/resources/api");
        log.info("Done!");
    }

    /**
     * 获取当前类名称
     */
    public static String getClassName(){
        String className = Thread.currentThread().getStackTrace()[1].getClassName();
        String[] split = className.split("\\.");
        String res = split[split.length - 1];

        return res;
    }

    /**
     * 获取测试用例数据
     */
    public static List<CaseDataModel> getTestCaseData() throws IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference<List<CaseDataModel>> typeReference = new TypeReference<List<CaseDataModel>>() {
        };
        List<CaseDataModel> data = mapper.readValue(CaseDataModel.class.getResourceAsStream("/casedata/" + getClassName() + "Data.yaml"), typeReference);

        return data;
    }

    /**
     * 测试方法封装主体内容
     * @param caseDataModel
     * @param methodName
     */
    public static void startMethod(CaseDataModel caseDataModel, String methodName) {
        //获取用例数据
        List<LinkedHashMap<String, Object>> data = caseDataModel.getData();
        log.info("当前用例测试执行方法为{ " + methodName + " }");
        data.forEach(ele->{
            if(ele.containsKey(methodName)){
                ArrayList<HashMap<String,String>> caseDataList = (ArrayList<HashMap<String,String>>)ele.get(methodName);
                log.info("caseDataList"+caseDataList);
                caseNum += caseDataList.size();
                caseDataList.forEach(caseData->{
                    log.info("第{ " + caseData.get("num") + " }条测试用例开始执行");
                    ApiTestCaseModel apiTestCaseModel = null;
                    try {
                        apiTestCaseModel = ApiTestCaseModel.load("src/test/resources/testcase/" + methodName + ".yaml");
                        log.info("apiTestCaseModel="+apiTestCaseModel);
                        apiTestCaseModel.run(caseData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        });
        log.info("方法{ " + methodName + " }测试用例执行结束");
    }

    @Description("测试人项目测试")
    @Story("创建部门需求")
    @DisplayName("创建功能")
    @ParameterizedTest
    @MethodSource("getTestCaseData")
    void createDeparmentMethod(CaseDataModel caseDataModel) throws IOException {
        //获取当前方法名称
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        startMethod(caseDataModel,methodName);
        log.info("debbuger!");
    }

}
