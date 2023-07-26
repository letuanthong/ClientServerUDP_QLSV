package Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBAccess {

    private Connection con;
    private Statement stmt;
    static boolean check = false;
    private String keyD = "88888888";
    private String keyV = "LTMCK";

    public DBAccess() {
        try {
            MyConnection mycon = new MyConnection();
            con = mycon.getConnection();
            stmt = con.createStatement();
            check = true;
        } catch (Exception e) {
            check = false;
        }
    }

    public int Update(String str) {
        try {
            int i = stmt.executeUpdate(str);
            return i;
        } catch (Exception e) {
            return -1;
        }
    }
    
    public ResultSet Query(String srt){
        try{
            ResultSet rs = stmt.executeQuery(srt);
            int size = rs.getRow();
            return rs;
        }
        catch(Exception e){
            return null;
        }
    }

    public String getSV(String srt) {
        StringBuilder students = new StringBuilder();
        try {
            ResultSet rs = stmt.executeQuery(srt);

//            if (!rs.next()) {
//                return "";
//            }

            while (rs.next()) {
                String hoten3 = rs.getString("hoten");
                String mssv3 = rs.getString("mssv");
                String toan3 = rs.getString("toan");
                String van3 = rs.getString("van");
                String anh3 = rs.getString("anh");


                //Giải DES
                String dehoten = Encode.DecryptDES(hoten3, keyD);
                String demssv = Encode.DecryptDES(mssv3, keyD);
                String detoan = Encode.DecryptDES(toan3, keyD);
                String devan = Encode.DecryptDES(van3, keyD);
                String deanh = Encode.DecryptDES(anh3, keyD);

                float toan4 = Float.valueOf(detoan).floatValue();
                float van4 = Float.valueOf(devan).floatValue();
                float anh4 = Float.valueOf(deanh).floatValue();

                float avg = ((toan4 + van4 + anh4) / 3);

                if (hoten3 == null) {
                    hoten3 = "";
                }
                if (mssv3 == null) {
                    mssv3 = "";
                }
                if (toan3 == null) {
                    toan3 = "";
                }
                if (van3 == null) {
                    van3 = "";
                }
                if (anh3 == null) {
                    anh3 = "";
                }
                String student = dehoten.trim() + "," + demssv.trim() + "," + String.format("%.2f", avg) + "#";
                students.append(student);
                String kq = students.toString();
            }

        } catch (Exception e) {
            String err = e.toString();
            return null;
        }
        return students.toString();
    }
    
    

}
