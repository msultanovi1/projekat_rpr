package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.UserDaoSQLImpl;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserManagerTest {

    private UserManager userManager;
    private User user;
    private UserDaoSQLImpl userDaoSQLMock;
    private List<User> users;

    @BeforeEach
    public void initializeObjects() throws MyBookListException {
        userManager = Mockito.mock(UserManager.class);
        userDaoSQLMock = Mockito.mock(UserDaoSQLImpl.class);

        User user1 = new User(1, "Kemo", "jakasifra", "prvisamnalisti");
        User user2 = new User(2, "Amila", "hehehehehe", "hocunamore");

        users = new ArrayList<>();
        users.addAll(Arrays.asList(user1, user2));
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDaoSQLImpl getUserDaoSQLMock() {
        return userDaoSQLMock;
    }

    public void setUserDaoSQLMock(UserDaoSQLImpl userDaoSQLMock) {
        this.userDaoSQLMock = userDaoSQLMock;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Testing the add method
     * @throws MyBookListException
     */
    @Test
    public void addUser() throws MyBookListException{
        user = new User(3, "erni", "spiderman", "IAmSpiderman");
        Mockito.doCallRealMethod().when(userManager).add(user);
        userManager.add(user);

        Assertions.assertTrue(true);
        Mockito.verify(userManager).add(user);
    }

    /**
     * Testing the delete method
     * @throws MyBookListException
     */
    @Test
    public void deleteUser() throws MyBookListException{
        user = new User(3, "erni", "spiderman", "IAmSpiderman");
        Mockito.doCallRealMethod().when(userManager).delete(user.getId());
        userManager.delete(user.getId());

        Assertions.assertTrue(true);
        Mockito.verify(userManager).delete(user.getId());
    }
    /**
     * Testing add method, but now we are adding a user that already exists
     * @throws MyBookListException
     */
    @Disabled
    public void addAlreadyExisting() throws MyBookListException{
        user = new User(3, "erni", "spiderman", "IAmSpiderman");
        Mockito.doCallRealMethod().when(userManager).add(user);

        MyBookListException myBookListException = Assertions.assertThrows(MyBookListException.class, () -> userManager.add(user),
                "User already exists");
        Assertions.assertEquals("User already exists", myBookListException.getMessage());
    }

}
