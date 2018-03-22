package be.kiop.characters.heroes;

public enum Heroes {
	Mage(Mage.class),
	Warrior(Warrior.class);
	
	private Class<?> hero;
	Heroes(Class<?> hero){
		this.hero = hero;
	}
	
	public Class<?> getHeroClass() {
		return this.hero;
	}
}
