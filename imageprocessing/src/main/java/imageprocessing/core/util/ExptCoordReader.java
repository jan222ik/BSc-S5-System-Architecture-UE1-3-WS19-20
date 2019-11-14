package imageprocessing.core.util;

import imageprocessing.framework.pmp.img.Coordinate;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class ExptCoordReader {

    private ExptCoordReader() {
    }

    public static List<Coordinate> parse(File file) {
        String text = null;
        try {
            text = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern pattern = Pattern.compile("expected_coordinates=\\[(?<coordlist>(\\(\\d+?,\\d+?\\))(, (\\(\\d+?,\\d+?\\)))*?)]");

        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String allCoordsAsString = matcher.group("coordlist");
            String[] allCoords = allCoordsAsString.split("\\), ");
            return Arrays.stream(allCoords).map(s -> s.replace("(", "").replace(")", "")).map(cs -> {
                String[] values = cs.split(",");
                return new Coordinate(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
            }).collect(Collectors.toList());
        }
        return new LinkedList<>();
    }
}


