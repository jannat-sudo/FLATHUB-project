
package Model;

import java.io.Serializable;

public class RECIEPT implements Serializable{
    private int recieptNo;
    private String tenantName;
    private String PaymentType;
    private double amount;
    private String FlatNo;
    private String Date;
    
    public RECIEPT(int recieptNo,String tenantName,String PaymentType,double amount,String FlatNo,String Date) {
        this.recieptNo = recieptNo;
        this.tenantName=tenantName;
        this.PaymentType=PaymentType;
        this.amount=amount;
        this.FlatNo=FlatNo;
        this.Date=Date;
    }
    public void Monthly_reciept(){
        System.out.println("----------Payment type=monthly---------");
        System.out.println("Reciept no:"+recieptNo+"      Tenant:"+tenantName+"         Flat#"+FlatNo+"      type:"+PaymentType+"        Amount:"+amount+"     date:"+Date);
    }
    public void yearly_reciept(){
        System.out.println("----------Payment type=yearly----------");
        System.out.println("Reciept no:"+recieptNo+"      Tenant:"+tenantName+"         Flat#"+FlatNo+"      type:"+PaymentType+"        Amount:"+amount*12+"     date:"+Date);
    }

    public int getRecieptNo() {
        return recieptNo;
    }

    public void setRecieptNo(int recieptNo) {
        this.recieptNo = recieptNo;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPaymentType() {
        return PaymentType;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFlatNo() {
        return FlatNo;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setFlat_no(String flatNo) {
        this.FlatNo=flatNo;
    }

    public void setPayment_type(String paymentType) {
        this.PaymentType=paymentType;
    }
}



















