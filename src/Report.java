
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ACER
 */
public class Report extends javax.swing.JFrame {

    private Connection conn;

    public Report() {
        setUndecorated(true);
        initComponents();
        loadComboBoxTahun();  // Isi ComboBox dengan tahun
        loadComboBoxBulan();  // Isi ComboBox dengan bulan
        loadLaporanLabaRugi();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Pastikan koneksi terbuka
        conn = DatabaseConnection.getConnection();
        
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal!");
        }

        // Event listener untuk tombol Filter
        btnFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                filterData(); // Panggil method filterData()
            }
        });
    }
    
    

    // Method untuk mengisi ComboBox Tahun
    private void loadComboBoxTahun() {
        int tahunSekarang = LocalDate.now().getYear();

        // Hapus item yang ada di JComboBox terlebih dahulu (jika ada)
        cmbTahun.removeAllItems();

        // Tambahkan tahun dari 5 tahun yang lalu hingga 5 tahun ke depan
        for (int i = tahunSekarang - 5; i <= tahunSekarang + 5; i++) {
            cmbTahun.addItem(String.valueOf(i));
        }

        // Set default pilihan tahun ke tahun sekarang
        cmbTahun.setSelectedItem(String.valueOf(tahunSekarang));
    }

    // Method untuk mengisi ComboBox Bulan
    private void loadComboBoxBulan() {
        cmbBulan.removeAllItems(); // Hapus item yang sudah ada (jika ada)

        // Menambahkan bulan 1 hingga 12
        for (int i = 1; i <= 12; i++) {
            cmbBulan.addItem(String.valueOf(i)); // Tambahkan bulan 1 sampai 12
        }

        // Set default pilihan bulan, misalnya bulan sekarang
        cmbBulan.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
    }

    // Method untuk Filter Data
    private void filterData() {
        // Ambil bulan dan tahun yang dipilih dari combobox
        String bulan = String.valueOf(cmbBulan.getSelectedItem()); // Bulan dalam format angka
        String tahun = String.valueOf(cmbTahun.getSelectedItem());

        // Pastikan koneksi database valid
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal!");
            return;
        }

        try {
            // Query SQL Filter
            String query = "SELECT COALESCE(t1.tanggal_transaksi, t2.tanggal_stok, t3.tanggal_pemeliharaan) AS tanggal, " +
                           "COALESCE(t1.total_pemasukan, 0) AS total_pemasukan, " +
                           "COALESCE(t2.total_pengeluaran_stok, 0) AS total_pengeluaran_stok, " +
                           "COALESCE(t3.total_pengeluaran_maintenance, 0) AS total_pengeluaran_maintenance, " +
                           "COALESCE(t1.total_pemasukan, 0) - (COALESCE(t2.total_pengeluaran_stok, 0) + COALESCE(t3.total_pengeluaran_maintenance, 0)) AS laba_rugi " +
                           "FROM (SELECT DATE(tanggal_transaksi) AS tanggal_transaksi, SUM(harga_paket) AS total_pemasukan " +
                           "      FROM transaksi " +
                           "      WHERE MONTH(tanggal_transaksi) = ? AND YEAR(tanggal_transaksi) = ? " +
                           "      GROUP BY DATE(tanggal_transaksi)) t1 " +
                           "LEFT JOIN " +
                           "     (SELECT DATE(tanggal_stok) AS tanggal_stok, SUM(harga_stok) AS total_pengeluaran_stok " +
                           "      FROM stok " +
                           "      WHERE MONTH(tanggal_stok) = ? AND YEAR(tanggal_stok) = ? " +
                           "      GROUP BY DATE(tanggal_stok)) t2 " +
                           "ON t1.tanggal_transaksi = t2.tanggal_stok " +
                           "LEFT JOIN " +
                           "     (SELECT DATE(tanggal_pemeliharaan) AS tanggal_pemeliharaan, SUM(harga_pemeliharaanAlat) AS total_pengeluaran_maintenance " +
                           "      FROM maintance " +
                           "      WHERE MONTH(tanggal_pemeliharaan) = ? AND YEAR(tanggal_pemeliharaan) = ? " +
                           "      GROUP BY DATE(tanggal_pemeliharaan)) t3 " +
                           "ON t1.tanggal_transaksi = t3.tanggal_pemeliharaan OR t2.tanggal_stok = t3.tanggal_pemeliharaan";

            // Persiapkan statement dengan query dan parameter
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bulan); // Bulan untuk transaksi
            ps.setString(2, tahun); // Tahun untuk transaksi
            ps.setString(3, bulan); // Bulan untuk stok
            ps.setString(4, tahun); // Tahun untuk stok
            ps.setString(5, bulan); // Bulan untuk pemeliharaan
            ps.setString(6, tahun); // Tahun untuk pemeliharaan

            ResultSet rs = ps.executeQuery();

            // Tampilkan di JTable
            DefaultTableModel model = (DefaultTableModel) tblLaporanLabaRugi.getModel();
            model.setRowCount(0); // Reset tabel

            while (rs.next()) {
            String tanggal = rs.getString("tanggal");
            double pemasukan = rs.getDouble("total_pemasukan");
            double pengeluaranStok = rs.getDouble("total_pengeluaran_stok");
            double pengeluaranMaintenance = rs.getDouble("total_pengeluaran_maintenance");
            double labaRugi = rs.getDouble("laba_rugi");

            // Tambahkan data ke model
            model.addRow(new Object[] {
                tanggal,
                formatCurrency(pemasukan),
                formatCurrency(pengeluaranStok),
                formatCurrency(pengeluaranMaintenance),
                formatCurrency(labaRugi)
            });
        }

        // Set model ke JTable
        tblLaporanLabaRugi.setModel(model);

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(
            this, 
            "Gagal memuat laporan: " + e.getMessage(), 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}

    


    
    private String formatCurrency(double value) {
    // Membuat format mata uang Indonesia (Rp)
    java.text.NumberFormat currencyFormat = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("id", "ID"));
    return currencyFormat.format(value);
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
  
