package Database;
import Model.*;
import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
class connection {
    private static Connection con = null;

    public connection() {
    }

    public static Connection getConnection()
    {
        if (con==null) {
            try {
                con =DriverManager.getConnection("jdbc:mysql://localhost:3306/flathub", "root", "1234");
                System.out.println("database is working...");
            } catch (SQLException e) {
                System.out.println("error check database.java");
            }
        }
        return con;
    }

}

public class Database {
    static Connection con=connection.getConnection();

    public static ArrayList<Tenant> Tenants_list = new ArrayList<>();
    public static ArrayList<RECIEPT> Reciept_list = new ArrayList<>();
    public static ArrayList<String[]> Vacant_list = new ArrayList<>();

    public void load_all()  {
        load_tenants();
        load_reciepts();
        load_vacancies();
    }

  public   void load_tenants() {
        Tenants_list.clear();
        try {
            Statement stat = con.createStatement();
            ResultSet RS = stat.executeQuery("select * from tenants");
            while (RS.next()) {
                Tenant t = new Tenant();
                t.setName(RS.getString("name"));
                t.setCnic(RS.getString("cnic"));
                t.setPhone_no(RS.getString("phone_no"));
                t.setFlat_no(RS.getString("flat_no"));
                t.setRent(RS.getDouble("rent"));
                t.setDues(RS.getDouble("dues"));
                t.setType(RS.getString("type"));
                t.setJoinDate(RS.getString("join_date"));
                t.setActive(RS.getBoolean("active"));
                Tenants_list.add(t);
            }
            RS.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println("Error while loading tenants");
        }
    }

   public void load_reciepts() {
        Database.Reciept_list.clear();
        try {
            Statement stat = con.createStatement();
            ResultSet RS = stat.executeQuery("SELECT * FROM receipts");
            while (RS.next()) {
                RECIEPT r = new RECIEPT(RS.getInt("reciept_no"),RS.getString("tenant_name"),RS.getString("payment_type"),RS.getDouble("amount"),RS.getString("flat_no"),RS.getString("date"));
                r.setRecieptNo(RS.getInt("receipt_no"));
                r.setTenantName(RS.getString("tenant_name"));
                r.setFlat_no(RS.getString("flat_no"));
                r.setAmount(RS.getDouble("amount"));
                r.setDate(RS.getString("date"));
                r.setPayment_type(RS.getString("payment_type"));
                Reciept_list.add(r);
            }
            RS.close();
            stat.close();
        } catch (SQLException e) {
            System.out.println("Error while loading reciepts");
        }
    }

   public void load_vacancies() {
        Vacant_list.clear();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM vacancies");
            while (rs.next()) {
                String[] v = new String[4];
                v[0] = rs.getString("flat_no");
                v[1] = rs.getString("prev_tenant");
                v[2] = rs.getString("cnic");
                v[3] = rs.getString("vacated_date");
                Vacant_list.add(v);
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            System.out.println("Error while loading vacancies");
        }
    }

    public void add_tenant(Tenant T) {
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getFlat_no().equals(T.getFlat_no())) {
                System.out.println("This flat is already occupied!!");
                return;
            }
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO tenants VALUES (?,?,?,?,?,?,?,?,?)"
            );
            ps.setString(1, T.getName());
            ps.setString(2, T.getCnic());
            ps.setString(3, T.getPhone_no());
            ps.setString(4, T.getFlat_no());
            ps.setDouble(5, T.getRent());
            ps.setDouble(6, T.getDues());
            ps.setString(7, T.getType());
            ps.setString(8, T.getJoinDate());
            ps.setBoolean(9, T.isActive());
            ps.executeUpdate();
            ps.close();
            Tenants_list.add(T);

            for (int i = 0; i < Vacant_list.size(); i++) {
                if (Vacant_list.get(i)[0].equals(T.getFlat_no())) {
                    Vacant_list.remove(i);
                    delete_vacancy(T.getFlat_no());
                    break;
                }
            }
            System.out.println("Tenant added succesfully!!");
        } catch (SQLException e) {
            System.out.println("Error while adding tenant");
        }
    }

   public void show_all_tenants() {
        if (Tenants_list.isEmpty()) {
            System.out.println("No tenants found!!\n");
            return;
        }
        System.out.println("==================");
        for (int i = 0; i < Tenants_list.size(); i++) {
            System.out.println(Tenants_list.get(i));
        }
        System.out.println("==================\n");
    }
   public void search_by_cnic(String cnic) {
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getCnic().equals(cnic)) {
                System.out.println("Tenant Found!!");
                System.out.println(Tenants_list.get(i));
                return;
            }
        }
        System.out.println("No tenant found with CNIC:"+cnic);
    }

   public void search_by_flat(String flat_no) {
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getFlat_no().equals(flat_no)) {
                System.out.println("Tenant Found!!");
                System.out.println(Tenants_list.get(i));
                return;
            }
        }
        System.out.println("No tenant found in flat: " + flat_no);
    }

   public void search_by_name(String name) {
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getName().equalsIgnoreCase(name)) {
                System.out.println("Tenant Found!!");
                System.out.println(Tenants_list.get(i));
                return;
            }
        }
        System.out.println("No tenant found with name: " + name);
    }

   public void remove_tenant(String flat_no) {
        Tenant nikalo = null;
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getFlat_no().equals(flat_no)) {
                nikalo = Tenants_list.get(i);
                break;
            }
        }
        if (null == nikalo) {
            System.out.println("No tenant found in flat: " + flat_no);
            return;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM tenants WHERE flat_no=?"
            );
            ps.setString(1, flat_no);
            ps.executeUpdate();
            ps.close();
            Tenants_list.remove(nikalo);
            add_vacancy(flat_no, nikalo.getName(), nikalo.getCnic());
            System.out.println(nikalo.getName() + " removed.Flat " + flat_no + " is now vacant.");
        } catch (SQLException e) {
            System.out.println("Error while removing tenant");
        }
    }

