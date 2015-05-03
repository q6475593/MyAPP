package kelaodi.shenmesafe.domain;

/**
 * Created by Administrator on 2015/5/3.
 */
public class ContactInfo {


    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ContactInfo{" + "name='" + name + '\'' + ", phone='" + phone + '\'' + '}';
    }
}
