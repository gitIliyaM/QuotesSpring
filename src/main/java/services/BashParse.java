package services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class BashParse {
    public Map<Integer, String> getPage(int page) {
        Map<Integer, String> quotes = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://ibash.org.ru/?page=" + page).get();
            System.out.println(doc.title());
            //log(doc.title());
            Elements sourceQuotes = doc.select(".quote");
            for (Element quotesElement : sourceQuotes) {
                int id = Integer.parseInt(quotesElement.select("b").first().text().substring(1));
                String text = quotesElement.select(".quotbody").first().text();
                quotes.put(id,text);
                //log("%s\n\t%s",headline.attr("title"), headline.absUrl("href"));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return quotes;
    }
    public Map.Entry<Integer, String> getById(int id) {
        Map<Integer, String> quotes = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://ibash.org.ru/quote.php?id=" + id).get();
            //log(doc.title());
            Element quoteElement = doc.select(".quote").first();
            String realId = quoteElement.select("b").first().text();
            if(realId.equals("#???")){
                return null;
            }

            String text = quoteElement.select(".quotbody").first().text();
            return new AbstractMap.SimpleEntry<>(id,text);
            //log("%s\n\t%s",headline.attr("title"), headline.absUrl("href"));
        } catch (ClassCastException | IOException ee) {
            ee.printStackTrace();
        }
        return null;
    }
    /*public Map<Integer, String> getRandom() {
        Map<Integer, String> quotes = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://ibash.org.ru/random.php").get();
            //log(doc.title());
            Elements sourceQuotes = doc.select(".quote");
            for (Element quotesElement : sourceQuotes) {
                int id = Integer.parseInt(quotesElement.select("b").first().text().substring(1));
                String text = quotesElement.select(".quotbody").first().text();
                quotes.put(id,text);
            }
        }catch (IOException e) {
                e.printStackTrace();
        }
        return quotes;
    }*/
    public Map.Entry<Integer, String> getRandom() {
        Map<Integer, String> quotes = new HashMap<>();
        try {
            Document doc = Jsoup.connect("http://ibash.org.ru/random.php").get();
            //log(doc.title());
            Element quoteElement = doc.select(".quote").first();
            String realId = quoteElement.select("b").first().text();
            if(realId.equals("#???")){
                return null;
            }
            String text = quoteElement.select(".quotbody").first().text();
            return new AbstractMap.SimpleEntry<>(Integer.parseInt(realId.substring(1)), text);
        } catch (ClassCastException | IOException ee) {
            ee.printStackTrace();
        }
        return null;
    }
    public Map<Integer, String> getIndex() throws IOException {
        Document doc = Jsoup.connect("http://ibash.org.ru/").get();
        System.out.println(doc.title());
        //log(doc.title());
        Elements sourceQuotes = doc.select(".quote");
        Map<Integer, String> quotes = new HashMap<>();
        for (Element quotesElement : sourceQuotes) {
            int id = Integer.parseInt(quotesElement.select("b").first().text().substring(1));
            String text = quotesElement.select(".quotbody").first().text();
            quotes.put(id,text);
            //log("%s\n\t%s",headline.attr("title"), headline.absUrl("href"));
        }
        return quotes;
    }
}
