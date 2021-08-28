import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class WebAppTesting {

    private static AndroidDriver ad = null;

    private String port = "4723";
    private String server = "http://127.0.0.1";

    @BeforeClass
    public void setUp() throws MalformedURLException {
        initDriver();
    }

    public AndroidDriver getDriver(){
        return ad;
    }

    private void initDriver() throws MalformedURLException {
        DesiredCapabilities ds = new DesiredCapabilities();
        ds.setCapability(MobileCapabilityType.DEVICE_NAME,"emulator-5554");
        ds.setCapability("platformName","android");
        ds.setCapability(MobileCapabilityType.PLATFORM_VERSION,"8.1.0");
        ds.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UIAutomator2");
        ds.setCapability(MobileCapabilityType.BROWSER_NAME,"Chrome");
        ds.setCapability(MobileCapabilityType.BROWSER_VERSION,"4.26.3");/*
        ds.setCapability("appPackage","com.example.myapplication");*/
        //ds.setCapability("appPackage",".swipelistview");
        //ds.setCapability("app","D:\\PROJECTS\\Appium automation\\Appium APK\\SwipeListView Demo_v1.13_apkpure.com.apk");
        ds.setCapability("app","D:\\PROJECTS\\Appium automation\\Appium APK\\SwipeListView Demo_v1.13_apkpure.com.apk");
        ds.setCapability("noReset",true);
        String url= server+":"+port+"/wd/hub";

        ad = new AndroidDriver(new URL(url), ds);

    }


    @Test
    public void test() throws InterruptedException{
        Set<String> contextNames = ad.getContextHandles();
        System.out.println(contextNames.size());
        for (String contextName:contextNames){
            System.out.println(contextName);
            if (contextName.contains("WEBVIEW")){
                ad.context(contextName);
            }
        }

        ad.get("http://facebook.com");
        ad.findElementByXPath("//email").click();
        ad.findElementByXPath("//email").sendKeys();
        ad.findElementByXPath("//pass").click();
        ad.findElementByXPath("//pass").sendKeys();

        ad.findElementByXPath("//login_button").click();

        Thread.sleep(10000);

        synchronized (ad){
            ad.wait();
        }
        ad.context("NATIVE_APP");
        ad.findElementById("com.android");

        ad.context((String)contextNames.toArray()[1]);

        ad.findElementByXPath("//search").click();
        ad.findElementByXPath("//search").sendKeys("appium"+"\n");


        if(ad.findElementByXPath(".//a").isDisplayed()==false){
            ad.findElementByXPath("//button").click();
        }

        Actions actions = new Actions(ad);
        actions.moveToElement(ad.findElement(By.xpath("//view"))).build().perform();
        ad.findElement(By.xpath("//view")).click();

        Thread.sleep(10000);


        System.out.println(ad.findElementByXPath("//like").getAttribute("class"));
        Assert.assertTrue(ad.findElementByXPath("//like").getAttribute("class").contains("active"));
    }

    @AfterClass
    public void teatDown(){
        ad.quit();
    }
}
