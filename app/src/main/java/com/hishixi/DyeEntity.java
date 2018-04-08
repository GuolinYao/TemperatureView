package com.hishixi;

import java.io.Serializable;

/**
 * Created by seamus on 2018/3/19 14:25
 */
public class DyeEntity implements Serializable {
    private final static long serialVersionUID = 111;
    //批号
    private String lotid;
    //客户代号
    private String lotcus;
    //重量
    private String lotweight;
    //长度
    private String lotlength;
    //克重
    private String lotweightscale;
    //宽度
    private String lotwidth;
    //卷装形式
    private String lotroll;
    //批类型
    private String lottype;
    //染色品群组
    private String dyegroup;
    //品名
    private String quality;
    //色号
    private String shade;
    //机器群组
    private String lotmachgroup;
    //染程编号
    private String dyeno;
    //机器编号
    private String lotmach;
    //总时间
    private String secondes;
    //上布时间
    private String addquality;
    //操作工编号
    private String lothandler;
    //交货时间
    private String lotdeliver;
    //创建人
    private String lotown;

    //创建时间
    private String starttime;
    //备注
    private String remarks;

    public DyeEntity(String lotid, String lotcus, String lotweight, String lotlength, String
            lotweightscale, String lotwidth, String lotroll, String lottype, String dyegroup,
                     String quality, String shade, String lotmachgroup, String dyeno, String
                             lotmach, String secondes, String addquality, String lothandler,
                     String lotdeliver, String lotown, String remarks, String starttime) {
        this.lotid = lotid;
        this.lotcus = lotcus;
        this.lotweight = lotweight;
        this.lotlength = lotlength;
        this.lotweightscale = lotweightscale;
        this.lotwidth = lotwidth;
        this.lotroll = lotroll;
        this.lottype = lottype;
        this.dyegroup = dyegroup;
        this.quality = quality;
        this.shade = shade;
        this.lotmachgroup = lotmachgroup;
        this.dyeno = dyeno;
        this.lotmach = lotmach;
        this.secondes = secondes;
        this.addquality = addquality;
        this.lothandler = lothandler;
        this.lotdeliver = lotdeliver;
        this.lotown = lotown;
        this.remarks = remarks;
        this.starttime = starttime;
    }
    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    public String getLotroll() {
        return lotroll;
    }

    public void setLotroll(String lotroll) {
        this.lotroll = lotroll;
    }

    public String getLottype() {
        return lottype;
    }

    public void setLottype(String lottype) {
        this.lottype = lottype;
    }

    public String getDyegroup() {
        return dyegroup;
    }

    public void setDyegroup(String dyegroup) {
        this.dyegroup = dyegroup;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getShade() {
        return shade;
    }

    public void setShade(String shade) {
        this.shade = shade;
    }

    public String getLotmachgroup() {
        return lotmachgroup;
    }

    public void setLotmachgroup(String lotmachgroup) {
        this.lotmachgroup = lotmachgroup;
    }

    public String getDyeno() {
        return dyeno;
    }

    public void setDyeno(String dyeno) {
        this.dyeno = dyeno;
    }

    public String getLotmach() {
        return lotmach;
    }

    public void setLotmach(String lotmach) {
        this.lotmach = lotmach;
    }

    public String getSecondes() {
        return secondes;
    }

    public void setSecondes(String secondes) {
        this.secondes = secondes;
    }

    public String getAddquality() {
        return addquality;
    }

    public void setAddquality(String addquality) {
        this.addquality = addquality;
    }

    public String getLothandler() {
        return lothandler;
    }

    public void setLothandler(String lothandler) {
        this.lothandler = lothandler;
    }

    public String getLotdeliver() {
        return lotdeliver;
    }

    public void setLotdeliver(String lotdeliver) {
        this.lotdeliver = lotdeliver;
    }

    public String getLotown() {
        return lotown;
    }

    public void setLotown(String lotown) {
        this.lotown = lotown;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public DyeEntity() {
    }

    public String getLotid() {
        return this.lotid;
    }

    public void setLotid(String lotid) {
        this.lotid = lotid;
    }

    public String getLotcus() {
        return this.lotcus;
    }

    public void setLotcus(String lotcus) {
        this.lotcus = lotcus;
    }

    public String getLotweight() {
        return this.lotweight;
    }

    public void setLotweight(String lotweight) {
        this.lotweight = lotweight;
    }

    public String getLotlength() {
        return this.lotlength;
    }

    public void setLotlength(String lotlength) {
        this.lotlength = lotlength;
    }

    public String getLotweightscale() {
        return this.lotweightscale;
    }

    public void setLotweightscale(String lotweightscale) {
        this.lotweightscale = lotweightscale;
    }

    public String getLotwidth() {
        return this.lotwidth;
    }

    public void setLotwidth(String lotwidth) {
        this.lotwidth = lotwidth;
    }


}
