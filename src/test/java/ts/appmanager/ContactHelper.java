package ts.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ts.model.ContactData;
import ts.model.Contacts;
import ts.model.GroupData;

import java.util.ArrayList;
import java.util.List;

import static ts.tests.TestBase.app;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void submitContactCreation() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getfName());
        type(By.name("middlename"), contactData.getmName());
        type(By.name("lastname"), contactData.getlName());
        type(By.name("nickname"), contactData.getNick());
        type(By.name("title"), contactData.getTitle());
        type(By.name("company"), contactData.getCompany());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("fax"), contactData.getFax());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());

        //attach(By.name("photo"), contactData.getPhoto());

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }

    type(By.name("notes"),contactData.

    getNotes());
}
/*
    public void getContactInfo(ContactData contactData) {
        contactData.setfName(getText(By.name("firstname")));
        contactData.setmName(getText(By.name("middlename")));
        contactData.setlName(getText(By.name("lastname")));
        contactData.setNick(getText(By.name("nickname")));
        contactData.setTitle(getText(By.name("title")));
        contactData.setCompany(getText(By.name("company")));
        contactData.setAddress(getText(By.name("address")));
        contactData.setHomePhone(getText(By.name("home")));
        contactData.setMobilePhone(getText(By.name("mobile")));
        contactData.setWorkPhone(getText(By.name("work")));
        contactData.setFax(getText(By.name("fax")));
        contactData.setEmail(getText(By.name("email")));
        contactData.setEmail2(getText(By.name("email2")));
    }*/


/*        wd.findElement(By.xpath("//select[@name='bday']")).click();
        new Select(wd.findElement(By.xpath("//select[@name='bday']"))).selectByVisibleText(contactData.getBday());
        wd.findElement(By.xpath("//option[@value='" + contactData.getBday() + "']")).click();
        wd.findElement(By.xpath("//select[@name='bmonth']")).click();
        new Select(wd.findElement(By.xpath("//select[@name='bmonth']"))).selectByVisibleText(contactData.getbMonth());
        wd.findElement(By.xpath("//option[@value='" + contactData.getbMonth() + "']")).click();
        wd.findElement(By.xpath("//input[@name='byear']")).click();
        wd.findElement(By.name("byear")).clear();
        wd.findElement(By.name("byear")).sendKeys(contactData.getbYear());
        wd.findElement(By.xpath("//select[@name='aday']")).click();
        new Select(wd.findElement(By.xpath("//select[@name='aday']"))).selectByVisibleText(contactData.getaDay());
        wd.findElement(By.xpath("(//option[@value='" + contactData.getaDay() + "'])[2]")).click();
        wd.findElement(By.xpath("//select[@name='amonth']")).click();
        new Select(wd.findElement(By.xpath("//select[@name='amonth']"))).selectByVisibleText(contactData.getaMonth());
        wd.findElement(By.xpath("(//option[@value='" + contactData.getaMonth() + "'])[2]")).click();

        type(By.xpath("//input[@name='ayear']"), contactData.getaYear());*/

    public void initContactModification(ContactData contact) {
        wd.findElement(By.xpath(String.format("//a[contains(@href, 'edit.php?id=%s')]", contact.getId()))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    private void selectGroupByIdFromList(GroupData groupData) {
        app.goTo().homePage();
        new Select(wd.findElement(By.xpath("//select[@name='group']"))).selectByVisibleText(groupData.getName());

    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));

    }

    public void create(ContactData contact, boolean creation) {
        initContactCreation();
        fillContactForm(contact, creation);
        submitContactCreation();
    }


    public void delete(int index) {
        selectContact(index);
        deleteContact();
        app.goTo().acceptAlert();
        app.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.msgbox")));
        app.goTo().homePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        app.goTo().acceptAlert();
        app.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.msgbox")));
        app.goTo().homePage();
    }

    public void modifyContact(int index, ContactData contact) {
        selectContact(index);
        initContactModification(contact);
        fillContactForm(contact, false);
        submitContactModification();
    }

    public void modifyContact(ContactData contact) {
        selectContactById(contact.getId());
        initContactModification(contact);
        fillContactForm(contact, false);
        submitContactModification();

    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        WebElement table = wd.findElement(By.id("maintable"));
        List<WebElement> rows = table.findElements(By.name("entry"));
        for (WebElement we : rows) {
            List<WebElement> cells = we.findElements(By.tagName("td"));
            String lName = cells.get(1).getText();
            String fName = cells.get(2).getText();
            int id = Integer.parseInt(wd.findElement(By.name("selected[]")).getAttribute("id"));
            ContactData contact = new ContactData().withId(id).withfName(fName).withlName(lName);
            contacts.add(contact);
        }
        return contacts;
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        WebElement table = wd.findElement(By.id("maintable"));
        List<WebElement> rows = table.findElements(By.name("entry"));
        for (WebElement we : rows) {
            List<WebElement> cells = we.findElements(By.tagName("td"));
            int id = Integer.parseInt(we.findElement(By.cssSelector("input[name='selected[]']")).getAttribute("id"));
            String lName = cells.get(1).getText();
            String fName = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            String address = cells.get(3).getText();
            ContactData contact = new ContactData().withId(id).withfName(fName).withlName(lName).withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address);
            contacts.add(contact);
        }
        return contacts;
    }


    public ContactData infoFromEditForm(ContactData contact) {
        initContactModification(contact);
        String fName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withfName(fName).withlName(lName)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withAddress(address);
    }

    public void addContactToGroup(int contactId, GroupData groupData) {
        selectContactById(contactId);
        new Select(wd.findElement(By.xpath("//select[@name='to_group']"))).selectByVisibleText(groupData.getName());
        click(By.name("add"));
    }


    public void deleteContactFromGroup(int contactId, GroupData groupData) {
        selectGroupByIdFromList(groupData);
        selectContactById(contactId);
        wd.findElement(By.xpath("//*[@name = 'remove']")).click();
    }


}







