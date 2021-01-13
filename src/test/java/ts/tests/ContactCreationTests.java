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

/*        ContactData contact = new ContactData()
                .withfName("Vasili").withmName("Petrovich").withlName("Vasilevski").withNick("Ivanych").withTitle("Tovarisch").withCompany("Tovarishchestvo Tovarishchei")
                .withAddress("Mostovaja str. 2, Moscow").withHomePhone("9123-123-4567").withMobilePhone("234-5656").withWorkPhone("454-53434").withFax("343-43434")
                .withEmail("ivanych@mail.ru").withEmail2("tovarishchivan@mail.ru").withBday("1").withbMonth("April").withbYear("1990").withaDay("1")
                .withaMonth("April").withaYear("2020").withGroup("MyNewGroup");*/


        app.goTo().homePage();
        Contacts after = app.db().contacts();
        assertThat(after.size(), equalTo(before.size() + 1));


        // Finding max element (id) in contactList
        // Option 1 (using for loop)
/*              int max = 0;
                for (ContactData g : after) {
                    if (g.getId() > max) {
                        max = g.getId();
                    }
                }   */


        //Option 2 (using Comparator)
        // Option 2.1 (full implementation)
/*                  Comparator<? super ContactData> byId = new Comparator<ContactData>() {
                    @Override
                        public int compare(ContactData o1, ContactData o2) {
                        return Integer.compare(o1.getId(), o2.getId());
                        }
                    }
                    int max1 = after.stream().max(byId).get().getId();  */

        // Option 2.2 (using Lambda)
/*                  Comparator<? super ContactData> byId = (o1, o2) -> Integer.compare(o1.getId(), o2.getId());
                    int max1 = after.stream().max(byId).get().getId();  */

        // Option 2.2.1 (optimized)
        /*int max1 = after.stream().max((Comparator<ContactData>) (o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId();*/

        //contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        //contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
        contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        assertThat(after, equalTo(before.withAdded(contact)));
    }

}

