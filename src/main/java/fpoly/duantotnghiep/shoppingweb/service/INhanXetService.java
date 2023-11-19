package fpoly.duantotnghiep.shoppingweb.service;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanXetDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanXetDtoRequest;
import org.springframework.data.domain.Page;

public interface INhanXetService {
    Page<NhanXetDtoResponse> getNhanXetBySanPham(Integer page, Integer limit, String maSP);

    Page<NhanXetDtoResponse> getNhanXetBySanPhamAndRate(Integer page, Integer limit, String maSP, Float rate);

    void add(NhanXetDtoRequest nhanXetDtoRequest);

    Float getAvgRatingBySanPham(String maSP);
}
