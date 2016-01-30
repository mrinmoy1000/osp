package com.flamingos.osp.exception;

@SuppressWarnings("serial")
public class OspServiceException extends Exception {

  /**
	 * 
	 */
  private String returnMessage = null;

  public OspServiceException() {
    super();

  }

  public OspServiceException(Throwable excp) {
    super(excp);

  }

  public OspServiceException(Throwable excp, String message) {
    super(excp);
    this.returnMessage = message;

  }

  public OspServiceException(String message) {
    super(message);
    this.returnMessage = message;

  }

  @Override
  public String getMessage() {
    return returnMessage;

  }

  public String toString() {
    return returnMessage;

  }

}
