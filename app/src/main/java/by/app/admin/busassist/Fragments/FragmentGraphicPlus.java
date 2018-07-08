package by.app.admin.busassist.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 10.05.2018.
 */

public class FragmentGraphicPlus extends Fragment {

    public static final String TABLE_NAME_PLUS = "balance_plus";
    public static final String COLUMN_ID_PLUS = "_id_plus";
    public static final String COLUMN_DATE_PLUS = "date_plus";
    public static final String COLUMN_CATEGORY_PLUS = "category_plus";
    public static final String COLUMN_CATEGORY_POS_PLUS = "category_pos_plus";
    public static final String COLUMN_AMOUNT_PLUS = "amount_plus";
    public static final String COLUMN_COMMIT_PLUS = "commit_plus";

    DBHelper dbHelper;


    TextView tvMin1;
    TextView tvMin2;
    TextView tvMin3;
    TextView tvMin4;
    TextView tvMin5;
    TextView tvMin6;
    TextView tvMin7;
    TextView tvMin8;
    TextView tvMin9;
    TextView tvMin10;
    TextView tvMin11;


    TextView tvGrafikMinus1;
    TextView tvGrafikMinus2;
    TextView tvGrafikMinus3;
    TextView tvGrafikMinus4;
    TextView tvGrafikMinus5;
    TextView tvGrafikMinus6;
    TextView tvGrafikMinus7;
    TextView tvGrafikMinus8;
    TextView tvGrafikMinus9;
    TextView tvGrafikMinus10;
    TextView tvGrafikMinus11;
    TextView tvGrafikMinus12;

    TextView editDateGrafik;

    TextView tvForDateMinus;

    Button updateMinus;

    PieGraph pg;

    Button btnbackMinus;

    int dateGrafikMinus;

    int a = 0;

    int b = 0;

    int year;

    int month;

