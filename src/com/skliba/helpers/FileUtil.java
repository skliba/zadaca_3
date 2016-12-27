package com.skliba.helpers;

import com.skliba.dpatterns.observer.Agency;
import com.skliba.dpatterns.observer.Institution;
import com.skliba.dpatterns.observer.Observer;
import com.skliba.dpatterns.singleton.DivingClub;
import com.skliba.dpatterns.visitor.ConcreteVisitor;
import com.skliba.dpatterns.visitor.Diver;
import com.skliba.models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class FileUtil {

    private static final String SEMICOLON_CHECK = ";";

    private static final int EXPECTED_AMOUNT_OF_DATA_FROM_FILE_ROW = 4;
    private static final String EMPTY_STRING = "";


    public static void readFileAndAddDivers(File file) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<Diver> divers = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals(EMPTY_STRING)) {
                    String[] diverData = line.split(SEMICOLON_CHECK);

                    if (diverData.length != EXPECTED_AMOUNT_OF_DATA_FROM_FILE_ROW) {
                        throw new IllegalStateException("Formatting inside the file seems to be invalid, check if data is separated with a ';'");
                    }

                    Diver diver = new Diver();
                    diver.setName(diverData[0]);
                    diver.setAgencyName(diverData[1]);

                    //Create a new observer if he doesn't already exist
                    if (!DivingClub.getInstance().observerExists(diverData[1])) {
                        DivingClub.getInstance().createANewObserver(diverData[1]);
                    }

                    diver.setCertType(diverData[2]);
                    diver.setYearOfBirth(Integer.parseInt(diverData[3]));
                    divers.add(diver);
                }
            }
            DivingClub.getInstance().setDivers(divers);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFileAndAddDates(File divesFile) {
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(divesFile));
            ArrayList<Divings> divings = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals(EMPTY_STRING)) {
                    String[] diveData = line.split(SEMICOLON_CHECK);
                    if (Integer.parseInt(diveData[3]) >= 2) {
                        Divings dive = new Divings();
                        dive.setDiveDate(diveData[0]);
                        dive.setDiveTime(diveData[1]);
                        dive.setDiveDepth(diveData[2]);
                        dive.setNumberOfDivers(diveData[3]);
                        divings.add(dive);
                    }
                }
            }

            DivingClub.getInstance().setDivings(divings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeReport(String filename, ArrayList<Dive> dives) {
        File outputFile = new File(filename);
        PrintWriter writer;
        try {
            writer = new PrintWriter(outputFile, "UTF-8");

            writer.write("ALL DIVES WITH THEIR SECURITY MEASURES");
            writer.write("\n");
            writer.write("----------------------------------------------------------------");
            writer.write("\n\n");

            String leftAlignFormatForSecurityMeasuresPerDive = "| %-15s | %-10s | %-29s | %-29s | %-30s |%n";
            writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write(String.format(leftAlignFormatForSecurityMeasuresPerDive, "Dive date", "Dive time", "Security measure (Random)",
                    "Security measure (MaxPartner)", "Security measure (MaxDepth)"));
            writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            for (Dive dive : dives) {
                writer.write(String.format(
                        leftAlignFormatForSecurityMeasuresPerDive,
                        dive.getDiveDateString(),
                        DateUtil.getTimeAsString(dive.getDiveTime()),
                        dive.getRandomSecurityMeasure(),
                        dive.getMaxPartnerSecurityMeasure(),
                        dive.getMaxDepthSecurityMeasure()));
            }
            writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n\n");
            writer.write("\n");


            writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write("| DIVERS DETAILED INFORMATION");
            writer.write("\n");
            writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write("\n");

            String headerFormat = "| %-125s |%n";
            String leftAlignFormat = "| %-15s | %-10s | %-10s | %-81s |%n";
            for (Diver diver : DivingClub.getInstance().getDivers()) {
                writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
                writer.write("\n");
                writer.write(String.format(headerFormat, "Diver: " + diver.getName()));
                writer.write(String.format(headerFormat, "Rank: " + diver.getCertName() + " " + diver.getCertType()));
                writer.write(String.format(headerFormat, diver.getCertType()));
                writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
                writer.write("\n");
                writer.write(String.format(leftAlignFormat, "Dive date", "Dive time", "Dive depth", "Partners"));
                writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
                writer.write("\n");
                if (diver.getDiverDiveInformation() != null) {
                    for (DiverDiveInformation information : diver.getDiverDiveInformation()) {
                        String partnersString = "";
                        for (Diver partner : information.getPartners()) {
                            partnersString += partner.getName() + ", ";
                        }
                        writer.write(String.format(
                                leftAlignFormat,
                                information.getDiveDateParticipation(),
                                information.getDiveTimeParticipationString(),
                                information.getDiveDepth(),
                                partnersString));

                    }
                }
                writer.write("+-------------------------------------------------------------------------------------------------------------------------------+");
                writer.write("\n");
                writer.write("\n");
                writer.write("\n");
            }

            writer.write("\n");
            writer.write("\n");
            writer.write("-----------------------------------------------------------");
            writer.write("\n");
            writer.write("DETAILED DIVINGS REPORT");
            writer.write("\n");
            writer.write("-----------------------------------------------------------");
            writer.write("\n");
            writer.write("\n");

            String diverAlignment = "| %-10s | %-16s | %-6s | %-13s | %n";
            String diverHeader = "| %-54s |%n";
            for (Dive dive : dives) {
                writer.write("+--------------------------------------------------------+");
                writer.write("\n");
                writer.write(String.format(diverHeader, "Dive date: " + dive.getDiveDateString()));
                writer.write(String.format(diverHeader, "Dive time: " + DateUtil.getTimeAsString(dive.getDiveTime())));
                writer.write(String.format(diverHeader, "Number of divers: " + dive.getAllDiversTogether().size()));
                writer.write(String.format(diverHeader, "Dive depth: " + dive.getDiveDepth()));
                writer.write(String.format(diverHeader, "Security measure: " + dive.getSecurityMeasure()));
                writer.write(String.format(diverHeader, "Security random measure: " + dive.getRandomSecurityMeasure()));
                writer.write(String.format(diverHeader, "Security max depth measure: " + dive.getMaxDepthSecurityMeasure()));
                writer.write(String.format(diverHeader, "Security max partner measure: " + dive.getMaxPartnerSecurityMeasure()));
                writer.write("+--------------------------------------------------------+");
                writer.write("\n");
                writer.write(String.format(diverAlignment, "Diver name", "Certificate type", "Agency", "Year of birth"));
                writer.write("+--------------------------------------------------------+");
                writer.write("\n");
                for (Diver diver : dive.getAllDiversTogether()) {
                    writer.write(String.format(diverAlignment, diver.getName(), diver.getCertType(), diver.getAgencyName(), diver.getYearOfBirth()));
                }
                writer.write("+--------------------------------------------------------+");
                writer.write("\n");
                writer.write("\n");
                writer.write("\n");
                writer.write("\n");
            }

            String tableHeader = "| %-126s | %n";
            String agenciesAlignment = "| %-12s | %-15s | %-10s | %-3s |  %-3s |  %-3s |  %-3s |  %-3s |  %-3s |  %-3s |  %-3s |  %-3s " +
                    "|  %-3s |  %-3s |  %-3s | %n";
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write(String.format(tableHeader, "AGENCIES STATISTICS"));
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write(String.format(agenciesAlignment, "Agency name", "Number of dives", "Avg depth", "R0", "R1", "R2",
                    "R3", "R4", "R5", "I1", "I2", "I3", "I4", "I5", "I6"));
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            for (Observer o : DivingClub.getInstance().getObservers()) {
                if (o instanceof Agency) {
                    writer.write(String.format(agenciesAlignment, ((Agency) o).getName(), ((Agency) o).getDiverInAgency().size(),
                            ((Agency) o).getAvgDepth(), ((Agency) o).getNumOfDiversPerCategory("R0"),
                            ((Agency) o).getNumOfDiversPerCategory("R1"), ((Agency) o).getNumOfDiversPerCategory("R2"),
                            ((Agency) o).getNumOfDiversPerCategory("R3"), ((Agency) o).getNumOfDiversPerCategory("R4"),
                            ((Agency) o).getNumOfDiversPerCategory("R5"), ((Agency) o).getNumOfDiversPerCategory("I1"),
                            ((Agency) o).getNumOfDiversPerCategory("I2"), ((Agency) o).getNumOfDiversPerCategory("I3"),
                            ((Agency) o).getNumOfDiversPerCategory("I4"), ((Agency) o).getNumOfDiversPerCategory("I5"),
                            ((Agency) o).getNumOfDiversPerCategory("I6")));
                } else {
                    writer.write(String.format(agenciesAlignment, ((Institution) o).getName(),
                            ((Institution) o).getDiversInAnInstitution().size(), ((Institution) o).getAvgDepth(),
                            ((Institution) o).getNumOfDiversPerCategory("R0"),
                            ((Institution) o).getNumOfDiversPerCategory("R1"), ((Institution) o).getNumOfDiversPerCategory("R2"),
                            ((Institution) o).getNumOfDiversPerCategory("R3"), ((Institution) o).getNumOfDiversPerCategory("R4"),
                            ((Institution) o).getNumOfDiversPerCategory("R5"), ((Institution) o).getNumOfDiversPerCategory("I1"),
                            ((Institution) o).getNumOfDiversPerCategory("I2"), ((Institution) o).getNumOfDiversPerCategory("I3"),
                            ((Institution) o).getNumOfDiversPerCategory("I4"), ((Institution) o).getNumOfDiversPerCategory("I5"),
                            ((Institution) o).getNumOfDiversPerCategory("I6")));
                }
            }
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n\n");

            ConcreteVisitor concreteVisitor = DivingClub.getInstance().getConcreteVisitor();

            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write("| CUSTOM FUNCTIONALITY - We have tried to rank agencies that have divers in them, ranks were formed by skill of \n" +
                    "| a specific diver, meaning the more skilled divers in an agency, the agency has a bigger ranking number. Ranking number" +
                    "\n| was formed by calculating the avg skill level of a diver in a specific agency - the following table shows results");
            writer.write("\n");
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");

            String customHeader = "| %-15s | %-10s | %n";
            writer.write("+------------------------------+");
            writer.write("\n");
            writer.write(String.format(customHeader, "Agency name", "Ranking"));
            writer.write("+------------------------------+");
            writer.write("\n");

            for (Map.Entry<String, Float> entry : concreteVisitor.getSortedMap().entrySet()) {
                writer.write(String.format(customHeader, entry.getKey(), entry.getValue()));
            }
            writer.write("+------------------------------+");

            writer.write("\n\n");
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            writer.write("| CUSTOM FUNCTIONALITY - We have tried to calculate on average how far were divers from "
                         + "reaching their maximum allowed\n| depth. Based on the dives they participated in, we took "
                         + "the average depth and subtracted it with maximum allowed depth.\n| We did it for every "
                         + "diver and got an average distance from the maximum allowed depth.");
            writer.write("\n");
            writer.write("+--------------------------------------------------------------------------------------------------------------------------------+");
            writer.write("\n");
            String customHeader2 = "| %-36s | %n";
            writer.write("+--------------------------------------+");
            writer.write("\n");
            writer.write(String.format(customHeader2, "Average distance from maximum depth"));
            writer.write("+--------------------------------------+");
            writer.write("\n");
            writer.write(String.format(customHeader2, concreteVisitor.getAverageDiversEfficiency()));
            writer.write("+--------------------------------------+");

            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