// ------------------------------------------------------------------------

    public void add_reciept(RECIEPT r) {
        for (int i = 0; i < Reciept_list.size(); i++) {
            if (Reciept_list.get(i).getRecieptNo() == r.getRecieptNo()) {
                System.out.println("Duplicate Reciept!!");
                return;
            }
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO receipts VALUES (?,?,?,?,?,?)"
            );
            ps.setInt(1, r.getRecieptNo());
            ps.setString(2, r.getTenantName());
            ps.setString(3, r.getFlatNo());
            ps.setDouble(4, r.getAmount());
            ps.setString(5, r.getDate());
            ps.setString(6, r.getPaymentType());
            ps.executeUpdate();
            ps.close();
            Reciept_list.add(r);
            System.out.println("Reciept saved!!");
        } catch (SQLException e) {
            System.out.println("Error while saving reciept");
        }
    }

   public void show_reciepts() {
        if (Reciept_list.isEmpty()) {
            System.out.println("No reciepts found.");
            return;
        }
        System.out.println("\n==================");
        for (int i = 0; i < Reciept_list.size(); i++) {
            System.out.println(Reciept_list.get(i));
        }
        System.out.println("==================\n");
    }

   public void pay_rent(String flat_no) {
        Tenant found = null;
        for (int i = 0; i < Tenants_list.size(); i++) {
            if (Tenants_list.get(i).getFlat_no().equals(flat_no)) {
                found=Tenants_list.get(i);
                break;
            }
        }
        if (found == null) {
            System.out.println("No tenant found in flat: "+flat_no);
            return;
        }

        String today = java.time.LocalDate.now().toString();
        int nextNo = Reciept_list.isEmpty() ? 1 :
                Reciept_list.get(Reciept_list.size() - 1).getRecieptNo() + 1;

//        if (found.getType().equals("monthly")) {
//            MonthlyTenant mt = new MonthlyTenant(
//                    found.getName(), found.getCnic(), found.getPhone_no(),
//                    found.getFlat_no(), found.getRent(), found.getDues(), found.getJoinDate()
//            );
//            mt.GenerateReciept(nextNo, today);
//        } else {
//            YearlyTenant yt = new YearlyTenant(
//                    found.getName(), found.getCnic(), found.getPhone_no(),
//                    found.getFlat_no(), found.getRent(), found.getDues(), found.getJoinDate()
//            );
//            yt.GenerateReciept(nextNo, today);
//        }

        RECIEPT r;
        r = new RECIEPT(nextNo, found.getName(), flat_no,
                found.getRent(), today, found.getType());
        add_reciept(r);
    }



    public void add_vacancy(String flat_no, String prev_tenant, String cnic) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO vacancies VALUES (?,?,?,?)"
            );
            String today = java.time.LocalDate.now().toString();
            ps.setString(1, flat_no);
            ps.setString(2, prev_tenant);
            ps.setString(3, cnic);
            ps.setString(4, today);
            ps.executeUpdate();
            ps.close();
            String[] v = {flat_no, prev_tenant, cnic, today};
            Vacant_list.add(v);
            System.out.println("Flat " + flat_no + " marked as vacant.");
        } catch (SQLException e) {
            System.out.println("Error while adding vacancy");
        }
    }
   public void delete_vacancy(String flat_no) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM vacancies WHERE flat_no=?"
            );
            ps.setString(1, flat_no);
            ps.executeUpdate();
            ps.close();

            for (int i = 0; i < Vacant_list.size(); i++) {
                if (Vacant_list.get(i)[0].equals(flat_no)) {
                    Vacant_list.remove(i);
                    break;
                }
            }
            System.out.println("Flat " + flat_no + " removed from vacant list.");
        } catch (SQLException e) {
            System.out.println("Error while deleting vacancy");
        }
    }


   public void show_vacancies() {
        if (Vacant_list.isEmpty()) {
            System.out.println("No vacant flats.");
            return;
        }
        System.out.println("\n==================");
        for (int i = 0; i < Vacant_list.size(); i++) {
            String[] v = Vacant_list.get(i);
            System.out.println("Flat: " + v[0] + " | Prev: " + v[1] +
                    " | CNIC: " + v[2] + " | Date: " + v[3]);
        }
        System.out.println("==================\n");
    }





    public void insert_landlord(String username, String password) {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO landlord VALUES (?,?)"
            );
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            ps.close();
            System.out.println("Landlord saved!!");
        } catch (SQLException e) {
            System.out.println("Error while saving landlord");
        }
    }
}







