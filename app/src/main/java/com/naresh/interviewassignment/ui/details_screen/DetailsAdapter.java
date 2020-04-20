package com.naresh.interviewassignment.ui.details_screen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.naresh.interviewassignment.R;
import com.naresh.interviewassignment.databinding.ContributorsListBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>{
    private List<ContributorModel> contributorModels;

    public DetailsAdapter() {
        this.contributorModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ContributorsListBinding contributorsListBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.contributors_list, viewGroup, false);
        return new DetailsViewHolder(contributorsListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        ContributorModel contributorModel = contributorModels.get(position);
        holder.contributorsListBinding.setContributors(contributorModel);
    }

    @Override
    public int getItemCount() {
        if (contributorModels != null) {
            return contributorModels.size();
        } else {
            return 0;
        }
    }

    void setContributorModels(List<ContributorModel> contributorModels) {
        this.contributorModels = contributorModels;
        notifyDataSetChanged();
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder {
        private ContributorsListBinding contributorsListBinding;

        DetailsViewHolder(ContributorsListBinding contributorsListBinding) {
            super(contributorsListBinding.getRoot());
            this.contributorsListBinding = contributorsListBinding;
        }
    }
}
