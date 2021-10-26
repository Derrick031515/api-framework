/**
 * projectName: apiobject-framwork
 * fileName: StepResult.java
 * packageName: com.apiobject.framework.steps
 * date: 2021-06-19 下午4:15
 */
package com.api.framework.steps;

import lombok.Data;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @version: V1.0
 * @author: Derrick
 * @className: StepResult
 * @packageName: com.apiobject.framework.steps
 * @description: step run result
 * @data: 2021-06-19 下午4:15
 **/
@Data
public class StepResult {
    private ArrayList<Executable> assertList;
    private HashMap<String,String> stepVariables = new HashMap<>();
}