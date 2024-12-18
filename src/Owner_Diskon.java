
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JPanel;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class Owner_Diskon extends javax.swing.JFrame {

          private JLabel[] hargaLabels;

    /**
     * Creates new form Owner_Diskon
     */
    public Owner_Diskon() {
     setUndecorated(true);
     initComponents();
     hargaLabels = new JLabel[]{diskon1, diskon2};  // Sesuaikan dengan nama JLabel Anda
     // Memuat gambar pada setiap JLabel sesuai dengan id_paket saat aplikasi dimulai
    loadImageFromDatabase(1, diskon1);
    loadImageFromDatabase(2, diskon2);
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
    
  private ImageIcon resizeImageIcon(ImageIcon imageIcon, int maxWidth, int maxHeight) {
    Image image = imageIcon.getImage();

    // Dapatkan dimensi asli gambar
    int width = image.getWidth(null);
    int height = image.getHeight(null);

    // Tentukan rasio baru untuk ukuran gambar yang sesuai dengan batas maksimal
    double ratio = Math.min((double) maxWidth / width, (double) maxHeight / height);
    int newWidth = (int) (width * ratio);
    int newHeight = (int) (height * ratio);

    // Mengubah ukuran gambar sesuai rasio
    Image resizedImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    return new ImageIcon(resizedImage);
}

private void loadImageFromDatabase(int idPaket, JLabel label) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT image FROM diskon WHERE id_diskon = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPaket);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                byte[] imageBytes = rs.getBytes("image");
                if (imageBytes != null) {
                    ImageIcon icon = new ImageIcon(imageBytes);

                    // Resize gambar agar sesuai dengan ukuran maksimal yang ditentukan
                    ImageIcon resizedIcon = resizeImageIcon(icon, 651, 146);  // Sesuaikan ukuran maksimal sesuai kebutuhan
                    label.setIcon(resizedIcon);
                } else {
                    label.setIcon(null);
                    JOptionPane.showMessageDialog(null, "Paket ini tidak memiliki gambar.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Gambar dengan id_paket " + idPaket + " tidak ditemukan.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal memuat gambar: " + e.getMessage());
    }
}


   
   
    
    private void saveImageToDatabase(int idPaket, File imageFile) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "UPDATE diskon SET image = ? WHERE id_diskon = ?"; // Query untuk update gambar
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            FileInputStream fis = new FileInputStream(imageFile);
            stmt.setBinaryStream(1, fis, (int) imageFile.length()); // Set gambar dalam bentuk stream
            stmt.setInt(2, idPaket); // Set ID paket yang ingin diperbarui
            stmt.executeUpdate();
            System.out.println("Gambar berhasil disimpan dan diperbarui!");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menyimpan gambar: " + e.getMessage());
    }
}
    


    private void selectAndSaveImage(int idPaket, JLabel label) {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        saveImageToDatabase(idPaket, selectedFile); // Simpan gambar ke database
        loadImageFromDatabase(idPaket, label); // Memuat gambar terbaru ke JLabel
    }
}




private void displayImageOnLabel(JLabel label, String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
        ImageIcon imageIcon = new ImageIcon(filePath);
        Image image = imageIcon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(image));  // Set gambar pada JLabel
        label.setText("");  // Menghapus teks jika ada
    } else {
        System.out.println("Gambar tidak ditemukan di path: " + filePath);
    }
}


private void ubahActionPerformed(JLabel label, int idPaket) {
    // Pilih file gambar
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        
        // Simpan gambar ke database dan tampilkan di JLabel
        saveImageToDatabase(idPaket, selectedFile);
        loadImageFromDatabase(idPaket, label); // Memuat gambar terbaru ke JLabel
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
        jLabel2 = new javax.swing.JLabel();
        Left = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        Dashboard = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        Mid = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        diskon1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        diskon2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Top.setBackground(new java.awt.Color(255, 255, 255));
        Top.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TopMousePressed(evt);
            }
        });

        lg.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Logout_1.png"))); // NOI18N
        lg.setText("LOGOUT");
        lg.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        lg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lgMousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo cucian eric baru.png"))); // NOI18N

        javax.swing.GroupLayout TopLayout = new javax.swing.GroupLayout(Top);
        Top.setLayout(TopLayout);
        TopLayout.setHorizontalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TopLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lg)
                .addGap(17, 17, 17))
        );
        TopLayout.setVerticalGroup(
            TopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(TopLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Discount.png"))); // NOI18N
        jLabel30.setText("     Diskon Spesial");
        jLabel30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel30MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout DashboardLayout = new javax.swing.GroupLayout(Dashboard);
        Dashboard.setLayout(DashboardLayout);
        DashboardLayout.setHorizontalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DashboardLayout.setVerticalGroup(
            DashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
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

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Dashboard Layout.png"))); // NOI18N
        jLabel33.setText("     Dashboard");
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Caretaker_1.png"))); // NOI18N

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
            .addComponent(Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(LeftLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(LeftLayout.createSequentialGroup()
                        .addGroup(LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(LeftLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel40))
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        LeftLayout.setVerticalGroup(
            LeftLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LeftLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel40)
                .addGap(25, 25, 25)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel42)
                .addGap(10, 10, 10)
                .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        Mid.setBackground(new java.awt.Color(93, 173, 226));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("PROMO CUCIAN RADJA");

        diskon1.setBackground(new java.awt.Color(0, 102, 102));
        diskon1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        diskon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Upload To FTP.png"))); // NOI18N
        jButton2.setText("UPLOAD");
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        diskon2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        diskon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Upload To FTP.png"))); // NOI18N
        jButton3.setText("UPLOAD  ");
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MidLayout = new javax.swing.GroupLayout(Mid);
        Mid.setLayout(MidLayout);
        MidLayout.setHorizontalGroup(
            MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MidLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MidLayout.createSequentialGroup()
                        .addComponent(diskon1, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(MidLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(185, 185, 185))
                        .addGroup(MidLayout.createSequentialGroup()
                            .addComponent(diskon2, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MidLayout.setVerticalGroup(
            MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MidLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGroup(MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MidLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(diskon2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(MidLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jButton3)))
                .addGap(59, 59, 59)
                .addGroup(MidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diskon1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MidLayout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(64, 64, 64)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
    }//GEN-LAST:event_lgMousePressed

    private void TopMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TopMousePressed
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
    }//GEN-LAST:event_TopMousePressed

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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        selectAndSaveImage(1, diskon1); // Memilih dan menyimpan gambar untuk paket dengan id_paket 1
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         selectAndSaveImage(2, diskon2); // Memilih dan menyimpan gambar untuk paket dengan id_paket 1
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jLabel41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel41MouseClicked
        // TODO add your handling code here:
        Owner.openFormUser();
      dispose();
    }//GEN-LAST:event_jLabel41MouseClicked

    private void jLabel30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel30MouseClicked
        // TODO add your handling code here:
        Owner.openFormDiskon();
        dispose();
    }//GEN-LAST:event_jLabel30MouseClicked

    private void jLabel42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel42MouseClicked
        // TODO add your handling code here:
        Owner.openFormReport();
      dispose();
    }//GEN-LAST:event_jLabel42MouseClicked

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
            java.util.logging.Logger.getLogger(Owner_Diskon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Owner_Diskon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Owner_Diskon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Owner_Diskon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Owner_Diskon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dashboard;
    private javax.swing.JPanel Left;
    private javax.swing.JPanel Mid;
    private javax.swing.JPanel Top;
    private javax.swing.JLabel diskon1;
    private javax.swing.JLabel diskon2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel lg;
    // End of variables declaration//GEN-END:variables
}
