/**
 * Copyright 2011, 2012 Kevin Gaudin
 *
 * This file is part of letsread.me.
 *
 * letsread.me is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * letsread.me is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with letsread.me.  If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import java.net.URL;
import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

/**
 * All the data we need to store about feeds.
 */
@Entity
public class Feed extends Model implements Comparable<Feed> {

    public String title;
    public Date createdAt;
    public URL url;

    @ManyToOne
    public User creator;

    @OneToMany(mappedBy="feed", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Keyword> keywords;

    public Feed(User creator, String title, URL url) {
        this.creator = creator;
        this.title = title;
        this.url = url;
        this.createdAt = new Date();
        this.keywords = new ArrayList<Keyword>();
    }

    public static void delete(User creator, URL url) {
        Feed feed = Feed.find("byCreatorAndUrl", creator, url).first();
        feed.delete();
    }

    /**
     * Return the Keyword object standing for keyword if there is one 
     * in the feed or null if there is no matching Keyword objects.  
     * 
     * @param keyword 
     * @return 
     * 		The Keyword object for the keyword in the feed.
     */
	public Keyword getKeyword(String keyword) {
		for (Keyword kw : keywords) {
			if (kw.keyword.compareToIgnoreCase(keyword) == 0) {
				return kw;				
			}
		}
		return null;
	}
    
	/**
     * Return the index of keyword in feed's keywords list if there is one 
     * or -1 if there is no match.  
     * 
     * @param keyword 
     * @return 
     * 		The keyword index in the keywords of the feed.
     */
	public int getKeywordIndex(String keyword) {
		int j = 0;
		for (Keyword kw : keywords) {
			if (kw.keyword.compareToIgnoreCase(keyword) == 0) {
				return j;				
			}
			j++;
		}
		return -1;
	}
	
    public Feed addKeyword(String keyword) {
		Keyword newKeyword = new Keyword(this, keyword).save();
		this.keywords.add(newKeyword);
		this.save();
		return this;
	}
	
	public Feed deleteKeyword(String keyword) {
		Keyword delKeyword = getKeyword(keyword);
		int delIndex = getKeywordIndex(keyword);
		if (delKeyword != null && delIndex != -1) {
			this.keywords.remove(delIndex);
			delKeyword.delete();
		}
		this.save();
		return this;
	}
	
    /**
     * Alphabetical order.
     */
    @Override
    public int compareTo(Feed o) {
        return title.compareTo(o.title);
    }
}
