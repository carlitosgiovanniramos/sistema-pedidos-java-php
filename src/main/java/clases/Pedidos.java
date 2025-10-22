/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author Lenovo LOQ
 */
public class Pedidos {
    
    private String id_ped;
    private String ced_cli_ped;
    private String cod_pro_ped;
    private int can_ped;
    // Campos para mapear el JSON de la API (CLIENTE y PRODUCTO nombres legibles)
    private String CLIENTE;
    private String PRODUCTO;

    public Pedidos(String id_ped, String ced_cli_ped, String cod_pro_ped, int can_ped) {
        this.id_ped = id_ped;
        this.ced_cli_ped = ced_cli_ped;
        this.cod_pro_ped = cod_pro_ped;
        this.can_ped = can_ped;
    }

    // Constructor por defecto (necesario para Gson)
    public Pedidos() {
    }

    public String getId_ped() {
        return id_ped;
    }

    public void setId_ped(String id_ped) {
        this.id_ped = id_ped;
    }

    public String getCed_cli_ped() {
        return ced_cli_ped;
    }

    public void setCed_cli_ped(String ced_cli_ped) {
        this.ced_cli_ped = ced_cli_ped;
    }

    public String getCod_pro_ped() {
        return cod_pro_ped;
    }

    public void setCod_pro_ped(String cod_pro_ped) {
        this.cod_pro_ped = cod_pro_ped;
    }

    public int getCan_ped() {
        return can_ped;
    }

    public void setCan_ped(int can_ped) {
        this.can_ped = can_ped;
    }

    public String getCLIENTE() {
        return CLIENTE;
    }

    public void setCLIENTE(String CLIENTE) {
        this.CLIENTE = CLIENTE;
    }

    public String getPRODUCTO() {
        return PRODUCTO;
    }

    public void setPRODUCTO(String PRODUCTO) {
        this.PRODUCTO = PRODUCTO;
    }
    
    
    
}
