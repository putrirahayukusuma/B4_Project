package codingan;

import static codingan.dashboard.username;

import java.util.Random;
import com.onbarcode.barcode.IBarcode;
import com.onbarcode.barcode.EAN13;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author B4
 */

public class data_barang extends javax.swing.JFrame {

    public data_barang(String username) {
        initComponents();
        this.setResizable(false);
        setTitle("Data Barang");
        setNama(username);
        TampilanData();
        tampil_combo();
        waktu();

    }

    public static String getRandomNumberString(){
      Random rnd = new Random();
      int number = rnd.nextInt(999999999);
      return String.format("%012d", number);
    }
    
    private void  generate(String kode) throws Exception {
        EAN13 barcode = new EAN13();
        
        barcode.setData (kode);
        barcode.setUom(IBarcode.UOM_PIXEL);
        
        barcode.setX(3f);
        
        barcode.setY(10f);
        
        barcode.setLeftMargin(0f);
        barcode.setRightMargin(0f);
        barcode.setTopMargin(0f);
        barcode.setBottomMargin(0f);
        
        barcode.setResolution(72);
        
        barcode.setShowText(true);
        
        barcode.setTextFont(new Font("Arial", 0, 12));
        
        barcode.setTextMargin(6);
        
        barcode.setRotate(IBarcode.ROTATE_0);
        
        barcode.drawBarcode("src" + "/" + "img" + "/barcode/" + kode + ".gif");
    }
    
