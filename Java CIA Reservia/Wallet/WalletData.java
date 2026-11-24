package cia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletData {

    public static long getBalance(int userId) {

        String sql =
                "SELECT balance FROM wallet WHERE user_id = ?";

        try (
            Connection cn = DBConnection.getConnection();
            PreparedStatement pst = cn.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getLong("balance");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
    
    public static List<Object[]> getTransactions(int userId) {

        List<Object[]> transactions = new ArrayList<>();

        String sql =
                "SELECT transaction_type, description, booking_id, " +
                "transaction_date, amount, status " +
                "FROM transaction " +
                "WHERE user_id = ? " +
                "ORDER BY transaction_date DESC";

        try (
                Connection cn = DBConnection.getConnection();
                PreparedStatement pst = cn.prepareStatement(sql)
        ) {

            pst.setInt(1, userId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String type =
                        rs.getString("transaction_type");

                String description =
                        rs.getString("description");

                String bookingId =
                        rs.getString("booking_id");

                Timestamp date =
                        rs.getTimestamp("transaction_date");

                long amount =
                        rs.getLong("amount");

                String status =
                        rs.getString("status");

                String formattedAmount;

                if (amount >= 0) {
                    formattedAmount = "+ ₹" + amount;
                } else {
                    formattedAmount = "- ₹" + Math.abs(amount);
                }

                transactions.add(new Object[] {
                        type,
                        description,
                        bookingId,
                        date,
                        formattedAmount,
                        status
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return transactions;
    }
}