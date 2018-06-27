package com.hzb.customviewproject.customview.searchview

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator


class MySearchView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    constructor(context: Context?) : this(context, null)

    private val mPaint = Paint()//画笔
    private var offestFactoty = 0.9f //偏移因子画布是组件的0.9
    private var mWidth: Float = 0.0f  //组件宽度
    private var mHeight: Float = 0.0f //组件高度
    private lateinit var searchRecf: RectF
    private lateinit var cicleRecf: RectF
    private val searchPath: Path = Path() //放大镜的path
    private val ciclePath: Path = Path()  //外圆的path
    private var viewStatus = AnimStatus.NONE
    private var isTopAnim:Boolean=false
    private var animatorValue: Float = 0.0f  //动画变量，0-1
    //属性动画
    private lateinit var startAnim: ValueAnimator
    private lateinit var searingAnim: ValueAnimator
    private lateinit var endAnim: ValueAnimator

    private enum class AnimStatus { //标志动画状态
        NONE, START, SEARING, END//初始状态，开始搜索，搜索中，结束搜索
    }


    init {
        initPaint()
        initAnimator()
        initEvent()
    }

    private fun initAnimator() {
        //AnimStatus.START状态的动画
        startAnim = ValueAnimator.ofFloat(0f, 1f)
        startAnim.duration = 1000
        startAnim.addUpdateListener { animation ->
            animatorValue = animation.animatedValue as Float
            invalidate()
        }
        //AniStatus.SEARING状态动画
        searingAnim = ValueAnimator.ofFloat(0f, 1f)
        searingAnim.interpolator = LinearInterpolator()
        searingAnim.duration=1500
        searingAnim.repeatCount=ValueAnimator.INFINITE
        searingAnim.repeatMode=ValueAnimator.RESTART
        searingAnim.addUpdateListener { animation ->
            if (viewStatus==AnimStatus.SEARING) { //这里必须添加，不然放大镜会有一刹那全部显示
                animatorValue = animation.animatedValue as Float
                invalidate()
            }
        }
        //AniStatus.END状态动画
        endAnim = ValueAnimator.ofFloat(1f, 0f)
        endAnim.duration=1000
        endAnim.addUpdateListener { animation ->
            animatorValue = animation.animatedValue as Float
            invalidate()
        }

    }

    private fun initPaint() {
        mPaint.color = Color.BLUE
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 8f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        initPath()
    }

    private fun initPath() {
        cicleRecf = RectF(-mWidth / 2 * offestFactoty, -mHeight / 2 * offestFactoty, mWidth / 2 * offestFactoty, mHeight / 2 * offestFactoty)
        searchRecf = RectF(-mWidth / 5, -mHeight / 5, mWidth / 5, mHeight / 5)
        searchPath.addArc(searchRecf, 45f, 359.9f)
        ciclePath.addArc(cicleRecf, 45f, 359.9f)
        val pathMeasure = PathMeasure(ciclePath, false)
        val floatArray = FloatArray(2)
        val posTan = pathMeasure.getPosTan(0f, floatArray, null)
        searchPath.lineTo(floatArray[0], floatArray[1])//放大镜的手柄
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.translate(mWidth / 2, mHeight / 2)//移动坐标到组件中心
        when (viewStatus) {
            AnimStatus.NONE -> {  //初识转态
                canvas?.drawPath(searchPath, mPaint)
            }
            AnimStatus.START -> {  //开始搜索
                val pathMeasure = PathMeasure(searchPath, false)
                val dst = Path()
                pathMeasure.getSegment(pathMeasure.length * animatorValue, pathMeasure.length, dst, true)
                canvas?.drawPath(dst, mPaint)
            }
            AnimStatus.SEARING -> { //搜索中
                val pathMeasure = PathMeasure(ciclePath, false)
                val dst = Path()
                val stop = pathMeasure.length * animatorValue
                val start = (stop - (0.5 - Math.abs(animatorValue - 0.5)) * pathMeasure.length/2).toFloat()
                pathMeasure.getSegment(start,stop, dst, true)
                canvas?.drawPath(dst, mPaint)
            }
            AnimStatus.END -> {   //搜索结束
                val pathMeasure = PathMeasure(searchPath, false)
                val dst = Path()
                pathMeasure.getSegment(pathMeasure.length*animatorValue , pathMeasure.length,  dst, true)
                canvas?.drawPath(dst, mPaint)
            }
        }
    }

    /**
     * 开始动画
     */
    fun startAnim() {
        viewStatus=AnimStatus.START
        startAnim.start()
        this.isClickable = false
    }

    /**
     * 结束动画
     */
    fun stopAnim(){
        isTopAnim=true
    }
    /**
     * 监听动画结束
     */
    private fun initEvent() {

        val listener: Animator.AnimatorListener = object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                if (isTopAnim) {
                    animation?.cancel()
                    viewStatus=AnimStatus.END
                    endAnim.start()
                }
            }

            override fun onAnimationEnd(animation: Animator?) {
                when (viewStatus) {
                    AnimStatus.START -> {
                        searingAnim.start()
                        viewStatus=AnimStatus.SEARING
                    }

                    AnimStatus.END -> {
                        viewStatus = AnimStatus.NONE
                        this@MySearchView.isClickable = true
                        isTopAnim=false
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        }

        startAnim.addListener(listener)
        searingAnim.addListener(listener)
        endAnim.addListener(listener)
    }
}