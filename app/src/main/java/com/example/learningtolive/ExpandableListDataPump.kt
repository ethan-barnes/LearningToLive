package com.example.learningtolive

import android.content.Context
import android.util.Log

object ExpandableListDataPump {
    private const val TAG = "ExpandableListDataPump"

    /***
     * This a more logical function name than getData()
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    @JvmStatic
    fun populateLists(categoryActivity: CategoryActivity, context: Context) {
        getData(categoryActivity, context)
    }

    /***
     * Sends request to FireBase for each heading in the selected page. Then calls createLists() to
     * send this information back to CategoryActivity.
     * @param categoryActivity used to work around asynchronous issues.
     * @param context static method needs context.
     */
    private fun getData(categoryActivity: CategoryActivity, context: Context) {
        val fb = FirebaseHandler()
        val refs = CategoryActivity.references

        try {
            for (ref in refs) {
                fb.getValue(ref, object : MyCallback {
                    override fun onCallBack(
                        title: String?,
                        value: java.util.HashMap<String?, String?>?
                    ) {
                        if (value!!.isNotEmpty()) {
                            createLists(title!!, value, categoryActivity, context)
                        }
                    }
                })
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
