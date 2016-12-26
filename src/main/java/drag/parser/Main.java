package drag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by evseevvd on 26.12.16.
 */
public class Main {

    public static void main(String[] args) throws SQLException, IOException {
            Document document = Jsoup.connect("http://www.rlsnet.ru").get();
            Elements alphaList = document.body().select("div.tn_letters_main > div.tn_letters").get(0).getAllElements();

            Connection connection = DriverManager.getConnection("jdbc:postgresql://10.254.202.49:10054/rlsnet", "postgres", "postgres");

            for (Element element : alphaList) {
                if (!element.attr("href").isEmpty()) {
                    String url = element.attr("href");
                    //Список лекарств на определенную букву
                    Elements pills = Jsoup.connect(url).get().body().select("div.tn_alf_list > ul > li > a:not(:has(.monetkaElem))");

                    Set<String> indexes = new HashSet<>();

                    for (Element pill : pills) {
                        Matcher indexMatcher = Pattern.compile("\\d+").matcher(pill.attr("href"));
                        indexMatcher.find();
                        String index = indexMatcher.group();

                        //TODO мб есть смысл проверять урлы, на тот случай если пилульки разные а ID один

                        if (pill.text().length() < 2000 && !indexes.contains(index)) {

                            PreparedStatement statement = connection.prepareStatement("INSERT INTO RLS.PILL_STORE (ID, NAME, URL) VALUES (?, ?, ?)");
                            statement.setString(1, index);
                            statement.setString(2, pill.text());
                            statement.setString(3, pill.attr("href"));
                            statement.execute();

                            indexes.add(index);
                        }
                    }

                    indexes.clear();
                }
            }
    }
}
