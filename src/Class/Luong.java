/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author huyle
 */
public class Luong {
    static int lcbCV = 100000;
    static int lcbTP = 250000;
    static int lcbAD = 400000;
    static int pcCV = 150000;
    static int pcTP = 250000;
    static int pcAD = 300000;
    
    private NhanVien nv;
    private final String maNV;
    private int luongCB, ngayCong, phuCap;
    private long tongLuong;

    public Luong(){
        maNV = null;
    }
    
    public Luong(NhanVien nv) {
        this.nv = nv;
        this.maNV = nv.getMaNV();
        this.ngayCong = 0;
        setLuongCB();
        setPhuCap();
        this.tongLuong = tinhTongLuong();
    }

    public int getLuongCB() {
        return luongCB;
    }
    
    public NhanVien getNV() {
        return nv;
    }
    
    public void setNV(NhanVien nv){
        this.nv = nv;
    }
    
    public void setLuongCB() {
        if (nv.getChucVu().trim().equals("Chuyen vien")) {
            this.luongCB = lcbCV;
        } else if (nv.getChucVu().trim().equals("Truong phong")) {
            this.luongCB = lcbTP;
        } else if (nv.getChucVu().trim().equals("Admin")) {
            this.luongCB = lcbAD;
        }
    }

    public void setLuongCB(String pos, int n) {
        if(pos.equals("Chuyen vien"))  lcbCV = n;
        if(pos.equals("Truong phong"))  lcbTP = n;
        if(pos.equals("Admin")) lcbAD = n;
    }
    
    public int getPhuCap() {
        return phuCap;
    }

    public void setPhuCap() {
        if (nv.getChucVu().trim().equals("Chuyen vien")) {
            this.phuCap = pcCV; 
        } else if (nv.getChucVu().trim().equals("Truong phong")) {
            this.phuCap = pcTP;
        } else if(nv.getChucVu().trim().equals("Admin")){
            this.phuCap = pcAD;
        }
    }

    public void setPhuCap(String pos, int n) {
        if(pos.equals("Chuyen vien"))  pcCV = n;
        if(pos.equals("Truong phong"))  pcTP = n;
        if(pos.equals("Admin"))  pcAD = n;
    }

    public int getNgayCong() {
        return ngayCong;
    }

    public void setNgayCong(int ngayCong) {
        this.ngayCong = ngayCong;
    }

    public long setTongLuong(long tongLuong) {
        return this.tongLuong = tongLuong;
    }

    public long getTongLuong() {
        return tongLuong;
    }

    public long tinhTongLuong() {
        return luongCB * ngayCong + phuCap;
    }

    @Override
    public String toString() {
        return nv + " " + " " + luongCB + " " + ngayCong + " " + phuCap + " " + tongLuong;
    }

    public String getMaNV() {
        return this.maNV;
    }
}
