# CCVerticalDragToSlideLayout
仿原淘宝详情页拖拽效果，支持多页

该项目由[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)项目修改而来，只是对[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)项目的[@VerticalSlide.java](https://github.com/jeasonlzy/VerticalSlideView/blob/master/verticalslide/src/main/java/com/lzy/widget/VerticalSlide.java)进行了修改，使得其支持多页拖拽功能，具体使用方法与[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)的使用方式完全一致。

使用方式
---
(1)maven
 
   - Step 1. Add the JitPack repository to your build file
   	
	<repositories>
	  	<repository>
		  	<id>jitpack.io</id>
 		 	<url>https://jitpack.io</url>
 	 	</repository>
  	</repositories> 

	  
   - Step 2. Add the dependency
   	
	<dependency>
		<groupId>com.github.CodingCodersCode</groupId>
		<artifactId>CCVerticalDragToSlideLayout</artifactId>
		<version>V1.0</version>
	</dependency> 

  
(2)gradle
  
   - project root `build.gradle`:
   	
	allprojects {
              repositories {
                  ......
                  maven { url 'https://jitpack.io' }
                  maven { url 'https://maven.google.com' }
              }
          }
          
   - app `build.gradle`:
   
   	dependencies {
	        compile 'com.github.CodingCodersCode:CCVerticalDragToSlideLayout:V1.0'
	      }
 
效果图
---
![image](https://github.com/CodingCodersCode/CCVerticalDragToSlideLayout/blob/master/screenshot/screenshot.gif)

感谢
---
  
本项目特别感谢[@jeasonlzy](https://github.com/jeasonlzy)，因本项目由[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)修改而来，只是对[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)项目的[@VerticalSlide.java](https://github.com/jeasonlzy/VerticalSlideView/blob/master/verticalslide/src/main/java/com/lzy/widget/VerticalSlide.java)进行了修改，其余代码均拷贝自[@jeasonlzy/VerticalSlideView](https://github.com/jeasonlzy/VerticalSlideView)，
大佬的贡献不可磨灭，感谢大佬，望大佬勿怪。


说明
---
本项目仍存在些许不足之处有待完善，希望有解决了相关bug或好的意见和建议的大佬们能够指点，帮助小弟完善项目，以帮助更多的人。

License
---
Copyright 2018 CodingCodersCode

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
