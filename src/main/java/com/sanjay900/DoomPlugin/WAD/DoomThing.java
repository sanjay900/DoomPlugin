package com.sanjay900.DoomPlugin.WAD;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedHashMap;
import java.util.Map;
public class DoomThing {
		short x;
	short y;
	short angle;
		DoomThingType type;
		short flags;
		private boolean skill1and2;
	private boolean skill3;
	private boolean skill4and5;
	private boolean deaf;
		private boolean multiOnly;
		public DoomThing(byte[] data) {
		ByteBuffer bb = ByteBuffer.wrap(data);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		this.x = bb.getShort(0);
		this.y = bb.getShort(2);
		this.angle = bb.getShort(4);
		short typeshort = bb.getShort(6);
		this.type = DoomThingType.forCode(typeshort);
		this.flags = bb.getShort(8);
		skill1and2 = (flags & 0x0001) != 0;
		skill3 = (flags & 0x0002) != 0;
		skill4and5 = (flags & 0x0004) != 0;
		deaf = (flags & 0x0008) != 0;
		multiOnly = (flags & 0x0010) != 0;

	}
		public boolean isSkill1and2() {
		return skill1and2;
	}
	public boolean isSkill3() {
		return skill3;
	}
	public boolean isSkill4and5() {
		return skill4and5;
	}
	public boolean isDeaf() {
		return deaf;
	}
	public boolean isMultiOnly() {
		return multiOnly;
	}
		public enum DoomThingCatagory {
		Artifact,Powerup,Weapon,Ammunition,Key,Monster,Obstacle,Decoration,Other
	}
		public enum DoomThingType {
		/**Artifact item*/
		Berserk(2023,DoomThingCatagory.Artifact),ComputerMap(2026,DoomThingCatagory.Artifact),
		HealthPotion(2014,DoomThingCatagory.Artifact),Invisibility(2024,DoomThingCatagory.Artifact),Invulnerability(2022,DoomThingCatagory.Artifact),
		LightAmplificationVisor(2045,DoomThingCatagory.Artifact),Megasphere(83,DoomThingCatagory.Artifact),SoulSphere(2013,DoomThingCatagory.Artifact),
		SpirtualArmour(2015,DoomThingCatagory.Artifact),
		/**Powerups*/
		Backpack(8,DoomThingCatagory.Powerup),BlueArmour(2019,DoomThingCatagory.Powerup),
		GreeenArmour(2018,DoomThingCatagory.Powerup),Medikit(2012,DoomThingCatagory.Powerup),
		RadiationSuit(2026,DoomThingCatagory.Powerup),Stimpack(2011,DoomThingCatagory.Powerup),
		/**Weapons*/
		BFG900(2006,DoomThingCatagory.Weapon),Chaingun(2002,DoomThingCatagory.Weapon),
		Chainsaw(2005,DoomThingCatagory.Weapon),PlasmaRifle(2004,DoomThingCatagory.Weapon),
		RocketLauncher(2003,DoomThingCatagory.Weapon),ShotGun(2001,DoomThingCatagory.Weapon),
		SuperShotGun(82,DoomThingCatagory.Weapon),
		/**Ammunition*/
		AmmoClip(2007,DoomThingCatagory.Ammunition),AmmoBox(2048,DoomThingCatagory.Ammunition),
		RocketBox(2046,DoomThingCatagory.Ammunition),ShellBox(2049,DoomThingCatagory.Ammunition),
		CellCharge(2047,DoomThingCatagory.Ammunition),CellChargePack(17,DoomThingCatagory.Ammunition),
		Rocket(2010,DoomThingCatagory.Ammunition),ShotGunShells(2008,DoomThingCatagory.Ammunition),
		/**Keys*/
		BlueKeycard(5,DoomThingCatagory.Key),BlueSkullKey(40,DoomThingCatagory.Key),
		RedKeycard(13,DoomThingCatagory.Key),RedSkullKey(38,DoomThingCatagory.Key),
		YellowKeycard(6,DoomThingCatagory.Key),YellowSkullKey(39,DoomThingCatagory.Key),
		/**Monsters*/
		Arachnotron(68,DoomThingCatagory.Monster), ArchVile(64,DoomThingCatagory.Monster), Baron_of_Hell(3003,DoomThingCatagory.Monster), 
		Cacodemon(3005,DoomThingCatagory.Monster), Chaingunner(65,DoomThingCatagory.Monster), Commander_Keen(72,DoomThingCatagory.Monster), 
		Cyberdemon(16,DoomThingCatagory.Monster), Demon(3002,DoomThingCatagory.Monster), Trooper(3004,DoomThingCatagory.Monster), 
		Former_Human_Sergeant(9,DoomThingCatagory.Monster), Hell_Knight(69,DoomThingCatagory.Monster), 
		Imp(3001,DoomThingCatagory.Monster), Mancubus(67,DoomThingCatagory.Monster), Pain_Elemental(71,DoomThingCatagory.Monster), 
		Revenant(66,DoomThingCatagory.Monster), Spectre(58,DoomThingCatagory.Monster), Spider_Mastermind(7,DoomThingCatagory.Monster), 
		Wolfenstein_SS(84,DoomThingCatagory.Monster),
		/**Obstacles*/
		Barrel(2035,DoomThingCatagory.Obstacle), Barrel2(70,DoomThingCatagory.Obstacle), Burnttree(43,DoomThingCatagory.Obstacle), Candelabra(35,DoomThingCatagory.Obstacle), Evileye(41,DoomThingCatagory.Obstacle), Fiveskulls(28,DoomThingCatagory.Obstacle), Floatingskull(42,DoomThingCatagory.Obstacle), Floorlamp(2028,DoomThingCatagory.Obstacle), Hangingleg2(53,DoomThingCatagory.Obstacle), Hangingpairoflegs2(52,DoomThingCatagory.Obstacle), Hangingtorsobrainremoved(78,DoomThingCatagory.Obstacle), Hangingtorsolookingdown(75,DoomThingCatagory.Obstacle),  Hangingtorsolookingup(77,DoomThingCatagory.Obstacle),  Hangingtorsoopenskull(76,DoomThingCatagory.Obstacle),  Hangingvictimarmsout(50,DoomThingCatagory.Obstacle),  Hangingvictimgutsandbrainremoved(74,DoomThingCatagory.Obstacle),  Hangingvictimgutsremoved(73,DoomThingCatagory.Obstacle),  Hangingvictimonelegged2(51,DoomThingCatagory.Obstacle),  Hangingvictimtwitching(49,DoomThingCatagory.Obstacle),  Impaledhuman(25,DoomThingCatagory.Obstacle), Largebrowntree(54,DoomThingCatagory.Obstacle), Pileofskullsandcandles(29,DoomThingCatagory.Obstacle), Shortbluefirestick(55,DoomThingCatagory.Obstacle), Shortgreenfirestick(56,DoomThingCatagory.Obstacle), Shortgreenpillar(31,DoomThingCatagory.Obstacle), Shortgreenpillarwithbeatingheart(36,DoomThingCatagory.Obstacle), Shortredfirestick(57,DoomThingCatagory.Obstacle), Shortredpillar(33,DoomThingCatagory.Obstacle), Shortredpillarwithskull(37,DoomThingCatagory.Obstacle), Shorttechnofloorlamp(86,DoomThingCatagory.Obstacle), Skullonapole(27,DoomThingCatagory.Obstacle), Stalagmite(47,DoomThingCatagory.Obstacle), Tallbluefirestick(44,DoomThingCatagory.Obstacle), Tallgreenfirestick(45,DoomThingCatagory.Obstacle), Tallgreenpillar(30,DoomThingCatagory.Obstacle), Tallredfirestick(46,DoomThingCatagory.Obstacle), Tallredpillar(32,DoomThingCatagory.Obstacle), Talltechnofloorlamp(85,DoomThingCatagory.Obstacle), Talltechnopillar(48,DoomThingCatagory.Obstacle), Twitchingimpaledhuman(26,DoomThingCatagory.Obstacle), 
		/**Decorations*/
		Bloodymess(10,DoomThingCatagory.Decoration), Bloodymess2(12,DoomThingCatagory.Decoration), Candle(34,DoomThingCatagory.Decoration), Deadcacodemon(22,DoomThingCatagory.Decoration), Deaddemon(21,DoomThingCatagory.Decoration), Deadformberhuman(18,DoomThingCatagory.Decoration), Deadformersergeant(19,DoomThingCatagory.Decoration), Deadimp(20,DoomThingCatagory.Decoration), Deadlostsoul(23,DoomThingCatagory.Decoration), Deadplayer(15,DoomThingCatagory.Decoration), Hangingleg(62,DoomThingCatagory.Decoration), Hangingpairoflegs(60,DoomThingCatagory.Decoration), Hangingvictimarmsout2(59,DoomThingCatagory.Decoration),  Hangingvictimonelegged(61,DoomThingCatagory.Decoration),  Hangingvictimtwitching2(63,DoomThingCatagory.Decoration),  Poolofblood(79,DoomThingCatagory.Decoration), Poolofblood2(80,DoomThingCatagory.Decoration), Poolofbloodandflesh(24,DoomThingCatagory.Decoration), Poolofbrains(81,DoomThingCatagory.Decoration), 
		/**Other*/
		Player1(1,DoomThingCatagory.Other),Player2(2,DoomThingCatagory.Other),Player3(3,DoomThingCatagory.Other),Player4(4,DoomThingCatagory.Other),BossBrain(88,DoomThingCatagory.Other),DeathMatchStart(11,DoomThingCatagory.Other),SpawnShooter(89,DoomThingCatagory.Other),SpawnSpot(87,DoomThingCatagory.Other),TeleportLanding(14,DoomThingCatagory.Other);
				private final int id;
		private final DoomThingCatagory type;
				DoomThingType(int id, DoomThingCatagory type) {
			this.id = id;
			this.type = type;
		}
				private static final Map<Integer, DoomThingType> BY_CODE_MAP = new LinkedHashMap<>();
		static {
			for (DoomThingType thing : DoomThingType.values()) {
				BY_CODE_MAP.put(thing.id, thing);
			}
		}
				public static DoomThingType forCode(int code) {
			return BY_CODE_MAP.get(code);
		}
				public DoomThingCatagory getDoomThingCatagory() {return type;}
				public double getId() { return id; }
	}
}
