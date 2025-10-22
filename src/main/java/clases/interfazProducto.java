/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package clases;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Lenovo LOQ
 */
public class interfazProducto extends javax.swing.JFrame {

    /**
     * Creates new form interfazProducto
     */
    DefaultTableModel modelo;
    DefaultListModel<String> lista;
    DefaultComboBoxModel<String> combo;
    clienteProducto clienteArray;
    
    public interfazProducto() {
        initComponents();
        clienteArray = new clienteProducto();
        cargarTabla();
        seleccionarEstudiante();
        cargarListaYCombo();
    }
    
    public void cargarListaYCombo(){
    ArrayList<Producto> productos = clienteArray.obtenerProductos();
    
    lista = new DefaultListModel<>();
    combo = new DefaultComboBoxModel<>();
    
    for (Producto producto: productos) {
    String item = producto.getDes_pro();
    lista.addElement(item);
    combo.addElement(item);
    }
    jListProductos.setModel(lista);
    jCbxProductos.setModel(combo);
    }
    
    public void cargarListaYComboCodigo(String codigo){
    ArrayList<Producto> productos = clienteArray.obtenerProductoPorCodigo(codigo);
    
    lista = new DefaultListModel<>();
    combo = new DefaultComboBoxModel<>();
    
    for (Producto producto: productos) {
    String item = producto.getDes_pro();
    lista.addElement(item);
    combo.addElement(item);
    }
    jListProductos.setModel(lista);
    jCbxProductos.setModel(combo);
    }

    public void cargarTabla() {
        String[] columnas = {"Codigo", "Descripcion", "Precio Unitario", "Stock"};
        String[] filas = new String[4];
        modelo = new DefaultTableModel(null, columnas);

        ArrayList<Producto> productos = clienteArray.obtenerProductos();
        if (productos == null) {
            System.out.println("CargarTabla: obtenerClientes() devolvió null");
            productos = new ArrayList<>();
        }
        if (productos.isEmpty()) {
            System.out.println("CargarTabla: no hay clientes para mostrar");
        }
        for (Producto producto : productos) {
            filas[0] = producto.getCod_pro();
            filas[1] = producto.getDes_pro();
            filas[2] = String.valueOf(producto.getPre_uni());
            filas[3] = String.valueOf(producto.getStock());
            modelo.addRow(filas);
        }
        jtblProductos.setModel(modelo);
    }

    public void cargarTablaCodigo(String codigo) {
        String[] columnas = {"Codigo", "Descripcion", "Precio Unitario", "Stock"};
        String[] filas = new String[4];
        modelo = new DefaultTableModel(null, columnas);
        
        ArrayList<Producto> productos = clienteArray.obtenerProductoPorCodigo(codigo);
        if (productos == null) {
            System.out.println("CargarTabla: obtenerClientes() devolvió null");
            productos = new ArrayList<>();
        }
        if (productos.isEmpty()) {
            System.out.println("CargarTabla: no hay clientes para mostrar");
        }
        for (Producto producto : productos) {
            filas[0] = producto.getCod_pro();
            filas[1] = producto.getDes_pro();
            filas[2] = String.valueOf(producto.getPre_uni());
            filas[3] = String.valueOf(producto.getStock());
            modelo.addRow(filas);
        }
        jtblProductos.setModel(modelo);
    }

