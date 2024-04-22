# cordova-plugin-tapsdk

Cordova app 用 TapTap SDK，目前只接入`更新唤起`。

## Part I. 更新唤起
### 👇功能介绍，详情请阅读[TapTap文档](https://developer.taptap.cn/docs/sdk/update/features/)
- 更新唤起服务主要应用于在 TapTap 国内商店分发的游戏包体更新场景，目前仅支持`安卓`。
- 使用更新唤起功能，需接入 TapSDK，开发者在判断游戏需要强更后调用 TapSDK 更新接口，就会唤起 TapTap 客户端，引导用户前往 TapTap 完成更新。

#### 测试流程
1. 需模拟游戏实际更新场景，测试的包体版本号（Version Code）需低于游戏在服务器上设置的最新版本；
2. 当打开版本号较低的测试包体时，游戏需自行获取服务器上的版本号并能与当前测试包体的版本号进行判断对比；
3. 判断当前测试包体版本号较低的情况下，能成功调起更新唤起功能接口；
4. 功能验证完成。

### 集成前准备​

使用更新唤起功能前提需要通过 **TapTap 开发者中心 > 商店 > 游戏资料 > 商店资料** 中已经上传 APK， 发布设置为 **立即上线** 并通过 **审核**（开发者包如果暂时不想对外，发布状态选 **敬请期待** 或者 **预约**）。

### 环境要求
#### Android
- Android 5.0（API level 21）或更高版本
#### 插件附带
- TapUpdate_3.28.3.arr
- TapCommon_3.28.3.arr

### 权限说明
#### Android
依赖权限如下：
权限|权限名称|使用目的|权限申请时机
--|--|--|--
网络权限|INTERNET|用于正常网络访问|用户首次使用该功能时会申请权限
安装 APK 权限|REQUEST_INSTALL_PACKAGES|用于安装 Tap 客户端|用户首次使用该功能时会申请权限

插件将在`AndroidManifest.xml`中添加如下配置：
```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
<queries>
    <package android:name="com.taptap" />
    <package android:name="com.taptap.global" />
</queries>
```

## Part II. cordova-plugin-tapsdk 使用说明

首先，为了更新唤起的初始化，需要在开发者中心的应用配置获取 `Client ID` 和 `Client Token`，在安装插件时传入。

### 安装插件
- 通过npm安装
``` shell
cordova plugin add cordova-plugin-tapsdk --variable TAP_CLIENT_ID=XXX --variable TAP_CLIENT_TOKEN=XXX
```

- 通过git链接安装
``` shell
cordova plugin add https://github.com/tadazly/cordova-plugin-tapsdk.git --variable TAP_CLIENT_ID=XXX --variable TAP_CLIENT_TOKEN=XXX
```

- 本地调试
``` shell
cordova plugin add /Your/path/to/cordova-plugin-tapsdk --variable TAP_CLIENT_ID=XXX --variable TAP_CLIENT_TOKEN=XXX --link
```

- 移除插件
``` shell
cordova plugin rm cordova-plugin-tapsdk --variable TAP_CLIENT_ID=XXX --variable TAP_CLIENT_TOKEN=XXX
```

### 调用更新接口
你的项目判断完版本号后，确认需要强更时，便可以调用该接口👇
``` typeScript
/**
 * 自行判断版本号后，确定需要强更时调用
 * @param onCancel 用户取消更新回掉
 * @example TapSDK.updateGame(()=>log('user canceled'));
 */
function updateGame(onCancel?: () => void): void;
```
Example:
``` js
if (__currentVersion__ < __latestVersion__) {
    alert(`发现新版本（${__latestVersion__}），请立即更新！`, ()=>{
        switch (__channel__) {
            case 'taptap':
                TapSDK.updateGame(() => {
                    // 取消更新的回掉函数，可不传
                    console.log('Uh oh, 用户取消更新了哦');
                    location.reload(true);
                });
                break;
            default:
                window.open(__otherChannelDownloadLink__, '_system');
                location.reload(true);
                break;
        }
    }, '新版本提示', '立即前往');
}
```