package com.example.culturearound.PretrazivanjeZnamenitosti.recyclerview;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.culturearound.Firebase.FirebaseHelper;
import com.example.culturearound.Firebase.Listeners.ZnamenitostListener;
import com.example.culturearound.Firebase.LoginHelper;
import com.example.culturearound.Firebase.ZnamenitostiHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.example.culturearound.Firebase.EntitiesFirebase.Komentar;
import com.example.culturearound.Firebase.EntitiesFirebase.Korisnik;
import com.example.culturearound.Firebase.Listeners.UserListener;
import com.example.culturearound.Firebase.UserHelper;
import com.example.culturearound.R;

public class RecenzijeViewHolder extends ParentViewHolder implements UserListener {
   private UserHelper userHelper;
   private ZnamenitostiHelper znamenitostiHelper;
   private DatabaseReference mDataLike;
   private boolean mProcess=false;


   DatabaseReference mDataBaseLike;
   FirebaseAuth mAuth;

   Komentar komentar;
   Korisnik korisnik;
   Context context;
    @BindView(R.id.recenzija_opis)
    TextView opis;

    @BindView(R.id.recenzija_ocjena)
    TextView ocjena;

    @BindView(R.id.korime)
    TextView korime;

    @BindView(R.id.korinsnik_image)
    ImageView slika;

    @BindView(R.id.recenzija_ocjena_rate)
    RatingBar rate;

    public ImageButton like;
    public ImageButton dislike;






    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public RecenzijeViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        like = (ImageButton) itemView.findViewById(R.id.likeButton);
        dislike = (ImageButton) itemView.findViewById(R.id.dislikeButton);
        mDataBaseLike=FirebaseDatabase.getInstance().getReference().child("Like");
        mAuth=FirebaseAuth.getInstance();
        mDataBaseLike.keepSynced(true);

    }
    String rface;
    String rface2;
    public void setLikeBtn(String key,int idZnamenitost){
        mDataBaseLike.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child(key).child(korisnik.getUid()).hasChild(Integer.toString(idZnamenitost))) {
                    Log.d("Anjaaaa1",dataSnapshot.child(key).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").getValue().toString());
                    rface = (String)dataSnapshot.child(key).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").getValue();


                    if(rface.equals("Dislike")){
                        Log.d("Anjaaaa2", dataSnapshot.child(key).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").getValue().toString());
                        like.setImageResource(R.mipmap.ic_like_white_round);
                        dislike.setImageResource(R.mipmap.ic_dislike_red_round);
                    }
                    else{
                        like.setImageResource(R.mipmap.ic_like_reens_round);
                        dislike.setImageResource(R.mipmap.ic_dislike_white_round);
                        Log.d("Anjaaaa3", "tu sam");
                    }
                }
                else{

                    like.setImageResource(R.mipmap.ic_like_white_round);
                    dislike.setImageResource(R.mipmap.ic_dislike_white_round);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void bindToData (Komentar komentar, Context context){
        this.context = context;

        Log.d("Anja", "RecViewHold: postavljanje komentara");
        this.komentar = komentar;
        Log.d("Anja", "Dohvati uid: " + komentar.getUid());

        Log.d("Anja", "Ucitavanje userHelpera...");
        userHelper = new UserHelper(context, this);
        Log.d("Anja", "Ucitavanje userHelpera...DONE");

        userHelper.findUserById(komentar.getUid());
    }

    private void ucitajPodatke() {
        mDataLike=FirebaseDatabase.getInstance().getReference().child("Like");



        mDataLike.keepSynced(true);

        Log.d("Anja", "Setaj--opis");

        String opiss = komentar.getOpis();
        int ocjenaa = komentar.getOcjena();
        Log.d("Anja3", opiss);
        opis.setText(opiss);

        Log.d("Anja3", "Setaj--ocjenu");
        ocjena.setText(Integer.toString(ocjenaa));


        rate.setRating(komentar.getOcjena());
        Log.d("Anja1", "Setano sve");

        korime.setText(korisnik.getIme() + " " + korisnik.getPrezime());
        Log.d("Anja", "slika"+ korisnik.getLokacijaSlike());
        Picasso.with(itemView.getContext())
                .load(korisnik.getLokacijaSlike())
                .into(slika);

        String uid=userHelper.getmAuth().getUid();
        int idZnamenitost=komentar.getIdZnamenitost();

        setLikeBtn(uid,idZnamenitost);


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Anja6", "Dosao do tud");
                mProcess=true;

                    mDataLike.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(mProcess) {


                                if (dataSnapshot.child(uid).child(korisnik.getUid()).hasChild(Integer.toString(idZnamenitost))) {
                                    rface2 = (String)dataSnapshot.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").getValue();
                                    if(rface2.equals("Dislike")){
                                        mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").setValue("Like");
                                        mProcess = false;
                                    }
                                    else{
                                        mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").removeValue();
                                        mProcess = false;

                                    }

                                } else {
                                    mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").setValue("Like");
                                    mProcess = false;


                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

            }
        });


        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Anja7", "Dosao do tud");
                mProcess=true;

                mDataLike.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(mProcess) {

                            if (dataSnapshot.child(uid).child(korisnik.getUid()).hasChild(Integer.toString(idZnamenitost))) {
                                rface2 = (String)dataSnapshot.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").getValue();
                                if(rface2.equals("Like")){
                                    mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").setValue("Dislike");
                                    mProcess = false;
                                }
                                else{
                                    mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").removeValue();
                                    mProcess = false;

                                }
                            }
                            else {
                                mDataLike.child(uid).child(korisnik.getUid()).child(Integer.toString(idZnamenitost)).child("vote").setValue("Dislike");
                                mProcess = false;


                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }

    @Override
    public void onLoadUserSuccess(String message, Korisnik currentUser) {
        korisnik = currentUser;
        ucitajPodatke();

    }

    @Override
    public void onLoadUserFail(String message) {
        Log.d("Anja", message);
    }
}
