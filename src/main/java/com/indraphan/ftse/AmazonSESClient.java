package com.indraphan.ftse;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AmazonSESClient {

    // Replace sender@example.com with your "From" address.
    // This address must be verified with Amazon SES.
    private static final String FROM = System.getenv("SES_SENDER");

    // Replace recipient@example.com with a "To" address. If your account
    // is still in the sandbox, this address must be verified.
    private static final String TO = System.getenv("SES_RECIPIENT");

    // The configuration set to use for this email. If you do not want to use a
    // configuration set, comment the following variable and the
    // .withConfigurationSetName(CONFIGSET); argument below.
//    private static final String CONFIGSET = "ConfigSet";

    // The subject line for the email.
    private static final String SUBJECT = "Amazon SES - FTSE Region Summaries";

    // The HTML body for the email.
    private static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
            + "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
            + "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>"
            + "AWS SDK for Java</a>";

    // The email body for recipients with non-HTML email clients.
    private static final String TEXTBODY = "This email was sent through Amazon SES "
            + "using the AWS SDK for Java.";

    private static final String CHARSET_UTF_8 = "UTF-8";

    public void sendEmail(String content) throws IOException {

        AmazonSimpleEmailService client =
                AmazonSimpleEmailServiceClientBuilder.standard()
                        // Replace US_WEST_2 with the AWS Region you're using for
                        // Amazon SES.
                        .withRegion(Regions.AP_SOUTHEAST_1).build();
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(
                        new Destination().withToAddresses(Arrays.stream(TO.split(";")).map(v -> v.trim()).collect(Collectors.toList())))
                .withMessage(new Message()
                        .withBody(new Body()
                                .withHtml(new Content()
                                        .withCharset(CHARSET_UTF_8).withData(content))
                                .withText(new Content()
                                        .withCharset(CHARSET_UTF_8).withData(TEXTBODY)))
                        .withSubject(new Content()
                                .withCharset(CHARSET_UTF_8).withData(SUBJECT)))
                .withSource(FROM);
                // Comment or remove the next line if you are not using a
                // configuration set
//                .withConfigurationSetName(CONFIGSET);

        client.sendEmail(request);
    }
}