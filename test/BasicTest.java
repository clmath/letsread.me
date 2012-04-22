import org.junit.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {

    @Before
    public void setup() {
    	Fixtures.deleteDatabase();
    }

    @Test
    public void createAndRetrieveUser() {
		// Create a new user and save it
		new User("http://test.openid/bobuser", "bob@gmail.com", "Bob", "Jones").save();
	
		// Retrieve the user by openId
		User bob = User.find("byOpenId", "http://test.openid/bobuser").first();
	
		// Test
		assertNotNull(bob);
		assertEquals("Bob", bob.firstname);
    }

    @Test
    public void tryConnectAsUser() {
		// Test
		assertNotNull(User.connectOrCreate("http://test.openid/bobuser", "bob@gmail.com", "Bob", "Jones"));
    }

    @Test
    public void createFeed() {
		// Create a new user and save it
		User bob = User.connectOrCreate("http://test.openid/bobuser", "bob@gmail.com", "Bob", "Jones");
		URL url = null;
		try {
		    url = new URL("http://www.google.fr");
		} catch (MalformedURLException e) {
		    assertTrue(false);
		}
	
		// Create a new feed
		new Feed(bob, "The Google Feed", url).save();
	
		// Test that the post has been created
		assertEquals(1, Feed.count());
	
		// Retrieve all feeds created by Bob
		List<Feed> bobFeeds = Feed.find("byCreator", bob).fetch();
	
		// Tests
		assertEquals(1, bobFeeds.size());
		Feed firstPost = bobFeeds.get(0);
		assertNotNull(firstPost);
		assertEquals(bob, firstPost.creator);
		assertEquals("The Google Feed", firstPost.title);
		assertNotNull(firstPost.createdAt);
    }

    
    @Test
    public void createKeyword() {
		// Create a new user and save it
		User bob = User.connectOrCreate("http://test.openid/bobuser", "bob@gmail.com", "Bob", "Jones");
		URL url = null;
		try {
		    url = new URL("http://www.google.fr");
		} catch (MalformedURLException e) {
		    assertTrue(false);
		}
	
		// Create a new feed
		Feed feed = new Feed(bob, "The Google Feed", url).save();
	
		// Add a new keyword
		feed.addKeyword("kw #1");
		
		// Test that the keyword has been created
		assertEquals(1, Keyword.count());
	
		// Retrieve all keywords of the feed
		List<Keyword> keywords = Keyword.find("byFeed", feed).fetch();
			
		// Tests
		assertEquals(1, keywords.size());
		Keyword firstKeyword = keywords.get(0);
		assertNotNull(firstKeyword);
		assertEquals(feed, firstKeyword.feed);
		assertEquals("kw #1", firstKeyword.keyword);
    }

    @Test
    public void deleteKeyword() {
		// Create a new user and save it
		User bob = User.connectOrCreate("http://test.openid/bobuser", "bob@gmail.com", "Bob", "Jones");
		URL url = null;
		try {
		    url = new URL("http://www.google.fr");
		} catch (MalformedURLException e) {
		    assertTrue(false);
		}
	
		// Create a new feed
		Feed feed = new Feed(bob, "The Google Feed", url).save();
	
		// Add new keywords
		feed.addKeyword("kw #1");
		feed.addKeyword("kw #2");
		feed.addKeyword("kw #3");
				
		// Test that the keywords has been created
		assertEquals(3, Keyword.count());
	
		// Tests
		feed.deleteKeyword("kw #1");
		assertEquals(2, Keyword.count());
		assertNull(feed.getKeyword("kw #1"));

		feed.deleteKeyword("kw #3");
		assertEquals(1, Keyword.count());
		assertNull(feed.getKeyword("kw #3"));
    }
    
}
