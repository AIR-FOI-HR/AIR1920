package com.example.culturearound.Firebase.Listeners;

import com.example.culturearound.Firebase.EntitiesFirebase.Znamenitost;

import java.util.List;

public interface ZnamenitostListener {
    void onLoadZnamenitostSucess(String message, List<Znamenitost> listaZnamenitosti);
    void onLoadZnamenitostFail(String message);
}
