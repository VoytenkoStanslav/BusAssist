package by.app.admin.busassist.Adapters;

import android.annotation.SuppressLint;
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

import by.app.admin.busassist.ConstClass.Client;
import by.app.admin.busassist.DBHelper;
import by.app.admin.busassist.R;
import by.app.admin.busassist.Update.ClientUpdateValue;

/**
 * Created by Admin on 26.04.2018.
 */

public class ClientAdapter  extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{

    private List<Client> mClientList;
    private Context mContext;
    private RecyclerView mRVClient;
    String path = null;
    Integer zacaz;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView imageViewClient;
        public TextView tvNameCompany;
        public TextView tvNameClient;
        public TextView tvNumberClient;
        public TextView tvSkypeClient;
        public TextView tvProjectClient;
        public TextView tvCommitClient;
        public View viewTop;
        public View viewBottom;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            imageViewClient = (ImageView) v.findViewById(R.id.imageViewClient);
            tvNameCompany = (TextView) v.findViewById(R.id.tvNameCompany);
            tvNameClient = (TextView) v.findViewById(R.id.tvNameClient);
            tvNumberClient = (TextView) v.findViewById(R.id.tvNumberClient);
            tvSkypeClient = (TextView) v.findViewById(R.id.tvSkypeClient);
            tvProjectClient = (TextView) v.findViewById(R.id.tvProjectClient);
            tvCommitClient = (TextView) v.findViewById(R.id.tvCommitClient);
            viewTop = (View) v.findViewById(R.id.viewPrTop);
            viewBottom = (View) v.findViewById(R.id.viewPrBottom);
        }

    }

    public void add(int position, Client client) {
        mClientList.add(position, client);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mClientList.remove(position);
        notifyItemRemoved(position);
    }

    public ClientAdapter(List<Client> myDataset, FragmentActivity context, RecyclerView recyclerView) {
        mClientList = myDataset;
        mContext = context;
        mRVClient = recyclerView;
    }

    public ClientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.single_row_client, parent, false);
        ClientAdapter.ViewHolder vh = new ClientAdapter.ViewHolder(v);
        return vh;
    }

    public void onBindViewHolder(ClientAdapter.ViewHolder holder, final int position) {

        final Client client = mClientList.get(position);

        Bitmap bitmap;

        zacaz = client.getType_pos_client();
        new ClientAdapter.LoadImageTask(holder.imageViewClient).execute(position);
        if (zacaz == 2) {
            holder.viewTop.setVisibility(View.VISIBLE);
            holder.tvNameCompany.setVisibility(View.VISIBLE);
            holder.tvNameCompany.setText(client.getCompany_client());
            holder.viewBottom.setVisibility(View.VISIBLE);
        }

        holder.tvNameClient.setText("Имя:        " + client.getName_client());
        holder.tvNumberClient.setText("Номер:   " + client.getNumber_client());
        String skype = client.getSkype_client();
        holder.tvSkypeClient.setText("Skype:     " + skype);
        holder.tvProjectClient.setText("Проект:     " + client.getProject_client());
        String commit = client.getCommit_client();
        holder.tvCommitClient.setText(commit);



        if (skype.isEmpty()) {
            holder.tvSkypeClient.setText("Skype:     " + "Не указан");
        }
        if (commit.isEmpty()) {
            holder.tvCommitClient.setText("Без комментария");
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
                        goToUpdateActivity(client.getId_client());

                    }
                });
                builder.setNeutralButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.deleteClient(client.getId_client(), mContext);

                        mClientList.remove(position);
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

        ImageView imageViewClient;

        public LoadImageTask(ImageView imageViewClient) {
            this.imageViewClient = imageViewClient;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected Bitmap doInBackground(Integer... params){

            Bitmap bitmap = null;

            final Client client = mClientList.get(params[0]);
            try{
                String path = client.getImage_path_client();
                File file = new File(path);
                if (file.exists()) {
                    if (path.equals("null")) {
                        if (zacaz == 2) {
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                    R.mipmap.client_company);
                        } else {
                            bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                    R.mipmap.zakazchik);
                        }
                    } else {
                        bitmap = decodeSampledBitmapFromResource(path, 80, 100);
                    }
                }else {
                    if (zacaz == 2) {
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                R.mipmap.client_company);
                    } else {
                        bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                                R.mipmap.zakazchik);
                    }
                }
            } catch (NullPointerException e) {
                if (zacaz == 2) {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                            R.mipmap.client_company);
                } else {
                    bitmap = BitmapFactory.decodeResource(mContext.getResources(),
                            R.mipmap.zakazchik);
                }
            }
            //Uri selectedImageNew = Uri.parse(colleagues.getImage_uri());
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageViewClient.setImageBitmap(bitmap);
        }
    }


    private void goToUpdateActivity(long clientId) {
        Intent goToUpdate = new Intent(mContext, ClientUpdateValue.class);
        goToUpdate.putExtra("CLIENT_ID", clientId);
        mContext.startActivity(goToUpdate);
    }

    public int getItemCount() {
        return mClientList.size();
    }
}
