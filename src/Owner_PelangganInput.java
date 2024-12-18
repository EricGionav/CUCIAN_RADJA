
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
public class Owner_PelangganInput extends javax.swing.JFrame {

    /**
     * Creates new form Kasir_PelangganInput
     */
    public Owner_PelangganInput() {
        setUndecorated(true);
        initComponents();
        setAutoIncrementId();
        setAutoIncrementAntrian();
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
    
      private void setAutoIncrementId() {
    String query = "SELECT MAX(id_pelanggan) AS last_id FROM pelanggan";

    try (Connection conn = Owner_PelangganInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            int lastId = rs.getInt("last_id");
            int nextId = lastId + 1; // Tambahkan 1 ke ID terakhir
            id_p.setText(String.valueOf(nextId));
        } else {
            id_p.setText("1"); // Jika tidak ada data, mulai dari 1
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil ID terakhir: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
      
           private void setAutoIncrementAntrian() {
    String query = "SELECT MAX(no_antrian) AS last_noAntrian FROM pelanggan";

    try (Connection conn = Owner_PelangganInput.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            int last_noAntrian = rs.getInt("last_noAntrian");
            int next_noAntrian = last_noAntrian + 1; // Tambahkan 1 ke ID terakhir
            noAntrian.setText(String.valueOf(next_noAntrian));
        } else {
            noAntrian.setText("1"); // Jika tidak ada data, mulai dari 1
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil nomor terakhir: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}
           
           
     private void loadTableData() {
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID Pelanggan", "Nama Pelanggan", "No Antrian", "Plat Nomor", "Jenis Kendaraan"}, 0);
    String query = "SELECT * FROM pelanggan";
    String maxIdQuery = "SELECT MAX(id_pelanggan) AS max_id FROM pelanggan";
    String maxNoAntrianQuery = "SELECT MAX(no_antrian) AS max_antrian FROM pelanggan";

    try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         PreparedStatement psMaxId = conn.prepareStatement(maxIdQuery);
         PreparedStatement psMaxAntrian = conn.prepareStatement(maxNoAntrianQuery);
         ResultSet rs = ps.executeQuery();
         ResultSet rsMaxId = psMaxId.executeQuery();
         ResultSet rsMaxAntrian = psMaxAntrian.executeQuery()) {

        // Mengisi data JTable
        while (rs.next()) {
            int id = rs.getInt("id_pelanggan");
            String nama = rs.getString("nama_pelanggan");
            int noAntrian = rs.getInt("no_antrian");
            String platNomor = rs.getString("plat_nomor");
            String jenisKendaraan = rs.getString("jenis_kendaraan");

            model.addRow(new Object[]{id, nama, noAntrian, platNomor, jenisKendaraan});
        }
        tabel3.setModel(model); // Tampilkan data di JTable

        // Mengatur ID pelanggan otomatis
        if (rsMaxId.next()) {
            int nextId = rsMaxId.getInt("max_id") + 1; // ID berikutnya
            id_p.setText(String.valueOf(nextId)); // Atur TextField dengan ID berikutnya
        } else {
            id_p.setText("1"); // Jika tidak ada data, mulai dari 1
        }

        // Mengatur nomor antrian otomatis
        if (rsMaxAntrian.next()) {
            int nextNoAntrian = rsMaxAntrian.getInt("max_antrian") + 1; // Nomor antrian berikutnya
            noAntrian.setText(String.valueOf(nextNoAntrian)); // Atur TextField dengan nomor antrian berikutnya
        } else {
            noAntrian.setText("1"); // Jika tidak ada data, mulai dari 1
        }

        // Reset ComboBox dan field lain (opsional)
        nama_p.setText(""); 
        platNomor.setText("");
        jenis_k.setSelectedIndex(0); // Reset ComboBox ke pilihan pertama

    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}


     
     public class DataHandler {
    public ArrayList<Object[]> getDataUsers() {
        ArrayList<Object[]> data = new ArrayList<>();
        String query = "SELECT * FROM pelanggan";
        try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new Object[]{rs.getInt("id_pelanggan"), rs.getString("nama_pelanggan"), rs.getString("no_antrian"), rs.getString("plat_nomor"), rs.getString("jenis_kendaraan")});
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
        loadTableData();
    }

    private void loadTableData() {
    DefaultTableModel model = new DefaultTableModel(new String[]{"ID Pelanggan", "Nama Pelanggan", "No Antrian", "Plat Nomor", "Jenis Kendaraan"}, 0);
    String query = "SELECT * FROM pelanggan";
    String maxIdQuery = "SELECT MAX(id_pelanggan) AS max_id FROM pelanggan";

    try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query);
         PreparedStatement psMaxId = conn.prepareStatement(maxIdQuery);
         ResultSet rs = ps.executeQuery();
         ResultSet rsMaxId = psMaxId.executeQuery()) {

        // Mengisi data JTable
        while (rs.next()) {
            int id = rs.getInt("id_pelanggan");
            String nama = rs.getString("nama_pelanggan");
            int noAntrian = rs.getInt("no_antrian");
            String platNomor = rs.getString("plat_nomor");
            String jenisKendaraan = rs.getString("jenis_kendaraan");

            model.addRow(new Object[]{id, nama, noAntrian, platNomor, jenisKendaraan});
        }
        tabel3.setModel(model); // Tampilkan data di JTable

        // Mengatur ID pelanggan otomatis
        if (rsMaxId.next()) {
            int nextId = rsMaxId.getInt("max_id") + 1; // ID berikutnya
            id_p.setText(String.valueOf(nextId)); // Atur TextField dengan ID berikutnya
        } else {
            id_p.setText("1"); // Jika tidak ada data, mulai dari 1
        }

        // Reset ComboBox dan field lain (opsional)
        nama_p.setText(""); 
        noAntrian.setText("");
        platNomor.setText("");
        jenis_k.setSelectedIndex(0); // Reset ComboBox ke pilihan pertama

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
                new String []{"ID Pelanggan","Nama Pelanggan","No Antrian","Plat Nomor","Jenis Kendaraan"}
                ){
            boolean[] canEdit = new boolean []{
                false, false, false, false, false
            };
         public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
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

        Top = new javax.swing.JPanel();
        lg = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        Dashboard = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        Dashboard1 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        Mid = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        id_p = new javax.swing.JTextField();
        nama_p = new javax.swing.JTextField();
        noAntrian = new javax.swing.JTextField();
        platNomor = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        t_cari = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel3 = new javax.swing.JTable();
        btn_refresh = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jenis_k = new javax.swing.JComboBox<>();

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

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout TopLayout = new javax.swing.GroupLayout(Top);
        Top.setLayout(TopLayout);
        TopLayout.setHorizontalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopLayout.createSequentialGroup()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lg)
                .addGap(17, 17, 17))
        );
        TopLayout.setVerticalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addGroup(TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TopLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TopLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(lg, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Left.setBackground(new java.awt.Color(44, 46, 141));

        Dashboard.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        Dashboard.setForeground(new java.awt.Color(255, 255, 255));
        Dashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Dashboard Layout.png"))); // NOI18N
        Dashboard.setText("     Dashboard");
        Dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DashboardMouseClicked(evt);
            }
        });

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

