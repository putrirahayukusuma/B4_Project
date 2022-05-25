package codingan;

import static codingan.dashboard.username;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author B4
 */

public class transaksi_beli extends javax.swing.JFrame {
    private Statement st;
    private ResultSet rs;
    public void kosong() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void tambahTransaksi() {
        int jumlah, harga, total;
        jumlah = Integer.valueOf(txt_qty.getText());
        harga = Integer.valueOf(txt_harga.getText());
        total = jumlah * harga;
        
        txt_totalBeli.setText(String.valueOf(total));
        
        tampilan();
        totalBiaya();
    }
    
    private void otobarang() {
        try {
            st = connection.configDB().createStatement();
            rs = st.executeQuery("SELECT * FROM hijab WHERE id_hijab = '"
                    + cbid.getSelectedItem() + "'");
            while (rs.next()) {
                
                String d = rs.getString("harga_beli");
              
                txt_harga.setText(d);
            }
        } catch (Exception e) {
        }
    }
    
    public void tambahkan1() {
       try {
            String sql = "INSERT INTO transaksi_beli (`id_transBeli`, `id_hijab`, `id_supp`, `qty_beli`, `waktu_beli`, `total_beli`) "
                    + "VALUES ('" + txt_idbeli.getText() + "','" + cbid.getSelectedItem() + "','" + jComboBox2.getSelectedItem() + "','" + txt_qty.getText()
                   + "','" + txt_tanggal.getText() + "','" + txt_totalBeli.getText() + "')";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        tampilan();
        kosong();  
    }
    
     
    public void totalBiaya() {
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(jTable1.getValueAt(i, 4).toString());
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 6).toString());
            totalBiaya = jumlahBarang * hargaBarang;
        }
    }
  
    
    public void clear2() {
            txt_idbeli.setText("");
            cbid.setSelectedItem("");
            jComboBox2.setSelectedItem("");
            txt_harga.setText("");
            txt_qty.setText("");
            txt_tanggal.setText("");
            txt_totalBeli.setText("");
            
    }
    
    public transaksi_beli(String username) {
        initComponents();
        this.setResizable(false);
        setTitle("Transaksi Beli");
        setNama(username);
        tampilan();
        waktu();
        tampil_combo1();
        tampil_combo2();
    }

    public void setNama(String user) {
        transBeli_nama.setText(user);
    }
    
    private void waktu() {
        Timer t2 = new Timer();
        t2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                waktu.setText(new SimpleDateFormat("EEEE dd MMMM yyyy hh:mm:ss").format(new java.util.Date()));
            }
        }, 0, 1000);
    }
    
    private void RubahKeExcel(JTable jTable2, File file) {
        try {
            DefaultTableModel Model_Data = (DefaultTableModel) jTable2.getModel();
            FileWriter ObjWriter = new FileWriter(file);
            for (int i = 0; i < Model_Data.getColumnCount(); i++) {
                ObjWriter.write(Model_Data.getColumnName(i) + "\t");
            }

            ObjWriter.write("\n");

            for (int i = 0; i < Model_Data.getRowCount(); i++) {
                for (int j = 0; j < Model_Data.getColumnCount(); j++) {
                    ObjWriter.write(Model_Data.getValueAt(i, j).toString() + "\t");
                }
                ObjWriter.write("\n");
            }

            ObjWriter.close();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void tampilan(){
        
    DefaultTableModel  tbl = new DefaultTableModel();
      tbl.addColumn("No.");
      tbl.addColumn("Id Transaksi Beli");
      tbl.addColumn("Id Hijab");
      tbl.addColumn("Id Supplier");
      tbl.addColumn("QTY Beli");
      tbl.addColumn("Waktu");
      tbl.addColumn("Harga Beli");
      tbl.addColumn("Total Beli");
      
    try {
     int no = 1;
     int total = 0;
     int total_barang = 0;
     String sql = "select transaksi_beli.id_transBeli, hijab.id_hijab, supplier.id_supp, hijab.harga_beli,"
             + " transaksi_beli.qty_beli, transaksi_beli.waktu_beli, transaksi_beli.total_beli from transaksi_beli"
             + " join hijab on transaksi_beli.id_hijab = hijab.id_hijab join supplier on transaksi_beli.id_supp = supplier.id_supp"
             + " group by transaksi_beli.id_transBeli;";
     System.out.println(sql);
     java.sql.Connection conn = (Connection)connection.configDB();
     java.sql.Statement stm = conn.createStatement();
     java.sql.ResultSet rs = stm.executeQuery(sql);
   
     while (rs.next()) {
         tbl.addRow(new Object [ ] {
             no++,
             rs.getString("id_transBeli"),
             rs.getString("id_hijab"),
             rs.getString("id_supp"),
             rs.getString("qty_beli"),
             rs.getString("waktu_beli"),
             rs.getString("harga_beli"),
             rs.getString("total_beli")
     });
         total_barang += Integer.parseInt(rs.getString("total_beli"));
         txt_pengeluaran.setText(String.valueOf(total_barang));
         total += Integer.parseInt(rs.getString("total_beli"));
         jTable1.setModel(tbl);
     }
    }catch(Exception e) {
        JOptionPane.showMessageDialog(null, "Koneksi Database Gagal"+ e.getMessage());
    }
    }
    
    private void tampil_combo1() {
        try {
            String sql = "SELECT * FROM hijab";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cbid.addItem(rs.getString("id_hijab"));
            }
            rs.last();
            int jumlahdata = rs.getRow();
        } catch (Exception e) {
        }
    }
    
    private void tampil_combo2() {
        try {
            String sql = "SELECT * FROM supplier";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("id_supp"));
            }
            rs.last();
            int jumlahdata = rs.getRow();
        } catch (Exception e) {
        }
    }
    
    private void CariData(String key){
        
      DefaultTableModel tbl = new DefaultTableModel();
      tbl.addColumn("No");
      tbl.addColumn("Id Transaksi Beli");
      tbl.addColumn("Id Hijab");
      tbl.addColumn("Id Supplier");
      tbl.addColumn("QTY Beli");
      tbl.addColumn("Waktu");
      tbl.addColumn("Harga Beli");
      tbl.addColumn("Total Beli");

        String sql = "select transaksi_beli.id_transBeli, hijab.id_hijab, supplier.id_supp, hijab.harga_beli, transaksi_beli.qty_beli, transaksi_beli.waktu_beli, transaksi_beli.total_beli from transaksi_beli join hijab on transaksi_beli.id_hijab = hijab.id_hijab join supplier on transaksi_beli.id_supp = supplier.id_supp where transaksi_beli.id_transBeli LIKE '%"+key+"%' or hijab.id_hijab LIKE '%"+key+"%' or supplier.id_supp LIKE '%"+key+"%'";
        System.out.println("sql");
        try{
           int no = 1;
           java.sql.Connection conn = (Connection)connection.configDB();
           java.sql.PreparedStatement stm = conn.prepareStatement(sql);
           java.sql.ResultSet rs = stm.executeQuery();
           while(rs.next()){
               tbl.addRow(new Object[]{
                 no++,
                 rs.getString("id_transBeli"),
                 rs.getString("id_hijab"),
                 rs.getString("id_supp"),
                 rs.getString(4),
                 rs.getString("waktu_beli"),
                 rs.getString("harga_beli"),
                 rs.getString("total_beli")
               });
            jTable1.setModel(tbl);
           }
           
       }
       catch(Exception e){
           e.getMessage();
       }   
    }
    
    private void cari_hari() {
    DefaultTableModel  tbl = new DefaultTableModel();
      tbl.addColumn("No.");
      tbl.addColumn("Id Transaksi Beli");
      tbl.addColumn("Id Hijab");
      tbl.addColumn("Id Supplier");
      tbl.addColumn("QTY Beli");
      tbl.addColumn("Waktu");
      tbl.addColumn("Harga Beli");
      tbl.addColumn("Total Beli");

    try {
        int no =1;
        int total = 0;
        int total_barang = 0;
        String sql = "select transaksi_beli.id_transBeli, hijab.id_hijab, supplier.id_supp, hijab.harga_beli, transaksi_beli.qty_beli, transaksi_beli.waktu_beli, transaksi_beli.total_beli from transaksi_beli join hijab on transaksi_beli.id_hijab = hijab.id_hijab join supplier on transaksi_beli.id_supp = supplier.id_supp where DAY(waktu_beli)='"+hari.getText()+"' AND MONTH(waktu_beli)='"+bulan.getText()+"' AND YEAR(waktu_beli)='"+tahun.getText()+"' group by transaksi_beli.id_transBeli;";
        System.out.println(sql);
        java.sql.Connection conn = (Connection)connection.configDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        
        while (rs.next()) {
         tbl.addRow(new Object [ ] {
             no++,
             rs.getString("id_transBeli"),
             rs.getString("id_hijab"),
             rs.getString("id_supp"),
             rs.getString("qty_beli"),
             rs.getString("waktu_beli"),
             rs.getString("harga_beli"),
             rs.getString("total_beli")
     });
        total += Integer.parseInt(rs.getString("total_beli"));
        txt_pengeluaran.setText(String.valueOf(total));
        jTable1.setModel(tbl);
        }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Koneksi Database Gagal"+ e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_idbeli = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        txt_qty = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_pengeluaran = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        waktu = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        cari = new javax.swing.JButton();
        tampilkanData = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        hari = new javax.swing.JTextField();
        bulan = new javax.swing.JTextField();
        tahun = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_caridata = new javax.swing.JTextField();
        btn_cetak = new javax.swing.JButton();
        cbid = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btn_clear = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txt_totalBeli = new javax.swing.JTextField();
        btn_dashboard = new javax.swing.JButton();
        btn_dataBarang = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_admin = new javax.swing.JButton();
        btn_transaksiJual = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_tanggal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 204));
        jLabel2.setText("ID Transaksi");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 204));
        jLabel3.setText("ID Supplier");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 204));
        jLabel4.setText("ID Hijab");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, 30));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 204));
        jLabel5.setText("Harga");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, -1, 30));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 204));
        jLabel6.setText("Jumlah");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, 30));
        jPanel1.add(txt_idbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, 230, 30));

        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 530, 230, 30));

        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        jPanel1.add(txt_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 450, 230, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 204));
        jLabel7.setText("TOTAL PENGELUARAN");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 640, -1, 60));

        txt_pengeluaran.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_pengeluaran.setForeground(new java.awt.Color(255, 51, 204));
        txt_pengeluaran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        txt_pengeluaran.setOpaque(false);
        jPanel1.add(txt_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 650, 230, 40));

        jTable1.setBackground(new java.awt.Color(255, 153, 102));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 410, 810, 230));

        transBeli_nama.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        transBeli_nama.setForeground(new java.awt.Color(204, 0, 204));
        transBeli_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        transBeli_nama.setText("Username");
        jPanel1.add(transBeli_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, -1, -1));

        waktu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        waktu.setForeground(new java.awt.Color(204, 0, 204));
        waktu.setText("Tanggal");
        jPanel1.add(waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        btn_tambah.setBackground(new java.awt.Color(255, 153, 102));
        btn_tambah.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(204, 0, 204));
        btn_tambah.setText("TAMBAH ");
        btn_tambah.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 610, 370, 30));

        btn_hapus.setBackground(new java.awt.Color(255, 153, 102));
        btn_hapus.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(204, 0, 204));
        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 650, 100, 40));

        btn_simpan.setBackground(new java.awt.Color(255, 153, 102));
        btn_simpan.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(204, 0, 204));
        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 650, 110, 40));

        cari.setBackground(new java.awt.Color(255, 153, 102));
        cari.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cari.setForeground(new java.awt.Color(204, 0, 204));
        cari.setText("CARI");
        cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cariActionPerformed(evt);
            }
        });
        jPanel1.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 330, 90, -1));

        tampilkanData.setBackground(new java.awt.Color(255, 153, 102));
        tampilkanData.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tampilkanData.setForeground(new java.awt.Color(204, 0, 204));
        tampilkanData.setText("TAMPILKAN DATA");
        tampilkanData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilkanDataActionPerformed(evt);
            }
        });
        jPanel1.add(tampilkanData, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 370, 170, 30));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 204));
        jLabel8.setText("TANGGAL");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 330, -1, 30));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 204));
        jLabel11.setText("BULAN");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 330, -1, 30));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 204));
        jLabel12.setText("TAHUN");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 330, -1, 30));
        jPanel1.add(hari, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 330, 50, 30));
        jPanel1.add(bulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 330, 50, 30));
        jPanel1.add(tahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 330, 70, 30));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 204));
        jLabel13.setText("CARI DATA");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 370, -1, 30));

        txt_caridata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_caridataActionPerformed(evt);
            }
        });
        txt_caridata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_caridataKeyReleased(evt);
            }
        });
        jPanel1.add(txt_caridata, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 370, 260, 30));

        btn_cetak.setBackground(new java.awt.Color(255, 153, 102));
        btn_cetak.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(204, 0, 204));
        btn_cetak.setText("CETAK");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 650, 110, 40));

        cbid.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbidItemStateChanged(evt);
            }
        });
        jPanel1.add(cbid, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 370, 230, 30));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("MASUKKAN DATA PEMBELIAN BARU");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, -1, 30));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 0, 204));
        jLabel16.setText("Waktu");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, -1, 30));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("LAPORAN TOTAL DATA PEMBELIAN");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 280, -1, -1));

        btn_clear.setBackground(new java.awt.Color(255, 153, 102));
        btn_clear.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(204, 0, 204));
        btn_clear.setText("CLEAR");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel1.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 650, 100, 40));

        btn_edit.setBackground(new java.awt.Color(255, 153, 102));
        btn_edit.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(204, 0, 204));
        btn_edit.setText("EDIT");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel1.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 650, 90, 40));

        jPanel1.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 230, 30));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 0, 204));
        jLabel18.setText("Total Beli");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 570, -1, 30));

        txt_totalBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalBeliActionPerformed(evt);
            }
        });
        jPanel1.add(txt_totalBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, 230, 30));

        btn_dashboard.setBorder(null);
        btn_dashboard.setContentAreaFilled(false);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        jPanel1.add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 150, 70));

        btn_dataBarang.setBorder(null);
        btn_dataBarang.setContentAreaFilled(false);
        btn_dataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dataBarangActionPerformed(evt);
            }
        });
        jPanel1.add(btn_dataBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 220, 70));

        btn_supplier.setBorder(null);
        btn_supplier.setContentAreaFilled(false);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        jPanel1.add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 70, 180, 70));

        btn_admin.setBorder(null);
        btn_admin.setContentAreaFilled(false);
        btn_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adminActionPerformed(evt);
            }
        });
        jPanel1.add(btn_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 140, 70));

        btn_transaksiJual.setBorder(null);
        btn_transaksiJual.setContentAreaFilled(false);
        btn_transaksiJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiJualActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transaksiJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 240, 70));

        jButton2.setBackground(new java.awt.Color(255, 153, 102));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 0, 204));
        jButton2.setText("HITUNG TOTAL");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 330, -1, 30));

        txt_tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tanggalActionPerformed(evt);
            }
        });
        jPanel1.add(txt_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 490, 230, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Transaksi_beli1.jpeg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_caridataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_caridataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_caridataActionPerformed

    private void cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cariActionPerformed
        cari_hari();
    }//GEN-LAST:event_cariActionPerformed

    private void txt_caridataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_caridataKeyReleased
        String key = txt_caridata.getText();

        if (key != "") {
            CariData(key);

        } else {
            tampilan();
        }

    }//GEN-LAST:event_txt_caridataKeyReleased

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        this.setVisible(false);
        new dashboard(transBeli_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_dataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataBarangActionPerformed
        this.setVisible(false);
        new data_barang(transBeli_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dataBarangActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        this.setVisible(false);
        new supplier(transBeli_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adminActionPerformed
        this.setVisible(false);
        new admin(transBeli_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_adminActionPerformed

    private void btn_transaksiJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiJualActionPerformed
        this.setVisible(false);
        new transaksi_jual(transBeli_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transaksiJualActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
       tambahkan1();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int baris = jTable1.rowAtPoint(evt.getPoint());
        String ID_Hijab = jTable1.getValueAt(baris, 1).toString();
       
        if (jTable1.getValueAt(baris, 1) == null) {
            txt_idbeli.setText("");
        } else {
            txt_idbeli.setText(jTable1.getValueAt(baris, 1).toString());
        }
        if (jTable1.getValueAt(baris, 4) == null) {
            txt_qty.setText("");
        } else {
            txt_qty.setText(jTable1.getValueAt(baris, 4).toString());
        }
        if (jTable1.getValueAt(baris, 5) == null) {
            txt_tanggal.setText("");
        } else {
            txt_tanggal.setText(jTable1.getValueAt(baris, 5).toString());
        }
        if (jTable1.getValueAt(baris, 6) == null) {
            txt_harga.setText("");
        } else {
            txt_harga.setText(jTable1.getValueAt(baris, 6).toString());
        }
        if (jTable1.getValueAt(baris, 7) == null) {
            txt_totalBeli.setText("");
        } else {
            txt_totalBeli.setText(jTable1.getValueAt(baris, 7).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       tambahTransaksi();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_tanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_tanggalActionPerformed

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_txt_qtyActionPerformed

    private void tampilkanDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilkanDataActionPerformed
        tampilan();
    }//GEN-LAST:event_tampilkanDataActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        clear2();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        String sql = "UPDATE transaksi_beli Set id_transBeli = '" + txt_idbeli.getText() + "',qty_beli = '" + txt_qty.getText() + "',waktu_beli = '" + txt_tanggal.getText() 
                + "' WHERE id_transBeli = '" + txt_idbeli.getText() + "'";
        try {
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di edit");
            tampilan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EROR \n" + e.getMessage());
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void txt_totalBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalBeliActionPerformed
        
    }//GEN-LAST:event_txt_totalBeliActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
      
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        String sql = "DELETE FROM transaksi_beli WHERE id_transBeli = '" + txt_idbeli.getText() + "'";
        try {
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di Hapus");
            tampilan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EROR \n" + e.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        File pathDefault = new File("C:\\Users\\LENOVO\\Downloads");
        if (!pathDefault.exists()) {
            pathDefault.mkdir();
        } else if (pathDefault.exists()) {
            java.util.Date tgl = new java.util.Date();
            File fp = new File("C:\\Users\\LENOVO\\Downloads\\excel" + tgl.getTime() + ".xls");
            JOptionPane.showMessageDialog(null, "DATA SUKSES DI SIMPAN");
            RubahKeExcel(jTable1, fp);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
         cetak();
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void cbidItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbidItemStateChanged
        // TODO add your handling code here:
        otobarang();
    }//GEN-LAST:event_cbidItemStateChanged
    
     private void cetak() {
        try {
            jTable1.print();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Maaf Data Table Anda Kosong");
        }
    }
     
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
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_beli(transBeli_nama.getText()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_admin;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_dataBarang;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_transaksiJual;
    private javax.swing.JTextField bulan;
    private javax.swing.JButton cari;
    private javax.swing.JComboBox<String> cbid;
    private javax.swing.JTextField hari;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tahun;
    private javax.swing.JButton tampilkanData;
    public static final javax.swing.JLabel transBeli_nama = new javax.swing.JLabel();
    private javax.swing.JTextField txt_caridata;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_idbeli;
    private javax.swing.JTextField txt_pengeluaran;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_tanggal;
    private javax.swing.JTextField txt_totalBeli;
    private javax.swing.JLabel waktu;
    // End of variables declaration//GEN-END:variables
}
