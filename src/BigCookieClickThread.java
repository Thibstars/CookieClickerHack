import navigation.Navigator;
import org.openqa.selenium.WebElement;

public class BigCookieClickThread extends Thread {

    private WebElement bigCookie;

    public BigCookieClickThread(WebElement bigCookie) {
        this.bigCookie = bigCookie;
    }

    @Override
    public void run() {
        while (bigCookie.isDisplayed() && bigCookie.isDisplayed()) {
            Navigator.getInstance().click(bigCookie);
        }
    }
}
