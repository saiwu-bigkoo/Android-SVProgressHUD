# Android-SVProgressHUD
SVProgressHUD For Android
精仿iOS的提示库 SVProgressHUD，api也几乎一样。

## Demo
![](https://github.com/saiwu-bigkoo/Android-SVProgressHUD/blob/master/preview/svprogresshuddemo.gif)

demo是用Module方式依赖，你也可以使用gradle 依赖:
```java
   compile 'com.bigkoo:svprogresshud:1.0.0'
```

### config in java code
```java
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.None);
SVProgressHUD.showWithMaskType(context,SVProgressHUD.SVProgressHUDMaskType.Black);
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.BlackCancel);
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.Clear);
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.ClearCancel);
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.Gradient);
SVProgressHUD.showWithMaskType(context, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
```
```java
SVProgressHUD.showInfoWithStatus(context, "这是提示");
```
使用起来就是这样简单，更多详情请看demo
