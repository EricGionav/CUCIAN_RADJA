import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;


public class Confirm_pw extends javax.swing.JFrame {
    
    private String idUser; // Variabel untuk menyimpan ID Pelanggan
    

    /**
     * Creates new form Confirm_pw
     */
    // Constructor untuk menerima ID Pelanggan dari form Forgotpass
    public Confirm_pw(String idUser) {
        this.idUser = idUser; // Simpan ID Pelanggan ke variabel
        initComponents(); // Inisialisasi komponen GUI
        t_ID.setText(idUser); // Menampilkan ID di TextField (opsional)
        t_ID.setEditable(false); // Buat TextField ID tidak bisa diedit
    }

    public Confirm_pw() {
        initComponents(); // Constructor default (opsional jika dibutuhkan)
    }

    void bersih(){
    newpw.setText("Username");
    confirmpw.setText("Password");
}  
    
    
    public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String url = "jdbc:mysql://localhost:3306/cucianradja2"; // Ganti nama_database
            String username = "root"; // Username MySQL
            String password = ""; // Password MySQL
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
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

        newpw2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        t_ID = new javax.swing.JTextField();
        confirmpw = new javax.swing.JTextField();
        newpw = new javax.swing.JTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        jLabel3 = new javax.swing.JLabel();

        newpw2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newpw2.setForeground(new java.awt.Color(51, 51, 51));
        newpw2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newpw2.setText("New password");
        newpw2.setBorder(null);
        newpw2.setCaretColor(new java.awt.Color(193, 223, 239));
        newpw2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                newpw2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                newpw2FocusLost(evt);
            }
        });
        newpw2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpw2ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/log_1-removebg-preview.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 120, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Reset password-bro.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 169, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MASUKKAN PASSWORD BARU !!");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 370, 41));

        t_ID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        t_ID.setForeground(new java.awt.Color(51, 51, 51));
        t_ID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t_ID.setText("ID Pelanggan");
        t_ID.setBorder(null);
        t_ID.setCaretColor(new java.awt.Color(193, 223, 239));
        t_ID.setEnabled(false);
        t_ID.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_IDFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_IDFocusLost(evt);
            }
        });
        t_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_IDActionPerformed(evt);
            }
        });
        jPanel2.add(t_ID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 250, 30));

        confirmpw.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        confirmpw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        confirmpw.setText("Confirm password");
        confirmpw.setBorder(null);
        confirmpw.setCaretColor(new java.awt.Color(193, 223, 239));
        confirmpw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                confirmpwFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                confirmpwFocusLost(evt);
            }
        });
        confirmpw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmpwActionPerformed(evt);
            }
        });
        jPanel2.add(confirmpw, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 250, 30));

        newpw.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        newpw.setForeground(new java.awt.Color(51, 51, 51));
        newpw.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        newpw.setText("New password");
        newpw.setBorder(null);
        newpw.setCaretColor(new java.awt.Color(193, 223, 239));
        newpw.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                newpwFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                newpwFocusLost(evt);
            }
        });
        newpw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newpwActionPerformed(evt);
            }
        });
        jPanel2.add(newpw, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 250, 30));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(0, 0, 0));
        rSMaterialButtonRectangle1.setText("next");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        jPanel2.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, 120, 40));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pop.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 370, 170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void confirmpwFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmpwFocusGained
        System.out.println("Focus Gained: " + confirmpw.getText());
        if (confirmpw.getText().equals("Confirm password")) {
            confirmpw.setText("");
        }
    }//GEN-LAST:event_confirmpwFocusGained

    private void confirmpwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmpwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmpwActionPerformed

    private void newpwFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newpwFocusGained
       String Newpw=newpw.getText();
      if (Newpw.equals("New password")){
          newpw.setText("");
      }
    }//GEN-LAST:event_newpwFocusGained

    private void newpwActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newpwActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newpwActionPerformed

    private void newpwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newpwFocusLost
       String Newpw=newpw.getText();
     if(Newpw.equals("")||Newpw.equals("New password")){
     newpw.setText("New password");
     }
    }//GEN-LAST:event_newpwFocusLost

    private void confirmpwFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_confirmpwFocusLost
       String Confirmpw=confirmpw.getText();
     if(Confirmpw.equals("")||Confirmpw.equals("Confirm password")){
     confirmpw.setText("Confirm password");
     }
    }//GEN-LAST:event_confirmpwFocusLost

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
    String passwordBaru = newpw.getText(); // Password Baru
    String cfpasswordBaru = confirmpw.getText(); // Konfirmasi Password Baru

    // Validasi jika field password kosong
    if (passwordBaru.isEmpty() || cfpasswordBaru.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Password baru dan konfirmasi password tidak boleh kosong!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validasi jika password baru dan konfirmasi password tidak sama
    if (!passwordBaru.equals(cfpasswordBaru)) {
        javax.swing.JOptionPane.showMessageDialog(this, "Password baru dan konfirmasi password tidak sama!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }
    new LOGIN().setVisible(true); // Membuka form login baru
            this.dispose();

    // Query untuk memperbarui password
    String query = "UPDATE user SET password = ? WHERE id_user = ?";

    try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setString(1, passwordBaru); // Set password baru
        ps.setInt(2, Integer.parseInt(idUser)); // Gunakan ID pelanggan dari constructor

        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Password berhasil diperbarui!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            this.dispose(); // Tutup form
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "ID Pelanggan tidak ditemukan. Periksa ID Anda.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memperbarui password: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void t_IDFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_IDFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_t_IDFocusGained

    private void t_IDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_IDFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_t_IDFocusLost

    private void t_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_IDActionPerformed

    private void newpw2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newpw2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_newpw2FocusGained

    private void newpw2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_newpw2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_newpw2FocusLost

    private void newpw2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newpw2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newpw2ActionPerformed

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
            java.util.logging.Logger.getLogger(Confirm_pw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Confirm_pw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Confirm_pw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Confirm_pw.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Confirm_pw().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField confirmpw;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField newpw;
    private javax.swing.JTextField newpw2;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private javax.swing.JTextField t_ID;
    // End of variables declaration//GEN-END:variables
}
