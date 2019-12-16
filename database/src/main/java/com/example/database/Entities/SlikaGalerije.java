package com.example.database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "slikaGalerije", primaryKeys = {"id_slika", "id_znamenitost"})
public class SlikaGalerije {

    @ForeignKey(entity = Slika.class, parentColumns = "id_slika", childColumns = "id_slika")
    @ColumnInfo(index = true)
    @NonNull
    public int id_slika;

    @ForeignKey(entity = Znamenitost.class, parentColumns = "id_znamenitost", childColumns = "id_znamenitost")
    @ColumnInfo(index = true)
    @NonNull public int id_znamenitost;

    public int getId_slika() {
        return id_slika;
    }

    public void setId_slika(int id_slika) {
        this.id_slika = id_slika;
    }

    public int getId_znamenitost() {
        return id_znamenitost;
    }

    public void setId_znamenitost(int id_znamenitost) {
        this.id_znamenitost = id_znamenitost;
    }
}
