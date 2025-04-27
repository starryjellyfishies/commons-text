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

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class AnotherCosineSimilarityTest {
  
  //Test 1: Null values - Should throw exception
  @Test
  public void testNullVectors() {
    assertThrows(IllegalArgumentException.class, () -> {
      new CosineSimilarity().cosineSimilarity(null, new HashMap<>());
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new CosineSimilarity().cosineSimilarity(new HashMap<>(), null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new CosineSimilarity().cosineSimilarity(null, null);
    });
  }

  //Test 2: Empty vectors - Should return 0.0
  @Test
  public void testEmptyVectors() {
    double similar = new CosineSimilarity().cosineSimilarity(new HashMap<>(), new HashMap<>());
    assertEquals(0.0, similar);
  }

  //Test 3: Identical Vectors - Should return ~1.0
  @Test
  public void testIdenticalVec(){
    final Map<CharSequence, Integer> vector = new HashMap<>();
    vector.put("a", 1);
    vector.put("b", 2);
    double similar = new CosineSimilarity().cosineSimilarity(vector, vector);
    assertEquals(1.0, similar, 0.00001);
  }

  //Test 4: Different Vectors - Should return 0.0
  @Test
  public void testDiffVec(){
    Map<CharSequence, Integer> vector1 = new HashMap<>();
    Map<CharSequence, Integer> vector2 = new HashMap<>();
    vector1.put("a", 1);
    vector1.put("b", 2);
    vector2.put("c", 1);
    vector2.put("d", 2);
    double similar = new CosineSimilarity().cosineSimilarity(vector1, vector2);
    assertEquals(0.0, similar);
  }

  //SPECIAL CASE TEST
  //Test 5: Testing case sensitivity- result should be 0.0
  @Test
  void testCaseSens(){
    Map<CharSequence, Integer> vector1 = new HashMap<>();
    Map<CharSequence, Integer> vector2 = new HashMap<>();
    vector1.put("a", 1);
    vector1.put("b", 2);
    vector2.put("A", 1);
    vector2.put("B", 2);
    double similar = new CosineSimilarity().cosineSimilarity(vector1, vector2);
    assertEquals(0.0, similar);
  }

}
