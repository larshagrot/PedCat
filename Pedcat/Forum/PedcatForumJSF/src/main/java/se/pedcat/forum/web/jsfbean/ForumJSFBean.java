package se.pedcat.forum.web.jsfbean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import se.pedcat.forum.model.ForumMessage;
import se.pedcat.forum.model.ForumMessage.ForumMessageKey;
import se.pedcat.forum.service.ForumService;
import se.pedcat.forum.service.ForumServiceImpl;
import se.pedcat.framework.common.util.CommonUtility;

/**
 * @author laha
 * 
 */
public class ForumJSFBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient ForumService theForumService = null;
	private ForumMessage currentForumMessage;
	private String currentMessageId;
	private String userName;
	private String pwd;
	private ForumMessage currentThread;
	
	
	
	/**
	 *  Returnerar userName
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Sätter userName
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	

	/**
	 *  Returnerar pwd
	 *
	 * @return the pwd
	 */
	public String getPwd() {
		return this.pwd;
	}

	/**
	 * Sätter pwd
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * Returnerar currentMessageId
	 * 
	 * @return the currentMessageId
	 */
	public String getCurrentMessageId() {
		return this.currentMessageId;
	}

	/**
	 * Sätter currentMessageId
	 * 
	 * @param currentMessageId
	 *            the currentMessageId to set
	 */
	public void setCurrentMessageId(String currentMessageId) {
		this.currentMessageId = currentMessageId;
	}

	/**
	 * Returnerar currentForumMessage
	 * 
	 * @return the currentForumMessage
	 */
	public ForumMessage getCurrentForumMessage() {
		return this.currentForumMessage;
	}

	/**
	 * Sätter currentForumMessage
	 * 
	 * @param currentForumMessage
	 *            the currentForumMessage to set
	 */
	public void setCurrentForumMessage(ForumMessage currentForumMessage) {
		this.currentForumMessage = currentForumMessage;
	}

	public ForumMessage[] getForumMessageHeaders() {
		ForumMessage[] messages = new ForumMessage[0];
		try {
			messages = this.getForumService().findAll();
			messages = buildTree(messages);
			
			Arrays.sort(messages);
		} catch (Exception e) {
			addMessage(e);
		}
		return messages;
	}
	
	public ForumMessage[] getLatestForumMessageHeaders() {
		List<ForumMessage> list = new ArrayList<ForumMessage>();
		ForumMessage[] messages = new ForumMessage[0];
		try {
			messages = this.getForumService().findAll();
			messages = buildTree(messages);
			Arrays.sort(messages);
			
			int index = 0;
			for(ForumMessage fm :messages)
			{
				list.add(fm);
				index++;
				if (index>5)
				{
					break;
				}
			}
			
		} catch (Exception e) {
			addMessage(e);
		}
		return list.toArray(new ForumMessage[0]);
	}


	
	/**
	 * @param messages
	 * @return
	 */
	private ForumMessage[] buildTree(ForumMessage[] messages) 
	{
		
		Map<String,ForumMessage> parentsmap = new HashMap<String,ForumMessage>();
		
		Map<String,List<ForumMessage>> map = new HashMap<String,List<ForumMessage>>();
		for(ForumMessage message: messages)
		{
			if (message.isHasParent())
			{
				CommonUtility.addToListInMap(map, message.getParentId(), message);
			}
			else
			{
				parentsmap.put(message.getObjectId(), message);
			}
		}
		if (map.size()>0)
		{
			for(String key:map.keySet())
			{
				ForumMessage forumMessage = parentsmap.get(key);
				if (forumMessage!=null)
				{
					List<ForumMessage> list = map.get(key);
					forumMessage.setChildMessages(list.toArray(new ForumMessage[0]));
				}
 			}
		}
		
		return parentsmap.values().toArray(new ForumMessage[0]);
	}

	public ForumMessage[] getForumMessages() {
		ForumMessage[] messages = new ForumMessage[0];
		try {
			messages = this.getForumService().findAll();
			Arrays.sort(messages);
		} catch (Exception e) {
			addMessage(e);
		}
		return messages;
	}

	public boolean isNew() {
		return this.currentForumMessage != null
				&& this.currentForumMessage.isNew();
	}

	public String cancel() {

		this.currentForumMessage = null;
		return "success";
	}

	public String save() {
		try {
			if (this.currentForumMessage.getMessage() != null) {
				this.currentForumMessage.setMessage(this.currentForumMessage
						.getMessage().trim());
				if (this.currentForumMessage.getMessage().length() > 2000) {
					this.currentForumMessage
							.setMessage(this.currentForumMessage.getMessage()
									.substring(0, 2000));
				}
			}
			if (this.currentForumMessage.getUserName() != null) {
				this.currentForumMessage.setUserName(this.currentForumMessage
						.getUserName().trim());
				if (this.currentForumMessage.getUserName().length() > 50) {
					this.currentForumMessage
							.setUserName(this.currentForumMessage.getUserName()
									.substring(0, 128));
				}
			}

			this.currentForumMessage.setDatum(new Date());
			this.currentForumMessage.setIp(this.getIp());

			this.currentForumMessage = this.getForumService().save(
					this.currentForumMessage);

		} catch (Exception e) {
			addMessage(e);
		}
		this.currentForumMessage = null;
		return "success";
	}

	public String register() {
		try {
			if (this.currentMessageId != null) {
				this.currentForumMessage = this.getForumService().findByKey(
						new ForumMessageKey(this.currentMessageId));
				this.currentForumMessage.setRegistered(true);
				this.getForumService().update(this.currentForumMessage);
				currentMessageId = null;
			}
		} catch (Exception e) {
			addMessage(e);
		}
		this.currentForumMessage = null;
		return "success";
	}

	public String delete() {
		try {
			if (this.currentMessageId != null) {
				this.getForumService().delete(
						new ForumMessageKey(this.currentMessageId));
				this.currentForumMessage = null;
				this.currentMessageId = null;
			}
		} catch (Exception e) {
			addMessage(e);
		}
		return "success";
	}

	/**
	 * @return
	 */
	public String getIp() {

		Object o = FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		if (o instanceof HttpServletRequest) {
			return ((HttpServletRequest) o).getRemoteAddr();
		} else 
			if (o instanceof javax.portlet.PortletRequest) {
				
					return ((javax.portlet.PortletRequest) o).getRemoteUser();
				}
				else
				{
					return "";
				}
			

	}

	public String nytt() {
		this.currentForumMessage = new ForumMessage();
		return "success";
	}

	public String update() {
		try {
			this.currentForumMessage = this.getForumService().findByKey(
					new ForumMessageKey(this.currentMessageId));
			this.currentMessageId = null;
		} catch (Exception e) {
			addMessage(e);
		}
		return "success";
	}

	/**
	 * @param e
	 */
	private void addMessage(Exception e) {
		// TODO Auto-generated method stub

	}

	public boolean isHasMessage() {
		return this.currentForumMessage != null;
	}

	public ForumService getForumService() {
		if (this.theForumService == null) {
			this.theForumService = new ForumServiceImpl();
		}
		return this.theForumService;
	}
	
	public boolean isLoggedIn()
	{
		return pwd!=null && pwd.equals("vikurs99");
	}
	

	
	public String kommentera()
	{
		this.currentForumMessage = new ForumMessage();
	
		
		ForumMessage parent = this.getForumService().findByKey(new ForumMessage.ForumMessageKey(this.currentMessageId));
		if (parent!=null)
		{
			if (parent.isHasParent())
			{
				this.currentForumMessage.setParentId(parent.getParentId());
			}
			else
			{
				this.currentForumMessage.setParentId(parent.getObjectId());
			}
			this.currentForumMessage.setRubrik(parent.getRubrik());
		}
		else
		{
			this.currentForumMessage.setParentId(this.currentMessageId);
		}
		
		return "success";
	}
	
	public String showThread()
	{
		ForumMessage[] messages = new ForumMessage[0];
		try 
		{
			System.out.println("ShowThread");
			messages = this.getForumService().findAll();
			currentThread = buildThreadTree(this.currentMessageId,messages);
			
			currentThread.setReadCount(currentThread.getReadCount()+1);
			this.getForumService().update(this.currentThread);
			
			Arrays.sort(messages);
			System.out.println("OK");
		} catch (Exception e) {
			addMessage(e);
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		return "success";
	}

	/**
	 * @param currentMessageId2
	 * @param messages
	 * @return
	 */
	private ForumMessage buildThreadTree(String currentMessageId2,
			ForumMessage[] messages) {
		
		messages = buildTree(messages);
		for(ForumMessage message:messages)
		{
			if (message.getObjectId().equals(currentMessageId2))
			{
				return message;
			}
		}
		return null;
	}

	public ForumMessage[] getForumThreadMessages()
	{
		if (this.currentThread!=null)
		{
			List<ForumMessage> list = new ArrayList<ForumMessage>();
			list.add(this.currentThread);
			if (this.currentThread.isHasChildren())
			{
				list.addAll(Arrays.asList(this.currentThread.getChildMessages()));
			}
			ForumMessage[] messages = list.toArray(new ForumMessage[0]);
			Arrays.sort(messages,new Comparator<ForumMessage>(){

				@Override
				public int compare(ForumMessage o1, ForumMessage o2) {
					// TODO Auto-generated method stub
					return o1.getDatum()!=null && o2.getDatum()!=null?1*o1.getDatum().compareTo(o2.getDatum()):0;
				}

				});
			return messages;
		}
		
		
		return new ForumMessage[0];
	}
	
	public String xxxxx()
	{
		
		return "success";
	}

	public static void main(String[] args) {

		ForumJSFBean bean = new ForumJSFBean();
	
		
		bean.getForumMessages();
		
		/*bean.nytt();
		bean.getCurrentForumMessage().setMessage("text");
		bean.getCurrentForumMessage().setUserName("namn");
		bean.getCurrentForumMessage().setEpost("text@mail.se");

		// bean.save();
		System.out.println(bean.getForumMessages()[0].getObjectId());
		bean.setCurrentMessageId("5504e19e291c208d01291c21e6450001");
		bean.delete();
*/
	}
	public boolean isHasCurrentThread()
	{
		return this.currentThread!=null;
	}
	
	public ForumMessage getCurrentThread()
	{
		return this.currentThread;
	}
	
}
