package net.amar.oreojava.db;

import net.amar.oreojava.db.tables.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBUpdater {

    public static void updateMessageId(Connection connection, long caseId, String messageId) throws SQLException {
        final String sql = """
                UPDATE cases
                SET messageId=?
                WHERE caseId=?;
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, messageId);
            ps.setString(2, String.valueOf(caseId));
            ps.executeUpdate();
        }
    }

    public static void updateReason(Connection connection, long caseId, String reason) throws SQLException {
        final String sql = """
                UPDATE cases
                SET reason=?
                WHERE caseId=?;
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, reason);
            ps.setString(2, String.valueOf(caseId));
            ps.executeUpdate();
        }
    }

    public static void updateData(Connection connection, Data data) throws SQLException {
        final String sql = """
                UPDATE data
                SET fieldValue = ?
                WHERE fieldName = ?;
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, data.getFieldValue());
            ps.setString(2, data.getFieldName());
            ps.executeUpdate();
        }
    }
}
