/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.util.*;
import java.io.*;

/**
 *
 * @author huyle
 */
public class DSLuong {

    private ArrayList<Luong> dsLuong;

    public DSLuong() {
        this.dsLuong = new ArrayList<>();
    }

    public ArrayList<Luong> getDSLuong() {
        return dsLuong;
    }

    public void addLuong(Luong luong) {
        dsLuong.add(luong);
    }

    public void removeLuong(Luong luong) {
        dsLuong.remove(luong);
    }

    public void in() {
        for (Luong x : dsLuong) {
            System.out.println(x);
        }
    }

    public Luong getLuongByMaNV(String s) {
        for (Luong x : dsLuong) {
            if (x.getMaNV().equals(s)) {
                return x;
            }
        }
        return null;
    }

    public void sapXepTheoMaNhanVien() {
        dsLuong.sort(Comparator.comparing(Luong::getMaNV));
    }

    public void sapXepTheoTongLuongTangDan() {
        dsLuong.sort(Comparator.comparing(Luong::getTongLuong));
    }

    public void sapXepTheoTongLuongGiamDan() {
        dsLuong.sort(Comparator.comparing(Luong::getTongLuong).reversed());
    }

    public void saveToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Luong luong : dsLuong) {
                writer.write(luong.getNV().getMaNV()); // Ghi dữ liệu, cần đảm bảo lớp `Luong` có phương thức `toString()` phù hợp
                writer.write("|");
                writer.write(luong.getNV().getTenNV());
                writer.write("|");
                writer.write(luong.getNV().getChucVu());
                writer.write("|");
                writer.write(luong.getNV().getGioiTinh());
                writer.write("|");
                writer.write(luong.getNV().getEmail());
                writer.write("|");
                writer.write(luong.getNV().getSoDT());
                writer.write("|");
                writer.write(luong.getNV().getNgaySinh());
                writer.write("|");
                writer.write(luong.getNV().getNgayBatDau());
                writer.write("|");
                writer.write(String.valueOf(luong.getLuongCB()));
                writer.write("|");
                writer.write(String.valueOf(luong.getNgayCong()));
                writer.write("|");
                writer.write(String.valueOf(luong.getPhuCap()));
                writer.write("|");
                writer.write(String.valueOf(luong.getTongLuong()));
                writer.newLine(); // Xuống dòng
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu dữ liệu: " + e.getMessage());
        }
    }

    public void loadFromFile(String filePath) {
        dsLuong.clear(); // Xóa dữ liệu cũ trước khi load
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|"); // Giả sử các trường được phân cách bằng dấu phẩy
                if (parts.length >= 5) { // Đảm bảo số lượng trường hợp lệ (tùy thuộc vào số lượng thuộc tính của `Luong`)
                    String maNV = parts[0];
                    String tenNV = parts[1];
                    String cv = parts[2];
                    String gt = parts[3];
                    String mail = parts[4];
                    String sdt = parts[5];
                    String dob = parts[6];
                    String ngaybatdau = parts[7];
                    int luongCoBan = Integer.parseInt(parts[8]);
                    int ngayCong = Integer.parseInt(parts[9]);
                    int phuCap = Integer.parseInt(parts[10]);
                    long tongLuong = Long.parseLong(parts[11]);
                    Luong luong = new Luong(new NhanVien(maNV, tenNV, cv, gt, mail, sdt, dob, ngaybatdau));
                    luong.setLuongCB(cv, luongCoBan); //dat lcb cho cv
                    luong.setPhuCap(cv, phuCap); //dat pc cho cv
                    luong.setLuongCB();// Khởi tạo đối tượng `Luong`
                    luong.setNgayCong(ngayCong);
                    luong.setPhuCap();
                    luong.setTongLuong(tongLuong);
                    dsLuong.add(luong); // Thêm vào danh sách
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc dữ liệu: " + e.getMessage());
        }
    }
}
