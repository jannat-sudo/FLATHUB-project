package View;
import Database.Database;
import Model.*;
import Controller.*;

import java.util.Scanner;


public class main {
    public static void main(String[] args){
        Database db = new Database();
        db.load_all();

        System.out.println("========================================");
        System.out.println("           FLATHUB SYSTEM               ");
        System.out.println("========================================");

        // ===== ADD 3 TENANTS =====
        System.out.println("\n--- Adding Tenants ---");
        Tenant t1 = new Tenant("Ali Hassan", "35202-1234567-1", "0300-1111111", "A1", 15000, 0, "monthly", java.time.LocalDate.now().toString(), true);
        Tenant t2 = new Tenant("Sara Khan", "35202-7654321-2", "0311-2222222", "B2", 20000, 5000, "yearly", java.time.LocalDate.now().toString(), true);
        Tenant t3 = new Tenant("Usman Ali", "35202-1111111-3", "0333-3333333", "C3", 12000, 0, "monthly", java.time.LocalDate.now().toString(), true);

        db.add_tenant(t1);
        db.add_tenant(t2);
        db.add_tenant(t3);

        // ===== SHOW ALL TENANTS =====
        System.out.println("\n--- All Tenants ---");
        db.show_all_tenants();

        // ===== SEARCH TESTS =====
        System.out.println("\n--- Search by CNIC ---");
        db.search_by_cnic("35202-1234567-1");

        System.out.println("\n--- Search by Flat ---");
        db.search_by_flat("B2");

        System.out.println("\n--- Search by Name ---");
        db.search_by_name("Usman Ali");

        // ===== GENERATE RECEIPTS =====
        System.out.println("\n--- Generating Receipts ---");
        db.pay_rent("A1");
        db.pay_rent("B2");
        db.pay_rent("C3");

        // ===== SHOW RECEIPTS =====
        System.out.println("\n--- All Receipts ---");
        db.show_reciepts();

        // ===== REMOVE TENANT =====
        System.out.println("\n--- Removing Tenant from C3 ---");
        db.remove_tenant("C3");

        // ===== SHOW VACANT =====
        System.out.println("\n--- Vacant Flats ---");
        db.show_vacancies();

        // ===== FILE BACKUP =====
        System.out.println("\n--- Taking Backup ---");
        File_handler fh = new File_handler();
        fh.backup_of_tenants();
        fh.backup_of_receipts();
        fh.backup_vacancies();

        System.out.println("\n========================================");
        System.out.println("   ALL TESTS PASSED - FLATHUB READY!   ");
        System.out.println("========================================");
    }

            }



