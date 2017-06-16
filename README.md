# DTN GIS

[![Build Status](https://travis-ci.org/archie94/dtngis-core.svg?branch=master)](https://travis-ci.org/archie94/dtngis-core)

This project aims to dissolve several similar crowd-sourced geospatial objects.

### Working Details

We share geospatial data with our [pdm](https://github.com/sunny5125/pdm/tree/development) app. The app exports GIS layes in standard *geojson* format. We aim to take layers received from nodes and compare geospoatial objects from similar layers. Similar geospatial objects (ie, the objects which represent the same data) from similar layers are then clubbed into one layer and this layer contains the *merged* view of geospatial objects. 

Currently we are able to merge only polygon layers. 

### Getting started

- Clone this project.
- Build and run. Change `WORKING_DIR_TEMP` in [Paths.java](https://github.com/archie94/dtngis-core/blob/master/src/main/java/constants/Paths.java) to whichever is your testing directory. If there are similar layers in this directory we get the resulting layer in `out.zip`

### Disclaimer

Still in pre alpha stage. Not yet automated to work with [pdm](https://github.com/sunny5125/pdm/tree/development).
