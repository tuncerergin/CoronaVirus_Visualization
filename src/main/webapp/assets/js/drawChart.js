am4core.ready(function () {
    am4core.useTheme(am4themes_animated);

    var chart = am4core.create("timeLineChart", am4plugins_timeline.SerpentineChart);
    chart.curveContainer.padding(50, 25, 50, 25);
    chart.levelCount = 4;
    chart.yAxisRadius = am4core.percent(25);
    chart.yAxisInnerRadius = am4core.percent(-25);
    chart.maskBullets = false;

    var title = chart.titles.create();
    title.text = "Türkiye koronavirüs timeline";
    title.fontSize = 18;
    title.marginBottom = 15;
    /*    var label = chart.chartContainer.createChild(am4core.Label);
        label.text = "Km / h";
        label.align = "center";*/
    var colorSet = new am4core.ColorSet();
    colorSet.saturation = 0.5;
    var saglikIcon = "https://www.flaticon.com/svg/static/icons/svg/3096/3096565.svg";
    var egitimIcon = "https://www.flaticon.com/svg/static/icons/svg/1940/1940611.svg";
    var sporIcon = "https://www.flaticon.com/svg/static/icons/svg/1165/1165187.svg";
    var ulasimIcon = "https://www.flaticon.com/svg/static/icons/svg/832/832945.svg";
    var yasakIcon = "https://www.flaticon.com/svg/static/icons/svg/497/497788.svg";
    var normallesmeIcon = "https://www.flaticon.com/svg/static/icons/svg/3063/3063346.svg";
    var saglikColor = "#13D8AA";
    var egitimColor = "#F46036";
    var sporColor = "#FEB019";
    var ulasimColor = "#008FFB";
    var yasakColor = "#FF4560";
    var normallesmeColor = "#775DD0";
    chart.data = [{
        "category": "Sağlık",
        "start": "2020-03-11",
        "end": "2020-03-12",
        "color": saglikColor,
        "icon": saglikIcon,
        "task": "İlk koronavirüs vakası görüldü"
    }, {
        "category": "Spor",
        "start": "2020-03-12",
        "end": "2020-03-13",
        "color": sporColor,
        "icon": sporIcon,
        "task": "Spor müsabakalarının seyircisiz oynanması"
    }, {
        "category": "Ulaşım",
        "start": "2020-03-14",
        "end": "2020-03-15",
        "color": ulasimColor,
        "icon": ulasimIcon,
        "task": "Ülkeler arası uçuş kısıtlaması"
    }, {
        "category": "Ulaşım",
        "start": "2020-03-15",
        "end": "2020-03-16",
        "color": ulasimColor,
        "icon": ulasimIcon,
        "task": "Gürcistan ile Sarp sınır kapısının kapatılması"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-15",
        "end": "2020-03-16",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Kütüphanelerin kapatılması"
    }, {
        "category": "Eğitim",
        "start": "2020-03-16",
        "end": "2020-03-17",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "Eğitime 1 hafta ara verildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-16",
        "end": "2020-03-17",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Gece kulüpleri vb. eğlence mekanlarının kapatılması\n" +
            "Camiilerin ibadete kapatılması\nSinema, kafe vb. mekanların kapatılması"
    }, {
        "category": "Spor",
        "start": "2020-03-19",
        "end": "2020-03-20",
        "color": sporColor,
        "icon": sporIcon,
        "task": "Tüm spor müsabakaların ertelenmesi"
    }, {
        "category": "Sağlık",
        "start": "2020-03-20",
        "end": "2020-03-21",
        "color": saglikColor,
        "icon": saglikIcon,
        "task": "Tüm vakıf hastahaneleri pandemi hastahanesi ilan edildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-21",
        "end": "2020-03-22",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Mangal yakılması yasaklandı\nAsker uğurlama törenleri yasaklandı\n" +
            "Berber, kuaför vb kapatılması\nRestaurant, pastahane vb, gel-al sistemine geçilmesi\n65 yaş üstü kişilere sokağa çıkma kısıtlaması getirildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-22",
        "end": "2020-03-23",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Tüm icra ve iflas işlemleri durduruldu"
    }, {
        "category": "Eğitim",
        "start": "2020-03-22",
        "end": "2020-03-23",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "Online eğitime geçildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-21",
        "end": "2020-03-22",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "3 Ay işten çıkarma yasaklandı"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-03-21",
        "end": "2020-03-22",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Piknik alanı, orman, ören yerlerinin hafta sonu kapatılması"
    }, {
        "category": "Ulaşım",
        "start": "2020-04-03",
        "end": "2020-04-04",
        "color": ulasimColor,
        "icon": ulasimIcon,
        "task": "Şehirlerarası seyehat kısıtlaması"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-03",
        "end": "2020-04-04",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "01.01.2000 ve üzeri doğumlular için sokağa çıkma yasağı"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-06",
        "end": "2020-04-07",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "23 Nisan kutlamalarının yasaklanması\nParayla maske satışının yasaklanması"
    }, {
        "category": "Sağlık",
        "start": "2020-04-06",
        "end": "2020-04-07",
        "color": saglikColor,
        "icon": saglikIcon,
        "task": "İstanbula 1000 kapasiteli hastahane inşaatı yapılması"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-08",
        "end": "2020-04-09",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Camiilerde teravih namazının kılınmaması"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-11",
        "end": "2020-04-12",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Haftasonu sokağa çıkma yasağı ilan edildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-18",
        "end": "2020-04-19",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Haftasonu sokağa çıkma yasağı ilan edildi"
    }, {
        "category": "Sağlık",
        "start": "2020-04-20",
        "end": "2020-04-21",
        "color": saglikColor,
        "icon": saglikIcon,
        "task": "Başakşehir Şehir Hastahanesi hizmete açıldı"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-04-23",
        "end": "2020-04-26",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "23 Nisan sebebiyle 4 günlük sokağa çıkma yasağı ilan edildi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-05-01",
        "end": "2020-05-03",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "1 Mayıs İşçi bayramı nedeniyle 3 gün sokağa çıkma yasağı ilan edildi"
    }, {
        "category": "Normalleşme",
        "start": "2020-05-04",
        "end": "2020-05-05",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "65 yaş üstü sokağa çıkma yasağının esnetilmesi\n" +
            "AVM, berber kuaför vb. yerlerin 11 mayıs itibarıyla açılması\n" +
            "20 yaş ve altı grubun sokağa çıkma yasağının esnetilmesi\n" +
            "Bazı illerde ulaşım kısıtlamasının esnetilmesi\n" +
            "Askerlik terhis işlemlerinin 31 Mayısta başlatılması"
    }, {
        "category": "Normalleşme",
        "start": "2020-05-06",
        "end": "2020-05-07",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "Hayat eve sığar(HES) uygulamasının tanıtılması"
    }, {
        "category": "Normalleşme",
        "start": "2020-05-07",
        "end": "2020-05-08",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "Cerrahi maskelerin tavan fiyatı ₺1 olarak belirlenmesi"
    }, {
        "category": "Yasaklamalar",
        "start": "2020-05-23",
        "end": "2020-05-26",
        "color": yasakColor,
        "icon": yasakIcon,
        "task": "Ramazan bayramı sebebiyle 4 günlük sokağa çıkma yasağı"
    }, {
        "category": "Normalleşme",
        "start": "2020-05-29",
        "end": "2020-05-30",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "Camiilerin ibadete tekrar açılması"
    }, {
        "category": "Normalleşme",
        "start": "2020-06-01",
        "end": "2020-06-02",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "Şehirler arası seyehat kısıtlamasının kaldırılması\n" +
            "Kafe, Reatoran vb, yerlerin açılması"
    }, {
        "category": "Normalleşme",
        "start": "2020-06-03",
        "end": "2020-06-04",
        "color": normallesmeColor,
        "icon": normallesmeIcon,
        "task": "65 yaş üstü sokağa çıkma yasağının tamamen kaldırılması"
    }, {
        "category": "Spor",
        "start": "2020-06-12",
        "end": "2020-06-13",
        "color": sporColor,
        "icon": sporIcon,
        "task": "Ertelenen liglerin seyircisiz olarak tekrar başlatılması"
    }, {
        "category": "Eğitim",
        "start": "2020-06-13",
        "end": "2020-06-14",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "MSÜ Askeri Öğrenci sınavının yapılması"
    }, {
        "category": "Eğitim",
        "start": "2020-06-20",
        "end": "2020-06-21",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "LGS sınavının yapılması"
    }, {
        "category": "Eğitim",
        "start": "2020-06-25",
        "end": "2020-06-26",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "YKS’ye giriş sınavının yapılması"
    }, {
        "category": "Eğitim",
        "start": "2020-08-15",
        "end": "2020-08-16",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "Özel okulların telafi eğitimi yapması"
    }, {
        "category": "Eğitim",
        "start": "2020-08-31",
        "end": "2020-09-01",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "Devlet okulların telafi eğitimi yapması"
    }, {
        "category": "Eğitim",
        "start": "2020-09-06",
        "end": "2020-09-07",
        "color": egitimColor,
        "icon": egitimIcon,
        "task": "KPSS sınavının yapılması"
    }];
    chart.language.locale = am4lang_tr_TR;
    chart.dateFormatter.dateFormat = "yyyy-MM-dd";
    chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";

    chart.fontSize = 11;

    var categoryAxis = chart.yAxes.push(new am4charts.CategoryAxis());
    categoryAxis.dataFields.category = "category";
    categoryAxis.renderer.grid.template.disabled = true;
    categoryAxis.renderer.labels.template.paddingRight = 25;
    categoryAxis.renderer.minGridDistance = 10;
    categoryAxis.renderer.innerRadius = -60;
    categoryAxis.renderer.radius = 60;

    var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
    dateAxis.renderer.minGridDistance = 70;
    dateAxis.baseInterval = {count: 1, timeUnit: "day"};
    dateAxis.renderer.tooltipLocation = 0;
    dateAxis.startLocation = -0.5;
    dateAxis.renderer.line.strokeDasharray = "1,4";
    dateAxis.renderer.line.strokeOpacity = 0.6;
    dateAxis.tooltip.background.fillOpacity = 0.2;
    dateAxis.tooltip.background.cornerRadius = 5;
    dateAxis.tooltip.label.fill = new am4core.InterfaceColorSet().getFor("alternativeBackground");
    dateAxis.tooltip.label.paddingTop = 7;

    var labelTemplate = dateAxis.renderer.labels.template;
    labelTemplate.verticalCenter = "middle";
    labelTemplate.fillOpacity = 0.7;
    labelTemplate.background.fill = new am4core.InterfaceColorSet().getFor("color");
    labelTemplate.background.fillOpacity = 0.7;
    labelTemplate.padding(7, 7, 7, 7);

    var series = chart.series.push(new am4plugins_timeline.CurveColumnSeries());
    series.columns.template.height = am4core.percent(20);
    series.columns.template.tooltipText = "{task}: [bold]{openDateX}[/] - [bold]{dateX}[/]";

    series.dataFields.openDateX = "start";
    series.dataFields.dateX = "end";
    series.dataFields.categoryY = "category";
    series.columns.template.propertyFields.fill = "color"; // get color from data
    series.columns.template.propertyFields.stroke = "color";
    series.columns.template.strokeOpacity = 0;

    var bullet = series.bullets.push(new am4charts.CircleBullet());
    bullet.circle.radius = 3;
    bullet.circle.strokeOpacity = 0;
    bullet.propertyFields.fill = "color";
    bullet.locationX = 0;


    var bullet2 = series.bullets.push(new am4charts.CircleBullet());
    bullet2.circle.radius = 3;
    bullet2.circle.strokeOpacity = 0;
    bullet2.propertyFields.fill = "color";
    bullet2.locationX = 1;

    var imageBullet1 = series.bullets.push(new am4plugins_bullets.PinBullet());
    imageBullet1.background.radius = 18;
    imageBullet1.locationX = 1;
    imageBullet1.propertyFields.stroke = "color";
    imageBullet1.background.propertyFields.fill = "color";
    imageBullet1.image = new am4core.Image();
    imageBullet1.image.propertyFields.href = "icon";
    imageBullet1.image.scale = 0.5;
    imageBullet1.circle.radius = am4core.percent(100);
    imageBullet1.background.fillOpacity = 0.8;
    imageBullet1.background.strokeOpacity = 0;
    imageBullet1.dy = -2;
    imageBullet1.background.pointerBaseWidth = 10;
    imageBullet1.background.pointerLength = 10;
    imageBullet1.tooltipText = "{task}";

    series.tooltip.pointerOrientation = "up";

    imageBullet1.background.adapter.add("pointerAngle", (value, target) => {
        if (target.dataItem) {
            var position = dateAxis.valueToPosition(target.dataItem.openDateX.getTime());
            return dateAxis.renderer.positionToAngle(position);
        }
        return value;
    });

    var hs = imageBullet1.states.create("hover")
    hs.properties.scale = 1.3;
    hs.properties.opacity = 1;
    chart.scrollbarX = new am4core.Scrollbar();
    chart.scrollbarX.align = "center"
    chart.scrollbarX.width = am4core.percent(85);

    var cursor = new am4plugins_timeline.CurveCursor();
    chart.cursor = cursor;
    cursor.xAxis = dateAxis;
    cursor.yAxis = categoryAxis;
    cursor.lineY.disabled = true;
    cursor.lineX.strokeDasharray = "1,4";
    cursor.lineX.strokeOpacity = 1;

    dateAxis.renderer.tooltipLocation2 = 0;
    categoryAxis.cursorTooltipEnabled = false;


});


