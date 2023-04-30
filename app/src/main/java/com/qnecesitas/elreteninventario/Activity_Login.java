package com.qnecesitas.elreteninventario;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.qnecesitas.elreteninventario.auxiliary.Constants;
import com.qnecesitas.elreteninventario.auxiliary.NetworkTools;
import com.qnecesitas.elreteninventario.data.ModelPassword;
import com.qnecesitas.elreteninventario.databinding.ActivityLoginBinding;
import com.qnecesitas.elreteninventario.network.RetrofitPasswords;

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

                    binding.ALPBCargando.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        al_password = response.body();
                        if (al_password != null) {
                            checkPassword();
                        } else {
                            Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(binding.ALCLContainerAll, getString(R.string.Revise_su_conexion), Snackbar.LENGTH_LONG).show();
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
        if (!Objects.requireNonNull(binding.ALTIETPassword.getText()).toString().isEmpty()) {

            String user = binding.ALRBAdministrator.isChecked() ? "Administrador" : "Dependiente";
            String bdPassword;

            if (al_password.get(0).getUser().equals(user)) {
                bdPassword = al_password.get(0).getPassword();
            } else {
                bdPassword = al_password.get(1).getPassword();
            }

            String inputPassword = binding.ALTIETPassword.getText().toString();

            if (bdPassword.equals(inputPassword)) {
                Intent intent = new Intent(Activity_Login.this, Activity_MenuAdmin.class);
                startActivity(intent);
            } else {
                countBadPassword++;
                binding.ALTILPassword.setError(getString(R.string.Contrasena_incorrecta));
                if(countBadPassword == 5)showAlertDialogClosePassword();
            }


        } else {
            binding.ALTILPassword.setError(getString(R.string.este_campo_no_debe_vacio));
        }
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