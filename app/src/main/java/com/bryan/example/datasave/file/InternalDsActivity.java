package com.bryan.example.datasave.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.widget.TextView;

import com.bryan.example.base.BaseActivity;

/**
 * 文件的内部存储
 * 
 * 使用Context.OpenFileInput()和Context.OpenFileOutput进行内部文件存储
 * 
 * 内部文件存储和外部文件存储的主要区别在于外部文件存储可以被挂载，移动电脑上等到，外部存储一旦被挂载或被移除应用程序便无法访问
 * 
 * 
 * 可以看出，两个方法都是使用Context方法。所以无论在任何地方，只要有Context的实例，我们就可以使用者两个方法进行文件的存储
 *
 */
public class InternalDsActivity extends BaseActivity {

	private static final String FILE_NAME= "data.txt";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tvDisplay = new TextView(this);
		setContentView(tvDisplay);
		
		//创建一个新文件并且写入数据
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			// MODE_PRIVATE以这种格式创建的文件每次写入都会覆盖原有的文件，如果需要写入到文件的尾部则应该使用MODE_APPEND
			String content = "Today I Will Get New Life!!!";
			// 写入到文件中。以字节的形式
			fos.write(content.getBytes());
			fos.flush();
			fos.close();//流记得要关闭
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//读取写入的文件展示在屏幕上
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] bs = new byte[128];
			fis.read(bs);
			fis.close();//流记得要关闭

			tvDisplay.setText(new String(bs));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		deleteFile(FILE_NAME);//删除文件
		

	}

}
