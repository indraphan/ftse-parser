package com.indraphan.ftse;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class FtseParser {

    private static NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

    public static FtseData parseFtseLine(String line) {
        FtseData ftseData = new FtseData();

        String data[] = line.split(" ");
        Integer idx = 0;

        try {
            ftseData.setName(data[idx++]);
            if(isAlphabet(data[idx].charAt(0)))
                ftseData.setName(ftseData.getName() + " " + data[idx++]);
            ftseData.setNoOfCons(Integer.parseInt(data[idx++]));
            ftseData.setNetCapitalInMillion(numberFormat.parse(data[idx++]).longValue());
            ftseData.setWeightPercentage(numberFormat.parse(data[idx++]).doubleValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return ftseData;
    }

    private static boolean isAlphabet(char x) {
        if(x >= 'a' && x <= 'z') return true;
        if(x >= 'A' && x <= 'Z') return true;

        return false;
    }

//    public static void main(String[] args) throws IOException {
//        Path ftsePath = Path.of("C:\\Users\\indra\\Downloads\\ftse.txt");
//
//        try(
//                FileReader fileReader = new FileReader(ftsePath.toFile());
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
//        ) {
//            List<Country> countries = bufferedReader.lines()
//                    .map(FtseParser::parseFtseLine)
//                    .collect(Collectors.toList());
//
//
//        }
//    }
}
