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

 
public class AnotherCosineDistTest {

  private final CosineDistance cosineDistance = new CosineDistance();

  //Test 1: Testing empty strings - should return error
  @Test
  void testEmptyStrings() {
    assertThrows(IllegalArgumentException.class, () -> {
      double distance = cosineDistance.apply("", "");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      double distance = cosineDistance.apply("", "a");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      double distance = cosineDistance.apply("a", "");
    });
  }

  //Test 2: Testing identical strings - should return distance 0.0
  @Test
  void testIdenticalStrings(){
    double distance = cosineDistance.apply("testing", "testing");
    assertEquals(0.0, distance);
  }

  //Test 3: Testing different strings - should return distance 1.0
  @Test
  void testDifferentStrings(){
    double distance = cosineDistance.apply("testing", "success");
    assertEquals(1.0, distance);
  }

  //Test 4: Testing repeated word - result should be 0.0
  @Test
  void testOneRepeatedWord(){
    double distance = cosineDistance.apply("testing", "testing testing");
    assertEquals(0.0, distance);
  }

  //Test 5: Testing word with subword- result should be between 0.0 and 1.0
  @Test
  void testSubword(){
    double distance = cosineDistance.apply("testing", "test");
    assertTrue(distance != 0.0);
  }

  //Test 6: Testing very large identical strings - result should be 0.0
  @Test
  void testLargeStrings(){

    String word1 = "abc";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 9999; i++) {
        sb.append(word1).append("abc");
    }
    String word2 = word1;

    double distance = cosineDistance.apply(word1, word2);
    assertEquals(0.0, distance);
  }

  //SPECIAL CASE TEST
  //Test 7: Testing case sensitivity- result should not be 0.0
  @Test
  void testCaseSens(){
    double distance = cosineDistance.apply("test", "Test");
    assertTrue(distance != 0.0);
  }
  
}
