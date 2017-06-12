package experiment.diary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity {
    private EditText title;
    private EditText inputText;
    private TextView dayAndMonth;
    private TextView weather;
    private ImageView image;
    private Button editBtn;
    private Button saveBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //创建实体
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        //建立映射

        //日记标题
        title = (EditText) findViewById(R.id.diary_title);
        //日记内容
        inputText = (EditText) findViewById(R.id.diary_text);
        //TODO：日期编辑器
        dayAndMonth = (TextView) findViewById(R.id.day);
        //天气
        weather = (TextView) findViewById(R.id.weather);
        //日记所用照片
        image= (ImageView) findViewById(R.id.imageview);
        //编辑状态按钮组
        editBtn = (Button) findViewById(R.id.edit_btn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        //初始化界面
        inputText.setEnabled(false);
        saveBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);

        //TODO：打开相册，选取照片并显示出来
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add picture
                Toast.makeText(EditActivity.this,"add picture",Toast.LENGTH_SHORT).show();
            }
        });

        //TODO：保存各元素副本后，将各元素转入可编辑状态
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换为可编辑状态
                saveBtn.setVisibility(View.VISIBLE);
                cancelBtn.setVisibility(View.VISIBLE);
//                Log.e("key","click editBtn");
                inputText.setEnabled(true);
                editBtn.setVisibility(View.GONE);
            }
        });

        //TODO:检查各元素是否符合规范，符合则存入数据库
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save the diary
                finish();
            }
        });

        //TODO：从先前保存的副本恢复各元素
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回到修改前的情况
                inputText.setEnabled(false);
                editBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.GONE);
                cancelBtn.setVisibility(View.GONE);
            }
        });
    }
}
