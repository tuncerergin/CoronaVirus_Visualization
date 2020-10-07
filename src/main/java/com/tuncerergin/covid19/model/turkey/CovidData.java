
package com.tuncerergin.covid19.model.turkey;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Setter;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CovidData implements Serializable {

    private final static long serialVersionUID = 2371853632427410066L;

    public String tarih;

    public String gunlukTest;

    public String gunlukVaka;

    public String gunlukVefat;

    public String gunlukIyilesen;

    public String toplamTest;

    public String toplamVaka;

    public String toplamVefat;

    public String toplamIyilesen;

    public String toplamYogunBakim;

    public String toplamEntube;

    public String hastalardaZaturreOran;

    public String agirHastaSayisi;

    public String yatakDolulukOrani;


    public String eriskinYogunBakimDolulukOrani;
    public String ventilatorDolulukOrani;
    public String ortalamaFilyasyonSuresi;
    public String ortalamaTemasliTespitSuresi;
    public String filyasyonOrani;


    public Date getTarih() throws ParseException {
        return new SimpleDateFormat("dd.MM.yyyy").parse(tarih);
    }

    public String getGunlukTest() {
        gunlukTest = gunlukTest.replace(".", "");
        gunlukTest = gunlukTest.equals("") ? "0" : gunlukTest;
        return gunlukTest;
    }

    public String getGunlukVaka() {
        gunlukVaka = gunlukVaka.replace(".", "");
        gunlukVaka = gunlukVaka.equals("") ? "0" : gunlukVaka;
        return gunlukVaka;
    }

    public String getGunlukVefat() {
        gunlukVefat = gunlukVefat.replace(".", "");
        gunlukVefat = gunlukVefat.equals("") ? "0" : gunlukVefat;
        return gunlukVefat;
    }

    public String getGunlukIyilesen() {
        gunlukIyilesen = gunlukIyilesen.replace(".", "");
        gunlukIyilesen = gunlukIyilesen.equals("") ? "0" : gunlukIyilesen;
        return gunlukIyilesen;
    }

    public String getToplamTest() {
        toplamTest = toplamTest.replace(".", "");
        toplamTest = toplamTest.equals("") ? "0" : toplamTest;
        return toplamTest;
    }

    public String getToplamVaka() {
        toplamVaka = toplamVaka.replace(".", "");
        toplamVaka = toplamVaka.equals("") ? "0" : toplamVaka;
        return toplamVaka;
    }

    public String getToplamVefat() {
        toplamVefat = toplamVefat.replace(".", "");
        toplamVefat = toplamVefat.equals("") ? "0" : toplamVefat;
        return toplamVefat;
    }

    public String getToplamIyilesen() {
        toplamIyilesen = toplamIyilesen.replace(".", "");
        toplamIyilesen = toplamIyilesen.equals("") ? "0" : toplamIyilesen;
        return toplamIyilesen;
    }

    public String getToplamYogunBakim() {
        toplamYogunBakim = toplamYogunBakim.replace(".", "");
        toplamYogunBakim = toplamYogunBakim.equals("") ? "0" : toplamYogunBakim;
        return toplamYogunBakim;
    }

    public String getToplamEntube() {
        toplamEntube = toplamEntube.replace(".", "");
        toplamEntube = toplamEntube.equals("") ? "0" : toplamEntube;
        return toplamEntube;
    }

    public String getHastalardaZaturreOran() {
        hastalardaZaturreOran = hastalardaZaturreOran.equals("") ? "0.0" : hastalardaZaturreOran;
        return hastalardaZaturreOran;
    }

    public String getAgirHastaSayisi() {
        agirHastaSayisi = agirHastaSayisi.replace(".", "");
        agirHastaSayisi = agirHastaSayisi.equals("") ? "0" : agirHastaSayisi;
        return agirHastaSayisi;
    }

    public String getYatakDolulukOrani() {
        //   yatakDolulukOrani = yatakDolulukOrani.replace(".", "");
        yatakDolulukOrani = yatakDolulukOrani.equals("") ? "0" : yatakDolulukOrani;
        return yatakDolulukOrani;
    }

    public String getEriskinYogunBakimDolulukOrani() {
        // eriskinYogunBakimDolulukOrani = eriskinYogunBakimDolulukOrani.replace(".", "");
        eriskinYogunBakimDolulukOrani = eriskinYogunBakimDolulukOrani.equals("") ? "0" : eriskinYogunBakimDolulukOrani;
        return eriskinYogunBakimDolulukOrani;
    }

    public String getVentilatorDolulukOrani() {
        // ventilatorDolulukOrani = ventilatorDolulukOrani.replace(".", "");
        ventilatorDolulukOrani = ventilatorDolulukOrani.equals("") ? "0" : ventilatorDolulukOrani;
        return ventilatorDolulukOrani;
    }

    public String getOrtalamaFilyasyonSuresi() {
        //ortalamaFilyasyonSuresi = ortalamaFilyasyonSuresi.replace(".", "");
        ortalamaFilyasyonSuresi = ortalamaFilyasyonSuresi.equals("") ? "0" : ortalamaFilyasyonSuresi;
        return ortalamaFilyasyonSuresi;
    }

    public String getOrtalamaTemasliTespitSuresi() {
        //  ortalamaTemasliTespitSuresi = ortalamaTemasliTespitSuresi.replace(".", "");
        ortalamaTemasliTespitSuresi = ortalamaTemasliTespitSuresi.equals("") ? "0" : ortalamaTemasliTespitSuresi;
        return ortalamaTemasliTespitSuresi;
    }

    public String getFilyasyonOrani() {
        //filyasyonOrani = filyasyonOrani.replace(".", "");
        filyasyonOrani = filyasyonOrani.equals("") ? "0" : filyasyonOrani;
        return filyasyonOrani;
    }
}
