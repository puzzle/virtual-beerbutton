package ch.puzzle.fyrabebier;

public class Configuration {
	private boolean drymodeFeatureEnabled;
	private String virtualRoomUrlBeerButton;
	private String virtualRoomUrlCoffeeButton;
	
	public Configuration(boolean drymodeFeatureEnabled, String virtualRoomUrlBeerButton,
			String virtualRoomUrlCoffeeButton) {
		super();
		this.drymodeFeatureEnabled = drymodeFeatureEnabled;
		this.virtualRoomUrlBeerButton = virtualRoomUrlBeerButton;
		this.virtualRoomUrlCoffeeButton = virtualRoomUrlCoffeeButton;
	}
	public boolean isDrymodeFeatureEnabled() {
		return drymodeFeatureEnabled;
	}
	public void setDrymodeFeatureEnabled(boolean drymodeFeatureEnabled) {
		this.drymodeFeatureEnabled = drymodeFeatureEnabled;
	}
	public String getVirtualRoomUrlBeerButton() {
		return virtualRoomUrlBeerButton;
	}
	public void setVirtualRoomUrlBeerButton(String virtualRoomUrlBeerButton) {
		this.virtualRoomUrlBeerButton = virtualRoomUrlBeerButton;
	}
	public String getVirtualRoomUrlCoffeeButton() {
		return virtualRoomUrlCoffeeButton;
	}
	public void setVirtualRoomUrlCoffeeButton(String virtualRoomUrlCoffeeButton) {
		this.virtualRoomUrlCoffeeButton = virtualRoomUrlCoffeeButton;
	}
}
