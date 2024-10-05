package PrestaBanco.Crud.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import PrestaBanco.Crud.Repositories.UserRepository;
import PrestaBanco.Crud.Entities.UserEntity;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Create a new user
    /**
     * Save a client in the database.
     * @param user A UserEntity with the data of the client to save.
     * @return A UserEntity with the data of the client saved.
     */
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    //Read a user
    /**
     * Search for a client in the database.
     * @param email A String with the email of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Search for a user by id
    /**
     * Search for a client in the database.
     * @param id A Long with the id of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    //Delete a user
    /**
     * Delete a client from the database.
     * @param customer A UserEntity with the customer data to delete.
     */
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    //Simulation of a loan
    /**
     * Simulate a loan.
     * @param amount An int with the amount of the loan.
     * @param type A String with the type of loan.
     * @param term An int with the term of the loan.
     * @param interestRate A double with the interest rate of the loan.
     * @return An int with the monthly payment of the loan.
     */
    public int simulation(int amount, String type, int term, double interestRate) {
        double monthlyInterestRate = (interestRate / 12);
        int termInMonths = term * 12;
        double monthlyPayment = amount * ((monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termInMonths)) / (Math.pow(1 + monthlyInterestRate, termInMonths) - 1));
        return (int) monthlyPayment;
    }

    //List all users
    /**
     * List all clients in the database.
     * @return An ArrayList with all the clients found.
     */
    public ArrayList<UserEntity> findAll() {
        return (ArrayList<UserEntity>) userRepository.findAll();  
    }

    //Search for a user by email
    /**
     * Search for a client in the database.
     * @param email A String with the email of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    //Search for a user by identifying document
    /**
     * Search for a client in the database.
     * @param identifyingDocument A String with the identifying document of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public UserEntity findByIdentifyingDocument(String identifyingDocument) {
        return userRepository.findByIdentifyingDocument(identifyingDocument);
    }

    //Search for a user by id
    /**
     * Search for a client in the database.
     * @param id A Long with the id of the client to search.
     * @return A UserEntity with the data of the client found.
     */
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

