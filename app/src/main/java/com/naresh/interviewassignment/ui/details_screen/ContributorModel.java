package com.naresh.interviewassignment.ui.details_screen;

import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.naresh.interviewassignment.R;

import java.io.Serializable;

public class ContributorModel implements Serializable {

    @SerializedName("avatar_url")
    @Expose
    private String contributorAvatar;

    @SerializedName("login")
    @Expose
    private String name;

    public String getContributorAvatar() {
        return contributorAvatar;
    }

    public void setContributorAvatar(String contributorAvatar) {
        this.contributorAvatar = contributorAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @BindingAdapter({ "avatar_url" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView);
    }

}
