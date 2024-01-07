# BlockElevator for ReIndev

BlockElevator is a mod/plugin that turn a block into an elevator to lift you from a block to another block above or below you. It can go as high as you want between two blocks, or add multiple elevator blocks in between two blocks to access more than two floors. You can add as many elevator block as you can as long as your elevator block is in same coordinate as each others, regardless of the y-height.

This is an direct rewrite of [CivElevator](https://github.com/KingColton1/CivElevator), a independent plugin with decoupled codes of CivMC's GoldElevator plugin.

## USAGE

To create an elevator, place first gold block (default) anywhere (e.g. ground, underground, etc.) and place second gold block more than two blocks above or below of first gold block you placed previously. Then use jump (space bar) to go up if you placed second block above. Or sneak (shift) to go down if you placed second block below. Second block can be placed anywhere in y-height as long as the second block is on same x,z coordinate as first block.

NOTE: You can only move up to 40 blocks up or down before you need to place down another elevator block. 

## KNOWN ISSUES

- You can be teleported inside a block if there's any above an elevator block
- A slab placed on top of an elevator block will not stop it from working

## CONTRIBUTION

To contribute, please fork this repo and modify or fix as you deem necessary to improve BlockElevator. Then you may make a pull request to main branch and I will review before I approve to merge into main.
