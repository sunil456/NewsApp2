package com.androidprojects.sunilsharma.newsapp.Model;

/**
 * Created by sunil sharma on 11/16/2017.
 */

public class Icon
{
    private String url;
    private int width , height , bytes;
    private String formate , shalsum;
    private Object error;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBytes() {
        return bytes;
    }

    public void setBytes(int bytes) {
        this.bytes = bytes;
    }

    public String getFormate() {
        return formate;
    }

    public void setFormate(String formate) {
        this.formate = formate;
    }

    public String getShalsum() {
        return shalsum;
    }

    public void setShalsum(String shalsum) {
        this.shalsum = shalsum;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}
