package no.ntnu;

import no.ntnu.tv.SmartTv;
import no.ntnu.tv.TvServer;

public class SmartTVApp {

  public static void main(String[] args) {
    SmartTv tv = new SmartTv(13);
    new TvServer(tv);
  }
}
