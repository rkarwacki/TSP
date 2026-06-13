package pl.radoslawkarwacki.tsp.cli;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.OutputStream;

public class ResultWriter {
    private final ObjectMapper mapper = new ObjectMapper();

    public void write(RunResultDto result, String outputPath) throws Exception {
        if (outputPath == null || outputPath.isBlank()) {
            try (OutputStream os = System.out) {
                mapper.writeValue(os, result);
                os.flush();
            }
        } else {
            mapper.writeValue(new File(outputPath), result);
        }
    }
}
