package com.demo.jspdemo.entity;

public class Cart {
    //    pid         varchar(30) foreign key references Product (pid),
    //    cid         varchar(30) foreign key references Customer (cid),
    //    buyQuantity int,
    //    buyPrice    money,
    //    subtotal    money
    private String pid;
    private String cid;
    private int buyQuantity;

    public Cart() {
    }

    public Cart(String pid, String cid, int buyQuantity) {
        this.pid = pid;
        this.cid = cid;
        this.buyQuantity = buyQuantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }
}
