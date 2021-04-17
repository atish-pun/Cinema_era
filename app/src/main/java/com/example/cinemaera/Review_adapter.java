package com.example.cinemaera;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.widget.KhaltiButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

class Review_adapter extends RecyclerView.Adapter<Review_adapter.ViewHolder> {
    Context context;
    List<Film.ReviewInfo> reviewInfo = new ArrayList<>();

    public Review_adapter(Context context, List<Film.ReviewInfo> reviewInfo){
        this.context = context;
        this.reviewInfo = reviewInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_review,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.reviewOutput.setText(reviewInfo.get(position).getReviews());
        holder.ratingOutput.setRating(reviewInfo.get(position).getRatedValue());
        holder.userName.setText(reviewInfo.get(position).getUserName());
        holder.ratingOutput.setIsIndicator(true);
        holder.ReviewLinear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.ic_favourite_delete_btn)
                        .setTitle("Delete Reviews")
                        .setMessage("Would you like to Delete your Review?")
                        .setCancelable(false)
                       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               String parameter = "Review_userType.php?id="+ reviewInfo.get(position).getId() +"&otoken="+ Util.FAVOURITE_TOKEN +"&uid="+ Util.SESSION_USERID;
                               String url = context.getString(R.string.server_api_url) + parameter;
                               RequestQueue queue = Volley.newRequestQueue(context);
                               StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                                   @Override
                                   public void onResponse(String response) {
                                       try {
                                           JSONObject obj = new JSONObject(response);
                                           if (obj.getInt("status") == 200) {
                                               reviewInfo.remove(position);
                                               notifyDataSetChanged();
                                               Log.d(TAG, "Review will be deleted only for specific users ");
                                           }
                                       } catch (JSONException e) {
                                           e.printStackTrace();
                                       }
                                   }
                               }, new Response.ErrorListener() {
                                   @Override
                                   public void onErrorResponse(VolleyError error) {
                                       Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               });
                               stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                               queue.add(stringRequest);
                           }
                       })
                        .setNegativeButton("No",null)
                        .show();
                return true;
    }
        });
    }

    @Override
    public int getItemCount() {
        return reviewInfo.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingOutput;
        TextView reviewOutput, userName;
        LinearLayout ReviewLinear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewOutput = itemView.findViewById(R.id.reviewOutput);
            ratingOutput = itemView.findViewById(R.id.Rates);
            userName = itemView.findViewById(R.id.Username);
            ReviewLinear =itemView.findViewById(R.id.ReviewLinear);
        }
    }
}
