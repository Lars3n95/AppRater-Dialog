package com.kila.apprater_dialog.lars;

import android.app.AlertDialog;
import android.content.Context;

import com.kila.apprater_dialog.lars.utils.Const;

public class AppRater {
    /**
     * Todo: Launch intervals
     * Todo: Stars to rate
     */
    private static final String TAG = AppRater.class.getCanonicalName();

    public static class DefaultBuilder {
        protected final Context context;
        protected final String packageName;

        protected int daysToWait;
        protected int timesToLaunch;
        protected String title;
        protected String message;
        protected String rateButtonText;
        protected String notNowButtonText;
        protected String neverButtonText;
        protected int timesToLaunchInterval = 1;
        protected boolean cancelable;

        public DefaultBuilder(Context context, String packageName) {
            this.context = context;
            this.packageName = packageName;
        }

        public DefaultBuilder showDefault() {
            daysToWait(Const.DEFAULT_DAYS_TO_WAIT);
            timesToLaunch(Const.DEFAULT_TIMES_TO_LAUNCH);
            title(context.getString(R.string.default_title));
            message(context.getString(R.string.default_message));
            rateButton(context.getString(R.string.default_rate_button_text));
            notNowButton(context.getString(R.string.default_not_now_button_text));
            neverButton(context.getString(R.string.default_never_button_text));
            timesToLaunchInterval(Const.DEFAULT_TIMES_TO_LAUNCH_INTERVAL);
            isCancelable(true);
            return this;
        }

        public DefaultBuilder daysToWait(int daysToWait) {
            this.daysToWait = daysToWait;
            return this;
        }

        public DefaultBuilder timesToLaunch(int timesToLaunch) {
            this.timesToLaunch = timesToLaunch;
            return this;
        }

        public DefaultBuilder title(String title) {
            this.title = title;
            return this;
        }

        public DefaultBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DefaultBuilder rateButton(String rateButtonText) {
            this.rateButtonText = rateButtonText;
            return this;
        }

        public DefaultBuilder notNowButton(String notNowButtonText) {
            this.notNowButtonText = notNowButtonText;
            return this;
        }

        public DefaultBuilder neverButton(String neverButtonText) {
            this.neverButtonText = neverButtonText;
            return this;
        }

        public DefaultBuilder timesToLaunchInterval(int timesToLaunchInterval) {
            this.timesToLaunchInterval = timesToLaunchInterval;
            return this;
        }

        public DefaultBuilder isCancelable(boolean cancelable) {
            this.cancelable = cancelable;
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

        protected AlertDialog showAppRaterDialog() {
            AppRaterDialog.Builder builder = buildAppRaterDialog();
            AlertDialog dialog = builder.create();
            builder.show();
            return dialog;
        }

        protected AppRaterDialog.Builder buildAppRaterDialog() {
            AppRaterDialog.Builder builder = new AppRaterDialog.Builder(context);
            if (title != null)
                builder.setTitle(title);
            if (message != null)
                builder.setMessage(message);
            if (rateButtonText != null)
                builder.setPositiveButton(rateButtonText);
            if (notNowButtonText != null)
                builder.setNeutralButton(notNowButtonText);
            if (neverButtonText != null)
                builder.setNegativeButton(neverButtonText);
            builder.setCancelable(cancelable);
            return builder;
        }
    }

    public static class StarBuilder extends DefaultBuilder {
        private boolean showStars = true;
        private int minimumNumberOfStars;
        private String email;

        public StarBuilder(Context context, String packageName) {
            super(context, packageName);
        }

        @Override
        public StarBuilder showDefault() {
            super.showDefault();
            title(context.getString(R.string.star_title));
            message(context.getString(R.string.star_message));
            rateButton(context.getString(R.string.star_rate_button_text));
            notNowButton(context.getString(R.string.star_not_now_button_text));
            neverButton(context.getString(R.string.star_never_button_text));
            minimumNumberOfStars(Const.DEFAULT_MINIMUM_NUMBER_OF_STARS);
            return this;
        }

        public StarBuilder minimumNumberOfStars(int minimumNumberOfStars) {
            this.minimumNumberOfStars = minimumNumberOfStars;
            return this;
        }

        public StarBuilder email(String email) {
            this.email = email;
            return this;
        }

        @Override
        protected AppRaterDialog.Builder buildAppRaterDialog() {
            AppRaterDialog.Builder builder = new AppRaterDialog.Builder(context);
            builder.setPackageName(packageName);
            if (title != null)
                builder.setTitle(title);
            if (message != null)
                builder.setMessage(message);
            if (rateButtonText != null)
                builder.setPositiveButton(rateButtonText);
            if (notNowButtonText != null)
                builder.setNeutralButton(notNowButtonText);
            if (neverButtonText != null)
                builder.setNegativeButton(neverButtonText);
            if (minimumNumberOfStars != 0)
                builder.setMinimumNumberOfStars(minimumNumberOfStars);
            if (showStars)
                builder.showStars();
            if (email != null)
                builder.setEmail(email);
            builder.setCancelable(cancelable);
            return builder;
        }
    }
}
