package hu.ait.android.penz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.penz.adapter.MyPreferences;
import hu.ait.android.penz.data.Item;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.bumptech.glide.Glide;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hu.ait.android.penz.adapter.ItemsAdapter;
import hu.ait.android.penz.ItemListActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvBudget)
    TextView tvBudget;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.tvDay)
    TextView tvDay;
    @BindView(R.id.ivAdd)
    ImageView ivAdd;
    @BindView(R.id.ivConvert)
    ImageView ivConvert;
    @BindView(R.id.ivMail)
    ImageView ivMail;
    @BindView(R.id.ivReset)
    ImageView ivReset;

    public static final int REQUEST_NEW_ITEM = 101;
    public static final int REQUEST_EDIT_ITEM = 102;
    public static final String KEY_EDIT = "KEY_EDIT";
    private GestureDetectorCompat gestureObject;
    private ConstraintLayout constraintLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = mdformat.format(calendar.getTime());
        tvDate.setText(strDate);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        tvDay.setText(dayOfTheWeek);

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        ((MainApplication) getApplication()).openRealm();

        refreshSum();


    }

    private void refreshSum() {
        RealmResults<Item> allItems = getRealm().where(Item.class).findAll();
        final Item itemsArray[] = new Item[allItems.size()];
        List<Item> itemsResult = new ArrayList<Item>(Arrays.asList(allItems.toArray(itemsArray)));

        constraintLayout = (ConstraintLayout) findViewById(
                R.id.constraintLayout);

        MyPreferences.sum = calculate(itemsResult);
        int total = MyPreferences.sum;
        tvBudget.setText("$" + total);
    }

    public int calculate(List<Item> itemsList) {
        int length = itemsList.size();
        int total = 0;
        for (int i = 0; i < length; i++) {
            String add = itemsList.get(i).getPrice();
            if (add != null) {
                int combine = Integer.parseInt(add);
                total = total + combine;
            }
        }
        return total;
    }

    public void counting(List<Item> itemsList) {
        int size = itemsList.size();

        for (int i = 0; i < size; i++) {
            int itemID = itemsList.get(i).getItemType().getValue();

            if (itemID == 0) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.groceries = y + MyPreferences.groceries;
            }

            if (itemID == 1) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.entertainment = y + MyPreferences.entertainment;
            }

            if (itemID == 2) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.clothing = y + MyPreferences.clothing;
            }

            if (itemID == 3) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.transportation = y + MyPreferences.transportation;
            }

            if (itemID == 4) {
                String x = itemsList.get(i).getPrice();
                int y = Integer.parseInt(x);
                MyPreferences.misc = y + MyPreferences.misc;
            }
        }

    }

    public Realm getRealm() {
        return ((MainApplication) getApplication()).getRealmItems();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()) {

                Intent intentStat = new Intent(MainActivity.this,
                        StatisticsActivity.class);
                startActivity(intentStat);

            } else if (event2.getX() < event1.getX()) {
                Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
                startActivity(intent);
            }

            return true;
        }

    }


    @OnClick(R.id.ivReset)
    public void Reset(ImageView imageView) {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to reset?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getRealm().beginTransaction();
                        getRealm().deleteAll();
                        getRealm().commitTransaction();
                        refreshSum();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void deleteAllItem() {
        getRealm().beginTransaction();
        getRealm().deleteAll();
        getRealm().commitTransaction();
    }

    @OnClick(R.id.ivConvert)
    public void Converter(ImageView imageView) {
        Intent intentStart = new Intent(MainActivity.this,
                ConvertActivity.class);
        startActivity(intentStart);
    }

    @OnClick(R.id.ivMail)
    public void Statistics(ImageView imageView) {

        RealmResults<Item> allItems = getRealm().where(Item.class).findAll();
        final Item itemsArray[] = new Item[allItems.size()];
        List<Item> itemsResult = new ArrayList<Item>(Arrays.asList(allItems.toArray(itemsArray)));

        constraintLayout = (ConstraintLayout) findViewById(
                R.id.constraintLayout);

        MyPreferences.groceries = 0;
        MyPreferences.entertainment = 0;
        MyPreferences.clothing = 0;
        MyPreferences.transportation = 0;
        MyPreferences.misc = 0;

        counting(itemsResult);

        openDialog();

    }

    private void openDialog(){
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View subView = inflater.inflate(R.layout.email, null);
        final EditText etDialog = (EditText)subView.findViewById(R.id.etDialog);
        final ImageView ivMail = (ImageView)subView.findViewById(R.id.ivMail);
        Drawable drawable = getResources().getDrawable(R.drawable.android_mail);
        ivMail.setImageDrawable(drawable);

        final StringBuilder sb = new StringBuilder();

        int groceries = MyPreferences.groceries;
        int entertainment = MyPreferences.entertainment;
        int clothing = MyPreferences.clothing;
        int transportation = MyPreferences.transportation;
        int misc = MyPreferences.misc;

        sb.append("Total: ");
        sb.append(tvBudget.getText().toString());
        sb.append('\n');
        sb.append("Groceries: ");
        sb.append(groceries);
        sb.append('\n');
        sb.append("Entertainment: ");
        sb.append(entertainment);
        sb.append('\n');
        sb.append("Clothing: ");
        sb.append(clothing);
        sb.append('\n');
        sb.append("Rent/Utilities/Transportation: ");
        sb.append(transportation);
        sb.append('\n');
        sb.append("Misc: ");
        sb.append(misc);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage("AlertDialog Message");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{etDialog.getText().toString()});
                i.putExtra(Intent.EXTRA_SUBJECT, "Expenses of the month");
                i.putExtra(Intent.EXTRA_TEXT, sb.toString());
                try {
                    startActivity(Intent.createChooser(i, "Send email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }

    @OnClick(R.id.ivAdd)
    public void Adding(ImageView imageView) {
        Intent intentStart = new Intent(MainActivity.this,
                CreateItemActivity.class);
        startActivityForResult(intentStart, REQUEST_NEW_ITEM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                String itemID = data.getStringExtra(
                        CreateItemActivity.KEY_ITEM);

                Item item = getRealm().where(Item.class)
                        .equalTo("itemID", itemID)
                        .findFirst();

                if (requestCode == REQUEST_NEW_ITEM) {
                    showSnackBarMessage(getString(R.string.txt_item_added));
                }
                break;
            case RESULT_CANCELED:
                showSnackBarMessage(getString(R.string.txt_add_cancel));
                break;
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(constraintLayout,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        refreshSum();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((MainApplication) getApplication()).closeRealm();
    }

}


