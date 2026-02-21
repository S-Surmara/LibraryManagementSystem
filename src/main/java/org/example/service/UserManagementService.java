package org.example.service;


import org.example.Entity.User;
import org.example.Exception.UserNotFoundException;
import org.example.persistance.UserRepository;
import org.example.visitor.LibrarianVisitor;

public class UserManagementService {
    private final UserRepository userRepository;

    public UserManagementService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user)         { userRepository.save(user); }

    public void removeUser(String userId)  { userRepository.remove(userId); }

    public User getUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public boolean isLibrarian(String userId) {
        User user = getUser(userId);
        LibrarianVisitor visitor = new LibrarianVisitor();
        user.accept(visitor);
        return visitor.isLibrarian();
    }
}

