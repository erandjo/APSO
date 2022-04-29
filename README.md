# APSO
Android Permission Sharing Ontology (APSO)

### The interesting files:
  
##### ./Ontology
    * The files to inspect (protege) apso.owl (the full ontology) or apso-central.owl (the core and permission terms).
    * The files to inspect (fuseki)  apso.ttl (the ontology including the scenarios given in the master thesis).
    * python_files/replace_prefixes.py (used to clean up the ontology when its inferred axioms is exported. Prefix declarations must be manually changed)
    
##### ./Dataset
    * json-files
        + Android 77 Dataset/pretty_andapps77.json (a JSON file listing the collected applications and their attributes. It is formated pretty)
        + Intermediate Files/dataset_andapps77.json (the file listing the collected applications and their attributes. It is not formated pretty)
        + Intermediate Files/from_android_emulator.json (the JSON file written by MainActivity.kt)  
    * AndroidStudioProjects/MyApplication2 is the Android studio project for collecting the 77 android applications on an Android 11 emulated device. The interesting files are:
        + /app/src/main/java/com/example/myapplication/MainActivity.kt (the code for writing a json with information about the emulated device's applications).
        + /app/src/main/AndroidManifest.xml (include the android permissions needed to collect information on each application and store the information in a JSON file; 
          the file was written to the device's external storage. We manually copied the file from the emulated device's external storage.
    * Jupyter
      + Application Dataset/CleaningDatasetFromAndroidEmulator.ipynb (create dataset_andapps77 from from_android_emulator)
      + Investigate dataset_andapps77.ipynb (look at the applications in the dataset)
      + ParseToCsv (create the csv files)
      + ParseToLatex (create the table in the master thesis showing permissions and their groups).
    * csv-files.zip 
      + androidpermissiongroups.csv (a file listing Androids permission groups related to the permissions found when parsing AndroidManifest.xml)
      + androidpermissions.csv (a file listing android runtimes permissions; found when parsing AndroidManifest.xml)
      + collection of categories and authors.csv (a file listing the authors and play categories of the applications in the collected andapp77 dataset)
