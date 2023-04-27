package thirdstage.sirius.exception;

public class InvalidArgumentException extends CodedException {

  public InvalidArgumentException(final String code, final String msg){
    super(code, msg);
  }
  
}
