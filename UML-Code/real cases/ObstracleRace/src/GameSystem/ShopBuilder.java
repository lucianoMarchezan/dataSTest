package GameSystem;

/**
  * A class for building shops
  */ 
class ShopBuilder{

	//CONSTRUCTORS
	public ShopBuilder(){
		$PARTBUILDER = new PartBuilder();
	}
	
	//INSPECTORS
	/**
	  * Get the shop built by this shop builder
	  */
	Shop getShop(){
		return $shop;
	}
	
	//MUTATORS
	/**
	  * Build a shop
	  */
	void buildShop(){
		$shop = new Shop();	
		$PARTBUILDER.buildParts();
		$shop.addParts($PARTBUILDER.getParts());
	}
	
	//VARIABLES
	/**
	  * A variable for holding the shop built by this shop builder
	  */
	private Shop $shop;  
	
	/**
	  * A variable for holding the partbuilder used by this shop builder
	  */
	private final PartBuilder $PARTBUILDER;
}