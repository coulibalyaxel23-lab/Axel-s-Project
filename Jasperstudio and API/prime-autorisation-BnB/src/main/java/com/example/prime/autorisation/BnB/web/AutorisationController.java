package com.example.prime.autorisation.BnB.web;

import com.example.prime.autorisation.BnB.dto.AutorisationRequest;
import com.example.prime.autorisation.BnB.service.AutorisationReportService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autorisation-prelevement-prime-bnb")
public class AutorisationController {

    private final AutorisationReportService reportService;

    public AutorisationController(AutorisationReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(
            path = "/rapport",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<byte[]> genererRapport(@RequestBody AutorisationRequest request) {
        byte[] pdfBytes = reportService.generateReport(request);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.inline()
                        .filename("autorisation-prelevement-prime-bnb.pdf")
                        .build()
        );

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}

