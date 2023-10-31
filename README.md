# MakeSomething

A simple experimental Minecraft / spigot plugin. This started as experimental Javascript code, generating text `/execute at @p fill ~ ~ ~` type commands to pipe into a server console, but was tedious and fragile. We decided it would be more fun to just make an actual plugin.

No idea which servers this will work with, we run `paper` at the Maker Space, and this drops right in. 

## Usage

Provides a `make` command, to make geometric shapes.

Make a sphere with a radius of 15, centered on the player's feet using default material set:
```
/make sphere 15 # 
```

Make a half-sphere (dome) with radius of 42, emptying out the innards:
```
/make dome 42 empty # 
```

Make a half-sphere (bowl) with a radius of 12, emptying the innards, out of smooth stone:
```
/make bowl 12 empty SMOOTH_STONE
```

Make a dome out of bedrock, without emptying contents.
```
/make dome 120 false BEDROCK
```

## CHANGELOG

*1.1.0*
  - TBD

*1.0.0*
  - Initial Release


## Developing

You should be able to just `mvn install` and `mvn build` to generate an appropriate `.jar` file, to drop into your `$server/plugins` folder.  

## TODO

* better emptying math (some points are missed)
* port other geometry things from original javascript code to real spigot plugin/commands
* walls, towers, spires, arches, etc
* better material sets, named material sets
