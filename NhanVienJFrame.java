/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import DAO.NhanvienDAO;
import MODEL.NhanVien;
import helper.DialogHelper;
import helper.ShareHelper;
import java.awt.Color;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class NhanVienJFrame extends javax.swing.JFrame {
    int index =0;
    NhanvienDAO dao = new NhanvienDAO();
    /**
     * Creates new form NhanVienJFrame
     */
    void load(){
        DefaultTableModel model = (DefaultTableModel) tblgridview.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = dao.select();
            for (NhanVien nv : list) {
               Object[] row = {
                   nv.getMaNV(),
                   nv.getMatkhau(),
                   nv.getHoTen(),
                   nv.isVaiTro() ?"Trưởng Phòng":"Nhân viên"
               } ;
               model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu !");
        }
    }
    void Insert(){
        NhanVien model = getModel();
        
        String confirm = new String(txtxacnhanmatkhau.getPassword());
        if (confirm.equals(model.getMatkhau())) {
            try {
                dao.insert(model);
                this.load();
                this.Clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm mới thất bại");
            }
        }
    }
    void Update(){
        NhanVien model = getModel();
        String confirm = new String(txtxacnhanmatkhau.getPassword());
        if (!confirm.equals(model.getMatkhau())) {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        }else{
            try {
                dao.update(model);
                this.load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại");
            }
        }
    }
    void Delete(){
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này ?")) {
            String manv = txtmanhanvien.getText();
            try {
                dao.delete(manv);
                this.load();
                this.Clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại");
            }
        }
    }
    void edit(){
        try {
            String manv = (String)tblgridview.getValueAt(this.index, 0);
            NhanVien model = dao.findByld(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }
    void Clear(){
        this.setModel(new NhanVien());
        this.setStatus(true);
    }
    void setModel(NhanVien model){
        txtmanhanvien.setText(model.getMaNV());
        txthoten.setText(model.getHoTen());
        txtmatkhau.setText(model.getMatkhau());
        txtxacnhanmatkhau.setText(model.getMatkhau());
        rdotruongphong.setSelected(model.isVaiTro());
        rdonhanvien.setSelected(!model.isVaiTro());
    }
    NhanVien getModel(){
        NhanVien model = new NhanVien();
        model.setMaNV(txtmanhanvien.getText());
        model.setHoTen(txthoten.getText());
        model.setMatkhau(new String (txtmatkhau.getPassword()));
        model.setVaiTro(rdotruongphong.isSelected());
        return model;
    }
    void setStatus(boolean insertable){
        txtmanhanvien.setEditable(insertable);
        btninsert.setEnabled(insertable);
        btnupdate.setEnabled(!insertable);
        btndelete.setEnabled(!insertable);
        
        
        boolean firt = this.index >0;
        boolean last = this.index < tblgridview.getRowCount() -1;
        btnfirst.setEnabled(!insertable && firt);
        btnprev.setEnabled(!insertable && firt);
        btnnext.setEnabled(!insertable && last);
        btnlast.setEnabled(!insertable && last);
    }
    public NhanVienJFrame() {
        initComponents();
        Init();
    }
    void Init(){
        setIconImage(ShareHelper.APP_ICON);
        setLocationRelativeTo(null);
    }
    public boolean check(){
        
        if (txthoten.getText().equals("")) {
//            DialogHelper.alert(this, "bạn chưa nhập tên");
              lblhot.setText("họ tên rỗng");
              lblhot.setBackground(Color.red);
              return false;
        }
//        if (txtmatkhau.equals("") && txtmatkhau.) {
//            
//        }
        if (txtmanhanvien.getText().equals("")) {
            lblmnv.setText("mã nhân viên rỗng");
            lblmnv.setBackground(Color.red);
            return false;
        }
        return true;
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jCheckBox1 = new javax.swing.JCheckBox();
        lbltitle = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnledit = new javax.swing.JPanel();
        lblmanv = new javax.swing.JLabel();
        lblmatkhau = new javax.swing.JLabel();
        lblxacnhanmatkhau = new javax.swing.JLabel();
        lblhoten = new javax.swing.JLabel();
        lblvaitro = new javax.swing.JLabel();
        txtmanhanvien = new javax.swing.JTextField();
        txthoten = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnclear = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btninsert = new javax.swing.JButton();
        btnfirst = new javax.swing.JButton();
        btnprev = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();
        txtmatkhau = new javax.swing.JPasswordField();
        txtxacnhanmatkhau = new javax.swing.JPasswordField();
        lblmnv = new javax.swing.JLabel();
        lblmk = new javax.swing.JLabel();
        lblhot = new javax.swing.JLabel();
        rdonhanvien = new javax.swing.JRadioButton();
        rdotruongphong = new javax.swing.JRadioButton();
        lblxacnhanmk = new javax.swing.JLabel();
        pnllist = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblgridview = new javax.swing.JTable();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("QUẢN LÝ NHÂN VIÊN");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbltitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltitle.setForeground(new java.awt.Color(51, 0, 255));
        lbltitle.setText("QUẢN LÝ NHÂN VIÊN QUẢN TRỊ");

        lblmanv.setText("Mã nhân viên");

        lblmatkhau.setText("Mật khẩu");

        lblxacnhanmatkhau.setText("Xác nhận mật khẩu");

        lblhoten.setText("Họ tên");

        lblvaitro.setText("Vai trò");

        btnclear.setText("Mới");
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });

        btndelete.setText("Xóa");
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });

        btnupdate.setText("Sửa");
        btnupdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdateActionPerformed(evt);
            }
        });

        btninsert.setText("Thêm");
        btninsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertActionPerformed(evt);
            }
        });

        btnfirst.setText("|<");
        btnfirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfirstActionPerformed(evt);
            }
        });

        btnprev.setText("<<");
        btnprev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnprevActionPerformed(evt);
            }
        });

        btnnext.setText(">>");
        btnnext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextActionPerformed(evt);
            }
        });

        btnlast.setText(">|");
        btnlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlastActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(290, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btninsert)
                        .addGap(48, 48, 48)
                        .addComponent(btnupdate)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnfirst)
                        .addGap(45, 45, 45)
                        .addComponent(btnprev)
                        .addGap(41, 41, 41)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnnext)
                    .addComponent(btndelete))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnlast))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(btnclear)))
                .addGap(255, 255, 255))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndelete)
                    .addComponent(btnclear)
                    .addComponent(btnupdate)
                    .addComponent(btninsert))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlast)
                    .addComponent(btnnext)
                    .addComponent(btnprev)
                    .addComponent(btnfirst)))
        );

        buttonGroup1.add(rdonhanvien);
        rdonhanvien.setText("Nhân viên");

        buttonGroup1.add(rdotruongphong);
        rdotruongphong.setSelected(true);
        rdotruongphong.setText("Trưởng phòng");

        javax.swing.GroupLayout pnleditLayout = new javax.swing.GroupLayout(pnledit);
        pnledit.setLayout(pnleditLayout);
        pnleditLayout.setHorizontalGroup(
            pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnleditLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblvaitro)
                        .addComponent(lblmanv)
                        .addComponent(lblmatkhau)
                        .addComponent(lblxacnhanmatkhau)
                        .addComponent(lblhoten)
                        .addGroup(pnleditLayout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtmanhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblmnv, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblmk, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtxacnhanmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 848, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblhot, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnleditLayout.createSequentialGroup()
                                    .addComponent(rdotruongphong)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(rdonhanvien))
                                .addComponent(lblxacnhanmk, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnleditLayout.setVerticalGroup(
            pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnleditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblmanv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmanhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblmnv, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblmatkhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblmk, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblxacnhanmatkhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtxacnhanmatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblxacnhanmk, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblhoten)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txthoten, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(lblhot, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblvaitro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdotruongphong)
                    .addComponent(rdonhanvien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", pnledit);

        tblgridview.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên", "Mật khẩu", "Họ và tên", "Vai trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblgridview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblgridviewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblgridview);

        javax.swing.GroupLayout pnllistLayout = new javax.swing.GroupLayout(pnllist);
        pnllist.setLayout(pnllistLayout);
        pnllistLayout.setHorizontalGroup(
            pnllistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnllistLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 894, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnllistLayout.setVerticalGroup(
            pnllistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnllistLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        tabs.addTab("DANH SÁCH", pnllist);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabs, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbltitle, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblgridviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblgridviewMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.index = tblgridview.rowAtPoint(evt.getPoint());
            if (this.index >=0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblgridviewMouseClicked

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        Delete();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        Update();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
        // TODO add your handling code here:
        if (check()) {
            Insert();
        }
        
    }//GEN-LAST:event_btninsertActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.load();
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfirstActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnfirstActionPerformed

    private void btnprevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprevActionPerformed
        // TODO add your handling code here:
        this.index--;
        this.edit();
    }//GEN-LAST:event_btnprevActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        // TODO add your handling code here:
        this.index++;
    }//GEN-LAST:event_btnnextActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
        // TODO add your handling code here:
        this.index = tblgridview.getRowCount() -1;
        this.edit();
    }//GEN-LAST:event_btnlastActionPerformed

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
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnfirst;
    private javax.swing.JButton btninsert;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnext;
    private javax.swing.JButton btnprev;
    private javax.swing.JButton btnupdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblhot;
    private javax.swing.JLabel lblhoten;
    private javax.swing.JLabel lblmanv;
    private javax.swing.JLabel lblmatkhau;
    private javax.swing.JLabel lblmk;
    private javax.swing.JLabel lblmnv;
    private javax.swing.JLabel lbltitle;
    private javax.swing.JLabel lblvaitro;
    private javax.swing.JLabel lblxacnhanmatkhau;
    private javax.swing.JLabel lblxacnhanmk;
    private javax.swing.JPanel pnledit;
    private javax.swing.JPanel pnllist;
    private javax.swing.JRadioButton rdonhanvien;
    private javax.swing.JRadioButton rdotruongphong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblgridview;
    private javax.swing.JTextField txthoten;
    private javax.swing.JTextField txtmanhanvien;
    private javax.swing.JPasswordField txtmatkhau;
    private javax.swing.JPasswordField txtxacnhanmatkhau;
    // End of variables declaration//GEN-END:variables
}
