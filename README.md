# tobacco-smokers-problem

##題目說明
Tobacco Smokers (TS) Problem的問題情境為，有三種材料Tobacco(菸草)、Cigarette papers(捲菸紙)、Matches(火柴)，一個Agent會固定發放其中兩種材料到Table(桌子)上，三個Smokers各持有一種無限的材料，之後會到Table(桌子)上搶奪資源，其中一個Smoker順利搶奪自己所沒有的其他兩種材料之後，會製作Cigarette(香菸)，一個Smoker製菸完成會馬上抽菸，之後叫醒Agent繼續發放三種材料中的其中兩種材料於桌上，如此循環。
##Deadlock時機：
如果兩個Smokers擅自各搶奪桌上一種材料，如此一來，沒有Smoker可以完成製菸，也沒有Smoker可以抽菸後叫醒Agent繼續發放材料，至此階段發生Deadlock，沒有後續，動作完全停止。