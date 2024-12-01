package Class;

// Nguyễn Quang Trung - B22DCDT321

public class NhanVien implements Comparable<NhanVien>{
    static int COUNT = 1;

    private final String maNV;
    private String tenNV;
    private String gioiTinh;
    private String email;
    private String soDT;
    private String ngaySinh;
    private String ngayBatDau;
    private String chucVu;

    public NhanVien() {
        this.maNV = null;
        this.tenNV = null;
        this.gioiTinh = null;
        this.email = null;
        this.soDT = null;
        this.ngaySinh = null;
        this.ngayBatDau = null;
        this.chucVu = null;
    }

    public NhanVien(String maNV, String tenNV, String chucVu, String gioiTinh, String email, String soDT, String ngaySinh, String ngayBatDau) {
        this.maNV = maNV;
        this.tenNV = formatName(tenNV);
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.soDT = soDT;
        this.ngaySinh = ngaySinh;
        this.ngayBatDau = ngayBatDau;
    }

    // setter và getter cho các thuộc tính
    public String getMaNV() {
        return maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = formatName(tenNV);
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
    
    public String getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDT() {
        return soDT;
    }
    
    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }
    
    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }
    
    public String[] getData(){
        String[] res = new String[8];
        res[0] = getMaNV();
        res[1] = getTenNV();
        res[2] = getChucVu();
        res[3] = getGioiTinh();
        res[4] = getEmail();
        res[5] = getSoDT();
        res[6] = getNgaySinh();
        res[7] = getNgayBatDau();
        return res;
    }
    
    private String formatName(String name) {
        String res ="";
        String[] tmp = name.split("\\s+");
        for(String x:tmp){
            res += x.substring(0,1).toUpperCase()+x.substring(1).toLowerCase()+" ";
        }
        return res.trim();
    }
    
    @Override
    public int compareTo(NhanVien b){
        return this.maNV.compareTo(b.maNV);
    }
    
    @Override
    public String toString() {
        return maNV+"|"+tenNV + "|" + chucVu + "|" + gioiTinh + "|" + email + "|" + soDT + "|" + ngaySinh + "|" + ngayBatDau;
    }

    public static void setupCnt() {
        COUNT = 1; // Reset the counter to 0
    }
}