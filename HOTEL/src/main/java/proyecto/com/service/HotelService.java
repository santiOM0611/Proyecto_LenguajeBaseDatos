package proyecto.com.service;

import proyecto.com.model.Hotel;
import proyecto.com.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HotelService {
    
    @Autowired
    private HotelRepository repo;

    public List<Hotel> listar() {
        return repo.listar();
    }

    public Hotel obtenerPorId(int id) {
        return repo.obtenerPorId(id);
    }

    // ✅ Simplificado: solo llama al repositorio
    public String guardarHotel(Hotel hotel) {
        return repo.insertar(hotel);
    }

    // ✅ Simplificado: solo llama al repositorio
    public String actualizarHotel(Hotel hotel) {
        return repo.actualizar(hotel);
    }

    public String eliminar(int idHotel) {
        return repo.eliminar(idHotel);
    }
    
    
    
}

