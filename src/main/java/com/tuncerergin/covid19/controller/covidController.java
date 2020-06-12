package com.tuncerergin.covid19.controller;

import com.tuncerergin.covid19.model.Breakdown;
import com.tuncerergin.covid19.model.CoronaVirus;
import com.tuncerergin.covid19.model.History;
import com.tuncerergin.covid19.service.RestService;
import lombok.Data;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Data
@RequestMapping("/")
public class covidController {
    CoronaVirus coronaVirus;
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH);
    private LineChartModel totalCasesLineChart;
    private LineChartModel recoveredCasesLineChart;
    private LineChartModel deathsCasesLineChart;
    private LineChartModel allCasesLineChart;
    private LineChartModel cartesianLinerModel;
    private LineChartModel recoveredDeathsLinerModel;
    private BarChartModel mountlyCases;
    private BarChartModel dailyRecoveredChart;
    private BarChartModel dailyDeathsChart;
    private BarChartModel dailyNewCasesChart;
    String countryCode;
    @Value("${google.mapsApiKey}")
    String mapsApiKey;
    /*Default sorted*/
    TreeMap<LocalDate, Integer> dailyConfirmed;
    TreeMap<LocalDate, Integer> dailyDeaths;
    TreeMap<LocalDate, Integer> dailyRecovered;
    String lastUpdateTime;
    private final RestService restService;

    public covidController(RestService restService) {
        this.restService = restService;
    }

    @GetMapping("/global")
    public String globalData(Model model) {
        init("global");
        List<Breakdown> breakdownList = coronaVirus.getStats().getBreakdowns();
        breakdownList.sort((br1, br2) -> br2.getTotalConfirmedCases() - br1.getTotalConfirmedCases());
        model.addAttribute("breakdowns", breakdownList);

        return "covid.xhtml";
    }

    @GetMapping("/covid")
    public String countryData(@RequestParam(value = "countryCode", required = false, defaultValue = "global") String countryCode, Model model) {

        init(countryCode);
        List<Breakdown> breakdownList = coronaVirus.getStats().getBreakdowns();
        breakdownList.sort((br1, br2) -> br2.getTotalConfirmedCases() - br1.getTotalConfirmedCases());
        model.addAttribute("breakdowns", breakdownList);
        return "/covid.xhtml";

    }

    public void init(String countryCode) {
        getData(countryCode);
        mountlyCases();
        totalCasesLineModel();
        recoveredCasesLineModel();
        deathsCasesLineModel();
        activeCasesLineModel();
        deathsRecoveredLineModel();
        createNewCasesBarModel();
        createRecoveredCasesBarModel();
        createDeathsBarModel();
    }

    public void getData(String countryCode) {
        coronaVirus = restService.getData(countryCode);
    }

    private void mountlyCases() {
        mountlyCases = new BarChartModel();
        dailyConfirmed = new TreeMap<>();
        dailyRecovered = new TreeMap<>();
        dailyDeaths = new TreeMap<>();
        int marchTotal = 0, marchDead = 0, marchRecovered = 0;
        int aprilTotal = 0, aprilDead = 0, aprilRecovered = 0;
        int mayTotal = 0, mayDead = 0, mayRecovered = 0;
        int junTotal = 0, junDead = 0, junRecovered = 0;


        History beforeToday = coronaVirus.getStats().getHistory().get(0);
        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                LocalDate date = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                dailyConfirmed.put(date, dailyCase.getConfirmed() - beforeToday.getConfirmed());
                dailyRecovered.put(date, dailyCase.getRecovered() - beforeToday.getRecovered());
                dailyDeaths.put(date, dailyCase.getDeaths() - beforeToday.getDeaths());
                beforeToday = dailyCase;

            }
        }


        for (Map.Entry<LocalDate, Integer> entry : dailyConfirmed.entrySet()) {
            if (entry.getKey().getMonth().getValue() == 3) {
                marchTotal = marchTotal + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 4) {
                aprilTotal = aprilTotal + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 5) {
                mayTotal = mayTotal + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 6) {
                junTotal = junTotal + entry.getValue();
            }
        }
        for (Map.Entry<LocalDate, Integer> entry : dailyDeaths.entrySet()) {
            if (entry.getKey().getMonth().getValue() == 3) {
                marchDead = marchDead + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 4) {
                aprilDead = aprilDead + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 5) {
                mayDead = mayDead + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 6) {
                junDead = junDead + entry.getValue();
            }
        }
        for (Map.Entry<LocalDate, Integer> entry : dailyRecovered.entrySet()) {
            if (entry.getKey().getMonth().getValue() == 3) {
                marchRecovered = marchRecovered + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 4) {
                aprilRecovered = aprilRecovered + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 5) {
                mayRecovered = mayRecovered + entry.getValue();
            } else if (entry.getKey().getMonth().getValue() == 6) {
                junRecovered = junRecovered + entry.getValue();
            }
        }

        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Confirmed Cases");
        barDataSet.setBackgroundColor("rgba(255, 159, 64, 0.2)");
        barDataSet.setBorderColor("rgb(255, 159, 64)");
        barDataSet.setBorderWidth(1);

        List<Number> values = new ArrayList<>();
        values.add(marchTotal);
        values.add(aprilTotal);
        values.add(mayTotal);
        values.add(junTotal);
        barDataSet.setData(values);

        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("Recovered Cases");
        barDataSet2.setBackgroundColor("rgba(97, 255, 163, 0.2)");
        barDataSet2.setBorderColor("rgb(97, 255, 163)");
        barDataSet2.setBorderWidth(1);

        List<Number> values2 = new ArrayList<>();
        values2.add(marchRecovered);
        values2.add(aprilRecovered);
        values2.add(mayRecovered);
        values2.add(junRecovered);
        barDataSet2.setData(values2);

        BarChartDataSet barDataSet3 = new BarChartDataSet();
        barDataSet3.setLabel("Deaths Cases");
        barDataSet3.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet3.setBorderColor("rgb(255, 99, 132)");
        barDataSet3.setBorderWidth(1);

        List<Number> values3 = new ArrayList<>();
        values3.add(marchDead);
        values3.add(aprilDead);
        values3.add(mayDead);
        values3.add(junDead);
        barDataSet3.setData(values3);


        List<String> labels = new ArrayList<>();
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
        data.addChartDataSet(barDataSet3);
        data.setLabels(labels);
        mountlyCases.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Monthly Cases");
        options.setTitle(title);

        mountlyCases.setOptions(options);
    }


    public void totalCasesLineModel() {
        totalCasesLineChart = new LineChartModel();
        ChartData confirmedCasesData = new ChartData();

        LineChartDataSet confirmedCasesDataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getConfirmed());

                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
            }
        }
        //Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Total Confirmed Cases");
        options.setLegend(getLegend());
        options.setTitle(title);
        totalCasesLineChart.setOptions(options);
        totalCasesLineChart.setData(confirmedCasesData);
        totalCasesLineChart.setData(getChartData(values, labels, "Total Confirmed Cases", "#ffc107"));
    }

    public void recoveredCasesLineModel() {
        recoveredCasesLineChart = new LineChartModel();
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getRecovered());
                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
            }
        }

        //Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Total Recovered Cases");
        options.setTitle(title);
        options.setLegend(getLegend());
        recoveredCasesLineChart.setOptions(options);
        recoveredCasesLineChart.setData(getChartData(values, labels, "Recovered Cases", "#28a745"));
    }

    public void deathsCasesLineModel() {
        deathsCasesLineChart = new LineChartModel();
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getDeaths());
                LocalDate date = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date));
            }
        }

        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Total Deaths");
        options.setTitle(title);
        options.setLegend(getLegend());
        deathsCasesLineChart.setOptions(options);
        deathsCasesLineChart.setData(getChartData(values, labels, "Deaths Cases", "#dc3545"));
    }

    public void activeCasesLineModel() {
        cartesianLinerModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
                values.add(dailyCase.getConfirmed()-dailyCase.getRecovered()-dailyCase.getDeaths());

            }
        }
        dataSet.setData(values);
        dataSet.setLabel("Active Cases");
        dataSet.setYaxisID("left-y-axis");
        dataSet.setBackgroundColor("rgba(138, 74, 243, 0)");
        dataSet.setBorderColor("rgb(138, 74, 243)");
        dataSet.setPointStyle("circle");
        dataSet.setPointBorderWidth(0.1);



        data.addChartDataSet(dataSet);

        data.setLabels(labels);
        cartesianLinerModel.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");

        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Active Cases");
        options.setTitle(title);
        options.setLegend(getLegend());
        cartesianLinerModel.setOptions(options);
    }

    public void deathsRecoveredLineModel() {
        recoveredDeathsLinerModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        LineChartDataSet dataSet2 = new LineChartDataSet();

        List<String> labels = new ArrayList<>();
        List<Number> values = new ArrayList<>(dailyDeaths.values());
        List<Number> values2 = new ArrayList<>(dailyRecovered.values());

        for (Map.Entry<LocalDate, Integer> entry : dailyDeaths.entrySet()) {
            labels.add(outputFormatter.format(entry.getKey()));
        }

        dataSet.setData(values);
        dataSet.setLabel("Deaths Cases");
        dataSet.setYaxisID("right-y-axis");
        dataSet.setBackgroundColor("rgba(255, 159, 64, 0)");
        dataSet.setBorderColor("rgb(255, 159, 64)");
        dataSet.setPointStyle("circle");
        dataSet.setPointBorderWidth(0.1);

        dataSet2.setData(values2);
        dataSet2.setLabel("Recovered Cases");
        dataSet2.setYaxisID("left-y-axis");
        dataSet2.setBackgroundColor("rgba(97, 255, 163, 0)");
        dataSet2.setBorderColor("rgb(97, 255, 163)");
        dataSet2.setPointStyle("circle");
        dataSet2.setPointBorderWidth(0.1);

        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);

        data.setLabels(labels);
        recoveredDeathsLinerModel.setData(data);

        //Options
        LineChartOptions options = new LineChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setId("left-y-axis");
        linearAxes.setPosition("left");
        CartesianLinearAxes linearAxes2 = new CartesianLinearAxes();
        linearAxes2.setId("right-y-axis");
        linearAxes2.setPosition("right");

        cScales.addYAxesData(linearAxes);
        cScales.addYAxesData(linearAxes2);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Daily New Recovered-Deaths");
        options.setTitle(title);
        options.setLegend(getLegend());
        recoveredDeathsLinerModel.setOptions(options);
    }

    public void createRecoveredCasesBarModel() {
        dailyRecoveredChart = new BarChartModel();

        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Daily New Recovered");
        List<Number> values = new ArrayList<>(dailyRecovered.values());
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(75, 192, 192, 0.2)");
        }
        barDataSet.setBackgroundColor(bgColor);
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            borderColor.add("rgb(75, 192, 192)");
        }
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);
        List<String> labels = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : dailyRecovered.entrySet()) {
            labels.add(outputFormatter.format(entry.getKey()));
        }
        data.setLabels(labels);
        dailyRecoveredChart.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Daily New Recovered");
        options.setTitle(title);
        options.setLegend(getLegend());
        dailyRecoveredChart.setOptions(options);
    }

    public void createNewCasesBarModel() {

        dailyNewCasesChart = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Daily New Cases");
        List<Number> values = new ArrayList<>(dailyConfirmed.values());
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(255, 205, 86, 0.2)");
        }
        barDataSet.setBackgroundColor(bgColor);
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            borderColor.add("rgb(255, 205, 86)");
        }
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);
        List<String> labels = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : dailyConfirmed.entrySet()) {
            labels.add(outputFormatter.format(entry.getKey()));
        }
        data.setLabels(labels);
        dailyNewCasesChart.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Daily New Cases");
        options.setTitle(title);
        options.setLegend(getLegend());
        dailyNewCasesChart.setOptions(options);
    }

    public void createDeathsBarModel() {

        dailyDeathsChart = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Daily New Deaths");
        List<Number> values = new ArrayList<>(dailyDeaths.values());
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(255, 99, 132, 0.2)");
        }
        barDataSet.setBackgroundColor(bgColor);
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            borderColor.add("rgb(255, 99, 132)");
        }
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);
        List<String> labels = new ArrayList<>();
        for (Map.Entry<LocalDate, Integer> entry : dailyDeaths.entrySet()) {
            labels.add(outputFormatter.format(entry.getKey()));
        }
        data.setLabels(labels);
        dailyDeathsChart.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Daily New Deaths");
        options.setTitle(title);
        options.setLegend(getLegend());
        dailyDeathsChart.setOptions(options);
    }

    public Legend getLegend() {
        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(10);
        legend.setLabels(legendLabels);
        return legend;
    }



    public ChartData getChartData(List<Number> values, List<String> labels, String label, String color) {
        ChartData chartData = new ChartData();

        LineChartDataSet lineChartDataSet = new LineChartDataSet();
        lineChartDataSet.setData(values);
        lineChartDataSet.setLabel(label);
        lineChartDataSet.setBorderColor(color);
        lineChartDataSet.setBackgroundColor("rgba(255, 255, 255, 0)");
        lineChartDataSet.setPointStyle("circle");
        lineChartDataSet.setPointBorderWidth(0.1);
        chartData.addChartDataSet(lineChartDataSet);
        chartData.setLabels(labels);
        return chartData;
    }

}
