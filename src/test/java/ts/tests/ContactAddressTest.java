package ts.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ts.model.ContactData;
import ts.model.GroupData;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddressTest extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        // Check if group is present (group is needed for create a contact), if not - creates one
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData().withName("MyNewGroup"));
        }
        app.goTo().homePage();

        // Check if Contact is present, if not - creates one
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withfName("Jack").withlName("Levine")
                    .withNick("Jacks").withTitle("Mr.").withCompany("LevinS inc")
                    .withAddress("205 Riverside str.").withHomePhone("9123-123-4567").withMobilePhone("234-5656")
                    .withWorkPhone("454-53434").withFax("343-43434").withEmail("levinjack@gmail.com")
                    .withEmail2("levins@gmail.com").withBday("1").withbMonth("April").withbYear("1990")
                    .withaDay("1").withaMonth("April").withaYear("2020"), true);
            app.wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        app.goTo().homePage();
    }


    @Test
    public void contactAddressTest(){
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditPage = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditPage.getAddress()));

    }

}
