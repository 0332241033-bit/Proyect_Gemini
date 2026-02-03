package geminiAPP.controller;

import geminiAPP.entity.food;
import geminiAPP.service.pdfFoodService;
import geminiAPP.service.foodServiceIMPL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/pdf/food")
@RequiredArgsConstructor
public class pdfFoodController {
    private final pdfFoodService pdfFoodService;
    private final foodServiceIMPL foodService;

    /**
     * Endpoint para leer un PDF subido y devolver su texto.
     */
    @PostMapping("/read")
    public ResponseEntity<String> readPdf(@RequestParam("file") MultipartFile file) {
        String contenido = pdfFoodService.readPdf(file);
        return ResponseEntity.ok(contenido);
    }

    /**
     * Endpoint para generar un PDF con datos de usuarios.
     */
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf() {
        byte[] pdfBytes = pdfFoodService.generatePdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=usuarios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    /**
     * Endpoint auxiliar para crear usuarios de prueba.
     */
    @PostMapping("/user")
    public ResponseEntity<food> createUser(@RequestBody food user) {
        return ResponseEntity.ok(foodService.addFood(user));
    }

    /**
     * Endpoint auxiliar para listar usuarios.
     */
    @GetMapping("/users")
    public ResponseEntity<List<food>> getUsers() {
        return ResponseEntity.ok(foodService.getAllFood());
    }
}
