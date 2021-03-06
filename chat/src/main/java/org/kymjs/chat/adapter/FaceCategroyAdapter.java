package org.kymjs.chat.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.kymjs.chat.ChatFunctionFragment;
import org.kymjs.chat.FacePageFragment;
import org.kymjs.chat.OnOperationListener;
import org.kymjs.chat.R;
import org.kymjs.chat.emoji.EmojiPageFragment;
import org.kymjs.chat.widget.KJChatKeyboard;
import org.kymjs.chat.widget.PagerSlidingTabStrip;

import java.util.List;

/**
 * 控件分类的viewpager适配器
 *
 * @author kymjs (http://www.kymjs.com/)
 */
public class FaceCategroyAdapter extends FragmentStatePagerAdapter implements
        PagerSlidingTabStrip.IconTabProvider {
    private final int sMode;

    //每个item表示一个folder绝对路径，每个folder中存放的是一套face图片
    private List<String> datas;
    private OnOperationListener listener;

    public FaceCategroyAdapter(FragmentManager fm, int mode) {
        super(fm);
        sMode = mode;
    }

    @Override
    public int getPageIconResId(int position) {
        int id = 0;
        switch (position) {
            case 0:
                id = R.drawable.icon_face_click;
                break;
            case 1:
                id = R.drawable.big1;
                break;
            case 2:
                id = R.drawable.cig2;
                break;
            case 3:
                id = R.drawable.dig1;
                break;
        }
        return id;
    }

    @Override
    public int getCount() {
        if (sMode == KJChatKeyboard.LAYOUT_TYPE_FACE) {
            int count = datas == null ? 0 : datas.size();
            //加1是因为有默认的emoji表情分类
            return (count + 1);
        } else {
            return 1;
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        if (sMode == KJChatKeyboard.LAYOUT_TYPE_FACE) {
            if (position == 0) {
                f = new EmojiPageFragment();
                ((EmojiPageFragment) f).setOnOperationListener(listener);
            } else {
                position--;
                f = new FacePageFragment();
                ((FacePageFragment) f).setOnOperationListener(listener);
                Bundle bundle = new Bundle();
                bundle.putString(FacePageFragment.FACE_FOLDER_PATH, datas.get(position));
                f.setArguments(bundle);
            }
        } else {
            f = new ChatFunctionFragment();
            ((ChatFunctionFragment) f).setOnOperationListener(listener);
        }
        return f;
    }

    public void setOnOperationListener(OnOperationListener onOperationListener) {
        this.listener = onOperationListener;
    }

    public void refresh(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
}