/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanly;

import doan.DataBaseHelper;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
/**
 *
 * @author danh
 */
public class JPanelSinhVien extends javax.swing.JPanel {

    /**
     * Creates new form JPanelSinhVien
     */
    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#FFFDE4"), 0, getHeight(), Color.decode("#005AA7"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }
    
    public JPanelSinhVien() {
        initComponents();
        initData();
        initboxLop();
        initboxCn();
        jLabelMaSv.setText(taoMaSv());
        jLabelMaSv.setForeground(Color.RED);
        vohieuButton();
    }
    DefaultTableModel model = new DefaultTableModel();
    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date date = java.util.Calendar.getInstance().getTime();
    
    
    public void vohieuButton()
    {
        jButtonSua.setEnabled(false);
        jButtonXoa.setEnabled(false);

    }
    
    public void KichHoatButton()
    {
        jButtonSua.setEnabled(true);
        jButtonXoa.setEnabled(true);
    }
    
    public String taoMaSv()
    {
        String sql = "select masv from sinhvien";
        int max =0;
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();
                 )
        {   
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {   
                int ma = Integer.parseInt(rs.getString(1).substring(2,rs.getString(1).length()).trim());
                if(ma> max)
                    max = ma;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "SV" + String.valueOf(max +1);
    }
    
    public String taoMaLop()
    {
        String sql = "select malop from lop";
        int max =0;
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();
                 )
        {   
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {   
                int ma = Integer.parseInt(rs.getString(1).substring(2,rs.getString(1).length()).trim());
                if(ma> max)
                    max = ma;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "LO" + String.valueOf(max +1);
    }
    
    public String taoMaDoAnTotNghiep()
    {
        String sql = "select mada from doantotnghiep";
        int max =0;
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();
                 )
        {   
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {   
                int ma = Integer.parseInt(rs.getString(1).substring(2,rs.getString(1).length()).trim());
                if(ma> max)
                    max = ma;
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "DA" + String.valueOf(max +1);
    }
    
    public void initData()
    {   
        model = (DefaultTableModel) jTableDSSV.getModel();
        String sql = "select * from sinhvien";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();)
        {   
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                vt.add(rs.getString(3).equals("0") ? "Nam":"Nữ");
                vt.add(rs.getString(4));
                vt.add(rs.getString(5));
                model.addRow(vt);
            }
            jTableDSSV.setModel(model);
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void timKiemSv(String s)
    {   
        model = (DefaultTableModel) jTableDSSV.getModel();
        String sql = "select * from sinhvien where masv like N'%"+s+"%' or hoten like N'%"+s+"%' or diachi like N'%"+s+"%' or ngaysinh like N'%"+s+"%'";
        model.setRowCount(0);
        try(Connection con = DataBaseHelper.getConnection();
                Statement smt = con.createStatement();)
        {   
            Vector vt;
            ResultSet rs = smt.executeQuery(sql);
            while(rs.next())
            {
                vt = new Vector();
                vt.add(rs.getString(1));
                vt.add(rs.getString(2));
                vt.add(rs.getString(3).equals("0") ? "Nam":"Nữ");
                vt.add(rs.getString(4));
                vt.add(rs.getString(5));
                model.addRow(vt);
            }
            jTableDSSV.setModel(model);
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("" + sql);
        
    }
    
    public void initboxLop() {
        String sql = "select tenlop from lop";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxLopHoc.addItem(rs.getString(1));
            }          
            jComboBoxLopHoc.setSelectedIndex(-1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void initboxCn() {
        String sql = "select tencn from chuyennganh";
        try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
            ResultSet rs = smt.executeQuery();
            while (rs.next()) {
                jComboBoxCn.addItem(rs.getString(1));
            }          
            

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public String searchMaLop(String tenLop) {
        String sql = " SELECT malop FROM lop WHERE tenlop=N'" + tenLop + "'";
        try {
            Connection con = DataBaseHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public String searchMaCn(String tenCn) {
        String sql = " SELECT macn FROM chuyennganh WHERE tencn=N'" + tenCn + "'";
        try {
            Connection con = DataBaseHelper.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public void lamMoi()
    {
        jTextHoTenSv.setText("");
        jTextDiaChi.setText("");
        jLabelMaSv.setText("");
        jTextAreaDaTn.setText("");
        jComboBoxKhoaHoc.setSelectedIndex(-1);
        jComboBoxLopHoc.setSelectedIndex(-1);
        jComboBoxCn.setSelectedIndex(-1);
        jDateNgaySinhSv.setDate(date);
    }
    
    
    
    public String insertDoAnTotNghiep(String maSv)
    {
        String maDa = null;
        String sql = "insert into doantotnghiep values(?,?,?,?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql)) {
            maDa = taoMaDoAnTotNghiep();
            smt.setString(1, maDa);
            smt.setFloat(2, 0 );
            smt.setFloat(3, 0 );
            smt.setString(4, jTextAreaDaTn.getText());
            smt.setString(5,maSv);
            smt.setNull(6, Types.NULL);
            smt.setNull(7, Types.NULL);
            smt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return maDa;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabelMaSv = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTextHoTenSv = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jRadioNam = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioNu = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jDateNgaySinhSv = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        jTextDiaChi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxKhoaHoc = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxLopHoc = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxCn = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jRadioButtonDangDiHoc = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jRadioButtonDaNghi = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSSV = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaDaTn = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButtonLamMoi = new javax.swing.JButton();
        jButtonThem = new javax.swing.JButton();
        jButtonSua = new javax.swing.JButton();
        jButtonXoa = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextTimKiem = new javax.swing.JTextField();

        setForeground(new java.awt.Color(255, 255, 255));

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(6, 2));

        jLabel12.setBackground(new java.awt.Color(255, 204, 204));
        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel12.setText("Mã Sinh Viên: ");
        jPanel2.add(jLabel12);

        jLabelMaSv.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.add(jLabelMaSv);

        jLabel16.setBackground(new java.awt.Color(255, 204, 204));
        jLabel16.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel16.setText("Họ Tên: ");
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 22));
        jPanel2.add(jLabel16);

        jTextHoTenSv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextHoTenSvActionPerformed(evt);
            }
        });
        jPanel2.add(jTextHoTenSv);

        jLabel15.setBackground(new java.awt.Color(255, 204, 204));
        jLabel15.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel15.setText("Giới Tính: ");
        jPanel2.add(jLabel15);

        buttonGroup1.add(jRadioNam);
        jRadioNam.setSelected(true);
        jRadioNam.setText("Nam");
        jPanel2.add(jRadioNam);
        jPanel2.add(jLabel1);

        buttonGroup1.add(jRadioNu);
        jRadioNu.setText("Nữ");
        jPanel2.add(jRadioNu);

        jLabel13.setBackground(new java.awt.Color(255, 204, 204));
        jLabel13.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel13.setText("Ngày Sinh: ");
        jPanel2.add(jLabel13);
        jPanel2.add(jDateNgaySinhSv);

        jLabel17.setBackground(new java.awt.Color(255, 204, 204));
        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel17.setText("Địa Chỉ: ");
        jPanel2.add(jLabel17);

        jTextDiaChi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDiaChiActionPerformed(evt);
            }
        });
        jPanel2.add(jTextDiaChi);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridLayout(6, 2));

        jLabel4.setBackground(new java.awt.Color(255, 204, 204));
        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel4.setText("Khóa Học: ");
        jPanel3.add(jLabel4);

        jComboBoxKhoaHoc.setEditable(true);
        jComboBoxKhoaHoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2020-2024", "2021-2025", "2022-2026" }));
        jPanel3.add(jComboBoxKhoaHoc);

        jLabel14.setBackground(new java.awt.Color(255, 204, 204));
        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel14.setText("Lớp Học");
        jPanel3.add(jLabel14);

        jComboBoxLopHoc.setEditable(true);
        jPanel3.add(jComboBoxLopHoc);

        jLabel9.setBackground(new java.awt.Color(255, 204, 204));
        jLabel9.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel9.setText("Chuyên Ngành");
        jPanel3.add(jLabel9);

        jPanel3.add(jComboBoxCn);

        jLabel10.setBackground(new java.awt.Color(255, 204, 204));
        jLabel10.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel10.setText("Trạng Thái Nghỉ:");
        jPanel3.add(jLabel10);

        buttonGroup2.add(jRadioButtonDangDiHoc);
        jRadioButtonDangDiHoc.setSelected(true);
        jRadioButtonDangDiHoc.setText("Đang đi học");
        jRadioButtonDangDiHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonDangDiHocActionPerformed(evt);
            }
        });
        jPanel3.add(jRadioButtonDangDiHoc);

        jLabel11.setBackground(new java.awt.Color(255, 204, 204));
        jLabel11.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jPanel3.add(jLabel11);

        buttonGroup2.add(jRadioButtonDaNghi);
        jRadioButtonDaNghi.setText("Đã nghỉ");
        jPanel3.add(jRadioButtonDaNghi);

        jPanel5.setBackground(new java.awt.Color(255, 204, 204));
        jPanel5.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Sinh Viên");
        jPanel5.add(jLabel2);

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 204));

        jTableDSSV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Sinh Viên", "Họ và Tên", "Phái", "NGày Sinh", "Địa Chỉ"
            }
        ));
        jTableDSSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSSVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSSV);

        jLabel8.setBackground(new java.awt.Color(255, 204, 204));
        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel8.setText("Đề Án Tốt Nghiệp: ");

        jTextAreaDaTn.setColumns(20);
        jTextAreaDaTn.setRows(5);
        jScrollPane2.setViewportView(jTextAreaDaTn);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(1, 4, 20, 0));

        jButtonLamMoi.setBackground(new java.awt.Color(0, 102, 204));
        jButtonLamMoi.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLamMoi.setText("Làm Mới");
        jButtonLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLamMoiActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonLamMoi);

        jButtonThem.setBackground(new java.awt.Color(0, 102, 204));
        jButtonThem.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonThem.setForeground(new java.awt.Color(255, 255, 255));
        jButtonThem.setText("Thêm");
        jButtonThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonThem);

        jButtonSua.setBackground(new java.awt.Color(0, 102, 204));
        jButtonSua.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonSua.setForeground(new java.awt.Color(255, 255, 255));
        jButtonSua.setText("Sửa");
        jButtonSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSua);

        jButtonXoa.setBackground(new java.awt.Color(0, 102, 204));
        jButtonXoa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonXoa.setForeground(new java.awt.Color(255, 255, 255));
        jButtonXoa.setText("Xóa");
        jButtonXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonXoa);

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel7.setText("Tìm Kiếm:");
        jLabel7.setMaximumSize(new java.awt.Dimension(90, 22));
        jLabel7.setPreferredSize(new java.awt.Dimension(90, 13));

        jTextTimKiem.setPreferredSize(new java.awt.Dimension(79, 13));
        jTextTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextTimKiemActionPerformed(evt);
            }
        });
        jTextTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextTimKiemKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 994, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 733, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 41, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextHoTenSvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextHoTenSvActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextHoTenSvActionPerformed

    private void jTextDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDiaChiActionPerformed

    private void jRadioButtonDangDiHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonDangDiHocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonDangDiHocActionPerformed

    private void jButtonLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLamMoiActionPerformed
        // TODO add your handling code here:
        lamMoi();
        jTableDSSV.setRowSelectionAllowed(false);  
    }//GEN-LAST:event_jButtonLamMoiActionPerformed

    private void jButtonThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonThemActionPerformed
        // TODO add your handling code here:
        
        if (searchMaLop(jComboBoxLopHoc.getSelectedItem().toString()) == null) {

            String sql = "insert into lop values(?,?)";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql)) {
                smt.setString(1, taoMaLop());
                smt.setString(2, jComboBoxLopHoc.getSelectedItem().toString());
                smt.executeUpdate();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            jComboBoxLopHoc.addItem(jComboBoxLopHoc.getSelectedItem().toString());
        }
        

        String sql = "insert into sinhvien values(?,?,?,?,?,  ?,?,?,?,?,?)";
        try (Connection con = DataBaseHelper.getConnection();
            PreparedStatement smt = con.prepareStatement(sql);) {
            String maSv = taoMaSv();
            smt.setString(1, maSv);
            smt.setString(2, jTextHoTenSv.getText());
            //smt.setInt(3, jRadioNam.isSelected() ? '0': '1' );
            if (jRadioNam.isSelected()) {
                smt.setBoolean(3, false);
            } else {
                smt.setBoolean(3, true);
            }
            
            smt.setString(4, ft.format(jDateNgaySinhSv.getDate()));
            smt.setString(5, jTextDiaChi.getText());
            smt.setString(6, jComboBoxKhoaHoc.getSelectedItem().toString());
            //smt.setInt(7, jRadioButtonDaNghi.isSelected() ? '1': '0' );
            if (jRadioButtonDaNghi.isSelected()) {
                smt.setBoolean(7, true);
            } else {
                smt.setBoolean(7, false);
            }
            
            //jComboBoxTotNghiep.getSelectedItem().toString()
           // jComboBoxCn.getSelectedItem().toString()
           
            smt.setString(9, searchMaCn(jComboBoxCn.getSelectedItem().toString()) );
            smt.setString(10, searchMaLop(jComboBoxLopHoc.getSelectedItem().toString()) );
            smt.setString(11, "123");
            
            if(jTextAreaDaTn.getText().equals("")) 
                smt.setNull(8, Types.NULL);
            else
            {
               smt.setNull(8, Types.NULL);  
                //smt.setString(8,maDa);  
            }
            
            int kt = smt.executeUpdate();
            String maDa  = insertDoAnTotNghiep(maSv);
            System.out.println("" + maDa);
            if (kt > 0) {
                JOptionPane.showMessageDialog(this, "insert thanh cong");
            }
            initData();
            lamMoi();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButtonThemActionPerformed

    private void jButtonXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonXoaActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "ban co muon xoa khong");
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) {
            String sql = "delete from sinhvien where masv=?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {
                smt.setString(1, jLabelMaSv.getText());

                int kt2 = smt.executeUpdate();
                if (kt2 > 0) {
                    JOptionPane.showMessageDialog(this, "xoa thanh cong");
                }
                initData();
                lamMoi();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
//            String sql2 = "delete from doantotnghiep where masv=?";
//            try (Connection con = DataBaseHelper.getConnection();
//                PreparedStatement smt = con.prepareStatement(sql);) {
//                smt.setString(1, jLabelMaSv.getText());
//
//                int kt2 = smt.executeUpdate();
//                if (kt2 > 0) {
//                    JOptionPane.showMessageDialog(this, "xoa thanh cong");
//                }
//                initData();
//                lamMoi();
//
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
        }
    }//GEN-LAST:event_jButtonXoaActionPerformed

    private void jButtonSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSuaActionPerformed
        // TODO add your handling code here:
        int kt = JOptionPane.showConfirmDialog(this, "ban chan chan muon sua khong");
        
        if (kt == JOptionPane.CANCEL_OPTION) {
            return;
        } else if (kt == JOptionPane.OK_OPTION) 
        
        {
            String sql = "update sinhvien set hoten=?,phai=?,ngaysinh=?,diachi=?,khoahoc=?,trangthainghi=?,mada=?,macn=?,malop=? where masv = ?";
            try (Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql);) {

                smt.setString(1, jTextHoTenSv.getText());
                //smt.setInt(2, jRadioNam.isSelected() ? '0': '1' );
                if (jRadioNam.isSelected()) {
                    smt.setBoolean(2, false);
                } else {
                    smt.setBoolean(2, true);
                }
                
                smt.setString(3, ft.format(jDateNgaySinhSv.getDate()));
                smt.setString(4, jTextDiaChi.getText());
                smt.setString(5, jComboBoxKhoaHoc.getSelectedItem().toString());
                //smt.setInt(6, jRadioButtonDaNghi.isSelected() ? '1': '0' );
                if (jRadioButtonDaNghi.isSelected()) {
                    smt.setBoolean(6, true);
                } else {
                    smt.setBoolean(6, false);
                }

                smt.setNull(7, Types.NULL);
                smt.setString(8, searchMaCn(jComboBoxCn.getSelectedItem().toString()));
                smt.setString(9, searchMaLop(jComboBoxLopHoc.getSelectedItem().toString()));
                smt.setString(10, jLabelMaSv.getText());
                int kt1 = smt.executeUpdate();
                
                int kt2=0;
                String sql2 = "update doantotnghiep set noidung=? where masv=?";
                try (PreparedStatement smt2 = con.prepareStatement(sql2);) {
                smt2.setString(1, jTextAreaDaTn.getText());
                smt2.setString(2, jLabelMaSv.getText());
                kt2= smt2.executeUpdate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex.toString());
                }

                
                int kqkt = kt1 + kt2;
                if (kqkt > 1) {
                    JOptionPane.showMessageDialog(this, "update thanh cong");
                }

                initData();
                lamMoi();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.toString());
            }
            
            

            
        }
    }//GEN-LAST:event_jButtonSuaActionPerformed
    
    
    private void jTableDSSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSSVMouseClicked
        // TODO add your handling code here:
        int row = jTableDSSV.getSelectedRow();
        if(row>=0)
        {
//            jLabelMaMH.setText(jTableDSSV.getValueAt(row, 0).toString().toUpperCase());
//            jTextMh.setText(jTableDSSV.getValueAt(row, 1).toString());
//            jTextStLt.setText(jTableDSSV.getValueAt(row, 2).toString());
//            jTextStTh.setText(jTableDSSV.getValueAt(row, 3).toString());
//            jTextSTc.setText(jTableDSSV.getValueAt(row, 4).toString());
            String sql = "{ CALL LOAD_TEXT_SINHVIEN(?) }";
            try(Connection con = DataBaseHelper.getConnection();
                PreparedStatement smt = con.prepareStatement(sql); 
               ) {
                
                smt.setString(1, jTableDSSV.getValueAt(row, 0).toString());
                ResultSet rs = smt.executeQuery();
                if (rs.next()) {
                    jLabelMaSv.setText(rs.getString(1));
                    jTextHoTenSv.setText(rs.getString(2));
                    if(rs.getString(3).equals("0"))
                        jRadioNam.setSelected(true);
                    else
                        jRadioNu.setSelected(true);
                    jDateNgaySinhSv.setDate(ft.parse(rs.getString(4).toString()));
                    jTextDiaChi.setText(rs.getString(5));
                    jComboBoxKhoaHoc.setSelectedItem(rs.getString(6));
                    jComboBoxLopHoc.setSelectedItem(rs.getString(7));
                    jComboBoxCn.setSelectedItem(rs.getString(8));
                    if(rs.getString(9).equals("0"))
                        jRadioButtonDangDiHoc.setSelected(true);
                    else
                        jRadioButtonDaNghi.setSelected(true);
                    jTextAreaDaTn.setText(rs.getString(10));
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            KichHoatButton();
            jButtonThem.setEnabled(false);
        }
        
    }//GEN-LAST:event_jTableDSSVMouseClicked

    private void jTextTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextTimKiemActionPerformed

    private void jTextTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextTimKiemKeyReleased
        // TODO add your handling code here:
        timKiemSv(jTextTimKiem.getText());
    }//GEN-LAST:event_jTextTimKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButtonLamMoi;
    private javax.swing.JButton jButtonSua;
    private javax.swing.JButton jButtonThem;
    private javax.swing.JButton jButtonXoa;
    private javax.swing.JComboBox<String> jComboBoxCn;
    private javax.swing.JComboBox<String> jComboBoxKhoaHoc;
    private javax.swing.JComboBox<String> jComboBoxLopHoc;
    private com.toedter.calendar.JDateChooser jDateNgaySinhSv;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelMaSv;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonDaNghi;
    private javax.swing.JRadioButton jRadioButtonDangDiHoc;
    private javax.swing.JRadioButton jRadioNam;
    private javax.swing.JRadioButton jRadioNu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableDSSV;
    private javax.swing.JTextArea jTextAreaDaTn;
    private javax.swing.JTextField jTextDiaChi;
    private javax.swing.JTextField jTextHoTenSv;
    private javax.swing.JTextField jTextTimKiem;
    // End of variables declaration//GEN-END:variables
}
