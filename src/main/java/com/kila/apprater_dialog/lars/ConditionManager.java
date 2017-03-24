package com.kila.apprater_dialog.lars;

import android.content.Context;
import android.content.SharedPreferences;

import com.kila.apprater_dialog.lars.utils.Const;

public class ConditionManager {

    private final Context context;

    ConditionManager(Context context) {
        this.context = context;
        setFirstLaunchDay();
    }

    /**
     * Set firstLaunchDay if neccessary
     */
    private void setFirstLaunchDay() {
        SharedPreferences preferences = context.getSharedPreferences(Const.SHARED_PREFERENCES, 0);
        long firstLaunchDay = preferences.getLong(Const.SHARED_PREFERENCES_DAYS, -1);
        if (firstLaunchDay == -1) {
            preferences.edit().putLong(Const.SHARED_PREFERENCES_DAYS, System.currentTimeMillis()).apply();
        }
    }

    /**
     * Check if the app is installed long enough (daysToWait) and launched often enough (timesToLauch)
     *
     * @return if the dialog should be shown
     */
    boolean conditionsFulfilled(int daysToWait, int timesToLaunch, int timesToLaunchInterval) {
        return daysToWaitReached(daysToWait) && timesToLaunchReached(timesToLaunch, timesToLaunchInterval) && showDialog();
    }

    /**
     * Wait at least daysToWait days before showing the dialog
     *
     * @param daysToWait number of days to wait
     * @return if the number of days is reached
     */
    private boolean daysToWaitReached(int daysToWait) {
        long firstLaunchDay = context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).getLong(Const.SHARED_PREFERENCES_DAYS, -1);
        return firstLaunchDay != -1 && System.currentTimeMillis() >= (firstLaunchDay + (daysToWait * 24 * 60 * 60 * 1000));
    }

    /**
     * Wait at least timesToLaunch launches before showing the dialog
     *
     * @param timesToLaunch         number of times the app has to be launched
     * @param timesToLaunchInterval how often the app has to be launched again (after 'Not now') to show dialog
     * @return if the number of launches is reached
     */
    private boolean timesToLaunchReached(int timesToLaunch, int timesToLaunchInterval) {
        long timesLaunched = context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).getLong(Const.SHARED_PREFERENCES_LAUNCHES, 0);
        return (timesLaunched >= timesToLaunch) && ((timesLaunched - timesToLaunch) % timesToLaunchInterval == 0);
    }

    /**
     * If the app was rated or the user does not want to rate don't show the dialog again
     *
     * @return if the dialog should be shown again
     */
    private boolean showDialog() {
        return context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).getBoolean(Const.SHARED_PREFERENCES_SHOW, true);
    }

    /**
     * Increase number of times the app has been launched
     */
    void refreshConditions() {
        long numberOfLaunches = context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).getLong(Const.SHARED_PREFERENCES_LAUNCHES, 0);
        context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).edit().putLong(Const.SHARED_PREFERENCES_LAUNCHES, numberOfLaunches + 1).apply();
    }

    void dontShowDialogAgain() {
        context.getSharedPreferences(Const.SHARED_PREFERENCES, 0).edit().putBoolean(Const.SHARED_PREFERENCES_SHOW, false).apply();
    }
}
