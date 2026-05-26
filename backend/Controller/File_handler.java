
package Controller;
import Model.*;




import java.io.*;
import java.util.ArrayList;

public class File_handler implements Serializable {
    static ArrayList<Tenant> Tenants_list = new ArrayList<>();
    static ArrayList<RECIEPT> Reciept_list = new ArrayList<>();
    static ArrayList<String> Vacant_list = new ArrayList<>();

    public void create_tenant() {
        File T_file = new File("src/Data/TENANT.txt");
        try {
            if (T_file.createNewFile()) {
                System.out.println("Model.Tenant file created succesfully!!");
            }
        } catch (IOException e) {
            System.out.println("Error while creating file");
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

    public void create_vacanciesFile() {
        File T_file = new File("src/Data/Vacant.txt");
        try {
            if (T_file.createNewFile()) {
                System.out.println("vacancies file created succesfully!!");
            }
        } catch (IOException e) {
            System.out.println("Error while creating Vacant file");
        }
    }
}

















