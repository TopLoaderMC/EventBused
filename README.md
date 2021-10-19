Event Bus(ed)
=============
![Latest release version badge](https://img.shields.io/maven-metadata/v?color=forestgreen&label=release&metadataUrl=https%3A%2F%2Fmaven.socketmods.dev%2Frepository%2Ftoploader%2Fcom%2Fgithub%2Ftoploadermc%2Feventbused%2Feventbus-core%2Fmaven-metadata.xml)
![Latest snapshot version badge](https://img.shields.io/maven-metadata/v?color=tomato&label=snapshot&metadataUrl=https%3A%2F%2Fmaven.socketmods.dev%2Frepository%2Ftoploader%2Fcom%2Fgithub%2Ftoploadermc%2Feventbused%2Feventbus-core%2Fmaven-metadata.xml&versionSuffix=-SNAPSHOT)

A Slower* Implementation of https://github.com/MinecraftForge/EventBus

- Automatic Type resolving via TypeTools separated out

- Removes ASM / ModLauncher Requirement
    - TODO: Add a module to declare usage of these to improve speed

- Events can be plain Objects (No Base / Marker Type required)
    - NOTE: It's still recommended doing so, as it'll be used as a marker for transformation in the ASM / ModLauncher module
    
- Slimmer Impact on Event Objects
    - Declare what's necessary yourself, See Cancellable / HasResult / HasPriority

- Slower than the Original!

Builds Published to https://maven.socketmods.dev/repository/toploader/