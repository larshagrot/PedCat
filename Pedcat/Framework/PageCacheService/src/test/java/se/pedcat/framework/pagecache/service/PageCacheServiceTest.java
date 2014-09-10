package se.pedcat.framework.pagecache.service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import se.pedcat.framework.pagecache.model.PageCacheTicket;
import se.pedcat.framework.pagecache.service.PageCacheService;

//import org.junit.Test;

public class PageCacheServiceTest {
	public static class TextCompare implements Comparator<Test> {
		public TextCompare(boolean stigande) {
			super();
			this.stigande = stigande;
		}

		private boolean stigande;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Test o1, Test o2) {
			// TODO Auto-generated method stub
			return (stigande ? 1 : -1) * o1.getText().compareTo(o2.getText());
		}

	}

	public static class NumberCompare implements Comparator<Test> {
		public NumberCompare(boolean stigande) {
			super();
			this.stigande = stigande;
		}

		private boolean stigande;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Test o1, Test o2) {
			// TODO Auto-generated method stub
			return (stigande ? 1 : -1)
					* (o1.getNumber().intValue() - o2.getNumber().intValue());
		}

	}

	public static class Test {

		public Test(String text, Integer number) {
			super();
			this.text = text;
			this.number = number;
		}

		private String text;
		private Integer number;

		/**
		 * Returnerar text
		 * 
		 * @return the text
		 */
		public String getText() {
			return this.text;
		}

		/**
		 * Sätter text
		 * 
		 * @param text
		 *            the text to set
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * Returnerar number
		 * 
		 * @return the number
		 */
		public Integer getNumber() {
			return this.number;
		}

		/**
		 * Sätter number
		 * 
		 * @param number
		 *            the number to set
		 */
		public void setNumber(Integer number) {
			this.number = number;
		}

	}
	
	@org.junit.Test
	public void testPageCacheService()
	{
		testPageCache2();
	}
	
	
	

	public static void main(String[] args) {
		new PageCacheServiceTest().testPageCache2();
	}

	private PageCacheService pcs = new PageCacheServiceImpl();

	/**
	 * 
	 */
	private void testPageCache2() {
		Test[] test = new Test[100];
		for (int i = 0; i < test.length; i++) {
			test[i] = new Test("" + i, 1000 - i);
		}

		// PageCache<Test,Object> testPageCache =
		// PageCacheFactory.getInstance().createPageCache(test);
		PageCacheTicket[] tickets = pcs.cacheResult(test, new Comparator[] {
				new NumberCompare(true), new NumberCompare(false),
				new TextCompare(true), new TextCompare(false) });
		for (PageCacheTicket aticket : tickets) {
			System.out.println(aticket.getSortorder() + " "
					+ aticket.getValue());
			List<Test> list = pcs.getPage(aticket, 1);
			for (Test vr : list) {
				System.out.println(vr.getNumber() + " " + vr.getText());
			}
		}

	}

}
