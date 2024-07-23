package base;

import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite
    public void setUp(){
        RestAssured.baseURI = "http://localhost:80";
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        int testStatus = result.getStatus();
        String methodName = result.getMethod().getMethodName();
        if(testStatus == ITestResult.SUCCESS){
            System.out.println(methodName+" Pass");
        }else if(testStatus == ITestResult.FAILURE){
            System.out.println(methodName+" Fail");
        }else if(testStatus == ITestResult.SKIP){
            System.out.println(methodName+" Skip");
        }


    }
}
