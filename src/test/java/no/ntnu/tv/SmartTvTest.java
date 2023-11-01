package no.ntnu.tv;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
public class SmartTvTest {

  //Test the SmartTv constructor
  @Test
  void testCreationOfSmartTvWithInvalidInputs(){
    assertThrows(IllegalArgumentException.class, () -> new SmartTv(0));
    assertThrows(IllegalArgumentException.class, () -> new SmartTv(-1));
  }

  //Test the turn on and off functionality
  @Test
  void testIfTvIsOn(){
    SmartTv smartTv = new SmartTv(10);
    assertEquals(smartTv.isTvOn(), false);
    smartTv.turnOn();
    assertEquals(smartTv.isTvOn(), true);
    smartTv.turnOff();
    assertEquals(smartTv.isTvOn(), false);
  }

  //Test getting the channel count
  @Test
  void testGetChannelCount(){
    SmartTv smartTv = new SmartTv(10);
    smartTv.turnOn();
    assertEquals(smartTv.getChannelCount(), 10);
  }
  @Test
  void testGetChannelCountWhenTvIsTurnedOff(){
    SmartTv smartTv = new SmartTv(10);
    assertThrows(IllegalStateException.class, () -> smartTv.getChannelCount());
  }

  //Test setChannel
  @Test
  void testSetChannelWithTvOff(){
    SmartTv smartTv = new SmartTv(10);
    assertThrows(IllegalStateException.class, () -> smartTv.setChannel(5));
  }
  @Test
  void testSetChannelWithInvalidChannel(){
    SmartTv smartTv = new SmartTv(10);
    smartTv.turnOn();
    assertThrows(IllegalArgumentException.class, () -> smartTv.setChannel(100));
    assertThrows(IllegalArgumentException.class, () -> smartTv.setChannel(0));
    assertThrows(IllegalArgumentException.class, () -> smartTv.setChannel(-1));
  }
  @Test
  void testSetChannelWithValidInputs(){
    SmartTv smartTv = new SmartTv(10);
    smartTv.turnOn();
    smartTv.setChannel(5);
    assertEquals(smartTv.getCurrentChannel(), 5);
  }

  //Test getCurrentChannel
  @Test
  void testGetCurrentChannelWithTvOff(){
    SmartTv smartTv = new SmartTv(10);
    assertThrows(IllegalStateException.class, () -> smartTv.getCurrentChannel());
  }
  @Test
  void testGetCurrentChannelWithTvOnAndDefaultValue(){
    SmartTv smartTv = new SmartTv(10);
    smartTv.turnOn();
    assertEquals(smartTv.getCurrentChannel(), 1);
  }


}
