package accountaccess;

import java.sql.*;

public class BangDangNhap {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        String url = "jdbc:mysql://sql.freedb.tech:3306/freedb_Tetris";
        String username = "freedb_user1";
        String password = "qN?825jv2yzPBnc";

        String tenDangNhap = "new_user";
        String matKhauDangNhap = "userpassword";
        try (Connection LuongKetNoiCSDL = DriverManager.getConnection(url, username, password)) {
            System.out.println("KET NOI THANH CONG");
            String mauTruyVan = "SELECT password FROM Users WHERE username = ?";
            PreparedStatement lenhTuyVanChuanBi = LuongKetNoiCSDL.prepareStatement(mauTruyVan);
            lenhTuyVanChuanBi.setString(1, tenDangNhap);
            ResultSet ketQuaTryVan = lenhTuyVanChuanBi.executeQuery();

            if (ketQuaTryVan.next()) {
                String storedPassword = ketQuaTryVan.getString("password");
                if (matKhauDangNhap.equals(storedPassword)) {
                    System.out.println("Đăng nhập thành công!");
                } else {
                    System.out.println("Mật khẩu không đúng!");
                }
            } else {
                System.out.println("Tài khoản không tồn tại!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
