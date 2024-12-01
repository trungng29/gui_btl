package gui;

import Class.NhanVien;
import Class.DSLuong;
import Class.DSAccount;
import Class.DSDuAn;
import Class.DSNhanVien;
import Class.DSPhongBan;
import Class.DuAn;
import Class.Luong;
import Class.PhongBan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import static java.nio.file.Files.list;
import java.time.LocalDate;
import java.time.Period;
import static java.util.Collections.list;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class AdminFrame1 extends javax.swing.JFrame {
    private NhanVien nv;
    private DSNhanVien dsNV;
    private DSLuong dsL;
    private DSAccount dsA;
    private DSPhongBan dsPB;
    private DSDuAn dsDA;
    private JPanel glassPane;
    /**
     * Creates new form ChuyenVienFrame1
     */
    public AdminFrame1(NhanVien nv, DSNhanVien dsNV, DSLuong dsL, DSAccount dsA) {
        this.nv = nv;
        this.dsNV = dsNV;
        this.dsL = dsL;
        this.dsA = dsA;
        this.dsPB = new DSPhongBan();
        this.dsDA = new DSDuAn();
        
        //--Doc File PhongBan vào dsPB
        //Setup data từng phòng ban
        try {
            //Đặt lại biến static cnt để tạo maPB từ đầu
//            PhongBan tmp = new PhongBan();
//            tmp.setupCnt();
            
            Scanner in = new Scanner(new File("PB.txt"));
            while(in.hasNextLine()){
                String[] line = in.nextLine().trim().split("\\|");
                PhongBan pb = new PhongBan(line[0], line[1]);
                for(int i=2;i<line.length;i++){
                    pb.addNhanVien(dsNV.searchNhanVien(line[i]));
                }
                dsPB.addPhongBan(pb);
            }
        } catch (FileNotFoundException e) {
        }
        
        
        //--Doc File DuAn vao dsDA
        try {
            Scanner in = new Scanner(new File("DA.txt"));
            while(in.hasNextLine()){
                String[] line = in.nextLine().trim().split("\\|");
                String maDA = line[0];
                String tenDA = line[1];
                String ngayThucHien = line[2];
                DuAn tmp = new DuAn(maDA, tenDA, ngayThucHien);
                for(int i=3;i<line.length;i++){
                    tmp.addNhanVien(dsNV.searchNhanVien(line[i]));
                }
                dsDA.addDuAn(tmp);
            }
        } catch (FileNotFoundException e) {
        }
        
        initComponents();
        setData();
        setDAData();
        addTableSelectionListener();
//        loadEmployeeDataFromFile();
        setDataLuong();
        Footer.setVisible(false);
    }
    
    private void setDAData(){
        //setup AccName
        AccName.setText(nv.getTenNV());
        //Setup DSDuAn
        DefaultTableModel modelDSDA = (DefaultTableModel) nhanVienTable1.getModel();
        DefaultTableModel modelDSNVDA = (DefaultTableModel)nhanVienTGDATable.getModel();
        int cnt1 = 1;
        int cnt2 = 1;
        for(DuAn x: dsDA.getDSDuAn()){
            modelDSDA.addRow(new Object[]{cnt1++, x.getMaDA(), x.getTenDA(), x.getNgayThucHien()});
        }
        jLabelDate = new javax.swing.JLabel();
        themSuaTTDAPanel.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 200, 30));
        ngayCBB.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            ngayCBB.addItem(String.format("%02d", i));
        }
        thangCBB.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            thangCBB.addItem(String.format("%02d", i));
        }
        namCBB.removeAllItems();
        for (int i = 1900; i <= 2100; i++) {
            namCBB.addItem(String.format("%02d", i));
        }
        ngayCBB.setSelectedIndex(0);
        thangCBB.setSelectedIndex(0);
        namCBB.setSelectedIndex(122);
    }
    
    private void SetupNonNVDATable(){
        DefaultTableModel modelNonNVDA = (DefaultTableModel)nhanVienNonDuAnTable.getModel();
        modelNonNVDA.setRowCount(0);
        for(NhanVien x : dsNV.getDSNhanVien()){
            int ok = 0;
            for (DuAn y: dsDA.getDSDuAn()) {
                if (y.getDSNhanVien().contains(x)) {
                    ok = 1;
                    break;
                }
            }
            if(ok==0) modelNonNVDA.addRow(x.getData());
        }
    }
    
    private void ResetTableNVDA(){
        DefaultTableModel modelNVPB = (DefaultTableModel)nhanVienTGDATable.getModel();
        modelNVPB.setRowCount(0);

        DuAn da = dsDA.timDuAn(maDATxtField.getText());

        for(NhanVien x  :da.getDSNhanVien()){
            modelNVPB.addRow(x.getData());
        }
    }
    
    private void updateDSLuong(){
        DefaultTableModel model = (DefaultTableModel) LuongTable.getModel();
        model.setRowCount(0);
        for(Luong x: dsL.getDSLuong()){
            model.addRow(new Object[]{x.getNV().getMaNV(),x.getNV().getTenNV(),x.getNV().getChucVu(),x.getLuongCB(),x.getNgayCong(),x.getPhuCap(),x.getTongLuong()});
        }
        dsL.saveToFile("dsLuong.txt");
    }
    
    private void loadEmployeeDataFromFile() {
        String name = nv.getMaNV();
        for(NhanVien x : dsNV.getDSNhanVien()){
            if(name.equals(x.getMaNV())){
                maNVField.setText(x.getMaNV());
                hoTenField.setText(x.getTenNV());
                gioiTinhField.setText(x.getGioiTinh());
                ngaySinhField1.setText(x.getNgaySinh());
                sdtField.setText(x.getSoDT());
                emailField.setText(x.getEmail());
                //luongCBField.setText(data[6]);
                //phuCapField.setText(data[7]);
                ngayBatDauField.setText(x.getNgayBatDau());
                ngaySinhField.setText(x.getChucVu());
                hoTenField1.setText(x.getTenNV());
            }
            
        }
        for(Luong z : dsL.getDSLuong()){
            if(name.equals(z.getMaNV())){
                luongCBField.setText(String.valueOf(z.getLuongCB()));
                phuCapField.setText(String.valueOf(z.getPhuCap()));
            }
        }       
    }
    
    private void addTableSelectionListener() {
        DSNhanVienTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = DSNhanVienTable.getSelectedRow();
                if (selectedRow != -1) {
                    loadSelectedRowToForm(selectedRow);
                }
            }
        });
    }
    
    private void setDataLuong() {
        //cập nhật danh sách nhân viên phòng ban
        DefaultTableModel model = new DefaultTableModel();
        LuongTable.setModel(model);
        model.setColumnIdentifiers(new String[]{"Mã NV", "Tên NV", "Chức vụ", "Lương cơ bản", "Ngày công", "Phụ cấp", "Tổng lương"});
//        model.setRowCount(0);
        for (Luong x : dsL.getDSLuong()) {
            model.addRow(new Object[]{
                x.getNV().getMaNV(), x.getNV().getTenNV(), x.getNV().getChucVu(), x.getLuongCB(), x.getNgayCong(), x.getPhuCap(), x.getTongLuong()
            });
        }
    }
    
    private void ResetTablePB(){
        DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
        modelDSPB.setRowCount(0);
        int cnt = 1;
        for(PhongBan x:dsPB.getDSPhongBan()){
            modelDSPB.addRow(new Object[]{cnt++, x.getMaPhongBan(), x.getTenPhongBan(),x.getDSNhanVien().size()});
        }
    }
    
    private void ResetTableNVPB(){
        DefaultTableModel modelNVPB = (DefaultTableModel)NhanVienPBTable.getModel();
        modelNVPB.setRowCount(0);
        PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
//        int cnt = 1;
        for(NhanVien x:pb.getDSNhanVien()){
            modelNVPB.addRow(x.getData());
        }
    }
    
    private void SetupNonNVPBTable(){
        DefaultTableModel modelNonNVPB = (DefaultTableModel)NonNVPBTable.getModel();
        modelNonNVPB.setRowCount(0);
        for(NhanVien x:dsNV.getDSNhanVien()){
            int ok = 0;
            for(PhongBan y:dsPB.getDSPhongBan()){
                if(y.getDSNhanVien().contains(x)){
                    ok=1;
                    break;
                }
            }
            if(ok==0) modelNonNVPB.addRow(x.getData());
        }
    }
    
    private void ResetTableNV(){
        DefaultTableModel modelNV = (DefaultTableModel) DSNhanVienTable.getModel();
        modelNV.setRowCount(0);
        for (NhanVien x : dsNV.getDSNhanVien()) {
            modelNV.addRow(x.getData());
        }
    }
    
    
    private void setData(){
        glassPane = new JPanel();
        glassPane.setOpaque(false); // Glass pane trong suốt
        glassPane.setLayout(new GridBagLayout());

        // Chặn sự kiện chuột
        glassPane.addMouseListener(new MouseAdapter() {}); 
        glassPane.addKeyListener(new KeyAdapter() {});
        glassPane.add(ThemSuaPanel);
        setGlassPane(glassPane);
        glassPane.setVisible(false);
        ThemSuaPanel.setVisible(false);
        
        
        
        //Setup DSPhongBan, DSNonNVPB 
        ResetTablePB();
        SetupNonNVPBTable();
        
        //Set up DSNhanVien
        ResetTableNV();
        
//        //cập nhật thông tin phòng ban
//        MaPBText.setText("PB01");
//        TenPBText.setText("Nhân sự");
//        
//        //cập nhật danh sách nhân viên phòng ban
//        DefaultTableModel model = new DefaultTableModel();
//        NhanVienPBTable.setModel(model);
//        model.setColumnIdentifiers(new String [] {"Mã NV", "Tên NV", "Chức vụ", "Giới tính", "Email", "SDT", "Ngày sinh", "Ngày Bắt Đầu"});
////        model.setRowCount(0);
//        for(NhanVien x:dsNV.getDSNhanVien()){
//            model.addRow(x.getData());
//        }
        chucVuBox.removeAllItems(); // Clear existing items
        chucVuBox.addItem("Chuyen vien");
        chucVuBox.addItem("Truong phong");
        chucVuBox.addItem("Admin");
        gioiTinhBox.removeAllItems(); // Clear existing items
        gioiTinhBox.addItem("Nam");
        gioiTinhBox.addItem("Nữ");
        chucVuBox.setSelectedIndex(0); // Select the first position
        gioiTinhBox.setSelectedIndex(0);
        jLabelDate = new javax.swing.JLabel();
        NhanVien.add(jLabelDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 200, 30));
        ngaySinhNVBox.removeAllItems(); 
        for (int i = 1; i <= 31; i++) {
            ngaySinhNVBox.addItem(String.format("%02d", i)); 
        }
        thangSinhNVBox.removeAllItems(); 
        for (int i = 1; i <= 12; i++) {
            thangSinhNVBox.addItem(String.format("%02d", i));
        }
        namSinhNVBox.removeAllItems(); 
        for (int i = 1900; i <= 2100; i++) {
            namSinhNVBox.addItem(String.format("%02d", i));
        }
        ngaySinhNVBox.setSelectedIndex(0);
        thangSinhNVBox.setSelectedIndex(0);
        namSinhNVBox.setSelectedIndex(122);
        ngayBDNVBox.removeAllItems(); 
        for (int i = 1; i <= 31; i++) {
            ngayBDNVBox.addItem(String.format("%02d", i));
        }
        thangBDNVBox.removeAllItems(); 
        for (int i = 1; i <= 12; i++) {
            thangBDNVBox.addItem(String.format("%02d", i));
        }
        namBDNVBox.removeAllItems(); 
        for (int i = 1900; i <= 2100; i++) {
            namBDNVBox.addItem(String.format("%02d", i));
        }
        ngayBDNVBox.setSelectedIndex(0);
        thangBDNVBox.setSelectedIndex(0);
        namBDNVBox.setSelectedIndex(122);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        themSuaTTDAFrame = new javax.swing.JFrame();
        themSuaTTDAPanel = new javax.swing.JPanel();
        xacNhanTTDABtn = new javax.swing.JButton();
        huyTTDABtn = new javax.swing.JButton();
        ThemSuaTitle1 = new javax.swing.JLabel();
        tenDALabel = new javax.swing.JLabel();
        tenDATTDATxtField = new javax.swing.JTextField();
        ngayTHLabel = new javax.swing.JLabel();
        ngayCBB = new javax.swing.JComboBox<>();
        thangCBB = new javax.swing.JComboBox<>();
        namCBB = new javax.swing.JComboBox<>();
        danhSachNVFrame = new javax.swing.JFrame();
        danhSachNVPanel = new javax.swing.JPanel();
        danhSachNVTGDALabel = new javax.swing.JLabel();
        backNVTGDABtn = new javax.swing.JLabel();
        maDALabel = new javax.swing.JLabel();
        maDATxtField = new javax.swing.JLabel();
        tenDANVTGDALabel = new javax.swing.JLabel();
        tenDANVTGDAField = new javax.swing.JLabel();
        ngayBDField = new javax.swing.JLabel();
        ngayBDTxtField = new javax.swing.JLabel();
        nhanVienScrollTable = new javax.swing.JScrollPane();
        nhanVienTGDATable = new javax.swing.JTable();
        searchNVDAField = new javax.swing.JTextField();
        themNVDABtn = new javax.swing.JButton();
        xoaNVDABtn = new javax.swing.JButton();
        resetNVDABtn = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        danhSachNVNonDuAnFrame = new javax.swing.JFrame();
        danhSachNVNonDuAnPanel = new javax.swing.JPanel();
        danhSachNhanVienNonDuAnLabel = new javax.swing.JLabel();
        nhanVienScrollTable1 = new javax.swing.JScrollPane();
        nhanVienNonDuAnTable = new javax.swing.JTable();
        themNVNonDABtn = new javax.swing.JButton();
        searchNVNonDAField = new javax.swing.JTextField();
        backNVNonDABtn = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        AppName = new javax.swing.JLabel();
        SubAppName = new javax.swing.JLabel();
        AccName = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Sidebar = new javax.swing.JPanel();
        SidebarNhanVien = new javax.swing.JButton();
        SidebarTTCN = new javax.swing.JButton();
        SidebarDuAn = new javax.swing.JButton();
        LogoutButton = new javax.swing.JLabel();
        SidebarPhongBan = new javax.swing.JButton();
        SidebarLuong = new javax.swing.JButton();
        Footer = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        NhanVien = new javax.swing.JPanel();
        NVTitle = new javax.swing.JLabel();
        DSNVLabel2 = new javax.swing.JLabel();
        SearchNVText = new javax.swing.JTextField();
        SearchNVButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        DSNhanVienTable = new javax.swing.JTable();
        UpdateNVButton = new javax.swing.JButton();
        AddNVButton = new javax.swing.JButton();
        DeleteNVButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        chucVuBox = new javax.swing.JComboBox<>();
        gioiTinhBox = new javax.swing.JComboBox<>();
        tenNVField1 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        emailField1 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        sdtField1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        Header2 = new javax.swing.JPanel();
        AppName2 = new javax.swing.JLabel();
        SubAppName2 = new javax.swing.JLabel();
        ngaySinhNVBox = new javax.swing.JComboBox<>();
        thangSinhNVBox = new javax.swing.JComboBox<>();
        namSinhNVBox = new javax.swing.JComboBox<>();
        ngayBDNVBox = new javax.swing.JComboBox<>();
        thangBDNVBox = new javax.swing.JComboBox<>();
        namBDNVBox = new javax.swing.JComboBox<>();
        QuanLyPhongBan = new javax.swing.JTabbedPane();
        DSPhongBan = new javax.swing.JPanel();
        ThemSuaPanel = new javax.swing.JPanel();
        XacNhanButton = new javax.swing.JButton();
        HuyButton = new javax.swing.JButton();
        ThemSuaTitle = new javax.swing.JLabel();
        TenTPLabel = new javax.swing.JLabel();
        TenPBtext = new javax.swing.JTextField();
        DSPhongBanTitle = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DSPhongBanTable = new javax.swing.JTable();
        ThemButton = new javax.swing.JButton();
        XoaButton = new javax.swing.JButton();
        SuaButton = new javax.swing.JButton();
        SearchPBButton = new javax.swing.JButton();
        SearchPBText = new javax.swing.JTextField();
        DSNVLabel1 = new javax.swing.JLabel();
        ChucNangLabel = new javax.swing.JLabel();
        TTChiTietButton = new javax.swing.JButton();
        ResetButton = new javax.swing.JLabel();
        PhongBan = new javax.swing.JPanel();
        ThemNVPBPanel = new javax.swing.JPanel();
        ThemNVPBTitle1 = new javax.swing.JLabel();
        HuyNVPBButton = new javax.swing.JButton();
        XacNhanNVPBButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        NonNVPBTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        NhanVienPBTable = new javax.swing.JTable();
        SearchNVPBText = new javax.swing.JTextField();
        SearchNVPBButton = new javax.swing.JButton();
        PBTitle = new javax.swing.JLabel();
        MaPBLabel = new javax.swing.JLabel();
        MaPBText = new javax.swing.JLabel();
        DSNVLabel = new javax.swing.JLabel();
        TenPBLabel = new javax.swing.JLabel();
        TenPBText = new javax.swing.JLabel();
        ThemNVPBButton = new javax.swing.JButton();
        XoaNVPBButton = new javax.swing.JButton();
        ChucNangLabel1 = new javax.swing.JLabel();
        ResetNVPBButton = new javax.swing.JLabel();
        BackNVPBButton = new javax.swing.JLabel();
        WarningTxt = new javax.swing.JLabel();
        TTCN = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        hoTenField1 = new javax.swing.JLabel();
        ngaySinhField = new javax.swing.JLabel();
        sdtField = new javax.swing.JLabel();
        sdtLabel = new javax.swing.JLabel();
        ngaySinhField1 = new javax.swing.JLabel();
        ngaySinhLabel = new javax.swing.JLabel();
        gioiTinhField = new javax.swing.JLabel();
        gioiTinhLabel = new javax.swing.JLabel();
        hoTenField = new javax.swing.JLabel();
        hoTenLabel = new javax.swing.JLabel();
        maNVField = new javax.swing.JLabel();
        maNVLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        emailLabel = new javax.swing.JLabel();
        emailField = new javax.swing.JLabel();
        luongCBLabel = new javax.swing.JLabel();
        luongCBField = new javax.swing.JLabel();
        phuCapLabel = new javax.swing.JLabel();
        phuCapField = new javax.swing.JLabel();
        ngayBDLabel = new javax.swing.JLabel();
        ngayBatDauField = new javax.swing.JLabel();
        DSDuAn = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        nhanVienTable1 = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        themTTDABtn = new javax.swing.JButton();
        xoaTTDABtn = new javax.swing.JButton();
        suaTTDABtn = new javax.swing.JButton();
        searchTTDATxtField = new javax.swing.JTextField();
        openTTDABtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        LƯƠNG = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LuongTable = new javax.swing.JTable();
        TimLuongtxt = new javax.swing.JTextField();
        TimLuong = new javax.swing.JButton();
        LTitle1 = new javax.swing.JLabel();
        DSNVLabel3 = new javax.swing.JLabel();
        SET_ngaycong = new javax.swing.JButton();
        txtNgayCong = new javax.swing.JTextField();
        SapxepBtn = new javax.swing.JButton();
        setLuongBtn = new javax.swing.JButton();

        themSuaTTDAFrame.setPreferredSize(new Dimension(493, 260));
        themSuaTTDAFrame.pack();
        themSuaTTDAFrame.setLocationRelativeTo(null);
        themSuaTTDAFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        themSuaTTDAFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                themSuaTTDAFrameWindowClosed(evt);
            }
        });

        themSuaTTDAPanel.setBackground(new java.awt.Color(144, 210, 254));
        themSuaTTDAPanel.setMinimumSize(new java.awt.Dimension(470, 250));
        themSuaTTDAPanel.setPreferredSize(new java.awt.Dimension(470, 250));
        themSuaTTDAPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        xacNhanTTDABtn.setText("Xác nhận");
        xacNhanTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xacNhanTTDABtnActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(xacNhanTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 167, -1, -1));

        huyTTDABtn.setText("Hủy");
        huyTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                huyTTDABtnActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(huyTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(116, 167, -1, -1));

        ThemSuaTitle1.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        ThemSuaTitle1.setText("Thêm thông tin Dự án mới");
        themSuaTTDAPanel.add(ThemSuaTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, -1, -1));

        tenDALabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tenDALabel.setText("Tên Dự án:");
        themSuaTTDAPanel.add(tenDALabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, -1, 40));

        tenDATTDATxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tenDATTDATxtFieldActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(tenDATTDATxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 186, 25));

        ngayTHLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        ngayTHLabel.setText("Ngày Thực hiện:");
        themSuaTTDAPanel.add(ngayTHLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, 40));

        ngayCBB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ngayCBB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ngayCBBMouseReleased(evt);
            }
        });
        ngayCBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngayCBBActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(ngayCBB, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, 30));

        thangCBB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        thangCBB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                thangCBBMouseReleased(evt);
            }
        });
        thangCBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thangCBBActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(thangCBB, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 120, -1, 30));

        namCBB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namCBB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                namCBBMouseReleased(evt);
            }
        });
        namCBB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namCBBActionPerformed(evt);
            }
        });
        themSuaTTDAPanel.add(namCBB, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, -1, 30));

        themSuaTTDAFrame.getContentPane().add(themSuaTTDAPanel, java.awt.BorderLayout.CENTER);

        danhSachNVFrame.setPreferredSize(new Dimension(990, 650));
        danhSachNVFrame.pack();
        danhSachNVFrame.setLocationRelativeTo(null);
        danhSachNVFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        danhSachNVFrame.setResizable(false);
        danhSachNVFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                danhSachNVFrameWindowClosed(evt);
            }
        });
        danhSachNVFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        danhSachNVPanel.setBackground(new java.awt.Color(228, 238, 244));
        danhSachNVPanel.setMinimumSize(new java.awt.Dimension(980, 610));
        danhSachNVPanel.setPreferredSize(new java.awt.Dimension(980, 610));
        danhSachNVPanel.setVerifyInputWhenFocusTarget(false);
        danhSachNVPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        danhSachNVTGDALabel.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        danhSachNVTGDALabel.setText("DANH SÁCH NHÂN VIÊN THAM GIA DỰ ÁN");
        danhSachNVPanel.add(danhSachNVTGDALabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        backNVTGDABtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backNVTGDABtnMouseClicked(evt);
            }
        });
        danhSachNVPanel.add(backNVTGDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, -1, -1));

        maDALabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        maDALabel.setForeground(new java.awt.Color(74, 98, 138));
        maDALabel.setText("Mã Dự án:");
        danhSachNVPanel.add(maDALabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, -1, -1));

        maDATxtField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        maDATxtField.setText("null");
        danhSachNVPanel.add(maDATxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, -1, -1));

        tenDANVTGDALabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tenDANVTGDALabel.setForeground(new java.awt.Color(74, 98, 138));
        tenDANVTGDALabel.setText("Tên Dự án:");
        danhSachNVPanel.add(tenDANVTGDALabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, -1, -1));

        tenDANVTGDAField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tenDANVTGDAField.setText("null");
        danhSachNVPanel.add(tenDANVTGDAField, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, -1));

        ngayBDField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ngayBDField.setForeground(new java.awt.Color(74, 98, 138));
        ngayBDField.setText("Ngày Bắt đầu:");
        danhSachNVPanel.add(ngayBDField, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, -1, -1));

        ngayBDTxtField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ngayBDTxtField.setText("null");
        danhSachNVPanel.add(ngayBDTxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, -1, -1));

        nhanVienTGDATable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân viên", "Họ và Tên", "Chức vụ", "Giới tính", "Email ", "Số điện thoại", "Ngày sinh", "Ngày bắt đầu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        nhanVienTGDATable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienTGDATableMouseClicked(evt);
            }
        });
        nhanVienScrollTable.setViewportView(nhanVienTGDATable);

        danhSachNVPanel.add(nhanVienScrollTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 920, 330));

        searchNVDAField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNVDAFieldActionPerformed(evt);
            }
        });
        searchNVDAField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNVDAFieldKeyReleased(evt);
            }
        });
        danhSachNVPanel.add(searchNVDAField, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 220, 230, -1));

        themNVDABtn.setText("Thêm");
        themNVDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNVDABtnActionPerformed(evt);
            }
        });
        danhSachNVPanel.add(themNVDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, -1, -1));

        xoaNVDABtn.setText("Xóa");
        xoaNVDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaNVDABtnActionPerformed(evt);
            }
        });
        danhSachNVPanel.add(xoaNVDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, -1, -1));

        resetNVDABtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Reset.png"))); // NOI18N
        resetNVDABtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resetNVDABtnMouseClicked(evt);
            }
        });
        danhSachNVPanel.add(resetNVDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(74, 98, 138));
        jLabel10.setText("Thanh tìm kiếm:");
        danhSachNVPanel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 190, -1, -1));

        danhSachNVFrame.getContentPane().add(danhSachNVPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 610));

        danhSachNVFrame.getAccessibleContext().setAccessibleParent(danhSachNVNonDuAnFrame);

        danhSachNVNonDuAnFrame.setPreferredSize(new Dimension(990, 650));
        danhSachNVNonDuAnFrame.pack();
        danhSachNVNonDuAnFrame.setLocationRelativeTo(null);
        danhSachNVNonDuAnFrame.setResizable(false);
        danhSachNVNonDuAnFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                danhSachNVNonDuAnFrameWindowClosed(evt);
            }
        });
        danhSachNVNonDuAnFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        danhSachNVNonDuAnPanel.setBackground(new java.awt.Color(228, 238, 244));
        danhSachNVNonDuAnPanel.setMinimumSize(new java.awt.Dimension(980, 610));
        danhSachNVNonDuAnPanel.setPreferredSize(new java.awt.Dimension(980, 610));
        danhSachNVNonDuAnPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        danhSachNhanVienNonDuAnLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        danhSachNhanVienNonDuAnLabel.setText("Danh sách Nhân viên chưa thuộc Dự án nào");
        danhSachNVNonDuAnPanel.add(danhSachNhanVienNonDuAnLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        nhanVienNonDuAnTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân viên", "Họ và Tên", "Chức vụ", "Giới tính", "Email", "Số điện thoại ", "Ngày sinh", "Ngày Bắt đầu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        nhanVienNonDuAnTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                nhanVienNonDuAnTableMouseClicked(evt);
            }
        });
        nhanVienScrollTable1.setViewportView(nhanVienNonDuAnTable);

        danhSachNVNonDuAnPanel.add(nhanVienScrollTable1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 920, 460));

        themNVNonDABtn.setText("Thêm");
        themNVNonDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themNVNonDABtnActionPerformed(evt);
            }
        });
        danhSachNVNonDuAnPanel.add(themNVNonDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 80, -1, -1));

        searchNVNonDAField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNVNonDAFieldActionPerformed(evt);
            }
        });
        searchNVNonDAField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchNVNonDAFieldKeyReleased(evt);
            }
        });
        danhSachNVNonDuAnPanel.add(searchNVNonDAField, new org.netbeans.lib.awtextra.AbsoluteConstraints(591, 80, 270, -1));

        backNVNonDABtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backNVNonDABtnMouseClicked(evt);
            }
        });
        danhSachNVNonDuAnPanel.add(backNVNonDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(74, 98, 138));
        jLabel11.setText("Thanh tìm kiếm:");
        danhSachNVNonDuAnPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, -1, -1));

        danhSachNVNonDuAnFrame.getContentPane().add(danhSachNVNonDuAnPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        danhSachNVNonDuAnFrame.getAccessibleContext().setAccessibleParent(danhSachNVFrame);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("EMS");
        setLocationByPlatform(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(205, 225, 238));
        jPanel2.setMaximumSize(new java.awt.Dimension(230, 632));
        jPanel2.setMinimumSize(new java.awt.Dimension(230, 632));
        jPanel2.setPreferredSize(new java.awt.Dimension(230, 632));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Header.setBackground(new java.awt.Color(74, 98, 138));
        Header.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AppName.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        AppName.setForeground(new java.awt.Color(255, 255, 255));
        AppName.setText("EMS");
        Header.add(AppName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        SubAppName.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SubAppName.setForeground(new java.awt.Color(205, 225, 238));
        SubAppName.setText("CLC-02");
        Header.add(SubAppName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        AccName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        AccName.setForeground(new java.awt.Color(245, 245, 245));
        AccName.setText("Do Trung Quan");
        Header.add(AccName, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 10, 170, 30));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        Header.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 30, 30));

        jPanel2.add(Header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 50));

        Sidebar.setBackground(new java.awt.Color(144, 176, 194));
        Sidebar.setMinimumSize(new java.awt.Dimension(240, 240));
        Sidebar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        SidebarNhanVien.setBackground(new java.awt.Color(144, 176, 194));
        SidebarNhanVien.setText("NHÂN VIÊN");
        SidebarNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidebarNhanVienActionPerformed(evt);
            }
        });
        Sidebar.add(SidebarNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 240, 70));

        SidebarTTCN.setBackground(new java.awt.Color(144, 176, 194));
        SidebarTTCN.setText("THÔNG TIN CÁ NHÂN");
        SidebarTTCN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SidebarTTCNMouseClicked(evt);
            }
        });
        SidebarTTCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidebarTTCNActionPerformed(evt);
            }
        });
        Sidebar.add(SidebarTTCN, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 240, 70));

        SidebarDuAn.setBackground(new java.awt.Color(144, 176, 194));
        SidebarDuAn.setText("DỰ ÁN");
        SidebarDuAn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidebarDuAnActionPerformed(evt);
            }
        });
        Sidebar.add(SidebarDuAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 240, 70));

        LogoutButton.setBackground(new java.awt.Color(0, 0, 0));
        LogoutButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        LogoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Logout.png"))); // NOI18N
        LogoutButton.setText("LOGOUT");
        LogoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogoutButtonMouseEntered(evt);
            }
        });
        Sidebar.add(LogoutButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, -1, -1));

        SidebarPhongBan.setBackground(new java.awt.Color(144, 176, 194));
        SidebarPhongBan.setText("PHÒNG BAN");
        SidebarPhongBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidebarPhongBanActionPerformed(evt);
            }
        });
        Sidebar.add(SidebarPhongBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 240, 70));

        SidebarLuong.setBackground(new java.awt.Color(144, 176, 194));
        SidebarLuong.setText("LƯƠNG");
        SidebarLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SidebarLuongSidebarLUONGActionPerformed(evt);
            }
        });
        Sidebar.add(SidebarLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 240, 80));

        jPanel2.add(Sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 42, 240, 700));

        Footer.setBackground(new java.awt.Color(204, 204, 255));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(153, 153, 153));
        jLabel36.setText("EMPLOYEES MANAGEMENT SYSTEM");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(153, 153, 153));
        jLabel37.setText("Copyright - nhóm 8 OOP-BTL E22CQCN02-B PTIT 2024");

        javax.swing.GroupLayout FooterLayout = new javax.swing.GroupLayout(Footer);
        Footer.setLayout(FooterLayout);
        FooterLayout.setHorizontalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FooterLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel37))
                .addGap(293, 293, 293))
        );
        FooterLayout.setVerticalGroup(
            FooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FooterLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel2.add(Footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1200, 100));

        NhanVien.setBackground(new java.awt.Color(228, 238, 244));

        NVTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        NVTitle.setText("THÔNG TIN NHÂN VIÊN");

        DSNVLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        DSNVLabel2.setText("Danh sách nhân viên:");

        SearchNVText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchNVTextActionPerformed(evt);
            }
        });

        SearchNVButton.setText("Tìm");
        SearchNVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchNVButtonActionPerformed(evt);
            }
        });

        DSNhanVienTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Chức vụ", "Giới tính", "Email", "SDT", "Ngày sinh", "Ngày Bắt Đầu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DSNhanVienTable.setRowHeight(25);
        jScrollPane5.setViewportView(DSNhanVienTable);
        DSNhanVienTable.setDefaultEditor(Object.class, null);

        UpdateNVButton.setText("Sửa");
        UpdateNVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateNVButtonActionPerformed(evt);
            }
        });

        AddNVButton.setText("Thêm");
        AddNVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNVButtonActionPerformed(evt);
            }
        });

        DeleteNVButton.setText("Xóa");
        DeleteNVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteNVButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Chức vụ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Tên NV");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setText("Giới tính");

        chucVuBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        chucVuBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chucVuBoxActionPerformed(evt);
            }
        });

        gioiTinhBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gioiTinhBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gioiTinhBoxActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("Email");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("SDT");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Ngày sinh");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel30.setText("Ngày Bắt Đầu");

        Header2.setBackground(new java.awt.Color(74, 98, 138));
        Header2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Header2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AppName2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        AppName2.setForeground(new java.awt.Color(255, 255, 255));
        AppName2.setText("EMS");
        Header2.add(AppName2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        SubAppName2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        SubAppName2.setForeground(new java.awt.Color(205, 225, 238));
        SubAppName2.setText("CLC-02");
        Header2.add(SubAppName2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        ngaySinhNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ngaySinhNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngaySinhNVBoxActionPerformed(evt);
            }
        });

        thangSinhNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        thangSinhNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thangSinhNVBoxActionPerformed(evt);
            }
        });

        namSinhNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namSinhNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namSinhNVBoxActionPerformed(evt);
            }
        });

        ngayBDNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ngayBDNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ngayBDNVBoxActionPerformed(evt);
            }
        });

        thangBDNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        thangBDNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thangBDNVBoxActionPerformed(evt);
            }
        });

        namBDNVBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        namBDNVBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namBDNVBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NhanVienLayout = new javax.swing.GroupLayout(NhanVien);
        NhanVien.setLayout(NhanVienLayout);
        NhanVienLayout.setHorizontalGroup(
            NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, NhanVienLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Header2, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(NhanVienLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NVTitle)
                    .addGroup(NhanVienLayout.createSequentialGroup()
                        .addComponent(AddNVButton)
                        .addGap(56, 56, 56)
                        .addComponent(UpdateNVButton)
                        .addGap(61, 61, 61)
                        .addComponent(DeleteNVButton)
                        .addGap(173, 173, 173)
                        .addComponent(SearchNVText, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SearchNVButton))
                    .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, NhanVienLayout.createSequentialGroup()
                            .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DSNVLabel2)
                                .addGroup(NhanVienLayout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(chucVuBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(NhanVienLayout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tenNVField1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(NhanVienLayout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(gioiTinhBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(48, 48, 48)
                            .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(NhanVienLayout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addGap(18, 18, 18)
                                    .addComponent(ngaySinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(thangSinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(namSinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(323, 323, 323))
                                .addGroup(NhanVienLayout.createSequentialGroup()
                                    .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(51, 51, 51)
                                    .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(sdtField1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(NhanVienLayout.createSequentialGroup()
                                            .addComponent(emailField1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel30)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(ngayBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(thangBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(namBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        NhanVienLayout.setVerticalGroup(
            NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NhanVienLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(Header2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NVTitle)
                .addGap(25, 25, 25)
                .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SearchNVText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UpdateNVButton)
                    .addComponent(AddNVButton)
                    .addComponent(DeleteNVButton)
                    .addComponent(SearchNVButton))
                .addGap(18, 18, 18)
                .addComponent(DSNVLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(emailField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel30)
                        .addComponent(ngayBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(thangBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(namBDNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(tenNVField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel25)))
                .addGap(15, 15, 15)
                .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(chucVuBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(sdtField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(NhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(gioiTinhBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(ngaySinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thangSinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(namSinhNVBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("tab5", NhanVien);

        DSPhongBan.setBackground(new java.awt.Color(228, 238, 244));
        DSPhongBan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ThemSuaPanel.setBackground(new java.awt.Color(144, 210, 254));
        ThemSuaPanel.setLayout(new java.awt.GridBagLayout());

        XacNhanButton.setText("Xác nhận");
        XacNhanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XacNhanButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(48, 91, 20, 0);
        ThemSuaPanel.add(XacNhanButton, gridBagConstraints);

        HuyButton.setText("Hủy");
        HuyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HuyButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(48, 112, 20, 0);
        ThemSuaPanel.add(HuyButton, gridBagConstraints);

        ThemSuaTitle.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        ThemSuaTitle.setText("Thêm thông tin phòng ban mới");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 67, 0, 71);
        ThemSuaPanel.add(ThemSuaTitle, gridBagConstraints);

        TenTPLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        TenTPLabel.setText("Tên phòng ban:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(47, 67, 0, 0);
        ThemSuaPanel.add(TenTPLabel, gridBagConstraints);

        TenPBtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TenPBtextActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 122;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(50, 18, 0, 71);
        ThemSuaPanel.add(TenPBtext, gridBagConstraints);

        DSPhongBan.add(ThemSuaPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 280, 470, 210));

        DSPhongBanTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        DSPhongBanTitle.setText("QUẢN LÝ PHÒNG BAN");
        DSPhongBan.add(DSPhongBanTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        DSPhongBanTable.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        DSPhongBanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MaPB", "Tên PB", "Tổng số nhân viên"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        DSPhongBanTable.setRowHeight(50);
        jScrollPane2.setViewportView(DSPhongBanTable);

        DSPhongBan.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 900, 360));

        ThemButton.setText("Thêm");
        ThemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemButtonActionPerformed(evt);
            }
        });
        DSPhongBan.add(ThemButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, -1, -1));

        XoaButton.setText("Xóa");
        XoaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaButtonActionPerformed(evt);
            }
        });
        DSPhongBan.add(XoaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, -1, -1));

        SuaButton.setText("Sửa");
        SuaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SuaButtonActionPerformed(evt);
            }
        });
        DSPhongBan.add(SuaButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 130, -1, -1));

        SearchPBButton.setText("Tìm");
        SearchPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPBButtonActionPerformed(evt);
            }
        });
        DSPhongBan.add(SearchPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 170, -1, 20));

        SearchPBText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchPBTextActionPerformed(evt);
            }
        });
        DSPhongBan.add(SearchPBText, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 240, -1));

        DSNVLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        DSNVLabel1.setText("Bấm nút bên để truy cập riêng từng phòng ban:");
        DSPhongBan.add(DSNVLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        ChucNangLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ChucNangLabel.setText("Chức năng:");
        DSPhongBan.add(ChucNangLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, -1, -1));

        TTChiTietButton.setText("Thông tin chi tiết");
        TTChiTietButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TTChiTietButtonActionPerformed(evt);
            }
        });
        DSPhongBan.add(TTChiTietButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        ResetButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Reset.png"))); // NOI18N
        ResetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResetButtonMouseClicked(evt);
            }
        });
        DSPhongBan.add(ResetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 70, 30, 30));

        QuanLyPhongBan.addTab("tab1.1", DSPhongBan);

        PhongBan.setBackground(new java.awt.Color(228, 238, 244));
        PhongBan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ThemNVPBPanel.setBackground(new java.awt.Color(144, 210, 254));
        ThemNVPBPanel.setLayout(new java.awt.GridBagLayout());

        ThemNVPBTitle1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ThemNVPBTitle1.setText("Thêm nhân viên mới vào phòng ban");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(21, 117, 0, 0);
        ThemNVPBPanel.add(ThemNVPBTitle1, gridBagConstraints);

        HuyNVPBButton.setText("Hủy");
        HuyNVPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HuyNVPBButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 83, 24, 0);
        ThemNVPBPanel.add(HuyNVPBButton, gridBagConstraints);

        XacNhanNVPBButton.setText("Xác nhận");
        XacNhanNVPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XacNhanNVPBButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(30, 77, 24, 0);
        ThemNVPBPanel.add(XacNhanNVPBButton, gridBagConstraints);

        NonNVPBTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Chức vụ", "Giới tính", "Email", "SDT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        NonNVPBTable.setRowHeight(25);
        jScrollPane3.setViewportView(NonNVPBTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 520;
        gridBagConstraints.ipady = 285;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 53, 0, 51);
        ThemNVPBPanel.add(jScrollPane3, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel1.setText("Danh sách nhân viên chưa thuộc phòng ban nào:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 53, 0, 0);
        ThemNVPBPanel.add(jLabel1, gridBagConstraints);

        PhongBan.add(ThemNVPBPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 640, 480));

        NhanVienPBTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Chức vụ", "Giới tính", "Email", "SDT", "Ngày sinh", "Ngày Bắt Đầu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        NhanVienPBTable.setRowHeight(25);
        jScrollPane1.setViewportView(NhanVienPBTable);
        if (NhanVienPBTable.getColumnModel().getColumnCount() > 0) {
            NhanVienPBTable.getColumnModel().getColumn(2).setHeaderValue("Chức vụ");
            NhanVienPBTable.getColumnModel().getColumn(3).setHeaderValue("Giới tính");
            NhanVienPBTable.getColumnModel().getColumn(4).setHeaderValue("Email");
            NhanVienPBTable.getColumnModel().getColumn(5).setHeaderValue("SDT");
            NhanVienPBTable.getColumnModel().getColumn(6).setHeaderValue("Ngày sinh");
            NhanVienPBTable.getColumnModel().getColumn(7).setHeaderValue("Ngày Bắt Đầu");
        }

        PhongBan.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 900, 360));

        SearchNVPBText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchNVPBTextActionPerformed(evt);
            }
        });
        PhongBan.add(SearchNVPBText, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 170, 240, -1));

        SearchNVPBButton.setText("Tìm");
        SearchNVPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchNVPBButtonActionPerformed(evt);
            }
        });
        PhongBan.add(SearchNVPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 170, -1, 20));

        PBTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        PBTitle.setText("THÔNG TIN PHÒNG BAN");
        PhongBan.add(PBTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        MaPBLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        MaPBLabel.setText("Mã phòng ban:");
        PhongBan.add(MaPBLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, -1));

        MaPBText.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        MaPBText.setText("null");
        PhongBan.add(MaPBText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        DSNVLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        DSNVLabel.setText("Danh sách nhân viên thuộc phòng ban:");
        PhongBan.add(DSNVLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        TenPBLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        TenPBLabel.setText("Tên phòng ban:");
        PhongBan.add(TenPBLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        TenPBText.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        TenPBText.setText("null");
        PhongBan.add(TenPBText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, -1));

        ThemNVPBButton.setText("Thêm");
        ThemNVPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemNVPBButtonActionPerformed(evt);
            }
        });
        PhongBan.add(ThemNVPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, -1, -1));

        XoaNVPBButton.setText("Xóa");
        XoaNVPBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaNVPBButtonActionPerformed(evt);
            }
        });
        PhongBan.add(XoaNVPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, -1, -1));

        ChucNangLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ChucNangLabel1.setText("Chức năng:");
        PhongBan.add(ChucNangLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 90, -1, -1));

        ResetNVPBButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Reset.png"))); // NOI18N
        ResetNVPBButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ResetNVPBButtonMouseClicked(evt);
            }
        });
        PhongBan.add(ResetNVPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, 30, 30));

        BackNVPBButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/back.png"))); // NOI18N
        BackNVPBButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackNVPBButtonMouseClicked(evt);
            }
        });
        PhongBan.add(BackNVPBButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 30, 30));

        WarningTxt.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        WarningTxt.setForeground(new java.awt.Color(204, 0, 0));
        WarningTxt.setText("Phòng ban này hiện chưa có trưởng phòng!");
        PhongBan.add(WarningTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        QuanLyPhongBan.addTab("tab1.2", PhongBan);

        jTabbedPane2.addTab("tab1", QuanLyPhongBan);

        TTCN.setBackground(new java.awt.Color(228, 238, 244));
        TTCN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Screenshot 2024-11-29 140318.png"))); // NOI18N
        jLabel35.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        jLabel35.setPreferredSize(new java.awt.Dimension(200, 200));
        TTCN.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 150, 180));

        hoTenField1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        hoTenField1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hoTenField1.setText("NULL");
        TTCN.add(hoTenField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 360, 250, 40));

        ngaySinhField.setBackground(new java.awt.Color(255, 255, 255));
        ngaySinhField.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        ngaySinhField.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ngaySinhField.setText("chucvu");
        TTCN.add(ngaySinhField, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 230, 30));

        sdtField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        sdtField.setText("null");
        TTCN.add(sdtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 540, -1, 40));

        sdtLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        sdtLabel.setText("Số điện thoại:");
        TTCN.add(sdtLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 510, -1, 40));

        ngaySinhField1.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        ngaySinhField1.setText("null");
        TTCN.add(ngaySinhField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, -1, 40));

        ngaySinhLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        ngaySinhLabel.setText("Ngày sinh:");
        TTCN.add(ngaySinhLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 430, -1, 40));

        gioiTinhField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        gioiTinhField.setText("null");
        TTCN.add(gioiTinhField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 370, -1, 40));

        gioiTinhLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        gioiTinhLabel.setText("Giới tính:");
        TTCN.add(gioiTinhLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 340, -1, 40));

        hoTenField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        hoTenField.setText("null");
        TTCN.add(hoTenField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 280, 240, 40));

        hoTenLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        hoTenLabel.setText("Họ và Tên:");
        TTCN.add(hoTenLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 250, -1, 40));

        maNVField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        maNVField.setText("null");
        TTCN.add(maNVField, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, -1, 40));

        maNVLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        maNVLabel.setText("Mã Nhân Viên:");
        TTCN.add(maNVLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, -1, 40));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel2.setText("THÔNG TIN CÁ NHÂN");
        TTCN.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, -1));

        emailLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        emailLabel.setText("Email:");
        TTCN.add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 160, -1, 40));

        emailField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        emailField.setText("null");
        TTCN.add(emailField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, -1, 40));

        luongCBLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        luongCBLabel.setText("Lương cơ bản:");
        TTCN.add(luongCBLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 250, -1, 40));

        luongCBField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        luongCBField.setText("null");
        TTCN.add(luongCBField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, -1, 40));

        phuCapLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        phuCapLabel.setText("Phụ cấp:");
        TTCN.add(phuCapLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 340, -1, -1));

        phuCapField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        phuCapField.setText("null");
        TTCN.add(phuCapField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 370, -1, -1));

        ngayBDLabel.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        ngayBDLabel.setText("Ngày bắt đầu làm việc:");
        TTCN.add(ngayBDLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 430, -1, 40));

        ngayBatDauField.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        ngayBatDauField.setText("null");
        TTCN.add(ngayBatDauField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 460, -1, 40));

        jTabbedPane2.addTab("tab2", TTCN);

        DSDuAn.setBackground(new java.awt.Color(228, 238, 244));
        DSDuAn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nhanVienTable1.setRowHeight(25);
        nhanVienTable1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        nhanVienTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Dự án", "Tên Dự án", "Ngày Thực hiện"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        nhanVienTable1.setRowHeight(50);
        jScrollPane6.setViewportView(nhanVienTable1);

        DSDuAn.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 820, 340));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(74, 98, 138));
        jLabel7.setText("Danh sách Nhân viên của Dự án");
        DSDuAn.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 200, 250, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("THÔNG TIN DỰ ÁN");
        DSDuAn.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

        themTTDABtn.setText("Thêm");
        themTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themTTDABtnActionPerformed(evt);
            }
        });
        DSDuAn.add(themTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        xoaTTDABtn.setText("Xóa");
        xoaTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoaTTDABtnActionPerformed(evt);
            }
        });
        DSDuAn.add(xoaTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, -1));

        suaTTDABtn.setText("Sửa");
        suaTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suaTTDABtnActionPerformed(evt);
            }
        });
        DSDuAn.add(suaTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        searchTTDATxtField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTTDATxtFieldKeyReleased(evt);
            }
        });
        DSDuAn.add(searchTTDATxtField, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 240, 30));

        openTTDABtn.setText("Mở");
        openTTDABtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openTTDABtnActionPerformed(evt);
            }
        });
        DSDuAn.add(openTTDABtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 200, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(74, 98, 138));
        jLabel9.setText("Danh sách Dự Án");
        DSDuAn.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(74, 98, 138));
        jLabel5.setText("Thanh tìm kiếm:");
        DSDuAn.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 90, -1, -1));

        jTabbedPane2.addTab("tab3", DSDuAn);

        LƯƠNG.setBackground(new java.awt.Color(228, 238, 244));
        LƯƠNG.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LuongTable.setDefaultEditor(Object.class, null);
        LuongTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Tên NV", "Chức vụ", "Lương cơ bản", "Ngày công", "Phụ cấp", "Tổng lương"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        LuongTable.setRowHeight(25);
        LuongTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(LuongTable);

        LƯƠNG.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, 900, 360));

        TimLuongtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimLuongtxtActionPerformed(evt);
            }
        });
        LƯƠNG.add(TimLuongtxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 180, -1));

        TimLuong.setText("Tìm");
        TimLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimLuongActionPerformed(evt);
            }
        });
        LƯƠNG.add(TimLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, -1, 20));

        LTitle1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        LTitle1.setText("THÔNG TIN LƯƠNG");
        LƯƠNG.add(LTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        DSNVLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        DSNVLabel3.setText("Danh sách thông tin lương của nhân viên:");
        LƯƠNG.add(DSNVLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        SET_ngaycong.setText("SET ngày công");
        SET_ngaycong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SET_ngaycongActionPerformed(evt);
            }
        });
        LƯƠNG.add(SET_ngaycong, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 120, 40));

        txtNgayCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayCongActionPerformed(evt);
            }
        });
        LƯƠNG.add(txtNgayCong, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 100, 40));

        SapxepBtn.setText("Sắp xếp");
        SapxepBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SapxepBtnActionPerformed(evt);
            }
        });
        LƯƠNG.add(SapxepBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 110, 40));

        setLuongBtn.setText("SET lương cb, phụ cấp");
        setLuongBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setLuongBtnActionPerformed(evt);
            }
        });
        LƯƠNG.add(setLuongBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 180, 40));

        jTabbedPane2.addTab("tab5", LƯƠNG);

        jPanel2.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, -40, 960, 730));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 690));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void LogoutButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_LogoutButtonMouseEntered
 
    private void LogoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutButtonMouseClicked
        // TODO add your handling code here:
        int tmp = JOptionPane.showConfirmDialog(this,"Confirm Logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if ( tmp == 0 ) {
            new Login(dsNV, dsA, dsL).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LogoutButtonMouseClicked

    private void SidebarDuAnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidebarDuAnActionPerformed
        // TODO add your handling code here:
        Footer.setVisible(true);
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_SidebarDuAnActionPerformed

    private void SidebarTTCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidebarTTCNActionPerformed
        // TODO add your handling code here:
        Footer.setVisible(true);
        loadEmployeeDataFromFile();
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_SidebarTTCNActionPerformed

    private void SidebarNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidebarNhanVienActionPerformed
        // TODO add your handling code here:
        Footer.setVisible(false);
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_SidebarNhanVienActionPerformed

    private void SidebarTTCNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SidebarTTCNMouseClicked

    }//GEN-LAST:event_SidebarTTCNMouseClicked

    private void SearchNVPBTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchNVPBTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchNVPBTextActionPerformed
    
    private void SearchNVPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchNVPBButtonActionPerformed
        // TODO add your handling code here:
        String searchText = SearchNVPBText.getText();
        if(!searchText.equals("")){
            DefaultTableModel modelNVPB = (DefaultTableModel)NhanVienPBTable.getModel();
            modelNVPB.setRowCount(0);
            PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
            for(NhanVien x:pb.getDSNhanVien()){
                if(x.getMaNV().equals(searchText) || x.getTenNV().contains(searchText)){
                    modelNVPB.addRow(x.getData());
                }
            }
        }else{
            ResetTableNVPB();
        }
    }//GEN-LAST:event_SearchNVPBButtonActionPerformed

    private void ThemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemButtonActionPerformed
        // TODO add your handling code here:
        glassPane.setVisible(true);
        ThemSuaPanel.setVisible(true);
        ThemSuaTitle.setText("Thêm thông tin phòng ban mới");
        TenPBtext.setText("");
    }//GEN-LAST:event_ThemButtonActionPerformed

    private void HuyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HuyButtonActionPerformed
        // TODO add your handling code here:
        glassPane.setVisible(false);
        ThemSuaPanel.setVisible(false);
    }//GEN-LAST:event_HuyButtonActionPerformed

    private void XacNhanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XacNhanButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
        if(TenPBtext.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Xin hãy thêm đủ thông tin!");
        }else{
            if(ThemSuaTitle.getText().equals("Chỉnh sửa thông tin phòng ban")){
                String newName = TenPBtext.getText();
                for(PhongBan x:dsPB.getDSPhongBan()){
                    if(x.getMaPhongBan().equals(modelDSPB.getValueAt(DSPhongBanTable.getSelectedRow(), 1))){
                        x.setTenPhongBan(newName);
                        newName = x.getTenPhongBan();
                        break;
                    }
                }
                dsPB.writeFile(); //Ghi File sau khi chỉnh sửa
                modelDSPB.setValueAt(newName,DSPhongBanTable.getSelectedRow(), 2); 
                ThemSuaPanel.setVisible(false);
            }else{
                String newName = TenPBtext.getText();
                int nextCnt = Integer.parseInt(dsPB.getDSPhongBan().getLast().getMaPhongBan().substring(2))+1;
                PhongBan newPB = new PhongBan(String.format("PB%02d", nextCnt),newName);
                dsPB.addPhongBan(newPB);
                dsPB.writeFile(); //Ghi File sau khi chỉnh sửa
                Object[] newPBData = {DSPhongBanTable.getRowCount()+1, newPB.getMaPhongBan(), newPB.getTenPhongBan(), 0};
                modelDSPB.addRow(newPBData);
                ThemSuaPanel.setVisible(false);
            }
            glassPane.setVisible(false);
        }
    }//GEN-LAST:event_XacNhanButtonActionPerformed

    private void SuaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SuaButtonActionPerformed
        // TODO add your handling code here:
        
        
        if(DSPhongBanTable.getSelectedRowCount()==1){
            DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
            ThemSuaTitle.setText("Chỉnh sửa thông tin phòng ban");
            String tblTenPB = modelDSPB.getValueAt(DSPhongBanTable.getSelectedRow(), 2).toString();
            TenPBtext.setText(tblTenPB);
            glassPane.setVisible(true);
            ThemSuaPanel.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phòng ban để chỉnh sửa!");
        }
    }//GEN-LAST:event_SuaButtonActionPerformed

    private void XoaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaButtonActionPerformed
        // TODO add your handling code here:
        if(DSPhongBanTable.getSelectedRowCount()==1){
            DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
            dsPB.removePhongBan(dsPB.SearchPhongBan(DSPhongBanTable.getValueAt(DSPhongBanTable.getSelectedRow(), 1).toString())); //Xóa Phòng ban đang chọn trong dsPB
            dsPB.writeFile(); //Ghi File sau khi chỉnh sửa
            modelDSPB.removeRow(DSPhongBanTable.getSelectedRow()); //Xóa Phòng ban đang chọn trong giao diện
            for(int i=1;i<=DSPhongBanTable.getRowCount();i++){
                modelDSPB.setValueAt(i, i-1, 0);
            }
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phòng ban để xóa!");
        }

    }//GEN-LAST:event_XoaButtonActionPerformed

    private void SearchPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPBButtonActionPerformed
        // TODO add your handling code here:
        String searchText = SearchPBText.getText();
        if(!searchText.equals("")){
            DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
            modelDSPB.setRowCount(0);
            for(PhongBan x:dsPB.getDSPhongBan()){
                if(x.getMaPhongBan().equals(searchText) || x.getTenPhongBan().contains(searchText)){
                    Object[] newPBData = {DSPhongBanTable.getRowCount()+1, x.getMaPhongBan(), x.getTenPhongBan(), x.getDSNhanVien().size()};
                    modelDSPB.addRow(newPBData);
                }
            }
        }else{
            ResetTablePB();
        }
    }//GEN-LAST:event_SearchPBButtonActionPerformed

    private void SearchPBTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchPBTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchPBTextActionPerformed

    private void TTChiTietButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TTChiTietButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelNVPB = (DefaultTableModel)NhanVienPBTable.getModel();
        if(DSPhongBanTable.getSelectedRowCount()==1){
            WarningTxt.setVisible(true);
            int ind = DSPhongBanTable.getSelectedRow();
            String MaPB = DSPhongBanTable.getValueAt(ind, 1).toString();
            PhongBan pb = dsPB.SearchPhongBan(MaPB);
            
            MaPBText.setText(pb.getMaPhongBan());
            TenPBText.setText(pb.getTenPhongBan());
            modelNVPB.setRowCount(0);
            for(NhanVien x:pb.getDSNhanVien()){
                if(x.getChucVu().equals("Truong phong")) WarningTxt.setVisible(false);
                modelNVPB.addRow(x.getData());
            }
            glassPane.remove(ThemSuaPanel);
            glassPane.add(ThemNVPBPanel);
            ThemNVPBPanel.setVisible(false);
            QuanLyPhongBan.setSelectedIndex(1);
            
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 phòng ban để truy cập!");
        }
    }//GEN-LAST:event_TTChiTietButtonActionPerformed

    private void TenPBtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TenPBtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TenPBtextActionPerformed

    private void ResetButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResetButtonMouseClicked
        // TODO add your handling code here:
        ResetTablePB();
    }//GEN-LAST:event_ResetButtonMouseClicked

    private void ThemNVPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemNVPBButtonActionPerformed
        // TODO add your handling code here:
        SetupNonNVPBTable();
        glassPane.setVisible(true);
        ThemNVPBPanel.setVisible(true);
        
    }//GEN-LAST:event_ThemNVPBButtonActionPerformed

    private void XoaNVPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaNVPBButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelNVPB = (DefaultTableModel)NhanVienPBTable.getModel();
        if(NhanVienPBTable.getSelectedRowCount()==1){
            int ind = NhanVienPBTable.getSelectedRow();
            String maNV = NhanVienPBTable.getValueAt(ind, 0).toString();
            NhanVien nv = dsNV.searchNhanVien(maNV);
            if(nv.getChucVu().equals("Truong phong")){
                WarningTxt.setVisible(true);
            }
            PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
            pb.removeNhanVien(nv);
            dsPB.writeFile();
            modelNVPB.removeRow(ind);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên để thêm!");
        }
    }//GEN-LAST:event_XoaNVPBButtonActionPerformed

    private void HuyNVPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HuyNVPBButtonActionPerformed
        // TODO add your handling code here:
        glassPane.setVisible(false);
        ThemNVPBPanel.setVisible(false);
    }//GEN-LAST:event_HuyNVPBButtonActionPerformed

    private void XacNhanNVPBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XacNhanNVPBButtonActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelNVPB = (DefaultTableModel)NhanVienPBTable.getModel();
        if(NonNVPBTable.getSelectedRowCount()==1){
            int ind = NonNVPBTable.getSelectedRow();
            String maNV = NonNVPBTable.getValueAt(ind, 0).toString();
            NhanVien nv = dsNV.searchNhanVien(maNV);
            PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
            
            if(nv.getChucVu().equals("Truong phong")) { //Check ràng buộc 1 trưởng phòng 1 pb
                WarningTxt.setVisible(false);
                for(NhanVien x:pb.getDSNhanVien()){
                    if(x.getChucVu().equals("Truong phong")){
                        JOptionPane.showMessageDialog(this, "Phòng ban này đã có trưởng phòng!");
                        return;
                    }
                }
            }else if(nv.getChucVu().equals("Admin")){
                JOptionPane.showMessageDialog(this, "Không được thêm Admin vào phòng ban!");
                return;
            }
            
            pb.addNhanVien(nv);
            dsPB.writeFile();
            modelNVPB.addRow(nv.getData());
            glassPane.setVisible(false);
            ThemNVPBPanel.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên để thêm!");
        }
    }//GEN-LAST:event_XacNhanNVPBButtonActionPerformed

    private void ResetNVPBButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ResetNVPBButtonMouseClicked
        // TODO add your handling code here:
        ResetTableNVPB();
    }//GEN-LAST:event_ResetNVPBButtonMouseClicked

    private void BackNVPBButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackNVPBButtonMouseClicked
        // TODO add your handling code here:
        DefaultTableModel modelDSPB = (DefaultTableModel)DSPhongBanTable.getModel();
        modelDSPB.setValueAt(dsPB.SearchPhongBan(MaPBText.getText()).getDSNhanVien().size(), DSPhongBanTable.getSelectedRow(), 3);
        glassPane.remove(ThemNVPBPanel);
        glassPane.add(ThemSuaPanel);
        ThemSuaPanel.setVisible(false);
        QuanLyPhongBan.setSelectedIndex(0);
    }//GEN-LAST:event_BackNVPBButtonMouseClicked

    private void SearchNVTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchNVTextActionPerformed

    }//GEN-LAST:event_SearchNVTextActionPerformed

    private void SearchNVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchNVButtonActionPerformed
        String searchText = SearchNVText.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) DSNhanVienTable.getModel();
        model.setRowCount(0);
        for (NhanVien nv : dsNV.getDSNhanVien()) {
            if (nv.getMaNV().toLowerCase().contains(searchText) ||
                nv.getTenNV().toLowerCase().contains(searchText) ||
                    isApproximateMatch(nv.getTenNV().toLowerCase(), searchText)) {
                model.addRow(nv.getData());
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_SearchNVButtonActionPerformed

    private void UpdateNVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateNVButtonActionPerformed
        int selectedRow = DSNhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một nhân viên để cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String tenNV = tenNVField1.getText().trim();
        String email = emailField1.getText().trim();
        String sdt = sdtField1.getText().trim();

        String dayOfBirth = (String) ngaySinhNVBox.getSelectedItem();
        String monthOfBirth = (String) thangSinhNVBox.getSelectedItem();
        String yearOfBirth = (String) namSinhNVBox.getSelectedItem();
        String ngaySinh = dayOfBirth + "/" + monthOfBirth + "/" + yearOfBirth;

        String dayStart = (String) ngayBDNVBox.getSelectedItem();
        String monthStart = (String) thangBDNVBox.getSelectedItem();
        String yearStart = (String) namBDNVBox.getSelectedItem();
        String ngayBatDau = dayStart + "/" + monthStart + "/" + yearStart;

        String gioiTinh = (String) gioiTinhBox.getSelectedItem();
        String chucVu = (String) chucVuBox.getSelectedItem();

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isDateValid(Integer.parseInt(dayOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(yearOfBirth))) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isDateValid(Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isStartDateAfterBirthDate(Integer.parseInt(dayOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(yearOfBirth),
        Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
            JOptionPane.showMessageDialog(this, "Nhân viên công ty phải từ 18 tuổi trở lên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        NhanVien nv = dsNV.getDSNhanVien().get(selectedRow);
        nv.setTenNV(tenNV);
        nv.setChucVu(chucVu);
        nv.setGioiTinh(gioiTinh);
        nv.setEmail(email);
        nv.setSoDT(sdt);
        nv.setNgaySinh(ngaySinh);
        nv.setNgayBatDau(ngayBatDau);
        JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        for(Luong x:dsL.getDSLuong()){
            if(x.getMaNV().equals(nv.getMaNV())){
                x.setNV(nv);
                x.setLuongCB();
                x.setPhuCap();
            }
        }
        updateDSLuong();
        
        updateTable();
        resetFields();
        dsNV.writeToFile("NV1.txt");
    }//GEN-LAST:event_UpdateNVButtonActionPerformed

    private void AddNVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNVButtonActionPerformed
        
//        jFrame1.setVisible(true);
        
        String tenNV = tenNVField1.getText().trim();
        String email = emailField1.getText().trim();
        String sdt = sdtField1.getText().trim();

        String dayOfBirth = String.format("%02d", Integer.valueOf((String) ngaySinhNVBox.getSelectedItem()));
        String monthOfBirth = String.format("%02d", Integer.valueOf((String) thangSinhNVBox.getSelectedItem()));
        String yearOfBirth = (String) namSinhNVBox.getSelectedItem();
        String ngaySinh = dayOfBirth + "/" + monthOfBirth + "/" + yearOfBirth;

        String dayStart = String.format("%02d", Integer.valueOf((String) ngayBDNVBox.getSelectedItem()));
        String monthStart = String.format("%02d", Integer.valueOf((String) thangBDNVBox.getSelectedItem()));
        String yearStart = (String) namBDNVBox.getSelectedItem();
        String ngayBatDau = dayStart + "/" + monthStart + "/" + yearStart;

        String gioiTinh = (String) gioiTinhBox.getSelectedItem();

        String chucVu = (String) chucVuBox.getSelectedItem();

        if (tenNV.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên NV không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidPhoneNumber(sdt)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isDateValid(Integer.parseInt(dayOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(yearOfBirth))) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isDateValid(Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!isStartDateAfterBirthDate(Integer.parseInt(dayOfBirth), Integer.parseInt(monthOfBirth), Integer.parseInt(yearOfBirth),
        Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
            JOptionPane.showMessageDialog(this, "Nhân viên công ty phải từ 18 tuổi trở lên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int nextCnt = Integer.parseInt(dsNV.getDSNhanVien().getLast().getMaNV().substring(2))+1;
        NhanVien nv = new NhanVien(String.format("NV%02d", nextCnt),tenNV, chucVu, gioiTinh, email, sdt, ngaySinh, ngayBatDau);
        dsNV.addDSNhanVien(nv);
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        
        updateDSLuong();
        updateTable();
        resetFields();
        dsNV.writeToFile("NV1.txt");
    }//GEN-LAST:event_AddNVButtonActionPerformed

    private void DeleteNVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteNVButtonActionPerformed
        int selectedRow = DSNhanVienTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Hãy chọn một nhân viên để xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maNV = (String) DSNhanVienTable.getValueAt(selectedRow, 0); 
        String chucVu = (String) DSNhanVienTable.getValueAt(selectedRow, 2); 
        String currentUser = nv.getMaNV(); 
        
//        Iterator<String> iterator = list.iterator();
        for (DuAn tmp : dsDA.getDSDuAn()) {
            Iterator<NhanVien> iterator = tmp.getDSNhanVien().iterator();
            while (iterator.hasNext()) {
                NhanVien tmpNV = iterator.next();
                if (tmpNV.getMaNV().equals(maNV)) {
                    iterator.remove(); // Xóa phần tử an toàn
                }
            }
        }
        
        dsDA.writeFile();
        
        for (PhongBan tmpPB : dsPB.getDSPhongBan()) {
            Iterator<NhanVien> iterator = tmpPB.getDSNhanVien().iterator();
            while (iterator.hasNext()) {
                NhanVien tmpNV = iterator.next();
                if (tmpNV.getMaNV().equals(maNV)) {
                iterator.remove(); // Xóa phần tử an toàn
                }
            }
        }
        
        dsPB.writeFile();

        if ("Admin".equalsIgnoreCase(chucVu)) {
           JOptionPane.showMessageDialog(this, "Không thể xóa nhân viên có chức vụ Admin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
           return;
        }

        if (maNV.equals(currentUser)) {
           JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản của chính bạn!", "Lỗi", JOptionPane.ERROR_MESSAGE);
           return;
        }
        dsNV.removeDSNhanVien(dsNV.searchNhanVien(maNV));
        updateDSLuong();
        updateTable();
        dsNV.writeToFile("NV1.txt");
    }//GEN-LAST:event_DeleteNVButtonActionPerformed

    private void chucVuBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chucVuBoxActionPerformed
        String selectedOption = (String) chucVuBox.getSelectedItem();
    }//GEN-LAST:event_chucVuBoxActionPerformed

    private void gioiTinhBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gioiTinhBoxActionPerformed
        String selectedOption = (String) gioiTinhBox.getSelectedItem();
    }//GEN-LAST:event_gioiTinhBoxActionPerformed

    private void SidebarPhongBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidebarPhongBanActionPerformed
        Footer.setVisible(true);
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_SidebarPhongBanActionPerformed

    private void ngaySinhNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngaySinhNVBoxActionPerformed
        updateDateString1();
    }//GEN-LAST:event_ngaySinhNVBoxActionPerformed

    private void thangSinhNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thangSinhNVBoxActionPerformed
        updateDateString1();
    }//GEN-LAST:event_thangSinhNVBoxActionPerformed

    private void namSinhNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namSinhNVBoxActionPerformed
        updateDateString1();
    }//GEN-LAST:event_namSinhNVBoxActionPerformed

    private void ngayBDNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngayBDNVBoxActionPerformed
       updateDateString2();
    }//GEN-LAST:event_ngayBDNVBoxActionPerformed

    private void thangBDNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thangBDNVBoxActionPerformed
        updateDateString2();
    }//GEN-LAST:event_thangBDNVBoxActionPerformed

    private void namBDNVBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namBDNVBoxActionPerformed
        updateDateString2();
    }//GEN-LAST:event_namBDNVBoxActionPerformed

    private void TimLuongtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimLuongtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TimLuongtxtActionPerformed

    private void TimLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimLuongActionPerformed
        // TODO add your handling code here:
        // Lấy mã nhân viên từ ô tìm kiếm
        String searchtxt = TimLuongtxt.getText().trim();
        // Kiểm tra nếu mã nhân viên không rỗng
        if (!searchtxt.isEmpty()) {
            DefaultTableModel model = (DefaultTableModel) LuongTable.getModel();
            model.setRowCount(0);
            for(Luong x:dsL.getDSLuong()){
                if(x.getMaNV().equals(searchtxt) || x.getNV().getTenNV().contains(searchtxt)){
                    model.addRow(new Object[]{x.getNV().getMaNV(),x.getNV().getTenNV(),x.getNV().getChucVu(),x.getLuongCB(),x.getNgayCong(),x.getPhuCap(),x.getTongLuong()});
                }
            }
        } else {
            updateDSLuong();
        }
    }//GEN-LAST:event_TimLuongActionPerformed

    private void SET_ngaycongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SET_ngaycongActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) LuongTable.getModel();
        if(LuongTable.getSelectedRowCount()==1){
            String NgayCongMoi = txtNgayCong.getText();
            txtNgayCong.setText("");
            try{
                // kiem tra ngay hop le
                int ngayCong=Integer.parseInt(NgayCongMoi);
                if(ngayCong < 17 || ngayCong > 26){
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số ngày công có giá trị trong khoảng 17 đến 26 ngày!");
                    return;
                }
                int selectedRow = LuongTable.getSelectedRow();
                model.setValueAt(ngayCong, selectedRow, 4);// update ngaycong

                String maNV = LuongTable.getValueAt(selectedRow, 0).toString();// lay maNV can cap nhat

                Luong luong = dsL.getLuongByMaNV(maNV);
                luong.setNgayCong(ngayCong);// set lai ngay cong
                long tongLuong = luong.tinhTongLuong();// tinh tong luong
                luong.setTongLuong(tongLuong);
                model.setValueAt(tongLuong, selectedRow, 6);
                dsL.saveToFile("dsLuong.txt");
                JOptionPane.showMessageDialog(this, "Đã cập nhật ngày công");
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số ngày công là số nguyên dương hợp lệ!");
            }

        }else{
            if(LuongTable.getSelectedRowCount()==0){
                JOptionPane.showMessageDialog(this, "Hãy chọn nhân viên cần cập nhật ngày công");
            }else{
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 dòng duy nhất");
            }
        }
    }//GEN-LAST:event_SET_ngaycongActionPerformed

    private void txtNgayCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayCongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayCongActionPerformed

    private void SapxepBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SapxepBtnActionPerformed
        // TODO add your handling code here:
        // Các lựa chọn sắp xếp
        String[] options = {"Theo mã nhân viên", "Tổng lương tăng dần", "Tổng lương giảm dần"};
        // Hiển thị hộp thoại lựa chọn
        int choice = JOptionPane.showOptionDialog(
            null,
            "Chọn kiểu sắp xếp:",
            "Sắp xếp",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        DefaultTableModel model = (DefaultTableModel) LuongTable.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
        switch (choice) {
            case 0: // Theo mã nhân viên
            dsL.sapXepTheoMaNhanVien();
            break;
            case 1: // Tổng lương tăng dần
            dsL.sapXepTheoTongLuongTangDan();
            break;
            case 2: // Tổng lương giảm dần
            dsL.sapXepTheoTongLuongGiamDan();
            break;
            default:
            // Người dùng đóng hộp thoại hoặc không chọn gì
            System.out.println("Không chọn kiểu sắp xếp nào");
        }
        // Sau khi sắp xếp, cập nhật lại dữ liệu vào bảng
        for (Luong x : dsL.getDSLuong()) {
            model.addRow(new Object[]{
                x.getNV().getMaNV(),
                x.getNV().getTenNV(),
                x.getNV().getChucVu(),
                x.getLuongCB(),
                x.getNgayCong(),
                x.getPhuCap(),
                x.getTongLuong()
            });
        }
        LuongTable.setModel(model);
    }//GEN-LAST:event_SapxepBtnActionPerformed

    private void setLuongBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setLuongBtnActionPerformed
        // TODO add your handling code here:
        // Danh sách lựa chọn
        String[] options = {"Thay đổi Lương cơ bản", "Thay đổi phụ cấp"};

        // Hiển thị hộp thoại lựa chọn
        int choice = JOptionPane.showOptionDialog(
            null,
            "Chọn thứ cần thay đổi:",
            "Thay đổi",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if(choice ==0 || choice ==1){
            String[] chucVu={"Chuyen vien", "Truong phong", "Admin"};
            String chonChucVu= (String) JOptionPane.showInputDialog(
                null,
                "Chọn chức vụ muốn thay: ",
                "Chọn chức vụ",
                JOptionPane.QUESTION_MESSAGE,
                null,
                chucVu,
                chucVu[0]
            );

            if (chonChucVu!=null){
                String inp=(String) JOptionPane.showInputDialog(
                    null,
                    "Nhập mức " + (choice == 0 ? "Lương cơ bản" : "Phụ cấp") + " mới cho chức vụ: "+chonChucVu
                );

                if(inp!=null && !inp.trim().isEmpty()){
                    try{
                        int value= Integer.parseInt(inp.trim());
                      
                        if(choice == 0){
                            System.out.println(chonChucVu+" "+value);
                            new Luong().setLuongCB(chonChucVu, value);
                        }else new Luong().setPhuCap(chonChucVu, value);
                        
                        for(Luong luong: dsL.getDSLuong()){
                            luong.setLuongCB();
                            luong.setPhuCap();
                            luong.setTongLuong(luong.tinhTongLuong());
                        }

                        // cap nhat lai bang
                        DefaultTableModel model = (DefaultTableModel) LuongTable.getModel();
                        model.setRowCount(0);
                        for(Luong x: dsL.getDSLuong()){
                            model.addRow(new Object[]{x.getNV().getMaNV(),x.getNV().getTenNV(),x.getNV().getChucVu(),x.getLuongCB(),x.getNgayCong(),x.getPhuCap(),x.getTongLuong()});
                        }
                        dsL.saveToFile("dsLuong.txt");
                        LuongTable.setModel(model);
                    }catch(NumberFormatException e){
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_setLuongBtnActionPerformed

    private void SidebarLuongSidebarLUONGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SidebarLuongSidebarLUONGActionPerformed
        // TODO add your handling code here:
        Footer.setVisible(true);
        jTabbedPane2.setSelectedIndex(4);
    }//GEN-LAST:event_SidebarLuongSidebarLUONGActionPerformed

    private void xacNhanTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xacNhanTTDABtnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelDSDA = (DefaultTableModel) nhanVienTable1.getModel();
        //         || jTextField4.getText().equals("")
        if (tenDATTDATxtField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Xin hãy thêm đủ thông tin!");
        }else{
            if(ThemSuaTitle1.getText().equals("Chỉnh sửa thông tin Dự án")){

                if (nhanVienTable1.getSelectedRowCount() == 1) {
                    // if single row is selected -> update
                    String newName = tenDATTDATxtField.getText();
                    String dayStart = String.format("%02d", Integer.valueOf((String) ngayCBB.getSelectedItem()));
                    String monthStart = String.format("%02d", Integer.valueOf((String) thangCBB.getSelectedItem()));
                    String yearStart = (String) namCBB.getSelectedItem();
                    String newDate = dayStart + "/" + monthStart + "/" + yearStart;
                    if (!isDateValid(Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
                        JOptionPane.showMessageDialog(this, "Ngày thực hiện không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    for(DuAn x : dsDA.getDSDuAn()){
                        if (x.getMaDA().equals(modelDSDA.getValueAt(nhanVienTable1.getSelectedRow(), 1))) {
                            x.setTenDA(newName);
                            x.setNgayThucHien(newDate);
                            newName = x.getTenDA();
                            newDate = x.getNgayThucHien();
                            break;
                        }
                    }
                    modelDSDA.setValueAt(newName, nhanVienTable1.getSelectedRow(), 2);
                    modelDSDA.setValueAt(newDate, nhanVienTable1.getSelectedRow(), 3);
                    tenDATTDATxtField.setText("");
                    //                    jTextField4.setText("");
                    themSuaTTDAFrame.dispose();
                    dsDA.writeFile();
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công !");

                }
                else {
                    // if row is not selected or multiple row is selected
                    themSuaTTDAFrame.dispose();
                    JOptionPane.showMessageDialog(this, "Hãy chọn 1 bản ghi để cập nhật !");

                }

            }else{
                String newName = tenDATTDATxtField.getText();
                //                String newNgayTH = jTextField4

                String dayStart = String.format("%02d", Integer.valueOf((String) ngayCBB.getSelectedItem()));
                String monthStart = String.format("%02d", Integer.valueOf((String) thangCBB.getSelectedItem()));
                String yearStart = (String) namCBB.getSelectedItem();
                String newNgayTH = dayStart + "/" + monthStart + "/" + yearStart;
                //                PhongBan newPB = new PhongBan(newName);
                if (!isDateValid(Integer.parseInt(dayStart), Integer.parseInt(monthStart), Integer.parseInt(yearStart))) {
                    JOptionPane.showMessageDialog(this, "Ngày thực hiện không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int nextCnt = Integer.parseInt(dsDA.getDSDuAn().getLast().getMaDA().substring(2))+1;
                DuAn newDA = new DuAn(String.format("DA%02d", nextCnt), newName, newNgayTH);
                dsDA.addDuAn(newDA);
                Object[] newDAData = {nhanVienTable1.getRowCount() + 1, newDA.getMaDA(), newDA.getTenDA(), newDA.getNgayThucHien()};
                dsDA.writeFile();
                modelDSDA.addRow(newDAData);
                tenDATTDATxtField.setText("");
                //                jTextField4.setText("");
                //                ThemSuaPanel.setVisible(false);
            }
            //            glassPane.setVisible(false);
            themSuaTTDAFrame.dispose();

        }
    }//GEN-LAST:event_xacNhanTTDABtnActionPerformed

    private void huyTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_huyTTDABtnActionPerformed
        // TODO add your handling code here:
        //        glassPane.setVisible(false);
        tenDATTDATxtField.setText("");
        //        jTextField4.setText("");
        themSuaTTDAFrame.dispose();
    }//GEN-LAST:event_huyTTDABtnActionPerformed

    private void tenDATTDATxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tenDATTDATxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tenDATTDATxtFieldActionPerformed

    private void ngayCBBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ngayCBBMouseReleased
        // TODO add your handling code here:
        updateNgayTHString();
    }//GEN-LAST:event_ngayCBBMouseReleased

    private void ngayCBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ngayCBBActionPerformed
        updateNgayTHString();
    }//GEN-LAST:event_ngayCBBActionPerformed

    private void thangCBBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_thangCBBMouseReleased
        // TODO add your handling code here:
        updateNgayTHString();
    }//GEN-LAST:event_thangCBBMouseReleased

    private void thangCBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thangCBBActionPerformed
        updateNgayTHString();
    }//GEN-LAST:event_thangCBBActionPerformed

    private void namCBBMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_namCBBMouseReleased
        // TODO add your handling code here:
        updateNgayTHString();
    }//GEN-LAST:event_namCBBMouseReleased

    private void namCBBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namCBBActionPerformed
        updateNgayTHString();
    }//GEN-LAST:event_namCBBActionPerformed

    private void themSuaTTDAFrameWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_themSuaTTDAFrameWindowClosed
        // TODO add your handling code here:
        tenDATTDATxtField.setText("");
        //        jTextField4.setText("");
    }//GEN-LAST:event_themSuaTTDAFrameWindowClosed

    private void backNVTGDABtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backNVTGDABtnMouseClicked
        // TODO add your handling code here:
        danhSachNVFrame.dispose();
    }//GEN-LAST:event_backNVTGDABtnMouseClicked

    private void nhanVienTGDATableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienTGDATableMouseClicked
        // TODO add your handling code here:
        DefaultTableModel tblModel = (DefaultTableModel)nhanVienTGDATable.getModel();

        String tblMaNV = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 0).toString();
        String tblHoTen = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 1).toString();
        String tblChucVu = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 2).toString();
        String tblGioiTinh = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 3).toString();
        String tblEmail = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 4).toString();
        String tblSoDT = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 5).toString();
        String tblNgaySinh = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 6).toString();
        String tblNgayBatDau = tblModel.getValueAt(nhanVienTGDATable.getSelectedRow(), 7).toString();

        // set data to text field
        //        maNVField.setText(tblMaDA);
        //        hoTenField.setText(tblHoTen);
        //        gioiTinhField.setText(tblNgayTH);
        //        ngaySinhField.setText(tblNgaySinh);
        //        sdtField.setText(tblSDT);
        //        emailField.setText(tblEmail);
        //        luongCBField.setText(tblLuongCB);
        //        phuCapField.setText(tblPhuCap);
        //        ngayBatDauField.setText(tblNgayBatDau);
    }//GEN-LAST:event_nhanVienTGDATableMouseClicked

    private void searchNVDAFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNVDAFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchNVDAFieldActionPerformed

    private void searchNVDAFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchNVDAFieldKeyReleased
        // TODO add your handling code here:
        DefaultTableModel tbl = (DefaultTableModel)nhanVienTGDATable.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(tbl);
        nhanVienTGDATable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchNVDAField.getText()));
    }//GEN-LAST:event_searchNVDAFieldKeyReleased

    private void themNVDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNVDABtnActionPerformed
        // TODO add your handling code here:
        this.setEnabled(false);

        SetupNonNVDATable();

        danhSachNVNonDuAnFrame.setVisible(true);
    }//GEN-LAST:event_themNVDABtnActionPerformed

    private void xoaNVDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaNVDABtnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelNVDA = (DefaultTableModel)nhanVienTGDATable.getModel();
        if(nhanVienTGDATable.getSelectedRowCount()==1){
            int ind = nhanVienTGDATable.getSelectedRow();
            String maNV = nhanVienTGDATable.getValueAt(ind, 0).toString();
            NhanVien nv = dsNV.searchNhanVien(maNV);
            //            PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
            DuAn da = dsDA.timDuAn(maDATxtField.getText());
            da.removeNhanVien(nv);
            dsDA.writeFile();
            modelNVDA.removeRow(ind);
        }else{
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên để thêm!");
        }
    }//GEN-LAST:event_xoaNVDABtnActionPerformed

    private void resetNVDABtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resetNVDABtnMouseClicked
        // TODO add your handling code here:
        ResetTableNVDA();
    }//GEN-LAST:event_resetNVDABtnMouseClicked

    private void danhSachNVFrameWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_danhSachNVFrameWindowClosed
        // TODO add your handling code here:
        DefaultTableModel modelNVDA1 = (DefaultTableModel)nhanVienTGDATable.getModel();
        modelNVDA1.setRowCount(0);
        this.setEnabled(true);
    }//GEN-LAST:event_danhSachNVFrameWindowClosed

    private void nhanVienNonDuAnTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nhanVienNonDuAnTableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_nhanVienNonDuAnTableMouseClicked

    private void themNVNonDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themNVNonDABtnActionPerformed
        // TODO add your handling code here:

        //        DefaultTableModel modelNVPB = (DefaultTableModel)nhanVienTable3.getModel();
        //
        //        if (nhanVienTable3.getSelectedRowCount() == 1) {
            //            int ind = nhanVienTable3.getSelectedRow();
            //            String maNV = nhanVienTable3.getValueAt(ind, 0).toString();
            //            NhanVien nv = dsNV.searchNhanVien(maNV);
            //            DuAn pb = dsDA.timDuAn(jLabel25.getText());
            //            pb.addNhanVien(nv);
            //            dsDA.writeFile();
            //            ResetTableNVPB();
            //            modelNVPB.addRow(nv.getData());
            //            ResetTableNVPB();
            //            jFrame3.dispose();
            //            ResetTableNVPB();
            //
            //        } else {
            //            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên để thêm  !");
            //        }
        DefaultTableModel modelNVPB = (DefaultTableModel) nhanVienTGDATable.getModel();
        if (nhanVienNonDuAnTable.getSelectedRowCount() == 1) {
            int ind = nhanVienNonDuAnTable.getSelectedRow();
            String maNV = nhanVienNonDuAnTable.getValueAt(ind, 0).toString();
            NhanVien nv = dsNV.searchNhanVien(maNV);
            //            PhongBan pb = dsPB.SearchPhongBan(MaPBText.getText());
            DuAn pb = dsDA.timDuAn(maDATxtField.getText());
            pb.addNhanVien(nv);
            dsDA.writeFile();
            modelNVPB.addRow(nv.getData());
//            danhSachNVNonDuAnFrame.dispose();
            //            glassPane.setVisible(false);
            //            ThemNVPBPanel.setVisible(false);
            danhSachNVNonDuAnFrame.hide();
            modelNVPB.setRowCount(0);
            SetupNonNVDATable();
            DuAn da = dsDA.timDuAn(maDATxtField.getText());

            for(NhanVien x  :da.getDSNhanVien()){
                modelNVPB.addRow(x.getData());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 nhân viên để thêm!");
        }
    }//GEN-LAST:event_themNVNonDABtnActionPerformed

    private void searchNVNonDAFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNVNonDAFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchNVNonDAFieldActionPerformed

    private void searchNVNonDAFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchNVNonDAFieldKeyReleased
        // TODO add your handling code here:
        DefaultTableModel tbl = (DefaultTableModel)nhanVienNonDuAnTable.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(tbl);
        nhanVienNonDuAnTable.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchNVNonDAField.getText()));
    }//GEN-LAST:event_searchNVNonDAFieldKeyReleased

    private void backNVNonDABtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backNVNonDABtnMouseClicked
        // TODO add your handling code here:

        danhSachNVNonDuAnFrame.dispose();
    }//GEN-LAST:event_backNVNonDABtnMouseClicked

    private void danhSachNVNonDuAnFrameWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_danhSachNVNonDuAnFrameWindowClosed
        // TODO add your handling code here:
        DefaultTableModel modelNVDA1 = (DefaultTableModel)nhanVienTGDATable.getModel();
        modelNVDA1.setRowCount(0);
        this.setEnabled(true);
    }//GEN-LAST:event_danhSachNVNonDuAnFrameWindowClosed

    private void themTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themTTDABtnActionPerformed
        // TODO add your handling code here:
        ThemSuaTitle1.setText("Thêm thông tin Dự án mới");
        themSuaTTDAFrame.setVisible(true);
    }//GEN-LAST:event_themTTDABtnActionPerformed

    private void xoaTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoaTTDABtnActionPerformed
        // TODO add your handling code here:
        DefaultTableModel tblDA = (DefaultTableModel) nhanVienTable1.getModel();

        if (nhanVienTable1.getSelectedRowCount() == 1) {

            for ( DuAn tmp: dsDA.getDSDuAn() ) {
                if (tmp.getMaDA().equals(nhanVienTable1.getValueAt(nhanVienTable1.getSelectedRow(), 1))) {
                    tblDA.removeRow(nhanVienTable1.getSelectedRow());
                    dsDA.removeDuAn(tmp.getMaDA());
                    break;
                }
            }

            dsDA.writeFile();

        }
        else {
            if (nhanVienTable1.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Bảng rỗng !");
            }
            else {
                JOptionPane.showMessageDialog(this, "Hãy chọn 1 bản ghi để xóa !");
            }
        }
    }//GEN-LAST:event_xoaTTDABtnActionPerformed

    private void suaTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_suaTTDABtnActionPerformed
        // TODO add your handling code here:
        ThemSuaTitle1.setText("Chỉnh sửa thông tin Dự án");
        themSuaTTDAFrame.setVisible(true);
    }//GEN-LAST:event_suaTTDABtnActionPerformed

    private void searchTTDATxtFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTTDATxtFieldKeyReleased
        // TODO add your handling code here:
        DefaultTableModel tbl = (DefaultTableModel) nhanVienTable1.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(tbl);
        nhanVienTable1.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(searchTTDATxtField.getText()));
    }//GEN-LAST:event_searchTTDATxtFieldKeyReleased

    private void openTTDABtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openTTDABtnActionPerformed
        // TODO add your handling code here:
        //        .setEnabled(false);
        DefaultTableModel modelDSDA = (DefaultTableModel) nhanVienTable1.getModel();
        if (nhanVienTable1.getSelectedRowCount() == 1) {
            this.setEnabled(false);

            DefaultTableModel modelDSNVDA = (DefaultTableModel)nhanVienTGDATable.getModel();

            String tblMaDA = modelDSDA.getValueAt(nhanVienTable1.getSelectedRow(), 1).toString();
            String tblTenDA = modelDSDA.getValueAt(nhanVienTable1.getSelectedRow(), 2).toString();
            String tblNgayTH = modelDSDA.getValueAt(nhanVienTable1.getSelectedRow(), 3).toString();

            int cnt2 = 1;
            for(DuAn x: dsDA.getDSDuAn()){
                //            modelDSDA.addRow(new Object[]{cnt1++, x.getMaDA(), x.getTenDA(),x.getNgayThucHien()});
                if ( x.getMaDA().equals(tblMaDA) && x.getTenDA().equals(tblTenDA) && x.getNgayThucHien().equals(tblNgayTH) ) {
                    for ( NhanVien tmp: x.getDSNhanVien() ) {
                        modelDSNVDA.addRow(new Object[]{tmp.getMaNV(), tmp.getTenNV(), tmp.getChucVu(), tmp.getGioiTinh(), tmp.getEmail(), tmp.getSoDT(), tmp.getNgaySinh(), tmp.getNgayBatDau()});
                    }
                }
            }

            maDATxtField.setText(tblMaDA);
            tenDANVTGDAField.setText(tblTenDA);
            ngayBDTxtField.setText(tblNgayTH);

            danhSachNVFrame.setVisible(true);
        }
        else {
            if (nhanVienTable1.getRowCount() == 0) {
                //table is empty
                JOptionPane.showMessageDialog(this, "Bảng rỗng !");
            }
            else {
                // if row is not selected or multiple row is selected
                danhSachNVFrame.dispose();
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 Dự án để truy cập !");
            }
        }
    }//GEN-LAST:event_openTTDABtnActionPerformed
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        System.out.println("Hello");
//        System.out.println();
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
            java.util.logging.Logger.getLogger(AdminFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
////                SearchText1.setText("helloWorld");
////                new ChuyenVienFrame1().setVisible(true);
//            }
//        });
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
////                SearchText1.setText("helloWorld");
////                new AdminFrame1().setVisible(true);
//            }
//        });
    }
    private javax.swing.JLabel jLabelDate;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AccName;
    private javax.swing.JButton AddNVButton;
    private javax.swing.JLabel AppName;
    private javax.swing.JLabel AppName2;
    private javax.swing.JLabel BackNVPBButton;
    private javax.swing.JLabel ChucNangLabel;
    private javax.swing.JLabel ChucNangLabel1;
    private javax.swing.JPanel DSDuAn;
    private javax.swing.JLabel DSNVLabel;
    private javax.swing.JLabel DSNVLabel1;
    private javax.swing.JLabel DSNVLabel2;
    private javax.swing.JLabel DSNVLabel3;
    private javax.swing.JTable DSNhanVienTable;
    private javax.swing.JPanel DSPhongBan;
    private javax.swing.JTable DSPhongBanTable;
    private javax.swing.JLabel DSPhongBanTitle;
    private javax.swing.JButton DeleteNVButton;
    private javax.swing.JPanel Footer;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Header2;
    private javax.swing.JButton HuyButton;
    private javax.swing.JButton HuyNVPBButton;
    private javax.swing.JLabel LTitle1;
    private javax.swing.JLabel LogoutButton;
    private javax.swing.JTable LuongTable;
    private javax.swing.JPanel LƯƠNG;
    private javax.swing.JLabel MaPBLabel;
    private javax.swing.JLabel MaPBText;
    private javax.swing.JLabel NVTitle;
    private javax.swing.JPanel NhanVien;
    private javax.swing.JTable NhanVienPBTable;
    private javax.swing.JTable NonNVPBTable;
    private javax.swing.JLabel PBTitle;
    private javax.swing.JPanel PhongBan;
    private javax.swing.JTabbedPane QuanLyPhongBan;
    private javax.swing.JLabel ResetButton;
    private javax.swing.JLabel ResetNVPBButton;
    private javax.swing.JButton SET_ngaycong;
    private javax.swing.JButton SapxepBtn;
    private javax.swing.JButton SearchNVButton;
    private javax.swing.JButton SearchNVPBButton;
    private javax.swing.JTextField SearchNVPBText;
    private javax.swing.JTextField SearchNVText;
    private javax.swing.JButton SearchPBButton;
    private javax.swing.JTextField SearchPBText;
    private javax.swing.JPanel Sidebar;
    private javax.swing.JButton SidebarDuAn;
    private javax.swing.JButton SidebarLuong;
    private javax.swing.JButton SidebarNhanVien;
    private javax.swing.JButton SidebarPhongBan;
    private javax.swing.JButton SidebarTTCN;
    private javax.swing.JButton SuaButton;
    private javax.swing.JLabel SubAppName;
    private javax.swing.JLabel SubAppName2;
    private javax.swing.JPanel TTCN;
    private javax.swing.JButton TTChiTietButton;
    private javax.swing.JLabel TenPBLabel;
    private javax.swing.JLabel TenPBText;
    private javax.swing.JTextField TenPBtext;
    private javax.swing.JLabel TenTPLabel;
    private javax.swing.JButton ThemButton;
    private javax.swing.JButton ThemNVPBButton;
    private javax.swing.JPanel ThemNVPBPanel;
    private javax.swing.JLabel ThemNVPBTitle1;
    private javax.swing.JPanel ThemSuaPanel;
    private javax.swing.JLabel ThemSuaTitle;
    private javax.swing.JLabel ThemSuaTitle1;
    private javax.swing.JButton TimLuong;
    private javax.swing.JTextField TimLuongtxt;
    private javax.swing.JButton UpdateNVButton;
    private javax.swing.JLabel WarningTxt;
    private javax.swing.JButton XacNhanButton;
    private javax.swing.JButton XacNhanNVPBButton;
    private javax.swing.JButton XoaButton;
    private javax.swing.JButton XoaNVPBButton;
    private javax.swing.JLabel backNVNonDABtn;
    private javax.swing.JLabel backNVTGDABtn;
    private javax.swing.JComboBox<String> chucVuBox;
    private javax.swing.JFrame danhSachNVFrame;
    private javax.swing.JFrame danhSachNVNonDuAnFrame;
    private javax.swing.JPanel danhSachNVNonDuAnPanel;
    private javax.swing.JPanel danhSachNVPanel;
    private javax.swing.JLabel danhSachNVTGDALabel;
    private javax.swing.JLabel danhSachNhanVienNonDuAnLabel;
    private javax.swing.JLabel emailField;
    private javax.swing.JTextField emailField1;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox<String> gioiTinhBox;
    private javax.swing.JLabel gioiTinhField;
    private javax.swing.JLabel gioiTinhLabel;
    private javax.swing.JLabel hoTenField;
    private javax.swing.JLabel hoTenField1;
    private javax.swing.JLabel hoTenLabel;
    private javax.swing.JButton huyTTDABtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel luongCBField;
    private javax.swing.JLabel luongCBLabel;
    private javax.swing.JLabel maDALabel;
    private javax.swing.JLabel maDATxtField;
    private javax.swing.JLabel maNVField;
    private javax.swing.JLabel maNVLabel;
    private javax.swing.JComboBox<String> namBDNVBox;
    private javax.swing.JComboBox<String> namCBB;
    private javax.swing.JComboBox<String> namSinhNVBox;
    private javax.swing.JLabel ngayBDField;
    private javax.swing.JLabel ngayBDLabel;
    private javax.swing.JComboBox<String> ngayBDNVBox;
    private javax.swing.JLabel ngayBDTxtField;
    private javax.swing.JLabel ngayBatDauField;
    private javax.swing.JComboBox<String> ngayCBB;
    private javax.swing.JLabel ngaySinhField;
    private javax.swing.JLabel ngaySinhField1;
    private javax.swing.JLabel ngaySinhLabel;
    private javax.swing.JComboBox<String> ngaySinhNVBox;
    private javax.swing.JLabel ngayTHLabel;
    private javax.swing.JTable nhanVienNonDuAnTable;
    private javax.swing.JScrollPane nhanVienScrollTable;
    private javax.swing.JScrollPane nhanVienScrollTable1;
    private javax.swing.JTable nhanVienTGDATable;
    private javax.swing.JTable nhanVienTable1;
    private javax.swing.JButton openTTDABtn;
    private javax.swing.JLabel phuCapField;
    private javax.swing.JLabel phuCapLabel;
    private javax.swing.JLabel resetNVDABtn;
    private javax.swing.JLabel sdtField;
    private javax.swing.JTextField sdtField1;
    private javax.swing.JLabel sdtLabel;
    private javax.swing.JTextField searchNVDAField;
    private javax.swing.JTextField searchNVNonDAField;
    private javax.swing.JTextField searchTTDATxtField;
    private javax.swing.JButton setLuongBtn;
    private javax.swing.JButton suaTTDABtn;
    private javax.swing.JLabel tenDALabel;
    private javax.swing.JLabel tenDANVTGDAField;
    private javax.swing.JLabel tenDANVTGDALabel;
    private javax.swing.JTextField tenDATTDATxtField;
    private javax.swing.JTextField tenNVField1;
    private javax.swing.JComboBox<String> thangBDNVBox;
    private javax.swing.JComboBox<String> thangCBB;
    private javax.swing.JComboBox<String> thangSinhNVBox;
    private javax.swing.JButton themNVDABtn;
    private javax.swing.JButton themNVNonDABtn;
    private javax.swing.JFrame themSuaTTDAFrame;
    private javax.swing.JPanel themSuaTTDAPanel;
    private javax.swing.JButton themTTDABtn;
    private javax.swing.JTextField txtNgayCong;
    private javax.swing.JButton xacNhanTTDABtn;
    private javax.swing.JButton xoaNVDABtn;
    private javax.swing.JButton xoaTTDABtn;
    // End of variables declaration//GEN-END:variables
    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) DSNhanVienTable.getModel();
        model.setRowCount(0);
        for (NhanVien nv : dsNV.getDSNhanVien()) {
            model.addRow(new Object[]{
                nv.getMaNV(),
                nv.getTenNV(),
                nv.getChucVu(),
                nv.getGioiTinh(),
                nv.getEmail(),
                nv.getSoDT(),
                nv.getNgaySinh(),
                nv.getNgayBatDau()
            });
        }
    }

    private void updateDateString1() {
        try {
            if (ngaySinhNVBox.getSelectedItem() == null || 
                thangSinhNVBox.getSelectedItem() == null || 
                namSinhNVBox.getSelectedItem() == null) {
                jLabelDate.setText("Vui lòng chọn ngày, tháng, và năm!");
                return;
            }

            int day = Integer.parseInt((String) ngaySinhNVBox.getSelectedItem());
            int month = Integer.parseInt((String) thangSinhNVBox.getSelectedItem());
            int year = Integer.parseInt((String) namSinhNVBox.getSelectedItem());

            if (isDateValid(day, month, year)) {
                jLabelDate.setText(day + "/" + month + "/" + year);
            } else {
                jLabelDate.setText("Ngày không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            jLabelDate.setText("Lỗi: Giá trị không hợp lệ!");
        }
    }
    
    private void updateDateString2() {
        try {
            if (ngayBDNVBox.getSelectedItem() == null || 
                thangBDNVBox.getSelectedItem() == null || 
                namBDNVBox.getSelectedItem() == null) {
                jLabelDate.setText("Vui lòng chọn ngày, tháng, và năm!");
                return;
            }

            int day = Integer.parseInt((String) ngayBDNVBox.getSelectedItem());
            int month = Integer.parseInt((String) thangBDNVBox.getSelectedItem());
            int year = Integer.parseInt((String) namBDNVBox.getSelectedItem());

            if (isDateValid(day, month, year)) {
                jLabelDate.setText(day + "/" + month + "/" + year);
            } else {
                jLabelDate.setText("Ngày không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            jLabelDate.setText("Lỗi: Giá trị không hợp lệ!");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String sdt) {
        return sdt.matches("\\d{10,11}"); 
    }

    private boolean isDateValid(int day, int month, int year) {
        try {
            java.time.LocalDate.of(year, month, day);
            return true;
        } catch (java.time.DateTimeException e) {
            return false;
        }
    }
    
    private boolean isStartDateAfterBirthDate(int dayOfBirth, int monthOfBirth, int yearOfBirth, int dayStart, int monthStart, int yearStart) {
        LocalDate birthDate = LocalDate.of(yearOfBirth, monthOfBirth, dayOfBirth);
        LocalDate startDate = LocalDate.of(yearStart, monthStart, dayStart);
        int yearsBetween = Period.between(birthDate, startDate).getYears();
        return yearsBetween >= 18;
    }

    private void resetFields() {
        tenNVField1.setText("");
        emailField1.setText("");
        sdtField1.setText("");
        chucVuBox.setSelectedIndex(0);
        gioiTinhBox.setSelectedIndex(0);
        ngaySinhNVBox.setSelectedIndex(0);
        thangSinhNVBox.setSelectedIndex(0);
        namSinhNVBox.setSelectedIndex(0);
        ngayBDNVBox.setSelectedIndex(0);
        thangBDNVBox.setSelectedIndex(0);
        namBDNVBox.setSelectedIndex(0);
    }

    private void loadSelectedRowToForm(int selectedRow) {
        String tenNV = (String) DSNhanVienTable.getValueAt(selectedRow, 1);
        String chucVu = (String) DSNhanVienTable.getValueAt(selectedRow, 2);
        String gioiTinh = (String) DSNhanVienTable.getValueAt(selectedRow, 3);
        String email = (String) DSNhanVienTable.getValueAt(selectedRow, 4);
        String sdt = (String) DSNhanVienTable.getValueAt(selectedRow, 5);
        String ngaySinh = (String) DSNhanVienTable.getValueAt(selectedRow, 6);
        String ngayBatDau = (String) DSNhanVienTable.getValueAt(selectedRow, 7);

        tenNVField1.setText(tenNV);
        emailField1.setText(email);
        sdtField1.setText(sdt);

        String[] ngaySinhParts = ngaySinh.split("/");
        ngaySinhNVBox.setSelectedItem(ngaySinhParts[0]); 
        thangSinhNVBox.setSelectedItem(ngaySinhParts[1]); 
        namSinhNVBox.setSelectedItem(ngaySinhParts[2]); 

        String[] ngayBatDauParts = ngayBatDau.split("/");
        ngayBDNVBox.setSelectedItem(ngayBatDauParts[0]); 
        thangBDNVBox.setSelectedItem(ngayBatDauParts[1]); 
        namBDNVBox.setSelectedItem(ngayBatDauParts[2]); 

        gioiTinhBox.setSelectedItem(gioiTinh);
        chucVuBox.setSelectedItem(chucVu);
    }

    private boolean isApproximateMatch(String name, String searchText) {
        int index = 0;
        for (char c : name.toCharArray()) {
            if (index < searchText.length() && c == searchText.charAt(index)) {
                index++;
            }
        }
        return index == searchText.length();
    }
    
    private void updateNgayTHString() {
        try {
            if (ngayCBB.getSelectedItem() == null
                    || thangCBB.getSelectedItem() == null
                    || namCBB.getSelectedItem() == null) {
                jLabelDate.setText("Vui lòng chọn ngày, tháng, và năm!");
                return;
            }

            int day = Integer.parseInt((String) ngayCBB.getSelectedItem());
            int month = Integer.parseInt((String) thangCBB.getSelectedItem());
            int year = Integer.parseInt((String) namCBB.getSelectedItem());

            if (isDateValid(day, month, year)) {
                jLabelDate.setText(day + "/" + month + "/" + year);
            } else {
                jLabelDate.setText("Ngày không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            jLabelDate.setText("Lỗi: Giá trị không hợp lệ!");
        }
    }
}
