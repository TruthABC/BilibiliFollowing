package bf.nt;

import java.util.Date;

public class UserDetailCrawlerLauncher {

    public static void main(String[] args) {
        UserDetailCrawler crawler= new UserDetailCrawler();
        crawler.initializeTargetNamesFromCSVV("30plus.txt");
        crawler.initializeSimpleMappingFromCSVV("followings-1554013692072.csv");
        crawler.crawlAndFillDetails();
        System.out.print("Starting Saving to Files.");
        long time = new Date().getTime();
        crawler.saveDetailsToCSVV("users-" + time + ".csv");
        crawler.saveTagsToCSVV("tags-" + time + ".csv");
    }

}
