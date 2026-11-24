package cia;

import java.sql.*;

public class BookingManager {

    public static int createBooking(
            Connection con,
            int userId,
            String bookingType,
            String bookingName,
            String bookingDetails,
            String location,
            Date bookingDate,
            Time bookingTime,
            long amount) throws SQLException {

        String sql =
                "INSERT INTO bookings " +
                "(user_id, booking_type, booking_name, booking_details, " +
                "location, booking_date, booking_time, amount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, userId);
            pst.setString(2, bookingType);
            pst.setString(3, bookingName);
            pst.setString(4, bookingDetails);
            pst.setString(5, location);

            if (bookingDate != null)
                pst.setDate(6, bookingDate);
            else
                pst.setNull(6, Types.DATE);

            if (bookingTime != null)
                pst.setTime(7, bookingTime);
            else
                pst.setNull(7, Types.TIME);

            pst.setLong(8, amount);
            pst.setString(9, "Confirmed");

            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }

                throw new SQLException("Could not generate Booking ID.");
            }
        }
    }
}