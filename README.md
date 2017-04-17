# UniversalButton
- Android 自定义多样式Button
- 目的：解决重复引用selector,减少图片资源占用apk瘦身  

   ![image](https://github.com/xing609/UniversalButton/blob/master/app/assets/universal_button.gif)

##下面只简单介绍几个常用的属性##

**1.只设置背景**
    
    <com.star.widget.UniversalBtn  
         android:layout_width="70dp"  
         android:layout_height="70dp"  
         android:layout_gravity="center"  
         app:normalDrawable="@drawable/ic_launcher" />

**2.只设置正常颜色**   
    
    <com.star.widget.UniversalBtn  
         android:id="@+id/normalBtn"  
         android:layout_width="wrap_content"  
         android:layout_height="50dp"  
         android:layout_gravity="center"  
         android:layout_marginTop="10dp"  
         android:text="默认矩形一种颜色"  
         android:paddingLeft="15dp"  
         android:paddingRight="15dp"  
         app:normalSolid="@color/c1" />


**3.正常引用selector,改变字体按下颜色**    
     
    <com.star.widget.UniversalBtn
         android:id="@+id/selectedBtn"  
         android:layout_width="60dp"  
         android:layout_height="60dp"  
         android:layout_gravity="center"  
         android:layout_marginTop="10dp"  
         android:background="@drawable/sel_btn_love"  
         android:text="图片"  
         android:textSize="12sp"  
         app:normalTextColor="@color/c1"  
         app:selectedTextColor="@color/c4" />



**4.设置左边圆角，右边直角**    
     
     <com.star.widget.UniversalBtn
          android:id="@+id/btnLeftCircle"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="10dp"
          android:text="左边45弧度圆角"
          android:textAllCaps="false"
          app:normalSolid="@color/c1"
          app:roundButtonLeftBottomRadius="@dimen/radius_45"
          app:roundButtonLeftTopRadius="@dimen/radius_45"
          app:roundButtonRightBottomRadius="@dimen/radius_10" />



**5.设置圆角，背景跟颜色同时改变**    
     
     <com.star.widget.UniversalBtn
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="10dp"
          android:text="45弧度圆角一种颜色带按下效果"
          app:normalSolid="@color/c1"
          app:normalTextColor="@color/c6"
          app:selectedTextColor="@color/c4"
          app:roundButtonRadius="@dimen/radius_45" />

**6.设置圆角，描边变色**    
     
     <com.star.widget.UniversalBtn
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:layout_marginTop="10dp"
          android:text="45弧度圆角按下效果变色边框"
          app:normalSolid="@color/c1"
          app:normalStroke="@color/c5"
          app:pressedStroke="@color/c3"
          app:roundButtonRadius="@dimen/radius_45"
          app:strokeWidth="1dp" />


----------
欢迎加入Android 学习交流群：**413893967**


