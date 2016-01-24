//package com.bryan.example.summarize;
//
///**
// * 关于Matrix的小结


//http://blog.csdn.net/wang_shaner/article/details/7859637
// * @author bryan
// *
// */
/**
 * postxxx()方法：每个变换都只是一种新增的变换，在原有的基础上做一些小的变换
 * setxxxx()方法：清除原有的状态，只剩下Matrix中的变换
 */
//public class Matrix
//{
//    Matrix的操作，总共分为translate（平移），rotate（旋转），scale（缩放）和skew（倾斜）四种，每一种变换在
//
//    Android的API里都提供了set, post和pre三种操作方式,除了translate，其他三种操作都可以指定中心点。
//
//        set是直接设置Matrix的值，每次set一次，整个Matrix的数组都会变掉。
//
//        post是后乘，当前的矩阵乘以参数给出的矩阵。可以连续多次使用post，来完成所需的整个变换。例如，要将一个图片旋
//    转30度，然后平移到(100,100)的地方，那么可以这样做:
//    < type="application/x-shockwave-flash" width="14" height="15" src="http://www.javaeye.com/javascripts/syntaxhighlighter/clipboard_new.swf" src="http://www.javaeye.com/javascripts/syntaxhighlighter/clipboard_new.swf" flashvars="clipboard=Matrix%20m%20%3D%20new%20Matrix()%3B%0A%0Am.postRotate(30)%3B%0A%0Am.postTranslate(100%2C%20100)%3B%20%20" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="15" width="14">
//    Matrix m =  new  Matrix();  
//      
//    m.postRotate(30 );  
//      
//    m.postTranslate(100 ,  100 );    
//    [java] view plaincopy 
//    Matrix m = new Matrix();  
//      
//    m.postRotate(30);  
//      
//    m.postTranslate(100, 100);    
//     
//    这样就达到了想要的效果。
//
//        pre是前乘，参数给出的矩阵乘以当前的矩阵。所以操作是在当前矩阵的最前面发生的。例如上面的例子，如果用pre的话
//
//    ，就要这样:
//    < type="application/x-shockwave-flash" width="14" height="15" src="http://www.javaeye.com/javascripts/syntaxhighlighter/clipboard_new.swf" src="http://www.javaeye.com/javascripts/syntaxhighlighter/clipboard_new.swf" flashvars="clipboard=Matrix%20m%20%3D%20new%20Matrix()%3B%0A%0Am.setTranslate(100%2C%20100)%3B%0A%0Am.preRotate(30)%3B" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="15" width="14">
//    Matrix m =  new  Matrix();  
//      
//    m.setTranslate(100 ,  100 );  
//      
//    m.preRotate(30 );  
//    [java] view plaincopy 
//    Matrix m = new Matrix();  
//      
//    m.setTranslate(100, 100);  
//      
//    m.preRotate(30);  
//        旋转、缩放和倾斜都可以围绕一个中心点来进行，如果不指定，默认情况下，是围绕(0,0)点来进行。
//
//    package com.eoe android .demo.testcode;
//    import android.app .Activity;
//    import android.graphics.Bitmap;
//    import android.graphics.BitmapFactory;
//    import android.graphics.Matrix;
//    import android.graphics.drawable.BitmapDrawable;
//    import android.os.Bundle;
//    import android.view.View Group.Layout Params;
//    import android.widget.ImageView;
//    import android.widget.LinearLayout;
//    import android.widget.ImageView.ScaleType;
//    public class bitmaptest extends Activity {
//    public void onCreate(Bundle icicle) {
//            super.onCreate(icicle);
//            setTitle("eoeAndroid教程: 缩放和旋转图片 -by:IceskYsl");
//            LinearLayout linLayout = new LinearLayout(this);
//            // 加载需要操作的图片，这里是eoeAndroid的logo图片
//            Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
//                   R.drawable.eoe_android);
//            //获取 这个图片的宽和高
//            int width = bitmapOrg.getWidth();
//            int height = bitmapOrg.getHeight();
//            //定义 预转换成的图片的宽度和高度
//            int newWidth = 200;
//            int newHeight = 200;
//            //计算缩放率，新尺寸除原始尺寸
//            float scaleWidth = ((float) newWidth) / width;
//            float scaleHeight = ((float) newHeight) / height;
//            // 创建操作图片用的matrix对象
//            Matrix matrix = new Matrix();
//            // 缩放图片动作
//            matrix.postScale(scaleWidth, scaleHeight);
//            //旋转图片 动作
//            matrix.postRotate(45);
//            // 创建新的图片
//            Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
//                              width, height, matrix, true);
//            //将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//            BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
//            //创建一个ImageView
//            ImageView imageView = new ImageView(this);
//            // 设置 ImageView的图片为上面转换的图片
//            imageView.setImageDrawable(bmd);
//            //将图片居中显示
//            imageView.setScaleType(ScaleType.CENTER);
//            //将ImageView添加到布局模板中
//            linLayout.addView(imageView,
//              new LinearLayout.LayoutParams(
//                          LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT
//                    )
//            );
//            // 设置为本activity 的模板
//            setContentView(linLayout);
//        }
//    }
//    复制代码
//
//
//    这里没用定义XML 布局模板，而是直接在代码中生成了需要的模板和视图组件，你可以可以定义XML模板，其他原理是一样的。
//
//
//    在游戏 开发 中，自定义 View 是一个相当重要的功能 ，下面先讲一讲在View上绘制所需的四个基本主键：
//    Bitmap:用于容纳像素点(android .graphics.Bitmap)
//    Canvas：负责调用绘制方法，是整个过程的入口
//    要绘制的对象：比如绘制一个Bitmap,矩形或者圆
//    Paint: 设置 绘制图形的颜色和样式
//
//    Matrix：它包含一个3x3的矩阵，用于做变换匹配（图像处理中有讲），Matrix没有一个结构体，它必须被初始化，通过实现reset()方法或者set..()方法来实现。
//    下面来看代码 ：
//    import android.app .Activity;
//    import android.content.Context;
//    import android.content.res.Resources;
//    import android.os.Bundle;
//    import android.view.View;
//    import android.graphics.Bitmap;
//    import android.graphics.BitmapFactory;
//    //import android.graphics.Color;
//    import android.graphics.Matrix;
//    import android.graphics.Canvas;
//    import android.graphics.Paint;
//    //import android.graphics.Rect;
//
//    public class TestMartix extends Activity {
//    //新建Bitmap,Canvas和Paint
//    private Bitmap img,r_img;
//    private Canvas canvas;
//    private Paint paint;
//    //由于是自定义view,所以不需要调用Layout 文件 
//    public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    //调用自定义View
//    setContentView(new MyView(this));
//
//    }
//
//    public class MyView extends View{
//    //View的初始化
//    public MyView(Context context) {
//    super(context);
//
//    //BitmapFactory：从源创建一个Bitmap对象，这些源包括：文件，流或者数组
//    img = BitmapFactory.decodeResource(getResources(),R.drawable.img);
//    //新建一个Matrix对象
//    Matrix matrix = new Matrix();
//    //让矩阵实现翻转，参数为FLOAT型
//    matrix.postRotate(90);
//    //matrix.postRotate(0);
//    //获取 Bitmap的高与宽
//    int width = img.getWidth();
//    int height = img.getHeight();
//    //源Bitmap通过一个Matrix变化后，返回一个不可变的Bitmap
//    b_img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
//    paint = new Paint();
//
//    }
//    //在自定义VIEW时，必须实现此方法
//    public void onDraw(Canvas canvas){
//    //在重写父类的方法时，必须先调用父类的方法
//    super.onDraw(canvas);
//    //利用Canvas在View上绘制一个Bitmap，并设置它的样式和颜色
//    canvas.drawBitmap(b_img, 10, 10, paint);
//
//    //该方法是用来更新View的方法，多与线程结合使用。
//    //this.invalidate ()
//    //下面三段代码用于在View上绘制一个实心矩形，设置颜色为绿色，
//    //paint.setColor(Color.GREEN);
//    //paint.setAntiAlias(true);
//    //canvas.drawRect(new Rect(30,30,100,100), paint);
//
//    }
//    } 
//
//    }
//}
