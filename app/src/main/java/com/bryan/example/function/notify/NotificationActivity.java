package com.bryan.example.function.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.bryan.example.MainActivity;
import com.bryan.example.base.BaseActivity;
import com.example.pratisemeanperfect.R;

/**
 * 使用NotificationCompat.Builder构建消息通知。
 * 
 * note：系统默认提供了4种消息通知的布局 1-默认 2-BigTextStyle 3-BigpictureStyle 4-InBoxStyle
 * 后面三种都实现了Notification
 * .Style这个接口。如果需要更复杂的布局的话可以自己实现这个接口，实现更复杂的布局。使用自定义的布局来进行消息通知
 * 
 * 
 * note2: 可以给通知添加多个Action.这些Action会按顺序在底部进行排列
 * 
 * note3: 每条通知都有自己的ID，如果要对这条通知进行操作，可以先通过getID进行判断是不是对应的ID。 这个ID就相当于他的身份证
 * 
 * note4: 自定义notification布局
 * 
 * note5: http://blog.csdn.net/vipzjyno1/article/details/25248021 整理的很好
 * 
 * @author bryan
 *
 */
public class NotificationActivity extends BaseActivity
{

    private RadioGroup mOptionsGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mOptionsGroup = (RadioGroup) findViewById(R.id.options_group);
    }

    public void onPostClick(View v)
    {
        final int noteId = mOptionsGroup.getCheckedRadioButtonId();// 获取一组RadionButton中被选中的那一项
        final Notification note;
        switch (noteId)
        {
        case R.id.options_basic:
        case R.id.options_bigtext:
        case R.id.options_bigpictures:
        case R.id.options_inbox:
        case R.id.options_custom:
            note = buildStyledNotification(noteId);
            break;
        case R.id.options_private:
        case R.id.options_secret:
        case R.id.options_headsup:
            note = buildSecureNotification(noteId);
            break;
        default:
            throw new IllegalArgumentException("Unknown Type");
        }

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(noteId, note);
        // 应用程序的每种类型的通知都应该拥有唯一的ID，同一时刻，管理器只允许相同ID的一个通知显示在列表中。新的通知会覆盖具有相同ID的旧通知。
        // 手动取消某个通知时也需要传入对应的ID

    }

    private Notification buildSecureNotification(int type)
    {
        Intent launchIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);

        // 构造基础通知
        // 如果支持的版本大于等于4.1就可以使用Notification.Builder，如果小于4.1可以使用NotificationCompat.Builder代替
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Account Balaence Update")
                .setContentText("your account balance is - $250.00 ")
                .setStyle(
                        new Notification.BigTextStyle()
                                .bigText("Your account balance is -$250.00;pay us please or we will be forced to take legal action!!"))
                .setContentIntent(contentIntent);

        switch (type)
        {
        case R.id.options_private:
            // 为安全的锁定屏幕提供独特的通知版本
            Notification publicNote = new Notification.Builder(this).setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Account Notification").setContentText("An important message has arrived")
                    .setContentIntent(contentIntent).build();
            // return ((Object)
            // builder).setPublicVersion(publicNote).build();//找不到对应的方法
            // return null;
        case R.id.options_secret:
            // 从安全的锁定屏幕完全隐藏通知
            // return
            // builder.setVisibility(Notification.VISIBILITY_SECRET).build();//找不到对应的方法
            // return null;

        case R.id.options_headsup:
            // 在发布时显示警告通知
            return builder.setDefaults(Notification.DEFAULT_SOUND).setPriority(Notification.PRIORITY_HIGH).build();
        default:
            throw new IllegalArgumentException("Unknown Type");
        }
    }

    private Notification buildStyledNotification(int type)
    {
        Intent launchIntent = new Intent(this, NotificationActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        Notification.Builder builder = new Notification.Builder(NotificationActivity.this);

        builder.setSmallIcon(R.drawable.ic_launcher).setTicker("SomeThing Happened")
                .setWhen(System.currentTimeMillis()).setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("We're Finished!").setContentText("Click Here").setContentIntent(contentIntent);
        // .setNumber(10)用来设置数量
        // .setOngoing(true)这个属性可设置通知是否可以被滑动移除

        switch (type)
        {
        case R.id.options_basic:
            // 返回简单的通知
            return builder.build();

        case R.id.options_bigtext:
            // BigtextStyle显示更多的文本信息。（消息的全部内容，可能很长的那种）
            // 包括两个动作
            builder.addAction(android.R.drawable.ic_menu_call, "Call", contentIntent);
            builder.addAction(android.R.drawable.ic_menu_recent_history, "History", contentIntent);
            // 在展开时使用BigTextStyle
            Notification.BigTextStyle textstyle = new Notification.BigTextStyle(builder);
            textstyle
                    .bigText("Here is some additional text to be displayed when the notification is in expended mode,i can fit so much content into this giant view!");
            return textstyle.build();

        case R.id.options_bigpictures:
            // BigPictureStyle显示全彩的大图片
            // 添加一个额外的动作
            builder.addAction(android.R.drawable.ic_menu_compass, "View Location", contentIntent);
            // 在展开时使用BigPictureStyle
            Notification.BigPictureStyle pictureStyle = new Notification.BigPictureStyle(builder);
            pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.just_run));

            return pictureStyle.build();
        case R.id.options_inbox:
            // InboxStyle提供一个条目列表。
            // 在展开时使用InboxStyle
            Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);
            inboxStyle.setSummaryText("4 New Tasks");
            inboxStyle.addLine("Make Dinner");
            inboxStyle.addLine("Call Mom");
            inboxStyle.addLine("Call Wife First");
            inboxStyle.addLine("Pick up kid");
            return inboxStyle.build();
        case R.id.options_custom:
            
            RemoteViews contextView = new RemoteViews(getPackageName(), R.layout.custom_notification);

            contextView.setImageViewResource(R.id.image, R.drawable.ic_action_person);

            contextView.setTextViewText(R.id.name, "自定义通知的标题");

            contextView.setTextViewText(R.id.desc, "自定义通知的内容");

           

            Intent intent = new Intent(NotificationActivity.this, NotificationActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);

            builder.setContentIntent(pendingIntent);

            builder.setContent(contextView); // 指定自定义布局

//            Notification notification = builder.build();
            
            
//            Intent _launchIntent = new Intent(this, NotificationActivity.class);
//            PendingIntent _contentIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
//            NotificationCompat.Builder _builder = new NotificationCompat.Builder(NotificationActivity.this);
//
//            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
//            contentView.setImageViewResource(R.id.img, R.drawable.ic_action_person);
//            contentView.setTextViewText(R.id.name, "Practise makes perfect");
////            contentView.setTextViewText(R.id.text1, "test 1");
////            contentView.setTextViewText(R.id.text2, "test 2");
////            contentView.setTextViewText(R.id.click, "click me");
////            PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(NotificationActivity.this,
////                    MainActivity.class), 0);
//            
////            contentView.setOnClickPendingIntent(R.id.click, intent);
//            _builder.setSmallIcon(R.drawable.ic_launcher).setContentIntent(contentIntent)
//                    .setWhen(System.currentTimeMillis()).setDefaults(Notification.DEFAULT_SOUND).setContent(contentView);

            return builder.build();

        default:
            throw new IllegalArgumentException("Unknown Type");
        }
    }

}