am4core.ready(function () {

// Themes begin
    am4core.useTheme(am4themes_animated);
// Themes end

// Create map instance
    var chart = am4core.create("countryMap", am4maps.MapChart);


// Set map definition
    chart.geodata = am4geodata_turkeyLow;

// Set projection
    chart.projection = new am4maps.projections.Mercator();

// Set map definition
    chart.geodataSource.url = "https://www.amcharts.com/lib/4/geodata/json/turkeyLow.json";
    chart.seriesContainer.draggable = false;
    chart.seriesContainer.resizable = false;
    chart.maxZoomLevel = 1;
    // Set projection
    chart.projection = new am4maps.projections.Mercator();

    // Create map polygon series
    var polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());

    //Set min/max fill color for each area
    polygonSeries.heatRules.push({
        property: "fill",
        target: polygonSeries.mapPolygons.template,
        min: chart.colors.getIndex(1).brighten(1),
        max: chart.colors.getIndex(1).brighten(-0.3)
    });

    // Make map load polygon data (state shapes and names) from GeoJSON
    polygonSeries.useGeodata = true;

    // Configure series tooltip
    var polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = "{name}";
    polygonTemplate.nonScalingStroke = true;
    polygonTemplate.strokeWidth = 0.5;
    polygonTemplate.fill = am4core.color("#03A9F4");

    var hs = polygonTemplate.states.create("hover");
    hs.properties.fill = chart.colors.getIndex(2).brighten(-0.5);
});

