package com.qnecesitas.elreteninventario;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;
import com.qnecesitas.elreteninventario.auxiliary.Constants;
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools;
import com.qnecesitas.elreteninventario.data.ModelEditProduct;
import com.qnecesitas.elreteninventario.data.ModelPassword;
import com.qnecesitas.elreteninventario.databinding.ActivityLoginBinding;
import com.qnecesitas.elreteninventario.network.RetrofitPasswords;
import com.qnecesitas.elreteninventario.network.RetrofitProductsImplS;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private RetrofitPasswords retrofitPasswords;
    private ArrayList<ModelPassword> al_password;
    private int countBadPassword = 0;

    //Deficit
    private RetrofitProductsImplS retrofitProductsImplS;
    private ArrayList<ModelEditProduct> alProductsDeficitS;
    private ArrayList<ModelEditProduct> alProductsDeficitLS;

    //Notification
    private final String CHANNEL_ID = "ELReten";
    private final String CHANNEL_NAME = "EL Retén";
    private final int NOTIFICATION_ID1 = 123;
    private final int NOTIFICATION_ID2 = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        retrofitPasswords = new RetrofitPasswords();
        al_password = new ArrayList<>();

        binding.ALTIETPassword.setOnKeyListener((view, i, keyEvent)
                -> eventKeyboard(view, keyEvent));

        binding.ALBTNStartSession.setOnClickListener(view
                -> click_login());

        retrofitProductsImplS = new RetrofitProductsImplS();
        alProductsDeficitS = new ArrayList<>();
        alProductsDeficitLS = new ArrayList<>();

    }

    private boolean eventKeyboard(View view, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    private void click_login() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binding.ALTIETPassword.getWindowToken(), 0);
        if (al_password.isEmpty()) {
            loadPasswordInternet();
        } else {
            checkPassword();
        }
    }


    private void loadPasswordInternet() {
        if (NetworkTools.isOnline(Activity_Login.this, false)) {
            binding.ALPBCargando.setVisibility(View.VISIBLE);

            Call<ArrayList<ModelPassword>> call = retrofitPasswords.fetchAccounts(Constants.PHP_TOKEN);
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ArrayList<ModelPassword>> call, @NonNull Response<ArrayList<ModelPassword>> response) {
                    if (response.isSuccessful()) {
                        al_password = response.body();
                        if (al_password != null) {
                            checkPassword();
                        } else {
                            Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                            binding.ALPBCargando.setVisibility(View.GONE);
                        }
                    } else {
                        Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                        binding.ALPBCargando.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ArrayList<ModelPassword>> call, @NonNull Throwable t) {
                    binding.ALPBCargando.setVisibility(View.GONE);
                    Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                }
            });
        } else {
            Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
        }
    }

    private void checkPassword() {
        if (!binding.ALTIETPassword.getText().toString().isEmpty()) {

            String user = binding.ALRBAdministrator.isChecked() ? "Administrador" : "Dependiente";
            String bdPassword;

            if (al_password.get(0).getUser().equals(user)) {
                bdPassword = al_password.get(0).getPassword();
            } else {
                bdPassword = al_password.get(1).getPassword();
            }

            String inputPassword = binding.ALTIETPassword.getText().toString();

            if (bdPassword.equals(inputPassword)) {
                if(user.equals("Administrador")) {
                    loadDeficitInternetS();
                }else{
                    binding.ALPBCargando.setVisibility(View.GONE);
                    Intent intent = new Intent(Activity_Login.this, Activity_MenuSelesperson.class);
                    startActivity(intent);
                }
            } else {
                countBadPassword++;
                binding.ALTILPassword.setError(getString(R.string.Contrasena_incorrecta));
                if(countBadPassword == 5)showAlertDialogClosePassword();
            }


        } else {
            binding.ALTILPassword.setError(getString(R.string.este_campo_no_debe_vacio));
        }
    }

    private void loadDeficitInternetS(){
        if (NetworkTools.isOnline(Activity_Login.this, false)) {

            Call<ArrayList<ModelEditProduct>> call = retrofitProductsImplS.fetchProductsDeficit(Constants.PHP_TOKEN, "Almacén");
            call.enqueue(new Callback<ArrayList<ModelEditProduct>>() {
                @Override
                public void onResponse(Call<ArrayList<ModelEditProduct>> call, Response<ArrayList<ModelEditProduct>> response) {
                    if(response.isSuccessful()){
                        alProductsDeficitS = response.body();
                        loadDeficitInternetLS();
                    }else{
                        Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                        binding.ALPBCargando.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ModelEditProduct>> call, Throwable t) {
                    Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                    binding.ALPBCargando.setVisibility(View.GONE);
                }
            });
        }else{
            Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
            binding.ALPBCargando.setVisibility(View.GONE);
        }
    }

    private void loadDeficitInternetLS(){
        if (NetworkTools.isOnline(Activity_Login.this, false)) {

            Call<ArrayList<ModelEditProduct>> call = retrofitProductsImplS.fetchProductsDeficit(Constants.PHP_TOKEN, "Mostrador");
            call.enqueue(new Callback<ArrayList<ModelEditProduct>>() {
                @Override
                public void onResponse(Call<ArrayList<ModelEditProduct>> call, Response<ArrayList<ModelEditProduct>> response) {
                    binding.ALPBCargando.setVisibility(View.GONE);
                    if(response.isSuccessful()){
                        alProductsDeficitLS = response.body();
                        notifyNews();
                    }else{
                        Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ModelEditProduct>> call, Throwable t) {
                    Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                    binding.ALPBCargando.setVisibility(View.GONE);
                }
            });
        }else{
            Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
            binding.ALPBCargando.setVisibility(View.GONE);
        }
    }

    private void notifyNews(){
        int amountS = 0;
        int amountLS = 0;

        if(!alProductsDeficitS.isEmpty()){
            displayNotificationS(alProductsDeficitS.size());
        }

        if(!alProductsDeficitLS.isEmpty()){
            displayNotificationLS(alProductsDeficitLS.size());
        }

        //Finish activity
        Intent intent = new Intent(Activity_Login.this, Activity_MenuAdmin.class);
        startActivity(intent);
    }

    private void displayNotificationS(int amount){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.channel_decr));
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), Activity_Deficit.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getString(R.string.Productos_en_deficit))
                .setContentText(getString(R.string.deficit_almacen_admin,amount))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_view,getString(R.string.ver_deficit), pendingIntent);

        notificationManager.notify(NOTIFICATION_ID1, builder.build());
    }

    private void displayNotificationLS(int amount){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(getString(R.string.channel_decr));
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), Activity_Deficit.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(getString(R.string.Productos_en_deficit))
                .setContentText(getString(R.string.deficit_almacen_salesperson,amount))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_view,getString(R.string.ver_deficit), pendingIntent);

        notificationManager.notify(NOTIFICATION_ID2, builder.build());
    }

    @Override
    public void onBackPressed() {
        showAlertDialogExit();
    }

    private void showAlertDialogExit() {
        //init alert dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.salir);
        builder.setMessage(R.string.seguro_desea_salir);
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Si, (dialog, which) -> {
            //finish the activity
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            //dialog gone
            dialog.dismiss();
        });
        //create the alert dialog and show it
        builder.create().show();
    }
    private void showAlertDialogClosePassword() {
        //init alert dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle(R.string.intentos_fallidos);
        builder.setMessage(R.string.cerrar_password);
        //set listeners for dialog buttons
        builder.setPositiveButton(R.string.Cerrar, (dialog, which) -> {
            //finish the activity
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        //create the alert dialog and show it
        builder.create().show();
    }


}