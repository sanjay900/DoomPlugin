package com.sanjay900.DoomPlugin.WAD;

import java.util.HashMap;
public class DoomLineParser {
	public HashMap<Integer, LineType> LineTypes = new HashMap<>();
	public DoomLineParser() {
		/**The following just allows us to convert from doom number based LineTypes to an Enumeration thats
		 * human understandable, for easier coding.
		 */
								LineTypes.put(0,new LineType(LineTypeCatagory.NoAction));
				LineTypes.put(1,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(117,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(63,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(114,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(29,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(111,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(90,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(105,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(4,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(108,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenWaitClose,4));
		LineTypes.put(31,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(118,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(61,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(114,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(103,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(112,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(86,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(106,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(2,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(109,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(46,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.GunRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen));
		LineTypes.put(42,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(116,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(50,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(113,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(75,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(107,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(3,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(110,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.CloseStayClosed));
		LineTypes.put(196,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseWaitOpen,30));
		LineTypes.put(175,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseWaitOpen,30));
		LineTypes.put(76,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseWaitOpen,30));
		LineTypes.put(16,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.CloseWaitOpen,30));
		LineTypes.put(26,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4,LineTypeDoorLock.Blue));
		LineTypes.put(28,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4,LineTypeDoorLock.Red));
		LineTypes.put(27,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushRepeat,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenWaitClose,4,LineTypeDoorLock.Yellow));
		LineTypes.put(32,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Blue));
		LineTypes.put(33,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Red));
		LineTypes.put(34,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.PushOnce,LineTypeMotionSpeed.Slow,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Yellow));
		LineTypes.put(99,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Blue));
		LineTypes.put(134,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Red));
		LineTypes.put(136,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Yellow));
		LineTypes.put(133,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Blue));
		LineTypes.put(135,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Red));
		LineTypes.put(137,new LineType(LineTypeCatagory.Door,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDoorType.OpenStayOpen,LineTypeDoorLock.Yellow));
				LineTypes.put(60,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(23,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(82,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(38,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(177,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.AdjacentNumerical));
		LineTypes.put(159,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.AdjacentNumerical));
		LineTypes.put(84,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.AdjacentNumerical));
		LineTypes.put(37,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborFloor,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.AdjacentNumerical));
		LineTypes.put(69,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(18,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(128,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(119,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(132,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(131,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(129,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(130,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(222,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(221,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(220,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(219,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.NextNeighborFloor));
		LineTypes.put(64,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(101,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(91,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(5,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(24,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.GunOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(65,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Below8LowestNeighborCeiling,LineTypeMiscellaneous.EnableCrushing));
		LineTypes.put(55,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Below8LowestNeighborCeiling,LineTypeMiscellaneous.EnableCrushing));
		LineTypes.put(94,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Below8LowestNeighborCeiling,LineTypeMiscellaneous.EnableCrushing));
		LineTypes.put(56,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Below8LowestNeighborCeiling,LineTypeMiscellaneous.EnableCrushing));
		LineTypes.put(45,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(102,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(83,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(19,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(70,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Above8HighestNeighborFloor));
		LineTypes.put(71,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Above8HighestNeighborFloor));
		LineTypes.put(98,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Above8HighestNeighborFloor));
		LineTypes.put(36,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Above8HighestNeighborFloor));
		LineTypes.put(180,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24));
		LineTypes.put(161,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24));
		LineTypes.put(92,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24));
		LineTypes.put(58,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24));
		LineTypes.put(179,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.TriggerSector));
		LineTypes.put(160,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.TriggerSector));
		LineTypes.put(93,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.TriggerSector));
		LineTypes.put(59,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,24,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeModel.TriggerSector));
		LineTypes.put(176,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.ShortestLowerTexture));
		LineTypes.put(158,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.ShortestLowerTexture));
		LineTypes.put(96,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.ShortestLowerTexture));
		LineTypes.put(30,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.ShortestLowerTexture));
		LineTypes.put(178,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit512));
		LineTypes.put(140,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit512));
		LineTypes.put(147,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit512));
		LineTypes.put(142,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit512));
		LineTypes.put(190,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.TriggerSector));
		LineTypes.put(189,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.TriggerSector));
		LineTypes.put(154,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.TriggerSector));
		LineTypes.put(153,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.TriggerSector));
		LineTypes.put(78,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.AdjacentNumerical));
		LineTypes.put(241,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.AdjacentNumerical));
		LineTypes.put(240,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.AdjacentNumerical));
		LineTypes.put(239,new LineType(LineTypeCatagory.FloorMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.None,LineTypeSectorPropertyChanger.CopyTextureAndType,LineTypeDestination.ShortestLowerTexture,LineTypeModel.AdjacentNumerical));
				LineTypes.put(43,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Floor));
		LineTypes.put(41,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Floor));
		LineTypes.put(152,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Floor));
		LineTypes.put(145,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Fast,LineTypeDestination.Floor));
		LineTypes.put(186,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborCeiling));
		LineTypes.put(166,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborCeiling));
		LineTypes.put(151,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborCeiling));
		LineTypes.put(40,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborCeiling));
		LineTypes.put(187,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.AboveFloor8));
		LineTypes.put(167,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.AboveFloor8));
		LineTypes.put(72,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.AboveFloor8));
		LineTypes.put(44,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.AboveFloor8));
		LineTypes.put(205,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(203,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(201,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(199,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.LowestNeighborCeiling));
		LineTypes.put(205,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(204,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(202,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
		LineTypes.put(200,new LineType(LineTypeCatagory.CeilingMover,LineTypeTriggerType.WalkOnce,LineTypeDirection.Down,LineTypeMotionSpeed.Slow,LineTypeDestination.HighestNeighborFloor));
				LineTypes.put(66,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,24));
		LineTypes.put(15,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,24));
		LineTypes.put(148,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,24));
		LineTypes.put(143,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,24));
		LineTypes.put(67,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,LineTypeDestination.Unit32));
		LineTypes.put(14,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,LineTypeDestination.Unit32));
		LineTypes.put(149,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,LineTypeDestination.Unit32));
		LineTypes.put(144,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTexture,LineTypeModel.TriggerSector,LineTypeDestination.Unit32));
		LineTypes.put(68,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTextureResetType,LineTypeModel.TriggerSector,LineTypeDestination.NextAdjacentFloor));
		LineTypes.put(20,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTextureResetType,LineTypeModel.TriggerSector,LineTypeDestination.NextAdjacentFloor));
		LineTypes.put(95,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTextureResetType,LineTypeModel.TriggerSector,LineTypeDestination.NextAdjacentFloor));
		LineTypes.put(22,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTextureResetType,LineTypeModel.TriggerSector,LineTypeDestination.NextAdjacentFloor));
		LineTypes.put(47,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.GunOnce,LineTypeMotionSpeed.Slow,LineTypeSectorPropertyChanger.CopyTextureResetType,LineTypeModel.TriggerSector,LineTypeDestination.NextAdjacentFloor));
		LineTypes.put(181,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,3,LineTypeDestination.Perpetual));
		LineTypes.put(162,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,3,LineTypeDestination.Perpetual));
		LineTypes.put(87,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,3,LineTypeDestination.Perpetual));
		LineTypes.put(53,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,3,LineTypeDestination.Perpetual));
		LineTypes.put(182,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeDestination.Stop));
		LineTypes.put(163,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeDestination.Stop));
		LineTypes.put(89,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeDestination.Stop));
		LineTypes.put(54,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeDestination.Stop));
		LineTypes.put(62,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(21,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(88,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(10,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(123,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(122,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(120,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(121,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,3,LineTypeDestination.LowestNeighborFloor));
		LineTypes.put(211,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Instant,LineTypeDestination.Ceiling));
		LineTypes.put(212,new LineType(LineTypeCatagory.Platform,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Instant,LineTypeDestination.Ceiling));
				LineTypes.put(184,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow));
		LineTypes.put(49,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow));
		LineTypes.put(73,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow));
		LineTypes.put(25,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow));
		LineTypes.put(183,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast));
		LineTypes.put(164,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast));
		LineTypes.put(77,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast));
		LineTypes.put(6,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast));
		LineTypes.put(185,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Slow,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(165,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Slow,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(150,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Slow,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(141,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Slow,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(188,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchRepeat,LineTypeDestination.Stop));
		LineTypes.put(168,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.SwitchOnce,LineTypeDestination.Stop));
		LineTypes.put(74,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkRepeat,LineTypeDestination.Stop));
		LineTypes.put(57,new LineType(LineTypeCatagory.Crusher,LineTypeTriggerType.WalkOnce,LineTypeDestination.Stop));
				LineTypes.put(258,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit8));
		LineTypes.put(7,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit8));
		LineTypes.put(256,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit8));
		LineTypes.put(8,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Slow,LineTypeDestination.Unit8));
		LineTypes.put(259,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.SwitchRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.Unit16));
		LineTypes.put(127,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.SwitchOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.Unit16));
		LineTypes.put(257,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.WalkRepeat,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.Unit16));
		LineTypes.put(100,new LineType(LineTypeCatagory.Stair,LineTypeTriggerType.WalkOnce,LineTypeDirection.Up,LineTypeMotionSpeed.Fast,LineTypeDestination.Unit16));
				LineTypes.put(230,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.NextHighestNeighborFloor));
		LineTypes.put(229,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.NextHighestNeighborFloor));
		LineTypes.put(228,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.NextHighestNeighborFloor));
		LineTypes.put(227,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.NextHighestNeighborFloor));
		LineTypes.put(234,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.NextHighestNeighborFloor));
		LineTypes.put(233,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.NextLowestNeighborFloor));
		LineTypes.put(232,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.NextLowestNeighborFloor));
		LineTypes.put(231,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.NextLowestNeighborFloor));
		LineTypes.put(238,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.CurrentFloor));
		LineTypes.put(237,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.SwitchOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.CurrentFloor));
		LineTypes.put(236,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkRepeat,LineTypeMotionSpeed.Fast,LineTypeDestination.CurrentFloor));
		LineTypes.put(235,new LineType(LineTypeCatagory.Elevator,LineTypeTriggerType.WalkOnce,LineTypeMotionSpeed.Fast,LineTypeDestination.CurrentFloor));
				LineTypes.put(139,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchRepeat,LineTypeLighting.Unit35));
		LineTypes.put(170,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchOnce,LineTypeLighting.Unit35));
		LineTypes.put(79,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkRepeat,LineTypeLighting.Unit35));
		LineTypes.put(35,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkOnce,LineTypeLighting.Unit35));
		LineTypes.put(138,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchRepeat,LineTypeLighting.Unit255));
		LineTypes.put(171,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchOnce,LineTypeLighting.Unit255));
		LineTypes.put(81,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkRepeat,LineTypeLighting.Unit255));
		LineTypes.put(13,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkOnce,LineTypeLighting.Unit255));
		LineTypes.put(192,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchRepeat,LineTypeLighting.MaximumNeibor));
		LineTypes.put(169,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchOnce,LineTypeLighting.MaximumNeibor));
		LineTypes.put(80,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkRepeat,LineTypeLighting.MaximumNeibor));
		LineTypes.put(12,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkOnce,LineTypeLighting.MaximumNeibor));
		LineTypes.put(194,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchRepeat,LineTypeLighting.MinimumNeibour));
		LineTypes.put(173,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchOnce,LineTypeLighting.MinimumNeibour));
		LineTypes.put(157,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkRepeat,LineTypeLighting.MinimumNeibour));
		LineTypes.put(104,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkOnce,LineTypeLighting.MinimumNeibour));
		LineTypes.put(193,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchRepeat,LineTypeLighting.Blinking));
		LineTypes.put(172,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.SwitchOnce,LineTypeLighting.Blinking));
		LineTypes.put(156,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkRepeat,LineTypeLighting.Blinking));
		LineTypes.put(17,new LineType(LineTypeCatagory.LightChanger,LineTypeTriggerType.WalkOnce,LineTypeLighting.Blinking));
				LineTypes.put(11,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.SwitchOnce));
		LineTypes.put(52,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.WalkOnce));
		LineTypes.put(197,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.GunOnce));
		LineTypes.put(51,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.SwitchOnce,LineTypeMiscellaneous.SecretExit));
		LineTypes.put(124,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SecretExit));
		LineTypes.put(198,new LineType(LineTypeCatagory.Exit,LineTypeTriggerType.GunOnce,LineTypeMiscellaneous.SecretExit));
				LineTypes.put(195,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchRepeat));
		LineTypes.put(174,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchOnce));
		LineTypes.put(97,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat));
		LineTypes.put(39,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce));
		LineTypes.put(126,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(125,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(269,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchRepeat,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(268,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchOnce,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(210,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchRepeat,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(209,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.SwitchOnce,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(208,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(207,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SilentTeleporter));
		LineTypes.put(244,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter));
		LineTypes.put(243,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter));
		LineTypes.put(263,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.ReverseTeleporter));
		LineTypes.put(262,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.ReverseTeleporter));
		LineTypes.put(267,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(266,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(265,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkRepeat,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.ReverseTeleporter,LineTypeMiscellaneous.MonsterActivated));
		LineTypes.put(264,new LineType(LineTypeCatagory.Teleport,LineTypeTriggerType.WalkOnce,LineTypeMiscellaneous.SilentTeleporter,LineTypeMiscellaneous.LineTeleporter,LineTypeMiscellaneous.ReverseTeleporter,LineTypeMiscellaneous.MonsterActivated));
				LineTypes.put(191,new LineType(LineTypeCatagory.Donut,LineTypeTriggerType.SwitchRepeat));
		LineTypes.put(9,new LineType(LineTypeCatagory.Donut,LineTypeTriggerType.SwitchOnce));
		LineTypes.put(155,new LineType(LineTypeCatagory.Donut,LineTypeTriggerType.WalkRepeat));
		LineTypes.put(146,new LineType(LineTypeCatagory.Donut,LineTypeTriggerType.WalkOnce));
	}
		class LineType {
				int lineTypeDelay = -1;
		LineTypeCatagory lineTypeCatagory = null;
		LineTypeTriggerType lineTypeTriggerType = null;
		LineTypeDoorLock lineTypeDoorLock = null;
		LineTypeDoorType lineTypeDoorType = null;
		LineTypeMotionSpeed lineTypeMotionSpeed = null;
		LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger = null;
		LineTypeDirection lineTypeDirection  = null;
		LineTypeMiscellaneous[] lineTypeMiscellaneous  = null;
		LineTypeDestination lineTypeDestination  = null;
		LineTypeModel lineTypeModel = null;
		LineTypeLighting lineTypeLighting  = null;

				public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, LineTypeDirection lineTypeDirection,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeDestination lineTypeDestination) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeDirection = lineTypeDirection;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeDestination = lineTypeDestination;

		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, LineTypeDirection lineTypeDirection,
				LineTypeMotionSpeed lineTypeMotionSpeed, int lineTypeDelay,
				LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger,
				LineTypeModel lineTypeModel) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeDirection = lineTypeDirection;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeDelay = lineTypeDelay;
			this.lineTypeSectorPropertyChanger = lineTypeSectorPropertyChanger;
			this.lineTypeModel = lineTypeModel;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, LineTypeDirection lineTypeDirection,
				LineTypeMotionSpeed lineTypeMotionSpeed, int lineTypeDelay) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeDirection = lineTypeDirection;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeDelay = lineTypeDelay;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeDirection lineTypeDirection,
				LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger,
				LineTypeDestination lineTypeDestination,
				LineTypeModel lineTypeModel) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeDirection = lineTypeDirection;
			this.lineTypeSectorPropertyChanger = lineTypeSectorPropertyChanger;
			this.lineTypeDestination = lineTypeDestination;
			this.lineTypeModel = lineTypeModel;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger,
				LineTypeModel lineTypeModel, int lineTypeDelay) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeSectorPropertyChanger = lineTypeSectorPropertyChanger;
			this.lineTypeModel = lineTypeModel;
			this.lineTypeDelay = lineTypeDelay;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger,
				LineTypeModel lineTypeModel,
				LineTypeDestination lineTypeDestination) {
			this.lineTypeCatagory=lineTypeCatagory;
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeSectorPropertyChanger=lineTypeSectorPropertyChanger;
			this.lineTypeModel=lineTypeModel;
			this.lineTypeDestination=lineTypeDestination;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				int lineTypeDelay, LineTypeDestination lineTypeDestination) {
			this.lineTypeCatagory=lineTypeCatagory;
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDelay=lineTypeDelay ;
			this.lineTypeDestination=lineTypeDestination;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, LineTypeDestination lineTypeDestination) {
			this.lineTypeCatagory=lineTypeCatagory;
			this.lineTypeTriggerType=lineTypeTriggerType; 
			this.lineTypeDestination=lineTypeDestination;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeDestination lineTypeDestination) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeDestination = lineTypeDestination;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeMotionSpeed lineTypeMotionSpeed) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
		}
		public LineType(LineTypeCatagory lineTypeCatagory, 
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed, 
				LineTypeMiscellaneous lineTypeMiscellaneous) {
			this.lineTypeCatagory = lineTypeCatagory; 
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed; 
			this.lineTypeMiscellaneous = new LineTypeMiscellaneous[]{lineTypeMiscellaneous};
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeLighting lineTypeLighting) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeLighting = lineTypeLighting;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMiscellaneous... lineTypeMiscellaneous) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeMiscellaneous = lineTypeMiscellaneous;
		}

		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeDirection lineTypeDirection,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeDestination lineTypeDestination,
				LineTypeSectorPropertyChanger lineTypeSectorPropertyChanger,
				LineTypeModel lineTypeModel) {
			this.lineTypeCatagory = lineTypeCatagory;
			this.lineTypeTriggerType = lineTypeTriggerType;
			this.lineTypeDirection = lineTypeDirection;
			this.lineTypeMotionSpeed = lineTypeMotionSpeed;
			this.lineTypeDestination = lineTypeDestination;
			this.lineTypeSectorPropertyChanger = lineTypeSectorPropertyChanger;
			this.lineTypeModel = lineTypeModel;
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeDirection lineTypeDirection,
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeDestination lineTypeDestination,
				LineTypeMiscellaneous lineTypeMiscellaneous) {
			this.lineTypeCatagory =lineTypeCatagory;
			this.lineTypeTriggerType =lineTypeTriggerType; 
			this.lineTypeDirection= lineTypeDirection;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDestination=lineTypeDestination;
			this.lineTypeMiscellaneous=new LineTypeMiscellaneous[]{lineTypeMiscellaneous};
		}
		public LineType(LineTypeCatagory lineTypeCatagory,
				LineTypeTriggerType lineTypeTriggerType, 
				LineTypeMotionSpeed lineTypeMotionSpeed,
				LineTypeDoorType lineTypeDoorType, 
				LineTypeDoorLock lineTypeDoorLock) {
			this.lineTypeCatagory=lineTypeCatagory;
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDoorType=lineTypeDoorType;
			this.lineTypeDoorLock=lineTypeDoorLock;
		}
		public LineType(LineTypeCatagory lineTypeCatagory, 
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed, 
				LineTypeDoorType lineTypeDoorType,
				int lineTypeDelay, 
				LineTypeDoorLock lineTypeDoorLock) {
			this.lineTypeCatagory=lineTypeCatagory; 
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed; 
			this.lineTypeDoorType=lineTypeDoorType;
			this.lineTypeDelay=lineTypeDelay; 
			this.lineTypeDoorLock=lineTypeDoorLock;
		}
		public LineType(LineTypeCatagory lineTypeCatagory, 
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed, 
				LineTypeDoorType lineTypeDoorType, 
				int lineTypeDelay) {
			this.lineTypeCatagory=lineTypeCatagory;
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDoorType=lineTypeDoorType; 
			this.lineTypeDelay=lineTypeDelay;
		}
		public LineType(LineTypeCatagory lineTypeCatagory, 
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed, 
				LineTypeDoorType lineTypeDoorType) {
			this.lineTypeCatagory=lineTypeCatagory; 
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDoorType=lineTypeDoorType;
		}
		public LineType(LineTypeCatagory lineTypeCatagory) {
			this.lineTypeCatagory = lineTypeCatagory;
		}
		public LineType(LineTypeCatagory lineTypeCatagory, 
				LineTypeTriggerType lineTypeTriggerType,
				LineTypeMotionSpeed lineTypeMotionSpeed, 
				LineTypeDoorType lineTypeDoorType,
				int lineTypeDelay, 
				LineTypeMiscellaneous lineTypeMiscellaneous) {
			this.lineTypeCatagory=lineTypeCatagory; 
			this.lineTypeTriggerType=lineTypeTriggerType;
			this.lineTypeMotionSpeed=lineTypeMotionSpeed;
			this.lineTypeDoorType=lineTypeDoorType;
			this.lineTypeDelay=lineTypeDelay;
			this.lineTypeMiscellaneous = new LineTypeMiscellaneous[]{lineTypeMiscellaneous};
		}


	}
			public enum LineTypeCatagory {
		NoAction,Door,FloorMover,CeilingMover,Platform,Crusher,Stair,Elevator,LightChanger,Exit,Teleport,Donut
	}
	public enum LineTypeTriggerType {
		PushOnce,PushRepeat,SwitchOnce,SwitchRepeat,WalkOnce,WalkRepeat,GunOnce,GunRepeat
	}
	public enum LineTypeDoorLock {
		Yellow,Red,Blue
	}
	public enum LineTypeDoorType {
		OpenWaitClose,CloseWaitOpen,OpenStayOpen,CloseStayClosed
	}
	public enum LineTypeMotionSpeed {
		Slow,Normal,Fast,Turbo,Instant
	}
	public enum LineTypeSectorPropertyChanger {
		CopyTexture,CopyTextureResetType,CopyTextureAndType
	}
	public enum LineTypeDirection {
		Down,Up,None
	}
	public enum LineTypeMiscellaneous {
		SecretExit,MonsterActivated,LineTeleporter,ReverseTeleporter,SilentTeleporter,EnableCrushing
	}
	public enum LineTypeDestination {
		LowestNeighborFloor, LowestNeighborCeiling,
		HighestNeighborFloor, HighestNeighborCeiling,
		NextNeighborFloor, NextNeighborCeiling,
		Above8HighestNeighborFloor, Below8LowestNeighborCeiling,
		AboveFloor8, Unit8, Unit16, Unit32, Unit512, ShortestLowerTexture,ShortestUpperTexture,
		NextLowestNeighborFloor,NextLowestNeighborCeiling,NextHighestNeighborFloor,
		CurrentFloor,Floor,Ceiling,NextAdjacentFloor, Perpetual, Stop
	}
	public enum LineTypeModel {
		TriggerSector, AdjacentNumerical
	}
	public enum LineTypeLighting {
		Unit35, Unit255, MaximumNeibor,MinimumNeibour,Blinking
	}
}

