/**
 * 
 */
package se.pedcat.forum.test;

import se.pedcat.forum.model.ForumMessage;
import se.pedcat.forum.service.ForumService;
import se.pedcat.forum.service.ForumServiceImpl;

/**
 * @author laha
 *
 */
public class ForumTest {

	
	public static void main(String[] args)
	{
		ForumService s = new ForumServiceImpl(); 
		
		ForumMessage[] f = s.findAll();
		System.out.println(f.length);
	}
}
