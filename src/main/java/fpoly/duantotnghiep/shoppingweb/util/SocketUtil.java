package fpoly.duantotnghiep.shoppingweb.util;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class SocketUtil {

    @Autowired
    static SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private static ITaiKhoanService taiKhoanService;
    @Autowired
    private static IThongBaoService thongBaoService;
    @Autowired
    private static IThongBaoNhanService thongBaoNhanService;

    public static void sendNotification(ThongBaoModel thongBaoModel) {

        List<TaiKhoanDtoResponse> danhSachTaiKhoanHasRoleAdmin = taiKhoanService.getByRoleAdmin();

        List<ThongBaoNhanModel> thongBaoNhanModels = new ArrayList<>();
        thongBaoService.save(thongBaoModel);

        for (TaiKhoanDtoResponse t : danhSachTaiKhoanHasRoleAdmin) {
            TaiKhoanModel taiKhoan = taiKhoanService.findById(t.getId());
            thongBaoNhanModels.add(new ThongBaoNhanModel(null, thongBaoModel, taiKhoan, false));
        }


        thongBaoNhanModels = thongBaoNhanService.saveAll(thongBaoNhanModels);

        thongBaoNhanModels.forEach(t -> {
            simpMessagingTemplate.convertAndSend("/topic/" + t.getNguoiNhan().getId(), t);
        });

    }

}
