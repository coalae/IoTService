package at.tuwien.g7t4.iotservice.controller;

import at.tuwien.g7t4.iotservice.logic.Scraper;
import at.tuwien.g7t4.iotservice.logic.TextFilesScraper;
import at.tuwien.g7t4.iotservice.model.TextFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Random;

@RestController
public class RestAccess {

    private final Scraper scraper;

    public RestAccess() {
        scraper = new TextFilesScraper();
    }

    @GetMapping(value = "/getFile")
    public TextFile getFile() {
        List<TextFile> textFiles = scraper.read();
        return textFiles.get(getRandomNumber(textFiles.size()));
    }

    private int getRandomNumber(int max) {
        return new Random().nextInt(max);
    }

}
