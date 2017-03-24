package com.kila.apprater_dialog.lars;

import android.content.Context;

import com.kila.apprater_dialog.lars.utils.Const;

public class AppRater {
    /**
     * Todo: Launch intervals
     * Todo: Stars to rate
     */
    private static final String TAG = AppRater.class.getCanonicalName();

    public static class Builder {
        private final Context context;
        private final String packageName;

        private int daysToWait;
        private int timesToLaunch;
        private String title;
        private String message;
        private String rateButtonText;
        private String notNowButtonText;
        private String neverButtonText;
        private int timesToLaunchInterval;

        public Builder(Context context, String packageName) {
            this.context = context;
            this.packageName = packageName;
        }

        public Builder showDefault() {
            daysToWait(Const.DEFAULT_DAYS_TO_WAIT);
            timesToLaunch(Const.DEFAULT_TIMES_TO_LAUNCH);
            title(context.getString(R.string.default_title));
            message(context.getString(R.string.default_message));
            rateButton(context.getString(R.string.default_rate_button_text));
            notNowButton(context.getString(R.string.default_not_now_button_text));
            neverButton(context.getString(R.string.default_never_button_text));
            timesToLaunchInterval(2);
            return this;
        }

        public Builder daysToWait(int daysToWait) {
            this.daysToWait = daysToWait;
            return this;
        }

        public Builder timesToLaunch(int timesToLaunch) {
            this.timesToLaunch = timesToLaunch;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder rateButton(String rateButtonText) {
            this.rateButtonText = rateButtonText;
            return this;
        }

        public Builder notNowButton(String notNowButtonText) {
            this.notNowButtonText = notNowButtonText;
            return this;
        }

        public Builder neverButton(String neverButtonText) {
            this.neverButtonText = neverButtonText;
            return this;
        }

        public Builder timesToLaunchInterval(int timesToLaunchInterval) {
            this.timesToLaunchInterval = timesToLaunchInterval;
            return this;
        }

        /**
         * Call this when the App get started (onCreate in MainActivity e.g)
         *
         * @return if the dialog was shown or not
         */
        public boolean appLaunched() {
            ConditionManager conditionManager = new ConditionManager(context);
            if (conditionManager.conditionsFulfilled(daysToWait, timesToLaunch, timesToLaunchInterval)) {
                showAppRaterDialog();
                conditionManager.refreshConditions();
                return true;
            } else {
                conditionManager.refreshConditions();
                return false;
            }
        }

        private void showAppRaterDialog() {
            AppRaterDialog.Builder builder = buildAppRaterDialog();
            builder.show();
        }

        private AppRaterDialog.Builder buildAppRaterDialog() {
            AppRaterDialog.Builder builder = new AppRaterDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            if (message != null)
                builder.setMessage(message);
            if (rateButtonText != null)
                builder.setPositiveButton(rateButtonText, packageName);
            if (notNowButtonText != null)
                builder.setNeutralButton(notNowButtonText);
            if (neverButtonText != null)
                builder.setNegativeButton(neverButtonText);
            return builder;
        }
    }
}
