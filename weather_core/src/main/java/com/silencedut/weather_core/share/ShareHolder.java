package com.silencedut.weather_core.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.baselib.commonhelper.adapter.BaseRecyclerAdapter;
import com.silencedut.baselib.commonhelper.adapter.BaseViewHolder;
import com.silencedut.baselib.commonhelper.utils.UIUtil;
import com.silencedut.weather_core.R;
import com.silencedut.weather_core.R2;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 2016/11/16 .
 */

public class ShareHolder extends BaseViewHolder<ShareData> {

    @BindView(R2.id.share_icon)
    ImageView mShareIcon;
    @BindView(R2.id.share_tip)
    TextView mShareTip;
    private int mPosition;
    private ShareData mShareData;

    public static final int[] SHARE_ICONS = {R.mipmap.core_share_weixin, R.mipmap.core_share_moments, R.mipmap.core_share_qq, R.mipmap.core_share_kongjian};
    private static final String[] TIPS = {"微信", "朋友圈", "QQ", "QQ空间"};
    private static final SHARE_MEDIA[] SHAREMEDIAS = {SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE};

    public ShareHolder(View itemView, BaseRecyclerAdapter baseRecyclerAdapter) {
        super(itemView, baseRecyclerAdapter);
    }


    @Override
    public void updateItem(ShareData data, int position) {
        mShareData = data;
        mShareIcon.setImageResource(SHARE_ICONS[position]);
        mShareTip.setText(TIPS[position]);
        mPosition = position;
    }

    @Override
    public int getContentViewId() {
        return R.layout.core_item_share;
    }

    @OnClick(R2.id.share_container)
    public void onClick() {
        final UMImage umImage;
        Bitmap screenBitmap = null;
        String titleStr = getContext().getResources().getString(R.string.core_share_title);
        String infoStr = getContext().getResources().getString(R.string.core_share_info);
        ShareAction shareAction = new ShareAction((Activity) getContext()).setPlatform(SHAREMEDIAS[mPosition]).withTitle(titleStr).withText(infoStr);
        if (mShareData.mIsWeather) {
            screenBitmap = UIUtil.takeScreenShot((Activity) getContext());
            if (screenBitmap == null) {
                Toast.makeText(getContext(), R.string.core_share_fail, Toast.LENGTH_LONG).show();
                return;
            }
            umImage = new UMImage(getContext(), screenBitmap);
            shareAction.withMedia(umImage);
        } else {
            umImage = new UMImage(getContext(), R.mipmap.core_icon);
            shareAction.withMedia(umImage).withTargetUrl(getContext().getResources().getString(R.string.core_share_url));
        }

        final Bitmap finalScreenBitmap = screenBitmap;

        shareAction.setCallback(new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(getContext(), R.string.core_share_success, Toast.LENGTH_SHORT).show();
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                if (t != null) {
                    Toast.makeText(getContext(), R.string.core_share_fail, Toast.LENGTH_LONG).show();
                }
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                umImage.asBitmap().recycle();
                if (finalScreenBitmap != null) {
                    finalScreenBitmap.recycle();
                }
            }
        });
        shareAction.share();
        mShareData.mShareDialog.dismiss();
    }
}