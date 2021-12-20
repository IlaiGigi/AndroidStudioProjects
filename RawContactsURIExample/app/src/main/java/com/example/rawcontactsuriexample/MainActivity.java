package com.example.rawcontactsuriexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText etInput, etInputNewName;
    Button btAddContact, btDeleteContact, btUpdateContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission(Manifest.permission.READ_CONTACTS);
        requestPermission(Manifest.permission.WRITE_CONTACTS);
        etInput = findViewById(R.id.etInput);
        etInputNewName = findViewById(R.id.etNewContactName);
        btAddContact = findViewById(R.id.btAddContact);
        btDeleteContact = findViewById(R.id.deleteContact);
        btUpdateContact = findViewById(R.id.btUpdateContact);
        btAddContact.setOnClickListener(this);
        btDeleteContact.setOnClickListener(this);
        btUpdateContact.setOnClickListener(this);
    }
    public boolean permissionGranted(String permission)
    {
        // Check if the app is api 23 or above
        // If so, must ask for permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_GRANTED)
            {
                Log.d("Permissions", "permission granted");
                return true;
            }
            Log.d("Permissions", "Permission was not granted");
            return false;
        }
        // Permission is automatically granted on sdk<23 upon installation
        Log.d("Permissions", "permission granted");
        return true;
    }
    public boolean requestPermission(String permission)
    {
        if (permissionGranted(permission))
            return true;
        // Ask for permission
        ActivityCompat.requestPermissions(this, new String[]{permission}, 1);
        return false;
    }

    private void deleteContact(String contactName){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        String where = ContactsContract.Data.DISPLAY_NAME_PRIMARY + " = ?";
        String[] nameParams = new String[]{contactName};
        ops.add(android.content.ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection(where, nameParams).build());
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void updateContact(String oldName, String newName){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        String where = ContactsContract.Data.DISPLAY_NAME_PRIMARY + " = ?";
        String[] nameParams = new String[]{oldName};
        ops.add(android.content.ContentProviderOperation.newUpdate(ContactsContract.RawContacts.CONTENT_URI).withValue(ContactsContract.Data.DISPLAY_NAME_PRIMARY, newName).withSelection(where, nameParams).build());
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (!etInput.getText().toString().equals("")){
            String contactName = etInput.getText().toString();
            if (view == btAddContact){
                ArrayList<ContentProviderOperation> ops = new ArrayList<>();
                int rawContactInsertIndex = ops.size();
                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                        .build());

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName)
                        .build());

                try {
                    getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            if (view == btDeleteContact){
                deleteContact(contactName);
            }
            if (view == btUpdateContact){
                String newName = etInputNewName.getText().toString();
                updateContact(contactName, newName);
            }
        }

    }
}