package bf.nt;

public class VideoTagCrawlerLauncher {

    public static void main(String[] args) {
        VideoTagCrawler crawler = new VideoTagCrawler();
        crawler.crawlFromDeailedWithVideoTagWhileSavingCSV(
                "UserVideosDetailed-1555232296771-all.csv",
                "UserVideosDetailedWithTag-1555232296771.csv"
        );
    }

}
