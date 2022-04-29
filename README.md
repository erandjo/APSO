# APSO
Android Permission Sharing Ontology (APSO)

The interesting files:
  
 ./Ontology
    - The files to inspect (protege) apso.owl (the full ontology) or apso-central.owl (the core and permission terms).
    - The files to inspect (fuseki)  apso.ttl (the ontology including the scenarios given in the master thesis).
    
 ./Dataset
    - AndroidStudioProjects/MyApplication2 is the Android studio project for collecting the 77 android applications on an Android 11 emulated device. The interesting files are:
        o /app/src/main/java/com/example/myapplication/MainActivity.kt (the code for writing a json with information about the emulated device's applications).
        o /app/src/main/AndroidManifest.xml (include the android permissions needed to collect information on each application and store the information in a JSON file; 
          the file was written to the device's external storage. We manually copied the file from the emulated device's external storage.
