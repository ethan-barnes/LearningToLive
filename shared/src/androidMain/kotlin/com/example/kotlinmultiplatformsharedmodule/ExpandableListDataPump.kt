package com.example.kotlinmultiplatformsharedmodule

import android.content.Context
import android.util.Log
import com.example.shared.*
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.Flow

object ExpandableListDataPump {
    private const val TAG = "ExpandableListDataPump"
    private var fbShared = FirebaseShared()

    /***
     * This a more logical function name than getData()
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    @JvmStatic
    fun populateLists(categoryActivity: CategoryActivity, category: String, context: Context) {
        getData(categoryActivity, category, context)
    }

    /***
     * Sends request to FireBase for each heading in the selected page. Then calls createLists() to
     * send this information back to CategoryActivity.
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    private fun getData(categoryActivity: CategoryActivity, category: String, context: Context) {
        val refs = CategoryActivity.references
        val country = categoryActivity.intent.extras?.getString("country")
        val coroutineContext: CoroutineContext = Dispatchers.Main
        val scope = CoroutineScope(coroutineContext + SupervisorJob())

        try {
            for (ref in refs) {
                if (country != null) {
                    var flow = fbShared.getDataFlow(country, category, ref)

                    scope.launch {
                        flow.collect { response ->
                            val data = HashMap<String?, String?>()
                            for (ds in response.children) {
                                if (ds.key != null) {
                                    data[ds.key] = ds.value()
                                }
                            }
                            if (data.isNotEmpty()) {
                                var path = "$country/$category/$ref"
                                createLists(path, data, categoryActivity, context)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Could not get from FireBase. $e")
        }
    }

    /***
     * Takes values from FireBase requests, formats them, and sends them to CategoryActivity to
     * create expandable list.
     * @param title Heading that will be displayed in expandable list.
     * @param hash a hashmap created by FireBase handler of URL name and URL.
     * @param categoryActivity the categoryActivity object that will be displaying our information.
     * @param context static method needs context.
     */
    private fun createLists(
        title: String,
        hash: HashMap<String?, String?>?,
        categoryActivity: CategoryActivity,
        context: Context
    ) {
        val expandableListDetail = HashMap<String, List<String>>()
        val urls = HashMap<String, String>()
        val list = mutableListOf<String>()

        if (!hash.isNullOrEmpty()){
            for (key in hash.keys) {
                list.add(key!!)
                urls[key] = hash[key].toString()
            }
        }

        expandableListDetail[CategoryActivity.getSubCategory(title)!!] = list
        CategoryActivity.updateLists(categoryActivity, expandableListDetail, urls, context)
    }
}
