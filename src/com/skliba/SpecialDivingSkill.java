package com.skliba;

public enum SpecialDivingSkill {
    DRY_SUIT, UNDERWATER_PHOTOGRAPH, NIGHT_DIVE;

    public static SpecialDivingSkill getFromString(String specificDivingSkill) {
        switch (specificDivingSkill) {
            case "Suho odijelo":
                return DRY_SUIT;
            case "Podvodni fotograf":
                return UNDERWATER_PHOTOGRAPH;
            default:
                return NIGHT_DIVE;
        }
    }
}
