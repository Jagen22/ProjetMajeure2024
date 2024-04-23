import pandas as pd
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import mean_squared_error
import matplotlib.pyplot as plt
from sklearn.model_selection import learning_curve
import emoji
import random
from sklearn.model_selection import train_test_split,GridSearchCV,learning_curve
from sklearn.ensemble import RandomForestRegressor
from sklearn.metrics import mean_squared_error
from sklearn.preprocessing import StandardScaler
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
from sklearn.preprocessing import OneHotEncoder
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split, GridSearchCV, learning_curve
from sklearn.ensemble import GradientBoostingRegressor
from sklearn.metrics import mean_squared_error
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split, GridSearchCV, learning_curve
from xgboost import XGBRegressor
from sklearn.metrics import mean_squared_error
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
import os

def isThereEmoji(tweet):
  ret = len(emoji.emoji_list(tweet)) != 0
  return ret

def generate_likes(df):
  likes = []
  for i in range(0,len(df)):
    n = random.randint(0,10)
    likes.append(n)

  for i, row in df.iterrows():
    if isThereEmoji(row["Tweet"]):
      likes[i] += random.randint(55,60)

    if "💪" in row["Tweet"]:
      likes[i] += random.randint(50,55)

    if row["Type"] == "sports":
      likes[i] += random.randint(70,75)

    if row["Type"] == "tech":
      likes[i] += random.randint(7,12)

    if len(row["Tweet"]) > df['Tweet'].str.len().mean():
      likes[i] -= random.randint(0,20)
      likes[i] = max(likes[i],0)

    if "!" in row["Tweet"] :
      likes[i] += random.randint(0,10)

  return(likes)


def generate_retweets(df):
  retweets = []
  for i in range(0,len(df)):
    n = random.randint(0,15)
    retweets.append(n)

  i=0

  for i, row in df.iterrows():

    if row["Type"] == "tech":
      retweets[i] += random.randint(15,25)

    if row["Type"] == "sports":
      retweets[i] += random.randint(0,5)

    if "#" in row["Tweet"]:
      retweets[i] += random.randint(25,35)

    if "Share" in row["Tweet"] or "?" in row["Tweet"] or "share" in row["Tweet"]:
      retweets[i] += random.randint(40,50)

    if len(row["Tweet"]) > 150:
      retweets[i] += random.randint(7,12)

    i=1
  return retweets

def isThereHashtag(tweet):
  if "#" in tweet:
    return True
  else:
    return False
  
def isThereShare(tweet):
  if "Share" in tweet or "share" in tweet:
    return True
  else:
    return False
  
def plot_learning_curve(model, X, y, title="Learning Curve", cv=None, n_jobs=None, train_sizes=np.linspace(.1, 1.0, 5)):
    """
    Generate a simple plot of the test and training learning curve.

    Parameters:
    - model: The machine learning model
    - X: The training data
    - y: The target values
    - title: Title of the plot (default="Learning Curve")
    - cv: Cross-validation strategy (default=None, uses 3-fold cross-validation)
    - n_jobs: Number of jobs to run in parallel (default=None, uses all processors)
    - train_sizes: Relative or absolute numbers of training examples that will be used to generate the learning curve
    """

    plt.figure(figsize=(10, 6))

    train_sizes, train_scores, test_scores = learning_curve(
        model, X, y, cv=cv, n_jobs=n_jobs, train_sizes=train_sizes, scoring='neg_mean_squared_error'
    )

    train_scores_mean = -np.mean(train_scores, axis=1)
    train_scores_std = np.std(train_scores, axis=1)
    test_scores_mean = -np.mean(test_scores, axis=1)
    test_scores_std = np.std(test_scores, axis=1)

    plt.fill_between(train_sizes, train_scores_mean - train_scores_std, train_scores_mean + train_scores_std, alpha=0.1, color="r")
    plt.fill_between(train_sizes, test_scores_mean - test_scores_std, test_scores_mean + test_scores_std, alpha=0.1, color="g")

    plt.plot(train_sizes, train_scores_mean, 'o-', color="r", label="Training score")
    plt.plot(train_sizes, test_scores_mean, 'o-', color="g", label="Cross-validation score")

    plt.xlabel("Training examples")
    plt.ylabel("Negative Mean Squared Error")
    plt.title(title)
    plt.legend(loc="best")
    plt.grid(True)
    plt.show()

