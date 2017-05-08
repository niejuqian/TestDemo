package com.testdemo.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2017 04 06 10:41
 * @DESC：区域表
 */
@DatabaseTable(tableName = "sqt_area")
public class Area {
    @DatabaseField(generatedId = true)
    private Long area_code;

    @DatabaseField
    private Long province_code;

    @DatabaseField
    private Long city_code;

    @DatabaseField
    private Long district_code;

    @DatabaseField
    private Long town_code;

    @DatabaseField
    private String province_name;

    @DatabaseField
    private String city_name;

    @DatabaseField
    private String district_name;

    @DatabaseField
    private String town_name;

    @DatabaseField
    private Integer area_level;

    @DatabaseField
    private String first_letter;


    @DatabaseField
    private String city_short;

    @DatabaseField
    private Integer zip_code;

    @DatabaseField
    private String date_created;

    @DatabaseField
    private String date_updated;

    @DatabaseField
    private String logo_url;

    @DatabaseField
    private String is_open_flag;

    @DatabaseField
    private String is_hot;

    public Long getArea_code() {
        return area_code;
    }

    public void setArea_code(Long area_code) {
        this.area_code = area_code;
    }

    public Long getProvince_code() {
        return province_code;
    }

    public void setProvince_code(Long province_code) {
        this.province_code = province_code;
    }

    public Long getCity_code() {
        return city_code;
    }

    public void setCity_code(Long city_code) {
        this.city_code = city_code;
    }

    public Long getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(Long district_code) {
        this.district_code = district_code;
    }

    public Long getTown_code() {
        return town_code;
    }

    public void setTown_code(Long town_code) {
        this.town_code = town_code;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getTown_name() {
        return town_name;
    }

    public void setTown_name(String town_name) {
        this.town_name = town_name;
    }

    public Integer getArea_level() {
        return area_level;
    }

    public void setArea_level(Integer area_level) {
        this.area_level = area_level;
    }

    public String getFirst_letter() {
        return first_letter;
    }

    public void setFirst_letter(String first_letter) {
        this.first_letter = first_letter;
    }

    public String getCity_short() {
        return city_short;
    }

    public void setCity_short(String city_short) {
        this.city_short = city_short;
    }

    public Integer getZip_code() {
        return zip_code;
    }

    public void setZip_code(Integer zip_code) {
        this.zip_code = zip_code;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getIs_open_flag() {
        return is_open_flag;
    }

    public void setIs_open_flag(String is_open_flag) {
        this.is_open_flag = is_open_flag;
    }

    public String getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }
}
