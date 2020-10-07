package com.tuncerergin.covid19.controller;


import com.tuncerergin.covid19.model.turkey.CovidData;
import com.tuncerergin.covid19.service.TurkeyRestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

@Controller
@RequestMapping("/turkey")
public class TurkeyDataController {
    private final TurkeyRestService restService;
    CovidData[] history;
    CovidData[] sonDurum;
    ArrayList<String> toplamVaka;
    ArrayList<String> toplamIyilesen;
    ArrayList<String> toplamOlum;

    ArrayList<String> gunlukVaka;
    ArrayList<String> gunlukIyilesen;
    ArrayList<String> gunlukOlum;
    ArrayList<String> gunlukSonuc;
    ArrayList<String> tarihList;
    ArrayList<String> aktifVaka;
    ArrayList<String> iyilesmeOrani;
    ArrayList<String> olmeOrani;
    ArrayList<String> testSayisi;

    ArrayList<String> toplamYogunBakim;
    ArrayList<String> toplamEntube;
    ArrayList<String> hastalardaZaturreOran;
    ArrayList<String> agirHastaSayisi;

    ArrayList<String> yatakDolulukOrani;
    ArrayList<String> eriskinYogunBakimDolulukOrani;
    ArrayList<String> ventilatorDolulukOrani;
    ArrayList<String> ortalamaFilyasyonSuresi;
    ArrayList<String> ortalamaTemasliTespitSuresi;
    ArrayList<String> filyasyonOrani;
    ArrayList<String> gunlukVakaSayisininGunlukTestSayisinaOrani;

    public TurkeyDataController(TurkeyRestService restService) {
        this.restService = restService;
    }

