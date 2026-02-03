package geminiAPP.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import geminiAPP.entity.user;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class pdfUserService {

    private final userServiceIMPL userService;

    /**
     * Lee el contenido de un PDF usando PDFBox.
     */
    public String readPdf(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo PDF: " + e.getMessage(), e);
        }
    }

    /**
     * Genera un PDF con la lista de usuarios desde MongoDB (sin mostrar contraseñas).
     */
    public byte[] generateUserPdf() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // ======= TÍTULO =======
            Paragraph title = new Paragraph("📋 Reporte de Usuarios",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Fecha
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            Paragraph date = new Paragraph("Generado el: " + fecha,
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC));
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);

            document.add(Chunk.NEWLINE);

            // ======= TABLA =======
            PdfPTable table = new PdfPTable(3); // 👈 quitamos contraseña
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 3, 3}); // N°, Nombre, Usuario

            String[] headers = {"N°", "Nombre", "Usuario"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            List<user> users = userService.findAll();
            int contador = 1;
            for (user u : users) {
                table.addCell(String.valueOf(contador++));
                table.addCell(u.getName());
                table.addCell(u.getUsername());
            }

            document.add(table);

            // ======= PIE =======
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Reporte generado automáticamente por el sistema",
                    FontFactory.getFont(FontFactory.HELVETICA, 9, Font.ITALIC, Color.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF: " + e.getMessage(), e);
        }
    }
}
