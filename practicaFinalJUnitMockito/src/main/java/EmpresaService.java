import java.util.List;

public class EmpresaService {
    private EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa guardarEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa obtenerEmpresaPorId(Long id) {
        return empresaRepository.findById(id).orElse(null);
    }

    public List<Empresa> obtenerTodasLasEmpresas() {
        return empresaRepository.findAll();
    }

    public void eliminarEmpresaPorId(Long id) {
        empresaRepository.deleteById(id);
    }
}
