package com.example.Proposition.d.adhesion.BnB.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la génération du bulletin d'adhésion BnB (BBPD).
 * Correspond aux paramètres du rapport Jasper BBPD.jrxml.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulletinAdhesionDto {

    // Informations personnelles
    private String nom;
    private String dateNaissance;
    private String adresseDomicile;
    private String telephone;
    @Email(message = "Email invalide")
    private String email;
    private String nationalite;
    private String typePieceIdentite;
    private String numeroPieceIdentite;
    private String profession;

    // Couvertures
    private String capitalAssure;
    private String dureeContrat;
    private String nombreCotisantsMinimum;

    // Primes et paiement
    private String dateEffet;
    private String primePayer;
    private String frequencePaiement;
    private String modePaiement;
    private String optionVersement;
    private String numeroTelephoneMobile;
    private String operateurMobile;

    // Virement bancaire
    private String numeroBanque;
    private String nomBanque;
    private String adresseBanque;

    // Consentements
    private Boolean jeConsens1;
    private Boolean jeConsens2;

    // Signature et date
    private String signature;
    private String date;
}
