package hu.ait.android.penz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;
import java.util.UUID;

import hu.ait.android.penz.data.Item;
import io.realm.Realm;


public class CreateItemActivity extends AppCompatActivity {
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String TOTAL = "TOTAL";
    private Spinner spinnerItemType;
    private EditText etName;
    private EditText etPrice;
    private EditText etItemDesc;
    private Item itemToEdit = null;
    public int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        setupUI();


    }

    private void initCreate() {
        getRealm().beginTransaction();
        itemToEdit = getRealm().createObject(Item.class, UUID.randomUUID().toString());
        getRealm().commitTransaction();
    }

    private void initEdit() {
        String itemID = getIntent().getStringExtra(ItemListActivity.KEY_EDIT);
        itemToEdit = getRealm().where(Item.class)
                .equalTo("itemID", itemID)
                .findFirst();

        etName.setText(itemToEdit.getItemName());
        etPrice.setText(itemToEdit.getPrice());
        etItemDesc.setText(itemToEdit.getDescription());

        spinnerItemType.setSelection(itemToEdit.getItemType().getValue());
    }

    private void setupUI() {
        spinnerItemType = (Spinner) findViewById(R.id.spinnerItemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.itemtypes_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemType.setAdapter(adapter);

        etName = (EditText) findViewById(R.id.etName);
        etPrice = (EditText) findViewById(R.id.etPrice);
        etItemDesc = (EditText) findViewById(R.id.etItemDesc);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem();
            }
        });
    }

    public Realm getRealm() {
        return ((MainApplication)getApplication()).getRealmItems();
    }

    private void saveItem() {

        if (getIntent().getSerializableExtra(ItemListActivity.KEY_EDIT) != null) {
            initEdit();
        } else {
            initCreate();
        }

        if (!TextUtils.isEmpty(etName.getText()) && !TextUtils.isEmpty(etPrice.getText())) {
            Intent intentResult = new Intent();
            getRealm().beginTransaction();
            itemToEdit.setItemName(etName.getText().toString());
            itemToEdit.setPrice(etPrice.getText().toString());
            itemToEdit.setDescription(etItemDesc.getText().toString());
            itemToEdit.setItemType(spinnerItemType.getSelectedItemPosition());
            getRealm().commitTransaction();

            intentResult.putExtra(KEY_ITEM, itemToEdit.getItemID());
            setResult(RESULT_OK, intentResult);
            finish();
        }

        else {
            if (TextUtils.isEmpty(etPrice.getText())) {
                etPrice.setError("Error! Try again");
            }

            if (TextUtils.isEmpty(etName.getText())) {
                etName.setError("Error! Try again");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TOTAL, total);
    }

}
