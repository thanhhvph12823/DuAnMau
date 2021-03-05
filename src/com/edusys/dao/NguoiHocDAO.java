/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edusys.dao;

import com.edusys.entity.NguoiHoc;
import com.edusys.utils.JDBCHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thanhhvph12823
 */
public class NguoiHocDAO extends EduSysDAO<NguoiHoc, String> {

    @Override
    public void insert(NguoiHoc model) {
        String sql = "INSERT INTO NGUOIHOC (MANH, HOTEN, NGAYSINH, GIOITINH, DIENTHOAI, EMAIL, GHICHU, MANV) VALUES( ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)";
        JDBCHelper.executeUpdate(sql,
                model.getMaNH(),
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV());
    }

    @Override
    public void update(NguoiHoc model) {
        String sql = "UPDATE NGUOIHOC SET HOTEN=?, NGAYSINH=?, GIOITINH=?, DIENTHOAI=?, EMAIL=?, GHICHU=?, MANV =  ? WHERE  MANH =  ?";
        JDBCHelper.executeUpdate(sql,
                model.getHoTen(),
                model.getNgaySinh(),
                model.isGioiTinh(),
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaNH());
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM NGUOIHOC WHERE MANH=?";
        JDBCHelper.executeUpdate(sql, id);
    }

    @Override
    public List<NguoiHoc> selectAll() {
        String sql = "SELECT * FROM NGUOIHOC";
        return selectBySQL(sql);
    }

    @Override
    public NguoiHoc selectByID(String key) {
        String sql = "SELECT * FROM NGUOIHOC WHERE MANH=?";
        List<NguoiHoc> list = selectBySQL(sql, key);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    protected List<NguoiHoc> selectBySQL(String sql, Object... args) {
        List<NguoiHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JDBCHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NguoiHoc model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private NguoiHoc readFromResultSet(ResultSet rs) throws SQLException {
        NguoiHoc model = new NguoiHoc();
        model.setMaNH(rs.getString("MANH"));
        model.setHoTen(rs.getString("HOTEN"));
        model.setNgaySinh(rs.getDate("NGAYSINH"));
        model.setGioiTinh(rs.getBoolean("GIOITINH"));
        model.setDienThoai(rs.getString("DIENTHOAI"));
        model.setEmail(rs.getString("EMAIL"));
        model.setGhiChu(rs.getString("GHICHU"));
        model.setMaNV(rs.getString("MANV"));
        model.setNgayDK(rs.getDate("NGAYDK"));
        return model;
    }

    public List<NguoiHoc> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM NGUOIHOC WHERE HOTEN LIKE ?";
        return selectBySQL(sql, "%" + keyword + "%");
    }

    public List<NguoiHoc> selectByCourse(Integer makh) {
        String sql = "SELECT * FROM NGUOIHOC WHERE MANH NOT IN (SELECT MANH FROM HOCVIEN WHERE MaKH=?)";
        return selectBySQL(sql, makh);
    }
}