    @GetMapping("/data")
    public String countryData(Model model) throws ParseException {
        sonDurum = restService.getData("sondurum");
        history = restService.getData("liste");

        toplamVaka = new ArrayList<>();
        toplamIyilesen = new ArrayList<>();
        toplamOlum = new ArrayList<>();

        gunlukVaka = new ArrayList<>();
        gunlukIyilesen = new ArrayList<>();
        gunlukOlum = new ArrayList<>();
        gunlukSonuc = new ArrayList<>();
        tarihList = new ArrayList<>();
        aktifVaka = new ArrayList<>();
        iyilesmeOrani = new ArrayList<>();
        olmeOrani = new ArrayList<>();
        testSayisi = new ArrayList<>();

        toplamYogunBakim = new ArrayList<>();
        toplamEntube = new ArrayList<>();
        hastalardaZaturreOran = new ArrayList<>();
        agirHastaSayisi = new ArrayList<>();

        yatakDolulukOrani = new ArrayList<>();
        eriskinYogunBakimDolulukOrani = new ArrayList<>();
        ventilatorDolulukOrani = new ArrayList<>();
        ortalamaFilyasyonSuresi = new ArrayList<>();
        ortalamaTemasliTespitSuresi = new ArrayList<>();
        filyasyonOrani = new ArrayList<>();
        gunlukVakaSayisininGunlukTestSayisinaOrani = new ArrayList<>();
        for (int gun = history.length - 1; gun >= 0; gun--) {
            toplamVaka.add("'" + history[gun].getToplamVaka() + "'");
            toplamIyilesen.add("'" + history[gun].getToplamIyilesen() + "'");
            toplamOlum.add("'" + history[gun].getToplamVefat() + "'");

            gunlukVaka.add("'" + history[gun].getGunlukVaka() + "'");
            gunlukIyilesen.add("'" + history[gun].getGunlukIyilesen() + "'");
            gunlukOlum.add("'" + history[gun].getGunlukVefat() + "'");
            gunlukSonuc.add("'" + (Integer.parseInt(history[gun].getGunlukVaka().trim()) -
                    Integer.parseInt(history[gun].getGunlukIyilesen().trim()) +
                    Integer.parseInt(history[gun].getGunlukVefat().trim())) + "'");
            tarihList.add("'" + new SimpleDateFormat("dd.MM.yyyy").format(history[gun].getTarih()) + "'");

            aktifVaka.add(String.valueOf(
                    Integer.parseInt(history[gun].getToplamVaka()) -
                            Integer.parseInt(history[gun].getToplamIyilesen()) -
                            Integer.parseInt(history[gun].getToplamVefat())
            ));
            /*
             * Total Closed data =total recovered cases+total death cases
             * Recovery rate = total recovered cases/toal closed cases*100
             * Mortality rate = total death cases/toal closed cases*100
             * */
            Float recoveryRate = Float.parseFloat(history[gun].getToplamIyilesen()) /
                    (Float.parseFloat(history[gun].getToplamIyilesen()) + Float.parseFloat(history[gun].getToplamVefat()))
                    * (float) 100;

            Float mortalityRate = Float.parseFloat(history[gun].getToplamVefat()) /
                    (Float.parseFloat(history[gun].getToplamIyilesen()) + Float.parseFloat(history[gun].getToplamVefat()))
                    * (float) 100;


            olmeOrani.add(String.format(Locale.US, "%.2f", mortalityRate));
            iyilesmeOrani.add(String.format(Locale.US, "%.2f", recoveryRate));

            testSayisi.add("'" + history[gun].getGunlukTest() + "'");


            toplamYogunBakim.add("'" + history[gun].getToplamYogunBakim() + "'");

            toplamEntube.add("'" + history[gun].getToplamEntube() + "'");

            agirHastaSayisi.add("'" + history[gun].getAgirHastaSayisi() + "'");

            hastalardaZaturreOran.add("'" + history[gun].getHastalardaZaturreOran() + "'");

            yatakDolulukOrani.add("'" + history[gun].getYatakDolulukOrani() + "'");
            eriskinYogunBakimDolulukOrani.add("'" + history[gun].getEriskinYogunBakimDolulukOrani() + "'");
            ventilatorDolulukOrani.add("'" + history[gun].getVentilatorDolulukOrani() + "'");
            ortalamaFilyasyonSuresi.add("'" + history[gun].getOrtalamaFilyasyonSuresi() + "'");
            ortalamaTemasliTespitSuresi.add("'" + history[gun].getOrtalamaTemasliTespitSuresi() + "'");
            filyasyonOrani.add("'" + history[gun].getFilyasyonOrani() + "'");
            if (!history[gun].getGunlukVaka().equals("0") && !history[gun].getGunlukTest().equals("0")) {
                Float vakaSayisininGunlukTestSayisinaOrani = Float.parseFloat(history[gun].getGunlukVaka()) /
                        Float.parseFloat(history[gun].getGunlukTest());
                gunlukVakaSayisininGunlukTestSayisinaOrani.add("'" + String.format(Locale.US, "%.3f", vakaSayisininGunlukTestSayisinaOrani) + "'");
            } else {
                gunlukVakaSayisininGunlukTestSayisinaOrani.add("'0.000'");
            }
        }
        model.addAttribute("sonDurum", sonDurum[0]);
        model.addAttribute("tarih", new SimpleDateFormat("dd MMMM yyyy", new Locale("tr")).format(sonDurum[0].getTarih()));
        model.addAttribute("toplamVaka", toplamVaka);
        model.addAttribute("toplamIyilesen", toplamIyilesen);
        model.addAttribute("toplamOlum", toplamOlum);

        model.addAttribute("gunlukVaka", gunlukVaka);
        model.addAttribute("gunlukIyilesen", gunlukIyilesen);
        model.addAttribute("gunlukOlum", gunlukOlum);
        model.addAttribute("gunlukSonuc", gunlukSonuc);
        model.addAttribute("aktifVaka", aktifVaka);

        model.addAttribute("iyilesmeOrani", iyilesmeOrani);
        model.addAttribute("olmeOrani", olmeOrani);
        model.addAttribute("testSayisi", testSayisi);

        model.addAttribute("toplamYogunBakim", toplamYogunBakim);
        model.addAttribute("toplamEntube", toplamEntube);
        model.addAttribute("hastalardaZaturreOran", hastalardaZaturreOran);
        model.addAttribute("agirHastaSayisi", agirHastaSayisi);


        model.addAttribute("yatakDolulukOrani", yatakDolulukOrani);
        model.addAttribute("eriskinYogunBakimDolulukOrani", eriskinYogunBakimDolulukOrani);
        model.addAttribute("ventilatorDolulukOrani", ventilatorDolulukOrani);
        model.addAttribute("ortalamaFilyasyonSuresi", ortalamaFilyasyonSuresi);
        model.addAttribute("ortalamaTemasliTespitSuresi", ortalamaTemasliTespitSuresi);
        model.addAttribute("filyasyonOrani", filyasyonOrani);
        model.addAttribute("gunlukVakaSayisininGunlukTestSayisinaOrani", gunlukVakaSayisininGunlukTestSayisinaOrani);
        model.addAttribute("tarihList", tarihList);
        return "/dashboard.xhtml";

    }
}
