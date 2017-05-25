package org.apache.solr.search.xjoin.simple;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.InputStream;

import net.minidev.json.JSONArray;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonDocumentFactory {
   
  public PathDocument read(InputStream in) {
    final DocumentContext json = JsonPath.parse(in);
    
    return new PathDocument() {
      
      @Override
      public Object getPathValue(String path) {
        Object value = json.read(path);
        if (value instanceof JSONArray) {
          JSONArray array = (JSONArray)value;
          return array.size() > 0 ? array.get(0) : null;
        }
        return value;
      }

      @Override
      public Object[] getPathValues(String path) {
        return json.read(path, Object[].class);
      }
      
    };
  }

}
