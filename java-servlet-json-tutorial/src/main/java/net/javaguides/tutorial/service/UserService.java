package net.javaguides.tutorial.service;

import net.javaguides.tutorial.model.User;
import net.javaguides.tutorial.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    
    // Fetch all users from the database
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String email = resultSet.getString("email");
                users.add(new User(id, firstName, lastName, email));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    
    // Create a new user
    public void createUser(User user) {
        String query = "INSERT INTO users (firstName, lastName, email) VALUES (?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Update an existing user
    public void updateUser(User user) {
        String query = "UPDATE users SET firstName = ?, lastName = ?, email = ? WHERE id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getId());
            
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Number of affected rows: " + affectedRows);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    // Delete a user by ID
    public void deleteUser(Long id) {
        String query = "DELETE FROM users WHERE id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
