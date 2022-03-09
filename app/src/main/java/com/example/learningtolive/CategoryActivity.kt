package com.example.learningtolive

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ParseException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learningtolive.ExpandableListDataPump.populateLists
import java.util.*
import kotlin.collections.HashMap

class CategoryActivity : AppCompatActivity() {
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        // Category object is used to control what links to display in expandable list.
        if (extras != null) {
            category = (intent.getSerializableExtra("category") as Category?)!!
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        createCategoriesLists(this, category)
        expandableListView = findViewById<View>(R.id.expandableListView) as ExpandableListView
        refreshBtn = findViewById<View>(R.id.refreshBtn) as Button
        populateLists(this, applicationContext)
    }

    companion object {
        private const val TAG = "CategoryActivity"
        lateinit var expandableListView: ExpandableListView
        private lateinit var expandableListAdapter: ExpandableListAdapter
        private lateinit var expandableListTitle: List<String>
        private lateinit var refreshBtn: Button
        private val activityExpandableListDetail = HashMap<String, List<String>>()
        private val activityUrls = HashMap<String, String>()
        private val categories = HashMap<String, String>()
        var references = arrayOf<String>()

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
        private fun createCategoriesLists(context: Context, category: Category?) {
            var headings = arrayOf<String>()

            // Clear HashMaps to prevent irrelevant links being left over from previously visited
            // pages.
            activityExpandableListDetail.clear()
            activityUrls.clear()
            when (category!!.name) {
                Category.Name.DAILYLIFE -> {
                    headings = context.resources.getStringArray(R.array.daily_life_headings)
                    references = context.resources.getStringArray(R.array.daily_life_references)
                }
                Category.Name.HEALTH -> {
                    headings = context.resources.getStringArray(R.array.health_headings)
                    references = context.resources.getStringArray(R.array.health_references)
                }
                Category.Name.SETTLINGIN -> {
                    headings = context.resources.getStringArray(R.array.settling_headings)
                    references = context.resources.getStringArray(R.array.settling_references)
                }
                Category.Name.MIGRANTSTATUS -> {
                    headings = context.resources.getStringArray(R.array.migrant_headings)
                    references = context.resources.getStringArray(R.array.migrant_references)
                }
                Category.Name.LANGUAGE -> {
                    headings = context.resources.getStringArray(R.array.language_headings)
                    references = context.resources.getStringArray(R.array.language_references)
                }
            }

            // Provides mapping between FireBase reference name and human-readable heading in app.
            if (headings.size == references.size) {
                for (i in headings.indices) {
                    categories[references[i]] = headings[i]
                }
            }
        }
    }
}