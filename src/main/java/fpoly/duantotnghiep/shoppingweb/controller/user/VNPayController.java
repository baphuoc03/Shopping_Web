package fpoly.duantotnghiep.shoppingweb.controller.user;

import fpoly.duantotnghiep.shoppingweb.model.DonHangModel;
import fpoly.duantotnghiep.shoppingweb.service.impl.VnPayServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VNPayController {
//    @Autowired
//    private VnPayServiceImpl vnPayService;
//    @GetMapping("/vnpay-payment")
//    public String GetMappings(HttpServletRequest request, Model model, DonHangModel donHangModel){
//        int paymentStatus =vnPayService.orderReturn(request,donHangModel.getDiaChiChiTiet());
//        String totalPrice = request.getParameter("vnp_Amount");
//        model.addAttribute("totalPrice", totalPrice);
//        return paymentStatus == 1 ? "user/ordersuccess" : "user/orderfail";
//    }
}
