package com.tuncerergin.covid19.controller;

import com.tuncerergin.covid19.model.Breakdown;
import com.tuncerergin.covid19.model.CoronaVirus;
import com.tuncerergin.covid19.model.History;
import com.tuncerergin.covid19.model.MonthlyTotalCases;
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
    private LineChartModel mortalityRateChart;
    private LineChartModel recoveredRateChart;
    private LineChartModel recoveryDeathRateLinerModel;
    private BarChartModel monthlyCases;
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
    List<MonthlyTotalCases> monthlyTotalCasesList = new ArrayList<>();
    String lastUpdateTime;
    private final RestService restService;

    public covidController(RestService restService) {
        this.restService = restService;
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
        monthlyCases();
        totalCasesLineModel();
        recoveredCasesLineModel();
        deathsCasesLineModel();
        activeCasesLineModel();
        deathsRecoveredLineModel();
        createNewCasesBarModel();
        createRecoveredCasesBarModel();
        createDeathsBarModel();
        mortalityRateLineModel();
        recoveredRateLineModel();
        recoveryDeathRateLineModel();
    }

    public void getData(String countryCode) {
        coronaVirus = restService.getData(countryCode);
    }

    private void monthlyCases() {
        monthlyCases = new BarChartModel();
        dailyConfirmed = new TreeMap<>();
        dailyRecovered = new TreeMap<>();
        dailyDeaths = new TreeMap<>();
        for (int i = 0; i < 24; i++) {
            monthlyTotalCasesList.add(new MonthlyTotalCases());
        }

        History yesterday = coronaVirus.getStats().getHistory().get(0);
        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                LocalDate date = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);

                int confirmed = dailyCase.getConfirmed() - yesterday.getConfirmed();
                int recovered = dailyCase.getRecovered() - yesterday.getRecovered();
                int dead = dailyCase.getDeaths() - yesterday.getDeaths();
                yesterday = dailyCase;

                dailyConfirmed.put(date, confirmed);
                dailyRecovered.put(date, recovered);
                dailyDeaths.put(date, dead);

                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH);
                LocalDate convertedDate = LocalDate.parse(outputFormatter.format(date), DateTimeFormatter.ofPattern("M/d/yyyy"));

                MonthlyTotalCases monthlyTotalCases = monthlyTotalCasesList.get(convertedDate.getMonth().getValue());

                monthlyTotalCases.setMonthName(convertedDate.getMonth().name());
                monthlyTotalCases.setTotalCases(monthlyTotalCases.getTotalCases() + confirmed);
                monthlyTotalCases.setTotalRecovered(monthlyTotalCases.getTotalRecovered() + recovered);
                monthlyTotalCases.setTotalDeath(monthlyTotalCases.getTotalDeath() + dead);

                monthlyTotalCasesList.set(convertedDate.getMonth().getValue(), monthlyTotalCases);
            }
        }
        List<Number> values = new ArrayList<>();
        List<Number> values2 = new ArrayList<>();
        List<Number> values3 = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (MonthlyTotalCases t : monthlyTotalCasesList) {
            if (t.getMonthName() != null) {
                values.add(t.getTotalCases());
                values2.add(t.getTotalRecovered());
                values3.add(t.getTotalDeath());
                labels.add(t.getMonthName());
            }
        }
        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Confirmed Cases");
        barDataSet.setBackgroundColor("rgba(255, 159, 64, 0.2)");
        barDataSet.setBorderColor("rgb(255, 159, 64)");
        barDataSet.setBorderWidth(1);
        barDataSet.setData(values);

        BarChartDataSet barDataSet2 = new BarChartDataSet();
        barDataSet2.setLabel("Recovered Cases");
        barDataSet2.setBackgroundColor("rgba(97, 255, 163, 0.2)");
        barDataSet2.setBorderColor("rgb(97, 255, 163)");
        barDataSet2.setBorderWidth(1);
        barDataSet2.setData(values2);

        BarChartDataSet barDataSet3 = new BarChartDataSet();
        barDataSet3.setLabel("Deaths Cases");
        barDataSet3.setBackgroundColor("rgba(255, 99, 132, 0.2)");
        barDataSet3.setBorderColor("rgb(255, 99, 132)");
        barDataSet3.setBorderWidth(1);
        barDataSet3.setData(values3);

        ChartData data = new ChartData();
        data.addChartDataSet(barDataSet);
        data.addChartDataSet(barDataSet2);
        data.addChartDataSet(barDataSet3);

        data.setLabels(labels);
        monthlyCases.setData(data);

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

        monthlyCases.setOptions(options);
    }

    public void totalCasesLineModel() {
        totalCasesLineChart = new LineChartModel();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getConfirmed());

                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
            }
        }
        totalCasesLineChart.setOptions(getOptions("Total Confirmed Cases"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Total Confirmed Cases", "#ffc107"));
        totalCasesLineChart.setData(data);
    }

    public void recoveredCasesLineModel() {
        recoveredCasesLineChart = new LineChartModel();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getRecovered());
                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
            }
        }
        recoveredCasesLineChart.setOptions(getOptions("Total Recovered Cases"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Recovered Cases", "#28a745"));
        recoveredCasesLineChart.setData(data);
    }

    public void deathsCasesLineModel() {
        deathsCasesLineChart = new LineChartModel();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                values.add(dailyCase.getDeaths());
                LocalDate date = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date));
            }
        }
        deathsCasesLineChart.setOptions(getOptions("Total Deaths"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Deaths Cases", "#dc3545"));
        deathsCasesLineChart.setData(data);
    }

    public void activeCasesLineModel() {
        cartesianLinerModel = new LineChartModel();

        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0) {
                LocalDate date1 = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date1));
                values.add(dailyCase.getConfirmed() - dailyCase.getRecovered() - dailyCase.getDeaths());

            }
        }
        cartesianLinerModel.setOptions(getOptions("Active Cases"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Active Cases", "#8a4af3"));
        cartesianLinerModel.setData(data);

       /* //Options
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
        cartesianLinerModel.setOptions(options);*/
    }

    public void deathsRecoveredLineModel() {
        recoveredDeathsLinerModel = new LineChartModel();
        ChartData data = new ChartData();

        List<String> labels = new ArrayList<>();
        List<Object> values = new ArrayList<>(dailyDeaths.values());
        List<Object> values2 = new ArrayList<>(dailyRecovered.values());

        for (Map.Entry<LocalDate, Integer> entry : dailyDeaths.entrySet()) {
            labels.add(outputFormatter.format(entry.getKey()));
        }
        recoveredDeathsLinerModel.setOptions(getOptions("Daily New Recovered-Deaths"));

        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Deaths Cases", "#dc3545"));
        data.addChartDataSet(getLineChartDataSet(values2, "Recovered Cases", "#28a745"));
        recoveredDeathsLinerModel.setData(data);
    }

    public void createRecoveredCasesBarModel() {
        dailyRecoveredChart = new BarChartModel();

        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Daily New Recovered");
        List<Number> values = new ArrayList<>(dailyRecovered.values());
        barDataSet.setData(values);
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(75, 192, 192, 0.2)");
            borderColor.add("rgb(75, 192, 192)");
        }
        barDataSet.setBackgroundColor(bgColor);
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
        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(10);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
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
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(255, 205, 86, 0.2)");
            borderColor.add("rgb(255, 205, 86)");
        }
        barDataSet.setBackgroundColor(bgColor);
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
        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(10);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
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
        List<String> borderColor = new ArrayList<>();
        for (Number i : values) {
            bgColor.add("rgba(255, 99, 132, 0.2)");
            borderColor.add("rgb(255, 99, 132)");
        }
        barDataSet.setBackgroundColor(bgColor);
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

        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(10);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
        dailyDeathsChart.setOptions(options);

    }

    public void mortalityRateLineModel() {
        mortalityRateChart = new LineChartModel();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (Map.Entry<LocalDate, Integer> entry : dailyDeaths.entrySet()) {
            if (entry.getValue() > 1) {
                Float rate = entry.getValue().floatValue() / dailyConfirmed.get(entry.getKey()).floatValue();
                values.add(Float.valueOf(String.format("%.4f", rate)));
                labels.add(outputFormatter.format(entry.getKey()));
            }
        }
        mortalityRateChart.setOptions(getOptions("Daily Mortality Rate"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Mortality Rate", "#2196f3"));
        mortalityRateChart.setData(data);
    }

    public void recoveredRateLineModel() {
        recoveredRateChart = new LineChartModel();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (Map.Entry<LocalDate, Integer> entry : dailyRecovered.entrySet()) {
            if (entry.getValue() > 1) {
                Float rate = entry.getValue().floatValue() / dailyConfirmed.get(entry.getKey()).floatValue();
                values.add(Float.valueOf(String.format("%.4f", rate)));
                labels.add(outputFormatter.format(entry.getKey()));
            }
        }
        recoveredRateChart.setOptions(getOptions("Daily Recovery Rate"));
        ChartData data = new ChartData();
        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Recovery Rate", "#5bff22"));
        recoveredRateChart.setData(data);
    }

    public void recoveryDeathRateLineModel() {
        /*
         * Totla Closed data =total recovered cases+total death cases
         * Recovery rate = total recovered cases/toal closed cases*100
         * Mortality rate = total death cases/toal closed cases*100
         * */
        recoveryDeathRateLinerModel = new LineChartModel();
        ChartData data = new ChartData();

        List<String> labels = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        List<Object> values2 = new ArrayList<>();

        for (History dailyCase : coronaVirus.getStats().getHistory()) {
            if (dailyCase.getConfirmed() != 0 && (dailyCase.getRecovered() != 0 || dailyCase.getDeaths() != 0)) {

                int totalClosed = dailyCase.getRecovered() + dailyCase.getDeaths();

                Float recoveryRate = (float) dailyCase.getRecovered() / (float) totalClosed * (float) 100;
                values.add(Float.valueOf(String.format("%.2f", recoveryRate)));

                Float mortalityRate = (float) dailyCase.getDeaths() / (float) totalClosed * (float) 100;
                values2.add(Float.valueOf(String.format("%.2f", mortalityRate)));


                LocalDate date = LocalDate.parse(dailyCase.getDate() + ".000Z", inputFormatter);
                labels.add(outputFormatter.format(date));
            }
        }

        data.setLabels(labels);
        data.addChartDataSet(getLineChartDataSet(values, "Recovery Rate", "#5bff22"));
        data.addChartDataSet(getLineChartDataSet(values2, "Death Rate", "#ff9800"));
        recoveryDeathRateLinerModel.setData(data);


        recoveryDeathRateLinerModel.setOptions(getOptions("Outcome of total closed cases (recovery rate vs death rate)"));
    }

    private LineChartOptions getOptions(String titleText) {
        LineChartOptions options = new LineChartOptions();
        Legend legend = new Legend();
        legend.setDisplay(false);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontSize(10);
        legend.setLabels(legendLabels);

        Title title = new Title();
        title.setDisplay(false);
        title.setText(titleText);
        options.setTitle(title);
        options.setLegend(legend);
        return options;
    }

    public LineChartDataSet getLineChartDataSet(List<Object> values, String label, String color) {
        LineChartDataSet lineChartDataSet = new LineChartDataSet();
        lineChartDataSet.setData(values);
        lineChartDataSet.setLabel(label);
        lineChartDataSet.setBorderColor(color);
        lineChartDataSet.setBackgroundColor("rgba(255, 255, 255, 0)");
        lineChartDataSet.setPointStyle("circle");
        lineChartDataSet.setPointBorderWidth(0.1);
        return lineChartDataSet;
    }
}
