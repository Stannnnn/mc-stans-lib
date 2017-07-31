//Version 1.0 (29-7-2016)
package Stan.Lib.Yml.Objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_ItemStack {

	@YmlObject
	private String id = "STONE";

	@YmlObject
	private int data = 0;

	@YmlObject
	private Yml_Amount amount;

	@YmlObject(skip = true)
	private Yml_ItemMeta itemMeta;
	
	public Yml_ItemStack(){
		
	}
	
	private Yml_ItemStack(Builder builder){
		this.id = builder.id;
		this.data = builder.data;
		this.amount = builder.amount;
		this.itemMeta = builder.itemMeta;
	}

	public ItemStack getItemStack(RW... replaceWrappers) {
		ItemStack itemStack = new ItemStack(Material.getMaterial(id), amount.getAmount(), (short) data);
		itemStack.setItemMeta(itemMeta.getItemMeta(itemStack, replaceWrappers));
		return itemStack;
	}

	@Override
	public String toString() {
		return "Yml_ItemStack [id=" + id + ", data=" + data + ", amount=" + amount + ", itemMeta=" + itemMeta + "]";
	}
	
	public static class Builder{
		private String id;
		private int data;
		private Yml_Amount amount;
		private Yml_ItemMeta itemMeta;
		
		public Builder withId(String id){
			this.id = id;
			return this;
		}
		
		public Builder withData(int data){
			this.data = data;
			return this;
		}
		
		public Builder withAmount(Yml_Amount amount){
			this.amount = amount;
			return this;
		}
		
		public Builder withItemMeta(Yml_ItemMeta itemMeta){
			this.itemMeta = itemMeta;
			return this;
		}
		
		public Yml_ItemStack build(){
			return new Yml_ItemStack(this);
		}
	}

}