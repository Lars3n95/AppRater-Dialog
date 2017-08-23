package com.kila.apprater_dialog.lars;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;

public class AppRaterDialog extends AlertDialog {

    protected AppRaterDialog(Context context) {
        super(context);
    }

    public static class Builder extends AlertDialog.Builder {
        private final Context context;
        private RatingBar ratingBar;
        private String packageName;
        private int minimumNumberOfStars;
        private String email;
        private String lowRateTitle;
        private String lowRateMessage;
        private String lowRateEmailButton;
        private String lowRateDismissButton;

        public Builder(Context context) {
            super(context);
            this.context = context;

            lowRateTitle = context.getString(R.string.star_title_improve_app);
            lowRateMessage = context.getString(R.string.star_message_improve_app);
            lowRateEmailButton = context.getString(R.string.star_mail_improve_app);
            lowRateDismissButton = context.getString(R.string.star_cancel_improve_app);
        }

        public AlertDialog.Builder setPositiveButton(String rateButtonText) {
            this.setPositiveButton(rateButtonText, new RateClickListener());
            return this;
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

        public void setMinimumNumberOfStars(int minimumNumberOfStars) {
            this.minimumNumberOfStars = minimumNumberOfStars;
        }

        public void showStars() {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_with_stars, null);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            this.setView(view);
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setLowRateTitle(String lowRateTitle) {
            this.lowRateTitle = lowRateTitle;
        }

        public void setLowRateMessage(String lowRateMessage) {
            this.lowRateMessage = lowRateMessage;
        }

        public void setLowRateEmailButton(String lowRateEmailButton) {
            this.lowRateEmailButton = lowRateEmailButton;
        }

        public void setLowRateDismissButton(String lowRateDismissButton) {
            this.lowRateDismissButton = lowRateDismissButton;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        private class RateClickListener implements OnClickListener {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (ratingBar != null) {
                    if (ratingBar.getRating() >= minimumNumberOfStars) {
                        openPlayStore(packageName);
                    } else {
                        if(email != null) {
                            new AlertDialog.Builder(context)
                                    .setTitle(lowRateTitle)
                                    .setMessage(lowRateMessage)
                                    .setPositiveButton(lowRateEmailButton, new OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            openEmailApp();
                                        }
                                    })
                                    .setNegativeButton(lowRateDismissButton, new OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                        }
                                    }).show();
                        }
                    }
                } else {
                    openPlayStore(packageName);
                }
                new ConditionManager(context).dontShowDialogAgain();
            }

            private void openEmailApp() {
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.fromParts(
                        "mailto",email, null));
                intent.setType("message/rfc822");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {email});
                Intent mailer = Intent.createChooser(intent, null);
                context.startActivity(mailer);
            }

            private void openPlayStore(String packageName) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                } catch (ActivityNotFoundException e){
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                }
            }
        }
    }
}
