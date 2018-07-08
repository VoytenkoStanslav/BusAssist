package by.app.admin.busassist.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import by.app.admin.busassist.ActivityAddAndReport.ActivityAddProject;
import by.app.admin.busassist.ConstClass.Colleagues;
import by.app.admin.busassist.R;

/**
 * Created by Admin on 05.05.2018.
 */

public class ColleaguesForProjectAdapter extends RecyclerView.Adapter<ColleaguesForProjectAdapter.ViewHolder> {

    private List<Colleagues> mColleagList;
    private Context mContext;
    private RecyclerView mRVColleag;
    String path = null;
    long idClient;
    int posProject;
    int time;
    String myName;
    long update;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageViewColl;
        public TextView tvNameColl;
        public TextView tvNumberColl;
        public TextView tvSkypeColl;
        public TextView tvCommitColl;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageViewColl = (ImageView) v.findViewById(R.id.imageViewColl);
            tvNameColl = (TextView) v.findViewById(R.id.tvNameColl);
            tvNumberColl = (TextView) v.findViewById(R.id.tvNumberColl);
            tvSkypeColl = (TextView) v.findViewById(R.id.tvSkypeColl);
            tvCommitColl = (TextView) v.findViewById(R.id.tvCommitColl);
        }

    }

    //public void setClientId(long id){
    //    this.id = id;
    //}

    public void add(int position, Colleagues colleagues) {
        mColleagList.add(position, colleagues);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mColleagList.remove(position);
        notifyItemRemoved(position);
    }

    public ColleaguesForProjectAdapter(List<Colleagues> myDataset, FragmentActivity context, RecyclerView recyclerView,
                                       long id, int pos, int time, String myName, long update) {
        mColleagList = myDataset;
        mContext = context;
        mRVColleag = recyclerView;
        this.idClient = id;
        this.posProject = pos;
        this.time = time;
        this.myName = myName;
        this.update = update;
    }

    public ColleaguesForProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_colleagues, parent, false);
        ColleaguesForProjectAdapter.ViewHolder vh = new ColleaguesForProjectAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ColleaguesForProjectAdapter.ViewHolder holder, final int position) {


        final Colleagues colleagues = mColleagList.get(position);
        //Uri selectedImageNew = Uri.parse(colleagues.getImage_uri());
        //Picasso.get().load(selectedImageNew).into(holder.imageViewColl);
        //holder.imageViewColl.setImageURI(selectedImageNew);

        //path = colleagues.getImage_uri();
        //Bitmap bitmap = decodeSampledBitmapFromResource(path, 80, 100);
        //holder.imageViewColl.setImageBitmap(bitmap);

        //MyTask mt = new MyTask();
        //mt.execute();

        new ColleaguesForProjectAdapter.LoadImageTask(holder.imageViewColl).execute(position);
        holder.tvNameColl.setText("Имя:        " + colleagues.getName_colleag());
        holder.tvNumberColl.setText("Номер:   " + colleagues.getNumber_colleag());
        String skype = colleagues.getSkype_colleag();
        holder.tvSkypeColl.setText("Skype:     " + skype);
        String commit = colleagues.getCommit_colleag();
        holder.tvCommitColl.setText(commit);


        if (skype.isEmpty()) {
            holder.tvSkypeColl.setText("Skype:     " + "Не указан");
        }
        if (commit.isEmpty()) {
            holder.tvCommitColl.setText("Без комментария");
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Подтверждение");
                builder.setMessage("Выбрать " + colleagues.getName_colleag() + "?");
                builder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(colleagues.getId_colleag());

                    }
                });
                builder.setNeutralButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private class LoadImageTask extends AsyncTask<Integer, Void, Bitmap> {

        ImageView imageViewColl;

        public LoadImageTask(ImageView imageViewColl) {
            this.imageViewColl = imageViewColl;
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {

            Bitmap bitmap = null;

            Colleagues coll = new Colleagues();


            final Colleagues colleagues = mColleagList.get(params[0]);
            try {
                String path = colleagues.getImage_uri();
                File file = new File(path);
                if (file.exists()) {
                    if (path == "null") {
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                R.mipmap.sotrudnik);
                    } else {
                        bitmap = decodeSampledBitmapFromResource(path, 80, 100);
                    }
                } else {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                            R.mipmap.sotrudnik);
                }
            } catch (NullPointerException e){
                bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                        R.mipmap.sotrudnik);
            }

            //Uri selectedImageNew = Uri.parse(colleagues.getImage_uri());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageViewColl.setImageBitmap(bitmap);
        }
    }


    private void goToUpdateActivity(long colleagId) {
        Intent goToUpdate = new Intent(mContext, ActivityAddProject.class);
        goToUpdate.putExtra("COLLEAGUES_ID_PROJECT", colleagId);
        goToUpdate.putExtra("CLIENT_ID_PROJECT", idClient);
        goToUpdate.putExtra("POS_PROJECT", posProject);
        goToUpdate.putExtra("TIME_PROJECT", time);
        goToUpdate.putExtra("NAME_MY_PROJECT", myName);
        goToUpdate.putExtra("PROJECT_ID_FOR_UPDATE", update);
        mContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mColleagList.size();
    }
}
