package thirdstage.sirius.support.xml;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlValidationResult{

   public enum ItemType{
      FATAL,
      ERROR,
      WARN;
   }

   public static class Item{

      private int line = -1; //line number of the location for this item.
      private int col = -1; //column number of the location for this item.
      private String title = ""; //short intuitive string for this item.
      private ItemType type;
      private String desc = ""; //description of this item.

      public Item(){}

      /**
       * @return The line number of the lotation for this item in 1 base, or -1
       *         when the location is not clear.
       */
      public int getLine(){ return this.line; }
      public Item setLine(int l){
         this.line = l;
         return this;
      }

      /**
       * @return The column number of the loation for this item in 1 base, or -1
       *         when the location is not clear;
       */
      public int getColumn(){ return this.col; }
      public Item setColumn(int c){
         this.col = c;
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

      public String getDesc(){ return this.desc; }
      public Item setDesc(String desc){
         this.desc = desc;
         return this;
      }
   }


   private final List<Item> items = new ArrayList<Item>();

   public XmlValidationResult addItem(@Nonnull Item item){
      if(item == null) throw new IllegalArgumentException("Item to add shouldn't be null.");
      this.items.add(item);
      return this;
   }

   public List<Item> getItems(){
      return this.items;
   }


}
