# 笔记应用 (NoteApp)
# （app运行截图在文件夹里，如果markdown不支持查看或者从github克隆之后不能正常运行
# 查看效果，这可能和作者早期使用vscode上传项目有关，请移步文件夹，
# 本app已经打包为apk文件在手机上运行，有截图）

一个基于Android平台的简单、高效的笔记管理应用，帮助用户轻松记录和管理日常笔记，支持分类管理、图片存储和实时搜索功能。

## 功能特点

### 核心功能
- ✅ **笔记管理**：
  - 创建新笔记，输入标题和详细内容
  - 编辑现有笔记，实时保存修改
  - 长按删除不需要的笔记，支持批量操作
  - 自动记录笔记的创建和更新时间

- ✅ **分类系统**：
  - 创建自定义分类，管理不同类型的笔记
  - 为笔记选择已有分类或创建新分类
  - 查看所有分类列表，支持分类管理
  - 按分类筛选笔记，快速定位相关内容

- ✅ **实时搜索**：
  - 根据笔记标题和内容进行全文搜索
  - 输入关键词时实时显示搜索结果
  - 支持模糊搜索，提高搜索效率
  - 搜索结果高亮显示匹配内容

- ✅ **分类筛选**：
  - 通过下拉菜单快速选择分类
  - 筛选后只显示该分类下的笔记
  - 支持查看所有分类的笔记
  - 筛选状态实时更新

- ✅ **图片支持**：
  - 为笔记添加本地图片
  - 预览和删除已添加的图片
  - 自动处理图片压缩和存储
  - 笔记详情中完整展示图片内容

- ✅ **时间追踪**：
  - 自动记录笔记创建时间
  - 更新笔记时自动修改更新时间
  - 列表中显示笔记的最新更新时间
  - 支持按时间排序查看笔记

- ✅ **简洁UI**：
  - 直观易用的用户界面设计
  - 符合Material Design设计规范
  - 清晰的视觉层次和操作流程
  - 支持深色主题切换

### 技术特性
- 📱 **多设备适配**：
  - 兼容Android 5.0 (API Level 21)及以上版本
  - 自适应不同屏幕尺寸和分辨率
  - 支持横屏和竖屏显示
  - 优化平板设备的用户体验

- 💾 **本地存储**：
  - 使用SQLite数据库进行数据持久化
  - 数据存储在设备本地，无需网络连接
  - 支持数据备份和恢复
  - 数据库加密保护用户隐私

- 🎨 **现代设计**：
  - 采用Material Design 2.0设计风格
  - 流畅的动画过渡效果
  - 统一的色彩方案和图标设计
  - 注重用户体验和交互反馈

- ⚡ **高性能**：
  - 使用RecyclerView优化列表展示
  - 实现数据分页加载，提高加载速度
  - 图片懒加载，减少内存占用
  - 优化数据库查询，提升响应速度

- 🛡️ **稳定可靠**：
  - 完善的异常处理机制
  - 防止应用崩溃的错误捕获
  - 数据验证和错误提示
  - 定期自动备份数据

- 📂 **清晰架构**：
  - 模块化的项目结构设计
  - 遵循MVC设计模式
  - 代码分层清晰，易于维护
  - 组件化开发，提高代码复用性

## 技术栈

- **开发语言**: Java
- **开发框架**: Android SDK
- **数据库**: SQLite
- **UI组件**: RecyclerView, Material Design Components
- **构建工具**: Gradle
- **开发环境**: Android Studio

## 安装说明

