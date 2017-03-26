# AppRater-Dialog

An Android library providing the possibility to show a dialog, which asks the user to rate the app.

## What can you do with this library?

You can show customized dialogs to the user. Here are some examples:
![StarBuilder](http://imgur.com/XibDGKL.png?2 "StarBuilder")   
![StarBuilderMail](http://imgur.com/xbOUrud.png?2 "StarBuilderMail")   
![DefaultBuilder](http://imgur.com/X38eGJ6.png?2 "DefaultBuilder")


## How to use this library?

### Gradle
You can include the library via Gradle with the following dependencie:

     compile 'com.kila.apprater_dialog.lars:apprater-dialog:1.0.5'

### Builder

This library has two Builder with which you can build the AppRater-dialog.

#### DefaultBuilder
With this Builder you can build the default dialog with text only. You can change the texts, you can choose which buttons should be displayed, you can change the texts of the buttons and you can choose when the dialog should be shown.

To create a default dialog use the DefaultBuilder constructor:  

    new AppRater.DefaultBuilder(Context context, String packageName);

The context should be `this` of an Activity. The packageName is necessary to redirected the user to the PlayStore.

To show the dialog (if all conditions are fulfilled) call `appLaunched()`

    new AppRater.DefaultBuilder(Context context, String packageName).appLaunched();
    
This will build the dialog if all conditions are fullfilled. At this moment it will create an empty screen because there is no content you gave him.

If you just want to default texts and buttons like in the third picture above use `showDefault()`:

    new AppRater.DefaultBuilder(this, "com.kila.addnotification.lars").showDefault().appLaunched();
    
With this settings the dialog will be shown if the following conditions are fulfilled: The app is installed for a minimum of 3 days and the app is launched for a minimum of 5 times. Furthermore the dialog will be shown every second launch if the user clicks `Later`. If the rate or never button is clicked once, the dialog will never be shown again. This cannot be changed with the API and it is not recommendet to change this.

Between the constructor and the `appLaunched()` you can customize the dialog. You following possibilities:

+ change the number of days, the app has to be installed

      .daysToWait(int daysToWait)

+ change the number of launches, the app has to be launched

      .timesToLaunch(int timesToLaunch)

+ change the title

      .title(String title)

+ change the message

      .message(String message)
    
+ change the rate button's text

      .rateButton(String rateButtonText)
    
+ change the not now button's text

      .notNowButton(String nowNowButtonText)
    
+ change the never button's text

      .neverButton(String neverButtonText)
    
+ change the number of launches, the app has to be launched after a `Later` till the dialog is shown again

      .timesToLaunchInterval(int timesToLaunchInterval)
    
Here is an example of how to create a customized dialog:

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            new AppRater.DefaultBuilder(this, "com.kila.addnotification.lars")
                    .showDefault()
                    .daysToWait(4)
                    .timesToLaunch(3)
                    .title("Rate Notify me")
                    .notNowButton(null)
                    .rateButton("I want to rate")
                    .appLaunched();
            setContentView(R.layout.activity_main);
        }
    }
First all settings are set to default. The I overwrite so me of them. I want to wait 4 days instead of 3, the app should be launched only 3 times instead of 5. Furthermore I want a different title, I don't want the notNowButton (`Later`) to be displayed and I want to change the rateButton's text.
![CustomizedDialog](ttps://imgur.com/XibDGKL.png?2 "Customized dialog")

#### StarBuilder
With this Builder you can build the dialog with the stars. You can change the texts, you can choose which buttons should be displayed, you can choose when the dialog should be shown, you can choose the minimum number of stars which have to be selected to redirect to the PlayStore and you can choose if they can email you if the rating is not good enough. 

Use the `showDefault()` again to show the default dialog displayed in the first picture above. Next to the things you can customize at the DefaultBuilder you can customize following things:

+ change the minimum number of selected stars. If the user gives a rating matching your minimum number of stars he will be redirected to the PlayStore (default is 3)

      .minimumNumberOfStars(int minimumNumberOfStars)
        
+ set an email address. If the user does not select enough stars he will be asked to send an email with suggestions (second picture above) if you set an email address. Without email address the dialog just will be dismissed if the rating is not high enough
    
      .email(String email)
    
You only can use `.minimumNumberOfStars()` and `.email` after the constructor or `showDefault()`. It is not possible to set it after `.title()` or something. If you have a suggestion how to change this please tell me.
    
    
Here is an example of how to create a customized dialog:

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            new AppRater.StarBuilder(this, "com.kila.addnotification.lars")
                    .showDefault()
                    .minimumNumberOfStars(4)
                    .email("myemail@nodomain.abc")
                    .appLaunched();
            setContentView(R.layout.activity_main);
        }
    }
This time I am happy with the default settings. I just want the user to select a minimum of 4 stars before he gets redirected to the Play Store. Furthermore I set the email address so the dialog shown in the second picture above will be shown if the user only select 3 or less stars.

## Recommendation
Following things are highly recommendet to not annoy the user, whic might produce negative ratings:

+ don't show the dialog immediately after install (set daysToWait and timesToLaunch to a minimum of 1 or use showDefault())
+ don't hide the never button. If the user does not want to rate the app it is his decision, not yours. Don't force him ;)
+ set the title or message adapted to your app name. This will make it look and sound better
+ set an email address if you use the StarBuilder
+ don't set minimumNumberOfStars to 5

## Contribution
This is my first Open Source project. If you see anything you can improve please inform me or make a pull request. If you would like to add some features you can make a pull request too.
