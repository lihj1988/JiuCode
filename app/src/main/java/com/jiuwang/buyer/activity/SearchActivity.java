package com.jiuwang.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuwang.buyer.R;
import com.jiuwang.buyer.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索界面 标准版
 */
public class SearchActivity extends BaseActivity implements OnClickListener{
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";

    private ArrayAdapter<String> mArrAdapter;
    private SharedPreferences mPref;
    private Editor mEditor;

    private List<String> mHistoryKeywords;

    String type = "";//当等于 1 时是从首页跳转过来的


    @Bind(R.id.topView)
    LinearLayout topView; @Bind(R.id.tab_bar_keyword_et)
    EditText mKeywordEt;
    @Bind(R.id.tab_bar_cancel_tv)
    TextView mOperationTv;
    @Bind(R.id.actionbar_text)
    TextView actionbar_text;
    @Bind(R.id.search_history_ll)
    LinearLayout mSearchHistoryLl;
    @Bind(R.id.onclick_layout_left)
    RelativeLayout rl_left;
    @Bind(R.id.clear_keyword_iv)
    ImageView clearKeywordIv;
    @Bind(R.id.onclick_layout_right)
    Button onclick_layout_right;
    @Bind(R.id.search_history_lv)
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        type = getIntent().getStringExtra("type");
        mPref = getSharedPreferences("shangang", Activity.MODE_PRIVATE);
        mEditor = mPref.edit();
        mHistoryKeywords = new ArrayList<String>();
        ButterKnife.bind(this);
        intView();
        addListener();
        mOperationTv.setOnClickListener(this);
        initSearchHistory();
//        search_title.setPadding(0,getStatusBarHeight(this),0,0);
    }

    private void intView() {
        actionbar_text.setText("搜索");
        onclick_layout_right.setVisibility(View.GONE);
        mKeywordEt.requestFocus();
        mOperationTv.setText("搜索");
        setTopView(topView);
    }

    private void addListener() {
        rl_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clearKeywordIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeywordEt.setText("");
                v.setVisibility(View.GONE);
            }
        });
        mKeywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });
        mKeywordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() == 0) {
                    mOperationTv.setText("搜索");
                    clearKeywordIv.setVisibility(View.GONE);
                    if (mHistoryKeywords.size() > 0) {
                        mSearchHistoryLl.setVisibility(View.VISIBLE);
                    } else {
                        mSearchHistoryLl.setVisibility(View.GONE);
                    }
                } else {
                    mSearchHistoryLl.setVisibility(View.GONE);
                    mOperationTv.setText("搜索");
                    clearKeywordIv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void initSearchHistory() {
        findViewById(R.id.clear_history_btn).setOnClickListener(this);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(history)) {
            List<String> list = new ArrayList<String>();
            for (Object o : history.split(",")) {
                list.add((String) o);
            }
            mHistoryKeywords = list;
        }
        if (mHistoryKeywords.size() > 0) {
            mSearchHistoryLl.setVisibility(View.VISIBLE);
        } else {
            mSearchHistoryLl.setVisibility(View.GONE);
        }
        mArrAdapter = new ArrayAdapter<String>(this,
                R.layout.item_search_history, mHistoryKeywords);
        listView.setAdapter(mArrAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int i, long l) {
                mKeywordEt.setText(mHistoryKeywords.get(i));
                mSearchHistoryLl.setVisibility(View.GONE);
            }
        });
        mArrAdapter.notifyDataSetChanged();
    }

    public void save() {
        String text = mKeywordEt.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD, "");
        if (!TextUtils.isEmpty(text) && !oldText.contains(text)) {
            if (mHistoryKeywords.size() > 4) {
                Toast.makeText(this, "最多保存5条历史", Toast.LENGTH_SHORT).show();
                return;
            }
            mEditor.putString(KEY_SEARCH_HISTORY_KEYWORD, text + "," + oldText);
            mEditor.commit();
            mHistoryKeywords.add(0, text);
        }
        mArrAdapter.notifyDataSetChanged();
        showSelectedResult();
    }

    private void showSelectedResult() {
        if ("1".equals(type)) {
            String serchName = mKeywordEt.getText().toString();
            Intent it = new Intent();
            it.setAction("refresh_home");
            it.putExtra("searchName", serchName);
            sendBroadcast(it, null);
            finish();

        } else {
            String serchName = mKeywordEt.getText().toString();
            Intent it = new Intent();
            it.setAction("refresh_home");
            it.putExtra("searchName", serchName);
            sendBroadcast(it, null);
            finish();
        }
    }

    public void cleanHistory() {
        mEditor.clear();
        mEditor.commit();
        mHistoryKeywords.clear();
        mArrAdapter.notifyDataSetChanged();
        mSearchHistoryLl.setVisibility(View.GONE);
        Toast.makeText(this, "清除搜索历史记录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_history_btn:
                cleanHistory();
                break;
            case R.id.tab_bar_cancel_tv:
                save();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        String keyword = intent.getStringExtra(EXTRA_KEY_KEYWORD);
        if (!TextUtils.isEmpty(keyword)) {
            mKeywordEt.setText(keyword);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
