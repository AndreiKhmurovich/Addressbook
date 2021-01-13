package ts.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ts.model.ContactData;
import ts.model.Contacts;
import ts.model.GroupData;
import ts.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contact = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contact.stream().map((c) -> new Object[]{c}).collect(Collectors.toList()).iterator();
        }
    }


    @BeforeMethod()
    public void ensurePreconditions(){
        // Check if group is present (group is needed for create a contact), if not - creates one
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("MyNewGroup"));
        }
        app.goTo().homePage();
        // Check if Contact is present, if not - creates one
        if (app.db().contacts().size() == 0) {
            Groups allGroups = app.db().groups();
            app.contact().create(new ContactData().withfName("Jack").withlName("Levine")
                    .withNick("Jacks").withTitle("Mr.").withCompany("LevinS inc")
                    .withAddress("205 Riverside str.").withHomePhone("9123-123-4567").withMobilePhone("234-5656")
                    .withWorkPhone("454-53434").withFax("343-43434").withEmail("levinjack@gmail.com")
                    .withEmail2("levins@gmail.com").withBday("1").withbMonth("April").withbYear("1990")
                    .withaDay("1").withaMonth("April").withaYear("2020").inGroup(allGroups.iterator().next()), true);
        }
        app.goTo().homePage();

    }


    @Test(dataProvider = "validContactsFromJson")
    public void contactModificationTests(ContactData contact) {
        Groups allGroups = app.db().groups();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        app.contact().modifyContact(contact.withId(modifiedContact.getId()));
        app.goTo().homePage();

        before.remove(modifiedContact);
        before.add(contact);
        Contacts after = app.db().contacts();
        Assert.assertEquals(before.size(), after.size());
        assertThat(before, equalTo(after));


    }

}
