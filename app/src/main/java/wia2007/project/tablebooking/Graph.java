package wia2007.project.tablebooking;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wia2007.project.tablebooking.database.TableBookingDatabase;

public class Graph extends AppCompatActivity {

    private static String GROUP_1_LABEL, GROUP_2_LABEL;
    private static final int GROUPS = 2;
    private static final float BAR_SPACE = 0.05f;
    private static final float BAR_WIDTH = 0.35f;
    private BarChart chart;
    String year1 = "2023";
    String year2 = "2023";
    String month1 = "01";
    String month2 = "02";
    String data_type = "Food";
    int restaurant_id;
    ArrayList<String> xAxisValues;
    Map<String, List<DownloadPDF.saveFoodData>> map2;
    Map<String, List<DownloadPDF.saveTableData>> mapTable2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.TVGraphAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Graph");

        Spinner SpinnerGraph1 = findViewById(R.id.SpinnerGraph1);
        Spinner SpinnerGraphM1 = findViewById(R.id.SpinnerGraphM1);
        Spinner SpinnerGraph2 = findViewById(R.id.SpinnerGraph2);
        Spinner SpinnerGraphM2 = findViewById(R.id.SpinnerGraphM2);
        Spinner SpinnerGraphData = findViewById(R.id.SpinnerGraphData);
        Button BtnSubmit = findViewById(R.id.BtnSubmit);

