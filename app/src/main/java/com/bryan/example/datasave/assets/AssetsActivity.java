package com.bryan.example.datasave.assets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bryan.example.base.BaseActivity;
import com.bryan.example.mode.Person;

/**
 * 用Assets在本地保存资源，然后用来读取
 * 
 * 1-只读 2-不会被编译成id文件
 * 
 * note:
 * 在需要读取的数据量很大的时候，此种方法并不是最省内存的。但是如果文件不大，还是很适用的
 * 
 * 使用关键：
 * 使用getAsset方法获得assetmanager,在通过使用assetmanaget.open("文件的完整路径")读取文件中的内容(字节)。
 * 然后再对获得的字节流进行处理
 */
public class AssetsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView display = new TextView(this);
		setContentView(display);

		try {
			// 访问应用程序的Assets目录，通过AssetManager打开assets下任意的文件资源,使用AssetManager.open("文件的绝对路径名")
			AssetManager manager = getAssets();
			// 打开数据文件
			InputStream in = manager.open("csv/data.csv");
			// 解析CSV的数据并显示
			ArrayList<Person> persons = parse(in);
			StringBuilder sb = new StringBuilder();
			for (Person person : persons) {
				sb.append(String.format(
						"%s is %s years old , and likes the color %s",
						person.name, person.age, person.color));
				sb.append("\n");
			}

			display.setText(sb.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 简单的CSV解析器
	 */
	private static final int COL_NAME = 0;
	private static final int COL_AGE = 1;
	private static final int COL_COLOR = 2;

	private ArrayList<Person> parse(InputStream in) {
		ArrayList<Person> results = new ArrayList<Person>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String nextLine = null;
		try {
			while ((nextLine = reader.readLine()) != null) {
				String[] tokens = nextLine.split(",");
				if (tokens.length != 3) {
					Log.e("CVSParse", "Skipping bad CSV Row");
					continue;
				}
				// 添加新的解析器结果
				Person person = new Person();
				person.name = tokens[COL_NAME];
				person.age = tokens[COL_AGE];
				person.color = tokens[COL_COLOR];

				results.add(person);
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

}
