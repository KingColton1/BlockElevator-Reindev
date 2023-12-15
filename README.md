# BlockElevator for ReIndev

BlockElevator is a mod/plugin that turn a block into an elevator to lift you from a block to another block above or below you. It can go as high as you want between two blocks, or add multiple elevator blocks in between two blocks to access more than two floors. You can add as many elevator block as you can as long as your elevator block is in same coordinate as each others, regardless of the y-height.

This is an direct rewrite of [CivElevator](https://github.com/KingColton1/CivElevator), a independent plugin with decoupled codes of CivMC's GoldElevator plugin.

## USAGE

Due to the limitation in ReIndev's modified core codes of Minecraft, for now you won't be able to customize block unless if you know how to code and directly modify blockId code to change a block to be used as an elevator.

To create an elevator, place block of gold (by default) on bottom and place second block of gold two blocks above or below of first block of gold you placed previously. Then use jump (space bar) to go up if you placed second block above. Or sneak (shift) to go don if you placed second block below. Second block can be placed anywhere in y-height as long as the second block is on same x,z coordinate as first block.

## CONTRIBUTION

To contribute, please fork this repo and modify or fix whatever it need to improve BlockElevator. Then you may make a pull request to main branch and I will review before I approve to merge into main.
