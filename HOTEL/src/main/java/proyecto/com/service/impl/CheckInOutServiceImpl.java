package proyecto.com.service.impl;

import proyecto.com.model.CheckInOut;
import proyecto.com.repository.CheckInOutRepository;
import proyecto.com.service.CheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CheckInOutServiceImpl implements CheckInOutService {

    @Autowired
    private CheckInOutRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<CheckInOut> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    @Transactional(readOnly = true)
    public CheckInOut buscarPorId(Long idCheck) {
        return repository.buscarPorId(idCheck);
    }

    @Override
    @Transactional
    public String crear(CheckInOut check) {
        return repository.insertar(check);
    }

    @Override
    @Transactional
    public String actualizar(CheckInOut check) {
        return repository.actualizar(check);
    }

    @Override
    @Transactional
    public String eliminar(Long idCheck) {
        return repository.eliminar(idCheck);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> listarReservaciones() {
        return repository.listarReservaciones();
    }
}