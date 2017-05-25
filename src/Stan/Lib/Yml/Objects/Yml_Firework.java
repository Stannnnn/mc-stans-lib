package Stan.Lib.Yml.Objects;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Firework {

	@YmlObject
	private int power;
	
	@YmlObject
	private List<Yml_FireworkEffect> effects;

	public Firework getFirework(Firework base){
		FireworkMeta fwm = base.getFireworkMeta();
		
		fwm.setPower(power);
		
		if (effects != null){
			for (Yml_FireworkEffect effect : effects){
				Builder builder = FireworkEffect.builder().flicker(effect.isFlicker()).trail(effect.isTrail());
				
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
	
}
