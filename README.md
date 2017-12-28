# tobacco-smokers-problem

##題目說明
Tobacco Smokers (TS) Problem的問題情境為，有三種材料Tobacco(菸草)、Cigarette papers(捲菸紙)、Matches(火柴)，一個Agent會固定發放其中兩種材料到Table(桌子)上，三個Smokers各持有一種無限的材料，之後會到Table(桌子)上搶奪資源，其中一個Smoker順利搶奪自己所沒有的其他兩種材料之後，會製作Cigarette(香菸)，一個Smoker製菸完成會馬上抽菸，之後叫醒Agent繼續發放三種材料中的其中兩種材料於桌上，如此循環。

##Deadlock時機
如果兩個Smokers擅自各搶奪桌上一種材料，如此一來，沒有Smoker可以完成製菸，也沒有Smoker可以抽菸後叫醒Agent繼續發放材料，至此階段發生Deadlock，沒有後續，動作完全停止。

##Deadlock解法
首先按照題目要求使用六個Threads(三個Agent、三個Smoker)
之後使用三個Semaphore控管Smokers，Agent確定發放的兩種材料後叫醒Smoker於Table取材料。再一個Semaphore讓Smoker叫醒Agent繼續發放材料。

#GUI
使用java swing製作，提供5個api供主程式使用。
1. public void put(String item1, String item2)
將兩種材料放入桌上
Parameters:
		item1, item2 - "tobacco", "matches" or "paper"
2. public void get(int ID, String item)
	Smokers從桌上取的材料
	Parameters:
		ID – Smokers ID
		Item - "tobacco", "matches" or "paper"
3. public void smoke(int ID)
	Smokers顯示抽菸圖示
	Parameters:
		ID – Smokers ID
4. public void anger(int ID)
	Deadlock發生，Smokers顯示生氣圖示。
Parameters:
		ID – Smokers ID
5. public void restart()
	抽完菸後呼叫，重置GUI
