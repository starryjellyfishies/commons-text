/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements.  See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License.  You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;


public class AnotherLCSDistanceTest {

  private final LongestCommonSubsequenceDistance lcsDist = new LongestCommonSubsequenceDistance();

  //Test 1 - Test null values to throw exception
  @Test
  public void testNullInput(){
    assertThrows(IllegalArgumentException.class, () -> {
      lcsDist.apply(null, "test");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      lcsDist.apply("test", null);
    });
  }

  //Test 2 - Empty inputs
  @Test
  public void testEmptyInput(){
    assertEquals(0, lcsDist.apply("",""));

    assertEquals(4, lcsDist.apply("test",""));

    assertEquals(4, lcsDist.apply("","test"));
  }

  //Test 3 - Typical case with identical strings
  @Test
  public void testIdentStrings(){
    assertEquals(0, lcsDist.apply("abc","abc"));

    assertEquals(0, lcsDist.apply("testing","testing"));

  }

  //Test 4 - Typical case with competely different strings
  @Test
  public void testDiffStrings(){
    assertEquals(6, lcsDist.apply("abc","def"));

    assertEquals(10, lcsDist.apply("hello","pizza"));

  }

  //Test 5 - Typical case with some similarities and some differences
  @Test
  public void testTypicalCases(){
    assertEquals(3, lcsDist.apply("test","testing"));

    assertEquals(3, lcsDist.apply("testing","test"));

    assertEquals(6, lcsDist.apply("abcdef","defghi"));

  }

  //Test 6 - Extremely long strings
  @Test
  public void testLongStrings(){
    String word1 = "abc";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 9999; i++) {
        sb.append(word1).append("abc");
    }
    word1 = sb.toString();
    String word2 = word1;

    assertEquals(0, lcsDist.apply(word1,word2));

    String word3 = "def";
    StringBuilder sb2 = new StringBuilder();
    for (int i = 0; i < 9999; i++) {
      sb2.append(word3).append("def");
    }
    word3 = sb2.toString();

    assertEquals(119988, lcsDist.apply(word1,word3));

  }

}