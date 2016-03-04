package com.zpj.app_0001_ssd;

import android.app.Service;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
//import com.zpj.hardlibrary.*;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import android.os.ILedService;
//import android.os.ServiceManager;
import android.os.IBinder;


public class MainActivity extends ActionBarActivity {

    private boolean ledon = false;
    private Button button = null;
    private CheckBox checkBoxLed1 = null;
    private CheckBox checkBoxLed2 = null;
    private CheckBox checkBoxLed3 = null;
    private CheckBox checkBoxLed4 = null;
    //private ILedService iLedService = null;
    Object Proxy = null;
    Method ledCtrl =  null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.BUTTON);

        try {
            //iLedService = ILedService.Stub.asInterface(ServiceManager.getService("led"));
            Method getService = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder ledService = (IBinder)getService.invoke(null, "led");
            Method asInterface = Class.forName("android.os.ILedService$Stub").getMethod("asInterface", IBinder.class);
            Proxy = asInterface.invoke(null, ledService);
            ledCtrl = Class.forName("android.os.ILedService$Stub$Proxy").getMethod("ledCtrl", int.class, int.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //ledCtrl.invoke(Proxy, 0, 1);
                //HardControl.ledOpen();

        checkBoxLed1 = (CheckBox) findViewById(R.id.LED1);
        checkBoxLed2 = (CheckBox) findViewById(R.id.LED2);
        checkBoxLed3 = (CheckBox) findViewById(R.id.LED3);
        checkBoxLed4 = (CheckBox) findViewById(R.id.LED4);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //HardControl hardControl = new HardControl();
                // Perform action on click
                ledon = !ledon;
                if (ledon) {
                    button.setText("ALL OFF");
                    checkBoxLed1.setChecked(true);
                    checkBoxLed2.setChecked(true);
                    checkBoxLed3.setChecked(true);
                    checkBoxLed4.setChecked(true);


                    try {
                        for (int i = 0; i < 4; i++) {
                            //iLedService.ledCtrl(i, 1);
                            ledCtrl.invoke(Proxy, i, 1);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    button.setText("ALL ON");
                    checkBoxLed1.setChecked(false);
                    checkBoxLed2.setChecked(false);
                    checkBoxLed3.setChecked(false);
                    checkBoxLed4.setChecked(false);


                    try {
                        for (int i = 0; i < 4; i++) {
                            //iLedService.ledCtrl(i, 0);
                            ledCtrl.invoke(Proxy, i, 0);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


        try {
            // Check which checkbox was clicked
            switch(view.getId()) {
                case R.id.LED1:
                    if (checked) {
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED1 on", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(0, 1);
                        ledCtrl.invoke(Proxy, 0, 1);
                    }
                    else {
                        // Remove the meat
                        Toast.makeText(getApplicationContext(), "LED1 off", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(0, 0);
                        ledCtrl.invoke(Proxy, 0, 0);
                    }
                    break;
                case R.id.LED2:
                    if (checked) {
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED2 on", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(1, 1);
                        ledCtrl.invoke(Proxy, 1, 1);
                    }
                    else {
                        // Remove the meat
                        Toast.makeText(getApplicationContext(), "LED2 off", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(1, 0);
                        ledCtrl.invoke(Proxy, 1, 0);
                    }
                    break;
                case R.id.LED3:
                    if (checked) {
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED3 on", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(2, 1);
                        ledCtrl.invoke(Proxy, 2, 1);
                    }
                    else {
                        // Remove the meat
                        Toast.makeText(getApplicationContext(), "LED3 off", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(2, 0);
                        ledCtrl.invoke(Proxy, 2, 0);
                    }
                    break;
                case R.id.LED4:
                    if (checked) {
                        // Put some meat on the sandwich
                        Toast.makeText(getApplicationContext(), "LED4 on", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(3, 1);
                        ledCtrl.invoke(Proxy, 3, 1);
                    }
                    else {
                        // Remove the meat
                        Toast.makeText(getApplicationContext(), "LED4 off", Toast.LENGTH_SHORT).show();
                        //iLedService.ledCtrl(3, 0);
                        ledCtrl.invoke(Proxy, 3, 0);
                    }
                    break;

                // TODO: Veggie sandwich
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
