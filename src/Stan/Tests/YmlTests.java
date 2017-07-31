package Stan.Tests;

import java.util.ArrayList;
import java.util.List;

import Stan.Lib.Main;
import Stan.Lib.Utils.Data;
import Stan.Lib.Yml.YmlUtils;
import Stan.Lib.Yml.Objects.Yml_Action;

@SuppressWarnings({ "unused", "unchecked" })
public class YmlTests {

	public YmlTests(){
		
		//test1();
		//test2();
		test3();
		
	}
	
	private void test1(){
		Data d = new Data(Main.getInstance(), "temp.yml");
		
		d.getData().set("a.b.c.Actions.0.Action", "ConsoleMessage");
		d.getData().set("a.b.c.Actions.0.Value", "say Hello world!");
		d.getData().set("a.b.c.Actions.1.Action", "ConsoleMessage");
		//d.getData().set("a.b.c.Actions.1.Value", "say Hello other world!");
		
		List<Yml_Action> actions = (List<Yml_Action>) YmlUtils.get(d, "a.b.c", Yml_Action.class, true);
		
		YmlUtils.set(d, "a.d", actions);
		
		//Yml_Actions actions2 = (Yml_Actions) YmlUtils.get(d, "a.d", Yml_Actions.class);
		
		//System.out.println(actions.equals(actions2));
		
		d.saveData();
	}
	
	private void test2(){
		Data d = new Data(Main.getInstance(), "temp.yml");
		
		d.getData().set("b.b.c.Actions.0.Action", "ConsoleMessage");
		d.getData().set("b.b.c.Actions.0.Value", "say Hello world!");
		d.getData().set("b.b.c.Actions.1.Action", "ConsoleMessage");
		d.getData().set("b.b.c.Actions.1.Value", "say Hello other world!");
		
		ArrayList<Yml_Action> actions = (ArrayList<Yml_Action>) YmlUtils.get(d, "b.b.c.Actions", Yml_Action.class, true);
		
		YmlUtils.set(d, "b.d", actions);
		
		d.saveData();
	}
	
	private void test3(){
		Data d = new Data(Main.getInstance(), "temp.yml");
		
		d.getData().set("b.b.c.Actions.0.Action", "ConsoleMessage");
		d.getData().set("b.b.c.Actions.0.Value", "say Hello world!");
		d.getData().set("b.b.c.Actions.1.Action", "ConsoleMessage");
		d.getData().set("b.b.c.Actions.1.Value", "say Hello other world!");
		
		ArrayList<Yml_Action> actions = (ArrayList<Yml_Action>) YmlUtils.get(d, "b.b.c.Actions", Yml_Action.class, true);
		
		System.out.println(actions.get(0));
		
		YmlUtils.set(d, "b.d", actions);
		
		d.saveData();
	}
	
}
