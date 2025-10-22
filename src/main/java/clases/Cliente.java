/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author Lenovo LOQ
 */
public class Cliente {
    private String ced_cli;
    private String nom_cli;
    private String ape_cli;

    public Cliente(String ced_cli, String nom_cli, String ape_cli) {
        this.ced_cli = ced_cli;
        this.nom_cli = nom_cli;
        this.ape_cli = ape_cli;
    }

    public String getCed_cli() {
        return ced_cli;
    }

    public void setCed_cli(String ced_cli) {
        this.ced_cli = ced_cli;
    }

    public String getNom_cli() {
        return nom_cli;
    }

    public void setNom_cli(String nom_cli) {
        this.nom_cli = nom_cli;
    }

    public String getApe_cli() {
        return ape_cli;
    }

    public void setApe_cli(String ape_cli) {
        this.ape_cli = ape_cli;
    }
    
    
}
