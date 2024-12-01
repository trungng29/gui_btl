package Class;

import java.util.*;

public class PhongBan {
    static int cnt = 1; //Biến đếm tĩnh, dùng để tạo mã phòng ban (MaPhongBan) tự động tăng dần.
    private List<NhanVien> DSNhanVien = new ArrayList<>();// dùng arraylist để lưu (quản lí) nhân viên
    private String MaPhongBan, TenPhongBan;// thuộc tính mã phòng ban, tên phòng ban.

    public PhongBan() {
        
    }
    
    //Constructor có tham số, khởi tạo mã phòng ban tăng dần theo mẫu, tên phòng ban.
    public PhongBan(String maPB, String TenPhongBan) {
        this.MaPhongBan = maPB;
        this.TenPhongBan = chuanHoaTenPhongBan(TenPhongBan);
    }

    public void setupCnt(){
        cnt=1;
    }
    
    // phương thức thêm nhân viên vào phòng ban.
    public void addNhanVien(NhanVien x) {
        DSNhanVien.add(x);
    }
    
    public void removeNhanVien(NhanVien x) {
        DSNhanVien.remove(x);
    }

    // phương thức trả về mã phòng ban
    public String getMaPhongBan() {
        return this.MaPhongBan;
    }

    // phương thức trả về tên phòng ban
    public String getTenPhongBan() {
        return this.TenPhongBan;
    }
    
    // phương thức thiết lập tên phòng ban
    public void setTenPhongBan(String TenPhongBan) {
        this.TenPhongBan = chuanHoaTenPhongBan(TenPhongBan);
    }

    public String chuanHoaTenPhongBan(String s){
        String res="";
        String[] tmp= s.split("\\s+");
        for(String x:tmp){
            res+=x.substring(0,1).toUpperCase()+x.substring(1).toLowerCase()+" ";
        }
        return res.trim();
    }

    // phương thức trả về danh sách nhân viên thuộc phòng ban cần tìm
    public List<NhanVien> getDSNhanVien() {
        Collections.sort(DSNhanVien); // sắp xếp theo thứ tự từ điển của mã nhân viên
        return DSNhanVien;
    }
}
