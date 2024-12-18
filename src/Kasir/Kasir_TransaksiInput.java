/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Kasir;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ACER
 */
public class Kasir_TransaksiInput extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_TransaksiInput
     */
    public Kasir_TransaksiInput() {
        setUndecorated(true);
        initComponents();
        setCurrentDateTime();
        setAutoIncrementId();
        loadPelangganData();
        loadKaryawanData();
        loadPaketData();
        loadTable();
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

       
       public void initForm() {
    // Mengosongkan JTable saat form dibuka
    resetTable();

    // Menyiapkan ID Transaksi baru dengan mengambil ID terakhir dari database dan menambahkannya
    int idTransaksiBaru = getIDTransaksiBaru();
    id_t.setText(String.valueOf(idTransaksiBaru));  // Set ID transaksi baru ke TextField
}
       
       
       // Memuat data ke JTable
private void loadTable() {
    // Model untuk JTable
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "HargaPaket"}, 
        0
    );
    // Query untuk memilih transaksi terakhir berdasarkan ID transaksi
    String query = "SELECT * FROM transaksi ORDER BY id_transaksi DESC LIMIT 1";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        // Hanya ada satu baris hasil, karena kita mengambil transaksi dengan id terbesar
        if (rs.next()) {
            // Membaca data dari ResultSet
            int id = rs.getInt("id_transaksi");
            int idp = rs.getInt("id_pelanggan");
            int idk = rs.getInt("id_karyawan");
            String namap = rs.getString("id_paket");
            String tgl = rs.getString("tanggal_transaksi");
            String harga = rs.getString("harga_paket");

            // Menambahkan data ke model
            model.addRow(new Object[]{id, idp, idk, namap, tgl, harga});
        }

        // Atur model ke JTable
        Model_Transaksi.setModel(model);

        // Atur ukuran kolom dan pengaturan lainnya
        Tabel(Model_Transaksi, new int[]{100, 150, 150, 150, 250, 150});

        // Panggil metode untuk mengatur ID otomatis berikutnya
        setAutoIncrementId();

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(
            this, 
            "Gagal memuat data: " + e.getMessage(), 
            "Error", 
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
}





