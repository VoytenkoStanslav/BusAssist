package by.app.admin.busassist.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.app.admin.busassist.ActivityAddAndReport.ActivityAddProject;
import by.app.admin.busassist.ConstClass.Project;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 06.05.2018.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder>{

    private List<Project> mProjectList;
    private Context mContext;
    private RecyclerView mRVProject;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvNameProject;
        public TextView tvCompanyProject;
        public TextView tvResponsibleProject;
        public TextView tvFinishProject;
        public TextView tvFinishDay;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvNameProject = (TextView) v.findViewById(R.id.tvNameProjectSingle);
            tvCompanyProject = (TextView) v.findViewById(R.id.tvCompanyProjectSingle);
            tvResponsibleProject = (TextView) v.findViewById(R.id.tvResponsibleSingle);
            tvFinishProject = (TextView) v.findViewById(R.id.tvFinishProjectSingle);
            tvFinishDay = (TextView) v.findViewById(R.id.tvFinishDaySingle);
        }
    }

    public void add(int position, Project project) {
        mProjectList.add(position, project);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mProjectList.remove(position);
        notifyItemRemoved(position);
    }

    public ProjectAdapter(List<Project> myDataset, FragmentActivity context, RecyclerView recyclerView) {
        mProjectList = myDataset;
        mContext = context;
        mRVProject = recyclerView;
    }

    public ProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_project, parent, false);
        ProjectAdapter.ViewHolder vh = new ProjectAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ProjectAdapter.ViewHolder holder, final int position) {


        final Project project = mProjectList.get(position);

        String name = project.getName_project();
        Integer pos = project.getType_pos_project();
        String type = project.getType_project();
        String company = project.getCompany_project();
        String respons = project.getResponsible_for_project();
        Integer finish = project.getDate_finish_project();

        if (pos == 1) {
            holder.tvNameProject.setText(name);
            holder.tvCompanyProject.setText("Заказчик:             | " + company);
            if (respons.isEmpty()){
                holder.tvResponsibleProject.setText("Ответственный: | Не указан");
            } else{
                holder.tvResponsibleProject.setText("Ответственный: | " + respons);
            }
            String date = new SimpleDateFormat("dd MMM yyyy").format(new Date(finish * 1000L));
            holder.tvFinishProject.setText("Дата сдачи:         | " + date);
            Integer today = componentDateToday();
            Integer restOfDay = (finish - today) / 86400;
            holder.tvFinishDay.setText("Осталось " + restOfDay + " дней");
        }

        if (pos == 2){
            holder.tvNameProject.setText(name);
            holder.tvCompanyProject.setVisibility(View.GONE);
            if (respons.isEmpty()){
                holder.tvResponsibleProject.setText("Ответственный: | Не указан");
            } else{
                holder.tvResponsibleProject.setText("Ответственный: | " + respons);
            }
            String date = new SimpleDateFormat("dd MMM yyyy").format(new Date(finish * 1000L));
            holder.tvFinishProject.setText("Дата сдачи:         | " + date);
            Integer today = componentDateToday();
            Integer restOfDay = (finish - today) / 86400;
            if (restOfDay >= 0){
                if (restOfDay == 0){
                    holder.tvFinishDay.setText("День сдачи проекта");
                } else {
                    holder.tvFinishDay.setText("Осталось " + restOfDay + " дней");
                }
            } else {
                holder.tvFinishDay.setText("Время истекло");
            }
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Параметры");
                builder.setMessage("Удалить или изменить задачу?");
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(project.getId_project());
                        Log.e("Id", String.valueOf(project.getId_project()));

                    }
                });
                builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.deleteProject(project.getId_project(), mContext);

                        mProjectList.remove(position);
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

    int componentDateToday() {

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return (int) (c.getTimeInMillis() / 1000);

    }

    private void goToUpdateActivity(long projectId) {
        Intent goToUpdate = new Intent(mContext, ActivityAddProject.class);
        Log.e("Id_go", String.valueOf(projectId));
        goToUpdate.putExtra("PROJECT_ID_FOR_UPDATE", projectId);
        mContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mProjectList.size();
    }
}
