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
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class UDFTest {

  @Test
  public void adderUDFTest() {
    AdderUDF add = new AdderUDF();
    ArrayList<Object> initArgs = new ArrayList<Object>(1);
    initArgs.add(1L);
    add.init(initArgs);
    ArrayList<Object> inputs1 = new ArrayList<Object>();
    inputs1.add(1L);
    long result = add.exec(inputs1);
    long expected = 2L;
    assertEquals(expected, result);
    ArrayList<Object> inputs2 = new ArrayList<Object>();
    inputs2.add(9000L);
    result = add.exec(inputs2);
    expected = 9001L;
    assertEquals(expected, result);
  }

  @Test
  public void concatUDFTest() throws IOException {
    ConcatUDF concat = new ConcatUDF();
    ArrayList<Object> input = new ArrayList<Object>();
    input.add("hello");
    input.add("world");
    String result = concat.exec(input);
    String expected = "helloworld";
    assertEquals(expected, result);
  }

  @Test
  public void nullInputTest() throws IOException {
    AdderUDF add = new AdderUDF();
    ArrayList<Object> initArgs = new ArrayList<Object>(1);
    initArgs.add(1L);
    add.init(initArgs);
    ConcatUDF concat = new ConcatUDF();
    ArrayList<Object> nullList = null;
    Object result1 = add.exec(nullList);
    Object result2 = concat.exec(nullList);
    assertNull(result1);
    assertNull(result2);
  }

  @Test
  public void nullEntryTest() throws IOException {
    ConcatUDF concat = new ConcatUDF();
    ArrayList<Object> inputList = new ArrayList<Object>();
    inputList.add(null);
        Object result1 = concat.exec(inputList);
    assertNull(result1);
  }

  @Test
  public void properListWithNullEntryTest() throws IOException {
    ConcatUDF concat = new ConcatUDF();
    ArrayList<Object> inputList = new ArrayList<Object>();
    inputList.add("hi");
    inputList.add("world");
    String result = concat.exec(inputList);
    String expected = "hiworld";
    assertEquals(expected, result);

    ArrayList<Object> inputList1 = new ArrayList<Object>();
    inputList1.add("hi");
    inputList1.add("world");
    inputList1.add(null);
    inputList1.add(null);
    assertEquals(inputList1.size(), 4);
    assertNull(inputList1.get(2));
    Object result2 = concat.exec(inputList1);
    assertEquals(result2, "hiworld");
  }
}
