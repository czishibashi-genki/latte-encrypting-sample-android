package com.example.a13887.latteencryptingsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate");

        Button sendLtvBtn = (Button)findViewById(R.id.button);
        sendLtvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //　送信する
                String mid = "test123";

                // 暗号化
                String midEncrypted = encrypt(mid);
                Log.d(TAG, midEncrypted); // 出力された結果が「ttgiVU1WuN6+mR7+RcCY1w==」であることを確認

                // Ltv成果としてmidを送信
                AdManager ad = new AdManager(v.getContext());
                LtvManager ltv = new LtvManager(ad);
                ltv.addParam("mid", midEncrypted);
                ltv.sendLtvConversion(7725);
            }
        });
    }

    /**
     * AES128で暗号化、暗号化後Base64で変換した文字列を返す
     *
     * @return String Base64 and AES encoded String
     */
    public static String encrypt(String string) throws NullPointerException {

        // 暗号化用パラメータ
        String keyString = "6$J3&prVgGU8%~q1";
        String ivString = "PLeU#-!T28d-91fg";

        if (string.length() == 0 || string == null) {
            throw new NullPointerException("Please give text");
        }

        try {
            byte[] byteText = string.getBytes("UTF-8");
            byte[] byteKey = keyString.getBytes("UTF-8");
            byte[] byteIv = ivString.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(byteIv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] byteResult = cipher.doFinal(byteText);
            String encrypedValue = Base64.encodeToString(cipher.doFinal(byteText), Base64.DEFAULT);

            return encrypedValue;

        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }
}
