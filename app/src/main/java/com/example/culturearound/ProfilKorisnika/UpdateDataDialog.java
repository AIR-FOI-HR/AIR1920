package com.example.culturearound.ProfilKorisnika;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.database.EntitiesFirebase.Korisnik;
import com.example.database.Listeners.UserListener;
import com.example.database.UserHelper;
import com.example.culturearound.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;

public class UpdateDataDialog extends AppCompatDialogFragment implements UserListener, View.OnClickListener {

    /*@BindView(R.id.btn_save)
    Button btnUpdateData;*/

    EditText userName;
    EditText userSurname;
    EditText userMail;
    EditText userPictureUrl;
    ImageView userPicture;
    Button previewPicture;
    Button deletePicture;

    private String userId;
    private UserHelper userHelper;
    private  Bundle mArgs ;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_user_data, null);

        loadData(view);
        userHelper = new UserHelper(view.getContext(), this);
        userId = userHelper.returnUserId();

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateData();
                    }
                });

        return builder.create();
    }

    private void loadData(View view){

        userName = view.findViewById(R.id.update_ime) ;
        userSurname = view.findViewById(R.id.update_prezime) ;
        userMail = view.findViewById(R.id.update_email) ;
        userPictureUrl= view.findViewById(R.id.update_picture_url);
        userPicture = view.findViewById(R.id.update_userPicture);
        previewPicture = view.findViewById(R.id.update_preview_picture);
        deletePicture = view.findViewById(R.id.update_delete_picture);
        previewPicture.setOnClickListener(this);
        deletePicture.setOnClickListener(this);

        mArgs = getArguments();

        userName.setText(mArgs.getString("firstName"), TextView.BufferType.EDITABLE);
        userSurname.setText(mArgs.getString("lastName"), TextView.BufferType.EDITABLE);
        userMail.setText(mArgs.getString("email"), TextView.BufferType.EDITABLE);
        userPictureUrl.setText(mArgs.getString("url"), TextView.BufferType.EDITABLE);

        Picasso.with(getActivity())
                .load(mArgs.getString("url"))
                .into(userPicture);
    }

    String currentPictureUrl;

    private void updateData() {

        String currentUserName, currentLastName, currentEmail;

        currentUserName = userName.getText().toString();
        currentLastName = userSurname.getText().toString();
        currentEmail = userMail.getText().toString();
        currentPictureUrl = userPictureUrl.getText().toString();


        if(currentUserName.equals(mArgs.getString("firstName"))){
           currentUserName = "";
        }

        if(currentLastName.equals(mArgs.getString("lastName"))){
            currentLastName = "";
        }

        if(currentEmail.equals(mArgs.getString("email"))){
            currentEmail = "";
        }

        if(currentEmail.equals(mArgs.getString("url"))){
            currentEmail = "";
        }

        if(currentPictureUrl.equals(mArgs.getString("url"))){
            currentPictureUrl = "";
        }

        userHelper.updateData(currentUserName, currentLastName, currentEmail, currentPictureUrl, userId);
    }



    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }

    @Override
    public void onLoadUserFail(String message) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.update_preview_picture:
                currentPictureUrl = userPictureUrl.getText().toString();
                break;

            case R.id.update_delete_picture:
                currentPictureUrl = "https://www.speakingtigerbooks.com/wp-content/uploads/2017/07/no-avatar.png";
                userPictureUrl.setText(currentPictureUrl, TextView.BufferType.EDITABLE);
                break;

        }
        Picasso.with(getActivity())
                .load(currentPictureUrl)
                .into(userPicture);

    }
}
