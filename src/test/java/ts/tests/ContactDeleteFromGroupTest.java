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

public class ContactDeleteFromGroupTest extends TestBase {
    ContactData contactToDelete;

    @BeforeMethod
    public void ensurePreconditions() {

        // Check if Contact is present, if not - creates one
        if (app.db().contacts().size() == 0) {
            ContactData newContact = new ContactData().withfName("Vasili").withmName("Petrovich").withlName("Vasilevski").withCompany("Tovarishchestvo Tovarishchei").withAddress("Mostovaja str. 2, Moscow").withHomePhone("9123-123-4567")
                    .withMobilePhone("234-5656").withWorkPhone("454-53434").withEmail("ivanych@mail.ru").withBday("1").withbMonth("April")
                    .withbYear("1990").withaDay("1").withaMonth("April").withaYear("2020");
            app.contact().create(newContact, true);
        }
        app.goTo().homePage();

        // Check if group is present, if not - creates one
        // Check if contact is not present in any group, if not - add Contact to the new Group
        contactToDelete = app.db().contacts().iterator().next();
        app.goTo().groupPage();
        if (app.db().groups().size() == 0 || contactToDelete.getGroups().size() == 0) {
            GroupData newGroup = new GroupData().withName("Group " + String.format("%1$ty%1$tm%1$td%1$tH%1$tM", LocalDateTime.now())).withHeader("GroupHeader").withFooter("GroupFooter");
            app.group().create(newGroup);
            app.contact().addContactToGroup(contactToDelete.getId(), newGroup);
        }
        app.goTo().homePage();
    }

    @Test
    public void contactDeleteFromGroupTest() {
        GroupData groupToModify = new GroupData();
        Groups groups = app.db().groups();
        for (GroupData group : groups) {
            if (contactToDelete.getGroups().contains(group)) {
                groupToModify = group;
            }
        }

        Contacts contactsBefore = app.db().group(groupToModify.getId()).getContacts();
        Groups groupsBefore = app.db().contact(contactToDelete.getId()).getGroups();

        app.contact().deleteContactFromGroup(contactToDelete.getId(), groupToModify);

        Contacts contactsAfter = app.db().group(groupToModify.getId()).getContacts();
        Groups groupsAfter = app.db().contact(contactToDelete.getId()).getGroups();

        System.out.println("========================================");
        System.out.println("Contact with id - " + contactToDelete.getId() +
                ", last name - " + contactToDelete.getlName() + ", will be deleted from the - " + groupToModify.getName());
        System.out.println("List of (" + contactsBefore.size() + ") Contacts in " + groupToModify.getName() + " BEFORE modification: "
                + contactsBefore);
        System.out.println("List of (" + contactsAfter.size() + ") Contacts in " + groupToModify.getName() + " AFTER modification: "
                + contactsAfter);
        System.out.println("List of (" + groupsBefore.size() + ") Groups in Contact: id - " + contactToDelete.getId() + " last name - " + contactToDelete.getlName() + " BEFORE modification: "
                + groupsBefore);
        System.out.println("List of (" + groupsAfter.size() + ") Groups in Contact: id - " + contactToDelete.getId() + " last name - " + contactToDelete.getlName() + " AFTER modification: "
                + groupsAfter);

        assertThat(contactsAfter.size(), equalTo(contactsBefore.size() - 1));
        assertThat(contactsAfter, equalTo(contactsBefore.without(contactToDelete)));
        assertThat(groupsAfter.size(), equalTo(groupsBefore.size() - 1));
        assertThat(groupsAfter, equalTo(groupsBefore.without(groupToModify)));
    }

}
