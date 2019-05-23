package finiteautomaton;

import java.io.IOException;
import java.lang.reflect.Array;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Deco
 */
public class Main {

    public static void main(String[] args) {
//       Gui gui = new Gui();
//       gui.setVisible(true);       
        try {
            Document doc = Jsoup.connect("https://www.w3schools.com/xml/simple.xml").get();
            Elements table = doc.select("food");
            for(Element e : table){
                System.out.println(e.select("name").first().text());
            }
//
//            for (Element tagTR : table) {
//                System.out.println(tagTR.text());
//            }
//            Element e = table.first();
//            System.out.println(e.text());
//            for(Element e : table){
//                e.select("td");
//                e.text();
//            }
//                      
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
