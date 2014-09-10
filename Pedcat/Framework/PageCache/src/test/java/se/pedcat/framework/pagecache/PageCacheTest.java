/*
 * Copyright (c) Signifikant Svenska AB.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */
package se.pedcat.framework.pagecache;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.*;

//import org.junit.Test;

/**
 * The Class PageCacheTest.
 */
public class PageCacheTest {

	/**
	 * The Class Test.
	 */
	public static class Test {

		/**
		 * The Class TextCompare.
		 */
		public static class TextCompare implements Comparator<Test> {

			/**
			 * Instantiates a new text compare.
			 * 
			 * @param stigande
			 *            the stigande
			 */
			public TextCompare(boolean stigande) {
				super();
				this.stigande = stigande;
			}

			/** The stigande. */
			private boolean stigande;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object,
			 * java.lang.Object)
			 */
			@Override
			public int compare(
					se.pedcat.framework.pagecache.PageCacheTest.Test o1,
					se.pedcat.framework.pagecache.PageCacheTest.Test o2) {
				// TODO Auto-generated method stub
				return stigande ? 1 : -1 * o1.getText().compareTo(o2.getText());
			}

		}

		/**
		 * The Class NumberCompare.
		 */
		public static class NumberCompare implements Comparator<Test> {

			/**
			 * Instantiates a new number compare.
			 * 
			 * @param stigande
			 *            the stigande
			 */
			public NumberCompare(boolean stigande) {
				super();
				this.stigande = stigande;
			}

			/** The stigande. */
			private boolean stigande;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Comparator#compare(java.lang.Object,
			 * java.lang.Object)
			 */
			@Override
			public int compare(
					se.pedcat.framework.pagecache.PageCacheTest.Test o1,
					se.pedcat.framework.pagecache.PageCacheTest.Test o2) {
				// TODO Auto-generated method stub
				return stigande ? 1 : -1 * (o1.getNumber() - o2.getNumber());
			}

		}

		/**
		 * Instantiates a new test.
		 * 
		 * @param text
		 *            the text
		 * @param number
		 *            the number
		 */
		public Test(String text, Integer number) {
			super();
			this.text = text;
			this.number = number;
		}

		/** The text. */
		private String text;

		/** The number. */
		private Integer number;

		/**
		 * Returnerar text.
		 * 
		 * @return the text
		 */
		public String getText() {
			return this.text;
		}

		/**
		 * Sätter text.
		 * 
		 * @param text
		 *            the text to set
		 */
		public void setText(String text) {
			this.text = text;
		}

		/**
		 * Returnerar number.
		 * 
		 * @return the number
		 */
		public Integer getNumber() {
			return this.number;
		}

		/**
		 * Sätter number.
		 * 
		 * @param number
		 *            the number to set
		 */
		public void setNumber(Integer number) {
			this.number = number;
		}

	}

	/**
	 * Test page cache.
	 */
	@org.junit.Test
	public void testPageCache() {
		double tid1 = 0;
		double tid2 = 0;
		String mem1;
		String mem2;
		int SIZE = 10000;
		for (int n = 0; n < 2; n++) {
			// PageCacheFactory.getInstance().setFileCache(n==1);
			PageCacheFactory.getInstance().setFileCache(false);
			long start = System.currentTimeMillis();
			Date today = new Date();
			long delta = 24 * 60 * 60 * 1000;
			int count = 1;
			Date[] dates = new Date[SIZE];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = new Date(today.getTime() + ((count++) * delta));
			}

			PageCache<Date, Object> datePageCache = PageCacheFactory
					.getInstance().createPageCache(dates);

			for (int i = 1; i < datePageCache.getAntalSidor(); i++) {
				datePageCache.setCurrentPage(i);
				// System.out.println("Sida " + datePageCache.getCurrentPage());
				List<Date> page = datePageCache.getCurrentPageRader();
				for (Date date : page) {
					// System.out.println(date);
				}
			}

			int idelta = 1;
			int icount = 1;
			Integer[] Integers = new Integer[SIZE];
			for (int i = 0; i < Integers.length; i++) {
				Integers[i] = new Integer(icount++);
			}

			PageCache<Integer, Object> IntegerPageCache = PageCacheFactory
					.getInstance().createPageCache(Integers);

			for (int i = 1; i < IntegerPageCache.getAntalSidor(); i++) {
				IntegerPageCache.setCurrentPage(i);
				// System.out.println("Sida " +
				// IntegerPageCache.getCurrentPage());
				List<Integer> page = IntegerPageCache.getCurrentPageRader();
				for (Integer integer : page) {
					// System.out.println(integer);
				}
			}

			icount = 1;
			String[] strings = new String[SIZE];
			for (int i = 0; i < strings.length; i++) {
				strings[i] = new String("S " + (icount++));
			}

			PageCache<String, Object> stringPageCache = PageCacheFactory
					.getInstance().createPageCache(strings);

			for (int i = 1; i < stringPageCache.getAntalSidor(); i++) {
				stringPageCache.setCurrentPage(i);
				// System.out.println("Sida " +
				// stringPageCache.getCurrentPage());
				List<String> page = stringPageCache.getCurrentPageRader();
				for (String String : page) {
					// System.out.println(String);
				}
			}
			if (n == 0) {

				System.out.println(stringPageCache.getAntalSidor());
				tid1 = (System.currentTimeMillis() - start) / 1000.;
				long s = System.currentTimeMillis();

				stringPageCache
						.setCurrentPage(stringPageCache.getAntalSidor() / 2);
				List<String> page = stringPageCache.getCurrentPageRader();
				System.out.println(System.currentTimeMillis() - s);
				for (String String : page) {
					System.out.println(String);
				}

			} else {
				stringPageCache
						.setCurrentPage(stringPageCache.getAntalSidor() / 2);
				long s = System.currentTimeMillis();
				List<String> page = stringPageCache.getCurrentPageRader();
				System.out.println(System.currentTimeMillis() - s);
				for (String String : page) {
					System.out.println(String);
				}
				System.out.println(stringPageCache.getAntalSidor());
				tid2 = (System.currentTimeMillis() - start) / 1000.;
			}

		}
		System.out.println(tid1);
		System.out.println(tid2);
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new PageCacheTest().testPageCache();
	}

	/**
	 * Test page cache2.
	 */
	private void testPageCache2() {
		Test[] test = new Test[100];
		for (int i = 0; i < 90; i++) {
			test[i] = new Test("" + i, 1000 - i);
		}

		PageCache<Test, Object> testPageCache = PageCacheFactory.getInstance()
				.createPageCache(test);

	}

}
