package geminiAPP.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import geminiAPP.entity.food;
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
public class pdfFoodService {
    private final foodServiceIMPL foodService;

    /**
     * Lee el contenido de un PDF usando PDFBox.
     *
     * @param file archivo PDF subido
     * @return contenido extraído
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
     * Genera un PDF dinámico con la lista de comidas.
     *
     * @return archivo PDF en bytes
     */
    public byte[] generatePdf() {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // ======= TÍTULO =======
            Paragraph title = new Paragraph("📊 Reporte de Comidas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Fecha
            String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            Paragraph date = new Paragraph("Generado el: " + fecha,
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Font.ITALIC));
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);

            document.add(Chunk.NEWLINE); // Espacio

            // ======= TABLA =======
            PdfPTable table = new PdfPTable(5); // 5 columnas
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 2, 2, 2, 4}); // proporciones de columnas
            table.setSpacingBefore(10);

            // Cabecera
            String[] headers = {"ID", "Imagen", "Nombre", "Calorías", "Descripción"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header,
                        FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Datos
            List<food> foods = foodService.getAllFood();
            for (food f : foods) {
                table.addCell(String.valueOf(f.getId()));

                PdfPCell imgCell = new PdfPCell(new Phrase(f.getImage()));
                imgCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(imgCell);

                PdfPCell nameCell = new PdfPCell(new Phrase(f.getName()));
                nameCell.setPadding(5);
                table.addCell(nameCell);

                PdfPCell calCell = new PdfPCell(new Phrase(String.valueOf(f.getCalories())));
                calCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(calCell);

                PdfPCell descCell = new PdfPCell(new Phrase(f.getDescription()));
                descCell.setPadding(5);
                table.addCell(descCell);
            }

            document.add(table);

            // ======= PIE DE PÁGINA =======
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
