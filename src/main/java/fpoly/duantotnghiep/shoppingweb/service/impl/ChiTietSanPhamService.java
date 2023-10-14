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
        return chiTietSanPhamRepository.getAllBySanPhamMaAndTrangThaiOrderBySizeMa(maSP,true).stream()
                .map(c -> new ChiTietSanPhamDtoResponse(c)).collect(Collectors.toList());
    }

    @Override
    public ChiTietSanPhamDtoResponse updateSoLuong(ChiTietSanPhamDtoRequest request) {
        chiTietSanPhamRepository.updateSoLuong(request.getSoLuong(), request.getId());
        ChiTietSanPhamModel model = chiTietSanPhamRepository.findById(request.getId()).get();
        return new ChiTietSanPhamDtoResponse(model);
    }

    @Override
    public Boolean existsBySanPhamMaAndSizeMa(String maSP, Float size) {
        return chiTietSanPhamRepository.existsBySanPhamMaAndSizeMa(maSP, size);
    }

    @Override
    public ChiTietSanPhamDtoResponse save(ChiTietSanPhamDtoRequest entity) {

        ChiTietSanPhamModel model = null;

        if(existsBySanPhamMaAndSizeMa(entity.getSanPham(),entity.getSize())){
            model = chiTietSanPhamRepository.getBySanPhamMaAndSizeMa(entity.getSanPham(),entity.getSize());
            model.setSoLuong(entity.getSoLuong());
            model.setTrangThai(true);
            chiTietSanPhamRepository.save(model);
            return new ChiTietSanPhamDtoResponse(model);
        }

        model = entity.mapToModel();
        model.setTrangThai(true);
        chiTietSanPhamRepository.save(model);
        return new ChiTietSanPhamDtoResponse(model);

    }

    @Override
    public ChiTietSanPhamDtoResponse update(ChiTietSanPhamDtoRequest entity){
        ChiTietSanPhamModel model = entity.mapToModel();
        chiTietSanPhamRepository.updateSoLuong(model.getSoLuong(),model.getId());
        model = chiTietSanPhamRepository.findById(model.getId()).get();
//        model = chiTietSanPhamRepository.save(model);
        return new ChiTietSanPhamDtoResponse(model);
    }

    @Override
    public void delete(String id){
        ChiTietSanPhamModel model = chiTietSanPhamRepository.findById(id).get();
        if(model.kiemTraCoTrongDonHang()){
            chiTietSanPhamRepository.updateTrangThai(false,model.getId());
        }

        chiTietSanPhamRepository.deleteById(id);
    }

    @Override
    public List<ChiTietSanPhamDtoResponse> saveAll(List<Float> sizes,ChiTietSanPhamDtoRequest model){

        List<ChiTietSanPhamDtoRequest> etitys = sizes.stream().map(s -> {
            model.setSize(s);
            return model;
        }).collect(Collectors.toList());

        return etitys.stream().map(e -> save(e)).collect(Collectors.toList());
    }

    @Override
    public Boolean existsById(String id){
        return chiTietSanPhamRepository.existsById(id);
    }
}
