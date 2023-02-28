package com.insane.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

public class Tools {

    public static int scrollY;
    private static int doubleClick = 0;

    public static class App extends AppCompatActivity {

        /**
         * Method to open a activity
         * @param parentActivity is the current context where the new activity will be called
         * @param activityClass is a class from the activity to be opened
         * @param finishParent if true will finish the parent activity
         */
        public static void openActivity(Activity parentActivity, Class activityClass, boolean finishParent) {
            Intent intent = new Intent(parentActivity, activityClass);
            parentActivity.startActivity(intent);
            if (finishParent) parentActivity.finish();
        }
    }

    public static class Web extends AppCompatActivity {

        /**
         * Method to load a WebSite or URL into a WebView object
         * @param webview the object to display the website
         * @param url the website address protocol http
         */
        public static void loadURL(WebView webview, String url) {
            try {
                webview.getSettings().setJavaScriptEnabled(true);
                webview.addJavascriptInterface(new Window.JsObject(), "injectedObject");
                // webview.loadData(url, "text/html", null);
                // webview.loadUrl("javascript:alert(injectedObject.toString())"); */
                webview.clearCache(true);
                webview.loadUrl(url);
            }catch (Exception e) {}
        }

        public static void loadIFrame(WebView webview, String src, int width, int height) {
            try {
                webview.getSettings().setJavaScriptEnabled(true);
                webview.addJavascriptInterface(new Window.JsObject(), "injectedObject");
                webview.loadData("<iframe src=\""+src+"\" width=\""+width+"\" height=\""+height+"\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\" referrerpolicy=\"no-referrer-when-downgrade\"></iframe>", "text/html", null);
                webview.loadUrl("javascript:alert(injectedObject.toString())");
            }catch (Exception e) {}
        }

