# CurveChart

曲线图的简单自定义View

## 预览

<iframe height=498 width=510 src='http://player.youku.com/embed/XMTc0NTEzNDI2NA==' frameborder=0 'allowfullscreen'></iframe>

## 使用

### step1

引用aar包，最新版本链接：[curvechartlib-release.aar](https://github.com/Kyson/CurveChart/blob/master/ART/curvechartlib-release.aar)

### step2

用法和普通的View一样。

你可以自定义它的样式，通过如下属性

```xml
	app:ccv_DataSize="30"
    app:ccv_FillColor="#500000ff"
    app:ccv_GraduatedLineColor="#888888"
    app:ccv_GraduatedLineStrokeWidth="1"
    app:ccv_LineColor="#bbbbbb"
    app:ccv_LineStrokeWidth="2"
    app:ccv_MaxValueMulti="1.2"
    app:ccv_MinValueMulti="0.8"
    app:ccv_XTextPadding="50"
    app:ccv_XYColor="#bbbbbb"
    app:ccv_XYStrokeWidth="2"
    app:ccv_YFormat="%.1fMB"
    app:ccv_YLabelColor="#bbbbbb"
    app:ccv_YLabelSize="15"
    app:ccv_YPartCount="4"
    app:ccv_YTextPadding="0"
```

也可以调用`public void setUp(Config config)`方法，Config可以通过内部类Builder构建

### step3

提供的API

- `public void setUp(Config config)`
- `public void addData(float data)`