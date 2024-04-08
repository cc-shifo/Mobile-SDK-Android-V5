package dji.sampleV5.aircraft.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import dji.sdk.keyvalue.value.flightcontroller.RealNameRegistrationState;
import dji.v5.common.callback.CommonCallbacks;
import dji.v5.common.error.IDJIError;
import dji.v5.manager.aircraft.uas.AreaStrategy;
import dji.v5.manager.aircraft.uas.RealNameRegistrationStatus;
import dji.v5.manager.aircraft.uas.RealNameRegistrationStatusListener;
import dji.v5.manager.aircraft.uas.UASRemoteIDManager;

/**
 * 中国区域UOM注册
 */
public class UASUOMModel {

    private RealNameRegistrationStatusListener mListener;

    public UASUOMModel() {
        mListener = new RealNameRegistrationStatusListener() {
            @Override
            public void onUpdate(RealNameRegistrationStatus status) {
                getRealNameStatusTip(status.getRealNameRegistrationStateFromAircraft());
            }
        };
    }

    public static boolean isChinaArea(@Nullable String areaCode) {
        return "CN".equals(areaCode);
    }

    public void listen() {
        UASRemoteIDManager.getInstance().clearAllRealNameRegistrationStatusListener();
        updateUOMChina();
        UASRemoteIDManager.getInstance().addRealNameRegistrationStatusListener(mListener);
    }


    public void cancelListen() {
        UASRemoteIDManager.getInstance().removeRealNameRegistrationStatusListener(mListener);
    }

    public void updateFromUOM() {
        UASRemoteIDManager.getInstance().updateRealNameRegistrationStateFromUOM(
                new CommonCallbacks.CompletionCallback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(@NonNull IDJIError idjiError) {

                    }
                });
    }

    public void getRealNameStatusTip(RealNameRegistrationState status){
        switch (status) {
            case NOT_AUTH: // "未认证"。中国大陆飞行不能起飞；
                break;
                case VAILD_AUTH: // "已认证"。中国大陆飞行不能起飞；
                case CANCELLED: // "已注销"
                case NETWORK_ERROR: // "网络错误"
                case VERIFIED_AND_CANCLLED: // "飞机认证后注销"
                case UNSUPPORTED: // "飞机不支持实名认证功能"
                case NOT_ACTIVE_YET: // "飞机未激活"
                case DONT_NEED_CHECK_REALNAME: // "无需实名认证"
                case UNLOCKED: // "飞机已经解禁"
                case DONT_IN_CHINA_MAINLAND: // "不在中国大陆"
                case TMP_VALID_AUTH: // "临时有效"
            default: // "未知状态"
                break;
        }
    }

    private void updateUOMChina() {
        IDJIError error = UASRemoteIDManager.getInstance().setUASRemoteIDAreaStrategy(
                AreaStrategy.CHINA_STRATEGY);
        if (error != null) {
            // ToastUtils
        }
    }
}
