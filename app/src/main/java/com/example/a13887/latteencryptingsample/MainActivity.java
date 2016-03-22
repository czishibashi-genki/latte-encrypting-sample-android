package com.example.a13887.latteencryptingsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String string = "test123";
        String key = "6$J3&prVgGU8%~q1";
        String iv = "PLeU#-!T28d-91fg";

        // 暗号化
        String stringEncrypted = encrypt(string, key, iv);
        Log.d(TAG, stringEncrypted); // ttgiVU1WuN6+mR7+RcCY1w==
    }

    /**
     * Encodes a String in AES-128 with a given key
     *
     * @return String Base64 and AES encoded String
     */
    public static String encrypt(String string, String keyString, String ivString) throws NullPointerException {
        if (keyString.length() == 0 || keyString == null) {
            throw new NullPointerException("Please give key string");
        }

        if (ivString.length() == 0 || ivString == null) {
            throw new NullPointerException("Please give iv string");
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
