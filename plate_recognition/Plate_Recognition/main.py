import os
import cv2
import numpy as np

extension = '.jpg'
imagePath = 'images/WM-8888.jpg'
patternsPath = 'solid_patterns/'

rawImage = cv2.cvtColor(np.array(cv2.imread(imagePath)), cv2.COLOR_RGB2BGR)
contours, hierarchy = cv2.findContours(cv2.Canny(cv2.GaussianBlur(cv2.bilateralFilter(cv2.cvtColor(rawImage, cv2.COLOR_BGR2GRAY), 11, 17, 17), (5, 5), 0), 170, 200), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)  # 轉為灰階，去除背景雜訊，高斯模糊，取得邊緣，取得輪廓
rectangleContours = []  
for contour in sorted(contours, key=cv2.contourArea, reverse=True)[:30]: 
    if len(cv2.approxPolyDP(contour, 0.02 * cv2.arcLength(contour, True), True)) == 4: 
        rectangleContours.append(contour)
x, y, w, h = cv2.boundingRect(rectangleContours[0]) 
ret, plateImage = cv2.threshold(cv2.cvtColor(cv2.GaussianBlur(rawImage[y:y + h, x:x + w], (3, 3), 0), cv2.COLOR_RGB2GRAY), 0, 255, cv2.THRESH_OTSU) 

contours, hierarchy = cv2.findContours(plateImage, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE) 
letters = []
for contour in contours:  # 遍歷取得的輪廓
    rect = cv2.boundingRect(contour)
    if (rect[3] > (rect[2] * 1.5)) and (rect[3] < (rect[2] * 3.5) and (rect[2] > 10)): 
        letters.append(cv2.boundingRect(contour))
letter_images = []
for letter in sorted(letters, key=lambda s: s[0], reverse=False):  
    letter_images.append(plateImage[letter[1]:letter[1] + letter[3], letter[0]:letter[0] + letter[2]]) 

#from matplotlib import pyplot as plt
#for i, j in enumerate(letter_images):
#    plt.subplot(1, len(letter_images), i + 1)
#    plt.imshow(letter_images[i], cmap='gray')
#plt.show()

# 匹配車牌文字 Matching License Plate Number
results = []
for index, letter_image in enumerate(letter_images):
    best_score = []
    patterns = os.listdir(patternsPath)
    for filename in patterns:  # 讀取資料夾下所有的圖片
        ret, pattern_img = cv2.threshold(cv2.cvtColor(cv2.imdecode(np.fromfile(patternsPath + filename, dtype=np.uint8), 1), cv2.COLOR_RGB2GRAY), 0, 255, cv2.THRESH_OTSU)  # 將範本進行格式轉換，再獲得Binary圖
        pattern_img = cv2.resize(pattern_img, (letter_image.shape[1], letter_image.shape[0]))  
        best_score.append(cv2.matchTemplate(letter_image, pattern_img, cv2.TM_CCOEFF)[0][0])  # 範本匹配，返回匹配得分
    i = best_score.index(max(best_score))
    results.append(patterns[i])
print("".join(results).replace(extension, ""))

img=cv2.imread(imagePath)
cv2.imshow('Plate_Recognition',img)
cv2.waitKey(0)
cv2.destroyAllWindows()