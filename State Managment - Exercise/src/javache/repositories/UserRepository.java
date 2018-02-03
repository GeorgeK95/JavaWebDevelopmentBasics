package javache.repositories;

import javache.factories.UserFactory;
import javache.model.User;

import java.io.*;
import java.util.*;

import static javache.utils.WebConstants.DB_FILE;
import static javache.utils.WebConstants.PILE_SPLITTER;

/**
 * Created by George-Lenovo on 6/29/2017.
 */
public class UserRepository implements IUserRepository<User> {

    @Override
    public User getById(String id) {
        Optional<User> first = this.getAll().stream().filter(u -> u.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override
    public void persist(User user) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            String line = user.getId() + PILE_SPLITTER + user.getEmail() + PILE_SPLITTER + user.getPassword() + "\r\n";
            File file = new File(DB_FILE);
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(line);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public Set<User> getAll() {
        Scanner scanner;
        Set<User> allUsers = new HashSet<>();
        try {
            scanner = new Scanner(new File(DB_FILE));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] userData = line.split("\\" + PILE_SPLITTER);
                allUsers.add(UserFactory.create(userData[0], userData[1], userData[2]));
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public boolean exists(User user) {
        return this.findByEmail(user.getEmail()) != null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> first = this.getAll().stream().filter(u -> u.getEmail().equals(email)).findFirst();
        return first.orElse(null);
    }
}
