package bf;

import bf.entity.User;

import java.util.ArrayList;
import java.util.Date;

public class CrawlerLauncher {

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.initializeFirstUser(new User("257629", "Mr.Truth", "", new ArrayList<>()));
        crawler.crawlTillDepth(3);
        System.out.print("Starting Saving to Files.");
        long time = new Date().getTime();
        crawler.saveToCSVV("followings-" + time + ".csv");
        crawler.saveToCSVVEdge("edges-" + time + ".csv");
    }

}
