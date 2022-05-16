package GameSystem;

import GameSystem.*;
import GUISystem.*;
import java.awt.*;
import java.io.*;

/**
  * A BlackBoxTest for all the classes
  */
public class BlackBoxTest{
	public static void main(String args[]){
		testPlayer(); 		
		testHighScores();
		testRace();
		testCar();
		testGearbox();
		testNe();
		testShop();
		testEngines();
		testBodies();
		testTiresets();	
		testLevels();
	}

	private static void testNe() {
	}

	/**
	  * TEST FOR HIGHSCORES 
	  */	
	public static void testHighScores(){
		int nbErrors = 0; 
		HighScores hs = new HighScores();
		
		// Testing addPlayer of HighScores.
	 	//Create players for testing the HighScores		
		Player een = new Player("M Schumacher");
		een.setScore(1000);
		hs.addPlayer(een);
		
		Player twee = new Player("Coulthard");
		twee.setScore(2000);
		hs.addPlayer(twee);

		Player drie = new Player("Barrichello");
		drie.setScore(3000);
		hs.addPlayer(drie);
		
		Player vier = new Player("R Schumacher");
		vier.setScore(4000);
		hs.addPlayer(vier);

		Player vijf = new Player("H�kkinen");
		vijf.setScore(5000);
		hs.addPlayer(vijf);
		
		Player zes = new Player("Montoya");
		zes.setScore(6000);
		hs.addPlayer(zes);

		Player zeven = new Player("Villeneuve");
		zeven.setScore(7000);
		hs.addPlayer(zeven);
		
		Player acht = new Player("Fisichella");
		acht.setScore(8000);
		hs.addPlayer(acht);

		Player negen = new Player("Frentzen");
		negen.setScore(9000);
		hs.addPlayer(negen);
		
		Player tien = new Player("Verstappen");
		tien.setScore(10000);
		hs.addPlayer(tien);
	
		try	{
			if(hs.getPlayers()[0].getScore() != 10000)
				nbErrors++;
			if(hs.getPlayers()[1].getScore() != 9000)
				nbErrors++;
			if(hs.getPlayers()[2].getScore() != 8000)
				nbErrors++;
			if(hs.getPlayers()[3].getScore() != 7000)
				nbErrors++;
			if(hs.getPlayers()[4].getScore() != 6000)
				nbErrors++;
			if(hs.getPlayers()[5].getScore() != 5000)
				nbErrors++;
			if(hs.getPlayers()[6].getScore() != 4000)
				nbErrors++;
			if(hs.getPlayers()[7].getScore() != 3000)
				nbErrors++;
			if(hs.getPlayers()[8].getScore() != 2000)
				nbErrors++;
			if(hs.getPlayers()[9].getScore() != 1000)
				nbErrors++;	
		}
		catch(NullPointerException e){
			nbErrors++;
		}
		
		//Printing the number of errors
		System.out.print("HighScores correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else 
			System.out.println(nbErrors + " errors");
	}
	
	/**
	  * TEST FOR PLAYER 
	  */	
	public static void testPlayer(){
		int nbErrors = 0;
		
		//Test for the constructor of Player.
	 	Player player = new Player("Senna");
		if(!player.getName().equals("Senna"))
			nbErrors++;
		
		// Test for the constructor of Player with an empty name.
		try{	
			player = new Player("");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}	
		
		// Test for the constructor of Player with an null-reference as name.
		try{
			String name = null;	
			player = new Player(name);
			nbErrors++;
		}
		catch(NullPointerException e){}	
		
		//Test for the getName() method
		try{
			player = new Player("Test");
			if(!player.getName().equals("Test"))
				nbErrors++;
		}
		catch(Exception e){nbErrors++;}
		
		//Test for the getScore() and getMoney() methods
		try{
			player = new Player("Test");
			if(player.getScore() != 0)
				nbErrors++;
			if(player.getMoney() != 100)
				nbErrors++;
		}
		catch(Exception e){nbErrors++;}
		
		//Test for the setScore() method and addMoney() method normal case
		try{
			player = new Player("Test");
			player.setScore(100);
			if(player.getScore() != 100)
				nbErrors++;
			player.changeMoney(100);
			if(player.getMoney() != 200)
				nbErrors++;
			player.changeMoney(-50);
			if(player.getMoney() != 150)
				nbErrors++;	
		}
		catch(Exception e){nbErrors++;}
		
		//Test for the setScore() method with negative score given
		try{
			player = new Player("Test");
			player.setScore(-2323);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Printing the number of errors
		System.out.print("Player correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else 
			System.out.println(nbErrors + " errors");	
	}
	
	/**
	  * TEST FOR RACE
	  */
	private static void testRace(){
	
		/*
		 * RACE 
		 */
		int nbErrors = 0;
		
		// Test for the constructor of Race. -- normal case
		try{
			Player player = new Player("testplayer");
			Gear gear = new Gear(1,200,0.3);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(300,200,gearbox);
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			RoadBuilder roadBuilder = new RoadBuilder();
			roadBuilder.buildRoad(level);
			road[0] = roadBuilder.getRoad();
			roadBuilder.buildRoad(level);
			road[1] = roadBuilder.getRoad();
			ShopBuilder shopBuilder = new ShopBuilder();
			shopBuilder.buildShop();
			Shop shop = shopBuilder.getShop();
			Race race = new Race(player,car,road,1,shop);
			
			if(race.getPlayer() != player)
				nbErrors++;
			if(race.getCar() != car)
				nbErrors++;
			if(race.getRoads() != road)
				nbErrors++;
			if(race.getCurrentRoad() != 1)
				nbErrors++;
			if(race.getShop() != shop)
				nbErrors++;	
		}
		catch(Exception e){
			nbErrors++;
		}
		
		// Test for the constructor of Race with a null-reference as player.
		try{
			Player player = null;
			Gear gear = new Gear(1,200,0.3);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(300,200,gearbox);
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			RoadBuilder roadBuilder = new RoadBuilder();
			roadBuilder.buildRoad(level);
			road[0] = roadBuilder.getRoad();
			roadBuilder.buildRoad(level);
			road[1] = roadBuilder.getRoad();
			ShopBuilder shopBuilder = new ShopBuilder();
			shopBuilder.buildShop();
			Shop shop = shopBuilder.getShop();
			Race race = new Race(player,car,road,1,shop);
			nbErrors++;
		}
		catch(NullPointerException e){}
		
		// Test for the constructor of Race with a null-reference as car.
		try{
			Player player = new Player("testplayer");
			Car car = null;
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			RoadBuilder roadBuilder = new RoadBuilder();
			roadBuilder.buildRoad(level);
			road[0] = roadBuilder.getRoad();
			roadBuilder.buildRoad(level);
			road[1] = roadBuilder.getRoad();
			ShopBuilder shopBuilder = new ShopBuilder();
			shopBuilder.buildShop();
			Shop shop = shopBuilder.getShop();
			Race race = new Race(player,car,road,1,shop);
			nbErrors++;
		}
		catch(NullPointerException e){}
		
		// Test for the constructor of Race with a nullpointer for roads.
		try{
			Player player = new Player("testplayer");
			Gear gear = new Gear(1,200,0.3);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(300,200,gearbox);
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			ShopBuilder shopBuilder = new ShopBuilder();
			shopBuilder.buildShop();
			Shop shop = shopBuilder.getShop();
			Race race = new Race(player,car,null,1,shop);
			nbErrors++;
		}
		catch(NullPointerException e){}
		
		
		// Test for the constructor of Race with a wrong currentRoad.
		try{
			Player player = new Player("testplayer");
			Gear gear = new Gear(1,200,0.3);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(300,200,gearbox);
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			RoadBuilder roadBuilder = new RoadBuilder();
			roadBuilder.buildRoad(level);
			road[0] = roadBuilder.getRoad();
			roadBuilder.buildRoad(level);
			road[1] = roadBuilder.getRoad();
			ShopBuilder shopBuilder = new ShopBuilder();
			shopBuilder.buildShop();
			Shop shop = shopBuilder.getShop();
			Race race = new Race(player,car,road,-5,shop);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		// Test for the constructor of Race with a null reference as shop.
		try{
			Player player = new Player("testplayer");
			Gear gear = new Gear(1,200,0.3);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(300,200,gearbox);
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			Level level = levelBuilder.getLevel();
			Road[] road = new Road[2];
			RoadBuilder roadBuilder = new RoadBuilder();
			roadBuilder.buildRoad(level);
			road[0] = roadBuilder.getRoad();
			roadBuilder.buildRoad(level);
			road[1] = roadBuilder.getRoad();
			Race race = new Race(player,car,road,1,null);
			nbErrors++;
		}
		catch(NullPointerException e){}
	
		// Printing the number of errors
		System.out.print("Race correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
	}
	
	/**
	  * Test for CAR
	  */
	private static void testCar(){
		int nbErrors = 0;
		
		//Test for the constructor of Car -  normal case
		try{
			Gear gear = new Gear(1,200,0.2);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,100,gearbox);		
			if(car.getGearbox() != gearbox)
				nbErrors++;
			if(car.getPosition() != 10)
				nbErrors++;	
			if(car.getSpeed() != 0)
				nbErrors++;	
			if(car.getDistanceToCheckPoint() != 100)
				nbErrors++;		
			}
		catch(Exception e){nbErrors++;}	
		
		//Test for the constructor of Car -- distance lower than 0
		try{
			Gear gear = new Gear(1,200,0.2);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,-100,gearbox);		
			nbErrors++;
			}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of Car -- null reference as gearbox
		try{Car car = new Car(10,100,null);
			nbErrors++;		
			}
		catch(NullPointerException e){}
		
		//Test for the hasAllParts() method
		//all parts are added
		try{
			Gear gear = new Gear(1,200,0.2);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,100,gearbox);
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			int i=0;
			while(!(parts[i] instanceof Engine))
				i++;
			car.addPart(parts[i]);
			i=0;
			while(!(parts[i] instanceof Body))
				i++;
			car.addPart(parts[i]);
			i=0;
			while(!(parts[i] instanceof Tireset))
				i++;
			car.addPart(parts[i]);
			if(!car.hasAllParts())
				nbErrors++;		
			}
		catch(Exception e){nbErrors++;}
		
		//the engine is not added
		try{
			Gear gear = new Gear(1,200,0.2);
			ManualGearbox gearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,100,gearbox);
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			int i=0;
			while(!(parts[i] instanceof Body))
				i++;
			car.addPart(parts[i]);
			i=0;
			while(!(parts[i] instanceof Tireset))
				i++;
			car.addPart(parts[i]);
			if(car.hasAllParts())
				nbErrors++;		
			}
		catch(Exception e){nbErrors++;}
		
		//Printing the number of errors
		System.out.print("Car correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
	
	}
	
	/**
	  * Test for Gearbox, ManualGearbox, AutomaticGearbox, Gear
	  */
	private static void testGearbox(){
		int nbErrors = 0;
		/**
		  * Test for the Gear class
		  */
		//test for the constructor of Gear -- normal case
		try{Gear gear = new Gear(1,100,0.3);
			if(gear.getNumber() != 1)
				nbErrors++;
			if(gear.getMaximumSpeed() != 100)
				nbErrors++;
			if(gear.getAccelerationFactor()!=0.3)
				nbErrors++;	
			}
		catch(Exception e){nbErrors++;}
		
		//test for the constructor of Gear -- number lower than 1
		try{Gear gear = new Gear(0,100,0.3);
			nbErrors++;
			}
		catch(IllegalArgumentException e){}
		
		//test for the constructor of Gear -- maximum speed lower than 0
		try{Gear gear = new Gear(1,-2,0.3);
			nbErrors++;	
			}
		catch(IllegalArgumentException e){}
		
		//test for the constructor of Gear -- accelerationFactor lower than 0
		try{Gear gear = new Gear(1,100,-0.3);
			nbErrors++;	
			}
		catch(IllegalArgumentException e){}
		
		/**
		  * Test for the gearbox class
		  */
		//Test for the Gearbox constructor --  normal case
		try{
			Gear gear1 = new Gear(1,10,0.6);
			Gear gear2 = new Gear(2,20,0.5);		
			Gear gear3 = new Gear(3,30,0.4);
			Gear gear4 = new Gear(4,40,0.3);		
			Gear gear5 = new Gear(5,50,0.2);
			Gear[] gears = new Gear[]{gear1,gear2,gear3,gear4,gear5};
			ManualGearbox gearbox = new ManualGearbox(gears);
			if(gearbox.getGears() != gears)
				nbErrors++;
			if(gearbox.getNbGears() != 5)
				nbErrors++;
			}
		catch(Exception e){nbErrors++;}

		//Test for the Gearbox constructor --  nullpointer for gears
		try{
			ManualGearbox gearbox = new ManualGearbox(null);
			}
		catch(NullPointerException e){}

		/**
		  * Test for the ManualGearbox class
		  */
		//Test for the gearUp() method 
		  try{
		  	Gear gear1 = new Gear(1,10,0.6);
		  	Gear gear2 = new Gear(2,20,0.5);		
		  	Gear gear3 = new Gear(3,30,0.4);
		  	Gear gear4 = new Gear(4,40,0.3);		
		  	Gear gear5 = new Gear(5,50,0.2);
		  	Gear[] gears = new Gear[]{gear1,gear2,gear3,gear4,gear5};
		  	ManualGearbox gearbox = new ManualGearbox(gears);
		  	int nr = gearbox.getCurrentGear().getNumber();
			gearbox.gearUp();
			if(!(nr+1==gearbox.getCurrentGear().getNumber()))
				nbErrors++; 
			}
		  catch(Exception e){nbErrors++;}
		  
	  	  //Test for the gearUp() method -- the gear may not be changed up anymore
		  try{
		  	Gear gear1 = new Gear(1,10,0.6);
		  	Gear gear2 = new Gear(2,20,0.5);		
		  	Gear gear3 = new Gear(3,30,0.4);
		  	Gear gear4 = new Gear(4,40,0.3);		
		  	Gear gear5 = new Gear(5,50,0.2);
		  	Gear[] gears = new Gear[]{gear1,gear2,gear3,gear4,gear5};
		  	ManualGearbox gearbox = new ManualGearbox(gears);
		  	for(int i = 0;i<=10;i++)
				gearbox.gearUp();
			if(!(5==gearbox.getCurrentGear().getNumber()))
				nbErrors++; 
			}
		  catch(Exception e){nbErrors++;}
		
				
		//Printing the number of errors
		System.out.print("Gearbox correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
	}
	
	private static void testShop(){
		int nbErrors = 0;
		
		//test for the construction of a shop
		try{Shop shop = new Shop();
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			
			for(int i=1; i<=20;i++)
				shop.addParts(new Part[]{parts[i]});
			if(shop.getParts().length != 20)
				nbErrors++;	
			}
		catch(Exception e){nbErrors++;}
		
		//test for the sellPart() method -- normal case
		try{Shop shop = new Shop();
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			
			for(int i=1; i<=20;i++)
				shop.addParts(new Part[]{parts[i]});
			Shop temp = new Shop();
			for(int i=1; i<=20;i++)
				temp.addParts(new Part[]{parts[i]});
			Part part = shop.getParts()[0];
			Gear gear = new Gear(1,2,0.3);
			ManualGearbox manualGearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,20,manualGearbox);
			shop.sellPart(part,car);
			if(shop.getParts().length!=19)
				nbErrors++;
			for(int i=0;i<=18;i++)
				if(shop.getParts()[i] != temp.getParts()[i+1])
					{nbErrors++;}
			}
		catch(Exception e){nbErrors++;}
		
		//test for the sellPart() method -- nullpointer for part
		try{Shop shop = new Shop();
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			for(int i=1; i<=20;i++)
				shop.addParts(new Part[]{parts[i]});
			Gear gear = new Gear(1,2,0.3);
			ManualGearbox manualGearbox = new ManualGearbox(new Gear[]{gear});
			Car car = new Car(10,20,manualGearbox);	
			shop.sellPart(null,car);
			nbErrors++;
			}
		catch(NullPointerException e){}
				
		//test for the sellPart() method -- nullpointer for car
		try{Shop shop = new Shop();
			PartBuilder partBuilder = new PartBuilder();
			partBuilder.buildParts();
			Part[] parts = partBuilder.getParts();
			for(int i=1; i<=20;i++)
				shop.addParts(new Part[]{parts[i]});
			shop.sellPart(parts[1],null);
			nbErrors++;
			}
		catch(NullPointerException e){}
	
	
		//Printing the number of errors
		System.out.print("Shop correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
	}
	
	/**
	  * TEST FOR ENGINE, ENGINETYPE, ENGINECATALOG
	  */
	public static void testEngines(){
	
		/*
		 * ENGINETYPE
		 */
		int nbErrors = 0;

		//Test for the constructor of EngineType
		EngineType engineType;

		//Test for the constructor of EngineType with a wrong name (empty).	
		try{
			engineType = new EngineType("", 5, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of EngineType with a wrong name (null).	
		try{
			engineType = new EngineType(null, 5, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(NullPointerException e){}
				
		//Test for the constructor of EngineType with a wrong accelerationFactor (<=0).	
		try{
			engineType = new EngineType("test", -8, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}

		//Test for the constructor of EngineType with a wrong maxSpeed (<0).	
		try{
			engineType = new EngineType("test", 5, -8, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}

		//Test for the constructor of EngineType with a wrong price (<0).	
		try{
			engineType = new EngineType("test", 5, 5, -8, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of EngineType with a wrong strength (<0).	
		try{
			engineType = new EngineType("test", 5, 5, 5, -8, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of EngineType with a wrong repairPriceIndex (<0).	
		try{
			engineType = new EngineType("test", 5, 5, 5, 5, -8, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of EngineType with a wrong filename (empty).	
		try{
			engineType = new EngineType("test", 5, 5, 5, 5, 5, "");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of EngineType with a wrong name (null).	
		try{
			engineType = new EngineType(null, 5, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(NullPointerException e){}
					
		//Printing the number of errors
		System.out.print("Engine type correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
		
		/*
		 * ENGINE 
		 */ 
		nbErrors = 0;
		engineType = new EngineType("test", 5, 5, 5, 5, 5, "test");				
		
		//Test for the constructor of Engine
		Engine engine = new Engine(engineType);
		if(engine.getPartType() != engineType)
				nbErrors++;

		//Test for the constructor of engine with a null-reference as engineType
		try{
			engineType = null;
			engine = new Engine(engineType);
		}
		catch(NullPointerException e){}
		
		//Printing the number of errors
		System.out.print("Engine correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");		
		
		/*
		 * ENGINECATALOG
		 */
		nbErrors = 0;
		
		// Test for the RoadPartCatalog.
		if(!EngineCatalog.getReference().getEngineTypes()[0].getName().equals("Weak Engine")) 
			nbErrors++;
		if(!EngineCatalog.getReference().getEngineTypes()[1].getName().equals("High-performance Engine")) 
			nbErrors++;
				
		//Printing the number of errors
		System.out.print("EngineCatalog correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else 
			System.out.println(nbErrors + " errors");
	}
	
	/**
	  * TEST FOR BODY, BODYTYPE, BODYCATALOG
	  */
	public static void testBodies(){
	
		/*
		 * BODYTYPE
		 */
		int nbErrors = 0;
		//Test for the constructor of BodyType
		BodyType bodyType;

		//Test for the constructor of BodyType with a wrong name (empty).	
		try{
			bodyType = new BodyType("", 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of BodyType with a wrong name (null).	
		try{
			bodyType = new BodyType(null, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(NullPointerException e){}

		//Test for the constructor of BodyType with a wrong accelerationFactor (<=0).	
		try{
			bodyType = new BodyType("test", -8, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of BodyType with a wrong price (<0).	
		try{
			bodyType = new BodyType("test", 5, -8, 5, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of BodyType with a wrong strength (<0).	
		try{
			bodyType = new BodyType("test", 5, 5, -8, 5, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of BodyType with a wrong repairPriceIndex (<0).	
		try{
			bodyType = new BodyType("test", 5, 5, 5, -8, "test");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
			
		//Test for the constructor of BodyType with a wrong filename (empty).	
		try{
			bodyType = new BodyType("test", 5, 5, 5, 5, "");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of BodyType with a wrong filename (null).	
		try{
			bodyType = new BodyType("test", 5, 5, 5, 5, null);
			nbErrors++;
		}
		catch(NullPointerException e){}
		
		//Printing the number of errors
		System.out.print("Body type correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
		
		/*
		 * Body 
		 */ 
		nbErrors = 0;
		bodyType = new BodyType("test", 5, 5, 5, 5, "test");				
		
		//Test for the constructor of Body
		Body body = new Body(bodyType);
		if(body.getPartType() != bodyType)
				nbErrors++;

		//Test for the constructor of body with a null-reference as bodyType
		try{
			bodyType = null;
			body = new Body(bodyType);
		}
		catch(NullPointerException e){}
		
		//Printing the number of errors
		System.out.print("Body correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");		
		
		/*
		 * BODYCATALOG
		 */
		nbErrors = 0;

		// Test for the RoadPartCatalog.
		if(!BodyCatalog.getReference().getBodyTypes()[0].getName().equals("Normal Body")) 
			nbErrors++;
		if(!BodyCatalog.getReference().getBodyTypes()[1].getName().equals("Lightweight Body")) 
			nbErrors++;
				
		//Printing the number of errors
		System.out.print("BodyCatalog correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else 
			System.out.println(nbErrors + " errors");
	}

	/**
	  * TEST FOR TIRESET, TIRESETTYPE, TIRESETCATALOG
	  */
	public static void testTiresets(){
	
		/*
		 * TIRESETTYPE
		 */
		int nbErrors = 0;
		//Test for the constructor of TiresetType
		TiresetType tiresetType;

		//Test for the constructor of TiresetType with a wrong name (empty).	
		try{
			tiresetType = new TiresetType("", 5, 5, 5, 5, 5, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}

		//Test for the constructor of TiresetType with a wrong name (null).	
		try{
			tiresetType = new TiresetType(null, 5, 5, 5, 5, 5, 5, "test");
			nbErrors++;
		}
		catch(NullPointerException e){}
		
		//Test for the constructor of TiresetType with a wrong durability (<=0).	
		try{
			tiresetType = new TiresetType("test", -8, 5, 5, 5, 5, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of TiresetType with a wrong grip (<=0).	
		try{
			tiresetType = new TiresetType("test", 5, -8, 5, 5, 5, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of TiresetType with a wrong steerAbility (<0).	
		try{
			tiresetType = new TiresetType("test", 5, 5, -8, 5, 5, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of TiresetType with a wrong price (<0).	
		try{
			tiresetType = new TiresetType("test", 5, 5, 5, -8, 5, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of TiresetType with a wrong strength (<0).	
		try{
			tiresetType = new TiresetType("test", 5, 5, 5, 5, -8, 5, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of TiresetType with a wrong repairPriceIndex (<0).	
		try{
			tiresetType = new TiresetType("test", 5, 5, 5, 5, 5, -8, "file");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
			
		//Test for the constructor of TiresetType with a wrong filename (empty).	
		try{
			tiresetType = new TiresetType("test", 5, 5, 5, 5, 5, 5, "");
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
	
		//Test for the constructor of TiresetType with a wrong name (null).	
		try{
			tiresetType = new TiresetType("test", 5, 5, 5, 5, 5, 5, null);
			nbErrors++;
		}
		catch(NullPointerException e){}
					
		//Printing the number of errors
		System.out.print("Tireset type correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
		
		/*
		 * Tireset 
		 */ 
		nbErrors = 0;
		tiresetType = new TiresetType("test", 5, 5, 5, 5, 5, 5, "test");				
		
		//Test for the constructor of Tireset
		Tireset tireset = new Tireset(tiresetType);
		if(tireset.getPartType() != tiresetType)
				nbErrors++;

		//Test for the constructor of tireset with a null-reference as tiresetType
		try{
			tiresetType = null;
			tireset = new Tireset(tiresetType);
		}
		catch(NullPointerException e){}
		
		//Printing the number of errors
		System.out.print("Tireset correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");		
		
		/*
		 * TIRESETCATALOG
		 */
		nbErrors = 0;

		// Test for the RoadPartCatalog.
		if(!TiresetCatalog.getReference().getTiresetTypes()[0].getName().equals("Normal Tireset"))
			nbErrors++;
		if(!TiresetCatalog.getReference().getTiresetTypes()[1].getName().equals("Racing Tireset"))
			nbErrors++;
				
		//Printing the number of errors
		System.out.print("Tireset Catalog correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else 
			System.out.println(nbErrors + " errors");
	}
	
	/**
	  * TEST FOR LEVEL, LEVELTYPE, LEVELCATALOG, LEVELBUILDER
	  */	
	public static void testLevels(){
	
		/*
		 * LEVELTYPE
		 */
		int nbErrors = 0;

		//Test for the constructor of LevelType
		int[] obstacleTypeProbability = {3,4,3};
		int numberOfParts = 10;
		int numberOfStaticObstacles = 20;
		int numberOfMovingObstacles = 25;
		int numberOfBonusItems = 30;
		int bonusTime=100;
		int startTime=500;
		int[] partProbability = {1,2,1,2,4};
		int levelNumber = 4;
		LevelType levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
		numberOfMovingObstacles, numberOfBonusItems,bonusTime, startTime, partProbability, levelNumber);
			
		for(int i=0;i<3;i++)
			if(obstacleTypeProbability[i] != levelType.getObstacleTypeProbability()[i])
				nbErrors++;
		if(numberOfParts != 10)
			nbErrors++;
		if(numberOfStaticObstacles != 20)
			nbErrors++;
		if(numberOfMovingObstacles != 25)
			nbErrors++;
		if(numberOfBonusItems != 30)
			nbErrors++;
		if(bonusTime != 100)
			nbErrors++;
		if(startTime != 500)
			nbErrors++;
		for(int i=0;i<5;i++)
			if(partProbability[i] != levelType.getPartProbability()[i])
				nbErrors++;
		if(levelNumber != 4)
			nbErrors++;

		//Test for the constructor of LevelType with a wrong numberOfParts.	
		try{
			levelType = new LevelType(obstacleTypeProbability,-numberOfParts,numberOfStaticObstacles,
			numberOfMovingObstacles, numberOfBonusItems,bonusTime, startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}

		//Test for the constructor of LevelType with a wrong numberOfStaticObstacles.	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,-numberOfStaticObstacles, 
			numberOfMovingObstacles, numberOfBonusItems,bonusTime, startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of LevelType with a wrong numberOfMovingObstacles.	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
			-numberOfMovingObstacles, numberOfBonusItems,bonusTime, startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of LevelType with a wrong numberOfBonusItems.	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
		numberOfMovingObstacles, -numberOfBonusItems,bonusTime, startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of LevelType with a wrong bonusTime	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
		numberOfMovingObstacles, numberOfBonusItems,-bonusTime, startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Test for the constructor of LevelType with a wrong startTime.	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
			numberOfMovingObstacles, numberOfBonusItems,bonusTime, -startTime, partProbability, levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}

		//Test for the constructor of LevelType with a wrong levelNumber.	
		try{
			levelType = new LevelType(obstacleTypeProbability,numberOfParts,numberOfStaticObstacles, 
			numberOfMovingObstacles, numberOfBonusItems,bonusTime, startTime, partProbability, -levelNumber);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Printing the number of errors
		System.out.print("Level type correctness: ");	
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
		
		/*
		 * LEVEL
		 */ 
		nbErrors = 0;				

		//Test for the constructor of Level	
		Level level = new Level(levelType);
		if(level.getLevelType() != levelType)
				nbErrors++;

		//Test for the constructor of Level	with a null-reference as levelType
		try{
			levelType = null;
			level = new Level(levelType);
		}
		catch(NullPointerException e){}
		
		//Printing the number of errors
		System.out.print("Level correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
		
		/*
		 * LEVELBUILDER
		 */		
		nbErrors = 0;
		
		// Test for the buildLevel of LevelBuilder.
		try{
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(1);
			level = levelBuilder.getLevel();
			if(level.getLevelType().getLevelNumber() != 1)
				nbErrors++;
			}
		catch(Exception e){
			nbErrors++;
		}
		
		// Test for the buildLevel of LevelBuilder with a wrong levelNumber.
		try{
			LevelBuilder levelBuilder = new LevelBuilder();
			levelBuilder.buildLevel(-1);
			nbErrors++;
		}
		catch(IllegalArgumentException e){}
		
		//Printing the number of errors
		System.out.print("Level builder correctness: ");
		if(nbErrors == 0)
			System.out.println("no errors");
		else System.out.println(nbErrors + " errors");	
	}

	
}
