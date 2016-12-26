package drag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evseevvd on 26.12.16.
 */
public class Main {

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect("http://www.rlsnet.ru").get();
            Elements alphaList = document.body().select("div.tn_letters_main > div.tn_letters").get(0).getAllElements();

            List<String> searchPanel = new ArrayList<>();
            for (Element element : alphaList) {
                if (!element.attr("href").isEmpty()) {
                    searchPanel.add(element.attr("href"));
                }
            }

            for (String url : searchPanel) {
                Elements select = Jsoup.connect(url).get().body().select("div.tn_alf_list > ul > li");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
