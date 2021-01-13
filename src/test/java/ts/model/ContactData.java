package ts.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "addressbook")
public class ContactData {

    @Id
    @Column(name="id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name="firstname")
    private String fName = "";

    @Transient
    private String mName = "";

    @Expose
    @Column(name="lastname")
    private String lName = "";

    @Column(name="nickname")
    private String nick = "";

    private String title = "";
    private String company = "";

    @Type(type = "text")
    private String address = "";

    @Expose
    @Column(name="home")
    @Type(type = "text")
    private String homePhone = "";

    @Column(name="mobile")
    @Type(type = "text")
    private String mobilePhone = "";

    @Column(name="work")
    @Type(type = "text")
    private String workPhone = "";

    @Type(type = "text")
    private String fax = "";

    @Type(type = "text")
    private String email = "";

    @Type(type = "text")
    private String email2 = "";

    @Column(name = "bday", columnDefinition = "tinyint")
    private String bday = "0";

    @Column(name = "bMonth", columnDefinition = "tinyint")
    private String bMonth = "-";

    @Column(name = "bYear", columnDefinition = "tinyint")
    private String bYear = "";

    @Column(name = "aday", columnDefinition = "tinyint")
    private String aDay = "0";

    @Column(name = "aMonth", columnDefinition = "tinyint")
    private String aMonth = "-";

    @Column(name = "aYear", columnDefinition = "tinyint")
    private String aYear = "";

    @Transient
    private String notes = "";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="address_in_groups",
            joinColumns = @JoinColumn(name="id"), inverseJoinColumns = @JoinColumn(name="group_id"))
    private Set<GroupData> groups = new HashSet<GroupData>();

    @Transient
    private String allPhones = "";

    @Transient
    private String allEmails = "";

    @Column(name="photo")
    @Type(type = "text")
    private String photo = "";


    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getmName() {
        return mName;
    }

    public String getlName() {
        return lName;
    }

    public String getNick() {
        return nick;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getEmail2() {
        return email2;
    }

    public String getBday() {
        return bday;
    }

    public String getbMonth() {
        return bMonth;
    }

    public String getbYear() {
        return bYear;
    }

    public String getaDay() {
        return aDay;
    }

    public String getaMonth() {
        return aMonth;
    }

    public String getaYear() {
        return aYear;
    }

    public String getNotes() {
        return notes;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    public File getPhoto() {
        return new File(photo);
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withfName(String fName) {
        this.fName = fName;
        return this;
    }

    public ContactData withmName(String mName) {
        this.mName = mName;
        return this;
    }

    public ContactData withlName(String lName) {
        this.lName = lName;
        return this;
    }

    public ContactData withNick(String nick) {
        this.nick = nick;
        return this;
    }

    public ContactData withTitle(String title) {
        this.title = title;
        return this;
    }

    public ContactData withCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public ContactData withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public ContactData withFax(String fax) {
        this.fax = fax;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public ContactData withBday(String bday) {
        this.bday = bday;
        return this;
    }

    public ContactData withbMonth(String bMonth) {
        this.bMonth = bMonth;
        return this;
    }

    public ContactData withbYear(String bYear) {
        this.bYear = bYear;
        return this;
    }

    public ContactData withaDay(String aDay) {
        this.aDay = aDay;
        return this;
    }

    public ContactData withaMonth(String aMonth) {
        this.aMonth = aMonth;
        return this;
    }

    public ContactData withaYear(String aYear) {
        this.aYear = aYear;
        return this;
    }

    public ContactData withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", homePhone='" + homePhone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id &&
                Objects.equals(fName, that.fName) &&
                Objects.equals(lName, that.lName) &&
                Objects.equals(title, that.title) &&
                Objects.equals(company, that.company) &&
                Objects.equals(address, that.address) &&
                Objects.equals(homePhone, that.homePhone) &&
                Objects.equals(mobilePhone, that.mobilePhone) &&
                Objects.equals(workPhone, that.workPhone) &&
                Objects.equals(email, that.email) &&
                Objects.equals(email2, that.email2) &&
                Objects.equals(bday, that.bday) &&
                Objects.equals(bMonth, that.bMonth) &&
                Objects.equals(bYear, that.bYear) &&
                Objects.equals(aDay, that.aDay) &&
                Objects.equals(aMonth, that.aMonth) &&
                Objects.equals(aYear, that.aYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fName, lName, title, company, address, homePhone, mobilePhone, workPhone, email, email2, bday, bMonth, bYear, aDay, aMonth, aYear);
    }


    public ContactData inGroup(GroupData group) {
        groups.add(group);
        return this;
    }
}
