package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChiTietSanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChiTietSanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.ChiTietSanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.IChiTietSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.IChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChiTietSanPhamService implements IChiTietSanPhamService {

    @Autowired
    private IChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<ChiTietSanPhamDtoResponse> getAllBySanPhamMa(String maSP) {
        return chiTietSanPhamRepository.getAllBySanPhamMaAndTrangThai(maSP,true).stream()
                .map(c -> new ChiTietSanPhamDtoResponse(c)).collect(Collectors.toList());
    }

    public ChiTietSanPhamDtoResponse updateSoLuong(ChiTietSanPhamDtoRequest request) {
        ChiTietSanPhamModel model = chiTietSanPhamRepository.updateSoLuong(request.getSoLuong(), request.getId());
        return new ChiTietSanPhamDtoResponse(model);
    }

    public Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size) {
        return chiTietSanPhamRepository.existsBySanPhamMaAndSizeMa(maSP, size);
    }

    public ChiTietSanPhamDtoResponse save(ChiTietSanPhamDtoRequest entity) {

        ChiTietSanPhamModel model = null;

        if(existsBySanPhamMaAndSizeMa(entity.getSanPham(),entity.getSize())){
            model = chiTietSanPhamRepository.getBySanPhamMaAndSizeMa(entity.getSanPham(),entity.getSize());
            model.setTrangThai(true);
            model.setSoLuong(entity.getSoLuong());
            chiTietSanPhamRepository.save(model);
            return new ChiTietSanPhamDtoResponse(model);
        }

        model = entity.mapToModel();
        chiTietSanPhamRepository.save(model);
        return new ChiTietSanPhamDtoResponse(model);

    }

    public ChiTietSanPhamDtoResponse update(ChiTietSanPhamDtoRequest entity){
        ChiTietSanPhamModel model = entity.mapToModel();
        model = chiTietSanPhamRepository.updateSoLuong(model.getSoLuong(),model.getId());
        return new ChiTietSanPhamDtoResponse(model);
    }

    public void delete(String id){
        ChiTietSanPhamModel model = chiTietSanPhamRepository.findById(id).get();
        if(model.kiemTraCoTrongDonHang()){
            chiTietSanPhamRepository.updateTrangThai(false,model.getId());
        }

        chiTietSanPhamRepository.deleteById(id);
    }
}
