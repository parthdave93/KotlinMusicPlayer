
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AppCompatActivity


inline fun <reified T : Activity> Context.startActivityWithBundle(context: Context, openActivity: Class<T>, bundle: Bundle) {
    var intent = Intent(context, openActivity)
    intent.putExtras(bundle)
    context.startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivityWithBundle(context: Context, openActivity: Class<T>, bundle: Bundle,options: ActivityOptionsCompat) {
    var intent = Intent(context, openActivity)
    intent.putExtras(bundle)
    if (options != null)
        context.startActivity(intent, options.toBundle())
}

inline fun startActivity(context: Context, openActivity: Class<AppCompatActivity>, bundle: Bundle, flag: Int) {

    var intent = Intent(context, openActivity)
    intent.setFlags(flag);
    intent.putExtras(bundle)
    context.startActivity(intent)
}