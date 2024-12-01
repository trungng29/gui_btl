package Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DSPhongBan {
    private List<PhongBan> dsPhongBan; // Danh sách phòng ban của công ty

    // Constructor khởi tạo danh sách phòng ban
    public DSPhongBan() {
        this.dsPhongBan = new ArrayList<>();
    }
    
    public void writeFile(){
        try {
            FileWriter in = new FileWriter(new File("PB.txt"));
            int cnt = 1;
            for(PhongBan x:dsPhongBan){
                if(cnt++ < dsPhongBan.size()){
                    in.write(x.getMaPhongBan()+"|"+x.getTenPhongBan());
                    for(NhanVien y:x.getDSNhanVien()){
                        in.write("|"+y.getMaNV());
                    }
                    in.write("\n");
                }
                else {
                    in.write(x.getMaPhongBan()+"|"+x.getTenPhongBan());
                    for(NhanVien y:x.getDSNhanVien()){
                        in.write("|"+y.getMaNV());
                    }
                }
            }
            in.close();
        } catch (IOException e) {
        }
    }

    public List<PhongBan> getDSPhongBan(){
        return dsPhongBan;
    }
    
    // b.1) Thêm mới phòng ban vào danh sách phòng ban của công ty
    public void addPhongBan(PhongBan pb) {
        dsPhongBan.add(pb);
    }

    // b.2) Xóa phòng ban khỏi danh sách phòng ban của công ty
    public void removePhongBan(PhongBan pb) {
        dsPhongBan.remove(pb);
    }

    // Phương thức tìm phòng ban theo mã
    public PhongBan SearchPhongBan(String n) {
        for (PhongBan pb : dsPhongBan) {
            if (pb.getMaPhongBan().equals(n) || pb.getTenPhongBan().equals(n)) {
                return pb;
            }
        }
        return null;
    }
}
