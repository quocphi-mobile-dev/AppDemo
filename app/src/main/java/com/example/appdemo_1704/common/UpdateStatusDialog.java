package com.example.appdemo_1704.common;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.appdemo_1704.R;
import com.example.appdemo_1704.interf.OnUpdateDiaglogListener;

public class UpdateStatusDialog extends Dialog {
    EditText editText;
    Button btn_save;
    Button btn_cancel;









    OnUpdateDiaglogListener listener;

    public UpdateStatusDialog(@NonNull Context context, OnUpdateDiaglogListener listener) {
        super(context);
        this.listener = listener;
        init();
        addLintener();
    }

    private void init() {
        setContentView(R.layout.layout_update_status);
        Window window = getWindow();
        this.setCanceledOnTouchOutside(false);
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        }
        editText = findViewById(R.id.edt_content);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save = findViewById(R.id.btn_save);
    }

    public void getContent(String content) {
        editText.setText(content);
    }

    private void addLintener() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thực hiện hủy
                UpdateStatusDialog.this.dismiss();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // thực hiện gọi API để xóa
                if (!editText.getText().toString().isEmpty()) {
                    listener.onSaveClick(editText.getText().toString());
                }
                UpdateStatusDialog.this.dismiss();
            }
        });

    }

}
