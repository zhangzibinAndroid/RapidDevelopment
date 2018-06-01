package com.zzb.zzblibrary.upversion;

/**
 * 作者： 张梓彬
 * 日期： 2017/11/17 0017
 * 时间： 下午 7:32
 * 描述： 更新信息
 */

public class UpdateInfo {
    private String version; //版本号
    private String description; //版本更新描述
    private String url; //地址

    public UpdateInfo(String version, String description, String url) {
        this.version = version;
        this.description = description;
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
