import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.Page;
import pages.Pages;
import pages.Section;

import java.util.ArrayList;
import java.util.List;

public class BuyProductsThread extends Thread {

    private List<WebElement> products;

    public BuyProductsThread() {
        Page homePage = Pages.getPage("/cookieclicker/");
        Section productSection = homePage.getSection("Product Section");
        products = new ArrayList<WebElement>();
        for (int i = 13; i >= 0; i--) {
            products.add(productSection.getRoot().findElement(By.id("product" + i)));
        }
    }

    @Override
    public void run() {
        while (true) {
            buyProducts(products);
        }
    }

    private static void buyProducts(List<WebElement> products) {

        for (WebElement product : products) {
            if (product.isDisplayed() && product.isEnabled()
                    && product.getAttribute("class").contains("enabled")) {
                Navigator.getInstance().click(product);
            }
        }
    }
}
