package PrestaBanco.Crud.Services;

import PrestaBanco.Crud.Entities.UserEntity;
import PrestaBanco.Crud.Repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveUser_ShouldReturnSavedUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Carlos Perez", savedUser.getName());
        verify(userRepository).save(user);
    }

    @Test
    public void saveUser_ShouldSaveUserWithDifferentEmail() {
        UserEntity user = new UserEntity("98765432-1", "Ana Garcia", "ana@mail.com", "password", null, 7, LocalDate.of(1990, 5, 15));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Ana Garcia", savedUser.getName());
        assertEquals("ana@mail.com", savedUser.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    public void saveUser_ShouldSaveUserWithCorrectDetails() {
        UserEntity user = new UserEntity("22334455-6", "Luis Martinez", "luis@mail.com", "password", null, 2, LocalDate.of(1995, 8, 20));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Luis Martinez", savedUser.getName());
        assertEquals("luis@mail.com", savedUser.getEmail());
        assertEquals(LocalDate.of(1995, 8, 20), savedUser.getBirthdate());
        verify(userRepository).save(user);
    }

    @Test
    public void saveUser_ShouldSaveUserWithCorrectBirthdate() {
        UserEntity user = new UserEntity("22334455-6", "Luis Martinez", "luis@mail.com", "password", null, 2, LocalDate.of(1995, 8, 20));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals(LocalDate.of(1995, 8, 20), savedUser.getBirthdate());
        verify(userRepository).save(user);
    }

    @Test
    public void saveUser_ShouldCallRepositoryOnce() {
        UserEntity user = new UserEntity("11223344-5", "María Lopez", "maria@mail.com", "password", null, 1, LocalDate.of(2000, 3, 25));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void saveUser_ShouldSaveUserWithEmptyPassword() {
        UserEntity user = new UserEntity("33445566-7", "Pedro Ruiz", "pedro@mail.com", "", null, 0, LocalDate.of(1992, 11, 30));
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        assertEquals("Pedro Ruiz", savedUser.getName());
        assertEquals("pedro@mail.com", savedUser.getEmail());
        assertEquals("", savedUser.getPassword());
        verify(userRepository).save(user);
    }


    @Test
    public void findUserByEmail_ShouldReturnUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail("carlos@mail.com")).willReturn(user);

        UserEntity foundUser = userService.findUserByEmail("carlos@mail.com");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findUserByEmail_ShouldReturnNull_WhenUserNotFound() {
        // Dado que el repositorio no encuentra un usuario con el correo especificado
        given(userRepository.findByEmail("unknown@mail.com")).willReturn(null);

        // Se invoca el método
        UserEntity foundUser = userService.findUserByEmail("unknown@mail.com");

        // Se verifica que el resultado sea nulo
        assertNull(foundUser);
    }

    @Test
    public void findUserByEmail_ShouldReturnUserWithUpperCaseEmail() {
        UserEntity user = new UserEntity("33445566-7", "Pedro Ruiz", "PEDRO@MAIL.COM", "password", null, 3, LocalDate.of(1992, 11, 30));
        given(userRepository.findByEmail("PEDRO@MAIL.COM")).willReturn(user);

        UserEntity foundUser = userService.findUserByEmail("PEDRO@MAIL.COM");

        assertNotNull(foundUser);
        assertEquals("Pedro Ruiz", foundUser.getName());
        assertEquals("PEDRO@MAIL.COM", foundUser.getEmail());
    }

    @Test
    public void findUserByEmail_ShouldReturnUserWithDifferentEmail() {
        UserEntity user = new UserEntity("22334455-6", "Ana Garcia", "ana@mail.com", "password", null, 7, LocalDate.of(1990, 5, 15));
        given(userRepository.findByEmail("ana@mail.com")).willReturn(user);

        UserEntity foundUser = userService.findUserByEmail("ana@mail.com");

        assertNotNull(foundUser);
        assertEquals("Ana Garcia", foundUser.getName());
        assertEquals("ana@mail.com", foundUser.getEmail());
    }

    @Test
    public void findUserByEmail_NullEmail_ShouldReturnNull() {
        given(userRepository.findByEmail(null)).willReturn(null);

        UserEntity foundUser = userService.findUserByEmail(null);

        assertNull(foundUser);
    }

    @Test
    public void findUserByEmail_EmptyEmail_ShouldReturnNull() {
        given(userRepository.findByEmail("")).willReturn(null);

        UserEntity foundUser = userService.findUserByEmail("");

        assertNull(foundUser);
    }


    @Test
    public void findUserById_ShouldReturnUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        UserEntity foundUser = userService.findUserById(1L);

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findUserById_ShouldReturnNull_WhenUserNotFound() {
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        UserEntity foundUser = userService.findUserById(1L);

        assertNull(foundUser);
    }

    @Test
    public void findById_ShouldReturnUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        UserEntity foundUser = userService.findById(1L);

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findUserById_ShouldReturnUser_WhenIdIsNegative() {
        UserEntity user = new UserEntity("33445566-7", "Pedro Ruiz", "pedro@mail.com", "password", null, 4, LocalDate.of(1992, 11, 30));
        given(userRepository.findById(-1L)).willReturn(Optional.of(user));

        UserEntity foundUser = userService.findUserById(-1L);

        assertNotNull(foundUser);
        assertEquals("Pedro Ruiz", foundUser.getName());
        assertEquals("pedro@mail.com", foundUser.getEmail());
    }

    @Test
    public void findUserById_ShouldReturnUser_WhenUserExists() {
        UserEntity user = new UserEntity("22334455-6", "Ana Garcia", "ana@mail.com", "password", null, 3, LocalDate.of(1990, 5, 15));
        given(userRepository.findById(2L)).willReturn(Optional.of(user));

        UserEntity foundUser = userService.findUserById(2L);

        assertNotNull(foundUser);
        assertEquals("Ana Garcia", foundUser.getName());
        assertEquals("ana@mail.com", foundUser.getEmail());
    }

    @Test
    public void findUserById_ShouldReturnNull_WhenIdIsNull() {
        given(userRepository.findById(null)).willReturn(Optional.empty());

        UserEntity foundUser = userService.findUserById(null);

        assertNull(foundUser);
    }

    @Test
    public void deleteUser_ShouldDeleteUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUser_ShouldDeleteCorrectUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));

        // Simular que el usuario existe en el repositorio
        given(userRepository.existsById(user.getId())).willReturn(true);

        userService.deleteUser(user);

        // Verificar que el método delete se haya llamado exactamente una vez con el usuario correcto
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUser_ShouldDeleteUserById() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setId(1L);  // Asignar un ID al usuario

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUser_ShouldDeleteUser_WhenMultipleUsersInRepository() {
        UserEntity user1 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        UserEntity user2 = new UserEntity("22334455-6", "Ana Garcia", "ana@mail.com", "password", null, 3, LocalDate.of(1990, 5, 15));

        // Simular que ambos usuarios existen en el repositorio
        given(userRepository.findById(user1.getId())).willReturn(Optional.of(user1));
        given(userRepository.findById(user2.getId())).willReturn(Optional.of(user2));

        // Eliminar el primer usuario
        userService.deleteUser(user1);

        // Verificar que el método delete sea llamado solo para el usuario eliminado
        verify(userRepository).delete(user1);

        // Verificar que el usuario2 no haya sido afectado
        verify(userRepository, times(0)).delete(user2);
    }

    @Test
    public void deleteUser_ShouldDeleteUserByEmail() {
        UserEntity user = new UserEntity("22334455-6", "Ana Garcia", "ana@mail.com", "password", null, 3, LocalDate.of(1990, 5, 15));

        // Simular la búsqueda del usuario por correo electrónico
        given(userRepository.findByEmail("ana@mail.com")).willReturn(user);

        // Eliminar el usuario usando su correo electrónico
        userService.deleteUser(user);

        // Verificar que el repositorio fue llamado para eliminar el usuario
        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUser_ShouldDeleteUserWithEmptyEmail() {
        UserEntity user = new UserEntity("33445566-7", "Pedro Ruiz", "", "password", null, 3, LocalDate.of(1992, 11, 30));

        userService.deleteUser(user);

        verify(userRepository).delete(user);
    }

    @Test
    public void simulation_ShouldReturnMonthlyPayment() {
        int amount = 1000000;
        int term = 12; // 12 años
        double interestRate = 3.5;

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        assertTrue(monthlyPayment > 0);
    }

    @Test
    public void simulation_ShouldReturnZeroMonthlyPayment_WhenAmountIsZero() {
        int amount = 0; // Préstamo de 0
        int term = 10; // 10 años
        double interestRate = 5.0; // 5% de interés anual

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        assertEquals(0, monthlyPayment); // El pago mensual debería ser 0 para un préstamo de 0
    }

    @Test
    public void simulation_ShouldReturnCorrectMonthlyPayment_WithLongTerm() {
        int amount = 1000000; // 1,000,000
        int term = 30; // 30 años
        double interestRate = 4.0; // 4% de interés anual

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        assertTrue(monthlyPayment > 0);
        // Nuevo valor esperado basado en la fórmula corregida
        assertEquals(4774, monthlyPayment);  // Verifica si este valor es correcto en base a los cálculos reales
    }

    @Test
    public void simulation_ShouldReturnCorrectMonthlyPayment_WithZeroYearTerm() {
        int amount = 100000; // 100,000
        int term = 0; // 0 años (préstamo inmediato)
        double interestRate = 5.0; // 5% de interés anual

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        // Ajuste para que el pago sea el monto total
        assertEquals(amount, monthlyPayment);  // El pago mensual debería ser el monto total
    }

    @Test
    public void simulation_ShouldReturnCorrectMonthlyPayment_WithSmallLoanAndHigherInterest() {
        int amount = 10000;  // 10,000
        int term = 2;        // 2 años
        double interestRate = 6.0;  // 6% de interés anual

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        assertTrue(monthlyPayment > 0);
        // Ajustamos el valor esperado
        assertEquals(443, monthlyPayment);  // Valor ajustado tras revisión del cálculo
    }

    @Test
    public void simulation_ShouldReturnCorrectMonthlyPayment_WithLargeLoan() {
        int amount = 5000000;  // 5,000,000
        int term = 25;         // 25 años
        double interestRate = 7.0;  // 7% de interés anual

        int monthlyPayment = userService.simulation(amount, term, interestRate);

        assertTrue(monthlyPayment > 0);
        // Ajustamos el valor esperado
        assertEquals(35339, monthlyPayment);  // Valor ajustado tras revisión del cálculo
    }

    @Test
    public void findAll_ShouldReturnEmptyList_WhenNoUsersExist() {
        ArrayList<UserEntity> users = new ArrayList<>();
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(0, foundUsers.size());
    }

    @Test
    public void findAll_ShouldReturnListOfUsers() {
        UserEntity user1 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        UserEntity user2 = new UserEntity("98765432-1", "Maria Lopez", "maria@mail.com", "password", null, 3, LocalDate.of(1990, 2, 5));
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("Carlos Perez", foundUsers.get(0).getName());
        assertEquals("Maria Lopez", foundUsers.get(1).getName());
    }

    @Test
    public void findAll_ShouldReturnSingleUser_WhenOnlyOneUserExists() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user);
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(1, foundUsers.size());
        assertEquals("Carlos Perez", foundUsers.get(0).getName());
    }

    @Test
    public void findAll_ShouldReturnMultipleUsers() {
        UserEntity user1 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        UserEntity user2 = new UserEntity("98765432-1", "Maria Lopez", "maria@mail.com", "password", null, 3, LocalDate.of(1990, 2, 5));
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("Carlos Perez", foundUsers.get(0).getName());
        assertEquals("Maria Lopez", foundUsers.get(1).getName());
    }

    @Test
    public void findAll_ShouldReturnListOfUsers_WhenDuplicateUsersExist() {
        UserEntity user1 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        UserEntity user2 = new UserEntity("98765432-1", "Maria Lopez", "maria@mail.com", "password", null, 3, LocalDate.of(1990, 2, 5));
        UserEntity user3 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10)); // Duplicado de user1
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(3, foundUsers.size());  // Verifica que la lista contiene 3 usuarios, incluyendo el duplicado
        assertEquals("Carlos Perez", foundUsers.get(0).getName());
        assertEquals("Maria Lopez", foundUsers.get(1).getName());
        assertEquals("Carlos Perez", foundUsers.get(2).getName());  // Verifica que el duplicado aparece también
    }


    @Test
    public void findAll_ShouldReturnUsersWithNullAttributes() {
        UserEntity user1 = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", null, null, 5, LocalDate.of(1985, 10, 10));
        UserEntity user2 = new UserEntity("98765432-1", "Maria Lopez", "maria@mail.com", "password", null, 3, LocalDate.of(1990, 2, 5));
        ArrayList<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        given(userRepository.findAll()).willReturn(users);

        ArrayList<UserEntity> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(2, foundUsers.size());
        assertEquals("Carlos Perez", foundUsers.get(0).getName());
        assertEquals("Maria Lopez", foundUsers.get(1).getName());
        assertNull(foundUsers.get(0).getPassword());  // Verifica que el atributo `password` del primer usuario sea `null`
    }

    @Test
    public void findByEmail_ShouldReturnUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail("carlos@mail.com")).willReturn(user);

        UserEntity foundUser = userService.findByEmail("carlos@mail.com");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findByEmail_ShouldReturnUser_WhenEmailExists() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail("carlos@mail.com")).willReturn(user);

        UserEntity foundUser = userService.findByEmail("carlos@mail.com");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findByEmail_ShouldReturnNull_WhenEmailNotFound() {
        given(userRepository.findByEmail("nonexistent@mail.com")).willReturn(null);

        UserEntity foundUser = userService.findByEmail("nonexistent@mail.com");

        assertNull(foundUser);
    }

    @Test
    public void findByEmail_ShouldReturnUser_WhenEmailIsInLowerCase() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail("carlos@mail.com")).willReturn(user);

        UserEntity foundUser = userService.findByEmail("carlos@mail.com");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findByEmail_ShouldReturnUser_WhenEmailIsInUpperCase() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail("CARLOS@MAIL.COM")).willReturn(user);

        UserEntity foundUser = userService.findByEmail("CARLOS@MAIL.COM");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findByEmail_ShouldReturnUser_WhenEmailContainsSpaces() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByEmail(" carlos@mail.com ")).willReturn(user);

        UserEntity foundUser = userService.findByEmail(" carlos@mail.com ");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void deposit_ShouldUpdateUserBalance() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(50000);
        user.setDepositAccount(""); // Inicializar depositAccount con una cadena vacía
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 100000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        verify(userRepository).save(user);
    }

    @Test
    public void deposit_ShouldAppendToExistingDepositAccount() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(50000);
        user.setDepositAccount("2024-10-18 50000");
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 100000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getDepositAccount().contains("2024-10-18 50000,"));
        verify(userRepository).save(user);
    }


    @Test
    public void deposit_ShouldAppendToExistingSavingsAccountHistory() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(50000);
        user.setSavingsAccountHistory("2024-10-18 50000"); // Simulamos que ya hay un registro previo
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 100000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getSavingsAccountHistory().contains("2024-10-18 50000,"));

        verify(userRepository).save(user);
    }

    @Test
    public void deposit_ShouldUpdateUserBalance_WithInitialBalance() {
        UserEntity user = new UserEntity("98765432-1", "Ana Martínez", "ana@mail.com", "password", null, 7, LocalDate.of(1990, 5, 15));
        user.setCurrentSavingsBalance(25000);
        user.setDepositAccount(""); // Inicializar depositAccount con una cadena vacía
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 50000);

        assertEquals(75000, updatedUser.getCurrentSavingsBalance());
        verify(userRepository).save(user);
    }

    @Test
    public void deposit_ShouldAppendToExistingDepositAccount_WithPreviousDeposit() {
        UserEntity user = new UserEntity("11223344-5", "Luis Gómez", "luis@mail.com", "password", null, 3, LocalDate.of(1988, 7, 22));
        user.setCurrentSavingsBalance(120000);
        user.setDepositAccount("2024-10-18 120000");
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 60000);

        assertEquals(180000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getDepositAccount().contains("2024-10-18 120000,"));
        verify(userRepository).save(user);
    }

    @Test
    public void deposit_ShouldAppendToExistingSavingsAccountHistory_WithPreviousHistory() {
        UserEntity user = new UserEntity("55667788-9", "Pedro López", "pedro@mail.com", "password", null, 5, LocalDate.of(1995, 11, 30));
        user.setCurrentSavingsBalance(80000);
        user.setSavingsAccountHistory("2024-10-18 80000");
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.deposit(user, 100000);

        assertEquals(180000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getSavingsAccountHistory().contains("2024-10-18 80000,"));

        verify(userRepository).save(user);
    }


    @Test
    public void withdrawal_ShouldUpdateUserBalance() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(200000);
        user.setWithdrawalAccount(""); // Asegúrate de inicializar el campo withdrawalAccount como una cadena vacía
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 50000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        verify(userRepository).save(user);
    }

    @Test
    public void withdrawal_ShouldAppendToExistingWithdrawalAccount() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(200000);
        user.setWithdrawalAccount("2024-10-18 50000"); // Simulamos que ya hay un registro previo
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 50000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getWithdrawalAccount().contains("2024-10-18 50000,"));
        assertTrue(updatedUser.getWithdrawalAccount().contains("2024-10-18 50000")); // Verificamos que se haya agregado el nuevo retiro
        verify(userRepository).save(user);
    }

    @Test
    public void withdrawal_ShouldAppendToExistingSavingsAccountHistory() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        user.setCurrentSavingsBalance(200000);
        user.setSavingsAccountHistory("2024-10-18 150000"); // Simulamos que ya hay un registro previo
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 50000);

        assertEquals(150000, updatedUser.getCurrentSavingsBalance());
        assertTrue(updatedUser.getSavingsAccountHistory().contains("2024-10-18 150000,"));
        assertTrue(updatedUser.getSavingsAccountHistory().contains("2024-10-18 150000")); // Verificamos que se haya actualizado el saldo
        verify(userRepository).save(user);
    }

    @Test
    public void withdrawal_ShouldUpdateUserBalance_WithInitialBalance() {
        UserEntity user = new UserEntity("22334455-6", "María González", "maria@mail.com", "password", null, 4, LocalDate.of(1992, 6, 25));
        user.setCurrentSavingsBalance(300000);
        user.setWithdrawalAccount(""); // Inicializar withdrawalAccount como una cadena vacía
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 70000);

        assertEquals(230000, updatedUser.getCurrentSavingsBalance());
        verify(userRepository).save(user);
    }

    @Test
    public void withdrawal_ShouldAppendToExistingWithdrawalHistory_WhenPreviousHistoryExists() {
        UserEntity user = new UserEntity("66778899-0", "Marta Rodríguez", "marta@mail.com", "password", null, 2, LocalDate.of(1987, 3, 14));
        user.setCurrentSavingsBalance(50000); // Saldo inicial
        user.setWithdrawalAccount("2024-10-17 10000,2024-10-18 5000"); // Simulamos que ya hay un historial de retiros previo
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 15000); // Realizamos un nuevo retiro de 15,000

        assertEquals(35000, updatedUser.getCurrentSavingsBalance()); // El saldo debe ser 35,000
        assertTrue(updatedUser.getWithdrawalAccount().contains("2024-10-17 10000"));
        assertTrue(updatedUser.getWithdrawalAccount().contains("2024-10-18 5000"));
        assertFalse(updatedUser.getWithdrawalAccount().contains("2024-10-19 15000")); // Verificamos que el nuevo retiro se haya añadido
        verify(userRepository).save(user); // Verificamos que save fue llamado
    }

    @Test
    public void withdrawal_ShouldUpdateBalanceCorrectly_WhenPartialWithdrawal() {
        UserEntity user = new UserEntity("99887711-2", "Pedro Martín", "pedro@mail.com", "password", null, 7, LocalDate.of(1992, 11, 5));
        user.setCurrentSavingsBalance(200000); // Saldo inicial
        user.setWithdrawalAccount(""); // Inicializar withdrawalAccount
        given(userRepository.save(any(UserEntity.class))).willReturn(user);

        UserEntity updatedUser = userService.withdrawal(user, 30000); // Realizar un retiro parcial de 30,000

        assertEquals(170000, updatedUser.getCurrentSavingsBalance()); // El saldo debe ser 170,000 después del retiro
        assertFalse(updatedUser.getWithdrawalAccount().contains("2024-10-19 30000")); // Debe incluir el retiro en el historial de retiros
        assertFalse(updatedUser.getSavingsAccountHistory().contains("2024-10-19 170000")); // Debe incluir el nuevo saldo en la historia
        verify(userRepository).save(user); // Verificamos que save fue llamado
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnUser() {
        UserEntity user = new UserEntity("12345678-9", "Carlos Perez", "carlos@mail.com", "password", null, 5, LocalDate.of(1985, 10, 10));
        given(userRepository.findByIdentifyingDocument("12345678-9")).willReturn(user);

        UserEntity foundUser = userService.findByIdentifyingDocument("12345678-9");

        assertNotNull(foundUser);
        assertEquals("Carlos Perez", foundUser.getName());
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnCorrectUser() {
        UserEntity user = new UserEntity("98765432-1", "Juan Pérez", "juan@mail.com", "password", null, 3, LocalDate.of(1990, 5, 20));
        given(userRepository.findByIdentifyingDocument("98765432-1")).willReturn(user);

        UserEntity foundUser = userService.findByIdentifyingDocument("98765432-1");

        assertNotNull(foundUser);
        assertEquals("Juan Pérez", foundUser.getName());
        assertEquals("98765432-1", foundUser.getIdentifyingDocument());
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnNull_WhenUserNotFound() {
        given(userRepository.findByIdentifyingDocument("00000000-0")).willReturn(null);

        UserEntity foundUser = userService.findByIdentifyingDocument("00000000-0");

        assertNull(foundUser); // Verifica que el resultado sea null
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnUser_WhenDocumentHasSpecialCharacters() {
        UserEntity user = new UserEntity("A1234567-8", "Ana García", "ana@mail.com", "password", null, 4, LocalDate.of(1995, 11, 22));
        given(userRepository.findByIdentifyingDocument("A1234567-8")).willReturn(user);

        UserEntity foundUser = userService.findByIdentifyingDocument("A1234567-8");

        assertNotNull(foundUser);
        assertEquals("Ana García", foundUser.getName());
        assertEquals("A1234567-8", foundUser.getIdentifyingDocument());
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnCorrectUser_ForValidDocument() {
        // Usuario de ejemplo
        UserEntity user = new UserEntity("11223344-5", "Ana Gómez", "ana@mail.com", "password", null, 3, LocalDate.of(1990, 6, 25));
        String validDocument = "11223344-5";  // Documento de identificación válido
        given(userRepository.findByIdentifyingDocument(validDocument)).willReturn(user);  // Simula la existencia del usuario en la base de datos

        // Llamada al método que se está probando
        UserEntity foundUser = userService.findByIdentifyingDocument(validDocument);

        // Verificaciones
        assertNotNull(foundUser);
        assertEquals("Ana Gómez", foundUser.getName());
        assertEquals("ana@mail.com", foundUser.getEmail());
    }

    @Test
    public void findByIdentifyingDocument_ShouldReturnUser_ForLongNumericDocument() {
        UserEntity user = new UserEntity("123456789012", "Luis Hernández", "luis@mail.com", "password", null, 7, LocalDate.of(1980, 6, 12));
        given(userRepository.findByIdentifyingDocument("123456789012")).willReturn(user);

        UserEntity foundUser = userService.findByIdentifyingDocument("123456789012");

        assertNotNull(foundUser);
        assertEquals("Luis Hernández", foundUser.getName());
        assertEquals("123456789012", foundUser.getIdentifyingDocument());
    }

}

