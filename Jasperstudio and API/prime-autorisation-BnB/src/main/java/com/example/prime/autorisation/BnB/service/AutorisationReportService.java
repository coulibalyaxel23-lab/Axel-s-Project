package com.example.prime.autorisation.BnB.service;

import com.example.prime.autorisation.BnB.dto.AutorisationRequest;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AutorisationReportService {

    private static final String TEMPLATE_PATH = "/APB.jrxml";

    public byte[] generateReport(AutorisationRequest request) {
        File tempSignatureFile = null;
        try (InputStream templateStream = getClass().getResourceAsStream(TEMPLATE_PATH)) {
            if (templateStream == null) {
                throw new IllegalStateException("Modèle Jasper introuvable à l'emplacement : " + TEMPLATE_PATH);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("nomPrenoms", request.getNomPrenoms());
            parameters.put("codeAgent", request.getCodeAgent());
            parameters.put("numeroCel", request.getNumeroCel());
            parameters.put("adresseMail", request.getAdresseMail());
            parameters.put("numeroPolice", request.getNumeroPolice());
            parameters.put("lieuFait", request.getLieuFait());
            parameters.put("jourFait", request.getJourFait());
            parameters.put("moisFait", request.getMoisFait());
            parameters.put("anneeFait", request.getAnneeFait());
            parameters.put("jeConsens", request.getJeConsens());
            
            // Traiter la signature - télécharger et sauvegarder localement
            if (request.getSignature() != null && !request.getSignature().isEmpty()) {
                try {
                    URL imageUrl = new URL(request.getSignature().trim());
                    URLConnection connection = imageUrl.openConnection();
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    
                    InputStream imageStream = connection.getInputStream();
                    tempSignatureFile = File.createTempFile("signature_", ".tmp");
                    try (FileOutputStream fos = new FileOutputStream(tempSignatureFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = imageStream.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                    imageStream.close();
                    
                    parameters.put("signature", tempSignatureFile.getAbsolutePath());
                } catch (Exception e) {
                    System.err.println("Erreur téléchargement signature: " + e.getMessage());
                    parameters.put("signature", null);
                }
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    new JREmptyDataSource()
            );

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du rapport Jasper", e);
        } finally {
            // Nettoyer le fichier temporaire
            if (tempSignatureFile != null && tempSignatureFile.exists()) {
                tempSignatureFile.delete();
            }
        }
    }
}