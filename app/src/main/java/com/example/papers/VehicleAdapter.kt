package com.example.papers
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class VehicleAdapter(private val context: Context, private val vehicles: List<AddDocumentActivity>) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicles[position]

        // Mostrar los datos del vehículo
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    inner class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val imgVehicle: ImageView = itemView.findViewById(R.id.imgVehicle)
        private val txtVehicleType: TextView = itemView.findViewById(R.id.txtVehicleType)
        private val txtPlate: TextView = itemView.findViewById(R.id.txtVehiclePlate)

        fun bind(vehicle: AddDocumentActivity) {
            // Mostrar los datos del vehículo
            imgVehicle.setImageURI(Uri.parse(vehicle.imageUri.toString()))
            txtVehicleType.text = vehicle.vehicleType
            txtPlate.text = vehicle.plate

            // Si deseas agregar clics a los elementos del card view, puedes hacerlo aquí
            cardView.setOnClickListener {
                // Lógica de clic del card view, si es necesario
            }
        }
    }
}

