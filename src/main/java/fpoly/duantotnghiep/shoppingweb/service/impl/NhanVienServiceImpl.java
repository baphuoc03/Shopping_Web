package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.NhanVienDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.NhanVienDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.NhanVienModel;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import fpoly.duantotnghiep.shoppingweb.service.INhanVienService;
import fpoly.duantotnghiep.shoppingweb.util.EmailUtil;
import fpoly.duantotnghiep.shoppingweb.util.ImgUtil;
import fpoly.duantotnghiep.shoppingweb.util.RandomUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl implements INhanVienService {

    @Autowired
    private INhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVienDtoResponse> getAll() {
        return nhanVienRepository.findAll().stream().map(n -> new NhanVienDtoResponse(n)).collect(Collectors.toList());
    }

    @Override
    public NhanVienDtoResponse findById(String username) {
        return new NhanVienDtoResponse(nhanVienRepository.findById(username).get());
    }

    @Override
    public Boolean existsByUsername(String username){
        return nhanVienRepository.existsById(username);
    }

    @Override
    public NhanVienDtoResponse add(NhanVienDtoRequest nhanVien) throws MessagingException {
        nhanVien.setPassword(RandomUtil.randomPassword());
        EmailUtil.sendEmail(nhanVien.getEmail(),"Thông Tin Tài Khoản","Thông tin tài khoản: \nUsername: "+nhanVien.getUsername()+"\nPassword: "+nhanVien.getPassword());
        NhanVienModel nhanVienModel = nhanVienRepository.save(nhanVien.mapToModel());
        return new NhanVienDtoResponse(nhanVienModel);
    }

    @Override
    public NhanVienDtoResponse update(NhanVienDtoRequest nhanVien) {
        return null;
    }

    @Override
    public NhanVienDtoResponse update(NhanVienDtoRequest nhanVien, MultipartFile img) throws IOException {

        NhanVienModel nhanVienDefault = nhanVienRepository.findById(nhanVien.getUsername()).get();
        nhanVien.setPassword(nhanVienDefault.getPassword());
        nhanVien.setVaiTro(nhanVienDefault.getVaiTro().getMa());

        if(img==null) {
            if(nhanVienDefault.getAnhDaiDien()!=null) ImgUtil.deleteImg(nhanVienDefault.getAnhDaiDien(),"user");
            nhanVien.setAnhDaiDien(null);
        }else{
            if(!img.getOriginalFilename().equalsIgnoreCase(nhanVien.getUsername())){//add ảnh
                byte[] bytes = img.getBytes();
                String fileName = img.getOriginalFilename();
                String name = nhanVien.getUsername() + fileName.substring(fileName.lastIndexOf("."));
                Path path = Paths.get("src/main/resources/images/user/" + name);
                Files.write(path, bytes);
                nhanVien.setAnhDaiDien(name);
            }else{
                nhanVien.setAnhDaiDien(img.getOriginalFilename());
            }
        }

        NhanVienModel nhanVienModel = nhanVienRepository.save(nhanVien.mapToModel());
        return new NhanVienDtoResponse(nhanVienModel);
    }

}
