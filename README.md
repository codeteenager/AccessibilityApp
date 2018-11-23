## AccessibilityService
AccessibilityService是用户可选服务，AccessibilityService由系统在后台运行,并接收回调函数AccessibilityEvents。此类事件表示一些状态转换的用户界面,
例如,界面已经改变, 点击一个按钮,等等。这种服务可以选择请求的能力查询活动窗口的内容。
开发一个可访问性服务需要扩展这个类并实现其抽象方法。

AccessibilityService由AccessibilityServiceInfo来描述。
系统通知的AccessibilityService, AccessibilityEvents的节点信息封装在这个类中。

## 用法
+ onServiceConnected():服务连接时，也就是第一次打开时调用，这里我们可以初始化常量和标签等
+ onCreate():服务创建时调用，初始化一些数据
+ onDestroy():服务消亡是，或者用户关闭时，调用，这里我们可以去做些业务相关的释放任务，
+ onAccessibilityEvent():监测到内容节点时调用
+ disableSelf():自身关闭时主动调用
+ onInterrupt():终止accessibility service时调用。

## 相关配置
在AndroidManifest.xml文件中声明AccessibilityService时，有如下几点必须注意：
+ android:name属性为自定义MyAccessibilityService的绝对类名；
+ 声明"android.permission.BIND_ACCESSIBILITY_SERVICE"权限;
+ 使用<meta-data../>设定AccessibilityService的配置信息，其中，android:name属性的值必须为"android.accessibilityservice"、android:resource属性的值为配置信息，保存在res/xml目录下的accessibility_settings.xml文件，代码如下：
```java
<?xml version="1.0" encoding="utf-8"?>
<accessibility-service
	 android:packageNames="com.android.packageinstaller"
     android:description="@string/accessibility_service_description"
     android:accessibilityEventTypes="typeAllMask"
     android:accessibilityFeedbackType="feedbackGeneric"
     android:accessibilityFlags="flagDefault"
     android:notificationTimeout="100"
     android:canRetrieveWindowContent="true"
  xmlns:android="http://schemas.android.com/apk/res/android" />
```

其中，android:packageNames指明AccessibilityService监听哪个应用程序下的窗口活动，
这里写com.android.packageinstaller表示监听Android系统的安装界面。
android:description指定在无障碍服务当中显示给用户看的说明信息。
android:accessibilityEventTypes指定我们在监听窗口中可以模拟哪些事件，
这里写typeAllMask表示所有的事件都能模拟。
