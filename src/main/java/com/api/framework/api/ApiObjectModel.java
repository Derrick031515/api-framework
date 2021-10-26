/**
 * projectName: apiobject-framwork
 * fileName: ApiObjectModel.java
 * packageName: com.apiobject.framework.api
 * date: 2021-06-19 下午3:31
 */
package com.api.framework.api;

import com.api.framework.actions.ApiActionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @version: V1.0
 * @author: kuohai
 * @className: ApiObjectModel
 * @packageName: com.apiobject.framework.api
 * @description: 接口对象类
 * @data: 2021-06-19 下午3:31
 **/
@Data
public class ApiObjectModel {
    private String name;
    private HashMap<String, ApiActionModel> actions;

    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiObjectModel.class);
    }
}