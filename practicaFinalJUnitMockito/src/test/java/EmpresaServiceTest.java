import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EmpresaService empresaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardarEmpresa() {
        // Crear una nueva empresa
        Empresa empresa = new Empresa( "Mcdonalds", "Mcdonalds S.A", 43225327);
        when(empresaRepository.save(empresa)).thenReturn(empresa);

        // Guardar una empresa
        Empresa empresaGuardada = empresaService.guardarEmpresa(empresa);


        // Verificar que la empresa ha sido guardada correctamente
        assertThat(empresaGuardada.getNombre()).isEqualTo("Mcdonalds");
        assertThat(empresaGuardada.getRazonSocial()).isEqualTo("Mcdonalds S.A");
        assertThat(empresaGuardada.getCuil()).isEqualTo(43225327);

        //verificar que el metodo para crear la empresa ha sido utilizado
        verify(empresaRepository).save(empresa);

    }

    @Test
    public void testObtenerEmpresaPorId(){

        //Crear una nueva Empresa
        Empresa empresa = new Empresa( "Mcdonalds", "Mcdonalds S.A", 43225327);
        when(empresaRepository.findById(1L)).thenReturn(Optional.of(empresa));

        //Recuperar empresa por su id
        Empresa empresaRecuperada = empresaService.obtenerEmpresaPorId(1L);

        // Verificar que el artículo recuperado es el mismo que el esperado
        assertThat(empresaRecuperada).isNotNull();
        assertThat(empresaRecuperada.getNombre()).isEqualTo("Mcdonalds");
        assertThat(empresaRecuperada.getRazonSocial()).isEqualTo("Mcdonalds S.A");
        assertThat(empresaRecuperada.getCuil()).isEqualTo(43225327);

        // Verificar que el método findById del repositorio fue llamado
        verify(empresaRepository).findById(1L);
    }


    @Test
    public void testObtenerTodasLasEmpresas() {
        // Crear y guardar varias empresas
        Empresa empresa1 = new Empresa( "Mcdonalds","Mcdonalds S.A",36253275);
        Empresa empresa2 = new Empresa( "Mercedez Benz","Mercedez Benz S.A",63247627);
        when(empresaRepository.findAll()).thenReturn(Arrays.asList(empresa1, empresa2));


        // Recuperar todas las empresas
        List<Empresa> empresas = empresaService.obtenerTodasLasEmpresas();

        // Verificar que se han recuperado las empresas correctas
        assertThat(empresas).hasSize(2);
        assertThat(empresas).extracting(Empresa::getNombre).contains("Mcdonalds", "Mercedez Benz");

        // Verificar que el método findAll del repositorio fue llamado
        verify(empresaRepository).findAll();
    }

    @Test
    public void testEliminarEmpresaPorId() {
        // Eliminar una empresa por su ID
        empresaService.eliminarEmpresaPorId(1L);

        // Verificar que el método deleteById fue llamado con el ID correcto
        verify(empresaRepository).deleteById(1L);
    }

    @Test
    public void testGuardarEmpresa_LanzaExcepcion() {
        // Crear una nueva empresa
        Empresa empresa = new Empresa( "Mcdonalds","Mcdonalds S.A",36253275);
        when(empresaRepository.save(empresa)).thenThrow(new RuntimeException("Error al guardar"));

        // Verificar que la excepción se lanza
        try {
            empresaService.guardarEmpresa(empresa);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Error al guardar");
        }

        // Verificar que el método save del repositorio fue llamado
        verify(empresaRepository).save(empresa);
    }
}

