import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
        String line = "�\u0000P�^\u0017�\u0018pݠ�\u0018\u0010\u0000n�\u0000\u0000\u0001\n" +
                "6\u0014G�f\u0019z\n" +
                "POST /w2/mail/sendMail.do HTTP/1.1\n" +
                "Host: mail.21cn.com\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 225\n" +
                "Accept: application/json, text/javascript, */*; q=0.01\n" +
                "Origin: http://mail.21cn.com\n" +
                "X-Requested-With: XMLHttpRequest\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36\n" +
                "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                "Referer: http://mail.21cn.com/w2/template/writeMail.jsp\n" +
                "Accept-Encoding: gzip, deflate\n" +
                "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8\n" +
                "Cookie: JSESSIONID=avNpkLZnatI4sDlzPo; LSID=avNpkLZnatI4sDlzPo; apm_ct=20180529085540684; apm_sid=8E471B97A9986C985A0D64BE7734D8F1; apm_uid=AA3F81A089017648EA8D27827DCEC4D3; apm_ip=223.104.19.32; apm_ua=92155205F3E35399C2DAC7AA9B7CC3AA; qimo_seosource_ef9e5f30-ed02-11e6-8921-5bf6fce3cd5e=%E7%AB%99%E5%86%85; qimo_seokeywords_ef9e5f30-ed02-11e6-8921-5bf6fce3cd5e=; accessId=ef9e5f30-ed02-11e6-8921-5bf6fce3cd5e; bad_idef9e5f30-ed02-11e6-8921-5bf6fce3cd5e=028f3661-62db-11e8-aa3b-61dfa73b2c8a; nice_idef9e5f30-ed02-11e6-8921-5bf6fce3cd5e=028f3662-62db-11e8-aa3b-61dfa73b2c8a; pageViewNum=2; BINDMOBILEFLAT=true; 21CNSESSION_ID=00e15dc86c7df4160af72fe4e015ab14; 21CNACCOUNT=rylynn_ab@21cn.com\n";
        System.out.println(Pattern.matches(".*POST*", line));
    }
}
