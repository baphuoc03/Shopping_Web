package fpoly.duantotnghiep.shoppingweb.dto.reponse;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DonHangReponseUser {
    private String ma;
    private String nguoiSoHuu;
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
    private String trangThaiDetail;
    private String phuongThucThanhToan;
    private String lyDoHuy;
    private String voucherCode;
    private String voucherName;
    private List<ChiTietDonHangDtoResponse> chiTietDonHang;


    public DonHangReponseUser(DonHangModel model) {
        this.ma = model.getMa();
        this.nguoiSoHuu = model.getNguoiSoHuu() == null ? "" : model.getNguoiSoHuu().getHoVaTen();
        this.tenNguoiNhan = model.getTenNguoiNhan();
        this.soDienThoai = model.getSoDienThoai();
        this.email = model.getEmail();
        this.thanhPhoName = model.getThanhphoName();
        this.thanhPhoCode = model.getThanhPhoCode();
        this.quanHuyenName = model.getQuanHuyenName();
        this.quanHuyenCode = model.getQuanHuyenCode();
        this.xaPhuongName = model.getXaPhuongName();
        this.xaPhuongCode = model.getXaPhuongCode();
        this.diaChiChiTiet = model.getDiaChiChiTiet();
        this.ngayDatHang = model.getNgayDatHang();
        this.trangThai = model.getTrangThai();
        this.ghiChu = model.getGhiChu();
        this.tienGiam = model.getTienGiam() == null ? BigDecimal.valueOf(0) : model.getTienGiam();
        this.phiGiaoHang = model.getPhiGiaoHang();
        this.trangThaiDetail = model.trangThaiDetail();
        this.phuongThucThanhToan = model.getPhuongThucThanhToan() == true ? "Thanh toán khi nhận hàng" : "Thanh toán online";
        this.lyDoHuy = model.getLyDoHuy();
        if (model.getVoucher() != null) {
            voucherCode = model.getVoucher().getMa();
            voucherName = model.getVoucher().getTen();
        }
        this.chiTietDonHang = model.getDanhSachSanPham().stream().map(s -> new ChiTietDonHangDtoResponse(s)).collect(Collectors.toList());
    }
}
