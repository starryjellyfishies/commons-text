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

public class AnotherLCSTest {
  
  private final LongestCommonSubsequence lcs = new LongestCommonSubsequence();

  //Test 1 - Null inputs should throw exception
  @Test
  public void testNullInput(){
    assertThrows(IllegalArgumentException.class, () -> {
      lcs.apply(null, "test");
    });
    assertThrows(IllegalArgumentException.class, () -> {
      lcs.apply("test", null);
    });
  }

  //Test 2 - Empty inputs
  @Test
  public void testEmptyInput(){
    assertEquals(0, lcs.apply("",""));
    assertEquals("", lcs.longestCommonSubsequence("", ""));

    assertEquals(0, lcs.apply("test",""));
    assertEquals("", lcs.longestCommonSubsequence("test", ""));

    assertEquals(0, lcs.apply("","test"));
    assertEquals("", lcs.longestCommonSubsequence("", "test"));
  }

  //Test 3 - Typical case with identical strings
  @Test
  public void testIdentStrings(){
    assertEquals(3, lcs.apply("abc","abc"));
    assertEquals("abc", lcs.longestCommonSubsequence("abc", "abc"));

    assertEquals(7, lcs.apply("testing","testing"));
    assertEquals("testing", lcs.longestCommonSubsequence("testing", "testing"));

  }

  //Test 4 - Typical case with competely different strings
  @Test
  public void testDiffStrings(){
    assertEquals(0, lcs.apply("abc","def"));
    assertEquals("", lcs.longestCommonSubsequence("abc", "def"));

    assertEquals(0, lcs.apply("hello","pizza"));
    assertEquals("", lcs.longestCommonSubsequence("hello", "pizza"));

  }

  //Test 5 - Typical case with some similarities and some differences
  @Test
  public void testTypicalCases(){
    assertEquals(4, lcs.apply("test","testing"));
    assertEquals("test", lcs.longestCommonSubsequence("test", "testing"));

    assertEquals(4, lcs.apply("testing","test"));
    assertEquals("test", lcs.longestCommonSubsequence("testing", "test"));

    assertEquals(3, lcs.apply("abcdef","defghi"));
    assertEquals("def", lcs.longestCommonSubsequence("abcdef", "defghi"));

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

    String word3 = "def";

    StringBuilder sb2 = new StringBuilder();
    for (int i = 0; i < 9999; i++) {
      sb2.append(word3).append("def");
    }
    word3 = sb2.toString();

    assertEquals(59994, lcs.apply(word1,word2));
    //value is too large to assertEquals with longestCommonSubsequence

    assertEquals(0, lcs.apply(word1, word3));
    assertEquals("", lcs.longestCommonSubsequence(word1, word3));

  }

}