class ModelService():
  def launch_model_1(self,df):
    """### Model 1 : Forest Regressor"""
    # Extract features and target variables
    X = df[['Tweet','Type','Emoji','HashTag','Share']]
    y_likes = df['Likes']
    y_retweets = df['Retweets']

    X_train_likes, X_test_likes, y_likes_train, y_likes_test = train_test_split(
        X, y_likes, test_size=0.2, random_state=42
    )

    X_train_retweets, X_test_retweets, y_retweets_train, y_retweets_test = train_test_split(
        X, y_retweets, test_size=0.2, random_state=42
    )

    # Define preprocessing steps
    preprocessor = ColumnTransformer(
        transformers=[
            ('Tweet', TfidfVectorizer(), 'Tweet'),
            ('Type', OneHotEncoder(), ['Type'])
            #('Followers', StandardScaler(), ['Followers'])  # 'passthrough' for numerical feature
        ]
    )

    param_grid = {
        'regressor__n_estimators': [150],
        'regressor__max_depth': [3],
    }

    param_grid = {
        'regressor__n_estimators': [200],
        'regressor__max_depth': [3],
    }

    # Define the model
    model_likes = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', RandomForestRegressor())
    ])

    model_retweets = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', RandomForestRegressor())
    ])

    grid_search_likes = GridSearchCV(model_likes, param_grid, cv=3, scoring='neg_mean_squared_error')
    grid_search_retweets = GridSearchCV(model_retweets, param_grid, cv=3, scoring='neg_mean_squared_error')

    plot_learning_curve(grid_search_likes, X_train_likes, y_likes_train, title="Learning Curve - Likes")
    plot_learning_curve(grid_search_retweets, X_train_retweets, y_retweets_train, title="Learning Curve - Retweets")

    # Train the models with hyperparameter tuning
    grid_search_likes.fit(X_train_likes, y_likes_train)
    grid_search_retweets.fit(X_train_retweets, y_retweets_train)

    # Make predictions
    likes_predictions = grid_search_likes.predict(X_test_likes)
    retweets_predictions = grid_search_retweets.predict(X_test_retweets)

    # Evaluate the models
    mse_likes = mean_squared_error(y_likes_test, likes_predictions)
    mse_retweets = mean_squared_error(y_retweets_test, retweets_predictions)

    print(f'Mean Squared Error (Likes): {mse_likes}')
    print(f'Mean Squared Error (Retweets): {mse_retweets}')

    print('Best hyperparameters for Likes:', grid_search_likes.best_params_)
    print('Best hyperparameters for Retweets:', grid_search_retweets.best_params_)

    return(grid_search_likes,grid_search_retweets)

  """### Model 2 Gradient Boost"""
  def launch_model_2(self):
    df = self.df
    # Extract features and target variables
    X = df[['Tweet','Type','Emoji','HashTag','Share']]
    y_likes = df['Likes']
    y_retweets = df['Retweets']

    # Split the data
    X_train_likes, X_test_likes, y_likes_train, y_likes_test = train_test_split(
        X, y_likes, test_size=0.2, random_state=42
    )

    X_train_retweets, X_test_retweets, y_retweets_train, y_retweets_test = train_test_split(
        X, y_retweets, test_size=0.2, random_state=42
    )

    # Define preprocessing steps
    preprocessor = ColumnTransformer(
        transformers=[
            ('tweet', TfidfVectorizer(), 'Tweet'),
            ('type', OneHotEncoder(), ['Type']),
            #('followers', 'passthrough', ['Followers'])
        ]
    )

    # Define the models with hyperparameter tuning using GridSearchCV
    param_grid_like = {
        'regressor__n_estimators': [50,100,150],
        'regressor__learning_rate': [0.01,0.1,0.2],
        'regressor__max_depth': [3,4,5],
    }

    param_grid_retweet = {
        'regressor__n_estimators': [50,100,150],
        'regressor__learning_rate': [0.01,0.1,0.2],
        'regressor__max_depth': [3,4,5],
    }

    model_likes = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', GradientBoostingRegressor())
    ])

    model_retweets = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', GradientBoostingRegressor())
    ])

    grid_search_likes = GridSearchCV(model_likes, param_grid_like, cv=3, scoring='neg_mean_squared_error')
    grid_search_retweets = GridSearchCV(model_retweets, param_grid_retweet, cv=3, scoring='neg_mean_squared_error')

    # Plot learning curves
    plot_learning_curve(grid_search_likes, X_train_likes, y_likes_train, title="Learning Curve - Likes")
    plot_learning_curve(grid_search_retweets, X_train_retweets, y_retweets_train, title="Learning Curve - Retweets")

    # Train the models with hyperparameter tuning
    grid_search_likes.fit(X_train_likes, y_likes_train)
    grid_search_retweets.fit(X_train_retweets, y_retweets_train)

    # Make predictions
    likes_predictions = grid_search_likes.predict(X_test_likes)
    retweets_predictions = grid_search_retweets.predict(X_test_retweets)

    # Evaluate the models
    mse_likes = mean_squared_error(y_likes_test, likes_predictions)
    mse_retweets = mean_squared_error(y_retweets_test, retweets_predictions)

    print(f'Mean Squared Error (Likes): {mse_likes}')
    print(f'Mean Squared Error (Retweets): {mse_retweets}')
    print('Best hyperparameters for Likes:', grid_search_likes.best_params_)
    print('Best hyperparameters for Retweets:', grid_search_retweets.best_params_)
    
    return (grid_search_likes,grid_search_retweets)

  """### Model 3 (XGBoost)"""

  def launch_model_3(self):
    # Extract features and target variables
    df = self.df
    X = df[['Tweet','Type','Emoji','HashTag','Share']]
    y_likes = df['Likes']
    y_retweets = df['Retweets']

    # Split the data
    X_train_likes, X_test_likes, y_likes_train, y_likes_test = train_test_split(
        X, y_likes, test_size=0.2, random_state=42
    )

    X_train_retweets, X_test_retweets, y_retweets_train, y_retweets_test = train_test_split(
        X, y_retweets, test_size=0.2, random_state=42
    )

    # Define preprocessing steps
    preprocessor = ColumnTransformer(
        transformers=[
            ('tweet', TfidfVectorizer(), 'Tweet'),
            ('type', OneHotEncoder(), ['Type']),
            #('followers', 'passthrough', ['Followers'])
        ]
    )

    # Define the models with hyperparameter tuning using GridSearchCV
    param_grid_like = {
        'regressor__n_estimators': [50,100,150],
        'regressor__learning_rate': [0.01,0.1,0.2],
        'regressor__max_depth': [3,4,5],
    }

    param_grid_retweet = {
        'regressor__n_estimators': [50,100,150],
        'regressor__learning_rate': [0.01,0.1,0.2],
        'regressor__max_depth': [3,4,5],
    }

    model_likes = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', XGBRegressor())
    ])

    model_retweets = Pipeline([
        ('preprocessor', preprocessor),
        ('regressor', XGBRegressor())
    ])

    grid_search_likes = GridSearchCV(model_likes, param_grid_like, cv=3, scoring='neg_mean_squared_error')
    grid_search_retweets = GridSearchCV(model_retweets, param_grid_retweet, cv=3, scoring='neg_mean_squared_error')

    # Plot learning curves
    plot_learning_curve(grid_search_likes, X_train_likes, y_likes_train, title="Learning Curve - Likes")
    plot_learning_curve(grid_search_retweets, X_train_retweets, y_retweets_train, title="Learning Curve - Retweets")

    # Train the models with hyperparameter tuning
    grid_search_likes.fit(X_train_likes, y_likes_train)
    grid_search_retweets.fit(X_train_retweets, y_retweets_train)

    # Make predictions
    likes_predictions = grid_search_likes.predict(X_test_likes)
    retweets_predictions = grid_search_retweets.predict(X_test_retweets)

    # Evaluate the models
    mse_likes = mean_squared_error(y_likes_test, likes_predictions)
    mse_retweets = mean_squared_error(y_retweets_test, retweets_predictions)

    print(f'Mean Squared Error (Likes): {mse_likes}')
    print(f'Mean Squared Error (Retweets): {mse_retweets}')
    print('Best hyperparameters for Likes:', grid_search_likes.best_params_)
    print('Best hyperparameters for Retweets:', grid_search_retweets.best_params_)
    
    return (grid_search_likes,grid_search_retweets)

  def predict(self,new_tweet,type,model):
                
    new_tweet_df = pd.DataFrame({'Tweet': [new_tweet], 'Type': [type]})
    
    new_tweet_df['HashTag'] = new_tweet_df['Tweet'].apply(isThereHashtag)
    new_tweet_df['HashTag'] = new_tweet_df['HashTag'].astype(int)

    new_tweet_df["Emoji"] = new_tweet_df['Tweet'].apply(isThereEmoji)
    new_tweet_df['Emoji'] = new_tweet_df['Emoji'].astype(int)

    new_tweet_df["Share"] = new_tweet_df['Tweet'].apply(isThereShare)
    new_tweet_df["Share"] = new_tweet_df['Share'].astype(int)
    
    # Make predictions
    new_tweet_likes_prediction = model[0].predict(new_tweet_df)
    new_tweet_retweets_prediction = model[1].predict(new_tweet_df)

    print(f'Predicted Likes for the new tweet: {new_tweet_likes_prediction[0]}')
    print(f'Predicted Retweets for the new tweet: {new_tweet_retweets_prediction[0]}')
    
  def __init__(self):
        
    self.model1 = ""
    self.model2 = ""
    self.model3 = ""
    
    self.current_model = ""

    misc=pd.read_csv("data"+os.sep+"tweets.csv")
    tech=pd.read_csv("data"+os.sep+"tech.csv")
    sports=pd.read_csv("data"+os.sep+"sports.csv")

    misc.insert(1, "Type", ["misc" for _ in range(len(misc))], True)
    tech.insert(1, "Type", ["tech" for _ in range(len(tech))], True)
    sports.insert(1, "Type", ["sports" for _ in range(len(sports))], True)

    misc = pd.DataFrame({"Tweet":misc["Tweet"],"Type":misc["Type"]})
    sports = pd.DataFrame({"Tweet":sports["Tweet"],"Type":sports["Type"]})
    tech = pd.DataFrame({"Tweet":tech["Tweet"],"Type":tech["Type"]})

    df = pd.concat([misc,sports,tech])

    df = df.reset_index()

    followers = []
    for i in range(0,len(df)):
      n = random.randint(0,10000)
      followers.append(n)

    df["Followers"] = followers

    likes = generate_likes(df)
    retweets = generate_retweets(df)

    df.insert(len(df.columns), "Likes",likes , True)

    df.insert(len(df.columns), "Retweets",retweets , True)

    df = df.drop('index',axis=1)

    df.to_csv('out.csv')

    df['HashTag'] = df['Tweet'].apply(isThereHashtag)
    df['HashTag'] = df['HashTag'].astype(int)

    df["Emoji"] = df['Tweet'].apply(isThereEmoji)
    df['Emoji'] = df['Emoji'].astype(int)

    df["Share"] = df['Tweet'].apply(isThereShare)
    df["Share"] = df['Share'].astype(int)
    
    self.df = df

    return