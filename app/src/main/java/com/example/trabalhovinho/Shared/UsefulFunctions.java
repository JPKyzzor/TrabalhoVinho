package com.example.trabalhovinho.Shared;

import android.app.Activity;
import android.content.Intent;

public class UsefulFunctions {

    public static void finalizaIntent(Activity activity) {
        Intent returnIntent = new Intent();
        activity.setResult(Activity.RESULT_OK, returnIntent);
        activity.finish();
    }
}
