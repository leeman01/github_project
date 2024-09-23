#  package import
import pandas as pd
import numpy as np
import pickle

from fastapi import FastAPI
import uvicorn
from pydantic import BaseModel
from starlette.responses import JSONResponse

# 설치 방법
# pip install FastAPI
# pip install fastapi uvicorn[standard]

# 설치 확인
# pip list install

# Model 생성
class Item(BaseModel): # DTO
    petalLength: float
    petalWidth: float
    sepalLength: float
    sepalWidth: float

app = FastAPI()

@app.post(path="/items", status_code=201)
def myiris(item : Item):
    # 학습 모델(pickle file) 로딩
    with open('./data.pickle', 'rb') as f:
        knn_model = pickle.load(f)
        dicted = dict(item)

        petalLength = dicted['petalLength']
        petalWidth = dicted['petalWidth']
        sepalLength = dicted['sepalLength']
        sepalWidth = dicted['sepalWidth']

        # 분석하기 위해 2차원 데이터로 변경
        X = np.array([[petalLength, petalWidth, sepalLength, sepalWidth]])
        target = ['setosa', 'vesicolor', 'virginica']

        pred = knn_model.predict(X)

        result = {"predict_result" : target[pred[0]]}

        print("---------- 예측 반환값 ----------", pred)
        print("---------- 예측 결과값 ----------", result)

    return JSONResponse(result)

if __name__ == '__main__':
    uvicorn.run(app, host='127.0.0.1', port=8000)