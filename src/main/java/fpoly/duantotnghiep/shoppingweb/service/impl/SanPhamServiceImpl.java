package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.repository.ISanPhamRepository;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SanPhamServiceImpl implements ISanPhamService {
    @Autowired
    private ISanPhamRepository sanPhamRepository;


    @Override
    public boolean existsById(String s) {
        return sanPhamRepository.existsById(s);
    }

    @Override
    public void deleteById(String s) {
        sanPhamRepository.deleteById(s);
    }
}
