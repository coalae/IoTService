package at.tuwien.g7t4.iotservice.logic;

import at.tuwien.g7t4.iotservice.model.TextFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TextFilesScraper implements Scraper {

    private static final String URL = "http://www.textfiles.com/science/";

    private Logger log = LoggerFactory.getLogger(TextFilesScraper.class);
    private List<TextFile> textFiles;

    public TextFilesScraper() {
        textFiles = new ArrayList<>();
    }

    public List<TextFile> read() {
        if (textFiles.isEmpty()) {
            try {
                Document doc = Jsoup.connect(URL).get(); // get whole html file
                Elements links = doc.select("a[href$=.txt]");
                for (Element link : links) {
                    String text = readText(link.attr("abs:href"));
                    if (!text.equals("Wrong Text Or Character")) {
                        TextFile textFile = TextFile.builder()
                                .fileName(link.text())
                                .url(link.attr("abs:href"))
                                .text(text)
                                .build();
                        textFiles.add(textFile);
                    }
                }
            } catch (IOException ex) {
                log.error(ex.getMessage());
            }
        }
        readProxyData();
        return textFiles;
    }

    private String readText(String url) {
        try {
            return Jsoup.connect(url).get().body().text();
        } catch (IOException ex) {
            log.error(url + " has wrong no text or wrong character!!");
            return "Wrong Text Or Character";
        }
    }

    private void readProxyData() {
        if (textFiles.isEmpty()) {
            try {
                ClassLoader classLoader = this.getClass().getClassLoader();
                File file = new File(classLoader.getResource("coldfusn.txt").getFile());
                String data = new String(Files.readAllBytes(file.toPath()));
                TextFile textFile = TextFile.builder()
                        .fileName("coldfusn.txt")
                        .url("From Proxy")
                        .text(data)
                        .build();
                textFiles.add(textFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
