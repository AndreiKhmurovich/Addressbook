package ts.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ts.appmanager.ApplicationManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {
    Logger logger = LoggerFactory.getLogger(TestBase.class);

    public static final ApplicationManager app = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));


    @BeforeSuite
    public void setUp() throws IOException {
        app.init();
    }

    @AfterSuite
    public void tearDown()  {
        app.stop();
    }

    public ApplicationManager getApp() {
        return app;
    }

    @BeforeMethod
    public void logStart(Method m, Object[] p){
        logger.info("Starting test: " + m.getName() + ", with parameters: " + Arrays.asList(p));
    }

    @AfterMethod
    public void logEnd(Method m, Object[] p){
        logger.info("Ending test: " + m.getName() + ", with parameters: " + Arrays.asList(p));
    }
}
