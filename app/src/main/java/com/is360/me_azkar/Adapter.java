package com.is360.me_azkar;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    Context context;
    Activity activity;
    ArrayList<Model> arrayList;
    DatabaseHelper databaseHelper;
    Boolean language = MainActivity.getLanguage();

    public Adapter(Context context, Activity activity, ArrayList<Model> arrayList) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        databaseHelper = new DatabaseHelper(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Adapter.viewHolder holder, final int position) {
        Model model = arrayList.get(position);
        holder.supplication_id.setText(model.getSupplication_id());
        holder.supplication_repeat.setText("Recite: " + model.getSupplication_repeat() + " time[s]");
        holder.supplication.setText(model.getSupplication());
        if (language) {
            holder.supplication_translation.setText(model.getSupplication_translation_en());
            holder.supplication_important_info.setText(model.getSupplication_important_info_en());
            holder.supplication_detail.setText(model.getSupplication_detail_en());
        } else {
            holder.supplication_translation.setText(model.getSupplication_translation_ur());
            holder.supplication_important_info.setText(model.getSupplication_important_info_ur());
            holder.supplication_detail.setText(model.getSupplication_detail_ur());
        }
        holder.supplication_reference_no.setText(model.getSupplication_reference_no());

        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIntent(position);
            }
        });

        holder.waShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    whatsappShareIntent(position);
                } catch (Exception e) {
                    Toast.makeText(context, "Error: In this time this feature is not supported in your mobile", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", getDisplayData(position));
                clipboard.setPrimaryClip(clip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void shareIntent(int position) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "ME AZKAR");
        intent.putExtra(Intent.EXTRA_TEXT, getDisplayData(position));
        intent.setType("text/plain");
        try {
            activity.startActivity(Intent.createChooser(intent, "Share To..."));
        } catch (Exception e) {
            Toast.makeText(context, "Unable to share..", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void whatsappShareIntent(final int position) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getDisplayData(position));
        intent.setType("text/plain");
        intent.setPackage("com.whatsapp");
        activity.startActivity(Intent.createChooser(intent, "Share To ..."));
    }

    public String getDisplayData(int position) {

        Model model = arrayList.get(position);
        if (language) {
            String data = "" +
                    "Supplication ID: " + model.getSupplication_id() + " | Recite " + model.getSupplication_repeat() + " time[s]" +
                    "\n" +
                    model.getSupplication_important_info_en() +
                    "\n\n" +
                    model.getSupplication() +
                    "\n\n" +
                    model.getSupplication_translation_en() +
                    "\n\n" +
                    model.getSupplication_detail_en() +
                    "\n\n" +
                    model.getSupplication_reference_no() +
                    "\n\n" +
                    "https://instagram.com/islamstatus360" +
                    "";
            return data;
        } else {
            String data = "" +
                    "Supplication ID: " + model.getSupplication_id() + " | Recite " + model.getSupplication_repeat() + " time[s]" +
                    "\n" +
                    model.getSupplication_important_info_ur() +
                    "\n\n" +
                    model.getSupplication() +
                    "\n\n" +
                    model.getSupplication_translation_ur() +
                    "\n\n" +
                    model.getSupplication_detail_ur() +
                    "\n\n" +
                    model.getSupplication_reference_no() +
                    "\n\n" +
                    "https://instagram.com/islamstatus360" +
                    "";
            return data;
        }
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        TextView supplication_id, supplication_repeat, supplication_important_info, supplication, supplication_translation, supplication_detail, supplication_reference_no;
        Button shareButton, waShareButton, copyButton;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            supplication_id = itemView.findViewById(R.id.supplication_id);
            supplication_repeat = itemView.findViewById(R.id.supplication_repeat_no);
            supplication_important_info = itemView.findViewById(R.id.supplication_important_info);
            supplication = itemView.findViewById(R.id.supplication);
            supplication_translation = itemView.findViewById(R.id.supplication_translation);
            supplication_detail = itemView.findViewById(R.id.supplication_detail);
            supplication_reference_no = itemView.findViewById(R.id.supplication_reference);

            shareButton = itemView.findViewById(R.id.shareButton);
            waShareButton = itemView.findViewById(R.id.whatsappButton);
            copyButton = itemView.findViewById(R.id.copyButton);

            if (language) {
                supplication_translation.setTypeface(Typeface.DEFAULT);
                supplication_important_info.setTypeface(Typeface.DEFAULT);
            } else {
                Typeface typeface = itemView.getResources().getFont(R.font.jameel_noori_nastaleeq_regular);
                supplication_translation.setTypeface(typeface);
                supplication_important_info.setTypeface(typeface);
                //verseTranslation.setTextDirection(View.TEXT_DIRECTION_FIRST_STRONG_LTR);
            }
        }
    }
}