private void loadLaporanLabaRugi() {
    // Model untuk JTable
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"Tanggal", "Total Pemasukan", "Total Pengeluaran Stok", "Total Pengeluaran Maintenance", "Laba/Rugi"}, 
        0
    );

    // Ambil bulan dan tahun yang dipilih dari combobox
    String bulan = String.valueOf(cmbBulan.getSelectedIndex() + 1); // Bulan dalam format angka
    String tahun = cmbTahun.getSelectedItem().toString();

    // Query yang telah diperbaiki untuk hanya membandingkan tanggal (tanpa waktu)
    String query = "SELECT COALESCE(t1.tanggal_transaksi, t2.tanggal_stok, t3.tanggal_pemeliharaan) AS tanggal, " +
                   "COALESCE(t1.total_pemasukan, 0) AS total_pemasukan, " +
                   "COALESCE(t2.total_pengeluaran_stok, 0) AS total_pengeluaran_stok, " +
                   "COALESCE(t3.total_pengeluaran_maintenance, 0) AS total_pengeluaran_maintenance, " +
                   "COALESCE(t1.total_pemasukan, 0) - (COALESCE(t2.total_pengeluaran_stok, 0) + COALESCE(t3.total_pengeluaran_maintenance, 0)) AS laba_rugi " +
                   "FROM (SELECT DATE(tanggal_transaksi) AS tanggal_transaksi, SUM(harga_paket) AS total_pemasukan " +
                   "      FROM transaksi " +
                   "      WHERE MONTH(tanggal_transaksi) = ? AND YEAR(tanggal_transaksi) = ? " +
                   "      GROUP BY DATE(tanggal_transaksi)) t1 " +
                   "LEFT JOIN " +
                   "     (SELECT DATE(tanggal_stok) AS tanggal_stok, SUM(harga_stok) AS total_pengeluaran_stok " +
                   "      FROM stok " +
                   "      WHERE MONTH(tanggal_stok) = ? AND YEAR(tanggal_stok) = ? " +
                   "      GROUP BY DATE(tanggal_stok)) t2 " +
                   "ON t1.tanggal_transaksi = t2.tanggal_stok " +
                   "LEFT JOIN " +
                   "     (SELECT DATE(tanggal_pemeliharaan) AS tanggal_pemeliharaan, SUM(harga_pemeliharaanAlat) AS total_pengeluaran_maintenance " +
                   "      FROM maintance " +
                   "      WHERE MONTH(tanggal_pemeliharaan) = ? AND YEAR(tanggal_pemeliharaan) = ? " +
                   "      GROUP BY DATE(tanggal_pemeliharaan)) t3 " +
                   "ON t1.tanggal_transaksi = t3.tanggal_pemeliharaan OR t2.tanggal_stok = t3.tanggal_pemeliharaan";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        // Set parameter untuk bulan dan tahun
        ps.setString(1, bulan); // Bulan untuk transaksi
        ps.setString(2, tahun); // Tahun untuk transaksi
        ps.setString(3, bulan); // Bulan untuk stok
        ps.setString(4, tahun); // Tahun untuk stok
        ps.setString(5, bulan); // Bulan untuk pemeliharaan
        ps.setString(6, tahun); // Tahun untuk pemeliharaan

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String tanggal = rs.getString("tanggal");
            double pemasukan = rs.getDouble("total_pemasukan");
            double pengeluaranStok = rs.getDouble("total_pengeluaran_stok");
            double pengeluaranMaintenance = rs.getDouble("total_pengeluaran_maintenance");
            double labaRugi = rs.getDouble("laba_rugi");

            // Tambahkan data ke model
            model.addRow(new Object[] {
                tanggal,
                formatCurrency(pemasukan),
                formatCurrency(pengeluaranStok),
                formatCurrency(pengeluaranMaintenance),
                formatCurrency(labaRugi)
            });
        }

        // Set model ke JTable
        tblLaporanLabaRugi.setModel(model);

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(
            this, 
            "Gagal memuat laporan: " + e.getMessage(), 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
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

        Left4 = new javax.swing.JPanel();
        Dashboard4 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Top = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLaporanLabaRugi = new javax.swing.JTable();
        cmbBulan = new javax.swing.JComboBox<>();
        cmbTahun = new javax.swing.JComboBox<>();
        btnFilter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Left4.setBackground(new java.awt.Color(44, 46, 141));

        Dashboard4.setBackground(new java.awt.Color(51, 51, 255));
        Dashboard4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Graph Report_2.png"))); // NOI18N
        jLabel33.setText("     Laporan");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Dashboard4Layout = new javax.swing.GroupLayout(Dashboard4);
        Dashboard4.setLayout(Dashboard4Layout);
        Dashboard4Layout.setHorizontalGroup(
            Dashboard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dashboard4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Dashboard4Layout.setVerticalGroup(
            Dashboard4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard4Layout.createSequentialGroup()
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jLabel25.setBackground(new java.awt.Color(51, 51, 255));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Group.png"))); // NOI18N
        jLabel25.setText("     Karyawan");
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel25MouseClicked(evt);
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

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Sell Stock.png"))); // NOI18N
        jLabel28.setText("     Stok");
        jLabel28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel28MouseClicked(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Discount.png"))); // NOI18N
        jLabel30.setText("     Diskon Spesial");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
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

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Dashboard Layout.png"))); // NOI18N
        jLabel27.setText("     Dashboard");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Caretaker_1.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(255, 255, 255));
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Name.png"))); // NOI18N
        jLabel41.setText("     User");
        jLabel41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel41MouseClicked(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Customer.png"))); // NOI18N
        jLabel24.setText("     Pelanggan");
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Service.png"))); // NOI18N
        jLabel31.setText("     Maintance");
        jLabel31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel31MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Left4Layout = new javax.swing.GroupLayout(Left4);
        Left4.setLayout(Left4Layout);
        Left4Layout.setHorizontalGroup(
            Left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Dashboard4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Left4Layout.createSequentialGroup()
                .addGroup(Left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Left4Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Left4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                    .addGroup(Left4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(Left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(Left4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Left4Layout.setVerticalGroup(
            Left4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Left4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel41)
                .addGap(10, 10, 10)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Dashboard4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(82, Short.MAX_VALUE))
        );

        Top.setBackground(new java.awt.Color(255, 255, 255));

        lg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logout_1.png"))); // NOI18N
        lg.setText("LOGOUT");
        lg.setFocusable(false);
        lg.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lgMousePressed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout TopLayout = new javax.swing.GroupLayout(Top);
        Top.setLayout(TopLayout);
        TopLayout.setHorizontalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lg)
                .addGap(16, 16, 16))
        );
        TopLayout.setVerticalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(TopLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lg, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(93, 173, 226));

        tblLaporanLabaRugi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Tanggal", "Total Pemesanan", "Total Pegeluaran S", "Total Pengelaran M", "Laba/Rugi"
            }
        ));
        jScrollPane1.setViewportView(tblLaporanLabaRugi);

        cmbBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBulanActionPerformed(evt);
            }
        });

        cmbTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTahunActionPerformed(evt);
            }
        });

        btnFilter.setText("Cari");
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnFilter)))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbTahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnFilter)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Left4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Left4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseClicked
        // TODO add your handling code here:
        Owner.openFormKaryawan();
        dispose();
    }//GEN-LAST:event_jLabel25MouseClicked

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
        Owner.openFormLayanan();
        dispose();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void jLabel28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel28MouseClicked
        // TODO add your handling code here:
        Owner.openFormStok();
        dispose();
    }//GEN-LAST:event_jLabel28MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code here:
        Owner.openFormDiskon();
        dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        // TODO add your handling code here:
        Owner.openFormRiwayat();
        dispose();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
        Owner.openForm();
        dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

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

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
        Owner.openFormPelanggan();
        dispose();
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        Owner.openFormUser();
      dispose();
    }//GEN-LAST:event_jLabel41MouseClicked
    public static void openFormMaintance(){
        Maintance ownerForm = new Maintance();
        ownerForm.setVisible(true);
    }
    private void jLabel31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel31MouseClicked
        // TODO add your handling code here:
        Owner.openFormMaintance();
      dispose();
    }//GEN-LAST:event_jLabel31MouseClicked

    private void cmbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBulanActionPerformed
    filterData();
    }//GEN-LAST:event_cmbBulanActionPerformed

    private void cmbTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTahunActionPerformed
// Jika ada logika tambahan yang perlu dijalankan setelah memilih tahun, letakkan di sini
    String tahun = cmbTahun.getSelectedItem().toString();
    // Misalnya, Anda bisa langsung memfilter data berdasarkan tahun yang dipilih:
    filterData();
    }//GEN-LAST:event_cmbTahunActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFilterActionPerformed

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
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard4;
    private javax.swing.JPanel Left4;
    private javax.swing.JPanel Top;
    private javax.swing.JButton btnFilter;
    private javax.swing.JComboBox<String> cmbBulan;
    private javax.swing.JComboBox<String> cmbTahun;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lg;
    private javax.swing.JTable tblLaporanLabaRugi;
    // End of variables declaration//GEN-END:variables
}
