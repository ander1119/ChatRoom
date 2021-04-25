# ChatRoom
## Author 趙雋同

### 聊天界面UI
1. 透過RecyclerView以及控制顯示在左在右的holder實現可以上下滑動的聊天室，且訊息從下方開始由新到舊呈現，使用者與聊天對象的訊息不同方向顯示。  
2. 透過FloatActionButton和TextInputLayout實現固定位置的輸入列。  
![image](https://user-images.githubusercontent.com/52092804/115981835-636c8d00-a5c9-11eb-9877-8bd0ad0c0013.png)  

### 資料庫以及帳號登入系統
1. 將發送端以及接收端的訊息以及個人資料利用class message包裝傳入firebase，以便下次登入或重啟App時將聊天內容。  
2. 帳號登入的功能主要以firebase預設的登入api實現。   
![image](https://user-images.githubusercontent.com/52092804/115982204-dd9e1100-a5cb-11eb-900c-7bfe4b183f02.png)



