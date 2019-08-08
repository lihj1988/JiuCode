package com.jiuwang.buyer.redpakge;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jiuwang.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author ChayChan
 * @description: 红包弹框
 * @date 2017/11/27  15:12
 */

public class RedPacketViewHolder {

    @Bind(R.id.iv_close)
    ImageView mIvClose;

    @Bind(R.id.iv_avatar)
    ImageView mIvAvatar;

    @Bind(R.id.tv_name)
    TextView mTvName;

    @Bind(R.id.tv_msg)
    TextView mTvMsg;

    @Bind(R.id.iv_open)
    ImageView mIvOpen;

    private Context mContext;
    private OnRedPacketDialogClickListener mListener;

    private int[] mImgResIds = new int[]{
            R.drawable.icon_open_red_packet1,
            R.drawable.icon_open_red_packet2,
            R.drawable.icon_open_red_packet3,
            R.drawable.icon_open_red_packet4,
            R.drawable.icon_open_red_packet5,
            R.drawable.icon_open_red_packet6,
            R.drawable.icon_open_red_packet7,
            R.drawable.icon_open_red_packet7,
            R.drawable.icon_open_red_packet8,
            R.drawable.icon_open_red_packet9,
            R.drawable.icon_open_red_packet4,
            R.drawable.icon_open_red_packet10,
            R.drawable.icon_open_red_packet11,
    };
    private FrameAnimation mFrameAnimation;

    public RedPacketViewHolder(Context context, View view) {
        mContext = context;
        ButterKnife.bind(this, view);
    }

    @OnClick({R.id.iv_close, R.id.iv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                stopAnim();
                if (mListener != null) {
                    mListener.onCloseClick();
                }
                break;

            case R.id.iv_open:
                if (mFrameAnimation != null) {
                    //如果正在转动，则直接返回
                    return;
                }

                startAnim();

                if (mListener != null) {
                    mListener.onOpenClick();
                }
                new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        stopAnim();
                    }
                }.sendEmptyMessageDelayed(0,2000);
                break;
        }
    }

    public void setData(RedPacketEntity entity) {
//        RequestOptions options = new RequestOptions();
//        options.centerCrop()
//                .circleCrop();

        Glide.with(mContext)
                .load(entity.avatar)
//                .apply(options)
                .into(mIvAvatar);

        mTvName.setText(entity.name);
        mTvMsg.setText(entity.remark);
    }

    public void startAnim() {
        mFrameAnimation = new FrameAnimation(mIvOpen, mImgResIds, 100, true);
        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {
                Log.i("", "start");
            }

            @Override
            public void onAnimationEnd() {
                Log.i("", "end");
            }

            @Override
            public void onAnimationRepeat() {
                Log.i("", "repeat");
            }

            @Override
            public void onAnimationPause() {
                mIvOpen.setBackgroundResource(R.drawable.icon_open_red_packet1);
            }
        });
    }

    public void stopAnim() {
        if (mFrameAnimation != null) {
            mFrameAnimation.release();
            mFrameAnimation = null;
        }
    }

    public void setOnRedPacketDialogClickListener(OnRedPacketDialogClickListener listener) {
        mListener = listener;
    }
}
