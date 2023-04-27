package thirdstage.sirius.rest;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public abstract class AsyncRequestBase {

  @Schema(title = "Async mode",
    description = """
      The way the result of async task will be delivered""") 
  @NotNull private AsyncMode mode;

  @Schema(title = "Callback URL", 
    description = """
      The location or endpoint to call to deliver the result of async task
      when the task is finished.""")
  @NotBlank private String callbackUrl;

  public AsyncMode getAsyncMode(){
    return mode;
  }
    
  public AsyncRequestBase setAsyncMode(final AsyncMode mode){
    this.mode = mode;
    return this;
  }

  public String getCallbackUrl(){
    return callbackUrl;
  }

  public AsyncRequestBase setCallbackUrl(final String url){
    this.callbackUrl = url;
    return this;
  }




  
}