function drawChart(chartID, text, color, data, annotation, label) {
    var options = {
        colors: [color],
        series: [{
            name: text[0],
            data: data
        }],
        chart: {
            height: 350,
            type: 'area',
            zoom: {
                enabled: false
            }
        }, annotations: {
            xaxis: [{
                x: annotation != null ? annotation[0] : '',
                strokeDashArray: 0,
                borderColor: '#FF4560',
                label: {
                    borderColor: '#FF4560',
                    style: {
                        color: '#fff',
                        background: '#FF4560',
                    },
                    text: annotation != null ? annotation[1] : '',

                }
            }]
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            width: 2,
            curve: 'smooth'
        },
        title: {
            text: text[0],
            align: 'left'
        },
        subtitle: {
            text: text[1] != null ? text[1] : '',
            align: 'left',
            margin: 10,
            floating: false,
            style: {
                fontSize: '10px',
                fontWeight: 'normal',
                color: '#9699a2'
            },
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'],
                opacity: 0.5
            },
        },
        xaxis: {
            categories: label,
            labels: {
                rotate: -30,
                style: {
                    fontSize: '10px'
                },
            }
        }
    };

    var chart = new ApexCharts(document.getElementById(chartID), options);
    chart.render();
}

function draw2LineChart(chartID, series1Name, series2Name, titleText, subTitleText, color1, color2, data, data2, label) {
    var options = {
        colors: [color1, color2],
        series: [{
            name: series1Name,
            data: data
        }, {
            name: series2Name,
            data: data2
        }],
        chart: {
            height: 350,
            type: 'area',
            zoom: {
                enabled: false
            }

        },
        fill: {
            type: "gradient",
            gradient: {
                shadeIntensity: 0.5,
                opacityFrom: 0.5,
                opacityTo: 0.0,
                stops: [0, 90, 100]
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            width: 2,
            curve: 'smooth'
        },
        title: {
            text: titleText,
            align: 'left'
        }, subtitle: {
            text: subTitleText,
            align: 'left',
            margin: 10,
            floating: false,
            style: {
                fontSize: '10px',
                fontWeight: 'normal',
                color: '#9699a2'
            },
        },
        tooltip: {
            y: [
                {
                    title: {
                        formatter: function (val) {
                            return val + " (%)"
                        }
                    }
                },
                {
                    title: {
                        formatter: function (val) {
                            return val + " (%)"
                        }
                    }
                }
            ]
        },
        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'],
                opacity: 0.5
            },
        },
        xaxis: {
            categories: label,
            labels: {
                rotate: -30,
                style: {
                    fontSize: '10px'
                },
            }
        },
        yaxis: {
            max: 100
        }
    };

    var chart = new ApexCharts(document.getElementById(chartID), options);
    chart.render();
}

