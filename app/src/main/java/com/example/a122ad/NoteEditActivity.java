package com.example.a122ad;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ImageView;
import android.net.Uri;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.a122ad.db.NoteDbHelper;
import com.example.a122ad.model.Note;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NoteEditActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private Spinner categorySpinner;
    private Button addCategoryButton;
    private Button saveButton;
    private ImageView noteImageView;
    private Button selectImageButton;
    private Button deleteImageButton;
    private Toolbar toolbar;
    private NoteDbHelper dbHelper;
    private long noteId = -1;
    private List<String> categories;
    private String currentImagePath;
    
    private final ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    saveImageToInternalStorage(uri);
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_note_edit);

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
                toolbar = findViewById(R.id.toolbar);
                titleEditText = findViewById(R.id.note_title);
                contentEditText = findViewById(R.id.note_content);
                categorySpinner = findViewById(R.id.category_spinner);
                addCategoryButton = findViewById(R.id.add_category_button);
                saveButton = findViewById(R.id.save_button);
                noteImageView = findViewById(R.id.note_image);
                selectImageButton = findViewById(R.id.select_image_button);
                deleteImageButton = findViewById(R.id.delete_image_button);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "视图初始化失败", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            // 设置工具栏
            try {
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }

                toolbar.setNavigationOnClickListener(v -> finish());
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "工具栏设置失败", Toast.LENGTH_LONG).show();
            }

            // 加载分类数据
            try {
                loadCategories();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "加载分类数据失败", Toast.LENGTH_LONG).show();
            }

            // 检查是否是编辑模式
            try {
                Intent intent = getIntent();
                if (intent.hasExtra("note_id")) {
                    noteId = intent.getLongExtra("note_id", -1);
                    if (noteId != -1) {
                        // 加载笔记数据
                        try {
                            loadNoteData();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "加载笔记数据失败", Toast.LENGTH_LONG).show();
                        }
                        if (getSupportActionBar() != null) {
                            getSupportActionBar().setTitle("编辑笔记");
                        }
                    }
                } else {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle("新建笔记");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "处理Intent数据失败", Toast.LENGTH_LONG).show();
            }

            // 底部保存按钮点击事件
            saveButton.setOnClickListener(v -> {
                try {
                    saveNote();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NoteEditActivity.this, "保存失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            // 设置添加分类按钮的点击事件
            addCategoryButton.setOnClickListener(v -> {
                try {
                    showAddCategoryDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(NoteEditActivity.this, "打开添加分类对话框失败", Toast.LENGTH_LONG).show();
                }
            });

            // 图片选择按钮点击事件
            selectImageButton.setOnClickListener(v -> pickImageLauncher.launch("image/*"));

            // 删除图片按钮点击事件
            deleteImageButton.setOnClickListener(v -> {
                currentImagePath = null;
                updateImageUI();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    // 加载分类数据
    private void loadCategories() {
        categories = dbHelper.getAllCategories();
        if (categories.isEmpty()) {
            categories.add("默认分类");
        }

        // 设置分类适配器
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    // 加载笔记数据（编辑模式）
    private void loadNoteData() {
        Note note = dbHelper.getNoteById(noteId);
        if (note != null) {
            titleEditText.setText(note.getTitle());
            contentEditText.setText(note.getContent());
            
            // 设置选中的分类
            int position = categories.indexOf(note.getCategory());
            if (position >= 0) {
                categorySpinner.setSelection(position);
            }
            
            // 加载图片
            currentImagePath = note.getImagePath();
            updateImageUI();
        }
    }

    // 保存笔记
    private void saveNote() {
        String title = titleEditText.getText().toString().trim();
        String content = contentEditText.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        // 验证标题
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this, "请输入笔记标题", Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建或更新笔记
        if (noteId == -1) {
            // 创建新笔记
            Note newNote = new Note(title, content);
            newNote.setCategory(category);
            newNote.setImagePath(currentImagePath);
            long id = dbHelper.addNote(newNote);
            if (id > 0) {
                Toast.makeText(this, "笔记已保存", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 更新现有笔记
            Note existingNote = dbHelper.getNoteById(noteId);
            if (existingNote != null) {
                existingNote.setTitle(title);
                existingNote.setContent(content);
                existingNote.setCategory(category);
                existingNote.setImagePath(currentImagePath);
                int rowsAffected = dbHelper.updateNote(existingNote);
                if (rowsAffected > 0) {
                    Toast.makeText(this, "笔记已更新", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(this, "更新失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 显示添加分类对话框
    private void showAddCategoryDialog() {
        final EditText categoryEditText = new EditText(this);
        categoryEditText.setHint("输入新分类名称");

        new AlertDialog.Builder(this)
                .setTitle("添加分类")
                .setView(categoryEditText)
                .setPositiveButton("确定", (dialog, which) -> {
                    String newCategory = categoryEditText.getText().toString().trim();
                    if (!TextUtils.isEmpty(newCategory)) {
                        if (!categories.contains(newCategory)) {
                            categories.add(newCategory);
                            // 更新分类适配器
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) categorySpinner.getAdapter();
                            adapter.notifyDataSetChanged();
                            // 选中新添加的分类
                            categorySpinner.setSelection(categories.size() - 1);
                        } else {
                            Toast.makeText(NoteEditActivity.this, "该分类已存在", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
    
    // 更新图片UI
    private void updateImageUI() {
        if (currentImagePath != null) {
            File imgFile = new File(currentImagePath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                noteImageView.setImageBitmap(myBitmap);
                noteImageView.setVisibility(View.VISIBLE);
                deleteImageButton.setVisibility(View.VISIBLE);
                selectImageButton.setText("更改图片");
            } else {
                currentImagePath = null;
                noteImageView.setVisibility(View.GONE);
                deleteImageButton.setVisibility(View.GONE);
                selectImageButton.setText("添加图片");
            }
        } else {
            noteImageView.setVisibility(View.GONE);
            deleteImageButton.setVisibility(View.GONE);
            selectImageButton.setText("添加图片");
        }
    }

    // 保存图片到内部存储
    private void saveImageToInternalStorage(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            if (inputStream == null) return;

            // 创建图片目录
            File imagesDir = new File(getFilesDir(), "images");
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }

            // 生成唯一文件名
            String fileName = "IMG_" + UUID.randomUUID().toString() + ".jpg";
            File file = new File(imagesDir, fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            currentImagePath = file.getAbsolutePath();
            updateImageUI();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "图片保存失败", Toast.LENGTH_SHORT).show();
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
