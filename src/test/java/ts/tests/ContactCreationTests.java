package ts.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.*;
import ts.model.ContactData;
import ts.model.Contacts;
import ts.model.GroupData;
import ts.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

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


    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("My new group"));
        }
        app.goTo().homePage();
    }

    @Test(dataProvider = "validContactsFromJson")
    public void contactCreationTest(ContactData contact) {
        Groups allGroups = app.db().groups();
        app.goTo().homePage();
        //File photo = new File("src/test/resources/user6.jpg");
        Contacts before = app.db().contacts();
        app.contact().create(new ContactData().withfName(contact.getfName())
                .withlName(contact.getlName()).withHomePhone(contact.getHomePhone())
                .withPhoto(contact.getPhoto()).inGroup(allGroups.iterator().next()), true);
        app.goTo().homePage();
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() + 1));
        contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(contact)));
    }

}

