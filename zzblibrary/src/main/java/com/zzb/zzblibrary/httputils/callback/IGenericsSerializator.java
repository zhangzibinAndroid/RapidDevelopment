package com.zzb.zzblibrary.httputils.callback;


public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
