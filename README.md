# DroidCalendarView
Customizable android calendar view

<b>Supports from Android SDK version 14 and above</b>.

<b><h1>About</h1></b>
An easy to implement and use calendar view with customizations and locales.

<p align="center">
  <img src="https://user-images.githubusercontent.com/12429051/31595818-dcb8d372-b25b-11e7-86b9-990073c906dd.png" height="250" width="250"/>
  <img src="https://user-images.githubusercontent.com/12429051/31595820-dd1a4828-b25b-11e7-98c3-4e9f5d8ba5a4.png" height="250" width="250"/>
  <img src="https://user-images.githubusercontent.com/12429051/31595989-0596dd7e-b25d-11e7-9a7e-a9fa90065dd3.png" height="250" width="250"/>
  <img src="https://user-images.githubusercontent.com/12429051/31595821-dd4b8884-b25b-11e7-911b-004cb8b29cce.png" height="250" width="250"/>
  <img src="https://user-images.githubusercontent.com/12429051/31595819-dce9673a-b25b-11e7-95e5-3759184bec82.png" height="250" width="250"/>
  <img src="https://user-images.githubusercontent.com/12429051/31595816-dc6f8280-b25b-11e7-847b-5588b3129ba8.png" height="250" width="250"/>
</p>

<b><h1>Usage</h1></b>
<b>Gradle dependency:</b>

Add the following to your project level build.gradle:

```java
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Add this to your app build.gradle:

```java
dependencies {
    compile 'com.github.vikramezhil:DroidCalendarView:v1.0.2’
}
```

<b>Maven:</b>

Add the following to the <repositories> section of your pom.xml:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```

Add the following to the <dependencies> section of your pom.xml:

```xml
<dependency>
    <groupId>com.github.vikramezhil</groupId>
    <artifactId>DroidCalendarView</artifactId>
    <version>v1.0.2</version>
</dependency>
```

<b><h1>Documentation</h1></b>

For a detailed documentation 📔, please have a look at the [Wiki](https://github.com/vikramezhil/DroidCalendarView/wiki).

In your layout file add Droid Calendar View,

```xml
<com.vikramezhil.droidcalendarview.DCView
    android:id="@+id/dcView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

In your class file, initialize Droid Calendar View using the ID specified in your layout file

```java
DCView dcView = findViewById(R.id.dcView);
```

Set droid calendar data

```java
// Setting the droid calendar data with preferred start and end range
dcView.setDCData("January 2017", "December 2017", "MMMM YYYY", "MMMM YYYY", "d", Locale.US);
```

Set and implement the droid calendar listener methods

```java
// Setting the droid calendar listener
dcView.setOnDCListener(this);

// Implementing the droid calendar listener methods
@Override
void onDCScreenData(int calendarPosition, List<String> calendarDatesWithPresent, List<String> calendarDatesWithPastPresentFuture)
{
    // Triggered whenever the on screen data changes in droid calendar
}
@Override
void onDCDateClicked(String date)
{
   // Triggered when a date is clicked in droid calendar
}
```

<b><h1>License</h1></b>

Copyright 2017 Vikram Ezhil

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
