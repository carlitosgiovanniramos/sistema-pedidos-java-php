/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clases;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lenovo LOQ
 */
public class interfazPedidos extends javax.swing.JFrame {

    /**
     * Creates new form interfazPedidos
     */
    DefaultTableModel modelo;
    DefaultListModel<String> lista;
    DefaultComboBoxModel<String> combo;
    clientePedidos clienteArray;

    public interfazPedidos() {
        initComponents();
        clienteArray = new clientePedidos();
        cargarTabla();
        cargarListaYCombo();
    }

    public void cargarTabla() {
        String[] columnas = {"Pedido", "Cliente", "Producto", "Cantidad"};
        String[] filas = new String[4];
        modelo = new DefaultTableModel(null, columnas);

        ArrayList<Pedidos> pedidos = clienteArray.obtenerPedidos();
        if (pedidos == null) {
            System.out.println("CargarTabla: obtenerPedidos() devolvió null");
            pedidos = new ArrayList<>();
        }
        if (pedidos.isEmpty()) {
            System.out.println("CargarTabla: no hay pedidos para mostrar");
        }
        for (Pedidos pedido : pedidos) {
            filas[0] = pedido.getId_ped();
            // Muestra CLIENTE (nombre legible) pero internamente tienes ced_cli_ped
            filas[1] = pedido.getCLIENTE() != null ? pedido.getCLIENTE() : pedido.getCed_cli_ped();
            // Muestra PRODUCTO (descripción legible) pero internamente tienes cod_pro_ped
            filas[2] = pedido.getPRODUCTO() != null ? pedido.getPRODUCTO() : pedido.getCod_pro_ped();
            filas[3] = String.valueOf(pedido.getCan_ped());
            modelo.addRow(filas);
        }
        jtblProductos.setModel(modelo);
    }
    
        public void cargarTablaCedula() {
        String[] columnas = {"Pedido", "Cliente", "Producto", "Cantidad"};
        String[] filas = new String[4];
        modelo = new DefaultTableModel(null, columnas);

        ArrayList<Pedidos> pedidos = clienteArray.obtenerPedidos();
        if (pedidos == null) {
            System.out.println("CargarTabla: obtenerPedidos() devolvió null");
            pedidos = new ArrayList<>();
        }
        if (pedidos.isEmpty()) {
            System.out.println("CargarTabla: no hay pedidos para mostrar");
        }
        for (Pedidos pedido : pedidos) {
            filas[0] = pedido.getId_ped();
            // Muestra CLIENTE (nombre legible) pero internamente tienes ced_cli_ped
            filas[1] = pedido.getCLIENTE() != null ? pedido.getCLIENTE() : pedido.getCed_cli_ped();
            // Muestra PRODUCTO (descripción legible) pero internamente tienes cod_pro_ped
            filas[2] = pedido.getPRODUCTO() != null ? pedido.getPRODUCTO() : pedido.getCod_pro_ped();
            filas[3] = String.valueOf(pedido.getCan_ped());
            modelo.addRow(filas);
        }
        jtblProductos.setModel(modelo);
    }

    public void cargarListaYCombo() {
        ArrayList<Pedidos> pedidos = clienteArray.obtenerPedidos();

        lista = new DefaultListModel<>();
        combo = new DefaultComboBoxModel<>();

        for (Pedidos pedido : pedidos) {
            String item = pedido.getCLIENTE();
            String item1 = pedido.getPRODUCTO();
            lista.addElement(item);
            combo.addElement(item1);
        }
        jListClientes.setModel(lista);
        jCbxProductos.setModel(combo);
    }

    public void insertar() {
        String id_ped = jtxtCodigoPedido.getText();
        String nombreCliente = (String) jListClientes.getSelectedValue();
        String nombreProducto = (String) jCbxProductos.getSelectedItem();
        String cantidad = jtxtCantidad.getText();

        if (nombreCliente == null || nombreProducto == null) {
            System.out.println("Debe seleccionar un cliente y un producto.");
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente y un producto.");
            return;
        }
        if (cantidad.isEmpty()) {
            System.out.println("Debe ingresar una cantidad.");
            JOptionPane.showMessageDialog(this, "Debe ingresar una cantidad.");
            return;
        }
        
        try {
            int cantidadInt = Integer.parseInt(cantidad);
            
            // Buscar los códigos correspondientes a los nombres seleccionados
            ArrayList<Pedidos> pedidos = clienteArray.obtenerPedidos();
            String ced_cli = null;
            String cod_pro = null;
            
            for (Pedidos pedido : pedidos) {
                if (nombreCliente.equals(pedido.getCLIENTE())) {
                    ced_cli = pedido.getCed_cli_ped();
                }
                if (nombreProducto.equals(pedido.getPRODUCTO())) {
                    cod_pro = pedido.getCod_pro_ped();
                }
                // Si ya encontramos ambos, podemos salir del loop
                if (ced_cli != null && cod_pro != null) {
                    break;
                }
            }
            
            if (ced_cli == null || cod_pro == null) {
                JOptionPane.showMessageDialog(this, "Error: No se encontraron los códigos del cliente o producto.");
                return;
            }
            
            // Crear el objeto Pedidos con los códigos correctos
            Pedidos nuevoPedido = new Pedidos();
            nuevoPedido.setId_ped(id_ped);
            nuevoPedido.setCed_cli_ped(ced_cli);
            nuevoPedido.setCod_pro_ped(cod_pro);
            nuevoPedido.setCan_ped(cantidadInt);

            // Insertar el pedido en la base de datos
            if (clienteArray.insertarPedidos(nuevoPedido)) {
                JOptionPane.showMessageDialog(this, "Pedido insertado correctamente");
                cargarTabla();
                cargarListaYCombo();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al insertar el pedido");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: La cantidad debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error: " + e.getMessage());
        }
    }
    
