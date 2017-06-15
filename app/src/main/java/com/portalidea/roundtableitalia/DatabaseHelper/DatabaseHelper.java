package com.portalidea.roundtableitalia.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.portalidea.roundtableitalia.Model.CalendarDetails;
import com.portalidea.roundtableitalia.Model.InternationalDetails;
import com.portalidea.roundtableitalia.Model.ItalianDetails;
import com.portalidea.roundtableitalia.Model.UserDetails;
import com.portalidea.roundtableitalia.Model.ZoneUserDetails;

import java.util.ArrayList;

/**
 * Created by archirayan on 5/11/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASENAME = "offline.db";
    public static int DATABASEVERSION = 2;
    public static String TABLE_NAME_USER = "UserTable";
    public static String USER_TABLE_id = "id";
    public static String USER_TABLE_social_class = "social_class";
    public static String USER_TABLE_areaid = "areaid";
    public static String USER_TABLE_zoneid = "zoneid";
    public static String USER_TABLE_clubid = "clubid";
    public static String USER_TABLE_nationalID = "nationalID";
    public static String USER_TABLE_name = "name";
    public static String USER_TABLE_surname = "surname";
    public static String USER_TABLE_dob = "dob";
    public static String USER_TABLE_tavola = "tavola";
    public static String USER_TABLE_city = "city";
    public static String USER_TABLE_profession = "profession";
    public static String USER_TABLE_province = "province";
    public static String USER_TABLE_tel_phone = "tel_phone";
    public static String USER_TABLE_name_wife = "name_wife";
    public static String USER_TABLE_photo = "photo";
    public static String USER_TABLE_inc_area = "inc_area";
    public static String USER_TABLE_Postal_Code = "Postal_Code";
    public static String USER_TABLE_homephone = "homephone";
    public static String USER_TABLE_mobilephone = "mobilephone";
    public static String USER_TABLE_workphone = "workphone";
    public static String USER_TABLE_fax = "fax";
    public static String USER_TABLE_address_home = "address_home";
    public static String USER_TABLE_email = "email";
    public static String USER_TABLE_password = "password";
    public static String USER_TABLE_occupation = "occupation";
    public static String USER_TABLE_fb = "fb";
    public static String USER_TABLE_twitter = "twitter";
    public static String USER_TABLE_linkedin = "linkedin";
    public static String USER_TABLE_googleplus = "googleplus";
    public static String USER_TABLE_lat = "lat";
    public static String USER_TABLE_log = "log";
    public static String USER_TABLE_deviceid = "deviceid";
    public static String USER_TABLE_status = "status";
    public static String USER_TABLE_club_city = "club_city";
    public static String USER_TABLE_club_tavola_1 = "tavola1";
    public static String USER_TABLE_club_tavola_2 = "tavola2";
    public static String USER_TABLE_club_is_enable = "is_enable";
    public static String USER_TABLE_club_is_register = "is_register";

    public static String TABLE_NAME_INTERNATIONAL = "International";
    public static String INTERNATIONAL_IMAGE = "Image";
    public static String INTERNATIONAL_TEXT = "Text";
    public static String INTERNATIONAL_NAME = "Name";
    public static String INTERNATIONAL_URL = "URL";


//    if ([[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Presidente"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Vice Presidente"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Past President"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Corrispondente"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Tesoriere"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Segretario"] || [[[tempComitatoTableDataArray objectAtIndex:i] objectForKey:@"inc_area"] isEqualToString:@"Gestore Materiali"])



    // italian table

    public static String TABLE_NAME_USER_ITALIAN = "ItalianUserTable";

    public static String ITALIAN_NUMERO = "ItalianNumero";
    public static String ITALIAN_NOME = "ItalianNome";
    public static String ITALIAN_ZONA = "ItalianZona";
    public static String ITALIAN_TAVOLA_SCIOLTA = "ItalianTavolaSciolta";
    public static String ITALIAN_MADRINA = "ItalianMadrina";
    public static String ITALIAN_CHARTER_MEETING = "ItalianCharterMeeting";
    public static String ITALIAN_GEMELLATE = "ItalianGemellate";
    public static String ITALIAN_RIUNIONI = "ItalianRiunioni";
    public static String ITALIAN_RITROVO = "ItalianRitrovo";
    public static String ITALIAN_EMAIL = "ItalianEmail";
    public static String ITALIAN_WEB = "ItalianWeb";
    public static String ITALIAN_FACEBOOK = "ItalianFacebook";
    public static String ITALIAN_TWITTER = "ItalianTwitter";
    public static String ITALIAN_CAP = "ItalianCap";
    public static String ITALIAN_FAX = "ItalianFax";
    public static String ITALIAN_FOTO = "ItalianFoto";
    public static String ITALIAN_EDITOR = "ItalianEditor";
    public static String ITALIAN_NOTE = "ItalianNote";
    public static String ITALIAN_PRESIDENT = "ItalianPresident";


    public static String TABLE_NAME_USER_CALENDAR_DETAILS = "CalendarDetail";

    public static String CALENDAR_TITLE = "CalendarTitle";
    public static String CALENDAR_START_DATE = "CalendarStartDate";
    public static String CALENDAR_END_DATE = "CalendarEndDate";
    public static String CALENDAR_ORGANIZZATORE = "CalendarOrganizzatore";
    public static String CALENDAR_QUANDO = "CalendarQuando";
    public static String CALENDAR_SITO = "CalendarSito";
    public static String CALENDAR_IMAGE = "CalendarImage";


    public static String CREATE_USRE_TABLE_CALENDAR_DETAILS = "Create table " + TABLE_NAME_USER_CALENDAR_DETAILS + " ("
            + CALENDAR_TITLE + " text,"
            + CALENDAR_START_DATE + " text,"
            + CALENDAR_END_DATE + " text,"
            + CALENDAR_ORGANIZZATORE + " text,"
            + CALENDAR_QUANDO + " text,"
            + CALENDAR_SITO + " text,"
            + CALENDAR_IMAGE + " text);";

    Context context;

    public static String CREATE_USRE_TABLE_ITALIAN = "Create table " + TABLE_NAME_USER_ITALIAN + " ("
            + ITALIAN_NUMERO + " text,"
            + ITALIAN_NOME + " text,"
            + ITALIAN_ZONA + " text,"
            + ITALIAN_TAVOLA_SCIOLTA + " text,"
            + ITALIAN_MADRINA + " text,"
            + ITALIAN_CHARTER_MEETING + " text,"
            + ITALIAN_GEMELLATE + " text,"
            + ITALIAN_RIUNIONI + " text,"
            + ITALIAN_RITROVO + " text,"
            + ITALIAN_EMAIL + " text,"
            + ITALIAN_WEB + " text,"
            + ITALIAN_FACEBOOK + " text,"
            + ITALIAN_TWITTER + " text,"
            + ITALIAN_CAP + " text,"
            + ITALIAN_FAX + " text,"
            + ITALIAN_FOTO + " text,"
            + ITALIAN_EDITOR + " text,"
            + ITALIAN_NOTE + " text,"
            + ITALIAN_PRESIDENT + " text);";


    public static String CREATE_USRE_TABLE = "Create table " + TABLE_NAME_USER + " ("
            + USER_TABLE_id + " text,"
            + USER_TABLE_social_class + " text,"
            + USER_TABLE_areaid + " text,"
            + USER_TABLE_zoneid + " text,"
            + USER_TABLE_clubid + " text,"
            + USER_TABLE_nationalID + " text,"
            + USER_TABLE_name + " text,"
            + USER_TABLE_surname + " text,"
            + USER_TABLE_dob + " text,"
            + USER_TABLE_tavola + " text,"
            + USER_TABLE_city + " text,"
            + USER_TABLE_profession + " text,"
            + USER_TABLE_province + " text,"
            + USER_TABLE_tel_phone + " text,"
            + USER_TABLE_name_wife + " text,"
            + USER_TABLE_photo + " text,"
            + USER_TABLE_inc_area + " text,"
            + USER_TABLE_Postal_Code + " text,"
            + USER_TABLE_homephone + " text,"
            + USER_TABLE_mobilephone + " text,"
            + USER_TABLE_workphone + " text,"
            + USER_TABLE_fax + " text,"
            + USER_TABLE_address_home + " text,"
            + USER_TABLE_email + " text,"
            + USER_TABLE_password + " text,"
            + USER_TABLE_occupation + " text,"
            + USER_TABLE_fb + " text,"
            + USER_TABLE_twitter + " text,"
            + USER_TABLE_linkedin + " text,"
            + USER_TABLE_googleplus + " text,"
            + USER_TABLE_lat + " text,"
            + USER_TABLE_log + " text,"
            + USER_TABLE_deviceid + " text,"
            + USER_TABLE_club_city + " text,"
            + USER_TABLE_club_is_enable + " text,"
            + USER_TABLE_club_is_register + " text,"
            + USER_TABLE_club_tavola_1 + " text,"
            + USER_TABLE_club_tavola_2 + " text,"
            + USER_TABLE_status + " text);";

    public static String CREATE_INTERNATIONAL_TABLE = "Create table " + TABLE_NAME_INTERNATIONAL + " ("
            + INTERNATIONAL_IMAGE + " text,"
            + INTERNATIONAL_TEXT + " text,"
            + INTERNATIONAL_NAME + " text,"
            + INTERNATIONAL_URL + " text);";


    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USRE_TABLE);
        db.execSQL(CREATE_INTERNATIONAL_TABLE);
        db.execSQL(CREATE_USRE_TABLE_ITALIAN);
        db.execSQL(CREATE_USRE_TABLE_CALENDAR_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_INTERNATIONAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER_ITALIAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER_CALENDAR_DETAILS);
        onCreate(db);
    }

    public boolean insertUserList(ArrayList<UserDetails> array) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_USER );

        for (int i = 0; i < array.size(); i++) {
            UserDetails details = array.get(i);

            ContentValues values = new ContentValues();
            values.put(USER_TABLE_id, details.getId());
            values.put(USER_TABLE_social_class, details.getSocial_class());
            values.put(USER_TABLE_areaid, details.getAreaid());
            values.put(USER_TABLE_zoneid, details.getZoneid());
            values.put(USER_TABLE_clubid, details.getClubid());
            values.put(USER_TABLE_nationalID, details.getNationalID());
            values.put(USER_TABLE_name, details.getName());
            values.put(USER_TABLE_surname, details.getSurname());
            values.put(USER_TABLE_dob, details.getDob());
            values.put(USER_TABLE_tavola, details.getTavola());
            values.put(USER_TABLE_city, details.getCity());
            values.put(USER_TABLE_profession, details.getProfession());
            values.put(USER_TABLE_province, details.getProvince());
            values.put(USER_TABLE_tel_phone, details.getTel_phone());
            values.put(USER_TABLE_name_wife, details.getName_wife());
            values.put(USER_TABLE_photo, details.getPhoto());
            values.put(USER_TABLE_inc_area, details.getInc_area());
            values.put(USER_TABLE_Postal_Code, details.getPostal_Code());
            values.put(USER_TABLE_homephone, details.getHomephone());
            values.put(USER_TABLE_mobilephone, details.getMobilephone());
            values.put(USER_TABLE_workphone, details.getWorkphone());
            values.put(USER_TABLE_fax, details.getFax());
            values.put(USER_TABLE_address_home, details.getAddress_home());
            values.put(USER_TABLE_email, details.getEmail());
            values.put(USER_TABLE_password, details.getPassword());
            values.put(USER_TABLE_occupation, details.getOccupation());
            values.put(USER_TABLE_fb, details.getFb());
            values.put(USER_TABLE_twitter, details.getTwitter());
            values.put(USER_TABLE_linkedin, details.getLinkedin());
            values.put(USER_TABLE_googleplus, details.getGoogleplus());
            values.put(USER_TABLE_lat, details.getLat());
            values.put(USER_TABLE_log, details.getLog());
            values.put(USER_TABLE_deviceid, details.getDeviceid());
            values.put(USER_TABLE_status, details.getStatus());
            values.put(USER_TABLE_club_city, details.getClub_city_name());
            values.put(USER_TABLE_club_is_enable, details.getIs_enable());
            values.put(USER_TABLE_club_is_register, details.getIsRegisted());
            values.put(USER_TABLE_club_tavola_1, details.getTavola1());
            values.put(USER_TABLE_club_tavola_2, details.getTavola2());
            // Inserting Row
            db.insert(TABLE_NAME_USER, null, values);
        }
        db.close();
        return true;
    }


// Insert calendar details

    public boolean insertUserCalendarDetails(ArrayList<CalendarDetails> array) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_USER_CALENDAR_DETAILS);

        for (int i = 0; i < array.size(); i++) {
            CalendarDetails details = array.get(i);

            ContentValues values = new ContentValues();
            values.put(CALENDAR_TITLE, details.getCalTitle());
            values.put(CALENDAR_START_DATE, details.getCalStartDate());
            values.put(CALENDAR_END_DATE, details.getCalEndDate());
            values.put(CALENDAR_ORGANIZZATORE, details.getCalOrganization());
            values.put(CALENDAR_QUANDO, details.getCalQuando());
            values.put(CALENDAR_SITO, details.getCalSito());
            values.put(CALENDAR_IMAGE, details.getCalImage());

            // Inserting Row
            db.insert(TABLE_NAME_USER_CALENDAR_DETAILS, null, values);

        }
        db.close();
        return true;
    }


    public boolean insertInternationalList(ArrayList<InternationalDetails> array) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_INTERNATIONAL);

        for (int i = 0; i < array.size(); i++) {
            InternationalDetails details = array.get(i);

            ContentValues values = new ContentValues();
            values.put(INTERNATIONAL_IMAGE, details.getImage());
            values.put(INTERNATIONAL_URL, details.getUrl());
            values.put(INTERNATIONAL_TEXT, details.getText());
            values.put(INTERNATIONAL_NAME, details.getName());


            // Inserting Row
            db.insert(TABLE_NAME_INTERNATIONAL, null, values);
        }
        db.close();
        return true;
    }


    public boolean insertItalianUserList(ArrayList<ItalianDetails> array) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_USER_ITALIAN);

        for (int i = 0; i < array.size(); i++) {
            ItalianDetails details = array.get(i);


            ContentValues values = new ContentValues();
            values.put(ITALIAN_NUMERO, details.getNumero());

            values.put(ITALIAN_NOME, details.getNome());
            values.put(ITALIAN_ZONA, details.getZona());
            values.put(ITALIAN_TAVOLA_SCIOLTA, details.getTavola_sciolta());
            values.put(ITALIAN_MADRINA, details.getMadrina());
            values.put(ITALIAN_CHARTER_MEETING, details.getCharter_meeting());
            values.put(ITALIAN_GEMELLATE, details.getGemellate());
            values.put(ITALIAN_RIUNIONI, details.getRiunioni());
            values.put(ITALIAN_EMAIL, details.getEmail());
            values.put(ITALIAN_WEB, details.getWeb());
            values.put(ITALIAN_FACEBOOK, details.getFacebook());
            values.put(ITALIAN_TWITTER, details.getTwitter());
            values.put(ITALIAN_CAP, details.getCap());
            values.put(ITALIAN_FAX, details.getFax());
            values.put(ITALIAN_FOTO, details.getFoto());
            values.put(ITALIAN_EDITOR, details.getEditor());
            values.put(ITALIAN_NOTE, details.getNote());
            values.put(ITALIAN_PRESIDENT, details.getPresident());


            // Inserting Row
            db.insert(TABLE_NAME_USER_ITALIAN, null, values);

        }
        db.close();
        return true;
    }

    // get calendar details data

    public ArrayList<CalendarDetails> getAllCalendarDetails() {

        ArrayList<CalendarDetails> array = new ArrayList<>();
        String selectQuery = "select * from " + TABLE_NAME_USER_CALENDAR_DETAILS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                CalendarDetails details = new CalendarDetails();
                details.setCalTitle(cursor.getString(cursor.getColumnIndex(CALENDAR_TITLE)));
                details.setCalImage(cursor.getString(cursor.getColumnIndex(CALENDAR_IMAGE)));

                details.setCalStartDate(cursor.getString(cursor.getColumnIndex(CALENDAR_START_DATE)));
                details.setCalEndDate(cursor.getString(cursor.getColumnIndex(CALENDAR_END_DATE)));
                details.setCalOrganization(cursor.getString(cursor.getColumnIndex(CALENDAR_ORGANIZZATORE)));
                details.setCalSito(cursor.getString(cursor.getColumnIndex(CALENDAR_SITO)));
                details.setCalQuando(cursor.getString(cursor.getColumnIndex(CALENDAR_QUANDO)));
                // Adding contact to list
                array.add(details);
            } while (cursor.moveToNext());
        }
        return array;
    }


    public ArrayList<ItalianDetails> getAllItalianUser() {
        ArrayList<ItalianDetails> array = new ArrayList<ItalianDetails>();
        String selectQuery = "select * from " + TABLE_NAME_USER_ITALIAN;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ItalianDetails details = new ItalianDetails();
                details.setNumero(cursor.getString(cursor.getColumnIndex(ITALIAN_NUMERO)));
                details.setNome(cursor.getString(cursor.getColumnIndex(ITALIAN_NOME)));
                details.setZona(cursor.getString(cursor.getColumnIndex(ITALIAN_ZONA)));
                details.setTavola_sciolta(cursor.getString(cursor.getColumnIndex(ITALIAN_TAVOLA_SCIOLTA)));
                details.setMadrina(cursor.getString(cursor.getColumnIndex(ITALIAN_MADRINA)));
                details.setCharter_meeting(cursor.getString(cursor.getColumnIndex(ITALIAN_CHARTER_MEETING)));
                details.setGemellate(cursor.getString(cursor.getColumnIndex(ITALIAN_GEMELLATE)));
                details.setRiunioni(cursor.getString(cursor.getColumnIndex(ITALIAN_RIUNIONI)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(ITALIAN_EMAIL)));
                details.setWeb(cursor.getString(cursor.getColumnIndex(ITALIAN_WEB)));
                details.setFacebook(cursor.getString(cursor.getColumnIndex(ITALIAN_FACEBOOK)));
                details.setTwitter(cursor.getString(cursor.getColumnIndex(ITALIAN_TWITTER)));
                details.setCap(cursor.getString(cursor.getColumnIndex(ITALIAN_CAP)));
                details.setFax(cursor.getString(cursor.getColumnIndex(ITALIAN_FAX)));
                details.setFoto(cursor.getString(cursor.getColumnIndex(ITALIAN_FOTO)));
                details.setEditor(cursor.getString(cursor.getColumnIndex(ITALIAN_EDITOR)));
                details.setNote(cursor.getString(cursor.getColumnIndex(ITALIAN_NOTE)));
                details.setPresident(cursor.getString(cursor.getColumnIndex(ITALIAN_PRESIDENT)));
                // Adding contact to list
                array.add(details);
            } while (cursor.moveToNext());
        }
        return array;
    }

    public ArrayList<ItalianDetails> getAllItalianUserByZone(String zone) {
        ArrayList<ItalianDetails> array = new ArrayList<ItalianDetails>();
        String selectQuery = "select * from " + TABLE_NAME_USER_ITALIAN + " where " + ITALIAN_ZONA + " = '" + zone + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ItalianDetails details = new ItalianDetails();
                details.setNumero(cursor.getString(cursor.getColumnIndex(ITALIAN_NUMERO)));
                details.setNome(cursor.getString(cursor.getColumnIndex(ITALIAN_NOME)));
                details.setZona(cursor.getString(cursor.getColumnIndex(ITALIAN_ZONA)));
                details.setTavola_sciolta(cursor.getString(cursor.getColumnIndex(ITALIAN_TAVOLA_SCIOLTA)));
                details.setMadrina(cursor.getString(cursor.getColumnIndex(ITALIAN_MADRINA)));
                details.setCharter_meeting(cursor.getString(cursor.getColumnIndex(ITALIAN_CHARTER_MEETING)));
                details.setGemellate(cursor.getString(cursor.getColumnIndex(ITALIAN_GEMELLATE)));
                details.setRiunioni(cursor.getString(cursor.getColumnIndex(ITALIAN_RIUNIONI)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(ITALIAN_EMAIL)));
                details.setWeb(cursor.getString(cursor.getColumnIndex(ITALIAN_WEB)));
                details.setFacebook(cursor.getString(cursor.getColumnIndex(ITALIAN_FACEBOOK)));
                details.setTwitter(cursor.getString(cursor.getColumnIndex(ITALIAN_TWITTER)));
                details.setCap(cursor.getString(cursor.getColumnIndex(ITALIAN_CAP)));
                details.setFax(cursor.getString(cursor.getColumnIndex(ITALIAN_FAX)));
                details.setFoto(cursor.getString(cursor.getColumnIndex(ITALIAN_FOTO)));
                details.setEditor(cursor.getString(cursor.getColumnIndex(ITALIAN_EDITOR)));
                details.setNote(cursor.getString(cursor.getColumnIndex(ITALIAN_NOTE)));
                details.setPresident(cursor.getString(cursor.getColumnIndex(ITALIAN_PRESIDENT)));
                // Adding contact to list
                array.add(details);
            } while (cursor.moveToNext());
        }
        return array;
    }


    public ArrayList<InternationalDetails> getAllInternational() {
        ArrayList<InternationalDetails> array = new ArrayList<InternationalDetails>();
        String selectQuery = "select * from " + TABLE_NAME_INTERNATIONAL;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                InternationalDetails details = new InternationalDetails();
                details.setImage(cursor.getString(cursor.getColumnIndex(INTERNATIONAL_IMAGE)));
                details.setName(cursor.getString(cursor.getColumnIndex(INTERNATIONAL_NAME)));
                details.setText(cursor.getString(cursor.getColumnIndex(INTERNATIONAL_TEXT)));
                details.setUrl(cursor.getString(cursor.getColumnIndex(INTERNATIONAL_URL)));

                // Adding contact to list
                array.add(details);
            } while (cursor.moveToNext());
        }
        return array;
    }


    public ArrayList<UserDetails> getAllUser() {
        ArrayList<UserDetails> array = new ArrayList<UserDetails>();
        String selectQuery = "select * from " + TABLE_NAME_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDetails details = new UserDetails();
                details.setId(cursor.getString(cursor.getColumnIndex(USER_TABLE_id)));
                details.setName(cursor.getString(cursor.getColumnIndex(USER_TABLE_name)));
                details.setSurname(cursor.getString(cursor.getColumnIndex(USER_TABLE_surname)));
                details.setPhoto(cursor.getString(cursor.getColumnIndex(USER_TABLE_photo)));
                details.setSocial_class(cursor.getString(cursor.getColumnIndex(USER_TABLE_social_class)));
                details.setAreaid(cursor.getString(cursor.getColumnIndex(USER_TABLE_areaid)));
                details.setZoneid(cursor.getString(cursor.getColumnIndex(USER_TABLE_zoneid)));
                details.setClubid(cursor.getString(cursor.getColumnIndex(USER_TABLE_clubid)));
                details.setNationalID(cursor.getString(cursor.getColumnIndex(USER_TABLE_nationalID)));
                details.setDob(cursor.getString(cursor.getColumnIndex(USER_TABLE_dob)));
                details.setTavola(cursor.getString(cursor.getColumnIndex(USER_TABLE_tavola)));
                details.setCity(cursor.getString(cursor.getColumnIndex(USER_TABLE_city)));
                details.setProfession(cursor.getString(cursor.getColumnIndex(USER_TABLE_profession)));
                details.setProvince(cursor.getString(cursor.getColumnIndex(USER_TABLE_province)));
                details.setTel_phone(cursor.getString(cursor.getColumnIndex(USER_TABLE_tel_phone)));
                details.setName_wife(cursor.getString(cursor.getColumnIndex(USER_TABLE_name_wife)));
                details.setInc_area(cursor.getString(cursor.getColumnIndex(USER_TABLE_inc_area)));
                details.setPostal_Code(cursor.getString(cursor.getColumnIndex(USER_TABLE_Postal_Code)));
                details.setHomephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_homephone)));
                details.setMobilephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_mobilephone)));
                details.setWorkphone(cursor.getString(cursor.getColumnIndex(USER_TABLE_workphone)));
                details.setFax(cursor.getString(cursor.getColumnIndex(USER_TABLE_fax)));
                details.setAddress_home(cursor.getString(cursor.getColumnIndex(USER_TABLE_address_home)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(USER_TABLE_email)));
                details.setPassword(cursor.getString(cursor.getColumnIndex(USER_TABLE_password)));
                details.setOccupation(cursor.getString(cursor.getColumnIndex(USER_TABLE_occupation)));
                details.setFb(cursor.getString(cursor.getColumnIndex(USER_TABLE_fb)));
                details.setTwitter(cursor.getString(cursor.getColumnIndex(USER_TABLE_twitter)));
                details.setLinkedin(cursor.getString(cursor.getColumnIndex(USER_TABLE_linkedin)));
                details.setGoogleplus(cursor.getString(cursor.getColumnIndex(USER_TABLE_googleplus)));
                details.setLat(cursor.getString(cursor.getColumnIndex(USER_TABLE_lat)));
                details.setLog(cursor.getString(cursor.getColumnIndex(USER_TABLE_log)));
                details.setDeviceid(cursor.getString(cursor.getColumnIndex(USER_TABLE_deviceid)));
                details.setStatus(cursor.getString(cursor.getColumnIndex(USER_TABLE_status)));
                details.setClub_city_name(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_city)));
                details.setIs_enable(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_is_enable)));
                details.setIsRegisted(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_is_register)));
                details.setTavola1(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_tavola_1)));
                details.setTavola2(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_tavola_2)));
                // Adding contact to list
                array.add(details);

            } while (cursor.moveToNext());
        }
        return array;
    }


    public ArrayList<ZoneUserDetails> getAllUserByZone(String zone) {
        ArrayList<ZoneUserDetails> array = new ArrayList<ZoneUserDetails>();
        String selectQuery = "select * from " + TABLE_NAME_USER + " where " + USER_TABLE_zoneid + " = '" + zone + "' and " + USER_TABLE_status + " = 'Tabler attivo' and " + USER_TABLE_inc_area + " != '' and " + USER_TABLE_inc_area + " = 'Presidente' or " + USER_TABLE_inc_area + " = 'Vice Presidente' or " + USER_TABLE_inc_area + " = 'Past President' or " + USER_TABLE_inc_area + " = 'Corrispondente' or " + USER_TABLE_inc_area + " = 'Tesoriere' or " + USER_TABLE_inc_area + " = 'Segretario' or " + USER_TABLE_inc_area + " = 'Gestore Materiali'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                ZoneUserDetails details = new ZoneUserDetails();
                details.setZona(cursor.getString(cursor.getColumnIndex(USER_TABLE_zoneid)));
                details.setFoto(cursor.getString(cursor.getColumnIndex(USER_TABLE_photo)));
                details.setTavola(cursor.getString(cursor.getColumnIndex(USER_TABLE_tavola)));
                details.setCognome(cursor.getString(cursor.getColumnIndex(USER_TABLE_surname)));
                details.setNome(cursor.getString(cursor.getColumnIndex(USER_TABLE_name)));
                details.setData_nascita(cursor.getString(cursor.getColumnIndex(USER_TABLE_dob)));
                details.setInc_zona(cursor.getString(cursor.getColumnIndex(USER_TABLE_inc_area)));
                details.setCitta(cursor.getString(cursor.getColumnIndex(USER_TABLE_city)));
                details.setTel_cellulare(cursor.getString(cursor.getColumnIndex(USER_TABLE_tel_phone)));
                details.setTel_ufficio(cursor.getString(cursor.getColumnIndex(USER_TABLE_homephone)));
                details.setNewcitta(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_city)));
                details.setStatus(cursor.getString(cursor.getColumnIndex(USER_TABLE_status)));
                details.setCap(cursor.getString(cursor.getColumnIndex(USER_TABLE_Postal_Code)));
                details.setIndirizzo(cursor.getString(cursor.getColumnIndex(USER_TABLE_address_home)));
                details.setProfessione(cursor.getString(cursor.getColumnIndex(USER_TABLE_profession)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(USER_TABLE_email)));
                details.setNome_moglie(cursor.getString(cursor.getColumnIndex(USER_TABLE_name_wife)));
                array.add(details);
//                UserDetails details = new UserDetails();
//                details.setId(cursor.getString(cursor.getColumnIndex(USER_TABLE_id)));
//                details.setName(cursor.getString(cursor.getColumnIndex(USER_TABLE_name)));
//                details.setSurname(cursor.getString(cursor.getColumnIndex(USER_TABLE_surname)));
//                details.setPhoto(cursor.getString(cursor.getColumnIndex(USER_TABLE_photo)));
//                details.setSocial_class(cursor.getString(cursor.getColumnIndex(USER_TABLE_social_class)));
//                details.setAreaid(cursor.getString(cursor.getColumnIndex(USER_TABLE_areaid)));
//                details.setZoneid(cursor.getString(cursor.getColumnIndex(USER_TABLE_zoneid)));
//                details.setClubid(cursor.getString(cursor.getColumnIndex(USER_TABLE_clubid)));
//                details.setNationalID(cursor.getString(cursor.getColumnIndex(USER_TABLE_nationalID)));
//                details.setDob(cursor.getString(cursor.getColumnIndex(USER_TABLE_dob)));
//                details.setTavola(cursor.getString(cursor.getColumnIndex(USER_TABLE_tavola)));
//                details.setCity(cursor.getString(cursor.getColumnIndex(USER_TABLE_city)));
//                details.setProfession(cursor.getString(cursor.getColumnIndex(USER_TABLE_profession)));
//                details.setProvince(cursor.getString(cursor.getColumnIndex(USER_TABLE_province)));
//                details.setTel_phone(cursor.getString(cursor.getColumnIndex(USER_TABLE_tel_phone)));
//                details.setName_wife(cursor.getString(cursor.getColumnIndex(USER_TABLE_name_wife)));
//                details.setInc_area(cursor.getString(cursor.getColumnIndex(USER_TABLE_inc_area)));
//                details.setPostal_Code(cursor.getString(cursor.getColumnIndex(USER_TABLE_Postal_Code)));
//                details.setHomephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_homephone)));
//                details.setMobilephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_mobilephone)));
//                details.setWorkphone(cursor.getString(cursor.getColumnIndex(USER_TABLE_workphone)));
//                details.setFax(cursor.getString(cursor.getColumnIndex(USER_TABLE_fax)));
//                details.setAddress_home(cursor.getString(cursor.getColumnIndex(USER_TABLE_address_home)));
//                details.setEmail(cursor.getString(cursor.getColumnIndex(USER_TABLE_email)));
//                details.setPassword(cursor.getString(cursor.getColumnIndex(USER_TABLE_password)));
//                details.setOccupation(cursor.getString(cursor.getColumnIndex(USER_TABLE_occupation)));
//                details.setFb(cursor.getString(cursor.getColumnIndex(USER_TABLE_fb)));
//                details.setTwitter(cursor.getString(cursor.getColumnIndex(USER_TABLE_twitter)));
//                details.setLinkedin(cursor.getString(cursor.getColumnIndex(USER_TABLE_linkedin)));
//                details.setGoogleplus(cursor.getString(cursor.getColumnIndex(USER_TABLE_googleplus)));
//                details.setLat(cursor.getString(cursor.getColumnIndex(USER_TABLE_lat)));
//                details.setLog(cursor.getString(cursor.getColumnIndex(USER_TABLE_log)));
//                details.setDeviceid(cursor.getString(cursor.getColumnIndex(USER_TABLE_deviceid)));
//                details.setStatus(cursor.getString(cursor.getColumnIndex(USER_TABLE_status)));
//                details.setClub_city_name(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_city)));
//                details.setIs_enable(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_is_enable)));
//                // Adding contact to list
//                array.add(details);


            } while (cursor.moveToNext());
        }
        return array;
    }


    public ArrayList<UserDetails> getLogin(String email, String password) {
        ArrayList<UserDetails> array = new ArrayList<UserDetails>();
        String selectQuery = "select * from " + TABLE_NAME_USER + " where " + USER_TABLE_email + "='" + email + "' and " + USER_TABLE_password + "='" + password + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserDetails details = new UserDetails();
                details.setId(cursor.getString(cursor.getColumnIndex(USER_TABLE_id)));
                details.setName(cursor.getString(cursor.getColumnIndex(USER_TABLE_name)));
                details.setSurname(cursor.getString(cursor.getColumnIndex(USER_TABLE_surname)));
                details.setPhoto(cursor.getString(cursor.getColumnIndex(USER_TABLE_photo)));
                details.setSocial_class(cursor.getString(cursor.getColumnIndex(USER_TABLE_social_class)));
                details.setAreaid(cursor.getString(cursor.getColumnIndex(USER_TABLE_areaid)));
                details.setZoneid(cursor.getString(cursor.getColumnIndex(USER_TABLE_zoneid)));
                details.setClubid(cursor.getString(cursor.getColumnIndex(USER_TABLE_clubid)));
                details.setNationalID(cursor.getString(cursor.getColumnIndex(USER_TABLE_nationalID)));
                details.setDob(cursor.getString(cursor.getColumnIndex(USER_TABLE_dob)));
                details.setTavola(cursor.getString(cursor.getColumnIndex(USER_TABLE_tavola)));
                details.setCity(cursor.getString(cursor.getColumnIndex(USER_TABLE_city)));
                details.setProfession(cursor.getString(cursor.getColumnIndex(USER_TABLE_profession)));
                details.setProvince(cursor.getString(cursor.getColumnIndex(USER_TABLE_province)));
                details.setTel_phone(cursor.getString(cursor.getColumnIndex(USER_TABLE_tel_phone)));
                details.setName_wife(cursor.getString(cursor.getColumnIndex(USER_TABLE_name_wife)));
                details.setInc_area(cursor.getString(cursor.getColumnIndex(USER_TABLE_inc_area)));
                details.setPostal_Code(cursor.getString(cursor.getColumnIndex(USER_TABLE_Postal_Code)));
                details.setHomephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_homephone)));
                details.setMobilephone(cursor.getString(cursor.getColumnIndex(USER_TABLE_mobilephone)));
                details.setWorkphone(cursor.getString(cursor.getColumnIndex(USER_TABLE_workphone)));
                details.setFax(cursor.getString(cursor.getColumnIndex(USER_TABLE_fax)));
                details.setAddress_home(cursor.getString(cursor.getColumnIndex(USER_TABLE_address_home)));
                details.setEmail(cursor.getString(cursor.getColumnIndex(USER_TABLE_email)));
                details.setPassword(cursor.getString(cursor.getColumnIndex(USER_TABLE_password)));
                details.setOccupation(cursor.getString(cursor.getColumnIndex(USER_TABLE_occupation)));
                details.setFb(cursor.getString(cursor.getColumnIndex(USER_TABLE_fb)));
                details.setTwitter(cursor.getString(cursor.getColumnIndex(USER_TABLE_twitter)));
                details.setLinkedin(cursor.getString(cursor.getColumnIndex(USER_TABLE_linkedin)));
                details.setGoogleplus(cursor.getString(cursor.getColumnIndex(USER_TABLE_googleplus)));
                details.setLat(cursor.getString(cursor.getColumnIndex(USER_TABLE_lat)));
                details.setLog(cursor.getString(cursor.getColumnIndex(USER_TABLE_log)));
                details.setDeviceid(cursor.getString(cursor.getColumnIndex(USER_TABLE_deviceid)));
                details.setStatus(cursor.getString(cursor.getColumnIndex(USER_TABLE_status)));
                details.setClub_city_name(cursor.getString(cursor.getColumnIndex(USER_TABLE_club_city)));
                // Adding contact to list
                array.add(details);

            } while (cursor.moveToNext());
        }
        return array;
    }

}
