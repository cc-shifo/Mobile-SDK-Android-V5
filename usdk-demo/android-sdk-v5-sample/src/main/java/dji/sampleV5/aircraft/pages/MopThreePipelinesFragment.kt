package dji.sampleV5.aircraft.pages

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import dji.sampleV5.aircraft.R
import dji.sampleV5.aircraft.models.MopVM
import dji.sampleV5.aircraft.views.MSDKInfoFragment
import dji.sdk.keyvalue.value.mop.PipelineDeviceType
import dji.sdk.keyvalue.value.mop.TransmissionControlType
import dji.v5.utils.common.LogUtils
import kotlinx.android.synthetic.main.frag_mop_interface_page.*
import kotlinx.android.synthetic.main.frag_mop_interface_page.btn_disconnect
import kotlinx.android.synthetic.main.frag_mop_interface_page.cb_reliable
import kotlinx.android.synthetic.main.frag_mop_interface_page.et_channel_id
import kotlinx.android.synthetic.main.frag_mop_interface_page.rg_mop_type
import kotlinx.android.synthetic.main.frag_mop_interface_page_three_pipelines.*
import kotlinx.android.synthetic.main.frag_payload_data_page.btn_send_data_to_payload
import kotlinx.android.synthetic.main.frag_payload_data_page.ed_data
import kotlinx.android.synthetic.main.frag_payload_data_page.message_listview

/**
 * Description :
 *
 * @author: Byte.Cai
 *  date : 2023/1/31
 *
 * Copyright (c) 2022, DJI All Rights Reserved.
 */
class MopThreePipelinesFragment : DJIFragment() {
    companion object {
        const val TAG = "MopThreePipelinesFragment"
    }




    private val onKeyListener: View.OnKeyListener = object : View.OnKeyListener {
        override
        fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                //隐藏软键盘
                val inputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (inputMethodManager.isActive) {
                    inputMethodManager.hideSoftInputFromWindow(v.applicationWindowToken, 0)
                }
                return true
            }
            return false
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_mop_interface_page_three_pipelines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initListener() {
       // nothing
    }

    private fun initView() {
        childFragmentManager.commit {
            add(R.id.frag_mop_49152, MopInterfaceFragment())
            add(R.id.frag_mop_49153, MopInterfaceFragment())
            add(R.id.frag_mop_49160, MopInterfaceFragment())
        }
        // val transaction1: FragmentTransaction = childFragmentManager.beginTransaction();
        // transaction1.add(R.id.frag_mop_49152, MopInterfaceFragment())
        //     .commit()
        // val transaction2: FragmentTransaction = childFragmentManager.beginTransaction();
        // transaction2.add(R.id.frag_mop_49153, MopInterfaceFragment())
        //     .commit()
        // val transaction3: FragmentTransaction = childFragmentManager.beginTransaction();
        // transaction3.add(R.id.frag_mop_49160, MopInterfaceFragment())
        //     .commit()
    }

}