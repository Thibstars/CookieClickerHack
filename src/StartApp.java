import commands.InitFrameworkCommand;
import navigation.Browser;
import navigation.Navigator;
import navigation.NavigatorFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import pages.Page;
import pages.Pages;
import pages.Section;
import sut.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StartApp {

    public static void main(String[] args) {
        new InitFrameworkCommand().execute();
        NavigatorFactory.createChromeNavigator(new Environment("Cookie clicker", "http://orteil.dashnet.org/cookieclicker/"), false);
        Page homePage = new Page("/cookieclicker/") {};
        Pages.registerPage(homePage);

        WebElement sectionLeft = Navigator.getInstance().getDriver().findElement(By.id("sectionLeft"));
        homePage.addSection(new Section("Left Section", sectionLeft) {});

        WebElement sectionProducts = Navigator.getInstance().getDriver().findElement(By.id("products"));
        homePage.addSection(new Section("Product Section", sectionProducts) {});

        WebElement sectionUpgrades = Navigator.getInstance().getDriver().findElement(By.id("upgrades"));
        homePage.addSection(new Section("Upgrade Section", sectionUpgrades) {});

        //Accept cookies
        WebElement acceptCookiesMessage = Navigator.getInstance().getDriver().findElement(By.xpath("/html/body/div[1]/div/p"));
        ((JavascriptExecutor) Navigator.getInstance().getDriver()).executeScript(
                "arguments[0].parentNode.removeChild(arguments[0])", acceptCookiesMessage);
        WebElement acceptCookies = Navigator.getInstance().getDriver().findElement(By.xpath("/html/body/div[1]/div/a[1]"));
        Navigator.getInstance().explicitlyWaitForElementClickable(acceptCookies);
        Navigator.getInstance().click(acceptCookies);

        //TODO use following element to dismiss notifications
        //WebElement closeSideNote = Navigator.getInstance().getDriver().findElement(By.xpath("//*[@id='notes']/div[3]"));

        final WebElement bigCookie = sectionLeft.findElement(By.id("bigCookie"));

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 25; i++) {
            Thread bigCookieClickThread = new BigCookieClickThread(bigCookie);
            bigCookieClickThread.setPriority(Thread.MIN_PRIORITY);
            executor.execute(bigCookieClickThread);
        }

        Thread buyProductsThread = new BuyProductsThread();
        buyProductsThread.setPriority(Thread.MAX_PRIORITY);
        executor.execute(buyProductsThread);

        Thread buyUpgradesThread = new BuyUpgradesThread();
        buyUpgradesThread.setPriority(Thread.MAX_PRIORITY);
        executor.execute(buyProductsThread);
    }
}
