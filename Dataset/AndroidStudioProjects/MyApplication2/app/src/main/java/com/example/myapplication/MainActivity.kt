package com.example.myapplication


import android.content.pm.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

data class DataInstance(val packageName: String, val label: String, val description: String?, val category: String?, val reqPermissions: List<Pair<String, String>>)
data class DataGroup(val group: String, val gLabel: String, val permissions: MutableList<DataPermission>)
data class DataPermission(val permission: String, val pLabel: String)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val dataset = collectApplicationDataSet()
        val permissionGroups = collectPermissionsAndGroups()
        logcat(permissionGroups.toString())
        // writing to external memory
//        val gson: Gson = GsonBuilder().create()
//        val json: String = gson.toJson(permissionGroups)
//        writeToExternalStorage(json, "1-permissions-groups")

        setContentView(R.layout.activity_main)
    }

    private fun collectApplicationDataSet(): MutableList<DataInstance> {
        // retrieving packages with user applications, and their requested permissions
        val installedPackages: List<PackageInfo> = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS)
        val userPackages: List<PackageInfo> = installedPackages.filter { !it.applicationInfo.equals(null) && (it.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }

        //building dataset
        val dataset: MutableList<DataInstance> = mutableListOf()
        userPackages.forEach {
            val pairList: MutableList<Pair<String, String>> = mutableListOf()
            it.requestedPermissions?.forEach { perm -> try {
                pairList.add(getPermissionProtectionPair(perm))
            } catch (e: PackageManager.NameNotFoundException){ e.printStackTrace() } }
            dataset.add(
                DataInstance(
                    it.packageName,
                    it.applicationInfo.loadLabel(packageManager).toString() ,
                    it.applicationInfo.loadDescription(packageManager)?.toString() ?: "none",
                    ApplicationInfo.getCategoryTitle(applicationContext, it.applicationInfo.category)?.toString() ?: "none",
                    pairList))
        }

        return dataset
    }

    private fun collectPermissionsAndGroups(): MutableList<DataGroup>{
        val groups: MutableList<DataGroup> = mutableListOf()
        val groupInfo: List<PermissionGroupInfo> = packageManager.getAllPermissionGroups(0)

        groupInfo.forEach { group -> try {
            val permissionInfo: List<PermissionInfo> = packageManager.queryPermissionsByGroup(group.name, PackageManager.GET_META_DATA)
            val dg: DataGroup = DataGroup(group.name, group.loadLabel(packageManager).toString(), mutableListOf())
            permissionInfo.forEach { permission ->
                dg.permissions.add(
                    DataPermission(
                        permission.name,
                        permission.loadLabel(packageManager).toString()
                    )
                )
            }
            groups.add(dg)
        } catch (e: PackageManager.NameNotFoundException){ e.printStackTrace() } }

        return groups
    }

    // Write json object to file
    private fun writeToExternalStorage(json: String, fName: String) {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            logcat("not mounted"); return
        }

        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),fName)

        try {
            file.createNewFile()
            val stream = FileOutputStream(file, true)
            logcat("writing")
            stream.write(json.encodeToByteArray())
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            logcat("something went wrong")
        }

        logcat("done")
    }

    // function to extract the protection level a permission
    private fun getPermissionProtectionPair(perm: String): Pair<String, String> {
        val actual: PermissionInfo = packageManager.getPermissionInfo(perm, PackageManager.GET_META_DATA)
        return when (actual.protection) {
            PermissionInfo.PROTECTION_DANGEROUS -> Pair(perm, "dangerous")
            PermissionInfo.PROTECTION_NORMAL -> Pair(perm, "normal")
            PermissionInfo.PROTECTION_SIGNATURE -> Pair(perm, "signature")
            PermissionInfo.PROTECTION_INTERNAL -> Pair(perm, "internal")
            else -> Pair(perm, "unknown")
        }
    }

    private fun logcat(s: String) = Log.d("log2", s)
}