        /**
         * Method to take user to a PlayStore page with the packageName
         * @param parentActivity
         * @param targetPackageName
         */
        public static void openPlayStore(Activity parentActivity, String targetPackageName) {

            try {
                parentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + targetPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                parentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + targetPackageName)));
            }
        }

        public static void openBrowser(Activity parentActivity, String URL) {
            if (URL != null & !URL.equals("")) {
                // CHECK THAT URL STARTS WITH PROTOCOL HTTP
                if (!URL.startsWith("http://") && !URL.startsWith("https://"))
                    URL = "http://" + URL;
                // OPEN BROWSER
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                parentActivity.startActivity(browserIntent);
            }
        }

        public static void composeEmail(Activity parentActivity, String[] addresses, String subject) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            try {
                parentActivity.startActivity(intent);
            } catch (Exception e) {
                Tools.Show.message(parentActivity, "Aplicativo de email não encontrado!");
            }
        }
    }

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

        public static String captilizeString(String name){
            String captilizedString="";
            if(!name.trim().equals("")){
                captilizedString = name.substring(0,1).toUpperCase() + name.substring(1);
            }
            return captilizedString;
        }

        public static String captilizeSentence(String name) {
            StringBuilder capitizedSentence= new StringBuilder();
            String[] words = name.split(" ");
            String avoid = "de,des,da,das,do,dos";
            for (String word : words) {
                if (!avoid.contains(word.toLowerCase())) {
                    capitizedSentence.append(captilizeString(word)).append(" ");
                } else {
                    capitizedSentence.append(word).append(" ");
                }
            }
            return capitizedSentence.toString();
        }

        public static boolean isEmail(String email) {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email).matches();
        }

        public static String getMonth(int month) {
            String months[];
            months = new String[ 13 ];
            months[ 0 ] = null;
            months[ 1 ] = "JAN";
            months[ 2 ] = "FEV";
            months[ 3 ] = "MAR";
            months[ 4 ] = "ABR";
            months[ 5 ] = "MAI";
            months[ 6 ] = "JUN";
            months[ 7 ] = "JUL";
            months[ 8 ] = "AGO";
            months[ 9 ] = "SET";
            months[ 10 ] = "OUT";
            months[ 11 ] = "NOV";
            months[ 12 ] = "DEZ";
            return months[month];
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
         * @param widthORheight parameter type String needs to be width or height
         * @return a Integer number
         */
        public static int size(Activity activity, String widthORheight) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int result = 0;
            if (widthORheight.equals("width"))          { result  = (dm.widthPixels); }
            else if (widthORheight.equals("height"))    { result  = (dm.heightPixels); }
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

        public static void checkViews(List list, View viewToCheck) {
            // TODO: We can do this one better
            RelativeLayout imgBefore = null;
            RelativeLayout imgAfter = null;
            boolean before;
            boolean after;

            try {
                for (int id = 0; id < list.size(); id++) {
                    try {
                        if (id > 0)              {before=true;  imgBefore  = viewToCheck.findViewWithTag(id-1); } else {before=false;} // Pega o layout anterior
                        if (list.size() <= id+1) {after=true;   imgAfter   = viewToCheck.findViewWithTag(id+1); } else {after=false;} // Pega o layout posterior

                        RelativeLayout imgContainer = viewToCheck.findViewWithTag(id);
                        RelativeLayout contentImg = viewToCheck.findViewById(id);

                        if ((Tools.Window.isView(contentImg,viewToCheck))) {
                            imgContainer.setVisibility(View.VISIBLE);
                            if (before) { if ((!Tools.Window.isView(imgBefore, viewToCheck)) && (imgBefore != null))  {imgBefore.setVisibility(View.VISIBLE);}}
                            if (after)  { if (!Tools.Window.isView(imgAfter, viewToCheck) && (imgAfter != null))   {imgAfter.setVisibility(View.VISIBLE);}}
                        }

                        if ((!Tools.Window.isView(contentImg, viewToCheck))) {
                            imgContainer.setVisibility(View.GONE);
                            if (before) { if (Tools.Window.isView(imgBefore, viewToCheck))  {imgContainer.setVisibility(View.VISIBLE);}}
                            if (after)  { if (Tools.Window.isView(imgAfter, viewToCheck))   {imgContainer.setVisibility(View.VISIBLE);}}
                        }

                    } catch (Exception e) {e.printStackTrace();}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public static boolean isView (View thisView, View isVisibleIn) {
            if (thisView == null) {
                return false;
            }
            if (!thisView.isShown()) {
                return false;
            }
            Rect scrollBounds = new Rect();
            isVisibleIn.getHitRect(scrollBounds);
            if (thisView.getLocalVisibleRect(scrollBounds)) {
                // Any portion of the imageView, even a single pixel, is within the visible window
                return true;
            } else {
                // NONE of the imageView is within the visible window
                return false;
            }
        }

        // Enable javascript for WebView \\
        public static class JsObject {
            @NonNull
            @JavascriptInterface
            public String toString() { return "injectedObject"; }
        }
    }

    public static class Device {

        public static void vibrate(Context context) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(50);
            }
        }

        public static void ring (Context context) {
            try {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(context, notification);
                r.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

        public static String getDeviceName() {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                return Tools.Text.captilizeString(model);
            } else {
                return Tools.Text.captilizeString(manufacturer) + " " + model;
            }
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

    public static class Redirect {

        public static void toPlayStore(Activity parentActivity, String targetPackageName) {
            try {
                parentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + targetPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                parentActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + targetPackageName)));
            }
        }

    }

    public static class DeviceService {

        public static void callPhone(Context context, String numero) {
            numero = numero.replaceAll("[^\\d]", "");
            if (numero.length() > 0) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // USED ONLY ON STATIC
                callIntent.setData(Uri.parse("tel:" + numero));
                context.startActivity(callIntent);
            } else {
                Toast.makeText(context, "Número de telefone inválido", Toast.LENGTH_SHORT).show();
            }
        }

        public static void openWhatsApp(Context context, String numero, String mensagem) {
            numero = numero.replaceAll("[^\\d]", "");
            if (numero.length() > 0) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + numero + "&text=" + mensagem);
                Intent whatsapp = new Intent(Intent.ACTION_VIEW, uri);
                whatsapp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // USED ONLY ON STATIC
                whatsapp.setPackage("com.whatsapp");
                try {
                    context.startActivity(whatsapp);
                } catch (Exception e) {
                    //Toast.makeText(context, "O aplicativo Whatsapp não foi encontrado", Toast.LENGTH_SHORT).show();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=" + numero + "&text=" + mensagem));
                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // USED ONLY ON STATIC
                    context.startActivity(browserIntent);
                }
            } else {
                Toast.makeText(context, "Número de telefone inválido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static class Media {

        public static void playSFX(Context context, int idSong)  {
            // TODO: Would better to check first if the media is playing before create a new of it
            MediaPlayer mediaPlayer = MediaPlayer.create(context, idSong);
            mediaPlayer.start();

            System.out.println("CLICKED SOUND2!");
            mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.release());
        }

    }
}