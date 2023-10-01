package net.javaguides.tutorial.controller;

import com.google.gson.Gson;
import net.javaguides.tutorial.model.User;
import net.javaguides.tutorial.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/users/*"}, name = "UserServlet", description = "UserServlet for CRUD operations")
public class UserServlet extends HttpServlet {
    private final UserService service = new UserService();
    private final Gson gson = new Gson();

    // READ: Fetch all users
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = service.getUsers();
        String json = gson.toJson(users);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    // CREATE: Add a new user
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");

        User newUser = new User(0L, firstName, lastName, email);  // Assuming ID is auto-generated by the DB.
        service.createUser(newUser);

        response.getWriter().write("User created successfully");
    }

    // UPDATE: Modify an existing user
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Extract user ID from the URL parameter
        String userId = request.getParameter("id");
        if (userId == null || userId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is missing");
            return;
        }

        // Read the JSON from the request body
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        String jsonInput = jsonBuffer.toString();
        User userToUpdate = gson.fromJson(jsonInput, User.class);
        userToUpdate.setId(Long.parseLong(userId)); // Set the ID we got from URL parameter to the user object

        System.out.println("Received update for user with ID: " + userToUpdate.getId());
        System.out.println("First Name: " + userToUpdate.getFirstName());
        System.out.println("Last Name: " + userToUpdate.getLastName());
        System.out.println("Email: " + userToUpdate.getEmail());

        service.updateUser(userToUpdate);

        response.getWriter().write("User updated successfully");
    }



    // DELETE: Remove a user
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        service.deleteUser(Long.parseLong(userId));

        response.getWriter().write("User deleted successfully");
    }
}