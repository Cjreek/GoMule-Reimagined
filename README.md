# GoMule-Reimagined

This is a fork of the popular Muling tool "GoMule" originally created by Andy Theuninck (Gohanman) and then continued by Randall and Silospen.
It was modified to be compatible with the Diablo 2 Resurrected mod "Reimagined" (2.1.1+)

![image](https://github.com/user-attachments/assets/3c53b5dd-4b99-45ba-9187-572866ed9963)

Get D2R Reimagined here: https://www.nexusmods.com/diablo2resurrected/mods/503

## Download

Go to the release page: https://github.com/Cjreek/GoMule-Reimagined/releases

### Direct download links
⮕ [2.1.2](https://github.com/Cjreek/GoMule-Reimagined/releases/download/gomule_reimagined_212/GoMule_Reimagined_2.1.2.zip)  
⮕ [2.1.1](https://github.com/Cjreek/GoMule-Reimagined/releases/download/gomule_reimagined_211/GoMule_Reimagined_2.1.1.zip)  

## Installation

0) You need to have the Java runtime installed on your PC!
1) Extract the contents of the .zip file.
2) Done

## Restrictions & Known Bugs

The main feature of opening characters and shared stashes and moving items inbetween those and custom GoMule/ATMA stashes should be fully functional for D2RR.
You might encounter very few custom properties from D2RR that are not correctly rendered. This is just a visual glitch and there is no danger of file corruption or anything bad happening with those items or characters.
It's just a minor visual issue that I might try to fix if you find something like this and report it to me.

The Flavie Report feature is currently not compatible for D2RR and I currently have no plans of fixing this anytime soon or probably at all.
I have focused on the main feature which is muling.

At the time of the first release I have tested this version of GoMule on my own characters/stashes for about a week with no issues.
It's still advised to make regular backups of your characters and GoMule stashes - just in case.

## Help & Support

If you have any questions or issues with this tool you can either post your issue here on github or contact @Cjreek on the Reimagined discord

## Compiling for different/new versions of D2RR

This is addressed at developers or technically versed people who want to compile GoMule Reimagined for a different or a new release of the Reimagined Mod.
Generally no actual code changes are necessary. Some files from the mod need to be copied over and then GoMule needs to be recompiled:

1) Copy all `.dc6` files from `/data/global/items/` into `resources/gfx/`
2) Copy following files from `/data/local/lng/strings/` into `/src/main/resources/d2Files/D2R_1.0/translations/`
    - item-modifiers.json
    - item-nameaffixes.json
    - item-names.json
    - item-runes.json
    - monsters.json
    - skills.json
4) Copy following files from `/data/global/excel/` into `d2111/`
    - armor.txt
    - automagic.txt
    - charstats.txt
    - gems.txt
    - hireling.txt
    - itemstatcost.txt
    - itemtypes.txt
    - levels.txt
    - magicprefix.txt
    - magicsuffix.txt
    - misc.txt
    - monstats.txt
    - properties.txt
    - runes.txt
    - setitems.txt
    - sets.txt
    - skilldesc.txt
    - skills.txt
    - superuniques.txt
    - treasureclassex.txt
    - uniqueitems.txt
    - weapons.txt
4) Open `build.gradle` and change the `archiveName` in the distribution task to match the version you want to build GoMule for:
```gradle
task distribution(type: Zip) {
    from 'build/tmp/distribution/'
    include 'GoMule/**'
    archiveName 'GoMule_Reimagined_2.1.2.zip'
}
```
5) Execute the `distribution` gradle task.
6) An updated version of GoMule should be generated and placed in `/build/distributions/`
