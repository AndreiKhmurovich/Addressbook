package ts.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ts.model.ContactData;
import ts.model.Contacts;
import ts.model.GroupData;
import ts.model.Groups;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddToGroupTest extends TestBase {

    ContactData contactToAdd;

    @BeforeMethod
    public void ensurePreconditions() {

        // Check if Contact is present, if not - creates one
        if (app.db().contacts().size() == 0) {
            ContactData newContact = new ContactData().withfName("Jack").withlName("Levine")
                    .withNick("Jacks").withTitle("Mr.").withCompany("LevinS inc")
                    .withAddress("205 Riverside str.").withHomePhone("9123-123-4567").withMobilePhone("234-5656")
                    .withWorkPhone("454-53434").withFax("343-43434").withEmail("levinjack@gmail.com")
                    .withEmail2("levins@gmail.com").withBday("1").withbMonth("April").withbYear("1990")
                    .withaDay("1").withaMonth("April").withaYear("2020");
            app.contact().create(newContact, true);
        }
        app.goTo().homePage();

        // Check if group is present, if not - creates one
        // Check if contact is present in all existing groups, if yes - create new Group
        contactToAdd = app.db().contacts().iterator().next();
        app.goTo().groupPage();
        if (app.db().groups().size() == 0 || contactToAdd.getGroups().size() == app.db().groups().size()) {
            app.group().create(new GroupData().withName("Group " + String.format("%1$ty%1$tm%1$td%1$tH%1$tM", LocalDateTime.now())).withHeader("GroupHeader").withFooter("GroupFooter"));
        }
        app.goTo().homePage();
    }

    @Test
    public void contactAddToGroupTest() {
        app.goTo().homePage();
        GroupData groupToModify = new GroupData();
        Groups groups = app.db().groups();
        for (GroupData group: groups) {
            if (!contactToAdd.getGroups().contains(group)) {
                groupToModify = group;
            }
        }

        Contacts contactsBefore = app.db().group(groupToModify.getId()).getContacts();
        Groups groupsBefore = app.db().contact(contactToAdd.getId()).getGroups();
        app.contact().addContactToGroup(contactToAdd.getId(), groupToModify);
        Contacts contactsAfter = app.db().group(groupToModify.getId()).getContacts();
        Groups groupsAfter = app.db().contact(contactToAdd.getId()).getGroups();

        System.out.println("========================================");
        System.out.println("Contact with id - " + contactToAdd.getId() +
                ", last name - " + contactToAdd.getlName() + ", will be added to the - " + groupToModify.getName());
        System.out.println("List of (" + contactsBefore.size() + ") Contacts in " + groupToModify.getName() + " BEFORE modification: "
                + contactsBefore);
        System.out.println("List of (" + contactsAfter.size() + ") Contacts in " + groupToModify.getName() + " AFTER modification: "
                + contactsAfter);
        System.out.println("List of (" + groupsBefore.size() + ") Groups in Contact: id - " + contactToAdd.getId() + " last name - " + contactToAdd.getlName() + " BEFORE modification: "
                + groupsBefore);
        System.out.println("List of (" + groupsAfter.size() + ") Groups in Contact: id - " + contactToAdd.getId() + " last name - " + contactToAdd.getlName() + " AFTER modification: "
                + groupsAfter);

        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() + 1));
        assertThat(contactsAfter, equalTo(contactsBefore.withAdded(contactToAdd)));
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() + 1));
        assertThat(groupsAfter, equalTo(groupsBefore.withAdded(groupToModify)));

    }
}
