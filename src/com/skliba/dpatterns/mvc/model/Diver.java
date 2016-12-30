package com.skliba.dpatterns.mvc.model;

import java.util.ArrayList;
import java.util.List;

import com.skliba.DiverInventoryLevel;
import com.skliba.SpecialDivingSkill;
import com.skliba.dpatterns.composite.Item;
import com.skliba.dpatterns.visitor.Visitable;
import com.skliba.dpatterns.visitor.Visitor;
import com.skliba.models.DiverDiveInformation;

public class Diver implements Visitable, Cloneable {

    private String name;
    private String certType;
    private int certTypeAsInteger;
    private String certName;
    private int yearOfBirth;
    private String agencyName;
    private int maxDepth = 0;
    private List<SpecialDivingSkill> specialDivingSkills = new ArrayList<>();
    private List<Item> inventoryItems = new ArrayList<>();
    private DiverInventoryLevel diverInventoryLevel;
    private boolean minimalGear = true;

    private static final String SSI = "SSI";
    private static final String CMAS = "CMAS";
    private static final String NAUI = "NAUI";
    private static final String BSAC = "BSAC";

    private ArrayList<DiverDiveInformation> diverDiveInformation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        switch (certType) {
            case "R0":
                this.certType = certType;
                this.certTypeAsInteger = 0;
                break;
            case "R1":
                this.certType = certType;
                this.certTypeAsInteger = 1;
                break;
            case "R2":
                this.certType = certType;
                this.certTypeAsInteger = 2;
                break;
            case "R3":
                this.certType = certType;
                this.certTypeAsInteger = 3;
                break;
            case "R4":
                this.certType = certType;
                this.certTypeAsInteger = 4;
                break;
            case "R5":
                this.certType = certType;
                this.certTypeAsInteger = 5;
                break;
            case "I1":
                this.certType = certType;
                this.certTypeAsInteger = 7;
                break;
            case "I2":
                this.certType = certType;
                this.certTypeAsInteger = 8;
                break;
            case "I3":
                this.certType = certType;
                this.certTypeAsInteger = 9;
                break;
            case "I4":
                this.certType = certType;
                this.certTypeAsInteger = 10;
                break;
            case "I5":
                this.certType = certType;
                this.certTypeAsInteger = 11;
                break;
            case "I6":
                this.certType = certType;
                this.certTypeAsInteger = 12;
                break;
            default:
                throw new IllegalArgumentException("Certificate type can be any of the following: R[0-5] or I[1-6] and you passed: "
                        + certType);
        }
        setCertName();
        setMaxDepth();
    }

    private void setMaxDepth() {
        switch (certType) {
            case "R0":
                this.maxDepth = 0;
                break;
            case "R1":
                this.maxDepth = 10;
                break;
            case "R2":
                this.maxDepth = 20;
                break;
            case "R3":
                this.maxDepth = 30;
                break;
            case "R4":
            case "R5":
            case "I1":
            case "I2":
            case "I3":
            case "I4":
            case "I5":
            case "I6":
                this.maxDepth = 40;
                break;
            default:
                maxDepth = 0;
        }
    }

    public ArrayList<DiverDiveInformation> getDiverDiveInformation() {
        return diverDiveInformation;
    }

    public void setDiverDiveInformation(ArrayList<DiverDiveInformation> diverDiveInformation) {
        this.diverDiveInformation = diverDiveInformation;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName() {
        char pro = certType.charAt(0);
        char degree = certType.charAt(1);
        switch (agencyName) {
            case SSI:
                if (pro == 'R') {
                    switch (degree) {
                        case 48:
                            this.certName = "Scuba Diver";
                            break;
                        case 49:
                            this.certName = "Open Water Diver";
                            break;
                        case 50:
                            this.certName = "Advanced Adventure";
                            break;
                        case 51:
                            this.certName = "Diver Stress & Rescue";
                            break;
                        case 52:
                            this.certName = "Advanced Open Water Diver";
                            break;
                        case 53:
                            this.certName = "Master Diver";
                            break;
                    }
                } else {
                    switch (degree) {
                        case 49:
                            this.certName = "Dive Guide";
                            break;
                        case 50:
                            this.certName = "Divemaster";
                            break;
                        case 51:
                            this.certName = "Dive Control Specialist";
                            break;
                        case 52:
                            this.certName = "Open Water Instructor";
                            break;
                        case 53:
                            this.certName = "Advanced Open Water Instructor";
                            break;
                        case 54:
                            this.certName = "Divemaster Instructor";
                            break;
                        case 55:
                            this.certName = "Dive Control Specialist Instructor";
                            break;
                        case 56:
                            this.certName = "Instructor Trainer";
                            break;
                    }
                }
                break;
            case NAUI:
                if (pro == 'R') {
                    switch (degree) {
                        case 48:
                            this.certName = "Scuba Diver";
                            break;
                        case 49:
                            this.certName = "Scuba Diver";
                            break;
                        case 50:
                            this.certName = "Advanced Scuba Diver";
                            break;
                        case 51:
                            this.certName = "Scuba Rescue Diver";
                            break;
                        case 52:
                            this.certName = "Master Scuba Diver";
                            break;
                        case 53:
                            this.certName = "Master Scuba Diver";
                            break;
                    }
                } else {
                    switch (degree) {
                        case 49:
                            this.certName = "Assistant Instructor";
                            break;
                        case 50:
                            this.certName = "Assistant Instructor";
                            break;
                        case 51:
                            this.certName = "Divemaster";
                            break;
                        case 52:
                            this.certName = "Instructor";
                            break;
                        case 53:
                            this.certName = "Instructor";
                            break;
                        case 54:
                            this.certName = "Instructor";
                            break;
                        case 55:
                            this.certName = "Instructor";
                            break;
                        case 56:
                            this.certName = "Instructor Trainer";
                            break;
                    }
                }
                break;
            case BSAC:
                if (pro == 'R') {
                    switch (degree) {
                        case 48:
                            this.certName = "Ocean Diver";
                            break;
                        case 49:
                            this.certName = "Ocean Diver";
                            break;
                        case 50:
                            this.certName = "Ocean Diver";
                            break;
                        case 51:
                            this.certName = "Sports Diver";
                            break;
                        case 52:
                            this.certName = "Sports Diver";
                            break;
                        case 53:
                            this.certName = "Sports Diver";
                            break;
                    }
                } else {
                    switch (degree) {
                        case 49:
                            this.certName = "Dive Leader";
                            break;
                        case 50:
                            this.certName = "Dive Leader";
                            break;
                        case 51:
                            this.certName = "Assistant Open Water Instructor";
                            break;
                        case 52:
                            this.certName = "Open Water Instructor";
                            break;
                        case 53:
                            this.certName = "Advanced Instructor";
                            break;
                        case 54:
                            this.certName = "Advanced Instructor";
                            break;
                        case 55:
                            this.certName = "Advanced Instructor";
                            break;
                        case 56:
                            this.certName = "Advanced Instructor";
                            break;
                    }
                }
                break;
            case CMAS:
                if (pro == 'R') {
                    switch (degree) {
                        case 48:
                            this.certName = "One Star Diver";
                            break;
                        case 49:
                            this.certName = "One Star Diver";
                            break;
                        case 50:
                            this.certName = "One Star Diver";
                            break;
                        case 51:
                            this.certName = "Two Star Diver";
                            break;
                        case 52:
                            this.certName = "Two Star Diver";
                            break;
                        case 53:
                            this.certName = "Two Star Diver";
                            break;
                    }
                } else {
                    switch (degree) {
                        case 48:
                            this.certName = "Three Star Diver";
                            break;
                        case 49:
                            this.certName = "Three Star Diver";
                            break;
                        case 51:
                            this.certName = "One Star Instructor";
                            break;
                        case 52:
                            this.certName = "Two Star Instructor";
                            break;
                        case 53:
                            this.certName = "Two Star Instructor";
                            break;
                        case 54:
                            this.certName = "Two Star Instructor";
                            break;
                        case 55:
                            this.certName = "Two Star Instructor";
                            break;
                        case 56:
                            this.certName = "Two Star Instructor";
                            break;
                    }
                }
                break;
        }
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void addASingleDiverInfo(DiverDiveInformation information) {
        if (diverDiveInformation != null) {
            diverDiveInformation.add(information);
        } else {
            diverDiveInformation = new ArrayList<>();
            diverDiveInformation.add(information);
        }
    }

    public List<SpecialDivingSkill> getSpecialDivingSkills() {
        return specialDivingSkills;
    }

    public void addSpecificDivingSkill(SpecialDivingSkill specificDivingSkill) {
        this.specialDivingSkills.add(specificDivingSkill);
    }

    public int getCeritificateTypeAsInteger() {
        return certTypeAsInteger;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitDiver(this);
    }

    public boolean canUseDrySuit() {
        return specialDivingSkills.contains(SpecialDivingSkill.DRY_SUIT);
    }

    public boolean canPerformNightDive() {
        return specialDivingSkills.contains(SpecialDivingSkill.NIGHT_DIVE);
    }

    public boolean isUnderWaterPhotograph() {
        return specialDivingSkills.contains(SpecialDivingSkill.UNDERWATER_PHOTOGRAPH);
    }

    public DiverInventoryLevel getDiverInventoryLevel() {
        return diverInventoryLevel;
    }

    public void setDiverInventoryLevel(DiverInventoryLevel diverInventoryLevel) {
        this.diverInventoryLevel = diverInventoryLevel;
    }

    public void addInventoryItem(Item item) {
        inventoryItems.add(item);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isMinimalGear() {
        return minimalGear;
    }

    public void setMinimalGear(boolean minimalGear) {
        this.minimalGear = minimalGear;
    }

}
