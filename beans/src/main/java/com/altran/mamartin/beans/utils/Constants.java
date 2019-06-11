package com.altran.mamartin.beans.utils;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import java.util.concurrent.TimeUnit;

/**
 * The {@code Constants} class represents...
 */
public class Constants {

  public static final StdDateFormat SDF = new StdDateFormat();
  public static final int TIMEOUT = (int) TimeUnit.SECONDS.toMillis(10);
}
