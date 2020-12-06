package muchsin;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public final class FastaReader {

    public static HashMap<String, String> parseAsHashmapString(String filePath) {

        HashMap<String, String> fastaRecord = new HashMap<>();

        String header = null;
        StringBuilder sequence = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

            String line;
            while ((line=br.readLine())!=null) {
                if (line.startsWith(">")) {
                    if(header != null) {
                        fastaRecord.put(header, sequence.toString());
                        sequence = new StringBuilder();
                    }
                    header = line.substring(1);
                }
                else
                    sequence.append(line);
            }

            fastaRecord.put(header, sequence.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fastaRecord;

    }

}
