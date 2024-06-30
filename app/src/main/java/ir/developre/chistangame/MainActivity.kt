package ir.developre.chistangame

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.developre.chistangame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController = findNavController(R.id.my_nav_host_fragment)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.Watery)
        window.statusBarColor = ContextCompat.getColor(this, R.color.friendlyFrost)



//        navController.addOnDestinationChangedListener { _, destination, _ ->
//
//            when (destination.id) {
//                R.id.splashFragment -> {
//                    window.statusBarColor =
//                        ContextCompat.getColor(this, R.color.background_main)
//                    window.navigationBarColor =
//                        ContextCompat.getColor(this, R.color.background_main)
//                }
//
//                else -> {
//                    window.statusBarColor =
//                        ContextCompat.getColor(this, R.color.background_main)
//                    window.navigationBarColor =
//                        ContextCompat.getColor(this, R.color.background_main)
//                }
//            }
//
//        }


//        val dialog = Dialog(this)
////        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout.layout_dialog_shop)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window!!.setGravity(Gravity.CENTER)
//        dialog.window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//        )
//        dialog.show()

    }
}