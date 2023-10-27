package fpoly.duantotnghiep.shoppingweb.service.impl;

import fpoly.duantotnghiep.shoppingweb.config.security.AdminUser;
import fpoly.duantotnghiep.shoppingweb.config.security.AdminUsers;
import fpoly.duantotnghiep.shoppingweb.model.KhachHangModel;
import fpoly.duantotnghiep.shoppingweb.repository.IKhachHangRepository;
import fpoly.duantotnghiep.shoppingweb.repository.INhanVienRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private IKhachHangRepository khachHangRepository;

    public UserService(IKhachHangRepository khachHangRepository) {
        this.khachHangRepository = khachHangRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KhachHangModel khachHangModel = khachHangRepository.findById(username).orElse(null);
        if(khachHangModel==null){
            throw new UsernameNotFoundException("Username không tồn tại");
        }
        return new AdminUsers(khachHangModel);
    }
}
