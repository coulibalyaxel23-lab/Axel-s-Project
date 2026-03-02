package com.example.Proposition.d.adhesion.BnB.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration des rapports Jasper (chemin, nom de fichier).
 */
@Configuration
@ConfigurationProperties(prefix = "app.report")
@Getter
@Setter
public class ReportConfig {

    /**
     * Chemin du rapport principal (sous classpath:resources), ex: reports/BBPD.jrxml
     */
    private String bulletinPath = "reports/BBPD.jrxml";

    /**
     * Nom du fichier PDF généré en téléchargement.
     */
    private String bulletinPdfFilename = "bulletin-adhesion-BBPD.pdf";
}
