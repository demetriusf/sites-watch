package com.asccode.siteswatch.telas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.asccode.siteswatch.dao.LoginDao;
import com.asccode.siteswatch.models.User;
import com.asccode.siteswatch.task.SiteAddTask;
import com.asccode.siteswatch.task.SiteUpdateTask;

/**
 * Created with IntelliJ IDEA.
 * User: Trabalho
 * Date: 08/04/13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class Site extends Activity {

    private EditText editTextNomeSite;
    private EditText editTextUrl;
    private CheckBox checkBoxReceiveNotification;
    private CheckBox checkBoxOptPing;
    private Button button;
    private com.asccode.siteswatch.models.Site site;
    private boolean opAdd = true;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.site);

        this.editTextNomeSite = (EditText) findViewById(R.id.editTextNomeSite);
        this.editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        this.checkBoxReceiveNotification = (CheckBox) findViewById(R.id.checkBoxReceiveNotification);
        this.checkBoxOptPing = (CheckBox) findViewById(R.id.checkBoxOptPing);
        this.button = (Button) findViewById(R.id.button);

        this.site = (com.asccode.siteswatch.models.Site) getIntent().getSerializableExtra("site");

        if(this.site != null){

            this.editTextNomeSite.setText(this.site.getName());
            this.editTextUrl.setText(this.site.getUrl());
            this.checkBoxReceiveNotification.setChecked(this.site.getReceiveNotification());
            this.checkBoxOptPing.setChecked(this.site.getOptPing());
            this.button.setText(getString(R.string.btnSiteUpdate));
            this.opAdd = false;

        }else{

            this.site = new com.asccode.siteswatch.models.Site();

        }

        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Teste
                User user = new LoginDao(Site.this).getLogged();
                site.setUser(user);

                //-------------
                site.setName(editTextNomeSite.getEditableText().toString());
                site.setUrl(editTextUrl.getEditableText().toString());
                site.setReceiveNotification(checkBoxReceiveNotification.isChecked());
                site.setOptPing(checkBoxOptPing.isChecked());

                if( !opAdd ){ // Update

                    new SiteUpdateTask(site, Site.this).execute();

                }else{ //Save

                    new SiteAddTask(site, Site.this).execute();

                }

            }
        });

    }

}