package com.zzb.zzblibrary.httputils;

import com.google.gson.Gson;
import com.zzb.zzblibrary.httputils.builder.GetBuilder;
import com.zzb.zzblibrary.httputils.builder.PostFormBuilder;
import com.zzb.zzblibrary.httputils.callback.FileCallBack;
import com.zzb.zzblibrary.httputils.callback.StringCallback;
import com.zzb.zzblibrary.utils.AppManager;
import com.zzb.zzblibrary.utils.LogUtils;
import com.zzb.zzblibrary.utils.ToastUtils;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;

/**
 * 作者： 张梓彬
 * 日期： 2018/4/20 0020
 * 时间： 下午 4:51
 * 描述： OkHttpUtils二次封装,带回解析值
 */

public class XHttpUtils {
    private static XHttpUtils ourInstance;
    private static Gson gson;
    public static final String STRING = "String";

    public static XHttpUtils getInstance() {
        if (ourInstance == null) {
            gson = new Gson();
            ourInstance = new XHttpUtils();
        }
        return ourInstance;
    }

    private XHttpUtils() {

    }

    /**
     * @param url              请求地址
     * @param classOfT         解析类型
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    public void get(Class<?> classOfT, Map<String, String> params, String url, final OnResultCallBack onResultCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setStringParams(classOfT, getBuilder, params, onResultCallBack);
    }

    /**
     * 无参数请求
     *
     * @param url              请求地址
     * @param classOfT         解析类型
     * @param onResultCallBack 请求回调
     */
    public void get(Class<?> classOfT, String url, final OnResultCallBack onResultCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setStringParams(classOfT, getBuilder, onResultCallBack);
    }


    /**
     * @param url              请求地址
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    public void get(Map<String, String> params, String url, final OnResultCallBack onResultCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setStringParams(getBuilder, params, onResultCallBack);
    }


    /**
     * 无参数请求
     *
     * @param url              请求地址
     * @param onResultCallBack 请求回调
     */
    public void get(String url, final OnResultCallBack onResultCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setStringParams(getBuilder, onResultCallBack);
    }

    /**
     * @param classOfT         解析类型
     * @param url              请求地址
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    public void post(Class<?> classOfT, Map<String, Object> params, String url, final OnResultCallBack onResultCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setParams(classOfT, postFormBuilder, params, onResultCallBack);
    }


    /**
     * 无参数请求
     *
     * @param classOfT         解析类型
     * @param url              请求地址
     * @param onResultCallBack 请求回调
     */
    public void post(Class<?> classOfT, String url, final OnResultCallBack onResultCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setParams(classOfT, postFormBuilder, onResultCallBack);
    }


    /**
     * @param url              请求地址
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    public void post(Map<String, Object> params, String url, final OnResultCallBack onResultCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setParams(postFormBuilder, params, onResultCallBack);
    }

    /**
     * 无参数请求
     *
     * @param url              请求地址
     * @param onResultCallBack 请求回调
     */
    public void post(String url, final OnResultCallBack onResultCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setParams(postFormBuilder, onResultCallBack);
    }


