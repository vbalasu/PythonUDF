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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import com.trifacta.trifactaudfs.utils.UDFUtil;

public class UDFExecutionTest {


  @Test
  public void adderUDFTest() throws IllegalAccessException, IllegalArgumentException,
  InvocationTargetException {
    AdderUDF add = new AdderUDF();
    ArrayList<Object> initArgs = new ArrayList<Object>(1);
    initArgs.add(1L);
    add.init(initArgs);
    ArrayList<Object> input = new ArrayList<Object>();
    input.add(1L);
    long result = add.exec(input);
    long expected = 2L;
    assertEquals(expected, result);
    Method exec = UDFUtil.getExecMethod(AdderUDF.class);
    long result2 = (Long) exec.invoke(add, input);
    assertEquals(expected, result2);
    assertEquals(result, result2);
  }
}
