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
import com.portalidea.roundtableitalia.Constant.Constant;
import com.portalidea.roundtableitalia.Constant.Utils;
import com.portalidea.roundtableitalia.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by archirayan on 5/2/2016.
 */
public class ContactInfo extends Activity implements View.OnClickListener {
    private static final int PERMISSION_CONTACT_NUMBER = 11;
    private LinearLayout addContactLinear;
    private String username, homephone, mobilephone;
    String id, social_class, areaid, zoneid,
            clubid, nationalID, surname,
            dob, tavola, city, profession, province,
            tel_phone, name_wife, photo, inc_area,
            Postal_Code, workphone, fax,
            address_home, email, password, occupation, fb, lat, log,
            twitter, linkedin, googleplus,
            deviceid, status, club_city_name, tavola1, tavola2;
    TextView TextviewTabler, TextViewUserFullName, TextViewInfo, TextViewDate, TextViewWifeName, TextViewAddress, TextViewProfession;
    Button ButtonHomeNumber, ButtonMobileNumber;
    ImageView imageView, emailIv, messageIv;
    LinearLayout homeLinear, mobileLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        (findViewById(R.id.actionBarBackImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        Intent intent = getIntent();
        username = intent.getStringExtra(Constant.UserName);
        homephone = intent.getStringExtra("homephone");
        mobilephone = intent.getStringExtra("mobilephone");
        id = intent.getStringExtra("id");
        social_class = intent.getStringExtra("social_class");
        areaid = intent.getStringExtra("areaid");
        zoneid = intent.getStringExtra("zoneid");
        clubid = intent.getStringExtra("clubid");
        nationalID = intent.getStringExtra("nationalID");
        surname = intent.getStringExtra("surname");
        dob = intent.getStringExtra("dob");
        tavola = intent.getStringExtra("tavola");
        city = intent.getStringExtra("city");
        profession = intent.getStringExtra("profession");
        province = intent.getStringExtra("province");
        tel_phone = intent.getStringExtra("tel_phone");
        name_wife = intent.getStringExtra("name_wife");
        photo = intent.getStringExtra("photo");
        inc_area = intent.getStringExtra("inc_area");
        Postal_Code = intent.getStringExtra("Postal_Code");
        workphone = intent.getStringExtra("workphone");
        fax = intent.getStringExtra("fax");
        address_home = intent.getStringExtra("address_home");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        occupation = intent.getStringExtra("occupation");
        fb = intent.getStringExtra("fb");
        lat = intent.getStringExtra("lat");
        log = intent.getStringExtra("log");
        twitter = intent.getStringExtra("twitter");
        linkedin = intent.getStringExtra("linkedin");
        googleplus = intent.getStringExtra("googleplus");
        deviceid = intent.getStringExtra("deviceid");
        status = intent.getStringExtra("status");
        club_city_name = intent.getStringExtra("club_city_name");
        tavola1 = intent.getStringExtra("tavola1");
        tavola2 = intent.getStringExtra("tavola2");
        init();
        ((TextView) findViewById(R.id.actionBarTitle)).setText(status);
        TextviewTabler.setText("RT" + tavola + " " + club_city_name);
        TextViewUserFullName.setText(username + " " + surname);
        TextViewInfo.setText(Utils.getFinalTavolaString(nationalID, inc_area, tavola1, tavola2));
        TextViewDate.setText(dob);
        TextViewWifeName.setText(name_wife);
        TextViewAddress.setText(address_home + " " + Postal_Code + " " + city + " " + province);
        ButtonHomeNumber.setText(homephone);
        ButtonMobileNumber.setText(mobilephone);
        TextViewProfession.setText(profession);
        String imgURL = photo;
        if (imgURL.length() > 3) {
            if (imgURL.substring(imgURL.length() - 3, imgURL.length()).equalsIgnoreCase("gif")) {
                Glide.with(ContactInfo.this)
                        .load(Uri.parse(imgURL))
                        .asGif().placeholder(R.drawable.ic_placeholder)
                        .into(imageView);
            } else {
                Glide.with(ContactInfo.this)
                        .load(imgURL)
                        .error(R.drawable.ic_user_blue)
                        .placeholder(R.drawable.ic_placeholder)
                        .into(imageView);
            }
        }

        if (ButtonHomeNumber.getText().length() > 0) {
            messageIv.setImageDrawable(ContextCompat.getDrawable(ContactInfo.this, R.drawable.ic_msg));
            messageIv.setOnClickListener(this);
        } else {
            messageIv.setImageDrawable(ContextCompat.getDrawable(ContactInfo.this, R.drawable.ic_dis_msg));
        }

        emailIv.setOnClickListener(this);
        ButtonHomeNumber.setOnClickListener(this);
        ButtonMobileNumber.setOnClickListener(this);
        homeLinear.setOnClickListener(this);
        addContactLinear.setOnClickListener(this);
        mobileLinear.setOnClickListener(this);
        homeLinear.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        TextViewProfession = (TextView) findViewById(R.id.TextViewPrefession);
        imageView = (ImageView) findViewById(R.id.activity_user_profile_image);
        emailIv = (ImageView) findViewById(R.id.activity_user_email);
        homeLinear = (LinearLayout) findViewById(R.id.activity_user_profile_homenumberLinear);
        mobileLinear = (LinearLayout) findViewById(R.id.activity_user_profile_mobilenumberLinear);
        messageIv = (ImageView) findViewById(R.id.activity_user_profile_msg);
    }


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
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, username + " " + surname) // Name of the person
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
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, homephone) // Number of the person
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number

                ops.add(ContentProviderOperation
                        .newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(
                                ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobilephone) // Number of the person
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number

                ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(photo))
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
                        .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, address_home).build());

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
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, username + " " + surname) // Name of the person
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
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, homephone) // Number of the person
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME).build()); // Type of mobile number

            ops.add(ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(
                            ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mobilephone) // Number of the person
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build()); // Type of mobile number

            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, getBitmapFromURL(photo))
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
                    .withValue(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, address_home).build());

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
                    Toast.makeText(ContactInfo.this, "Request not granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_user_profile_linear:
                if (addContact()) {
                    Toast.makeText(ContactInfo.this, R.string.contactsaved, Toast.LENGTH_SHORT).show();
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
            case R.id.activity_user_email:
                Intent mailClient = new Intent(Intent.ACTION_SEND);
                mailClient.setPackage("com.google.android.gm");
                mailClient.setType("message/rfc822");
                String[] TO = {email};
                mailClient.putExtra(Intent.EXTRA_EMAIL, TO);
                if (mailClient != null) {
                    startActivity(mailClient);
                } else {
                    Toast.makeText(ContactInfo.this, "Please install Gmail.", Toast.LENGTH_SHORT).show();
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
            case R.id.activity_user_profile_msg:
                String number = "";
                if (ButtonMobileNumber.length() > 3) {
                    number = ButtonMobileNumber.getText().toString();
                } else {
                    number = ButtonHomeNumber.getText().toString();
                }
                Uri uri = Uri.parse("smsto:" + number);
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", "");
                startActivity(it);
                break;


        }

    }
}