    /**
     * @param classOfT         解析类型
     * @param builder          get请求
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    private void setStringParams(final Class<?> classOfT, GetBuilder builder, Map<String, String> params, final OnResultCallBack onResultCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            builder.addParams(key, value);
        }

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                Object ob = null;
                try {
                    ob = gson.fromJson(response, classOfT);
                } catch (Exception e) {
                    LogUtils.e("解析异常: " + e.getMessage());
                    try {
                        ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "数据解析异常!");
                    } catch (Exception ex) {
                        LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                    }
                }
                onResultCallBack.onSuccess(ob, id);
            }
        });
    }


    /**
     * 无参数请求
     *
     * @param classOfT         解析类型
     * @param builder          get请求
     * @param onResultCallBack 请求回调
     */
    private void setStringParams(final Class<?> classOfT, GetBuilder builder, final OnResultCallBack onResultCallBack) {
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                Object ob = null;
                try {
                    ob = gson.fromJson(response, classOfT);
                } catch (Exception e) {
                    LogUtils.e("解析异常: " + e.getMessage());
                    try {
                        ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "数据解析异常!");
                    } catch (Exception ex) {
                        LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                    }
                }
                onResultCallBack.onSuccess(ob, id);
            }
        });
    }


    /**
     * @param builder          get请求
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    private void setStringParams(GetBuilder builder, Map<String, String> params, final OnResultCallBack onResultCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            builder.addParams(key, value);
        }

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                onResultCallBack.onSuccess(response, id);
            }
        });
    }


    /**
     * 无参数请求
     *
     * @param builder          get请求
     * @param onResultCallBack 请求回调
     */
    private void setStringParams(GetBuilder builder, final OnResultCallBack onResultCallBack) {
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                onResultCallBack.onSuccess(response, id);
            }
        });
    }


    /**
     * @param classOfT         解析类型
     * @param builder          post请求
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    private void setParams(final Class<?> classOfT, PostFormBuilder builder, Map<String, Object> params, final OnResultCallBack onResultCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            if (entry.getValue() instanceof File) {
                File value = (File) entry.getValue();
                String fileName = value.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String upFileName = System.currentTimeMillis() + suffix;
                LogUtils.e("提交到服务器的名字: " + upFileName);
                builder.addFile(key, upFileName, value);
            } else if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                builder.addParams(key, value);
            }
        }

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                Object ob = null;
                try {
                    ob = gson.fromJson(response, classOfT);
                } catch (Exception e) {
                    LogUtils.e("解析异常: " + e.getMessage());
                    try {
                        ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "数据解析异常!");
                    } catch (Exception ex) {
                        LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                    }
                }
                onResultCallBack.onSuccess(ob, id);
            }
        });
    }


    /**
     * 无参数请求
     *
     * @param classOfT         解析类型
     * @param builder          post请求
     * @param onResultCallBack 请求回调
     */
    private void setParams(final Class<?> classOfT, PostFormBuilder builder, final OnResultCallBack onResultCallBack) {

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                Object ob = null;
                try {
                    ob = gson.fromJson(response, classOfT);
                } catch (Exception e) {
                    LogUtils.e("解析异常: " + e.getMessage());
                    try {
                        ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "数据解析异常!");
                    } catch (Exception ex) {
                        LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                    }
                }
                onResultCallBack.onSuccess(ob, id);
            }
        });
    }


    /**
     * @param builder          post请求
     * @param params           参数设置
     * @param onResultCallBack 请求回调
     */
    private void setParams(PostFormBuilder builder, Map<String, Object> params, final OnResultCallBack onResultCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            if (entry.getValue() instanceof File) {
                File value = (File) entry.getValue();
                String fileName = value.getName();
                String suffix = fileName.substring(fileName.lastIndexOf("."));
                String upFileName = System.currentTimeMillis() + suffix;
                LogUtils.e("提交到服务器的名字: " + upFileName);
                builder.addFile(key, upFileName, value);
            } else if (entry.getValue() instanceof String) {
                String value = (String) entry.getValue();
                builder.addParams(key, value);
            }
        }

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                onResultCallBack.onSuccess(response, id);
            }
        });
    }


    /**
     * 无参数请求
     *
     * @param builder          post请求
     * @param onResultCallBack 请求回调
     */
    private void setParams(PostFormBuilder builder, final OnResultCallBack onResultCallBack) {
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onResultCallBack.onError(call, e, id);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                LogUtils.json(response);
                onResultCallBack.onSuccess(response, id);
            }
        });
    }


    public interface OnResultCallBack<T> {

        void onSuccess(T t, int id);

        void onError(Call call, Exception e, int id);
    }


    public interface OnDownLoadCallBack {

        void onSuccess(File file, int id);

        void inProgress(float progress, long total, int id);

        void onError(Call call, Exception e, int id);
    }


    /**
     * 下载文件
     *
     * @param url                请求地址
     * @param params             参数设置
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    public void getDownLoad(String destFileDir, String destFileName, Map<String, Object> params, String url, final OnDownLoadCallBack onDownLoadCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setDownLoadParams(destFileDir, destFileName, getBuilder, params, onDownLoadCallBack);
    }


    /**
     * 无参数下载文件
     *
     * @param url                请求地址
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    public void getDownLoad(String destFileDir, String destFileName, String url, final OnDownLoadCallBack onDownLoadCallBack) {
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        setDownLoadParams(destFileDir, destFileName, getBuilder, onDownLoadCallBack);
    }


    /**
     * 下载文件
     *
     * @param url                请求地址
     * @param params             参数设置
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    public void postDownLoad(String destFileDir, String destFileName, Map<String, Object> params, String url, final OnDownLoadCallBack onDownLoadCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setDownLoadParams(destFileDir, destFileName, postFormBuilder, params, onDownLoadCallBack);
    }


    /**
     * 无参数下载文件
     *
     * @param url                请求地址
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    public void postDownLoad(String destFileDir, String destFileName, String url, final OnDownLoadCallBack onDownLoadCallBack) {
        PostFormBuilder postFormBuilder = OkHttpUtils.post().url(url);
        setDownLoadParams(destFileDir, destFileName, postFormBuilder, onDownLoadCallBack);
    }

    /**
     * @param builder            get请求
     * @param params             参数设置
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    private void setDownLoadParams(String destFileDir, String destFileName, GetBuilder builder, Map<String, Object> params, final OnDownLoadCallBack onDownLoadCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            builder.addParams(key, value);
        }

        builder.build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onResponse(File response, int id) {
                onDownLoadCallBack.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                onDownLoadCallBack.inProgress(progress, total, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onDownLoadCallBack.onError(call, e, id);
                }
            }
        });

    }


    /**
     * 无参数下载
     *
     * @param builder            get请求
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    private void setDownLoadParams(String destFileDir, String destFileName, GetBuilder builder, final OnDownLoadCallBack onDownLoadCallBack) {
        builder.build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onResponse(File response, int id) {
                onDownLoadCallBack.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                onDownLoadCallBack.inProgress(progress, total, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onDownLoadCallBack.onError(call, e, id);
                }
            }
        });

    }


    /**
     * @param builder            post请求
     * @param params             参数设置
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    private void setDownLoadParams(String destFileDir, String destFileName, PostFormBuilder builder, Map<String, Object> params, final OnDownLoadCallBack onDownLoadCallBack) {
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            builder.addParams(key, value);
        }

        builder.build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onResponse(File response, int id) {
                onDownLoadCallBack.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                onDownLoadCallBack.inProgress(progress, total, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onDownLoadCallBack.onError(call, e, id);
                }
            }
        });

    }


    /**
     * 无参数下载
     *
     * @param builder            post请求
     * @param onDownLoadCallBack 请求回调
     * @param destFileDir        目标文件存储的文件夹路径
     * @param destFileName       目标文件存储的文件名
     */
    private void setDownLoadParams(String destFileDir, String destFileName, PostFormBuilder builder, final OnDownLoadCallBack onDownLoadCallBack) {
        builder.build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onResponse(File response, int id) {
                onDownLoadCallBack.onSuccess(response, id);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                onDownLoadCallBack.inProgress(progress, total, id);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                try {
                    ToastUtils.show(AppManager.getInstance().currentActivity().getApplicationContext(), "网络不给力,请检查网络设置!");
                } catch (Exception ex) {
                    LogUtils.e("please add your current activity to AppManager: " + ex.getMessage());
                } finally {
                    onDownLoadCallBack.onError(call, e, id);
                }
            }
        });

    }


}
