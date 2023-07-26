package Server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.File;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import static java.sql.DriverManager.println;
import java.util.ArrayList;
import java.util.List;

public class ServerCtr implements Runnable {

    private Scanner in = null;
    private PrintWriter out = null;
    private Socket socket;
    private String name;
    private ResultSet rs;

    public ServerCtr(Socket socket, String string) throws IOException {
        this.socket = socket;
        this.name = string;
        this.in = new Scanner(this.socket.getInputStream());
        this.out = new PrintWriter(this.socket.getOutputStream(), true);

        new Thread(this).start();
    }

    public static final int KETNOI = 1;
    public static final int FILE = 2;
    public static final int NHAP = 3;
    public static final int HIENTHI = 4;
    public static final int FILEOPEN = 5;
    public static final int OPEN = 6;
    public static final int NOCMD = -1;

    public static final String database = "sinhvien";
    public static final String username = "root";
    public static final String password = "ltt030500";

    private String keyD = "88888888";
    private String keyV = "LTMCK";
    private int count = 2;

    public int lenh(String strcmd) {
        if (strcmd.equals("KN")) {
            return KETNOI;
        }
        if (strcmd.equals("FILE")) {
            return FILE;
        }
        if (strcmd.equals("NP")) {
            return NHAP;
        }
        if (strcmd.equals("HT")) {
            return HIENTHI;
        }
        if (strcmd.equals("FO")) {
            return FILEOPEN;
        }
        if (strcmd.equals("OP")) {
            return OPEN;
        }
        return NOCMD;
    }

    public void run() {
        Scanner sc = null;
        DBAccess acc = null;
        try {
            while (true) {
                String s = in.nextLine().trim();
                sc = null;
                String cmd = "";
                String data = "";
                try {
                    sc = new Scanner(s);
                    sc.useDelimiter("@");
                    cmd = sc.next();
                    data = sc.next();
                } catch (Exception e) {

                }

                switch (lenh(cmd)) {
                    case KETNOI:
                        String[] ketnoi = data.split("#");
                        InetAddress host = InetAddress.getByName(ketnoi[0]);
                        int port = Integer.parseInt(ketnoi[1]);
                        System.out.println(host + " " + port);

                        if (host.equals(InetAddress.getByName("192.168.1.10")) && port == Server.PORT) {
                            out.println("TC");
                        } else {
                            out.println("TB");
                        }
                        break;

                    case FILE:
//                        acc = new DBAccess();
                        String[] ketnoi2 = data.split("#");
//                        database = ketnoi2[0];
//                        username = ketnoi2[1];
//                        password = ketnoi2[2];
//                        acc = new DBAccess();
//                        if (acc.check) {
//                            out.println("TC");
//                        } else {
//                            out.println("TB");
//                        }
//                        break;
                        // Code nhan du lieu va luu 
                        String noidung = ketnoi2[0];
                        String ten = ketnoi2[1];
                        String keyVi = ketnoi2[2];

                        String ennoidung = Encode.EncryptVigenere(noidung, keyVi);
                        File f = new File("D:\\dev\\mangcanban\\db\\" + ten + ".txt");
                        if (f.exists()) {
                            out.println("TB");
                        } else {
                            out.println("TC");
                        }
                        FileWriter fw = new FileWriter("D:\\dev\\mangcanban\\db\\" + ten + ".txt");

                        fw.write(ennoidung);
                        fw.close();
                        break;

                    case NHAP:
                        acc = new DBAccess();
                        String[] ketnoi3 = data.split("#");
                        String hoten = ketnoi3[0];
                        String mssv = ketnoi3[1];
                        String toan = ketnoi3[2];
                        String van = ketnoi3[3];
                        String anh = ketnoi3[4];

                        //Thực hiện DES
                        String enhoten = Encode.EncryptDES(hoten, keyD);
                        String enmssv = Encode.EncryptDES(mssv, keyD);
                        String entoan = Encode.EncryptDES(toan, keyD);
                        String envan = Encode.EncryptDES(van, keyD);
                        String enanh = Encode.EncryptDES(anh, keyD);

                        String query = "insert into sinhvien (hoten,mssv,toan,van,anh) values('" + enhoten + "','" + enmssv + "','" + entoan + "','" + envan + "','" + enanh + "')";
                        if (acc.Update(query) >= 0) {
                            out.println("TC");
                        } else {
                            out.println("TB");
                        }
                        break;
                    
                    case OPEN:
                        String[] mofile = data.split("#");
                        String chuaen = mofile[0];
                        String keyVmo = mofile[1];
                        chuaen = chuaen.trim();
                        File files = new File("D:\\dev\\mangcanban\\db\\"+chuaen);
                        BufferedReader br = Files.newBufferedReader(files.toPath(), StandardCharsets.UTF_8);
                        String doc = null;
                        String line = null;
                        while(true){
                            line = br.readLine();
                            if(line==null){
                                break;
                            }else{
                                doc = line;
                            }
                        }
                        String docma = Encode.DecryptVigenere(doc, keyVmo);
                        if (docma != null){
//                            for (String file : fileList) {
//				data += "--" + file;
//                            }
                            out.println(docma);
//                            System.out.println(docma);
                            
                        } else {
                            out.println("TB");
                        }   
                        break;
                    
                    case FILEOPEN:
                        
                        List<String> fileList = getFileList("D:\\dev\\mangcanban\\db\\");
//                        String[] files = fileWorker.getAllFileName();
//                        File file = new File("D:\\dev\\mangcanban\\db");
//                        String[] fileList = file.list();

                        if (fileList != null){
//                            for (String file : fileList) {
//				data += "--" + file;
//                            }
                            out.println(fileList);
                            
                        } else {
                            out.println("TB");
                        }   
                        break;
                    case HIENTHI:
                        acc = new DBAccess();
                        String sql = "select *  from sinhvien";
                        
                        String kq = acc.getSV(sql);
                        if (kq != null) {
                            out.println(kq);
                        } else {
                            out.println("TB");
                        }

                        break;    

                }
            }
        } catch (Exception e) {
            System.out.println(name + " has departed");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {

            }
            if (sc != null) {
                sc.close();
            }
        }
    }

    public List<String> getFileList(String folderPath) {
        List<String> fileList = new ArrayList<>();
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getName());
                }
            }
        }
        return fileList;
    }
}
