/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.New_ConnectDB;
import static layout.CustomersFrame.tabeloutx;
import static layout.InventoryFrame.tabelouty;
import static layout.UserFrame.tabeloutz;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author user
 */
public class MainFrame extends javax.swing.JFrame {
    private Connection conn = new New_ConnectDB().connect();
    private DefaultTableModel tabmode;
    private DefaultTableModel daftarbeli;
    
    JasperReport jasrep;
    JasperPrint jaspri;
    JasperDesign jasdes;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(this);
        setTanggal();
        auto_user();
        auto_barang();
        auto_cust();
        dataTable();
        code_transaksi();
    }
    
    public void setTanggal(){
        java.util.Date skrg = new java.util.Date();
        java.text.SimpleDateFormat kal = new
        java.text.SimpleDateFormat("dd/MM/yyyy");
        tf_tanggal.setText(kal.format(skrg));
        tf_tanggal1.setText(kal.format(skrg));
  
    //tf_tglBatal.setText(kal.format(skrg));
    }
    
    protected void transaksi_nota(){
        Object[]baris = {"Tanggal","Nomor Transaksi","ID Customers","Total"};
        tabmode = new DefaultTableModel(null, baris);
        tabelout3.setModel(tabmode);
        String sql = "select * from laporan_transaksi order by Nomor_Transaksi asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String a = hasil.getString("Tanggal");
                String b = hasil.getString("Nomor_Transaksi");
                String c = hasil.getString("ID_Customers");
                String d = hasil.getString("Total");
                String[] data = {a,b,c,d};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void update_tabel_user(){
        Object[]baris = {"ID User","Nama","No_HP","Alamat"};
        tabmode = new DefaultTableModel(null, baris);
        tabeloutz.setModel(tabmode);
        String sql = "select * from user order by Nama asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String skode_barang = hasil.getString("ID_User");
                String snama_barang = hasil.getString("Nama");
                String sharga_satuan = hasil.getString("No_HP");
                String salamat = hasil.getString("Alamat");
                String[] data = {skode_barang,snama_barang,sharga_satuan,salamat};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void update_tabel_customers(){
        Object[]baris = {"ID Customers","Nama Customers","NO Handphone","Email","Alamat"};
        tabmode = new DefaultTableModel(null, baris);
        tabeloutx.setModel(tabmode);
        String sql = "select * from customers order by Nama asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String skode_barang = hasil.getString("ID_Cust");
                String snama_barang = hasil.getString("Nama");
                String sharga_satuan = hasil.getString("No_HP");
                String sjumlah_barang = hasil.getString("Email");
                String salamat = hasil.getString("Alamat");
                String[] data = {skode_barang,snama_barang,sharga_satuan,sjumlah_barang,salamat};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void update_tabel_barang(){
        Object[]baris = {"Kode Barang","Nama Barang","Harga Satuan","Stok Barang"};
        tabmode = new DefaultTableModel(null, baris);
        tabelouty.setModel(tabmode);
        String sql = "select * from barang order by Nama asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String skode_barang = hasil.getString("Kode_Barang");
                String snama_barang = hasil.getString("Nama");
                String sharga_satuan = hasil.getString("Harga");
                String sjumlah_barang = hasil.getString("Jumlah");
                String[] data = {skode_barang,snama_barang,sharga_satuan,sjumlah_barang};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void kosong(){
        auto_barang();
        tf_namaBarang.setText("");
        tf_harga.setText("");
        tf_jumlah.setText("");
        
    }
    
    protected void kosong1(){
        tf_nmBarang.setText("");//
        tf_kdBarang1.setText("");
        jl_satuan.setText("");
        tf_jumlahBarang.setText("");
        tumbal_bayar.setText("");
        jl_totalBayar.setText("");
    }
    
    protected void kosong2(){
        tf_nmBarang.setText("");//
        tf_kdBarang1.setText("");
        jl_satuan.setText("");
        tf_jumlahBarang.setText("");
        tumbal_bayar.setText("");
        jl_totalBayar.setText("");
        ID_Cust.setText("");
        tf_namaCust.setText("");
        tf_nohp2.setText("");
        jl_sum.setText("");
    }
    
    protected void sum() {
        try {
        String sql = "select sum(Total_Pembayaran) from pembelian where Nomor_Transaksi='"+tf_kdTransaksi.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                
                String stotal_saldo = hasil.getString("sum(Total_Pembayaran)");
                //editan baru
                tumbal.setText(stotal_saldo);
                int total = Integer.parseInt(tumbal.getText());
                NumberFormat nf = NumberFormat.getInstance();
                jl_sum.setText("Rp "+nf.format(total));
                
            }
        } catch (SQLException e) {
        }
    }
    
    protected void code_transaksi(){
        try {
            //--> melakukan eksekusi query untuk mengambil data dari tabel
            String sql = "SELECT MAX(RIGHT(Nomor_Transaksi,6)) AS NO FROM laporan_transaksi";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                if (hasil.first() == false) {
                    tf_kdTransaksi.setText("NJ-000001");
                } else {
                    hasil.last();
                    int auto_id = hasil.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    //MENGATUR jumlah 0
                    for (int j = 0; j < 6 - NomorJual; j++) {
                        no = "0" + no;
                    }
                    tf_kdTransaksi.setText("NJ-" + no);
                }
            }
            hasil.close();
            stat.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    protected void Update_Stok() {
        int a = Integer.parseInt(tumbal_stok.getText());
        int b = Integer.parseInt(tf_jumlahBarang.getText());
        int stotal = a - b;
        tumbal_update_stok.setText(""+stotal);
        try {
            String sql = "update barang set Jumlah='"+tumbal_update_stok.getText()+"' where Kode_Barang='"+tf_kdBarang1.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.executeUpdate();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Hubungi Administrator +62 8778-8298-067" +e);
        }
    }
    
    protected void auto_user(){
        try {
            String sql="select * from user order by ID_User desc";
            java.sql.Statement stat=conn.createStatement();
            ResultSet hasil=stat.executeQuery(sql);
            if (hasil.next()) {
                String auto = hasil.getString("ID_User").substring(1);
                String AN = "" + (Integer.parseInt(auto) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}
                
               tf_IdUser.setText("K" + Nol + AN);
            } else {
               tf_IdUser.setText("K0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }
    
    protected void auto_barang(){
        try {
            String sql="select * from barang order by Kode_Barang desc";
            java.sql.Statement stat=conn.createStatement();
            ResultSet hasil=stat.executeQuery(sql);
            if (hasil.next()) {
                String auto = hasil.getString("Kode_Barang").substring(1);
                String AN = "" + (Integer.parseInt(auto) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}
                
               tf_kdBarang.setText("B" + Nol + AN);
            } else {
               tf_kdBarang.setText("B0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }
    
    protected void auto_cust(){
        try {
            String sql="select * from customers order by ID_Cust desc";
            java.sql.Statement stat=conn.createStatement();
            ResultSet hasil=stat.executeQuery(sql);
            if (hasil.next()) {
                String auto = hasil.getString("ID_Cust").substring(1);
                String AN = "" + (Integer.parseInt(auto) + 1);
                String Nol = "";

                if(AN.length()==1)
                {Nol = "000";}
                else if(AN.length()==2)
                {Nol = "00";}
                else if(AN.length()==3)
                {Nol = "0";}
                else if(AN.length()==4)
                {Nol = "";}
                
               tf_IdCust.setText("C" + Nol + AN);
            } else {
               tf_IdCust.setText("C0001");
            }

           }catch(Exception e){
           JOptionPane.showMessageDialog(null, e);
           }
    }
    
    protected void daftar_list(){
    Object[] baris = {"Kode Barang","Nama Barang","Harga","QTY","Total"};
        daftarbeli = new DefaultTableModel(null, baris);
        pembeli.setModel(daftarbeli);
        String sql="select * from pembelian where Nomor_Transaksi='"+tf_kdTransaksi.getText()+"'";
        try{
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String a = hasil.getString("Kode_Barang");
                String b = hasil.getString("Nama_barang");
                String c = hasil.getString("Harga");
                String d = hasil.getString("QTY");
                String e = hasil.getString("Total_Pembayaran");
                String[]data = {a,b,c,d,e};
                daftarbeli.addRow(data);
                sum();
            }
        }catch (Exception e) {
        }
    }
    
    protected void reset_list(){
        Object[] baris = {"Kode Barang","Nama Barang","Harga","QTY","Total"};
            daftarbeli = new DefaultTableModel(null, baris);
            pembeli.setModel(daftarbeli);
            String sql="select * from pembelian where Nomor_Transaksi ='0'";
            try{
                java.sql.Statement stat = conn.createStatement();
                ResultSet hasil = stat.executeQuery(sql);
                while(hasil.next()){
                    String a = hasil.getString("Kode_Barang");
                    String b = hasil.getString("Nama_barang");
                    String c = hasil.getString("Harga");
                    String d = hasil.getString("QTY");
                    String e = hasil.getString("Total_Pembayaran");
                    String[]data = {a,b,c,d,e};
                    daftarbeli.addRow(data);
                    
                }
            }catch (Exception e) {
        }
    
    }
    
        protected void dataTable() {
        Object[]baris = {"Nama Barang","Stok Barang"};
        tabmode = new DefaultTableModel(null, baris);
        tabelout.setModel(tabmode);
        String sql = "select Nama,Jumlah from barang order by Nama asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String snama_barang = hasil.getString("Nama");
                String sjumlah_barang = hasil.getString("Jumlah");
                String[] data = {snama_barang, sjumlah_barang};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
        
    protected void data_nota() {
        Object[]baris = {"No Pemesanan","Nama","Tanggal Pemesanan","Jam Main"};
        tabmode = new DefaultTableModel(null, baris);
        tabelout2.setModel(tabmode);
        String sql = "select No_Pemesanan,Nama,Tanggal_Pemesanan,Jam_Main from pemesanan where Tanggal_Main !='Pembatalan' ";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String snopem = hasil.getString("No_Pemesanan");
                String snama = hasil.getString("Nama");
                String stanggalpem = hasil.getString("Tanggal_Pemesanan");
                String sjam = hasil.getString("Jam_Main");
                String[] data = {snopem,snama, stanggalpem, sjam};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void Laporan(){
        Object[]baris = {"Tanggal","Nomor Transaksi","ID Customers","Total"};
        tabmode = new DefaultTableModel(null, baris);
        tabelout2.setModel(tabmode);
        String sql = "select * from laporan_transaksi order by Nomor_Transaksi asc";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String a = hasil.getString("Tanggal");
                String b = hasil.getString("Nomor_Transaksi");
                String c = hasil.getString("ID_Customers");
                String d = hasil.getString("Total");
                String[] data = {a,b,c,d};
                tabmode.addRow(data);

            }
        } catch (Exception e) {
        }
    }
    
    protected void kosong_user(){
        tf_namaKaryawan.setText("");//
        tf_nohp.setText("");
        tf_username.setText("");
        tf_pass.setText("");
        ta_alamat.setText("");
        
    }
    
    protected void kosong_cust(){
        tf_nama1.setText("");//
        tf_nohp1.setText("");
        tf_email1.setText("");
        ta_alamat1.setText("");
    }
        
    protected void virtual_btn(){
        try {
            String sql = "insert into laporan_transaksi (Tanggal,Nomor_Transaksi,ID_Customers,Total) values (?,?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_tanggal1.getText());
            stat.setString(2, tf_kdTransaksi.getText());//
            stat.setString(3, ID_Cust.getText());
            stat.setString(4, tumbal.getText());
            stat.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "" + e);
        }
    }
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        View = new javax.swing.JPanel();
        MainFrame = new javax.swing.JPanel();
        bg2 = new javax.swing.JLabel();
        Frame_dataKaryawan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tf_namaKaryawan = new javax.swing.JTextField();
        tf_nohp = new javax.swing.JTextField();
        tf_username = new javax.swing.JTextField();
        tf_pass = new javax.swing.JPasswordField();
        cb_kel = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_alamat = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        tf_IdUser = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        mode1 = new javax.swing.JComboBox<>();
        bg = new javax.swing.JLabel();
        Frame_dataCustomers = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        tf_IdCust = new javax.swing.JTextField();
        tf_nohp1 = new javax.swing.JTextField();
        tf_nama1 = new javax.swing.JTextField();
        cb_kel1 = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        ta_alamat1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        tf_email1 = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        mode2 = new javax.swing.JComboBox<>();
        bg4 = new javax.swing.JLabel();
        Frame_dataBarang = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        tf_kdBarang = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tf_namaBarang = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tf_harga = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tf_jumlah = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        mode = new javax.swing.JComboBox<>();
        tf_tanggal = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        tf_VerifId = new javax.swing.JTextField();
        tf_VerifNama = new javax.swing.JTextField();
        bg1 = new javax.swing.JLabel();
        Frame_transaksiPemesanan = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        tf_nmBarang = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tf_kdBarang1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jl_satuan = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        tf_satuan = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tf_jumlahBarang = new javax.swing.JTextField();
        jl_totalBayar = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tf_namaCust = new javax.swing.JTextField();
        tf_nohp2 = new javax.swing.JTextField();
        submit1 = new javax.swing.JButton();
        submit = new javax.swing.JButton();
        tf_tanggal1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelout = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        pembeli = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jl_sum = new javax.swing.JLabel();
        tf_sum = new javax.swing.JTextField();
        ID_Cust = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tf_kdTransaksi = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tf_IdVerif = new javax.swing.JTextField();
        tf_NamaVerif = new javax.swing.JTextField();
        bg3 = new javax.swing.JLabel();
        tumbal_update_stok = new javax.swing.JLabel();
        tumbal_stok = new javax.swing.JLabel();
        tumbal_bayar = new javax.swing.JLabel();
        tumbal = new javax.swing.JLabel();
        Frame_transaksiNota = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        tf_dari = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelout2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        tf_sampai = new javax.swing.JTextField();
        bg5 = new javax.swing.JLabel();
        Frame_Laporan = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelout3 = new javax.swing.JTable();
        jLabel35 = new javax.swing.JLabel();
        tf_cari1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        bg6 = new javax.swing.JLabel();
        tf_verif = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        btn_Data = new javax.swing.JMenu();
        btn_Karyawan = new javax.swing.JMenuItem();
        btn_Customers = new javax.swing.JMenuItem();
        btn_Barang = new javax.swing.JMenuItem();
        btn_Transaksi = new javax.swing.JMenu();
        btn_Pemesanan = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        btn_Laporan = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        View.setLayout(new java.awt.CardLayout());

        MainFrame.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bg2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_pure.png"))); // NOI18N
        MainFrame.add(bg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, -1));

        View.add(MainFrame, "card8");

        Frame_dataKaryawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Nama User");
        Frame_dataKaryawan.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 180, 20));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("No Handphone");
        Frame_dataKaryawan.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 190, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Username");
        Frame_dataKaryawan.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 160, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Password");
        Frame_dataKaryawan.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 160, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setText("Jenis Kelamin");
        Frame_dataKaryawan.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 110, 20));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Alamat");
        Frame_dataKaryawan.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 110, 20));

        tf_namaKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_namaKaryawanActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(tf_namaKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 240, -1));
        Frame_dataKaryawan.add(tf_nohp, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 240, -1));
        Frame_dataKaryawan.add(tf_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 240, -1));

        tf_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_passActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(tf_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 240, -1));

        cb_kel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan" }));
        Frame_dataKaryawan.add(cb_kel, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 240, -1));

        ta_alamat.setColumns(20);
        ta_alamat.setRows(5);
        jScrollPane1.setViewportView(ta_alamat);

        Frame_dataKaryawan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 400, 240, 120));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 540, 80, 30));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("ID User");
        Frame_dataKaryawan.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, -1, 20));

        tf_IdUser.setEditable(false);
        Frame_dataKaryawan.add(tf_IdUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 110, -1));

        jButton10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton10.setText("Edit");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 540, 70, 30));

        jButton11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton11.setText("Delete");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 540, -1, 30));

        mode1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[MODE] INPUT USER", "[MODE] PEMBARUAN USER" }));
        mode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mode1ActionPerformed(evt);
            }
        });
        Frame_dataKaryawan.add(mode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 210, 190, -1));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_registCustomers.png"))); // NOI18N
        Frame_dataKaryawan.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, -1));

        View.add(Frame_dataKaryawan, "card3");

        Frame_dataCustomers.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel27.setText("ID Customers");
        Frame_dataCustomers.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 210, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel28.setText("Nama");
        Frame_dataCustomers.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 250, 200, 20));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel29.setText("Nomor Handphone");
        Frame_dataCustomers.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 200, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("Email");
        Frame_dataCustomers.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 310, 160, 20));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("Jenis Kelamin");
        Frame_dataCustomers.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, 110, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("Alamat");
        Frame_dataCustomers.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, 110, 20));

        tf_IdCust.setEditable(false);
        tf_IdCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_IdCustActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(tf_IdCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 220, 100, -1));
        Frame_dataCustomers.add(tf_nohp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 280, 240, -1));

        tf_nama1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nama1ActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(tf_nama1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, 240, -1));

        cb_kel1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Laki-Laki", "Perempuan" }));
        Frame_dataCustomers.add(cb_kel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, 240, -1));

        ta_alamat1.setColumns(20);
        ta_alamat1.setRows(5);
        jScrollPane5.setViewportView(ta_alamat1);

        Frame_dataCustomers.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 370, 240, 120));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 510, 80, 30));
        Frame_dataCustomers.add(tf_email1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 310, 240, -1));

        jButton12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton12.setText("Edit");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, 70, 30));

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setText("Delete");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 510, -1, 30));

        mode2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[MODE] INPUT CUSTOMERS", "[MODE] PEMBARUAN CUSTOMERS" }));
        mode2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mode2ActionPerformed(evt);
            }
        });
        Frame_dataCustomers.add(mode2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 210, 210, -1));

        bg4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_registCustomers_1.png"))); // NOI18N
        Frame_dataCustomers.add(bg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 720));

        View.add(Frame_dataCustomers, "card4");

        Frame_dataBarang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("kode Barang");
        Frame_dataBarang.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 160, 20));

        tf_kdBarang.setEditable(false);
        tf_kdBarang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tf_kdBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_kdBarangActionPerformed(evt);
            }
        });
        tf_kdBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_kdBarangKeyTyped(evt);
            }
        });
        Frame_dataBarang.add(tf_kdBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 90, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("Nama Barang");
        Frame_dataBarang.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 190, 20));

        tf_namaBarang.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frame_dataBarang.add(tf_namaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, 240, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Harga Satuan");
        Frame_dataBarang.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 160, 20));

        tf_harga.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frame_dataBarang.add(tf_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 260, 240, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Jumlah Barang");
        Frame_dataBarang.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 290, 190, 20));

        tf_jumlah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Frame_dataBarang.add(tf_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 290, 240, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setText("Submit");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        Frame_dataBarang.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 390, -1, 30));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton4.setText("Update");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        Frame_dataBarang.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, -1, 30));

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setText("Delete");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        Frame_dataBarang.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 390, 80, 30));

        mode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "[MODE] INPUT BARANG", "[MODE] PERBARUI BARANG" }));
        mode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeActionPerformed(evt);
            }
        });
        Frame_dataBarang.add(mode, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 190, -1));

        tf_tanggal.setEditable(false);
        Frame_dataBarang.add(tf_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, 110, 30));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("ID User");
        Frame_dataBarang.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setText("Nama User");
        Frame_dataBarang.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 350, -1, -1));

        tf_VerifId.setEditable(false);
        Frame_dataBarang.add(tf_VerifId, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 320, 240, -1));

        tf_VerifNama.setEditable(false);
        Frame_dataBarang.add(tf_VerifNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 350, 240, -1));

        bg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_stokBarang.png"))); // NOI18N
        Frame_dataBarang.add(bg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 720));

        View.add(Frame_dataBarang, "card5");

        Frame_transaksiPemesanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Nama Barang");
        Frame_transaksiPemesanan.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, 210, 20));

        tf_nmBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_nmBarangActionPerformed(evt);
            }
        });
        tf_nmBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_nmBarangKeyReleased(evt);
            }
        });
        Frame_transaksiPemesanan.add(tf_nmBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 270, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel19.setText("Kode Barang");
        Frame_transaksiPemesanan.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 110, 20));

        tf_kdBarang1.setEditable(false);
        Frame_transaksiPemesanan.add(tf_kdBarang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 180, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Harga Satuan");
        Frame_transaksiPemesanan.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, 210, 20));

        jl_satuan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jl_satuanAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        Frame_transaksiPemesanan.add(jl_satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 110, 25));

        jLabel21.setText("Rp.");
        Frame_transaksiPemesanan.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 290, 30, 25));

        tf_satuan.setEditable(false);
        tf_satuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_satuanActionPerformed(evt);
            }
        });
        Frame_transaksiPemesanan.add(tf_satuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, 180, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("X");
        Frame_transaksiPemesanan.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 290, 20, 30));

        tf_jumlahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_jumlahBarangActionPerformed(evt);
            }
        });
        tf_jumlahBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tf_jumlahBarangKeyReleased(evt);
            }
        });
        Frame_transaksiPemesanan.add(tf_jumlahBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 290, 60, -1));
        Frame_transaksiPemesanan.add(jl_totalBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 100, 25));

        jTextField1.setEditable(false);
        Frame_transaksiPemesanan.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 320, 180, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel23.setText("Total Pembayaran");
        Frame_transaksiPemesanan.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 140, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel24.setText("Nama Customers");
        Frame_transaksiPemesanan.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, 200, 20));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel25.setText("No Handphone Customers");
        Frame_transaksiPemesanan.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 170, 20));

        tf_namaCust.setEditable(false);
        tf_namaCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_namaCustActionPerformed(evt);
            }
        });
        Frame_transaksiPemesanan.add(tf_namaCust, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 180, -1));

        tf_nohp2.setEditable(false);
        tf_nohp2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tf_nohp2KeyTyped(evt);
            }
        });
        Frame_transaksiPemesanan.add(tf_nohp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 180, -1));

        submit1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        submit1.setText("Submit");
        submit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submit1ActionPerformed(evt);
            }
        });
        Frame_transaksiPemesanan.add(submit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 510, 80, 30));

        submit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        submit.setText("Print");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });
        Frame_transaksiPemesanan.add(submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, 80, 30));

        tf_tanggal1.setEditable(false);
        Frame_transaksiPemesanan.add(tf_tanggal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 30, 110, 30));

        tabelout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeloutMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelout);

        Frame_transaksiPemesanan.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 300, 100));

        pembeli.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        pembeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pembeliMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(pembeli);

        Frame_transaksiPemesanan.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, 370, 100));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Id Customers");
        Frame_transaksiPemesanan.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 350, 200, 20));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel15.setText("Total Pembelanjaan");
        Frame_transaksiPemesanan.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 510, 130, -1));
        Frame_transaksiPemesanan.add(jl_sum, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 510, 120, 20));

        tf_sum.setEditable(false);
        Frame_transaksiPemesanan.add(tf_sum, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 510, 150, -1));

        ID_Cust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ID_CustActionPerformed(evt);
            }
        });
        Frame_transaksiPemesanan.add(ID_Cust, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 350, 180, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Kode Transaksi");
        Frame_transaksiPemesanan.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 150, 20));

        tf_kdTransaksi.setEditable(false);
        Frame_transaksiPemesanan.add(tf_kdTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 130, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("ID User");
        Frame_transaksiPemesanan.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel16.setText("Nama USer");
        Frame_transaksiPemesanan.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, -1, -1));

        tf_IdVerif.setEditable(false);
        Frame_transaksiPemesanan.add(tf_IdVerif, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 440, 180, -1));

        tf_NamaVerif.setEditable(false);
        Frame_transaksiPemesanan.add(tf_NamaVerif, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 470, 180, -1));

        bg3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_pembelianBarang.png"))); // NOI18N
        Frame_transaksiPemesanan.add(bg3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));
        Frame_transaksiPemesanan.add(tumbal_update_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 20));
        Frame_transaksiPemesanan.add(tumbal_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tumbal_bayar.setText("jLabel8");
        Frame_transaksiPemesanan.add(tumbal_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        tumbal.setText("jLabel2");
        Frame_transaksiPemesanan.add(tumbal, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 50, -1, -1));

        View.add(Frame_transaksiPemesanan, "card6");

        Frame_transaksiNota.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Dari");
        Frame_transaksiNota.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, -1, -1));

        tf_dari.setText("dd/mm/yy");
        tf_dari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_dariMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tf_dariMouseExited(evt);
            }
        });
        tf_dari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dariActionPerformed(evt);
            }
        });
        Frame_transaksiNota.add(tf_dari, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 450, 110, 30));

        tabelout2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelout2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelout2MouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabelout2);

        Frame_transaksiNota.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 840, 180));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ico/Printer-icon.png"))); // NOI18N
        jButton3.setText("Cetak");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        Frame_transaksiNota.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 450, -1, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Sampai");
        Frame_transaksiNota.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, -1, -1));

        tf_sampai.setText("dd/mm/yy");
        tf_sampai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_sampaiMouseClicked(evt);
            }
        });
        Frame_transaksiNota.add(tf_sampai, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 110, 30));

        bg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_nota.png"))); // NOI18N
        Frame_transaksiNota.add(bg5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 720));

        View.add(Frame_transaksiNota, "card7");

        Frame_Laporan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelout3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelout3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelout3MouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelout3);

        Frame_Laporan.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 840, 180));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText("Pencarian");
        Frame_Laporan.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        tf_cari1.setText("No Transaksi");
        tf_cari1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tf_cari1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tf_cari1MouseExited(evt);
            }
        });
        tf_cari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_cari1ActionPerformed(evt);
            }
        });
        Frame_Laporan.add(tf_cari1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 110, 30));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ico/Printer-icon.png"))); // NOI18N
        jButton5.setText("Cetak");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        Frame_Laporan.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, -1, 30));

        bg6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bg_laporan.png"))); // NOI18N
        Frame_Laporan.add(bg6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 720));

        View.add(Frame_Laporan, "card8");

        getContentPane().add(View, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 720));

        tf_verif.setEditable(false);
        tf_verif.setText("jTextField2");
        getContentPane().add(tf_verif, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        btn_Data.setText("Data Master");

        btn_Karyawan.setText("Data Karyawan");
        btn_Karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_KaryawanActionPerformed(evt);
            }
        });
        btn_Data.add(btn_Karyawan);

        btn_Customers.setText("Data Customers");
        btn_Customers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CustomersActionPerformed(evt);
            }
        });
        btn_Data.add(btn_Customers);

        btn_Barang.setText("Data Barang");
        btn_Barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BarangActionPerformed(evt);
            }
        });
        btn_Data.add(btn_Barang);

        jMenuBar1.add(btn_Data);

        btn_Transaksi.setText("Transaksi");

        btn_Pemesanan.setText("Pemesanan");
        btn_Pemesanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PemesananActionPerformed(evt);
            }
        });
        btn_Transaksi.add(btn_Pemesanan);

        jMenuBar1.add(btn_Transaksi);

        jMenu4.setText("Laporan");

        btn_Laporan.setText("Laporan Penjualan");
        btn_Laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LaporanActionPerformed(evt);
            }
        });
        jMenu4.add(btn_Laporan);

        jMenuBar1.add(jMenu4);

        jMenu3.setText("Logout");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tf_namaKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_namaKaryawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_namaKaryawanActionPerformed

    private void tf_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_passActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "insert into user (ID_User,Nama,No_HP,Username,"
            + "Password,Jenis_Kelamin,Alamat) "
            + "values (?,?,?,?,?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_IdUser.getText());
            stat.setString(2, tf_namaKaryawan.getText());//
            stat.setString(3, tf_nohp.getText());
            stat.setString(4, tf_username.getText());
            stat.setString(5, tf_pass.getText());
            stat.setString(6, (String) cb_kel.getSelectedItem());
            //stat.setString(6, tf_pin.getText());
            stat.setString(7, ta_alamat.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil");
            auto_user();
            kosong_user();
            //new FormLoginUtama().setVisible(true);
            //tfregist_user.requestFocus();
            //dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Disimpan " + e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tf_nmBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nmBarangActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "select * from barang where Nama = '" + tf_nmBarang.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kdbarang = hasil.getString("Kode_Barang");
                String ssatuan = hasil.getString("Harga");
                String stumbal_stok = hasil.getString("Jumlah");
                tumbal_stok.setText(stumbal_stok);
                jl_satuan.setText(ssatuan);
                tf_kdBarang1.setText(kdbarang);

            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_tf_nmBarangActionPerformed

    private void tf_nmBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_nmBarangKeyReleased
        // TODO add your handling code here:
        try {
            String sql = "select * from stok_barang where nama_barang = '" + tf_nmBarang.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String kdbarang = hasil.getString("kd_barang");
                String ssatuan = hasil.getString("harga_satuan");
                String stumbal_stok = hasil.getString("jumlah_barang");
                //tumbal_stok.setText(stumbal_stok);
                jl_satuan.setText(ssatuan);
                tf_kdBarang1.setText(kdbarang);
            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_tf_nmBarangKeyReleased

    private void jl_satuanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jl_satuanAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jl_satuanAncestorAdded

    private void tf_satuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_satuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_satuanActionPerformed

    private void tf_jumlahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_jumlahBarangActionPerformed
        // TODO add your handling code here:
        int a = Integer.parseInt(jl_satuan.getText());
        int b = Integer.parseInt(tf_jumlahBarang.getText());
        //kondisi();
        int stotal = a * b;
        NumberFormat nf = NumberFormat.getInstance();
        jl_totalBayar.setText("Rp " +nf.format(stotal));
        tumbal_bayar.setText(""+stotal);
    }//GEN-LAST:event_tf_jumlahBarangActionPerformed

    private void tf_jumlahBarangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_jumlahBarangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_jumlahBarangKeyReleased

    private void tf_namaCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_namaCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_namaCustActionPerformed

    private void tf_nohp2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_nohp2KeyTyped
        // TODO add your handling code here:
        char enter=evt.getKeyChar();

        if(!(Character.isDigit(enter))){

            evt.consume();
        }
    }//GEN-LAST:event_tf_nohp2KeyTyped

    private void submit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submit1ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "insert into pembelian (Tanggal,Nama_barang,Kode_Barang,Harga,"
            + "QTY,Total_Pembayaran,ID_Customers,Nama_Cust,No_HP,Nomor_Transaksi,ID_User,Nama_User) "
            + "values (?,?,?,?,?,?,?,?,?,?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_tanggal1.getText());
            stat.setString(2, tf_nmBarang.getText());//
            stat.setString(3, tf_kdBarang1.getText());
            stat.setString(4, jl_satuan.getText());
            stat.setString(5, tf_jumlahBarang.getText());
            stat.setString(6, tumbal_bayar.getText());
            stat.setString(7, ID_Cust.getText());
            stat.setString(8, tf_namaCust.getText());
            stat.setString(9, tf_nohp2.getText());
            stat.setString(10,tf_kdTransaksi.getText());
            stat.setString(11,tf_IdVerif.getText());
            stat.setString(12,tf_NamaVerif.getText());
            

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil");
            Update_Stok();
            daftar_list();
            kosong1();
            dataTable();

            //new FormLoginUtama().setVisible(true);
            //tfregist_user.requestFocus();

            /*barang_keluar b= new barang_keluar();
            b.jl_verif.setText(""+tumbal_verif.getText());
            b.setVisible(true);*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal " + e);
        }

    }//GEN-LAST:event_submit1ActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        // TODO add your handling code here:
        //bukti();
        virtual_btn();
        try{
            String NamaFile = "./src/report/pembelian.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/ms_rumahazza","root","");
            HashMap param = new HashMap();
            //Mengambil parameter
            param.put("no",tf_kdTransaksi.getText());
                   
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, param, koneksi);
            JasperViewer.viewReport(JPrint,false);
        }catch(Exception ex){
            System.out.println(ex);
        }
        code_transaksi();
        kosong2();
        reset_list();
    }//GEN-LAST:event_submitActionPerformed

    private void tabeloutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeloutMouseClicked
        // TODO add your handling code here:
        int bar = tabelout.getSelectedRow();
        String a = tabmode.getValueAt (bar, 0).toString();

        tf_nmBarang.setText(a);
    }//GEN-LAST:event_tabeloutMouseClicked

    private void pembeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pembeliMouseClicked
        // TODO add your handling code here:
        /*int bar = tabelout.getSelectedRow();
        String a = tabmode.getValueAt (bar, 0).toString();
        String b = tabmode.getValueAt (bar, 0).toString();
        String c = tabmode.getValueAt (bar, 0).toString();
        String d = tabmode.getValueAt (bar, 0).toString();
        String e = tabmode.getValueAt (bar, 0).toString();
        
        tf_kdBarang1.setText(a);
        tf_nmBarang.setText(b);
        jl_satuan.setText(c);
        tf_jumlahBarang.setText(d);
        tumbal_bayar.setText(e);
        */
    }//GEN-LAST:event_pembeliMouseClicked

    private void tf_IdCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_IdCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_IdCustActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "insert into customers (ID_Cust,Nama,No_HP,Email,"
            + "Jenis_Kelamin,Alamat) "
            + "values (?,?,?,?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_IdCust.getText());
            stat.setString(2, tf_nama1.getText());//
            stat.setString(3, tf_nohp1.getText());
            stat.setString(4, tf_email1.getText());
            stat.setString(5, (String) cb_kel1.getSelectedItem());
            //stat.setString(6, tf_pin.getText());
            stat.setString(6, ta_alamat1.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil");
            auto_cust();
            kosong_cust();
            //kosong();
            //new FormLoginUtama().setVisible(true);
            //tfregist_user.requestFocus();
            //dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal " + e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tf_nama1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_nama1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_nama1ActionPerformed

    private void btn_BarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BarangActionPerformed
        // TODO add your handling code here:
        if (evt.getSource()==btn_Barang){
            MainFrame.setVisible(false);
            Frame_dataKaryawan.setVisible(false);
            //dataTable4();
            Frame_dataCustomers.setVisible(false);
            Frame_dataBarang.setVisible(true);
            Frame_transaksiPemesanan.setVisible(false);
            Frame_transaksiNota.setVisible(false);
        }
    }//GEN-LAST:event_btn_BarangActionPerformed

    private void btn_KaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_KaryawanActionPerformed
        // TODO add your handling code here:
        if (evt.getSource()==btn_Karyawan){
            MainFrame.setVisible(false);
            Frame_dataKaryawan.setVisible(true);
            //dataTable4();
            Frame_dataCustomers.setVisible(false);
            Frame_dataBarang.setVisible(false);
            Frame_transaksiPemesanan.setVisible(false);
            Frame_transaksiNota.setVisible(false);
            Frame_Laporan.setVisible(false);
            
        }
    }//GEN-LAST:event_btn_KaryawanActionPerformed

    private void btn_CustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CustomersActionPerformed
        // TODO add your handling code here:
        if (evt.getSource()==btn_Customers){
            MainFrame.setVisible(false);
            Frame_dataKaryawan.setVisible(false);
            //dataTable4();
            Frame_dataCustomers.setVisible(true);
            Frame_dataBarang.setVisible(false);
            Frame_transaksiPemesanan.setVisible(false);
            Frame_transaksiNota.setVisible(false);
            Frame_Laporan.setVisible(false);
        }
    }//GEN-LAST:event_btn_CustomersActionPerformed

    private void btn_PemesananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PemesananActionPerformed
        // TODO add your handling code here:
        if (evt.getSource()==btn_Pemesanan){
            MainFrame.setVisible(false);
            Frame_dataKaryawan.setVisible(false);
            //dataTable4();
            Frame_dataCustomers.setVisible(false);
            Frame_dataBarang.setVisible(false);
            Frame_transaksiPemesanan.setVisible(true);
            dataTable();
            Frame_transaksiNota.setVisible(false);
            Frame_Laporan.setVisible(false);
        }
    }//GEN-LAST:event_btn_PemesananActionPerformed

    private void modeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeActionPerformed
        // TODO add your handling code here:
        if (mode.getSelectedItem()=="[MODE] INPUT BARANG") {
            kosong();
            auto_barang();
            new InventoryFrame().setVisible(false);
        }else if (mode.getSelectedItem()=="[MODE] PERBARUI BARANG"){
            new InventoryFrame().setVisible(true);
        }
    }//GEN-LAST:event_modeActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        try {
                String sql = "delete from barang where Kode_Barang='"+tf_kdBarang.getText()+"'";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        "Berhasil");
                auto_barang();
                kosong();
                update_tabel_barang();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Data gagal direset" +e);
            }
        auto_barang();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "update barang set Nama=?,Harga=?,Jumlah=? where Kode_Barang='"+tf_kdBarang.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_namaBarang.getText());
            stat.setString(2, tf_harga.getText());
            stat.setString(3, tf_jumlah.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,
                "berhasil");
            update_tabel_barang();
            kosong();
            tf_kdBarang.requestFocus();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "gagal" +e);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "insert into barang (Tanggal,Kode_Barang,Nama,Harga,"
            + "Jumlah,ID_User,Nama_User) "
            + "values (?,?,?,?,?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_tanggal.getText());
            stat.setString(2, tf_kdBarang.getText());//
            stat.setString(3, tf_namaBarang.getText());
            stat.setString(4, tf_harga.getText());
            stat.setString(5, tf_jumlah.getText());
            stat.setString(6, tf_VerifId.getText());
            stat.setString(7, tf_VerifNama.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Berhasil");
            kosong();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal " + e);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tf_kdBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tf_kdBarangKeyTyped
        // TODO add your handling code here:
        char enter=evt.getKeyChar();

        if(!(Character.isDigit(enter))){

            evt.consume();
        }
    }//GEN-LAST:event_tf_kdBarangKeyTyped

    private void tf_kdBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_kdBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_kdBarangActionPerformed

    private void ID_CustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ID_CustActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "select * from customers where ID_Cust = '" + ID_Cust.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String snama = hasil.getString("Nama");
                String snohp = hasil.getString("No_HP");
                tf_namaCust.setText(snama);
                tf_nohp2.setText(snohp);
            }
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_ID_CustActionPerformed

    private void tf_dariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_dariMouseClicked
        // TODO add your handling code here:
        tf_dari.setText("");
    }//GEN-LAST:event_tf_dariMouseClicked

    private void tf_dariMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_dariMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_dariMouseExited

    private void tf_dariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_dariActionPerformed
        // TODO add your handling code here:
        Object[] Baris ={"No Pemesanan","Nama","Tanggal Pemesanan","Jam Main"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelout2.setModel(tabmode);
        String sql_select = "select * from pemesanan where No_Pemesanan like '%"+tf_dari.getText()+"%' && Tanggal_Main !='Pembatalan'";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql_select);
            while(hasil.next()){
                String a = hasil.getString("No_Pemesanan");
                String b = hasil.getString("Nama");
                String c = hasil.getString("Tanggal_Pemesanan");
                String d = hasil.getString("Jam_Main");
                String[] data={a,b,c,d};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//GEN-LAST:event_tf_dariActionPerformed

    private void mode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mode1ActionPerformed
        // TODO add your handling code here:
        if (mode1.getSelectedItem()=="[MODE] INPUT USER") {
            auto_user();
            kosong_user();
            tf_username.setEnabled(true);
            tf_pass.setEnabled(true);
            cb_kel.setEnabled(true);
        }else if (mode1.getSelectedItem()=="[MODE] PEMBARUAN USER"){
            new UserFrame().setVisible(true);
            auto_user();
            tf_username.setEnabled(false);
            tf_pass.setEnabled(false);
            cb_kel.setEnabled(false);
        }
    }//GEN-LAST:event_mode1ActionPerformed

    private void mode2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mode2ActionPerformed
        // TODO add your handling code here:
        if (mode2.getSelectedItem()=="[MODE] INPUT CUSTOMERS") {
            kosong_cust();
            auto_cust();
            cb_kel1.setEnabled(true);
        }else if (mode2.getSelectedItem()=="[MODE] PEMBARUAN CUSTOMERS"){
            new CustomersFrame().setVisible(true);
            auto_cust();
            cb_kel1.setEnabled(false);
        }
    }//GEN-LAST:event_mode2ActionPerformed

    private void btn_LaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LaporanActionPerformed
        // TODO add your handling code here:
        if (evt.getSource()==btn_Laporan){
            MainFrame.setVisible(false);
            Frame_dataKaryawan.setVisible(false);
            Frame_dataCustomers.setVisible(false);
            Frame_dataBarang.setVisible(false);
            Frame_transaksiPemesanan.setVisible(false);
            Laporan();
            Frame_transaksiNota.setVisible(true);
            Frame_Laporan.setVisible(false);
        }
    }//GEN-LAST:event_btn_LaporanActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "update user set nama=?,No_HP=?,Alamat=? where ID_User='"+tf_IdUser.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_namaKaryawan.getText());
            stat.setString(2, tf_nohp.getText());
            stat.setString(3, ta_alamat.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,
                    "Berhasil");
            update_tabel_user();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal" +e);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
        // TODO add your handling code here:
        dispose();
        new PublicFrame().setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
            try {
                String sql = "delete from user where ID_User='"+tf_IdUser.getText()+"'";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        "Berhasil");
                auto_user();
                kosong_user();
                update_tabel_user();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Data gagal direset" +e);
            }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        try {
            String sql = "update customers set Nama=?,No_HP=?,Email=?,Alamat=? where ID_Cust='"+tf_IdCust.getText()+"'";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, tf_nama1.getText());
            stat.setString(2, tf_nohp1.getText());
            stat.setString(3, tf_email1.getText());
            stat.setString(4, ta_alamat1.getText());
            stat.executeUpdate();
            JOptionPane.showMessageDialog(null,
                    "Berhasil");
            update_tabel_customers();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal" +e);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        try {
                String sql = "delete from customers where ID_Cust='"+tf_IdCust.getText()+"'";
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,
                        "Berhasil");
                auto_cust();
                kosong_cust();
                update_tabel_customers();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                        "Data gagal direset" +e);
            }
        update_tabel_customers();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void tabelout2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelout2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelout2MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //SELECT * FROM laporan_transaksi WHERE Tanggal BETWEEN '14/07/2020' and '14/07/2020' 
        try{
            String NamaFile = "./src/report/bulanan.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/ms_rumahazza","root","");
            
            HashMap param = new HashMap();
            //Mengambil parameter
            param.put("dari",tf_dari.getText());
            param.put("sampai",tf_sampai.getText());
                   
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, param, koneksi);
            JasperViewer.viewReport(JPrint,false);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tf_cari1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_cari1MouseClicked
        // TODO add your handling code here:
        tf_cari1.setText("");
    }//GEN-LAST:event_tf_cari1MouseClicked

    private void tf_cari1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_cari1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cari1MouseExited

    private void tf_cari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tf_cari1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tf_cari1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
            String NamaFile = "./src/report/pembelian.jasper";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/ms_rumahazza","root","");
            
            HashMap param = new HashMap();
            //Mengambil parameter
            param.put("no",tf_cari1.getText());
                   
            JasperPrint JPrint = JasperFillManager.fillReport(NamaFile, param, koneksi);
            JasperViewer.viewReport(JPrint,false);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tabelout3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelout3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelout3MouseClicked

    private void tf_sampaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tf_sampaiMouseClicked
        // TODO add your handling code here:
        tf_sampai.setText("");
    }//GEN-LAST:event_tf_sampaiMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Frame_Laporan;
    private javax.swing.JPanel Frame_dataBarang;
    private javax.swing.JPanel Frame_dataCustomers;
    private javax.swing.JPanel Frame_dataKaryawan;
    private javax.swing.JPanel Frame_transaksiNota;
    private javax.swing.JPanel Frame_transaksiPemesanan;
    private javax.swing.JTextField ID_Cust;
    private javax.swing.JPanel MainFrame;
    private javax.swing.JPanel View;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel bg1;
    private javax.swing.JLabel bg2;
    private javax.swing.JLabel bg3;
    private javax.swing.JLabel bg4;
    private javax.swing.JLabel bg5;
    private javax.swing.JLabel bg6;
    private javax.swing.JMenuItem btn_Barang;
    private javax.swing.JMenuItem btn_Customers;
    public static javax.swing.JMenu btn_Data;
    private javax.swing.JMenuItem btn_Karyawan;
    private javax.swing.JMenuItem btn_Laporan;
    public static javax.swing.JMenuItem btn_Pemesanan;
    public static javax.swing.JMenu btn_Transaksi;
    private javax.swing.JComboBox<String> cb_kel;
    private javax.swing.JComboBox<String> cb_kel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jl_satuan;
    private javax.swing.JLabel jl_sum;
    private javax.swing.JLabel jl_totalBayar;
    public static javax.swing.JComboBox<String> mode;
    public static javax.swing.JComboBox<String> mode1;
    public static javax.swing.JComboBox<String> mode2;
    private javax.swing.JTable pembeli;
    private javax.swing.JButton submit;
    private javax.swing.JButton submit1;
    public static javax.swing.JTextArea ta_alamat;
    public static javax.swing.JTextArea ta_alamat1;
    private javax.swing.JTable tabelout;
    private javax.swing.JTable tabelout2;
    private javax.swing.JTable tabelout3;
    public static javax.swing.JTextField tf_IdCust;
    public static javax.swing.JTextField tf_IdUser;
    public static javax.swing.JTextField tf_IdVerif;
    public static javax.swing.JTextField tf_NamaVerif;
    public static javax.swing.JTextField tf_VerifId;
    public static javax.swing.JTextField tf_VerifNama;
    private javax.swing.JTextField tf_cari1;
    private javax.swing.JTextField tf_dari;
    public static javax.swing.JTextField tf_email1;
    public static javax.swing.JTextField tf_harga;
    public static javax.swing.JTextField tf_jumlah;
    private javax.swing.JTextField tf_jumlahBarang;
    public static javax.swing.JTextField tf_kdBarang;
    private javax.swing.JTextField tf_kdBarang1;
    private javax.swing.JTextField tf_kdTransaksi;
    public static javax.swing.JTextField tf_nama1;
    public static javax.swing.JTextField tf_namaBarang;
    private javax.swing.JTextField tf_namaCust;
    public static javax.swing.JTextField tf_namaKaryawan;
    private javax.swing.JTextField tf_nmBarang;
    public static javax.swing.JTextField tf_nohp;
    public static javax.swing.JTextField tf_nohp1;
    private javax.swing.JTextField tf_nohp2;
    private javax.swing.JPasswordField tf_pass;
    private javax.swing.JTextField tf_sampai;
    private javax.swing.JTextField tf_satuan;
    private javax.swing.JTextField tf_sum;
    private javax.swing.JTextField tf_tanggal;
    private javax.swing.JTextField tf_tanggal1;
    private javax.swing.JTextField tf_username;
    private javax.swing.JTextField tf_verif;
    private javax.swing.JLabel tumbal;
    private javax.swing.JLabel tumbal_bayar;
    private javax.swing.JLabel tumbal_stok;
    private javax.swing.JLabel tumbal_update_stok;
    // End of variables declaration//GEN-END:variables
}
