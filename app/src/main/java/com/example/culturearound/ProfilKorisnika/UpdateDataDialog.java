package com.example.culturearound.ProfilKorisnika;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.culturearound.Firebase.EntitiesFirebase.Korisnik;
import com.example.culturearound.Firebase.Listeners.UserListener;
import com.example.culturearound.Firebase.UserHelper;
import com.example.culturearound.R;

import butterknife.BindView;

public class UpdateDataDialog extends AppCompatDialogFragment implements UserListener {

    /*@BindView(R.id.btn_save)
    Button btnUpdateData;*/

    EditText userName;
    EditText userSurname;
    EditText userMail;

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

        builder.setView(view)
                .setTitle("Update data")
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

        mArgs = getArguments();

        userName.setText(mArgs.getString("firstName"), TextView.BufferType.EDITABLE);
        userSurname.setText(mArgs.getString("lastName"), TextView.BufferType.EDITABLE);
        userMail.setText(mArgs.getString("email"), TextView.BufferType.EDITABLE);
    }

    private void updateData() {

        String currentUserName, currentLastName, currentEmail;

        currentUserName = userName.getText().toString();
        currentLastName = userSurname.getText().toString();
        currentEmail = userMail.getText().toString();


        if(currentUserName.equals(mArgs.getString("firstName"))){
           currentUserName = "";
        }

        if(currentLastName.equals(mArgs.getString("lastName"))){
            currentLastName = "";
        }

        if(currentEmail.equals(mArgs.getString("email"))){
            currentEmail = "";
        }

        userHelper.updateData(currentUserName, currentLastName, currentEmail);
    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {

    }

    @Override
    public void onLoadUserFail(String message) {

    }
}
