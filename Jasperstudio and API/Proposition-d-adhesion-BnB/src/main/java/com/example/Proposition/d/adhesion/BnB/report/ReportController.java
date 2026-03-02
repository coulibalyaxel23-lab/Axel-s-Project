package com.example.Proposition.d.adhesion.BnB.report;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * API REST pour la génération du bulletin d'adhésion (PDF).
 * Accepte n'importe quel JSON : chaque clé est envoyée comme paramètre au rapport Jasper.
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Génère le PDF à partir des données envoyées en JSON (dynamique).
     * Toutes les clés du body sont passées au rapport ; ajoutez ou retirez des champs sans changer le code.
     */
    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateReport(@RequestBody Map<String, Object> body) {
        byte[] pdf = reportService.generateBulletinPdf(body != null ? body : Map.of());
        String filename = reportService.getBulletinPdfFilename();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdf);
    }
}
