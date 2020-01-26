package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.List;

public interface ZnamenitostListener {
    void onLoadSucess(String message, List<Znamenitost> listaZnamenitosti);
    void onLoadFail(String message);
}
