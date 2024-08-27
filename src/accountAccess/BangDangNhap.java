package accountAccess;

import java.sql.*;

public class BangDangNhap {

    public static void main(String[] args) {
        String url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12727727";
        String username = "sql12727727";
        String password = "V6ldaGqBZT";

        String tenDangNhap = "new_user";
        String matKhauDangNhap = "userpassword";
        try (Connection LuongKetNoiCSDL = DriverManager.getConnection(url, username, password)) {
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
            System.out.println("Không thể kết nối tới CSLD");
        }
    }
}
