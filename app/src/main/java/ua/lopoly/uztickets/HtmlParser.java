package ua.lopoly.uztickets;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lopoly on 12.08.2015.
 */
public class HtmlParser {
    private String mSessionId;
    private String mGvToken;

    public HtmlParser(String sessionId, String gvToken) {
        mSessionId = sessionId;
        mGvToken = gvToken;
    }

    public HtmlParser() {
    }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String sessionId) {
        mSessionId = sessionId;
    }

    public String getGvToken() {
        return mGvToken;
    }

    public void setGvToken(String gvToken) {
        mGvToken = gvToken;
    }

    public HtmlParser getHeaders() {

        Document mDocument = new Document("http://booking.uz.gov.ua");
        String mCipherLine = null;
        String sessionId = null;

        try {

            Connection.Response res = Jsoup.connect("http://booking.uz.gov.ua").timeout(0).data().method(Connection.Method.POST)
                    .execute();


            sessionId = res.cookie("_gv_sessid");
            mDocument = res.parse();


        } catch (IOException e) {
            e.printStackTrace();
        }


        Element elements = mDocument.body();
        Elements element = elements.getElementsByTag("script");

        String s = element.toString();

        Pattern p = Pattern.compile("\"\\\\\\\\\\\\\"\"(.*?)\"\\\\\\\\\\\\\"");
        Matcher matcher = p.matcher(s);

        while (matcher.find()) {

            mCipherLine = matcher.group(1);
        }

        String[] mCipher = mCipherLine.split("\\+");

        Map<String, String> mKey = new HashMap<>();
        mKey.put("$$_.___", "0");
        mKey.put("$$_.__$", "1");
        mKey.put("$$_._$_", "2");
        mKey.put("$$_._$$", "3");
        mKey.put("$$_.$__", "4");
        mKey.put("$$_.$_$", "5");
        mKey.put("$$_.$$_", "6");
        mKey.put("$$_.$$$", "7");
        mKey.put("$$_.$___", "8");
        mKey.put("$$_.$__$", "9");
        mKey.put("$$_.$_$_", "a");
        mKey.put("$$_.$_$$", "b");
        mKey.put("$$_.$$__", "c");
        mKey.put("$$_.$$_$", "d");
        mKey.put("$$_.$$$_", "e");
        mKey.put("$$_.$$$$", "f");

        Iterator<String> iterator = mKey.keySet().iterator();

        for (Map.Entry<String, String> entry : mKey.entrySet()) {
            for (int i = 0; i < mCipher.length; i++) {
                if (mCipher[i].equals(entry.getKey())) {
                    mCipher[i] = mCipher[i].replace(entry.getKey(), entry.getValue());
                }
            }
        }

        String mDecoded = "";
        for (int i = 0; i < mCipher.length; i++) {
            mDecoded += new StringBuilder().append(mCipher[i]);
        }
        System.out.println("_gv_sessid: " + sessionId);
        System.out.println("GV-TOKEN:   " + mDecoded);

        HtmlParser mData = new HtmlParser();

        mData.setSessionId(sessionId);
        mData.setGvToken(mDecoded);

        return mData;
    }
}
