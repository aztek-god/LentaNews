package dv.serg.lentanews.util

import android.annotation.SuppressLint
import android.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup

abstract class RetainAbstractActivity : AppCompatActivity() {

    companion object {
        val DEF_RETAIN_TAG = "DEF_RETAIN_TAG"
        val TAG = "RetainAbstractActivity"
    }


    private var tag: String = DEF_RETAIN_TAG

    private var retainAdapterFragment: RetainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        retainAdapterFragment = fragmentManager.findFragmentByTag(tag) as RetainFragment?

        if (retainAdapterFragment == null) {
            retainAdapterFragment = RetainFragment()
            fragmentManager.beginTransaction().add(retainAdapterFragment, tag).commit()
        }
    }

    fun saveAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        this.retainAdapterFragment?.adapterInstance = adapter
    }

    fun loadAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return retainAdapterFragment?.adapterInstance ?: object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun getItemCount(): Int {
                throw Exception("The empty adapter has been returned")
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
                throw Exception("The empty adapter has been returned")
            }

            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
                throw Exception("The empty adapter has been returned")
            }

        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            val fm = fragmentManager
            fm.beginTransaction().remove(retainAdapterFragment).commit()
        }
    }

    fun changeTag(tag: String) {
        this.tag = tag
    }

    @SuppressLint("ValidFragment")
    private class RetainFragment : Fragment() {

        companion object {
            val TAG = "RetainFragment"
        }

        var adapterInstance: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            Log.d(TAG, "RetainFragment:onCreate")

            retainInstance = true
        }
    }
}