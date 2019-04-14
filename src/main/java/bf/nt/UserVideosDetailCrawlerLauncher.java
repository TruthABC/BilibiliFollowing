package bf.nt;

public class UserVideosDetailCrawlerLauncher {

    public static void main(String[] args) {
        UserVideosDetailCrawler crawler = new UserVideosDetailCrawler();
        crawler.initializeBasicFromCSVV("UserVideosBasicCleaned-1555232296771.csv");
        crawler.crawlDetailedVideoInfoWhileSavingCSV("UserVideosDetailed-1555232296771.csv");
    }

}
