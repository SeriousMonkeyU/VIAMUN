package shared.transferObjects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsflashListTest
{
    private NewsflashList list;

    @BeforeEach
    void setUp()
    {
        list = new NewsflashList();
    }

    @Test
    void constructor_z()
    {
        assertDoesNotThrow(() -> new NewsflashList());
    }

    @Test
    void constructor_o()
    {
        //constructor doesn't take arguments
    }

    @Test
    void constructor_m()
    {
        //constructor doesn't take arguments
    }

    @Test
    void constructor_b()
    {
        //constructor doesn't take arguments
    }

    @Test
    void constructor_e()
    {
        //constructor doesn't take arguments
    }


    @Test
    void getAllNewsflashes_z()
    {
        String sender = "sender";
        assertDoesNotThrow(() -> list.getAllNewsflashes(sender));
    }

    @Test
    void getAllNewsflashes_o()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        list.addNewsflash(newsflash);
        assertDoesNotThrow(() -> list.getAllNewsflashes(sender));
    }

    @Test
    void getAllNewsflashes_m()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        Newsflash newsflash1 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash1", false, true);
        Newsflash newsflash2 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash2", true, false);
        list.addNewsflash(newsflash);
        list.addNewsflash(newsflash1);
        list.addNewsflash(newsflash2);
        assertDoesNotThrow(() -> list.getAllNewsflashes(sender));

    }

    @Test
    void getAllNewsflashes_b()
    {

    }

    @Test
    void getAllNewsflashes_e()
    {

    }

    @Test
    void getApprovedNewsflashes_z()
    {
        String sender = "sender";
        assertDoesNotThrow(() -> list.getApprovedNewsflashes(sender));
    }

    @Test
    void getApprovedNewsflashes_o()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        list.addNewsflash(newsflash);
        NewsflashApproved approved = new NewsflashApproved();
        newsflash.setState(approved);
        assertDoesNotThrow(() -> list.getApprovedNewsflashes(sender));
    }

    @Test
    void getApprovedNewsflashes_m()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        Newsflash newsflash1 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash1", false, true);
        Newsflash newsflash2 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash2", true, false);
        NewsflashApproved approved = new NewsflashApproved();
        newsflash.setState(approved);
        newsflash1.setState(approved);
        newsflash2.setState(approved);
        list.addNewsflash(newsflash);
        list.addNewsflash(newsflash1);
        list.addNewsflash(newsflash2);
        assertDoesNotThrow(() -> list.getApprovedNewsflashes(sender));
    }

    @Test
    void getApprovedNewsflashes_b()
    {

    }

    @Test
    void getApprovedNewsflashes_e()
    {

    }

    @Test
    void getPendingNewsflashes_z()
    {
        String sender = "sender";
        assertDoesNotThrow(() -> list.getPendingNewsflashes(sender));
    }

    @Test
    void getPendingNewsflashes_o()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        list.addNewsflash(newsflash);
        NewsflashPending pending = new NewsflashPending();
        newsflash.setState(pending);
        assertDoesNotThrow(() -> list.getPendingNewsflashes(sender));
    }

    @Test
    void getPendingNewsflashes_m()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        Newsflash newsflash1 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash1", false, true);
        Newsflash newsflash2 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash2", true, false);
        NewsflashPending pending = new NewsflashPending();
        newsflash.setState(pending);
        newsflash1.setState(pending);
        newsflash2.setState(pending);
        list.addNewsflash(newsflash);
        list.addNewsflash(newsflash1);
        list.addNewsflash(newsflash2);
        assertDoesNotThrow(() -> list.getPendingNewsflashes(sender));
    }

    @Test
    void getPendingNewsflashes_b()
    {

    }

    @Test
    void getPendingNewsflashes_e()
    {

    }

    @Test
    void addNewsflash_z()
    {
        assertDoesNotThrow(() -> list.addNewsflash(null));
    }

    @Test
    void addNewsflash_o()
    {
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), "sender", "random Newsflash", true, true);
        assertDoesNotThrow(() -> list.addNewsflash(newsflash));
    }

    @Test
    void addNewsflash_m()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        Newsflash newsflash1 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash1", false, true);
        Newsflash newsflash2 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash2", true, false);
        assertDoesNotThrow(() ->
        {
            list.addNewsflash(newsflash);
            list.addNewsflash(newsflash1);
            list.addNewsflash(newsflash2);
        });
    }

    @Test
    void addNewsflash_b()
    {

    }

    @Test
    void addNewsflash_e()
    {

    }

    @Test
    void getSize_z()
    {
        assertEquals(0, list.getSize());
    }

    @Test
    void getSize_o()
    {
        list.addNewsflash(new Newsflash(LocalDateTime.now(), "sender", "random Newsflash", true, true));
        assertEquals(1, list.getSize());
    }

    @Test
    void getSize_m()
    {
        String sender = "sender";
        Newsflash newsflash = new Newsflash(LocalDateTime.now(), sender, "random Newsflash", true, true);
        Newsflash newsflash1 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash1", false, true);
        Newsflash newsflash2 = new Newsflash(LocalDateTime.now(), sender, "random Newsflash2", true, false);

        list.addNewsflash(newsflash);
        list.addNewsflash(newsflash1);
        list.addNewsflash(newsflash2);
        assertEquals(3, list.getSize());

    }

    @Test
    void getSize_b()
    {

    }

    @Test
    void getSize_e()
    {

    }

}