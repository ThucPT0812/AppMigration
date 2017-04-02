package sevensoft.appmigration.screen;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import sevensoft.appmigration.R;
import sevensoft.appmigration.object.ActivityMigration;
import sevensoft.appmigration.object.Country;
import sevensoft.appmigration.object.Occupation;
import sevensoft.appmigration.object.SqlLiteDbHelper;
import sevensoft.appmigration.object.User;
import sevensoft.appmigration.util.Save;

public class Home extends ActivityMigration {

    Button home_btn_continues;
    ArrayList<Occupation> arrOccupation = new ArrayList<Occupation>();
    ArrayList<Country> arrListCountry = new ArrayList<Country>();
    SqlLiteDbHelper dbHelper;
    AutoCompleteTextView home_edt_work, home_edt_country_code;
    String[] arrWork ;
    TextView home_tv_work;
    EditText home_edt_full_name, home_edt_email , home_edt_phone;
    String[] arrCountry ;

    boolean mEditing = false;
    boolean mEditingCountry = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.l_home);

        initActivity();
        Save.currentActivity = this;
        Save.posActivity = 0;
        Save.listActivity[Save.posActivity] = this;
        setupContent();
        setUpListener();
    }

    public void init() {
        Save.currentActivity = null;
        Save.listActivity = new ActivityMigration[100];
        Save.posActivity = 0;

    }

    @Override
    public void setupContent() {
        super.setupContent();

        home_btn_continues = (Button)findViewById(R.id.home_btn_continues);
        home_edt_work = (AutoCompleteTextView)findViewById(R.id.home_edt_work);
        home_tv_work = (TextView)findViewById(R.id.home_tv_work);
        home_edt_full_name = (EditText)findViewById(R.id.home_edt_full_name);
        home_edt_email = (EditText)findViewById(R.id.home_edt_email);
        home_edt_phone = (EditText)findViewById(R.id.home_edt_phone);
        home_edt_country_code = (AutoCompleteTextView)findViewById(R.id.home_edt_country_code);
        setDataAdapterAutoComplete();
        setDataAdapterAutoCompleteCountry();

    }

    public void setDataAdapterAutoCompleteCountry(){
        dbHelper = new SqlLiteDbHelper(Save.currentActivity);
        dbHelper.openDataBase();
        arrListCountry = dbHelper.getAllCountry();

        arrCountry = new String[arrListCountry.size()];

        for(int i = 0 ; i <arrListCountry.size(); i ++){
            arrCountry[i] = arrListCountry.get(i).getCountryName();
        }

        home_edt_country_code.setThreshold(1);

        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this, R.layout.l_custom_item, R.id.autoCompleteItem, arrCountry);
        home_edt_country_code.setAdapter(adapterCountry);

        home_edt_country_code.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(arrCountry.length > 0){
                    if(home_edt_country_code.getText().toString().compareTo("") == 0){
                        home_edt_country_code.showDropDown();
                    }
                }
                return  false;
            }
        });

        handlerCountry();
    }

    public void handlerCountry(){
        home_edt_country_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mEditingCountry && home_edt_country_code.hasFocus()) {
                    mEditingCountry = true;

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        home_edt_country_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && mEditingCountry){
                    mEditingCountry = false;
                    dbHelper = new SqlLiteDbHelper(Save.currentActivity);
                    dbHelper.openDataBase();
                    Country country = dbHelper.getCountry(home_edt_country_code.getText().toString());
                    if(country != null){
                        home_edt_phone.setText(country.getCountryCode());
                    }
                }
            }
        });
    }


    public void setDataAdapterAutoComplete(){

        dbHelper = new SqlLiteDbHelper(Save.currentActivity);
        dbHelper.openDataBase();
        arrOccupation = dbHelper.getOccupation();
        Occupation occupationnotWork = new Occupation("", "My occupation is not in this list");
        arrOccupation.add(0, occupationnotWork);
        Occupation occupationnotWork1 = new Occupation("", "Unemployed");
        arrOccupation.add(0, occupationnotWork1);
        Occupation occupation = new Occupation("", "Not yet working");
        arrOccupation.add(0, occupation);
        arrWork = new String[arrOccupation.size()];

        for(int i = 0; i < arrOccupation.size(); i ++){
            arrWork[i] = arrOccupation.get(i).getOccupationCode() + " " + arrOccupation.get(i).getOccupationName();
        }

        home_edt_work.setThreshold(1);
        ArrayAdapter<String> adapterWork = new ArrayAdapter<String>(this,  R.layout.l_custom_item, R.id.autoCompleteItem, arrWork);
        home_edt_work.setAdapter(adapterWork);
        setLayoutAutoCompleted();
    }

    public void setLayoutAutoCompleted(){

        home_edt_work.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!mEditing && home_edt_work.hasFocus()) {
                    mEditing = true;
//                   home_edt_email.requestFocus();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        home_edt_work.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b && mEditing){
                    mEditing = false;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)getResources().getDimension(R.dimen.layx350), ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.RIGHT_OF, home_tv_work.getId());
                    home_edt_work.setLayoutParams(params);
//                    home_edt_email.requestFocus();
                }
            }
        });
    }

    public void setUpListener(){

        home_btn_continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(home_edt_full_name.getText().toString().compareTo("") == 0){
                    showDialogOk("Migration", "Please input your name!");
                    home_edt_full_name.requestFocus();
                    return;
                }

                if(home_edt_email.getText().toString().compareTo("") == 0){
                    showDialogOk("Migration", "Please input your email!");
                    home_edt_email.requestFocus();
                    return;
                }

                if(home_edt_work.getText().toString().trim().compareTo("My occupation is not in this list") == 0){
                    String message = "Unfortunately, it seems like you are not eligible for visa subclasses 189, 190 or 489.\n";
                    message += "There may be other visa options for you. Please fill in your email address and one of our registered migration agents will contact you to discuss further if you'd like.";
                    showDialogOk("Migration", message);
                    return;
                }

                Save.user = new User();
                Save.user.setOccupation(home_edt_work.getText().toString());
                Save.user.setName(home_edt_full_name.getText().toString());
                Save.user.setEmail(home_edt_email.getText().toString());
                Save.user.setCountry(home_edt_country_code.getText().toString());
                Save.user.setPhone(home_edt_phone.getText().toString());
                Intent questionItent = new Intent(Save.currentActivity, Question.class);
                startActivity(questionItent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Save.currentActivity = this;
        Save.posActivity = 0;
        Save.listActivity[Save.posActivity] = this;

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void finish() {
        init();
        System.exit(-1);
        super.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
