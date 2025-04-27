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
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AnotherLevenshteinDistanceTest {

  //Test 1: Null values - Should throw exceptions
  @Test
  public void testNullValues() {
  assertThrows(IllegalArgumentException.class, () -> {
    new LevenshteinDistance().apply(null, "test");
    });
  assertThrows(IllegalArgumentException.class, () -> {
    new LevenshteinDistance().apply("test", null);
    });
  }

  //Test 2: Empty Strings - Should return values
  @Test
  public void testEmptyStrings(){
    LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
    assertEquals(0, distance.apply("", "").intValue());
    assertEquals(1, distance.apply("", "a").intValue());
    assertEquals(1, distance.apply("a", "").intValue());
  }

  //Test 3: Different Strings/ Regular Values
  @Test
  public void testRegularValues(){
    LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
    assertEquals(1, distance.apply("a", "b").intValue());
    assertEquals(2, distance.apply("qwerty", "erty").intValue());
    assertEquals(2, distance.apply("erty", "qwerty").intValue());
    assertEquals(3, distance.apply("aaa", "bbb").intValue());
    assertEquals(3, distance.apply("abc", "cde").intValue());
    assertEquals(3, distance.apply("testing", "test").intValue());
  }

  //Test 4: Long Values
  @Test
  public void testLongValues(){
    LevenshteinDistance distance = LevenshteinDistance.getDefaultInstance();
    String word1 = "abc";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 999; i++) {
        sb.append(word1).append("abc");
    }
    word1 = sb.toString();
    String word2 = word1;

    String word3 = "def";
    StringBuilder sb2 = new StringBuilder();
    for (int i = 0; i < 999; i++) {
      sb2.append(word3).append("def");
    }
    word3 = sb2.toString();

    assertEquals(0, distance.apply(word1, word2).intValue());
    assertTrue(distance.apply(word1, word3) != 0.0);
  }

}
