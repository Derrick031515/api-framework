package com.api.framework.steps;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 解析测试用例Yaml数据，封装对象
 */
@Data
public class CaseDataModel {
    List<LinkedHashMap<String,Object>> data;
}
