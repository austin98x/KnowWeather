package com.silencedut.setting.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.silencedut.baselib.commonhelper.log.LogHelper;
import com.silencedut.setting.BuildConfig;
import com.silencedut.setting.R;
import com.silencedut.setting.R2;
import com.silencedut.weather_core.Version;
import com.silencedut.weather_core.corebase.BaseActivity;
//import com.tencent.bugly.beta.Beta;
//import com.tencent.bugly.beta.UpgradeInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SilenceDut on 2016/11/18 .
 */

public class AboutActivity extends BaseActivity {
    @BindView(R2.id.title)
    Toolbar mTitle;
    @BindView(R2.id.imageView)
    ImageView mImageView;
    @BindView(R2.id.version)
    TextView mVersion;
    @BindView(R2.id.mark)
    TextView mMark;
    @BindView(R2.id.suggestion)
    TextView mSuggestion;
    @BindView(R2.id.new_version)
    TextView mNewVersion;
    @BindView(R2.id.new_version_tip)
    ImageView mNewVersionTip;
    @BindView(R2.id.share)
    TextView mShare;

    public static void navigationActivity(Context from) {
        Intent intent = new Intent(from, AboutActivity.class);
        from.startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.setting_activity_about;
    }

    @Override
    public void initViews() {
        setSupportActionBar(mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about);

        mVersion.setText(mVersion.getText() + Version.getVersionName(this));
        mNewVersion.setVisibility(View.GONE);
        mNewVersionTip.setVisibility(View.GONE);

        loadUpgradeInfo();
    }

    private void loadUpgradeInfo() {
        /***** 获取升级信息 *****/
        /*
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            LogHelper.info("AboutActivity", "no new version %s","s");
            return;
        }

        if (upgradeInfo.versionCode > Version.getVersionCode(this)) {
            mNewVersion.setText("有新版本更新");
            mNewVersionTip.setVisibility(View.VISIBLE);
        }
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R2.id.mark, R2.id.suggestion, R2.id.update_version, R2.id.pay, R2.id.share})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.mark) {
            openAppMarket();
        } else if (i == R.id.suggestion) {
            openContact();
        } else if (i == R.id.pay) {
            PayActivity.navigationActivity(this);
        } else if (i == R.id.update_version) {
            if (mNewVersionTip.getVisibility() == View.VISIBLE) {
                //Beta.checkUpgrade();
            } else {
                Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        } else if (i == R.id.share) {
            openShare();
        }
    }

    private void openAppMarket() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException anf) {
            Toast.makeText(this,"未找到相关应用",Toast.LENGTH_SHORT).show();
        }
    }

    private void openContact() {
        try{
            Uri uri = Uri.parse(getString(R.string.mail_send_to));
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_topic));
            intent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.device_model) + Build.MODEL + " \n"
                            + getString(R.string.sdk_version) + Build.VERSION.RELEASE + " \n"
                            + "版本：" + BuildConfig.VERSION_NAME);
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(this, R.string.no_mail_app, Toast.LENGTH_SHORT).show();
        }
    }

    private void openShare() {
        try {
            String shareTittle = getString(R.string.share_title);
            String shareContent = getString(R.string.share_content);
            String shareUrl = getString(R.string.share_url);
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareTittle + "\n" + shareContent + "\n" + shareUrl);
            sendIntent.setType("text/plain");
            startActivityForResult(sendIntent, 0);
        } catch (ActivityNotFoundException anf) {
            Toast.makeText(this, R.string.not_find_app, Toast.LENGTH_SHORT).show();
        }
    }
}
