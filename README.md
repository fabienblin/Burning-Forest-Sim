# Java technical test CirilGroup (JDK 11)

Simulate fire propagation in a forest.

## Usage

Run using a predefined map from the maps folder
`./fire (propagation rate) (map file)`

Run using arguments
`./fire (propagation rate) (map size) ...(fire init positions)`

The program will stop after all fires stop propagating.

### Arguments

- `**propagation rate** : a floating point value ranging between 0 and 1, a low value is a high propagation prbability ex.: "0.5"`
- `**map file** : a file from the maps/ folder ex.: "maps/empty.forest"`
- `**map size** : size of initial map ex.: "[100, 100]"`
- `**fire init** positions : a list of starting positions for the fire (positions that are out of the map will not trigger a fire) ex.: "[0, 0]" "[100, 100]"`

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