function drawCombinedLineChart(chartID, series1Name, series2Name, series3Name, titleText, color1, color2, color3, data, data2, data3, annotation, label) {
    var options = {
        colors: [color1, color2, color3],
        series: [{
            name: series1Name,
            data: data
        }, {
            name: series2Name,
            data: data2
        }, {
            name: series3Name,
            data: data3
        }],
        chart: {
            height: 350,
            type: 'area',
            stacked: false,
            zoom: {
                enabled: false
            }

        },
        fill: {
            type: "gradient",
            gradient: {
                shadeIntensity: 1,
                inverseColors: false,
                opacityFrom: 0.45,
                opacityTo: 0.05,
                stops: [20, 100, 100, 100]
            }
        },
        dataLabels: {
            enabled: false
        },
        markers: {
            size: 0,
        },
        annotations: {
            xaxis: [{
                x: annotation != null ? annotation[0] : '',
                strokeDashArray: 0,
                borderColor: '#FF4560',
                label: {
                    borderColor: '#FF4560',
                    style: {
                        color: '#fff',
                        background: '#FF4560',
                    },
                    text: annotation != null ? annotation[1] : '',

                }
            }]
        },
        stroke: {
            width: 2,
            curve: 'smooth'
        },
        title: {
            text: titleText,
            align: 'left'
        },

        grid: {
            row: {
                colors: ['#f3f3f3', 'transparent'],
                opacity: 0.5
            },
        },
        tooltip: {
            shared: true
        },
        xaxis: {
            categories: label,
            labels: {
                rotate: -30,
                style: {
                    fontSize: '10px'
                },
            }
        }
    };

    var chart = new ApexCharts(document.getElementById(chartID), options);
    chart.render();
}

