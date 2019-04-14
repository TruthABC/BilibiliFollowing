package bf.nt;

public class UserVideosCrawlerLauncher {

    public static void main(String[] args) {
        UserVideosCrawler crawler = new UserVideosCrawler();
        crawler.initializeSimpleMappingFromCSVV("users-1555232296771.csv");
        crawler.crawlBasicVideoInfoWhileSavingCSV("UserVideosBasic-1555232296771.csv");
    }

}
