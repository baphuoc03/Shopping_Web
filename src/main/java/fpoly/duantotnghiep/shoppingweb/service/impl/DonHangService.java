package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DonHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DonHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDonHangResponsitory;
import fpoly.duantotnghiep.shoppingweb.repository.IDongSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonHangService implements IDonHangService {

    @Autowired
    private IDonHangResponsitory donHangResponsitory;

    @Override
    public List<DonHangDtoResponse> getAllByTrangThai(Integer trangThai) {
        return donHangResponsitory.getAllByTrangThai(trangThai).stream()
                .map(d -> new DonHangDtoResponse(d))
                .collect(Collectors.toList());
    }

    @Override
    public void checkOut(DonHangDTORequest donHangDTORequest) {
        DonHangModel model = donHangDTORequest.mapModel();
        donHangResponsitory.save(model);
    }

    @Override
    public DonHangDtoResponse findByMa(String ma) {
        return new DonHangDtoResponse(donHangResponsitory.findById(ma).orElse(new DonHangModel()));
    }

    @Override
    public Boolean existsByMa(String ma) {
        return donHangResponsitory.existsById(ma);
    }
}
