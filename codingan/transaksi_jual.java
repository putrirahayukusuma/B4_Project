package codingan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author B4
 */

public class transaksi_jual extends javax.swing.JFrame {
    private Statement st;
    private ResultSet rs;
    public transaksi_jual(String username) {
        this.setResizable(false);
        initComponents();
        tampil_combo1();
        tampil_combo2();
        setTitle("Transaksi Jual");
        setNama(username);
        tampilan();
    }
    
    public void setNama(String user) {
        transJual_nama.setText(user);
        
    }
    
    
    
    public void kosong() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void kosong1() {
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        
        while (model.getRowCount()>0) {
            model.removeRow(0);
        }
    }
    
    public void tambahTransaksi() {
        int jumlah, harga, total;
        jumlah = Integer.valueOf(txt_qty.getText());
        harga = Integer.valueOf(txt_harga.getText());
        total = jumlah * harga;
        
        txt_total.setText(String.valueOf(total));
        
        load_data();
        tampilan();
        totalBiaya();
    }
    
    public void totalBiaya() {
        int jumlahBaris = jTable1.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(jTable1.getValueAt(i, 3).toString());
            hargaBarang = Integer.parseInt(jTable1.getValueAt(i, 5).toString());
            totalBiaya = jumlahBarang * hargaBarang;
        }
    }
    
    int no = 1;
    int totalJumlah;
    int totalHarga;
    String IDbarang;
    String IDadmin;
    public void load_data() {
        
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.addRow(new Object[]{
            no++,
            id_transjual.getText(),
            txt_waktu.getText(),
            txt_qty.getText(),
            idbarang.getSelectedItem(),
            txt_harga.getText(),
            txt_total.getText(),
            idAdmin.getSelectedItem()
        });
    }
    
    public void clear2() {
            id_transjual.setText("");
            txt_waktu.setText("");
            txt_qty.setText("");
            idbarang.setSelectedItem("");
            txt_harga.setText("");
            idAdmin.setSelectedItem("");
            txt_total.setText("");
    }
    
    private void tampil_combo1() {
        try {
            String sql = "SELECT * FROM hijab";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idbarang.addItem(rs.getString("id_hijab"));
            }
            rs.last();
            int jumlahdata = rs.getRow();
        } catch (Exception e) {
        }
    }
    
    private void tampil_combo2() {
        try {
            String sql = "SELECT * FROM admin";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idAdmin.addItem(rs.getString("id_admin"));
            }
            rs.last();
            int jumlahdata = rs.getRow();
        } catch (Exception e) {
        }
    }
        
        
    private void tampilan(){
        
      DefaultTableModel  tbl = new DefaultTableModel();
      tbl.addColumn("No.");
      tbl.addColumn("Id Transaksi Jual");
      tbl.addColumn("Waktu Jual");
      tbl.addColumn("QTY");
      tbl.addColumn("ID Hijab");
      tbl.addColumn("Harga Jual");
      tbl.addColumn("Total Bayar");
      tbl.addColumn("ID Admin");
    
    try {
     int  no = 1;
     int total = 0;
     int total_barang = 0;
     String sql = "select transaksi_jual.id_transJual, transaksi_jual.waktu, transaksi_jual.qty, "
             + " hijab.id_hijab, hijab.harga_jual, transaksi_jual.total_bayar,"
             + " transaksi_jual.id_admin from transaksi_jual "
             + " join hijab on transaksi_jual.id_hijab = hijab.id_hijab"
             + " group by transaksi_jual.id_transJual";
     java.sql.Connection conn = (Connection)connection.configDB();
     java.sql.Statement stm = conn.createStatement();
     java.sql.ResultSet rs = stm.executeQuery(sql);
     
     while (rs.next()) {
         tbl.addRow(new Object [ ] {
             no++,
             rs.getString("id_transJual"),
             rs.getString("waktu"),
             rs.getString(3),
             rs.getString("id_hijab"),
             rs.getString("harga_jual"),
             rs.getString("total_bayar"),
             rs.getString("id_admin")
     });
        total_barang += Integer.parseInt(rs.getString(3));
        txt_hasil.setText(String.valueOf(total_barang));
        total += Integer.parseInt(rs.getString("total_bayar"));
        txt_pendapatan.setText(String.valueOf(total));
        jTable1.setModel(tbl);
     }
    }catch(Exception e) {
        JOptionPane.showMessageDialog(null, "Koneksi Database Gagal"+ e.getMessage());
    }
    }
    
    private void cari_hari() {
    DefaultTableModel  tbl = new DefaultTableModel();
      tbl.addColumn("No.");
      tbl.addColumn("Id Transaksi Jual");
      tbl.addColumn("Waktu Jual");
      tbl.addColumn("QTY");
      tbl.addColumn("ID Hijab");
      tbl.addColumn("Harga Jual");
      tbl.addColumn("Total Bayar");
      tbl.addColumn("ID Admin");

    try {
        int no = 1;
        int total = 0;
        int total_barang = 0;
        String sql = "select transaksi_jual.id_transJual, transaksi_jual.waktu, sum(det_trsansaksi.qty),"
                + "det_trsansaksi.harga_jual, det_trsansaksi.id_hijab, transaksi_jual.total_bayar, transaksi_jual.id_admin "
                + "from transaksi_jual join det_trsansaksi on transaksi_jual.id_transJual = det_trsansaksi.id_transJual "
                + "where DAY(waktu)='"+hari.getText()+"' AND MONTH(waktu)='"+bulan.getText()+"' AND YEAR(waktu)='"+tahun.getText()+"' "
                + "group by transaksi_jual.id_transJual;";
        java.sql.Connection conn = (Connection)connection.configDB();
        java.sql.Statement stm = conn.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        
        while (rs.next()) {
         tbl.addRow(new Object [ ] {
             no++,
             rs.getString("id_transJual"),
             rs.getString("waktu"),
             rs.getString(3),
             rs.getString("id_hijab"),
             rs.getString("harga_jual"),
             rs.getString("total_bayar"),
             rs.getString("id_admin")
     });
        total_barang += Integer.parseInt(rs.getString(3));
        txt_hasil.setText(String.valueOf(total_barang));
        total += Integer.parseInt(rs.getString("total_bayar"));
        txt_pendapatan.setText(String.valueOf(total));
        jTable1.setModel(tbl);
        }
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, "Koneksi Database Gagal"+ e.getMessage());
    }
 }
    
    private void CariData(String Key){
        
      DefaultTableModel  tbl = new DefaultTableModel();
      tbl.addColumn("No.");
      tbl.addColumn("Id Transaksi Jual");
      tbl.addColumn("Waktu Jual");
      tbl.addColumn("QTY");
      tbl.addColumn("ID Hijab");
      tbl.addColumn("Harga Jual");
      tbl.addColumn("Total Bayar");
      tbl.addColumn("ID Admin");

        String sql = "select transaksi_jual.id_transJual, transaksi_jual.waktu, transaksi_jual.qty, "
             + " hijab.id_hijab, hijab.harga_jual, transaksi_jual.total_bayar,"
             + " transaksi_jual.id_admin from transaksi_jual "
             + " join hijab on transaksi_jual.id_hijab = hijab.id_hijab"
             + " where transaksi_jual.id_transJual LIKE '%"+Key+"%' "
                + "or hijab.id_hijab LIKE '%"+Key+"%' or admin.id_admin LIKE '%"+Key+"%'";
        System.out.println("sql");
        
        try{
           int no = 1;
           java.sql.Connection conn = (Connection)connection.configDB();
           java.sql.PreparedStatement stm = conn.prepareStatement(sql);
           java.sql.ResultSet rs = stm.executeQuery();
        
           while(rs.next()){
             tbl.addRow(new Object [ ] {
             no++,
             rs.getString("id_transJual"),
             rs.getString("waktu"),
             rs.getString(3),
             rs.getString("id_hijab"),
             rs.getString("harga_jual"),
             rs.getString("total_bayar"),
             rs.getString("id_admin")
     });
           }
           jTable1.setModel(tbl);
       }
       catch(Exception e){
           e.getMessage();
       }   
    }
    
    public void tambahkan1() {
       try {
            String sql = "INSERT INTO transaksi_jual (`id_transJual`, `id_hijab`, `waktu`, `qty`, `total_bayar`, id_admin) "
                    + "VALUES ('" + id_transjual.getText() + "','" + idbarang.getSelectedItem() + "','" + txt_waktu.getText() + "','" + txt_qty.getText() + "','" + txt_total.getText()
                   + "','" + idAdmin.getSelectedItem() + "')";
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
    
    private void otobarang() {
        try {
            st = connection.configDB().createStatement();
            rs = st.executeQuery("SELECT * FROM hijab WHERE id_hijab = '"
                    + idbarang.getSelectedItem() + "'");
            while (rs.next()) {
               
                
                String d = rs.getString("harga_jual");
              
                txt_harga.setText(d);
            }
        } catch (Exception e) {
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
        id_transjual = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_waktu = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        txt_qty = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        Hitung = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        Radio1 = new javax.swing.JCheckBox();
        Radio2 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        Radio3 = new javax.swing.JCheckBox();
        txt_kembalian = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        hari = new javax.swing.JTextField();
        bulan = new javax.swing.JTextField();
        tahun = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txt_cariData = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        idbarang = new javax.swing.JComboBox<>();
        txt_pendapatan = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txt_hasil = new javax.swing.JTextField();
        btn_dashboard = new javax.swing.JButton();
        btn_dataBarang = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_admin = new javax.swing.JButton();
        btn_transBeli = new javax.swing.JButton();
        idAdmin = new javax.swing.JComboBox<>();
        Tambah = new javax.swing.JButton();
        waktu = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Waktu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_transjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_transjualActionPerformed(evt);
            }
        });
        jPanel1.add(id_transjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, 230, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 204));
        jLabel3.setText("ID Transaksi Jual  ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 160, 50));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 204));
        jLabel5.setText("Harga");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 204));
        jLabel4.setText("ID Hijab");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 50));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 204));
        jLabel2.setText("Waktu");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 50));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 204));
        jLabel6.setText("Jumlah");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, 30));
        jPanel1.add(txt_waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, 230, 30));
        jPanel1.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 230, 30));

        txt_qty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_qtyActionPerformed(evt);
            }
        });
        jPanel1.add(txt_qty, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 230, 30));

        jButton2.setBackground(new java.awt.Color(255, 153, 102));
        jButton2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(204, 0, 204));
        jButton2.setText("TAMBAHKAN");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 610, 330, 40));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 204));
        jLabel7.setText("Total Harga");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, -1, 60));

        txt_total.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        jPanel1.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 450, 230, 40));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 204));
        jLabel8.setText("Bayar");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 490, -1, 60));

        txt_bayar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        jPanel1.add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 500, 230, 40));

        Hitung.setBackground(new java.awt.Color(255, 153, 102));
        Hitung.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Hitung.setForeground(new java.awt.Color(204, 0, 204));
        Hitung.setText("HITUNG");
        Hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HitungActionPerformed(evt);
            }
        });
        jPanel1.add(Hitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 120, 40));

        jButton4.setBackground(new java.awt.Color(255, 153, 102));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(204, 0, 204));
        jButton4.setText("HAPUS");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 590, 120, 40));

        jButton5.setBackground(new java.awt.Color(255, 153, 102));
        jButton5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton5.setForeground(new java.awt.Color(204, 0, 204));
        jButton5.setText("SIMPAN");
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 590, 120, 40));

        Radio1.setText("50.000");
        Radio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Radio1ActionPerformed(evt);
            }
        });
        jPanel1.add(Radio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 550, 70, -1));

        Radio2.setText("100.000");
        Radio2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Radio2ActionPerformed(evt);
            }
        });
        jPanel1.add(Radio2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, 70, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 204));
        jLabel9.setText("KEMBALIAN");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 650, -1, 50));

        Radio3.setText("500.000");
        Radio3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Radio3ActionPerformed(evt);
            }
        });
        jPanel1.add(Radio3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 70, -1));

        txt_kembalian.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        txt_kembalian.setOpaque(false);
        jPanel1.add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 650, 230, 50));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 204));
        jLabel10.setText("TANGGAL");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 400, -1, 30));

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 204));
        jLabel11.setText("BULAN");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 400, -1, 30));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 204));
        jLabel12.setText("TAHUN");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 400, -1, 30));

        jButton6.setBackground(new java.awt.Color(255, 153, 102));
        jButton6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(204, 0, 204));
        jButton6.setText("CARI");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 400, 80, 30));
        jPanel1.add(hari, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 400, 40, 30));
        jPanel1.add(bulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 400, 40, 30));
        jPanel1.add(tahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 400, 60, 30));

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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 440, 800, 160));

        jButton7.setBackground(new java.awt.Color(255, 153, 102));
        jButton7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton7.setForeground(new java.awt.Color(204, 0, 204));
        jButton7.setText("TAMPILKAN DATA");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 660, 200, 40));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 0, 204));
        jLabel13.setText("CARI DATA");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, -1, 30));

        txt_cariData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariDataActionPerformed(evt);
            }
        });
        txt_cariData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariDataKeyReleased(evt);
            }
        });
        jPanel1.add(txt_cariData, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 110, 30));

        jButton8.setBackground(new java.awt.Color(255, 153, 102));
        jButton8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton8.setForeground(new java.awt.Color(204, 0, 204));
        jButton8.setText("CETAK");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 660, 120, 40));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 0, 204));
        jLabel14.setText("PENDAPATAN");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 660, -1, 40));

        idbarang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                idbarangItemStateChanged(evt);
            }
        });
        idbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idbarangActionPerformed(evt);
            }
        });
        jPanel1.add(idbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 230, 30));

        txt_pendapatan.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_pendapatan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        jPanel1.add(txt_pendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 660, 260, 40));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("LAPORAN TOTAL DATA PENJUALAN");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 360, -1, -1));

        jTable2.setBackground(new java.awt.Color(255, 153, 102));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No.", "ID Transaksi Jual", "Waktu Jual", "QTY", "ID Hijab", "Harga Jual", "Total Bayar", "ID Admin"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 210, 800, 140));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("TABLE MENGHITUNG BELANJAAN");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 170, -1, 30));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(204, 0, 204));
        jLabel17.setText("BARANG TERJUAL");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 610, -1, 40));

        txt_hasil.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_hasil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102), 5));
        jPanel1.add(txt_hasil, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 610, 260, 40));

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

        btn_transBeli.setBorder(null);
        btn_transBeli.setContentAreaFilled(false);
        btn_transBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transBeliActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 240, 70));

        jPanel1.add(idAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 410, 230, 30));

        Tambah.setBackground(new java.awt.Color(255, 153, 102));
        Tambah.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        Tambah.setForeground(new java.awt.Color(204, 0, 204));
        Tambah.setText("+");
        Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahActionPerformed(evt);
            }
        });
        jPanel1.add(Tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 50, 30));

        waktu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        waktu.setForeground(new java.awt.Color(204, 0, 204));
        waktu.setText("Tanggal");
        jPanel1.add(waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        transJual_nama.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        transJual_nama.setForeground(new java.awt.Color(204, 0, 204));
        transJual_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        transJual_nama.setText("username");
        jPanel1.add(transJual_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 204));
        jLabel1.setText("ID Admin");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 50));

        jButton1.setBackground(new java.awt.Color(255, 153, 102));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(204, 0, 204));
        jButton1.setText("CLEAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 170, 90, -1));

        Waktu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Waktu.setForeground(new java.awt.Color(204, 0, 204));
        Waktu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Transaksi_jual1.jpeg"))); // NOI18N
        jPanel1.add(Waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void id_transjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_transjualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_transjualActionPerformed

    private void idbarangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_idbarangItemStateChanged
    otobarang();
    }//GEN-LAST:event_idbarangItemStateChanged

    private void txt_cariDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariDataKeyReleased
        String key = txt_cariData.getText();

        if (key != "") {
            CariData(key);

        } else {
            tampilan();
        }
    }//GEN-LAST:event_txt_cariDataKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        cari_hari();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int baris = jTable1.rowAtPoint(evt.getPoint());
        String ID_Hijab = jTable1.getValueAt(baris, 1).toString();
        
        if (jTable1.getValueAt(baris, 1) == null) {
            id_transjual.setText("");
        } else {
            id_transjual.setText(jTable1.getValueAt(baris, 1).toString());
        }
        if (jTable1.getValueAt(baris, 2) == null) {
            txt_waktu.setText("");
        } else {
            txt_waktu.setText(jTable1.getValueAt(baris, 2).toString());
        }
        if (jTable1.getValueAt(baris, 3) == null) {
            txt_qty.setText("");
        } else {
            txt_qty.setText(jTable1.getValueAt(baris, 3).toString());
        }
        if (jTable1.getValueAt(baris, 5) == null) {
            txt_harga.setText("");
        } else {
            txt_harga.setText(jTable1.getValueAt(baris, 5).toString());
        }
        if (jTable1.getValueAt(baris, 6) == null) {
            txt_total.setText("");
        } else {
            txt_total.setText(jTable1.getValueAt(baris, 6).toString());
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        this.setVisible(false);
        new dashboard(transJual_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_dataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataBarangActionPerformed
        this.setVisible(false);
        new data_barang(transJual_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dataBarangActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        this.setVisible(false);
        new supplier(transJual_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adminActionPerformed
        this.setVisible(false);
        new admin(transJual_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_adminActionPerformed

    private void btn_transBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transBeliActionPerformed
        this.setVisible(false);
        new transaksi_beli(transJual_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transBeliActionPerformed
    
    private void txt_cariDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariDataActionPerformed

    private void Radio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Radio1ActionPerformed
        if(Radio1.isSelected()) {
            txt_bayar.setText(String.valueOf("50000"));
            
        }else {
            txt_bayar.setText(String.valueOf(""));
        }
    }//GEN-LAST:event_Radio1ActionPerformed

    private void Radio2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Radio2ActionPerformed
        if(Radio2.isSelected()) {
            txt_bayar.setText(String.valueOf("100000"));
            
        }else {
            txt_bayar.setText(String.valueOf(""));
        }
    }//GEN-LAST:event_Radio2ActionPerformed

    private void Radio3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Radio3ActionPerformed
        if(Radio3.isSelected()) {
            txt_bayar.setText(String.valueOf("500000"));
            
        }else {
            txt_bayar.setText(String.valueOf(""));
        }
    }//GEN-LAST:event_Radio3ActionPerformed

    private void HitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HitungActionPerformed
        int total, bayar, kembalian;
        
        total = Integer.valueOf(txt_total.getText());
        bayar = Integer.valueOf(txt_bayar.getText());
        
        if (total > bayar) {
            JOptionPane.showMessageDialog(null, "Uang tidak cukup untuk melakukan pembayaran");
        } else {
            kembalian = bayar - total;
            txt_kembalian.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_HitungActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String sql = "DELETE FROM transaksi_jual WHERE id_transJual = '" + id_transjual.getText() + "'";
        try {
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di Hapus");
            tampilan();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EROR \n" + e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_TambahActionPerformed

    private void txt_qtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_qtyActionPerformed
        tambahTransaksi();
    }//GEN-LAST:event_txt_qtyActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        tampilan();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        cetak();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tambahkan1();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        clear2();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void idbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idbarangActionPerformed
        
    }//GEN-LAST:event_idbarangActionPerformed
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
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_jual(transJual_nama.getText()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Hitung;
    private javax.swing.JCheckBox Radio1;
    private javax.swing.JCheckBox Radio2;
    private javax.swing.JCheckBox Radio3;
    private javax.swing.JButton Tambah;
    private javax.swing.JLabel Waktu;
    private javax.swing.JButton btn_admin;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_dataBarang;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_transBeli;
    private javax.swing.JTextField bulan;
    private javax.swing.JTextField hari;
    private javax.swing.JComboBox<String> idAdmin;
    private javax.swing.JTextField id_transjual;
    private javax.swing.JComboBox<String> idbarang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField tahun;
    public static final javax.swing.JLabel transJual_nama = new javax.swing.JLabel();
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_cariData;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_hasil;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_pendapatan;
    private javax.swing.JTextField txt_qty;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_waktu;
    private javax.swing.JLabel waktu;
    // End of variables declaration//GEN-END:variables
}
