package com.qinl.goodsScan.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.google.zxing.WriterException;
import com.qinl.goodsScan.service.EncodeService;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private TextView resultTextView;
    private EditText qrStrEditText;
    private ImageView qrImgImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        scanBarCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MainActivity.this, null);
                startActivityForResult(openCameraIntent, 0);
            }
        });

        Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
        generateQRCodeButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    String contentString = qrStrEditText.getText().toString();
                    if (!contentString.equals("")) {
                        Bitmap qrCodeBitmap = EncodeService.createQRCode(contentString, 350);
                        qrImgImageView.setImageBitmap(qrCodeBitmap);
                    } else {
                        Toast.makeText(MainActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                    }

                } catch (WriterException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            resultTextView.setText(scanResult);
        }
    }
}