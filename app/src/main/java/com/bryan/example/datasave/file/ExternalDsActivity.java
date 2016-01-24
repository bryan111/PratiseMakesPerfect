package com.bryan.example.datasave.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.constant.FilePathConstant;

/**
 * 外部存储
 * 
 * 1-需要声明读写文件的权限
 * 
 * 2-使用FileInputStream和FileOutputStream方法
 * 
 * 3-使用前应该先判断外部存储器是否可以使用
 * 
 * 
 ******************* 写入外部文件的标准代码块********************** 
 * fos.write(content.getBytes());
 * fos.flush();//确保驻留在流中的所有数据写出到VM内存缓冲区中
 * fos.getFD().sync(); //此方法将阻塞，直至系统将所有数据成功写入底层文件系统。
 * fos.close();
 */
public class ExternalDsActivity extends BaseActivity {

	private static final String FILE_NAME = "data.txt";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView displatTv = new TextView(this);
		setContentView(displatTv);

		// 1-创建文件的引用
		File dataFile = new File(FilePathConstant.getRootFileDir(), FILE_NAME);
		// 2-检查外部存储器是否可用
		// 因为外部存储器可以被挂载，当被挂载或者被移除的时候，应用程序时无法访问到它的，所以开始进行操作的时候应该先进行判断
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			toast("Cannot use storage");
			finish();
			return;
		}
		// 3-创建一个新文件并且写入一些数据
		try {
			FileOutputStream fos = new FileOutputStream(dataFile, false);
			// false表示每次写入文件都会被覆盖，true表示每次写入文件都会追加到末尾。
			String content = "Today I Will Get A NewLife !!";
			fos.write(content.getBytes());
			fos.flush();
			// 使用外部文件最好同步该文件
			fos.getFD().sync();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 读取已经创建的文件并且显示在屏幕上
		try {
			FileInputStream fis = new FileInputStream(dataFile);
			byte[] data = new byte[128];
			fis.read(data);
			fis.close();

			displatTv.setText(new String(data));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// dataFile.delete();//删除文件

	}
}
