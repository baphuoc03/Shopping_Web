package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.MauSacDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.MauSacDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IMauSacService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController

public class MauSacRestController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IMauSacService service;

    @GetMapping("find-all")
    public List<MauSacDTOResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("view-alll")
    public String viewAdd(@ModelAttribute("mauSac") MauSacDTORequest mauSac){
        request.setAttribute("method","add");
        return "test";
    }
    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody MauSacDTORequest mauSac) throws IOException {
        System.out.println(mauSac.mapToModel().toString());
        return  ResponseEntity.ok(service.save(mauSac));
    }

}
