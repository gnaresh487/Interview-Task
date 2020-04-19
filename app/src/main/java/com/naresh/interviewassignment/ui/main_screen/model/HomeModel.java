
package com.naresh.interviewassignment.ui.main_screen.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HomeModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("owner")
    @Expose
    private Owner owner;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("watchers")
    @Expose
    private Integer watchers;

    public final static Creator<HomeModel> CREATOR = new Creator<HomeModel>() {

        @SuppressWarnings({
            "unchecked"
        })
        public HomeModel createFromParcel(Parcel in) {
            return new HomeModel(in);
        }

        public HomeModel[] newArray(int size) {
            return (new HomeModel[size]);
        }

    };

    protected HomeModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.fullName = ((String) in.readValue((String.class.getClassLoader())));
        this.owner = ((Owner) in.readValue((Owner.class.getClassLoader())));
        this.htmlUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.watchers = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public HomeModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWatchers() {
        return watchers;
    }

    public void setWatchers(Integer watchers) {
        this.watchers = watchers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(fullName);
        dest.writeValue(owner);
        dest.writeValue(htmlUrl);
        dest.writeValue(description);
        dest.writeValue(watchers);
    }

    public int describeContents() {
        return  0;
    }

    public static DiffUtil.ItemCallback<HomeModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<HomeModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull HomeModel oldItem, @NonNull HomeModel newItem) {
            return oldItem.getId()== newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull HomeModel oldItem, @NonNull HomeModel newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        HomeModel article = (HomeModel) obj;
        return article.getId() == this.getId();
    }

}
