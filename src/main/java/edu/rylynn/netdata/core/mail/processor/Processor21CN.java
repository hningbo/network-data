package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Processor21CN extends AbstractProcessor {

    public Processor21CN(AbstractWebMailListener listenerMail) {
        super(listenerMail);
    }

    @Override
    public void sendMailExtract(String httpContent) {
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
        handler.insertMail(form);
    }


}
