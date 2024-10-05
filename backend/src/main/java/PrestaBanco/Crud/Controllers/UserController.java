package PrestaBanco.Crud.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import PrestaBanco.Crud.Entities.UserEntity;
import PrestaBanco.Crud.Services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;

    //Get a user
    /**
     * Controller that allows obtaining a client's data.
     * @param  identifyingDocument String with the client's identifying document.
     * @return A UserEntity with the client's data found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id) {
        try {
            // The user is searched in the database.
            UserEntity user = userService.findUserById(id);
            // The user found is returned.
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // If the user is not found, return null.
            return null;
        }
    }

    //Get all users
    /**
     * Controller that allows obtaining all the clients in the database.
     * @return A List with all the clients found.
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        // All users are searched in the database.
        List<UserEntity> users = userService.findAll();
        // The users found are returned.
        return ResponseEntity.ok(users);
    }

    //Create a new user
    /**
     * Controller that allows creating a new client.
     * @param body A Map with the client's data to save.
     * @return A UserEntity with the client's data saved.
     */
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<UserEntity> registerUser( @RequestParam("file") MultipartFile file, 
                            @RequestParam("identifyingDocument") String identifyingDocument, 
                            @RequestParam("name") String name, 
                            @RequestParam("email") String email, 
                            @RequestParam("password") String password){
        try {
            String filename = file.getOriginalFilename();
            // If the file is a PDF, the client is saved in the database.
            if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                // The file is converted to bytes.
                byte[] pdfBytes = file.getBytes();
                UserEntity user = new UserEntity(identifyingDocument, name, email, password, pdfBytes);
                userService.saveUser(user);
                // The user is saved in the database.
                return ResponseEntity.ok(user);
            } else {
                // If the file is not a PDF, return null.
                return null;
            }
        } catch (Exception e) {
            // If there is an error, return null.
            return null;
        }
    }

    //Update a user
    /**
     * Controller that allows updating a client's data.
     * @param body A Map with the client's data to update.
     * @return A UserEntity with the client's data updated.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> updateUser(
        @RequestParam("file") MultipartFile file, 
        @RequestParam("email") String email,
        @PathVariable Long id) {
        try {
            String filename = file.getOriginalFilename();
            if (filename != null && filename.toLowerCase().endsWith(".pdf")) {
                byte[] pdfBytes = file.getBytes();
                UserEntity user = userService.findById(id);
                user.setFile(pdfBytes);
                user.setEmail(email);
                userService.saveUser(user);
                return ResponseEntity.ok(user);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    //Delete a user
    /**
     * Controller that allows deleting a client.
     * @param id A Long with the client's id to delete.
     * @return A boolean that indicates if the client was deleted.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        try {
            // The user is searched in the database.
            UserEntity user = userService.findUserById(id);
            // The user is deleted from the database.
            userService.deleteUser(user);
            // If the user is deleted, return true.
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // If the user is not found, return false.
            return ResponseEntity.ok(false);
        }
    }

    //User Mortgage Credit Simulation
    /**
     * Controller that allows simulating a mortgage loan.
     * @param body A Map with the data of the loan to simulate.
     * @return An int with the monthly payment of the loan.
     */
    @GetMapping("/simulation")
    public int simulation(@RequestBody Map<String, String> body) {
        try {
            // The amount of the loan is obtained.
            int amount = Integer.parseInt(body.get("amount"));
            // The type of loan is obtained.
            String type = body.get("type");
            // The term of the loan is obtained.
            int term = Integer.parseInt(body.get("term"));
            // The interest rate of the loan is obtained.
            double interestRate = Double.parseDouble(body.get("interestRate"));
            // The monthly payment of the loan is calculated.
            return userService.simulation(amount, type, term, interestRate);
        } catch (Exception e) {
            // If there is an error, return 0.
            return 0;
        }
    }
}
