# estimote-android-tutorial1
based on http://developer.estimote.com/android/tutorial/part-1-setting-up/

##原本的範例是以兩個iBeacon為例,用來顯示出兩種可能的組合,只在開發環境的logcat顯示.

我按照範例,更改了
*  region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);

多加了 major=12345
*  region = new Region("ranged region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 12345, null);

這樣子,另外一組三顆(major=33357) 就不會干擾到這次練習. 

##刪掉了預設的 Hello World, 加入 webView 到整個畫面,方便用來顯示三個遠近地點的資訊.

##程式的核心是使用  public void onBeaconsDiscovered(Region region, List<Beacon> list)
官網的API Doc
https://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/BeaconManager.RangingListener.html

