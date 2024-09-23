# package import
import pandas as pd
import pickle

from sklearn.datasets import load_iris
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split

# 데이터 로딩
iris = load_iris()

# print(iris)

# DataFrame으로 만들기
iris_df = pd.DataFrame(iris['data'], columns=iris['feature_names'])
target = iris['target_names']

# print(iris_df)
# print(target)

# x와 y 처리
X = iris_df
y = iris['target']

# 섞어서 나누는 작업
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=1)

# print(X_train)
# print(y_train)

# model 생성 및 학습
knn_model = KNeighborsClassifier(n_neighbors=3)
knn_model.fit(X_train, y_train)

# 예측
knn_model.predict(X_test)

# score 확인
print(knn_model.score(X_test, y_test)) # 98%

pred = knn_model.predict([[5.1, 3.5, 1.4, 0.2]]) # 0번 데이터(세토사)
print('예측 결과: ', target[pred[0]])

# 피클 저장
with open('data.pickle', 'wb') as f:
    pickle.dump(knn_model, f, pickle.HIGHEST_PROTOCOL)

print('끝')

# ctrl + `(백틱) = open terminal
# cmd terminal: local 설치한 python
# python ./iris.py = ./iris.py을 실행하라는 의미
# (terminal에서) python + enter = 파이썬 개발을 위한 raw 프로그램 실행 (x)
# dir