    private void limpiarCampos() {
        jtxtCodigoPedido.setText("");
        jtxtCantidad.setText("");
        jListClientes.clearSelection();
        jCbxProductos.setSelectedIndex(-1);
    }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtxtBuscarCodigo = new javax.swing.JTextField();
        jbtnBuscarCodigo = new javax.swing.JButton();
        jbtnP = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jtxtBuscarCedula = new javax.swing.JTextField();
        jbtnBuscarCedula = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevoP = new javax.swing.JButton();
        jbtnEditarP = new javax.swing.JButton();
        jbtnEliminarP = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jtxtCodigoPedido = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Pedido = new javax.swing.JLabel();
        jtxtCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jCbxProductos = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListClientes = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Buscar producto");

        jbtnBuscarCodigo.setText("Buscar");
        jbtnBuscarCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarCodigoActionPerformed(evt);
            }
        });

        jbtnP.setText("Todo");
        jbtnP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPActionPerformed(evt);
            }
        });

        jLabel5.setText("Buscar cedula");

        jtxtBuscarCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtBuscarCedulaActionPerformed(evt);
            }
        });

        jbtnBuscarCedula.setText("Buscar");

        jButton1.setText("Todo");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtBuscarCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jtxtBuscarCodigo))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jbtnBuscarCedula)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jbtnBuscarCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnP)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtBuscarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBuscarCodigo)
                    .addComponent(jbtnP))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtBuscarCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBuscarCedula)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblProductos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jbtnNuevoP.setText("Nuevo");
        jbtnNuevoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoPActionPerformed(evt);
            }
        });

        jbtnEditarP.setText("Editar");
        jbtnEditarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarPActionPerformed(evt);
            }
        });

        jbtnEliminarP.setText("Borrar");
        jbtnEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnNuevoP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnEditarP, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbtnEliminarP, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jbtnNuevoP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnEditarP)
                .addGap(18, 18, 18)
                .addComponent(jbtnEliminarP)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jtxtCodigoPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodigoPedidoActionPerformed(evt);
            }
        });

        jLabel1.setText("Cliente");

        jLabel2.setText("Producto");

        Pedido.setText("Codigo");

        jLabel6.setText("Cantidad");

        jCbxProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jListClientes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListClientes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(Pedido, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCbxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtCodigoPedido, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(jtxtCantidad))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCodigoPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Pedido))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jCbxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnBuscarCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarCodigoActionPerformed
        // TODO add your handling code here:
        String codigo = jtxtBuscarCodigo.getText();

    }//GEN-LAST:event_jbtnBuscarCodigoActionPerformed

    private void jbtnPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPActionPerformed

    }//GEN-LAST:event_jbtnPActionPerformed

    private void jbtnNuevoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoPActionPerformed
insertar();
    }//GEN-LAST:event_jbtnNuevoPActionPerformed

    private void jbtnEditarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarPActionPerformed
    }//GEN-LAST:event_jbtnEditarPActionPerformed

    private void jbtnEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarPActionPerformed
    }//GEN-LAST:event_jbtnEliminarPActionPerformed

    private void jtxtCodigoPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodigoPedidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodigoPedidoActionPerformed

    private void jtxtBuscarCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtBuscarCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtBuscarCedulaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(interfazPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfazPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfazPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfazPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfazPedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Pedido;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jCbxProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jListClientes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbtnBuscarCedula;
    private javax.swing.JButton jbtnBuscarCodigo;
    private javax.swing.JButton jbtnEditarP;
    private javax.swing.JButton jbtnEliminarP;
    private javax.swing.JButton jbtnNuevoP;
    private javax.swing.JButton jbtnP;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTextField jtxtBuscarCedula;
    private javax.swing.JTextField jtxtBuscarCodigo;
    private javax.swing.JTextField jtxtCantidad;
    private javax.swing.JTextField jtxtCodigoPedido;
    // End of variables declaration//GEN-END:variables
}
