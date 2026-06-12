package Controller;
import Database.Database;
import Model.*;
import com.google.gson.Gson;
import spark.Spark;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;


public class API {

    private static Database db = new Database();
    private static Gson gson = new Gson();
    private static File_handler fh = new File_handler();

    public static void main(String[] args) {
        // Load all data from database
        db.load_all();

        // Enable CORS so frontend can call from different port
        Spark.options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }
            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        Spark.before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            res.type("application/json");
        });

        // ==================== TENANT APIs ====================

        // GET all tenants
        Spark.get("/api/tenants", (req, res) -> {
            return gson.toJson(Database.Tenants_list);
        });

        // POST add tenant
        Spark.post("/api/tenants", (req, res) -> {
            Tenant t = gson.fromJson(req.body(), Tenant.class);

            // Validation
            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                if (Database.Tenants_list.get(i).getFlat_no().equals(t.getFlat_no()) && Database.Tenants_list.get(i).isActive()) {
                    res.status(400);
                    return gson.toJson(Map.of("error", "Flat already occupied"));
                }
            }

            db.add_tenant(t);
            fh.backup_of_tenants();
            return gson.toJson(Map.of("success", true, "message", "Tenant added"));
        });

        Spark.delete("/api/tenants/:flat", (req, res) -> {
            String flat = req.params(":flat");
            db.remove_tenant(flat);
            fh.backup_of_tenants();
            return gson.toJson(Map.of("success", true, "message", "Tenant removed"));
        });

        // GET search tenant by CNIC
        Spark.get("/api/tenants/search/cnic", (req, res) -> {
            String cnic = req.queryParams("cnic");
            ArrayList<Tenant> results = new ArrayList<>();
            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                if (Database.Tenants_list.get(i).getCnic().equals(cnic)) {
                    results.add(Database.Tenants_list.get(i));
                }
            }
            return gson.toJson(results);
        });

        // GET search tenant by flat
        Spark.get("/api/tenants/search/flat", (req, res) -> {
            String flat = req.queryParams("flat");
            ArrayList<Tenant> results = new ArrayList<>();
            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                if (Database.Tenants_list.get(i).getFlat_no().equals(flat)) {
                    results.add(Database.Tenants_list.get(i));
                }
            }
            return gson.toJson(results);
        });

        // GET search tenant by name
        Spark.get("/api/tenants/search/name", (req, res) -> {
            String name = req.queryParams("name");
            ArrayList<Tenant> results = new ArrayList<>();
            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                if (Database.Tenants_list.get(i).getName().equalsIgnoreCase(name)) {
                    results.add(Database.Tenants_list.get(i));
                }
            }
            return gson.toJson(results);
        });

        // ==================== RECEIPT APIs ====================

        // GET all receipts
        Spark.get("/api/receipts", (req, res) -> {
            return gson.toJson(Database.Reciept_list);
        });

        // POST generate receipt
        Spark.post("/api/receipts", (req, res) -> {
            String flatNo = req.queryParams("flat_no");
            db.pay_rent(flatNo);
            fh.backup_of_receipts();
            return gson.toJson(Map.of("success", true, "message", "Receipt generated"));
        });

        // ==================== VACANCY APIs ====================

        // GET all vacancies
        Spark.get("/api/vacancies", (req, res) -> {
            return gson.toJson(Database.Vacant_list);
        });

        // ==================== BACKUP API ====================

        // POST backup all
        Spark.post("/api/backup", (req, res) -> {
            fh.backup_of_tenants();
            fh.backup_of_receipts();
            fh.backup_vacancies();
            return gson.toJson(Map.of("success", true, "message", "Backup completed"));
        });

        // GET backup data as JSON
        Spark.get("/api/backup", (req, res) -> {
            Map<String, Object> backup = new HashMap<>();
            backup.put("timestamp", java.time.LocalDate.now().toString());
            backup.put("tenants", Database.Tenants_list);
            backup.put("receipts", Database.Reciept_list);
            backup.put("vacancies", Database.Vacant_list);
            return gson.toJson(backup);
        });

        // ==================== STATS API ====================

        // GET dashboard stats
        Spark.get("/api/stats", (req, res) -> {
            Map<String, Object> stats = new HashMap<>();
            int activeCount = 0;
            double revenue = 0;
            for (int i = 0; i < Database.Tenants_list.size(); i++) {
                if (Database.Tenants_list.get(i).isActive()) {
                    activeCount++;
                    revenue += Database.Tenants_list.get(i).getRent();
                }
            }
            stats.put("totalTenants", activeCount);
            stats.put("totalVacant", Database.Vacant_list.size());
            stats.put("totalReceipts", Database.Reciept_list.size());
            stats.put("monthlyRevenue", revenue);
            return gson.toJson(stats);
        });

        System.out.println("API Server started on http://localhost:4567");
        System.out.println("Available endpoints:");
        System.out.println("  GET  /api/tenants");
        System.out.println("  POST /api/tenants");
        System.out.println("  DELETE /api/tenants/:flat");
        System.out.println("  GET  /api/tenants/search/cnic?cnic=...");
        System.out.println("  GET  /api/tenants/search/flat?flat=...");
        System.out.println("  GET  /api/tenants/search/name?name=...");
        System.out.println("  GET  /api/receipts");
        System.out.println("  POST /api/receipts?flat_no=...");
        System.out.println("  GET  /api/vacancies");
        System.out.println("  GET  /api/stats");
        System.out.println("  GET  /api/backup");
        System.out.println("  POST /api/backup");
    }
}

