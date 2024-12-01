/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import Class.DSLuong;
import Class.DSAccount;
import Class.DSNhanVien;
import gui.Login;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author huyle
 */
public class main{
    public static void main(String[] args) throws FileNotFoundException{
        DSPhongBan dsPB = new DSPhongBan();
        DSLuong dsL = new DSLuong();
        DSAccount dsA = new DSAccount();
        Scanner in = new Scanner(new File("NV1.txt"));
        DSNhanVien dSNhanVien = new DSNhanVien(dsA, dsL);
        File file = new File("dsLuong.txt");
        if (file.exists()) {
            dsL.loadFromFile("dsLuong.txt");
        } else {
            System.out.println("File dữ liệu không tồn tại. Sử dụng dữ liệu mặc định.");
        }
        
        while(in.hasNextLine()){
            String[] line = in.nextLine().trim().split("\\|");
            String maNV = line[0];
            String tenNV = line[1];
            String chucVu = line[2];
            String GioiTinh = line[3];
            String email = line[4];
            String SoDT = line[5];
            String NgaySinh = line[6];
            String NgayBatDau = line[7];
            NhanVien nv = new NhanVien(maNV, tenNV, chucVu, GioiTinh, email, SoDT, NgaySinh, NgayBatDau);
            System.out.println(tenNV+" "+chucVu+" "+GioiTinh+" "+email+" "+SoDT+" "+NgaySinh+" "+NgayBatDau);
            
            dSNhanVien.addDSNhanVienFirst(nv);
        }

//        dsL.saveToFile("dsLuong.txt");
        new Login(dSNhanVien, dsA, dsL).setVisible(true);
        
    }
    
}
