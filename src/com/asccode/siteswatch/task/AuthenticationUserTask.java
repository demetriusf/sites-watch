package com.asccode.siteswatch.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import com.asccode.siteswatch.dao.LoginDao;
import com.asccode.siteswatch.models.User;
import com.asccode.siteswatch.support.Login;
import com.asccode.siteswatch.support.WebServiceOperations;
import com.asccode.siteswatch.telas.R;

/**
 * Created with IntelliJ IDEA.
 * User: Trabalho
 * Date: 25/03/13
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationUserTask extends AsyncTask<Object, Object, Boolean> {

    private User user;
    private Context context;
    private ProgressDialog progressDialog;

    public AuthenticationUserTask(User user, Context context){

        this.user = user;
        this.context = context;

    }

    @Override
    protected void onPreExecute(){

        this.progressDialog = ProgressDialog.show(this.context, this.context.getString(R.string.dialogTitleAuthUser), this.context.getString(R.string.dialogBodyAuthUser), true, true);

    }

    @Override
    protected Boolean doInBackground(Object... objects) {

        return new WebServiceOperations().userAuthentication(this.user);

    }

    @Override
    protected void onPostExecute( Boolean result ){

        this.progressDialog.dismiss();

        if(result){

            LoginDao loginDao = new LoginDao(this.context);

            if(loginDao.login(this.user)){

               new Login(this.context).redirectLoggedUser();

            }else{

                Toast.makeText(this.context, this.context.getString(R.string.fbAlertFailLogin), Toast.LENGTH_LONG).show();

            }

        }else{

            ProgressDialog.Builder dialog = new ProgressDialog.Builder(this.context);
            dialog.setCancelable(false);
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.setTitle(this.context.getString(R.string.fbDialogErrorTitleUserAuthentication));
            dialog.setMessage(this.context.getString(R.string.fbDialogErrorBodyUserAuthentication));
            dialog.setPositiveButton(this.context.getString(R.string.fbDialogErrorPositiveButtonUserAuthentication), null);
            dialog.show();

        }

    }

}
