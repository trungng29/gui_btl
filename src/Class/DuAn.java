package Class;

import java.util.*;

public class DuAn {    
//    static int cnt = 1; //Biến đếm tĩnh, dùng để tạo mã dự án (MaDA) tự động tăng dần.
    private String maDA, tenDA, ngayThucHien;  //các thuộc tính: mã dự án, tên dự án, ngày thực hiện
    private ArrayList<NhanVien> DSNhanVien; // Danh sách nhân viên tham gia dự án
    
    //Constructor có tham số, khởi tạo mã dự án tăng dần theo mẫu, tên dự án và ngày thực hiện theo dữ liệu vào
    public DuAn(String maDA, String tenDa, String ngayThucHien){
        this.maDA = maDA;  //mã dự án tự động tăng dần
        setTenDA(tenDa);     //tên dự án được chuẩn hóa
        setNgayThucHien(ngayThucHien);  //dữ liệu ngày tháng được kiểm tra, đảm bảo chuẩn dd/mm/yyyy
        DSNhanVien = new ArrayList<>();
    }

    // phương thức thêm nhân viên vào dự án.
    public void addNhanVien(NhanVien x) {
        DSNhanVien.add(x);
    }
    
    public void removeNhanVien(NhanVien x) {
        DSNhanVien.remove(x);
    }
    
    //phương thức trả về mã dự án
    public String getMaDA(){ 
        return this.maDA;
    }
    
    //phương thức thiết lập tên dự án
    public void setTenDA(String newName){ 
        this.tenDA = formatName(newName);
    }
    
    //phương thức trả về tên dự án
    public String getTenDA(){
        return this.tenDA;
    }
    
    //phương thức thiết lập ngày thực hiện
    public void setNgayThucHien(String newDate){
        this.ngayThucHien = newDate; //thiết lập với những dữ liệu đã đạt chuẩn
    }
    
    //phương thức trả về ngày thực hiện
    public String getNgayThucHien(){ 
        return this.ngayThucHien;
    }
    
    //phương thức chuẩn hóa tên dự án
    public String formatName(String n){
        StringBuilder formatedName = new StringBuilder();
        for(String x:n.trim().toLowerCase().split("\\s+")){ //chuẩn hóa để các từ cách nhau 1 ô trống
            formatedName.append(x+" ");
        }
        //các kí tự trong tên viết thường ngoại trừ kí tự đầu tiên in hoa
        formatedName.replace(0, 1, Character.toString(formatedName.charAt(0)).toUpperCase());
        return formatedName.toString().trim();
    }

    // phương thức trả về danh sách nhân viên thuộc dự án cần tìm
    public ArrayList<NhanVien> getDSNhanVien() {
        Collections.sort(DSNhanVien);
        return DSNhanVien;
    }
}
