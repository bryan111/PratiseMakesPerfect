package com.bryan.example.function.imageview;

import com.bryan.example.utils.MathUtil;

import android.R.integer;
import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

/**
 * 可以被缩放、旋转的ImageView
 * 
 * 问题：View的各个回掉方法什么时候会被调用？
 *      ScaleGestureDetector还能做些什么事？
 * @author bryan
 *
 */
public class RotateZoomImageView extends ImageView
{

    private ScaleGestureDetector mScaleDetector;
    private Matrix mImageMatrix;
    /**
     * 上次旋转的角度
     */
    private int mLastAngle = 0;
    /**
     * 变换时的轴点
     */
    private int mPivotX, mPivotY;

    public RotateZoomImageView(Context context)
    {
        super(context);
        init(context);
    }

    public RotateZoomImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public RotateZoomImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context)
    {
        mScaleDetector = new ScaleGestureDetector(context, mScaleListener);

        setScaleType(ScaleType.MATRIX);
        mImageMatrix = new Matrix();
    }
    
    /**
     * 在onSizeChanged()中根据视图的尺寸计算一些值
     * 这个视图在init（）期间并没有尺寸，因此必须等到这个回掉方法才能得到尺寸
     * 
     * 
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        //在视图构建并被布局完成后就会马上被触发一次。此方法会被多次调用，这里通过判断，当大小发生改变后才去进行处理
        if (oldw != w || oldh != h)
        {
            //将图片移到视图的中央
            //getDrawable().getIntrinsicWidth()返回图片的大小
            int translateX = Math.abs(w - getDrawable().getIntrinsicWidth())/2;
            int translatrY = Math.abs(h - getDrawable().getIntrinsicHeight())/2;
            mImageMatrix.setTranslate(translateX, translatrY);
            setImageMatrix(mImageMatrix);
            
            //得到未来缩放和旋转变换时的中轴点
            mPivotX = w/2;
            mPivotY = h/2;
            
        }
    }
    
    private SimpleOnScaleGestureListener mScaleListener = new SimpleOnScaleGestureListener()
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            // ScaleGestureDetector会根据手指的分开和合拢计算出缩放因子
            float scaleFactor = detector.getScaleFactor();
            // 将缩放因子传递给图片进行缩放
            mImageMatrix.postScale(scaleFactor, scaleFactor, mPivotX, mPivotY);
            setImageMatrix(mImageMatrix);
            
//            detector.getCurrentSpan();//获得手势中两个触点建的距离
//            detector.getFocusX();//获得当前手势的焦点坐标。它是触点收缩时的平均位置
//            detector.getScaleFactor();//当前事件和之前事件之间的变化比例。多跟手指分开时这个值会大于1，收拢时会稍微小于1
//            
            return true;
        }
    };
    
    /**
     * 处理两根手指的事件来旋转图片
     * 这个方法 根据<触点间的角度变化>对<图片进行相应的旋转>
     */
     private boolean doRotationEvent(MotionEvent ev)
    {

         float deltaX = ev.getX(0) -ev.getX(1);
         float deltaY = ev.getY(0) - ev.getY(1);
         //弧度,这个方法会返回一个临界值，下面会进行处理
         double radians = Math.atan(deltaY/deltaX);
         //转换成角度
         int degrees = (int) (radians * 180 /Math.PI);

        switch (ev.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            // 记住初始角度
            mLastAngle = degrees;
            break;
        case MotionEvent.ACTION_MOVE:
            // 返回一个转换后的介于-90度到+90度的值
            // 这样在两根手指垂直触摸时可以得到翻转信号和相应的角度
            // 这种情况下会将图片在我们侦测到的反向上旋转一个很小的角度（5度）
            Log.e("degrees", degrees+"");
            Log.e("mLastAngle", mLastAngle+"");

            if ((degrees - mLastAngle) > 45)
            {
                // 逆时针旋转（可以超出边界）
                mImageMatrix.postRotate(-2, mPivotX, mPivotY);
            }
            else if ((degrees - mLastAngle) < -45)
            {
                // 顺时针旋转（可以超出边界）
                mImageMatrix.postRotate(2, mPivotX, mPivotY);
            }
            else
            {
                // 正常旋转
                mImageMatrix.postRotate(degrees - mLastAngle, mPivotX, mPivotY);
            }
            // 将旋转的矩阵设置给图片
            setImageMatrix(mImageMatrix);
            // 保存当前的角度
            mLastAngle = degrees;
            break;
        }

        return true;
    } 
     
     @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            /**
             * 必须处理ACTION_DOWN事件，否则后续其他手指的触摸事件将不会传递到这个视图。虽然不用对这个事件（ACTION_DOWN）
             * 做任何操作，但是也要显示的返回true
             */
            return true;
        }

        switch (event.getPointerCount())
        {
        case 3:
            // 按下三根手指时，使用ScaleGestureDetector缩放图片
            return mScaleDetector.onTouchEvent(event);
        case 2:
            // 按下两根手指时，根据手指操作旋转的图片
            return doRotationEvent(event);
        default:
            //
            return super.onTouchEvent(event);
        }
    }
    

}
