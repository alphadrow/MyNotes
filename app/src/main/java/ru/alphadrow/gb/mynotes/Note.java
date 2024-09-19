package ru.alphadrow.gb.mynotes;

import android.icu.util.LocaleData;
import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Note implements Parcelable{
    private String id;
    private String name;
    private String description;
    private Date dateOfCreation;
    private Importance importance;





    public Note(String name, String description, Date dateOfCreation, Importance importance) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
        this.importance = importance;
    }


    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
