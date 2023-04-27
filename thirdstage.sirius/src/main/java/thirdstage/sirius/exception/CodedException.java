package thirdstage.sirius.exception;

public abstract class CodedException extends RuntimeException {

  private String code;

  public CodedException(final String code, final String message){
    super(message);
    this.code = code;
  };

  public String getCode(){
    return this.code;
  }

}
