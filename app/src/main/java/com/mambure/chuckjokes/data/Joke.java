package com.mambure.chuckjokes.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "chuckJokes")
public class Joke {

   @PrimaryKey(autoGenerate = true)
   int index;

   @ColumnInfo public String id;
   @ColumnInfo public String url;
   @ColumnInfo public String value;

   public Joke(String id, String url, String value) {
      this.id = id;
      this.url = url;
      this.value = value;
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      return obj instanceof Joke && ((Joke) obj).id == id;
   }

   @NonNull
   @Override
   public String toString() {
      return value;
   }
}
