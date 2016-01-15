package com.flamingos.osp.exception;

public class OspDaoException extends Exception {

	/**
	 * 
	 */
	private String returnMessage = null;
	
	public OspDaoException()
	{
		super();
		
	}
	
	public OspDaoException(Throwable excp)
	{
		super(excp);
		
	}
	
	public OspDaoException(String message)
	{
		super(message);
		this.returnMessage = message;
		
	}
	
	@Override
	public String getMessage()
	{
		 return returnMessage;
		
	}
	
	public String toString()
	{
		return returnMessage;
		
	}

}
