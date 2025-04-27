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

public class AnotherJaccardSimilarityTest {
  
  private final JaccardSimilarity jacSim = new JaccardSimilarity();

  //Test 1 - Null inputs should throw exceptions
  @Test
  public void testNullInput(){
    assertThrows(IllegalArgumentException.class, () -> {
      jacSim.apply(null, "test");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      jacSim.apply("test", null);
    });
  }
  
  //Test 2 - Empty strings should return 0
  @Test
  public void testEmptyStrings(){
    assertEquals(1.0, jacSim.apply("", ""));
  }

  //Test 3 - Test regular identical values
  @Test
  public void identValues(){
    assertEquals(1.0, jacSim.apply("abcd", "abcd"));
    assertEquals(1.0, jacSim.apply("testing", "testing"));

  }

  //Test 4 - test regular different values
  @Test
  public void testRegularValues(){
    assertEquals(0.0, jacSim.apply("testing", "qwry"));
    assertEquals(0.0, jacSim.apply("abcd", "efgh"));
    assertEquals(0.0, jacSim.apply("hamburger", "pizza"), 0.1);
  }

  //Test 5 - test regular similar values
  @Test
  public void testSimilarValues(){
    assertEquals(1.0, jacSim.apply("abc", "acb"));
    assertEquals(0.5, jacSim.apply("test", "testing"));
  }
}
