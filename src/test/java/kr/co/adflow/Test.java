 package kr.co.adflow;

 import java.sql.Connection;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.util.ArrayList;


 import kr.co.adflow.common.CommonDBCP;
 import kr.co.adflow.domain.Item;

 public class Test {

 public static void main(String[] args) throws SQLException {
 Connection con = null;
int a = 12;
 
 
 
// // 전체 상품 리스트 조회
// con = CommonDBCP.getConn();
// System.out.println("connection..." + con);
// Statement stmt = con.createStatement();
// System.out.println("stmt..." + stmt);
// String sql = "select * from item";
// ResultSet resultSet = stmt.executeQuery(sql);
// while (resultSet.next()) {
// System.out.println(resultSet.getInt("item_no"));
// }
// resultSet.close();
// stmt.close();
// CommonDBCP.closeConnection();
 }
 }