    public void insertar() {
        String cod_pro = jtxtCodigoP.getText();
        String des_pro = jtxtDescripcionP.getText();
        String pre_uni = jtxtPrecioP.getText();
        String stock = jtxtStockP1.getText();

        try {
            BigDecimal precioDecimal = new BigDecimal(pre_uni);
            int stockInt = Integer.parseInt(stock);

            // Crear el objeto Producto con los tipos correctos
            Producto producto = new Producto(cod_pro, des_pro, precioDecimal, stockInt);

            // Insertar el producto en la base de datos
            if (clienteArray.insertarProductos(producto)) {
                cargarTabla();
                cargarListaYCombo();
                limpiarTxt();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error: Verifica que el precio y el stock sean valores numéricos válidos.");
        }

    }

    public void editar() {
        String cod_pro = jtxtCodigoP.getText();
        String des_pro = jtxtDescripcionP.getText();
        String pre_uni = jtxtPrecioP.getText();
        String stock = jtxtStockP1.getText();
        try {
            BigDecimal precioDecimal = new BigDecimal(pre_uni);
            int stockInt = Integer.parseInt(stock);

            // Crear el objeto Producto con los tipos correctos
            Producto producto = new Producto(cod_pro, des_pro, precioDecimal, stockInt);

            // Insertar el producto en la base de datos
            if (clienteArray.editarProductos(producto)) {
                cargarTabla();
                cargarListaYCombo();
                limpiarTxt();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error: Verifica que el precio y el stock sean valores numéricos válidos.");
        }
    }

    public void eliminar() {
        String cod_pro = jtxtCodigoP.getText();
        if (clienteArray.eliminarProductos(cod_pro)) {
            cargarTabla();
            cargarListaYCombo();
            limpiarTxt();
        }
    }

    public void seleccionarEstudiante() {
        jtblProductos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblProductos.getSelectedRow() != -1) {
                    int fila = jtblProductos.getSelectedRow();
                    jtxtCodigoP.setText(jtblProductos.getValueAt(fila, 0).toString().trim());
                    jtxtDescripcionP.setText(jtblProductos.getValueAt(fila, 1).toString().trim());
                    jtxtPrecioP.setText(jtblProductos.getValueAt(fila, 2).toString().trim());
                    jtxtStockP1.setText(jtblProductos.getValueAt(fila, 3).toString().trim());
                }
            }
        });
    }

    public void limpiarTxt() {
        jtxtCodigoP.setText("");
        jtxtDescripcionP.setText("");
        jtxtPrecioP.setText("");
        jtxtStockP1.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtxtBuscarP = new javax.swing.JTextField();
        jbtnBuscarP = new javax.swing.JButton();
        jbtnP = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevoP = new javax.swing.JButton();
        jbtnEditarP = new javax.swing.JButton();
        jbtnEliminarP = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jtxtCodigoP = new javax.swing.JTextField();
        jtxtDescripcionP = new javax.swing.JTextField();
        jtxtPrecioP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtxtStockP1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblProductos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListProductos = new javax.swing.JList<>();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jCbxProductos = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Buscar");

        jbtnBuscarP.setText("Buscar");
        jbtnBuscarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBuscarPActionPerformed(evt);
            }
        });

        jbtnP.setText("Todo");
        jbtnP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxtBuscarP, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbtnBuscarP)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnP)
                .addGap(51, 51, 51))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtBuscarP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnBuscarP)
                    .addComponent(jbtnP))
                .addGap(10, 10, 10))
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

        jtxtCodigoP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCodigoPActionPerformed(evt);
            }
        });

        jLabel1.setText("Descripcion");

        jLabel2.setText("Precio");

        jLabel3.setText("Codigo");

        jLabel5.setText("Stock");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtCodigoP, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                    .addComponent(jtxtDescripcionP)
                    .addComponent(jtxtPrecioP)
                    .addComponent(jtxtStockP1))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCodigoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDescripcionP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPrecioP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtStockP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jListProductos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListProductos);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jCbxProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jScrollPane3.setViewportView(jCbxProductos);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnBuscarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBuscarPActionPerformed
        // TODO add your handling code here:
        String codigo = jtxtBuscarP.getText();
        cargarTablaCodigo(codigo);
        cargarListaYComboCodigo(codigo);
    }//GEN-LAST:event_jbtnBuscarPActionPerformed

    private void jbtnPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPActionPerformed

        cargarTabla(); 
        cargarListaYCombo();// TODO add your handling code here:
    }//GEN-LAST:event_jbtnPActionPerformed

    private void jbtnNuevoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoPActionPerformed

        insertar();        // TODO add your handling code here:

    }//GEN-LAST:event_jbtnNuevoPActionPerformed

    private void jbtnEditarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarPActionPerformed
        editar();
    }//GEN-LAST:event_jbtnEditarPActionPerformed

    private void jbtnEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarPActionPerformed
        eliminar();
    }//GEN-LAST:event_jbtnEliminarPActionPerformed

    private void jtxtCodigoPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCodigoPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCodigoPActionPerformed

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
            java.util.logging.Logger.getLogger(interfazProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(interfazProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(interfazProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(interfazProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new interfazProducto().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jCbxProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jListProductos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbtnBuscarP;
    private javax.swing.JButton jbtnEditarP;
    private javax.swing.JButton jbtnEliminarP;
    private javax.swing.JButton jbtnNuevoP;
    private javax.swing.JButton jbtnP;
    private javax.swing.JTable jtblProductos;
    private javax.swing.JTextField jtxtBuscarP;
    private javax.swing.JTextField jtxtCodigoP;
    private javax.swing.JTextField jtxtDescripcionP;
    private javax.swing.JTextField jtxtPrecioP;
    private javax.swing.JTextField jtxtStockP1;
    // End of variables declaration//GEN-END:variables
}
