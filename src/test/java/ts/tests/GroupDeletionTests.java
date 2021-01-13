package ts.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ts.model.GroupData;
import ts.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("MyNewGroup").withHeader("MyNewGroupHeader").withFooter("MyNewGroupFooter"));
        }

    }

    @Test(enabled = false)
    public void deleteGroups(){
        app.goTo().groupPage();
        Groups all = app.group().all();
        for(int i = 0; i <= all.size() - 5; i++) {
            app.group().deleteGroupByIndex(0);
        }
    }

    @Test
    public void groupDeletionTest() {
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        //int index = before.size() - 1;
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), equalTo(before.size() - 1));
        Groups after = app.db().groups();
        before.remove(deletedGroup);
        assertThat(after, equalTo(before.without(deletedGroup)));
    }



}
