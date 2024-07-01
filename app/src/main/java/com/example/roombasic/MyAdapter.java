package com.example.roombasic;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ListAdapter<Word,MyAdapter.MyViewHolder> {

   private boolean useCardView;
   private WordViewModel wordViewModel;

   MyAdapter(boolean useCardView,WordViewModel wordViewModel) {
     //Diffutil是处理两个列表的差别的，差异化处理的一个回调
       super(new DiffUtil.ItemCallback<Word>() {
           @Override
           public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
               return oldItem.getId()==newItem.getId();
           }

           @Override
           public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
               return (oldItem.getWord().equals(newItem.getWord())
               &&oldItem.getChineseMeaning().equals(newItem.getChineseMeaning())
               &&oldItem.isChineseInvisible()== newItem.isChineseInvisible());
           }
       });
        this.useCardView = useCardView;
        this.wordViewModel=wordViewModel;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View itemView;
        if (useCardView){
            itemView=layoutInflater.inflate(R.layout.cell_card_2,parent,false);
        }else {
            itemView=layoutInflater.inflate(R.layout.cell_normal_2,parent,false);
        }
       final MyViewHolder holder=new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri=Uri.parse("https://m.youdao.com/dict?le=eng&q="+holder.textViewEnglish.getText());
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitchChineseInvisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //gettag获得的类型要类型转换
                    Word word=(Word) holder.itemView.getTag(R.id.word_for_view_holder);
                        if (isChecked){
                    holder.textViewChinese.setVisibility(View.GONE);
                    word.setChineseInvisible(true);
                    wordViewModel.updateWords(word);
                }else {
                    holder.textViewChinese.setVisibility(View.VISIBLE);
                    word.setChineseInvisible(false);
                    wordViewModel.updateWords(word);
                }
            }
        });

        return holder;

    }
//之前将监听器放在了onbindviewholder里面会被多次呼叫，会有性能上的问题有多的开销，应该放在oncreate里面因为创建只创建一次
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Word word=getItem(position);
        holder.itemView.setTag(R.id.word_for_view_holder,word);//在values中创建ids， 那么在其他地方用这个setTag保证大家不会冲突
        holder.textViewNumber.setText(String.valueOf(position+1));
        holder.textViewEnglish.setText(word.getWord());
        holder.textViewChinese.setText(word.getChineseMeaning());

        if (word.isChineseInvisible()){
            holder.textViewChinese.setVisibility(View.GONE);
            holder.aSwitchChineseInvisible.setChecked(true);
        }else {
            holder.textViewChinese.setVisibility(View.VISIBLE);
            holder.aSwitchChineseInvisible.setChecked(false);
        }


    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.textViewNumber.setText(String.valueOf(holder.getAdapterPosition()+1));
    }

    //内部内一般都要加一个static防止内存泄露
   static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNumber,textViewEnglish,textViewChinese;
        Switch aSwitchChineseInvisible;
         MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber=itemView.findViewById(R.id.textViewNumber);
            textViewEnglish=itemView.findViewById(R.id.textViewEnglish);
            textViewChinese=itemView.findViewById(R.id.textViewChinese);
            aSwitchChineseInvisible=itemView.findViewById(R.id.switchChineseInvisible);
        }
    }
}
