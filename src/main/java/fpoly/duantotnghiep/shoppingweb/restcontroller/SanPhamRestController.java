package fpoly.duantotnghiep.shoppingweb.restcontroller;

import fpoly.duantotnghiep.shoppingweb.enumtype.ThongBaoType;
import fpoly.duantotnghiep.shoppingweb.model.SanPhamModel;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import fpoly.duantotnghiep.shoppingweb.model.ThongBaoModel;
import fpoly.duantotnghiep.shoppingweb.service.ISanPhamService;
import fpoly.duantotnghiep.shoppingweb.service.ITaiKhoanService;
import fpoly.duantotnghiep.shoppingweb.util.SocketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SanPhamRestController {

    @Autowired
    private ITaiKhoanService service;

    @PostMapping("add")
    @Transactional(rollbackFor =  {Exception.class, Throwable.class})
    public SanPhamModel add(){
        SanPhamModel sanPhamModel = new SanPhamModel();

        service.getByRoleAdmin().forEach(t -> System.out.println(t.getId()+" - "+t.getVaiTro()));

        sanPhamModel.setMa("111");
        sanPhamModel.setTen("san pham 1");

        TaiKhoanModel taiKhoanModel = new TaiKhoanModel();
        taiKhoanModel.setId("afc0b6cc-4c66-11ee-b10b-d69e940a783b");

        SocketUtil.sendNotification(new ThongBaoModel(null,taiKhoanModel, ThongBaoType.Add.name(),"Thêm mới sản phẩm",new Date(),"aa"));

        return sanPhamModel;
    }

}
