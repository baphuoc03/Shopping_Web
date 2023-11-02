package fpoly.duantotnghiep.shoppingweb.dto.request;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.model.DongSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class DonHangDTORequest {
    private String ma;
    private KhachHangModel nguoiSoHuu;
    private String voucher;
    private String tenNguoiNhan;
    private String soDienThoai;
    private String email;
    private String thanhPhoName;
    private Integer thanhPhoCode;
    private String quanHuyenName;
    private Integer quanHuyenCode;
    private String xaPhuongName;
    private String xaPhuongCode;
    private String diaChiChiTiet;
    private Date ngayDatHang;
    private Integer trangThai;
    private String ghiChu;
    private BigDecimal tienGiam;
    private BigDecimal phiGiaoHang;
    private int phuongThucThanhToan;
    private String trangThaiDetail;
    private String lyDoHuy;

    public DonHangModel mapModel() {
        DonHangModel donHangModel = new DonHangModel();
        if(this.voucher!=null && this.voucher.length()>0) {
            VoucherModel voucherModel = new VoucherModel();
            voucherModel.setMa(voucher);
            donHangModel.setVoucher(voucherModel);
        }
        donHangModel.setMa(ma);
        if(nguoiSoHuu!=null) donHangModel.setNguoiSoHuu(nguoiSoHuu);
        if(lyDoHuy!=null && !lyDoHuy.isBlank()) donHangModel.setLyDoHuy(lyDoHuy);
        donHangModel.setTenNguoiNhan(tenNguoiNhan);
        donHangModel.setSoDienThoai(soDienThoai);
        donHangModel.setEmail(email);
        donHangModel.setThanhphoName(thanhPhoName);
        donHangModel.setThanhPhoCode(thanhPhoCode);
        donHangModel.setQuanHuyenName(quanHuyenName);
        donHangModel.setQuanHuyenCode(quanHuyenCode);
        donHangModel.setXaPhuongName(xaPhuongName);
        donHangModel.setXaPhuongCode(xaPhuongCode);
        donHangModel.setDiaChiChiTiet(diaChiChiTiet);
        donHangModel.setNgayDatHang(ngayDatHang);
        donHangModel.setTrangThai(2);
        donHangModel.setGhiChu(ghiChu);
        donHangModel.setTienGiam(tienGiam);
        donHangModel.setPhiGiaoHang(phiGiaoHang);
        donHangModel.setPhuongThucThanhToan(phuongThucThanhToan == 1 ? false : true);
        return donHangModel;
    }
}
