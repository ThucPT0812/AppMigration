package sevensoft.appmigration.object;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import sevensoft.appmigration.R;
import sevensoft.appmigration.util.Save;

/**
 * Created by TrungThuc on 2016/12/05.
 */

public class ActivityMigration extends Activity implements ActivityInterface {

    public View contentView;
    public Dialog dialogWaiting;
    public Dialog dialogOk;

    protected static final int HIDER_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
            View.SYSTEM_UI_FLAG_FULLSCREEN |
            View.SYSTEM_UI_FLAG_IMMERSIVE |
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void initActivity(){
        contentView = findViewById(R.id.ROOT_VIEW_ID);
        setupUI(contentView);
        dialogWaiting = new Dialog(this);
        dialogWaiting.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWaiting.setContentView(R.layout.dialog_waiting);

        dialogOk = new Dialog(this);
        dialogOk.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOk.setContentView(R.layout.dialog_select_ok);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    KeybroadUtility.hideSoftKeyboard(ActivityMigration.this);
                    return false;
                }

            });
        } else if(view instanceof AutoCompleteTextView) {

        } else {
            ((EditText)view).setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        hideImmersiveMode();
                    }
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    protected boolean hideImmersiveMode() {
        // Retrieve if the Immersive mode is enabled or not.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if(getWindow().getDecorView().getSystemUiVisibility() == HIDER_FLAGS) {
                return false;
            } else {
                updateSystemUiVisibility();
                return true;
            }
        }
        return false;
    }

    protected void updateSystemUiVisibility() {
        // Retrieve if the Immersive mode is enabled or not.
        enableImmersiveMode();
    }

    @SuppressLint("NewApi")
    private void enableImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(HIDER_FLAGS);
    }

    public Activity getActivity(){

        return this;
    }

    public void setFinish(){}

    public void setupContent(){}

    public void showToast(String text){
        if(text.length() > 80) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    public void showDialogOk(String header, String text){
        endDialog();
        FrameLayout l_main_dialog_select = (FrameLayout)dialogOk.findViewById(R.id.l_main_dialog_select);
        l_main_dialog_select.setClickable(false);
        dialogOk.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tvHeader = (TextView) dialogOk.findViewById(R.id.dialog_select_header);
        tvHeader.setText(header);
        TextView tv = (TextView) dialogOk.findViewById(R.id.dialog_select_content);
        tv.setText(text);
        ((Button)dialogOk.findViewById(R.id.dialog_select_bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDialog();
            }
        });

        dialogOk.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        try {
            //Set the dialog to immersive
            dialogOk.getWindow().getDecorView().setSystemUiVisibility(
                    Save.currentActivity.getActivity().getWindow().getDecorView().getSystemUiVisibility());
        } catch (Throwable e) {}
        //Show the dialog! (Hopefully no soft navigation...)
        dialogOk.show();

        //Clear the not focusable flag from the window
        dialogOk.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    public void closeDialogSelect(View v){
        findViewById(R.id.l_main_dialog_select).setVisibility(View.GONE);
    }

    public void endDialog(){
        if (dialogWaiting != null && dialogWaiting.isShowing()) {
            dialogWaiting.dismiss();
        }

        if (dialogOk != null && dialogOk.isShowing()) {
            dialogOk.dismiss();
        }
    }

    public void showDialogWaiting(String text){
        endDialog();

        dialogWaiting.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogWaiting.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        try {
            //Set the dialog to immersive
            dialogWaiting.getWindow().getDecorView().setSystemUiVisibility(
                    Save.currentActivity.getActivity().getWindow().getDecorView().getSystemUiVisibility());
        } catch (Throwable e) {}
        //Show the dialog! (Hopefully no soft navigation...)
        dialogWaiting.show();

        //Clear the not focusable flag from the window
        dialogWaiting.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }
}
