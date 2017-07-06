package com.lzw.order.dinnerorderapp.Bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2017/7/6.
 */

public class WeatherInfo {
    @SerializedName("temperature")
    private String temperature;
    @SerializedName("code")
    private String code;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }

    @SerializedName("description")

    private String description;
    @SerializedName("image_hash")
    private String image_hash;

}
