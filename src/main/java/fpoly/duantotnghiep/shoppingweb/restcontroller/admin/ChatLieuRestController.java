package fpoly.duantotnghiep.shoppingweb.restcontroller.admin;

import fpoly.duantotnghiep.shoppingweb.dto.reponse.ChatLieuDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.reponse.SizeDTOResponse;
import fpoly.duantotnghiep.shoppingweb.dto.request.ChatLieuDTORequest;
import fpoly.duantotnghiep.shoppingweb.dto.request.SizeDTORequest;
import fpoly.duantotnghiep.shoppingweb.service.IChatLieuService;
import fpoly.duantotnghiep.shoppingweb.service.ISizeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("${admin.domain}/chat-lieu")
public class ChatLieuRestController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IChatLieuService service;

    @GetMapping("find-all")
    public List<ChatLieuDTOResponse> findAll(){
        return service.findAll();
    }

    @PostMapping("")
    public ResponseEntity<?> add(@RequestBody ChatLieuDTORequest chatLieu) throws IOException {
        service.save(chatLieu);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@RequestBody ChatLieuDTORequest chatLieu, @PathVariable String id){
        chatLieu.setId(id);
        service.save(chatLieu);
        return ResponseEntity.ok().build();
    }
}
