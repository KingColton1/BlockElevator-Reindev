# BlockElevator for ReIndev
BlockElevator is a ReIndev FoxLoader mod that turns a block into an elevator to lift you from a block to another block above or below you. It can go as high as you want between two blocks, or add multiple elevator blocks in between two blocks to access more than two floors. You can add as many elevator block as you want as long as your elevator blocks are in the same x,z coordinate, regardless of height.

This is a direct rewrite of [CivElevator](https://github.com/KingColton1/CivElevator), an independent plugin with decoupled codes of CivMC's GoldElevator plugin.

## Usage
To create an elevator, first place a gold block (default) anywhere and place a second gold block more than two blocks above or below the first gold block you placed previously. Then jump to go up if you placed the second block above, or crouch to go down if you placed the second block below. The second block can be placed anywhere as long as it is in the same x,z coordinate as the first block.

NOTE: You can only move up to 40 blocks up or down by default before you need to place down another elevator block.

## Commands
`/elevator` (OP not required, but is for the commands below)\
Shows the config

`/elevator enabled <true or false>`\
Enables/disables elevators (Default: **true**)

`/elevator cooldownticks <integer number>`\
Set the cooldown in ticks (1/20 of a second), set to 0 for instant (Default: **15**)

`/elevator maxystep <integer number>`\
Set the max space you can have between elevator blocks (Default: **40**)

`/elevator dyrequiredforjump <floating point number>`\
Set the deltaY per tick threshold at which jumping will trigger an upward teleport\
Essentially, how instant a jump will trigger an elevator. Set to 0 for instant (Default: **0.075**)

`/elevatorblocks` (OP not required, but is for the commands below)\
List the blocks considered elevator blocks

`/elevatorblocks <add/remove> <block name or id>`\
Add or remove a block as an elevator block by name or ID\
Example: `/elevatorblocks add obsidian`

## Known issues / caveats
- You can be teleported inside a block if there's any above an elevator block
- You can trigger moving downward when stood right next to an elevator block
- It might not trigger properly if you have multiple elevator blocks next to eachother
- A slab placed on top of an elevator block will not stop it from working
- If you removed all the blocks registered as elevator blocks with `/elevatorblocks remove ...`, the default gold block will be automatically added when the server restarts

## Other
All data BlockElevator stores is in the `mods/BlockElevator` folder.\
The config file is `mods/BlockElevator/config.txt`

## Contributing
To contribute, please fork this repo and modify or fix as you deem necessary to improve BlockElevator. Then you may make a pull request to main branch and I will review before I approve to merge into main.
