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


public class EditDistanceFromTest {

    private final EditDistance<Integer> levenshteinDist = LevenshteinDistance.getDefaultInstance();
  
    //Testing using robust boundary value testing and special-case testing

    //First Test: Identical strings
    @Test
      public void testIdenticalStrings() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "text");
          assertEquals(0, distanceFrom.apply("text"));
          //Should have 0 distance since they are identical strings (no changes are made)
      }

    //Second Test: Empty Left string
    @Test
      public void testEmptyLeftString() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "");
          assertEquals(4, distanceFrom.apply("text"));
          //Should have a distance of 4 since 4 insertions are made
      }

    //Third Test: Empty Right String
    @Test
      public void testEmptyRightString() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "text");
          assertEquals(4, distanceFrom.apply("")); // 4 deletions
          //Should have a distance of 4 since 4 deletions are made
      }

    //Fourth Test: Both Strings are empty 
    @Test
      public void testEmptyStrings() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "");
          assertEquals(0, distanceFrom.apply(""));
          //Should have a distance of 0 since they are empty (and identical) strings 
      }

    //Fifth Test: One character difference
    @Test
      public void testOneCharaDiff() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "test");
          assertEquals(1, distanceFrom.apply("text"));
          //Should have a distance of 1 since they are one character off
      }

    //Sixth Test: Uses special characters
    @Test
      public void testApply_withSpecialCharacters() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "Sp3c1@l!");
          assertEquals(4, distanceFrom.apply("Special"));
          //Should have a distance of 4 since there are 4 special characters
      }

    //Seventh Test: Test maximum value (extremely long strings)
    @Test
      public void testApply_veryLongStrings() {
          StringBuilder longStr = new StringBuilder();
          for (int i = 0; i < 1000; i++) {
              longStr.append("a");
          }
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, longStr.toString());

          //adjusting single character at end
          StringBuilder oneCharaChange = new StringBuilder(longStr);
          oneCharaChange.setCharAt(999, 'b');

          assertEquals(1, distanceFrom.apply(oneCharaChange.toString()));
      }

    //Eighth Test: Tests Typical Case
    @Test
      public void testTypicalCase() {
          EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "typical");

          int distance = distanceFrom.apply("typing");

          assertEquals(3, distance);
      }

    
    //The following test cases are special cases (null) in which exceptions should be thrown:

    //Nineth Test: null editDistance
    @Test
      public void testConstructor_nullEditDistance_throwsException() {
          assertThrows(IllegalArgumentException.class, () -> {
              new EditDistanceFrom<Integer>(null, "test");
          });
      }

    //Tenth Test: null Left String
    @Test
    public void testNullLeftStringException() {
      EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, null);

      assertThrows(IllegalArgumentException.class, () -> distanceFrom.apply("right"));
    }

    //Eleventh Test: null Right String
    @Test
      public void testNullRightStringException() {
        EditDistanceFrom<Integer> distanceFrom = new EditDistanceFrom<>(levenshteinDist, "left");

        assertThrows(IllegalArgumentException.class, () -> distanceFrom.apply(null));
      }

}
