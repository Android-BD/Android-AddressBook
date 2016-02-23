package com.bowenzhang.contact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btn_add;
    private ListView lv_contacts;

    private ContactAdapter adapter;
    private List<ContactInfo> contacts = new ArrayList<ContactInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = (Button) findViewById(R.id.btn_add);
        lv_contacts = (ListView) findViewById(R.id.lv_contacts);

        adapter = new ContactAdapter(this,contacts);
        lv_contacts.setAdapter(adapter);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
                setContactsData();
            }
        });

         lv_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 ContactInfo contact = adapter.getItem(position);

                 showLongClickDialog(contact);



             }
         });

        lv_contacts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo contact = adapter.getItem(position);
                ContactManager.deleteContact(MainActivity.this,contact);

                setContactsData();
                return true;
            }
        });

        setContactsData();
    }

    private void showLongClickDialog(final ContactInfo contact) {
        new AlertDialog.Builder(this)
                .setItems(new String[]{this.getString(R.string.call),this.getString(R.string.text),this.getString(R.string.update)},
                        new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        Intent intentCall = new Intent();
                                        intentCall.setAction(Intent.ACTION_CALL);
                                        intentCall.setData(Uri.parse("tel:" + contact.getPhone()));
                                        startActivity(intentCall);
                                        break;
                                    case 1:
                                        Intent intentSend = new Intent();
                                        intentSend.setAction(Intent.ACTION_SENDTO);
                                        intentSend.setData(Uri.parse("smsto://" + contact.getPhone()));
                                        startActivity(intentSend);
                                        break;
                                    case 2:
                                        showUpdateDialog(contact);
                                        setContactsData();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }).show();

    }


    private void setContactsData() {
        List<ContactInfo> contactData = ContactManager.getContacts(this);
        contacts.clear();
        contacts.addAll(contactData);
        adapter.notifyDataSetChanged();
    }

    private void showAddDialog(){
        View view = View.inflate(this, R.layout.dialog_contact, null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);
        new AlertDialog.Builder(this)
                .setTitle(this.getString(R.string.add))
                .setView(view)
                .setPositiveButton(this.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String string = et_name.getText().toString();
                        if (TextUtils.isEmpty(string)) {
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        } else {
                            ContactInfo contact = new ContactInfo();
                            contact.setName(et_name.getText() + "");
                            contact.setPhone(et_phone.getText() + "");

                            ContactManager.addContact(MainActivity.this, contact);
                            setContactsData();
                        }
                    }
                })
                .setNegativeButton(this.getString(R.string.cancel), null)
                .show();
    }

    private void showUpdateDialog(final ContactInfo oldContact){
        View view = View.inflate(this, R.layout.dialog_contact,null);

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_phone = (EditText) view.findViewById(R.id.et_phone);

        et_name.setText(oldContact.getName());
        et_phone.setText(oldContact.getPhone());

        new AlertDialog.Builder(this)
                .setTitle(this.getString(R.string.update))
                .setView(view)
                .setPositiveButton(this.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ContactInfo contact = new ContactInfo();
                        contact.setRawContactId(oldContact.getRawContactId());
                        contact.setName(et_name.getText() + "");
                        contact.setPhone(et_phone.getText() + "");

                        ContactManager.updateContact(MainActivity.this, contact);
                        setContactsData();
                    }
                })
                .setNegativeButton(this.getString(R.string.cancel),null)
                .show();
    }

}
