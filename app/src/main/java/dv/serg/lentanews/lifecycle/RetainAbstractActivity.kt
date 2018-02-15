package dv.serg.lentanews.lifecycle

import android.annotation.SuppressLint
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import dv.serg.lentanews.util.Tim

abstract class RetainAbstractActivity : AppCompatActivity() {

    companion object {
        const val DEF_RETAIN_TAG = "DEF_RETAIN_TAG"
        const val TAG = "RetainAbstractActivity"
        const val AUTO_PERSIST = "auto_persist"
    }

    private var tag: String = DEF_RETAIN_TAG

    private var retainAdapterFragment: RetainFragment? = null

    private var mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Tim.d("RetainAbstractActivity:onCreate")

        retainAdapterFragment = fragmentManager.findFragmentByTag(tag) as RetainFragment?

        if (retainAdapterFragment == null) {
            retainAdapterFragment = RetainFragment()
            fragmentManager.beginTransaction().add(retainAdapterFragment, tag).commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        retainAdapterFragment?.adapterInstance = mAdapter
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
//        mAdapter = retainAdapterFragment?.adapterInstance
//        recoveryAdapter(mAdapter ?: throw Exception("Adapter recovery exception"))
    }

    abstract fun recoveryAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>)

    fun autoPersistAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mAdapter = adapter
//        adapter.
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

        override fun onAttach(context: Context?) {
            super.onAttach(context)
            Tim.d("RetainFragment:onAttach")

        }

        override fun onDetach() {
            super.onDetach()
            Tim.d("RetainFragment:onDetach")
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            Tim.d("RetainFragment:onActivityCreated")
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            Tim.d("RetainFragment:onCreate")

            retainInstance = true
        }

        override fun onDestroyView() {
            super.onDestroyView()
            Tim.d("RetainFragment:onDestroyView")
        }

        override fun onDestroy() {
            super.onDestroy()
            Tim.d("RetainFragment:onDestroy")
        }
    }
}