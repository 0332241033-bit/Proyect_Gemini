package geminiAPP.controller;

import geminiAPP.entity.user;
import geminiAPP.service.pdfUserService;
import geminiAPP.service.userServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pdf/user")
@RequiredArgsConstructor
public class pdfUserController {
    private final pdfUserService pdfService;
    private final userServiceIMPL userService;

    @PostMapping("/read")
    public ResponseEntity<String> readPdf(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(pdfService.readPdf(file));
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateUserPdf() {
        byte[] pdfBytes = pdfService.generateUserPdf();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuarios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/add")
    public ResponseEntity<user> createUser(@RequestBody user user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<user>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }
}
