from typing import Union
from pydantic import BaseModel
from fastapi import FastAPI
from fooocus_requests import text_prompt

class Item(BaseModel):
    prompt: str
    image_number: int | None = 1

app = FastAPI()

@app.get("/") 
def read_root():
    return{"Hello":"World"}

@app.put("/fooocus/generate")
def generate(item: Item):
    url = text_prompt(item.prompt,item.image_number)
    return {"download_url":url}