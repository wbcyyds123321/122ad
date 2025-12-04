package com.example.a122ad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.example.a122ad.adapter.NoteAdapter;
import com.example.a122ad.db.NoteDbHelper;
import com.example.a122ad.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity implements NoteAdapter.OnNoteClickListener {

    private static final int REQUEST_ADD_NOTE = 1;
    private static final int REQUEST_EDIT_NOTE = 2;

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private NoteDbHelper dbHelper;
    private List<Note> noteList;
    private FloatingActionButton addNoteButton;
    private SearchView searchView;
    private Spinner categorySpinner;
    private List<String> categories;
    private String currentCategory = "全部"; // 默认显示所有分类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_note_list);

            // 初始化数据库帮助类
            try {
                dbHelper = new NoteDbHelper(this);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "数据库初始化失败", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // 初始化UI组件
            try {
                recyclerView = findViewById(R.id.note_list);
                addNoteButton = findViewById(R.id.add_note_button);
                searchView = findViewById(R.id.search_view);
                categorySpinner = findViewById(R.id.category_spinner);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "视图初始化失败", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // 设置RecyclerView
            try {
                if (recyclerView != null) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(layoutManager);
                    noteList = new ArrayList<>();
                    adapter = new NoteAdapter(this, noteList, this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
                    // 添加分割线
                    recyclerView.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(this, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "RecyclerView设置失败", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // 加载分类数据
            try {
                loadCategories();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "加载分类数据失败", Toast.LENGTH_LONG).show();
            }

            // 设置分类选择监听器
            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        currentCategory = categories.get(position);
                        loadNotes();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NoteListActivity.this, "切换分类失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            // 设置搜索监听器
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    try {
                        // 搜索笔记
                        searchNotes(query);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NoteListActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    try {
                        // 实时搜索
                        if (newText.isEmpty()) {
                            loadNotes(); // 如果搜索框为空，加载所有笔记
                        } else {
                            searchNotes(newText);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(NoteListActivity.this, "搜索失败", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            // 设置添加笔记按钮的点击事件
            addNoteButton.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(NoteListActivity.this, NoteEditActivity.class);
                    startActivityForResult(intent, REQUEST_ADD_NOTE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NoteListActivity.this, "启动添加笔记页面失败", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes(); // 每次回到列表时重新加载笔记
    }

    // 更新笔记列表
    private void loadNotes() {
        // 在UI线程中执行，避免ANR和线程安全问题
        runOnUiThread(() -> {
            if (dbHelper != null && adapter != null) {
                try {
                    List<Note> loadedNotes;
                    if (currentCategory.equals("全部")) {
                        loadedNotes = dbHelper.getAllNotes();
                    } else {
                        loadedNotes = dbHelper.getNotesByCategory(currentCategory);
                    }
                    // 确保不为空
                    noteList = (loadedNotes != null) ? loadedNotes : new ArrayList<>();
                    adapter.updateList(noteList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NoteListActivity.this, "加载笔记失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // 出错时清空列表
                    noteList = new ArrayList<>();
                    adapter.updateList(noteList);
                }
            }
        });
    }

    // 加载分类数据
    private void loadCategories() {
        try {
            List<String> dbCategories = dbHelper.getAllCategories();
            // 初始化并确保不为空
            categories = new ArrayList<>();
            categories.add("全部");
            
            // 安全地添加数据库返回的分类
            if (dbCategories != null && !dbCategories.isEmpty()) {
                categories.addAll(dbCategories);
            }

            // 设置分类适配器
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, categories);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(categoryAdapter);

            // 确保默认选择"全部"
            categorySpinner.setSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
            // 即使出错也要设置一个基本的分类适配器
            categories = new ArrayList<>();
            categories.add("全部");
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, categories);
            categorySpinner.setAdapter(categoryAdapter);
        }
    }

    // 搜索笔记
    private void searchNotes(String query) {
        List<Note> searchResults = dbHelper.searchNotes(query);
        // 如果有分类筛选，在搜索结果中进一步筛选
        if (!currentCategory.equals("全部")) {
            List<Note> filteredResults = new ArrayList<>();
            for (Note note : searchResults) {
                if (note.getCategory().equals(currentCategory)) {
                    filteredResults.add(note);
                }
            }
            adapter.updateList(filteredResults);
        } else {
            adapter.updateList(searchResults);
        }
    }

    @Override
    public void onNoteClick(Note note) {
        // 点击笔记项，跳转到编辑页面
        Intent intent = new Intent(NoteListActivity.this, NoteEditActivity.class);
        intent.putExtra("note_id", note.getId());
        startActivityForResult(intent, REQUEST_EDIT_NOTE);
    }

    @Override
    public void onNoteLongClick(Note note, int position) {
        // 长按笔记项，显示删除选项
        new AlertDialog.Builder(this)
                .setTitle("删除笔记")
                .setMessage("确定要删除这条笔记吗？")
                .setPositiveButton("删除", (dialog, which) -> {
                    dbHelper.deleteNote(note.getId());
                    loadNotes(); // 删除后重新加载列表
                    Toast.makeText(NoteListActivity.this, "笔记已删除", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 无论是添加还是编辑，都重新加载笔记列表
            loadNotes();
            // 重新加载分类列表，因为可能添加了新分类
            loadCategories();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 确保在Activity销毁时关闭数据库连接，防止内存泄漏
        try {
            if (dbHelper != null) {
                dbHelper.close();
                dbHelper = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}