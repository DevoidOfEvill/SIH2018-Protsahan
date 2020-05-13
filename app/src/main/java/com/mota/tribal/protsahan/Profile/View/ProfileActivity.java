package com.mota.tribal.protsahan.Profile.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Login.SQLiteHandler;
import com.mota.tribal.protsahan.Login.View.AccountActivity;
import com.mota.tribal.protsahan.Login.View.SessionManager;
import com.mota.tribal.protsahan.MainActivity;
import com.mota.tribal.protsahan.Profile.Model.Data.Profile;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.Profile.Model.RetrofitProfileProvider;
import com.mota.tribal.protsahan.Profile.Presenter.ProfilePresenter;
import com.mota.tribal.protsahan.Profile.Presenter.ProfilePresenterImpl;
import com.mota.tribal.protsahan.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;

public class ProfileActivity extends AppCompatActivity implements
        EasyPermissions.PermissionCallbacks, ProfileView, VideoViewFragment.OnFragmentInteractionListener,
        DocViewFragment.OnFragmentInteractionListener, AdapterView.OnItemSelectedListener {

    private static final int REQUEST_GALLERY_CODE = 1;
    private static final int READ_REQUEST_CODE = 2;
    Button myDocs, save;
    private EditText name, tribe, description, address, aadhar, phoneNo, state, education;
    private ImageView myVideos, myImages;
    private TextView myVidText, myImgText;
    private RadioButton Male, Female, genderOther;

    private ProfilePresenter presenter;
    private CircleImageView profilePic;
    private String gender;
    private String token;
    private ProgressBar pBar;
    private Uri uri;
    private MultipartBody.Part fileToUpload;
    private RequestBody filename;
    private String username;
    private String tribalName, profilePicUrl;
    private SQLiteHandler db;
    private boolean hideState;
    private String countType;
    private String callFrom;
    private ArrayList<String> skillsSelected;
    private TextView skillsShowcase;
    private Spinner spinner;
    private ProgressBar profilePicProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        skillsSelected = new ArrayList<>();

        hideState = false;
        countType = "normal";
        if (getIntent().getExtras() != null)
            callFrom = getIntent().getExtras().getString("call_from");

        if (callFrom != null && !callFrom.equals("") && callFrom.equals("more_info")) {
            username = getIntent().getExtras().getString("username");
            token = "";
            countType = "normal_many";
            hideState = true;
            invalidateOptionsMenu();
        } else {
            db = new SQLiteHandler(this);
            username = db.getUser().getUsername();
            token = db.getUser().getToken();
        }

        name = findViewById(R.id.name);
        tribe = findViewById(R.id.tribe);
        description = findViewById(R.id.description);
        address = findViewById(R.id.address);
        aadhar = findViewById(R.id.aadhar);
        phoneNo = findViewById(R.id.phone_no);
        state = findViewById(R.id.state);
        education = findViewById(R.id.education);
        profilePicProgress = findViewById(R.id.profile_pic_progress_bar);

        spinner = findViewById(R.id.skills_spinner);

        List<String> skills = new ArrayList<String>();
        skills.add("Select a skill...");
        skills.add("Pottery");
        skills.add("Weaving");
        skills.add("Painting");
        skills.add("Stone Engraving");
        skills.add("Marble Engraving");
        skills.add("Clay Modelling");
        skills.add("Cloth Painting");
        skills.add("Bamboo Handcrats");
        skills.add("Gum Painting");
        skills.add("Wooden Handcrafts");
        skills.add("Stone Carving");
        skills.add("Collection of Honey");
        skills.add("Collection of Gum");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, skills);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);

        profilePic = findViewById(R.id.profile_pic);

        Male = findViewById(R.id.radioButton2);
        Female = findViewById(R.id.radioButton3);
        genderOther = findViewById(R.id.radioButton);
        skillsShowcase = findViewById(R.id.skills_show);

        myVideos = findViewById(R.id.imageView3);
        myImages = findViewById(R.id.imageView2);
        myVidText = findViewById(R.id.textView1);
        myImgText = findViewById(R.id.textView);
