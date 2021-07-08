# MineStomDataSaving

This is a library that allows you to easily save and load worlds

Todo
- Player data saving

Done
- World data saving

## Setup

### Maven

Repository
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

Dependency
```
<dependency>
	<groupId>com.github.justdoom</groupId>
	<artifactId>minestomdatasaving</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```

### Gradle

Repository
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Dependency
```
dependencies {
    implementation 'com.github.justdoom:minestomdatasaving:-SNAPSHOT'
}
```

## API usage

Loading a world
```java
InstanceContainer instanceContainer = MineStomDataSaving.loadWorld("worlds/lobby");
```

Saving a world
```java
MineStomDataSaving.saveWorld("worlds/lobby", instanceContainer);
```
