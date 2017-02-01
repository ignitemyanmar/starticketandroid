package com.ignite.mm.ticketing.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/20/17.
 */

public class ListUtils {

  public static List<String> search(final List<String> list, String keyword) {
    final List result = new ArrayList(list.size());

    for (String s : list) {

      if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(s, keyword)) {
        result.add(s);
      }
    }
    return result;
  }
}
