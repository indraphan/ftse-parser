package com.indraphan.ftse;

import java.io.BufferedReader;
import java.util.Map;
import java.util.stream.Collectors;

public class RegionParser {

    public static Map<String, String> parse(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(line -> line.split(","))
                .collect(Collectors.toMap(d -> d[0], d -> d[1]))
                ;
    }

//    public static void main(String[] args) throws IOException {
//        Path ftsePath = Paths.get("C:\\Users\\indra\\Downloads\\regions.txt");
//
//        try(
//                FileReader fileReader = new FileReader(ftsePath.toFile());
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//        ) {
//            Map<String, String> regions = parse(bufferedReader);
//            System.out.println(regions);
//        }
//    }
}