    public FragmentGraphicPlus() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_graphic, container, false);

        pg = (PieGraph) v.findViewById(R.id.graphMinus);


        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);

        a = DateMonthStart(year, month);

        b = DateMonthFinish(year, month);


        tvMin1 = (TextView) v.findViewById(R.id.tvMin1);
        tvMin1.setBackgroundColor(Color.parseColor("#808080"));
        tvMin2 = (TextView) v.findViewById(R.id.tvMin2);
        tvMin2.setBackgroundColor(Color.parseColor("#800080"));
        tvMin3 = (TextView) v.findViewById(R.id.tvMin3);
        tvMin3.setBackgroundColor(Color.parseColor("#FF0000"));
        tvMin4 = (TextView) v.findViewById(R.id.tvMin4);
        tvMin4.setBackgroundColor(Color.parseColor("#800000"));
        tvMin5 = (TextView) v.findViewById(R.id.tvMin5);
        tvMin5.setBackgroundColor(Color.parseColor("#FFD700"));
        tvMin6 = (TextView) v.findViewById(R.id.tvMin6);
        tvMin6.setBackgroundColor(Color.parseColor("#808000"));
        tvMin7 = (TextView) v.findViewById(R.id.tvMin7);
        tvMin7.setBackgroundColor(Color.parseColor("#006400"));
        tvMin8 = (TextView) v.findViewById(R.id.tvMin8);
        tvMin8.setBackgroundColor(Color.parseColor("#8B4513"));
        tvMin9 = (TextView) v.findViewById(R.id.tvMin9);
        tvMin9.setBackgroundColor(Color.parseColor("#48D1CC"));
        tvMin10 = (TextView) v.findViewById(R.id.tvMin10);
        tvMin10.setBackgroundColor(Color.parseColor("#191970"));
        tvMin11 = (TextView) v.findViewById(R.id.tvMin11);
        tvMin11.setBackgroundColor(Color.parseColor("#7FFF00"));

        tvGrafikMinus1 = (TextView) v.findViewById(R.id.tvGrafikMinus1);
        tvGrafikMinus2 = (TextView) v.findViewById(R.id.tvGrafikMinus2);
        tvGrafikMinus3 = (TextView) v.findViewById(R.id.tvGrafikMinus3);
        tvGrafikMinus4 = (TextView) v.findViewById(R.id.tvGrafikMinus4);
        tvGrafikMinus5 = (TextView) v.findViewById(R.id.tvGrafikMinus5);
        tvGrafikMinus6 = (TextView) v.findViewById(R.id.tvGrafikMinus6);
        tvGrafikMinus7 = (TextView) v.findViewById(R.id.tvGrafikMinus7);
        tvGrafikMinus8 = (TextView) v.findViewById(R.id.tvGrafikMinus8);
        tvGrafikMinus9 = (TextView) v.findViewById(R.id.tvGrafikMinus9);
        tvGrafikMinus10 = (TextView) v.findViewById(R.id.tvGrafikMinus10);
        tvGrafikMinus11 = (TextView) v.findViewById(R.id.tvGrafikMinus11);
        tvGrafikMinus12 = (TextView) v.findViewById(R.id.tvGrafikMinus12);


        tvForDateMinus = (TextView) v.findViewById(R.id.tvForDateMinus);

        btnbackMinus = (Button) v.findViewById(R.id.btnBackMinus);
        btnbackMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myPieGraphNull();

                month = month - 1;
                if(month == -1){
                    year = year - 1;
                    month = 11;
                }
                a = DateMonthStart(year, month);
                b = DateMonthFinish(year, month);
                Log.e("LooooogAonClick-", String.valueOf(a));
                Log.e("LooooogBonClick-", String.valueOf(b));
                myPieGraf(a, b);
            }
        });


        editDateGrafik = (TextView) v.findViewById(R.id.tvDateGraficMinus);



        updateMinus = (Button) v.findViewById(R.id.btnUpdateMinus);
        updateMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myPieGraphNull();

                month = month + 1;
                if(month == 12){
                    year = year + 1;
                    month = 0;
                }
                a = DateMonthStart(year, month);
                b = DateMonthFinish(year, month);
                Log.e("LooooogAonClick+", String.valueOf(a));
                Log.e("LooooogBonClick+", String.valueOf(b));
                myPieGraf(a, b);
            }
        });

        myPieGraf(a, b);
        return v;
    }

    public float getSumValue(long id, int a, int b){

        float d;
        float i = 0;

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT " + COLUMN_CATEGORY_POS_PLUS + ", " + COLUMN_DATE_PLUS + ", " +
                COLUMN_AMOUNT_PLUS + " FROM " + TABLE_NAME_PLUS +
                " WHERE category_pos_plus=" + id + " AND " + COLUMN_DATE_PLUS +
                " BETWEEN " + a + " AND " + b;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                d = cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT_PLUS));
                i = i + d;
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return i;
    }


    int DateMonthStart(int year, int month) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    int DateMonthFinish(int year, int month) {

        Calendar c = Calendar.getInstance();
        if (month == 11) {
            month = -1;
            year = year +1;
        }
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, (month + 1));
        c.set(Calendar.DAY_OF_MONTH, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    public void myPieGraf(int a, int b){

        tvMin1.setVisibility(View.VISIBLE);
        tvGrafikMinus1.setVisibility(View.VISIBLE);
        tvMin2.setVisibility(View.VISIBLE);
        tvGrafikMinus2.setVisibility(View.VISIBLE);
        tvMin3.setVisibility(View.VISIBLE);
        tvGrafikMinus3.setVisibility(View.VISIBLE);
        tvMin4.setVisibility(View.VISIBLE);
        tvGrafikMinus4.setVisibility(View.VISIBLE);
        tvMin5.setVisibility(View.VISIBLE);
        tvGrafikMinus5.setVisibility(View.VISIBLE);
        tvMin6.setVisibility(View.VISIBLE);
        tvGrafikMinus6.setVisibility(View.VISIBLE);
        tvMin7.setVisibility(View.GONE);
        tvGrafikMinus7.setVisibility(View.GONE);
        tvMin8.setVisibility(View.GONE);
        tvGrafikMinus8.setVisibility(View.GONE);
        tvMin9.setVisibility(View.GONE);
        tvGrafikMinus9.setVisibility(View.GONE);
        tvMin10.setVisibility(View.GONE);
        tvGrafikMinus10.setVisibility(View.GONE);
        tvMin11.setVisibility(View.GONE);
        tvGrafikMinus11.setVisibility(View.GONE);


        Log.e("LooooogAmyPie", String.valueOf(a));
        Log.e("LooooogBmyPie", String.valueOf(b));

        float a1 = getSumValue(1, a, b);
        float a2 = getSumValue(2, a, b);
        float a3 = getSumValue(3, a, b);
        float a4 = getSumValue(4, a, b);
        float a5 = getSumValue(5, a, b);
        float a6 = getSumValue(6, a, b);

        if (a1 == 0){

            tvMin1.setVisibility(View.GONE);
            tvGrafikMinus1.setVisibility(View.GONE);

        } if (a2 == 0){

            tvMin2.setVisibility(View.GONE);
            tvGrafikMinus2.setVisibility(View.GONE);

        } if (a3 == 0){

            tvMin3.setVisibility(View.GONE);
            tvGrafikMinus3.setVisibility(View.GONE);

        } if (a4 == 0){

            tvMin4.setVisibility(View.GONE);
            tvGrafikMinus4.setVisibility(View.GONE);

        } if (a5 == 0){

            tvMin5.setVisibility(View.GONE);
            tvGrafikMinus5.setVisibility(View.GONE);

        } if (a6 == 0){

            tvMin6.setVisibility(View.GONE);
            tvGrafikMinus6.setVisibility(View.GONE);

        }

        float allSum = a1 + a2 + a3 + a4 + a5 + a6;

        if (allSum == 0){
            tvGrafikMinus12.setVisibility(View.VISIBLE);
            tvGrafikMinus12.setText("Доходов за месяц нет");
            tvGrafikMinus12.setPaintFlags(tvGrafikMinus12.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            tvGrafikMinus12.setText("Сумма доходов за месяц: " + String.format("%.2f",allSum) + " BYN");
            tvGrafikMinus12.setPaintFlags(tvGrafikMinus12.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }


        int b1 = (int) (a1/allSum*100);
        int b2 = (int) (a2/allSum*100);
        int b3 = (int) (a3/allSum*100);
        int b4 = (int) (a4/allSum*100);
        int b5 = (int) (a5/allSum*100);
        int b6 = (int) (a6/allSum*100);


        SimpleDateFormat sdfd = new SimpleDateFormat("MMM");
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
        String date = sdfd.format(new Date((a)*1000L));
        String dateYear = sdfy.format(new Date((a)*1000L));

        if (date.equals("янв.")){
            date = "Январь";
        } if (date.equals("февр.")){
            date = "Февраль";
        } if (date.equals("мар.")){
            date = "Март";
        } if (date.equals("апр.")){
            date = "Апрель";
        } if (date.equals("мая")){
            date = "Май";
        } if (date.equals("июн.")){
            date = "Июнь";
        } if (date.equals("июл.")){
            date = "Июль";
        } if (date.equals("авг.")){
            date = "Август";
        } if (date.equals("сент.")){
            date = "Сентябрь";
        } if (date.equals("окт.")){
            date = "Октябрь";
        } if (date.equals("нояб.")){
            date = "Ноябрь";
        } if (date.equals("дек.")){
            date = "Декабрь";
        }
        editDateGrafik.setText(date + " " + dateYear);


        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#808080"));
        slice.setValue(a1/allSum);
        pg.addSlice(slice);
        tvGrafikMinus1.setText(" - Предоплата за проект | " + String.format("%.2f",a1) + " BYN, " + b1 + "%");
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#800080"));
        slice.setValue(a2/allSum);
        pg.addSlice(slice);
        tvGrafikMinus2.setText(" - Конечный расчет за проект | " + String.format("%.2f",a2) + " BYN, " + b2 + "%");
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FF0000"));
        slice.setValue(a3/allSum);
        pg.addSlice(slice);
        tvGrafikMinus3.setText(" - Оплата проекта по дог. | " + String.format("%.2f",a3) + " BYN, " + b3 + "%");
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#800000"));
        slice.setValue(a4/allSum);
        pg.addSlice(slice);
        tvGrafikMinus4.setText(" - Доход от своих прилож. | " + String.format("%.2f",a4) + " BYN, " + b4 + "%");
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFD700"));
        slice.setValue(a5/allSum);
        pg.addSlice(slice);
        tvGrafikMinus5.setText(" - Помощь инвестора | " + String.format("%.2f",a5) + " BYN, " + b5 + "%");
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#808000"));
        slice.setValue(a6/allSum);
        pg.addSlice(slice);
        tvGrafikMinus6.setText(" - Прочие доходы " + String.format("%.2f",a6) + " BYN, " + b6 + "%");



    }

    public void myPieGraphNull(){
        pg.removeSlices();
    }

}