//        myDocs = findViewById(R.id.my_docs);
        save = findViewById(R.id.save);

        pBar = findViewById(R.id.progress_bar);

        name.setEnabled(false);
        tribe.setEnabled(false);
        description.setEnabled(false);
        address.setEnabled(false);
        aadhar.setEnabled(false);
        phoneNo.setEnabled(false);
        state.setEnabled(false);
        education.setEnabled(false);
        spinner.setVisibility(View.GONE);

        Male.setEnabled(false);
        Female.setEnabled(false);
        genderOther.setEnabled(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (!countType.equals("normal_many"))
            profilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("abhi", "In the on Click");
                    Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                    openGalleryIntent.setType("image/*");
                    startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
                }
            });


        //TODO:Need to get Id,Username from shared preference and store it in the variable "id"
//        token = getIntent().getExtras().getString("token");
        //      username = getIntent().getExtras().getString("username");

//        presenter = new ProfilePresenterImpl(this, new MockProfileProvider(), this);
        presenter = new ProfilePresenterImpl(this, new RetrofitProfileProvider(), this);
        presenter.getProfile(token, username);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            uri = data.getData();
            if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, ProfileActivity.this);
                File file = new File(filePath);
                Picasso.with(this).load(file).into(profilePic);
                Log.d("abhi", "Filename " + file.getName());
                //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                fileToUpload = MultipartBody.Part.createFormData("profilephoto", file.getName(), mFile);
                filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                presenter.postProfilePic(token, username, fileToUpload);
            } else {
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.profile_menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);

        if (hideState) {
            MenuItem item = menu.findItem(R.id.edit);
            MenuItem item1 = menu.findItem(R.id.logout);
            item.setVisible(false);
            item1.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit) {
            Log.d("abhi", "in the edit field");
            allowEdit();
        } else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to log out of the session ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SessionManager session = new SessionManager(ProfileActivity.this);
                            session.setLogin(false);
                            Intent intent = new Intent(ProfileActivity.this, AccountActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("Logout");
            alert.show();

        }
        return true;
    }

    private void allowEdit() {
        save.setVisibility(View.VISIBLE);

        name.setEnabled(true);
        tribe.setEnabled(true);
        description.setEnabled(true);
        address.setEnabled(true);
        aadhar.setEnabled(true);
        phoneNo.setEnabled(true);
        state.setEnabled(true);
        education.setEnabled(true);
        spinner.setVisibility(View.VISIBLE);

        Male.setEnabled(true);
        Female.setEnabled(true);
        genderOther.setEnabled(true);

//        myDocs.setVisibility(QueryView.GONE);
        myImages.setVisibility(View.GONE);
        myVideos.setVisibility(View.GONE);
        myImgText.setVisibility(View.GONE);
        myVidText.setVisibility(View.GONE);

        showMessage("You can now edit your profile!\nPress the save button to save the changes");
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.my_profile_relLayout), message, Snackbar.LENGTH_LONG).
                setAction("Action", null).show();
    }

    @Override
    public void showProgressBar(boolean b) {
        if (b)
            pBar.setVisibility(View.VISIBLE);
        else
            pBar.setVisibility(View.GONE);
    }

    @Override
    public void onProfilePosted() {
        showMessage("Changes Saved!");

        name.setEnabled(false);
        tribe.setEnabled(false);
        description.setEnabled(false);
        address.setEnabled(false);
        aadhar.setEnabled(false);
        phoneNo.setEnabled(false);
        state.setEnabled(false);
        spinner.setVisibility(View.GONE);

        name.setFocusable(false);
        tribe.setFocusable(false);
        description.setFocusable(false);
        address.setFocusable(false);
        aadhar.setFocusable(false);
        phoneNo.setFocusable(false);

        save.setVisibility(View.GONE);

//        myDocs.setVisibility(QueryView.VISIBLE);
        myImages.setVisibility(View.VISIBLE);
        myVideos.setVisibility(View.VISIBLE);
        myImgText.setVisibility(View.VISIBLE);
        myVidText.setVisibility(View.VISIBLE);

    }

    @Override
    public void onProfileGet(Profile profile) {
        if (!profile.equals(null)) {
            name.setText(profile.getName());
            tribalName = profile.getName();
            profilePicUrl = profile.getImg();
            tribe.setText(profile.getTribe());
            description.setText(profile.getDescription());
            aadhar.setText(profile.getAadhar());
            phoneNo.setText(profile.getPhone());
            address.setText(profile.getAddress());
            state.setText(profile.getState());
            education.setText(profile.getqualification());

            String skill = "";
            if (profile.getskills() != null)
                for (int i = 0; i < profile.getskills().size(); i++)
                    skill = skill + profile.getskills().get(i) + ", ";
            skillsShowcase.setText(skill);

            gender = profile.getGender();
            if (gender != null && gender.equals("Male")) {
                Male.setChecked(true);
                Male.setEnabled(true);
                Female.setChecked(false);
                genderOther.setChecked(false);
                gender = "Male";
            } else if (gender != null && gender.equals("Female")) {
                Female.setChecked(true);
                Female.setEnabled(true);
                Male.setChecked(false);
                genderOther.setChecked(false);
                gender = "Female";
            } else if (gender != null && gender.equals("Other")) {
                genderOther.setChecked(true);
                genderOther.setEnabled(true);
                Male.setChecked(false);
                Female.setChecked(false);
                gender = "Other";
            }
            if (profilePicUrl != null)
                Picasso.with(this).load(Urls.BASE_URL2 + profilePicUrl.substring(7)).
                        placeholder(R.drawable.mario_black).into(profilePic, new Callback() {
                    @Override
                    public void onSuccess() {
                        profilePicProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        }
    }

    @Override
    public void showVideos(ArrayList<VidImgDocData.Obj> objects) {

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("objects", objects);
        intent.putExtra("tribal_name", tribalName);
        intent.putExtra("profile_pic_url", profilePicUrl);
        intent.putExtra("type", "video");
        intent.putExtra("count_type", countType);
        startActivity(intent);
    }

    @Override
    public void showImages(ArrayList<VidImgDocData.Obj> objects) {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("objects", objects);
        intent.putExtra("tribal_name", tribalName);
        intent.putExtra("profile_pic_url", profilePicUrl);
        intent.putExtra("type", "image");
        intent.putExtra("count_type", countType);
        startActivity(intent);
    }

    @Override
    public void showDocs(ArrayList<VidImgDocData.Obj> urls) {
        Bundle bundle = new Bundle();
        Log.d("abhi", urls.get(0) + " " + urls.size());
        //bundle.putStringArrayList("urls", urls);
        bundle.putString("type_of_item", "docs");
        VideoViewFragment fragment = new VideoViewFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.my_profile_relLayout, fragment,
                        fragment.getClass().getSimpleName()).
                addToBackStack(null).commit();
    }


    public void save(View v) {
        if (phoneNo.getText().toString().trim().length() != 10)
            showMessage("Incorrect mobile number");
        else if (aadhar.getText().toString().trim().length() != 12)
            showMessage("Incorrect aadhar number");
        else if (name.getText().toString().equals("") || description.getText().toString().equals("")
                || tribe.getText().toString().equals("") || address.getText().toString().equals("") || state.getText().toString().equals(""))
            showMessage("Please Enter all the details");
        else if (gender == null)
            showMessage("Please select gender");
        else {
            Profile profile = new Profile(token, name.getText().toString(), description.getText().toString(),
                    tribe.getText().toString(), address.getText().toString(), aadhar.getText().toString(),
                    phoneNo.getText().toString(), gender, "", username, state.getText().toString(),
                    skillsSelected, education.getText().toString());
            presenter.postProfile(profile);
        }
    }

    public void GenderSelect(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioButton3:
                if (checked)
                    gender = "Female";
                break;
            case R.id.radioButton2:
                if (checked)
                    gender = "Male";
                break;
            case R.id.radioButton:
                if (checked)
                    gender = "Other";
                break;
        }
    }

    public void showMyVideos(View view) {
        presenter.getVideos(token, username);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void showMyImages(View view) {
        presenter.getImages(token, username);
    }

    public void showMyDocs(View view) {
        presenter.getDocs(token, username);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (uri != null) {
            profilePicProgress.setVisibility(View.VISIBLE);
            String filePath = getRealPathFromURIPath(uri, ProfileActivity.this);
            Log.d("abhi", filePath);
            File file = new File(filePath);
            Picasso.with(this).load(file).into(profilePic, new Callback() {
                @Override
                public void onSuccess() {
                    profilePicProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            presenter.postProfilePic(token, username, fileToUpload);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String skill = parent.getItemAtPosition(position).toString();
        Log.d("abhi", "" + skill);
        if (!skillsSelected.contains(skill) && !skill.equals("Select a skill...")) {
            skillsSelected.add(skill);
            skillsShowcase.setText(skillsShowcase.getText().toString() + skill + ", ");
        } else
            showMessage("Already Selected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}