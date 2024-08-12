import torch
import cv2
import sys
def detect():
    model = torch.hub.load(
        'ultralytics/yolov5', 'yolov5m')
    # 從 PyTorch Hub 下載 YOLOv5s 預訓練模型，可選用的模型有 yolov5s, yolov5m, yolov5x 等
    cap = cv2.VideoCapture('Pexels_Videos_2670_AdobeExpress.mp4')
    if not cap.isOpened():
        print("Cannot open camera")
        sys.exit()
    while True:
        ret, frame = cap.read()             # 讀取影片的每一幀
        if not ret:
            print("Cannot receive frame")   # 如果讀取錯誤，印出訊息
            break

        # 進行物件偵測
        results = model(frame)
        data=results.pandas().xyxy[0]
        person=data['name']=='person'
        person=data[person]
        pys=0
        num=0
        for p in range(person.shape[0]):
            if person.iat[p,4]>0.33:
                pys=person.iat[p,3]-person.iat[p,1]
                if pys<320:
                    num+=1
                    #print(results.pandas().xyxy[0])
                    cv2.rectangle(frame,(int(person.iat[p,0]),int(person.iat[p,1])),(int(person.iat[p,2]),int(person.iat[p,3])),(0, 255, 0),5)
        print("人數:"+str(num))
#        if person.shape[0] != 0:
#            print('人數：' + str(person.shape[0]))
        cv2.namedWindow('frame',0)
        cv2.resizeWindow('frame',720,720)
        cv2.imshow('frame',frame)     # 如果讀取成功，顯示該幀的畫面，要回傳img這張圖片，並且不能跳出迴圈
        
        if cv2.waitKey(1) == ord('q'):      # 每一毫秒更新一次，直到按下 q 結束
            break
    cap.release()                           # 所有作業都完成後，釋放資源
    cv2.destroyAllWindows()                 # 結束所有視窗
if __name__ == '__main__':
    detect()