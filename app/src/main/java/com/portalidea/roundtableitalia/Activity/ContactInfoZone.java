package com.portalidea.roundtableitalia.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by archirayan on 6/14/2016.
 */
public class ContactInfoZone extends Activity implements View.OnClickListener {
    private LinearLayout addContactLinear;
    private String username, homephone, mobilephone;
    public String email, status, tavola, newcitta, citta, cognome, nome, cap, data_nascita, indirizzo, profession, office, cellular, image, inc_zona, moglie, provincia, tavola1, tavola2,national;
    public TextView TextviewTabler, TextViewUserFullName, TextViewInfo, TextViewDate, TextViewWifeName, TextViewAddress, TextViewMoglie;
    public Button ButtonHomeNumber, ButtonMobileNumber;
    private ImageView imageView;
    private ImageView emailIv, messageIv;
    private LinearLayout homeLinear, mobileLinear;
    private static final int PERMISSION_CONTACT_NUMBER = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        init();
    }

    private void init() {

        addContactLinear = (LinearLayout) findViewById(R.id.activity_user_profile_linear);
        TextviewTabler = (TextView) findViewById(R.id.TextviewTabler);
        TextViewUserFullName = (TextView) findViewById(R.id.TextViewUserFullName);
        TextViewInfo = (TextView) findViewById(R.id.TextViewInfo);
        TextViewDate = (TextView) findViewById(R.id.TextViewDate);
        TextViewWifeName = (TextView) findViewById(R.id.TextViewWifeName);
        TextViewAddress = (TextView) findViewById(R.id.TextViewAddress);
        ButtonHomeNumber = (Button) findViewById(R.id.ButtonHomeNumber);
        ButtonMobileNumber = (Button) findViewById(R.id.ButtonMobileNumber);
        emailIv = (ImageView) findViewById(R.id.activity_user_profile_email);
        homeLinear = (LinearLayout) findViewById(R.id.activity_user_profile_homenumberLinear);
        mobileLinear = (LinearLayout) findViewById(R.id.activity_user_profile_mobilenumberLinear);
        imageView = (ImageView) findViewById(R.id.activity_contactinfo_userimage);
        TextViewMoglie = (TextView) findViewById(R.id.TextViewMoglie);
        messageIv = (ImageView) findViewById(R.id.imageView6);
        if (getIntent().getExtras() != null) {

            status = getIntent().getExtras().getString("status", "");
            tavola1 = getIntent().getExtras().getString("tavola1", "");
            tavola2 = getIntent().getExtras().getString("tavola2", "");
            national = getIntent().getExtras().getString("national", "");
            tavola = getIntent().getExtras().getString("tavola", "");
            newcitta = getIntent().getExtras().getString("newcitta", "");
            citta = getIntent().getExtras().getString("citta", "");
            cognome = getIntent().getExtras().getString("cognome", "");
            nome = getIntent().getExtras().getString("nome", "");
            cap = getIntent().getExtras().getString("cap", "");
            data_nascita = getIntent().getExtras().getString("data_nascita", "");
            indirizzo = getIntent().getExtras().getString("indirizzo", "");
            profession = getIntent().getExtras().getString("profession", "");
            office = getIntent().getExtras().getString("office", "");
            cellular = getIntent().getExtras().getString("cellular", "");
            image = getIntent().getExtras().getString("image", "");
            inc_zona = getIntent().getExtras().getString("inc_zona", "");
            email = getIntent().getExtras().getString("email", "");
            moglie = getIntent().getExtras().getString("moglie", "");
            provincia = getIntent().getExtras().getString("provincia", "");


        }

        String tavolaString = Utils.getFinalTavolaString(national,inc_zona,tavola1,tavola2);


        ((TextView) findViewById(R.id.actionBarTitle)).setText(status);
        TextviewTabler.setText("RT" + tavola + " " + newcitta);
        TextViewUserFullName.setText(nome + " " + cognome);
        TextViewInfo.setText(tavolaString);
        TextViewDate.setText(data_nascita);
        TextViewWifeName.setText(indirizzo + " " + cap + " " + citta + "" + provincia);
        TextViewAddress.setText(profession);
        ButtonHomeNumber.setText(cellular);
        ButtonMobileNumber.setText(office);
        TextViewMoglie.setText(moglie);


        if (cellular.length() > 0) {
            messageIv.setImageDrawable(ContextCompat.getDrawable(ContactInfoZone.this, R.drawable.ic_msg));
            messageIv.setOnClickListener(this);
        } else {
            messageIv.setImageDrawable(ContextCompat.getDrawable(ContactInfoZone.this, R.drawable.ic_dis_msg));
        }


        emailIv.setOnClickListener(this);
        ButtonHomeNumber.setOnClickListener(this);
        ButtonMobileNumber.setOnClickListener(this);
        homeLinear.setOnClickListener(this);
        addContactLinear.setOnClickListener(this);
        mobileLinear.setOnClickListener(this);
        homeLinear.setOnClickListener(this);
        emailIv.setOnClickListener(this);
        String imgURL = image;
//        String imgURL =
        if (imgURL.length() > 3) {
            if (imgURL.substring(imgURL.length() - 3, imgURL.length()).equalsIgnoreCase("gif")) {
                Glide.with(ContactInfoZone.this)
                        .load(Uri.parse(imgURL))
                        .asGif().placeholder(R.drawable.ic_placeholder)
                        .into(imageView);
            } else {
                Glide.with(ContactInfoZone.this)
                        .load(imgURL)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(imageView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_profile_linear:
                if (addContact()) {
                    Toast.makeText(ContactInfoZone.this, R.string.contactsaved, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ButtonHomeNumber:
                if (ButtonHomeNumber.length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + ButtonHomeNumber.getText()));
                    startActivity(intent);
                }
                break;
            case R.id.ButtonMobileNumber:
                if (ButtonMobileNumber.length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + ButtonMobileNumber.getText()));
                    startActivity(intent);
                }
                break;
            case R.id.activity_user_profile_email:
                Intent mailClient = new Intent(Intent.ACTION_SEND);
                mailClient.setPackage("com.google.android.gm");
                mailClient.setType("message/rfc822");
                String[] TO = {email};
                mailClient.putExtra(Intent.EXTRA_EMAIL, TO);
                if (mailClient != null) {
                    startActivity(mailClient);
                } else {
                    Toast.makeText(ContactInfoZone.this, "Please install Gmail.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_user_profile_homenumberLinear:
                if (ButtonHomeNumber.length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + ButtonHomeNumber.getText()));
                    startActivity(intent);
                }
                break;
            case R.id.activity_user_profile_mobilenumberLinear:
                if (ButtonMobileNumber.length() > 3) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + ButtonMobileNumber.getText()));
                    startActivity(intent);
                }
                break;
            case R.id.imageView6:
                String number = "";
                if (cellular.length() < 1) {
                    number = office;
                } else {
                    number = cellular;
                }
                Uri uri = Uri.parse("smsto:" + number);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "");
                startActivity(it);
                break;
        }
    }

