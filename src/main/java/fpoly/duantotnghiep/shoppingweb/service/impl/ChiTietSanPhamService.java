package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChiTietSanPhamService implements IChiTietSanPhamService {

    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<ChiTietSanPhamDtoResponse> getAllBySanPhamMa(String maSP) {
        return chiTietSanPhamRepository.getAllBySanPhamMa(maSP).stream()
                .map(c -> new ChiTietSanPhamDtoResponse(c)).collect(Collectors.toList());
    }
}