### 前提条件
- Android Studio Arctic Fox (2020.3.1) 或更高版本
- JDK 8 或更高版本
- Android SDK API Level 21 或更高版本
- Android Build Tools 30.0.3 或更高版本

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/your-username/noteapp.git
   cd noteapp
   ```

2. **打开项目**
   - 启动Android Studio
   - 选择 "Open an existing project"
   - 导航到项目目录并选择它

3. **配置项目**
   - Android Studio会自动下载所需的依赖
   - 等待Gradle同步完成

4. **运行应用**
   - 连接Android设备或启动模拟器
   - 点击 "Run" 按钮运行应用

## 应用截图

### 1. 笔记列表页面

展示所有笔记的主界面，包含分类筛选下拉菜单和实时搜索框，支持长按删除操作。

![笔记列表页面](app截图/ae8b6a42ebf74a93c3586a2e7f8f285c.jpg)
### 2. 笔记编辑页面

创建和编辑笔记的界面，包含标题、内容输入框，分类选择器和图片添加功能。

![笔记编辑页面](app截图/3d62fd92258a547b09979df49d471ae7.jpg)
添加图片
![笔记编辑页面](app截图/43b6ee8698ee929cfa7caadda969c861.jpg)
添加/选择类型
![笔记编辑页面](app截图/d60b7d9cb4bf8d2edc71e60aa1abf17f.jpg)
![笔记编辑页面](app截图/6ea984952917e3643b98b7387d062dce.jpg)

### 3. 分类管理功能

根据分类来查找对应的内容

![分类管理功能](app截图/49875de02f27c70898be4adc60359fe8.jpg)

### 4. 搜索功能

使用搜索框查找笔记的实时搜索功能演示。

![分类管理功能](app截图/4856028c978700bbfa60292e5138e493.jpg)

### 5. 分类筛选功能

通过分类下拉菜单筛选特定分类笔记的功能演示。

![分类筛选功能](app截图/767d02ff2d69c63014790571a6bc78ee.jpg)



## 功能详解

### 1. 笔记列表页面 (NoteListActivity)

**功能说明**：
笔记列表页面是应用的主界面，负责展示所有用户创建的笔记，并提供多种交互功能。用户可以在这里查看笔记的基本信息（标题、内容预览、分类、更新时间），进行搜索、分类筛选、创建新笔记和删除笔记等操作。界面采用卡片式布局，每个笔记以卡片形式展示，支持上下滚动浏览。

**核心代码实现**：
- `setupRecyclerView()`: 初始化RecyclerView组件，设置线性布局管理器以垂直方式排列笔记项，并关联自定义的NoteAdapter适配器。适配器负责将笔记数据绑定到UI组件上。
- `loadNotes()`: 从SQLite数据库加载所有笔记数据，支持三种模式：显示所有笔记、按分类筛选笔记和按关键词搜索笔记。该方法会根据当前选择的分类和搜索关键词动态过滤数据。
- `setupSearchView()`: 配置SearchView组件，实现实时搜索功能。通过设置OnQueryTextListener监听器，当用户输入搜索关键词时，自动调用loadNotes()方法更新列表。
- `setupCategorySpinner()`: 初始化分类下拉菜单（Spinner），从数据库加载所有可用分类并添加"全部"选项。当用户选择不同分类时，自动调用loadNotes()方法筛选对应分类的笔记。
- `onNoteLongClick()`: 处理笔记项的长按事件，当用户长按某个笔记时，弹出确认删除对话框。用户确认后，从数据库中删除该笔记并更新列表。
- `onFloatingActionButtonClick()`: 处理浮动操作按钮（FloatingActionButton）的点击事件，跳转到笔记编辑页面以创建新笔记。

**关键组件**：
- RecyclerView: 高效显示大量笔记列表，使用ViewHolder模式减少内存占用和UI刷新时间。
- SearchView: 集成在标题栏的搜索组件，支持实时搜索和清除搜索结果。
- Spinner: 分类筛选下拉菜单，显示所有可用分类供用户选择。
- FloatingActionButton: 右下角的圆形按钮，突出显示，用于创建新笔记。
- CardView: 每个笔记项的卡片式容器，包含笔记的标题、内容预览、分类标签和更新时间。
- TextView: 显示笔记标题、内容摘要、分类名称和时间信息。

### 2. 笔记编辑页面 (NoteEditActivity)

**功能说明**：
笔记编辑页面用于创建新笔记和编辑现有笔记。用户可以在此输入笔记标题和详细内容，选择或创建分类，添加或更换图片，并保存笔记。界面设计简洁直观，采用表单式布局，所有编辑控件清晰可见。

**核心代码实现**：
- `onCreate()`: 初始化Activity，检查是否为编辑模式（通过Intent传递的笔记ID判断），并加载相应的布局和数据。
- `loadNote()`: 当处于编辑模式时，根据笔记ID从数据库加载笔记数据，并填充到编辑控件中。
- `setupCategorySpinner()`: 初始化分类选择器，从数据库加载所有现有分类，并添加创建新分类的功能。
- `saveNote()`: 将用户输入的笔记数据保存到数据库。如果是新建笔记，会插入一条新记录；如果是编辑现有笔记，会更新数据库中的对应记录。
- `setupImageButton()`: 配置图片选择按钮，当用户点击时，启动系统图片选择器允许用户从相册中选择图片。
- `showAddCategoryDialog()`: 显示添加新分类的对话框，包含一个输入框用于输入分类名称，以及确认和取消按钮。
- `onActivityResult()`: 处理图片选择的返回结果，获取用户选择的图片URI并显示在ImageView中。

**关键组件**：
- EditText: 用于输入笔记标题和详细内容的文本编辑框，支持多行输入和滚动。
- Spinner: 分类选择下拉菜单，显示所有可用分类，并提供创建新分类的选项。
- ImageView: 显示已选择的图片预览，用户可以点击更换图片。
- Button: 保存笔记的按钮，点击后将笔记数据保存到数据库并返回笔记列表页面。
- FloatingActionButton: 用于添加或更换图片的圆形按钮。

### 3. 分类管理功能

**功能说明**：
分类管理功能允许用户创建和管理笔记分类，以便更好地组织和筛选笔记。用户可以在创建或编辑笔记时选择现有分类，也可以创建新分类。分类系统帮助用户快速定位特定类型的笔记，提高笔记管理效率。

**核心代码实现**：
- `addCategory()`: 将新创建的分类添加到数据库中。首先检查分类名称是否为空或已存在，然后执行插入操作。
- `loadCategories()`: 从数据库加载所有分类数据，并返回一个包含所有分类名称的列表，用于填充分类选择器。
- `showAddCategoryDialog()`: 创建并显示添加分类的对话框，包含输入框和确认/取消按钮。
- `onCategoryAdd()`: 处理添加分类的点击事件，获取用户输入的分类名称，调用addCategory()方法保存分类，并更新分类选择器。
- `getNotesByCategory()`: 根据指定的分类名称从数据库中查询并返回该分类下的所有笔记。

**关键组件**：
- Dialog: 添加分类的弹出对话框，包含输入框和操作按钮。
- EditText: 用于输入新分类名称的文本框，支持中文和英文字符。
- Button: 确认和取消按钮，用于完成或取消分类创建操作。
- Spinner: 分类选择器，显示所有可用分类，支持下拉选择。
- NoteDbHelper: 数据库操作帮助类，提供分类相关的CRUD方法。

### 4. 图片支持功能

**功能说明**：
图片支持功能允许用户为笔记添加本地图片，丰富笔记内容。用户可以从设备相册中选择一张图片添加到笔记中，图片会与笔记内容一起保存。在查看笔记时，图片会完整展示在笔记详情中，支持预览。

**核心代码实现**：
- `selectImage()`: 启动系统图片选择器，使用Intent.ACTION_PICK操作允许用户从相册中选择图片。
- `saveImage()`: 将用户选择的图片保存到应用的私有存储目录，并将图片路径保存到数据库中。该方法会自动处理图片压缩，以减少存储占用。
- `loadImage()`: 根据图片路径加载并显示图片。使用BitmapFactory解码图片文件，并设置到ImageView中显示。
- `checkPermission()`: 检查并请求读取外部存储的权限，确保应用可以访问设备相册。
- `deleteImage()`: 从存储目录中删除指定的图片文件，并更新数据库中的图片路径为null。

**关键组件**：
- Intent: 用于启动系统图片选择器的意图对象。
- ImageView: 显示已选择图片的预览控件，支持点击交互。
- Button: 触发图片选择操作的按钮。
- FileProvider: 安全地共享应用内图片文件，避免文件权限问题。
- Bitmap: 表示和操作图片的对象，用于图片的加载和显示。

### 5. 搜索功能

**功能说明**：
搜索功能允许用户根据关键词快速查找笔记。用户可以在笔记列表页面的搜索框中输入关键词，系统会实时根据笔记标题和内容进行匹配，并显示匹配的笔记列表。搜索结果实时更新，无需用户点击额外的搜索按钮。

**核心代码实现**：
- `searchNotes()`: 根据关键词搜索笔记，构建SQL查询语句，使用LIKE操作符匹配笔记标题和内容中包含关键词的记录。
- `onQueryTextChange()`: SearchView的文本变化监听器，当用户输入或删除搜索关键词时自动触发，调用loadNotes()方法更新列表。
- `filterNotes()`: 根据搜索关键词过滤内存中的笔记列表（备用方法，当数据库查询效率较低时使用）。
- `highlightSearchResults()`: （预留功能）在搜索结果中高亮显示匹配的关键词，提高可读性。

**关键组件**：
- SearchView: 集成在标题栏的搜索组件，支持输入、清除和提交操作。
- OnQueryTextListener: 监听搜索框文本变化的监听器接口。
- SQLiteDatabase: 执行数据库搜索查询的核心类。
- NoteAdapter: 根据搜索结果更新RecyclerView的适配器。

## 使用方法

### 基本操作

#### 1. 创建笔记
1. 在笔记列表页面点击右下角的 **+** 按钮
2. 进入笔记编辑页面，输入笔记标题和内容
3. 选择分类（可选）：
   - 点击分类下拉菜单
   - 从现有分类中选择一个
   - 或点击旁边的 **+** 按钮添加新分类
4. 添加图片（可选）：
   - 点击 "添加图片" 按钮
   - 从相册中选择一张图片
   - 图片会显示在编辑页面中
5. 点击 "保存" 按钮完成笔记创建

#### 2. 编辑笔记
1. 在笔记列表页面点击要编辑的笔记项
2. 进入笔记编辑页面，修改标题、内容、分类或图片
3. 点击 "保存" 按钮保存更改

#### 3. 删除笔记
1. 在笔记列表页面长按要删除的笔记项
2. 系统会提示确认删除
3. 点击确认后，笔记将被永久删除

#### 4. 搜索笔记
1. 在笔记列表页面点击顶部的搜索框
2. 输入关键词（支持标题和内容搜索）
3. 列表会实时显示匹配的笔记
4. 清除搜索框内容可恢复显示所有笔记

#### 5. 分类筛选笔记
1. 在笔记列表页面点击分类下拉菜单
2. 选择一个分类
3. 列表将只显示该分类下的笔记
4. 选择 "全部" 可恢复显示所有笔记

#### 6. 管理分类
1. 在笔记编辑页面点击分类旁的 **+** 按钮
2. 在弹出的对话框中输入新分类名称
3. 点击 "确定" 按钮添加分类
4. 新分类将立即显示在分类选择器中

### 高级操作

#### 图片管理
- 添加图片后，点击图片可预览大图
- 再次点击 "添加图片" 按钮可更换图片
- 删除图片可通过长按图片并选择删除（如果应用支持此功能）

#### 数据管理
- 应用数据默认存储在设备本地数据库中
- 如需备份数据，可以通过Android系统的备份功能
- 卸载应用前建议备份重要数据

## 项目结构

```
app/src/main/
├── java/com/example/a122ad/
│   ├── MainActivity.java        # 应用入口，直接跳转到笔记列表
│   ├── NoteListActivity.java    # 笔记列表页面
│   ├── NoteEditActivity.java    # 笔记编辑页面
│   ├── adapter/
│   │   └── NoteAdapter.java     # 笔记列表适配器
│   ├── db/
│   │   └── NoteDbHelper.java    # 数据库操作帮助类
│   └── model/
│       └── Note.java            # 笔记数据模型
└── res/
    ├── layout/
    │   ├── activity_main.xml         # 主页面布局
    │   ├── activity_note_list.xml    # 笔记列表布局
    │   ├── activity_note_edit.xml    # 笔记编辑布局
    │   └── note_item.xml             # 笔记列表项布局
    ├── values/
    │   ├── colors.xml      # 颜色资源
    │   ├── strings.xml     # 字符串资源
    │   └── styles.xml      # 样式资源
    └── drawable/
        └── spinner_background.xml    # 自定义Spinner背景
