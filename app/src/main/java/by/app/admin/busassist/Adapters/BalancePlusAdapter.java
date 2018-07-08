package by.app.admin.busassist.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import by.app.admin.busassist.ConstClass.BalancePlus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;
import by.app.admin.busassist.Update.PlusUpdateValue;

/**
 * Created by Admin on 18.04.2018.
 */

public class BalancePlusAdapter  extends RecyclerView.Adapter<BalancePlusAdapter.ViewHolder> {

    private List<BalancePlus> mBalancePlusList;
    private Context mBPContext;
    private RecyclerView mRVBalancePlus;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView BPDate;
        public TextView BPCategory;
        public TextView BPAmount;
        public TextView BPCommit;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            BPDate = (TextView) v.findViewById(R.id.tvDateBalance);
            BPCategory = (TextView) v.findViewById(R.id.tvCategoryBalance);
            BPAmount = (TextView) v.findViewById(R.id.tvAmountBalance);
            BPCommit = (TextView) v.findViewById(R.id.tvCommitBalance);
        }
    }

    public void add(int position, BalancePlus balance) {
        mBalancePlusList.add(position, balance);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mBalancePlusList.remove(position);
        notifyItemRemoved(position);
    }

    public BalancePlusAdapter(List<BalancePlus> myDataset, FragmentActivity context, RecyclerView recyclerView) {
        mBalancePlusList = myDataset;
        mBPContext = context;
        mRVBalancePlus = recyclerView;
    }

    public BalancePlusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_balance, parent, false);
        BalancePlusAdapter.ViewHolder vh = new BalancePlusAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(BalancePlusAdapter.ViewHolder holder, final int position) {

        final BalancePlus balance = mBalancePlusList.get(position);
        //Integer OutDate = Integer.parseInt(schedule.getDate_Time());
        //SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        //tvDate.setText(sdf.format(new Date(System.currentTimeMillis())));
        String date = new SimpleDateFormat("d MMM yyyy").format(new Date(balance.getDate_plus()*1000L));
        //String year = new SimpleDateFormat("yyyy").format(new Date(balance.getDate_minus()*1000L));
        //String time = new SimpleDateFormat("HH:mm").format(new Date(schedule.getDate_Time()*1000L));
        //String check_time = schedule.getCheck_time();
        //holder.scheduleDate.setText(" " + date.format(new Date(schedule.getDate_Time()*1000)));
        String commit = balance.getCommit_plus();
        holder.BPDate.setText(date);
        holder.BPCategory.setText(balance.getCategory_plus());
        holder.BPAmount.setText(balance.getAmount_plus() + " BYN");
        if (commit.isEmpty()){
            holder.BPCommit.setText("Без комментария");
        }
        holder.BPCommit.setText(commit);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mBPContext);
                builder.setTitle("Параметры");
                builder.setMessage("Удалить или изменить доход?");
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(balance.getId_plus());

                    }
                });
                builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mBPContext);
                        dbHelper.deleteBalancePlus(balance.getId_plus(), mBPContext);

                        mBalancePlusList.remove(position);
                        //mRVSchedule.removeViewAt(position);
                        //notifyItemRemoved(position);
                        //notifyItemRangeChanged(position, mScheduleList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void goToUpdateActivity(long BalancePlusId){
        Intent goToUpdate = new Intent(mBPContext, PlusUpdateValue.class);
        goToUpdate.putExtra("BALANCE_PLUS_ID", BalancePlusId);
        mBPContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mBalancePlusList.size();
    }

}
