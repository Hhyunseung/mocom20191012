package com.cookandroid.registerloginexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{

    private ArrayList<TodoItem> mTodoItems;
    private Context mContext;
    private DBHelper mDBHelper;

    public CustomAdapter(ArrayList<TodoItem> mTodoItems, Context mContext) {
        this.mTodoItems = mTodoItems;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.tv_title.setText(mTodoItems.get(position).getTitle());
        holder.tv_content.setText(mTodoItems.get(position).getContent());
        holder.tv_writeDate.setText(mTodoItems.get(position).getWriteDate());
        holder.tv_spe.setText(mTodoItems.get(position).getSpe());
        holder.tv_note.setText(mTodoItems.get(position).getNote());
    }

    @Override
    public int getItemCount()
    {
        return mTodoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_writeDate;
        private TextView tv_spe;
        private TextView tv_note;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_writeDate = itemView.findViewById(R.id.tv_date);
            tv_spe = itemView.findViewById(R.id.tv_spe);
            tv_note = itemView.findViewById(R.id.tv_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int curPos = getAdapterPosition(); // 현재 리스트 아이템 위치
                    TodoItem todoItem = mTodoItems.get(curPos);

                    String[] strChoiceItems = {"수정하기", "삭제하기"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("원하는 작업을 선택 해주세요");
                    builder.setItems(strChoiceItems, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int position)
                        {
                            if(position == 0) {
                                //수정하기
                                //팝업 창 띄우기
                                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                                dialog.setContentView(R.layout.dialog_edit);
                                EditText et_title = dialog.findViewById(R.id.et_title);
                                EditText et_content = dialog.findViewById(R.id.et_content);
                                EditText et_spe = dialog.findViewById(R.id.et_spe);
                                EditText et_note = dialog.findViewById(R.id.et_note);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        //update database
                                        String title = et_title.getText().toString();
                                        String content = et_content.getText().toString();
                                        String spe = et_spe.getText().toString();
                                        String note = et_note.getText().toString();
                                        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); //현재 시간 받아오기
                                        String beforeTime = todoItem.getWriteDate();

                                        mDBHelper.UpdateTodo(title, content, spe, note, currentTime, beforeTime);

                                        // update UI
                                        todoItem.setTitle(title);
                                        todoItem.setContent(content);
                                        todoItem.setSpe(spe);
                                        todoItem.setNote(note);
                                        todoItem.setWriteDate(currentTime);
                                        notifyItemChanged(curPos, todoItem);
                                        dialog.dismiss();
                                        Toast.makeText(mContext, "목록 수정이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                dialog.show();
                                }
                                else if(position == 1) {
                                    // 테이블 삭제하기
                                    String beforeTime = todoItem.getWriteDate();
                                    mDBHelper.deleteTodo(beforeTime);

                                    // delete UI
                                    mTodoItems.remove(curPos);
                                    notifyItemRemoved(curPos);
                                    Toast.makeText(mContext, "목록이 제거되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                    builder.show();
                }
            });

        }

    }
    // 액티비티에서 호출되는 함수이며, 현재 어댐터에 새로운 게시글 아이템을 전달받아 추가하는 목적
    public void addItem(TodoItem _item) {
        mTodoItems.add(0, _item);
        notifyItemInserted(0);
    }

}
