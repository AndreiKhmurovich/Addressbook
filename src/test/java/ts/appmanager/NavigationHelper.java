package ts.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))
                && wd.findElement(By.name("new")).getAttribute("value").equals("New group")) {

            return;
    }
      click(By.linkText("groups"));
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        wd.findElement(By.xpath("//*[contains(text(), 'home')]")).click();
    }

    public void acceptAlert() {
        alert = wd.switchTo().alert();
        alert.accept();
    }

}
