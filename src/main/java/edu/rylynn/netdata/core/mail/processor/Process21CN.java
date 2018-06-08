package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;

import java.util.HashMap;
import java.util.Map;

public class Process21CN extends AbstractProcessor {

    public Process21CN(AbstractWebMailListener listenerMail) {
        super(listenerMail);
    }

    @Override
    public void sendMailExtract(String httpContent) {
        String sender;
        String reciever;
        String subject;
        String content;
        Map<String, String> form = new HashMap<>();
        httpContent = httpContent.replace("%40", "@");
        httpContent = httpContent.replace("%3C", "<");
        httpContent = httpContent.replace("%3E", ">");
        httpContent = httpContent.replace("\r\n", "");
        System.out.println("------------------------------------");
        System.out.println(httpContent);
        System.out.println("------------------------------------");

//        Pattern senderPattern = Pattern.compile("from=[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
//        Pattern revieverPattern = Pattern.compile("to=.*<[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w]>)?");
//        Matcher senderMatcher = senderPattern.matcher(httpContent);
//        Matcher recieverMatcher = revieverPattern.matcher(httpContent);
//        if (senderMatcher.find()) {
//           String temp = senderMatcher.group();
//           sender = temp.split("=")[1];
//        }
//        if (recieverMatcher.find()){
//            String temp = recieverMatcher.group();
//            reciever = temp.split("=")[1];
//        }
        String[] splitContent = httpContent.split(";");
        String formContent = splitContent[splitContent.length - 1];

        for (String formItem : formContent.split("&")) {
            String[] kv = formItem.split("=");
            if (kv.length == 1) {
                continue;
            }
            form.put(kv[0], kv[1]);
            System.out.println(kv[0] + " " + kv[1]);
        }
    }

    @Override
    public void recieveMailExtract(String httpContent) {
        Map<String, String> form = new HashMap<>();
        httpContent = httpContent.replace("%40", "@");
        httpContent = httpContent.replace("%3C", "<");
        httpContent = httpContent.replace("%3E", ">");
        httpContent = httpContent.replace("\r\n", "");
        System.out.println("------------------------------------");
        System.out.println(httpContent);
        System.out.println("------------------------------------");
    }
}
