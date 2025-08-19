# List of changes

# v1.4(dev)
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