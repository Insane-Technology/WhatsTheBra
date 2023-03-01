package com.insane.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class Tools {

    private static int doubleClick = 0;

    public static class Connection {
        public static boolean internetStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // WIFI ENABLE \\
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // MOBILE DATA ENABLE \\
                    return true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                    // ETHERNET DATA ENABLE \\
                    return true;
                }
                // TODO: Verify if there is any other type of Connectivity Service other than WIFI, MOBILE and ETHERNET
            }
            // NO INTERNET \\
            return false;
        }
    }

    public static class Text {

        public static String removeAccents(String string) {
            string = Normalizer.normalize(string, Normalizer.Form.NFD);
            string = string.replaceAll("[^\\p{ASCII}]", "");
            return Normalizer.normalize(string, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }

        public static boolean isEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }

    }

    /**
     * Methods to deal with Json objects
     */
    public static class Json {

        /**
         * Json objects in type String
         */
        public static class string {

            public static JsonObject toJson(String strJson) {
                JsonObject jsonObject = new JsonObject();
                if (strJson != null) {
                    try {
                        if (!strJson.equals("null")) {
                            jsonObject = new JsonParser().parse(strJson).getAsJsonObject();
                        } else {
                            jsonObject = null;
                        }
                    } catch (JsonParseException e) {
                        e.printStackTrace();
                    }
                }
                return jsonObject;
            }

        }

    }

    public static class Encryption {
        public static String sha256(String base) {
            try{
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) {
                    String hex = Integer.toHexString(0xff & b);
                    if (hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }
                return hexString.toString();
            } catch(Exception ex){
                throw new RuntimeException(ex);
            }
        }
    }

    public static class Encoding {
        public static String fromBase64(String message) {
            byte[] data = Base64.decode(message, Base64.DEFAULT);
            try {
                return new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static String toBase64(String message) {
            byte[] data;
            try {
                data = message.getBytes("UTF-8");
                String base64Sms = Base64.encodeToString(data, Base64.DEFAULT);
                return base64Sms;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static class Window {

        /**
         * Gets the window size either width or height
         * @param activity context
         * @param widthOrHeight parameter type String needs to be width or height
         * @return a Integer number
         */
        public static int size(Activity activity, String widthOrHeight) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int result = 0;
            if (widthOrHeight.equals("width"))          { result  = (dm.widthPixels); }
            else if (widthOrHeight.equals("height"))    { result  = (dm.heightPixels); }
            return result;
        }

        public static int dpToPx(int dp) {
            return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
        }

        public static int pxToDp(int px) {
            return (int) (px / Resources.getSystem().getDisplayMetrics().density);
        }

        public static int convertDpToPixel(float dp) {
            DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
            float px = dp * (metrics.densityDpi / 160f);
            return Math.round(px);
        }
    }

    public static class Device {

        public static void vibrate(Context context) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        }

        public static String currentVersion(){
            double release=Double.parseDouble(Build.VERSION.RELEASE.replaceAll("(\\d+[.]\\d+)(.*)","$1"));
            String codeName="Unsupported";//below Jelly bean OR above Oreo
            if(release>=4.1 && release<4.4)codeName="Jelly Bean";
            else if(release<5)codeName="Kit Kat";
            else if(release<6)codeName="Lollipop";
            else if(release<7)codeName="Marshmallow";
            else if(release<8)codeName="Nougat";
            else if(release<9)codeName="Oreo";
            else if(release<10)codeName="Android Q";
            else if(release>=10)codeName="Android 10";
            return codeName+" v"+release+", API Level: "+Build.VERSION.SDK_INT;
        }

        public static boolean isDoubleClick() {
            doubleClick++;
            new Handler().postDelayed(new Runnable() {@Override public void run() {doubleClick=0;}}, 500);
            if (doubleClick >= 2) {doubleClick = 0; return true;} else {return false;}
        }
    }

    public static class Show {
        public static void noConnection(Context context) {
            Toast.makeText(context, "Sem conexão com a Internet", Toast.LENGTH_SHORT).show();
        }
        public static void message(Context context, String message) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

}