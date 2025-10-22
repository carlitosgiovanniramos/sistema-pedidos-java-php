/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.math.BigDecimal;

/**
 *
 * @author Lenovo LOQ
 */
public class Producto {
    private String cod_pro;
    private String des_pro;
    private BigDecimal pre_uni;
    private int stock;

    public Producto(String cod_pro, String des_pro, BigDecimal pre_uni, int stock) {
        this.cod_pro = cod_pro;
        this.des_pro = des_pro;
        this.pre_uni = pre_uni;
        this.stock = stock;
    }

    public String getCod_pro() {
        return cod_pro;
    }

    public void setCod_pro(String cod_pro) {
        this.cod_pro = cod_pro;
    }

    public String getDes_pro() {
        return des_pro;
    }

    public void setDes_pro(String des_pro) {
        this.des_pro = des_pro;
    }

    public BigDecimal getPre_uni() {
        return pre_uni;
    }

    public void setPre_uni(BigDecimal pre_uni) {
        this.pre_uni = pre_uni;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
}
