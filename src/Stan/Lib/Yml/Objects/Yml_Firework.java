package Stan.Lib.Yml.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Firework {

	@YmlObject
	private int power;
	
	@YmlObject
	private List<Yml_FireworkEffect> effects;
	
	public Yml_Firework(){
		
	}
	
	private Yml_Firework(Builder builder){
		this.power = builder.power;
		this.effects = builder.effects;
	}

	public Firework getFirework(Firework base){
		FireworkMeta fwm = base.getFireworkMeta();
		
		fwm.setPower(power);
		
		if (effects != null){
			for (Yml_FireworkEffect effect : effects){
				org.bukkit.FireworkEffect.Builder builder = FireworkEffect.builder().flicker(effect.isFlicker()).trail(effect.isTrail());
				
				if (effect.getColors() != null){
					builder.withColor(effect.getColors());
				}else{
					builder.withColor(Color.WHITE);
				}
				
				if (effect.getFadingColors() != null){
					builder.withFade(effect.getFadingColors());
				}
				
				if (effect.getType() != null){
					builder.with(effect.getType());
				}
				
				fwm.addEffect(builder.build());
			}
		}
		
		base.setFireworkMeta(fwm);
		return base;
	}
	
	public static class Builder{
		private int power;
		private List<Yml_FireworkEffect> effects;
		
		public Builder(){
			effects = new ArrayList<>();
		}
		
		public Builder withPower(int power){
			this.power = power;
			return this;
		}
		
		public Builder addEffect(Yml_FireworkEffect effect){
			effects.add(effect);
			return this;
		}
		
		public Yml_Firework build(){
			return new Yml_Firework(this);
		}
	}
	
}
