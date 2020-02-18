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
 * Example UDF that concatenates two columns
 */
public class ConcatUDF implements TrifactaUDF<String> {

  @Override
  public String exec(List<Object> inputs) throws IOException {
    if (inputs == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < inputSchema().length; i += 1) {
      if (inputs.get(i) == null) {
        return null;
      }
      sb.append(inputs.get(i));
    }
    return sb.toString();
  }

  @SuppressWarnings("rawtypes")
  public Class[] inputSchema() {
    return new Class[]{String.class, String.class};
  }

  @Override
  public void finish() throws IOException {
  }

  @Override
  public void init(List<Object> initArgs) {
  }
}
