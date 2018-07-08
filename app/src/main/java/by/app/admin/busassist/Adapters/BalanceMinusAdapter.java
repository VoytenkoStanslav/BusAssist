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

import by.app.admin.busassist.ConstClass.BalanceMinus;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;
import by.app.admin.busassist.Update.MinusUpdateValue;

/**
 * Created by Admin on 17.04.2018.
 */

public class BalanceMinusAdapter extends RecyclerView.Adapter<BalanceMinusAdapter.ViewHolder> {

    private List<BalanceMinus> mBalanceMinusList;
    private Context mBMContext;
    private RecyclerView mRVBalanceMinus;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView BMDate;
        public TextView BMCategory;
        public TextView BMAmount;
        public TextView BMCommit;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            BMDate = (TextView) v.findViewById(R.id.tvDateBalance);
            BMCategory = (TextView) v.findViewById(R.id.tvCategoryBalance);
            BMAmount = (TextView) v.findViewById(R.id.tvAmountBalance);
            BMCommit = (TextView) v.findViewById(R.id.tvCommitBalance);
        }
    }

    public void add(int position, BalanceMinus balance) {
        mBalanceMinusList.add(position, balance);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mBalanceMinusList.remove(position);
        notifyItemRemoved(position);
    }

    public BalanceMinusAdapter(List<BalanceMinus> myDataset, FragmentActivity context, RecyclerView recyclerView) {
        mBalanceMinusList = myDataset;
        mBMContext = context;
        mRVBalanceMinus = recyclerView;
    }

    public BalanceMinusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_balance, parent, false);
        BalanceMinusAdapter.ViewHolder vh = new BalanceMinusAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(BalanceMinusAdapter.ViewHolder holder, final int position) {

        final BalanceMinus balance_minus = mBalanceMinusList.get(position);
        String date = new SimpleDateFormat("d MMM yyyy").format(new Date(balance_minus.getDate_minus()*1000L));
        String commit = balance_minus.getCommit_minus();
        String am = String.format("%.2f", balance_minus.getAmount_minus());
        holder.BMDate.setText(date);
        holder.BMCategory.setText(balance_minus.getCategory_minus());
        holder.BMAmount.setText(am + " BYN");
        if (commit.isEmpty()){
            holder.BMCommit.setText("Без комментария");
        }
        holder.BMCommit.setText(commit);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mBMContext);
                builder.setTitle("Параметры");
                builder.setMessage("Удалить или изменить расход?");
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(balance_minus.getId_minus());

                    }
                });
                builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mBMContext);
                        dbHelper.deleteBalanceMinus(balance_minus.getId_minus(), mBMContext);

                        mBalanceMinusList.remove(position);
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

    private void goToUpdateActivity(long BalanceMinusId){
        Intent goToUpdate = new Intent(mBMContext, MinusUpdateValue.class);
        goToUpdate.putExtra("BALANCE_MINUS_ID", BalanceMinusId);
        mBMContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mBalanceMinusList.size();
    }

}
