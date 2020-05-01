package com.vicenteaguilera.ipmascara;

import java.util.List;

public class RedModel
{
    private String Red;
    private String mascara;
    private List<String> redes;

    public RedModel(String red, String mascara, List<String> redes) {
        Red = red;
        this.mascara = mascara;
        this.redes = redes;
    }

    public String getRed() {
        return Red;
    }

    public void setRed(String red) {
        Red = red;
    }

    public String getMascara() {
        return mascara;
    }

    public void setMascara(String mascara) {
        this.mascara = mascara;
    }

    public List<String> getRedes() {
        return redes;
    }

    public void setRedes(List<String> redes) {
        this.redes = redes;
    }
    public  void addIP(String ip)
    {
        this.redes.add(ip);
    }
}
