package codingan;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DecimalFormat;

/**
 *
 * @author B4
 */

public class dashboard extends javax.swing.JFrame implements Runnable {

    Thread T;
    boolean kanan = true;
    boolean kiri = false;
    boolean berjalan = true;
    int x, y;

    /**
     * Creates new form dashboard
     */
    
    public dashboard(String username) {

        initComponents();
        this.setResizable(false);
        setNama(username);
        waktu();
        stok();
        supplier();
        Pembelian();
        Barang_terjual();
        Pengguna();
        total_pembeli();
        setTitle("Dashboard");
        setLocationRelativeTo(this);
        x = jLabel2.getX();
        y = jLabel2.getY();
        T = new Thread(this);
        T.start();
    }

    public void setNama(String user) {
        username.setText(user);
    }

    @Override
    public void run() {
        while (true) {
            if (berjalan) {
                if (x >= jPanel2.getWidth() - 100) {
                    kiri = true;
                    kanan = false;
                }
                if (kiri) {
                    x--;
                    jLabel2.setLocation(x, y);
                }
                if (x <= -1100) {
                    kanan = true;
                    kiri = false;
                }
                if (kanan) {
                    x++;
                    jLabel2.setLocation(x, y);
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }

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

    private void stok() {
        txt_stokBarang.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, SUM(stok) AS TOTAL FROM hijab";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL"));
                txt_stokBarang.setText(String.valueOf(total));
            }
        } catch (Exception e) {
        }   
    }

    private void supplier() {
        txt_supplier.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, COUNT(id_supp) AS TOTAL_SUPPLIER FROM supplier";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL_SUPPLIER"));
                txt_supplier.setText(String.valueOf(total));
            }
        } catch (Exception e) {
        }       
    }

    private void Pembelian() {
        txt_pembelian.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, COUNT(id_hijab) AS TOTAL_PEMBELIAN FROM hijab";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL_PEMBELIAN"));
                txt_pembelian.setText(String.valueOf(total));
            }
        } catch (Exception e) {
        }        
    }

    private void Barang_terjual() {
        txt_barangTerjual.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, SUM(qty) AS TOTAL_BARANGTERJUAL FROM det_trsansaksi";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL_BARANGTERJUAL"));
                txt_barangTerjual.setText(String.valueOf(total));
            }
        
        } catch (Exception e) {
        }
    }

    private void Pengguna() {
        txt_penggunaAplikasi.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, COUNT(id_admin) AS TOTAL_Admin FROM admin";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL_Admin"));
                txt_penggunaAplikasi.setText(String.valueOf(total));
            }
        } catch (Exception e) {
        }        
    }

    private void total_pembeli() {
        txt_totalPembeli.setText(null);
        int total =0;
        try {
            String sql = "SELECT *, COUNT(id_transJual) AS TOTAL_PEMBELIAN FROM transaksi_jual";
            java.sql.Connection conn = (Connection)connection.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()) {
                total += Integer.parseInt(res.getString("TOTAL_PEMBELIAN"));
                txt_totalPembeli.setText(String.valueOf(total));
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btn_dataBarang = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_admin = new javax.swing.JButton();
        btn_transBeli = new javax.swing.JButton();
        btn_transJual = new javax.swing.JButton();
        stok = new javax.swing.JTextField();
        txt_stokBarang = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        txt_pembelian = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        txt_supplier = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        txt_barangTerjual = new javax.swing.JTextField();
        txt_penggunaAplikasi = new javax.swing.JTextField();
        txt_waktu = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        txt_totalPembeli = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 204, 153));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 23)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 153));
        jLabel2.setText("SELAMAT DATANG DI APLIKASI HIJABCOLLECTIONS...BELANJA AMAN, MUDAH, DAN TERPERCAYA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 1170, 50));

        btn_dataBarang.setBorder(null);
        btn_dataBarang.setContentAreaFilled(false);
        btn_dataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dataBarangActionPerformed(evt);
            }
        });
        jPanel1.add(btn_dataBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 230, 60));

        btn_supplier.setBorder(null);
        btn_supplier.setContentAreaFilled(false);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        jPanel1.add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 180, 60));

        btn_admin.setBorder(null);
        btn_admin.setContentAreaFilled(false);
        btn_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_adminActionPerformed(evt);
            }
        });
        jPanel1.add(btn_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 140, 60));

        btn_transBeli.setBorder(null);
        btn_transBeli.setContentAreaFilled(false);
        btn_transBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transBeliActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transBeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 80, 240, 60));

        btn_transJual.setBorder(null);
        btn_transJual.setContentAreaFilled(false);
        btn_transJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transJualActionPerformed(evt);
            }
        });
        jPanel1.add(btn_transJual, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 80, 250, 60));

        stok.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        stok.setText("Stok Barang");
        stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stokActionPerformed(evt);
            }
        });
        jPanel1.add(stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 270, 40));

        txt_stokBarang.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_stokBarang.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_stokBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, 270, 40));

        jTextField3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField3.setText("Pembelian");
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 290, 270, 40));

        txt_pembelian.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_pembelian.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_pembelian, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 330, 270, 40));

        jTextField5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField5.setText("Supplier");
        jPanel1.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 270, 40));

        txt_supplier.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_supplier.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 460, 270, 40));

        jTextField7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField7.setText("Barang Terjual");
        jPanel1.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 420, 270, 40));

        txt_barangTerjual.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_barangTerjual.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_barangTerjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 460, 270, 40));

        txt_penggunaAplikasi.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_penggunaAplikasi.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_penggunaAplikasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 590, 270, 40));

        txt_waktu.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_waktu.setForeground(new java.awt.Color(153, 0, 153));
        txt_waktu.setText("Tanggal");
        jPanel1.add(txt_waktu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, -1, 40));

        jTextField9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField9.setText("Pengguna Aplikasi");
        jPanel1.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 270, 40));

        username.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        username.setForeground(new java.awt.Color(153, 0, 153));
        username.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        username.setText("Username");
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 10, -1, 40));

        jTextField1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTextField1.setText(" Total Pembeli");
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 550, 270, 40));

        txt_totalPembeli.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        txt_totalPembeli.setForeground(new java.awt.Color(204, 0, 204));
        jPanel1.add(txt_totalPembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 590, 270, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Dashboard1.jpeg"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1281, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 718, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void btn_dataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dataBarangActionPerformed
        this.setVisible(false);
        new data_barang(username.getText()).setVisible(true);
    }//GEN-LAST:event_btn_dataBarangActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        this.setVisible(false);
        new supplier(username.getText()).setVisible(true);
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_adminActionPerformed
        this.setVisible(false);
        new admin(username.getText()).setVisible(true);
    }//GEN-LAST:event_btn_adminActionPerformed

    private void btn_transBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transBeliActionPerformed
        this.setVisible(false);
        new transaksi_beli(username.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transBeliActionPerformed

    private void btn_transJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transJualActionPerformed
        this.setVisible(false);
        new transaksi_jual(username.getText()).setVisible(true);
    }//GEN-LAST:event_btn_transJualActionPerformed

    private void stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stokActionPerformed
    
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
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard(username.getText()).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_admin;
    private javax.swing.JButton btn_dataBarang;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_transBeli;
    private javax.swing.JButton btn_transJual;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField stok;
    private javax.swing.JTextField txt_barangTerjual;
    private javax.swing.JTextField txt_pembelian;
    private javax.swing.JTextField txt_penggunaAplikasi;
    private javax.swing.JTextField txt_stokBarang;
    private javax.swing.JTextField txt_supplier;
    private javax.swing.JTextField txt_totalPembeli;
    private javax.swing.JLabel txt_waktu;
    public static final javax.swing.JLabel username = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables

}
