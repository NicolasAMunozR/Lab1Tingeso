package PrestaBanco.Crud.Repositories;

import PrestaBanco.Crud.Entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void setup() {
        // Crear un usuario para usar en las pruebas
        user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(100000);
        userRepository.save(user);
    }

    @Test
    public void findByEmail_ShouldReturnUser() {
        // Buscar usuario por email
        UserEntity foundUser = userRepository.findByEmail("carlos@mail.com");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
        assertEquals("12345678-9", foundUser.getIdentifyingDocument());
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnUser() {
        // Buscar usuario por documento identificador
        UserEntity foundUser = userRepository.findByIdentifyingDocument("12345678-9");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
        assertEquals("carlos@mail.com", foundUser.getEmail());
    }

    @Test
    public void findById_ShouldReturnUser() {
        // Buscar usuario por ID
        Optional<UserEntity> foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Carlos Perez", foundUser.get().getName());
    }

    @Test
    public void save_ShouldSaveUserSuccessfully() {
        // Guardar un nuevo usuario
        UserEntity newUser = new UserEntity("98765432-1", "Maria Lopez", "maria@mail.com", "password123", null, 3, LocalDate.of(1990, 2, 5));
        UserEntity savedUser = userRepository.save(newUser);

        assertNotNull(savedUser);
        assertEquals("Maria Lopez", savedUser.getName());
        assertEquals("maria@mail.com", savedUser.getEmail());
    }

    @Test
    public void delete_ShouldDeleteUser() {
        // Eliminar usuario
        userRepository.delete(user);

        Optional<UserEntity> deletedUser = userRepository.findById(user.getId());

        assertFalse(deletedUser.isPresent());
    }
}

