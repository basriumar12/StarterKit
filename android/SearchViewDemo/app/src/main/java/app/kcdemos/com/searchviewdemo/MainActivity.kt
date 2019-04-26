package app.kcdemos.com.searchviewdemo

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import java.io.IOException
import java.util.*


class MainActivity : AppCompatActivity(), DogModelAdapter.ItemClickListener {
    private var recyclerView: RecyclerView? = null
    private var dogsList: MutableList<DogModel>? = null
    private var mAdapter: DogModelAdapter? = null
    private var searchView: SearchView? = null
    private var mainLayout: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainLayout = layoutInflater.inflate(R.layout.activity_main, null)
        setContentView(mainLayout)

        recyclerView = findViewById(R.id.recycler_view)
        dogsList = ArrayList()
        mAdapter = DogModelAdapter(this, dogsList as ArrayList<DogModel>, this)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = mAdapter

        getServiceData()
    }

    private fun getServiceData() {

        try {
            //Use remote service for real - For this demo we are using a JSON file
            val jsonString = getJSONData()
            dogsList!!.clear()

            val collectionType = object : TypeToken<List<DogModel>>() {
            }.type

            val moviesArray = Gson()!!
                    .fromJson(jsonString, collectionType) as List<DogModel>?

            for (i in 0 until moviesArray!!.size) {

                val mObject = moviesArray[i]

                val mItem = DogModel(
                        mObject.name,
                        mObject.age,
                        mObject.picture)
                dogsList!!.add(mItem)
            }
        } catch (exception: IOException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        } catch (exception: JSONException) {
            Log.e(MainActivity::class.java.name, "Unable to parse JSON file.", exception)
        }
    }

    @Throws(IOException::class)
    private fun getJSONData(): String? {
        var jsonString: String?
        try {
            val inputStream = applicationContext.getAssets().open("service_data.json")
            val bufferSizesize = inputStream.available()
            val buffer = ByteArray(bufferSizesize)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            jsonString = null
        }

        return jsonString

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Associate search_layout configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        searchView!!.setSearchableInfo(searchManager
                .getSearchableInfo(componentName))
        searchView!!.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                mAdapter!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                mAdapter!!.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (!searchView!!.isIconified) {
            searchView!!.isIconified = true
            return
        }
        super.onBackPressed()
    }


    override fun onItemClicked(dog: DogModel) {
        Snackbar.make(mainLayout!!, "You Clicked: " + dog.name + ", Age: " + dog.age, Snackbar.LENGTH_LONG).show()
    }

}
