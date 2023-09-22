package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.AnhModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import fpoly.duantotnghiep.shoppingweb.util.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements ISanPhamService {
    @Autowired
    private ISanPhamRepository sanPhamRepository;
    @Autowired
    private AnhServiceImpl anhService;

    @Override
    public List<SanPhamDtoResponse> findAll() {
        return sanPhamRepository.findAll().stream()
                .filter(s -> s.getTrangThai() == true)
                .map(s -> new SanPhamDtoResponse(s))
                .collect(Collectors.toList());
    }

    @Override
    public SanPhamDtoResponse findByMa(String ma) {
        SanPhamModel sanPhamModel = sanPhamRepository.findById(ma).get();
        return new SanPhamDtoResponse(sanPhamModel);
    }

    @Override
    public SanPhamDtoRequest findDtoRequetsByMa(String ma) {
        SanPhamModel sanPhamModel = sanPhamRepository.findById(ma).get();
        return new SanPhamDtoRequest(sanPhamModel);
    }

    @Override
    public List<SanPhamDtoResponse> saveAll(List<SanPhamDtoRequest> sanPham) {

        List<SanPhamModel> entities = sanPham.stream().map(s -> s.mapToModel()).collect(Collectors.toList());

        entities = sanPhamRepository.saveAll(entities);

        return entities.stream().map(s -> new SanPhamDtoResponse(s)).collect(Collectors.toList());
    }

    @Override
    public List<SanPhamModel> findListById(List<String> ma) {
        List<SanPhamModel> listSanPhamModels = sanPhamRepository.findAllById(ma);
        return listSanPhamModels;
    }

    @Override
    public SanPhamDtoResponse save(SanPhamDtoRequest entity) {
        SanPhamModel model = entity.mapToModel();
        List<AnhModel> imgs = model.getImages();
        model.setTrangThai(true);
        model = sanPhamRepository.save(model);
        anhService.saveAll(imgs);
        return new SanPhamDtoResponse(model);
    }

    @Override
    public SanPhamDtoResponse update(SanPhamDtoRequest entity) throws IOException {
        ProductUtil.deleteImg(findDtoRequetsByMa(entity.getMa()).getAnh());

        SanPhamModel model = entity.mapToModel();
        anhService.deleteBySanPham(model);
        anhService.saveAll(model.getImages());
        model.setTrangThai(true);
        model = sanPhamRepository.save(model);
        return new SanPhamDtoResponse(model);
    }

    @Override
    public boolean existsById(String s) {
        return sanPhamRepository.existsById(s);
    }

    @Override
    public void deleteById(String s) throws IOException {

        SanPhamModel model = sanPhamRepository.findById(s).get();
        Boolean checkCTSPInSanPham = model.getCtsp().stream().allMatch(c -> c.kiemTraCoTrongDonHang() == false);
        ProductUtil.deleteImg(model.getImages().stream().map(img -> img.getTen()).collect(Collectors.toList()));
        if (model.getCtsp().size() == 0 || checkCTSPInSanPham == true) {
            anhService.deleteBySanPham(model);
            sanPhamRepository.deleteById(s);
        } else {
            model.setTrangThai(false);
            sanPhamRepository.save(model);
        }
//        anhService.deleteBySanPham(model);

    }

    @Override
    public Integer updateTrangThaiHIenThi(Boolean trangThai, String ma) {
        return sanPhamRepository.updateTrangThaiHIenThi(trangThai, ma);
    }
}
