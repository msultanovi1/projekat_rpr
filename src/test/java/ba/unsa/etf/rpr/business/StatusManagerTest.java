package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.StatusDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Status;
import ba.unsa.etf.rpr.exceptions.MyBookListException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class StatusManagerTest {

    private Status status;
    private StatusManager statusManager;
    private List<Status> statuses;
    private StatusDaoSQLImpl statusDaoSQLMock;

    private UserManagerTest userManagerTest;
    private BookManagerTest bookManagerTest;

    @BeforeEach
    public void initializeObjects() throws MyBookListException{
        userManagerTest = new UserManagerTest();
        userManagerTest.initializeObjects();
        bookManagerTest = new BookManagerTest();
        bookManagerTest.initializeObjects();
        statusManager = Mockito.mock(StatusManager.class);
        statusDaoSQLMock = Mockito.mock(StatusDaoSQLImpl.class);

        statuses = new ArrayList<>();
        statuses.add(new Status(1,"read", 3, userManagerTest.getUserManager().getById(1), bookManagerTest.getBookManager().getById(1)));
        statuses.add(new Status(2,"reading", 5, userManagerTest.getUserManager().getById(2), bookManagerTest.getBookManager().getById(2)));

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public StatusManager getStatusManager() {
        return statusManager;
    }

    public void setStatusManager(StatusManager statusManager) {
        this.statusManager = statusManager;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public StatusDaoSQLImpl getStatusDaoSQLMock() {
        return statusDaoSQLMock;
    }

    public void setStatusDaoSQLMock(StatusDaoSQLImpl statusDaoSQLMock) {
        this.statusDaoSQLMock = statusDaoSQLMock;
    }

    public UserManagerTest getUserManagerTest() {
        return userManagerTest;
    }

    public void setUserManagerTest(UserManagerTest userManagerTest) {
        this.userManagerTest = userManagerTest;
    }

    public BookManagerTest getBookManagerTest() {
        return bookManagerTest;
    }

    public void setBookManagerTest(BookManagerTest bookManagerTest) {
        this.bookManagerTest = bookManagerTest;
    }

    /**
     * Testing add method for Status
     * @throws MyBookListException
     */
    @Test
    public void addStatus() throws MyBookListException{
        status = new Status(3, "HaHaHa", 10, userManagerTest.getUserManager().getById(3), bookManagerTest.getBookManager().getById(3));
        statusManager.add(status);

        Assertions.assertTrue(true);
        Mockito.verify(statusManager).add(status);
    }

    /**
     * Testing delete method for Status
     * @throws MyBookListException
     */
    @Test
    public void deleteStatus() throws MyBookListException{
        status = new Status(4, "HiHiHi", 9, userManagerTest.getUserManager().getById(1), bookManagerTest.getBookManager().getById(2));
        Mockito.doCallRealMethod().when(statusManager).delete(status.getId());
        statusManager.delete(status.getId());

        Assertions.assertTrue(true);
        Mockito.verify(statusManager).delete(status.getId());
    }
}
