package com.indraphan.ftse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<S3Event, String> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String PARAMETER_REGION_SRC_KEY = "parameters/regions.txt";

    private LambdaLogger logger;

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        logger = context.getLogger();
        logger.log("EVENT: " + gson.toJson(s3Event));

        List<FtseData> ftseDataList = extractFtseData(s3Event);
        logger.log("FTSE:" + gson.toJson(ftseDataList));

        Map<String, String> regions = getRegionsData(s3Event);
        logger.log("REGIONS: " + regions);

        Map<String, Double> regionSummaries = getRegionSummaries(ftseDataList, regions);
        logger.log("SUMMARIES: " + gson.toJson(regionSummaries));

        return regionSummaries.toString();
    }

    private Map<String, Double> getRegionSummaries(List<FtseData> ftseDataList, Map<String, String> regions) {
        Map<String, Double> regionSummaries = ftseDataList.stream()
                .collect(Collectors.toMap(
                        d -> regions.get(d.getName()),
                        d -> d.getWeightPercentage(),
                        (v1, v2) -> v1 + v2
                ));

        return regionSummaries;
    }

    private List<FtseData> extractFtseData(S3Event s3Event) {

        S3Object s3Object = getS3EventObject(s3Event);

        try (
                InputStream objectData = s3Object.getObjectContent();
                InputStreamReader isr = new InputStreamReader(objectData, CHARSET_UTF_8);
                BufferedReader br = new BufferedReader(isr);
        ) {
            return getFtseDataList(br);
        } catch (IOException e) {
            handleIOException(e);
        }

        return new ArrayList<>();
    }

    private List<FtseData> getFtseDataList(BufferedReader br) {
        return br.lines()
                .map(FtseParser::parseFtseLine)
                .collect(Collectors.toList());
    }

    private Map<String, String> getRegionsData(S3Event s3Event) {
        S3Object s3Object = getS3RegionsObject(s3Event);

        try (
                InputStream objectData = s3Object.getObjectContent();
                InputStreamReader isr = new InputStreamReader(objectData, CHARSET_UTF_8);
                BufferedReader br = new BufferedReader(isr);
        ) {
            return RegionParser.parse(br);
        } catch (IOException e) {
            handleIOException(e);
        }

        return new HashMap<>();
    }

    private S3Object getS3EventObject(S3Event s3Event) {
        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);

        String srcBucket = record.getS3().getBucket().getName();

        // Object key may have spaces or unicode non-ASCII characters.
        String srcKey = record.getS3().getObject().getUrlDecodedKey();

        return getS3Object(srcBucket, srcKey);
    }

    private S3Object getS3RegionsObject(S3Event s3Event) {
        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);

        String srcBucket = record.getS3().getBucket().getName();
        return getS3Object(srcBucket, PARAMETER_REGION_SRC_KEY);
    }

    private S3Object getS3Object(String srcBucket, String srcKey) {

        // Download the file from S3 into a stream
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();

        return s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
    }

    private void handleIOException(IOException e) {
        e.printStackTrace();
    }
}
