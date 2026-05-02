import java.io.Serializable;

public class Person implements Serializable {
    private static final long serialVersionUID=1L;
    private String name;
    private String cnic;
    private String phone_no;

    public Person() {

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


}










