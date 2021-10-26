package com.wechat.testcase;

import com.wechat.business.CeshirenTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({CeshirenTest.class})
public class TestCaseMain {
}
