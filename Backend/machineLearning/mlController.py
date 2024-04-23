from soooufrance import ModelService
from fastapi import FastAPI , Request




app = FastAPI()
ms = ModelService()
model = ms.launch_model_2()

@app.get("/")
def read_root():
    return {"Hello": "World"}
        
@app.get("/MachineLearning/predict")
def read_item(tweet : str):
    ms.predict(new_tweet=tweet,type="misc",model=model)  
      
@app.get("/MachineLearning/predict")
def read_item(tweet : str,style : str):
    ms.predict(new_tweet=tweet,type=style,model=model)
    
        
