package org.atrokhau.vasili.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.atrokhau.vasili.model.Human;
import org.atrokhau.vasili.util.DBUtil;

import java.sql.*;

public class HumanService {

    public void saveHuman(Human human) {
        Connection connection = DBUtil.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("" +
                "INSERT INTO humans (name, age, date_of_birth) VALUES (?, ?, ?)")) {

            statement.setString(1, human.getName());
            statement.setInt(2, human.getAge());
            statement.setDate(3, Date.valueOf(human.getBirthday()));

            statement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteHuman(Long id) {
        Connection connection = DBUtil.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("DELETE" +
                " FROM humans WHERE id = ?")) {

            statement.setLong(1, id);
            statement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateHuman(Human human) {
        Connection connection = DBUtil.getConnection();

        try (PreparedStatement statement = connection.prepareStatement("UPDATE" +
                "  humans SET name = ?, age = ?, date_of_birth = ? WHERE id = ?")) {

            statement.setString(1, human.getName());
            statement.setInt(2, human.getAge());
            statement.setDate(3, Date.valueOf(human.getBirthday()));
            statement.setLong(4, human.getId());

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<Human> buildData() {
        Connection connection = DBUtil.getConnection();
        ObservableList<Human> humans = FXCollections.observableArrayList();

        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT id, name, age, date_of_birth FROM humans");

            while (rs.next()) {
                Human human = Human.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .age(rs.getInt("age"))
                        .birthday(rs.getDate("date_of_birth").toLocalDate())
                        .build();

                humans.add(human);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return humans;
    }
}
