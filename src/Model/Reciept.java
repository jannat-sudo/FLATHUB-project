
package Model;
import java.io.Serializable;


    class RECIEPT extends Tenant implements Serializable {
        static int recieptNo=0;
        public RECIEPT(int recieptNo) {
         R_increment();
        }

        public RECIEPT() {
        }

        public static int R_increment(){
          return  recieptNo++;
        }

        public int getrecieptNo() {
            return recieptNo;
        }

        public void setrecieptNo() {
            this.recieptNo = recieptNo;
        }

        public RECIEPT(String name, String cnic, String phone, String flat_no, double rent, double dues, String type, String JoinDate, boolean Active) {
            super(name, cnic, phone, flat_no, rent, dues, type, JoinDate, Active);

        }
        public void GenerateReciept(int recieptNo,String date){
            System.out.println("-------Payment type=Monthly------");
            System.out.println("Reciept #"+getrecieptNo()+" , name="+getName()+" , flat#"+getFlat_no()+" , rent="+getRent()+" , Date="+date);
        }
    }



class Receipt extends RECIEPT implements Serializable {
    private double yearly_rent;

    public double getYearly_rent() {
        return yearly_rent;
    }

    public void setYearly_rent(double yearly_rent) {
        this.yearly_rent = yearly_rent;
    }

    public Receipt(String name, String cnic, String phone, String flat_no, double rent, double dues, String type, String JoinDate, boolean Active) {
        super(name, cnic, phone, flat_no, rent, dues, type, JoinDate, Active);
        this.yearly_rent = rent * 12;
    }

    @Override
    public void GenerateReciept(int recieptNo, String date) {
        System.out.println("--------Payment type=Yearly-----");
        System.out.println("Reciept #" + getrecieptNo() + " , name=" + getName() + " , flat#" + getFlat_no() + " , rent=" + getRent() + " , Date=" + date);

    }
}


















