# api-framework

## 一、GitHub链接

项目网址链接：https://github.com/Derrick031515/api_framework.git

## 二、Api AutoTesting Framework 结构图

封装的底层框架用到Java+RestAssured+Har+Junit5+Log4j+Allure，支持HarToCase，即API Copy all as Har生成har配置文件，根据配置文件自动转化成测试用例配置文件，根据配置文件【包括业务和用例】自动解析、生成并执行测试用例，且用例实现参数化。在框架封装基础之上，仅关注业务开发。<br />

![image-20211026173848852](/Users/hupo/Library/Application Support/typora-user-images/image-20211026173848852.png)

- main
  - java
    - actions
      - **ApiActionModel**：封装API Action对象类
    - api
      - **ApiObjectModel**：封装API对象类
    - global
      - **ApiLoader**：封装API对象加载器对象类
      - **GlobalVariables**：封装API执行结果全局变量存储对象类
    - steps
      - **AssertModel**：封装测试用例断言对象类
      - **CaseDataModel**：封装测试用例参数化数据对象类
      - **StepModel**：封装测试用例执行步骤对象类
      - **StepResult**：封装测试用例执行结果对象类
    - testcase
      - **ApiTestCaseModel**：封装测试用例对象类
    - util
      - **FakerUtils**：封装数据伪造工具类
      - **PlaceholderUtils**：封装配置文件或模板中的占位符替换工具类
  - resources
    - **log4j2.xml**：log4j2的配置文件、控制台输出和文件滚动输出
- test
  - java
    - business
      - **CeshirenTest**：项目业务模块
  - resources
    - api
      - **department.yaml**：项目业务模块API actions配置
    - casedata
      - **CeshirenTestData.yaml****：项目业务模块用例参数化数据
    - har
      - **departmentHar.yaml****：项目业务用例Har配置
    - schema
      - **createDepartment.json****：项目业务用例断言配置，支持接口所有字段断言设置
    - testcase
      - **createDepartment.yaml**：项目业务测试用例Api配置
- **pom.xml**：Maven配置文件
  <a name="YoWRl"></a>

## 三、企业微信项目开发示例

1、项目需求描述

针对企业微信通讯录模块完成部门CRUD接口测试

2、具体实现

（1）设计通讯录模块接口测试类，实现通讯录模块初始化、加载模块配置文件进行用例自动解析、生成并参数化形式执行、最后统一断言【src/test/java/com/wechat/business/WeChatTest.java】

（2）设计通讯录模块接口配置【src/test/resources/api/createDepartment.yaml】

（3）设计通讯录模块接口配置，完成用例步骤设计【src/test/resources/testcase/createDeparment.yaml】

（4）设计通讯录模块接口断言配置【src/test/resources/schema/createDepartment.json】

（5）设计通讯录模块接口用例参数化配置【src/test/resources/casedata/WeChatTestData.yaml】

（6）执行命令，生成报告【 #mvn clean test -Dtest=WeChatTest】

