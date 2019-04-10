package bf;

import java.io.PrintStream;
import java.util.*;

public class EdgeFilter {

    List<String> mV1, mV2;
    Set<String> mTargetNames;
    Map<String, Set<String>> mFollowingMap;

    public void initializeTargetFromCSVV(String path) {
        Scanner scanner = Global.getFileScanner(path);
        if (scanner == null) {
            return;
        }

        mTargetNames = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            mTargetNames.add(line);
        }
    }

    public void initializeEdgesFromCSVV(String path) {
        Scanner scanner = Global.getFileScanner(path);
        if (scanner == null) {
            return;
        }

        mV1 = new ArrayList<>();
        mV2 = new ArrayList<>();
        mFollowingMap = new HashMap<>();

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (mTargetNames.contains(parts[0]) && mTargetNames.contains(parts[1])) {
                mV1.add(parts[0]);
                mV2.add(parts[1]);
                if (!mFollowingMap.containsKey(parts[0])) {
                    mFollowingMap.put(parts[0], new HashSet<>());
                }
                mFollowingMap.get(parts[0]).add(parts[1]);
            }
        }

    }

    public void saveToCSVVOnlyTargetEdge(String path) {
        PrintStream output = Global.getFilePrintStream(path);

        if (output == null) {
            return;
        }

        output.println("Vertex 1,Vertex 2");

        for (int i = 0; i < mV1.size(); i++) {
            output.println(mV1.get(i) + "," +  mV2.get(i));
        }

        output.flush();
        output.close();
    }


    public void saveToCSVVOnlyTargetDoubleEdge(String path) {

        PrintStream output = Global.getFilePrintStream(path);

        if (output == null) {
            return;
        }

        output.println("Vertex 1,Vertex 2");


        for (String from:mFollowingMap.keySet()) {
            for (String to:mFollowingMap.get(from)) {
                if (mFollowingMap.containsKey(to) && mFollowingMap.get(to).contains(from)) {
                    output.println(from + "," +  to);
                }
            }
        }

        output.flush();
        output.close();

    }

}
