package fpoly.duantotnghiep.shoppingweb.util;

import fpoly.duantotnghiep.shoppingweb.ShoppingwebApplication;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.TaiKhoanDtoResponse;
import fpoly.duantotnghiep.shoppingweb.model.TaiKhoanModel;
import fpoly.duantotnghiep.shoppingweb.model.ThongBaoModel;
import fpoly.duantotnghiep.shoppingweb.model.ThongBaoNhanModel;
import fpoly.duantotnghiep.shoppingweb.service.ITaiKhoanService;
import fpoly.duantotnghiep.shoppingweb.service.IThongBaoNhanService;
import fpoly.duantotnghiep.shoppingweb.service.IThongBaoService;
import fpoly.duantotnghiep.shoppingweb.service.impl.TaiKhoanServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.ThongBaoNhanServiceImpl;
import fpoly.duantotnghiep.shoppingweb.service.impl.ThongBaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocketUtil {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ITaiKhoanService taiKhoanService;
    @Autowired
    private IThongBaoService thongBaoService;
    @Autowired
    private IThongBaoNhanService thongBaoNhanService;

    private static SimpMessagingTemplate staticSimpMessagingTemplate;
    private static ITaiKhoanService staticTaiKhoanService;
    private static IThongBaoService staticThongBaoService;
    private static IThongBaoNhanService staticThongBaoNhanService;

    public static void sendNotification(ThongBaoModel thongBaoModel) {

        List<TaiKhoanDtoResponse> danhSachTaiKhoanHasRoleAdmin = staticTaiKhoanService.getByRoleAdmin();

        List<ThongBaoNhanModel> thongBaoNhanModels = new ArrayList<>();
        staticThongBaoService.save(thongBaoModel);

        for (TaiKhoanDtoResponse t : danhSachTaiKhoanHasRoleAdmin) {
            TaiKhoanModel taiKhoan = staticTaiKhoanService.findById(t.getId());
            thongBaoNhanModels.add(new ThongBaoNhanModel(null, thongBaoModel, taiKhoan, false));
        }


        thongBaoNhanModels = staticThongBaoNhanService.saveAll(thongBaoNhanModels);

        thongBaoNhanModels.forEach(t -> {
            staticSimpMessagingTemplate.convertAndSend("/" + t.getNguoiNhan().getId(), t);
        });

    }

    ////////////////////????//////////////////////////////////////////////////////////////////////////////

    @Autowired
    public void setStaticSimpMessagingTemplate(SimpMessagingTemplate staticSimpMessagingTemplate) {
        SocketUtil.staticSimpMessagingTemplate = staticSimpMessagingTemplate;
    }

    @Autowired
    public void setStaticTaiKhoanService(ITaiKhoanService staticTaiKhoanService) {
        SocketUtil.staticTaiKhoanService = staticTaiKhoanService;
    }

    @Autowired
    public void setStaticThongBaoService(IThongBaoService staticThongBaoService) {
        SocketUtil.staticThongBaoService = staticThongBaoService;
    }

    @Autowired
    public void setStaticThongBaoNhanService(IThongBaoNhanService staticThongBaoNhanService) {
        SocketUtil.staticThongBaoNhanService = staticThongBaoNhanService;
    }
}