        Dashboard1.setBackground(new java.awt.Color(51, 51, 255));
        Dashboard1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Customer.png"))); // NOI18N
        jLabel18.setText("     Pelanggan");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout Dashboard1Layout = new javax.swing.GroupLayout(Dashboard1);
        Dashboard1.setLayout(Dashboard1Layout);
        Dashboard1Layout.setHorizontalGroup(
            Dashboard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Dashboard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        Dashboard1Layout.setVerticalGroup(
            Dashboard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Dashboard1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Discount.png"))); // NOI18N
        jLabel27.setText("     Diskon Spesial");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
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

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Transaction.png"))); // NOI18N
        jLabel29.setText("     Riwayat Transaksi");
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
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
            .addComponent(Dashboard1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addGap(50, 50, 50))
                    .addComponent(Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel15)
                .addGap(25, 25, 25)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel41)
                .addGap(10, 10, 10)
                .addComponent(Dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel42)
                .addGap(10, 10, 10)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Mid.setBackground(new java.awt.Color(93, 173, 226));
        Mid.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Input Data Pelanggan");
        Mid.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Data Pelanggan");
        Mid.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 40, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Nama Pelanggan");
        Mid.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("ID Pelanggan");
        Mid.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Nomor Antrian");
        Mid.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Plat Nomor");
        Mid.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        id_p.setEnabled(false);
        Mid.add(id_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 90, 150, -1));
        Mid.add(nama_p, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 150, -1));

        noAntrian.setEnabled(false);
        noAntrian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noAntrianActionPerformed(evt);
            }
        });
        Mid.add(noAntrian, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 150, -1));

        platNomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                platNomorActionPerformed(evt);
            }
        });
        Mid.add(platNomor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 150, -1));

        btn_simpan.setBackground(new java.awt.Color(51, 0, 204));
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Save_1_1.png"))); // NOI18N
        btn_simpan.setText("SIMPAN");
        btn_simpan.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        Mid.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 373, 110, 30));

        btn_hapus.setBackground(new java.awt.Color(255, 0, 0));
        btn_hapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Delete Trash.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        Mid.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, -1, -1));

        btn_kembali.setBackground(new java.awt.Color(102, 255, 0));
        btn_kembali.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_kembali.setForeground(new java.awt.Color(255, 255, 255));
        btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Go Back.png"))); // NOI18N
        btn_kembali.setText("Kembali");
        btn_kembali.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        Mid.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 373, -1, -1));

        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Search_1.png"))); // NOI18N
        searchButton.setContentAreaFilled(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        Mid.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 110, 30, 40));

        t_cari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                t_cariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                t_cariFocusLost(evt);
            }
        });
        Mid.add(t_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 112, 231, 35));

        tabel3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Pelanggan", "ID Pelanggan", "Nomor Antrian", "Jenis Kendaraan"
            }
        ));
        tabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel3MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel3);

        Mid.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(338, 165, 471, 279));

        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Refresh.png"))); // NOI18N
        btn_refresh.setContentAreaFilled(false);
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });
        Mid.add(btn_refresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 113, 30, 30));

        btn_ubah.setBackground(new java.awt.Color(255, 255, 0));
        btn_ubah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Change.png"))); // NOI18N
        btn_ubah.setText("Ubah");
        btn_ubah.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        Mid.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 90, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("Jenis Kendaraan");
        Mid.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, -1, -1));

        jenis_k.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RODA 2", "RODA 4" }));
        Mid.add(jenis_k, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 300, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Left, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Mid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Top, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        setVisible(true);    }//GEN-LAST:event_lgMousePressed

    private void DashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DashboardMouseClicked
        // TODO add your handling code here:
        Owner.openForm();
      dispose();
    }//GEN-LAST:event_DashboardMouseClicked

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

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel18MouseClicked

    private void noAntrianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noAntrianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noAntrianActionPerformed

    private void platNomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_platNomorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_platNomorActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
    // Mendapatkan nilai dari TextField dan ComboBox
    String idPelangganStr = id_p.getText();
    String namaPelangganStr = nama_p.getText();
    String noAntrianStr = noAntrian.getText();
    String platNomorStr = platNomor.getText();
    String jenisKendaraanStr = jenis_k.getSelectedItem().toString();

    // Validasi jika ada field kosong
    if (idPelangganStr.isEmpty() || namaPelangganStr.isEmpty() || noAntrianStr.isEmpty() || platNomorStr.isEmpty() || jenisKendaraanStr.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validasi ID Pelanggan harus berupa angka
    int idPelanggan;
    try {
        idPelanggan = Integer.parseInt(idPelangganStr);
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "ID Pelanggan harus berupa angka!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validasi No Antrian harus berupa angka
    int noAntrian;
    try {
        noAntrian = Integer.parseInt(noAntrianStr);
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Nomor Antrian harus berupa angka!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Query untuk menambahkan data baru (INSERT)
    String query = "INSERT INTO pelanggan (id_pelanggan, nama_pelanggan, no_antrian, plat_nomor, jenis_kendaraan) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        // Mengatur parameter untuk query INSERT
        ps.setInt(1, idPelanggan); // ID Pelanggan
        ps.setString(2, namaPelangganStr); // Nama Pelanggan
        ps.setInt(3, noAntrian); // No Antrian
        ps.setString(4, platNomorStr); // Plat Nomor
        ps.setString(5, jenisKendaraanStr); // Jenis Kendaraan

        // Eksekusi query INSERT
        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            loadTableData(); // Memuat ulang data di JTable
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan data.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    
}    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
// Pastikan pengguna memilih baris di JTable
        int selectedRow = tabel3.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil ID karyawan dari baris yang dipilih
        int idPelanggan = (int) tabel3.getValueAt(selectedRow, 0);

        // Konfirmasi penghapusan
        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin menghapus data ini?",
            "Konfirmasi Hapus",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try (Connection conn = Owner_KaryawanInput.DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM pelanggan WHERE id_pelanggan = ?")) {

                // Set parameter ID karyawan yang akan dihapus
                ps.setInt(1, idPelanggan);

                // Eksekusi query
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    loadTableData(); // Refresh tabel setelah penghapusan
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Gagal menghapus data!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        Owner_Pelanggan owner_pelangganinput = new Owner_Pelanggan(); // Buat instance form
        owner_pelangganinput.setVisible(true); // Tampilkan form ForgotPassword
        this.dispose(); // Tutup form LOGIN (opsional)
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        // TODO add your handling code here:
        Owner.openFormDiskon();
      dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

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

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
String cari = t_cari.getText().trim();  // Mengambil ID yang dicari dari TextField

    if (cari.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Harap masukkan ID yang ingin dicari!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return; // Tidak lanjutkan jika field kosong
    }

    // Menghapus semua data lama di JTable
    DefaultTableModel model = (DefaultTableModel) tabel3.getModel();
    model.setRowCount(0); // Menghapus semua baris sebelumnya

    try (Connection conn = Owner_PelangganInput.DatabaseConnection.getConnection()) {
        // Menggunakan PreparedStatement untuk menghindari SQL injection
        String sql = "SELECT * FROM pelanggan WHERE id_pelanggan LIKE ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, "%" + cari + "%");  // Menggunakan wildcard untuk pencarian yang lebih fleksibel

            try (ResultSet rs = pst.executeQuery()) {
                boolean found = false;  // Flag untuk mengecek jika ada hasil pencarian
                while (rs.next()) {
                    // Menampilkan hasil pencarian ke field yang sesuai
                    id_p.setText(rs.getString("id_pelanggan")); 
                    nama_p.setText(rs.getString("nama_pelanggan"));  
                    noAntrian.setText(rs.getString("no_antrian"));  
                    platNomor.setText(rs.getString("plat_nomor"));  
                    jenis_k.setSelectedItem(rs.getString("jenis_kendaraan"));  

                    // Mengaktifkan field untuk diubah
                    id_p.setEnabled(false);
                    nama_p.setEnabled(true);
                    noAntrian.setEnabled(false);
                    platNomor.setEnabled(true);
                    jenis_k.setEnabled(true);

                    // Menambahkan hasil pencarian ke JTable
                    model.addRow(new Object[]{
                        rs.getString("id_pelanggan"), 
                        rs.getString("nama_pelanggan"),    
                        rs.getString("no_antrian"),
                        rs.getString("plat_nomor"),       
                        rs.getString("jenis_kendaraan"), 
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

    // Reset tombol dan text field setelah pencarian
    searchButton.setText("");
    btn_simpan.setEnabled(true);
    btn_kembali.setEnabled(true);
    btn_hapus.setEnabled(true);
    btn_ubah.setEnabled(true);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
            loadTableData();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void tabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel3MouseClicked
        if(evt.getClickCount()==1){
           Tampil();
       }
    }//GEN-LAST:event_tabel3MouseClicked

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
    // Mendapatkan nilai dari TextField dan ComboBox
    String idPelangganStr = id_p.getText();
    String namaPelangganStr = nama_p.getText();
    String noAntrianStr = noAntrian.getText();
    String platNomorStr = platNomor.getText();
    String jenisKendaraanStr = jenis_k.getSelectedItem().toString();

    // Validasi jika ada field kosong
    if (idPelangganStr.isEmpty() || namaPelangganStr.isEmpty() || noAntrianStr.isEmpty() || platNomorStr.isEmpty() || jenisKendaraanStr.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validasi ID Pelanggan harus berupa angka
    int idPelanggan;
    try {
        idPelanggan = Integer.parseInt(idPelangganStr);
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "ID Pelanggan harus berupa angka!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validasi No Antrian harus berupa angka
    int noAntrian;
    try {
        noAntrian = Integer.parseInt(noAntrianStr);
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Nomor Antrian harus berupa angka!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Query untuk mengupdate data
    String query = "UPDATE pelanggan SET nama_pelanggan = ?, no_antrian = ?, plat_nomor = ?, jenis_kendaraan = ? WHERE id_pelanggan = ?";

    try (Connection conn = Owner_Pelanggan.DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        // Mengatur parameter untuk query
        ps.setString(1, namaPelangganStr);
        ps.setInt(2, noAntrian);
        ps.setString(3, platNomorStr);
        ps.setString(4, jenisKendaraanStr);
        ps.setInt(5, idPelanggan);

        // Eksekusi query
        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Data berhasil diperbarui!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            loadTableData(); // Memuat ulang data di JTable
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Tidak ada data yang diperbarui. Periksa ID Pelanggan.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void t_cariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_cariFocusGained
 String Username=t_cari.getText();
        if (Username.equals("Cari ID Pelanggan")){
            t_cari.setText("");
        }
    }//GEN-LAST:event_t_cariFocusGained

    private void t_cariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_t_cariFocusLost
String Username=t_cari.getText();
        if(Username.equals("")||Username.equals("Cari ID Pelanggan")){
            t_cari.setText("Cari ID Pelanggan");
        }
    }//GEN-LAST:event_t_cariFocusLost

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
 int row = 0;
    public void Tampil(){
        row = tabel3.getSelectedRow();
        id_p.setText(tabel3.getValueAt(row,0).toString());
        nama_p.setText(tabel3.getValueAt(row,1).toString());
        noAntrian.setText(tabel3.getValueAt(row,2).toString());
        platNomor.setText(tabel3.getValueAt(row,3).toString());
        jenis_k.setSelectedItem(tabel3.getValueAt(row,4).toString());
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
            java.util.logging.Logger.getLogger(Owner_PelangganInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Owner_PelangganInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Owner_PelangganInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Owner_PelangganInput.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Owner_PelangganInput().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Dashboard;
    private javax.swing.JPanel Dashboard1;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Mid;
    private javax.swing.JPanel Top;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JTextField id_p;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> jenis_k;
    private javax.swing.JLabel lg;
    private javax.swing.JTextField nama_p;
    private javax.swing.JTextField noAntrian;
    private javax.swing.JTextField platNomor;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField t_cari;
    private javax.swing.JTable tabel3;
    // End of variables declaration//GEN-END:variables
}
