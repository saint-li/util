package com.saint.utillib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.saint.util.base.BaseAct;
import com.saint.util.binding.BaseBindingAct;
import com.saint.util.util.AppUtil;
import com.saint.util.util.toast.AppToast;
import com.saint.utillib.databinding.ActScanBinding;
import com.saint.widget.DrawableClickEditText;
import com.saint.zxinglibrary.android.CaptureActivity;
import com.saint.zxinglibrary.bean.ZxingConfig;
import com.saint.zxinglibrary.common.ScanConstant;

import java.util.Objects;

public class ScanAct extends BaseBindingAct<ActScanBinding> {
    private final int REQUEST_CODE_SCAN = 111;
    private DrawableClickEditText clickEditText;

    @Override
    protected void initTitleView() {
        mActionBar = findViewById(R.id.my_action_bar);
        mActionBar.setTitle(R.string.scan_test);
        mActionBar.setLeftListener(view -> finish());
    }

    @Override
    protected void initView() {
        clickEditText = findViewById(R.id.et_result);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        clickEditText.setOnRightDrawableClick(this::scan);
    }

    //扫码
    public void scan(View v) {
//        PermissionUtil.request(act, new RequestPermissionBack() {
//            @Override
//            public void onSuccess(List<String> permissions) {
        Intent intent = new Intent(act, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
        config.setBind(true);
        intent.putExtra(ScanConstant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
//            }
//
//            @Override
//            public void onFailed(List<String> permissions) {
//                PermissionUtil.showSettingDialog(act, permissions, 1101);
//            }
//        }, Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(ScanConstant.CODED_CONTENT);
                if (!AppUtil.isStringNull(content)) {
                    clickEditText.setText(content);
                    clickEditText.setSelection(Objects.requireNonNull(clickEditText.getText()).length());
                } else {
                    AppToast.INSTANCE.tShort("扫描失败,请重试或手动输入");
                }
            }
        }
    }
}
