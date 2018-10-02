/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import DAO.ChuyenDeDAO;
import DAO.KhoaHocDAO;
import MODEL.ChuyenDe;
import MODEL.KhoaHoc;
import helper.DateHelper;
import helper.DialogHelper;
import helper.ShareHelper;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class KhoaHocJFrame extends javax.swing.JFrame {

    int index = 0;
    KhoaHocDAO dao = new KhoaHocDAO();
    ChuyenDeDAO cddao = new ChuyenDeDAO();

    void load() {
        DefaultTableModel model = (DefaultTableModel) tblgridview.getModel();
        model.setRowCount(0);
        try {
            List<KhoaHoc> list = dao.select();
            for (KhoaHoc kh : list) {
                Object[] row = {
                    kh.getMaKH(),
                    kh.getMaCD(),
                    kh.getThoiLuong(),
                    kh.getHocPhi(),
                    DateHelper.toString(kh.getNgayKG()),
                    kh.getMaNV(),
                    DateHelper.toString(kh.getNgayTao())
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void insert() {
        KhoaHoc model = getModel();
        model.setNgayTao(new Date());
        try {
            dao.insert(model);
            this.load();
            this.clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
        }
    }

    void update() {
        KhoaHoc model = getModel();
        try {
            dao.update(model);
            this.load();

            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bài!");
        }

    }

    void delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa khóa học này?")) {
            Integer makh = Integer.valueOf(cbochuyende.getToolTipText());
            try {
                dao.delete(makh);
                this.load();
                this.clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    void clear() {
        KhoaHoc model = new KhoaHoc();
        String chuyende = (String) cbochuyende.getSelectedItem();
        ChuyenDe a = cddao.findByName(chuyende);
        model.setMaCD(a.getMaCD());
        model.setMaNV(ShareHelper.USER.getMaNV());
        model.setNgayKG(DateHelper.add(30));
        model.setNgayTao(DateHelper.now());

        this.setModel(model);
    }

    void edit() {
        try {
            Integer makh = (Integer) tblgridview.getValueAt(this.index, 0);
            KhoaHoc model = dao.findById(makh);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(KhoaHoc model) {
        cbochuyende.setToolTipText(String.valueOf(model.getMaKH()));
        ChuyenDe cd = cddao.findById(model.getMaCD());
        cbochuyende.setSelectedItem(cd.getTenCD());
        txtngaykhaigiang.setText(DateHelper.toString(model.getNgayKG()));
        txthocphi.setText(String.valueOf(model.getHocPhi()));
        txtthoiluong.setText(String.valueOf(model.getThoiLuong()));
        txtmnv.setText(model.getMaNV());
        txtngaytao.setText(DateHelper.toString(model.getNgayTao()));
        txtghichu.setText(model.getGhiChu());

    }

    KhoaHoc getModel() {
        KhoaHoc model = new KhoaHoc();
        ChuyenDe chuyenDe = (ChuyenDe) cbochuyende.getSelectedItem();
        model.setMaCD(chuyenDe.getMaCD());
        model.setNgayKG(DateHelper.toDate(txtngaykhaigiang.getText()));
        model.setHocPhi(Double.valueOf(txthocphi.getText()));
        model.setThoiLuong(Integer.valueOf(txtthoiluong.getText()));
        model.setGhiChu(txtghichu.getText());
        model.setMaNV(ShareHelper.USER.getMaNV());
        model.setNgayTao(DateHelper.toDate(txtngaytao.getText()));
        model.setMaKH(Integer.valueOf(cbochuyende.getToolTipText()));
        return model;
    }

    void setStatus(boolean insertable) {
        btninsert.setEnabled(insertable);
        btnupdate.setEnabled(!insertable);
        btndelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblgridview.getRowCount() - 1;
        btnfirst.setEnabled(!insertable && first);
        btnprev.setEnabled(!insertable && first);
        btnlast.setEnabled(!insertable && last);
        btnnext.setEnabled(!insertable && last);

        btnhocvien.setVisible(!insertable);
    }

    void selectComboBox() {
        String a = (String) cbochuyende.getSelectedItem();
        ChuyenDe chuyende = cddao.findByName(a);
        txtthoiluong.setText(String.valueOf(chuyende.getThoiLuong()));
        txthocphi.setText(String.valueOf(chuyende.getHocPhi()));
    }

    void openHocVien() {
        Integer id = Integer.valueOf(cbochuyende.getToolTipText());
        new HocVienJFrame(id).setVisible(true);
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbochuyende.getModel();
        model.removeAllElements();
        try {
            List<ChuyenDe> list = cddao.select();
            for (ChuyenDe cd : list) {
                model.addElement(cd.getTenCD());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    /**
     * Creates new form KhoaHocJFrame
     */
    public KhoaHocJFrame() {
        initComponents();
        init();
    }
    void init(){
        setIconImage(ShareHelper.APP_ICON);
        setLocationRelativeTo(null);
        txthocphi.setEditable(false);
        txtngaykhaigiang.setEditable(false);
        txtngaytao.setEditable(false);
        txtthoiluong.setEditable(false);
        txtmnv.setEditable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbltitle = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        pnledit = new javax.swing.JPanel();
        lblchuyende = new javax.swing.JLabel();
        lblmanv = new javax.swing.JLabel();
        lblhocphi = new javax.swing.JLabel();
        lblngaykhiagiang = new javax.swing.JLabel();
        lblghichu = new javax.swing.JLabel();
        txthocphi = new javax.swing.JTextField();
        txtngaykhaigiang = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnclear = new javax.swing.JButton();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btninsert = new javax.swing.JButton();
        btnfirst = new javax.swing.JButton();
        btnprev = new javax.swing.JButton();
        btnnext = new javax.swing.JButton();
        btnlast = new javax.swing.JButton();
        btnhocvien = new javax.swing.JButton();
        cbochuyende = new javax.swing.JComboBox<>();
        lblthoiluong = new javax.swing.JLabel();
        txtngaytao = new javax.swing.JTextField();
        lblngaytao = new javax.swing.JLabel();
        txtmnv = new javax.swing.JTextField();
        txtthoiluong = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtghichu = new javax.swing.JTextArea();
        pnllist = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblgridview = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Hệ thống quản lý khóa học");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lbltitle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbltitle.setForeground(new java.awt.Color(51, 0, 255));
        lbltitle.setText("QUẢN LÝ KHÓA HỌC");

        lblchuyende.setText("Chuyên đề");

        lblmanv.setText("Người tạo");

        lblhocphi.setText("Học phí");

        lblngaykhiagiang.setText("Ngày khai giảng");

        lblghichu.setText("Ghi chú");

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

        btnhocvien.setText("Học Viên");
        btnhocvien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhocvienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnfirst)
                        .addGap(31, 31, 31)
                        .addComponent(btnprev))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btninsert)
                        .addGap(55, 55, 55)
                        .addComponent(btndelete)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnnext))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnupdate)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnlast)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnclear)
                        .addGap(18, 18, 18)
                        .addComponent(btnhocvien)))
                .addGap(242, 242, 242))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnupdate)
                    .addComponent(btndelete)
                    .addComponent(btnclear)
                    .addComponent(btninsert)
                    .addComponent(btnhocvien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnlast)
                    .addComponent(btnnext)
                    .addComponent(btnprev)
                    .addComponent(btnfirst))
                .addGap(44, 44, 44))
        );

        cbochuyende.setAutoscrolls(true);
        cbochuyende.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbochuyendeItemStateChanged(evt);
            }
        });
        cbochuyende.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbochuyendeMouseClicked(evt);
            }
        });
        cbochuyende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbochuyendeActionPerformed(evt);
            }
        });

        lblthoiluong.setText("Thời lượng(giời)");

        lblngaytao.setText("Ngày tạo");

        txtghichu.setColumns(20);
        txtghichu.setRows(5);
        jScrollPane2.setViewportView(txtghichu);

        javax.swing.GroupLayout pnleditLayout = new javax.swing.GroupLayout(pnledit);
        pnledit.setLayout(pnleditLayout);
        pnleditLayout.setHorizontalGroup(
            pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnleditLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblghichu)
                    .addGroup(pnleditLayout.createSequentialGroup()
                        .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblchuyende)
                            .addGroup(pnleditLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(txtmnv, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(73, 73, 73)
                        .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblngaykhiagiang)
                            .addComponent(lblngaytao)
                            .addComponent(txtngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblmanv)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnleditLayout.createSequentialGroup()
                        .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblhocphi)
                            .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txthocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnleditLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(cbochuyende, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(73, 73, 73)
                        .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtngaykhaigiang, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
                            .addComponent(txtthoiluong)
                            .addComponent(lblthoiluong))))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        pnleditLayout.setVerticalGroup(
            pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnleditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblchuyende)
                    .addComponent(lblngaykhiagiang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngaykhaigiang, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbochuyende, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblhocphi)
                    .addComponent(lblthoiluong, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txthocphi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtthoiluong, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblngaytao, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblmanv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnleditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtngaytao, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtmnv, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblghichu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("CẬP NHẬT", pnledit);

        tblgridview.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khóa học", "Chuyên đề", "Thời lượng", "Học phí", "Khai giảng", "Tạo bởi", "Ngày tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
                .addGap(82, 82, 82)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
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

            if (this.index >= 0) {
                this.edit();
                tabs.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_tblgridviewMouseClicked

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btninsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btninsertActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btndeleteActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btnfirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfirstActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnfirstActionPerformed

    private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
        // TODO add your handling code here:
        this.index++;
        this.edit();
    }//GEN-LAST:event_btnnextActionPerformed

    private void btnprevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnprevActionPerformed
        // TODO add your handling code here:
        this.index--;
        this.edit();
    }//GEN-LAST:event_btnprevActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
        // TODO add your handling code here:
        this.index = tblgridview.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnlastActionPerformed

    private void cbochuyendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbochuyendeActionPerformed
        // TODO add your handling code here:
        selectComboBox();
    }//GEN-LAST:event_cbochuyendeActionPerformed

    private void cbochuyendeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbochuyendeMouseClicked
        // TODO add your handling code here:
     
        selectComboBox();
    }//GEN-LAST:event_cbochuyendeMouseClicked

    private void cbochuyendeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbochuyendeItemStateChanged
        // TODO add your handling code here:
        selectComboBox();
    }//GEN-LAST:event_cbochuyendeItemStateChanged

    private void btnhocvienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhocvienActionPerformed
        // TODO add your handling code here:
        this.openHocVien();
    }//GEN-LAST:event_btnhocvienActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.fillComboBox();
        this.load();
        this.clear();
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

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
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhoaHocJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhoaHocJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnclear;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btnfirst;
    private javax.swing.JButton btnhocvien;
    private javax.swing.JButton btninsert;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnext;
    private javax.swing.JButton btnprev;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox<String> cbochuyende;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblchuyende;
    private javax.swing.JLabel lblghichu;
    private javax.swing.JLabel lblhocphi;
    private javax.swing.JLabel lblmanv;
    private javax.swing.JLabel lblngaykhiagiang;
    private javax.swing.JLabel lblngaytao;
    private javax.swing.JLabel lblthoiluong;
    private javax.swing.JLabel lbltitle;
    private javax.swing.JPanel pnledit;
    private javax.swing.JPanel pnllist;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblgridview;
    private javax.swing.JTextArea txtghichu;
    private javax.swing.JTextField txthocphi;
    private javax.swing.JTextField txtmnv;
    private javax.swing.JTextField txtngaykhaigiang;
    private javax.swing.JTextField txtngaytao;
    private javax.swing.JTextField txtthoiluong;
    // End of variables declaration//GEN-END:variables
}
