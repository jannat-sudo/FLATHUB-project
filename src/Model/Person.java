package Model;

import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private String cnic;
    private String phone_no;

    public Person() {

    }

    public Person(String name,String cnic,String phone_no) {
        this.name = name;
        this.cnic=cnic;
        this.phone_no=phone_no;
    }

    public String getName() {
        return name;
    }

    public String getcnic() {
        return cnic;
    }

 public String getPhone_no() {
        return phone_no;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}










