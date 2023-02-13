# Watermelon 
It is a simple tool written in Java to modify images using batch process defined in JSON file

## Example configuration file
```json
{
  "inputDirectory": "input",
  "outputDirectory": "output",
  "resourcesDirectory": "res",
  "steps": [
    {
      "type": "SCALE",
      "x": 50,
      "y": 50
    },
    {
      "type": "WRITE_IMAGE",
      "fileName": "watermark-1.png",
      "alpha": 0.5,
      "x": 50,
      "y": 50
    },
    {
      "type": "WRITE_IMAGE",
      "fileName": "watermark-1.png",
      "alpha": 0.5,
      "x": -50,
      "y": -50
    },
    {
      "type": "WRITE_IMAGE",
      "fileName": "watermark-2.png",
      "x": 50,
      "y": -50
    },
    {
      "type": "WRITE_IMAGE",
      "fileName": "watermark-2.png",
      "x": -50,
      "y": 50
    }
  ]
}
```

## Configuration file reference
root:
* inputDirectory - defines a subdirectory of main process directory, files here will be used are source files for all process steps
* outputDirectory - defines a subdirectory of main process directory, here the output files will be created
* resourcesDirectory - defines a subdirectory of main process directory, is used to store resource files, for example watermarks
* steps - list of steps objects defined below

step:
* type: WRITE_IMAGE or SCALE
  * **WRITE_IMAGE** - will write the image defined in **fileName** parameter on top on source image
    * image will be draw in given position (x and y parameters)
    * if the image has to be drawn on right or top edge, value will be negative. -50 means 50px margin
  * **SCALE** - will scale the source image for given x/y values, value should be defined as percent 0-100