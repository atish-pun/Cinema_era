//package com.example.cinemaera;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class Film_details_adapter extends RecyclerView.Adapter<Film_details_adapter.ViewHolder> {
//    Context context;
//    List<Film_Details> film_detail = new ArrayList<>();
//
//    public Film_details_adapter(Context context, List<Film_Details> film_detail){
//        this.context = context;
//        this.film_detail=film_detail;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.film_details,parent,false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.fname.setText(film_detail.get(position).getFname());
//    }
//
//    @Override
//    public int getItemCount() {
//        return film_detail.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//        TextView fname;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            fname = itemView.findViewById(R.id.inner_Fname);
//        }
//    }
//}
