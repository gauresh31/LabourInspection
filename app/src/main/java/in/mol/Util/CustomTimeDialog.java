package in.mol.Util;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import in.mol.labourinspection.R;

public class CustomTimeDialog extends AlertDialog.Builder implements View.OnClickListener {

    Button btn_hr_minus, btn_hr_plus, btn_min_minus, btn_min_plus, btnOk;
    EditText edit_hour, edit_min, edit_set_time;
    Context contextAct;
    View dialogView;
    AlertDialog alert;

    public CustomTimeDialog(Context con, EditText edit) {
        super(con);

        contextAct = con;
        edit_set_time = edit;

        LayoutInflater inflater = LayoutInflater.from(contextAct);
        dialogView = inflater.inflate(R.layout.dialog_time_picker, null);
        setView(dialogView);

        init();
        setListeners();

        edit_min.setText("0");
        edit_hour.setText("0");

    }

    private void init() {
        btn_hr_minus = (Button) dialogView.findViewById(R.id.btn_hr_minus);
        btn_hr_plus = (Button) dialogView.findViewById(R.id.btn_hr_plus);
        btn_min_minus = (Button) dialogView.findViewById(R.id.btn_min_minus);
        btn_min_plus = (Button) dialogView.findViewById(R.id.btn_min_plus);

        btnOk = (Button) dialogView.findViewById(R.id.btn_ok);

        edit_hour = (EditText) dialogView.findViewById(R.id.edit_hour);
        edit_min = (EditText) dialogView.findViewById(R.id.edit_min);
    }

    private void setListeners() {
        btn_hr_minus.setOnClickListener(this);
        btn_hr_plus.setOnClickListener(this);
        btn_min_minus.setOnClickListener(this);
        btn_min_plus.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hr_plus:
                String strPrev = edit_hour.getText().toString();

                if (strPrev.equalsIgnoreCase("23")) {
                    edit_hour.setText("0");
                } else {
                    edit_hour.setText(Integer.toString(Integer.parseInt(strPrev) + 1));
                }
                break;

            case R.id.btn_hr_minus:
                String strNext = edit_hour.getText().toString();

                if (strNext.equalsIgnoreCase("0")) {
                    edit_hour.setText("23");
                } else {
                    edit_hour.setText(Integer.toString(Integer.parseInt(strNext) - 1));
                }
                break;

            case R.id.btn_min_plus:
                String strMinPrev = edit_min.getText().toString();

                if (strMinPrev.equalsIgnoreCase("59")) {
                    edit_min.setText("0");
                } else {
                    edit_min.setText(Integer.toString(Integer.parseInt(strMinPrev) + 1));
                }
                break;

            case R.id.btn_min_minus:
                String strMinNext = edit_min.getText().toString();

                if (strMinNext.equalsIgnoreCase("0")) {
                    edit_min.setText("59");
                } else {
                    edit_min.setText(Integer.toString(Integer.parseInt(strMinNext) - 1));
                }
                break;

            case R.id.btn_ok:
                edit_set_time.setText(edit_hour.getText().toString() + ":" + edit_min.getText().toString());
                this.alert.dismiss();
                break;

            default:
                break;
        }
    }


    public void getAlert(AlertDialog alertDialog) {
        alert = alertDialog;
    }
}
