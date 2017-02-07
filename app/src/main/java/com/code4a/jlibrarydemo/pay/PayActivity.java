package com.code4a.jlibrarydemo.pay;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.code4a.jlibrary.base.AppActivity;
import com.code4a.jlibrary.base.BaseFragment;
import com.code4a.jlibrary.utils.LogUtil;
import com.code4a.jlibrarydemo.R;
import com.code4a.jlibrarydemo.data.AlipayBean;
import com.code4a.jlibrarydemo.utils.Constants;
import com.code4a.jlibrarydemo.utils.OrderInfoUtil;
import com.tsy.sdk.pay.PayUtils;
import com.tsy.sdk.pay.alipay.Alipay;
import com.tsy.sdk.pay.weixin.WXPay;

import java.util.Map;

import butterknife.BindView;

public class PayActivity extends AppActivity implements PayView{

    private static final String TAG = PayActivity.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView titleTv;

    @BindView(R.id.editWXParam)
    EditText editWXParam;
    @BindView(R.id.editAlipayParam)
    EditText editAlipayParam;
    @BindView(R.id.btnWXPay)
    Button btnWXPay;
    @BindView(R.id.btnAliPay)
    Button btnAlipay;
    @BindView(R.id.btnWXClear)
    Button btnWXClear;
    @BindView(R.id.btnWXPaste)
    Button btnWXPaste;
    @BindView(R.id.btnAliPayClear)
    Button btnAliPayClear;
    @BindView(R.id.btnAliPayPaste)
    Button btnAliPayPaste;
    @BindView(R.id.btnGetIp)
    Button btnGetIp;

    private PayPresenter payPresenter;

    public void setmTitle(String tit) {
        if (titleTv != null) {
            titleTv.setText(tit);
        }
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(R.color.colorToolBar);
        setmTitle("支付页面");

        btnAlipay.setOnClickListener(this);
        btnWXPay.setOnClickListener(this);
        btnWXClear.setOnClickListener(this);
        btnWXPaste.setOnClickListener(this);
        btnAliPayClear.setOnClickListener(this);
        btnAliPayPaste.setOnClickListener(this);
        btnGetIp.setOnClickListener(this);

        this.payPresenter = new PayPresenterImpl(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_pay;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnWXPay:
                String wx_pay_param = editWXParam.getText().toString();
                if(TextUtils.isEmpty(wx_pay_param)) {
                    Toast.makeText(getApplication(), "请输入参数", Toast.LENGTH_SHORT).show();
                    return;
                }
                doWXPay(wx_pay_param);
                break;

            case R.id.btnWXClear:
                editWXParam.setText("");
                break;

            case R.id.btnWXPaste:
                ClipboardManager cbm=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                editWXParam.setText(cbm.getText());
                break;

            case R.id.btnAliPay:
                String alipay_pay_param = editAlipayParam.getText().toString();
                if(TextUtils.isEmpty(alipay_pay_param)) {
                    Toast.makeText(getApplication(), "请输入参数", Toast.LENGTH_SHORT).show();
                    return;
                }
//                doAlipay(alipay_pay_param);
                this.payPresenter.onGetPayInfo(Constants.CODE4A_ALIPAYINFO);
                break;

            case R.id.btnAliPayClear:
                editAlipayParam.setText("");
                break;

            case R.id.btnAliPayPaste:
                ClipboardManager cbm2=(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                editAlipayParam.setText(cbm2.getText());
                break;

            case R.id.btnGetIp:
                String ip = PayUtils.getIpAddress();
                if(ip != null) {
                    Toast.makeText(getApplication(), ip, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplication(), "获取ip失败", Toast.LENGTH_SHORT).show();
                }

            default:
                break;
        }
    }

    /**
     * 支付宝支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param) {
        new Alipay(this, pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDealing() {
                Toast.makeText(getApplication(), "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(getApplication(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(getApplication(), "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplication(), "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();
    }

    /**
     * 微信支付
     * @param pay_param 支付服务生成的支付参数
     */
    private void doWXPay(String pay_param) {
        String wx_appid = "wxXXXXXXX";     //替换为自己的appid
        WXPay.init(getApplicationContext(), wx_appid);      //要在支付前调用
        WXPay.getInstance().doPay(pay_param, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void doNext(AlipayBean alipayBean) {
        boolean rsa2 = (alipayBean.getPrivateKey().length() > 0);
        Map<String, String> params = OrderInfoUtil.buildOrderParamMap(alipayBean.getAppid(), rsa2);
        String orderParam = OrderInfoUtil.buildOrderParam(params);

        String sign = OrderInfoUtil.getSign(params, alipayBean.getPrivateKey(), rsa2);
        String orderInfo = orderParam + "&" + sign;
        LogUtil.i(TAG, "orderInfo : " + orderInfo);
        doAlipay(orderInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.payPresenter.onCancel();
        this.payPresenter.releaseRes();
        this.payPresenter = null;
    }
}
