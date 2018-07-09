package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;
import edu.rylynn.netdata.util.EnDeCoder;
import edu.rylynn.netdata.util.FileUtil;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.util.ByteArrays;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor21CN extends AbstractProcessor {

    public Processor21CN(AbstractWebMailListener listenerMail) {
        super(listenerMail);
    }

    @Override
    public void sendMailExtract(List<TcpPacket> packets) {
        StringBuilder tempHttpRequestContent = new StringBuilder();
        Pattern attachmentBeginSignal = Pattern.compile(".*WebKitFormBoundary.*filename.*");
        Pattern attachmentEndSignal = Pattern.compile(".*WebKitFormBoundary.*");
        Pattern isUploadSignal = Pattern.compile("POST.*upload.do.*");
        Pattern isMailSignal = Pattern.compile("sendMail.do");

        String filename = null;
        byte[] attachmentData = null;

        boolean isAttachment = false;
        boolean isMail = false;
        for (TcpPacket tcpPacket : packets) {

            byte[] thisRawData;
            try {
                thisRawData = tcpPacket.getPayload().getRawData();
            } catch (NullPointerException e) {
                continue;
            }
            String thisHexString = ByteArrays.toHexString(thisRawData, "");
            String thisContent = EnDeCoder.hexStringToString(thisHexString);

            Matcher attachmentBeginMatcher = attachmentBeginSignal.matcher(thisContent.replaceAll("\r\n", ""));
            Matcher attachmentEndMatcher = attachmentEndSignal.matcher(thisContent.replaceAll("\r\n", ""));
            Matcher sendMailMatcher = isMailSignal.matcher(thisContent.replaceAll("\r\n", ""));

            if (attachmentBeginMatcher.find()) {
                System.out.println(thisContent);
                if (!isAttachment) {
                    String tempFilename = "";

                    Matcher m = Pattern.compile("filename=\".*\"").matcher(thisContent.replaceAll("\r\n", ""));
                    if (m.find()) {
                        tempFilename = m.group();
                    }
                    filename = tempFilename.substring(tempFilename.indexOf("\"") + 1, tempFilename.lastIndexOf("\""));
                }
                isAttachment = true;

                continue;
            }

            if (attachmentEndMatcher.find()) {
                if (isAttachment) {
                    isAttachment = false;
                }
                continue;
            }

            if (isAttachment) {
                attachmentData = tcpPacket.getPayload().getRawData();
                FileUtil.writeIntoBinaryFile(filename, attachmentData);
                continue;
            }


            if (isMail) {
                tempHttpRequestContent.append(thisContent).append("\n");
                continue;
            }
        }

        String httpContent = tempHttpRequestContent.toString();

        Map<String, Object> form = new HashMap<>();

        try {
            httpContent = httpContent.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            httpContent = URLDecoder.decode(httpContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------");
        System.out.println(httpContent);
        System.out.println("------------------------------------");

        Matcher matcherFrom = Pattern.compile("from=(.*)&orgMailMode").matcher(httpContent);
        Matcher matcherTo = Pattern.compile("to=(.*)&cc").matcher(httpContent);
        Matcher matcherSubject = Pattern.compile("subject=(.*)&content").matcher(httpContent);
        Matcher matcherContent = Pattern.compile("content=(.*)&action").matcher(httpContent);

        if (matcherSubject.find()) {
            String group = matcherSubject.group();
            form.put("subject", group.substring(group.lastIndexOf("subject=") + 8, group.indexOf("&content")));
        }

        if (matcherContent.find()) {
            String group = matcherContent.group();
            form.put("content", group.substring(group.lastIndexOf("content=") + 8, group.indexOf("&action")));
        }

        if (matcherTo.find()) {
            String group = matcherTo.group();
            form.put("to", group.substring(group.lastIndexOf("to=") + 3, group.indexOf("&cc")));
        }

        if (matcherFrom.find()) {
            String group = matcherFrom.group();
            form.put("from", group.substring(group.lastIndexOf("from=") + 5, group.indexOf("&orgMailMode")));
        }

        form.put("time", new Date().toString());
        //handler.insertMail(form);
    }


}
