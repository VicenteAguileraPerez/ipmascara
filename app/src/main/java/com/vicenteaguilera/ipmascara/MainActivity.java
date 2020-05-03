package com.vicenteaguilera.ipmascara;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    Spinner spinner_mascaras;
    EditText editText_ip;
    ListView listView_ips;
    Button button_add, button_clear;
    List<RedModel> redes = new ArrayList<>();
    String ip, ipBinaria, mascaraString, direccionRed;
    int mascara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner_mascaras = findViewById(R.id.spinner_mascaras);
        editText_ip = findViewById(R.id.editText_ip);
        listView_ips = findViewById(R.id.listView_ips);
        button_add = findViewById(R.id.button_add);
        button_clear = findViewById(R.id.button_clean);
        spinner_mascaras.setPrompt("Seleccione una máscara");
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, MascarasRed.mascarasDecimal);
        spinner_mascaras.setAdapter(adapter);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = editText_ip.getText().toString();
                mascara = spinner_mascaras.getSelectedItemPosition();
                ipBinaria = isValidate(ip);
                if (!ipBinaria.equals("")) {
                    if (mascara > 0) {

                        mascaraString = MascarasRed.mascarasBinaria.get(mascara - 1);
                        Toast.makeText(getApplicationContext(), mascaraString + " " + ipBinaria, Toast.LENGTH_SHORT).show();
                        direccionRed = direccionRed(ipBinaria, mascaraString);
                        String ipDecimal[]=octetos(direccionRed);
                        String DirecciondeRed=ipDecimal[0]+"."+ipDecimal[1]+"."+ipDecimal[2]+"."+ipDecimal[3];
                        Log.e("P",redes+"");
                        if(redes.size()==0)
                        {


                           List<String> ips = new ArrayList<>();
                           ips.add(ip);

                           RedModel redModel = new RedModel(DirecciondeRed,spinner_mascaras.getSelectedItem().toString(),ips);
                           redes.add(redModel);

                        }
                        else
                        {
                            boolean existe=false;
                                for(int i=0;i<redes.size();i++)
                                {
                                    if(DirecciondeRed.equals(redes.get(i).getRed()) && spinner_mascaras.getSelectedItem().toString().equals(redes.get(i).getMascara()))
                                    {
                                        RedModel redModel = redes.get(i);
                                        redModel.addIP(ip);
                                        existe=true;
                                    }

                                }
                                if(!existe)
                                {
                                    List<String> ips = new ArrayList<>();
                                    ips.add(ip);
                                    RedModel redModel = new RedModel(DirecciondeRed, spinner_mascaras.getSelectedItem().toString(), ips);
                                    redes.add(redModel);
                                }

                        }
                        listView_ips.setAdapter(new Adapter(redes,MainActivity.this));



                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Seleccione una mascara de red", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Ip no válida. ejemplo 192.168.0.1", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText_ip.setText("");
                spinner_mascaras.setSelection(0);
                listView_ips.setAdapter(null);
                redes= new ArrayList<>();
            }
        });

    }

    private String isValidate(String ip) {
        String bits32 = "";
        String vec[] = ip.trim().split("\\.");

        if (vec.length == 4) {
            for (int i = 0; i < vec.length; i++) {
                if (isNumeric(vec[i])) {
                    bits32 += convertToBinario(Integer.parseInt(vec[i]));
                } else {
                    bits32 = "";
                    break;
                }
            }
        } else {
            bits32 = "";
            Log.e("p", ip + "else principal" + vec.length);
        }
        return bits32;
    }

    private boolean isNumeric(String octeto) {
        try {
            if (Integer.parseInt(octeto) <= 223) {
                return true;
            }
            return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private String direccionRed(String ip, String mask) {
        String RedAddress = "";
        for (int i = 0; i < 32; i++) {
            if (ip.charAt(i) == '1' && mask.charAt(i) == '1') {
                RedAddress += "1";
            } else {
                RedAddress += "0";
            }
        }
        return RedAddress;
    }

    private String convertToBinario(int dec) {
        String dato = "";
        if (dec >= 128) {
            dato += "1";
            dec -= 128;
        } else {
            dato += "0";
        }
        if (dec >= 64) {
            dato += "1";
            dec -= 64;
        } else {
            dato += "0";
        }
        if (dec >= 32) {
            dato += "1";
            dec -= 32;
        } else {
            dato += "0";
        }
        if (dec >= 16) {
            dato += "1";
            dec -= 16;
        } else {
            dato += "0";
        }
        if (dec >= 8) {
            dato += "1";
            dec -= 8;
        } else {
            dato += "0";
        }
        if (dec >= 4) {
            dato += "1";
            dec -= 4;
        } else {
            dato += "0";
        }
        if (dec >= 2) {
            dato += "1";
            dec -= 2;
        } else {
            dato += "0";
        }
        if (dec >= 1) {

            dato += "1";

        } else {
            dato += "0";
        }
        return dato;
    }

    private int convertToDecimal(String octeto) {
        int dato = 0;
        for (int i = 0; i < octeto.length(); i++) {
            if (octeto.charAt(i) == '1') {
                dato += Math.pow(2, octeto.length() - i - 1);
            } else {
                dato += 0;
            }
        }
        return dato;

    }

    private String[] octetos(String direccionRed)
    {
        String octetos[] = new String[4];
        String bytesS = "";
        for (int i = 0; i < direccionRed.length(); i++)
        {
            if (i == 8) {

                octetos[0] = String.valueOf(convertToDecimal(bytesS));
                Log.e("p=",octetos[0]);
                bytesS = "";
            } else if (i == 16) {

                octetos[1] = String.valueOf(convertToDecimal(bytesS));
                Log.e("p*",octetos[1]);
                bytesS = "";
            } else if (i == 24) {

                octetos[2] = String.valueOf(convertToDecimal(bytesS));
                Log.e("p-",octetos[2]);
                bytesS = "";
            }
            else if (i == 31){

                octetos[3] = String.valueOf(convertToDecimal(bytesS));
                Log.e("p/",octetos[3]);
                bytesS = "";
            }
            bytesS = bytesS+direccionRed.charAt(i);


        }

        return octetos;
    }

}
