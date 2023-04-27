package thirdstage.sirius.exception;

public class ConflictingStateException extends CodedException {

  private static final long serialVersionUID = 1L;

  public ConflictingStateException(final String code, final String msg){
    super(code, msg);
  }
  
}