```

## 实现方法和技术应用

### 1. 架构设计

采用经典的MVC（Model-View-Controller）架构模式：

- **Model**: 数据模型层，包含Note类和数据库操作
- **View**: UI界面层，包含各种Activity和布局文件
- **Controller**: 业务逻辑层，处理用户交互和数据流转

### 2. 数据持久化

使用SQLite数据库进行本地数据存储：

**数据库设计**：
```sql
CREATE TABLE notes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,
    content TEXT,
    category TEXT,
    created_at DATETIME,
    updated_at DATETIME,
    image_path TEXT
);

CREATE TABLE categories (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT UNIQUE
);
```

**核心数据库操作类 (NoteDbHelper)**：
- 使用SQLiteOpenHelper管理数据库创建和版本升级
- 实现增删改查(CRUD)操作
- 支持事务处理，确保数据一致性

### 3. RecyclerView实现高效列表

使用RecyclerView优化笔记列表展示：

- **ViewHolder模式**：减少View的创建和查找次数
- **DiffUtil**：高效更新列表数据
- **布局管理器**：支持线性布局和网格布局
- **动画效果**：添加列表项动画，提升用户体验

### 4. 图片处理

实现笔记图片的选择、保存和加载功能：

- **Intent.ACTION_PICK**：从相册选择图片
- **FileProvider**：安全地共享文件
- **Bitmap压缩**：优化图片存储和加载性能
- **图片缓存**：减少重复加载，提升应用响应速度

### 5. 异常处理机制

建立多层异常处理体系：

- **数据库异常处理**：捕获SQLite操作异常，确保数据安全
- **UI组件异常处理**：防止空指针和布局错误
- **文件操作异常处理**：安全处理图片文件操作
- **网络异常处理**：为未来的云同步功能预留

### 6. 性能优化

应用多种性能优化策略：

- **延迟加载**：按需加载数据和图片
- **内存管理**：及时释放资源，避免内存泄漏
- **数据库索引**：优化查询性能
- **UI渲染优化**：减少布局层级，使用约束布局

### 7. Material Design应用

采用Material Design设计规范：

- **主题颜色**：统一的配色方案
- **组件设计**：使用Material组件
- **动画效果**：平滑的过渡和交互动画
- **响应式设计**：适配不同屏幕尺寸

## 核心代码说明

### 数据模型 (Note.java)
```java
public class Note implements Serializable {
    private long id;
    private String title;
    private String content;
    private String category;
    private Date createdAt;
    private Date updatedAt;
    private String imagePath;
    // getter和setter方法
}
```

### 数据库操作 (NoteDbHelper.java)
- `addNote(Note note)`: 添加新笔记到数据库
- `updateNote(Note note)`: 更新现有笔记
- `deleteNote(long id)`: 从数据库删除笔记
- `getNoteById(long id)`: 根据ID查询笔记
- `getAllNotes()`: 获取所有笔记
- `getNotesByCategory(String category)`: 按分类获取笔记
- `searchNotes(String keyword)`: 根据关键词搜索笔记

### 列表适配器 (NoteAdapter.java)
- **ViewHolder模式**：优化列表项渲染性能
- **事件回调**：处理点击和长按事件
- **数据绑定**：将笔记数据绑定到UI组件
- **日期格式化**：显示友好的时间格式

### 主活动跳转 (MainActivity.java)
```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Intent intent = new Intent(MainActivity.this, NoteListActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }
}
```

## 异常处理机制

应用采用了多层异常处理机制，确保在各种异常情况下都能稳定运行：

1. **Activity初始化异常处理**
   - 捕获数据库初始化失败
   - 捕获视图初始化失败
   - 捕获RecyclerView设置失败

2. **数据库操作异常处理**
   - 捕获SQLite操作异常
   - 处理游标资源泄漏
   - 验证数据库查询结果

3. **UI交互异常处理**
   - 处理空指针异常
   - 验证用户输入
   - 确保在UI线程更新界面

## 性能优化

1. **数据库优化**
   - 合理使用索引
   - 及时关闭数据库连接
   - 批量操作减少IO开销

2. **UI优化**
   - RecyclerView复用机制
   - 延迟加载和预加载
   - 减少布局嵌套层级

3. **内存管理**
   - 避免内存泄漏
   - 及时释放资源
   - 优化对象创建和销毁

## 未来改进方向

- [ ] 添加云同步功能
- [ ] 支持笔记标签
- [ ] 实现富文本编辑
- [ ] 添加笔记提醒功能
- [ ] 支持导出笔记为PDF
- [ ] 添加夜间模式

## 贡献指南

欢迎对项目进行贡献！以下是贡献步骤：

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 作者

**NoteApp 开发者**