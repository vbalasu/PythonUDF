/**
 * Trifacta Inc. Confidential
 *
 * Copyright 2015 Trifacta Inc.
 * All Rights Reserved.
 *
 * Any use of this material is subject to the Trifacta Inc., Source License located
 * in the file 'SOURCE_LICENSE.txt' which is part of this package.  All rights to
 * this material and any derivative works thereof are reserved by Trifacta Inc.
 */

package com.trifacta.trifactaudfs;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author or
 * Example UDF. Adds a constant amount to an Integer column.
 *
 */
public class AdderUDF implements TrifactaUDF<Long> {

  private Long _addAmount;

  @Override
  public void init(List<Object> initArgs) {
    if (initArgs.size() != 1) {
      System.out.println("AdderUDF takes in exactly one init argument");
    }
    Long addAmount = (Long) initArgs.get(0);
    _addAmount = addAmount;
  }

  @Override
  public Long exec(List<Object> input) {
    if (input == null) {
      return null;
    }
    if (input.size() != 1) {
      return null;
    }
    return (Long) input.get(0) + _addAmount;
  }

  @SuppressWarnings("rawtypes")
  public Class[] inputSchema() {
    return new Class[]{Long.class};
  }

  @Override
  public void finish() throws IOException {
  }

}
