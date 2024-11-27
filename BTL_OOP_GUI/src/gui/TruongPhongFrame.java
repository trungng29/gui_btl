/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import gui.Login;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class TruongPhongFrame extends javax.swing.JFrame {

    /**
     * Creates new form TruongPhongFrame
     */
    public TruongPhongFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        headerPanel = new javax.swing.JPanel();
        emsTxt = new javax.swing.JLabel();
        clc02Txt = new javax.swing.JLabel();
        categoryPanel = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JLabel();
        duAnBtn = new javax.swing.JPanel();
        duAnTxt = new javax.swing.JLabel();
        duAnSidePanel = new javax.swing.JPanel();
        ttcnBtn = new javax.swing.JPanel();
        ttcnTxt = new javax.swing.JLabel();
        ttcnSidePanel = new javax.swing.JPanel();
        nhanVienBtn = new javax.swing.JPanel();
        nhanVienTxt = new javax.swing.JLabel();
        nhanVienSidePanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        panelNhanVien = new javax.swing.JPanel();
        nhanVienScrollTable = new javax.swing.JScrollPane();
        nhanVienTable = new javax.swing.JTable();
        nhanVienSearchBar = new javax.swing.JTextField();
        nhanVienSearchBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        luongCBField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        phuCapField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ngayBatDauField = new javax.swing.JTextField();
        maNVField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        hoTenField = new javax.swing.JTextField();
        gioiTinhField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        ngaySinhField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        sdtField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1200, 690));
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 690));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 51, 51));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        headerPanel.setBackground(new java.awt.Color(74, 98, 138));
        headerPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        headerPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emsTxt.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        emsTxt.setForeground(new java.awt.Color(255, 255, 255));
        emsTxt.setText("EMS");
        headerPanel.add(emsTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        clc02Txt.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        clc02Txt.setForeground(new java.awt.Color(205, 225, 238));
        clc02Txt.setText("CLC-02");
        headerPanel.add(clc02Txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jPanel3.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 1200, 50));

        categoryPanel.setBackground(new java.awt.Color(144, 176, 194));
        categoryPanel.setMinimumSize(new java.awt.Dimension(240, 240));
        categoryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoutBtn.setBackground(new java.awt.Color(0, 0, 0));
        logoutBtn.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        logoutBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Logout.png"))); // NOI18N
        logoutBtn.setText("LOGOUT");
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logoutBtnMouseEntered(evt);
            }
        });
        categoryPanel.add(logoutBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, -1, -1));

        duAnBtn.setBackground(new java.awt.Color(205, 225, 238));
        duAnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                duAnBtnMouseClicked(evt);
            }
        });
        duAnBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        duAnTxt.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        duAnTxt.setForeground(new java.awt.Color(74, 98, 138));
        duAnTxt.setText("Dự Án");
        duAnBtn.add(duAnTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 0, 130, 50));

        duAnSidePanel.setBackground(new java.awt.Color(74, 98, 138));
        duAnBtn.add(duAnSidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        categoryPanel.add(duAnBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 240, 50));

        ttcnBtn.setBackground(new java.awt.Color(205, 225, 238));
        ttcnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ttcnBtnMouseClicked(evt);
            }
        });
        ttcnBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ttcnTxt.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        ttcnTxt.setForeground(new java.awt.Color(74, 98, 138));
        ttcnTxt.setText("Thông tin Cá nhân");
        ttcnBtn.add(ttcnTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 180, 50));

        ttcnSidePanel.setBackground(new java.awt.Color(74, 98, 138));
        ttcnBtn.add(ttcnSidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        categoryPanel.add(ttcnBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 240, 50));

        nhanVienBtn.setBackground(new java.awt.Color(74, 98, 138));
        nhanVienBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienBtnMouseClicked(evt);
            }
        });
        nhanVienBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nhanVienTxt.setBackground(new java.awt.Color(205, 225, 238));
        nhanVienTxt.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        nhanVienTxt.setForeground(new java.awt.Color(255, 255, 255));
        nhanVienTxt.setText("Nhân Viên");
        nhanVienBtn.add(nhanVienTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 5, 150, 40));

        nhanVienSidePanel.setBackground(new java.awt.Color(255, 255, 255));
        nhanVienBtn.add(nhanVienSidePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 50));

        categoryPanel.add(nhanVienBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 240, 50));

        jPanel3.add(categoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, 240, 650));

        panelNhanVien.setBackground(new java.awt.Color(228, 238, 244));
        panelNhanVien.setMinimumSize(new java.awt.Dimension(1200, 690));
        panelNhanVien.setPreferredSize(new java.awt.Dimension(1200, 690));
        panelNhanVien.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nhanVienTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân viên", "Họ và Tên", "Giới tính", "Ngày sinh", "Số điện thoại ", "Email", "Lương cơ bản", "Phụ cấp", "Ngày bắt đầu làm việc"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        nhanVienTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienTableMouseClicked(evt);
            }
        });
        nhanVienScrollTable.setViewportView(nhanVienTable);

        panelNhanVien.add(nhanVienScrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 900, 360));

        nhanVienSearchBar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nhanVienSearchBarActionPerformed(evt);
            }
        });
        panelNhanVien.add(nhanVienSearchBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 60, 240, -1));

        nhanVienSearchBtn.setText("Search");
        panelNhanVien.add(nhanVienSearchBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 60, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Lương Cơ bản");
        panelNhanVien.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 510, -1, 30));

        luongCBField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                luongCBFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(luongCBField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 510, 180, 30));

        emailField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 470, 180, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Email");
        panelNhanVien.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, -1, 30));

        phuCapField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phuCapFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(phuCapField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 550, 180, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Phụ cấp");
        panelNhanVien.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, -1, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Ngày Bắt đầu làm việc");
        panelNhanVien.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 590, -1, 30));

        ngayBatDauField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngayBatDauFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(ngayBatDauField, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 590, 180, 30));

        maNVField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maNVFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(maNVField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, 180, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Mã Nhân viên");
        panelNhanVien.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, -1, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Họ và Tên");
        panelNhanVien.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 510, -1, 30));

        hoTenField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hoTenFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(hoTenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 180, 30));

        gioiTinhField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gioiTinhFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(gioiTinhField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 550, 180, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Giới tính");
        panelNhanVien.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 550, -1, 30));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Ngày sinh");
        panelNhanVien.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 590, 90, 30));

        ngaySinhField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaySinhFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(ngaySinhField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 590, 180, 30));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Số điện thoại");
        panelNhanVien.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 630, -1, 30));

        sdtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sdtFieldActionPerformed(evt);
            }
        });
        panelNhanVien.add(sdtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 630, 180, 30));

        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelNhanVien.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 650, -1, -1));

        jButton3.setText("ADD");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelNhanVien.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 650, -1, -1));

        jButton2.setText("DELETE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelNhanVien.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 650, -1, -1));

        jTabbedPane1.addTab("tab1", panelNhanVien);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab2", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jTabbedPane1.addTab("tab3", jPanel2);

        jPanel3.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, -30, 960, 720));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-240, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
        // TODO add your handling code here:
        int tmp = JOptionPane.showConfirmDialog(this,"Confirm Logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if ( tmp == 0 ) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutBtnMouseClicked

    private void logoutBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutBtnMouseEntered

    private void duAnBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_duAnBtnMouseClicked
        duAnBtn.setBackground(new Color(74, 98, 138));
        duAnTxt.setForeground(new Color(255, 255, 255));
        duAnSidePanel.setBackground(new Color(255, 255, 255));

        nhanVienBtn.setBackground(new Color(205,225,238));
        nhanVienTxt.setForeground(new Color(74,98,138));
        nhanVienSidePanel.setBackground(new Color(74,98,138));

        ttcnBtn.setBackground(new Color(205,225,238));
        ttcnTxt.setForeground(new Color(74,98,138));
        ttcnSidePanel.setBackground(new Color(74,98,138));
        
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_duAnBtnMouseClicked

    private void ttcnBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttcnBtnMouseClicked
        ttcnBtn.setBackground(new Color(74, 98, 138));
        ttcnTxt.setForeground(new Color(255, 255, 255));
        ttcnSidePanel.setBackground(new Color(255, 255, 255));

        nhanVienBtn.setBackground(new Color(205,225,238));
        nhanVienTxt.setForeground(new Color(74,98,138));
        nhanVienSidePanel.setBackground(new Color(74,98,138));

        duAnBtn.setBackground(new Color(205,225,238));
        duAnTxt.setForeground(new Color(74,98,138));
        duAnSidePanel.setBackground(new Color(74,98,138));

        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_ttcnBtnMouseClicked

    private void nhanVienBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienBtnMouseClicked
        nhanVienBtn.setBackground(new Color(74, 98, 138));
        nhanVienTxt.setForeground(new Color(255, 255, 255));
        nhanVienSidePanel.setBackground(new Color(255, 255, 255));

        ttcnBtn.setBackground(new Color(205,225,238));
        ttcnTxt.setForeground(new Color(74,98,138));
        ttcnSidePanel.setBackground(new Color(74,98,138));

        duAnBtn.setBackground(new Color(205,225,238));
        duAnTxt.setForeground(new Color(74,98,138));
        duAnSidePanel.setBackground(new Color(74,98,138));

        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_nhanVienBtnMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        if (maNVField.getText().equals("") ||
            hoTenField.getText().equals("") ||
            gioiTinhField.getText().equals("") ||
            ngaySinhField.getText().equals("") ||
            sdtField.getText().equals("") ||
            emailField.getText().equals("") ||
            luongCBField.getText().equals("") ||
            phuCapField.getText().equals("") ||
            ngayBatDauField.getText() .equals("") 
        ) {
            JOptionPane.showMessageDialog(this, "Please enter all data fields !"); 
        }
        else {
            String[] data = {   maNVField.getText(),
                                hoTenField.getText(),
                                gioiTinhField.getText(),
                                ngaySinhField.getText(),
                                sdtField.getText(),
                                emailField.getText(),
                                luongCBField.getText(),
                                phuCapField.getText(),
                                ngayBatDauField.getText()
                            };
            
            DefaultTableModel tblModel = (DefaultTableModel)nhanVienTable.getModel();
            tblModel.addRow(data);
            JOptionPane.showMessageDialog(this, "Add data successfully !"); 
            // clear text field
            maNVField.setText("");
            hoTenField.setText("");
            gioiTinhField.setText("");
            ngaySinhField.setText("");
            sdtField.setText("");
            emailField.setText("");
            luongCBField.setText("");
            phuCapField.setText("");
            ngayBatDauField.setText("");
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tblModel = (DefaultTableModel)nhanVienTable.getModel();
        
        if ( nhanVienTable.getSelectedRowCount() == 1 ) {
            // if single row is selected -> update
            
            String code = maNVField.getText();
            String name = hoTenField.getText();
            String gender = gioiTinhField.getText();
            String dob = ngaySinhField.getText();
            String sdt = sdtField.getText();
            String email = emailField.getText();
            String luongCB = luongCBField.getText();
            String phuCap = phuCapField.getText();
            String ngayBD = ngayBatDauField.getText();
            
            if (maNVField.getText().equals("") ||
                hoTenField.getText().equals("") ||
                gioiTinhField.getText().equals("") ||
                ngaySinhField.getText().equals("") ||
                sdtField.getText().equals("") ||
                emailField.getText().equals("") ||
                luongCBField.getText().equals("") ||
                phuCapField.getText().equals("") ||
                ngayBatDauField.getText() .equals("") 
            ) {
                JOptionPane.showMessageDialog(this, "Please enter all data fields !"); 
            }
            else {
                tblModel.setValueAt(code, nhanVienTable.getSelectedRow(), 0);
                tblModel.setValueAt(name, nhanVienTable.getSelectedRow(), 1);
                tblModel.setValueAt(gender, nhanVienTable.getSelectedRow(), 2);
                tblModel.setValueAt(dob, nhanVienTable.getSelectedRow(), 3);
                tblModel.setValueAt(sdt, nhanVienTable.getSelectedRow(), 4);
                tblModel.setValueAt(email, nhanVienTable.getSelectedRow(), 5);
                tblModel.setValueAt(luongCB, nhanVienTable.getSelectedRow(), 6);
                tblModel.setValueAt(phuCap, nhanVienTable.getSelectedRow(), 7);
                tblModel.setValueAt(ngayBD, nhanVienTable.getSelectedRow(), 8);
            
                JOptionPane.showMessageDialog(this, "Update Successfully !");   
            } 
        }
        else {
            if ( nhanVienTable.getRowCount() == 0 ) {
                //table is empty
                JOptionPane.showMessageDialog(this, "Table is empty !"); 
            }
            else {
                // if row is not selected or multiple row is selected
                JOptionPane.showMessageDialog(this, "Please select a single row for update !");    
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void sdtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sdtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sdtFieldActionPerformed

    private void ngaySinhFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaySinhFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngaySinhFieldActionPerformed

    private void gioiTinhFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gioiTinhFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gioiTinhFieldActionPerformed

    private void hoTenFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hoTenFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hoTenFieldActionPerformed

    private void maNVFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maNVFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maNVFieldActionPerformed

    private void ngayBatDauFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngayBatDauFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ngayBatDauFieldActionPerformed

    private void phuCapFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phuCapFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phuCapFieldActionPerformed

    private void emailFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailFieldActionPerformed

    private void luongCBFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_luongCBFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_luongCBFieldActionPerformed

    private void nhanVienSearchBarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nhanVienSearchBarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nhanVienSearchBarActionPerformed

    private void nhanVienTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienTableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tblModel = (DefaultTableModel)nhanVienTable.getModel();
        
        String tblMaNV = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 0).toString();
        String tblHoTen = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 1).toString();
        String tblGioiTinh = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 2).toString();
        String tblNgaySinh = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 3).toString();
        String tblSDT = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 4).toString();
        String tblEmail = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 5).toString();
        String tblLuongCB = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 6).toString();
        String tblPhuCap = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 7).toString();
        String tblNgayBatDau = tblModel.getValueAt(nhanVienTable.getSelectedRow(), 8).toString();
        
        // set data to text field
        maNVField.setText(tblMaNV);
        hoTenField.setText(tblHoTen);
        gioiTinhField.setText(tblGioiTinh);
        ngaySinhField.setText(tblNgaySinh);
        sdtField.setText(tblSDT);
        emailField.setText(tblEmail);
        luongCBField.setText(tblLuongCB);
        phuCapField.setText(tblPhuCap);
        ngayBatDauField.setText(tblNgayBatDau);
        
    }//GEN-LAST:event_nhanVienTableMouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

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
            java.util.logging.Logger.getLogger(TruongPhongFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TruongPhongFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TruongPhongFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TruongPhongFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TruongPhongFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel categoryPanel;
    private javax.swing.JLabel clc02Txt;
    private javax.swing.JPanel duAnBtn;
    private javax.swing.JPanel duAnSidePanel;
    private javax.swing.JLabel duAnTxt;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emsTxt;
    private javax.swing.JTextField gioiTinhField;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JTextField hoTenField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel logoutBtn;
    private javax.swing.JTextField luongCBField;
    private javax.swing.JTextField maNVField;
    private javax.swing.JTextField ngayBatDauField;
    private javax.swing.JTextField ngaySinhField;
    private javax.swing.JPanel nhanVienBtn;
    private javax.swing.JScrollPane nhanVienScrollTable;
    private javax.swing.JTextField nhanVienSearchBar;
    private javax.swing.JButton nhanVienSearchBtn;
    private javax.swing.JPanel nhanVienSidePanel;
    private javax.swing.JTable nhanVienTable;
    private javax.swing.JLabel nhanVienTxt;
    private javax.swing.JPanel panelNhanVien;
    private javax.swing.JTextField phuCapField;
    private javax.swing.JTextField sdtField;
    private javax.swing.JPanel ttcnBtn;
    private javax.swing.JPanel ttcnSidePanel;
    private javax.swing.JLabel ttcnTxt;
    // End of variables declaration//GEN-END:variables
}
