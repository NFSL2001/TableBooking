package wia2007.project.tablebooking;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import wia2007.project.tablebooking.database.TableBookingDatabase;
import wia2007.project.tablebooking.entity.Restaurant;

public class DownloadPDF extends AppCompatActivity {

    String yearSelected = "2023";
    String monthSelected = "January";
    int restaurant_id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_pdf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.TVDownloadPDFAct);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Download PDF");

        Spinner SpinnerSelectYear = findViewById(R.id.SpinnerSelectYear);
        Spinner SpinnerSelectMonth = findViewById(R.id.SpinnerSelectMonth);

        String month[] = {"All", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSelectMonth.setAdapter(spinnerArrayAdapter);

        String year[] = TableBookingDatabase.getDatabase(this).bookingDAO().selectYear();
        ArrayAdapter<String> spinnerArrayAdapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        spinnerArrayAdapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerSelectYear.setAdapter(spinnerArrayAdapterYear);

        Button BtnDownloadPDF = findViewById(R.id.Download);
        BtnDownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    yearSelected = SpinnerSelectYear.getSelectedItem().toString();
                    monthSelected = SpinnerSelectMonth.getSelectedItem().toString();
                    restaurant_id = getIntent().getExtras().getInt("Restaurant_id");
                    if ("All".equals(monthSelected))
                        monthSelected = "";
                    printPDF();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void printPDF() throws IOException {
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();
        String restaurant = createFoodPage(pdfDocument, paint);
        createTablePage(pdfDocument, paint);


        File file = null;
        String name;
        if ("".equals(monthSelected)) {
            name = yearSelected + "_All_" + restaurant + ".pdf";
        } else {
            name = yearSelected + "_" + monthSelected + "_" + restaurant + ".pdf";
        }
        file = new File(this.getExternalFilesDir("/"), name);
        Toast.makeText(this, name + " is successfully saved", Toast.LENGTH_LONG).show();
        pdfDocument.writeTo(new FileOutputStream(file));

        pdfDocument.close();

    }

    private void createTablePage(PdfDocument pdfDocument, Paint paint) {
        List<saveTableData> list = null;
        if ("".equals(monthSelected)) {
            list = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, yearSelected);
        } else {
            String tempMonth = "";
            switch (monthSelected) {
                case "January":
                    tempMonth = "01";
                    break;
                case "February":
                    tempMonth = "02";
                    break;
                case "March":
                    tempMonth = "03";
                    break;
                case "April":
                    tempMonth = "04";
                    break;
                case "May":
                    tempMonth = "05";
                    break;
                case "June":
                    tempMonth = "06";
                    break;
                case "July":
                    tempMonth = "07";
                    break;
                case "August":
                    tempMonth = "08";
                    break;
                case "September":
                    tempMonth = "09";
                    break;
                case "October":
                    tempMonth = "10";
                    break;
                case "November":
                    tempMonth = "11";
                    break;
                case "December":
                    tempMonth = "12";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + monthSelected);
            }
            list = TableBookingDatabase.getDatabase(this).tableDAO().calculateTableBooking(restaurant_id, yearSelected, tempMonth);
        }

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1000, 1500, 2).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();


        paint.setTextSize(30);

        paint.setColor(Color.parseColor("#C1121F"));
        canvas.drawRect(30, 50, 250, 100, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Table: ", 50, 85, paint);

        paint.setColor(Color.parseColor("#C1121F"));
        paint.setTextSize(20);
        canvas.drawText("Only Top 24 Table will be shown.", 30, 140, paint);

        paint.setTextSize(30);
        canvas.drawRect(30, 160, canvas.getWidth() - 30, 210, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText("Table Name", 50, 195, paint);
        canvas.drawText("No.Pax", 450, 195, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Total Booked", canvas.getWidth() - 40, 195, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        paint.setColor(Color.BLACK);
        int y = 195;
        int temp = 25;
        if (list.size() < 24)
            temp = list.size();
        for (int i = 0; i < temp; i++) {
            y += 50;
            canvas.drawText(list.get(i).getName(), 50, y, paint);
            canvas.drawText(Integer.toString(list.get(i).getSize()), 450, y, paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(Integer.toString(list.get(i).getQuantity()), canvas.getWidth() - 40, y, paint);
            paint.setTextAlign(Paint.Align.LEFT);
        }

        paint.setColor(Color.parseColor("#C1121F"));
        canvas.drawRect(30, y + 20, canvas.getWidth() - 30, y + 30, paint);


        pdfDocument.finishPage(page);
    }

    private String createFoodPage(PdfDocument pdfDocument, Paint paint) {
        List<saveFoodData> list = null;
        if ("".equals(monthSelected)) {
            list = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, yearSelected);
        } else {
            String tempMonth = "";
            switch (monthSelected) {
                case "January":
                    tempMonth = "01";
                    break;
                case "February":
                    tempMonth = "02";
                    break;
                case "March":
                    tempMonth = "03";
                    break;
                case "April":
                    tempMonth = "04";
                    break;
                case "May":
                    tempMonth = "05";
                    break;
                case "June":
                    tempMonth = "06";
                    break;
                case "July":
                    tempMonth = "07";
                    break;
                case "August":
                    tempMonth = "08";
                    break;
                case "September":
                    tempMonth = "09";
                    break;
                case "October":
                    tempMonth = "10";
                    break;
                case "November":
                    tempMonth = "11";
                    break;
                case "December":
                    tempMonth = "12";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + monthSelected);
            }
            list = TableBookingDatabase.getDatabase(this).bookingContainMenuDAO().calculateFoodOrder(restaurant_id, yearSelected, tempMonth);
        }

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1000, 1500, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        List<Restaurant> restaurant = TableBookingDatabase.getDatabase(this).restaurantDAO().getRestaurantInfoById(1);
        paint.setTextSize(50);
        canvas.drawText(restaurant.get(0).getRestaurant_name(), 30, 80, paint);

        paint.setTextSize(30);

        canvas.drawText("Date:", 30, 120, paint);
        canvas.drawText(Calendar.getInstance().getTime().toString(), 120, 120, paint);

        paint.setColor(Color.parseColor("#C1121F"));
        canvas.drawRect(30, 150, canvas.getWidth() - 30, 160, paint);

        paint.setColor(Color.BLACK);
        canvas.drawText("Year: ", 50, 200, paint);
        canvas.drawText(yearSelected, 250, 200, paint);

        canvas.drawText("Month: ", 620, 200, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(monthSelected, canvas.getWidth() - 40, 200, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        paint.setColor(Color.parseColor("#C1121F"));
        canvas.drawRect(30, 250, 250, 300, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Food Order: ", 50, 285, paint);

        paint.setColor(Color.parseColor("#C1121F"));
        paint.setTextSize(20);
        canvas.drawText("Only Top 20 Items will be shown.", 30, 340, paint);

        paint.setTextSize(30);
        canvas.drawRect(30, 360, canvas.getWidth() - 30, 410, paint);

        paint.setColor(Color.WHITE);
        canvas.drawText("Item Name", 50, 395, paint);
        canvas.drawText("Price", 450, 395, paint);
        canvas.drawText("Quantity", 600, 395, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("Total Amount", canvas.getWidth() - 40, 395, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        paint.setColor(Color.BLACK);
        int y = 395;
        double totalSum = 0;
        int temp = 21;
        if (list.size() < 20)
            temp = list.size();
        for (int i = 0; i < temp; i++) {
            y += 50;
            double totalAmount = list.get(i).getTotal();
            canvas.drawText(list.get(i).getMenu_name(), 50, y, paint);
            canvas.drawText(String.format("%.2f", list.get(i).getPrice()), 450, y, paint);
            canvas.drawText(Integer.toString(list.get(i).getQuantity()), 600, y, paint);
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(String.format("%.2f", totalAmount), canvas.getWidth() - 40, y, paint);
            paint.setTextAlign(Paint.Align.LEFT);
            totalSum += totalAmount;
        }

        paint.setColor(Color.parseColor("#C1121F"));
        canvas.drawRect(30, y + 20, canvas.getWidth() - 30, y + 30, paint);

        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("TOTAL", 550, y + 60, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(String.format("%.2f", totalSum), canvas.getWidth() - 40, y + 60, paint);
        paint.setTextAlign(Paint.Align.LEFT);

        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        pdfDocument.finishPage(page);

        return restaurant.get(0).getRestaurant_name();
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

    public static class saveFoodData implements Comparable<saveFoodData> {
        String menu_name;
        float price;
        int Quantity;
        double Total;

        public String getMenu_name() {
            return menu_name;
        }

        public void setMenu_name(String menu_name) {
            this.menu_name = menu_name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            this.Quantity = quantity;
        }

        public double getTotal() {
            return Total;
        }

        public void setTotal(double total) {
            this.Total = total;
        }

        @Override
        public int compareTo(saveFoodData saveFoodData) {
            return this.getMenu_name().compareTo(saveFoodData.getMenu_name());
        }
    }

    public static class saveTableData {
        String name;
        int size;
        int Quantity;

        public saveTableData() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getQuantity() {
            return Quantity;
        }

        public void setQuantity(int quantity) {
            Quantity = quantity;
        }
    }
}