package com.example.Proposition.d.adhesion.BnB.report;

import com.example.Proposition.d.adhesion.BnB.config.ReportConfig;
import com.example.Proposition.d.adhesion.BnB.dto.BulletinAdhesionDto;
import com.example.Proposition.d.adhesion.BnB.exception.ReportGenerationException;
import com.example.Proposition.d.adhesion.BnB.mapper.BulletinAdhesionMapper;
import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class ReportService {

    private final ReportConfig reportConfig;

    public ReportService(ReportConfig reportConfig) {
        this.reportConfig = reportConfig;
    }

    /**
     * Génère le PDF à partir de données dynamiques (n'importe quel JSON).
     * Chaque clé du map est passée comme paramètre au rapport Jasper.
     */
    public byte[] generateBulletinPdf(Map<String, Object> data) {
        Map<String, Object> params = BulletinAdhesionMapper.toReportParametersFromMap(data);
        return fillAndExport(params);
    }

    /**
     * Génère le PDF à partir du DTO (compatibilité).
     */
    public byte[] generateBulletinPdf(BulletinAdhesionDto dto) {
        Map<String, Object> params = BulletinAdhesionMapper.toReportParameters(dto);
        return fillAndExport(params);
    }

    private byte[] fillAndExport(Map<String, Object> params) {
        try {
            InputStream reportStream = new ClassPathResource(reportConfig.getBulletinPath()).getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportGenerationException("Impossible de générer le bulletin: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ReportGenerationException("Erreur lors de la génération du rapport: " + e.getMessage(), e);
        }
    }

    public String getBulletinPdfFilename() {
        return reportConfig.getBulletinPdfFilename();
    }
}
