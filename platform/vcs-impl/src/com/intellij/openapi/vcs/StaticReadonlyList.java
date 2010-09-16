/*
 * Copyright 2000-2010 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.openapi.vcs;

import com.intellij.util.Consumer;
import com.intellij.util.containers.ReadonlyList;

/**
 * @author irengrig
 */
public class StaticReadonlyList<T> implements ReadonlyList<T>, Consumer<T> {
  private final BigArray<T> myList;

  public StaticReadonlyList(final int size2power) {
    myList = new BigArray<T>(size2power);
  }

  @Override
  public T get(int idx) {
    return myList.get(idx);
  }

  @Override
  public int getSize() {
    return myList.getSize();
  }

  @Override
  public void consume(T t) {
    myList.add(t);
  }
}
