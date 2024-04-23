from rssService import RSSService
from fastapi import FastAPI

app = FastAPI()
rs = RSSService()

@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/RSSFeed/")
def read_item():
    news = rs.get_latest_news()
    return news

