package org.example.persistance;
import org.example.Entity.User;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private final ConcurrentHashMap<String, User> store = new ConcurrentHashMap<>();

    public void save(User user)                        { store.put(user.getUserId(), user); }
    public Optional<User> findById(String userId)      { return Optional.ofNullable(store.get(userId)); }
    public void remove(String userId)                  { store.remove(userId); }
    public boolean exists(String userId)               { return store.containsKey(userId); }
}

