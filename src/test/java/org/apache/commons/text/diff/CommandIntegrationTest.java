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


package org.apache.commons.text.diff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandIntegrationTest {

  //Create Visitor through using CommandVisitor
  private static class myVisitor<T> implements CommandVisitor<T> {
    private final List<String> visits = new ArrayList<>();

    public List<String> getVisits() {
      return visits;
    }
  
    @Override
    public void visitInsertCommand(T object) {
      visits.add("Insert: " + object);
    }
    @Override
    public void visitDeleteCommand(T object) {
      visits.add("Delete: " + object);
    }

    @Override
    public void visitKeepCommand(T object) {
      visits.add("Keep: " + object);
    }
  }

  private myVisitor<String> visitor;

  @BeforeEach
  void InitializeVisitor(){
    visitor = new myVisitor<>();
  }

  //Test 1 - Test all commands together with edit command
  @Test
  void testAllCommands() {
    List<EditCommand<String>> commands = Arrays.asList(
      new DeleteCommand<>("A"),
      new KeepCommand<>("B"),
      new InsertCommand<>("C")
    );

    for (EditCommand<String> commandList : commands) {
      commandList.accept(visitor);
    }

    List<String> expectedVisits = Arrays.asList(
      "Delete: A",
      "Keep: B",
      "Insert: C"
      );

    assertEquals(expectedVisits, visitor.getVisits());
  }

  //Test 2,3,4 - Test individual commands
  @Test
  void testDeleteCommand() {
    DeleteCommand<String> delete = new DeleteCommand<>("D");
    delete.accept(visitor);
    assertEquals(Arrays.asList("Delete: D"), visitor.getVisits());
  }

  @Test
  void testInsertCommand() {
    InsertCommand<String> insert = new InsertCommand<>("E");
    insert.accept(visitor);
    assertEquals(Arrays.asList("Insert: E"), visitor.getVisits());
  }

  @Test
  void testKeepCommand() {
    KeepCommand<String> keep = new KeepCommand<>("F");
    keep.accept(visitor);
    assertEquals(Arrays.asList("Keep: F"), visitor.getVisits());
  }

}