//    public boolean addContact() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (haveContactPermission()) {
//                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//                int rawContactInsertIndex = ops.size();
//
//                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
//
//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, email).build());
//
//                ops.add(ContentProviderOperation
//                        .newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, TextViewUserFullName.getText()) // Name of the person
//                        .build());
//
//                ops.add(ContentProviderOperation
//                        .newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(
//                                ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonHomeNumber.getText()) // Number of the person
//                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number
//
//                ops.add(ContentProviderOperation
//                        .newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(
//                                ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonMobileNumber.getText()) // Number of the person
//                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number
//
//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(image))
//                        .build());
//                try {
//                    ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//                    return true;
//                } catch (RemoteException e) {
//                    // error
//                    return false;
//                } catch (OperationApplicationException e) {
//                    // error
//                    return false;
//                }
//
//            } else {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS},
//                        PERMISSION_CONTACT_NUMBER);
//                return false;
//            }
//        } else {
//            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//            int rawContactInsertIndex = ops.size();
//
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());
//
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, email).build());
//
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, TextViewUserFullName.getText()) // Name of the person
//                    .build());
//
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(
//                            ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonHomeNumber.getText()) // Number of the person
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number
//
//            ops.add(ContentProviderOperation
//                    .newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(
//                            ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonMobileNumber.getText()) // Number of the person
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number
//
//            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(image))
//                    .build());
//            try {
//                ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//                return true;
//            } catch (RemoteException e) {
//                // error
//                return false;
//            } catch (OperationApplicationException e) {
//                // error
//                return false;
//            }
//        }
//
//    }

    public boolean addContact() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (haveContactPermission()) {
                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
                int rawContactInsertIndex = ops.size();

                ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, TextViewUserFullName.getText()) // Name of the person
                        .build());

//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, email).build());

                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(
                                ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonHomeNumber.getText()) // Number of the person
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number

                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(
                                ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonMobileNumber.getText()) // Number of the person
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(image))
                        .build());

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                        .build());


                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, TextViewWifeName.getText()).build());

                try {
                    ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                    return true;
                } catch (RemoteException e) {
                    Toast.makeText(ContactInfoZone.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    // error
                    return false;
                } catch (OperationApplicationException e) {
                    Toast.makeText(ContactInfoZone.this, "" + e.toString(), Toast.LENGTH_SHORT).show();
                    // error
                    return false;
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS},
                        PERMISSION_CONTACT_NUMBER);
                return false;
            }

        } else {


            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
            int rawContactInsertIndex = ops.size();

            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, TextViewUserFullName.getText()) // Name of the person
                    .build());

//                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                        .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
//                        .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
//                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
//                        .withValue(ContactsContract.CommonDataKinds.Email.DATA, email).build());

            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonHomeNumber.getText()) // Number of the person
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number

            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, ButtonMobileNumber.getText()) // Number of the person
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(image))
                    .build());

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                    .build());

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, TextViewWifeName.getText()).build());

            try {
                ContentProviderResult[] res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                return true;
            } catch (RemoteException e) {
                // error
                return false;
            } catch (OperationApplicationException e) {
                // error
                return false;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean haveContactPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static byte[] getBitmapFromURL(String src) {
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            return byteArray;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CONTACT_NUMBER:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addContact();
                } else {
                    Toast.makeText(ContactInfoZone.this, "Request not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
