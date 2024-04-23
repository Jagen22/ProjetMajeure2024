import feedparser

class RSSService():
    def __init__(self):
        self.news_feed_sport = feedparser.parse('https://www.france24.com/en/sport/rss')
        self.news_feed_tech  = feedparser.parse('https://www.france24.com/en/business-tech/rss')

    def get_latest_news(self):
        ret = []
        for entry in self.news_feed_sport.entries[0:5]:
            ret.append(entry.title)
        for entry in self.news_feed_tech.entries:
            ret.append(entry.title)
            
        return ret
    

    
