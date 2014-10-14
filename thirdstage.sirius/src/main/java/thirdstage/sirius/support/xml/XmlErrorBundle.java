package thirdstage.sirius.support.xml;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillNotClose;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @version 1.1 2014-10-13, Add {@code Item.xPath} field {@code toJson} method.
 * @version 1.0 2014-10-03, initial
 * @author Sangmoon Oh
 * @since 2014-10-03 
 */
@NotThreadSafe
public class XmlErrorBundle{

   public enum ItemType{
      FATAL,
      ERROR,
      WARN;
   }

   public static class Item{

      private int line = -1; //line number of the location for this item.
      private int col = -1; //column number of the location for this item.
      private String locationHint = ""; //hint for the location of this item, such as XPath.
      private String title = ""; //short intuitive string for this item.
      private ItemType type;
      private String message = ""; //detail of this item.

      public Item(){}

      /**
       * @return The line number of the location for this item in 1 base, or -1
       *         when the location is not clear.
       */
      public int getLine(){ return this.line; }
      public Item setLine(int l){
         this.line = l;
         return this;
      }

      /**
       * @return The column number of the location for this item in 1 base, or -1
       *         when the location is not clear;
       */
      public int getColumn(){ return this.col; }
      public Item setColumn(int c){
         this.col = c;
         return this;
      }
      
      /**
       * @return Hint for the location of this item, such as XPath.
       * @since 1.1
       */
      public String getLocationHint(){ return this.locationHint; }
      
      /**
       * @since 1.1
       */
      public Item setLocationHint(String hint){
         this.locationHint = hint;
         return this;
      }
      

      public String getTitle(){ return this.title; }
      public Item setTitle(String title){
         this.title = title;
         return this;
      }

      public ItemType getType(){ return this.type; }
      public Item setType(ItemType type){
         this.type = type;
         return this;
      }

      public String getMessage(){ return this.message; }
      public Item setMessage(String msg){
         this.message = msg;
         return this;
      }
   }


   private final List<Item> items = new ArrayList<Item>();

   public XmlErrorBundle addItem(@Nonnull Item item){
      if(item == null) throw new IllegalArgumentException("Item to add shouldn't be null.");
      this.items.add(item);
      return this;
   }

   @Nonnull
   public List<Item> getItems(){
      return this.items;
   }

   public boolean isEmpty(){
      return this.items.isEmpty();
   }
   
   public void print(@Nonnull @WillNotClose PrintStream ps){
      if(ps == null) return;
      
      StringBuilder sb = new StringBuilder();
      for(Item item : items){
         sb.append("L:").append(item.getLine())
            .append("C:").append(item.getColumn())
            .append(", ").append(item.getLocationHint())
            .append(", ").append(item.getMessage()).append("\n");
      }
      
      ps.print(sb.toString());
   }
   
   /**
    * @return
    * @since 1.1
    */
   public String toJson(){
      
      return null;
   }

}
