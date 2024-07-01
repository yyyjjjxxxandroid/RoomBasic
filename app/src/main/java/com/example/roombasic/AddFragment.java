package com.example.roombasic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class AddFragment extends Fragment {

    private Button buttonSubmit;
    private EditText editTextEnglish,editTextChinese;
    private WordViewModel wordViewModel;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wordViewModel= new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        buttonSubmit=requireView().findViewById(R.id.buttonSubmit);

        editTextEnglish=requireView().findViewById(R.id.editTextEnglish);
        editTextChinese=requireView().findViewById(R.id.editTextChinese);
        buttonSubmit.setEnabled(false);
        editTextEnglish.requestFocus();
        InputMethodManager imm=(InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextEnglish,0);

        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String english=editTextEnglish.getText().toString().trim();
                String chinese=editTextChinese.getText().toString().trim();
                buttonSubmit.setEnabled(!english.isEmpty()&&!chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextEnglish.addTextChangedListener(textWatcher);
        editTextChinese.addTextChangedListener(textWatcher);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english=editTextEnglish.getText().toString().trim();
                String chinese=editTextChinese.getText().toString().trim();
                Word word=new Word(english,chinese);
                wordViewModel.insertWords(word);
                NavController navController= Navigation.findNavController(v);
                navController.navigateUp();
                //requireActivity() 是在Fragment中使用的方法，它确保当前Fragment已经附加到了一个Activity上，并安全地返回那个Activity的实例。
                InputMethodManager imm=(InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
               //v.getWindowToken() 获取了触发这个操作的视图（View）的窗口令牌（window token）。窗口令牌是一个与窗口关联的唯一标识符，用于安全操作，如这里的隐藏软键盘请求。
                //第二个参数0 是一个操作标志，用于指定隐藏输入法的方式。在这里使用0是遵循默认行为，即直接尝试隐藏输入法，不带任何特定的额外指令或动画。
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);




            }
        });
}
}