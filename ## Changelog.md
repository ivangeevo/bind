# List of changes

# v???(dev)

# v1.5.2
+ Changed BTWR: Shared Library to allow higher versions than the current one specified
+ Updated the mod to BTWR: Shared Library 0.8

# v1.5.1
+ Added BTWR: SL as a dependency. It was in the mod before, but it's required as a dependency now, so compatibility with Better With Time can happen without errors. Before it didn't send any error messages which could've been confusing with people trying to use the mod with BWT
+ Fixed a bug (this time for real) with items from Tough Environment and Better With Time crashing the game on placement because of using BTWR:SL 0.6.5 instead of 0.7 which changed packages internally, and they couldn't be located
+ Fixed mod icon not displaying properly since it got broken in some of the last updates
+ Updated the mod to BTWR: Shared Library 0.7

# v1.5
+ Fixed a bug with Tough Environment chisel tools & Better With Time mattock/battleaxe tools causing a crash because of old BTWR: Shared Library version. This bug was present when using the previous version of Bind with the new BTWR: SL 0.6.5
+ Refactored pretty much the whole code; mainly for readability and cleaning up, but also so it's more in order with other mods from the BTWR project
+ Changed some code internally to prepare the mod for allowing placeable tools to be data driven. This is an extensive change and will come in a later update
+ Updated the mod to Fabric API 0.116.7, Fabric Loader 0.17.3 & BTWR: Shared Library 0.6.5

# v1.4
+ Changed some code internally to split client side code into it's separate package
+ Fixed a bug with the mod not working on dedicated servers and causing a crash
+ Updated the mod to Fabric API 0.116.5, Fabric Loader 0.16.14 & BTWR: Shared Library 0.57

# v1.3
+ Updated the mod to BTWR-SL v0.50

Minimised the amount of mixins that the mod uses by:
+ Moving outline shapes, the visual offset, pitch and bounding box values into the utility class `ToolRenderManager`

Fixed the following bugs:

+ Placing tools while holding a block in the off-hand tries placing both which makes the placed tool invisible on the client side.
+ Using axes on logs (while trying to place it as a block)flickers the log block(tries to strip it. but fails on the server side)
+ Axes aren't placeable as blocks without the sturdy trees mod present.

## v1.2
+ Made axes placeable on Sturdy Trees stump blocks
+ Fixed a bug that set tools to player's offhand instead of main hand on pickup
+ Updated the mod to Fabric API 0.115.3 & BTWR: Shared Library 0.48

## v1.1
+ Fixed the light level for the placed tools to be properly lit/dim whenever it's appropriate
+ Improved pickup logic for the placed tools to work in more cases (not only on empty hand)
+ Updated the mod Fabric API 0.115.1

## v1.0
+ Initial release