        String year[] = TableBookingDatabase.getDatabase(this).bookingDAO().selectYear();
        ArrayAdapter<String> spinnerArrayAdapterYear = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);
        spinnerArrayAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerGraph1.setAdapter(spinnerArrayAdapterYear);
        SpinnerGraph2.setAdapter(spinnerArrayAdapterYear);

        String month[] = {"All", "Jan.", "Feb.", "March", "April", "May", "June", "July", "Aug", "Sept.", "Oct.", "Nov.", "Dec."};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, month);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerGraphM1.setAdapter(spinnerArrayAdapter);
        SpinnerGraphM2.setAdapter(spinnerArrayAdapter);

        String dataType[] = {"Food", "Table"};
        ArrayAdapter<String> spinnerArrayAdapterData = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataType);
        spinnerArrayAdapterData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerGraphData.setAdapter(spinnerArrayAdapterData);

        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurant_id = getIntent().getExtras().getInt("RestaurantID");
                year1 = SpinnerGraph1.getSelectedItem().toString();
                year2 = SpinnerGraph2.getSelectedItem().toString();
                month1 = SpinnerGraphM1.getSelectedItem().toString();
                month2 = SpinnerGraphM2.getSelectedItem().toString();
                data_type = SpinnerGraphData.getSelectedItem().toString();

                GROUP_1_LABEL = year1 + " " + month1;
                GROUP_2_LABEL = year2 + " " + month2;
                chart = findViewById(R.id.GroupedBarChart);
                chart.setVisibility(View.VISIBLE);
                BarData data = createChartData();
                configureChartAppearance();
                prepareChartData(data);
            }
        });
    }

    private void configureChartAppearance() {
        chart.setPinchZoom(true);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setAxisMinimum(0f);

        chart.setExtraOffsets(5f, 10f, 5f, 0f);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setTextSize(15f);
        chart.getXAxis().setAxisMinimum(0);
        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        if("Food".equals(data_type))
            chart.getXAxis().setAxisMaximum(map2.size());
        else if("Table".equals(data_type))
            chart.getXAxis().setAxisMaximum(mapTable2.size());
    }

    private BarData createChartData() {
        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        if ("Food".equals(data_type)) {
            List<DownloadPDF.saveFoodData> foodDataList1 = null;
            if ("All".equals(month1)) {
                foodDataList1 = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, year1);
            } else {
                String tempMonth1 = "";
                switch (month1) {
                    case "Jan.":
                        tempMonth1 = "01";
                        break;
                    case "Feb.":
                        tempMonth1 = "02";
                        break;
                    case "March":
                        tempMonth1 = "03";
                        break;
                    case "April":
                        tempMonth1 = "04";
                        break;
                    case "May":
                        tempMonth1 = "05";
                        break;
                    case "June":
                        tempMonth1 = "06";
                        break;
                    case "July":
                        tempMonth1 = "07";
                        break;
                    case "Aug.":
                        tempMonth1 = "08";
                        break;
                    case "Sept.":
                        tempMonth1 = "09";
                        break;
                    case "Oct.":
                        tempMonth1 = "10";
                        break;
                    case "Nov.":
                        tempMonth1 = "11";
                        break;
                    case "Dec.":
                        tempMonth1 = "12";
                        break;
                }
                foodDataList1 = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, year1, tempMonth1);
            }

            List<DownloadPDF.saveFoodData> foodDataList2 = null;
            if ("All".equals(month2)) {
                foodDataList2 = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, year2);
            } else {
                String tempMonth2 = "";
                switch (month2) {
                    case "Jan.":
                        tempMonth2 = "01";
                        break;
                    case "Feb.":
                        tempMonth2 = "02";
                        break;
                    case "March":
                        tempMonth2 = "03";
                        break;
                    case "April":
                        tempMonth2 = "04";
                        break;
                    case "May":
                        tempMonth2 = "05";
                        break;
                    case "June":
                        tempMonth2 = "06";
                        break;
                    case "July":
                        tempMonth2 = "07";
                        break;
                    case "Aug.":
                        tempMonth2 = "08";
                        break;
                    case "Sept.":
                        tempMonth2 = "09";
                        break;
                    case "Oct.":
                        tempMonth2 = "10";
                        break;
                    case "Nov.":
                        tempMonth2 = "11";
                        break;
                    case "Dec.":
                        tempMonth2 = "12";
                        break;
                }
                foodDataList2 = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, year2, tempMonth2);
            }

            Map<String, List<DownloadPDF.saveFoodData>> map1;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                map1 = foodDataList1.stream().collect(Collectors.groupingBy(m -> m.getMenu_name() == null ? "Not defined" : m.getMenu_name()));
                map2 = foodDataList2.stream().collect(Collectors.groupingBy(m -> m.getMenu_name() == null ? "Not defined" : m.getMenu_name()));

                String arr[] = map2.keySet().toArray(new String[0]);
                for (int i = 0; i < arr.length; i++) {
                    String key = arr[i];
                    List<DownloadPDF.saveFoodData> tempList = new ArrayList<>();
                    tempList.add(new DownloadPDF.saveFoodData());
                    map1.putIfAbsent(key, tempList);
                }
                arr = map1.keySet().toArray(new String[0]);
                for (int i = 0; i < arr.length; i++) {
                    String key = arr[i];
                    List<DownloadPDF.saveFoodData> tempList = new ArrayList<>();
                    tempList.add(new DownloadPDF.saveFoodData());
                    map2.putIfAbsent(key, tempList);
                }

                map1.forEach((k, v) -> map2.merge(k, v, (v1, v2) -> {
                    List<DownloadPDF.saveFoodData> set = new ArrayList<>(v1);
                    set.addAll(v2);
                    return new ArrayList<>(set);
                }));
                xAxisValues = new ArrayList<>(map2.keySet());
                List<List<DownloadPDF.saveFoodData>> dataArr = new ArrayList<>(map2.values());
                for (int i = 0; i < map2.size(); i++) {
                    List<DownloadPDF.saveFoodData> tempList = dataArr.get(i);
                    if (tempList.get(0) != null) {
                        double total1 = tempList.get(0).getTotal();
                        values1.add(new BarEntry(i, (float) total1));
                    } else {
                        values1.add(new BarEntry(i, 0));
                    }

                    if (tempList.get(1) != null) {
                        double total2 = tempList.get(1).getTotal();
                        values2.add(new BarEntry(i, (float) total2));
                    } else {
                        values2.add(new BarEntry(i, 0));
                    }
                }
            }
        } else if ("Table".equals(data_type)) {
            List<DownloadPDF.saveTableData> tableDataList1 = null;
            if ("All".equals(month1)) {
                tableDataList1 = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, year1);
            } else {
                String tempMonth1 = "";
                switch (month1) {
                    case "Jan.":
                        tempMonth1 = "01";
                        break;
                    case "Feb.":
                        tempMonth1 = "02";
                        break;
                    case "March":
                        tempMonth1 = "03";
                        break;
                    case "April":
                        tempMonth1 = "04";
                        break;
                    case "May":
                        tempMonth1 = "05";
                        break;
                    case "June":
                        tempMonth1 = "06";
                        break;
                    case "July":
                        tempMonth1 = "07";
                        break;
                    case "Aug.":
                        tempMonth1 = "08";
                        break;
                    case "Sept.":
                        tempMonth1 = "09";
                        break;
                    case "Oct.":
                        tempMonth1 = "10";
                        break;
                    case "Nov.":
                        tempMonth1 = "11";
                        break;
                    case "Dec.":
                        tempMonth1 = "12";
                        break;
                }
                tableDataList1 = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, year1, tempMonth1);
            }

            List<DownloadPDF.saveTableData> tableDataList2 = null;
            if ("All".equals(month2)) {
                tableDataList2 = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, year2);
            } else {
                String tempMonth2 = "";
                switch (month2) {
                    case "Jan.":
                        tempMonth2 = "01";
                        break;
                    case "Feb.":
                        tempMonth2 = "02";
                        break;
                    case "March":
                        tempMonth2 = "03";
                        break;
                    case "April":
                        tempMonth2 = "04";
                        break;
                    case "May":
                        tempMonth2 = "05";
                        break;
                    case "June":
                        tempMonth2 = "06";
                        break;
                    case "July":
                        tempMonth2 = "07";
                        break;
                    case "Aug.":
                        tempMonth2 = "08";
                        break;
                    case "Sept.":
                        tempMonth2 = "09";
                        break;
                    case "Oct.":
                        tempMonth2 = "10";
                        break;
                    case "Nov.":
                        tempMonth2 = "11";
                        break;
                    case "Dec.":
                        tempMonth2 = "12";
                        break;
                }
                tableDataList2 = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, year2, tempMonth2);
            }

            Map<String, List<DownloadPDF.saveTableData>> map1;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                map1 = tableDataList1.stream().collect(Collectors.groupingBy(m -> m.getName() == null ? "Not defined" : m.getName()));
                mapTable2 = tableDataList2.stream().collect(Collectors.groupingBy(m -> m.getName() == null ? "Not defined" : m.getName()));

                String arr[] = mapTable2.keySet().toArray(new String[0]);
                for (int i = 0; i < arr.length; i++) {
                    String key = arr[i];
                    List<DownloadPDF.saveTableData> tempList = new ArrayList<>();
                    tempList.add(new DownloadPDF.saveTableData());
                    map1.putIfAbsent(key, tempList);
                }
                arr = map1.keySet().toArray(new String[0]);
                for (int i = 0; i < arr.length; i++) {
                    String key = arr[i];
                    List<DownloadPDF.saveTableData> tempList = new ArrayList<>();
                    tempList.add(new DownloadPDF.saveTableData());
                    mapTable2.putIfAbsent(key, tempList);
                }

                map1.forEach((k, v) -> mapTable2.merge(k, v, (v1, v2) -> {
                    List<DownloadPDF.saveTableData> set = new ArrayList<>(v1);
                    set.addAll(v2);
                    return new ArrayList<>(set);
                }));
                xAxisValues = new ArrayList<>(mapTable2.keySet());
                List<List<DownloadPDF.saveTableData>> dataArr = new ArrayList<>(mapTable2.values());
                for (int i = 0; i < mapTable2.size(); i++) {
                    List<DownloadPDF.saveTableData> tempList = dataArr.get(i);
                    if (tempList.get(0) != null) {
                        values1.add(new BarEntry(i, tempList.get(0).getQuantity()));
                    } else {
                        values1.add(new BarEntry(i, 0));
                    }

                    if (tempList.get(1) != null) {
                        values2.add(new BarEntry(i, tempList.get(1).getQuantity()));
                    } else {
                        values2.add(new BarEntry(i, 0));
                    }
                }
            }
        }
        BarDataSet set1 = new BarDataSet(values1, GROUP_1_LABEL);
        BarDataSet set2 = new BarDataSet(values2, GROUP_2_LABEL);

        set1.setColor(Color.parseColor("#C1121F"));
        set2.setColor(Color.BLACK);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);
        BarData data = new BarData(dataSets);

        return data;
    }

    private void prepareChartData(BarData data) {
        chart.setData(data);
        chart.setVisibleXRangeMaximum(2);
        chart.getBarData().setBarWidth(BAR_WIDTH);

        float groupSpace = 1f - ((BAR_SPACE + BAR_WIDTH) * GROUPS);
        chart.groupBars(0, groupSpace, BAR_SPACE);
        chart.getData().setValueTextSize(15f);
        if("Food".equals(data_type))
            chart.getData().setValueFormatter(new DefaultValueFormatter(2));
        else if("Table".equals(data_type))
            chart.getData().setValueFormatter(new DefaultValueFormatter(0));
        chart.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}