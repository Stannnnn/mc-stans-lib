package Stan.Lib.World;

public class Region {

	private String world;
	private Point a;
	private Point b;
	
	public Region(String world, Point a, Point b){
		this.world = world;
		this.a = a;
		this.b = b;
	}
	
	public boolean isInRegion(String word, Point p){
		return isInRegion(word, p.getX(), p.getY(), p.getZ());
	}
	
	private boolean isInRegion(String world, int x, int y, int z) {
		return this.world.equals(world) && (x >= this.a.getX()) && (x <= this.b.getX()) && (y >= this.a.getY()) && (y <= this.b.getY()) && (z >= this.a.getZ()) && (z <= this.b.getZ());
	}
	
}
