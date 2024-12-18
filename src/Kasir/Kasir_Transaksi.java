/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Kasir;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class Kasir_Transaksi extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_Transaksi
     */
    public Kasir_Transaksi() {
        setUndecorated(true);
        initComponents();
        loadTableData();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        t_cari.setText("Cari ID");
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
     
     private void loadTableData() {
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "Harga Paket"}, 0);
    
    String query = "SELECT * FROM transaksi";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id_transaksi");
            int idPelanggan = rs.getInt("id_pelanggan");
            int idKaryawan = rs.getInt("id_karyawan");
            int idPaket = rs.getInt("id_paket");
            String tanggalTransaksi = rs.getString("tanggal_transaksi");
            String statusTransaksi = rs.getString("harga_paket");

            // Tambahkan data ke model
            model.addRow(new Object[]{id, idPelanggan, idKaryawan, idPaket, tanggalTransaksi, statusTransaksi});
        }

        // Set model ke JTable
        tabel2.setModel(model);
        System.out.println("Data berhasil dimuat ke tabel!");
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

     
     public class DataHandler {
    public ArrayList<Object[]> getDataUsers() {
        ArrayList<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM transaksi";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new Object[]{rs.getInt("id_transaksi"), rs.getInt("id_pelanggan"), rs.getInt("id_karyawan"), rs.getString("id_paket"), rs.getString("tanggal_transaksi"),  rs.getString("harga_paket")});
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return data;
    }
}
     
     public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        loadTable();
    }

    private void loadTable() {
        // Model untuk JTable
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi",  "Harga Paket"}, 0);
        String query = "SELECT * FROM transaksi";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_transaksi");
            int idp = rs.getInt("id_pelanggan");
            int idk = rs.getInt("id_karyawan");
            String tgl = rs.getString("tanggal_transaksi");
            String namap = rs.getString("id_paket");
            String harga = rs.getString("harga_paket");

            model.addRow(new Object[]{id, idp, idk, tgl, namap,harga});
            }

           // Atur model ke JTable
        tabel2.setModel(model);
        
        // Atur ukuran kolom dan pengaturan lainnya
        Tabel(tabel2, new int[]{100, 150, 150, 150, 350, 150});
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }
    
    private javax.swing.table.DefaultTableModel tblModel = getDefaultTabelModel();
    
    private void Tabel(javax.swing.JTable tb, int lebar[]) {
        tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int kolom=tb.getColumnCount();
        for(int i=0;i < kolom;i++) {
            javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
            tb.setRowHeight(17);
        }
    }
    
     private javax.swing.table.DefaultTableModel getDefaultTabelModel() {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "Harga Paket"}) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
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

        Mid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel2 = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        t_cari = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        Top5 = new javax.swing.JPanel();
        lg5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        Dashboard = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Mid.setBackground(new java.awt.Color(93, 173, 226));
        Mid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "Harga"
            }
        ));
        jScrollPane1.setViewportView(tabel2);

        Mid.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 640, 281));

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        Mid.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 50, 40, 30));

        t_cari.setText("Cari ID");
        t_cari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_cariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_cariFocusLost(evt);
            }
        });
        t_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_cariActionPerformed(evt);
            }
        });
        Mid.add(t_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 252, 33));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Mid.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 30, 30));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(48, 89, 213));
        rSMaterialButtonRectangle1.setText("+ Tambahkan transaksi");
        rSMaterialButtonRectangle1.setFont(new java.awt.Font("Roboto Medium", 1, 14)); // NOI18N
        rSMaterialButtonRectangle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle1MouseClicked(evt);
            }
        });
        Mid.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, 220, 40));

        Top5.setBackground(new java.awt.Color(255, 255, 255));

        lg5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lg5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logout_1.png"))); // NOI18N
        lg5.setText("LOGOUT");
        lg5.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lg5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lg5MousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout Top5Layout = new javax.swing.GroupLayout(Top5);
        Top5.setLayout(Top5Layout);
        Top5Layout.setHorizontalGroup(
            Top5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Top5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lg5)
                .addGap(21, 21, 21))
        );
        Top5Layout.setVerticalGroup(
            Top5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Top5Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(Top5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lg5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Left.setBackground(new java.awt.Color(44, 46, 141));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Price Tag.png"))); // NOI18N
        jLabel26.setText("     Layanan & Harga");
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        Dashboard.setBackground(new java.awt.Color(51, 51, 255));
        Dashboard.setForeground(new java.awt.Color(255, 255, 255));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Transaction.png"))); // NOI18N
        jLabel29.setText("     Riwayat Transaksi");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DashboardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 42, Short.MAX_VALUE)
            .addGroup(DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DashboardLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Customer.png"))); // NOI18N
        jLabel31.setText("     Pelanggan");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Dashboard Layout.png"))); // NOI18N
        jLabel33.setText("     Dashboard");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Discount.png"))); // NOI18N
        jLabel35.setText("     Diskon Spesial");
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Sell Stock.png"))); // NOI18N
        jLabel38.setText("     Stok");
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Old Man.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Name.png"))); // NOI18N
        jLabel41.setText("     User");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout LeftLayout = new javax.swing.GroupLayout(Left);
        Left.setLayout(LeftLayout);
        LeftLayout.setHorizontalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 6, Short.MAX_VALUE)))
                .addContainerGap())
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addGap(50, 50, 50)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel41)
                .addGap(10, 10, 10)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Mid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Top5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Top5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Mid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Left, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void OpenKasirForm(){
        Kasir_Das KasirForm = new Kasir_Das();
        KasirForm.setVisible(true);
    }
    
    public static void OpenKasirFormUser(){
        Kasir_Us KasirUser = new Kasir_Us();
        KasirUser.setVisible(true);
    }
    
    public static void OpenKasirFormPelanggan(){
        Kasir_Pel KasirPelanggan = new Kasir_Pel();
        KasirPelanggan.setVisible(true);
    }
    
    public static void OpenKasirFormLayanan(){
        Kasir_lay KasirLayanan = new Kasir_lay();
        KasirLayanan.setVisible(true);
    }
    
    public static void OpenKasirFormDiskon(){
        Kasir_Diskon KasirDiskon = new Kasir_Diskon();
        KasirDiskon.setVisible(true);
    }
    
    public static void OpenKasirFormStok(){
        Kasir_Stok KasirStok = new Kasir_Stok();
        KasirStok.setVisible(true);
    }
    
    public static void OpenKasirFormRiwayat(){
        Kasir_Transaksi KasirTransaksi = new Kasir_Transaksi();
        KasirTransaksi.setVisible(true);
    }
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String cari = t_cari.getText().trim();  // Mengambil ID yang dicari dari TextField

        if (cari.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap masukkan ID yang ingin dicari!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return; // Tidak lanjutkan jika field kosong
        }

        // Menghapus semua data lama di JTable
        DefaultTableModel model = (DefaultTableModel) tabel2.getModel();
        model.setRowCount(0); // Menghapus semua baris sebelumnya

        try (Connection conn = Kasir_Transaksi.DatabaseConnection.getConnection()) {
            // Menggunakan PreparedStatement untuk menghindari SQL injection
            String sql = "SELECT * FROM transaksi WHERE id_transaksi LIKE ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, "%" + cari + "%");  // Menggunakan wildcard untuk pencarian yang lebih fleksibel

                try (ResultSet rs = pst.executeQuery()) {
                    boolean found = false;  // Flag untuk mengecek jika ada hasil pencarian
                    while (rs.next()) {
                        // Menambahkan hasil pencarian ke JTable
                        model.addRow(new Object[]{
                            rs.getString("id_transaksi"), // ID Transaksi
                            rs.getString("id_pelanggan"),     // ID Paket
                            rs.getString("id_karyawan"),  // ID Karyawan
                            rs.getString("id_paket"),       // ID Paket
                            rs.getString("tanggal_transaksi"), // Tanggal Transaksi
                            rs.getString("harga_paket")   // Status Transaksi
                        });
                        found = true;  // Tandakan bahwa data ditemukan
                    }

                    // Jika tidak ada data ditemukan
                    if (!found) {
                        JOptionPane.showMessageDialog(null, "Maaf...Data tidak ditemukan", "Pencarian Gagal", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi Kesalahan: " + e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat melakukan pencarian.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void t_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_cariFocusGained
        String pass=t_cari.getText();
        if (pass.equals("Cari ID")){
            t_cari.setText("");
        }
    }//GEN-LAST:event_t_cariFocusGained

    private void t_cariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_cariFocusLost
        String Password=t_cari.getText();
        if(Password.equals("")||Password.equals("Cari ID")){
            t_cari.setText("Cari ID");
        }
    }//GEN-LAST:event_t_cariFocusLost

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadTableData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void rSMaterialButtonRectangle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1MouseClicked
        Kasir_TransaksiInput tambah = new Kasir_TransaksiInput(); // Buat instance form
        tambah.setVisible(true); // Tampilkan form ForgotPassword
        this.dispose(); // T
    }//GEN-LAST:event_rSMaterialButtonRectangle1MouseClicked

    private void lg5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lg5MousePressed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (response == JOptionPane.YES_OPTION) {
            // Lakukan tindakan logout, misalnya kembali ke halaman login
            System.out.println("Logout Berhasil");
            System.exit(0); // Menutup aplikasi (opsional)
        }

        this.dispose(); // Menutup form saat ini
        setVisible(true);
    }//GEN-LAST:event_lg5MousePressed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormLayanan();
        dispose();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormRiwayat();
        dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormPelanggan();
        dispose();
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirForm();
        dispose();
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormDiskon();
        dispose();
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:        
        Kasir_Das.OpenKasirFormStok();
        dispose();
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormUser();
        dispose();
    }//GEN-LAST:event_jLabel41MouseClicked

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
            java.util.logging.Logger.getLogger(Kasir_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir_Transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kasir_Transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Mid;
    private javax.swing.JPanel Top5;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lg5;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField t_cari;
    private javax.swing.JTable tabel2;
    // End of variables declaration//GEN-END:variables
}
