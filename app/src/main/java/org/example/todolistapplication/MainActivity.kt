package org.example.todolistapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.example.todolist.AfterFragment
import org.example.todolist.BeforeFragment
import org.example.todolist.MiddleFragment
import org.example.todolistapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val pagerAdapter = PagerAdapter(this)
        val tabName = listOf("시작 전", "진행 중", "완료")

        binding.viewpager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "${(tabName[position])}"
        }.attach()
    }

    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BeforeFragment()
                1 -> MiddleFragment()
                else -> AfterFragment()
            }
        }
    }
}