package com.example.kotlinmultiplatformsharedmodule

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

class CategoryActivity : AppCompatActivity() {
    private lateinit var category: Category
    private lateinit var country: String

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        // Category object is used to control what links to display in expandable list.
        if (extras != null) {
            category = (intent.getSerializableExtra("category") as Category?)!!
            country = extras.getString("country")!!
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        expandableListView = findViewById<View>(R.id.expandableListView) as ExpandableListView
        createCategoriesLists(this, this, category, country)
        //populateLists(this, applicationContext)
    }

    companion object {
        private const val TAG = "CategoryActivity"
        lateinit var expandableListView: ExpandableListView
        private lateinit var expandableListAdapter: ExpandableListAdapter
        private lateinit var expandableListTitle: List<String>
        private val activityExpandableListDetail = HashMap<String, List<String>>()
        private val activityUrls = HashMap<String, String>()
        private val categories = HashMap<String, String>()
        private var fbShared = Firebase()
        var headings = arrayOf<String>()
        var references = arrayOf<String>()

        private fun setHeadings(headings: ArrayList<String>) {
            this.headings = headings.toTypedArray()
        }

        private fun setReferences(references: ArrayList<String>) {
            this.references = references.toTypedArray()
        }

        /***
         * Creates expandable list and handles opening of URLs. Called for each heading in FireBase.
         * @param categoryActivity used to create browser activity.
         * @param expandableListDetail HashMap that contains headings and list of URL names from
         * FireBase.
         * @param urls List of URLs taken from FireBase
         * @param context static method needs context to create toast.
         */
        fun updateLists(
            categoryActivity: CategoryActivity,
            expandableListDetail: HashMap<String, List<String>>?,
            urls: HashMap<String, String>,
            context: Context
        ) {
            /* Stores our URls and URL text in the object. Headings are taken one at a time from
            FireBase so this information needs to be stored here so it can all be displayed at
            once. */
            activityExpandableListDetail.putAll(expandableListDetail!!)
            activityUrls.putAll(urls)

            // Creating the expandable list.
            expandableListTitle = ArrayList(activityExpandableListDetail.keys)

            expandableListAdapter = CustomExpandableListAdapter(
                categoryActivity, expandableListTitle,
                activityExpandableListDetail
            )

            expandList()

            // Attempts to open the URL in phone's browser, catches exceptions if it cannot.
            expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition,
                                                         _ ->
                val url = activityUrls[activityExpandableListDetail[(expandableListTitle
                        as ArrayList<String>)[groupPosition]]!![childPosition]]
                Log.d(TAG, "URL: $url")
                try {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    categoryActivity.startActivity(browserIntent)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        context,
                        "Cannot open link, missing browser on device.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(TAG, "Error opening link: $e")
                } catch (pe: ParseException) {
                    Toast.makeText(context, "Cannot parse link.", Toast.LENGTH_LONG).show()
                    Log.e(TAG, "Error parsing link: $pe")
                }
                false
            }
        }

        /***
         * Will keep list items expanded when/if content gets updated in Firebase.
         */
        private fun expandList() {
            var toExpand = arrayOfNulls<Boolean>(expandableListAdapter.groupCount)
            var doExpand = false
            for (i in 0 until expandableListAdapter.groupCount) {
                if (expandableListView.adapter != null) {
                    toExpand[i] = expandableListView.isGroupExpanded(i)
                    doExpand = true
                }
            }
            expandableListView.setAdapter(expandableListAdapter)
            for (j in toExpand.indices) {
                if (doExpand) {
                    if (toExpand[j]!!) {
                        expandableListView.expandGroup(j)
                    }
                }
            }
        }

        /***
         * Used to translate FireBase reference into human-readable heading text.
         * @param reference FireBase reference.
         * @return Human-readable heading that is mapped to FireBase reference.
         */
        fun getSubCategory(reference: String): String? {
            return categories[reference]
        }

        /***
         * Sets values that are used by FirebaseHandler to create links in expandable list.
         * @param context static method needs context to get class properties.
         * @param category is used to select correct links from firebase.
         */
        private fun createCategoriesLists(
            categoryActivity: CategoryActivity,
            context: Context,
            category: Category?,
            country: String
        ) {
            /* Clear HashMaps to prevent irrelevant links being left over from previously visited
                pages. */
            activityExpandableListDetail.clear()
            activityUrls.clear()

            when (category!!.name) {
                Category.Name.DAILYLIFE -> {
                    setCategoriesFirebase(country, "life", categoryActivity, context)
                }
                Category.Name.HEALTH -> {
                    setCategoriesFirebase(country, "health", categoryActivity, context)
                }
                Category.Name.SETTLINGIN -> {
                    setCategoriesFirebase(country, "settling", categoryActivity, context)
                }
                Category.Name.MIGRANTSTATUS -> {
                    setCategoriesFirebase(country, "migrant", categoryActivity, context)
                }
                Category.Name.LANGUAGE -> {
                    setCategoriesFirebase(country, "language", categoryActivity, context)
                }
            }
        }

        private fun setCategoriesFirebase(
            country: String,
            category: String,
            categoryActivity: CategoryActivity,
            context: Context
        ) {
            val coroutineContext: CoroutineContext = Dispatchers.Main
            val scope = CoroutineScope(coroutineContext + SupervisorJob())

            var flow = fbShared.getDataFlow(country, category, "headings")
            scope.launch {
                flow.collect { response ->
                    val headings = arrayListOf<String>()
                    val references = arrayListOf<String>()
                    for (ds in response.children) {
                        if (ds.key != null) {
                            var heading = ds.children.iterator()
                            references.add(heading.next().value()) // id
                            headings.add(heading.next().value()) // name
                        }
                    }
                    setHeadings(headings)
                    setReferences(references)

                    setCategories(country, category)
                    ExpandableListDataPump.populateLists(categoryActivity, category, context)
                }
            }
        }

        /***
         * Provides mapping between FireBase reference name and human-readable heading in app.
         */
        private fun setCategories(country: String, category: String) {
            if (headings.size == references.size) {
                for (i in headings.indices) {
                    val id = country + "/" + category + "/" + references[i]
                    categories[id] = headings[i]
                }
            }
        }
    }
}