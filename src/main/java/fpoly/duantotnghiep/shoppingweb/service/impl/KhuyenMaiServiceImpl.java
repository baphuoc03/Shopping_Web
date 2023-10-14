package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.KhuyenMaiResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SanPhamDtoResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.VoucherReponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.KhuyenMaiRequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SanPhamDtoRequest;
import fpoly.duantotnghiep.shoppingweb.model.KhuyenMaiModel;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.VoucherModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.repository.KhuyenMaiRepository;
import fpoly.duantotnghiep.shoppingweb.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {
    @Autowired
    KhuyenMaiRepository repository;
    @Autowired
    ISanPhamRepository sanPhamRepository;

    @Override
    public Page<KhuyenMaiResponse> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<KhuyenMaiModel> pageModel = repository.findAll(pageable);
        return pageModel.map(x -> new KhuyenMaiResponse(x));
    }


    @Override
    public KhuyenMaiResponse findById(String id) {
        KhuyenMaiModel getById = repository.findById(id).get();
        return new KhuyenMaiResponse(getById);
    }

    @Override
    public void save(KhuyenMaiRequest khuyenMai) {
        repository.save(khuyenMai.mapToModel());
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGiamGiaWithNgayBD() {
        for (var i : repository.findAllSanPhamWithKmWhereNgayBatDau()) {
            for (var j : i.getSanPham()) {
                BigDecimal giaUpdate = null;
                if (i.getLoai().equals("TIEN")) {
                    giaUpdate = j.getGiaBan().subtract(i.getMucGiam());
                } else if (i.getLoai().equals("PHAN TRAM")) {
                    giaUpdate = j.getGiaBan().subtract(j.getGiaBan().multiply(i.getMucGiam().divide(new BigDecimal("100"))));
                }
                SanPhamModel sanPham = sanPhamRepository.findById(j.getMa()).get();
                sanPham.setMa(j.getMa());
                sanPham.setGiaBan(giaUpdate);
                sanPhamRepository.save(sanPham);
            }
        }
        System.out.println("Thành công");
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updateGiamGiaWithNgayKT() {
        for (var i : repository.findAllSanPhamWithKmWhereNgayKetThuc()) {
            for (var j : i.getSanPham()) {
                BigDecimal gia = new BigDecimal("2000000");
                SanPhamModel sanPham = sanPhamRepository.findById(j.getMa()).get();
                sanPham.setMa(j.getMa());
                sanPham.setGiaBan(gia);
                sanPhamRepository.save(sanPham);
            }
        }
        System.out.println("Thành công");
    }
}
