
package Controller;
import Model.*;
import Database.Database;
import java.io.*;
import java.util.ArrayList;

public class File_handler implements Serializable {
    static ArrayList<Tenant> Tenants_list = new ArrayList<>();
    static ArrayList<RECIEPT> Reciept_list = new ArrayList<>();
    static ArrayList<String[]> Vacant_list = new ArrayList<>();



    public void create_tenant() {
        File T_file = new File("src/Data/TENANT.txt");
        try {
            if (T_file.createNewFile()) {
                System.out.println("Tenant file created succesfully!!");
            }
        } catch (IOException e) {
            System.out.println("Error while creating file");
        }
    }

    public void backup_of_tenants() {
        try {
            FileWriter T_file = new FileWriter("src/Data/TENANT.txt");
            T_file.write("-------------backup data of all tenant---------------  ");
            T_file.write("tenants upto this date are:" + java.time.LocalDate.now());

            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                Tenant t = Database.Tenants_list.get(i);
                T_file.write("Tenant#" + (i + 1) + "\t");
                T_file.write("Name:" + t.getName() + "\t");
                T_file.write("CNIC:" + t.getCnic() + "\t");
                T_file.write("Phone#" + t.getPhone_no() + "\t");
                T_file.write("Flat no:" + t.getFlat_no() + "\t");
                T_file.write("Rent:" + t.getRent() + "\t");
                T_file.write("Dues:" + t.getDues() + "\t");
                T_file.write("Type:" + t.getType() + "\t");
                T_file.write("Join date:" + t.getJoinDate() + "\t");
                T_file.write("Active:" + t.isActive() + "\t");
            }
            System.out.println("--------------------------------");
            T_file.close();
        } catch (IOException e) {
            System.out.println("Error while taking backup of tenants:");
        }
    }


    public void create_Recieptfile() {
        File R_file = new File("src/Data/RECIEPT.txt");
        try {
            if (R_file.createNewFile()) {
                System.out.println("Reciept file created succesfully....");
            }
        } catch (IOException e) {
            System.out.println("Error while creating Reciept file..");
        }
    }

    public void backup_of_receipts() {
        try {
            FileWriter R_file = new FileWriter("src/Data/RECIEPT.txt");

            R_file.write("------------- RECEIPT BACKUP ----------------\n");
            R_file.write("Date: " + java.time.LocalDate.now() + "\n\n");
            for (int i = 0; i < Database.Reciept_list.size(); i++) {
                RECIEPT r = Database.Reciept_list.get(i);
                R_file.write("Receipt #" + r.getRecieptNo() + "\n");
                R_file.write("Tenant name:" + r.getTenantName() + "\n");
                R_file.write("Flat#" + r.getFlatNo() + "\n");
                R_file.write("Amount:" + r.getAmount() + "\n");
                R_file.write("Type of payment:" + r.getPaymentType() + "\n");
                R_file.write("Date:" + r.getDate() + "\n");
                R_file.write("-----------------------------------\n");
            }

            R_file.close();
            System.out.println("Receipts are backed up successfully!!");
        } catch (IOException e) {
            System.out.println("Error while backing up receipts: " + e.getMessage());
        }
    }

    public void create_vacanciesFile() {
        File V_file = new File("src/Data/Vacant.txt");
        try {
            if (V_file.createNewFile()) {
                System.out.println("vacancies file created succesfully!!");
            }
        } catch (IOException e) {
            System.out.println("Error while creating Vacant file");
        }
    }

    public void backup_vacancies() {
        try {
            FileWriter V_file= new FileWriter("src/Data/Vacant.txt");

            V_file.write("========== VACANT FLATS BACKUP ==========\n");
            V_file.write("Date: " + java.time.LocalDate.now() + "\n\n");

            for (int i = 0; i < Database.Vacant_list.size();i++) {
                String[] v = Database.Vacant_list.get(i);
                V_file.write("Vacant Flat #" + (i + 1) + "\t");
                V_file.write("Flat Number: " + v[0] + "\t");
                V_file.write("Previous Tenant: "+v[1] + "\t");
                V_file.write("CNIC: " + v[2] + "\t");
                V_file.write("Vacated Date: " + v[3] + "\t");
                V_file.write("-----------------------------------\n");
            }

            V_file.close();
            System.out.println("Vacancies backed up successfully!!");
        } catch (IOException e) {
            System.out.println("Error while backing up vacancies: " + e.getMessage());
        }
    }
}
















