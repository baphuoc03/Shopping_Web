package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.filter.SanPhamDtoFilter;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.entitymanager.SanPhamEntityManager;
import fpoly.duantotnghiep.shoppingweb.model.AnhModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDongSanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import fpoly.duantotnghiep.shoppingweb.util.ImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements ISanPhamService {
    @Autowired
    private ISanPhamRepository sanPhamRepository;

    @Autowired
    private AnhServiceImpl anhService;
    @Autowired
    private SanPhamEntityManager sanPhamEntityManager;
    @Autowired
    private IDongSanPhamRepository dongSanPhamRepository;

    @Override
    public List<SanPhamDtoResponse> findAll() {
        return sanPhamRepository.findAll().stream()
                .filter(s -> s.getTrangThai() == true)
                .map(s -> new SanPhamDtoResponse(s))
                .collect(Collectors.toList());
    }
    @Override
    public Page<SanPhamDtoResponse> pagination(Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page,limit);
        List<SanPhamDtoResponse> pageContent = sanPhamRepository.findAll().stream()
                                                .filter(s -> s.getTrangThai() == true)
                                                .map(s -> new SanPhamDtoResponse(s))
                                                .collect(Collectors.toList());
        Page<SanPhamDtoResponse> pageDto = new PageImpl<>(pageContent.stream().skip(pageable.getOffset()).limit(limit).collect(Collectors.toList())
                                                        ,pageable,pageContent.size());
        return pageDto;
    }

    @Override
    public Page<SanPhamDtoResponse> paginationInUser(Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page,limit);
        List<SanPhamDtoResponse> pageContent = sanPhamRepository.findAll().stream()
                .filter(s -> s.getTrangThai() == true)
                .filter(s -> s.getHienThi() == true)
                .map(s -> new SanPhamDtoResponse(s))
                .collect(Collectors.toList());
        Page<SanPhamDtoResponse> pageDto = new PageImpl<>(pageContent.stream().skip(pageable.getOffset()).limit(limit).collect(Collectors.toList())
                ,pageable,pageContent.size());
        return pageDto;
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
    public List<SanPhamModel> findByAllSanPhamWithKM() {
        return sanPhamRepository.findAllSanPhamWithKhuyenMai();
    }

    @Override
    public List<SanPhamModel> findAllWithKmWhereNgayBD() {
        return sanPhamRepository.findAllSanPhamWithKmWhereNgayBatDau();
    }

    @Override
    public SanPhamDtoResponse save(SanPhamDtoRequest entity) {
        SanPhamModel model = entity.mapToModel();
        model.setGiaNiemYet(entity.getGiaBan());
        List<AnhModel> imgs = model.getImages();
        model.setTrangThai(true);
        model = sanPhamRepository.save(model);
        anhService.saveAll(imgs);
        return new SanPhamDtoResponse(model);
    }

    @Override
    public SanPhamModel save1(SanPhamModel entity) {
        return sanPhamRepository.save(entity);
    }


    @Override
    public SanPhamDtoResponse update(SanPhamDtoRequest entity) throws IOException {
//        ImgUtil.deleteImg(findDtoRequetsByMa(entity.getMa()).getAnh(),"product");

        SanPhamModel model = entity.mapToModel();

        SanPhamModel sanPhamOld = sanPhamRepository.findById(model.getMa()).get();
            BigDecimal giamGia = sanPhamOld.getGiaBan().subtract(sanPhamOld.getGiaNiemYet());
            model.setGiaNiemYet(model.getGiaBan().subtract(giamGia));

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
        ImgUtil.deleteImg(model.getImages().stream().map(img -> img.getTen()).collect(Collectors.toList()),"product");
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

    @Override
    public Page<SanPhamDtoResponse> filter(SanPhamDtoFilter sanPhamDtoFilter,Integer pageNumber, Integer limt){
        return sanPhamEntityManager.filterMultipleProperties(sanPhamDtoFilter,pageNumber,limt);
    }
    public Integer updateGiaBan(BigDecimal giaBan, String ma) {
        return sanPhamRepository.updateGiaBan(giaBan, ma);
    }

    @Override
    public List<SanPhamDtoResponse> getSanPhamTuongTu(String ma){
        List<SanPhamDtoResponse> listSP = new ArrayList<>();
        SanPhamModel sanPhamModel = sanPhamRepository.findById(ma).orElse(null);

        if(sanPhamModel==null || sanPhamModel.getDongSanPham() == null) return listSP;

        listSP = dongSanPhamRepository.findById(sanPhamModel.getDongSanPham().getId()).get().getDanhSachSanPham()
                .stream()
//                .filter(s -> !s.getMa().equals(ma))
                .map(s -> new SanPhamDtoResponse(s)).collect(Collectors.toList());
        return listSP;
    }
}
