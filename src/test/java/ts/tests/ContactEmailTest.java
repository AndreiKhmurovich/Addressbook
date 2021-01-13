package ts.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ts.model.ContactData;
import ts.model.GroupData;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTest extends TestBase {
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
            app.contact().create(new ContactData().withfName("Vasili").withmName("Petrovich").withlName("Vasilevski").withNick("Ivanych").withTitle("Tovarisch")
                    .withCompany("Tovarishchestvo Tovarishchei").withAddress("Mostovaja str. 2, Moscow").withHomePhone("9123-123-4567").withMobilePhone("234-5656")
                    .withWorkPhone("454-53434").withFax("343-43434").withEmail("ivanych@mail.ru").withEmail2("tovarishchivan@mail.ru").withBday("1").withbMonth("April")
                    .withbYear("1990").withaDay("1").withaMonth("April").withaYear("2020"), true);
            app.wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        app.goTo().homePage();
    }

    @Test
    public void contactEmailTest(){
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(),equalTo(mergeEmails(contactInfoFromEditForm)));
    }

    public String mergeEmails(ContactData contact){
        return
        Arrays.asList(contact.getEmail(), contact.getEmail2())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
