package com.zzb.zzblibrary.media;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zzb.zzblibrary.media.entity.Media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 张梓彬
 * 日期： 2018/3/28 0028
 * 时间： 下午 4:06
 * 描述： 图片视频选择库
 */
public class MediaSelectUtils {
    private ArrayList<Media> select;
    private List<File> files;
    private List<String> paths;
    private long maxSize = 188743680L;

    public MediaSelectUtils() {
        select = new ArrayList<>();
        files = new ArrayList<>();
        paths = new ArrayList<>();
    }

    /**
     * @param Kinds      选择种类 PickerConfig.PICKER_IMAGE,PickerConfig.PICKER_VIDEO,PickerConfig.PICKER_IMAGE_VIDEO
     * @param selectSize 选择数量
     */
    public void takeMediaListInActivity(Activity activity, int Kinds, int selectSize) {
        if (activity != null) {
            select.clear();
            files.clear();
            paths.clear();
            Intent intent = new Intent(activity, PickerActivity.class);
            intent.putExtra(PickerConfig.SELECT_MODE, Kinds);//default image and video (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_COUNT, selectSize);  //default 40 (Optional)
            intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
            activity.startActivityForResult(intent, 200);
        }
    }


    /**
     * @param Kinds      选择种类 PickerConfig.PICKER_IMAGE,PickerConfig.PICKER_VIDEO,PickerConfig.PICKER_IMAGE_VIDEO
     * @param selectSize 选择数量
     */
    public void takeMediaListInFragment(Fragment fragment, int Kinds, int selectSize) {
        if (fragment != null) {
            select.clear();
            files.clear();
            paths.clear();
            Intent intent = new Intent(fragment.getActivity(), PickerActivity.class);
            intent.putExtra(PickerConfig.SELECT_MODE, Kinds);//default image and video (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_COUNT, selectSize);  //default 40 (Optional)
            intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
            fragment.startActivityForResult(intent, 200);
        }
    }


    /**
     * @param Kinds      选择种类 PickerConfig.PICKER_IMAGE,PickerConfig.PICKER_VIDEO,PickerConfig.PICKER_IMAGE_VIDEO
     * @param selectSize 选择数量
     */
    public void takeMediaListInChildFragment(Fragment fragment, int Kinds, int selectSize) {
        if (fragment != null) {
            select.clear();
            files.clear();
            paths.clear();
            Intent intent = new Intent(fragment.getActivity(), PickerActivity.class);
            intent.putExtra(PickerConfig.SELECT_MODE, Kinds);//default image and video (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
            intent.putExtra(PickerConfig.MAX_SELECT_COUNT, selectSize);  //default 40 (Optional)
            intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST, select); // (Optional)
            fragment.getParentFragment().startActivityForResult(intent, 200);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == PickerConfig.RESULT_CODE) {
            select = data.getParcelableArrayListExtra(PickerConfig.EXTRA_RESULT);
            for (Media media : select) {
                paths.add(media.path);
                files.add(new File(media.path));
            }
            try {
                onFileCallBack.OnFile(select,files,paths);
            }catch (Exception e){
                throw new Error("MediaSelectUtils need setOnFileCallBack()");
            }
        }
    }

    public OnFileCallBack onFileCallBack;

    public interface OnFileCallBack {
        void OnFile(ArrayList<Media> list,List<File> files, List<String> paths);
    }

    public void setOnFileCallBack(OnFileCallBack onFileCallBack) {
        this.onFileCallBack = onFileCallBack;
    }
}
