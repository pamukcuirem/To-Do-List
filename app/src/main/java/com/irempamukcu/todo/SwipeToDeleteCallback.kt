// SwipeToDeleteCallback.kt
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.irempamukcu.todo.TaskItemAdapter

class SwipeToDeleteCallback(private val adapter: TaskItemAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // No-op
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Get the position of the swiped item
        val position = viewHolder.adapterPosition
        // Notify the adapter to remove the item
        adapter.removeItem(position)
    }
}
