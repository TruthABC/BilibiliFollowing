package bf;

import java.util.Date;

public class FilterLauncher {

    public static void main(String[] args) {
        EdgeFilter filter = new EdgeFilter();
        filter.initializeTargetFromCSVV("30plus.txt");
        filter.initializeEdgesFromCSVV("edges-1554013692072.csv");
        System.out.print("Starting Saving to Files.");
        long time = new Date().getTime();
//        filter.saveToCSVVOnlyTargetEdge("target_edges100-1554013692072.csv");
        filter.saveToCSVVOnlyTargetDoubleEdge("target_edges30_double-1554013692072.csv");
    }

}
