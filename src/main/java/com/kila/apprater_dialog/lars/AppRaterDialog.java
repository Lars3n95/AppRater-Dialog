package com.kila.apprater_dialog.lars;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

public class AppRaterDialog extends AlertDialog {

    protected AppRaterDialog(Context context) {
        super(context);
    }

    public static class Builder extends AlertDialog.Builder{

        public Builder(Context context) {
            super(context);
        }

        public AlertDialog.Builder setPositiveButton(String rateButtonText, final String packageName){
            this.setPositiveButton(rateButtonText, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    openPlayStore(packageName);
                    new ConditionManager(getContext()).dontShowDialogAgain();
                }
            });
            return this;
        }

        private void openPlayStore(String packageName) {
            getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
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
                    new ConditionManager(getContext()).dontShowDialogAgain();
                }
            });
        }
    }
}
