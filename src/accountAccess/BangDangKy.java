package accountAccess;

import java.sql.*;

public class BangDangKy {

    public static void main(String[] args) {
        String url = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12727727";
        String username = "sql12727727";
        String password = "V6ldaGqBZT";

        String tenDangKy = "new_user";
        String matKhauDangKy = "userpassword";
        try (Connection LuongKetNoiCSDL = DriverManager.getConnection(url, username, password)) {
            String mauTruyVan = "SELECT password FROM Users WHERE username = ?";

            //Sử dụng PreparedStatement để tránh tấn công kiểu Sql Injection
            PreparedStatement lenhTuyVanChuanBi = LuongKetNoiCSDL.prepareStatement(mauTruyVan);
            lenhTuyVanChuanBi.setString(1, tenDangKy);
            ResultSet ketQuaTryVan = lenhTuyVanChuanBi.executeQuery();
            if (!ketQuaTryVan.next()) {
                mauTruyVan = "INSERT INTO Users (username, password) VALUES (?, ?)";
                lenhTuyVanChuanBi = LuongKetNoiCSDL.prepareStatement(mauTruyVan);
                lenhTuyVanChuanBi.setString(1, tenDangKy);
                lenhTuyVanChuanBi.setString(2, matKhauDangKy);
                lenhTuyVanChuanBi.executeUpdate();
                System.out.println("Đăng ký thành công!");
            } else {
                System.out.println("Tài khoản đã tồn tại");
            }
        } catch (SQLException e) {
            System.out.println("Không thể kết nối tới CSLD");
        }
    }
}
