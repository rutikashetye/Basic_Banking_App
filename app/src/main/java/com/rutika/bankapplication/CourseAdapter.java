package com.rutika.bankapplication;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<CourseModal> courseModalArrayList;
    private mOnClickListener mOnClickListener;
    private Context context;

    // creating a constructor for our variables.
    public CourseAdapter(ArrayList<CourseModal> courseModalArrayList, Context context , mOnClickListener mOnClickListener) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        this.mOnClickListener=mOnClickListener;
    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<CourseModal> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        courseModalArrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(view, mOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        CourseModal modal = courseModalArrayList.get(position);
        holder.Name.setText(modal.getCourseName());
        holder.Accno.setText(modal.getCourseDescription());
        holder.Balance.setText(modal.getBalance());
    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // creating variables for our views.
        private TextView Name, Accno, Balance;
        mOnClickListener mOnClickListener;

        public ViewHolder(@NonNull View itemView,CourseAdapter.mOnClickListener obj1) {
            super(itemView);
            // initializing our views with their ids.
            Name = itemView.findViewById(R.id.idTVCustName);
            Accno = itemView.findViewById(R.id.idTVAccNo);
            Balance = itemView.findViewById(R.id.idTVBalance);
            this.mOnClickListener=obj1;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.mOnclick(getAdapterPosition());
        }
    }
        public interface mOnClickListener{
            void mOnclick(int position);
        }

    }


