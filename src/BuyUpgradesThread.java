import navigation.Navigator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import pages.Page;
import pages.Pages;
import pages.Section;

import java.util.ArrayList;
import java.util.List;

public class BuyUpgradesThread extends Thread {

    private List<WebElement> upgrades;

    private Section productSection;

    public BuyUpgradesThread() {
        Page homePage = Pages.getPage("/cookieclicker/");
        productSection = homePage.getSection("Upgrade Section");
        upgrades = new ArrayList<WebElement>();
        populateUpgrades();
    }

    private void populateUpgrades() {
        for (int i = 13; i >= 0; i--) {
            try {
                upgrades.add(productSection.getRoot().findElement(By.id("upgrade" + i)));
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            if (upgrades.size() <= 13) {
                populateUpgrades();
                buyUpgrades(upgrades);
            }
        }
    }

    private void buyUpgrades(List<WebElement> upgrades) {
        for (WebElement upgrade : upgrades) {
            if (upgrade.isDisplayed() && upgrade.isEnabled()
                    && upgrade.getAttribute("class").contains("enabled")) {
                Navigator.getInstance().click(upgrade);
            }
        }
    }
}
