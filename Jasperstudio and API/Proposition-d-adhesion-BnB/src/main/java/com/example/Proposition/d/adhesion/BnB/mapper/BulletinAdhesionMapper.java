package com.example.Proposition.d.adhesion.BnB.mapper;

import com.example.Proposition.d.adhesion.BnB.dto.BulletinAdhesionDto;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

/**
 * Mappe les données vers les paramètres du rapport Jasper.
 * Les champs sont entièrement dynamiques : tout ce qui est dans le JSON
 * est envoyé au rapport (clé = paramètre, type déduit de la valeur).
 */
public final class BulletinAdhesionMapper {

    private BulletinAdhesionMapper() {
    }

    /**
     * Convertit le JSON (Map) en paramètres Jasper de façon dynamique.
     * - Chaque clé du JSON = un paramètre du rapport.
     * - Type déduit de la valeur : Boolean → Boolean, sinon → String.
     * Aucune liste de champs figée : seuls les champs présents dans le JSON sont utilisés.
     */
    public static Map<String, Object> toReportParametersFromMap(Map<String, Object> data) {
        Map<String, Object> params = new HashMap<>();
        if (data == null || data.isEmpty()) {
            return params;
        }
        for (Map.Entry<String, Object> e : data.entrySet()) {
            String key = e.getKey();
            Object value = e.getValue();
            if (value instanceof Boolean) {
                params.put(key, value);
            } else if (isBooleanValue(value)) {
                params.put(key, toBoolean(value));
            } else {
                String strValue = toString(value);
                params.put(key, strValue);
                // Ajouter aussi le paramètre *Normalized (minuscules, sans accents, sans espaces)
                // utilisé par le JRXML pour les cases à cocher avec .contains()
                switch (key) {
                    case "frequencePaiement":
                    case "modePaiement":
                    case "optionVersement":
                    case "operateurMobile":
                        params.put(key + "Normalized", normalize(strValue));
                        break;
                    default:
                        break;
                }
            }
        }
        return params;
    }

    private static boolean isBooleanValue(Object v) {
        if (v == null) return false;
        String s = String.valueOf(v).trim().toLowerCase();
        return "true".equals(s) || "false".equals(s) || "1".equals(s) || "0".equals(s)
                || "oui".equals(s) || "non".equals(s) || "yes".equals(s) || "no".equals(s);
    }

    /**
     * Mappe le DTO bulletin d'adhésion vers les paramètres du rapport (compatibilité).
     */
    public static Map<String, Object> toReportParameters(BulletinAdhesionDto dto) {
        Map<String, Object> params = new HashMap<>();
        if (dto == null) {
            return params;
        }
        put(params, "nom", dto.getNom());
        put(params, "dateNaissance", dto.getDateNaissance());
        put(params, "adresseDomicile", dto.getAdresseDomicile());
        put(params, "telephone", dto.getTelephone());
        put(params, "email", dto.getEmail());
        put(params, "nationalite", dto.getNationalite());
        put(params, "typePieceIdentite", dto.getTypePieceIdentite());
        put(params, "numeroPieceIdentite", dto.getNumeroPieceIdentite());
        put(params, "profession", dto.getProfession());
        put(params, "capitalAssure", dto.getCapitalAssure());
        put(params, "dureeContrat", dto.getDureeContrat());
        put(params, "nombreCotisantsMinimum", dto.getNombreCotisantsMinimum());
        put(params, "dateEffet", dto.getDateEffet());
        put(params, "primePayer", dto.getPrimePayer());
        put(params, "frequencePaiement", dto.getFrequencePaiement());
        params.put("frequencePaiementNormalized", normalize(dto.getFrequencePaiement()));
        put(params, "modePaiement", dto.getModePaiement());
        params.put("modePaiementNormalized", normalize(dto.getModePaiement()));
        put(params, "optionVersement", dto.getOptionVersement());
        params.put("optionVersementNormalized", normalize(dto.getOptionVersement()));
        put(params, "numeroTelephoneMobile", dto.getNumeroTelephoneMobile());
        put(params, "operateurMobile", dto.getOperateurMobile());
        params.put("operateurMobileNormalized", normalize(dto.getOperateurMobile()));
        put(params, "numeroBanque", dto.getNumeroBanque());
        put(params, "nomBanque", dto.getNomBanque());
        put(params, "adresseBanque", dto.getAdresseBanque());
        params.put("jeConsens1", Boolean.TRUE.equals(dto.getJeConsens1()));
        params.put("jeConsens2", Boolean.TRUE.equals(dto.getJeConsens2()));
        put(params, "signature", dto.getSignature());
        put(params, "date", dto.getDate());
        return params;
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Normalisation des champs à cases à cocher
    // Insensible à la casse, aux accents et aux espaces / tirets
    // ──────────────────────────────────────────────────────────────────────────

    /**
     * Supprime les accents, met en minuscules et retire espaces/tirets/underscores.
     * Exemple : "Fréquence Paiement" → "frequencepaiement"
     */
    private static String normalize(String s) {
        if (s == null) return "";
        String withoutAccents = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return withoutAccents.toLowerCase().replaceAll("[\\s\\-_./\\\\]", "");
    }

    // Les méthodes normalizeFrequencePaiement, normalizeModePaiement, etc. ne sont plus nécessaires
    // car le JRXML utilise directement *Normalized avec .contains() en minuscules

    // ──────────────────────────────────────────────────────────────────────────

    private static void put(Map<String, Object> params, String key, String value) {
        params.put(key, value != null ? value : "");
    }

    private static String toString(Object v) {
        if (v == null) return "";
        if (v instanceof String) return (String) v;
        if (v instanceof Map || v instanceof Iterable) return String.valueOf(v);
        return String.valueOf(v);
    }

    private static Boolean toBoolean(Object v) {
        if (v instanceof Boolean) return (Boolean) v;
        if (v == null) return false;
        String s = String.valueOf(v).trim().toLowerCase();
        return "true".equals(s) || "1".equals(s) || "oui".equals(s) || "yes".equals(s);
    }
}
