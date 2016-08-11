# Android-SVProgressHUD
SVProgressHUD For Android
精仿iOS的提示库 SVProgressHUD，api也几乎一样。

## Demo
![](https://github.com/saiwu-bigkoo/Android-SVProgressHUD/blob/master/preview/svprogresshuddemo.gif)

demo是用Module方式依赖，你也可以使用gradle 依赖:
```java
   compile 'com.bigkoo:svprogresshud:1.0.6'
```

### config in java code

```java
new SVProgressHUD(context).showInfoWithStatus(context, "这是提示");
```
使用起来就是这样简单，更多详情请看demo

>## 更新说明
>v1.0.6
 - 修复dismiss偶尔失效问题  <br />
 
>v1.0.4
 - 小优化  <br />

>v1.0.3
 - 解决属性冲突  <br />
 - 加入OnDismissListener  <br />

>v1.0.2
 - 修复内存泄漏问题  <br />
 - 支持更低的系统版本  <br />
