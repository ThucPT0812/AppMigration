package sevensoft.appmigration.object;

import android.app.Activity;

/**
 * Created by STJHN on 2016/12/05.
 */

public interface ActivityInterface {

    public void initActivity();

    public Activity getActivity();

    public void setFinish();

    public void setupContent();

    public void showToast(String text);

    public void showDialogOk(String header, String text);

    public void showDialogWaiting(String text);

    public void endDialog();
}
