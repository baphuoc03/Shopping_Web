package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhachHangDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhachHangDTORequest;
import fpoly.duantotnghiep.shoppingweb.model.DiaChiModel;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.service.IKhachHangService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.RandomUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhachHangServiceImpl implements IKhachHangService {
    @Autowired
    IKhachHangRepository khachHangRepository;

    @Override
    public Page<KhachHangDtoResponse> getAll(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<KhachHangModel> pageModel = khachHangRepository.findAll(pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(k -> new KhachHangDtoResponse(k)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());
    }

    @Override
    public Page<KhachHangDtoResponse> search(String keyWord, Integer page, Integer limit){
        Pageable pageable = PageRequest.of(page, limit);
        Page<KhachHangModel> pageModel = khachHangRepository.search(keyWord,pageable);

        return new PageImpl<>(pageModel.getContent().stream().map(k -> new KhachHangDtoResponse(k)).collect(Collectors.toList()),
                pageable, pageModel.getTotalElements());
    }

    @Override
    public List<KhachHangDtoResponse> getAllFromVoucher() {
        return khachHangRepository.findAll().stream().map(x -> new KhachHangDtoResponse(x)).collect(Collectors.toList());
    }

    @Override
    public KhachHangDtoResponse findById(String username) {
        return new KhachHangDtoResponse(khachHangRepository.findById(username).get());
    }

    @Override
    public Boolean exsistsByUsername(String username) {
        return khachHangRepository.existsById(username);
    }

    @Override
    public KhachHangDtoResponse add(KhachHangDTORequest khachHang) throws MessagingException {
        KhachHangModel khachHangModel = khachHangRepository.save(khachHang.mapToModel());
        return new KhachHangDtoResponse(khachHangModel);
    }

    @Override
    public KhachHangDtoResponse update(KhachHangDTORequest khachHang) {
        KhachHangModel khachHangDefault = khachHangRepository.findById(khachHang.getUsername()).get();
        khachHang.setPassword(khachHangDefault.getPassword());
        khachHang.setAnhDaiDien(khachHangDefault.getAnhDaiDien());
        KhachHangModel khachHangModel = khachHangRepository.save(khachHang.mapToModel());
        return new KhachHangDtoResponse(khachHangModel);
    }

    @Override
    public KhachHangDtoResponse update(KhachHangDTORequest khachHang, MultipartFile img) throws IOException {
        return null;
    }

    @Override
    public void deleteByUsername(String username) {
        khachHangRepository.deleteById(username);
    }

    @Override
    public List<KhachHangModel> findByUserNameIn(List<String> maKhachHang) {
        return khachHangRepository.findByUsernameIn(maKhachHang);
    }

    @Override
    public List<DiaChiModel> diaChiByTaiKhoan(String taiKhoan) {
        return khachHangRepository.findAllDiaChiByTaiKhoan(taiKhoan);
    }

}
