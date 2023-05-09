import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.foryou.Model.Contents.District

class SpinnerAdapter(context: Context, districts: List<District>) :
    ArrayAdapter<District>(context, android.R.layout.simple_spinner_item, districts) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false)

        getItem(position)?.let { district ->
            val text = view.findViewById<TextView>(android.R.id.text1)
            text.text = district.name
        }

        return view
    }
}
