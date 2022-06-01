package com.example.finalproject;

import static android.app.Activity.RESULT_OK;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.UUID;


public class PersonalFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    SharedPreferences sp;

    TextView tvNameInfo;
    TextView tvSignOut;

    Button btShareGame;

    Switch switchToggleSound;

    DBHelper dbHelper;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

    public PersonalFragment() {
        // Required empty public constructor
    }

    // TODO: Remember to add: when logging out of the user, change sp value (key='rememberme') to unchecked.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sp = Utils.defineSharedPreferences(requireActivity(), "mainRoot");

        tvSignOut = requireView().findViewById(R.id.tvSignOut);
        tvSignOut.setOnClickListener(this);

        tvNameInfo = requireView().findViewById(R.id.tvNameInfo);
        tvNameInfo.setText(Utils.getDataFromSharedPreferences(sp, "username", null));

        btShareGame = requireView().findViewById(R.id.btShareGame);
        btShareGame.setOnClickListener(this);

        dbHelper = new DBHelper(getContext(), null, null, 1);

        switchToggleSound = requireView().findViewById(R.id.switchToggleSound);
        switchToggleSound.setOnCheckedChangeListener(this);
        String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
        Utils.getUserFromDatabase(uuid, user -> switchToggleSound.setChecked(user.isSound()));
    }

    @Override
    public void onClick(View view) {
        if (view == tvSignOut) {
            sp.edit().putBoolean("rememberMe", false).apply();
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
        } else if (view == btShareGame) {
            String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
            Utils.getUserFromDatabase(uuid, user -> {
                if (Math.abs(user.getShares()) == 1) {
                    Toast.makeText(requireActivity(), "כבר שיתפת את המשחק!", Toast.LENGTH_SHORT).show();
                } else {
                    final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(pickContact, 1);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String phoneNumber = null;

        String textMessage = "הצטרף אליי והורד את אפליקציית 'תשחץ נא'! שם המשתמש שלי: " + Utils.getDataFromSharedPreferences(sp, "username", null);
        if (resultCode != RESULT_OK) return;

        if (requestCode == 1 && data != null) {
            Uri contactUri = data.getData();

            // Specify which fields you want
            // your query to return values for
            String[] queryFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

            // Perform your query - the contactUri
            // is like a "where" clause here
            try (Cursor cursor = requireContext().getContentResolver()
                    .query(contactUri, queryFields, null, null, null)) {
                // Double-check that you
                // actually got results
                if (cursor.getCount() == 0) return;

                // Pull out the first column of
                // the first row of data
                // that is your contact's name
                cursor.moveToFirst();
                // Retrieve contact number using name
                phoneNumber = getContactNumber(cursor.getString(0));
            }
        }
        // Create the text message with a string.
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, textMessage, null, null);
            String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
            mDatabase.child(uuid).child("shares").setValue(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getContactNumber(String name) {
        ContentResolver cr = requireContext().getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode(name));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.PhoneLookup._ID}, null, null, null);
        String contactId = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactId = cursor.getString((int) cursor.getColumnIndex(ContactsContract.PhoneLookup._ID));
            }
            cursor.close();
        }
        if (contactId == null) {
            return null;
        }
        uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contactId);
        cursor = cr.query(uri, new String[]{ContactsContract.Contacts.HAS_PHONE_NUMBER}, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int hasPhoneNumber = cursor.getInt((int) cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                if (hasPhoneNumber == 1) {
                    Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
                    if (phoneCursor != null) {
                        if (phoneCursor.moveToFirst()) {
                            String phoneNumber = phoneCursor.getString((int) phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phoneCursor.close();
                            return phoneNumber;
                        }
                    }
                }
            }
            cursor.close();
        }
        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.switchToggleSound) {
            String uuid = Utils.getDataFromSharedPreferences(sp, "UUID", null);
            mDatabase.child(uuid).child("sound").setValue(compoundButton.isChecked());
        }
    }
}