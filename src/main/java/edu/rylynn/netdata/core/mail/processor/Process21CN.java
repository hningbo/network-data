package edu.rylynn.netdata.core.mail.processor;

import edu.rylynn.netdata.core.mail.listener.AbstractWebMailListener;

public class Process21CN extends AbstractProcessor{

    public Process21CN(AbstractWebMailListener listenerMail) {
        super(listenerMail);
    }

    @Override
    public void sendMailExtract(String httpContent) {
        //todo :recover the mail from the http content
    }

    @Override
    public void recieveMailExtract() {

    }
}
