package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.DanhSachYeuThichResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.DanhSachYeuThichRequest;
import fpoly.duantotnghiep.shoppingweb.model.DanhSachYeuThichModel;
import fpoly.duantotnghiep.shoppingweb.repository.IDanhSachYeuThichRepository;
import fpoly.duantotnghiep.shoppingweb.service.IDanhSachYeuThichService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DanhSachYeuThichServiceImpl implements IDanhSachYeuThichService {
    @Autowired
    IDanhSachYeuThichRepository repo;

    public List<DanhSachYeuThichResponse> findAll() {
        return repo.findAll().stream()
                .map(d -> new DanhSachYeuThichResponse(d))
                .collect(Collectors.toList());
    }

    public DanhSachYeuThichResponse save(DanhSachYeuThichRequest danhSachYeuThichRequest){
        DanhSachYeuThichModel model = repo.save(danhSachYeuThichRequest.maptomodel());
        return new DanhSachYeuThichResponse(model);
    }

    public DanhSachYeuThichResponse findById(String s) {
        DanhSachYeuThichModel model = repo.findById(s).get();
        return new DanhSachYeuThichResponse(model);
    }

    public boolean existsById(String s) {
        return repo.existsById(s);
    }

    public void deleteById(String s) {
        repo.deleteById(s);
    }

    public void deleteDanhSachYeuThich(String nguoiSoHuu,String sanPham){repo.deleteDanhSachYeuThichKKK(nguoiSoHuu,sanPham);}
}