    private void waktu() {
        Timer t2 = new Timer();
        t2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                txt_waktu.setText(new SimpleDateFormat("EEEE dd MMMM yyyy hh:mm:ss").format(new java.util.Date()));
            }
        }, 0, 1000);
    }
    public void setNama(String user) {
        barang_nama.setText(user);
    }  
    
    private void CariData(String Key) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Id Hijab");
        model.addColumn("Id Supplier");
        model.addColumn("Merk");
        model.addColumn("Harga Jual");
        model.addColumn ("Harga Beli");
        model.addColumn("Stok");

        String sql = "SELECT * FROM hijab WHERE id_hijab LIKE '%" + Key + "%' or id_supp LIKE '%" + Key + "%' or merk LIKE '%" + Key + "%'";
 
        try {
            int no = 1;
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement stm = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{no++, rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(3)});
            }
            jTable2.setModel(model);
        } catch (Exception e) {
            e.getMessage();
        }

    }

    private void TampilanData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("Id Hijab");
        model.addColumn("Id Supplier");
        model.addColumn("Merk");
        model.addColumn("Harga Jual");
        model.addColumn("Harga Beli");
        model.addColumn("Stok");
        model.addColumn("kd_barcode");

        String sql = "SELECT * FROM hijab";

        try {
            int no = 1;
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement stm = conn.prepareStatement(sql);
            java.sql.ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{no++, rs.getString(1), rs.getString(2), rs.getString(4), rs.getString(7), rs.getString(8), rs.getString(3), rs.getString(9)});
            }
            jTable2.setModel(model);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void tampil_combo() {
        try {
            String sql = "SELECT * FROM supplier";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("id_supp"));
            }
            rs.last();
            int jumlahdata = rs.getRow();
        } catch (Exception e) {
        }
    }

    private void kosong() {
        txt_idHijab.enable();
        txt_idHijab.setText(null);
        jComboBox1.setSelectedItem("");
        txt_merk.setText(null);
        txt_hargaJual.setText(null);
        txt_hargaBeli.setText(null);
        txt_stok.setText(null);
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
        txt_idHijab = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_merk = new javax.swing.JTextField();
        txt_hargaJual = new javax.swing.JTextField();
        btn_edit = new javax.swing.JButton();
        txt_stok = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txt_cari = new javax.swing.JTextField();
        btn_dashboard = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_admin = new javax.swing.JButton();
        btn_transaksi_beli = new javax.swing.JButton();
        btn_transaksi_jual = new javax.swing.JButton();
        txt_waktu = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        txt_hargaBeli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        kd_barang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        barcode = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 204));
        jLabel2.setText("ID Hijab");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, -1, 30));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 0, 204));
        jLabel3.setText("KD_barang");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, -1, 30));
        jPanel1.add(txt_idHijab, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 220, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 204));
        jLabel4.setText("Stok");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, -1, 30));
        jPanel1.add(txt_merk, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 340, 220, 30));
        jPanel1.add(txt_hargaJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 380, 220, 30));

        btn_edit.setBackground(new java.awt.Color(255, 153, 102));
        btn_edit.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_edit.setForeground(new java.awt.Color(204, 0, 204));
        btn_edit.setText("EDIT");
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel1.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 430, 120, 60));
        jPanel1.add(txt_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 460, 220, 30));

        btn_tambah.setBackground(new java.awt.Color(255, 153, 102));
        btn_tambah.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(204, 0, 204));
        btn_tambah.setText("TAMBAH");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 430, 120, 60));

        btn_hapus.setBackground(new java.awt.Color(255, 153, 102));
        btn_hapus.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(204, 0, 204));
        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 430, 120, 60));

        btn_cetak.setBackground(new java.awt.Color(255, 153, 102));
        btn_cetak.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_cetak.setForeground(new java.awt.Color(204, 0, 204));
        btn_cetak.setText("CETAK");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });
        jPanel1.add(btn_cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 430, 120, 60));

        btn_clear.setBackground(new java.awt.Color(255, 153, 102));
        btn_clear.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_clear.setForeground(new java.awt.Color(204, 0, 204));
        btn_clear.setText("CLEAR");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });
        jPanel1.add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 430, 120, 60));

        btn_simpan.setBackground(new java.awt.Color(255, 153, 102));
        btn_simpan.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(204, 0, 204));
        btn_simpan.setText("SIMPAN");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 430, 120, 60));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 0, 204));
        jLabel5.setText("Harga Jual");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 380, -1, 30));

        jTable2.setBackground(new java.awt.Color(255, 153, 102));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 500, 1180, 210));

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 153));
        jLabel6.setText("CARI DATA");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, -1, 30));

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });
        jPanel1.add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 260, 700, 30));

        btn_dashboard.setBorder(null);
        btn_dashboard.setContentAreaFilled(false);
        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        jPanel1.add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 70, 150, 70));

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
        jPanel1.add(btn_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 150, 70));

        btn_transaksi_beli.setBorder(null);
        btn_transaksi_beli.setContentAreaFilled(false);
        btn_transaksi_beli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksi_beliActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transaksi_beli, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 70, 240, 70));

        btn_transaksi_jual.setBorder(null);
        btn_transaksi_jual.setContentAreaFilled(false);
        btn_transaksi_jual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksi_jualActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transaksi_jual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 240, 70));

        txt_waktu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_waktu.setForeground(new java.awt.Color(204, 0, 204));
        txt_waktu.setText("Tanggal");
        jPanel1.add(txt_waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        barang_nama.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        barang_nama.setForeground(new java.awt.Color(204, 0, 204));
        barang_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        barang_nama.setText("Username");
        jPanel1.add(barang_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, -1, -1));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 204));
        jLabel9.setText("ID Supplier");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 220, 30));
        jPanel1.add(txt_hargaBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 420, 220, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 204));
        jLabel7.setText("Harga Beli");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, 30));

        jButton1.setText("NEW");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 383, -1, 30));
        jPanel1.add(kd_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 200, 30));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 204));
        jLabel8.setText("Merk");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, 30));

        barcode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                barcodeMouseClicked(evt);
            }
        });
        jPanel1.add(barcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 300, 360, 90));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Data_barang1.jpeg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 780));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        this.setVisible(false);
        new dashboard(barang_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        this.setVisible(false);
        new supplier(barang_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adminActionPerformed
        this.setVisible(false);
        new admin(barang_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_adminActionPerformed

    private void btn_transaksi_beliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksi_beliActionPerformed
        this.setVisible(false);
        new transaksi_beli(barang_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transaksi_beliActionPerformed

    private void btn_transaksi_jualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksi_jualActionPerformed
        this.setVisible(false);
        new transaksi_jual(barang_nama.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transaksi_jualActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int baris = jTable2.rowAtPoint(evt.getPoint());
        String ID_Hijab = jTable2.getValueAt(baris, 1).toString();
        txt_idHijab.setText(ID_Hijab);
        txt_idHijab.disable();
        if (jTable2.getValueAt(baris, 1) == null) {
            txt_idHijab.setText("");
        } else {
            txt_idHijab.setText(jTable2.getValueAt(baris, 1).toString());
        }
        if (jTable2.getValueAt(baris, 3) == null) {
            txt_merk.setText("");
        } else {
            txt_merk.setText(jTable2.getValueAt(baris, 3).toString());
        }
        if (jTable2.getValueAt(baris, 4) == null) {
            txt_hargaJual.setText("");
        } else {
            txt_hargaJual.setText(jTable2.getValueAt(baris, 4).toString());
        }
        if (jTable2.getValueAt(baris, 5) == null) {
            txt_hargaBeli.setText("");
        } else {
            txt_hargaBeli.setText(jTable2.getValueAt(baris, 5).toString());
        }
        if (jTable2.getValueAt(baris, 6) == null) {
            txt_stok.setText("");
        } else {
            txt_stok.setText(jTable2.getValueAt(baris, 6).toString());
        }
        if (jTable2.getValueAt (baris, 7)== null){
            barcode.setText("");
        } else {
            String kb2 = jTable2.getValueAt (baris, 7).toString();
              ImageIcon imgThisimg = new ImageIcon("src" + "/" + "img" + "/barcode/" + kb2 +  ".gif");
              barcode.setIcon(imgThisimg);
        }

    }//GEN-LAST:event_jTable2MouseClicked

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        String key = txt_cari.getText();

        if (key != "") {
            CariData(key);

        } else {
            TampilanData();
        }

    }//GEN-LAST:event_txt_cariKeyReleased

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        String kb = String.valueOf(kd_barang.getText());
        JOptionPane.showMessageDialog(null, "input data berhasil");
        TampilanData();
        try {
            String sql = "INSERT INTO hijab (`id_hijab`, `id_supp`, `stok`, `merk`, `warna`, `model_hijab`, `harga_jual`, `harga_beli`,`kd_barcode`) "
                    + "VALUES ('" + txt_idHijab.getText() + "','" + jComboBox1.getSelectedItem() + "','" + txt_stok.getText()
                    + "','" + txt_merk.getText() + "',NULL,NULL,'"+txt_hargaJual.getText() + "','" + txt_hargaBeli.getText()+ "','" +kd_barang.getText()+ "')";
            System.out.println(sql);
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Di Tambahkan");
            generate(kb);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        TampilanData();
        kosong();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed

    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        kosong();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        String sql = "DELETE FROM hijab WHERE id_hijab = '" + txt_idHijab.getText() + "'";
        try {
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di Hapus");
            TampilanData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EROR \n" + e.getMessage());
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        String sql = "UPDATE hijab Set id_hijab = '" + txt_idHijab.getText() + "',merk = '" + txt_merk.getText() + "',harga_jual = '" + txt_hargaJual.getText() + "',harga_beli = '" + txt_hargaBeli.getText() + "',stok = '" + txt_stok.getText()
                + "' WHERE id_hijab = '" + txt_idHijab.getText() + "'";
        try {
            java.sql.Connection conn = (Connection) connection.configDB();
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil di edit");
            TampilanData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EROR \n" + e.getMessage());
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        cetak();
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txt_idHijab.getText();
        jComboBox1.getSelectedItem();
        txt_stok.getText();
        txt_merk.getText();
        txt_merk.getText() ;
        txt_hargaJual.getText();
        txt_hargaBeli.getText();
        kd_barang.setText(getRandomNumberString());
        kd_barang.enable(false);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void barcodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barcodeMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_barcodeMouseClicked
    private void cetak() {
        try {
            jTable2.print();

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
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(data_barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new data_barang(barang_nama.getText()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static final javax.swing.JLabel barang_nama = new javax.swing.JLabel();
    private javax.swing.JLabel barcode;
    private javax.swing.JButton btn_admin;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_transaksi_beli;
    private javax.swing.JButton btn_transaksi_jual;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField kd_barang;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_hargaBeli;
    private javax.swing.JTextField txt_hargaJual;
    private javax.swing.JTextField txt_idHijab;
    private javax.swing.JTextField txt_merk;
    private javax.swing.JTextField txt_stok;
    private javax.swing.JLabel txt_waktu;
    // End of variables declaration//GEN-END:variables

}
