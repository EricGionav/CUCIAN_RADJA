
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Owner_Stok extends javax.swing.JFrame {

    /**
     * Creates new form Owner_Stok
     */
    public Owner_Stok() {
        setUndecorated(true);
        initComponents();
        loadTableData();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
    // Model untuk JTable
       DefaultTableModel model = new DefaultTableModel(new String[]{"ID Stok", "Nama Stok", "Jumlah Stok", "Harga Stok"}, 0);
    String query = "SELECT * FROM stok";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int ids = rs.getInt("id_stok");
            String namas = rs.getString("nama_stok");
            int jumlahs = rs.getInt("jumlah_stok");
            int hargas= rs.getInt("harga_stok");

            model.addRow(new Object[]{ids, namas, jumlahs, hargas});
        }

        tabel4.setModel(model); // Tampilkan data di JTable
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
     
     public class DataHandler {
    public ArrayList<Object[]> getDataUsers() {
        ArrayList<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM stok";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new Object[]{rs.getString("id_stok"), rs.getString("nama_stok"), rs.getInt("jumlah_stok"), rs.getInt("harga_stok")});
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
       DefaultTableModel model = new DefaultTableModel(new String[]{"ID Stok", "Nama Stok", "Jumlah Stok", "Harga Stok"}, 0);
    String query = "SELECT * FROM stok";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            int ids = rs.getInt("id_stok");
            String namas = rs.getString("nama_stok");
            int jumlahs = rs.getInt("jumlah_stok");
            int hargas= rs.getInt("harga_stok");

            model.addRow(new Object[]{ids, namas, jumlahs, hargas});
        }

        tabel4.setModel(model); // Tampilkan data di JTable
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
    
    private javax.swing.table.DefaultTableModel getDefaultTabelModel(){
        return new javax.swing.table.DefaultTableModel(
                new Object [][]{},
                new String []{"ID Stok", "Nama Stok","Jumlah Stok","Harga Stok"}
                ){
            boolean[] canEdit = new boolean []{
                false, false, false, false
            };
         public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
         }    
         };
        }


    // Method initComponents akan di-generate otomatis oleh NetBeans
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Top = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Mid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel4 = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        t_cari = new javax.swing.JTextField();
        rSMaterialButtonRectangle1 = new rojerusan.RSMaterialButtonRectangle();
        t_refresh = new javax.swing.JButton();
        Left = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        Dashboard = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Top.setBackground(new java.awt.Color(255, 255, 255));

        lg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logout_1.png"))); // NOI18N
        lg.setText("LOGOUT");
        lg.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lgMousePressed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout TopLayout = new javax.swing.GroupLayout(Top);
        Top.setLayout(TopLayout);
        TopLayout.setHorizontalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lg)
                .addGap(16, 16, 16))
        );
        TopLayout.setVerticalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addGroup(TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(TopLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lg)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Mid.setBackground(new java.awt.Color(93, 173, 226));
        Mid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama Stok", "Jumlah Stok", "Harga Stok", "Nama Alat", "Biaya Perbaikan Alat"
            }
        ));
        jScrollPane1.setViewportView(tabel4);

        Mid.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 76, 740, 355));

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        Mid.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 30, 30));

        t_cari.setText("Cari Nama Stok");
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
        Mid.add(t_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 25, 261, 40));

        rSMaterialButtonRectangle1.setBackground(new java.awt.Color(48, 89, 213));
        rSMaterialButtonRectangle1.setText("+ Tambahkan Stok");
        rSMaterialButtonRectangle1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSMaterialButtonRectangle1MouseClicked(evt);
            }
        });
        rSMaterialButtonRectangle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSMaterialButtonRectangle1ActionPerformed(evt);
            }
        });
        Mid.add(rSMaterialButtonRectangle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 440, 200, 43));

        t_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        t_refresh.setContentAreaFilled(false);
        t_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_refreshActionPerformed(evt);
            }
        });
        Mid.add(t_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, 40, 30));

        Left.setBackground(new java.awt.Color(44, 46, 141));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Group.png"))); // NOI18N
        jLabel27.setText("     Karyawan");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

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

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Sell Stock.png"))); // NOI18N
        jLabel28.setText("     Stok");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Transaction.png"))); // NOI18N
        jLabel29.setText("     Riwayat Transaksi");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
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

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Caretaker_1.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Name.png"))); // NOI18N
        jLabel41.setText("     User");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Graph Report_2.png"))); // NOI18N
        jLabel42.setText("     Laporan");
        jLabel42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel42MouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Service.png"))); // NOI18N
        jLabel43.setText("     Maintance");
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
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
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addGap(25, 25, 25)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel41)
                .addGap(10, 10, 10)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel42)
                .addGap(10, 10, 10)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
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
                    .addComponent(Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Mid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Left, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void openForm(){
        Owner ownerForm = new Owner();
        ownerForm.setVisible(true);
    }
    
    public static void openFormPelanggan(){
        Owner_Pelanggan ownerForm = new Owner_Pelanggan();
        ownerForm.setVisible(true);
    }
    
    public static void openFormKaryawan(){
        Owner_Karyawan ownerForm = new Owner_Karyawan();
        ownerForm.setVisible(true);
    }
    
    public static void openFormLayanan(){
        Owner_Layanan ownerForm = new Owner_Layanan();
        ownerForm.setVisible(true);
    }
    
    public static void openFormDiskon(){
        Owner_Diskon ownerForm = new Owner_Diskon();
        ownerForm.setVisible(true);
    }
    
    public static void openFormStok(){
        Owner_Stok ownerForm = new Owner_Stok();
        ownerForm.setVisible(true);
    }
    
    public static void openFormRiwayat(){
        Owner_Transaksi ownerForm = new Owner_Transaksi();
        ownerForm.setVisible(true);
    }
    
    public static void openFormReport(){
        Report ownerForm = new Report();
        ownerForm.setVisible(true);
    }
    
    public static void openFormUser(){
        Owner_User ownerForm = new Owner_User();
        ownerForm.setVisible(true);
    }
    private void lgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lgMousePressed
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
    }//GEN-LAST:event_lgMousePressed

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        Owner.openFormKaryawan();
        dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        Owner.openFormLayanan();
        dispose();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        Owner.openFormPelanggan();
        dispose();
    }//GEN-LAST:event_jLabel31MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        // TODO add your handling code here:
        Owner.openFormStok();
        dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        Owner.openFormRiwayat();
        dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
        Owner.openForm();
        dispose();
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        // TODO add your handling code here:
        Owner.openFormDiskon();
        dispose();
    }//GEN-LAST:event_jLabel35MouseClicked

    private void rSMaterialButtonRectangle1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSMaterialButtonRectangle1ActionPerformed

    private void rSMaterialButtonRectangle1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSMaterialButtonRectangle1MouseClicked
            // TODO add your handling code here:
    Owner_StokInput tambah = new Owner_StokInput(); // Buat instance form
    tambah.setVisible(true); 
    this.dispose(); // Tutup form LOGIN (opsional)
    }//GEN-LAST:event_rSMaterialButtonRectangle1MouseClicked

    private void t_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_cariActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
    String cari = t_cari.getText().trim();  // Mengambil teks pencarian dari TextField

    if (cari.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Harap masukkan nama stok yang ingin dicari!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return; // Tidak lanjutkan jika field kosong
    }

    // Menghapus semua data lama di JTable
    DefaultTableModel model = (DefaultTableModel) tabel4.getModel();
    model.setRowCount(0); // Menghapus semua baris sebelumnya

    try (Connection conn = Owner_Stok.DatabaseConnection.getConnection()) {
        // Query untuk mencari stok berdasarkan nama
        String sql = "SELECT * FROM stok WHERE nama_stok LIKE ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + cari + "%");  // Menggunakan wildcard untuk pencarian

            try (ResultSet rs = pst.executeQuery()) {
                boolean found = false;  // Flag untuk mengecek jika ada hasil pencarian
                while (rs.next()) {
                    // Menambahkan hasil pencarian ke JTable
                    model.addRow(new Object[]{
                        rs.getString("id_stok"),           // Nama Stok
                        rs.getString("nama_stok"),           // Nama Stok
                        rs.getInt("jumlah_stok"),            // Jumlah Stok
                        rs.getInt("harga_stok"),          // Harga Stok
                    });
                    found = true;  // Tandakan bahwa data ditemukan
                }

                // Jika tidak ada data ditemukan
                if (!found) {
                    JOptionPane.showMessageDialog(null, "Maaf, data stok tidak ditemukan!", "Pencarian Gagal", JOptionPane.ERROR_MESSAGE);
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
        if (pass.equals("Cari Nama Stok")){
            t_cari.setText("");
        }
    }//GEN-LAST:event_t_cariFocusGained

    private void t_cariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_cariFocusLost
String Password=t_cari.getText();
        if(Password.equals("")||Password.equals("Cari Nama Stok")){
            t_cari.setText("Cari Nama Stok");
        }
    }//GEN-LAST:event_t_cariFocusLost

    private void t_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_refreshActionPerformed
        loadTableData();
    }//GEN-LAST:event_t_refreshActionPerformed

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
        Owner.openFormReport();
      dispose();

    }//GEN-LAST:event_jLabel42MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        
        Owner.openFormUser();
      dispose();
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
        Owner.openFormMaintance();
        dispose();
    }//GEN-LAST:event_jLabel43MouseClicked

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
            java.util.logging.Logger.getLogger(Owner_Stok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Owner_Stok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Owner_Stok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Owner_Stok.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Owner_Stok().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Mid;
    private javax.swing.JPanel Top;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lg;
    private rojerusan.RSMaterialButtonRectangle rSMaterialButtonRectangle1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField t_cari;
    private javax.swing.JButton t_refresh;
    private javax.swing.JTable tabel4;
    // End of variables declaration//GEN-END:variables
}
