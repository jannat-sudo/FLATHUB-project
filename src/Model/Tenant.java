package Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Tenant extends Person implements Serializable{
    private String flat_no;
    private double rent;
    private double dues;
    private String type;
    private String JoinDate;
    private boolean Active;

    public Tenant() {
    }

    public Tenant(String name,String cnic,String phone,String flat_no,double rent,double dues,String type,String JoinDate,boolean Active) {
       super();
        this.flat_no = flat_no;
        this.rent=rent;
        this.dues=dues;
        this.type=type;
        this.JoinDate=JoinDate;
        this.Active=Active;
    }

    public String getFlat_no() {
        return flat_no;
    }

    public double getRent() {
        return rent;
    }

    public double getDues() {
        return dues;
    }

    public String getType() {
        return type;
    }

    public String getJoinDate() {
        return JoinDate;
    }

    public boolean isActive() {
        return Active;
    }

    public void setDues(double dues) {
        this.dues = dues;
    }
    public String time_Duration() {
        LocalDate start = LocalDate.parse(JoinDate);
        LocalDate Stop = LocalDate.now();
        Period p = Period.between(start, Stop);
        String duration = "";
        if (p.getYears() > 0) {
                   duration+="years:"+p.getYears();
        }
        if(p.getMonths()>0){
            duration=duration+"months:"+p.getMonths();
        }
        if(p.getDays()>0){
            duration=duration+"days:"+p.getDays();
        }
        return duration;
    }

    public void setFlat_no(String flat_no) {
        this.flat_no = flat_no;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setJoinDate(String joinDate) {
        JoinDate = joinDate;
    }

    public void setActive(boolean active) {
        Active = active;
    }


    @Override
    public String toString() {
        return "Model.Tenant{" +
                "flat_no='" + flat_no + '\'' +
                ", rent=" + rent +
                ", dues=" + dues +
                ", type='" + type + '\'' +
                ", JoinDate='" + JoinDate + '\'' +
                ", Active=" + Active +
                '}';
    }








}