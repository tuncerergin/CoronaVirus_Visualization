package com.tuncerergin.covid19.controller;


import com.tuncerergin.covid19.model.turkey.CovidData;
import com.tuncerergin.covid19.service.TurkeyRestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/turkey")
public class TurkeyDataController {
    private final TurkeyRestService restService;
    CovidData[] history;
    CovidData sonDurum;

    public TurkeyDataController(TurkeyRestService restService) {
        this.restService = restService;
    }

    @GetMapping("/data")
    public String countryData(Model model) {
        sonDurum = restService.getData("sondurum")[0];
        history = restService.getData("liste");

        Collections.reverse(Arrays.asList(history));
        ArrayList<List<String>> list = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            list.add(new ArrayList<>());
        }
        Arrays.stream(history).parallel().forEachOrdered(covid -> {

            try {
                list.get(0).add("'" + new SimpleDateFormat("dd.MM.yyyy", new Locale("tr")).format(covid.getTarih()) + "'");
            } catch (ParseException ignored) {
            }

            list.get(1).add("'" + covid.getToplamVaka() + "'");
            list.get(2).add("'" + covid.getToplamIyilesen() + "'");
            list.get(3).add("'" + covid.getToplamVefat() + "'");
            list.get(4).add("'" + covid.getGunlukVaka() + "'");
            list.get(5).add("'" + covid.getGunlukIyilesen() + "'");
            list.get(6).add("'" + covid.getGunlukVefat() + "'");
            list.get(7).add(String.valueOf(
                    Integer.parseInt(covid.getToplamVaka()) -
                            Integer.parseInt(covid.getToplamIyilesen()) -
                            Integer.parseInt(covid.getToplamVefat())
                    )
            );
            /*
             * Total Closed data =total recovered cases+total death cases
             * Recovery rate = total recovered cases/toal closed cases*100
             * Mortality rate = total death cases/toal closed cases*100
             * */
            list.get(8).add(String.format(Locale.US, "%.2f",
                    Float.parseFloat(covid.getToplamVefat()) /
                            (Float.parseFloat(covid.getToplamIyilesen()) + Float.parseFloat(covid.getToplamVefat()))
                            * (float) 100)
            );
            list.get(9).add(String.format(Locale.US, "%.2f",
                    Float.parseFloat(covid.getToplamIyilesen()) /
                            (Float.parseFloat(covid.getToplamIyilesen()) + Float.parseFloat(covid.getToplamVefat()))
                            * (float) 100)
            );

            list.get(10).add("'" + covid.getGunlukTest() + "'");
            list.get(11).add("'" + covid.getToplamYogunBakim() + "'");
            list.get(12).add("'" + covid.getToplamEntube() + "'");
            list.get(13).add("'" + covid.getAgirHastaSayisi() + "'");
            list.get(14).add("'" + covid.getHastalardaZaturreOran() + "'");
            list.get(15).add("'" + (
                    Integer.parseInt(covid.getGunlukVaka().trim()) -
                            Integer.parseInt(covid.getGunlukIyilesen().trim()) +
                            Integer.parseInt(covid.getGunlukVefat().trim())
            ) + "'");
            list.get(16).add("'" + covid.getYatakDolulukOrani() + "'");
            list.get(17).add("'" + covid.getEriskinYogunBakimDolulukOrani() + "'");
            list.get(18).add("'" + covid.getVentilatorDolulukOrani() + "'");
            list.get(19).add("'" + covid.getOrtalamaFilyasyonSuresi() + "'");
            list.get(20).add("'" + covid.getOrtalamaTemasliTespitSuresi() + "'");
            list.get(21).add("'" + covid.getFilyasyonOrani() + "'");
            list.get(22).add("'" + (
                    !covid.getGunlukVaka().equals("0") && !covid.getGunlukTest().equals("0") ?
                            String.format(Locale.US, "%.3f", Float.parseFloat(covid.getGunlukVaka()) / Float.parseFloat(covid.getGunlukTest())) : "0.000"
            ) + "'");
        });

        model.addAttribute("data", list);
        model.addAttribute("sonDurum", sonDurum);
        try {
            model.addAttribute("tarih", new SimpleDateFormat("dd MMMM yyyy", new Locale("tr")).format(sonDurum.getTarih()));
        } catch (ParseException ignored) {
            model.addAttribute("tarih", "Tarih çözümlenemedi.");
        }

        return "/dashboard.xhtml";

    }
}
