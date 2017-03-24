package com.kila.apprater_dialog.lars;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

public class AppRaterDialog extends AlertDialog {

    protected AppRaterDialog(Context context) {
        super(context);
    }

    public static class Builder extends AlertDialog.Builder {
        private final Context context;
        private RatingBar ratingBar;

        public Builder(Context context) {
            super(context);
            this.context = context;
        }

        public AlertDialog.Builder setPositiveButton(String rateButtonText, final String packageName, final int minimumNumberOfStars) {
            this.setPositiveButton(rateButtonText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (ratingBar != null) {
                        if (ratingBar.getRating() >= minimumNumberOfStars) {
                            openPlayStore(packageName);
                        }
                    }
                    new ConditionManager(context).dontShowDialogAgain();
                }
            });
            return this;
        }

        private void openPlayStore(String packageName) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        }

        public void setNeutralButton(String notNowButtonText) {
            this.setNeutralButton(notNowButtonText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }

        public void setNegativeButton(String neverButtonText) {
            this.setNegativeButton(neverButtonText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    new ConditionManager(context).dontShowDialogAgain();
                }
            });
        }

        public void showStars() {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_with_stars, null);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            this.setView(view);
        }
    }
}