private void resetTable() {
    // Mengosongkan semua data di JTable
    DefaultTableModel model = (DefaultTableModel) Model_Transaksi.getModel();
    model.setRowCount(0);  // Menghapus semua baris di JTable
}


        // Mengatur ukuran kolom JTable
        private void Tabel(javax.swing.JTable tb, int lebar[]) {
            tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  // Nonaktifkan auto-resize
            int kolom = tb.getColumnCount();

            // Menyesuaikan ukuran kolom sesuai lebar yang diberikan
            for (int i = 0; i < kolom; i++) {
                javax.swing.table.TableColumn tbc = tb.getColumnModel().getColumn(i);
                tbc.setPreferredWidth(lebar[i]);  // Set lebar kolom
            }

            tb.setRowHeight(25);  // Menetapkan tinggi baris
        }

        // Model default untuk JTable
        private javax.swing.table.DefaultTableModel getDefaultTabelModel() {
            return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "Harga Paket"}
            ) {
                boolean[] canEdit = new boolean[] {
                    false, false, false, false, false, false  // Kolom tidak dapat diedit
                };

                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };
        }


        private void setCurrentDateTime() {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       String currentDateTime = LocalDateTime.now().format(formatter);
       tglskrg.setText(currentDateTime);
   }
     
        private void setAutoIncrementId() {
    String query = "SELECT MAX(id_transaksi) AS last_id FROM transaksi";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int lastId = rs.getInt("last_id");
            int nextId = lastId + 1; // Tambahkan 1 ke ID terakhir
            id_t.setText(String.valueOf(nextId));
        } else {
            id_t.setText("1"); // Jika tidak ada data, mulai dari 1
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil ID terakhir: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}


        private void loadPelangganData() {
       String query = "SELECT id_pelanggan, nama_pelanggan FROM pelanggan"; // Ubah sesuai dengan tabel dan kolom Anda
       try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

           // Menambahkan item ke combo box
           while (rs.next()) {
               int idPelanggan = rs.getInt("id_pelanggan");
               String namaPelanggan = rs.getString("nama_pelanggan");
               id_p.addItem(idPelanggan + " - " + namaPelanggan); // Format id_pelanggan dan nama_pelanggan
           }
       } catch (Exception e) {
           e.printStackTrace();
           javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pelanggan: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
       }
   }
     
        private void loadKaryawanData() {
       String query = "SELECT id_karyawan, nama_karyawan FROM karyawan"; // Ubah sesuai dengan tabel dan kolom Anda
       try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {

           // Menambahkan item ke combo box
           while (rs.next()) {
               int idKaryawan = rs.getInt("id_karyawan");
               String namaKaryawan = rs.getString("nama_karyawan");
               id_k.addItem(idKaryawan + " - " + namaKaryawan); // Format id_pelanggan dan nama_pelanggan
           }
       } catch (Exception e) {
           e.printStackTrace();
           javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data karyawan: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
       }
   }
        private void loadPaketData() {
      String query = "SELECT id_paket, nama_paket FROM daftar_paket"; // Ubah sesuai dengan tabel dan kolom Anda
      try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
           PreparedStatement ps = conn.prepareStatement(query);
           ResultSet rs = ps.executeQuery()) {

          // Menambahkan item ke combo box
          while (rs.next()) {
              int idPaket = rs.getInt("id_paket");
              String namaPaket = rs.getString("nama_paket");
              id_pkt.addItem(idPaket + " - " + namaPaket); // Format id_pelanggan dan nama_pelanggan
          }
      } catch (Exception e) {
          e.printStackTrace();
          javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data pelanggan: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
      }
  }
        
       public class StokUpdater {

    public static void scheduleStokUpdateAtStartOfMonth() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateStokKanebo();  // Memanggil fungsi untuk mengurangi stok kanebo
            }
        };

        Timer timer = new Timer(true);

        // Menentukan waktu untuk tengah malam pada tanggal 1 setiap bulan
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);        // Jam 00:00 (tengah malam)
        calendar.set(Calendar.MINUTE, 0);             // Menit 00
        calendar.set(Calendar.SECOND, 0);             // Detik 00
        calendar.set(Calendar.MILLISECOND, 0);        // Milidetik 00
        calendar.set(Calendar.DAY_OF_MONTH, 1);       // Tanggal 1 setiap bulan

        // Jika waktu sekarang sudah lewat tanggal 1 bulan ini, atur untuk bulan berikutnya
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.MONTH, 1);  // Tambahkan 1 bulan ke depan
        }

        // Mendapatkan waktu untuk awal bulan berikutnya
        long firstOfNextMonth = calendar.getTimeInMillis();
        long period = 1000L * 60L * 60L * 24L * 30L;  // Interval bulanan (~30 hari)

        // Menjadwalkan tugas untuk dijalankan pada awal bulan berikutnya
        timer.scheduleAtFixedRate(task, firstOfNextMonth, period);
    }

    private static void updateStokKanebo() {
        // Fungsi untuk mengurangi stok kanebo setiap bulan
        System.out.println("Stok kanebo berhasil diperbarui.");
    }

    public static void main(String[] args) {
        // Menjadwalkan pembaruan stok pada awal bulan
        scheduleStokUpdateAtStartOfMonth();
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
        Top7 = new javax.swing.JPanel();
        lg7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tglskrg = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Model_Transaksi = new javax.swing.JTable();
        id_p = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        id_k = new javax.swing.JComboBox<>();
        id_pkt = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        id_t = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_hitung = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        ttl_harga = new javax.swing.JTextField();
        txt_bayar = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

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
            .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel15)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Top7.setBackground(new java.awt.Color(255, 255, 255));

        lg7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lg7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logout_1.png"))); // NOI18N
        lg7.setText("LOGOUT");
        lg7.setFocusable(false);
        lg7.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lg7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lg7MousePressed(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout Top7Layout = new javax.swing.GroupLayout(Top7);
        Top7.setLayout(Top7Layout);
        Top7Layout.setHorizontalGroup(
            Top7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Top7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 857, Short.MAX_VALUE)
                .addComponent(lg7)
                .addGap(19, 19, 19))
        );
        Top7Layout.setVerticalGroup(
            Top7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Top7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lg7, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(Top7Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(93, 173, 226));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Input Data Transaksi");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Harga");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("ID Karyawan");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Tanggal Transaksi");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, -1, 20));

        tglskrg.setEnabled(false);
        tglskrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglskrgActionPerformed(evt);
            }
        });
        jPanel1.add(tglskrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, 150, -1));

        txt_harga.setEnabled(false);
        jPanel1.add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 150, -1));

        btn_simpan.setBackground(new java.awt.Color(0, 51, 204));
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save_1.png"))); // NOI18N
        btn_simpan.setText("SIMPAN");
        btn_simpan.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 480, 100, 30));

        btn_keluar.setBackground(new java.awt.Color(51, 255, 0));
        btn_keluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_keluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Go Back.png"))); // NOI18N
        btn_keluar.setText("KEMBALI");
        btn_keluar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 480, 100, 30));

        Model_Transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID Transaksi", "ID Pelanggan", "ID Karyawan", "ID Paket", "Tanggal Transaksi", "Harga"
            }
        ));
        jScrollPane1.setViewportView(Model_Transaksi);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 840, 250));

        jPanel1.add(id_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 90, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Kembalian");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 490, -1, -1));

        jPanel1.add(id_k, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 90, -1));

        id_pkt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_pktActionPerformed(evt);
            }
        });
        jPanel1.add(id_pkt, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, 150, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("ID Transaksi");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        id_t.setEnabled(false);
        id_t.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_tActionPerformed(evt);
            }
        });
        jPanel1.add(id_t, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 150, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("ID Pelanggan");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 130, -1, -1));

        btn_hitung.setText("Hitung");
        btn_hitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hitungActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hitung, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 540, 90, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setText("ID Paket");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("Total Harga");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 480, -1, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("Bayar");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, -1, -1));

        txt_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_kembalian.setEnabled(false);
        txt_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kembalianActionPerformed(evt);
            }
        });
        jPanel1.add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 480, 220, 50));

        ttl_harga.setEnabled(false);
        ttl_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttl_hargaActionPerformed(evt);
            }
        });
        jPanel1.add(ttl_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 480, 150, -1));
        jPanel1.add(txt_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 510, 150, -1));

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Discount_1.png"))); // NOI18N
        jButton1.setText("DISKON");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 320, 110, 30));

        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 220, 110, 30));

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 270, 110, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Top7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1076, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Left, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(Top7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private void tglskrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglskrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tglskrgActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
    // Dapatkan model dari tabel untuk mengakses data
    DefaultTableModel model = (DefaultTableModel) Model_Transaksi.getModel();

    // Jika tidak ada data di tabel, tampilkan pesan
    if (model.getRowCount() == 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Tidak ada transaksi untuk disimpan!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Menggunakan transaksi untuk memastikan konsistensi data
    String queryTransaksi = "INSERT INTO transaksi (id_transaksi, id_pelanggan, id_karyawan, id_paket, tanggal_transaksi, harga_paket) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection()) {
        conn.setAutoCommit(false);  // Menonaktifkan auto-commit untuk memulai transaksi

        try (PreparedStatement psTransaksi = conn.prepareStatement(queryTransaksi)) {
            // Iterasi melalui setiap baris di tabel untuk menyimpan data transaksi
            for (int i = 0; i < model.getRowCount(); i++) {
                // Ambil data dari setiap baris tabel
                int idTransaksi = (int) model.getValueAt(i, 0);
                int idPelanggan = (int) model.getValueAt(i, 1);
                int idKaryawan = (int) model.getValueAt(i, 2);
                int idPaket = (int) model.getValueAt(i, 3);
                String tanggal = model.getValueAt(i, 4).toString();
                int hargaPaket = (int) model.getValueAt(i, 5);

                // Mengisi parameter query transaksi
                psTransaksi.setInt(1, idTransaksi);
                psTransaksi.setInt(2, idPelanggan);
                psTransaksi.setInt(3, idKaryawan);
                psTransaksi.setInt(4, idPaket);
                psTransaksi.setString(5, tanggal);
                psTransaksi.setInt(6, hargaPaket);

                // Eksekusi query transaksi untuk setiap baris
                psTransaksi.addBatch();  // Menambahkan ke batch
            }

            // Eksekusi batch untuk menyimpan semua transaksi
            int[] result = psTransaksi.executeBatch();

            // Cek apakah ada transaksi yang berhasil disimpan
            if (result.length > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

                // Setelah transaksi berhasil disimpan, reset tabel dan total harga
                model.setRowCount(0);  // Menghapus semua baris dalam JTable
                totalHarga = 0;  // Reset total harga
                ttl_harga.setText("Total Harga: 0");  // Menampilkan total harga yang baru

                // Mengaktifkan tombol Simpan dan Reset form jika diperlukan
                btn_simpan.setEnabled(true);  // Aktifkan kembali tombol simpan jika diperlukan
            }

            // Commit transaksi jika semua query berhasil
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();  // Rollback jika terjadi kesalahan dalam transaksi
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal menghubungkan ke database: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    // Mengosongkan form setelah simpan berhasil
    id_p.setSelectedIndex(0);  // Reset ComboBox
    id_k.setSelectedIndex(0);
    id_pkt.setSelectedIndex(0);
    txt_harga.setText("");

    setAutoIncrementId(); // Panggil untuk memperbarui ID transaksi di txtid_t
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        Kasir_Transaksi owner_transaksiinput = new Kasir_Transaksi(); // Buat instance form
        owner_transaksiinput.setVisible(true); // Tampilkan form ForgotPassword
        this.dispose(); // Tutup form LOGIN (opsional)
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void lg7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lg7MousePressed
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
    }//GEN-LAST:event_lg7MousePressed

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

    private void id_pktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_pktActionPerformed
        String selectedItem = (String) id_pkt.getSelectedItem();

        if (selectedItem != null) {
            String id_Paket = selectedItem.split(" - ")[0]; // Ambil ID Barang saja

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cucianradja2", "root", "");
                String query = "SELECT harga_paket FROM daftar_paket WHERE id_paket = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, id_Paket);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String harga = rs.getString("harga_paket");
                    txt_harga.setText(harga); // Tampilkan harga di text field
                }

                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal mengambil harga: " + ex.getMessage());
            }
        }

        id_pkt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                id_pktActionPerformed(evt);
            }
        });
    }//GEN-LAST:event_id_pktActionPerformed

    private void id_tActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_id_tActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_id_tActionPerformed

    private void btn_hitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hitungActionPerformed
    try {
        // Ambil nilai dari JTextField
        String bayarText = txt_bayar.getText().replaceAll("[^\\d]", ""); // Menghapus karakter non-digit
        String totalBayarText = ttl_harga.getText().replaceAll("[^\\d]", ""); // Menghapus karakter non-digit

        // Pastikan string tidak kosong setelah pembersihan
        if (bayarText.isEmpty() || totalBayarText.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Masukkan angka yang valid pada semua field.",
                "Input Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Konversi ke tipe numerik (gunakan int)
        int bayar = Integer.parseInt(bayarText); // Input dari pengguna
        int totalBayar = Integer.parseInt(totalBayarText); // Total bayar

        // Periksa apakah bayar lebih kecil dari totalBayar
        if (bayar < totalBayar) {
            // Tampilkan pesan error jika bayar kurang dari totalBayar
            javax.swing.JOptionPane.showMessageDialog(this,
                "Jumlah yang dibayar tidak cukup. Silakan masukkan jumlah yang lebih besar.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return; // Batalkan perhitungan kembalian
        }

        // Hitung kembalian jika bayar >= totalBayar
        int kembalian = bayar - totalBayar;

        // Tampilkan kembalian ke txtKembalian
        txt_kembalian.setText(String.valueOf(kembalian));

    } catch (NumberFormatException e) {
        // Tampilkan pesan error jika input tidak valid
        javax.swing.JOptionPane.showMessageDialog(this,
            "Masukkan angka yang valid!",
            "Input Error",
            javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btn_hitungActionPerformed

    private void txt_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kembalianActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Periksa apakah hari ini adalah 1 Januari
        java.time.LocalDate today = java.time.LocalDate.now();

        if (today.getDayOfMonth() == 1 && today.getMonth() == java.time.Month.JANUARY) {
            try (Connection conn = DatabaseConnection.getConnection()) {
                // Query untuk memeriksa apakah diskon sudah diterapkan tahun ini
                String queryCheck = "SELECT MAX(tanggal_diskon) AS tanggal_terakhir FROM daftar_paket";
                try (PreparedStatement psCheck = conn.prepareStatement(queryCheck);
                    ResultSet rs = psCheck.executeQuery()) {

                    if (rs.next()) {
                        java.sql.Date lastDiscountDate = rs.getDate("tanggal_terakhir");

                        // Jika diskon sudah diterapkan tahun ini, tampilkan pesan
                        if (lastDiscountDate != null &&
                            lastDiscountDate.toLocalDate().getYear() == today.getYear()) {
                            javax.swing.JOptionPane.showMessageDialog(this,
                                "Diskon sudah diterapkan pada awal tahun ini. Tidak dapat menerapkan diskon lagi.",
                                "Diskon Sudah Berlaku",
                                javax.swing.JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                }

                // Proses diskon untuk harga stok
                String querySelect = "SELECT id_paket, harga_paket, harga_paketNormal FROM daftar_paket";
                try (PreparedStatement psSelect = conn.prepareStatement(querySelect);
                    ResultSet rs = psSelect.executeQuery()) {

                    String queryUpdate = "UPDATE daftar_paket SET harga_paket = ?, harga_paketNormal = ?, tanggal_diskon = ? WHERE id_paket = ?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(queryUpdate)) {

                        while (rs.next()) {
                            int idStok = rs.getInt("id_paket");
                            double hargaStok = rs.getDouble("harga_paket");
                            Double hargaNormal = rs.getDouble("harga_paketNormal");

                            // Jika harga_normal belum diatur, simpan harga asli
                            if (hargaNormal == 0) {
                                hargaNormal = hargaStok;
                            }

                            // Hitung harga setelah diskon 10%
                            double hargaDiskon = hargaStok - (hargaStok * 0.10);

                            // Update harga stok, harga normal, dan tanggal diskon di database
                            psUpdate.setDouble(1, hargaDiskon); // harga setelah diskon
                            psUpdate.setDouble(2, hargaNormal); // harga asli
                            psUpdate.setDate(3, java.sql.Date.valueOf(today)); // tanggal diskon
                            psUpdate.setInt(4, idStok); // id_paket
                            psUpdate.executeUpdate();
                        }
                    }

                    javax.swing.JOptionPane.showMessageDialog(this,
                        "Diskon 10% berhasil diterapkan ke semua harga stok untuk awal tahun!",
                        "Diskon Berhasil",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Gagal menerapkan diskon: " + e.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Diskon hanya berlaku pada awal tahun (1 Januari).\n" +
                "Kunjungi kami kembali pada 1 Januari untuk diskon spesial!",
                "Diskon Tidak Berlaku",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        Kasir_Das.OpenKasirFormUser();
        dispose();
    }//GEN-LAST:event_jLabel41MouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
// Mengosongkan JTable saat form dibuka
    resetTable();

    // Menyiapkan ID Transaksi baru dengan mengambil ID terakhir dari database dan menambahkannya
    int idTransaksiBaru = getIDTransaksiBaru();
    id_t.setText(String.valueOf(idTransaksiBaru));  // Set ID transaksi baru ke TextField
    }//GEN-LAST:event_formWindowOpened
private int totalHarga = 0; // Variabel untuk menyimpan total harga (menggunakan int)

private void addTransactionToDatabase(int idTransaksi, int idPelanggan, int idKaryawan, int idPaket, String tanggal, String hargaPaket) {
    String query = "INSERT INTO transaksi (id_transaksi, id_pelanggan, id_karyawan, id_paket, tanggal_transaksi, harga_paket) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, idTransaksi);
        ps.setInt(2, idPelanggan);
        ps.setInt(3, idKaryawan);
        ps.setInt(4, idPaket);
        ps.setString(5, tanggal);
        ps.setString(6, hargaPaket);

        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Error saat menyimpan transaksi ke database.", "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}




private int getHargaPaketById(int idPaket) {
    int hargaPaket = 0;
    String query = "SELECT harga_paket FROM daftar_paket WHERE id_paket = ?";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, idPaket);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            hargaPaket = rs.getInt("harga_paket");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return hargaPaket;
}

private int getNextTransactionId() {
    String query = "SELECT MAX(id_transaksi) FROM transaksi";
    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            int maxId = rs.getInt(1);
            return maxId + 1; // ID transaksi berikutnya
        } else {
            // Jika tidak ada transaksi sebelumnya, mulai dari ID 1
            return 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil ID transaksi: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return -1; // Mengembalikan ID -1 sebagai tanda error
    }
}

private int getLastTransactionId() {
    String query = "SELECT MAX(id_transaksi) AS last_id FROM transaksi";

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("last_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil ID terakhir: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    return 0;  // Jika tidak ada transaksi, mulai dari ID 1
}






    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
    // Mendapatkan nilai dari TextField dan ComboBox
    String idPelangganStr = id_p.getSelectedItem().toString();
    String idKaryawanStr = id_k.getSelectedItem().toString();
    String idPaketStr = id_pkt.getSelectedItem().toString();
    String tanggal = tglskrg.getText();

    // Validasi jika field kosong
    if (idPelangganStr.isEmpty() || idKaryawanStr.isEmpty() || idPaketStr.isEmpty() || tanggal.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Ambil ID pelanggan, ID karyawan, dan ID paket (angka sebelum tanda '-')
    int idPelanggan = Integer.parseInt(idPelangganStr.split(" - ")[0]);
    int idKaryawan = Integer.parseInt(idKaryawanStr.split(" - ")[0]);
    int idPaket = Integer.parseInt(idPaketStr.split(" - ")[0]);

    // Ambil harga berdasarkan ID Paket
    int hargaPaket = getHargaPaketById(idPaket);

    // Ambil ID transaksi dari txtid_t dan tambahkan ke tabel
    int idTransaksi = Integer.parseInt(id_t.getText());

    // Tambahkan data ke JTable
    DefaultTableModel model = (DefaultTableModel) Model_Transaksi.getModel();
    model.addRow(new Object[]{idTransaksi, idPelanggan, idKaryawan, idPaket, tanggal, hargaPaket});

    // Update total harga
    totalHarga += hargaPaket;
    ttl_harga.setText("Total Harga: " + totalHarga);

    // Perbarui ID transaksi untuk transaksi berikutnya
    id_t.setText(String.valueOf(idTransaksi + 1));

    // Reset form setelah transaksi ditambahkan
    id_p.setSelectedIndex(0);
    id_k.setSelectedIndex(0);
    id_pkt.setSelectedIndex(0);
    txt_harga.setText("");

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
    // Dapatkan model dari JTable
    DefaultTableModel model = (DefaultTableModel) Model_Transaksi.getModel();

    // Periksa apakah ada baris yang dipilih
    int selectedRow = Model_Transaksi.getSelectedRow();
    if (selectedRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Ambil nilai harga dari baris yang dipilih
    int hargaPaket = (int) model.getValueAt(selectedRow, 5); // Kolom harga (sesuaikan dengan indeks tabel Anda)

    // Kurangi total harga
    totalHarga -= hargaPaket;
    ttl_harga.setText("Total Harga: " + totalHarga);

    // Hapus baris dari JTable
    model.removeRow(selectedRow);

    javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil dihapus!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_btn_hapusActionPerformed

    private void ttl_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttl_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttl_hargaActionPerformed
       private int getIDTransaksiBaru() {
    int idTransaksiBaru = 1; // ID default jika belum ada transaksi

    String query = "SELECT MAX(id_transaksi) FROM transaksi";  // Query untuk mendapatkan ID transaksi terbesar

    try (Connection conn = Kasir_TransaksiInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            // Mengambil ID transaksi terakhir dan menambah 1 untuk ID transaksi baru
            idTransaksiBaru = rs.getInt(1) + 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mendapatkan ID transaksi baru: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    return idTransaksiBaru;
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
            java.util.logging.Logger.getLogger(Kasir_TransaksiInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Kasir_TransaksiInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Kasir_TransaksiInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Kasir_TransaksiInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kasir_TransaksiInput().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Left;
    private javax.swing.JTable Model_Transaksi;
    private javax.swing.JPanel Top7;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_hitung;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> id_k;
    private javax.swing.JComboBox<String> id_p;
    private javax.swing.JComboBox<String> id_pkt;
    private javax.swing.JTextField id_t;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lg7;
    private javax.swing.JTextField tglskrg;
    private javax.swing.JTextField ttl_harga;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_kembalian;
    // End of variables declaration//GEN-END:variables
}
