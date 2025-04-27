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

public class AnotherLevenshteinDetailDistTest {

  private final LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();

  //Test 1: Null values - Should throw exceptions
  @Test
  public void testNullValues() {
  assertThrows(IllegalArgumentException.class, () -> {
    new LevenshteinDetailedDistance().apply(null, "test");
    });
  assertThrows(IllegalArgumentException.class, () -> {
    new LevenshteinDetailedDistance().apply("test", null);
    });
  }

  //Test 2: Empty Strings - Should return values
  @Test
  public void testEmptyStrings(){
    LevenshteinResults result = distance.apply("", "");
    assertEquals(0, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

    result = distance.apply("", "test");
    assertEquals(4, result.getDistance());
    assertEquals(4, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

    result = distance.apply("test", "");
    assertEquals(4, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(4, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

  }

  //Test 3: Different Strings/ Regular Values
  @Test
  public void testRegularValues(){
    LevenshteinResults result = distance.apply("a", "b");
    assertEquals(1, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(1, result.getSubstituteCount());

    result = distance.apply("qwerty", "erty");
    assertEquals(2, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(2, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

    result = distance.apply("erty", "qwerty");
    assertEquals(2, result.getDistance());
    assertEquals(2, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

    result = distance.apply("aaa", "bbb");
    assertEquals(3, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(3, result.getSubstituteCount());

    result = distance.apply("abc", "cde");
    assertEquals(3, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(3, result.getSubstituteCount());

    result = distance.apply("testing", "test");
    assertEquals(3, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(3, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());
  }

  //Test 4: Long Values
  @Test
  public void testLongValues(){
    LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();
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


    LevenshteinResults result = distance.apply(word1, word2);
    assertEquals(0, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(0, result.getSubstituteCount());

    result = distance.apply(word1, word3);
    assertEquals(5994, result.getDistance());
    assertEquals(0, result.getInsertCount());
    assertEquals(0, result.getDeleteCount());
    assertEquals(5994, result.getSubstituteCount());
  }

}
